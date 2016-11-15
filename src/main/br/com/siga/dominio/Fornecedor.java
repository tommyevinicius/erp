package br.com.siga.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.utils.Enumerados.Situacao;

@Entity
@Name("fornecedor")
@Table(name = "FORNECEDOR")
@Scope(ScopeType.CONVERSATION)
public class Fornecedor implements Serializable, Comparable<Fornecedor> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDFORNECEDOR")
	private Long idFornecedor;

	@Column(name = "DESCRICAO", length = 100)
	private String descricao;

	@Column(name = "RAZAOSOCIAL", length = 100)
	private String razaoSocial;
	
	@Column(name = "ENDERECO", length = 255)
	private String endereco;

	@Column(name = "CNPJ", length = 20)
	private String cnpj;

	@Column(name = "TELEFONE", length = 20)
	private String telefone;

	@Column(name = "SITUACAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	public Long getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((idFornecedor == null) ? 0 : idFornecedor.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		if (cnpj == null) {
			if (other.getCnpj() != null)
				return false;
		} else if (!cnpj.equals(other.getCnpj()))
			return false;
		if (descricao == null) {
			if (other.getDescricao() != null)
				return false;
		} else if (!descricao.equals(other.getDescricao()))
			return false;
		if (endereco == null) {
			if (other.getEndereco() != null)
				return false;
		} else if (!endereco.equals(other.getEndereco()))
			return false;
		if (idFornecedor == null) {
			if (other.getIdFornecedor() != null)
				return false;
		} else if (!idFornecedor.equals(other.getIdFornecedor()))
			return false;
		if (razaoSocial == null) {
			if (other.getRazaoSocial() != null)
				return false;
		} else if (!razaoSocial.equals(other.getRazaoSocial()))
			return false;
		if (situacao != other.getSituacao())
			return false;
		if (telefone == null) {
			if (other.getTelefone() != null)
				return false;
		} else if (!telefone.equals(other.getTelefone()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return descricao;
	}

	@Override
	public int compareTo(Fornecedor o) {
		if (o != null) {
			return this.getDescricao().compareTo(o.getDescricao());
		}
		return 0;
	}

}