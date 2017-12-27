package acessorestrito.angularrestspringsecurity.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import acessorestrito.angularrestspringsecurity.dao.convenio.ConvenioDao;
import acessorestrito.angularrestspringsecurity.entity.Ano;
import acessorestrito.angularrestspringsecurity.entity.Convenio;

@Component
@Path("/convenio")
public class ConvenioResource {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private ConvenioDao convenioDaoInterface;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Convenio> list() {

		String query = "SELECT c FROM Convenio c";
		List<Convenio> convenios = entityManager.createQuery(query, Convenio.class).getResultList();
		entityManager.close();
		List<Convenio> conveniobusca = new ArrayList<>();
		for (Convenio convenio : convenios) {
			conveniobusca.add(convenio);
		}
		return conveniobusca;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Convenio Cadastrar(Convenio convenio) {

		return convenioDaoInterface.save(convenio);

	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Convenio Alterar(Convenio convenio) {
		return convenioDaoInterface.save(convenio);

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obterDados")
	public Convenio obterDados(@QueryParam("id") Integer id) {

		return convenioDaoInterface.find(id);

	}
	
}
