package com.app.das.log;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.util.StringUtils;

/**
 * error.properties를 load해서 HashMap에 보관하고 있다.
 * @author ysk523
 *
 */
public class ErrorPropHandler 
{
	private static ErrorPropHandler instance;
	
	private static HashMap errorMap = new HashMap();
	
	private static Logger logger = Logger.getLogger(ErrorPropHandler.class);
	
	static
	{
		load();
	}

	private ErrorPropHandler() {}

	public static ErrorPropHandler getInstance() 
	{
		if (instance == null) 
		{
			synchronized (ErrorPropHandler.class) 
			{
				if (instance == null) 
				{
					instance = new ErrorPropHandler();
				}
			}
		}

		return instance;
	}
	/**
	 * error.properties의 정보를 로드해서 특정 키에대한 값을 넘겨준다.
	 * @param key error.properties의 Key 정보
	 * @return String key에 해당하는 value 정보
	 */
	private static void load()
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[Start error.properties LOAD]");
		}
		
		if(errorMap == null || errorMap.isEmpty())
		{
			ResourceBundle bundle = ResourceBundle.getBundle(DASBusinessConstants.PropertiesFileName.ERROR_MESSAGE);
			Enumeration en = bundle.getKeys();
			
			while(en.hasMoreElements())
			{
				String key = (String)en.nextElement();				
				errorMap.put(key, StringUtils.encodingKo(bundle.getString(key)));
			}
		}
		
		if(logger.isDebugEnabled())
		{
			logger.debug("[End error.properties LOAD]");
		}
	}
	
	/**
	 * error.properties의 정보를 로드해서 특정 키에대한 값을 넘겨준다.
	 * @param key error.properties의 Key 정보
	 * @return String key에 해당하는 value 정보
	 */
	public String getProperty(String key)
	{
		Object value = errorMap.get(key);
		if(value == null)
		{
			value = Constants.BLANK;
		}
		
		return (String)value;
	}
	
	
	
	public static void main(String[] args)
	{
		String bundleName = "log4j";

		ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
		Enumeration en = bundle.getKeys();
			
		while(en.hasMoreElements())
		{
			String key = (String)en.nextElement();
			System.out.println("[Key]" +  key);
			System.out.println("[Value]" + StringUtils.encodingKo(bundle.getString(key)));
			
		}
	}

}
