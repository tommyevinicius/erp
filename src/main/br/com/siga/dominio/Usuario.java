package br.com.siga.dominio;

import java.io.Serializable;

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
import javax.persistence.Table;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.utils.Enumerados.Situacao;

@Entity
@Name("usuario")
@Table(name = "USUARIO")
@Scope(ScopeType.CONVERSATION)
public class Usuario implements Serializable, Comparable<Usuario> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDUSUARIO")
	private Long idUsuario;

	@Column(name = "NOME", length = 80)
	private String nome;

	@Column(name = "CPF", length = 15)
	private String cpf;

	@Column(name = "EMAIL", length = 50)
	private String email;

	@Column(unique = true, name = "LOGIN", length = 20)
	private String login;

	@Column(name = "SENHA", length = 50)
	private String senha;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERFIL_ID")
	private Perfil perfil;

	@Column(name = "SITUACAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	public void setId(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		Usuario other = (Usuario) obj;
		if (cpf == null) {
			if (other.getCpf() != null)
				return false;
		} else if (!cpf.equals(other.getCpf()))
			return false;
		if (email == null) {
			if (other.getEmail() != null)
				return false;
		} else if (!email.equals(other.getEmail()))
			return false;
		if (idUsuario == null) {
			if (other.getIdUsuario() != null)
				return false;
		} else if (!idUsuario.equals(other.getIdUsuario()))
			return false;
		if (login == null) {
			if (other.getLogin() != null)
				return false;
		} else if (!login.equals(other.getLogin()))
			return false;
		if (nome == null) {
			if (other.getNome() != null)
				return false;
		} else if (!nome.equals(other.getNome()))
			return false;
		if (perfil == null) {
			if (other.getPerfil() != null)
				return false;
		} else if (!perfil.equals(other.getPerfil()))
			return false;
		if (senha == null) {
			if (other.getSenha() != null)
				return false;
		} else if (!senha.equals(other.getSenha()))
			return false;
		if (situacao != other.getSituacao())
			return false;
		return true;
	}

	public int compareTo(Usuario o) {
		if (o != null) {
			return this.getNome().compareTo(o.getNome());
		}
		return 0;
	}

	@Override
	public String toString() {
		return nome + " - " + perfil;
	}

}