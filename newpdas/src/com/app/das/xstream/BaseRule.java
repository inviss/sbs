package com.app.das.xstream;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class BaseRule implements Serializable {
	
	

	/**
	 * XML 문자열로 변환
	 */
	protected String toXML() {
		XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("__", "_");
		XStream xstream = new XStream(new DomDriver("UTF-8",replacer));
		Annotations.configureAliases(xstream, getClass());
		Field[] fields = getClass().getDeclaredFields();
		for (Field f : fields) {
			Annotations.configureAliases(xstream, f.getType());
		}
		String xmlString = xstream.toXML(this);
		xstream = null;
		return xmlString;
	}

	/**
	 * XML 데이터로부터 Rule Build
	 */
	public static IXmlRule fromXML(String xmlString, Class clazz) {
		try {
			XStream xstream = new XStream();
			Annotations.configureAliases(xstream, clazz);
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				Annotations.configureAliases(xstream, f.getType());
			}
			IXmlRule rule = (IXmlRule) xstream.fromXML(xmlString);
			xstream = null;
			return rule;
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * xml을 받아서 파라미터로 Object로 변환
	 */
	public static Object fromXML(String xml) {
		XStream xstream = new XStream();
		return xstream.fromXML(xml);
	}
	
}
