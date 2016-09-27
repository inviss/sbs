package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ErrorLogDO;
import com.app.das.util.CommonUtl;


/**
 *  ERROR XML파서
 * @author asura207
 *
 */
public class ErrorLogDOXML extends DOXml {
	
	//private static Logger logger = Logger.getLogger(ErrorLogDOXML.class);
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "errorlog";
	/**
	 * 서버명
	 */
	private String XML_NODE_SERER_NM = "server_nm";
	/**
	 * 에러종류
	 */
	private String XML_NODE_ERROR_TYPE = "error_type";

	/**
	 * 발생시각
	 */
	private String XML_NODE_REG_DT= "reg_dt";
	/**
	 * 오류
	 */
	private String XML_NODE_ERROR_CONT = "error_cont";
	/**
	 * 발생시간 시작(검색용)
	 */
	private String XML_NODE_START_REG_DT= "start_reg_dt";
	
	/**
	 * 발생시간 종료(검색용) 
	 */
	private String XML_NODE_END_REG_DT= "end_reg_dt";
	/**
	 * 총페이지
	 */
	private String XML_NODE_TOTAL_COUNT= "totalcount";
	/**
	 * 시작페이지
	 */
	private String XML_NODE_START_PAGE= "start_page";

	
	/**
	 * ip
	 */
	private String XML_NODE_IP= "ip";

	public Object setDO(String _xml) {
		setDO(new ErrorLogDO());
		
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
			}
        }
		
		return getDO();
	}
	
	
	public Object setData(Element pElement) {
		ErrorLogDO infoDO = (ErrorLogDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_SERER_NM)) {
				infoDO.setServer_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ERROR_TYPE)) {
				infoDO.setError_type(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				
				infoDO.setReg_dt(_nodeValue);
				
			}
			else if(_nodeName.equals(XML_NODE_ERROR_CONT)) {
				
					infoDO.setError_cont(_nodeValue);
			
			}
			else if(_nodeName.equals(XML_NODE_START_REG_DT)) {
				infoDO.setStart_reg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_END_REG_DT)) {
				infoDO.setEnd_reg_dt(_nodeValue);
			}
			
			else if(_nodeName.equals(XML_NODE_START_PAGE)) {
				infoDO.setStart_page(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			
        }
	    
	    return infoDO;
	}
	
	
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}

	public String getSubXML() {
		ErrorLogDO infoDO = (ErrorLogDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_SERER_NM + ">" + infoDO.getServer_nm()+ "</"  + XML_NODE_SERER_NM + ">");
		_xml.append("<" + XML_NODE_ERROR_TYPE + ">" + infoDO.getError_type() + "</"  + XML_NODE_ERROR_TYPE + ">");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getReg_dt() + "</"  + XML_NODE_REG_DT + ">");
		_xml.append("<" + XML_NODE_ERROR_CONT + ">" + infoDO.getError_cont() + "</"  + XML_NODE_ERROR_CONT + ">");
		_xml.append("<" + XML_NODE_IP + ">" + infoDO.getIp()+ "</"  + XML_NODE_IP + ">");
		_xml.append("<" + XML_NODE_TOTAL_COUNT + ">" + infoDO.getTotalcount()+ "</"  + XML_NODE_TOTAL_COUNT + ">");
		


		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
	
}
