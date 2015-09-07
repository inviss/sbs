package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.PreviewDO;
import com.app.das.util.CommonUtl;





/**
 *   프리뷰 NOTE 정보 관련 XML파서
 * @author asura207
 *
 */

public class PreviewNoteDOXML extends DOXml {
	/**
	 * xml해더
	 */ 
	private String XML_NODE_HEAD = "previewnote";
	/**
	 * xml해더
	 */ 
	private String XML_NODE_HEAD2 = "sbsdas";
	/**
	 * segment
	 */ 
	private String XML_NODE_SEGMENT="segment";
	/**
	 * markin
	 */ 
	private String XML_NODE_MARKIN="markin";

	
	
	public Object setDO(String _xml) {
		setDO(new PreviewDO());
		
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			
			/*	
		NodeList node2= _node.getChildNodes();
			Node _node2 = node2.item(i);
			String _nodeName2 = _node2.getNodeName() ;
			NodeList node3= _node2.getChildNodes();
			Node _node3 = node3.item(i);
			String _nodeName3 = _node3.getNodeName() ;*/
			
			
			if(_nodeName.equals(XML_NODE_HEAD)) {
			/*	NodeList node2= _node.getChildNodes();
				Node _node2 = node2.item(i);
				String _nodeName2 = _node2.getNodeName() ;*/
				
				setData((Element)_node);
				
			}
        }
		
		return getDO();
	}
	
	public Object setData(Element pElement) {
		PreviewDO previewDO = (PreviewDO)getDO();
		String markin ="";
		String cont = "";
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			NamedNodeMap startAttr = _node.getAttributes();
			 if(_nodeName.equals(XML_NODE_SEGMENT)) {
				
				 for(int k = 0; k<startAttr.getLength();k++){
					 
					 Node attr = startAttr.item(k);
					 String nodeName = attr.getNodeName() ;
					 String att= attr.getNodeValue();
//					System.out.println("###########  " + nodeName);
//					System.out.println("###########  " + att);
//					System.out.println("###########  " + _nodeValue);
						if(nodeName.equals(XML_NODE_MARKIN)){
							 markin += "," + attr.getNodeValue();
						  
						}
						
				
				 }
				 cont += "," + _nodeValue;
			 }
				 previewDO.setPreview_subj(markin);
				 previewDO.setPreivew_cont(cont);
	    }
	  
	    return previewDO;
	}
	    
	    
	    
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		//_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}
	
	
/*	public String getSubXML() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();
		
		String _xml =   "<" + XML_NODE_HEAD + "> \n";	
		
		
		_xml = _xml + "<" + XML_NODE_ROLE_CD + ">" +roleInfoDO.getRole_id()+ "</"  + XML_NODE_ROLE_CD + "> \n";
		
	
		_xml = _xml + "</" + XML_NODE_HEAD + ">";
	
		
		
		return _xml;
}*/
	
	
	
	

}
