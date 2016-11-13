package org.domain.siga.session;

import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import br.com.siga.dominio.Usuario;

@Name("sessionHelper")
@Scope(ScopeType.SESSION)
public class SessionHelper {

	@In(required = true)
	private Identity identity;

	@In(create = false, required = false, value = "usuarioLogado")
	private Usuario usuarioLogado;

	@Logger
	private Log log;

	@Out(required = false)
	private boolean esqueciMinhaSenha;

	@In(value = "#{facesContext}", required = false)
	protected FacesContext facesContext;

	@Observer("org.jboss.seam.security.loginSuccessful")
	public void atribuirFuncionarioLogado() throws Exception {
		setEsqueciMinhaSenha(false);
		// listarRecursos(usuarioLogado);
	}

	@Observer("org.jboss.seam.security.loginFailed")
	public void exibirEsqueciMinhaSenha() {
		setEsqueciMinhaSenha(true);
	}

	public boolean isEsqueciMinhaSenha() {
		return esqueciMinhaSenha;
	}

	public void setEsqueciMinhaSenha(boolean esqueciMinhaSenha) {
		this.esqueciMinhaSenha = esqueciMinhaSenha;
	}

	public String getNomeUsuario() {
		if (usuarioLogado != null) {
			return usuarioLogado.getNome();
		}
		return null;
	}
}
