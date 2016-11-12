package br.com.siga.negocio;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;

import br.com.siga.dominio.Usuario;
import br.com.siga.utils.Criptografia;
import br.com.siga.utils.Enumerados.SimNao;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Mensagens;
import br.com.siga.utils.Validador;
import br.com.templates.utils.NegocioBase;

@Name("usuarioNegocio")
@Scope(ScopeType.CONVERSATION)
public class UsuarioNegocio extends NegocioBase<Usuario, Long> {

	/**
	 * Verifica o login e o cpf, criptografa a senha do usuario.
	 */
	@End
	public void incluir(Usuario usuario) throws Exception {
		if (Validador.isObjetoValido(consultarUsuario(usuario))) {
			throw new Exception("geral.cpfeloginCadastrado");
		}
		validarConfirmaSenha(usuario);
		validarUsuarioLogin(usuario);
		usuario.setSenha(Criptografia.criptografarSHA1(usuario.getSenha()));
		super.incluir(usuario);

	}

	/**
	 * Verifica se o cpf já existe no banco
	 */
	public void validarUsuarioCpf(Usuario usuario) throws Exception {
		Usuario usuarioBuscaCpf = consultarPorCPF(usuario);
		if (usuarioBuscaCpf != null) {
			if (usuarioBuscaCpf.getId() != usuario.getId()) {
				throw new Exception("geral.cpfCadastrado");
			}
		}
	}

	/**
	 * Verifica se o Login já existe no banco
	 */
	public void validarUsuarioLogin(Usuario usuario) throws Exception {
		Usuario usuarioBuscaLogin = consultarUsuario(usuario);
		if (usuarioBuscaLogin != null) {
			if (usuarioBuscaLogin.getId() != usuario.getId()) {
				throw new Exception("geral.loginCadastrado");
			}
		}
	}

	/**
	 * Metodo responsavel por verificar se a senha digitada é a mesma da confirmação
	 */

	public void validarConfirmaSenha(Usuario usuario) throws Exception {
		if (!usuario.getSenha().equals(usuario.getSenhaAntiga())) {
			if (!usuario.getSenha().equals(usuario.getConfimaSenha())) {
				throw new Exception("geral.erroConfirmaSenha");
			}
		}
	}

	/**
	 * Metodo responsavel alterar usuário.
	 */
	public void alterarUsuario(Usuario usuario) throws Exception {
		validarConfirmaSenha(usuario);
		validarUsuarioLogin(usuario);
		if (!usuario.getSenha().equals(usuario.getSenhaAntiga())) {
			usuario.setSenha(Criptografia.criptografarSHA1(usuario.getSenha()));
		}
		super.alterar(usuario);
	}

	/**
	 * Método utilizado para consultar usuário
	 */
	public Usuario consultarUsuario(Usuario usuario) {
		if (Validador.isObjetoValido(usuario)) {
			Session session = (Session) entityManager.getDelegate();
			Criteria criteria = session.createCriteria(Usuario.class);
			if (Validador.isStringValida(usuario.getLogin())) {
				criteria.add(Restrictions.eq("login", usuario.getLogin()));
			}
			return (Usuario) criteria.uniqueResult();
		}
		return null;
	}

	/**
	 * pesquisa no banco o cpf e retorna um objeto
	 */
	public Usuario consultarPorCPF(Usuario usuario) {
		if (Validador.isObjetoValido(usuario)) {
			Session session = (Session) entityManager.getDelegate();
			Criteria criteria = session.createCriteria(Usuario.class);
			if (Validador.isStringValida(usuario.getCpf())) {
				criteria.add(Restrictions.eq("cpf", usuario.getCpf()));
			}
			return (Usuario) criteria.uniqueResult();
		}
		return null;
	}

	/**
	 * Realiza a consulta paginada baseado nos parâmetros informados.
	 */
	public List<Usuario> listarPaginado(Usuario usuario) {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return listarPaginado(pesquisar(usuario), pesquisar(usuario), "nome");
	}

	/**
	 * Realiza a consulta paginada baseado nos parâmetros informados.
	 */
	public List<Usuario> listarPaginadoQuery(Usuario usuario) {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return listarPaginado(montarQuery(usuario, SimNao.NAO, "nome"), montarQuery(usuario, SimNao.SIM, null));
	}

	public Query montarQuery(Usuario usuario, SimNao count, String campoOrdenacao) {
		StringBuffer sb = new StringBuffer();
		if (Validador.isEnumValido(count) && count.equals(SimNao.SIM)) {
			sb.append(" SELECT COUNT(DISTINCT u) FROM Usuario u ");
		} else {
			sb.append(" SELECT DISTINCT u FROM Usuario u ");
		}

		sb.append(" where 1 = 1 ");

		if (Validador.isStringValida(usuario.getNome())) {
			sb.append(" AND lower(u.nome) like lower(concat('%', :nome, '%')) ");
		}
		if (Validador.isStringValida(usuario.getCpf())) {
			sb.append(" AND lower(u.cpf) like lower(concat('%', :cpf, '%')) ");
		}
		if (Validador.isStringValida(usuario.getEmail())) {
			sb.append(" AND lower(u.email) like lower(concat('%', :email, '%')) ");
		}
		if (Validador.isStringValida(usuario.getLogin())) {
			sb.append(" AND lower(u.login) like lower(concat('%',:login,'%')) ");
		}
		if (Validador.isEnumValido(usuario.getSituacao())) {
			sb.append(" AND u.situacao = :situacao ");
		}
		if (Validador.isStringValida(campoOrdenacao)) {
			sb.append(" ORDER BY " + campoOrdenacao);
		}

		Query query = entityManager.createQuery(sb.toString());

		if (Validador.isStringValida(usuario.getNome())) {
			query.setParameter("nome", usuario.getNome());
		}
		if (Validador.isStringValida(usuario.getCpf())) {
			query.setParameter("cpf", usuario.getCpf());
		}
		if (Validador.isStringValida(usuario.getEmail())) {
			query.setParameter("email", usuario.getEmail());
		}
		if (Validador.isStringValida(usuario.getLogin())) {
			query.setParameter("login", usuario.getLogin());
		}
		if (Validador.isEnumValido(usuario.getSituacao())) {
			query.setParameter("situacao", usuario.getSituacao());
		}
		if (Validador.isStringValida(campoOrdenacao)) {
			sb.append(" ORDER BY " + campoOrdenacao);
		}
		return query;
	}

	/**
	 * Monta a critéria para a consulta.
	 */
	private Criteria pesquisar(Usuario usuario) {
		Session session = (Session) entityManager.getDelegate();
		Criteria criteria = session.createCriteria(Usuario.class);
		if (Validador.isStringValida(usuario.getNome())) {
			criteria.add(Restrictions.like("nome", usuario.getNome(), MatchMode.ANYWHERE));
		}
		if (Validador.isStringValida(usuario.getCpf())) {
			criteria.add(Restrictions.like("cpf", usuario.getCpf(), MatchMode.ANYWHERE));
		}
		if (Validador.isStringValida(usuario.getEmail())) {
			criteria.add(Restrictions.like("email", usuario.getEmail(), MatchMode.ANYWHERE));
		}
		if (Validador.isStringValida(usuario.getLogin())) {
			criteria.add(Restrictions.like("login", usuario.getLogin(), MatchMode.ANYWHERE));
		}
		if (Validador.isEnumValido(usuario.getSituacao())) {
			criteria.add(Restrictions.eq("situacao", usuario.getSituacao()));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listar() {
		Session session = (Session) entityManager.getDelegate();
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("situacao", Situacao.ATIVO));
		criteria.addOrder(Order.asc("nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public boolean isLoginSenhaValidos(String login, String senha) {
		if (StringUtils.EMPTY.equals(login) || StringUtils.EMPTY.equals(senha)) {
			super.addMsg(Severity.ERROR, Mensagens.LOGIN_OU_SENHA_VAZIO);
			return false;
		}
		return true;
	}

	public Usuario consultarPorLoginSenha(String login, String senha) {
		StringBuilder hql = new StringBuilder();
		hql.append(" FROM Usuario u ");
		hql.append(" WHERE u.login = :login ");
		if (senha != null) {
			hql.append(" AND u.senha = :senha ");
		}

		Query query = createQuery(hql.toString());
		query.setParameter("login", login.trim());
		if (senha != null) {
			query.setParameter("senha", senha.trim());
		}

		Usuario usuario = (Usuario) query.getSingleResult();

		return usuario;
	}

}
