package vo.alarm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


/**
 * @author latrinh.vn
 */
public class Test {

	/**
	 * This method is used to read the data's from an excel file.
	 * 
	 * @param fileName
	 *            - Name of the excel file.
	 */
	private void readExcelFile(String fileName) {
		/**
		 * Create a new instance for cellDataList
		 */
		List cellDataList = new ArrayList();
		try {
			/**
			 * Create a new instance for FileInputStream class
			 */
			FileInputStream fileInputStream = new FileInputStream(fileName);

			/**
			 * Create a new instance for POIFSFileSystem class
			 */
			POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);

			/*
			 * Create a new instance for HSSFWorkBook Class
			 */
			HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);
			
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {  
	            
				HSSFSheet hssfSheet = workBook.getSheetAt(i);
				
				System.out.println("SHEET NAME: " + workBook.getSheetName(i));

				/**
				 * Iterate the rows and cells of the spreadsheet to get all the
				 * datas.
				 */
				Iterator rowIterator = hssfSheet.rowIterator();

				while (rowIterator.hasNext()) {
					HSSFRow hssfRow = (HSSFRow) rowIterator.next();
					Iterator iterator = hssfRow.cellIterator();
					List cellTempList = new ArrayList();
					HSSFCell hssfCell = null;
					
					while (iterator.hasNext()) {
						hssfCell = (HSSFCell) iterator.next();
						cellTempList.add(hssfCell);
					}
					
					if (hssfCell != null) {
						hssfCell.setCellValue("Dat");
					}
					
					cellDataList.add(cellTempList);
				}
            } 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * Call the printToConsole method to print the cell data in the console.
		 */
		printToConsole(cellDataList);
	}

	/**
	 * This method is used to print the cell data to the console.
	 * 
	 * @param cellDataList
	 *            - List of the data's in the spreadsheet.
	 */
	private void printToConsole(List cellDataList) {
		for (int i = 0; i < cellDataList.size(); i++) {
			List cellTempList = (List) cellDataList.get(i);
			for (int j = 0; j < cellTempList.size(); j++) {
				HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
				String stringCellValue = hssfCell.toString();
				System.out.print(stringCellValue + "\t");
			}
			System.out.println();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List readXlsFile(InputStream file) throws IOException {

		List sheetData = new ArrayList();
        
	    HSSFWorkbook wb_hssf; //Declare HSSF WorkBook
	    HSSFSheet sheet = null; // sheet can be used as common for XSSF and HSSF WorkBook
	    POIFSFileSystem fs = new POIFSFileSystem(file);
	    wb_hssf =  new HSSFWorkbook(fs);
	    
	    boolean first = true;
	    
	    boolean sheet1 = true;
	    
	    for (int i = 0; i < wb_hssf.getNumberOfSheets(); i++) { 
	    	sheet = wb_hssf.getSheetAt(i);
	    	String sheetName = wb_hssf.getSheetName(i);
	    	System.out.println("SHEET NAME: " + sheetName);
	    	
		    Iterator rows = sheet.rowIterator(); // Now we have rows ready from the sheet
		    first = true;
		    while (rows.hasNext()) {
		    	HSSFRow row = (HSSFRow) rows.next();
		    	
		    	if (sheet1 && first) {
		    		HSSFCell hssfCell = row.createCell(row.getLastCellNum() + 1);
			    	hssfCell.setCellValue("Hướng");
			    	
			    	first = false;
		    		sheet1 = false;
		    	} else {
		    		if (first == false) {
		    			HSSFCell hssfCell = row.createCell(row.getLastCellNum() + 1);
				    	hssfCell.setCellValue(sheetName);
		    		} else {
		    			first = false;
		    			continue;
		    		}
		    	}
	    		
	    		Iterator cells = row.cellIterator();
		    	List data = new ArrayList();
		    	HSSFCell cell = null;
		    	while (cells.hasNext()) {
		    		cell =  (HSSFCell) cells.next();  
		    		data.add(cell);
		    	}
	    		sheetData.add(data);
		    }
	    }
	    
	    file.close();
	    printToConsole(sheetData);
	    
	    return sheetData;
	}

	public static void main(String[] args) throws IOException {
		String fileName = "C:" + File.separator + "Users" + File.separator
				+ "Phoenix" + File.separator + "Desktop" + File.separator
				+ "CapTruyenDan.xls";
		
		FileInputStream fileInputStream = new FileInputStream(fileName);
		
		List sheetData = new Test().readXlsFile(fileInputStream);
		
		List heard= (List)sheetData.get(0);
		
		System.out.println("size: " + heard.size());
		
	}
}