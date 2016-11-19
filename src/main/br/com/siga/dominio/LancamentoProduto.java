package br.com.siga.dominio;

import java.io.Serializable;
import java.text.DecimalFormat;

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

import br.com.siga.utils.BaseEntity;

@Entity
@Name("lancamentoProduto")
@Table(name = "LANCAMENTOPRODUTO")
@Scope(ScopeType.CONVERSATION)
public class LancamentoProduto implements Serializable, Comparable<LancamentoProduto>, BaseEntity {

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

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public String totalLancamentoProduto() {
		Double valor = 0D;
		valor += this.valor * this.quantidade;

		DecimalFormat df = new DecimalFormat();
		df.applyPattern("R$ #,##0.00");

		return df.format(valor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLancamentoProduto == null) ? 0 : idLancamentoProduto.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((lancamento == null) ? 0 : lancamento.hashCode());
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
		if (lancamento == null) {
			if (other.getLancamento() != null)
				return false;
		} else if (!lancamento.equals(other.getLancamento()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "produto= " + produto + ", valor= " + valor + ", quantidade= " + quantidade;
	}

	@Override
	public int compareTo(LancamentoProduto o) {
		if (o != null) {
			return this.getProduto().getDescricao().compareTo(o.getProduto().getDescricao());
		}
		return 0;
	}

	@Override
	public Long getId() {
		return idLancamentoProduto;
	}
}
