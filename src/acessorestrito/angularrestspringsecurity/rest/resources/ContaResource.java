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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
	
	 private static final String fileName = "C:/Users/Michell/Documents/ler.xls";

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
                                    conta.setContaDataAtendimento((int) cell.getNumericCellValue());
                                    break;
                              case 3:
                                    conta.setContaMedico(cell.getStringCellValue());
                                    break;
                              case 4:
                                     conta.setContaNumeroGuia((int) cell.getNumericCellValue());
                                    break;
                              case 5:
                                    conta.setContaRefeId(cell.getCellType());
                                    break;
                              case 6:
                                  conta.setContaTipoAtendimento(cell.getStringCellValue());
                                  break;      
                              }
                              
                       }
                       conta.setContaRefeId(1);
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
