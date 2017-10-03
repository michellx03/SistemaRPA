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

import acessorestrito.angularrestspringsecurity.dao.referencia.ReferenciaDao;
import acessorestrito.angularrestspringsecurity.entity.Referencia;

@Component
@Path("/referencia")
public class ReferenciaResource {
	
	// @Consumes define o que ele consumirá (parametro)
		// @Produces define que tipo de conteúdo esse método retornará (return)

		private EntityManager entityManager;

		@PersistenceContext
		public void setEntityManager(final EntityManager entityManager) {
			this.entityManager = entityManager;
		}

		@Autowired
		private ReferenciaDao referenciaDaoInterface;

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<Referencia> list() {
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

			String query = "SELECT r FROM Referencia r";
			List<Referencia> referencias = entityManager.createQuery(query, Referencia.class).getResultList();
			entityManager.close();
			List<Referencia> referenciabusca = new ArrayList<>();
			for (Referencia referencia : referencias) {
				referenciabusca.add(referencia);
			}
			return referenciabusca;
		}

		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Referencia Cadastrar(Referencia referencia) {

			return referenciaDaoInterface.save(referencia);

		}

		@PUT
		@Produces(MediaType.APPLICATION_JSON)
		public Referencia Alterar(Referencia referencia) {
			return referenciaDaoInterface.save(referencia);

		}

		@DELETE
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public void Deletar(@QueryParam("id") Integer id) {

			referenciaDaoInterface.delete(id);

		}

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("/obterDados")
		public selectReferencia obterDados(@QueryParam("id") Integer id) {
			
			String select = "SELECT refe_id, refe_ano, refe_referencia, refe_status, ano_id, ano_ano, stat_id, stat_status FROM sistema.referencia "
					+ "INNER JOIN sistema.ano ON refe_ano = ano_id "
					+ "INNER JOIN sistema.status ON refe_status = stat_id "
					+ "WHERE refe_id =" + id + "";
			Query query = entityManager.createNativeQuery(select);
			List<Object[]> list = query.getResultList();
			
			selectReferencia referencia = new selectReferencia();
			
			for (Object[] obj : list) {
				referencia.setRefeId(Integer.parseInt(obj[0].toString()));
				referencia.setRefeAno(Integer.parseInt(obj[1].toString()));
				referencia.setRefeReferencia(obj[2].toString());
				referencia.setRefeStatus(Integer.parseInt(obj[3].toString()));
				referencia.setAnoId(Integer.parseInt(obj[4].toString()));
				referencia.setAnoAno(obj[5].toString());
				referencia.setStatId(Integer.parseInt(obj[6].toString()));
				referencia.setStatStatus(obj[7].toString());
					
			}

			return referencia;

		}

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("/obterDadosRelatorio")
		public List<Referencia> obterDadosRelatorio(@QueryParam("referencia") String referencia) {
			String select = "SELECT r FROM Referencia r";
			select += referencia.equals("undefined") ? "" : " WHERE a.referenciaReferencia =:referencia";
			System.out.println(select);
			Query query = (Query) entityManager.createQuery(select);
			if (!referencia.equals("undefined")) {
				((javax.persistence.Query) query).setParameter("referencia", referencia);

			}

			return ((javax.persistence.Query) query).getResultList();

		}

}

class selectReferencia{
	
	private Integer refeId;
	private Integer refeAno;
	private String refeReferencia;
	private Integer refeStatus;
	private Integer anoId;
	private String anoAno;
	private Integer statId;
	private String statStatus;
	
	public Integer getRefeId() {
		return refeId;
	}
	public void setRefeId(Integer refeId) {
		this.refeId = refeId;
	}
	public Integer getRefeAno() {
		return refeAno;
	}
	public void setRefeAno(Integer refeAno) {
		this.refeAno = refeAno;
	}
	public String getRefeReferencia() {
		return refeReferencia;
	}
	public void setRefeReferencia(String refeReferencia) {
		this.refeReferencia = refeReferencia;
	}
	public Integer getRefeStatus() {
		return refeStatus;
	}
	public void setRefeStatus(Integer refeStatus) {
		this.refeStatus = refeStatus;
	}
	public Integer getAnoId() {
		return anoId;
	}
	public void setAnoId(Integer anoId) {
		this.anoId = anoId;
	}
	public String getAnoAno() {
		return anoAno;
	}
	public void setAnoAno(String anoAno) {
		this.anoAno = anoAno;
	}
	public Integer getStatId() {
		return statId;
	}
	public void setStatId(Integer statId) {
		this.statId = statId;
	}
	public String getStatStatus() {
		return statStatus;
	}
	public void setStatStatus(String statStatus) {
		this.statStatus = statStatus;
	}
	
}
