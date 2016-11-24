package br.com.siga.utils;

import br.com.siga.utils.BaseEnum;

public class Enumerados {

	public enum Situacao implements BaseEnum {

		ATIVO(0, "ATIVO"), INATIVO(1, "INATIVO");

		private Integer valor;
		private String descricao;

		Situacao(Integer valor, String descricao) {
			this.valor = valor;
			this.descricao = descricao;
		}

		public String getDescricao() {

			return descricao;
		}

		public String toString() {

			return this.descricao;
		}

		public Integer getValor() {
			return this.valor;
		}
	}

	public enum SimNao implements BaseEnum {
		SIM(0, "SIM"), NAO(1, "NÃO");

		private Integer valor;
		private String descricao;

		SimNao(Integer valor, String descricao) {
			this.valor = valor;
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
		}

		public String toString() {
			return this.descricao;
		}

		public Integer getValor() {
			return this.valor;
		}
	}

	public enum Impressao implements BaseEnum {
		PDF(1, "PDF", "application/pdf", ".pdf"), DOC(2, "DOC", "application/msword", ".doc"), RTF(3, "RTF", "application/rtf", ".rtf"), ODT(4,
				"ODT", "application/vnd.oasis.opendocument.text", ".odt"), XLS(5, "XLS", "application/vnd.ms-excel", ".xls"), ODS(6, "ODS",
				"application/vnd.oasis.opendocument.spreadsheet", ".ods");

		private int valor;
		private String descricao;
		private String contentType;
		private String extensao;

		Impressao(int valor, String descricao, String contentType, String extensao) {
			this.valor = valor;
			this.descricao = descricao;
			this.contentType = contentType;
			this.extensao = extensao;
		}

		public String getDescricao() {

			return this.descricao;
		}

		public String toString() {

			return getDescricao();
		}

		public int getValue() {
			return valor;
		}

		public String getContentType() {
			return this.contentType;
		}

		public String getExtensao() {
			return this.extensao;
		}

		public Object getValor() {
			return valor;
		}
	}

	public enum TipoPessoa implements BaseEnum {
		FISICA(0, "Pessoa Fisíca"), JURIDICA(1, "Pessoa Jurídica");

		private Integer valor;
		private String descricao;

		TipoPessoa(Integer valor, String descricao) {
			this.valor = valor;
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
		}

		public Object getValor() {
			return this.valor;
		}

		public String toString() {
			return this.descricao;
		}

	}

	public enum TipoLancamento implements BaseEnum {
		ENTRADA(0, "Entrada"), SAIDA(1, "Saída");

		private Integer valor;
		private String descricao;

		TipoLancamento(Integer valor, String descricao) {
			this.valor = valor;
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
		}

		public Object getValor() {
			return this.valor;
		}

		public String toString() {
			return this.descricao;
		}

	}
}
