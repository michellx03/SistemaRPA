package acessorestrito.angularrestspringsecurity.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import acessorestrito.angularrestspringsecurity.dao.tipo_atendimento.TipoAtendimentoDao;
import acessorestrito.angularrestspringsecurity.entity.StatusPagamento;
import acessorestrito.angularrestspringsecurity.entity.TipoAtendimento;

@Component
@Path("/tipo_atendimento")
public class TipoAtendimentoResource {
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private TipoAtendimentoDao tipoAtendimentoDaoInterface;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TipoAtendimento> list() {
		
		String query = "SELECT t FROM TipoAtendimento t";
		List<TipoAtendimento> tipoAtendimentos = entityManager.createQuery(query, TipoAtendimento.class).getResultList();
		entityManager.close();
		List<TipoAtendimento> tipoAtendimentoBusca = new ArrayList<>();
		
		for (TipoAtendimento tipoAtendimento : tipoAtendimentos) {
			tipoAtendimentoBusca.add(tipoAtendimento);
		}
		
		return tipoAtendimentoBusca;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public TipoAtendimento Alterar(TipoAtendimento tipoAtendimento) {
		return tipoAtendimentoDaoInterface.save(tipoAtendimento);

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obterDados")
	public TipoAtendimento obterDados(@QueryParam("id") Integer id) {

		return tipoAtendimentoDaoInterface.find(id);

	}
}
