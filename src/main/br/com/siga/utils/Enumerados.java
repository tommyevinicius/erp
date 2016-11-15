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
		SIM(0, "SIM"), NAO(1, "NÃƒO");

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
	
	public enum Cargo implements BaseEnum {
		PROFESSOR(0, "Professor"), COORDENADOR(1, "Coordenador");

		private Integer valor;
		private String descricao;

		Cargo(Integer valor, String descricao) {
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
