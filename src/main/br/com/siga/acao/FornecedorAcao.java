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

import br.com.siga.dominio.Fornecedor;
import br.com.siga.negocio.FornecedorNegocio;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Navegacao;
import br.com.templates.utils.BaseAcao;

@Name("fornecedorAcao")
@Scope(ScopeType.CONVERSATION)
public class FornecedorAcao extends BaseAcao {

	@In(create = true, required = false)
	@Out(required = false)
	private Fornecedor fornecedor;

	@In(create = true, required = false)
	private FornecedorNegocio fornecedorNegocio;

	@In(create = true, required = false)
	@Out(required = false)
	private Fornecedor fornecedorSelecionado;

	@Out(required = false)
	private List<Fornecedor> listaFornecedor;

	@Out(required = false)
	private Situacao[] comboSituacoes = Situacao.values();

	@Create
	public void init() {
		listar();
	}

	@Factory(value = "listaFornecedor")
	public void listar() {
		listaFornecedor = fornecedorNegocio.pesquisar(fornecedor);
	}

	@End
	public String incluir() {
		try {
			fornecedorNegocio.incluir(fornecedor);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.FORNECEDORMANTER;
	}

	@End
	public String alterar() {
		try {
			fornecedorNegocio.alterar(fornecedorSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.alterar.erro");
		}
		return Navegacao.FORNECEDORMANTER;
	}

	@End
	public String excluir(Fornecedor fornecedor) {
		try {
			fornecedor.setSituacao(Situacao.INATIVO);
			fornecedorNegocio.alterar(fornecedor);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.excluir.erro");
		}
		return Navegacao.FORNECEDORMANTER;
	}

	@End
	public String limpar() {
		fornecedor = new Fornecedor();
		fornecedorSelecionado = new Fornecedor();
		return Navegacao.FORNECEDORMANTER;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirIncluir() {
		fornecedor = new Fornecedor();
		return Navegacao.FORNECEDORINCLUIR;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirAlterar(Fornecedor fornecedor) {
		this.fornecedorSelecionado = fornecedor;
		return Navegacao.FORNECEDORALTERAR;
	}

	@End
	public String cancelar() {
		try {
			entityManager.refresh(fornecedorSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.ERROR, "operacao.cancelada");
		}
		limpar();
		return Navegacao.FORNECEDORMANTER;
	}

	public Fornecedor getFornecedorSelecionado() {
		return fornecedorSelecionado;
	}

	public void setFornecedorSelecionado(Fornecedor fornecedorSelecionado) {
		this.fornecedorSelecionado = fornecedorSelecionado;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Situacao[] getComboSituacoes() {
		return comboSituacoes;
	}

	public void setComboSituacoes(Situacao[] comboSituacoes) {
		this.comboSituacoes = comboSituacoes;
	}
}
