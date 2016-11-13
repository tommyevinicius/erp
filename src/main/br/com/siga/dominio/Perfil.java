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
@Table(name = "PERFIL")
@Name("perfil")
@Scope(ScopeType.CONVERSATION)
public class Perfil implements Serializable, Comparable<Perfil> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDPERFIL", length = 11)
	private Long idPerfil;

	@Column(name = "NOME", length = 80)
	private String nome;

	@Column(name = "SITUACAO")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	public Perfil() { }

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		result = prime * result + ((idPerfil == null) ? 0 : idPerfil.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
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
		Perfil other = (Perfil) obj;
		if (idPerfil == null) {
			if (other.getIdPerfil() != null)
				return false;
		} else if (!idPerfil.equals(other.getIdPerfil()))
			return false;
		if (nome == null) {
			if (other.getNome() != null)
				return false;
		} else if (!nome.equals(other.getNome()))
			return false;
		if (situacao != other.getSituacao())
			return false;
		return true;
	}

	public int compareTo(Perfil o) {
		if (o != null) {
			return this.getNome().compareTo(o.getNome());
		}
		return 0;
	}

	@Override
	public String toString() {
		return "[ ID = " + idPerfil + ", nome = " + nome + " ]";
	}

}
