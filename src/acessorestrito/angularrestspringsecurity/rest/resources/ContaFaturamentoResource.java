package acessorestrito.angularrestspringsecurity.rest.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acessorestrito.angularrestspringsecurity.dao.conta_faturamento.ContaFaturamentoDao;
import acessorestrito.angularrestspringsecurity.entity.Conta;
import acessorestrito.angularrestspringsecurity.entity.ContaFaturamento;

@Component
@Path("/conta_faturamento")
public class ContaFaturamentoResource {
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private ContaFaturamentoDao contaFaturamentoDaoInterface;
	
	private static final String fileName = "C:/Users/Michell/Documents/ListagemFaturamento.xls";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContaFaturamento> list() {
		
		String query = "SELECT c FROM ContaFaturamento c";
		List<ContaFaturamento> contaFaturamentos = entityManager.createQuery(query, ContaFaturamento.class).getResultList();
		entityManager.close();
		List<ContaFaturamento> contaFaturamentobusca = new ArrayList<>();
		
		for (ContaFaturamento contaFaturamento : contaFaturamentos) {
			contaFaturamentobusca.add(contaFaturamento);
		}
		
		return contaFaturamentobusca;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/alterarConta")
	public ContaFaturamento AlterarConta(@QueryParam("id") Integer id, @QueryParam("dataAtendimento") String dataAtendimento, ContaFaturamento contaFaturamento) throws ParseException {

		ContaFaturamento contas = new ContaFaturamento();
		
		contas = contaFaturamentoDaoInterface.find(id);
		
		contas.setCofaPaciente(contaFaturamento.getCofaPaciente());
		contas.setCofaNumeroGuia(contaFaturamento.getCofaNumeroGuia());
		contas.setCofaTipoAtendimento(contaFaturamento.getCofaTipoAtendimento());
		contas.setCofaRefeId(contaFaturamento.getCofaRefeId());
		contas.setCofaMedico(contaFaturamento.getCofaMedico());
		contas.setCofaMatriculla(contaFaturamento.getCofaMatriculla());
		contas.setCofaValor(contaFaturamento.getCofaValor());
		
		String dataEmUmFormato = dataAtendimento;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date data = formato.parse(dataEmUmFormato);
		formato.applyPattern("yyyy-MM-dd");
		String dataFormatada = formato.format(data);
		Timestamp timestampNascimento = new java.sql.Timestamp(data.getTime());
		
		contas.setCofaDataAtendimento(timestampNascimento);
		
		contaFaturamentoDaoInterface.save(contas);
		
		return contaFaturamento;
	}

	 @GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/obterDados")
		public List<selectFaturamento> obterDados() throws ParseException {
		 
		 String select = "SELECT cofa_id, cofa_paciente, cofa_numero_guia, cofa_tipo_atendimento, cofa_refe_id, cofa_medico, cofa_matriculla, cofa_valor, cofa_data_atendimento, refe_referencia FROM sistema.conta_faturamento "
					+ "INNER JOIN sistema.referencia ON cofa_refe_id = refe_id ";
		 
		 Query query = entityManager.createNativeQuery(select);
		 List<Object[]> list = query.getResultList();
		 
		
		 
		 List<selectFaturamento> retornoDaLista = new ArrayList<>();
		 
		 for (Object[] obj : list) {
			 selectFaturamento conta = new selectFaturamento();
			 
			 conta.setCofa_id(Integer.parseInt(obj[0].toString()));
			 conta.setCofa_paciente(obj[1].toString());
			 conta.setCofa_numero_guia(Integer.parseInt(obj[2].toString()));
			 conta.setCofa_tipo_atendimento(obj[3].toString());
			 conta.setCofa_refe_id(Integer.parseInt(obj[4].toString()));
			 conta.setCofa_medico(Integer.parseInt(obj[5].toString()));
			 conta.setCofa_matriculla(Integer.parseInt(obj[6].toString()));
			 conta.setCofa_valor(Double.parseDouble(obj[7].toString()));
			 conta.setCofa_data_atendimento(obj[8].toString()==null?"":obj[8].toString().substring(8)+"-"+obj[8].toString().substring(5,7)+"-"+obj[8].toString().substring(0,4));
			 conta.setRefe_referencia(obj[9].toString());
			 
			 retornoDaLista.add(conta);
					
			}

			return retornoDaLista;
			
			
	 }
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("/obterDadosId")
		public selectFaturamento obterDadosId(@QueryParam("id") Integer id) throws ParseException {
		 
		 String select = "SELECT cofa_id, cofa_paciente, cofa_numero_guia, cofa_tipo_atendimento, cofa_refe_id, cofa_medico, cofa_matriculla, cofa_valor, cofa_data_atendimento, refe_referencia, refe_id FROM sistema.conta_faturamento "
					+ "INNER JOIN sistema.referencia ON cofa_refe_id = refe_id "
					+ "WHERE cofa_id ="+id;
		 
		 Query query = entityManager.createNativeQuery(select);
		 List<Object[]> list = query.getResultList();
		 
		
		 selectFaturamento conta = new selectFaturamento();
		 
		 for (Object[] obj : list) {
			 
			 conta.setCofa_id(Integer.parseInt(obj[0].toString()));
			 conta.setCofa_paciente(obj[1].toString());
			 conta.setCofa_numero_guia(Integer.parseInt(obj[2].toString()));
			 conta.setCofa_tipo_atendimento(obj[3].toString());
			 conta.setCofa_refe_id(Integer.parseInt(obj[4].toString()));
			 conta.setCofa_medico(Integer.parseInt(obj[5].toString()));
			 conta.setCofa_matriculla(Integer.parseInt(obj[6].toString()));
			 conta.setCofa_valor(Double.parseDouble(obj[7].toString()));
			 conta.setCofa_data_atendimento(obj[8].toString()==null?"":obj[8].toString().substring(8)+"-"+obj[8].toString().substring(5,7)+"-"+obj[8].toString().substring(0,4));
			 conta.setRefe_referencia(obj[9].toString());
			 conta.setRefe_id(Integer.parseInt(obj[10].toString()));
			 
			}

			return conta;	
	 }

	
	@POST
	@Path("lerArquivoFaturamento")
	@Produces(MediaType.APPLICATION_JSON)
	public void lerExcel() throws IOException {
		
		 List<ContaFaturamento> listaContas = new ArrayList<ContaFaturamento>();
		   
         try {
                FileInputStream arquivo = new FileInputStream(new File(
                              ContaFaturamentoResource.fileName));

                HSSFWorkbook workbook = new HSSFWorkbook(arquivo);

                HSSFSheet sheetAlunos = workbook.getSheetAt(0);

                Iterator<Row> rowIterator = sheetAlunos.iterator();

                while (rowIterator.hasNext()) {
                       Row row = rowIterator.next();
                       Iterator<Cell> cellIterator = row.cellIterator();

                       ContaFaturamento conta = new ContaFaturamento();
                       listaContas.add(conta);
                       while (cellIterator.hasNext()) {
                              Cell cell = cellIterator.next();
                              switch (cell.getColumnIndex()) {
                              case 0:
                                    conta.setCofaPaciente(cell.getStringCellValue());
                                    break;
                              case 1:
                                    conta.setCofaMatriculla((int) cell.getNumericCellValue());
                                    break;
                              case 2:
                                    conta.setCofaNumeroGuia((int) cell.getNumericCellValue());
                                    break;
                              case 3:
                                    conta.setCofaTipoAtendimento(cell.getStringCellValue());
                                    break;
                              case 4:
                                     conta.setCofaMedico((int) cell.getNumericCellValue());
                                    break;
                              case 5:
                                    conta.setCofaDataAtendimento(cell.getDateCellValue());
                                    break;
                              case 6:
                                  conta.setCofaValor(cell.getNumericCellValue());
                                  break;      
                              }
                              
                       }
                       conta.setCofaRefeId(1);
                       contaFaturamentoDaoInterface.save(conta);
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

class selectFaturamento {
	private Integer cofa_id;
	private String cofa_paciente;
	private Integer cofa_numero_guia;
	private String cofa_tipo_atendimento;
	private Integer cofa_refe_id;
	private Integer cofa_medico;
	private Integer cofa_matriculla;
	private Double cofa_valor;
	private String cofa_data_atendimento;
	private String refe_referencia;
	private Integer refe_id;
	
	public Integer getCofa_id() {
		return cofa_id;
	}
	public void setCofa_id(Integer cofa_id) {
		this.cofa_id = cofa_id;
	}
	public String getCofa_paciente() {
		return cofa_paciente;
	}
	public void setCofa_paciente(String cofa_paciente) {
		this.cofa_paciente = cofa_paciente;
	}
	public Integer getCofa_numero_guia() {
		return cofa_numero_guia;
	}
	public void setCofa_numero_guia(Integer cofa_numero_guia) {
		this.cofa_numero_guia = cofa_numero_guia;
	}
	public String getCofa_tipo_atendimento() {
		return cofa_tipo_atendimento;
	}
	public void setCofa_tipo_atendimento(String cofa_tipo_atendimento) {
		this.cofa_tipo_atendimento = cofa_tipo_atendimento;
	}
	public Integer getCofa_refe_id() {
		return cofa_refe_id;
	}
	public void setCofa_refe_id(Integer cofa_refe_id) {
		this.cofa_refe_id = cofa_refe_id;
	}
	public Integer getCofa_medico() {
		return cofa_medico;
	}
	public void setCofa_medico(Integer cofa_medico) {
		this.cofa_medico = cofa_medico;
	}
	public Integer getCofa_matriculla() {
		return cofa_matriculla;
	}
	public void setCofa_matriculla(Integer cofa_matriculla) {
		this.cofa_matriculla = cofa_matriculla;
	}
	public Double getCofa_valor() {
		return cofa_valor;
	}
	public void setCofa_valor(Double cofa_valor) {
		this.cofa_valor = cofa_valor;
	}
	public String getCofa_data_atendimento() {
		return cofa_data_atendimento;
	}
	public void setCofa_data_atendimento(String cofa_data_atendimento) {
		this.cofa_data_atendimento = cofa_data_atendimento;
	}
	public String getRefe_referencia() {
		return refe_referencia;
	}
	public void setRefe_referencia(String refe_referencia) {
		this.refe_referencia = refe_referencia;
	}
	public Integer getRefe_id() {
		return refe_id;
	}
	public void setRefe_id(Integer refe_id) {
		this.refe_id = refe_id;
	}
}
