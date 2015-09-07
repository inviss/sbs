package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.CopyInfoDO;
import com.app.das.util.CommonUtl;

/**
 *  복본 정보 관련 XML파서
 * @author asura207
 *
 */
public class CopyInfoDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "copyInfo";
	/**
	 * 프로그램 ID
	 */
	private String XML_NODE_PGM_ID = "pgm_id";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_TITLE = "title";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "reg_dt";
	/**
	 * 등록자
	 */
	private String XML_NODE_REGRID = "reg_id";
	/**
	 * 자동생성여부
	 */
	private String XML_NODE_COPY_YN = "copy_yn";
	/**
	 * cms_pgm_id
	 */
	private String XML_NODE_CMS_PGM_ID = "cms_pgm_id";
	/**
	 * 시작
	 */
	static  public String XML_NODE_START_REG_DT = "start_reg_dt";
	/**
	 * 종료
	 */
	private String XML_NODE_END_REG_DT = "end_reg_dt";
	/**
	 * 복본업데이트 그룹( y: 복본설정)
	 */
	private String XML_NODE_PGM_ID_Y = "cms_pgm_id_y";
	/**
	 * 복본업데이트 그룹( n: 복본설정)
	 */
	private String XML_NODE_PGM_ID_N = "cms_pgm_id_n";

	public Object setDO(String _xml) {
		setDO(new CopyInfoDO());

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
		CopyInfoDO infoDO = (CopyInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_PGM_ID)) {
				infoDO.setPgmId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals( XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				infoDO.setReg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGRID)) {
				infoDO.setRegrid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COPY_YN)) {
				infoDO.setCopy_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_START_REG_DT)) {
				infoDO.setStart_reg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_END_REG_DT)) {
				infoDO.setEnd__reg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CMS_PGM_ID)) {
				infoDO.setCms_pgm_id(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PGM_ID_Y)) {
				infoDO.setPgm_id_y(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PGM_ID_N)) {
				infoDO.setPgm_id_n(_nodeValue);
			}
		}



		return infoDO;
	}	    

	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		_xml.append("<das> \n");
		_xml.append(getSubXML());
		_xml.append("</das>");

		return _xml.toString();
	}

	public String getSubXML() {
		CopyInfoDO infoDO = (CopyInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_PGM_ID+ ">" + infoDO.getPgmId() + "</"  + XML_NODE_PGM_ID + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">" + infoDO.getRegrid() + "</"  + XML_NODE_REGRID + "> \n");
		_xml.append("<" + XML_NODE_COPY_YN + ">" + infoDO.getCopy_yn() + "</"  + XML_NODE_COPY_YN + "> \n");		
		_xml.append("<" + XML_NODE_CMS_PGM_ID + ">" + infoDO.getCms_pgm_id() + "</"  + XML_NODE_CMS_PGM_ID + "> \n");		
		//_xml = _xml + "<" + XML_NODE_START_REG_DT + ">" + infoDO.getStart_reg_dt() + "</"  + XML_NODE_START_REG_DT + ">";	
		//_xml = _xml + "<" + XML_NODE_END_REG_DT + ">" + infoDO.getEnd__reg_dt() + "</"  + XML_NODE_END_REG_DT + ">";	
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}
