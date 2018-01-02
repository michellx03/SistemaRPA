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

import acessorestrito.angularrestspringsecurity.dao.conta_demonstrativo.ContaDemonstrativoDao;
import acessorestrito.angularrestspringsecurity.entity.Ano;
import acessorestrito.angularrestspringsecurity.entity.Conta;
import acessorestrito.angularrestspringsecurity.entity.ContaDemonstrativo;
import acessorestrito.angularrestspringsecurity.entity.ContaFaturamento;
import acessorestrito.angularrestspringsecurity.entity.ProcedimentosIpasgo;

@Component
@Path("/conta_demonstrativo")
public class ContaDemonstrativoResource {
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private ContaDemonstrativoDao contaDemonstrativoDaoInterface;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContaDemonstrativo> list() {

		String query = "SELECT c FROM ContaDemonstrativo c";
		List<ContaDemonstrativo> contaDemonstrativos = entityManager.createQuery(query, ContaDemonstrativo.class).getResultList();
		entityManager.close();
		List<ContaDemonstrativo> contaDemonstrativobusca = new ArrayList<>();
		for (ContaDemonstrativo contaDemonstrativo : contaDemonstrativos) {
			contaDemonstrativobusca.add(contaDemonstrativo);
		}
		return contaDemonstrativobusca;
	}
	
	private static final String fileName = "C:/Users/Michell/Documents/DemonstrativoIpasgo.xls";

	@POST
	@Path("lerArquivoDemonstrativo")
	@Produces(MediaType.APPLICATION_JSON)
	public void lerExcel() throws IOException {
		
		 List<ContaDemonstrativo> listaContas = new ArrayList<ContaDemonstrativo>();
		   
         try {
                FileInputStream arquivo = new FileInputStream(new File(
                		ContaDemonstrativoResource.fileName));

                HSSFWorkbook workbook = new HSSFWorkbook(arquivo);

                HSSFSheet sheetAlunos = workbook.getSheetAt(0);

                Iterator<Row> rowIterator = sheetAlunos.iterator();

                while (rowIterator.hasNext()) {
                       Row row = rowIterator.next();
                       Iterator<Cell> cellIterator = row.cellIterator();

                       ContaDemonstrativo conta = new ContaDemonstrativo();
                       ProcedimentosIpasgo procedimento = new ProcedimentosIpasgo();
                       
                       listaContas.add(conta);
                       while (cellIterator.hasNext()) {
                              Cell cell = cellIterator.next();
                              switch (cell.getColumnIndex()) {
                              case 0:
                            	  	conta.setCodeDataAtendimento(cell.getDateCellValue());
                                    break;
                              case 1:
                                    conta.setCodeCarteira((int) cell.getNumericCellValue());
                                    break;
                              case 2:
                                    conta.setCodePaciente(cell.getStringCellValue());
                                    break;
                              case 3:
                            	  	conta.setCodeNumeroGuia(cell.getNumericCellValue());
                                    break;
                              case 4:
                            	  	conta.setCodeCodigo((int) cell.getNumericCellValue()); 
                                    break;
                              case 5:
                                    conta.setCodeValor(cell.getNumericCellValue());
                                    break;     
                              }
                              
                       }
                       
                       	String select = "SELECT prip_codigo FROM sistema.procedimentos_ipasgo";
             			
                       	Query query = entityManager.createNativeQuery(select);
                		List<Object[]> list = query.getResultList();;
             	
             			for (Object resolucao: list) {
             			   
             			  if(conta.getCodeCodigo().equals(resolucao)){
             				  
             				String idSelect = "SELECT prip_id FROM sistema.procedimentos_ipasgo WHERE prip_codigo="+resolucao;
                  			
             				Query queryId = entityManager.createNativeQuery(idSelect);
             				Object id = queryId.getSingleResult();
             				
                     		conta.setCodePripCodigo((Integer) id);
             				 
                           }
             			}
               	 	 	
                       conta.setCodeRefeId(1);
                       conta.setCodeConvenio("Ipasgo");
                       contaDemonstrativoDaoInterface.save(conta);
                }
                
                arquivo.close();

         } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Arquivo Excel não encontrado!");
         }

         if (listaContas.size() == 0) {
                System.out.println("Nenhuma conta encontrado!");
         } else {
              System.out.println("Importado com sucesso!");
         }

   }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/obterDadosId")
	public selectContaDemonstrativo obterDadosId(@QueryParam("id") Integer id) throws ParseException {

		String select = "SELECT code_id, code_paciente, code_numero_guia, code_convenio, code_refe_id, code_data_atendimento, refe_referencia, lpad(cast(code_codigo as varchar),8,'0'), code_carteira, code_valor, code_prip_codigo, prip_descricao, prip_codigo FROM sistema.conta_demonstrativo "
				+ "INNER JOIN sistema.referencia ON code_refe_id = refe_id " + "INNER JOIN sistema.procedimentos_ipasgo ON code_prip_codigo = prip_id " + "WHERE code_id =" + id;

		Query query = entityManager.createNativeQuery(select);
		List<Object[]> list = query.getResultList();

		selectContaDemonstrativo conta = new selectContaDemonstrativo();

		for (Object[] obj : list) {

			conta.setCode_id(Integer.parseInt(obj[0].toString()));
			 conta.setCode_paciente(obj[1].toString());
			 conta.setCode_numero_guia(Double.parseDouble(obj[2].toString()));
			 conta.setCode_convenio(obj[3].toString());
			 conta.setCode_refe_id(Integer.parseInt(obj[4].toString()));
			 conta.setCode_data_atendimento(obj[5].toString()==null?"":obj[5].toString().substring(8)+"-"+obj[5].toString().substring(5,7)+"-"+obj[5].toString().substring(0,4));
			 conta.setRefe_referencia(obj[6].toString());
			 conta.setCode_codigo(Integer.parseInt(obj[7].toString()));
			 conta.setCode_carteira(Integer.parseInt(obj[8].toString()));
			 conta.setCode_valor(Double.valueOf(obj[9].toString()));
			 conta.setCode_prip_codigo(Integer.parseInt(obj[10].toString()));
			 conta.setPrip_descricao(obj[11].toString());
			 conta.setPrip_codigo(Integer.parseInt(obj[12].toString()));
		}

		return conta;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/alterarConta")
	public ContaDemonstrativo AlterarConta(@QueryParam("id") Integer id, @QueryParam("dataAtendimento") String dataAtendimento, ContaDemonstrativo contaDemonstrativo) throws ParseException {

		ContaDemonstrativo contas = new ContaDemonstrativo();
		
		contas = contaDemonstrativoDaoInterface.find(id);
		
		contas.setCodePaciente(contaDemonstrativo.getCodePaciente());
		contas.setCodeConvenio(contaDemonstrativo.getCodeConvenio());
		contas.setCodeCarteira(contaDemonstrativo.getCodeCarteira());
		contas.setCodeValor(contaDemonstrativo.getCodeValor());
		contas.setCodeRefeId(contaDemonstrativo.getCodeRefeId());
		
		String dataEmUmFormato = dataAtendimento;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date data = formato.parse(dataEmUmFormato);
		formato.applyPattern("yyyy-MM-dd");
		String dataFormatada = formato.format(data);
		Timestamp timestampNascimento = new java.sql.Timestamp(data.getTime());
		
		contas.setCodeDataAtendimento(timestampNascimento);
		contas.setCodeNumeroGuia(contaDemonstrativo.getCodeNumeroGuia());
		contas.setCodeCodigo(contaDemonstrativo.getCodeCodigo());
		contas.setCodePripCodigo(contaDemonstrativo.getCodePripCodigo());
		
		contaDemonstrativoDaoInterface.save(contas);
		
		return contaDemonstrativo;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/obterDados")
	public List<selectContaDemonstrativo> obterDados() throws ParseException {
	 
	 String select = "SELECT code_id, code_paciente, code_numero_guia, code_convenio, code_refe_id, code_data_atendimento, refe_referencia, code_codigo, code_carteira, code_valor, code_prip_codigo, prip_descricao FROM sistema.conta_demonstrativo "
				+ "INNER JOIN sistema.referencia ON code_refe_id = refe_id "
				+ "INNER JOIN sistema.procedimentos_ipasgo ON code_prip_codigo = prip_id";
	 
	 Query query = entityManager.createNativeQuery(select);
	 List<Object[]> list = query.getResultList();
	 
	
	 
	 List<selectContaDemonstrativo> retornoDaLista = new ArrayList<>();
	 
	 for (Object[] obj : list) {
		 selectContaDemonstrativo conta = new selectContaDemonstrativo();
		 
		 conta.setCode_id(Integer.parseInt(obj[0].toString()));
		 conta.setCode_paciente(obj[1].toString());
		 conta.setCode_numero_guia(Double.parseDouble(obj[2].toString()));
		 conta.setCode_convenio(obj[3].toString());
		 conta.setCode_refe_id(Integer.parseInt(obj[4].toString()));
		 conta.setCode_data_atendimento(obj[5].toString()==null?"":obj[5].toString().substring(8)+"-"+obj[5].toString().substring(5,7)+"-"+obj[5].toString().substring(0,4));
		 conta.setRefe_referencia(obj[6].toString());
		 conta.setCode_codigo(Integer.valueOf(obj[7].toString()));
		 conta.setCode_carteira(Integer.parseInt(obj[8].toString()));
		 conta.setCode_valor(Double.valueOf(obj[9].toString()));
		 conta.setCode_prip_codigo(Integer.parseInt(obj[10].toString()));
		 conta.setPrip_descricao(obj[11].toString());
		 retornoDaLista.add(conta);
				
		}

		return retornoDaLista;		
		
 }

}

class selectContaDemonstrativo {
	
	private Integer code_id;
	private String code_paciente; 
	private Double code_numero_guia;
	private String code_convenio;
	private Integer code_refe_id; 
	private String code_data_atendimento;
	private String refe_referencia;
	private Integer code_codigo;
	private Integer code_carteira;
	private Double code_valor;
	private Integer code_prip_codigo;
	private String prip_descricao;
	private Integer prip_codigo; 
	
	public Integer getCode_id() {
		return code_id;
	}
	public void setCode_id(Integer code_id) {
		this.code_id = code_id;
	}
	public String getCode_paciente() {
		return code_paciente;
	}
	public void setCode_paciente(String code_paciente) {
		this.code_paciente = code_paciente;
	}
	public Double getCode_numero_guia() {
		return code_numero_guia;
	}
	public void setCode_numero_guia(Double code_numero_guia) {
		this.code_numero_guia = code_numero_guia;
	}
	public String getCode_convenio() {
		return code_convenio;
	}
	public void setCode_convenio(String code_convenio) {
		this.code_convenio = code_convenio;
	}
	public Integer getCode_refe_id() {
		return code_refe_id;
	}
	public void setCode_refe_id(Integer code_refe_id) {
		this.code_refe_id = code_refe_id;
	}
	public String getCode_data_atendimento() {
		return code_data_atendimento;
	}
	public void setCode_data_atendimento(String code_data_atendimento) {
		this.code_data_atendimento = code_data_atendimento;
	}
	public String getRefe_referencia() {
		return refe_referencia;
	}
	public void setRefe_referencia(String refe_referencia) {
		this.refe_referencia = refe_referencia;
	}
	public Integer getCode_codigo() {
		return code_codigo;
	}
	public void setCode_codigo(Integer code_codigo) {
		this.code_codigo = code_codigo;
	}
	public Integer getCode_carteira() {
		return code_carteira;
	}
	public void setCode_carteira(Integer code_carteira) {
		this.code_carteira = code_carteira;
	}
	public Double getCode_valor() {
		return code_valor;
	}
	public void setCode_valor(Double code_valor) {
		this.code_valor = code_valor;
	}
	public Integer getCode_prip_codigo() {
		return code_prip_codigo;
	}
	public void setCode_prip_codigo(Integer code_prip_codigo) {
		this.code_prip_codigo = code_prip_codigo;
	}
	public String getPrip_descricao() {
		return prip_descricao;
	}
	public void setPrip_descricao(String prip_descricao) {
		this.prip_descricao = prip_descricao;
	}
	public Integer getPrip_codigo() {
		return prip_codigo;
	}
	public void setPrip_codigo(Integer prip_codigo) {
		this.prip_codigo = prip_codigo;
	}
}