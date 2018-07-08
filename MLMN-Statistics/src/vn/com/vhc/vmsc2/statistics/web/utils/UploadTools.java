package vn.com.vhc.vmsc2.statistics.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UploadTools {
	
	private static Logger logger = Logger.getLogger(UploadTools.class.getName());
	//private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private static DateFormat df_full = new SimpleDateFormat("MM/dd/yyyy");
	//private static DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List readXlsFile(InputStream filePath) throws IOException {

        List sheetData = new ArrayList();
        
	    HSSFWorkbook wb_hssf; //Declare HSSF WorkBook
	    HSSFSheet sheet = null; // sheet can be used as common for XSSF and HSSF WorkBook
	    POIFSFileSystem fs = new POIFSFileSystem(filePath);
	    wb_hssf =  new HSSFWorkbook(fs);
	    sheet = wb_hssf.getSheetAt(0);
	    Iterator rows = sheet.rowIterator(); // Now we have rows ready from the sheet
	    
	    while (rows.hasNext()) {

	    	HSSFRow row = (HSSFRow) rows.next();
	    	Iterator cells = row.cellIterator();
	    	List data = new ArrayList();
	    	while (cells.hasNext()) {
	    		HSSFCell cell =  (HSSFCell) cells.next();  
	    		data.add(cell);
	    	}
	    	
	    	//logger.info("Số ô đọc được: "+ data.size());
	    	sheetData.add(data);
	    }
	    
	    filePath.close();
	    
	    return sheetData;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List readXlsxFile(InputStream inputStream) throws IOException {
		List sheetData = new ArrayList();
		
		Workbook wb_xssf; //Declare XSSF WorkBook
	    Sheet sheet = null;
	    wb_xssf = new XSSFWorkbook(inputStream);
        sheet = wb_xssf.getSheetAt(0);
        Iterator rows = sheet.rowIterator(); 
        
        while (rows.hasNext()) {
        	Row row = (Row) rows.next();
        	//logger.info("row#= " + row.getRowNum() + "");
               
        	//log(row.getPhysicalNumberOfCells()+"");
        	Iterator cells = row.cellIterator();
        	List data = new ArrayList();
        	while (cells.hasNext()) {
        		Cell cell = (Cell) cells.next();
                         
        		switch (cell.getCellType()) {
        			case Cell.CELL_TYPE_STRING:
        				data.add(cell.getRichStringCellValue().getString());
        				//logger.info("Chuoi: " + cell.getRichStringCellValue().getString());
        				break;
        			case Cell.CELL_TYPE_NUMERIC:
        				if(DateUtil.isCellDateFormatted(cell)) {
        					data.add(cell.getDateCellValue());
        					logger.info("Chuoi: " + cell.getDateCellValue());
        				} else {
        					data.add(cell.getNumericCellValue());
        					//logger.info("Chuoi: " + cell.getRichStringCellValue().getString());
        				}
        				break;
        			case Cell.CELL_TYPE_BOOLEAN:
        				data.add(cell.getBooleanCellValue());
        				//logger.info("Chuoi: " + cell.getBooleanCellValue());
        				break;
        			case Cell.CELL_TYPE_FORMULA:
        				data.add(cell.getCellFormula());
        				//logger.info("Chuoi: " + cell.getCellFormula());
        				break;
        			default:
        		}
        	}
        	sheetData.add(data);
        }
        inputStream.close();
        return sheetData;
	}
	
	public static String readTxtFile(InputStream filePath) {
		
		String strContent = null;
		try {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(filePath));
			StringBuffer sbfFileContents = new StringBuffer();
			String line = null;
			
			boolean first = true;
            
			while( (line = bReader.readLine()) != null){
				
				if (first) {
					first = false;
				} else {
					sbfFileContents.append(",");
				}
				
				sbfFileContents.append(line);
				strContent = sbfFileContents.toString();
			}
			
			//logger.info("File txt content: " + strContent);
		} catch (IOException e) {
			logger.warn("Error read file txt");
		}
		
		return strContent;
	}
	
	@SuppressWarnings("rawtypes")
	public static String uploadCode(InputStream in, String filename) throws IOException {
		String fileExtend = filename.substring(filename.indexOf(".") + 1);
		
		String content = "";
		
		if (fileExtend.equals("txt")) {
			//logger.info("readTxtFile: " + filename);
			content = readTxtFile(in);
		} else if (fileExtend.equals("xls")) {
			//logger.info("readTxtFile: " + filename);
			List sheetData = readXlsFile(in);
			
			int maxsize = sheetData.size();
			boolean first = true;

			for(int i=0; i<maxsize; i++) {
				List item = (List)sheetData.get(i);
				for(int j=0; j<item.size(); j++) {
					if (first) {
						content +="0";
						first = false;
					} else {
						content +=",0";
					}
					content += NumberTools.formatExponentNumber(item.get(j).toString());
					
					//System.out.println(item.get(j).toString());
				}
				
			}
			
		} else if (fileExtend.equals("xlsx")) {
			//logger.info("readTxtFile: " + filename);
			List sheetData = readXlsxFile(in);
			
			int maxsize = sheetData.size();
			boolean first = true;

			for(int i=0; i<maxsize; i++) {
				List item = (List)sheetData.get(i);
				for(int j=0; j<item.size(); j++) {
					if (first) {
						content +="0";
						first = false;
					} else {
						content +=",0";
					}
					content += NumberTools.formatExponentNumber(item.get(j).toString());
					
					//System.out.println(item.get(j).toString());
				}
				
			}
		}
		
		return content;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static List readXlsFileForCable(InputStream file) throws IOException {

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
	    	//System.out.println("SHEET NAME: " + sheetName);
	    	
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
	    //printToConsole(sheetData);
	    
	    return sheetData;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List readXlsFile2(InputStream filePath) throws IOException {

        List sheetData = new ArrayList();
        
        HSSFWorkbook wb_hssf; //Declare HSSF WorkBook
        HSSFSheet sheet = null; // sheet can be used as common for XSSF and HSSF WorkBook
	    wb_hssf = new HSSFWorkbook(filePath);
	    sheet = wb_hssf.getSheetAt(0);
	    Iterator rows = sheet.rowIterator(); // Now we have rows ready from the sheet
	    
	    while (rows.hasNext()) {

	    	HSSFRow row = (HSSFRow) rows.next();
	    	Iterator cells = row.cellIterator();
	    	List data = new ArrayList();
	    	while (cells.hasNext()) {
	    		HSSFCell cell =  (HSSFCell) cells.next();  
	    		String cellValue="";
	    		//cell =  (HSSFCell) cells.next(); 
	    		switch (cell.getCellType()) {

	    		case HSSFCell.CELL_TYPE_STRING:
	    			cellValue = cell.getRichStringCellValue().getString();
	    			data.add(cellValue);
	    		    break;
	    		case HSSFCell.CELL_TYPE_NUMERIC:
	    				if(HSSFDateUtil.isCellDateFormatted(cell)){
	    					Date valueDate = cell.getDateCellValue();
	    					String value = df_full.format(valueDate); 
	    					data.add(value);
	    				}else{
	    					int value= (int) cell.getNumericCellValue();
	    					data.add(value);
	    				} 
	    		    break;
	    		 default:
	    			 data.add(cell);
	    		}
	    		//data.add(cell);
	    	}
	    	
	    	//logger.info("Số ô đọc được: "+ data.size());
	    	sheetData.add(data);
	    }
	    
	    filePath.close();
	    
	    return sheetData;
	}
}