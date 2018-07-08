package vn.com.vhc.vms.vmsc2.web.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Welcome {
	@RequestMapping("/welcome")
	public ModelAndView hello( ModelMap model) {
		String region = getCenter("6");
		int linkNumber =0;
		linkNumber = getLinkNumber(linkNumber);
		String link = "";
		for(int i =0; i<linkNumber; i++)
		{
			link += "<a class=\"dock-item2\" href=\""+getLink(i)+"\"<span>"+ getSpan(i)+"</span><img src=\""+getSrc(i)+"\" alt=\""+getAlt(i)+"\"/></a>";
		}
		model.addAttribute("link", link);
		model.addAttribute("region", region);
		return new ModelAndView("welcome");
	}
	public String getCenter(String dfv) {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty("TT");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return dfv;
	}
	public int getLinkNumber( int linkNumber) {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return Integer.parseInt(props.getProperty("LinkNumber"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return linkNumber;
	}
	public String getLink(int i) {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty("Link"+i);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return "notlink";
	}
	public String getSpan(int i) {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty("Span"+i);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return "notspan";
	}
	public String getAlt(int i) {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty("Alt"+i);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return "notalt";
	}
	public String getSrc(int i) {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty("Src"+i);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return "notsrc";
	}
}
