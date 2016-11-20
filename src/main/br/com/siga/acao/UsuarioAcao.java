package br.com.siga.acao;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;

import br.com.siga.dominio.Perfil;
import br.com.siga.dominio.Usuario;
import br.com.siga.negocio.PerfilNegocio;
import br.com.siga.negocio.UsuarioNegocio;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Navegacao;
import br.com.templates.utils.BaseAcao;

@Name("usuarioAcao")
@Scope(ScopeType.CONVERSATION)
public class UsuarioAcao extends BaseAcao {

	@In(create = true, required = false)
	@Out(required = false)
	private Usuario usuario;

	@In(create = true, required = false)
	private UsuarioNegocio usuarioNegocio;

	@In(create = true, required = false)
	@Out(required = false)
	private Usuario usuarioSelecionado;

	@Out(required = false)
	private List<Usuario> listaUsuarios;

	@Out(required = false)
	private List<Perfil> perfils;

	@Out(required = false)
	private Situacao[] comboSituacoes;

	@In(create = true, required = false)
	private PerfilNegocio perfilNegocio;

	@Create
	public void init() {
		usuario.setSituacao(Situacao.ATIVO);
		listar();
		popularPerfils();
	}

	@Factory(value = "listaUsuarios")
	public void listar() {
		listaUsuarios = usuarioNegocio.pesquisar(usuario);
	}

	@Factory(value = "perfils")
	public void popularPerfils() {
		perfils = perfilNegocio.listarAtivos();
	}

	public String incluir() {
		try {
			usuarioNegocio.incluir(usuario);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.USUARIOMANTER;
	}

	public String alterar() {
		try {
			usuarioNegocio.alterarUsuario(usuarioSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.alterar.erro");
		}
		return Navegacao.USUARIOMANTER;
	}

	public String excluir(Usuario usuario) {
		try {
			usuario.setSituacao(Situacao.INATIVO);
			usuarioNegocio.alterar(usuario);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.excluir.erro");
		}
		return Navegacao.USUARIOMANTER;
	}

	public String limpar() {
		usuario = new Usuario();
		usuarioSelecionado = new Usuario();
		return Navegacao.USUARIOMANTER;
	}

	public String exibirIncluir() {
		usuario = new Usuario();
		return Navegacao.USUARIOINCLUIR;
	}

	public String exibirAlterar(Usuario usuario) {
		this.usuarioSelecionado = usuario;
		return Navegacao.USUARIOALTERAR;
	}

	public String cancelar() {
		try {
			usuarioNegocio.recarregar(usuarioSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "operacao.cancelada");
		}
		limpar();
		return Navegacao.USUARIOMANTER;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public Situacao[] getComboSituacoes() {
		return Situacao.values();
	}

	public List<Perfil> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
