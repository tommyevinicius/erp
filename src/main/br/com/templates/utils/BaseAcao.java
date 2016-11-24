package br.com.templates.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.security.Identity;

import br.com.siga.dominio.Usuario;
import br.com.siga.utils.ConexaoDataSource;
import br.com.siga.utils.Enumerados.Impressao;

@AutoCreate
@Name("baseAcao")
@Scope(ScopeType.CONVERSATION)
public class BaseAcao {

	@In(create = true, required = false)
	protected EntityManager entityManager;

	@In(create = true, required = false)
	protected FacesMessages facesMessages;

	@In(create = true, required = false)
	protected Identity identity;

	@In(value = "#{facesContext}", required = false)
	protected FacesContext facesContext;

	@In(value = "usuarioLogado", required = false)
	protected Usuario usuarioLogado;

	@In(value = "#{facesContext.externalContext}", required = false)
	protected ExternalContext extCtx;

	protected final String PAGINA_ATUAL = "paginaAtual";

	protected String paginaAtual;

	private Integer tamanhoObjeto;

	private String voltarPara;

	public void evict(Object entity) {
		org.hibernate.Session session = (Session) entityManager.getDelegate();
		session.evict(entity);
	}

	protected boolean isUsuarioLogadoAdmin() {
		if (usuarioLogado != null && usuarioLogado.getNome().equalsIgnoreCase("root")) {
			return true;
		}
		return false;
	}

	public Integer getTamanhoObjeto() {
		return tamanhoObjeto;
	}

	public String voltar() {
		if (voltarPara != null) {
			return voltarPara;
		}
		return "";
	}

	/**
	 * Locate an UIComponent from its root component.
	 */
	@SuppressWarnings("rawtypes")
	public static UIComponent findComponent(UIComponent base, String id) {
		if (id.equals(base.getId()))
			return base;

		UIComponent children = null;
		UIComponent result = null;
		Iterator childrens = base.getFacetsAndChildren();
		while (childrens.hasNext() && (result == null)) {
			children = (UIComponent) childrens.next();
			if (id.equals(children.getId())) {
				result = children;
				break;
			}
			result = findComponent(children, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	protected void addMsg(Severity severity, String msg) {
		StatusMessages.instance().addFromResourceBundle(severity, msg);
	}

	public void redirecionarPagina(String url) {
		FacesContext context = FacesContext.getCurrentInstance();
		NavigationHandler navHandler = context.getApplication().getNavigationHandler();
		navHandler.handleNavigation(context, null, url);
	}

	/**
	 * Metodo responsavel por atualizar a pagina atual
	 */
	protected void refreshPage() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String refreshpage = fc.getViewRoot().getViewId();
		ViewHandler ViewH = fc.getApplication().getViewHandler();
		UIViewRoot UIV = ViewH.createView(fc, refreshpage);
		UIV.setViewId(refreshpage);
		fc.setViewRoot(UIV);
	}
	
	/**
	 * Método responsável gerar relatório.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void gerar(HttpServletResponse response, JasperReport relatorioJasper, Map parametros, Impressao contentType, String nomeArquivo) {

		try {
			byte[] bytes = null;
			Connection conn = null;

			try {
				conn = ConexaoDataSource.getConnectionDataSource();
				if (contentType.equals(Impressao.PDF)) {
					bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros, conn);
					
					if (bytes != null && bytes.length > 0) {
						// envia o relatório em formato PDF para o browser
						response.setHeader("Content-Disposition", "inline; filename=" + nomeArquivo + contentType.getExtensao());
						// response.setHeader("Content-Disposition", "attachment; filename=" + nomeArquivo +
						// contentType.getExtensao());
						response.setContentType(contentType.getContentType());
						response.setContentLength(bytes.length);
						ServletOutputStream ouputStream = response.getOutputStream();
						ouputStream.write(bytes, 0, bytes.length);
						ouputStream.flush();
						ouputStream.close();
						facesContext.responseComplete();
						conn.close();
					}
				} else {
					JasperPrint jasperPrint = JasperFillManager.fillReport(relatorioJasper, parametros, conn);
					ByteArrayOutputStream osReport = new ByteArrayOutputStream();
					if (contentType.equals(Impressao.XLS)) {
						JExcelApiExporter xlsExporter = new JExcelApiExporter();
						xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
						xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, osReport);
						xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
						xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
						xlsExporter.exportReport();
					} else if (contentType.equals(Impressao.RTF)) {
						JRRtfExporter rtfExporter = new JRRtfExporter();
						rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
						rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, osReport);
						rtfExporter.exportReport();
					} else if (contentType.equals(Impressao.DOC)) {
						JRRtfExporter docExporter = new JRRtfExporter();
						docExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
						docExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, osReport);
						docExporter.exportReport();
					} else if (contentType.equals(Impressao.ODT)) {
						JROdtExporter odtExporter = new JROdtExporter();
						odtExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
						odtExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, osReport);
						odtExporter.exportReport();
					} else if (contentType.equals(Impressao.ODS)) {
						JROdsExporter odsExporter = new JROdsExporter();
						odsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
						odsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, osReport);
						odsExporter.exportReport();
					}
					bytes = osReport.toByteArray();
					if (bytes != null && bytes.length > 0) {
						response.setHeader("Content-Disposition", "inline; filename=" + nomeArquivo + contentType.getExtensao());
						response.setContentType(contentType.getContentType());
						response.setContentLength(bytes.length);
						osReport.close();
						ServletOutputStream ouputStream = response.getOutputStream();
						ouputStream.write(bytes, 0, bytes.length);
						ouputStream.flush();
						ouputStream.close();
						facesContext.responseComplete();
					}
				}
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					 // Fecha a conexão com o banco de dados utilizando informações do dataSource.
					ConexaoDataSource.closeConnectionDataSource(conn, null, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
