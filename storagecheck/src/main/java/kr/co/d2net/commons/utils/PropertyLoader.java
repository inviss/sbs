package kr.co.d2net.commons.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyLoader {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Properties property;
	private String propertyName;
	private HashMap<String, Object> cache;
	private static PropertyLoader propertyLoader;
	
	private PropertyLoader() {}
	
	public static final HashMap<String, Object> getPropertyHash(String _propertyName) {

		if(propertyLoader == null)
			propertyLoader = new PropertyLoader();
			propertyLoader.propertyName = "/config/" + _propertyName + ".properties";
			
			propertyLoader.loadProperties();
		
		return propertyLoader.cache;
		
	}
	
	public static final Properties getProperty(String _propertyName) {
		if(propertyLoader == null)
			propertyLoader = new PropertyLoader();
		propertyLoader.propertyName = "/config/" + _propertyName + ".properties";
		propertyLoader.loadProperties();
		return propertyLoader.property;
	}
	
	private void loadProperties() {
		InputStream inStream = null;
		property = new Properties();
		cache = new HashMap<String, Object>();
		try {
			logger.debug("Input param property name: "+ propertyName);
			inStream = getClass().getResourceAsStream(propertyName);
			if(inStream != null) {
				property.load(inStream);
				inStream.close();
			}
		} catch(Throwable t) {
			logger.error(propertyName+" file load error!");
			if(inStream != null) {
				try {
					inStream.close();
				} catch(Throwable u) { }
			}
		}
		
		if(property.size() < 1)
			return;
		
		synchronized(cache) {
			String key;
			for(Enumeration names = property.keys(); names.hasMoreElements(); cache.put(key, property.getProperty(key))) {
				key = (String)names.nextElement();
				logger.debug("key: "+ key + ", value: "+property.getProperty(key));
			}
		}
		
	}
}
