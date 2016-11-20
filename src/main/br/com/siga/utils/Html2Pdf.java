package br.com.siga.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.docx4j.org.xhtmlrenderer.pdf.ITextRenderer;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.DocumentException;

public class Html2Pdf {

	public static void convert(String input, OutputStream out) throws DocumentException, IOException {
		convert(new ByteArrayInputStream(input.getBytes()), out);
	}

	public static void convert(InputStream input, OutputStream out) throws DocumentException, IOException {
		Tidy tidy = new Tidy();
		tidy.setInputEncoding("UTF-8");
		tidy.setOutputEncoding("UTF-8");
		Document doc = tidy.parseDOM(input, null);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(doc, null);
		renderer.layout();
		renderer.createPDF(out);
	}

	public static void criarPdf(InputStream input, OutputStream out) throws com.itextpdf.text.DocumentException, IOException {
		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
		PdfWriter writer = PdfWriter.getInstance(document, out);
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, input, Charset.forName("UTF-8"));
		document.close();
	}

	public static void concatPDFs(InputStream streamProjeto, InputStream streamMeta, OutputStream outputStream, boolean paginate) {
		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
		
		try {
			int totalPages = 0;
			InputStream pdfProjeto = streamProjeto;
			InputStream pdfMeta = streamMeta;
			PdfReader pdfReaderProjeto = new PdfReader(pdfProjeto);
			PdfReader pdfReaderMeta = null;
			totalPages += pdfReaderProjeto.getNumberOfPages();
			
			if (streamMeta != null) {
				pdfReaderMeta = new PdfReader(pdfMeta);
				totalPages += pdfReaderMeta.getNumberOfPages();
			}

			// Create a writer for the outputstream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
			
			// data
			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;
			// Iterator<PdfReader> iteratorPDFReader = readers.iterator();

			// Loop through the PDF files and add to the output.
			while (pageOfCurrentReaderPDF < pdfReaderProjeto.getNumberOfPages()) {
				document.newPage();
				pageOfCurrentReaderPDF++;
				currentPageNumber++;
				page = writer.getImportedPage(pdfReaderProjeto, pageOfCurrentReaderPDF);
				cb.addTemplate(page, 0, 0);

				// Code for pagination.
				if (paginate) {
					cb.beginText();
					cb.setFontAndSize(bf, 9);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " de " + totalPages, 520, 15, 0);
					cb.endText();
				}
			}

			if (streamMeta != null) {
				pageOfCurrentReaderPDF = 0;
				while (pageOfCurrentReaderPDF < pdfReaderMeta.getNumberOfPages()) {
					document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
					document.newPage();
					pageOfCurrentReaderPDF++;
					currentPageNumber++;
					page = writer.getImportedPage(pdfReaderMeta, pageOfCurrentReaderPDF);
					cb.addTemplate(page, 0, 0);

					// Code for pagination.
					if (paginate) {
						cb.beginText();
						cb.setFontAndSize(bf, 9);
						cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " de " + totalPages, 785, 15, 0);
						cb.endText();
					}
				}
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document.isOpen())
				document.close();
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public static void concatPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate) {
		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
		
		try {
			List<InputStream> pdfs = streamOfPDFFiles;
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;
			Iterator<InputStream> iteratorPDFs = pdfs.iterator();

			// Create Readers for the pdfs.
			while (iteratorPDFs.hasNext()) {
				InputStream pdf = iteratorPDFs.next();
				PdfReader pdfReader = new PdfReader(pdf);
				readers.add(pdfReader);
				totalPages += pdfReader.getNumberOfPages();
			}
			
			// Create a writer for the outputstream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
			// data

			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;
			Iterator<PdfReader> iteratorPDFReader = readers.iterator();

			// Loop through the PDF files and add to the output.
			while (iteratorPDFReader.hasNext()) {
				PdfReader pdfReader = iteratorPDFReader.next();

				// Create a new page in the target for each source page.
				while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
					document.newPage();
					document.setPageSize(PageSize.LETTER.rotate());
					pageOfCurrentReaderPDF++;
					currentPageNumber++;
					page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
					cb.addTemplate(page, 0, 0);

					// Code for pagination.
					if (paginate) {
						cb.beginText();
						cb.setFontAndSize(bf, 9);
						cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " of " + totalPages, 520, 5, 0);
						cb.endText();
					}
				}
				pageOfCurrentReaderPDF = 0;
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document.isOpen())
				document.close();
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

}
