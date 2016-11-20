package br.com.siga.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.Component;

import br.com.siga.dominio.Fornecedor;

@FacesConverter(value = "fornecedorConverter")
public class FornecedorConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;

	EntityManager entityManager = (EntityManager) Component.getInstance("entityManager");

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.equals("")) {
			Session session = (Session) entityManager.getDelegate();
			Criteria criteria = session.createCriteria(Fornecedor.class);

			criteria.add(Restrictions.eq("descricao", value));

			Fornecedor fornecedor = (Fornecedor) criteria.uniqueResult();

			return fornecedor;
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Fornecedor) value).getDescricao());
		}
	}
}