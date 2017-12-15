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

import acessorestrito.angularrestspringsecurity.dao.medicos.MedicosDao;
import acessorestrito.angularrestspringsecurity.entity.Medico;

@Component
@Path("/medicos")
public class MedicosResource {
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private MedicosDao medicosDaoInterface;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Medico> list() {

		String query = "SELECT m FROM Medico m";
		List<Medico> medicos = entityManager.createQuery(query, Medico.class).getResultList();
		entityManager.close();
		List<Medico> medicobusca = new ArrayList<>();
		for (Medico medico : medicos) {
			medicobusca.add(medico);
		}
		return medicobusca;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Medico Cadastrar(Medico medico) {

		return medicosDaoInterface.save(medico);

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Medico Alterar(Medico medico) {
		return medicosDaoInterface.save(medico);

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void Deletar(@QueryParam("id") Integer id) {

		medicosDaoInterface.delete(id);

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obterDados")
	public selectMedicos obterDados(@QueryParam("id") Integer id) {
		
		String select = "SELECT medi_id, medi_cpf, medi_crm, medi_desc_id, medi_especialidade, medi_nome, desc_id, desc_convenio, desc_valor FROM sistema.medicos "
				+ "INNER JOIN sistema.descontos ON medi_desc_id = desc_id "
				+ "WHERE medi_id =" + id + "";
		
		Query query = entityManager.createNativeQuery(select);
		List<Object[]> list = query.getResultList();
		
		selectMedicos descontos = new selectMedicos();
		
		for (Object[] obj : list) {
			
			descontos.setMediId(Integer.parseInt(obj[0].toString()));
			descontos.setMediCpf(obj[1].toString());
			descontos.setMediCrm(Integer.parseInt(obj[2].toString()));
			descontos.setMediDescId(Integer.parseInt(obj[3].toString()));
			descontos.setMediEspecialidade(obj[4].toString());
			descontos.setMediNome(obj[5].toString());
			descontos.setDescId(Integer.parseInt(obj[6].toString()));
			descontos.setDescConvenio(obj[7].toString());
			descontos.setDescValor(Integer.parseInt(obj[8].toString()));
				
		}

		return descontos;

	}
}

class selectMedicos {
	private Integer mediId;
	private String mediCpf;
	private Integer mediCrm;
	private Integer mediDescId;
	private String mediEspecialidade;
	private String mediNome;
	private Integer descId;
	private String descConvenio;
	private Integer descValor;
	
	public Integer getMediId() {
		return mediId;
	}
	public void setMediId(Integer mediId) {
		this.mediId = mediId;
	}
	public String getMediCpf() {
		return mediCpf;
	}
	public void setMediCpf(String mediCpf) {
		this.mediCpf = mediCpf;
	}
	public Integer getMediCrm() {
		return mediCrm;
	}
	public void setMediCrm(Integer mediCrm) {
		this.mediCrm = mediCrm;
	}
	public Integer getMediDescId() {
		return mediDescId;
	}
	public void setMediDescId(Integer mediDescId) {
		this.mediDescId = mediDescId;
	}
	public String getMediEspecialidade() {
		return mediEspecialidade;
	}
	public void setMediEspecialidade(String mediEspecialidade) {
		this.mediEspecialidade = mediEspecialidade;
	}
	public String getMediNome() {
		return mediNome;
	}
	public void setMediNome(String mediNome) {
		this.mediNome = mediNome;
	}
	public Integer getDescId() {
		return descId;
	}
	public void setDescId(Integer descId) {
		this.descId = descId;
	}
	public String getDescConvenio() {
		return descConvenio;
	}
	public void setDescConvenio(String descConvenio) {
		this.descConvenio = descConvenio;
	}
	public Integer getDescValor() {
		return descValor;
	}
	public void setDescValor(Integer descValor) {
		this.descValor = descValor;
	}
}