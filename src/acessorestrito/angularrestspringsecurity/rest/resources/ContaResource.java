package acessorestrito.angularrestspringsecurity.rest.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.*;
import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acessorestrito.angularrestspringsecurity.dao.ano.AnoDao;
import acessorestrito.angularrestspringsecurity.dao.conta.ContaDao;
import acessorestrito.angularrestspringsecurity.entity.Ano;
import acessorestrito.angularrestspringsecurity.entity.Conta;

@Component
@Path("/conta")
public class ContaResource {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private ContaDao contaDaoInterface;
	
	 private static final String fileName = "C:/Users/Michell/Documents/Atendimento.xls";
	 
	 @GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<Conta> list() {
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

			String query = "SELECT c from Conta c";
			List<Conta> contas = entityManager.createQuery(query, Conta.class).getResultList();
			entityManager.close();
			List<Conta> contabusca = new ArrayList<>();
			for (Conta conta : contas) {
				contabusca.add(conta);
			}
			return contabusca;
		}
	 
	 @GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/obterDados")
		public List<selectConta> obterDados() throws ParseException {
		 
		 String select = "SELECT conta_id, conta_convenio, conta_data_atendimento, conta_medico, conta_numero_guia, conta_paciente, conta_refe_id, conta_stre_id, conta_tipo_atendimento, stre_status, refe_referencia FROM sistema.conta "
					+ "INNER JOIN sistema.status_recebimento ON conta_stre_id = stre_id "
					+ "INNER JOIN sistema.referencia ON conta_refe_id = refe_id ";
		 
		 Query query = entityManager.createNativeQuery(select);
		 List<Object[]> list = query.getResultList();
		 
		
		 
		 List<selectConta> retornoDaLista = new ArrayList<>();
		 
		 for (Object[] obj : list) {
			 selectConta conta = new selectConta();
			 
			 conta.setContaId(Integer.parseInt(obj[0].toString()));
			 conta.setContaConvenio(obj[1].toString());
			 
			 //SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			 //Date data = formato.parse(obj[2].toString());
			 
			 conta.setContaDataAtendimento(obj[2].toString()==null?"":obj[2].toString().substring(8)+"-"+obj[2].toString().substring(5,7)+"-"+obj[2].toString().substring(0,4));
			 conta.setContaMedico(obj[3].toString());
			 conta.setContaNumeroGuia(Double.parseDouble(obj[4].toString()));
			 conta.setContaPaciente(obj[5].toString());
			 conta.setContaRefeId(Integer.parseInt(obj[6].toString()));
			 conta.setContaStreId(Integer.parseInt(obj[7].toString()));
			 conta.setContaTipoAtendimento(obj[8].toString());
			 conta.setStreStatus(obj[9].toString());
			 conta.setRefeReferencia(obj[10].toString());
			 retornoDaLista.add(conta);
					
			}

			return retornoDaLista;
			
			
	 }
		
		@PUT
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("/alterarConta")
		public Conta AlterarConta(@QueryParam("id") Integer id, @QueryParam("dataAtendimento") String dataAtendimento, Conta conta) throws ParseException {

			Conta contas = new Conta();
			
			contas = contaDaoInterface.find(id);
			
			contas.setContaPaciente(conta.getContaPaciente());
			contas.setContaConvenio(conta.getContaConvenio());
			contas.setContaTipoAtendimento(conta.getContaTipoAtendimento());
			contas.setContaMedico(conta.getContaMedico());
			contas.setContaRefeId(conta.getContaRefeId());
			
			String dataEmUmFormato = dataAtendimento;
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			Date data = formato.parse(dataEmUmFormato);
			formato.applyPattern("yyyy-MM-dd");
			String dataFormatada = formato.format(data);
			Timestamp timestampNascimento = new java.sql.Timestamp(data.getTime());
			
			contas.setContaDataAtendimento(timestampNascimento);
			contas.setContaStreId(conta.getContaStreId());
			contas.setContaNumeroGuia(conta.getContaNumeroGuia());
			
			contaDaoInterface.save(contas);
			
			return conta;
		}
		
		 @GET
			@Produces(MediaType.APPLICATION_JSON)
			@Consumes(MediaType.APPLICATION_JSON)
			@Path("/obterDadosId")
			public selectConta obterDadosId(@QueryParam("id") Integer id) throws ParseException {
			 
			 String select = "SELECT conta_id, conta_convenio, conta_data_atendimento as text, conta_medico, conta_numero_guia, conta_paciente, conta_refe_id, conta_stre_id, conta_tipo_atendimento, stre_status, refe_referencia, stre_id FROM sistema.conta "
						+ "INNER JOIN sistema.status_recebimento ON conta_stre_id = stre_id "
						+ "INNER JOIN sistema.referencia ON conta_refe_id = refe_id "
						+ "WHERE conta_id ="+id;
			 
			 Query query = entityManager.createNativeQuery(select);
			 List<Object[]> list = query.getResultList();
			 
			
			 selectConta conta = new selectConta();
			 
			 for (Object[] obj : list) {
				 
				 conta.setContaId(Integer.parseInt(obj[0].toString()));
				 conta.setContaConvenio(obj[1].toString());
				 
				 //SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				 //Date data = formato.parse(obj[2].toString()==null?"":obj[2].toString().substring(8)+"-"+obj[2].toString().substring(5,7)+"-"+obj[2].toString().substring(0,4));
				 
				 conta.setContaDataAtendimento(obj[2].toString()==null?"":obj[2].toString().substring(8)+"-"+obj[2].toString().substring(5,7)+"-"+obj[2].toString().substring(0,4));
				 conta.setContaMedico(obj[3].toString());
				 conta.setContaNumeroGuia(Double.parseDouble(obj[4].toString()));
				 conta.setContaPaciente(obj[5].toString());
				 conta.setContaRefeId(Integer.parseInt(obj[6].toString()));
				 conta.setContaStreId(Integer.parseInt(obj[7].toString()));
				 conta.setContaTipoAtendimento(obj[8].toString());
				 conta.setStreStatus(obj[9].toString());
				 conta.setRefeReferencia(obj[10].toString());
				 conta.setStreId(Integer.parseInt(obj[11].toString()));
						
				}

				return conta;	
		 }
	
	@POST
	@Path("lerArquivo")
	@Produces(MediaType.APPLICATION_JSON)
	public void lerExcel() throws IOException {
		
		 List<Conta> listaContas = new ArrayList<Conta>();
		   
         try {
                FileInputStream arquivo = new FileInputStream(new File(
                              ContaResource.fileName));

                HSSFWorkbook workbook = new HSSFWorkbook(arquivo);

                HSSFSheet sheetAlunos = workbook.getSheetAt(0);

                Iterator<Row> rowIterator = sheetAlunos.iterator();

                while (rowIterator.hasNext()) {
                       Row row = rowIterator.next();
                       Iterator<Cell> cellIterator = row.cellIterator();

                      Conta conta = new Conta();
                       listaContas.add(conta);
                       while (cellIterator.hasNext()) {
                              Cell cell = cellIterator.next();
                              switch (cell.getColumnIndex()) {
                              case 0:
                                    conta.setContaPaciente(cell.getStringCellValue());
                                    break;
                              case 1:
                                    conta.setContaConvenio(cell.getStringCellValue());
                                    break;
                              case 2:
                            	  	conta.setContaNumeroGuia(cell.getNumericCellValue());
                                    break;
                              case 3:
                            	  	conta.setContaTipoAtendimento(cell.getStringCellValue());
                                    break;
                              case 4:
                            	  	conta.setContaDataAtendimento(cell.getDateCellValue());
                                    break;
                              case 5:
                            	  	conta.setContaMedico(cell.getStringCellValue());
                                    break;     
                              }
                              
                       }
                       conta.setContaRefeId(1);
                       conta.setContaStreId(2);
                       contaDaoInterface.save(conta);
                }
                
                arquivo.close();

         } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Arquivo Excel não encontrado!");
         }

         if (listaContas.size() == 0) {
                System.out.println("Nenhuma conta encontrado!");
         } else {
              System.out.println("Foi");
         }

   }

}	

class selectConta {
	
	private Integer contaId;
	private String contaConvenio;
	private String contaDataAtendimento;
	private String contaMedico;
	private Double contaNumeroGuia;
	private String contaPaciente;
	private Integer contaRefeId;
	private Integer contaStreId;
	private String contaTipoAtendimento;
	private Integer streId;
	private String streStatus;
	private String refeReferencia;
	
	public Integer getContaId() {
		return contaId;
	}
	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}
	public String getContaConvenio() {
		return contaConvenio;
	}
	public void setContaConvenio(String contaConvenio) {
		this.contaConvenio = contaConvenio;
	}
	public String getContaDataAtendimento() {
		return contaDataAtendimento;
	}
	public void setContaDataAtendimento(String contaDataAtendimento) {
		this.contaDataAtendimento = contaDataAtendimento;
	}
	public String getContaMedico() {
		return contaMedico;
	}
	public void setContaMedico(String contaMedico) {
		this.contaMedico = contaMedico;
	}
	public Double getContaNumeroGuia() {
		return contaNumeroGuia;
	}
	public void setContaNumeroGuia(Double contaNumeroGuia) {
		this.contaNumeroGuia = contaNumeroGuia;
	}
	public String getContaPaciente() {
		return contaPaciente;
	}
	public void setContaPaciente(String contaPaciente) {
		this.contaPaciente = contaPaciente;
	}
	public Integer getContaRefeId() {
		return contaRefeId;
	}
	public void setContaRefeId(Integer contaRefeId) {
		this.contaRefeId = contaRefeId;
	}
	public Integer getContaStreId() {
		return contaStreId;
	}
	public void setContaStreId(Integer contaStreId) {
		this.contaStreId = contaStreId;
	}
	public String getContaTipoAtendimento() {
		return contaTipoAtendimento;
	}
	public void setContaTipoAtendimento(String contaTipoAtendimento) {
		this.contaTipoAtendimento = contaTipoAtendimento;
	}
	public String getRefeReferencia() {
		return refeReferencia;
	}
	public void setRefeReferencia(String refeReferencia) {
		this.refeReferencia = refeReferencia;
	}
	public Integer getStreId() {
		return streId;
	}
	public void setStreId(Integer streId) {
		this.streId = streId;
	}
	public String getStreStatus() {
		return streStatus;
	}
	public void setStreStatus(String streStatus) {
		this.streStatus = streStatus;
	}

}
