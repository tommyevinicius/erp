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

	@Out(required = false)
	private Situacao[] comboSituacoes = Situacao.values();
	
	@Out(required = false)
	private TipoPessoa[] comboTipoPessoa = TipoPessoa.values();

	@Create
	public void init() {
		listar();
	}

	@Factory(value = "listaCliente")
	public void listar() {
		listaCliente = clienteNegocio.pesquisar(cliente);
	}

	@End
	public String incluir() {
		try {
			clienteNegocio.incluir(cliente);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.incluir.erro");
		}
		limpar();
		return Navegacao.CLIENTEMANTER;
	}

	@End
	public String alterar() {
		try {
			clienteNegocio.alterar(clienteSelecionado);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.alterar.erro");
		}
		return Navegacao.CLIENTEMANTER;
	}

	@End
	public String excluir(Cliente cliente) {
		try {
			cliente.setSituacao(Situacao.INATIVO);
			clienteNegocio.alterar(cliente);
		} catch (Exception e) {
			super.addMsg(Severity.FATAL, "registro.excluir.erro");
		}
		return Navegacao.CLIENTEMANTER;
	}

	@End
	public String limpar() {
		cliente = new Cliente();
		clienteSelecionado = new Cliente();
		return Navegacao.CLIENTEMANTER;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirIncluir() {
		cliente = new Cliente();
		cliente.setTipoPessoa(TipoPessoa.JURIDICA);
		return Navegacao.CLIENTEINCLUIR;
	}

	@Begin(join = true, flushMode = FlushModeType.MANUAL)
	public String exibirAlterar(Cliente cliente) {
		this.clienteSelecionado = cliente;
		clienteSelecionado.changePessoaFisica();
		return Navegacao.CLIENTEALTERAR;
	}

	@End
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
		return comboSituacoes;
	}

	public void setComboSituacoes(Situacao[] comboSituacoes) {
		this.comboSituacoes = comboSituacoes;
	}

	public TipoPessoa[] getComboTipoPessoa() {
		return comboTipoPessoa;
	}

	public void setComboTipoPessoa(TipoPessoa[] comboTipoPessoa) {
		this.comboTipoPessoa = comboTipoPessoa;
	}
}
