package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ManualDeleteDO;
import com.app.das.util.CommonUtl;


/**
 *  내목록 정보 관련 XML파서
 * @author asura207
 *
 */
public class ManualDeleteDOXML extends DOXml {

	//private static Logger logger = Logger.getLogger(ManualDeleteDOXML.class);
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "manualdelete";
	/**
	 * 구분(001: 아카이브, 002:다운로드)
	 */
	private String XML_NODE_GUBUN = "gubun";

	/**
	 * 파일명
	 */
	private String XML_NODE_FILE_NM = "file_nm";
	/**
	 * 파일 경로
	 */
	private String XML_NODE_FL_PATH = "fl_path";
	/**
	 * 등록일자(시작)
	 */
	private String XML_NODE_START_REG_DT = "strat_reg_dt";
	/**
	 * 등록일자(종료)
	 */
	private String XML_NODE_END_REG_DT= "end_reg_dt";

	/**
	 * 아카이브 경로
	 */
	private String XML_NODE_ARCH_ROUTE = "arch_route";
	/**
	 * 채널 
	 */
	private String XML_NODE_CHENNEL = "chennel";
	/**
	 * 삭제요청자id
	 */
	private String XML_NODE_REG_ID= "req_id";


	/**
	 * 총카운트
	 */
	private String XML_NODE_TOTALCOUNT= "totalcount";


	/**
	 * 시작페이지
	 */
	private String XML_NODE_START_PAGE= "start_page";


	/**
	 * 구분명
	 */
	private String XML_NODE_GUBUN_NM= "gubun_nm";


	/**
	 *  key
	 */
	private String XML_NODE_KEY= "key";


	public Object setDO(String _xml) {
		setDO(new ManualDeleteDO());

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
		ManualDeleteDO infoDO = (ManualDeleteDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();

		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_GUBUN)) {
				infoDO.setGubun(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILE_NM)) {
				infoDO.setFile_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FL_PATH)) {

				infoDO.setFl_path(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_START_REG_DT)) {

				infoDO.setStrat_reg_dt(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_END_REG_DT)) {
				infoDO.setEnd_reg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARCH_ROUTE)) {
				infoDO.setArch_route(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CHENNEL)) {
				infoDO.setChennel(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_ID)) {
				infoDO.setReq_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_TOTALCOUNT)) {
				infoDO.setTotalcount(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_START_PAGE)) {
				infoDO.setStart_page(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_KEY)) {
				infoDO.setKey(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
		}

		return infoDO;
	}


	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		_xml.append("<das>");
		_xml.append(getSubXML());
		_xml.append("</das>");

		return _xml.toString();
	}

	public String getSubXML() {
		ManualDeleteDO infoDO = (ManualDeleteDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_KEY + ">" + infoDO.getKey() + "</"  + XML_NODE_KEY + ">");
		_xml.append("<" + XML_NODE_GUBUN + ">" + infoDO.getGubun()+ "</"  + XML_NODE_GUBUN + ">");
		_xml.append("<" + XML_NODE_GUBUN_NM + ">" + CommonUtl.transXmlText(infoDO.getGubun_nm())+ "</"  + XML_NODE_GUBUN_NM + ">");
		_xml.append("<" + XML_NODE_FILE_NM + ">" + infoDO.getFile_nm() + "</"  + XML_NODE_FILE_NM + ">");
		_xml.append("<" + XML_NODE_FL_PATH + ">" + infoDO.getFl_path() + "</"  + XML_NODE_FL_PATH + ">");
		_xml.append("<" + XML_NODE_TOTALCOUNT + ">" + infoDO.getTotalcount() + "</"  + XML_NODE_TOTALCOUNT + ">");


		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}



}
