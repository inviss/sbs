package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.MediaInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  미디어 정보 관련 XML파서
 * @author asura207
 *
 */
public class MediaInfoDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "mediaInfo";
	/**
	 * 오디오여부
	 */
	private String XML_NODE_AUDIOYN = "AUDIO_YN";
	/**
	 * ME분리코드
	 */
	private String XML_NODE_MECODE = "ME_CD";
	/**
	 * 색상코드
	 */
	private String XML_NODE_COLORCODE = "COLOR_CD";
	/**
	 * 콘텐츠인스턴츠포맷코드
	 */
	private String XML_NODE_CTIFORMAT = "CTI_FMT"; 
	/**
	 * 등록일시
	 */
	private String XML_NODE_REGISTERDATE = "REG_DT"; 
	/**
	 * 파일크기
	 */
	private String XML_NODE_FILESIZE = "FL_SZ"; 
	/**
	 * 초당프레임
	 */
	private String XML_NODE_FRAMEPERSECOND = "FRM_PER_SEC"; 
	/**
	 * 비트전송율
	 */
	private String XML_NODE_BITRATE = "BIT_RT"; 
	/**
	 * 녹음방식코드
	 */
	private String XML_NODE_RECORDTYPECODE = "RECORD_TYPE_CD"; 
	/**
	 * 오디오대역폭
	 */
	private String XML_NODE_AUDIOBANDWIDTH = "AUDIO_BDWT"; 
	/**
	 * 오디오샘플
	 */
	private String XML_NODE_AUDIOSAMPLEFREQ = "AUD_SAMP_FRQ"; 

	public Object setDO(String _xml) {
		setDO(new MediaInfoDO());
		
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
		MediaInfoDO infoDO = (MediaInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_AUDIOYN)) {
				infoDO.setAudioYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MECODE)) {
				infoDO.setMeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COLORCODE)) {
				infoDO.setColorCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTIFORMAT)) {
				infoDO.setCtiFmt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTERDATE)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILESIZE)) {
			}
			else if(_nodeName.equals(XML_NODE_FRAMEPERSECOND)) {
				infoDO.setFrmPerSec(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BITRATE)) {
				infoDO.setBitRt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RECORDTYPECODE)) {
				infoDO.setRecordTypeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_AUDIOBANDWIDTH)) {
				infoDO.setAudioBdwt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_AUDIOSAMPLEFREQ)) {
				infoDO.setAudSampFrq(_nodeValue);
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
		MediaInfoDO infoDO = (MediaInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_AUDIOYN + ">" + infoDO.getAudioYn() + "</"  + XML_NODE_AUDIOYN + ">");
		_xml.append("<" + XML_NODE_MECODE + ">" + infoDO.getMeCd() + "</"  + XML_NODE_MECODE + ">");
		_xml.append("<" + XML_NODE_COLORCODE + ">" + infoDO.getColorCd() + "</"  + XML_NODE_COLORCODE + ">");
		_xml.append("<" + XML_NODE_CTIFORMAT + ">" + infoDO.getCtiFmt() + "</"  + XML_NODE_CTIFORMAT + ">");
		_xml.append("<" + XML_NODE_REGISTERDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTERDATE + ">");
		_xml.append("<" + XML_NODE_FILESIZE + ">" + infoDO.getFlSz() + "</"  + XML_NODE_FILESIZE + ">");
		_xml.append("<" + XML_NODE_FRAMEPERSECOND + ">" + infoDO.getFrmPerSec() + "</"  + XML_NODE_FRAMEPERSECOND + ">");
		_xml.append("<" + XML_NODE_BITRATE + ">" + infoDO.getBitRt() + "</"  + XML_NODE_BITRATE + ">");
		_xml.append("<" + XML_NODE_RECORDTYPECODE + ">" + infoDO.getRecordTypeCd() + "</"  + XML_NODE_RECORDTYPECODE + ">");
		_xml.append("<" + XML_NODE_AUDIOBANDWIDTH + ">" + infoDO.getAudioBdwt() + "</"  + XML_NODE_AUDIOBANDWIDTH + ">");
		_xml.append("<" + XML_NODE_AUDIOSAMPLEFREQ + ">" + infoDO.getAudSampFrq() + "</"  + XML_NODE_AUDIOSAMPLEFREQ + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
}
