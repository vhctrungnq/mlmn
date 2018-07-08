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
NAME:       InventoryHuawei2GConverter
PURPOSE:	Convert file inventory huawei bsc, bts

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        25/09/2013       AnhNT      	1. Ten file Huawei BSC: W_OMC_BSC6900GSM_.3221229568.3221274624.3221286916_BLATA2H.xml
										2. Ten file Huawei BTS: W_OMC_GSMBTS_.3221229568.3221274624.3221286913.3223113728.3222994944_LAMH23.xml
******************************************************************************/
public class InventoryHuawei2GConverter extends IN_BasicConverter {
	
	private String sep = ";";
	private static Logger logger = Logger.getLogger(InventoryHuawei2GConverter.class.getName());
	
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		 
		BufferedWriter writer = null; 
		String ne = "";
		String neType = "";
		String attrname = "";
		try {
			File outFile = new File(direcPath, file.getName()); 
			writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName())); 
			writer.write("#VENDOR;NE_TYPE;NE;BOARD_NAME;BOARD_TYPE;PRODUCT_DATE;SERIAL_NUMBER;RACK;SUBRACK;SLOT;SW_VERSION\n");
			
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});
			// parse file
			Document doc = docBuilder.parse(file);
			Node dataPacket_node = doc.getElementsByTagName("DATAPACKET").item(0);
			//Element dataPacket_element = (Element) dataPacket_node;
			// Lay danh sach tat ca node con thuoc node cha "<DATAPACKET"
			NodeList dataPacket_nodeList = dataPacket_node.getChildNodes(); 
			//Duyet tat bo node con thuoc node cha "<DATAPACKET"
			for(int i = 0; i < dataPacket_nodeList.getLength(); i++){
				// Doc du lieu node con thu k
				Node dataPacket_nodeChild = dataPacket_nodeList.item(i);  
				if (dataPacket_nodeChild.getNodeType() == 3) {
					continue;
				} 
				
				if (dataPacket_nodeChild != null && dataPacket_nodeChild.getNodeName().equalsIgnoreCase("NE")) {
					Element ne_element = (Element) dataPacket_nodeChild;
					// Lay gia tri NEName cua node "<NE"
					ne =  ne_element.getAttribute("NEName");
					// Lay gia tri cua NEType node "<NE"
					neType = ne_element.getAttribute("NEType");
					if(neType.contains("BSC")){
						neType = "BSC";
					}else if(neType.contains("BTS")){
						neType = "BTS";
					}
					continue;
				}
				
				if (dataPacket_nodeChild != null && dataPacket_nodeChild.getNodeName().equalsIgnoreCase("TABLES")) {
					//Element tables_element = (Element) dataPacket_nodeChild; 
					NodeList tables_nodeList = dataPacket_nodeChild.getChildNodes(); 
					//Duyet tat ca node con thuoc node cha "<TABLES"
					for(int j = 0; j < tables_nodeList.getLength(); j++){ 
						//Doc thong tin node con thu j
						Node tables_node = tables_nodeList.item(j); 
						if (tables_node.getNodeType() == 3) {
							continue;
						}
						
						if(tables_node != null && tables_node.getNodeName().equalsIgnoreCase("TABLE")){
							Element table_element = (Element) tables_node;
							// Lay gia tri attrname cua node "<TABLE"
							attrname =  table_element.getAttribute("attrname");
							
							if(attrname.equalsIgnoreCase("Board")){
								extractMeasData(neType,ne,writer, tables_node);
							}else{
								continue;
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

	private void extractMeasData(String neType,String ne, BufferedWriter writer, Node measData) throws Exception {
		// Doc node con "<ROWDATA" thuoc node cha measData
		Node measInfo = getChildByNodeName(measData, "ROWDATA");
		
		String subrack = "";
		String serial_number = "";
		String product_date = "";
		String slot = "";
		String board_name = "";
		String rack = "";
		String board_type = "";
		String softVer = "";

		NodeList nodeList = measInfo.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node n = nodeList.item(index);
			if (n.getNodeType() == 3) {
				continue;
			}
			
			if(n != null && n.getNodeName().equalsIgnoreCase("ROW")){
				Element row_element = (Element) n;
				board_name = row_element.getAttribute("BoardName");
				product_date = row_element.getAttribute("DateOfManufacture");
				subrack = row_element.getAttribute("FrameNo");
				rack = row_element.getAttribute("RackNo");
				serial_number = row_element.getAttribute("SerialNumber");
				slot = row_element.getAttribute("SlotNo");
				board_type = row_element.getAttribute("BoardType");
				softVer = row_element.getAttribute("SoftVer");
				String line = "HUAWEI"+sep+neType+sep+ne+sep +board_name +sep+board_type+sep +product_date +sep +serial_number +sep +rack +sep + subrack+sep+slot+sep+softVer;
				writer.write(line); 
				writer.newLine();
			}
		}    
	}
	 
	private Node getChildByNodeName(Node node, String name) {
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node n = nodeList.item(index);
				if (n != null && n.getNodeName().equalsIgnoreCase(name)) {
					return n ;
				}
			}
		}
		return null;
	}
}
