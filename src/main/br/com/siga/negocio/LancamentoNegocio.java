package br.com.siga.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Lancamento;
import br.com.siga.dominio.LancamentoProduto;
import br.com.siga.dominio.Produto;
import br.com.siga.utils.Enumerados.TipoLancamento;
import br.com.siga.utils.Validador;
import br.com.templates.utils.NegocioBase;

@AutoCreate
@Name("lancamentoNegocio")
@Scope(ScopeType.CONVERSATION)
public class LancamentoNegocio extends NegocioBase<Lancamento, Long> {

	@SuppressWarnings("unchecked")
	public List<Lancamento> listarAtivos() {
		Criteria criteria = getSession().createCriteria(Lancamento.class);

		criteria.addOrder(Order.asc("idLancamento"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> pesquisar(Lancamento lancamento) {
		StringBuilder jpql = new StringBuilder();

		// SELECT
		jpql.append(" select distinct (lan) from Lancamento lan ");

		if (Validador.isObjetoValido(lancamento.getUsuario()) && Validador.isStringValida(lancamento.getUsuario().getNome())) {
			jpql.append(" join lan.usuario user ");
		}
		if (Validador.isObjetoValido(lancamento.getCliente()) && Validador.isStringValida(lancamento.getCliente().getNome())) {
			jpql.append(" join lan.cliente cliente ");
		}
		if (Validador.isObjetoValido(lancamento.getFornecedor()) && Validador.isStringValida(lancamento.getFornecedor().getDescricao())) {
			jpql.append(" join lan.fornecedor fornecedor ");
		}

		// WHERE
		jpql.append(" where 1=1 ");

		if (Validador.isObjetoValido(lancamento.getUsuario()) && Validador.isStringValida(lancamento.getUsuario().getNome())) {
			jpql.append(" and user.nome like :nomeusuario ");
		}
		if (Validador.isObjetoValido(lancamento.getCliente()) && Validador.isStringValida(lancamento.getCliente().getNome())) {
			jpql.append(" and cliente.nome like :nomecliente ");
		}
		if (Validador.isObjetoValido(lancamento.getFornecedor()) && Validador.isStringValida(lancamento.getFornecedor().getDescricao())) {
			jpql.append(" and fornecedor.descricao like :descricaofornecedor ");
		}
		if (Validador.isStringValida(lancamento.getComentario())) {
			jpql.append(" and lan.comentario like :comentario ");
		}
		if (Validador.isEnumValido(lancamento.getTipoLancamento())) {
			jpql.append(" and lan.tipoLancamento = :tipolancamento ");
		}

		final Query query = createQuery(jpql.toString());

		// PARAMETROS
		if (Validador.isObjetoValido(lancamento.getUsuario()) && Validador.isStringValida(lancamento.getUsuario().getNome())) {
			query.setParameter("nomeusuario", "%" + lancamento.getUsuario().getNome() + "%");
		}
		if (Validador.isObjetoValido(lancamento.getCliente()) && Validador.isStringValida(lancamento.getCliente().getNome())) {
			query.setParameter("nomecliente", "%" + lancamento.getCliente().getNome() + "%");
		}
		if (Validador.isObjetoValido(lancamento.getFornecedor()) && Validador.isStringValida(lancamento.getFornecedor().getDescricao())) {
			query.setParameter("descricaofornecedor", "%" + lancamento.getFornecedor().getDescricao() + "%");
		}
		if (Validador.isStringValida(lancamento.getComentario())) {
			query.setParameter("comentario", "%" + lancamento.getComentario() + "%");
		}
		if (Validador.isEnumValido(lancamento.getTipoLancamento())) {
			query.setParameter("tipolancamento", lancamento.getTipoLancamento());
		}

		return query.getResultList();
	}

	public void incluirLancamento(Lancamento lancamento) throws Exception {
		List<Produto> produtos = new ArrayList<Produto>();
		for (LancamentoProduto lp : lancamento.getListaProdutos()) {
			produtos.add(lp.getProduto());
		}
		
		super.alterarCollection(produtos);
		super.incluir(lancamento);
	}

	public boolean permitirSaidaProduto(LancamentoProduto produtoLancado, Produto produto) {
		if (TipoLancamento.ENTRADA.equals(produtoLancado.getLancamento().getTipoLancamento())) {
			produto.setQuantidade(produto.getQuantidade() + produtoLancado.getQuantidade());
			return true;
		} else {
			Long saldo = produto.getQuantidade() - produtoLancado.getQuantidade();

			if (saldo >= 0) {
				produto.setQuantidade(saldo);
				return true;
			} else {
				return false;
			}
		}
	}
}
