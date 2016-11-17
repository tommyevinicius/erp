package br.com.siga.acao;

import java.util.ArrayList;
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

import br.com.siga.dominio.Cliente;
import br.com.siga.dominio.Fornecedor;
import br.com.siga.dominio.Lancamento;
import br.com.siga.dominio.LancamentoProduto;
import br.com.siga.dominio.Usuario;
import br.com.siga.negocio.ClienteNegocio;
import br.com.siga.negocio.FornecedorNegocio;
import br.com.siga.negocio.LancamentoNegocio;
import br.com.siga.utils.Enumerados.TipoLancamento;
import br.com.siga.utils.Navegacao;
import br.com.templates.utils.BaseAcao;

@Name("lancamentoAcao")
@Scope(ScopeType.CONVERSATION)
public class LancamentoAcao extends BaseAcao {

	@In(create = true, required = false)
	@Out(required = false)
	private Lancamento lancamento;

	@In(create = true, required = false)
	private LancamentoNegocio lancamentoNegocio;

	@In(create = true, required = false)
	@Out(required = false)
	private LancamentoProduto lancamentoProdutoSelecionado;

	@In(create = true, required = false)
	@Out(required = false)
	private Lancamento lancamentoSelecionado;

	@Out(required = false)
	private List<Lancamento> listaLancamento;

	@Out(required = false)
	private List<Fornecedor> fornecedores;

	@In(create = true, required = false)
	private FornecedorNegocio fornecedorNegocio;

	@In(create = true, required = false)
	private ClienteNegocio clienteNegocio;

	@Out(required = false)
	private List<Cliente> clientes;

	@Out(required = false)
	private TipoLancamento[] comboTipoLancamento = TipoLancamento.values();

	@Create
	public void init() {
		fornecedores = fornecedorNegocio.listarAtivos();
		lancamento.setCliente(new Cliente());
		lancamento.setFornecedor(new Fornecedor());
		lancamento.setUsuario(new Usuario());
		listar();
	}

	@Factory(value = "listaLancamento")
	public void listar() {
		listaLancamento = lancamentoNegocio.listar();
	}

	@Factory(value = "clientes")
	public void popularClientes() {
		clientes = clienteNegocio.listarAtivos();
	}

	@End
	public String lancar() {
		try {
			lancamento.setUsuario(usuarioLogado);
			lancamentoNegocio.incluirLancamento(lancamento);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.LANCAMENTOMANTER;
	}

	@End
	public String excluir(Lancamento lancamento) {
		try {
			// TODO - Modificar a quantidade do produto
			lancamentoNegocio.alterar(lancamento);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.excluir.erro");
		}
		return Navegacao.LANCAMENTOMANTER;
	}

	@End
	public String pesquisar() {
		try {
			listaLancamento = lancamentoNegocio.pesquisar(lancamento);
		} catch (Exception e) {
			super.addMsg(Severity.ERROR, "pesquisa.erro");
		}
		limpar();
		return Navegacao.LANCAMENTOMANTER;
	}

	@End
	public String limpar() {
		lancamento = new Lancamento();
		lancamento.setCliente(new Cliente());
		lancamento.setFornecedor(new Fornecedor());
		lancamento.setUsuario(new Usuario());
		lancamentoSelecionado = new Lancamento();
		return Navegacao.LANCAMENTOMANTER;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirLancar() {
		lancamento = new Lancamento();
		return Navegacao.LANCAMENTOINCLUIR;
	}

	@End
	public String cancelar() {
		limpar();
		return Navegacao.LANCAMENTOMANTER;
	}

	public String exibirDetalhe(Lancamento lancamento) {

		return Navegacao.LANCAMENTODETALHAR;
	}

	public void adicionarProduto() {
		// TODO - Analisar
		if (lancamento.getListaProdutos() == null) {
			lancamento.setListaProdutos(new ArrayList<LancamentoProduto>());
		}

		lancamento.getListaProdutos().add(lancamentoProdutoSelecionado);
	}

	public Lancamento getLancamentoSelecionado() {
		return lancamentoSelecionado;
	}

	public void setLancamentoSelecionado(Lancamento lancamentoSelecionado) {
		this.lancamentoSelecionado = lancamentoSelecionado;
	}

	public List<Lancamento> getListaLancamento() {
		return listaLancamento;
	}

	public void setListaLancamento(List<Lancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public TipoLancamento[] getComboTipoLancamento() {
		return comboTipoLancamento;
	}
}
