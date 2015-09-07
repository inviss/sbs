package com.sbs.das.commons.convertor;

import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter {

	private Logger logger = Logger.getLogger(DateConverter.class);
	
	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		if (obj != null){ 
			try {
				writer.setValue(String.valueOf(obj));
			} catch (Exception e) {
			}
		} else { 
			writer.startNode(""); 
			writer.endNode();
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Object o = null;
		if ( StringUtils.isNotBlank(reader.getValue())) {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("String value: "+URLDecoder.decode(reader.getValue(), "UTF-8"));
				}
				o = java.sql.Date.valueOf(URLDecoder.decode(reader.getValue(), "UTF-8")); 
			} catch (Exception e) {
				logger.debug("TimeStamp Error value: "+reader.getValue());
			}
			
		}
		return o;
	}

	public boolean canConvert(Class cls) {
		return cls.equals(java.sql.Date.class);
	}

}
