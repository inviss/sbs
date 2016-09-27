package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TransferDO;
import com.app.das.util.CommonUtl;
/**
 * DAS_TM의  정보 관련 XML파서
 * @author asura207
 *
 */
public class AllTmStatusDOXML extends DOXml{
	TransferDO infoDO;
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "TM_STATUS_INFO";
	/**
	 * 태스크아이디
	 */
	private String XML_NODE_TASK_ID = "TASK_ID";
	/**
	 * 메세지
	 */
	private String XML_NODE_MESSAGE = "MEM";
	/**
	 * 상태값
	 */
	private String XML_NODE_STATUS = "STATUS";
	/**
	 * 진행률
	 */
	private String XML_NODE_PROGRESS = "PROGRESS";
	/**
	 * 장비번호
	 */
	private String XML_NODE_EQ_ID = "EQ_ID";
	/**
	 * 에러코드
	 */
	private String XML_NODE_ERROR_CD = "ERROR_CD";


	/**
	 * 목적지 코드
	 */
	//private String XML_NODE_DESTINATION = "Destination";
	//private Logger logger = Logger.getLogger(ExternalDAO.class);
	public Object getStatus(String _xml){
		//Document _document = getDocument(_xml);
		//Element _rootElement = _document.getDocumentElement();
		//System.out.println("_rootElement"+_rootElement);

		return getDO();
	}

	public Object setDO(String _xml) {
		List result = new ArrayList();
		setDO(result);
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			infoDO = new TransferDO();
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;

			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
			}
			result.add(infoDO);
		}
		return getDO();
	}



	public Object setData(Element pElement) {
		List result = (List)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			//NamedNodeMap startAttr = _node.getAttributes();
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
 
			if(_nodeName.equals(XML_NODE_TASK_ID)) {
				infoDO.setTaskID(Integer.parseInt(_nodeValue));
			}else if(_nodeName.equals(XML_NODE_MESSAGE)) {
				infoDO.setMessage(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_STATUS)) {
				infoDO.setStatus(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PROGRESS)) {
				infoDO.setProgress(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_EQ_ID)) {
				infoDO.setEq_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_ERROR_CD)) {
				infoDO.setError_code(_nodeValue);
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
		//TransferDO infoDO = (TransferDO)getDO();
		String _xml = "<" + XML_NODE_HEAD+ ">";

		//		_xml = _xml + "<" + XML_NODE_SEQ+ ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQ + ">";
		//		_xml = _xml + "<" + XML_NODE_CLFCD + ">" + infoDO.getClfCd() + "</"  + XML_NODE_CLFCD + ">";
		//		_xml = _xml + "<" + XML_NODE_SCLCD + ">" + infoDO.getSclCd() + "</"  + XML_NODE_SCLCD + ">";
		//		_xml = _xml + "<" + XML_NODE_DESCRIPTION + ">" + infoDO.getDesc() + "</"  + XML_NODE_DESCRIPTION + ">";
		//		_xml = _xml + "<" + XML_NODE_GUBUN + ">" + infoDO.getGubun() + "</"  + XML_NODE_GUBUN + ">";
		//		
		//		_xml = _xml + "<" + XML_NODE_CLFNM + ">" + infoDO.getClfNm() + "</"  + XML_NODE_CLFNM + ">";
		//		_xml = _xml + "<" + XML_NODE_RMK_1 + ">" + infoDO.getRmk1() + "</"  + XML_NODE_RMK_1 + ">";
		//		_xml = _xml + "<" + XML_NODE_RMK_2 + ">" + infoDO.getRmk2() + "</"  + XML_NODE_RMK_2 + ">";
		//		_xml = _xml + "<" + XML_NODE_REGRID + ">" + infoDO.getRegrId() + "</"  + XML_NODE_REGRID + ">";
		//		_xml = _xml + "<" + XML_NODE_REG_DT + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REG_DT + ">";
		//		//_xml = _xml + "<" + XML_NODE_MOD_DT + ">" + infoDO.getModDt() + "</"  + XML_NODE_MOD_DT + ">";
		//		
		//		_xml = _xml + "<" + XML_NODE_SEARCH_TYPE + ">" + infoDO.getSearch_Type() + "</"  + XML_NODE_SEARCH_TYPE + ">";
		//		
		_xml = _xml + "</" + XML_NODE_HEAD + ">";
		//		
		return _xml;
	}


/*

	public String getXML(int TaskID) {
		TransferDO infoDO = (TransferDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD+ ">");
		_xml.append("<GetTaskStatus TaskID = \""+ TaskID +"\"/>");
		_xml.append("</" + XML_NODE_HEAD + ">");	
		return _xml.toString();
	}
	*/
}
