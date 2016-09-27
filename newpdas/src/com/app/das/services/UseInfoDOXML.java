package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.UseInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  이용 현황 정보  정보 관련 XML파서
 * @author asura207
 *
 */
public class UseInfoDOXML extends DOXml{
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "useinfo";
	/** 분류명 */
	private String XML_NODE_DESC = "desc";
	/** 제목 */
	private String XML_NODE_TITLE = "title";
	/** 방송일 */
	private String XML_NODE_BRD_DD = "brd_dd";
	/** 촬영일 */
	private String XML_NODE_FM_DD = "fm_dd";
	/** 등록일 */
	private String XML_NODE_REG_DT = "reg_dt";
	/** 길이 */
	private String XML_NODE_BRD_LENG = "brd_leng";
	/** 이용횟수 */
	private String XML_NODE_USE_CONT = "use_cont";
	/** 복본여부 */
	private String XML_NODE_COPY_OBJ_YN = "copy_obj_yn";
	/** 등록일 시작 */
	private String XML_NODE_START_REG_DT = "start_reg_dt";
	/** 등록일 종료 */
	private String XML_NODE_END_REG_DT = "end_reg_dt";
	/** 구분코드 */
	//private String XML_NODE_CLF_CD = "clf_cd";
	/** 구분상세코드 */
	private String XML_NODE_SCL_CD = "scl_cd";
	/** 설명 */
	private String XML_NODE_CONT_FLAG = "cont_flag";
	/** 컬럼명 */
	private String XML_NODE_COLUMNNAME = "coulmnname";
	/** 마스터 ID */
	private String XML_NODE_MASTER_ID = "master_id";
	/** 프로그램명 */
	private String XML_NODE_PGM_NM = "pgm_nm";
	/** 이용횟수 시작 */
	private String XML_NODE_USE_START_CONT = "use_start_count";
	/** 이용횟수 종료 */
	private String XML_NODE_USE_END_CONT = "use_end_count";
	/** 컨텐츠id */
	//private String XML_NODE_CTI_ID = "cti_id";
	/** page */
	private String XML_NODE_PAGE = "page";
	/** 총조회횟수 */
	private String XML_NODE_TOTAL_COUNT = "total_count";
	/** 대분류코드 */
	private String XML_NODE_CTGR_L_CD = "ctgr_l_cd";
	/** 유저id */
	private String XML_NODE_USER_ID = "user_id";
	/** 회차번호 */
	private String XML_NODE_EPIS_NO = "epis_no";
	/** 총길이 */
	private String XML_NODE_TOTAL_LENG = "total_leng";

	/** 방송시작일 */
	private String XML_NODE_BRD_START_DD= "start_brd_dd";
	/** 방송종료일 */
	private String XML_NODE_BRD_END_DD = "end_brd_dd";


	//2012.5.14
	/** 소산 여부 */
	private String XML_NODE_BACKUP_YN = "backup_yn";
	/** 소산 신청자 */
	private String XML_NODE_BAKCUP_ID= "backup_id";
	/** 소산 신청일 */
	private String XML_NODE_BACKUP_DT = "backup_dt";
	/** 구분 001 : 복본 002 : 소산 */
	private String XML_NODE_GUBUN = "gubun";

	/** 회사코드 */
	private String XML_NODE_COCD = "cocd";
	/** 채널코드*/
	private String XML_NODE_CHENNEL = "chennel";

	/** 복본여부(과거)*/
	private String XML_NODE_COPY_STATUS = "old_copy_obj_yn";

	public Object setDO(String _xml) {
		setDO(new UseInfoDO());

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
		UseInfoDO infoDO = (UseInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_DESC)) {
				infoDO.setDesc(_nodeValue);
			}
			else if(_nodeName.equals( XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_DD)) {
				infoDO.setBrd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FM_DD)) {
				infoDO.setFm_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_LENG)) {
				infoDO.setBrd_leng(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USE_CONT)) {
				infoDO.setUse_cont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COPY_OBJ_YN)) {
				infoDO.setCopy_object_yn(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_START_REG_DT)) {
				infoDO.setStart_reg_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_END_REG_DT)) {
				infoDO.setEnd_reg_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_REG_DT)) {
				infoDO.setReg_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_COLUMNNAME)) {
				infoDO.setClf_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SCL_CD)) {
				infoDO.setScl_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CONT_FLAG)) {
				infoDO.setFlag(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				infoDO.setMaster_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PGM_NM)) {
				infoDO.setTitle(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_USE_START_CONT)) {
				infoDO.setUse_start_cont(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_USE_END_CONT)) {
				infoDO.setUse_end_cont(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PAGE)) {
				infoDO.setPage(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_CTGR_L_CD)) {
				infoDO.setCtgr_l_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_ID)) {
				infoDO.setUser_id(_nodeValue);
				infoDO.setBackup_id(_nodeValue);
			}	else if(_nodeName.equals(XML_NODE_EPIS_NO)) {
				infoDO.setEpis_no(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_BRD_START_DD)) {
				infoDO.setStart_brd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_END_DD)) {
				infoDO.setEnd_brd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BACKUP_YN)) {
				infoDO.setBackup_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BAKCUP_ID)) {
				infoDO.setBackup_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BACKUP_DT)) {
				infoDO.setBackup_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GUBUN)) {
				infoDO.setGubun(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_COCD)) {
				infoDO.setCocd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CHENNEL)) {
				infoDO.setChennel(_nodeValue);
			}
		}



		return infoDO;
	}	    

	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("\n <?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		_xml.append("\n <das> \n");
		_xml.append(getSubXML());
		_xml.append("\n </das>");

		return _xml.toString();
	}

	public String getSubXML() {
		UseInfoDO infoDO = (UseInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("\n<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_DESC+ "> " + CommonUtl.transXmlText(infoDO.getDesc()) + "</"  + XML_NODE_DESC + ">  \n ");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_FM_DD + ">" + infoDO.getFm_dd() + "</"  + XML_NODE_FM_DD + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + infoDO.getBrd_leng() + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_USE_CONT + ">" + CommonUtl.transXmlText(infoDO.getUse_cont()) + "</"  + XML_NODE_USE_CONT + "> \n");
		_xml.append("<" + XML_NODE_COPY_OBJ_YN + ">" + infoDO.getCopy_object_yn() + "</"  + XML_NODE_COPY_OBJ_YN + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + infoDO.getEpisno() + "</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("<" + XML_NODE_BACKUP_YN + ">" + infoDO.getBackup_yn() + "</"  + XML_NODE_BACKUP_YN + "> \n");
		_xml.append("<" + XML_NODE_COPY_STATUS + ">" + infoDO.getCopy_status() + "</"  + XML_NODE_COPY_STATUS + "> \n");
		_xml.append("<" + XML_NODE_CHENNEL + ">" +infoDO.getChennel() + "</"  + XML_NODE_CHENNEL + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}


	public String getSubXML2() {
		UseInfoDO infoDO = (UseInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append( "\n<totalinfo> \n");

		_xml.append("<" + XML_NODE_TOTAL_COUNT + ">" + infoDO.getTotal() + "</"  + XML_NODE_TOTAL_COUNT + "> \n");
		_xml.append("<" + XML_NODE_TOTAL_LENG + ">" + infoDO.getTotal_leng() + "</"  + XML_NODE_TOTAL_LENG + "> \n");

		_xml.append("</totalinfo>");

		return _xml.toString();
	}

}
