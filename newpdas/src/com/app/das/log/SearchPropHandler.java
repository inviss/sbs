package com.app.das.log;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.util.StringUtils;

/**
 * 우리 시스템의 환경 설정 파일인 search.properties 파일을 로드해서 HashMap으로 가지고 있다.
 * @author dekim
 *
 */
public class SearchPropHandler 
{
	private static SearchPropHandler instance;
	
	private static HashMap errorMap = new HashMap();
	
	private static Logger logger = Logger.getLogger(SearchPropHandler.class);
	
	static
	{
		load();
	}

	private SearchPropHandler() {}

	public static SearchPropHandler getInstance() 
	{
		if (instance == null) 
		{
			synchronized (SearchPropHandler.class) 
			{
				if (instance == null) 
				{
					instance = new SearchPropHandler();
				}
			}
		}

		return instance;
	}
	/**
	 * search.properties의 정보를 로드해서 특정 키에대한 값을 넘겨준다.
	 * @param key error.properties의 Key 정보
	 * @return String key에 해당하는 value 정보
	 */
	private static void load()
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[Start search.properties LOAD]");
		}
		
		if(errorMap == null || errorMap.isEmpty())
		{
			ResourceBundle bundle = ResourceBundle.getBundle(DASBusinessConstants.PropertiesFileName.SEARCH_CONFIG);
			Enumeration en = bundle.getKeys();
			
			while(en.hasMoreElements())
			{
				String key = (String)en.nextElement();
					
				errorMap.put(key, StringUtils.encodingKo(bundle.getString(key)));
			}
		}
		
		if(logger.isDebugEnabled())
		{
			logger.debug("[End search.properties LOAD]");
		}
	}
	
	/**
	 * search.properties의 정보를 로드해서 특정 키에대한 값을 넘겨준다.
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

}
