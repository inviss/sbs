package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TransferDO;
/**
 *  DAS_TM 정보  정보 관련 XML파서
 * @author asura207
 *
 */
public class TmStatusDOXML extends DOXml{
	/**
	 * xml해더
	 */
	private String XML_NODE_ADDTASK_HEAD = "Request";
	/**
	 * xml해더
	 */
	//private String XML_NODE_GETSTATUS_HEAD = "Response";

	//private String XML_NODE_SOURCE_MEDIA_FILE ="";
	/**
	 * xml해더
	 */
	private String XML_NODE_SOURCE_RESULT ="Result";
	//private String XML_NODE_SOURCE_MEDIA_XML = "";
	//private String XML_NODE_TARGET_MEDIA_FILE = "";
	//private String XML_NODE_TARGET_MEDIA_XML = "";
	//private String XML_NODE_CELL_ID = "";
	/**
	 * 타입
	 */
	//private String XML_NODE_TYPE = "Type";
	//private String XML_NODE_ACTION = "";
	/**
	 * 태스크아이디
	 */
	//private String XML_NODE_ID = "ID";

	//private String XML_NODE_TASK = "Task";
	/**
	 * 메세지
	 */
	private String XML_NODE_MESSAGE = "Message";
	/**
	 * 상태값
	 */
	//private String XML_NODE_STATUS = "Status";
	/**
	 * 진행률
	 */
	//private String XML_NODE_PROGRESS = "Progress";
	/**
	 * 파일경로
	 */
	//private String XML_NODE_FILE_PATH = "file_path";
	/**
	 * 카드번호
	 */
	//private String XML_NODE_CART_NO = "cart_no";
	/**
	 * 카트순번
	 */
	//private String XML_NODE_CART_SEQ = "cart_seq";
	/**
	 * 성공여부
	 */
	//private String XML_NODE_SUCCESS_YN = "Success";

	public Object getStatus(String _xml){
		//Document _document = getDocument(_xml);
		//Element _rootElement = _document.getDocumentElement();
		return getDO();
	}

	public Object setDO(String _xml) {
		setDO(new TransferDO());

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;

			if(_nodeName.equals(XML_NODE_SOURCE_RESULT)) {

				setData((Element)_node);			
			}
			if(_nodeName.equals(XML_NODE_ADDTASK_HEAD)) {

				setData2((Element)_node);			
			}
		}

		return getDO();
	}


	public Object setData(Element pElement) {
		TransferDO infoDO = (TransferDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			NamedNodeMap startAttr = _node.getAttributes();
			String _nodeValue = getNodeValue(_node);

			//String _nodeChildValue= getNodeValue(startAttr);
			if(_nodeName.equals(XML_NODE_MESSAGE)) {
				infoDO.setMessage(_nodeValue);
			}

			for(int k = 0; k<startAttr.getLength();k++){
				Node attr = startAttr.item(k);
				String nodeName = attr.getNodeName() ;
				//String att= CommonUtl.transXMLText(attr.getNodeValue());
				//	 infoDO.setSuccess_yn(att);


				if(nodeName.equals("ID")){
					infoDO.setTaskID(Integer.parseInt(StringUtils.defaultIfEmpty(attr.getNodeValue(), "0")));
				}else if(nodeName.equals("Type")){
					infoDO.setType(attr.getNodeValue());
				}else if(nodeName.equals("Status")){
					infoDO.setStatus(attr.getNodeValue());
				}else if(nodeName.equals("Progress")){
					infoDO.setProgress(attr.getNodeValue());
				}


			}


		}





		return infoDO;
	}	    





	public Object setData2(Element pElement) {
		TransferDO infoDO = (TransferDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			//String _nodeName = _node.getNodeName() ;
			NamedNodeMap startAttr = _node.getAttributes();
			//String _nodeValue = getNodeValue(_node);


			for(int k = 0; k<startAttr.getLength();k++){
				Node attr = startAttr.item(k);
				String nodeName = attr.getNodeName() ;
				//String att= CommonUtl.transXMLText(attr.getNodeValue());
				//	 infoDO.setSuccess_yn(att);

				if(nodeName.equals("TaskID")){
					//infoDO.setTaskID(Integer.parseInt(attr.getNodeValue()));
					infoDO.setTaskID(Integer.parseInt(StringUtils.defaultIfEmpty(attr.getNodeValue(), "0")));
				}

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
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_ADDTASK_HEAD+ ">");

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
		_xml.append("</" + XML_NODE_ADDTASK_HEAD + ">");
		//		
		return _xml.toString();
	}




	public String getXML(int TaskID) {
		//TransferDO infoDO = (TransferDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_ADDTASK_HEAD+ ">");

		_xml.append("<GetTaskStatus TaskID = \""+ TaskID +"\"/>");

		_xml.append("</" + XML_NODE_ADDTASK_HEAD + ">");
		//		
		return _xml.toString();
	}
}
