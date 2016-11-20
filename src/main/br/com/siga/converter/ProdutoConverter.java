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

import br.com.siga.dominio.Produto;

@FacesConverter(value = "produtoConverter")
public class ProdutoConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;

	EntityManager entityManager = (EntityManager) Component.getInstance("entityManager");

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.equals("")) {
			Session session = (Session) entityManager.getDelegate();
			Criteria criteria = session.createCriteria(Produto.class);

			criteria.add(Restrictions.eq("descricao", value));

			Produto produto = (Produto) criteria.uniqueResult();

			return produto;
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Produto) value).getDescricao());
		}
	}
}