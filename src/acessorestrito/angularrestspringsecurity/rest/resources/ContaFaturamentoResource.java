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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
	
	private static final String fileName = "C:/Users/Michell/Documents/ler.xls";

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
                                    conta.setCofaConvenio(cell.getStringCellValue());
                                    break;
                              case 2:
                                    conta.setCofaDataAtendimento((int) cell.getNumericCellValue());
                                    break;
                              case 3:
                                    conta.setCofaMedico(cell.getStringCellValue());
                                    break;
                              case 4:
                                     conta.setCofaNumeroGuia((int) cell.getNumericCellValue());
                                    break;
                              case 5:
                                    conta.setCofaRefeId(cell.getCellType());
                                    break;
                              case 6:
                                  conta.setCofaTipoAtendimento(cell.getStringCellValue());
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
