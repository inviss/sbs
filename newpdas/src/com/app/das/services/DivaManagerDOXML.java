package com.app.das.services;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ManualDeleteDO;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.business.transfer.PdsArchiveDO;
import com.app.das.util.CommonUtl;
/**
 *  DIVA 요청 정보 관련 XML파서
 * @author asura207
 *
 */
public class DivaManagerDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "info";
	/** 
	 * 컨텐츠id
	 */
	private String XML_NODE_CT_ID = "ct_id";
	/** 
	 * das 장비id
	 */
	private String XML_NODE_DAS_EQ_ID = "das_eq_id";
	/** 
	 * das 장비 코드
	 */
	private String XML_NODE_DAS_EQ_PS_CD = "das_eq_ps_cd";
	/** 
	 * 컨텐츠인스턴스id
	 */
	private String XML_NODE_CTI_ID = "cti_id";
	/** 
	 * 복본그룹여부
	 */
	private String XML_NODE_COPY_TO_GROUP = "copy_to_group";
	/** 
	 * 외부 시스템 id
	 */
	private String XML_NODE_OUT_SYSTEM_ID = "out_system_id";
	/** 
	 * 경로-mxf
	 */
	private String XML_NODE_FL_PATH = "fl_path";
	/** 
	 * 경로-wmv
	 */
	private String XML_NODE_FILE_PATH = "file_path";
	/** 
	 * 파일명-mxf
	 */
	private String XML_NODE_FL_NM = "fl_nm";
	/** 
	 * 등록자id
	 */
	private String XML_NODE_REGRID = "regrid";
	/** 
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/** 
	 * 프로그램id
	 */
	private String XML_NODE_PGM_ID = "pgm_id";
	
	/** 
	 * 작업구분
	 */
	private String XML_NODE_JOB_GUBUN = "archive_type";
	
	/** 
	 * dtl 경로
	 */
	private String XML_NODE_DTL_TYPE = "dtl_type";
	
	
	/** 
	 * 작업 우선순위
	 */
	private String XML_NODE_PRIOITY = "priority";
	

	/** 
	 * 구분 005 아카이브 007 다운로드
	 */
	private String XML_NODE_GUBUN = "gubun";
	
	

	/** 
	 * KEY 값
	 */
	private String XML_NODE_NUM = "job_id";
	
	
	/** 
	 * 삭제요청
	 */
	private String DELETE = "delete";
	/** 
	 * VD_QLTY
	 */
	private String XML_NODE_VD_QLTY = "vd_qlty";
	/** 
	 * 파일명
	 */
	private String XML_NODE_FILE_NAME = "filename";
	
	
	/** 
	 * 회사코드
	 */
	private String XML_NODE_COCD = "co_cd";
	
	
	public Object setDO(String _xml) {
		setDO(new PdsArchiveDO());
		
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
		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_CT_ID)) {
				authorDO.setCt_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CTI_ID)) {
				authorDO.setCti_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_COPY_TO_GROUP)) {
				authorDO.setCopy_to_group(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FL_PATH)) {
				authorDO.setFl_path(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FL_NM)) {
				authorDO.setFl_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGRID)) {
				authorDO.setReq_id(_nodeValue);
			}

        }
	    
	    return authorDO;
	}
	public String toXML(){
		return "";
	}
	public String toXML(String DataXml) {
		/**  DTL 아카이브 요청 XML 샘플 시작 **/
//		 <?xml version="1.0" encoding="utf-8"?>
//		<das>
//		<info>
//		<das_eq_id>4</das_eq_id> 장비 아이디
//		<das_eq_ps_cd>005</das_eq_ps_cd> 장비 프로세스 아이디
//		<cti_id>550083</cti_id> 콘텐츠 인스턴스 아이디
//		<copy_to_group>N</copy_to_group>
//		<file_path></file_path> 파일 패스
//		<regrid>register</regrid> 등록자 아이디
//		</info>
//		</das>
		/** DTL 아카이브 요청 XML 샘플 끝 **/

		/** DTL 아카이브+복본 요청 XML 샘플 시작 **/
//		<?xml version="1.0" encoding="utf-8"?>
//		<das>
//		<info>
//		<das_eq_id>4</das_eq_id> 장비 아이디
//		<das_eq_ps_cd>005</das_eq_ps_cd> 장비 프로세스 아이디
//		<cti_id>0</cti_id> 콘텐츠 인스턴스 아이디
//		<copy_to_group>Y</copy_to_group> 자체적으로 아카이브 후 복본 요청
//		<file_path></file_path> 파일 패스
//		<regrid>register</regrid> 등록자 아이디
//		</info>
//		</das>
		/** DTL 아카이브+복본 요청 XML 샘플 끝 **/
		
		/** DTL 복본 요청 XML 샘플 시작 **/
//		<?xml version="1.0" encoding="utf-8"?>
//		<das>
//		<info>
//		<das_eq_id>4</das_eq_id> 장비 아이디
//		<das_eq_ps_cd>005</das_eq_ps_cd> 장비 프로세스 아이디
//		<copy_to_group>Y</copy_to_group> 아카이브 된것에 대해서 복본 요청
//		<out_system_id></out_system_id>    <= 필수 값 'DAS'+cti_id
//		<regrid>register</regrid> 등록자 아이디
//		</info>
//		</das>
		/** DTL 복본 요청 XML 샘플 끝 **/
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		_xml.append("<das> \n");
		_xml.append(DataXml);
		_xml.append("</das>");
		
		return _xml.toString();
	}
	
	
	public String getArchiveXML() {
		/**  DTL 아카이브 요청 XML 샘플 시작 **/
		/** DTL 아카이브+복본 요청 XML 샘플 시작 **/
		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_CTI_ID + ">" +authorDO.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n");
		_xml.append("<" + XML_NODE_COPY_TO_GROUP + ">" +authorDO.getCopy_to_group() + "</"  + XML_NODE_COPY_TO_GROUP + "> \n");
		_xml.append("<" + XML_NODE_FILE_PATH + ">" + authorDO.getFl_path()+"/"+ authorDO.getFl_nm()+ "</"  + XML_NODE_FILE_PATH + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">"+ authorDO.getReq_id()+"</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

	return toXML(_xml.toString());
	}
	
//	public String getArchiveCopyXML() {
//		/** DTL 아카이브+복본 요청 XML 샘플 시작 **/
//		
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
//		
//		String _xml =   "<" + XML_NODE_HEAD + "> \n";	
//		_xml = _xml + 	"<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n";
//		_xml = _xml + "<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n";
//		_xml = _xml + "<" + XML_NODE_CTI_ID + ">" +authorDO.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n";
//		_xml = _xml + "<" + XML_NODE_COPY_TO_GROUP + ">" +authorDO.getCopy_to_group() + "</"  + XML_NODE_COPY_TO_GROUP + "> \n";
//		_xml = _xml + "<" + XML_NODE_FILE_PATH + ">" + authorDO.getFl_path()+"/"+ authorDO.getFl_nm()+ "</"  + XML_NODE_FILE_PATH + "> \n";
//		_xml = _xml + "<" + XML_NODE_REGRID + ">requester</"  + XML_NODE_REGRID + "> \n";
//		_xml = _xml + "</" + XML_NODE_HEAD + ">";
//
//	return toXML(_xml);
//	}
	
	public String getCopyXML() {
		/** DTL 복본 요청 XML 샘플 시작 **/
		
		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_COPY_TO_GROUP + ">" +"Y" + "</"  + XML_NODE_COPY_TO_GROUP + "> \n");
		_xml.append("<" + XML_NODE_OUT_SYSTEM_ID+ ">" + "DAS"+authorDO.getCti_id()+ "</"  + XML_NODE_OUT_SYSTEM_ID + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">requester</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

	return toXML(_xml.toString());
	}
	
	public String getArchiveXML(PdsArchiveDO authorDO) {
		/**  DTL 아카이브 요청 XML 샘플 시작 **/
		/** DTL 아카이브+복본 요청 XML 샘플 시작 **/
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_CTI_ID + ">" +authorDO.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n");
		_xml.append("<" + XML_NODE_COPY_TO_GROUP + ">"+/* +authorDO.getCopy_to_group() +*/ "</"  + XML_NODE_COPY_TO_GROUP + "> \n");
		_xml.append("<" + XML_NODE_FILE_PATH + ">" + authorDO.getFl_path()+"/"+ authorDO.getFl_nm()+ "</"  + XML_NODE_FILE_PATH + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">"+ authorDO.getReq_id()+"</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	
	public String getCopyXML(PdsArchiveDO authorDO) {
		/** DTL 복본 요청 XML 샘플 시작 **/
		
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_COPY_TO_GROUP + ">" +"Y" + "</"  + XML_NODE_COPY_TO_GROUP + "> \n");
		_xml.append("<" + XML_NODE_OUT_SYSTEM_ID+ ">" + "DAS"+authorDO.getCti_id()+ "</"  + XML_NODE_OUT_SYSTEM_ID + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">"+ authorDO.getReq_id()+"</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	//2012.5.24
	public String getNewCopyXML(PdsArchiveDO authorDO) {
		/** DTL 복본 요청 XML 샘플 시작 **/
		
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_JOB_GUBUN + ">h264</"  + XML_NODE_JOB_GUBUN + "> \n");
		_xml.append("<" + XML_NODE_COPY_TO_GROUP + ">" +"Y" + "</"  + XML_NODE_COPY_TO_GROUP + "> \n");
		if(authorDO.getCocd().equals("S")){
			_xml.append("<" + XML_NODE_DTL_TYPE + ">das</"  + XML_NODE_DTL_TYPE + "> \n");
			}else {
			_xml.append("<" + XML_NODE_DTL_TYPE + ">medianet</"  + XML_NODE_DTL_TYPE + "> \n");
					
			}
		_xml.append("<" + XML_NODE_OUT_SYSTEM_ID+ ">" + "DAS"+authorDO.getCti_id()+ "</"  + XML_NODE_OUT_SYSTEM_ID + "> \n");
		_xml.append("<" + XML_NODE_FILE_NAME + ">" + authorDO.getFl_nm()+ "</"  + XML_NODE_FILE_NAME + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">"+ authorDO.getReq_id()+"</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	public String getNewBackupXML(PdsArchiveDO authorDO) {
		/** DTL 소산 요청 XML 샘플 시작 **/
		
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_JOB_GUBUN + ">h264</"  + XML_NODE_JOB_GUBUN + "> \n");
	
			_xml.append("<" + XML_NODE_DTL_TYPE + ">backup</"  + XML_NODE_DTL_TYPE + "> \n");
			 
			_xml.append("<" + XML_NODE_COCD + ">"+authorDO.getCocd()+"</"  + XML_NODE_COCD + "> \n");
			 
			_xml.append("<" + XML_NODE_FILE_NAME + ">" + authorDO.getFl_nm()+ "</"  + XML_NODE_FILE_NAME + "> \n");
		_xml.append("<" + XML_NODE_OUT_SYSTEM_ID+ ">" + "DAS"+authorDO.getCti_id()+ "</"  + XML_NODE_OUT_SYSTEM_ID + "> \n");
		
		_xml.append("<" + XML_NODE_REGRID + ">"+ authorDO.getReq_id()+"</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	
	

	public String getNewArchiveXML(PdsArchiveDO authorDO) {
		/**  DTL 아카이브 요청 XML 샘플 시작 **/
		/** DTL 아카이브+복본 요청 XML 샘플 시작 **/
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_CTI_ID + ">" +authorDO.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n");
		
		//20121105 프로그램이고 방송본의 경우 아카이브 + 복본 그이외는 아카이브만 요청한다. BY ASURA
		if(authorDO.getCt_cla().equals("006")&&authorDO.getCtgr_l_cd().equals("200")){
		_xml.append("<" + XML_NODE_JOB_GUBUN + ">all</"  + XML_NODE_JOB_GUBUN + "> \n");
		}else{
			_xml.append("<" + XML_NODE_JOB_GUBUN + ">mxf</"  + XML_NODE_JOB_GUBUN + "> \n");
		}
		if(authorDO.getCocd().equals("S")){
		_xml.append("<" + XML_NODE_DTL_TYPE + ">das</"  + XML_NODE_DTL_TYPE + "> \n");
		}else {
		_xml.append("<" + XML_NODE_DTL_TYPE + ">medianet</"  + XML_NODE_DTL_TYPE + "> \n");
				
		}
		_xml.append("<" + XML_NODE_FILE_PATH + ">" + authorDO.getFl_path()+"/"+ authorDO.getFl_nm()+ "</"  + XML_NODE_FILE_PATH + "> \n");
		_xml.append("<" + XML_NODE_FILE_NAME + ">" + authorDO.getFl_nm()+ "</"  + XML_NODE_FILE_NAME + "> \n");
		_xml.append("<" + XML_NODE_VD_QLTY + ">" + authorDO.getVd_qulty()+ "</"  + XML_NODE_VD_QLTY + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">"+ authorDO.getReq_id()+"</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	
	
	
	public String getNewArchiveTESTXML(PdsArchiveDO authorDO) {
		/**  DTL 아카이브 요청 XML 샘플 시작 **/
		/** DTL 아카이브+복본 요청 XML 샘플 시작 **/
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_CTI_ID + ">" +authorDO.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n");
		_xml.append("<" + XML_NODE_JOB_GUBUN + ">all</"  + XML_NODE_JOB_GUBUN + "> \n");
		if(authorDO.getCocd().equals("S")){
		_xml.append("<" + XML_NODE_DTL_TYPE + ">das</"  + XML_NODE_DTL_TYPE + "> \n");
		}else {
		_xml.append("<" + XML_NODE_DTL_TYPE + ">medianet</"  + XML_NODE_DTL_TYPE + "> \n");
				
		}
		_xml.append("<" + XML_NODE_FILE_PATH + ">" + authorDO.getFl_path()+"/"+ authorDO.getFl_nm()+ "</"  + XML_NODE_FILE_PATH + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">D080009</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	
	
	
	
	public String getRecorveryXML(PdsArchiveDO authorDO) {
		/** DTL 복원 요청 XML 시작 **/
		
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();

		StringBuffer _xml = new StringBuffer();	
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n");
		_xml.append("<" + XML_NODE_JOB_GUBUN + ">h264</"  + XML_NODE_JOB_GUBUN + "> \n");
		if(authorDO.getCocd().equals("S")){
			_xml.append("<" + XML_NODE_DTL_TYPE + ">das</"  + XML_NODE_DTL_TYPE + "> \n");
			}else {
			_xml.append("<" + XML_NODE_DTL_TYPE + ">medianet</"  + XML_NODE_DTL_TYPE + "> \n");
					
			}
		_xml.append("<" + XML_NODE_OUT_SYSTEM_ID+ ">" + "DAS"+authorDO.getCti_id()+ "</"  + XML_NODE_OUT_SYSTEM_ID + "> \n");
		
		_xml.append("<" + XML_NODE_REGRID + ">"+ authorDO.getReq_id()+"</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	
	
	
	
	
	
	
	/**
	 * 진행중 작업 취소 요청
	 * */
	public String getCancleArchiveJobXML(MonitoringDO authorDO) {
		/** DTL 복본 요청 XML 샘플 시작 **/
		
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
	//	_xml.append(	"<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n";
	//	_xml = _xml + "<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n";
		//_xml = _xml + "<" + XML_NODE_COPY_TO_GROUP + ">" +"Y" + "</"  + XML_NODE_COPY_TO_GROUP + "> \n";
		
		
		_xml.append("<" + XML_NODE_GUBUN + ">"+authorDO.getGubun()+"</"  + XML_NODE_GUBUN + "> \n");
		_xml.append("<" + XML_NODE_NUM + ">"+authorDO.getKey()+"</"  + XML_NODE_NUM + "> \n");
		_xml.append("<" + XML_NODE_OUT_SYSTEM_ID+ ">" + authorDO.getObj_name()+ "</"  + XML_NODE_OUT_SYSTEM_ID + "> \n");
		if(authorDO.getDtl_type().equals("S")){
			_xml.append("<" + XML_NODE_DTL_TYPE + ">das</"  + XML_NODE_DTL_TYPE + "> \n");
			}else {
			_xml.append("<" + XML_NODE_DTL_TYPE + ">medianet</"  + XML_NODE_DTL_TYPE + "> \n");
					
			}
		
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	
	

	/**
	 * 우선순위 변경
	 * */
	public String getChangePriorityJobXML(MonitoringDO authorDO) {
		/** DTL 우선순위 변경  XML  시작 **/
		
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
	//	_xml = _xml + 	"<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n";
	//	_xml = _xml + "<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n";
		_xml.append("<" + XML_NODE_GUBUN+ ">" +authorDO.getGubun() + "</"  + XML_NODE_GUBUN + "> \n");
		_xml.append("<" + XML_NODE_NUM+ ">" +authorDO.getKey() + "</"  + XML_NODE_NUM + "> \n");
		_xml.append("<" + XML_NODE_OUT_SYSTEM_ID+ ">" + authorDO.getObj_name()+ "</"  + XML_NODE_OUT_SYSTEM_ID + "> \n");
		if(authorDO.getDtl_type().equals("S")){
			_xml.append("<" + XML_NODE_DTL_TYPE + ">das</"  + XML_NODE_DTL_TYPE + "> \n");
			}else {
			_xml.append("<" + XML_NODE_DTL_TYPE + ">medianet</"  + XML_NODE_DTL_TYPE + "> \n");
					
			}
		_xml.append("<" + XML_NODE_PRIOITY + ">"+authorDO.getPriority()+"</"  + XML_NODE_PRIOITY + "> \n");
		
		_xml.append("</" + XML_NODE_HEAD + ">");

		return toXML(_xml.toString());
	}
	
	
	
	

	
	/**
	 * 삭제 요청
	 * */
	/*public String getDeleteJobXML(ManualDeleteDO manualDeleteDO) {
		*//** DTL 삭제 요청 XML 샘플 시작 **//*
		
//		PdsArchiveDO authorDO = (PdsArchiveDO)getDO();
		
		String _xml =   "<" + XML_NODE_HEAD + "> \n";	
	//	_xml = _xml + 	"<" + XML_NODE_DAS_EQ_ID + ">4</"  + XML_NODE_DAS_EQ_ID + "> \n";
	//	_xml = _xml + "<" + XML_NODE_DAS_EQ_PS_CD + ">005</"  + XML_NODE_DAS_EQ_PS_CD + "> \n";
		//_xml = _xml + "<" + XML_NODE_COPY_TO_GROUP + ">" +"Y" + "</"  + XML_NODE_COPY_TO_GROUP + "> \n";
		_xml = _xml + "<" + XML_NODE_OUT_SYSTEM_ID+ ">" + manualDeleteDO.getObj_name()+ "</"  + XML_NODE_OUT_SYSTEM_ID + "> \n";
		if(manualDeleteDO.getDtl_type().equals("S")){
			_xml = _xml + "<" + XML_NODE_DTL_TYPE + ">das</"  + XML_NODE_DTL_TYPE + "> \n";
			}else {
			_xml = _xml + "<" + XML_NODE_DTL_TYPE + ">medianet</"  + XML_NODE_DTL_TYPE + "> \n";
					
			}
		
		_xml = _xml + "</" + XML_NODE_HEAD + ">";

		return toXML(_xml);
	}*/
	//20120730 강명성 과장님 메일내용을 토대로 다시 만듬.
	public String getDeleteJobXML(ManualDeleteDO manualDeleteDO) {
		/** DTL 삭제 요청 XML 샘플 시작 **/
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">\n");
		_xml.append("<req_method>user</req_method>\n");
		_xml.append("</"  + XML_NODE_HEAD + ">\n");
		
		_xml.append("<" + DELETE + ">\n");;
		_xml.append("<" + XML_NODE_CT_ID + ">"+manualDeleteDO.getCt_id()+"</"  + XML_NODE_CT_ID + "> \n");
		_xml.append("</"  + DELETE + ">\n");
		
		return toXML(_xml.toString());
	}
	
	//20141201 PDS 메타 삭제로직을 변경 W/F로 파일삭제요청.
	public String getDeleteJobForPDSXML(long ctId) {
		/** DTL 삭제 요청 XML 샘플 시작 **/
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">\n");
		_xml.append("<req_method>force</req_method>\n");
		_xml.append("</"  + XML_NODE_HEAD + ">\n");
		
		_xml.append("<" + DELETE + ">\n");;
		_xml.append("<" + XML_NODE_CT_ID + ">"+ctId+"</"  + XML_NODE_CT_ID + "> \n");
		_xml.append("</"  + DELETE + ">\n");
		
		return toXML(_xml.toString());
	}
}
