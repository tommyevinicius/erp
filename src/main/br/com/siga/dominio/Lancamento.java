package br.com.siga.dominio;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.utils.BaseEntity;
import br.com.siga.utils.Enumerados.TipoLancamento;

@Entity
@Name("lancamento")
@Table(name = "LANCAMENTO")
@Scope(ScopeType.CONVERSATION)
public class Lancamento implements Serializable, BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDLANCAMENTO")
	private Long idLancamento;

	@OneToMany(mappedBy = "lancamento", orphanRemoval = true)
	@Cascade({ CascadeType.ALL })
	private List<LancamentoProduto> listaProdutos;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FORNECEDOR_ID")
	private Fornecedor fornecedor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CLIENTE_ID")
	private Cliente cliente;

	@Column(name = "TIPOLANCAMENTO")
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipoLancamento;
	
	@ForeignKey(name = "FK_LANCAMENTO_USUARIO_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USUARIO_ID")
	private Usuario usuario;
	
	@Column(name = "COMENTARIO")
	private String comentario;
	
	public Lancamento() { }
	
	public Long getIdLancamento() {
		return idLancamento;
	}

	public void setIdLancamento(Long idLancamento) {
		this.idLancamento = idLancamento;
	}

	public List<LancamentoProduto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<LancamentoProduto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String totalLancamento() {
		Double valor = 0D;
		for (LancamentoProduto lp : this.listaProdutos) {
			valor += lp.getValor() * lp.getQuantidade();
		}
		
		DecimalFormat df = new DecimalFormat();
        df.applyPattern("R$ #,##0.00");
		
		return df.format(valor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + ((idLancamento == null) ? 0 : idLancamento.hashCode());
		result = prime * result + ((listaProdutos == null) ? 0 : listaProdutos.hashCode());
		result = prime * result + ((tipoLancamento == null) ? 0 : tipoLancamento.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Lancamento other = (Lancamento) obj;
		if (cliente == null) {
			if (other.getCliente() != null)
				return false;
		} else if (!cliente.equals(other.getCliente()))
			return false;
		if (comentario == null) {
			if (other.getComentario() != null)
				return false;
		} else if (!comentario.equals(other.getComentario()))
			return false;
		if (fornecedor == null) {
			if (other.getFornecedor() != null)
				return false;
		} else if (!fornecedor.equals(other.getFornecedor()))
			return false;
		if (idLancamento == null) {
			if (other.getIdLancamento() != null)
				return false;
		} else if (!idLancamento.equals(other.getIdLancamento()))
			return false;
		if (listaProdutos == null) {
			if (other.getListaProdutos() != null)
				return false;
		} else if (!listaProdutos.equals(other.getListaProdutos()))
			return false;
		if (tipoLancamento != other.getTipoLancamento())
			return false;
		if (usuario == null) {
			if (other.getUsuario() != null)
				return false;
		} else if (!usuario.equals(other.getUsuario()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + idLancamento;
	}

	@Override
	public Long getId() {
		return idLancamento;
	}
}