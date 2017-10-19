package acessorestrito.angularrestspringsecurity.rest.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
		public List<selectConta> obterDados() {
		 
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
			 conta.setContaDataAtendimento(Integer.parseInt(obj[2].toString()));
			 conta.setContaMedico(obj[3].toString());
			 conta.setContaNumeroGuia(Double.parseDouble(obj[4].toString()));
			 conta.setContaPaciente(obj[5].toString());
			 conta.setContaRefeId(Integer.parseInt(obj[6].toString()));
			 conta.setContaStreId(Integer.parseInt(obj[7].toString()));
			 conta.setContaTipoAtendimento(obj[8].toString());
			 conta.setStpaStatus(obj[9].toString());
			 conta.setRefeReferencia(obj[10].toString());
			 retornoDaLista.add(conta);
					
			}

			return retornoDaLista;
			
			
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
                            	  	conta.setContaDataAtendimento((int) cell.getNumericCellValue());
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
	private Integer contaDataAtendimento;
	private String contaMedico;
	private Double contaNumeroGuia;
	private String contaPaciente;
	private Integer contaRefeId;
	private Integer contaStreId;
	private String contaTipoAtendimento;
	private String stpaStatus;
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
	public Integer getContaDataAtendimento() {
		return contaDataAtendimento;
	}
	public void setContaDataAtendimento(Integer contaDataAtendimento) {
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
	public String getStpaStatus() {
		return stpaStatus;
	}
	public void setStpaStatus(String stpaStatus) {
		this.stpaStatus = stpaStatus;
	}
	public String getRefeReferencia() {
		return refeReferencia;
	}
	public void setRefeReferencia(String refeReferencia) {
		this.refeReferencia = refeReferencia;
	}

}
