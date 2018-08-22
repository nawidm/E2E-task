package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileReader {
	
	private File file;
	private Workbook workbook;
	
	public void setFile(File f) {
		this.file = f;
		try {
			workbook = WorkbookFactory.create(f);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getColumnData(String colName) {
		ArrayList<String> dataList = new ArrayList<String>();
		Sheet sheet = this.workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        
        int cellIndex = 0;
        for(Cell cell: sheet.getRow(0)) {
        	String cellValue = dataFormatter.formatCellValue(cell);
        	if(cellValue.equals(colName)) {
        		break;
        	}
        	cellIndex++;
        }
        
        for(Row row: sheet) {
        	String cellValue = dataFormatter.formatCellValue(row.getCell(cellIndex));
        	dataList.add(cellValue);
        }
        
		return dataList;
	}
}
