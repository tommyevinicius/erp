package br.com.siga.dominio;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("relatorio")
@Scope(ScopeType.CONVERSATION)
public class Relatorio <T> implements Serializable {
	private static final long serialVersionUID = 1L;

}