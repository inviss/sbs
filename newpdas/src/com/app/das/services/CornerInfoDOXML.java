package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CornerInfoDO;
import com.app.das.util.CommonUtl;

/**
 *  코너 정보 관련 XML파서
 * @author asura207
 *
 */
public class CornerInfoDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "cornerInfo";
	/**
	 * 콘텐츠ID
	 */
	private String XML_NODE_CONTENTSID = "CT_ID";
	/**
	 * 키프레임해상도코드
	 */
	private String XML_NODE_KEYFRAMERESCODE = "KFRM_PX_CD";
	/**
	 * 화질코드
	 */
	private String XML_NODE_VIDEOQUALITY = "VD_QLTY";
	/**
	 * 종횡비코드
	 */
	private String XML_NODE_ASPRTOCODE = "ASP_RTO_CD"; 
	/**
	 * 코너ID
	 */
	private String XML_NODE_CORNERID = "CN_ID";
	/**
	 * 코너명
	 */
	private String XML_NODE_CORNERNAME = "CN_NM";
	/**
	 * 시작점
	 */
	private String XML_NODE_STARTOFFSET = "SOM";
	/**
	 * 종료점
	 */
	private String XML_NODE_ENDOFFSET = "EOM";
	/**
	 * 코너유형코드
	 */
	private String XML_NODE_CORNERTYPECODE = "CN_TYPE_CD";
	/**
	 * 코너정보
	 */
	private String XML_NODE_CORNERINFO = "CN_INFO";
	/**
	 * 프레임갯수
	 */
	private String XML_NODE_DURATION = "DURATION";
	/**
	 * 콘텐츠일련번호
	 */	
	private String XML_NODE_CONTENTSSEQUENCE = "CT_SEQ";	
	/**
	 * 키프레임 경로
	 */
	private String XML_NODE_KFRMPATH = "KFRM_PATH";
	/**
	 * WMV 경로
	 */	
	private String XML_NODE_FILEPATH = "FL_PATH";

	/**
	 * 콘텐트 이스탄스 ID
	 */	
	private String XML_NODE_CTIID = "CTI_ID";
	/**
	 * 코너 콘텐트 시작 길이
	 */	
	private String XML_NODE_STOPDURATION = "S_DURATION";
	/**
	 * 코너 콘텐트 종료  길이
	 */	
	private String XML_NODE_ENDDURATION = "E_DURATION";
	/**
	 * 대표화면 키프레임 일련번호
	 */	
	private String XML_NODE_RPIMGKFRMSEQ = "RPIMG_KFRM_SEQ";
	/** 
	 * 대표화면 콘텐츠 ID
	 */
	private String XML_NODE_RPIMGCTID = "RPIMG_CT_ID";
	/**
	 * 컨텐츠 이름
	 */	
	private String XML_NODE_CONTENTNAME = "CT_NM";
	/**
	 * 컨텐츠 내용
	 */	
	private String XML_NODE_CONTENT = "CONT";
	/** 
	 * 마스터 ID 
	 */
	private String XML_NODE_MASTERID = "MASTER_ID";
	/** 
	 * 등록일 
	 */
	private String XML_NODE_REGISTRATIONDATE = "REG_DT";
	/** 
	 * 등록자  ID 
	 */
	private String XML_NODE_REGISTRATORID = "REGRID";
	/** 
	 * 시작 프레임
	 */
	private String XML_NODE_STARFRAME = "S_FRAME";	
	/** 
	 * 키프레임 파일 리스트
	 */
	private String XML_NODE_FILELIST = "FILE_LIST";	
	
	
	
	
	public Object setDO(String _xml) {
		setDO(new CornerInfoDO());
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
		CornerInfoDO infoDO = new CornerInfoDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CONTENTSID)) {
				infoDO.setCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_KEYFRAMERESCODE)) {
				infoDO.setKfrmPxCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VIDEOQUALITY)) {
				infoDO.setVdQlty(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ASPRTOCODE)) {
				infoDO.setAspRtoCd(_nodeValue);
			}
//			else if(_nodeName.equals(XML_NODE_PROGRAMID)) {
//				infoDO.setPgmId(Long.parseLong(_nodeValue));
//			}
//			else if(_nodeName.equals(XML_NODE_PROGRAMNAME)) {
//				infoDO.setPgmNm(_nodeValue);
//			}
			else if(_nodeName.equals(XML_NODE_CORNERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCnId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CORNERNAME)) {
				infoDO.setCnNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STARTOFFSET)) {
				infoDO.setSom(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ENDOFFSET)) {
				infoDO.setEom(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CORNERTYPECODE)) {
				infoDO.setCnTypeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CORNERINFO)) {
				infoDO.setCnInfo(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DURATION)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSSEQUENCE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCtSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_KFRMPATH)) {
				infoDO.setKfrmPath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILEPATH)) {
				infoDO.setFlPath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTIID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCtiId(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_STOPDURATION)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setSDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_ENDDURATION)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setEDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_RPIMGKFRMSEQ)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setRpimgKfrmSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONTENTNAME)) {
				infoDO.setCtNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONTENT)) {
				infoDO.setCont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATIONDATE)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATORID)) {
				infoDO.setRegrid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RPIMGCTID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setRpimgCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_FILELIST)) {
				infoDO.setKfrmFileNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STARFRAME)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setSFrame(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
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
		CornerInfoDO infoDO = (CornerInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_CONTENTSID + ">" + infoDO.getCtId() + "</"  + XML_NODE_CONTENTSID + ">");
		_xml.append("<" + XML_NODE_KEYFRAMERESCODE + ">" + infoDO.getKfrmPxCd() + "</"  + XML_NODE_KEYFRAMERESCODE + ">");
		_xml.append("<" + XML_NODE_VIDEOQUALITY + ">" + infoDO.getVdQlty() + "</"  + XML_NODE_VIDEOQUALITY + ">");
		_xml.append("<" + XML_NODE_ASPRTOCODE + ">" + infoDO.getAspRtoCd() + "</"  + XML_NODE_ASPRTOCODE + ">");
	//	_xml = _xml + "<" + XML_NODE_PROGRAMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PROGRAMID + ">");
	//	_xml = _xml + "<" + XML_NODE_PROGRAMNAME + ">" + infoDO.getPgmNm() + "</"  + XML_NODE_PROGRAMNAME + ">";
		_xml.append("<" + XML_NODE_CORNERID + ">" + infoDO.getCnId() + "</"  + XML_NODE_CORNERID + ">");
		_xml.append("<" + XML_NODE_CORNERNAME + ">" + replaceParse(infoDO.getCnNm()) + "</"  + XML_NODE_CORNERNAME + ">");
		_xml.append("<" + XML_NODE_STARTOFFSET + ">" + infoDO.getSom() + "</"  + XML_NODE_STARTOFFSET + ">");
		_xml.append("<" + XML_NODE_ENDOFFSET + ">" + infoDO.getEom() + "</"  + XML_NODE_ENDOFFSET + ">");
		_xml.append("<" + XML_NODE_CORNERTYPECODE + ">" + infoDO.getCnTypeCd() + "</"  + XML_NODE_CORNERTYPECODE + ">");
		//_xml = _xml + "<" + XML_NODE_CORNERINFO + ">" + replaceParse(infoDO.getCnInfo()) + "</"  + XML_NODE_CORNERINFO + ">";
		_xml.append("<" + XML_NODE_DURATION + ">" + infoDO.getDuration() + "</"  + XML_NODE_DURATION + ">");
		_xml.append("<" + XML_NODE_CONTENTSSEQUENCE + ">" + infoDO.getCtSeq() + "</"  + XML_NODE_CONTENTSSEQUENCE + ">");
		_xml.append("<" + XML_NODE_KFRMPATH + ">" + infoDO.getKfrmPath() + "</"  + XML_NODE_KFRMPATH + ">");
		_xml.append("<" + XML_NODE_FILEPATH + ">" + infoDO.getFlPath() + "</"  + XML_NODE_FILEPATH + ">");
		_xml.append("<" + XML_NODE_CTIID + ">" + infoDO.getCtiId() + "</"  + XML_NODE_CTIID + ">");
		_xml.append("<" + XML_NODE_STOPDURATION + ">" + infoDO.getSDuration() + "</"  + XML_NODE_STOPDURATION + ">");
		_xml.append("<" + XML_NODE_ENDDURATION + ">" + infoDO.getEDuration() + "</"  + XML_NODE_ENDDURATION + ">");
		_xml.append("<" + XML_NODE_RPIMGKFRMSEQ + ">" + infoDO.getRpimgKfrmSeq() + "</"  + XML_NODE_RPIMGKFRMSEQ + ">");
		_xml.append("<" + XML_NODE_CONTENTNAME + ">" + replaceParse(infoDO.getCtNm()) + "</"  + XML_NODE_CONTENTNAME + ">");
		_xml.append("<" + XML_NODE_CONTENT + ">" + replaceParse(infoDO.getCont()) + "</"  + XML_NODE_CONTENT + ">");
		_xml.append("<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + ">");
		_xml.append("<" + XML_NODE_MASTERID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTERID + ">");
		_xml.append("<" + XML_NODE_REGISTRATORID + ">" + infoDO.getRegrid() + "</"  + XML_NODE_REGISTRATORID + ">");
		_xml.append("<" + XML_NODE_RPIMGCTID + ">" + infoDO.getRpimgCtId() + "</"  + XML_NODE_RPIMGCTID + ">");
		_xml.append("<" + XML_NODE_FILELIST + ">" + infoDO.getKfrmFileNm() + "</"  + XML_NODE_FILELIST + ">");
		_xml.append("<" + XML_NODE_STARFRAME + ">" + infoDO.getSFrame() + "</"  + XML_NODE_STARFRAME + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
}
