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
import javax.persistence.Transient;

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

	@Column(name = "NOME", nullable = false, length = 80)
	private String nome;

	@Column(name = "CPF", nullable = false, length = 15)
	private String cpf;

	@Column(name = "EMAIL", nullable = false, length = 40)
	private String email;

	@Column(unique = true, name = "LOGIN", nullable = false, length = 20)
	private String login;

	@Column(name = "SENHA", nullable = false, length = 50)
	private String senha;

	@Transient
	private String confimaSenha;

	@Transient
	private String senhaAntiga;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERFIL_ID")
	private Perfil perfil;

	@Column(name = "SITUACAO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	@Column(name = "PARAMETROPAGINACAO", nullable = true, length = 11)
	private Integer parametroPaginacao;

	@Transient
	private boolean administrador;

	public Usuario() {
	}

	public Usuario(String login) {
		this.login = login;
	}

	public Long getId() {
		return idUsuario;
	}

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

	public Integer getParametroPaginacao() {
		return parametroPaginacao;
	}

	public void setParametroPaginacao(Integer parametroPaginacao) {
		this.parametroPaginacao = parametroPaginacao;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfimaSenha() {
		return confimaSenha;
	}

	public void setConfimaSenha(String confimaSenha) {
		this.confimaSenha = confimaSenha;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		if (idUsuario == null) {
			if (other.getIdUsuario() != null)
				return false;
		} else if (!idUsuario.equals(other.getIdUsuario()))
			return false;
		if (nome == null) {
			if (other.getNome() != null)
				return false;
		} else if (!nome.equals(other.getNome()))
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
		return "Usuario [login=" + login + ", senha=" + senha + ", perfil=" + perfil + "]";
	}

}