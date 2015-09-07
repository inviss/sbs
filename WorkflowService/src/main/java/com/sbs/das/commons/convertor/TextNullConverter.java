package com.sbs.das.commons.convertor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 문자열에 대해서 URLEncod 처리를 하지않고 Null 처리만 담당하는 클래스
 */
public class TextNullConverter implements Converter {

	private Logger logger = Logger.getLogger(TextNullConverter.class);

	public void marshal(Object obj, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		if (obj != null){
			if(logger.isDebugEnabled()) {
				logger.debug("marshal value: "+(String)obj);
			}
			writer.setValue((String)obj);
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
			return reader.getValue(); 
		} else { 
			return ""; 
		}
	}

	public boolean canConvert(Class cls) {
		return cls.equals(String.class); 
	}

	private String escapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&.*")) {
			value = value.replaceAll("&", "&amp;");
		}
		if (value.matches(".*'.*")) {
			value = value.replaceAll("\''", "&apos;");
		}
		if (value.matches(".*<.*")) {
			value = value.replaceAll("<", "&lt;");
		}
		if (value.matches(".*>.*")) {
			value = value.replaceAll(">", "&gt;");
		}
		if (value.matches(".*\".*")) {
			value = value.replaceAll("'", "&quot;");
		}
		return value;
	}

	private String unescapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&amp;.*")) {
			value = value.replaceAll("&amp;", "&");
		}
		if (value.matches(".*&apos;.*")) {
			value = value.replaceAll("&apos;", "\'");
		}
		if (value.matches(".*&lt;.*")) {
			value = value.replaceAll("&lt;", "<");
		}
		if (value.matches(".*&gt;.*")) {
			value = value.replaceAll("&gt;", ">");
		}
		if (value.matches(".*&quot;.*")) {
			value = value.replaceAll("&quot;", "\"");
		}
		return value;
	}

}
