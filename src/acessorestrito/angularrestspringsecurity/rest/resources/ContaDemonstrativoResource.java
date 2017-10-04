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

import acessorestrito.angularrestspringsecurity.dao.conta_demonstrativo.ContaDemonstrativoDao;
import acessorestrito.angularrestspringsecurity.entity.ContaDemonstrativo;
import acessorestrito.angularrestspringsecurity.entity.ContaFaturamento;

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
	
	private static final String fileName = "C:/Users/Michell/Documents/ler.xls";

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
                       listaContas.add(conta);
                       while (cellIterator.hasNext()) {
                              Cell cell = cellIterator.next();
                              switch (cell.getColumnIndex()) {
                              case 0:
                                    conta.setCodePaciente(cell.getStringCellValue());
                                    break;
                              case 1:
                                    conta.setCodeConvenio(cell.getStringCellValue());
                                    break;
                              case 2:
                                    conta.setCodeDataAtendimento((int) cell.getNumericCellValue());
                                    break;
                              case 3:
                                    conta.setCodeMedico(cell.getStringCellValue());
                                    break;
                              case 4:
                                     conta.setCodeNumeroGuia((int) cell.getNumericCellValue());
                                    break;
                              case 5:
                                    conta.setCodeRefeId(cell.getCellType());
                                    break;
                              case 6:
                                  conta.setCodeTipoAtendimento(cell.getStringCellValue());
                                  break;      
                              }
                              
                       }
                       conta.setCodeRefeId(1);
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
              System.out.println("Foi");
         }

   }

}
