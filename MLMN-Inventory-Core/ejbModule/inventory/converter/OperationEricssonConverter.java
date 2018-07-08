package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/******************************************************************************
 * NAME: OperationEricssonConverter PURPOSE: Convert file Operation ericsson
 * rnc, nodeB
 * 
 * REVISIONS: Ver Date Author Description --------- ---------- ---------------
 * ------------------------------------ 1.0 24/04/2014 Thangpx 1. Raw file
 * format .xml
 ******************************************************************************/
public class OperationEricssonConverter extends IN_BasicConverter {

	private String sep = ";", neName = "";
	private final String vendor = "ERICSSON", neType = "RNC";
	private String domain = "OSS";
	private static Logger logger = Logger
			.getLogger(OperationEricssonConverter.class.getName());

	public void convertFile(File file, String direcPath,
			Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		BufferedWriter writer = null;
		String line = "";
		try {
			String fileName = file.getName();
			neName = fileName.substring(0,fileName.indexOf("_"));
			File outFile = new File(direcPath, fileName);
			// Create outFile and write header
			writer = new BufferedWriter(new FileWriter(outFile.getParent()
					+ "/" + outFile.getName()));
			writer.write("#VENDOR;NE_TYPE;NE_NAME;START_TIME;USER;USER_IP;COMMAND;RESULT;DOMAIN\n");

			// Read inputFile
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId,
						String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});
			// parse file
			Document doc = docBuilder.parse(file);
			// get childNode of <RecordContent> Node
			NodeList logRecordList = doc.getElementsByTagName("LogRecord");
			for (int i = 0; i < logRecordList.getLength(); i++) {
				Node nodeChild = logRecordList.item(i);
				line = this.extractMeasData(nodeChild);
				writer.write(line);
				writer.newLine();
				if (nodeChild.getNodeType() == 3) {
					continue;
				}
			}

		} catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMS-0306", e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("Convert file: " + file.getName() + " success");
		}
		
	}
	// GetContent
	private String extractMeasData(Node node) {
		String command = "", user = "", result = "", datetime = "", strLine = "", userIp = "";
		NodeList nodeListRecord = node.getChildNodes();
		for (int k = 0; k < nodeListRecord.getLength(); k++) {
			if (nodeListRecord.item(k).getNodeName().equalsIgnoreCase("TimeStamp"))
			{
				 domain = "Moshell";
				 datetime = getDateTime(nodeListRecord.item(k));
			}
			else {
			 
				NodeList nodeList = nodeListRecord.item(k).getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					if (nodeList.item(i).getNodeName().equalsIgnoreCase("Request")) {
						strLine = nodeList.item(i).getTextContent();
						String[] str = strLine.split("\n");
						for (int j = 0; j < str.length; j++) {
							if (str[j].contains("Unknown")) {
								datetime = parseDateTime(str[j].substring(
										str[j].indexOf("Unknown") + 8,
										str[j].length()));
							}

							else
								command += str[j] + " ";
						}
					} else if (nodeList.item(i).getNodeName()
							.equalsIgnoreCase("Info"))
						command = getCommand(nodeList.item(i).getTextContent());
					else if (nodeList.item(i).getNodeName().equalsIgnoreCase("User"))
						user = nodeList.item(i).getTextContent();
					else if (nodeList.item(i).getNodeName()
							.equalsIgnoreCase("Result")) {
						Element eElementME = (Element) nodeList.item(i);
						result = eElementME.getAttribute("value");
					}
					else if (nodeList.item(i).getNodeName()
							.equalsIgnoreCase("Termname"))
						userIp = nodeList.item(i).getTextContent();
				}
			}
		}

		return vendor + sep + neType + sep + neName + sep + datetime + sep + user.trim() + sep + userIp.trim() + sep
				+ command.trim() + sep + result.trim() + sep + domain;
	}
	// get DateTime in file SHELL_AUDITTRIAL_LOG
	private String getDateTime(Node timeStampNode) {
		String datetime = "", day = "", month = "", year = "", hour = "", minute = "", second = "";
		NodeList childNodes = timeStampNode.getChildNodes();
		
		for (int i = 0; i < childNodes.getLength(); i++) {
			
			
			if (childNodes.item(i).getNodeName().equalsIgnoreCase("day"))
				day = childNodes.item(i).getTextContent().trim()  ;
			else if (childNodes.item(i).getNodeName().equalsIgnoreCase("month"))
				month = childNodes.item(i).getTextContent().trim()  ;
			else if (childNodes.item(i).getNodeName().equalsIgnoreCase("year"))
				year = childNodes.item(i).getTextContent().trim() ;
			else if (childNodes.item(i).getNodeName().equalsIgnoreCase("hour"))
				hour = childNodes.item(i).getTextContent().trim() ;
			else if (childNodes.item(i).getNodeName().equalsIgnoreCase("minute"))
				minute = childNodes.item(i).getTextContent().trim() ;
			else if (childNodes.item(i).getNodeName().equalsIgnoreCase("second"))
				second = childNodes.item(i).getTextContent().trim();
		}
		datetime = insertZero(day) +"/"+ insertZero(month) +"/"+ insertZero(year) +" "+ insertZero(hour) +":"+ insertZero(minute) +":"+ insertZero(second);
		return datetime;
	}
	private String insertZero(String x)
	{
		if(x.length()==1)
			return x = "0" + x;
		else
			return x;
	}
	// get Command
	private String getCommand(String info) {
		info = info.trim();
		return info; 
	}
	// Pare datetime in file CORBA_AUDITTRIAL_LOG
	private String parseDateTime(String strDate) {
		SimpleDateFormat dt = new SimpleDateFormat("EEE MMM dd yyyy, HH:mm:ss");

		Date date = null;
		try {
			date = dt.parse(strDate);
			strDate = formatDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	} 
	private String formatDate(Date date) {
		String strDate = "";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		strDate = df.format(date);
		return strDate;
	}
}
