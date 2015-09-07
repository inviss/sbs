package com.sbs.das.commons.convertor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DoubleConverter implements Converter {

	private Logger logger = Logger.getLogger(DoubleConverter.class);

	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		if (obj != null){
			if(logger.isDebugEnabled()) {
				logger.debug("marshal value: "+String.valueOf(obj));
			}
			writer.setValue(String.valueOf(obj));
		} else { 
			writer.startNode(""); 
			writer.endNode();
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		if ( StringUtils.isNotBlank(reader.getValue())) {
			if(logger.isDebugEnabled()) {
				logger.debug("unmarshal value: "+reader.getValue());
			}
			return Long.valueOf(reader.getValue()); 
		} else { 
			return Long.valueOf(0); 
		}
	}

	public boolean canConvert(Class cls) {
		return cls.equals(Double.class); 
	}

}
