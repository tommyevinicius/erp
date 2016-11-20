package br.com.siga.negocio;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Lancamento;
import br.com.siga.dominio.Relatorio;
import br.com.templates.utils.NegocioBase;

@SuppressWarnings("rawtypes")
@AutoCreate
@Name("relatorioNegocio")
@Scope(ScopeType.CONVERSATION)
public class RelatorioNegocio extends NegocioBase<Relatorio, Long> {

	@SuppressWarnings("unchecked")
	public List<Lancamento> listarLancamentos () {
		Criteria criteria = getSession().createCriteria(Lancamento.class);
		
		criteria.addOrder(Order.asc("idLancamento"));
		
		return criteria.list();
	}
}
