package br.com.siga.negocio;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Produto;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Validador;
import br.com.templates.utils.NegocioBase;

@AutoCreate
@Name("produtoNegocio")
@Scope(ScopeType.CONVERSATION)
public class ProdutoNegocio extends NegocioBase<Produto, Long> {

	@SuppressWarnings("unchecked")
	public List<Produto> listarAtivos() {
		Criteria criteria = getSession().createCriteria(Produto.class);

		criteria.add(Restrictions.eq("situacao", Situacao.ATIVO));
		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> pesquisar(Produto produto) {
		Criteria criteria = getSession().createCriteria(Produto.class);
		if (Validador.isStringValida(produto.getDescricao())) {
			criteria.add(Restrictions.like("descricao", produto.getDescricao(), MatchMode.ANYWHERE));
		}
		if (Validador.isEnumValido(produto.getSituacao())) {
			criteria.add(Restrictions.eq("situacao", produto.getSituacao()));
		}
		criteria.addOrder(Order.asc("descricao"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> pesquisar(Produto produto, Situacao situacao) {
		Criteria criteria = getSession().createCriteria(Produto.class);
		if (Validador.isStringValida(produto.getDescricao())) {
			criteria.add(Restrictions.like("descricao", produto.getDescricao(), MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.eq("situacao", situacao));

		criteria.addOrder(Order.asc("descricao"));
		return criteria.list();
	}
}
