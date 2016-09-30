package com.app.das.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *   XML파서 정의
 * @author asura207
 *
 */
public abstract class DOXml {

	Object DO;
	
	public Object setDO(Object pDo) {
		DO = pDo;	
		return DO;
	}
	
	public Object getDO() {
		return DO;
	}
	
	
	protected Document getDocument(String pXMLContents) {
		Document doc = null;
		try {
			DocumentBuilderFactory mDocBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder mDocBuilder = mDocBuilderFactory.newDocumentBuilder();
			if (pXMLContents == null) {
				doc = mDocBuilder.newDocument();
			} else {
				InputStream _is = new ByteArrayInputStream(pXMLContents.getBytes("utf-8"));
				doc = mDocBuilder.parse(_is);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;		
	}
	
	//Reader r = new StringReader(new String(response));
	//docBuild.parse(new InputSource(r));


	
	protected String getNodeValue(Node pNode) {
		Node _node = pNode.getChildNodes().item(0);
		if(_node == null) {
			return new String();
		}
		return _node.getNodeValue();
	}
	
	
	
	// xml로 만들기 위해 <>등의 문자열 처리를 한다.
	protected String replaceParse(String str)
	{
		String strResult = str;
		strResult = strResult.replaceAll("&", "&amp;");
		strResult = strResult.replaceAll("<", "&lt;");
		strResult = strResult.replaceAll(">", "&gt;");
		
		return strResult;
	}
	
	protected NodeList collectNodes(Document doc, Set<String> filteredNames) {
		Node ret = doc.createElement("NodeList");
		collectNodes(doc, filteredNames, ret);
		return ret.getChildNodes();
	}

	protected void collectNodes(Node node, Set<String> filteredNames, Node ret) {
		NodeList chn = node.getChildNodes();
		for (int i = 0; i < chn.getLength(); i++) {
			Node child = chn.item(i);
			if (filteredNames.contains(child.getNodeName())) {
				ret.appendChild(child);
			}
			collectNodes(child, filteredNames, ret);
		}
	}
	
	abstract public Object setDO(String _xml);
	abstract public Object setData(Element pElement);		
	abstract public String toXML();	
}
