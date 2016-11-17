package br.com.siga.negocio;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.exception.GenericJDBCException;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;

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
			query.setParameter("nomeusuario", "%" +lancamento.getUsuario().getNome() + "%");
		}
		if (Validador.isObjetoValido(lancamento.getCliente()) && Validador.isStringValida(lancamento.getCliente().getNome())) {
			query.setParameter("nomecliente", "%" +lancamento.getCliente().getNome() + "%");
		}
		if (Validador.isObjetoValido(lancamento.getFornecedor()) && Validador.isStringValida(lancamento.getFornecedor().getDescricao())) {
			query.setParameter("descricaofornecedor", "%" +lancamento.getFornecedor().getDescricao() + "%");
		}
		if (Validador.isStringValida(lancamento.getComentario())) {
			query.setParameter("comentario", "%" +lancamento.getComentario() + "%");
		}
		if (Validador.isEnumValido(lancamento.getTipoLancamento())) { 
			query.setParameter("tipolancamento", lancamento.getTipoLancamento());
		}
		
		return query.getResultList();
	}

	public void incluirLancamento(Lancamento lancamento) throws Exception {
		if (TipoLancamento.ENTRADA.equals(lancamento.getTipoLancamento())) {
			for (LancamentoProduto lp : lancamento.getListaProdutos()) {
				if (Validador.isNumericoValido(lp.getProduto().getQuantidade())) {
					lp.getProduto().setQuantidade(lp.getProduto().getQuantidade() + lp.getQuantidade());
					alterar(lp.getProduto());
				}
			}
		} else {
			for (LancamentoProduto lp : lancamento.getListaProdutos()) {
				if (Validador.isNumericoValido(lp.getProduto().getQuantidade())) {
					Long saldo = lp.getProduto().getQuantidade() - lp.getQuantidade();
					
					if (saldo >= 0) {
						lp.getProduto().setQuantidade(saldo);
						alterar(lp.getProduto());
					} else {
						addMsg(Severity.WARN, "produto.negativo" + "\n(" + lp.getProduto().getDescricao() + ")");
						return;
					}
					
				}
			}
		}
		
		super.incluir(lancamento);
	}
	
	/**
	 * Metodo responsavel por todas as alteracoes do sistema.
	 */
	public void alterar(final Produto objeto) throws Exception {
		try {
			entityManager.merge(objeto);
			entityManager.flush();
		} catch (OptimisticLockException le) {
			le.printStackTrace();
			throw new Exception("geral.registroAlteradoOutraSessao", le);
		} catch (PersistenceException ee) {
			ee.printStackTrace();
			if (ee.getCause() instanceof GenericJDBCException) {
				SQLException sqle = ((GenericJDBCException) ee.getCause()).getSQLException();
				facesMessages.add(Severity.ERROR, sqle.getMessage());
			}
			if (ee.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				if (ee.getCause().getCause().getMessage().contains("null")) {
					throw new Exception("geral.campoNulo");
				}
				throw new Exception("registro.duplicado");
			}
			throw new Exception("geral.erroBancoDeDados", ee);
		} catch (RuntimeException re) {
			re.printStackTrace();
			facesMessages.add(Severity.ERROR, re.getMessage());
			throw new Exception("geral.erroBancoDeDados");
		}
	}
}
