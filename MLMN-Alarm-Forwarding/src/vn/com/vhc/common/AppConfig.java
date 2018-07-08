// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import vn.com.vhc.exception.AppConfigException;

/**
 * Application config
 * 
 * @author datnh
 *
 */
public class AppConfig {
	
	private static final String CONF_PATH = "conf";
	private static final String CONF_FILE = "conf.properties";
	private static final String BONECP_CONF_FILE_DB1 = "db1-config.properties";
	private static final String BONECP_CONF_FILE_DB2 = "db2-config.properties";
	
	private static PropertiesConfiguration propertiesConfiguration = null;

	/**
	 * Gets {@link PropertiesConfiguration} of application.
	 * 
	 * @return {@link PropertiesConfiguration} object.
	 * @throws AppConfigException
	 */
	public static synchronized PropertiesConfiguration getPropertiesConfig() throws AppConfigException {
		
		if (propertiesConfiguration == null) {
			try {
				String configFile = getLocation(CONF_PATH) + "/" + CONF_FILE;
				
				propertiesConfiguration = new PropertiesConfiguration();
				
				// auto reload config when content is changed
				propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
				propertiesConfiguration.load(configFile);
			} catch (Exception e) {
				throw new AppConfigException("Can not load app configuration file.", e);
			}
		}
		
		return propertiesConfiguration;
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

	/**
	 * Get BoneCP configuration properties.
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Properties getBoneCPConfigProperties(int dbNo) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		String boneCPConfigFile = "";
		
		if (dbNo == 1) {
			boneCPConfigFile = getLocation(CONF_PATH) + "/" + BONECP_CONF_FILE_DB1;
		} else {
			boneCPConfigFile = getLocation(CONF_PATH) + "/" + BONECP_CONF_FILE_DB2;
		}
		
		prop.load(new FileInputStream(boneCPConfigFile));
		
		return prop;
	}
	
}
