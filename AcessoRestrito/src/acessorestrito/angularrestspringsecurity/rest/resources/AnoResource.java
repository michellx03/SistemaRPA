package acessorestrito.angularrestspringsecurity.rest.resources;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import Uteis.Conversores;
import acessorestrito.angularrestspringsecurity.JsonViews;
import acessorestrito.angularrestspringsecurity.dao.JpaDao;
import acessorestrito.angularrestspringsecurity.dao.ano.AnoDao;
import acessorestrito.angularrestspringsecurity.entity.AccessToken;
import acessorestrito.angularrestspringsecurity.entity.Ano;
import javassist.expr.NewArray;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@Path("/ano")
public class AnoResource {

	// @Consumes define o que ele consumirá (parametro)
	// @Produces define que tipo de conteúdo esse método retornará (return)

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private AnoDao anoDaoInterface;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ano> list() {
		// Native Query
		/*
		 * 
		 * List<Ano> customers = (List<Ano>)entityManager.createNativeQuery
		 * ("SELECT * FROM sistema.ano where ano_id = 3 OR ano_id=4 OR ano_id = 2"
		 * , Ano.class).getResultList(); Iterator i = customers.iterator();
		 * List<Ano> anobusca = new ArrayList<>(); Ano year; while (i.hasNext())
		 * { year = (Ano)i.next(); anobusca.add(year); }
		 */

		// JPQL

		String query = "SELECT a from Ano a";
		List<Ano> anos = entityManager.createQuery(query, Ano.class).getResultList();
		entityManager.close();
		List<Ano> anobusca = new ArrayList<>();
		for (Ano ano : anos) {
			anobusca.add(ano);
		}
		return anobusca;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Ano Cadastrar(Ano ano) {

		return anoDaoInterface.save(ano);

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Ano Alterar(Ano ano) {
		return anoDaoInterface.save(ano);

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void Deletar(@QueryParam("id") Integer id) {

		anoDaoInterface.delete(id);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obterDados")
	public Ano obterDados(@QueryParam("id") Integer id) {

		return anoDaoInterface.find(id);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obterDadosRelatorio")
	public List<Ano> obterDadosRelatorio(@QueryParam("ano") String ano) {
		String select = "SELECT a FROM Ano a";
		select += ano.equals("undefined") ? "" : " WHERE a.anoAno =:ano";
		System.out.println(select);
		Query query = (Query) entityManager.createQuery(select);
		if (!ano.equals("undefined")) {
			((javax.persistence.Query) query).setParameter("ano", ano);

		}

		return ((javax.persistence.Query) query).getResultList();

	}
}
