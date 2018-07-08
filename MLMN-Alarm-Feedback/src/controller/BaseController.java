package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {
	//public static final String MESSAGES_KEY = "successMessages";
	public static final String MESSAGES_KEY = "successMessagesKey";
	public static final String MESSAGES_TITLE = "successMessagesTitle";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveError(HttpServletRequest request, String error) {
		List errors = (List) request.getSession().getAttribute("errors");
		if (errors == null) {
			errors = new ArrayList();
		}
		errors.add(error);
		request.getSession().setAttribute("errors", errors);
	}

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveMessage(HttpServletRequest request, String msg) {
		List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);

		if (messages == null) {
			messages = new ArrayList();
		}

		messages.add(msg);
		request.getSession().setAttribute(MESSAGES_KEY, messages);
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveMessageKey(HttpServletRequest request, String msg) {
		List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);

		if (messages == null) {
			messages = new ArrayList();
		}

		messages.add(msg);
		request.getSession().setAttribute(MESSAGES_KEY, messages);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveMessage(HttpServletRequest request, String msg) {
		List messages = (List) request.getSession().getAttribute(MESSAGES_TITLE);

		if (messages == null) {
			messages = new ArrayList();
		}

		messages.add(msg);
		request.getSession().setAttribute(MESSAGES_TITLE, messages);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	public String getCenter(String dfv) {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty("center");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return dfv;
	}

}
