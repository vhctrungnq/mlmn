package vn.com.vhc.alarm.core;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class AL_Global {
	private static Logger logger = Logger.getLogger(AL_Global.class.getName());
	public static final Properties SYSTEM_CONFIG = new Properties();
	public static final Properties MAIL_CONFIG = new Properties();
	public static final Properties MAIL_SYSTEM_CONFIG = new Properties();
	public static final Properties SHIFT_CONFIG = new Properties();
	public static final Properties UPLOAD_CONFIG = new Properties();
	
	
	public static final String SEPARATOR = "/";
	public static DataSource DATA_SOURCE = null;
	public static final String ROOT_LOG_DIR = System.getProperty("jboss.server.home.dir") + "/log";
	public static final String IMPORT_ERR = "[IMP-ERR] ";

	static {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			DATA_SOURCE = (DataSource) ctx.lookup("java:/vmsc6_alarm");
		} catch (NamingException e) {
			logger.error("Cannot init Datasource: " + e.getMessage());
		}
	}
}
