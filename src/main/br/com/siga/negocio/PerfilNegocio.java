package br.com.siga.negocio;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Perfil;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Validador;
import br.com.templates.utils.NegocioBase;

@Name("perfilNegocio")
@Scope(ScopeType.CONVERSATION)
public class PerfilNegocio extends NegocioBase<Perfil, Long>{

	@SuppressWarnings("unchecked")
	public List<Perfil> listarAtivos () {
		Criteria criteria = getSession().createCriteria(Perfil.class);
		criteria.add(Restrictions.eq("situacao", Situacao.ATIVO));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Perfil> pesquisar(Perfil perfil) {
		Criteria criteria = getSession().createCriteria(Perfil.class);
		if (Validador.isStringValida(perfil.getDescricao())) {
			criteria.add(Restrictions.like("descricao", perfil.getDescricao(), MatchMode.ANYWHERE));
		}
		if (Validador.isEnumValido(perfil.getSituacao())) {
			criteria.add(Restrictions.eq("situacao", perfil.getSituacao()));
		}
		criteria.addOrder(Order.asc("descricao"));
		return criteria.list();
	}
}
