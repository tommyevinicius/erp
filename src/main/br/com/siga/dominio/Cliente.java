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
import br.com.siga.utils.Enumerados.TipoPessoa;

@Entity
@Name("cliente")
@Table(name = "CLIENTE")
@Scope(ScopeType.CONVERSATION)
public class Cliente implements Serializable, Comparable<Cliente> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDCLIENTE")
	private Long idCliente;
	
	@Column(name = "TTPOPESSOA", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	@Column(name = "NOME", length = 100)
	private String nome;

	@Column(name = "RAZAOSOCIAL", length = 100)
	private String razaoSocial;
	
	@Column(name = "ENDERECO", length = 255)
	private String endereco;

	@Column(name = "CNPJ", length = 20)
	private String cnpj;
	
	@Column(name = "CPF", length = 20)
	private String cpf;

	@Column(name = "TELEFONE", length = 20)
	private String telefone;

	@Column(name = "SITUACAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	public Cliente() { }

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result + ((tipoPessoa == null) ? 0 : tipoPessoa.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cnpj == null) {
			if (other.getCnpj() != null)
				return false;
		} else if (!cnpj.equals(other.getCnpj()))
			return false;
		if (cpf == null) {
			if (other.getCpf() != null)
				return false;
		} else if (!cpf.equals(other.getCpf()))
			return false;
		if (nome == null) {
			if (other.getNome() != null)
				return false;
		} else if (!nome.equals(other.getNome()))
			return false;
		if (endereco == null) {
			if (other.getEndereco() != null)
				return false;
		} else if (!endereco.equals(other.getEndereco()))
			return false;
		if (idCliente == null) {
			if (other.getIdCliente() != null)
				return false;
		} else if (!idCliente.equals(other.getIdCliente()))
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
		if (tipoPessoa != other.getTipoPessoa())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int compareTo(Cliente o) {
		if (o != null) {
			return this.getNome().compareTo(o.getNome());
		}
		return 0;
	}

}