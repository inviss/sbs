package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.BoardDO;
import com.app.das.util.CommonUtl;


/**
 *  공지사항 정보 관련 XML파서
 * @author asura207
 *
 */
public class BoardInfoDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "boardInfo";
	/**
	 * 게시판ID
	 */
	private String XML_NODE_BOARD_ID = "board_id";
	/**
	 * 게시판종류코드
	 */
	private String XML_NODE_BOARD_TYPE_CD="board_type_cd";
	/**
	 * 제목
	 */
	private String XML_NODE_SUBJ="subj";
	/**
	 * 내용
	 */
	private String XML_NODE_CONT="cont";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT="reg_dt";
	/**
	 * 수정일
	 */
	private String XML_NODE_MOD_DT="mod_dt";	
	/**
	 * 등록자ID
	 */
	private String XML_NODE_REGID="regid";
	/**
	 * 수정자ID
	 */
	private String XML_NODE_MODID="modrid";
	/**
	 * 읽은횟수
	 */
	private String XML_NODE_RD_COUNT="rd_count";
	/**
	 * 본문ID
	 */
	private String XML_NODE_MAIN_ID="main_id";
	/**
	 * 대답여부
	 */
	private String XML_NODE_ANSWER_YN="answer_yn";
	/**
	 *  팝업여부
	 */
	private String XML_NODE_POPUP_YN="popup_yn";
	/**
	 * 파일명
	 */
	private String XML_NODE_FILE_NM="fl_nm";
	/**
	 * 파일경로
	 */
	private String XML_NODE_FILE_PATH="fl_path";
	/**
	 * 파일크기
	 */
	private String XML_NODE_FILE_SIZE="fl_size";
	/**
	 * 파일크기
	 */
	private String XML_NODE_FILE_SIZE2="fl_sz";
	/**
	 * 사용자명
	 */
	private String XML_NODE_USER_NM="user_nm";
	/**
	 * 등록자명
	 */
	private String XML_NODE_REG_NM="reg_nm";
	/**
	 * 팝업시작일
	 */
	private String XML_NODE_POPUP_START_DD="popup_start_dd";
	/**
	 * 팝업종료일
	 */
	private String XML_NODE_POPUP_END_DD="popup_end_dd";
	/**
	 * 첨부파일 여부
	 */
	//private String XML_NODE_ATTACH_YN="attach_yn";



	public Object setDO(String _xml) {
		setDO(new BoardDO());

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
		BoardDO boardDO = (BoardDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_BOARD_ID)) {
				boardDO.setBoardId(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_BOARD_TYPE_CD)) {
				boardDO.setBoardTypeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUBJ)) {
				boardDO.setSubj(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONT)) {

				boardDO.setCont(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_REG_DT)) {

				boardDO.setRegDt(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_MOD_DT)) {
				boardDO.setModDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGID)) {
				boardDO.setRegrid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MODID)) {
				boardDO.setModrid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RD_COUNT)) {

				boardDO.setRdCount(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_MAIN_ID)) {
				boardDO.setMainId(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_ANSWER_YN)) {
				boardDO.setAnswerYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_POPUP_YN)) {
				boardDO.setPopupyn(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_FILE_NM)) {
				boardDO.setFile_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_NM)) {
				boardDO.setUser_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_FILE_PATH)) {
				boardDO.setFile_path(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_FILE_SIZE)) {
				boardDO.setFile_size(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_POPUP_START_DD)) {
				boardDO.setPopup_start_dd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_POPUP_END_DD)) {
				boardDO.setPopup_end_dd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_FILE_SIZE2)) {
				boardDO.setFl_size(_nodeValue);
			}
		}

		return boardDO;
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
		BoardDO boardDO = (BoardDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_BOARD_ID + ">" + boardDO.getBoardId() + "</"  + XML_NODE_BOARD_ID + "> \n");
		_xml.append("<" + XML_NODE_BOARD_TYPE_CD + ">" + boardDO.getBoardTypeCd() + "</"  + XML_NODE_BOARD_TYPE_CD + "> \n");
		_xml.append("<" + XML_NODE_SUBJ + ">" + CommonUtl.transXmlText(boardDO.getSubj()) + "</"  + XML_NODE_SUBJ + "> \n");
		_xml.append("<" + XML_NODE_CONT + ">" + CommonUtl.transXmlText(boardDO.getCont()) + "</"  + XML_NODE_CONT + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + boardDO.getRegDt().trim() + "</"  + XML_NODE_REG_DT + "> \n");
		//_xml = _xml + "<" + XML_NODE_MOD_DT + ">" + boardDO.getModDt() + "</"  + XML_NODE_MOD_DT + ">";
		_xml.append("<" + XML_NODE_REGID + ">" + boardDO.getRegrid() + "</"  + XML_NODE_REGID + "> \n");
		//_xml = _xml + "<" + XML_NODE_MODID + ">" + boardDO.getModrid() + "</"  + XML_NODE_MODID + ">";
		_xml.append("<" + XML_NODE_RD_COUNT + ">" + boardDO.getRdCount() + "</"  + XML_NODE_RD_COUNT + "> \n");
		_xml.append("<" + XML_NODE_MAIN_ID + ">" + boardDO.getMainId() + "</"  + XML_NODE_MAIN_ID + "> \n");
		//_xml = _xml + "<" + XML_NODE_ANSWER_YN + ">" + boardDO.getAnswerYn() + "</"  + XML_NODE_ANSWER_YN + ">";
		_xml.append("<" + XML_NODE_POPUP_YN + ">" + boardDO.getPopupyn() + "</"  + XML_NODE_POPUP_YN + "> \n");
		_xml.append("<" + XML_NODE_FILE_NM + ">" + boardDO.getFile_nm() + "</"  + XML_NODE_FILE_NM + "> \n");
		_xml.append("<" + XML_NODE_USER_NM + ">" + CommonUtl.transXmlText(boardDO.getUser_nm()) + "</"  + XML_NODE_USER_NM + "> \n");
		_xml.append("<" + XML_NODE_REG_NM + ">" + CommonUtl.transXmlText(boardDO.getRegrNm()) + "</"  + XML_NODE_REG_NM + "> \n");
		_xml.append("<" + XML_NODE_FILE_PATH + ">" + boardDO.getFile_path() + "</"  + XML_NODE_FILE_PATH + "> \n");
		_xml.append("<" + XML_NODE_POPUP_START_DD + ">" + boardDO.getPopup_start_dd().trim() + "</"  + XML_NODE_POPUP_START_DD + "> \n");
		_xml.append("<" + XML_NODE_POPUP_END_DD + ">" + boardDO.getPopup_end_dd().trim() + "</"  + XML_NODE_POPUP_END_DD + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");


		return _xml.toString();
	}


}
