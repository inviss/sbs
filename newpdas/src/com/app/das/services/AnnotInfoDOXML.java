package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.AnnotInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 주석 정보 관련 XML파서
 * @author asura207
 *
 */
public class AnnotInfoDOXML extends DOXml {
	
	//private static Logger logger = Logger.getLogger(AnnotInfoDOXML.class);
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "annotInfo";
	/** 
	 * 코너ID 
	 */
	private String XML_NODE_CORNERID = "CN_ID";
	/** 
	 * 주석정보 ID 
	 */
	private String XML_NODE_ANNOTATIONID = "ANNOT_ID";
	/** 
	 * 주석구분코드 
	 */
	private String XML_NODE_ANNOTCLASSIFYCODE = "ANNOT_CLF_CD";
	/** 
	 * 주석구분내용 
	 */
	private String XML_NODE_ANNOTCLASSIFYCONT = "ANNOT_CLF_CONT";
	/** 
	 * 콘텐츠 ID 
	 */
	private String XML_NODE_CONTENTSID = "CT_ID"; 
	/** 
	 * 시작점 
	 */
	private String XML_NODE_SOM = "SOM";
	/** 
	 * 종료점 
	 */
	private String XML_NODE_EOM = "EOM";
	/** 
	 * 마스터 ID 
	 */
	private String XML_NODE_MASTERID = "MASTER_ID";
	/** 
	 * 등록일시
	 */
	private String XML_NODE_REGISTRATIONDATE = "REG_DT";
	/** 
	 * 등록자 ID 
	 */
	private String XML_NODE_REGISTRATORID = "REGRID";
	/** 
	 * 기간
	 */
	private String XML_NODE_DURATION = "DURATION";
	/** 
	 * 시작 프레임
	 */
	private String XML_NODE_STARFRAME = "S_FRAME";
	/**
	 * 주제영상 :S , 사용제한등급:L ,기타:N
	 */
	public static String XML_NODE_GUBUN = "GUBUN";
	
	/** 
	 * 전체영상여부
	 */
	private String XML_NODE_ENTIRE_YN = "ENTIRE_YN";
	/** 
	 * 전체영상 구분코드
	 */
	private String XML_NODE_ENTIRE_RIST_CLF_CD = "ENTIRE_RIST_CLF_CD";
	
	
	public Object setDO(String _xml) {
		setDO(new AnnotInfoDO());
		
		List resultList  = new ArrayList();
	
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
       
        int _length = _nodeList.getLength();
         for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				resultList.add(setData((Element)_node));
			}
        }     
		
         return resultList;
	}
	
	public Object setData(Element pElement) {
		AnnotInfoDO infoDO = new AnnotInfoDO();
	    NodeList _nodeList = pElement.getChildNodes();
	    
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CORNERID)) {
				infoDO.setCnId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_ANNOTCLASSIFYCODE)) {
				infoDO.setAnnotClfCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ANNOTCLASSIFYCONT)) {
				infoDO.setAnnotClfCont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_SOM)) {
				infoDO.setSom(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EOM)) {
				infoDO.setEom(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_ANNOTATIONID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setAnnotId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATIONDATE)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATORID)) {
				infoDO.setRegrId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DURATION)) {
				infoDO.setDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_STARFRAME)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setSFrame(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_GUBUN)) {
				infoDO.setGubun(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ENTIRE_YN)) {
				infoDO.setEntire_yn(_nodeValue);
			}	else if(_nodeName.equals(XML_NODE_ENTIRE_RIST_CLF_CD)) {
				infoDO.setEntire_rist_clf_cd(_nodeValue);
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
		AnnotInfoDO infoDO = (AnnotInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_ANNOTATIONID + ">" + infoDO.getAnnotId() + "</"  + XML_NODE_ANNOTATIONID + ">");
		_xml.append("<" + XML_NODE_CORNERID + ">" + infoDO.getCnId() + "</"  + XML_NODE_CORNERID + ">");
		_xml.append("<" + XML_NODE_ANNOTCLASSIFYCODE + ">" + infoDO.getAnnotClfCd() + "</"  + XML_NODE_ANNOTCLASSIFYCODE + ">");
		_xml.append("<" + XML_NODE_ANNOTCLASSIFYCONT + ">" + CommonUtl.transXmlText(infoDO.getAnnotClfCont()) + "  </"  + XML_NODE_ANNOTCLASSIFYCONT + ">");
		_xml.append("<" + XML_NODE_CONTENTSID + ">" + infoDO.getCtId() + "</"  + XML_NODE_CONTENTSID + ">");
		_xml.append("<" + XML_NODE_SOM + ">" + infoDO.getSom() + "</"  + XML_NODE_SOM + ">");
		_xml.append("<" + XML_NODE_EOM + ">" + infoDO.getEom() + "</"  + XML_NODE_EOM + ">");
		_xml.append("<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + ">");
		_xml.append("<" + XML_NODE_MASTERID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTERID + ">");
		_xml.append("<" + XML_NODE_REGISTRATORID + ">" + infoDO.getRegrId() + "</"  + XML_NODE_REGISTRATORID + ">");
		_xml.append("<" + XML_NODE_DURATION + ">" + infoDO.getDuration() + "</"  + XML_NODE_DURATION + ">");
		_xml.append("<" + XML_NODE_STARFRAME + ">" + infoDO.getSFrame() + "</"  + XML_NODE_STARFRAME + ">");
		_xml.append("<" + XML_NODE_GUBUN + ">" + infoDO.getGubun() + "</"  + XML_NODE_GUBUN + ">");
		_xml.append("<" + XML_NODE_ENTIRE_YN + ">" + infoDO.getEntire_yn() + "</"  + XML_NODE_ENTIRE_YN + ">");
		_xml.append("<" + XML_NODE_ENTIRE_RIST_CLF_CD + ">" + infoDO.getEntire_rist_clf_cd() + "</"  + XML_NODE_ENTIRE_RIST_CLF_CD + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
}
