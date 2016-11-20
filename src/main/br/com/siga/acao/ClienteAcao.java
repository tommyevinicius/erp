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
import org.primefaces.model.LazyDataModel;

import br.com.siga.dominio.Cliente;
import br.com.siga.negocio.ClienteNegocio;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Enumerados.TipoPessoa;
import br.com.siga.utils.Navegacao;
import br.com.templates.utils.BaseAcao;

@Name("clienteAcao")
@Scope(ScopeType.CONVERSATION)
public class ClienteAcao extends BaseAcao {

	@In(create = true, required = false)
	@Out(required = false)
	private Cliente cliente;

	@In(create = true, required = false)
	private ClienteNegocio clienteNegocio;

	@In(create = true, required = false)
	@Out(required = false)
	private Cliente clienteSelecionado;

	@Out(required = false)
	private List<Cliente> listaCliente;

	private LazyDataModel<Cliente> listaClientes;

	@Out(required = false)
	private Situacao[] comboSituacoes;

	@Out(required = false)
	private TipoPessoa[] comboTipoPessoa;

	@Create
	public void init() {
		cliente.setSituacao(Situacao.ATIVO);
		listar();
	}

	@Factory(value = "listaCliente")
	public void listar() {
		listaCliente = clienteNegocio.pesquisar(cliente);
	}

	public String incluir() {
		try {
			clienteNegocio.incluir(cliente);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.CLIENTEMANTER;
	}

	public String alterar() {
		try {
			clienteNegocio.alterar(clienteSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.alterar.erro");
		}
		return Navegacao.CLIENTEMANTER;
	}

	public String excluir(Cliente cliente) {
		try {
			cliente.setSituacao(Situacao.INATIVO);
			clienteNegocio.alterar(cliente);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.excluir.erro");
		}
		return Navegacao.CLIENTEMANTER;
	}

	public String limpar() {
		cliente = new Cliente();
		clienteSelecionado = new Cliente();
		return Navegacao.CLIENTEMANTER;
	}

	public String exibirIncluir() {
		cliente = new Cliente();
		cliente.setTipoPessoa(TipoPessoa.JURIDICA);
		return Navegacao.CLIENTEINCLUIR;
	}

	public String exibirAlterar(Cliente cliente) {
		this.clienteSelecionado = cliente;
		return Navegacao.CLIENTEALTERAR;
	}

	public String cancelar() {
		try {
			entityManager.refresh(clienteSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "operacao.cancelada");
		}
		limpar();
		return Navegacao.CLIENTEMANTER;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Situacao[] getComboSituacoes() {
		return Situacao.values();
	}

	public TipoPessoa[] getComboTipoPessoa() {
		return TipoPessoa.values();
	}

	public LazyDataModel<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(LazyDataModel<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
}
