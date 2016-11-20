package br.com.siga.acao;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;

import br.com.siga.dominio.Produto;
import br.com.siga.negocio.ProdutoNegocio;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Navegacao;
import br.com.templates.utils.BaseAcao;

@Name("produtoAcao")
@Scope(ScopeType.CONVERSATION)
public class ProdutoAcao extends BaseAcao {

	@In(create = true, required = false)
	@Out(required = false)
	private Produto produto;

	@In(create = true, required = false)
	private ProdutoNegocio produtoNegocio;

	@In(create = true, required = false)
	@Out(required = false)
	private Produto produtoSelecionado;

	@Out(required = false)
	private List<Produto> listaProduto;

	@Out(required = false)
	private Situacao[] comboSituacoes;

	@Create
	public void init() {
		produto.setSituacao(Situacao.ATIVO);
		listar();
	}

	@Factory(value = "listaProduto")
	public void listar() {
		listaProduto = produtoNegocio.pesquisar(produto);
	}

	public String incluir() {
		try {
			produtoNegocio.incluir(produto);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.PRODUTOMANTER;
	}

	public String alterar() {
		try {
			produtoNegocio.alterar(produtoSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.alterar.erro");
		}
		return Navegacao.PRODUTOMANTER;
	}

	public String excluir(Produto produto) {
		try {
			produto.setSituacao(Situacao.INATIVO);
			produtoNegocio.alterar(produto);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.excluir.erro");
		}
		return Navegacao.PRODUTOMANTER;
	}

	@End
	public String limpar() {
		produto = new Produto();
		produtoSelecionado = new Produto();
		return Navegacao.PRODUTOMANTER;
	}

	public String exibirIncluir() {
		produto = new Produto();
		return Navegacao.PRODUTOINCLUIR;
	}

	public String exibirAlterar(Produto produto) {
		this.produtoSelecionado = produto;
		return Navegacao.PRODUTOALTERAR;
	}

	public String cancelar() {
		try {
			entityManager.refresh(produtoSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "operacao.cancelada");
		}
		limpar();
		return Navegacao.PRODUTOMANTER;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public Situacao[] getComboSituacoes() {
		return Situacao.values();
	}
}
