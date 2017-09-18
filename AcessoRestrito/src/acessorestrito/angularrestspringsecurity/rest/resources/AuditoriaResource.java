package acessorestrito.angularrestspringsecurity.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acessorestrito.angularrestspringsecurity.dao.ano.AnoDao;
import acessorestrito.angularrestspringsecurity.dao.auditoria.*;
import acessorestrito.angularrestspringsecurity.entity.Ano;
import acessorestrito.angularrestspringsecurity.entity.Auditoria;
import acessorestrito.angularrestspringsecurity.entity.Role;
import acessorestrito.angularrestspringsecurity.entity.Usuario;

@Component
@Path("/auditoria")
public class AuditoriaResource {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private AuditoriaDao auditoriaDaoInterface;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void Cadastrar(@QueryParam("user") String user, @QueryParam("url") String url) {

		Auditoria auditoria = new Auditoria();
		String query = "SELECT u from Usuario u WHERE u.name='" + user + "'";
		List<Usuario> usuario = entityManager.createQuery(query, Usuario.class).getResultList();
		entityManager.close();
		Long usua_id = 0L;
		for (Usuario usuario_search : usuario) {
			usua_id = usuario_search.getId();
		}
		auditoria.setAudiUrl(url);
		auditoria.setAudiUsuaId(usua_id);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		auditoria.setAudiDataHoraInicio(timestamp);
		auditoriaDaoInterface.save(auditoria);

	}

}
