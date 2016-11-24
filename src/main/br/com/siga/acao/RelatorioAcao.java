package br.com.siga.acao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Lancamento;
import br.com.siga.dominio.Usuario;
import br.com.siga.negocio.LancamentoNegocio;
import br.com.siga.negocio.UsuarioNegocio;
import br.com.siga.utils.Enumerados.Impressao;
import br.com.siga.utils.Enumerados.TipoLancamento;
import br.com.siga.utils.Validador;
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

	@SuppressWarnings("unused")
	private Collection<String> tipoDocRelatorio;

	@In(create = true, required = false)
	@Out(required = false)
	private Usuario usuario;

	@Out(required = false)
	private List<Usuario> usuarios;

	@In(create = true, required = false)
	private UsuarioNegocio usuarioNegocio;

	@In(value = "#{facesContext.externalContext}", required = false)
	private ExternalContext extCtx;

	@In(create = true, required = false)
	protected EntityManager entityManager;

	private TipoLancamento tipoLancamento;
	private String tipoDoc;
	private String descricaoTipoLancamentoFiltro;

	@Create
	public void init() {
		listarUsuarios();
		imprimirLancamento();
	}

	@Factory(value = "listaLancamentos")
	public void imprimirLancamento() {
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
		imprimirLancamento();
		return null;
		// return Navegacao.CLIENTEINCLUIR;
	}

	/**
	 * Método responsável por receber e filtrar os dados referente ao relatório PAT a ser gerado.
	 */
	public void gerarLancamento() throws JRException {
		String localArquivo = "/relatorios/reportLancamento.jasper";

		List<String> listaIDTipoLancamento = new ArrayList<String>();
		listaIDTipoLancamento.add(setTipoLancamentoSelecionado(getTipoLancamento()));

		if (!(Validador.isCollectionValida(listaIDTipoLancamento))) {
			listaIDTipoLancamento = setTipoLancamentoSelecionado(TipoLancamento.values());
		}

		Impressao contentType = validadorContentType(getTipoDoc());
		String nomeArquivo = "Lançamento de Estoque";

		// Converte em String e formata uma lista de ids referente a UNIDADE OPERACIONAL.
		// A lista de ids será utilizada nas consultas dos arquivos jasper.
		String idsSituacaoString = String.valueOf(listaIDTipoLancamento);
		String newListIdsSituacaoString = idsSituacaoString.substring(1, idsSituacaoString.length() - 1);

		Map parametros = new HashMap();
		parametros.put("TIPO", listaIDTipoLancamento);
		// parametros.put("USUARIO", new ArrayList<Usuario>());

		String arquivoJasper = "reportLancamento.jasper";

		try {
			// carrega os arquivos jasper
			String caminho = ((ServletContext) extCtx.getContext()).getRealPath(localArquivo);
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminho);

			String[] path = caminho.split(arquivoJasper);
			parametros.put("SUBREPORT_DIR", path[0]);

			HttpServletResponse response = (HttpServletResponse) extCtx.getResponse();

			gerar(response, relatorioJasper, parametros, contentType, nomeArquivo);
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

	private String setTipoLancamentoSelecionado(TipoLancamento tipo) {
		String valorSituacao = tipo.getDescricao();

		if (valorSituacao != null) {
			setDescricaoTipoLancamentoFiltro(valorSituacao.toString());
		}

		return valorSituacao;
	}
	
	private List<String> setTipoLancamentoSelecionado(TipoLancamento[] tipo) {
		List<String> valorSituacao = new ArrayList<String>();
		StringBuffer sb = null;
		for (TipoLancamento sp : tipo) {
			if (sb == null) {
				sb = new StringBuffer();
				sb.append(sp.getDescricao());
			} else {
				sb.append(" - " + sp.getDescricao());
			}
			valorSituacao.add(sp.getDescricao());
		}
		if (sb != null) {
			setDescricaoTipoLancamentoFiltro(sb.toString());
		}

		return valorSituacao;
	}

	/**
	 * Métodos Utilizando na localização dos Ids dos itens selecionados na página xhtml.
	 */
	private List<String> comboTipoDoc() {
		List<String> tipoDoc = new ArrayList<String>();
		Impressao[] contentType = Impressao.values();
		for (int i = 0; i < contentType.length; i++) {
			tipoDoc.add(contentType[i].getDescricao());
		}
		return tipoDoc;
	}

	private Impressao validadorContentType(String descContentType) {
		Impressao[] listContentType = Impressao.values();
		Impressao contentType = null;

		for (int i = 0; i < listContentType.length; i++) {
			if (descContentType.equals(listContentType[i].getDescricao())) {
				contentType = listContentType[i];
			}
		}
		return contentType;
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

	public Impressao[] getTipoImpressao() {
		return Impressao.values();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Collection<String> getTipoDocRelatorio() {
		return tipoDocRelatorio = comboTipoDoc();
	}

	public void setTipoDocRelatorio(Collection<String> tipoDocRelatorio) {
		this.tipoDocRelatorio = tipoDocRelatorio;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricaoTipoLancamentoFiltro() {
		return descricaoTipoLancamentoFiltro;
	}

	public void setDescricaoTipoLancamentoFiltro(String descricaoTipoLancamentoFiltro) {
		this.descricaoTipoLancamentoFiltro = descricaoTipoLancamentoFiltro;
	}

	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
}
