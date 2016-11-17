package br.com.siga.acao;

import java.util.ArrayList;
import java.util.Collections;
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
import br.com.siga.dominio.Produto;
import br.com.siga.dominio.Usuario;
import br.com.siga.negocio.ClienteNegocio;
import br.com.siga.negocio.FornecedorNegocio;
import br.com.siga.negocio.LancamentoNegocio;
import br.com.siga.negocio.ProdutoNegocio;
import br.com.siga.utils.Enumerados.TipoLancamento;
import br.com.siga.utils.Navegacao;
import br.com.siga.utils.Validador;
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
	private LancamentoProduto lancamentoProduto;

	@In(create = true, required = false)
	@Out(required = false)
	private Lancamento lancamentoSelecionado;

	@In(create = true, required = false)
	private ClienteNegocio clienteNegocio;

	@In(create = true, required = false)
	private FornecedorNegocio fornecedorNegocio;

	@In(create = true, required = false)
	private ProdutoNegocio produtoNegocio;

	@Out(required = false)
	private List<Lancamento> listaLancamento;

	private List<LancamentoProduto> listaLancamentoProduto;

	@Out(required = false)
	private TipoLancamento[] comboTipoLancamento = TipoLancamento.values();

	@Create
	public void init() {
		lancamento.setCliente(new Cliente());
		lancamento.setFornecedor(new Fornecedor());
		lancamento.setUsuario(new Usuario());
		listar();
	}

	@Factory(value = "listaLancamento")
	public void listar() {
		listaLancamento = lancamentoNegocio.listar();
	}

	@End
	public String lancar() {
		try {
			popularObjetos();
			lancamento.setUsuario(usuarioLogado);
			if (validarIncluirLancamento()) {
				lancamentoNegocio.incluirLancamento(lancamento);
			}
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.LANCAMENTOMANTER;
	}

	private void popularObjetos() {
		Fornecedor fornecedor = lancamento.getFornecedor();
		fornecedor = fornecedorNegocio.localizar(fornecedor);
		
		Cliente cliente = lancamento.getCliente();
		cliente = clienteNegocio.localizar(cliente);
		
		lancamento.setFornecedor(fornecedor);
		lancamento.setCliente(cliente);
		
		if (lancamento.getListaProdutos() == null) {
			lancamento.setListaProdutos(listaLancamentoProduto);
		}
		
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
	public void limpar() {
		lancamento = new Lancamento();
		lancamento.setCliente(new Cliente());
		lancamento.setFornecedor(new Fornecedor());
		lancamento.setUsuario(new Usuario());
		lancamentoSelecionado = new Lancamento();
		lancamentoProduto = new LancamentoProduto();
		lancamentoProduto.setProduto(new Produto());
		listaLancamentoProduto = new ArrayList<LancamentoProduto>();
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirLancar() {
		limpar();
		lancamento.setTipoLancamento(TipoLancamento.ENTRADA);
		return Navegacao.LANCAMENTOINCLUIR;
	}

	@End
	public String cancelar() {
		limpar();
		return Navegacao.LANCAMENTOMANTER;
	}

	public String exibirDetalhe(Lancamento lancamento) {
		limpar();
		
		this.lancamento = lancamento;
		
		return Navegacao.LANCAMENTODETALHAR;
	}

	public void adicionarProduto() {
		if (listaLancamentoProduto == null) {
			listaLancamentoProduto = new ArrayList<LancamentoProduto>();
		}

		Produto produto = lancamentoProduto.getProduto();
		produto = produtoNegocio.localizar(produto);

		if (!validarLancamentoProduto(lancamentoProduto)) {
			addMsg(Severity.WARN, "produto.adicionado.erro");
			return;
		}

		lancamentoProduto.setProduto(produto);
		lancamentoProduto.setLancamento(lancamento);
		
		listaLancamentoProduto.add(lancamentoProduto);
		lancamentoProduto = new LancamentoProduto();
		lancamentoProduto.setProduto(new Produto());

		Collections.sort(listaLancamentoProduto);
	}

	public void excluirProduto(LancamentoProduto produto) {
		listaLancamentoProduto.remove(produto);

		Collections.sort(listaLancamentoProduto);
	}

	/**
	 * Métódo que retorna para o AutoComplete clientes com o nome digitado.
	 */
	public List<Cliente> autoCompleteCliente(String nome) {
		lancamento.getCliente().setNome(nome);

		return clienteNegocio.pesquisar(lancamento.getCliente());
	}
	
	/**
	 * Métódo que retorna para o AutoComplete fornecedores com o nome digitado.
	 */
	public List<Fornecedor> autoCompleteFornecedor(String nome) {
		lancamento.getFornecedor().setDescricao(nome);

		return fornecedorNegocio.pesquisar(lancamento.getFornecedor());
	}

	/**
	 * Métódo que retorna para o AutoComplete produtos com o nome digitado.
	 */
	public List<Produto> autoCompleteProduto(String nome) {
		if (Validador.isCollectionValida(lancamento.getListaProdutos())) {
			lancamento.setListaProdutos(new ArrayList<LancamentoProduto>());
		}
		lancamentoProduto.getProduto().setDescricao(nome);

		return produtoNegocio.pesquisar(lancamentoProduto.getProduto());
	}

	public boolean validarLancamentoProduto(LancamentoProduto lancamentoProduto) {
		if (!Validador.isStringValida(lancamentoProduto.getProduto().getDescricao())) {
			return false;
		}
		if (!Validador.isNumericoValido(lancamentoProduto.getValor())) {
			return false;
		}
		if (!Validador.isNumericoValido(lancamentoProduto.getQuantidade())) {
			return false;
		}

		return true;
	}
	
	public boolean validarIncluirLancamento() {
		if (!Validador.isCollectionValida(lancamento.getListaProdutos())) {
			return false;
		}
		
		if (TipoLancamento.ENTRADA.equals(lancamento.getTipoLancamento())){
			if (!Validador.isObjetoValido(lancamento.getFornecedor())) {
				return false;
			}
		} else {
			if (!Validador.isObjetoValido(lancamento.getCliente())) {
				return false;
			}
		}

		return true;
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

	public TipoLancamento[] getComboTipoLancamento() {
		return comboTipoLancamento;
	}

	public List<LancamentoProduto> getListaLancamentoProduto() {
		return listaLancamentoProduto;
	}

	public void setListaLancamentoProduto(List<LancamentoProduto> listaLancamentoProduto) {
		this.listaLancamentoProduto = listaLancamentoProduto;
	}

	public LancamentoProduto getLancamentoProduto() {
		return lancamentoProduto;
	}

	public void setLancamentoProduto(LancamentoProduto lancamentoProduto) {
		this.lancamentoProduto = lancamentoProduto;
	}
}
