package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.transfer.SubCodeDO;
import com.app.das.util.CommonUtl;
/**
 *  계열사코드  정보 관련 XML파서
 * @author asura207
 *
 */
public class SubCodeDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "subcode";
	/**
	 * 사용자 ID
	 */
	private String XML_NODE_SBS_USER_ID = "sbs_user_id";
	/**
	 * 계열사 코드
	 */
	private String XML_NODE_CO_CD = "co_cd";
	/**
	 * 계열사 명
	 */
	private String XML_NODE_CO_NM = "co_nm";
	/**
	 * 본부명
	 */
	private String XML_NODE_SEG_NM = "seg_nm"; 
	/**
	 * 본부 코드
	 */
	private String XML_NODE_SEG_CD = "seg_cd";
	/**
	 *  부서 코드
	 */
	private String XML_NODE_DEPT_CD = "dept_id";
	/**
	 *  부서명
	 */
	private String XML_NODE_DEPT_NM = "dept_nm";
	/**
	 * 수정일시
	 */
	private String XML_NODE_MOD_DT = "mod_dt";
	/**
	 * SBSI사용유무
	 */
	private String XML_NODE_SBSI_YN = "sbsi_yn";


	public Object setDO(String _xml) {
		setDO(new SubCodeDO());

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
		SubCodeDO infoDO = (SubCodeDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_SBS_USER_ID)) {
				infoDO.setSbs_user_ID(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CO_CD)) {
				infoDO.setCo_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CO_NM)) {
				infoDO.setCo_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEG_NM)) {
				infoDO.setSeg_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEG_CD)) {
				infoDO.setSeg_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_CD)) {
				infoDO.setDept_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_NM)) {
				infoDO.setDept_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MOD_DT)) {
				infoDO.setMod_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SBSI_YN)) {
				infoDO.setSbsi_yn(_nodeValue);
			}

		}

		return infoDO;
	}


	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + " \n </das>";

		return _xml;
	}

	public String getSubXML() {
		SubCodeDO infoDO = (SubCodeDO)getDO();

		StringBuffer _xml = new StringBuffer();
		_xml.append(" \n <" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_SBS_USER_ID + ">" + CommonUtl.transXmlText(infoDO.getSbs_user_ID()) + "</"  + XML_NODE_SBS_USER_ID + "> \n");
		_xml.append("<" + XML_NODE_CO_CD + ">" + CommonUtl.transXmlText(infoDO.getCo_cd()) + "</"  + XML_NODE_CO_CD + "> \n");
		_xml.append("<" + XML_NODE_CO_NM + ">" + CommonUtl.transXmlText(infoDO.getCo_nm()) + "</"  + XML_NODE_CO_NM + "> \n");
		_xml.append("<" + XML_NODE_SEG_NM + ">" + CommonUtl.transXmlText(infoDO.getSeg_nm()) + "</"  + XML_NODE_SEG_NM + "> \n");
		_xml.append("<" + XML_NODE_SEG_CD + ">" + CommonUtl.transXmlText(infoDO.getSeg_cd()) + "</"  + XML_NODE_SEG_CD + "> \n");
		_xml.append("<" + XML_NODE_DEPT_CD + ">" + CommonUtl.transXmlText(infoDO.getDept_id()) + "</"  + XML_NODE_DEPT_CD + "> \n");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" + CommonUtl.transXmlText(infoDO.getDept_nm()) + "</"  + XML_NODE_DEPT_NM + "> \n");
		_xml.append("<" + XML_NODE_MOD_DT + ">" + CommonUtl.transXmlText(infoDO.getMod_dt()) + "</"  + XML_NODE_MOD_DT + "> \n");
		_xml.append("<" + XML_NODE_SBSI_YN + ">" + CommonUtl.transXmlText(infoDO.getSbsi_yn()) + "</"  + XML_NODE_SBSI_YN + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}
