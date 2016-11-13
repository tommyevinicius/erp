package br.com.siga.negocio;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Perfil;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Validador;
import br.com.templates.utils.NegocioBase;

@AutoCreate
@Name("perfilNegocio")
@Scope(ScopeType.CONVERSATION)
public class PerfilNegocio extends NegocioBase<Perfil, Long>{

	@SuppressWarnings("unchecked")
	public List<Perfil> listarAtivos () {
		Criteria criteria = getSession().createCriteria(Perfil.class);
		
		criteria.add(Restrictions.eq("situacao", Situacao.ATIVO));
		
		return (List<Perfil>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Perfil> pesquisar(Perfil perfil) {
		Criteria criteria = getSession().createCriteria(Perfil.class);
		if (Validador.isStringValida(perfil.getNome())) {
			criteria.add(Restrictions.like("nome", perfil.getNome(), MatchMode.ANYWHERE));
		}
		if (Validador.isEnumValido(perfil.getSituacao())) {
			criteria.add(Restrictions.eq("situacao", perfil.getSituacao()));
		}

		return criteria.list();
	}
}
