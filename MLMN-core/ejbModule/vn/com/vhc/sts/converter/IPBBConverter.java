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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

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
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class IPBBConverter extends STS_BasicConverter {



	private String header = "";
	
	private static Logger logger = Logger.getLogger(EricssonXMLConverter.class.getName());
	private String[] strHeader = null;
	
	public IPBBConverter() {
	}


	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath) ;
		// insert record and update status in C_RAW_FILES_MLMN table
		String str = "LINK,TIME,in_bps,out_bps,delay_HK_ms,delay_JP_ms,delay_link_1_ms,delay_link_2_ms,delay_ms,delay_SING_ms,delay_thanhnien_ms,delay_tuoitre_ms,delay_UK_ms,delay_US_ms,jitter_dsn_ms,jitter_dsp_ms,jitter_sdn_ms,jitter_sdp_ms,loss_DS,loss_SD,MOS";
		strHeader = str.split(",");
		//strHeader = this.getColumn("R_E_IPBB").split(",");

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
			// parse file
			String headerMark = "";
			String line ="";
			String fileName = file.getName();
			File outFile = new File(direcPath, fileName);
			String link = fileName.substring(fileName.indexOf("_") + 1,fileName.lastIndexOf("_"));
			Node parentNode = doc.getElementsByTagName("xport").item(0);
			
			NodeList nodeList = parentNode.getChildNodes();
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node node = nodeList.item(index);
				/*if (node.getNodeType() == 3) {
					continue;
				}*/
				if (node != null && node.getNodeName().equalsIgnoreCase("meta")) {
					try {
						Node legend = getChildByNodeName(node, "legend");
						if(legend.getNodeName().equalsIgnoreCase("legend"))
						{
							header = getEntry(legend);
							headerMark = header;
							for(int i = 0; i < strHeader.length; i++)
							{
								if(!header.contains(strHeader[i]))
								{
									header += "," + strHeader[i];
								}
							}
							
							writeFileHeader(outFile);
						}
						
					} catch (Exception e) {
						logger.warn("Extract data faiule in node at index: " + index);
					}
				}
				
				if(node != null && node.getNodeName().equalsIgnoreCase("data"))
				{
					NodeList rows = node.getChildNodes();
					for (int i = 0; i < rows.getLength(); i++) {
						Node row = rows.item(i);
					if(row.getNodeName().equalsIgnoreCase("row"))
					{
							line = this.getValue(row, link);
							line = line.replace("NaN", "");
							for(int j = 0; j < strHeader.length; j++)
							{
								if(!headerMark.contains(strHeader[j]))
								{
									line += ",";
								}
							}
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
	private String getEntry(Node legend)
	{
		String title = "LINK" + "," + "TIME";
		NodeList entrys = legend.getChildNodes();
		for (int index = 0; index < entrys.getLength(); index++) {
			Node n = entrys.item(index);
			if(n.getNodeName().equalsIgnoreCase("entry"))
			{
				String tmp = n.getTextContent();
				tmp = tmp.replace(" ", "_");
				tmp = tmp.replace("tuoitre.vn", "tuoitre");
				tmp = tmp.replace("thanhnien.com.vn", "thanhnien");
				title +="," + tmp ;
			}
		}
		return title;
		
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
	
	private void writeFileHeader(File outFile) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		List<String> list = getFileHeaderInfo();
		for (String s : list) {
			writer.write(commentChar + s);
			writer.newLine();
		}
		if (header != null && header.length() > 0) {
			writer.write(commentChar + "---------------------------------------------");
			writer.newLine();
			writer.write(commentChar + header);
			writer.newLine();
		}
		writer.close();
	}

	private void writeLine(File outFile, String line) throws IOException {
		// write header
		if (!outFile.exists()) {
			writeFileHeader(outFile);
		}
		// write data
		if (line != null && line.length() > 0) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
			writer.append(line);
			writer.newLine();
			writer.flush();
			writer.close();
		}
	}
private Node getChildByNodeName(Node node, String name) {
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node n = nodeList.item(index);
				if (n != null && n.getNodeName().equalsIgnoreCase(name)) {
					return n;
				}
			}
		}
		return null;
	}

private String getColumn(String tableName)
{
	Connection conn = null;
	String file_column_header = "";
	try {
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			String queryStr = "select FILE_COLUMN_HEADER from I_IMPORT_MAPPING where STATUS = 'Y' AND RAW_TABLE = '"+tableName+"'";
			
			
			//
			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {
				file_column_header += rs.getString("FILE_COLUMN_HEADER")+",";
			}
			//file_column_header = file_column_header.substring(0,file_column_header.lastIndexOf(","));
			rs.close();
			st.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	} finally {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn("Cannot close connection to database");
			}
		}
	}
	
	return file_column_header;
}
}
