package br.com.siga.acao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
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
import br.com.siga.negocio.ClienteNegocio;
import br.com.siga.negocio.FornecedorNegocio;
import br.com.siga.negocio.LancamentoNegocio;
import br.com.siga.negocio.ProdutoNegocio;
import br.com.siga.utils.Enumerados.Situacao;
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

	@Out(required = false)
	private TipoLancamento[] comboTipoLancamento;

	@In(create = true, required = false)
	@Out(required = false)
	private Produto produtoSelecionado;

	private List<LancamentoProduto> listaLancamentoProduto;
	private List<Produto> listaProdutos;
	private List<Fornecedor> listaFornecedores;
	private List<Cliente> listaClientes;

	@Create
	public void init() {
		listar();
	}

	@Factory(value = "listaLancamento")
	public void listar() {
		listaLancamento = lancamentoNegocio.pesquisar(lancamento);
	}

	public String lancar() {
		try {
			corrigirObjetos();

			if (validarIncluirLancamento()) {
				lancamento.setUsuario(usuarioLogado);
				lancamento.setData(new Date());
				lancamentoNegocio.incluir(lancamento);
			} else {
				return Navegacao.LANCAMENTOINCLUIR;
			}

		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}

		limpar();
		return Navegacao.LANCAMENTOMANTER;
	}

	// public String excluir(Lancamento lancamento) {
	// try {
	// // TODO - Modificar a quantidade do produto
	// lancamentoNegocio.alterar(lancamento);
	// } catch (Exception e) {
	// super.addMsg(Severity.FATAL, "registro.excluir.erro");
	// }
	// return Navegacao.LANCAMENTOMANTER;
	// }

	public void limpar() {
		lancamento = new Lancamento();
		lancamentoSelecionado = new Lancamento();
		lancamentoProduto = new LancamentoProduto();
		listaLancamentoProduto = new ArrayList<LancamentoProduto>();
	}

	public void limparIncluir() {
		for (LancamentoProduto lp : listaLancamentoProduto) {
			alterarExcluirProdutoLista(lp);
		}

		listaLancamentoProduto = new ArrayList<LancamentoProduto>();
		lancamento.setCliente(new Cliente(""));
		lancamento.setFornecedor(new Fornecedor(""));
	}

	public void limparLancamentoProduto() {
		lancamentoProduto = new LancamentoProduto();
		produtoSelecionado = new Produto("");
	}

	public String cancelar() {
		for (LancamentoProduto lp : listaLancamentoProduto) {
			alterarExcluirProdutoLista(lp);
		}

		limpar();
		return Navegacao.LANCAMENTOMANTER;
	}

	public String exibirLancar() {
		limpar();
		lancamento.setTipoLancamento(TipoLancamento.ENTRADA);

		return Navegacao.LANCAMENTOINCLUIR;
	}

	public String exibirDetalhe(Lancamento lancamento) {
		limpar();
		this.lancamento = lancamento;

		return Navegacao.LANCAMENTODETALHAR;
	}

	/**
	 * Adicionar na lista de lançamento os produtos
	 */
	public void adicionarProduto() {
		if (listaLancamentoProduto == null) {
			listaLancamentoProduto = new ArrayList<LancamentoProduto>();
		}
		if (!camposProdutoValidos() || !isValidoAlterarEstoqueAdicao()) {
			return;
		}

		// SET
		lancamentoProduto.setLancamento(lancamento);
		lancamentoProduto.setProduto(produtoSelecionado);
		listaLancamentoProduto.add(lancamentoProduto);
		limparLancamentoProduto();

		Collections.sort(listaLancamentoProduto);
	}

	/**
	 * Remover da lista de lançamento o produto selecionado.
	 */
	public void removerProduto(LancamentoProduto lancamentoProduto) {
		alterarExcluirProdutoLista(lancamentoProduto);
		listaLancamentoProduto.remove(lancamentoProduto);

		super.addMsg(Severity.WARN, "Estoque (" + lancamentoProduto.getProduto().getQuantidade() + ")");
		Collections.sort(listaLancamentoProduto);
	}

	/**
	 * Métódo que retorna para o AutoComplete clientes com o nome digitado.
	 */
	public List<Cliente> autoCompleteCliente(String nome) {
		if (lancamento.getCliente() == null) {
			lancamento.setCliente(new Cliente());
		}

		lancamento.getCliente().setNome(nome);

		return clienteNegocio.pesquisar(lancamento.getCliente(), Situacao.ATIVO);
	}

	/**
	 * Métódo que retorna para o AutoComplete fornecedores com o nome digitado.
	 */
	public List<Fornecedor> autoCompleteFornecedor(String nome) {
		if (lancamento.getFornecedor() == null) {
			lancamento.setFornecedor(new Fornecedor());
		}

		lancamento.getFornecedor().setDescricao(nome);

		return fornecedorNegocio.pesquisar(lancamento.getFornecedor(), Situacao.ATIVO);
	}

	/**
	 * Métódo que retorna para o AutoComplete produtos com o nome digitado.
	 */
	public List<Produto> autoCompleteProduto(String valor) {
		Produto produto = new Produto();
		produto.setDescricao(valor);

		return produtoNegocio.pesquisar(produto, Situacao.ATIVO);
	}

	/**
	 * Setar os produtos lançados no LançamentoProdutos.
	 */
	private void corrigirObjetos() {
		if (lancamento.getListaProdutos() == null) {
			lancamento.setListaProdutos(listaLancamentoProduto);
		}
		if (!Validador.isObjetoValido(lancamento.getCliente().getIdCliente())) {
			lancamento.setCliente(null);
		}
		if (!Validador.isObjetoValido(lancamento.getFornecedor().getIdFornecedor())) {
			lancamento.setFornecedor(null);
		}
	}

	/**
	 * Irá alterar o estoque na adição dos produtos (ENTRADA e SAÍDA).
	 */
	private boolean isValidoAlterarEstoqueAdicao() {
		if (TipoLancamento.SAIDA.equals(lancamento.getTipoLancamento())) {
			if (produtoSelecionado.getQuantidade() - lancamentoProduto.getQuantidade() < 0) {
				super.addMsg(Severity.WARN, "produto.negativo");
				super.addMsg(Severity.WARN, "Estoque (" + produtoSelecionado.getQuantidade() + ")");
				return false;
			}
		}
		alterarAdicaoProdutoLista();
		return true;
	}

	/**
	 * Usado para o método preAlterarEstoqueAdicao().
	 */
	private void alterarAdicaoProdutoLista() {
		if (TipoLancamento.ENTRADA.equals(lancamento.getTipoLancamento())) {
			produtoSelecionado.setQuantidade(lancamentoProduto.getQuantidade() + produtoSelecionado.getQuantidade());
		} else {
			produtoSelecionado.setQuantidade(produtoSelecionado.getQuantidade() - lancamentoProduto.getQuantidade());
		}

		super.addMsg(Severity.WARN, "Estoque (" + produtoSelecionado.getQuantidade() + ")");
	}

	/**
	 * Método responsável por voltar o estoque sem a alteração deste lancamento produto.
	 */
	private void alterarExcluirProdutoLista(LancamentoProduto lancamentoProdutoSelecionado) {
		if (TipoLancamento.ENTRADA.equals(lancamento.getTipoLancamento())) {
			lancamentoProdutoSelecionado.getProduto().setQuantidade(lancamentoProdutoSelecionado.getProduto().getQuantidade() - lancamentoProdutoSelecionado.getQuantidade());
		} else {
			lancamentoProdutoSelecionado.getProduto().setQuantidade(lancamentoProdutoSelecionado.getProduto().getQuantidade() + lancamentoProdutoSelecionado.getQuantidade());
		}
	}

	/**
	 * Validar se os campos obrigatórios para adicionar o produto na lista estão corretos.
	 */
	public boolean camposProdutoValidos() {
		if (!Validador.isObjetoValido(produtoSelecionado)) {
			super.addMsg(Severity.WARN, "produto.invalido");
			return false;

		} else if (!Validador.isNumericoValido(lancamentoProduto.getValor())) {
			super.addMsg(Severity.WARN, "produto.valor.obrigatorio");
			return false;

		} else if (!Validador.isNumericoValido(lancamentoProduto.getQuantidade())) {
			super.addMsg(Severity.WARN, "produto.quantidade.obrigatorio");
			return false;
		}

		return true;
	}

	/**
	 * Validar se os campos obrigatórios para incluir o lançamento estão corretos.
	 */
	public boolean validarIncluirLancamento() {
		if (!Validador.isCollectionValida(lancamento.getListaProdutos())) {
			super.addMsg(Severity.WARN, "lista.produto");
			return false;

		} else if (TipoLancamento.ENTRADA.equals(lancamento.getTipoLancamento()) && !Validador.isObjetoValido(lancamento.getFornecedor())) {
			super.addMsg(Severity.WARN, "fornecedor.obrigatorio");
			return false;

		} else if (TipoLancamento.SAIDA.equals(lancamento.getTipoLancamento()) && !Validador.isObjetoValido(lancamento.getCliente())) {
			super.addMsg(Severity.WARN, "cliente.obrigatorio");
			return false;
		}

		return true;
	}

	// GETTER AND SETTER

	public TipoLancamento[] getComboTipoLancamento() {
		return TipoLancamento.values();
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

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}
}
