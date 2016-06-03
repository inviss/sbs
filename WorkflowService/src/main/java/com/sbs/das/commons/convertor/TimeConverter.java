package com.sbs.das.commons.convertor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TimeConverter implements Converter {

	private Logger logger = Logger.getLogger(TimeConverter.class);
	
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
				o = reader.getValue().replaceAll("[\\D]", "");
				if(logger.isDebugEnabled()) {
					logger.debug("input msg: "+reader.getValue()+", replace msg: "+o);
				}
			} catch (Exception e) {
				return "";
			}
			
		}
		return o;
	}

	public boolean canConvert(Class cls) {
		return cls.equals(java.lang.String.class);
	}

}
