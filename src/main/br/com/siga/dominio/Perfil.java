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

	@Column(name = "NOME", nullable = false, length = 80)
	private String nome;

	@Column(name = "SITUACAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	public Perfil() {
	}

	public Perfil(String descricao) {
		this.nome = descricao;
	}

	public Long getId() {
		return idPerfil;
	}

	public void setId(Long id) {
		this.idPerfil = id;
	}

	public String getDescricao() {
		return nome;
	}

	public void setDescricao(String descricao) {
		this.nome = descricao;
	}

	public long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(long idPerfil) {
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
		return this.idPerfil.intValue();
	}

	@Override
	public boolean equals(Object oPerfil) {
		Perfil perfil = (Perfil) oPerfil;
		return this.idPerfil == perfil.getIdPerfil();
	}

	public int compareTo(Perfil o) {
		if (o != null) {
			return this.getNome().compareTo(o.getNome());
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Perfil [nome=" + nome + "]";
	}

}
