package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
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
NAME:       InventoryEricssonConverter
PURPOSE:	Convert file inventory ericsson rnc, nodeB

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        22/08/2013       AnhNT      		1. Raw file format .xml
******************************************************************************/
public class InventoryEricsson3GConverter extends IN_BasicConverter {
	
	private String sep = ";";
	private static Logger logger = Logger.getLogger(InventoryEricsson3GConverter.class.getName());
	
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		 
		BufferedWriter writer = null;
		String neParent = ""; //Ten BSC, RNC ( Doi voi inventory o muc tram, nguoc lai se ko co)
		String ne = "";//Ten site, bsc,rnc, msc, sgsn, mgw.. (VD: BBDTA1N)
		String day = "";
		String neType = "";
		try { 
			File outFile = new File(direcPath, file.getName()); 
			writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName())); 
			writer.write("#DAY;VENDOR;NE_TYPE;NE;NE_PARENT;PRODUCT_CODE;SERIAL_NUMBER;PRODUCT_DATE;SLOT;BOARD_NAME\n");
			
			//
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});
			// parse file
			Document doc = docBuilder.parse(file);
			
			// Ngay lay du lieu
			Node fileFooter = doc.getElementsByTagName("fileFooter").item(0);
			Element fileFooter_Element = (Element) fileFooter;
			day = fileFooter_Element.getAttribute("dateTime").substring(0, 10); 
			Node nodeParent = doc.getElementsByTagName("xn:SubNetwork").item(0);
			NodeList nodeSnList = nodeParent.getChildNodes();
			System.out.println( nodeSnList.getLength());
			for(int s = 0; s< nodeSnList.getLength(); s++){
				
			
			
			//Doc khoi node co ten "xn:SubNetwork" xuat hien lan 2
			Node nodeSN = nodeSnList.item(s);
			if (nodeSN.getNodeType() == 3) {
				continue;
			} 
			/*Node nodeSN = doc.getElementsByTagName("xn:SubNetwork").item(1);*/
			Element eElementSN = (Element) nodeSN;
			// Lay ten neParent
			neParent =  eElementSN.getAttribute("id");
			// Lay danh sach tat ca node con thuoc node cha "<xn:SubNetwork"
			NodeList nodeSNList = nodeSN.getChildNodes(); 
			//Duyet tat bo node con thuoc node cha "<xn:SubNetwork"
			for(int i = 0; i < nodeSNList.getLength(); i++){
				// Doc du lieu node con thu k
				Node nodeChildSN = nodeSNList.item(i);  
				if (nodeChildSN.getNodeType() == 3) {
					continue;
				} 
				if (nodeChildSN != null && nodeChildSN.getNodeName().equalsIgnoreCase("xn:ManagedElement")) {
					Element eElementME = (Element) nodeChildSN;
					// Lay gia tri id cua node "<xn:ManagedElement"
					ne =  eElementME.getAttribute("id");
					if(neParent.equals(ne)){
						neParent = "";
						neType = "RNC";
					}else{
						neType = "NODEB";
					}
					
					// Danh sach tat ca node con thuoc node cha "<xn:ManagedElement"
					NodeList nodeListME = nodeChildSN.getChildNodes(); 
					//Duyet tat ca node con thuoc node cha "<xn:ManagedElement"
					for(int j = 0; j < nodeListME.getLength(); j++){ 
						//Doc thong tin node con thu j
						Node node = nodeListME.item(j); 
						if (node.getNodeType() == 3) {
							continue;
						}
						
						if(node != null && node.getNodeName().equalsIgnoreCase("in:InventoryUnit")){
							//Doc thong tin node "<in:InventoryUnit"
							Node nodeIU = nodeListME.item(j); 
							//Danh sach tat ca nhung node con thuoc node cha "<in:InventoryUnit"
							NodeList nodeIUList = nodeIU.getChildNodes(); 
							//Duyet tat ca danh sach node con thuoc node cha "<in:InventoryUnit"
							for (int k = 0; k < nodeIUList.getLength(); k++) { 
								Node n = nodeIUList.item(k); 
								if (n.getNodeType() == 3) {
									continue;
								}
								//Doc thong tin node "<in:InventoryUnit"
								if (n != null && n.getNodeName().equalsIgnoreCase("in:InventoryUnit")) {
									try { 
										extractMeasData(day,neType,neParent,ne,writer, n);
									} catch (Exception e) {
										logger.warn("Extract data faiule in node at k: " +k);
									}
								}
							}
						}
					}
				}
			} 
			}
		} catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMS-0306", e);
		}finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		logger.info("Convert file: " + file.getName() + " success");
	}

	private void extractMeasData(String day,String neType,String neParent, String ne, BufferedWriter writer, Node measData) throws Exception {
		// Doc node con "<in:attributes" thuoc node cha measData
		Node measInfo = getChildByNodeName(measData, "in:attributes");
		
		String product_code = "";
		String serial_number = "";
		String product_date = "";
		String slot = "";
		String broad_name = "";

		NodeList nodeList = measInfo.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node n = nodeList.item(index);
			if (n.getNodeType() == 3) {
				continue;
			}
			// lay thong tin product code chua trong node "<in:vendorUnitTypeNumber"
			if (n.getNodeName().equalsIgnoreCase("in:vendorUnitTypeNumber")) {
				product_code = n.getTextContent();
			}
			 /*lay thong tin serial number chua trong node "<in:serialNumber" 
			 Neu thong tin serial number = "N/A" khong ghi vao database*/
			if (n.getNodeName().equalsIgnoreCase("in:serialNumber")) {
				serial_number = n.getTextContent();
				if(serial_number.equalsIgnoreCase("N/A")){
					serial_number = "";
				}
			} 
			// lay thong tin ngay san xuat chua trong node "<in:dateOfManufacture"
			if(n.getNodeName().equalsIgnoreCase("in:dateOfManufacture")){
				product_date = n.getTextContent();
			}
			//lay thong tin slot chua trong node "<in:unitPosition"
			if(n.getNodeName().equalsIgnoreCase("in:unitPosition")){
				slot = n.getTextContent();
			}
			//lay thong tin broad name chua trong node "<in:manufacturerData"
			if(n.getNodeName().equalsIgnoreCase("in:manufacturerData")){
				broad_name = this.getBoard( n.getTextContent());
			}
		} 
		
		String line = day+sep+"ERICSSON"+sep+neType+sep+ne +sep +neParent+sep +product_code +sep +serial_number +sep +product_date +sep +slot +sep + broad_name;
		writer.write(line); 
		writer.newLine();
		
		//return 1;
	}
	
	private String getBoard(String moid)
	{
		if(moid.contains(",")){
			moid = moid.substring(0,moid.lastIndexOf(","));
		}
		String board = moid.substring(moid.lastIndexOf("=")+1,moid.length());
		return board;
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
}
