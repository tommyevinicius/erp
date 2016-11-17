package br.com.siga.negocio;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.siga.dominio.Cliente;
import br.com.siga.utils.Enumerados.Situacao;
import br.com.siga.utils.Validador;
import br.com.templates.utils.NegocioBase;

@AutoCreate
@Name("clienteNegocio")
@Scope(ScopeType.CONVERSATION)
public class ClienteNegocio extends NegocioBase<Cliente, Long>{

	@SuppressWarnings("unchecked")
	public List<Cliente> listarAtivos () {
		Criteria criteria = getSession().createCriteria(Cliente.class);
		
		criteria.add(Restrictions.eq("situacao", Situacao.ATIVO));
		criteria.addOrder(Order.asc("nome"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> pesquisar(Cliente cliente) {
		Criteria criteria = getSession().createCriteria(Cliente.class);
		if (Validador.isStringValida(cliente.getNome())) {
			criteria.add(Restrictions.like("nome", cliente.getNome(), MatchMode.ANYWHERE));
		}
		if (Validador.isStringValida(cliente.getRazaoSocial())) {
			criteria.add(Restrictions.like("razaoSocial", cliente.getRazaoSocial(), MatchMode.ANYWHERE));
		}
		if (Validador.isEnumValido(cliente.getTipoPessoa())) {
			criteria.add(Restrictions.eq("tipoPessoa", cliente.getTipoPessoa()));
		}
		if (Validador.isEnumValido(cliente.getSituacao())) {
			criteria.add(Restrictions.eq("situacao", cliente.getSituacao()));
		}
		if (Validador.isStringValida(cliente.getCnpj())) {
			criteria.add(Restrictions.like("cnpj", cliente.getCnpj(), MatchMode.ANYWHERE));
		}
		if (Validador.isStringValida(cliente.getCpf())) {
			criteria.add(Restrictions.like("cpf", cliente.getCpf(), MatchMode.ANYWHERE));
		}
		if (Validador.isStringValida(cliente.getTelefone())) {
			criteria.add(Restrictions.like("telefone", cliente.getTelefone(), MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.asc("nome"));
		return criteria.list();
	}
	
	public Cliente localizar(Cliente cliente) {
		Criteria criteria = getSession().createCriteria(Cliente.class);
		if (Validador.isStringValida(cliente.getNome())) {
			criteria.add(Restrictions.eq("nome", cliente.getNome()));
		}
		if (criteria.list().size() > 1 || !Validador.isObjetoValido(criteria.uniqueResult())) {
			return null;
		}
		
		return (Cliente) criteria.uniqueResult();
	}
}
