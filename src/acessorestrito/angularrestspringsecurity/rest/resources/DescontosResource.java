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

import acessorestrito.angularrestspringsecurity.dao.descontos.DescontosDao;
import acessorestrito.angularrestspringsecurity.entity.Ano;
import acessorestrito.angularrestspringsecurity.entity.Desconto;

@Component
@Path("/descontos")
public class DescontosResource {
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private DescontosDao descontosDaoInterface;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Desconto> list() {

		String query = "SELECT d FROM Desconto d";
		List<Desconto> descontos = entityManager.createQuery(query, Desconto.class).getResultList();
		entityManager.close();
		List<Desconto> descontobusca = new ArrayList<>();
		for (Desconto desconto : descontos) {
			descontobusca.add(desconto);
		}
		return descontobusca;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Desconto Cadastrar(Desconto desconto) {

		return descontosDaoInterface.save(desconto);

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Desconto Alterar(Desconto desconto) {
		return descontosDaoInterface.save(desconto);

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void Deletar(@QueryParam("id") Integer id) {

		descontosDaoInterface.delete(id);

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obterDados")
	public Desconto obterDados(@QueryParam("id") Integer id) {

		return descontosDaoInterface.find(id);

	}

}
