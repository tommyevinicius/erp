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

import br.com.siga.dominio.Fornecedor;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Validador;
import br.com.templates.utils.NegocioBase;

@AutoCreate
@Name("fornecedorNegocio")
@Scope(ScopeType.CONVERSATION)
public class FornecedorNegocio extends NegocioBase<Fornecedor, Long>{

	@SuppressWarnings("unchecked")
	public List<Fornecedor> listarAtivos () {
		Criteria criteria = getSession().createCriteria(Fornecedor.class);
		
		criteria.add(Restrictions.eq("situacao", Situacao.ATIVO));
		criteria.addOrder(Order.asc("descricao"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Fornecedor> pesquisar(Fornecedor fornecedor) {
		Criteria criteria = getSession().createCriteria(Fornecedor.class);
		if (Validador.isStringValida(fornecedor.getDescricao())) {
			criteria.add(Restrictions.like("descricao", fornecedor.getDescricao(), MatchMode.ANYWHERE));
		}
		if (Validador.isStringValida(fornecedor.getRazaoSocial())) {
			criteria.add(Restrictions.like("razaoSocial", fornecedor.getRazaoSocial(), MatchMode.ANYWHERE));
		}
		if (Validador.isEnumValido(fornecedor.getSituacao())) {
			criteria.add(Restrictions.eq("situacao", fornecedor.getSituacao()));
		}
		if (Validador.isStringValida(fornecedor.getCnpj())) {
			criteria.add(Restrictions.like("cnpj", fornecedor.getCnpj(), MatchMode.ANYWHERE));
		}
		if (Validador.isStringValida(fornecedor.getTelefone())) {
			criteria.add(Restrictions.like("telefone", fornecedor.getTelefone()));
		}
		criteria.addOrder(Order.asc("descricao"));
		return criteria.list();
	}
	
	public Fornecedor localizar(Fornecedor fornecedor) {
		Criteria criteria = getSession().createCriteria(Fornecedor.class);
		if (Validador.isStringValida(fornecedor.getDescricao())) {
			criteria.add(Restrictions.eq("descricao", fornecedor.getDescricao()));
		}
		if (criteria.list().size() > 1 || !Validador.isObjetoValido(criteria.uniqueResult())) {
			return null;
		}
		
		return (Fornecedor) criteria.uniqueResult();
	}
}
