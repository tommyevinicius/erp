package br.com.siga.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Entity
@Name("lancamentoProduto")
@Table(name = "LANCAMENTOPRODUTO")
@Scope(ScopeType.CONVERSATION)
public class LancamentoProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDLANCAMENTOPRODUTO")
	private Long idLancamentoProduto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANCAMENTO_ID", nullable = false)
	private Lancamento lancamento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUTO")
	private Produto produto;

	@Column(name = "VALOR")
	private Double valor;

	@Column(name = "QUANTIDADE")
	private Long quantidade;

	public Long getIdLancamentoProduto() {
		return idLancamentoProduto;
	}

	public void setIdLancamentoProduto(Long idLancamentoProduto) {
		this.idLancamentoProduto = idLancamentoProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLancamentoProduto == null) ? 0 : idLancamentoProduto.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
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
		LancamentoProduto other = (LancamentoProduto) obj;
		if (idLancamentoProduto == null) {
			if (other.getIdLancamentoProduto() != null)
				return false;
		} else if (!idLancamentoProduto.equals(other.getIdLancamentoProduto()))
			return false;
		if (produto == null) {
			if (other.getProduto() != null)
				return false;
		} else if (!produto.equals(other.getProduto()))
			return false;
		if (quantidade == null) {
			if (other.getQuantidade() != null)
				return false;
		} else if (!quantidade.equals(other.getQuantidade()))
			return false;
		if (valor == null) {
			if (other.getValor() != null)
				return false;
		} else if (!valor.equals(other.getValor()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "produto= " + produto + ", valor= " + valor + ", quantidade= " + quantidade;
	}
}
