package vn.com.vhc.alarm.util.telnet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.Properties; 


public class AppConfig {
	private static final String CONF_PATH = "config"; 
	private static final String CONF_FILE_DB = "db-config.properties";
	
	private static Properties properties = null;
	
	/**
	 * Gets {@link Properties} of application.
	 * 
	 * @return {@link Properties} object.
	 * @throws AppConfigException
	 */
	public synchronized Properties getPropertiesConfig() throws AppConfigException {
		
		if (properties == null) {
			try {
				//D:\VHC_Project\vms6_20131118\VMSC6-Alarm-Core\build\classes\conf\db-config.properties
				//String configFile = "D:/VHC_Project/vms6_20131118/VMSC6-Alarm-Core/build/classes/config/" +CONF_FILE_DB;
				String configFile = getLocation(CONF_PATH) + "/" + CONF_FILE_DB;
				properties = new Properties(); 
				
				// auto reload config when content is changed 
				try {
					properties.load(new FileInputStream(configFile));
				//	properties.load(this.getClass().getClassLoader().getResourceAsStream(configFile));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
				
			} catch (Exception e) {
				throw new AppConfigException("Can not load app configuration file.", e);
			}
		} 
		return properties;
	}
	
	/**
	 * Get path of config file
	 * @param location
	 * @return
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public static String getLocation(String location) throws MalformedURLException, UnsupportedEncodingException {
		String result = "";

		CodeSource src = AppConfig.class.getProtectionDomain().getCodeSource();

		URL url = new URL(src.getLocation(), location);
		result = URLDecoder.decode(url.getPath(), "utf-8");

		return result;
	}
	
/*	Properties configFile;
	   public AppConfig()
	   {
	    configFile = new java.util.Properties();
	    try {
	      configFile.load(this.getClass().getClassLoader().
	      getResourceAsStream("myapp/config.cfg"));
	    }catch(Exception eta){
	        eta.printStackTrace();
	    }
	   }
	 
	   public String getProperty(String key)
	   {
	    String value = this.configFile.getProperty(key);
	    return value;
	   }*/
}
