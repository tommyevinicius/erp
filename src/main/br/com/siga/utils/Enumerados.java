package br.com.siga.utils;

import br.com.siga.utils.BaseEnum;

public class Enumerados {

	public enum Situacao implements BaseEnum {

		ATIVO(1, "ATIVO"), INATIVO(2, "INATIVO");

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
		SIM(1, "SIM"), NAO(2, "NÃO");

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
	
	public enum Periodo implements BaseEnum {
		MAT(1, "MAT"), VESP(2, "VESP"), NOT(3, "NOT");

		private Integer valor;
		private String descricao;

		Periodo(Integer valor, String descricao) {
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
}
