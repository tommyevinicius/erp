package br.com.siga.acao;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.FlushModeType;
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
	private Situacao[] comboSituacoes = Situacao.values();

	@In(create = true, required = false)
	private PerfilNegocio perfilNegocio;

	@Create
	public void init() {
		listar();
		popularPerfils();
	}

	@Factory(value = "listaUsuarios")
	@End
	public void listar() {
		listaUsuarios = usuarioNegocio.pesquisar(usuario);
	}

	@Factory(value = "perfils")
	public void popularPerfils() {
		perfils = perfilNegocio.listarAtivos();
	}

	@End
	public String incluir() {
		try {
			usuarioNegocio.incluir(usuario);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.USUARIOMANTER;
	}

	@End
	public String alterar() {
		try {
			usuarioNegocio.alterar(usuarioSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.alterar.erro");
		}
		return Navegacao.USUARIOMANTER;
	}
	
	@End
	public String excluir(Usuario usuario) {
		try {
			usuario.setSituacao(Situacao.INATIVO);
			usuarioNegocio.alterar(usuario);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.excluir.erro");
		}
		return Navegacao.USUARIOMANTER;
	}

	@End
	public String limpar() {
		usuario = new Usuario();
		usuarioSelecionado = new Usuario();
		return Navegacao.USUARIOMANTER;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirIncluir() {
		usuario = new Usuario();
		return Navegacao.USUARIOINCLUIR;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirAlterar(Usuario usuario) {
		this.usuarioSelecionado = usuario;
		return Navegacao.USUARIOALTERAR;
	}

	@End
	public String cancelar() {
		try {
			entityManager.refresh(usuarioSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.ERROR, "operacao.cancelada");
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
		return comboSituacoes;
	}

	public List<Perfil> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}
}
