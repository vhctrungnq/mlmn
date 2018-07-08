package vn.com.vhc.vmsc2.statistics.web.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vn.com.vhc.vmsc2.statistics.domain.DyBscCore;
import vn.com.vhc.vmsc2.statistics.domain.DyMscQos;



public class Diagram {
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public static String CSCore(List<DyBscCore> dyBscCoreList, List<DyMscQos> dyMscQosList) {
		String text = "";

		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		String uri = "file://" + System.getProperty("jboss.server.home.dir") + "/data/CSCore.svg";

		try {
			Document doc = f.createDocument(uri);

			Element svg = doc.getDocumentElement();

			text = element2Text(svg);

			for (DyBscCore dyBscCore : dyBscCoreList) {
				text = text.replace("<v:tabList/>" + dyBscCore.getBscid() + "</text>", bscContent(dyBscCore));
			}

			for (DyMscQos dyMscQos : dyMscQosList) {
				text = text.replace("<v:tabList/>" + dyMscQos.getMscid() + "<v:newlineChar/>", mscContent(dyMscQos));
			}

		} catch (IOException e1) {
		}

		return text;
	}

	public static String PSCore(List<DyBscCore> dyBscCoreList) {
		String text = "";

		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		String uri = "file://" + System.getProperty("jboss.server.home.dir") + "/data/PSCore.svg";

		try {
			Document doc = f.createDocument(uri);

			Element svg = doc.getDocumentElement();

			text = element2Text(svg);

			for (DyBscCore dyBscCore : dyBscCoreList) {
				text = text.replace("<v:tabList/>" + dyBscCore.getBscid() + "</text>", bscContent(dyBscCore));
			}

		} catch (IOException e1) {
		}

		return text;
	}

	private static String bscContent(DyBscCore dyBscCore) {
		String text = "<v:tabList/>";
		text += "<a xlink:href=\"/VMSC2-Statistics/report/core/bsc/dy/main.htm?bscid=" + dyBscCore.getBscid() + "&amp;day="
				+ df.format(dyBscCore.getDay()) + "\" target=\"_top\">" + dyBscCore.getBscid() + "</a> ";
		text += "<tspan class=\"st31\">(" + Helper.float2String(dyBscCore.getCssr()) + ")</tspan>";
		text += "</text>";

		return text;
	}

	private static String mscContent(DyMscQos dyMscQos) {
		String text = "<v:tabList/><a xlink:href=\"/VMSC2-Statistics/report/core/msc/dy/main.htm?mscid=" + dyMscQos.getMscid() + "&amp;day="
				+ df.format(dyMscQos.getDay()) + "\" target=\"_top\">" + dyMscQos.getMscid() + "</a><v:newlineChar/><v:paragraph v:bulletSize=\"0.166667\"/>";
		text += "<tspan x=\"4\" dy=\"1.26em\" class=\"st26\">MCSSR</tspan><tspan class=\"st26\">: " + Helper.float2String(dyMscQos.getMcssr())
				+ "<v:newlineChar/></tspan>";
		text += "<tspan x=\"4\" dy=\"1.2em\" class=\"st26\">LUSR   </tspan><tspan class=\"st26\">: " + Helper.float2String(dyMscQos.getLusr())
				+ "<v:newlineChar/></tspan>";
		text += "<tspan x=\"4\" dy=\"1.2em\" class=\"st26\">PSR     </tspan><tspan class=\"st26\">: " + Helper.float2String(dyMscQos.getPsr())
				+ "<v:newlineChar/></tspan>";
		text += "<tspan x=\"4\" dy=\"1.2em\" class=\"st26\">HSR    </tspan><tspan class=\"st26\">: " + Helper.float2String(dyMscQos.getHsr())
				+ "<v:newlineChar/></tspan>";
		text += "<tspan x=\"4\" dy=\"1.2em\" class=\"st26\">SMSSR</tspan><tspan class=\"st26\">: " + Helper.float2String(dyMscQos.getSmssr())
				+ "<v:newlineChar/></tspan>";
		text += "<tspan x=\"4\" dy=\"1.2em\" class=\"st26\">AUSR </tspan><tspan	class=\"st26\">: " + Helper.float2String(dyMscQos.getAusr()) + "</tspan>";

		return text;
	}

	private static String element2Text(Element e) {
		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			StringWriter buffer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(e), new StreamResult(buffer));

			return buffer.toString();
		} catch (TransformerConfigurationException ex) {
		} catch (TransformerException ex) {
		}

		return "";
	}
}
