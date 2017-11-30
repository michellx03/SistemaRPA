package acessorestrito.angularrestspringsecurity.rest.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import acessorestrito.angularrestspringsecurity.dao.procedimentos_ipasgo.ProcedimentosIpasgoDao;
import acessorestrito.angularrestspringsecurity.entity.Conta;
import acessorestrito.angularrestspringsecurity.entity.ProcedimentosIpasgo;

@Component
@Path("/procedimento_ipasgo")
public class ProcedimentoIpasgoResource {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Autowired
	private ProcedimentosIpasgoDao procedimentosIpasgoDaoInterface;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProcedimentosIpasgo> list() {

		String query = "SELECT p FROM ProcedimentosIpasgo p";
		List<ProcedimentosIpasgo> procedimentos = entityManager.createQuery(query, ProcedimentosIpasgo.class).getResultList();
		entityManager.close();
		List<ProcedimentosIpasgo> procedimentosbusca = new ArrayList<>();
		for (ProcedimentosIpasgo procedimento : procedimentos) {
			procedimentosbusca.add(procedimento);
		}
		return procedimentosbusca;
	}
	
	private static final String fileName = "C:/Users/Michell/Documents/ProcedimentosIpasgo.xls";
	
	@POST
	@Path("lerProcedimentosIpasgo")
	@Produces(MediaType.APPLICATION_JSON)
	public void lerExcel() throws IOException {
		
		 List<ProcedimentosIpasgo> listaContas = new ArrayList<ProcedimentosIpasgo>();
		   
         try {
                FileInputStream arquivo = new FileInputStream(new File(ProcedimentoIpasgoResource.fileName));

                HSSFWorkbook workbook = new HSSFWorkbook(arquivo);

                HSSFSheet sheetAlunos = workbook.getSheetAt(0);

                Iterator<Row> rowIterator = sheetAlunos.iterator();

                while (rowIterator.hasNext()) {
                       Row row = rowIterator.next();
                       Iterator<Cell> cellIterator = row.cellIterator();

                      ProcedimentosIpasgo conta = new ProcedimentosIpasgo();
                       listaContas.add(conta);
                       while (cellIterator.hasNext()) {
                              Cell cell = cellIterator.next();
                              switch (cell.getColumnIndex()) {
                              case 0:
                            	  conta.setPripCodigo((int) cell.getNumericCellValue());
                                  break;
                              case 1:
                            	  conta.setPripDescricao(cell.getStringCellValue());
                                  break;
                              case 2:
                            	  	conta.setPripValor(cell.getNumericCellValue());
                                    break;
                              case 3:
                            	  	conta.setPripPorte((int) cell.getNumericCellValue());
                                    break;
                              case 4:
                            	  	conta.setPripAuxiliar((int) cell.getNumericCellValue());
                                    break;    
                              }
                              
                       }
                       
                       procedimentosIpasgoDaoInterface.save(conta);
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
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void Deletar(@QueryParam("id") Integer id) {

		procedimentosIpasgoDaoInterface.delete(id);

	}

}	

