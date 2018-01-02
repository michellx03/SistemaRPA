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
		contas.setCofaStatusPagamento(contaFaturamento.getCofaStatusPagamento());
		contas.setCofaDescricaoAtendimento(contaFaturamento.getCofaDescricaoAtendimento());
		
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
		 
		 String select = "SELECT cofa_id, cofa_paciente, cofa_numero_guia, cofa_tipo_atendimento, cofa_refe_id, cofa_medico, cofa_matriculla, cofa_valor, cofa_data_atendimento, refe_referencia, medi_id, medi_nome, cofa_status_pagamento, stpa_id, stpa_status, cofa_descricao_atendimento FROM sistema.conta_faturamento "
					+ "INNER JOIN sistema.referencia ON cofa_refe_id = refe_id "
					+ "INNER JOIN sistema.tipo_atendimento ON cofa_tipo_atendimento = tiat_id "
					+ "INNER JOIN sistema.medicos ON cofa_medico = medi_id "
					+ "INNER JOIN sistema.status_pagamento ON cofa_status_pagamento = stpa_id ";
		 
		 Query query = entityManager.createNativeQuery(select);
		 List<Object[]> list = query.getResultList();
		 
		
		 
		 List<selectFaturamento> retornoDaLista = new ArrayList<>();
		 
		 for (Object[] obj : list) {
			 selectFaturamento conta = new selectFaturamento();
			 
			 conta.setCofa_id(Integer.parseInt(obj[0].toString()));
			 conta.setCofa_paciente(obj[1].toString());
			 conta.setCofa_numero_guia(Integer.parseInt(obj[2].toString()));
			 conta.setCofa_tipo_atendimento(Integer.parseInt(obj[3].toString()));
			 conta.setCofa_refe_id(Integer.parseInt(obj[4].toString()));
			 conta.setCofa_medico(Integer.parseInt(obj[5].toString()));
			 conta.setCofa_matriculla(Integer.parseInt(obj[6].toString()));
			 conta.setCofa_valor(Double.parseDouble(obj[7].toString()));
			 conta.setCofa_data_atendimento(obj[8].toString()==null?"":obj[8].toString().substring(8)+"-"+obj[8].toString().substring(5,7)+"-"+obj[8].toString().substring(0,4));
			 conta.setRefe_referencia(obj[9].toString());
			 conta.setMedi_id(Integer.parseInt(obj[10].toString()));
			 conta.setMedi_nome(obj[11].toString());
			 conta.setCofa_status_pagamento(Integer.parseInt(obj[12].toString()));
			 conta.setStpa_id(Integer.parseInt(obj[13].toString()));
			 conta.setStpa_status(obj[14].toString());
			 conta.setCofa_descricao_atendimento(obj[15].toString());
			 
			 retornoDaLista.add(conta);
					
			}

			return retornoDaLista;
			
			
	 }
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("/obterDadosId")
		public selectFaturamento obterDadosId(@QueryParam("id") Integer id) throws ParseException {
		 
		 String select = "SELECT cofa_id, cofa_paciente, cofa_numero_guia, cofa_tipo_atendimento, cofa_refe_id, cofa_medico, cofa_matriculla, cofa_valor, cofa_data_atendimento, refe_referencia, refe_id, medi_id, medi_nome, cofa_status_pagamento, stpa_id, stpa_status, cofa_descricao_atendimento, tiat_tipo FROM sistema.conta_faturamento "
					+ "INNER JOIN sistema.referencia ON cofa_refe_id = refe_id "
					+ "INNER JOIN sistema.tipo_atendimento ON cofa_tipo_atendimento = tiat_id "
					+ "INNER JOIN sistema.medicos ON cofa_medico = medi_id "
					+ "INNER JOIN sistema.status_pagamento ON cofa_status_pagamento = stpa_id "
					+ "WHERE cofa_id ="+id;
		 
		 Query query = entityManager.createNativeQuery(select);
		 List<Object[]> list = query.getResultList();
		 
		
		 selectFaturamento conta = new selectFaturamento();
		 
		 for (Object[] obj : list) {
			 
			 conta.setCofa_id(Integer.parseInt(obj[0].toString()));
			 conta.setCofa_paciente(obj[1].toString());
			 conta.setCofa_numero_guia(Integer.parseInt(obj[2].toString()));
			 conta.setCofa_tipo_atendimento(Integer.parseInt(obj[3].toString()));
			 conta.setCofa_refe_id(Integer.parseInt(obj[4].toString()));
			 conta.setCofa_medico(Integer.parseInt(obj[5].toString()));
			 conta.setCofa_matriculla(Integer.parseInt(obj[6].toString()));
			 conta.setCofa_valor(Double.parseDouble(obj[7].toString()));
			 conta.setCofa_data_atendimento(obj[8].toString()==null?"":obj[8].toString().substring(8)+"-"+obj[8].toString().substring(5,7)+"-"+obj[8].toString().substring(0,4));
			 conta.setRefe_referencia(obj[9].toString());
			 conta.setRefe_id(Integer.parseInt(obj[10].toString()));
			 conta.setMedi_id(Integer.parseInt(obj[11].toString()));
			 conta.setMedi_nome(obj[12].toString());
			 conta.setCofa_status_pagamento(Integer.parseInt(obj[13].toString()));
			 conta.setStpa_id(Integer.parseInt(obj[14].toString()));
			 conta.setStpa_status(obj[15].toString());
			 conta.setCofa_descricao_atendimento(obj[16].toString());
			 conta.setTiat_tipo(obj[17].toString());
			 
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
                                    conta.setCofaDescricaoAtendimento(cell.getStringCellValue());
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
                       
                       //System.out.println("AQUI: " + conta.getCofaTipoAtendimento().length());

                       if(conta.getCofaDescricaoAtendimento().equals("PD")) {
                    	   conta.setCofaTipoAtendimento(2);
                       }else if(conta.getCofaDescricaoAtendimento().equals("I")) {
                    	   conta.setCofaTipoAtendimento(3);
                       }else if(conta.getCofaDescricaoAtendimento().equals("G")) {
                    	   conta.setCofaTipoAtendimento(1);
                       }
                       
                       if(conta.getCofaMedico() == 11616) {
                     	  conta.setCofaMedico(1);
                       }else if(conta.getCofaMedico() == 10753) {
                     	  conta.setCofaMedico(2);
                       }else if(conta.getCofaMedico() == 2566) {
                     	  conta.setCofaMedico(3);
                       }else if(conta.getCofaMedico() == 16836) {
                     	  conta.setCofaMedico(4);
                       }else if(conta.getCofaMedico() == 18336) {
                     	  conta.setCofaMedico(6);
                       }else if(conta.getCofaMedico() == 9724) {
                     	  conta.setCofaMedico(7);
                       }else if(conta.getCofaMedico() == 9317) {
                     	  conta.setCofaMedico(8);
                       }else if(conta.getCofaMedico() == 12530) {
                     	  conta.setCofaMedico(11);
                       }else if(conta.getCofaMedico() == 1042) {
                     	  conta.setCofaMedico(10);
                       }else if(conta.getCofaMedico() == 5079) {
                     	  conta.setCofaMedico(9);
                       }else if(conta.getCofaMedico() == 7886) {
                     	  conta.setCofaMedico(13);
                       }else if(conta.getCofaMedico() == 15215) {
                     	  conta.setCofaMedico(12);
                       }else if(conta.getCofaMedico() == 1) {
                     	  conta.setCofaMedico(15);
                       }else if(conta.getCofaMedico() == 15155) {
                     	  conta.setCofaMedico(14);
                       }else if(conta.getCofaMedico() == 5894) {
                     	  conta.setCofaMedico(17);
                       }else if(conta.getCofaMedico() == 8111) {
                     	  conta.setCofaMedico(16);
                       }else if(conta.getCofaMedico() == 8208722) {
                     	  conta.setCofaMedico(20);
                       }else if(conta.getCofaMedico() == 15693) {
                     	  conta.setCofaMedico(19);
                       }else if(conta.getCofaMedico() == 8026020) {
                     	  conta.setCofaMedico(18);
                       }else if(conta.getCofaMedico() == 1111) {
                     	  conta.setCofaMedico(21);
                       }else if(conta.getCofaMedico() == 7009) {
                     	  conta.setCofaMedico(22);
                       }else if(conta.getCofaMedico() == 1306) {
                     	  conta.setCofaMedico(23);
                       }else if(conta.getCofaMedico() == 15015) {
                     	  conta.setCofaMedico(24);
                       }else if(conta.getCofaMedico() == 16107) {
                     	  conta.setCofaMedico(29);
                       }else if(conta.getCofaMedico() == 1288) {
                     	  conta.setCofaMedico(25);
                       }else if(conta.getCofaMedico() == 10118) {
                     	  conta.setCofaMedico(26);
                       }else if(conta.getCofaMedico() == 9072) {
                     	  conta.setCofaMedico(27);
                       }else if(conta.getCofaMedico() == 14869) {
                     	  conta.setCofaMedico(28);
                       }else if(conta.getCofaMedico() == 10961) {
                     	  conta.setCofaMedico(31);
                       }else if(conta.getCofaMedico() == 23122) {
                     	  conta.setCofaMedico(30);
                       }else if(conta.getCofaMedico() == 4452) {
                     	  conta.setCofaMedico(37);
                       }else if(conta.getCofaMedico() == 8297) {
                     	  conta.setCofaMedico(36);
                       }else if(conta.getCofaMedico() == 8033124) {
                     	  conta.setCofaMedico(38);
                       }else if(conta.getCofaMedico() == 8297526) {
                     	  conta.setCofaMedico(39);
                       }else if(conta.getCofaMedico() == 9725) {
                     	  conta.setCofaMedico(41);
                       }else if(conta.getCofaMedico() == 7642) {
                     	  conta.setCofaMedico(40);
                       }else if(conta.getCofaMedico() == 9597) {
                     	  conta.setCofaMedico(42);
                       }else if(conta.getCofaMedico() == 19392) {
                     	  conta.setCofaMedico(43);
                       }else if(conta.getCofaMedico() == 8740) {
                     	  conta.setCofaMedico(49);
                       }else if(conta.getCofaMedico() == 14892) {
                     	  conta.setCofaMedico(50);
                       }else if(conta.getCofaMedico() == 8025920) {
                     	  conta.setCofaMedico(47);
                       }else if(conta.getCofaMedico() == 2337622) {
                     	  conta.setCofaMedico(48);
                       }else if(conta.getCofaMedico() == 8033024) {
                     	  conta.setCofaMedico(45);
                       }else if(conta.getCofaMedico() == 8100422) {
                     	  conta.setCofaMedico(46);
                       }else if(conta.getCofaMedico() == 8381525) {
                     	  conta.setCofaMedico(44);
                       }else if(conta.getCofaMedico() == 19396) {
                     	  conta.setCofaMedico(55);
                       }else if(conta.getCofaMedico() == 13893) {
                     	  conta.setCofaMedico(53);
                       }else if(conta.getCofaMedico() == 12561) {
                     	  conta.setCofaMedico(54);
                       }else if(conta.getCofaMedico() == 12057) {
                     	  conta.setCofaMedico(51);
                       }else if(conta.getCofaMedico() == 7575) {
                     	  conta.setCofaMedico(52);
                       }else if(conta.getCofaMedico() == 10367) {
                     	  conta.setCofaMedico(57);
                       }else if(conta.getCofaMedico() == 14635) {
                     	  conta.setCofaMedico(56);
                       }else if(conta.getCofaMedico() == 3123) {
                     	  conta.setCofaMedico(59);
                       }else if(conta.getCofaMedico() == 7415) {
                     	  conta.setCofaMedico(58);
                       }else if(conta.getCofaMedico() == 3020112) {
                     	  conta.setCofaMedico(60);
                       }else if(conta.getCofaMedico() == 111042) {
                     	  conta.setCofaMedico(63);
                       }else if(conta.getCofaMedico() == 21703) {
                     	  conta.setCofaMedico(65);
                       }else if(conta.getCofaMedico() == 12859) {
                     	  conta.setCofaMedico(64);
                       }else if(conta.getCofaMedico() == 17583) {
                     	  conta.setCofaMedico(62);
                       }else if(conta.getCofaMedico() == 13125) {
                     	  conta.setCofaMedico(61);
                       }else if(conta.getCofaMedico() == 10472) {
                     	  conta.setCofaMedico(66);
                       }else if(conta.getCofaMedico() == 28) {
                     	  conta.setCofaMedico(67);
                       }else if(conta.getCofaMedico() == 6310) {
                     	  conta.setCofaMedico(68);
                       }else if(conta.getCofaMedico() == 16335) {
                     	  conta.setCofaMedico(69);
                       }else if(conta.getCofaMedico() == 4303) {
                     	  conta.setCofaMedico(70);
                       }else if(conta.getCofaMedico() == 103299) {
                     	  conta.setCofaMedico(71);
                       }else if(conta.getCofaMedico() == 20545) {
                     	  conta.setCofaMedico(72);
                       }else if(conta.getCofaMedico() == 15318) {
                     	  conta.setCofaMedico(73);
                       }else if(conta.getCofaMedico() == 0) {
                     	  conta.setCofaMedico(79);
                       }else if(conta.getCofaMedico() == 7513) {
                     	  conta.setCofaMedico(80);
                       }else if(conta.getCofaMedico() == 1321) {
                     	  conta.setCofaMedico(78);
                       }else if(conta.getCofaMedico() == 3501) {
                     	  conta.setCofaMedico(77);
                       }else if(conta.getCofaMedico() == 15915) {
                     	  conta.setCofaMedico(76);
                       }else if(conta.getCofaMedico() == 14624) {
                     	  conta.setCofaMedico(75);
                       }else if(conta.getCofaMedico() == 9135) {
                     	  conta.setCofaMedico(74);
                       }
                       
                       conta.setCofaStatusPagamento(2);
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
	private Integer cofa_tipo_atendimento;
	private Integer cofa_refe_id;
	private Integer cofa_medico;
	private Integer cofa_matriculla;
	private Double cofa_valor;
	private String cofa_data_atendimento;
	private String refe_referencia;
	private Integer refe_id;
	private Integer cofa_status_pagamento;
	private Integer medi_id;
	private String medi_nome;
	private Integer stpa_id;
	private String stpa_status;
	private String cofa_descricao_atendimento;
	private String tiat_tipo;
	
	public String getTiat_tipo() {
		return tiat_tipo;
	}
	public void setTiat_tipo(String tiat_tipo) {
		this.tiat_tipo = tiat_tipo;
	}
	public String getCofa_descricao_atendimento() {
		return cofa_descricao_atendimento;
	}
	public void setCofa_descricao_atendimento(String cofa_descricao_atendimento) {
		this.cofa_descricao_atendimento = cofa_descricao_atendimento;
	}
	public Integer getStpa_id() {
		return stpa_id;
	}
	public void setStpa_id(Integer stpa_id) {
		this.stpa_id = stpa_id;
	}
	public String getStpa_status() {
		return stpa_status;
	}
	public void setStpa_status(String stpa_status) {
		this.stpa_status = stpa_status;
	}
	public Integer getMedi_id() {
		return medi_id;
	}
	public void setMedi_id(Integer medi_id) {
		this.medi_id = medi_id;
	}
	public String getMedi_nome() {
		return medi_nome;
	}
	public void setMedi_nome(String medi_nome) {
		this.medi_nome = medi_nome;
	}
	public Integer getCofa_status_pagamento() {
		return cofa_status_pagamento;
	}
	public void setCofa_status_pagamento(Integer cofa_status_pagamento) {
		this.cofa_status_pagamento = cofa_status_pagamento;
	}
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
	public Integer getCofa_tipo_atendimento() {
		return cofa_tipo_atendimento;
	}
	public void setCofa_tipo_atendimento(Integer cofa_tipo_atendimento) {
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
