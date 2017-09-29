package acessorestrito.angularrestspringsecurity.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acessorestrito.angularrestspringsecurity.dao.ano.AnoDao;
import acessorestrito.angularrestspringsecurity.dao.status.StatusDao;
import acessorestrito.angularrestspringsecurity.entity.Ano;
import acessorestrito.angularrestspringsecurity.entity.Status;

@Component
@Path("/status")
public class StatusResource {

	// @Consumes define o que ele consumirá (parametro)
	// @Produces define que tipo de conteúdo esse método retornará (return)

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private StatusDao statusDaoInterface;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Status> list() {
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

		String query = "SELECT s from Status s";
		List<Status> status = entityManager.createQuery(query, Status.class).getResultList();
		entityManager.close();
		List<Status> statusbusca = new ArrayList<>();
		for (Status statu : status) {
			statusbusca.add(statu);
		}
		return statusbusca;
	}


	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Status Alterar(Status status) {
		return statusDaoInterface.save(status);

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obterDados")
	public Status obterDados(@QueryParam("id") Integer id) {

		return statusDaoInterface.find(id);

	}
}
