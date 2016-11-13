package br.com.templates.utils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.GenericJDBCException;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import br.com.siga.dominio.Usuario;
import br.com.siga.utils.Mensagens;
import br.com.siga.utils.Validador;

@AutoCreate
@Name("negocioBase")
@Scope(ScopeType.CONVERSATION)
public class NegocioBase<T, PK extends Serializable> {

	@In(create = true, required = false)
	protected EntityManager entityManager;

	@Logger
	protected Log log;

	@In(create = true, required = false)
	private Session session;

	@In(create = true, required = false)
	protected FacesMessages facesMessages;

	@In(value = "#{facesContext.externalContext}", required = false)
	protected ExternalContext extCtx;

	@In(create = true, required = false)
	@Out(required = false)
	protected Integer paginaAtual;

	@In(create = true, required = false)
	@Out(required = false)
	protected Integer paginaAtualInput;

	@Out(required = false)
	protected Long totalRegistros;

	@Out(required = false)
	protected Long qtdePaginas;

	@Out(required = false)
	protected Integer[] tamanhoPaginacao;

	@In(value = "usuarioLogado", required = false)
	protected Usuario usuarioLogado;

	protected static final int MAXIMO_RESULTADOS = 10;

	protected long hashParametros;

	public NegocioBase() {
		this.qtdePaginas = new Long(0);
	}

	public Integer getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(Integer paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public Long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public long getQtdePaginas() {
		return qtdePaginas;
	}

	public Integer getPaginaAtualInput() {
		return paginaAtualInput;
	}

	public void setPaginaAtualInput(Integer paginaAtualInput) {
		this.paginaAtualInput = paginaAtualInput;
	}

	public void setQtdePaginas(Long qtdePaginas) {
		this.qtdePaginas = qtdePaginas;
	}

	public Integer[] getTamanhoPaginacao() {
		return tamanhoPaginacao;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public void addMsg (Severity severity, Mensagens msg) {
		StatusMessages.instance().addFromResourceBundle(severity, msg.getKey());
	}

	/**
	 * Cria uma instancia de Query baseado em hql
	 */
	protected Query createQuery(String hql) {
		return entityManager.createQuery(hql);
	}

	/**
	 * Metodo responsavel por localizar a pagina selecionada no paginador.
	 */
	protected void obterPaginaAtual() {
		paginaAtual = 1;
		if (Validador.isStringValida(extCtx.getRequestParameterMap().get("paginaAtual"))) {
			paginaAtual = Integer.parseInt(extCtx.getRequestParameterMap().get("paginaAtual"));
		}
		if (paginaAtualInput != null) {
			paginaAtual = paginaAtualInput;
		}
	}

	/**
	 * Metodo responsavel por todas as inclusoes do sistema.
	 */
	public void incluir(final T objeto) throws Exception {
		try {
			entityManager.persist(objeto);
			entityManager.flush();
		} catch (PersistenceException ee) {
			ee.printStackTrace();
			if (ee.getCause() instanceof GenericJDBCException) {
				SQLException sqle = ((GenericJDBCException) ee.getCause()).getSQLException();
				facesMessages.add(Severity.ERROR, sqle.getMessage());
			}
			if (ee.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				if (ee.getCause().getCause().getMessage().contains("null")) {
					throw new Exception("geral.camponulo");
				}
				throw new Exception("registro.duplicado");
			}
			throw new Exception("geral.erroBancoDeDados", ee);
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new Exception("geral.erroBancoDeDados");
		}
	}

	/**
	 * Metodo responsavel por todas as alteracoes do sistema.
	 */
	public void alterar(final T objeto) throws Exception {
		try {
			entityManager.merge(objeto);
			entityManager.flush();
		} catch (OptimisticLockException le) {
			le.printStackTrace();
			throw new Exception("geral.registroAlteradoOutraSessao", le);
		} catch (PersistenceException ee) {
			ee.printStackTrace();
			if (ee.getCause() instanceof GenericJDBCException) {
				SQLException sqle = ((GenericJDBCException) ee.getCause()).getSQLException();
				facesMessages.add(Severity.ERROR, sqle.getMessage());
			}
			if (ee.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				if (ee.getCause().getCause().getMessage().contains("null")) {
					throw new Exception("geral.campoNulo");
				}
				throw new Exception("registro.duplicado");
			}
			throw new Exception("geral.erroBancoDeDados", ee);
		} catch (RuntimeException re) {
			re.printStackTrace();
			facesMessages.add(Severity.ERROR, re.getMessage());
			throw new Exception("geral.erroBancoDeDados");
		}
	}

	/**
	 * Metodo responsavel por todas as exclusoes do sistema.
	 */
	public void excluir(final T objeto) throws Exception {
		try {
			entityManager.remove(objeto);
			entityManager.flush();
		} catch (RuntimeException re) {
			re.printStackTrace();
			facesMessages.add(Severity.ERROR, re.getMessage());
			throw new Exception("geral.erroBancoDeDados");
		}
	}

	/**
	 * Metodo responsavel por atualizar o objeto
	 */
	public void recarregar(T objeto) {
		Session session = (Session) entityManager.getDelegate();
		if (session.contains(objeto)) {
			session.refresh(objeto);
		}
	}

	/**
	 * Metodo responsavel por todas as exclusoes lógicas do sistema.
	 */
	public void excluirLogico(final T objeto) throws Exception {
		try {
			alterar(objeto);
		} catch (RuntimeException re) {
			re.printStackTrace();
			facesMessages.add(Severity.ERROR, re.getMessage());
			throw new Exception("geral.erroBancoDeDados");
		}
	}

	/**
	 * Método que obtém a lista de objetos paginados
	 */
	@SuppressWarnings("unchecked")
	protected List<T> listarPaginado(Query query, Query count) {
		try {
			obterPaginaAtual();
			if ((totalRegistros == null || totalRegistros == 0L)) {
				long t = obterTotalRegistros(count);
				totalRegistros = t;

				double total = totalRegistros;
				qtdePaginas = (long) Math.ceil(total / MAXIMO_RESULTADOS);
				query.setFirstResult(paginaAtual == null || paginaAtual == 1 ? 0 : (paginaAtual - 1) * MAXIMO_RESULTADOS);
				query.setMaxResults(MAXIMO_RESULTADOS);

				tamanhoPaginacao = new Integer[qtdePaginas.intValue()];

				for (int i = 0; i < tamanhoPaginacao.length; i++) {
					tamanhoPaginacao[i] = i;
				}
			}
			paginaAtualInput = null;

			return query.getResultList();

		} catch (RuntimeException re) {
			re.printStackTrace();
			facesMessages.add(Severity.ERROR, re.getMessage());
			facesMessages.addFromResourceBundle(Severity.FATAL, "geral.erroBancoDeDados");
		}
		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings("unchecked")
	protected List<T> listarPaginado(Criteria criteria, Criteria criteriaCount, String campoOrdenacao) {
		try {
			obterPaginaAtual();
			if ((totalRegistros == null || totalRegistros == 0L)) {
				long count = obterTotalRegistros(criteriaCount);
				totalRegistros = count;

				double total = totalRegistros;
				qtdePaginas = (long) Math.ceil(total / MAXIMO_RESULTADOS);
				criteria.setFirstResult(paginaAtual == null || paginaAtual == 1 ? 0 : (paginaAtual - 1) * MAXIMO_RESULTADOS);
				criteria.setMaxResults(MAXIMO_RESULTADOS);

				tamanhoPaginacao = new Integer[qtdePaginas.intValue()];

				for (int i = 0; i < tamanhoPaginacao.length; i++) {
					tamanhoPaginacao[i] = i;
				}
			}

			if (Validador.isStringValida(campoOrdenacao)) {
				criteria.addOrder(Order.asc(campoOrdenacao));
			}
			paginaAtualInput = null;
			return criteria.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			facesMessages.add(Severity.ERROR, re.getMessage());
			facesMessages.addFromResourceBundle(Severity.FATAL, "geral.erroBancoDeDados");
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * Localiza a quantidade de registros utilizados pela paginação.
	 */
	public Long obterTotalRegistros(Criteria criteria) {
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	/**
	 * Localiza a quantidade de registros utilizados pela paginação.
	 */
	public Long obterTotalRegistros(Query count) {

		return (Long) count.getSingleResult();
	}

	/**
	 * Localiza a quantidade de registros utilizados pela paginação.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long obterTotalRegistros() {

		Class clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

		StringBuilder sbQuery = new StringBuilder();
		sbQuery.append("SELECT COUNT(entidade) ");
		sbQuery.append("FROM ");
		sbQuery.append(clazz.getName());
		sbQuery.append(" entidade ");

		Query query = entityManager.createQuery(sbQuery.toString());

		return (Long) query.getSingleResult();
	}

	/**
	 * Utilizado para listas os objetos não paginados.
	 */
	@SuppressWarnings("unchecked")
	public List<T> listar() {
		try {
			StringBuilder sbQuery = montaQueryListar();

			Query query = entityManager.createQuery(sbQuery.toString());
			return query.getResultList();

		} catch (RuntimeException re) {
			re.printStackTrace();
			facesMessages.addFromResourceBundle(Severity.FATAL, "geral.erroBancoDeDados");
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * Monta a query para listar objetos baseado na entidade.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private StringBuilder montaQueryListar() {
		Class clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		StringBuilder sbQuery = new StringBuilder();
		sbQuery.append("FROM ");
		sbQuery.append(clazz.getName());
		sbQuery.append(" entidade ");
		return sbQuery;
	}

	/**
	 * Utilizado para localizar uma entidade baseado no seu respectivo ID.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T localizar(Serializable id) {
		try {
			Class clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			return (T) entityManager.find(clazz, id);

		} catch (RuntimeException re) {
			re.printStackTrace();
			facesMessages.add(Severity.ERROR, re.getMessage());
			facesMessages.addFromResourceBundle(Severity.FATAL, "geral.erroBancoDeDados");
		}
		return null;
	}

	public Integer getPosicaoInicio() {
		if (Validador.isNumericoValido(paginaAtual)) {
			return ((paginaAtual * MAXIMO_RESULTADOS) - MAXIMO_RESULTADOS + 1);
		}
		return 1;
	}

	public Integer getPosicaoFim() {
		if (Validador.isNumericoValido(paginaAtual)) {
			if (paginaAtual != 1) {
				Integer retorno = 0;
				retorno = paginaAtual * MAXIMO_RESULTADOS;
				if (Validador.isNumericoValido(totalRegistros) && retorno > totalRegistros.intValue()) {
					return totalRegistros.intValue();
				}
				return retorno;
			} else {
				return paginaAtual * MAXIMO_RESULTADOS;
			}
		}
		return 1;
	}

	public void selecionarPagina() {
		if (paginaAtual > qtdePaginas) {
			setPaginaAtualInput(qtdePaginas.intValue());
			paginaAtual = qtdePaginas.intValue();
		}
		if (paginaAtual < 1) {
			setPaginaAtualInput(1);
			paginaAtual = 1;
		}
		setPaginaAtualInput(paginaAtual);
	}

	public void detach(Object object) {
		entityManager.detach(object);
	}

}