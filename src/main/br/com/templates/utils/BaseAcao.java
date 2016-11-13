package br.com.templates.utils;

import java.util.Iterator;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.security.Identity;

import br.com.siga.dominio.Usuario;

@AutoCreate
@Name("baseAcao")
@Scope(ScopeType.CONVERSATION)
public class BaseAcao {

	@In(create = true, required = false)
	protected EntityManager entityManager;

	@In(create = true, required = false)
	protected FacesMessages facesMessages;

	@In(create = true, required = false)
	protected Identity identity;

	@In(value = "#{facesContext}", required = false)
	protected FacesContext facesContext;

	@In(value = "usuarioLogado", required = false)
	protected Usuario usuarioLogado;

	@In(value = "#{facesContext.externalContext}", required = false)
	protected ExternalContext extCtx;

	protected final String PAGINA_ATUAL = "paginaAtual";

	protected String paginaAtual;

	private Integer tamanhoObjeto;

	private String voltarPara;

	public void evict(Object entity) {
		org.hibernate.Session session = (Session) entityManager.getDelegate();
		session.evict(entity);
	}

	protected boolean isUsuarioLogadoAdmin() {
		if (usuarioLogado != null && usuarioLogado.getNome().equalsIgnoreCase("root")) {
			return true;
		}
		return false;
	}

	public Integer getTamanhoObjeto() {
		return tamanhoObjeto;
	}

	public String voltar() {
		if (voltarPara != null) {
			return voltarPara;
		}
		return "";
	}

	/**
	 * Locate an UIComponent from its root component.
	 */
	@SuppressWarnings("rawtypes")
	public static UIComponent findComponent(UIComponent base, String id) {
		if (id.equals(base.getId()))
			return base;

		UIComponent children = null;
		UIComponent result = null;
		Iterator childrens = base.getFacetsAndChildren();
		while (childrens.hasNext() && (result == null)) {
			children = (UIComponent) childrens.next();
			if (id.equals(children.getId())) {
				result = children;
				break;
			}
			result = findComponent(children, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	protected void addMsg(Severity severity, String msg) {
		StatusMessages.instance().addFromResourceBundle(severity, msg);
	}

	public void redirecionarPagina(String url) {
		FacesContext context = FacesContext.getCurrentInstance();
		NavigationHandler navHandler = context.getApplication().getNavigationHandler();
		navHandler.handleNavigation(context, null, url);
	}

	/**
	 * Metodo responsavel por atualizar a pagina atual
	 */
	protected void refreshPage() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String refreshpage = fc.getViewRoot().getViewId();
		ViewHandler ViewH = fc.getApplication().getViewHandler();
		UIViewRoot UIV = ViewH.createView(fc, refreshpage);
		UIV.setViewId(refreshpage);
		fc.setViewRoot(UIV);
	}

}
