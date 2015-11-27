package test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String _xml = FileUtils.readFileToString(new File("D:/aaa.xml"), "utf-8");
			Document _document = getDocument(_xml);
			Element _rootElement = _document.getDocumentElement();
			NodeList _nodeList = _rootElement.getChildNodes();
			int _length = _nodeList.getLength();
			for(int i = 0; i < _length; i++) {
				Node _node = _nodeList.item(i);
				String _nodeName = _node.getNodeName() ;
				System.out.println(_nodeName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Document getDocument(String pXMLContents) {
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

}
