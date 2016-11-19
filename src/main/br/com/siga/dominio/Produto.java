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

import br.com.siga.utils.BaseEntity;
import br.com.siga.utils.Enumerados.Situacao;

@Entity
@Name("produto")
@Table(name = "PRODUTO")
@Scope(ScopeType.CONVERSATION)
public class Produto implements Serializable, Comparable<Produto>, BaseEntity{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDPRODUTO")
	private Long idProduto;

	@Column(name = "DESCRICAO", length = 100)
	private String descricao;

	@Column(name = "VALOR", length = 10)
	private Double valor;
	
	@Column(name = "QUANTIDADE", length = 5)
	private Long quantidade;

	@Column(name = "SITUACAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	public Produto() { }

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
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
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((idProduto == null) ? 0 : idProduto.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Produto other = (Produto) obj;
		if (descricao == null) {
			if (other.getDescricao() != null)
				return false;
		} else if (!descricao.equals(other.getDescricao()))
			return false;
		if (idProduto == null) {
			if (other.getIdProduto() != null)
				return false;
		} else if (!idProduto.equals(other.getIdProduto()))
			return false;
		if (quantidade == null) {
			if (other.getQuantidade() != null)
				return false;
		} else if (!quantidade.equals(other.getQuantidade()))
			return false;
		if (situacao != other.getSituacao())
			return false;
		if (valor == null) {
			if (other.getValor() != null)
				return false;
		} else if (!valor.equals(other.getValor()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Produto o) {
		if (o != null) {
			return this.getDescricao().compareTo(o.getDescricao());
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return descricao;
	}

	@Override
	public Long getId() {
		return idProduto;
	}

}