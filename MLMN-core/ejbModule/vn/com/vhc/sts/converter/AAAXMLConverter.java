package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Date;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;
/*
 * @author: ThangPX. Create date: 10/12/2013
 * @param  Source file, DirectPath: File XML
 * @return file is converted
 * */
public class AAAXMLConverter extends STS_BasicConverter {


	private static Logger logger = Logger.getLogger(EricssonXMLConverter.class.getName());
	public AAAXMLConverter() {
	}


	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath) ;
		try {
			
			//
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			
		    removeLineFromFile(file.getAbsolutePath());
			
			Document doc = docBuilder.parse(file);
			docBuilder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});
			String line ="";
			String fileName = file.getName();
			File outFile = new File(direcPath, fileName);
			int t = fileName.lastIndexOf("_");
			
			
			String link = "";
			if(fileName.indexOf("_")==t)
				link = fileName.substring(t+1,fileName.indexOf("."));
			else
				link = fileName.substring(0,t);
			Node parentNode = doc.getElementsByTagName("xport").item(0);
			
			NodeList nodeList = parentNode.getChildNodes();
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node node = nodeList.item(index);
				/*if (node.getNodeType() == 3) {
					continue;
				}*/
				if(node != null && node.getNodeName().equalsIgnoreCase("data"))
				{
					NodeList rows = node.getChildNodes();
					for (int i = 0; i < rows.getLength(); i++) {
						Node row = rows.item(i);
					if(row.getNodeName().equalsIgnoreCase("row"))
					{
							line = this.getValue(row, link);
							line = line.replace("NaN", "0");
							writeLine(outFile,line);
					}
					}
				}
			
				logger.info("Convert file: " + file.getName() + " success");
			} 
		}catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}

		
		
	}
	private String convertToDate(long timestamp)
	{
		
		String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date (timestamp * 1000));
		return date;
	}
	private String getValue(Node rows, String link)
	{
		
		NodeList v = rows.getChildNodes();
		
		String line = link + ",";
		if(v != null && v.getLength() > 0)
		{
			Node n = v.item(0);
			String timestamp = n.getTextContent();
			String s = convertToDate(Integer.parseInt(timestamp));
			line += s;
			for(int i = 1; i < v.getLength(); i++)
			{
	
					line += "," +v.item(i).getTextContent();
					
			}
		}
		return line;
	}
	
	
	public void removeLineFromFile(String file) {
		boolean flag = false;
		try {

		  File inFile = new File(file);

		  if (!inFile.isFile()) {
		    System.out.println("Parameter is not an existing file");
		    return;
		  }

		  //Construct the new file that will later be renamed to the original filename.
		  File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

		  BufferedReader br = new BufferedReader(new FileReader(file));
		  PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

		  String line = null;

		  //Read from the original file and write to the new
		  //unless content matches data to be removed.
		  while ((line = br.readLine()) != null) {

			  if(!line.contains("<?xml") && flag == false)
				{
					continue;
				}
				else
					flag = true;
		    if (flag == true) {

		      pw.println(line);
		      pw.flush();
		    }
		  }
		  pw.close();
		  br.close();

		  //Delete the original file
		  if (!inFile.delete()) {
		    System.out.println("Could not delete file");
		    return;
		  }

		  //Rename the new file to the filename the original file had.
		  if (!tempFile.renameTo(inFile))
		    System.out.println("Could not rename file");

		}
		catch (FileNotFoundException ex) {
		  ex.printStackTrace();
		}
		catch (IOException ex) {
		  ex.printStackTrace();
		}

		}
	
	

	private void writeLine(File outFile, String line) throws IOException {
		
		// write data
		if (line != null && line.length() > 0) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
			writer.append(line);
			writer.newLine();
			writer.flush();
			writer.close();
		}
	}

}
