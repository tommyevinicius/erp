package br.com.siga.acao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Lancamento;
import br.com.siga.negocio.LancamentoNegocio;
import br.com.siga.utils.Enumerados.Impressao;
import br.com.siga.utils.Enumerados.TipoLancamento;
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

	@In(value = "#{facesContext.externalContext}", required = false)
	private ExternalContext extCtx;

	@In(create = true, required = false)
	protected EntityManager entityManager;

	private TipoLancamento tipoLancamento;
	private String tipoDoc;
	private String descricaoTipoLancamentoFiltro;

	public void init() { }

	/**
	 * Método responsável por receber e filtrar os dados referente ao relatório Lançamento a ser gerado.
	 */
	public void gerarLancamento() throws JRException {
		String localArquivo = "/relatorios/reportLancamento.jasper";

		Impressao contentType = validadorContentType(getTipoDoc());
		String nomeArquivo = "Lançamentos de Estoque";

		try {
			// carrega os arquivos jasper
			String caminho = ((ServletContext) extCtx.getContext()).getRealPath(localArquivo);
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminho);

			HttpServletResponse response = (HttpServletResponse) extCtx.getResponse();

			gerar(response, relatorioJasper, null, contentType, nomeArquivo);
		} catch (JRException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Método responsável por receber e filtrar os dados referente ao relatório Estoque a ser gerado.
	 */
	public void gerarEstoque() throws JRException {
		String localArquivo = "/relatorios/reportEstoqueProduto.jasper";

		Impressao contentType = validadorContentType(getTipoDoc());
		String nomeArquivo = "Estoque de Produtos";

		try {
			// carrega os arquivos jasper
			String caminho = ((ServletContext) extCtx.getContext()).getRealPath(localArquivo);
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminho);

			HttpServletResponse response = (HttpServletResponse) extCtx.getResponse();

			gerar(response, relatorioJasper, null, contentType, nomeArquivo);
		} catch (JRException e) {
			e.printStackTrace();
		}

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
