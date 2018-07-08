package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;



/******************************************************************************
NAME:       InventoryIPBBCiscoConverter
PURPOSE:	Convert file inventory IPBB Cisco

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        17/10/2013       AnhNT      		1. Raw file format .xml
******************************************************************************/
public class InventoryIPBBCiscoConverter extends IN_BasicConverter {
	private static Logger logger = Logger.getLogger(InventoryIPBBCiscoConverter.class.getName());
	private static DateFormat df_full = new SimpleDateFormat("yyyy-MM-dd");
	private String day = "";
	
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		String filePath = file.getPath();
		String fileType = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()); 
		BufferedWriter writerFile = null;
		
		try {
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#DAY;DEVICE;IP_ADDRESS;DEVICE_SERIES;ELEMENT_TYPE;CHASSIS_DESCRIPTION;CHASSIS_SERIAL_NUMBER" +
					";SHELF_DESCRIPTION;SHELF_SERIAL_NUMBER;SHELF_STATUS;MODULE_NAME;SUB_MODULE_NAME;MODULE_STATUS;HARDWARE_TYPE" +
					";HARDWARE_VERSION;PORT_LOCATION;PORT_TYPE;PORT_SENDING_ALARM;PORT_ALIAS;PORT_STATUS;PORT_MANAGED;PID;PLUGGABLE_TYPE_SERIAL_NUMBER\n");
			
			if(fileType.equalsIgnoreCase(".xlsx")){
				try {
					readXlsxFile(filePath, writerFile);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(fileType.equalsIgnoreCase(".xls")){
				readXlsFile(filePath, writerFile);
			}else if(fileType.equalsIgnoreCase(".csv")){
				readCsvFile(file, writerFile);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.warn("File not found "+ e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	  
	public void readXlsxFile(String filePath, BufferedWriter writerFile) throws IOException, Throwable {
		FileInputStream inputStream = new FileInputStream(filePath);
		XSSFWorkbook my_xlsx_workbook = new XSSFWorkbook(inputStream); 
		XSSFSheet my_worksheet = my_xlsx_workbook.getSheetAt(0);
		Iterator<Row> rowIterator = my_worksheet.iterator();
		int count=0;
        
        while (rowIterator.hasNext()) {
        	count++;
        	Row row = (Row) rowIterator.next(); 
        	String line = "";
        	Iterator<Cell> cellIterator = row.cellIterator();
        	while (cellIterator.hasNext()) {
        		Cell  cell = (Cell) cellIterator.next();
        		String cellValue="";         
        		switch (cell.getCellType()) {
        			case Cell.CELL_TYPE_STRING: 
        				cellValue = cell.getRichStringCellValue().getString();
        				line += cellValue+";";
        				break;
        			case Cell.CELL_TYPE_NUMERIC:
        				if(DateUtil.isCellDateFormatted(cell)){
	    					Date valueDate = cell.getDateCellValue();
	    					String value = df_full.format(valueDate); 
	    					line += value+";";
	    				}else{
	    					int value= (int) cell.getNumericCellValue();
	    					line += value+";";
	    				} 
        				break;
        			case Cell.CELL_TYPE_BOOLEAN:
		    			boolean value = cell.getBooleanCellValue();
        				line += value+";";
        				break;
        			case Cell.CELL_TYPE_FORMULA:
        				cellValue = cell.getCellFormula();
        				line += cellValue+";";
        				break;
        			default:
        		}
        		
        	} 
        	
	    	if(count == 2){
	    		day = line.split(";")[1].substring(0, 21);
	    		day = getTimestamp(day);
	    	}
	    	if(count > 5){
	    		// Mot dong du lieu
		    	//sheetData.add(day+";"+line);
		    	writerFile.write(day+";"+line.replace("N/A", ""));
				writerFile.newLine();
	    	} 
        }
        writerFile.close();
	}
	
	@SuppressWarnings({ "rawtypes"})
	public void readXlsFile(String filePath, BufferedWriter writerFile) throws IOException{
		 	FileInputStream inputStream = new FileInputStream(filePath);
		 	POIFSFileSystem fs = new POIFSFileSystem(inputStream);
	        HSSFWorkbook wb_hssf; //Declare HSSF WorkBook
	        HSSFSheet sheet = null; // sheet can be used as common for XSSF and HSSF WorkBook
		    wb_hssf = new HSSFWorkbook(fs);
		    sheet = wb_hssf.getSheetAt(0);
		    Iterator rows = sheet.rowIterator(); // Now we have rows ready from the sheet
		    int count = 0;
		    
		    while (rows.hasNext()) { 
		    	count++;
		    	String line = "";
	    		HSSFRow row = (HSSFRow) rows.next();
		    	Iterator cells = row.cellIterator();
		    	while (cells.hasNext()) {
		    		HSSFCell cell =  (HSSFCell) cells.next();  
		    		String cellValue="";
		    		//cell =  (HSSFCell) cells.next(); 
		    		switch (cell.getCellType()) {

		    		case HSSFCell.CELL_TYPE_STRING:
		    			cellValue = cell.getRichStringCellValue().getString();
		    			line += cellValue+";";
		    		    break;
		    		case HSSFCell.CELL_TYPE_NUMERIC:
		    				if(HSSFDateUtil.isCellDateFormatted(cell)){
		    					Date valueDate = cell.getDateCellValue();
		    					String value = df_full.format(valueDate); 
		    					line += value+";";
		    				}else{
		    					int value= (int) cell.getNumericCellValue();
		    					line += value+";";
		    				} 
		    		    break;
		    		case Cell.CELL_TYPE_BOOLEAN:
		    			boolean value = cell.getBooleanCellValue();
        				line += value+";";
        				break;
        			case Cell.CELL_TYPE_FORMULA:
        				cellValue = cell.getCellFormula();
        				line += cellValue+";";
        				break;
		    		 default:
		    		} 
		    	}
		    	
		    	if(count == 2){
		    		day = line.split(";")[1].substring(0, 21);
		    		day = getTimestamp(day);
		    	}
		    	if(count > 5){
		    		// Mot dong du lieu
			    	//sheetData.add(day+";"+line);
			    	writerFile.write(day+";"+line.replace("N/A", ""));
					writerFile.newLine();
		    	} 
		    } 
		    writerFile.close(); 
	}
	
	public void readCsvFile(File file, BufferedWriter writerFile){
		BufferedReader reader = null;  
		
		try { 
			reader = new BufferedReader(new FileReader(file));  			
			String strLine = ""; 
			int count = 0;
			while ((strLine = reader.readLine()) != null){
				count++;
				if(count > 1){
					strLine = strLine.replace(",", ";");
					writerFile.write(";"+strLine.replace("N/A", ""));
					writerFile.newLine();
				} 
			}  
		}catch (Exception e) {
			logger.warn("File not found" + e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close(); 
					writerFile.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		} 
	}  
	private String getTimestamp(String timestamp) {
		DateTime dateTime = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MMM-dd, HH:mm:ss");
		dateTime = formatter.parseDateTime(timestamp).plusHours(7);

		return dateTime.toString(formatter);
	}
}
