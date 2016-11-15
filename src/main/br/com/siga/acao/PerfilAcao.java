package br.com.siga.acao;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;

import br.com.siga.dominio.Perfil;
import br.com.siga.negocio.PerfilNegocio;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Navegacao;
import br.com.templates.utils.BaseAcao;

@Name("perfilAcao")
@Scope(ScopeType.CONVERSATION)
public class PerfilAcao extends BaseAcao {

	@In(create = true, required = false)
	@Out(required = false)
	private Perfil perfil;

	@In(create = true, required = false)
	private PerfilNegocio perfilNegocio;

	@In(create = true, required = false)
	@Out(required = false)
	private Perfil perfilSelecionado;

	@Out(required = false)
	private List<Perfil> listaPerfils;

	@Out(required = false)
	private Situacao[] comboSituacoes = Situacao.values();

	@Create
	public void init() {
		listar();
	}

	public void listar() {
		listaPerfils = perfilNegocio.pesquisar(perfil);
	}

	@End
	public String incluir() {
		try {
			perfilNegocio.incluir(perfil);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.PERFILMANTER;
	}

	@End
	public String alterar() {
		try {
			perfilNegocio.alterar(perfilSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.alterar.erro");
		}
		return Navegacao.PERFILMANTER;
	}

	@End
	public String excluir(Perfil perfil) {
		try {
			perfil.setSituacao(Situacao.INATIVO);
			perfilNegocio.alterar(perfil);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.excluir.erro");
		}
		return Navegacao.PERFILMANTER;
	}

	@End
	public String limpar() {
		perfil = new Perfil();
		perfilSelecionado = new Perfil();
		return Navegacao.PERFILMANTER;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirIncluir() {
		perfil = new Perfil();
		return Navegacao.PERFILINCLUIR;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirAlterar(Perfil perfil) {
		this.perfilSelecionado = perfil;
		return Navegacao.PERFILALTERAR;
	}

	@End
	public String cancelar() {
		try {
			entityManager.refresh(perfilSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "operacao.cancelada");
		}
		limpar();
		return Navegacao.PERFILMANTER;
	}

	public List<Perfil> getListaPerfils() {
		return listaPerfils;
	}

	public void setListaPerfils(List<Perfil> listaPerfils) {
		this.listaPerfils = listaPerfils;
	}

	public Perfil getPerfilSelecionado() {
		return perfilSelecionado;
	}

	public Situacao[] getComboSituacoes() {
		return comboSituacoes;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}
}
