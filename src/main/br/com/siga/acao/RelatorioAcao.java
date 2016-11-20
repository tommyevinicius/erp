package br.com.siga.acao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Lancamento;
import br.com.siga.dominio.Relatorio;
import br.com.siga.dominio.Usuario;
import br.com.siga.negocio.LancamentoNegocio;
import br.com.siga.negocio.UsuarioNegocio;
import br.com.siga.utils.Enumerados.TipoLancamento;
import br.com.siga.utils.Navegacao;
import br.com.templates.utils.BaseAcao;

@Name("relatorioAcao")
@Scope(ScopeType.CONVERSATION)
public class RelatorioAcao extends BaseAcao {

	@In(create = true, required = false)
	@Out(required = false)
	private Lancamento lancamento;

	@In(create = true, required = false)
	private LancamentoNegocio lancamentoNegocio;

	@Out(required = false)
	List<Lancamento> listaLancamentos;
	
	@Out(required = false)
	private TipoLancamento[] comboTipoLancamento;
	
	@Out(required = false)
	private List<Usuario> usuarios;
	
	@In(create = true, required = false)
	private UsuarioNegocio usuarioNegocio;

	@Create
	public void init() {
		listarUsuarios();
		listar();
	}

	@Factory(value = "listaLancamentos")
	public void listar() {
		listaLancamentos = lancamentoNegocio.pesquisar(lancamento);
	}
	
	@Factory(value = "usuarios")
	public void listarUsuarios() {
		usuarios = usuarioNegocio.listarAtivos();
	}

	public void limpar() {
		lancamento = new Lancamento();
		lancamento.setUsuario(new Usuario());
	}

	public String exibir() {
		listar();
		return null;
		// return Navegacao.CLIENTEINCLUIR;
	}

	public void getRelatorio() {
		Relatorio<Lancamento> report = new Relatorio<Lancamento>();
		if (listaLancamentos.size() > 0) {
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não há registros!"));
		}
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public List<Lancamento> getListaLancamentos() {
		return listaLancamentos;
	}

	public void setListaLancamentos(List<Lancamento> listaLancamentos) {
		this.listaLancamentos = listaLancamentos;
	}

	public TipoLancamento[] getComboTipoLancamento() {
		return TipoLancamento.values();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
