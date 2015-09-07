package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.log4j.*;

import com.app.das.business.transfer.AchiveManagerSystemDO;
import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.ErrorLogDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.business.transfer.ServersDO;
import com.app.das.util.CommonUtl;


/**
 *  서버 현황 XML파서
 * @author asura207
 *
 */
public class ServersDOXML extends DOXml {

	private static Logger logger = Logger.getLogger(ServersDOXML.class);
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "server";
	/**
	 * 서버명
	 */
	private String XML_NODE_SERER_NM = "server_nm";
	/**
	 * 상태
	 */
	private String XML_NODE_STATE = "state";

	/**
	 * 통신상태
	 */
	private String XML_NODE_NET_STATE= "net_state";
	/**
	 * ip
	 */
	private String XML_NODE_IP = "ip";
	/**
	 * 체널
	 */
	private String XML_NODE_CH_SEQ= "ch_seq";

	/**
	 * 서버재구동시간
	 */
	private String XML_NODE_RE_START_DT= "re_start_dt";
	/**
	 * 확인시간
	 */
	private String XML_NODE_CONFIRM_DT= "confirm_dt";


	/**
	 * 제목
	 */
	private String XML_NODE_TITLE= "title";


	/**
	 * 장비구분코드
	 */
	private String XML_NODE_DAS_EQ_CLF_CD= "das_eq_clf_cd";


	/**
	 * 장비id
	 */
	private String XML_NODE_DAS_EQ_ID= "das_eq_id";


	/**
	 * 포트
	 */
	private String XML_NODE_PORT= "port";


	public Object setDO(String _xml) {
		setDO(new ServersDO());

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
		ServersDO infoDO = (ServersDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();

		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_SERER_NM)) {
				infoDO.setServer_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STATE)) {
				infoDO.setState(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_NET_STATE)) {

				infoDO.setNet_state(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_IP)) {

				infoDO.setIp(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_CH_SEQ)) {
				infoDO.setCh_seq(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RE_START_DT)) {
				infoDO.setRe_start_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONFIRM_DT)) {
				infoDO.setConfirm_dt(_nodeValue);
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
		ServersDO infoDO = (ServersDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_SERER_NM + ">" + infoDO.getServer_nm()+ "</"  + XML_NODE_SERER_NM + ">");
		_xml.append("<" + XML_NODE_STATE + ">" + infoDO.getState() + "</"  + XML_NODE_STATE + ">");
		_xml.append("<" + XML_NODE_NET_STATE + ">" + infoDO.getNet_state() + "</"  + XML_NODE_NET_STATE + ">");
		_xml.append("<" + XML_NODE_IP + ">" + infoDO.getIp() + "</"  + XML_NODE_IP + ">");
		_xml.append("<" + XML_NODE_CH_SEQ + ">" + infoDO.getCh_seq()+ "</"  + XML_NODE_CH_SEQ + ">");
		_xml.append("<" + XML_NODE_RE_START_DT + ">" + infoDO.getRe_start_dt()+ "</"  + XML_NODE_RE_START_DT + ">");
		_xml.append("<" + XML_NODE_CONFIRM_DT + ">" + infoDO.getConfirm_dt()+ "</"  + XML_NODE_CONFIRM_DT + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_DAS_EQ_CLF_CD + ">" + infoDO.getDas_eq_clf_cd()+ "</"  + XML_NODE_DAS_EQ_CLF_CD + ">");
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">" + infoDO.getDas_eq_id()+ "</"  + XML_NODE_DAS_EQ_ID + ">");
		_xml.append("<" + XML_NODE_PORT + ">" + infoDO.getPort()+ "</"  + XML_NODE_PORT + ">");


		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}


}
