package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.MetaInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  메타 정보 관련 XML파서
 * @author asura207
 *
 */
public class MetaInfoDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "MetaInfo";
	/**
	 * 수정가능여부
	 */
	private String XML_NODE_MODIFY = "CAN_MODIFY";
	/** 
	 * 마스터 ID    
	 */
	private String XML_NODE_MASTERID = "MASTER_ID";
	/** 
	 * 자료 상태 코드   
	 */
	private String XML_NODE_DATASTATUSCODE = "DATA_STAT_CD";
	/** 
	 * 코드설명
	 */
	private String XML_NODE_DESCRIPTION = "DESC";
	/** 
	 * 타이틀      
	 */ 
	private String XML_NODE_TITLE = "TITLE"; 
	/**
	 * 방송길이
	 */
	private String XML_NODE_BRDLENGTH = "BRD_LENG";
	/**
	 * 등록일
	 */
	private String XML_NODE_REGISTRATIONDATE = "REG_DT";
	/**
	 * 아카이브 등록일
	 */
	private String XML_NODE_ARCHIVEREGDATE = "ARCH_REG_DD";
	/**
	 * 카운트 
	 */
	private String XML_NODE_COUNT = "COUNT";
	/**
	 * 에피소드 번호
	 */
	private String XML_NODE_EPISNO = "EPIS_NO";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRDDD = "BRD_DD";
	/**
	 * 촬영일
	 */
	private String XML_NODE_FMDT = "FM_DT";
	/**
	 * 요청 코드
	 */
	private String XML_NODE_REQUESTCODE = "REQ_CD";
	/**
	 * 수정자 ID
	 */
	private String XML_NODE_MODIFIERID = "MODRID";
	/**
	 * 인제스트 일자
	 */
	private String XML_NODE_ING_REG_DD = "ING_REG_DD";
	/**
	 * 잠금상태코드
	 */
	private String XML_NODE_LOCKSTATUSCODE = "LOCK_STAT_CD";
	/**
	 * 에러 코드
	 */
	private String XML_NODE_ERRORSTATUSCODE = "ERROR_STAT_CD";
	/**
	 * TAPE_ITEM_ID
	 */
	private String XML_NODE_TAPEITEMID = "TAPE_ITEM_ID";
	/**
	 * 주조ID
	 */
	private String XML_NODE_MCUID = "MCUID";
	/**
	 * 검색결과 카운트
	 */
	private String XML_NODE_QUERY_RESULT_COUNT = "QUERY_RESULT_COUNT";
	/**
	 * 총방송길이
	 */
	private String XML_NODE_SUM_BRD_LENG = "SUM_BRD_LENG";
	/**
	 * 프로그램id
	 */
	private String XML_NODE_PGM_ID = "PGM_ID";
	/**
	 * 대분류 코드
	 */
	private String XML_NODE_CTGR_L_CD = "SCL_CD";
	/**
	 * 영상  아이디	 */
	private String XML_NODE_CT_ID = "CT_ID";
	/**
	 * 영상 인스턴스 아이디	 */
	private String XML_NODE_CTI_ID = "CTI_ID";
	/**
	 * 콘텐츠 구분
	 */
	private String XML_NODE_CT_CLA = "CT_CLA";
	/**
	 * 대분류명
	 */
	private String XML_NODE_CTGR_L_NM = "CTGR_L_NM";
	/**
	 * 스토리지
	 */
	private String XML_NODE_STORAGE_NM = "STORAGE";
	/**
	 * 사용자명
	 */
	private String XML_NODE_USER_NM = "USER_NM";
	/**
	 * 관련영상 존재여부
	 */
	private String XML_NODE_LINK_PARENTS = "LINK_PARENTS";
	
	
	/**
	 * 전체 조회갯수
	 */
	private String XML_NODE_TOTAL_COUNT = "total_count";
	/**
	 * 시작페이지
	 */
	//private String XML_NODE_START_PAGE = "start_page";
	/**
	 * 종료페이지
	 */
	//private String XML_NODE_END_PAGE = "end_page";
	/**
	 * 요청 등록일 
	 */
	//private String XML_NODE_START_REG_DD = "start_reg_dd";
	/**
	 * 요청종료일
	 */
	//private String XML_NODE_END_REG_DD = "end_reg_dd";
	/**
	 * 요청자ID
	 */
	//private String XML_NODE_REQ_ID = "req_id";
	
	public Object setDO(String _xml) {
	
		setDO(new MetaInfoDO());
		
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
		MetaInfoDO infoDO = (MetaInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_MASTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_DATASTATUSCODE)) {
				infoDO.setDataStatCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DESCRIPTION)) {
				infoDO.setDesc(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);			
			}
			else if(_nodeName.equals(XML_NODE_BRDLENGTH)) {
				infoDO.setBrdLeng(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATIONDATE)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARCHIVEREGDATE)) {
				infoDO.setArchRegDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COUNT)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCount(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_EPISNO)) {
				if (_nodeValue != null && _nodeValue.length() > 0){
					infoDO.setEpisNo(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_BRDDD)) {
				infoDO.setBrdDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQUESTCODE)) {
				infoDO.setReqCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MODIFIERID)) {
				infoDO.setModrid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ING_REG_DD)) {
				infoDO.setIng_Reg_DD(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LOCKSTATUSCODE)) {
				infoDO.setLock_stat_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ERRORSTATUSCODE)) {
				infoDO.setLock_stat_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TAPEITEMID)) {
				infoDO.setTape_item_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MCUID)) {
				infoDO.setMcuid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CT_CLA)) {
				infoDO.setCt_cla(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTGR_L_NM)) {
				infoDO.setCtgr_l_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_QUERY_RESULT_COUNT)) {
				infoDO.setQueryResultCount(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_SUM_BRD_LENG)){
				infoDO.setSum_brd_leng(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_CTI_ID)){
				infoDO.setCti_id(Long.parseLong(_nodeValue));
			}else if(_nodeName.equals(XML_NODE_FMDT)) {
				infoDO.setFM_DT(_nodeValue);
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
		MetaInfoDO infoDO = (MetaInfoDO)getDO();
		
		StringBuffer buf = new StringBuffer();
		
	    buf.append( "<" + XML_NODE_HEAD + ">");
	    buf.append( "<" + XML_NODE_MODIFY + ">" + infoDO.getCanModify() + "</"  + XML_NODE_MODIFY + ">");
		buf.append( "<" + XML_NODE_MASTERID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTERID + ">");
		buf.append( "<" + XML_NODE_DATASTATUSCODE + ">" + infoDO.getDataStatCd() + "</"  + XML_NODE_DATASTATUSCODE + ">");
		buf.append( "<" + XML_NODE_DESCRIPTION + ">" + CommonUtl.transXmlText(infoDO.getDesc()) + "</"  + XML_NODE_DESCRIPTION + ">");
		buf.append(  "<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		buf.append( "<" + XML_NODE_BRDLENGTH + ">" + infoDO.getBrdLeng() + "</"  + XML_NODE_BRDLENGTH + ">");
		buf.append( "<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + ">");
		buf.append( "<" + XML_NODE_ARCHIVEREGDATE + ">" + infoDO.getArchRegDd() + "</"  + XML_NODE_ARCHIVEREGDATE + ">");
		buf.append( "<" + XML_NODE_COUNT + ">" + infoDO.getCount() + "</"  + XML_NODE_COUNT + ">");
		buf.append( "<" + XML_NODE_EPISNO + ">" + infoDO.getEpis_No() + "</" + XML_NODE_EPISNO + ">") ;
		buf.append( "<" + XML_NODE_BRDDD + ">" + infoDO.getBrdDd()  + "</" + XML_NODE_BRDDD + ">" );
		buf.append( "<" + XML_NODE_REQUESTCODE + ">" + infoDO.getReqCd()  + "</" + XML_NODE_REQUESTCODE + ">" );
		buf.append( "<" + XML_NODE_MODIFIERID + ">" + infoDO.getModrid()  + "</" + XML_NODE_MODIFIERID + ">" );
		buf.append( "<" + XML_NODE_USER_NM + ">" + CommonUtl.transXmlText(infoDO.getUser_nm())  + "</" + XML_NODE_USER_NM + ">" );
		buf.append( "<" + XML_NODE_ING_REG_DD + ">" + infoDO.getIng_Reg_DD()  + "</" + XML_NODE_ING_REG_DD + ">") ;
		buf.append( "<" + XML_NODE_LOCKSTATUSCODE + ">" + infoDO.getLock_stat_cd() + "</"  + XML_NODE_LOCKSTATUSCODE + ">");
		buf.append( "<" + XML_NODE_ERRORSTATUSCODE + ">" + infoDO.getError_stat_cd() + "</"  + XML_NODE_ERRORSTATUSCODE + ">");
		buf.append( "<" + XML_NODE_TAPEITEMID + ">" + infoDO.getTape_item_id() + "</"  + XML_NODE_TAPEITEMID + ">");
		buf.append( "<" + XML_NODE_MCUID + ">" + infoDO.getMcuid() + "</"  + XML_NODE_MCUID + ">");
		buf.append( "<" + XML_NODE_PGM_ID + ">" + infoDO.getPgm_id() + "</"  + XML_NODE_PGM_ID + ">");
		buf.append( "<" + XML_NODE_QUERY_RESULT_COUNT + ">" + infoDO.getQueryResultCount() + "</"  + XML_NODE_QUERY_RESULT_COUNT + ">");
		buf.append( "<" + XML_NODE_SUM_BRD_LENG + ">" + infoDO.getSum_brd_leng() + "</"  + XML_NODE_SUM_BRD_LENG + ">");
		buf.append( "<" + XML_NODE_CTGR_L_CD + ">" + infoDO.getCtgr_l_Cd() + "</"  + XML_NODE_CTGR_L_CD + ">");
		buf.append( "<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</"  + XML_NODE_CT_ID + ">");
		buf.append( "<" + XML_NODE_CTI_ID + ">" + infoDO.getCti_id() + "</"  + XML_NODE_CTI_ID + ">");
		buf.append( "<" + XML_NODE_CT_CLA + ">" + infoDO.getCt_cla() + "</"  + XML_NODE_CT_CLA + ">");
		buf.append( "<" + XML_NODE_CTGR_L_NM + ">" + infoDO.getCtgr_l_nm() + "</"  + XML_NODE_CTGR_L_NM + ">");
		buf.append( "<" + XML_NODE_STORAGE_NM + ">" + infoDO.getStorage() + "</"  + XML_NODE_STORAGE_NM + ">");
		buf.append( "<" + XML_NODE_FMDT + ">" + infoDO.getFM_DT() + "</"  + XML_NODE_FMDT + ">");
		buf.append( "<" + XML_NODE_LINK_PARENTS + ">" + infoDO.getLink_parent() + "</"  + XML_NODE_LINK_PARENTS + ">");
		buf.append( "</" + XML_NODE_HEAD + ">");

		
		return buf.toString();
	}
	
	
	
	

	
	public String getSubXML3() {
		MetaInfoDO infoDO = (MetaInfoDO)getDO();
		
		String _xml ="";
		
		_xml = _xml + "<totalinfo> \n";
		_xml = _xml + "<" + XML_NODE_TOTAL_COUNT + ">" + infoDO.getTotal_page() + "</"  + XML_NODE_TOTAL_COUNT + "> \n";
		_xml = _xml + "</totalinfo>";
		
		return _xml;
	}
	
	
	
	public String getReqListXML() {
		MetaInfoDO infoDO = (MetaInfoDO)getDO();
		
		StringBuffer buf = new StringBuffer();
		
	    buf.append( "<" + XML_NODE_HEAD + ">");
	
		buf.append(  "<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		buf.append( "<" + XML_NODE_CTI_ID + ">" + infoDO.getCti_id() + "</"  + XML_NODE_CTI_ID + ">");
		buf.append( "<" + XML_NODE_CTGR_L_NM + ">" + CommonUtl.transXmlText(infoDO.getCtgr_l_nm()) + "</"  + XML_NODE_CTGR_L_NM + ">");
		buf.append( "<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + ">");
		buf.append( "<" + XML_NODE_BRDDD + ">" + infoDO.getBrdDd()  + "</" + XML_NODE_BRDDD + ">" );
		
		buf.append( "</" + XML_NODE_HEAD + ">");

		
		return buf.toString();
	}
	
	
}
