package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.BoardDO;
import com.app.das.business.transfer.DownCartDO;
import com.app.das.business.transfer.PdsDownDO;
import com.app.das.util.CommonUtl;
import com.app.das.util.XmlUtil;



import java.io.*;





/**
 *  pds 다운 정보 관련 XML파서
 * @author asura207
 *
 */
public class PdsDownDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_PREVIEWNOTE = "previewnote";
	/**
	 * xml 해더
	 */
	private String XML_NODE_MEDIA_META = "media_meta";
	/**
	 * 경로
	 */
	private String XML_NODE_STREAM_PATH = "stream_path";
	/**
	 * xml 해더
	 */
	private String XML_NODE_STREAM = "stream";
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "clipinformation";
	/**
	 * xml 해더
	 */
	private String XML_NODE_SUB_HEAD = "system_meta";
	/**
	 *  프로그램명
	 */
	private String XML_NODE_PROGRAM_NAME = "program_name";
	/**
	 *  제목
	 */
	private String XML_NODE_PROGRAM_TITLE="program_title";
	/**
	 *  표기시작 해더
	 */
	private String XML_NODE_START_OF_MARK="start_of_mark";
	/**
	 *  표기종료 해더
	 */
	private String XML_NODE_END_OF_MARK="end_of_mark";
	/**
	 * 파일명(pds)
	 * 	 */
	private String XML_NODE_FILENAME="filename";
	/**
	 * 파일크기(실제 다운)
	 * 	 */
	private String XML_NODE_FILESIZE="filesize";	
	/**
	 * xml 해더
	 */
	private String XML_NODE_ISMULTIFILE="ismultifile";
	/**
	 * 미디어 id
	 */
	private String XML_NODE_MEDIAID="mediaid";
	/**
	 * 파일 경로
	 * 	 */
	private String XML_NODE_FILEPATH="filepath";
	/**
	 * 카테고리
	 * 
	 * 	 */
	private String XML_NODE_CATEGORY="category";

	/**
	 * 스토리지명
	 * 
	 * 	 */
	private String XML_NODE_STORAGE_NAME="storagename";
	/**
	 * 미디어타입
	 * 
	 * 	 */
	private String XML_NODE_MEDIA_TYPE="media_type";
	/**
	 * xml 해더
	 */
	private String XML_NODE_ASSETID="assetid";
	/**
	 * 경로
	 */
	private String XML_NODE_STREAMS_PATH="streams_path";
	/**
	 * HD여부
	 * 	 */
	private String XML_NODE_HDMODE="hdmode";
	/**
	 * 프로그램id
	 * 	 */
	private String XML_NODE_PDS_PROGRAM_ID="pds_program_id";
	/**
	 * 시작점
	 * 	 */
	private String XML_NODE_SOM="som";
	/**
	 * 종료점
	 * 	 */
	private String XML_NODE_EOM="eom";
	/**
	 * 수직해상도
	 * 	 */
	private String XML_NODE_VD_HRESOL="vd_hresol";
	/**
	 * 수평해상도
	 * 	 */
	private String XML_NODE_VD_VRESOL="vd_vresol";
	/**
	 * 파일크기
	 * 	 */
	private String XML_NODE_FL_SZ="fl_sz";
	/**
	 * 비트전송률
	 * 	 */
	private String XML_NODE_BIT_RT="bit_rt";
	/**
	 * 오디오 샘플링
	 * 	 */
	private String XML_NODE_AUD_SAMP_FRQ="aud_samp_frq";

	/**
	 * 오디오 대역폭
	 * 	 */
	private String XML_NODE_AUD_BDWT="aud_bdwt";
	/**
	 * 회차
	 * 	 */
	private String XML_NODE_EPIS_NO="epis_no";
	/**
	 * 프로듀서명
	 * 	 */
	private String XML_NODE_PRODUCER_NM="producer_nm";
	/**
	 * 촬영장소
	 * 	 */
	private String XML_NODE_CMR_PLACE="cmr_place";
	/**
	 * 촬영일
	 * 	 */
	private String XML_NODE_FM_DT="fm_dt";
	/**
	 * 저작권자
	 * 	 */
	private String XML_NODE_CPRT_NM="cprt_nm";
	/**
	 * 저작권유형
	 * 	 */
	private String XML_NODE_CPRT_CD="cprt_cd";
	/**
	 * 사용등급
	 * 	 */
	private String XML_NODE_RIST_CLF_CD="rist_clf_cd";
	/**
	 * 시청등급
	 * 	 */
	private String XML_NODE_DELIBERATION_CD="deliberation_cd";
	/**
	 * 미디어id
	 * 	 */
	private String XML_NODE_MEDIA_ID="media_id";
	/**
	 * 녹음방식 코드
	 * 	 */
	private String XML_NODE_RECORD_TYPE_CD="record_type_cd";
	/**
	 * 초당 키프레임
	 * 	 */
	private String XML_NODE_FRM_PER_SEC="frm_per_sec";
	/**
	 * 컨텐츠 유형
	 * 	 */
	private String XML_NODE_CT_TYP="ct_typ";
	/**
	 * 콘텐츠 길이
	 * 	 */
	private String XML_NODE_CT_LENG="ct_leng";
	/**
	 * 종횡비
	 * 	 */
	private String XML_NODE_ASP_RTO_CD="asp_rto_cd";
	/**
	 * 콘텐츠구분
	 * 	 */
	private String XML_NODE_CT_CLA="ct_cla";
	/**
	 * 방송길이
	 * 	 */
	private String XML_NODE_BRD_LENG="brd_leng";
	/**
	 * 방송종료시각
	 * 	 */
	private String XML_NODE_BRD_END_HMS="brd_end_hms";
	/**
	 * 방송시작시각
	 * 	 */
	private String XML_NODE_BRD_BGN_HMS="brd_bgn_hms";
	/**
	 * 방송일자
	 * 	 */
	private String XML_NODE_BRD_DD="brd_dd";
	/**
	 * 타이틀
	 * 	 */
	private String XML_NODE_TITLE="title";
	/**
	 * 프로그램명
	 * 	 */
	private String XML_NODE_PGM_NM="pgm_nm";
	/**
	 * 미디어
	 */
	private String XML_NODE_MEDIA="media";
	/**
	 * 미디어 유형
	 */
	private String XML_NODE_MEDIA_TYPE_CODE="mediatypecode";
	/**
	 * 부제
	 */
	private String XML_NODE_SUB_TTL="subtitle";
	/**
	 * 물리적 트리
	 */
	private String XML_NODE_PHYSICALTREE="physicaltree";
	/**
	 * 논리적 트리
	 */
	private String XML_NODE_LOGICALTREE="logicaltree";
	/**
	 * 작업자 명
	 */
	private String XML_NODE_WORKERNAME="workername";
	/**
	 * xml해더
	 */
	private String XML_NODE_METADATA="Metadata";
	/**
	 * 생방송일
	 */
	private String XML_NODE_ONAIRDATE="onairdate";
	/**
	 * 생방송 시간
	 */
	private String XML_NODE_ONAIRTIME="onair_time";
	/**
	 * 코멘트
	 */
	private String XML_NODE_CM_NOTE="cm_note";



	public Object setDO(String _xml) {
		setDO(new PdsDownDO());

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
		PdsDownDO pdsdown = (PdsDownDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PROGRAM_NAME)) {
				pdsdown.setCart_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_PROGRAM_TITLE)) {
				pdsdown.setCart_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}


		}

		return pdsdown;
	}
	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?> \n");
		_xml.append("<sbsnds xmlns=\"urn:sbsnds2010\"> \n");
		_xml.append(getSubXML());
		_xml.append("</sbsnds>");

		return _xml.toString();
	}




	public String getSubXML() {
		PdsDownDO pdsdown = (PdsDownDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<sbspds xmlns=\"urn:sbspds2010\">\n");
		_xml.append(" <generator>      <generator_name>SBSDAS</generator_name> \n" +
				" <generator_version>1.0</generator_version>   </generator>");
		_xml.append( "<stream_total_size>"+pdsdown.getFilesize()+"</stream_total_size> \n");

		_xml.append( "<"+XML_NODE_STREAM_PATH+">");
		//2011.05.17 박보아대리 원복요청에 의해 원복
		//	_xml = _xml +  "<"+XML_NODE_STREAM+" isfolder=\"false\" src = \""+pdsdown.getFilename()+"\">"   +pdsdown.getFl_path()+"/"+pdsdown.getRename() + "</"  + XML_NODE_STREAM + "> ";
		_xml.append( "<"+XML_NODE_STREAM+" isfolder=\"false\" src = \""+pdsdown.getCart_no()+"_"+pdsdown.getFilename()+"\">"   +pdsdown.getRename() +".mxf"+ "</"  + XML_NODE_STREAM + "> \n");


		//	_xml = _xml +  "<"+XML_NODE_STREAM+" isfolder=\"false\" src = \"\">" + "</"  + XML_NODE_STREAM + "> ";;
		_xml.append("</"+XML_NODE_STREAM_PATH+">");	
		_xml.append("<"+XML_NODE_HEAD+">");		  
		_xml.append("<" + XML_NODE_MEDIA_TYPE + ">video</"  + XML_NODE_MEDIA_TYPE + "> \n");
		_xml.append("<"+XML_NODE_SUB_HEAD+">");			
		_xml.append("<" + XML_NODE_STORAGE_NAME + ">" + pdsdown.getStoragename() + "</"  + XML_NODE_STORAGE_NAME + "> \n");
		_xml.append("<" + XML_NODE_CATEGORY + ">" + pdsdown.getCategory() + "</"  + XML_NODE_CATEGORY + "> \n");
		_xml.append("<" + XML_NODE_FILEPATH + ">" + pdsdown.getFl_path() + "</"  + XML_NODE_FILEPATH + "> \n");
		_xml.append("<" + XML_NODE_FILENAME + ">" +pdsdown.getRename()+".mxf"+ "</"  + XML_NODE_FILENAME + "> \n");
		_xml.append("</"+XML_NODE_SUB_HEAD+">\n");	
		_xml.append("<"+XML_NODE_MEDIA_META+">\n");			
		_xml.append("<" + XML_NODE_HDMODE + ">" + pdsdown.getHdmode() + "</"  + XML_NODE_HDMODE + "> \n");
		_xml.append("<" + XML_NODE_PDS_PROGRAM_ID + ">" + pdsdown.getPds_program_id()  + "</"  + XML_NODE_PDS_PROGRAM_ID + "> \n");
		_xml.append("<" + XML_NODE_SOM + ">"+pdsdown.getSom()+"</"  + XML_NODE_SOM + "> \n");
		_xml.append("<" + XML_NODE_EOM + ">"+pdsdown.getEom()+"</"  + XML_NODE_EOM + "> \n");
		_xml.append("<" + XML_NODE_VD_HRESOL + ">"+pdsdown.getVd_hresol()+"</"  + XML_NODE_VD_HRESOL + "> \n");
		_xml.append("<" + XML_NODE_VD_VRESOL + ">"+pdsdown.getVd_vresol()+"</"  + XML_NODE_VD_VRESOL + "> \n");
		_xml.append("<" + XML_NODE_FL_SZ + ">" + pdsdown.getFilesize() + "</"  + XML_NODE_FL_SZ + "> \n");
		_xml.append("<" + XML_NODE_BIT_RT + ">"+pdsdown.getBit_rt()+"</"  + XML_NODE_BIT_RT + "> \n");
		_xml.append("<" + XML_NODE_AUD_SAMP_FRQ + ">" + pdsdown.getAud_samp_frq()  + "</"  + XML_NODE_AUD_SAMP_FRQ + "> \n");
		_xml.append("<" + XML_NODE_AUD_BDWT + ">" + pdsdown.getAud_bdwt()  + "</"  + XML_NODE_AUD_BDWT + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">"+pdsdown.getEpis_no()+"</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("<" + XML_NODE_PRODUCER_NM + ">"+CommonUtl.transXmlText(pdsdown.getProducer_nm())+"</"  + XML_NODE_PRODUCER_NM + "> \n");
		_xml.append("<" + XML_NODE_CMR_PLACE + ">"+CommonUtl.transXmlText(pdsdown.getCmr_place())+"</"  + XML_NODE_CMR_PLACE + "> \n");
		_xml.append("<" + XML_NODE_FM_DT + ">"+pdsdown.getFm_dt()+"</"  + XML_NODE_FM_DT + "> \n");
		_xml.append("<" + XML_NODE_CPRT_NM + ">" + CommonUtl.transXmlText(pdsdown.getCprt_nm()) + "</"  + XML_NODE_CPRT_NM + "> \n");
		_xml.append("<" + XML_NODE_CPRT_CD + ">"+pdsdown.getCprt_cd()+"</"  + XML_NODE_CPRT_CD + "> \n");
		_xml.append("<" + XML_NODE_RIST_CLF_CD + ">" + pdsdown.getRist_clf_cd()  + "</"  + XML_NODE_RIST_CLF_CD + "> \n");
		_xml.append("<" + XML_NODE_DELIBERATION_CD + ">" + pdsdown.getDeliberation_cd()  + "</"  + XML_NODE_DELIBERATION_CD + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">"+pdsdown.getMedia_id()+"</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_RECORD_TYPE_CD + ">"+pdsdown.getRecord_type_cd()+"</"  + XML_NODE_RECORD_TYPE_CD + "> \n");
		_xml.append("<" + XML_NODE_FRM_PER_SEC + ">"+pdsdown.getFrm_per_sec()+"</"  + XML_NODE_FRM_PER_SEC + "> \n");
		_xml.append("<" + XML_NODE_CT_TYP + ">"+pdsdown.getCt_typ()+"</"  + XML_NODE_CT_TYP + "> \n");
		_xml.append("<" + XML_NODE_CT_LENG + ">" + pdsdown.getCt_leng() + "</"  + XML_NODE_CT_LENG + "> \n");
		_xml.append("<" + XML_NODE_CT_CLA + ">"+pdsdown.getCt_cla()+"</"  + XML_NODE_CT_CLA + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + pdsdown.getBrd_leng()  + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_BRD_END_HMS + ">" + pdsdown.getBrd_end_hms()  + "</"  + XML_NODE_BRD_END_HMS + "> \n");
		_xml.append("<" + XML_NODE_BRD_BGN_HMS + ">"+pdsdown.getBrd_bgn_hms()+"</"  + XML_NODE_BRD_BGN_HMS + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">"+pdsdown.getBrd_dd()+"</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_PGM_NM + ">"+CommonUtl.transXmlText(pdsdown.getPgm_nm())+"</"  + XML_NODE_PGM_NM + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(pdsdown.getTitle())+"</"  + XML_NODE_TITLE + "> \n");
		_xml.append( "</"+XML_NODE_MEDIA_META+">\n");			
		_xml.append("<" + XML_NODE_PREVIEWNOTE + ">\n") ;
		_xml.append("<file storagename =\"\" filepath=\"\" filesize = \"\" isfolderbool=\"0\">" + "</file>\n");
		_xml.append("</" + XML_NODE_PREVIEWNOTE + ">\n") ;	
		_xml.append("</" + XML_NODE_HEAD + ">\n") ;	
		_xml.append("</sbspds>\n");

		return _xml.toString();
	}



	public String getSubXMLForStorage() {
		PdsDownDO pdsdown = (PdsDownDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<sbspds xmlns=\"urn:sbspds2010\">\n");
		_xml.append(" <generator>      <generator_name>SBSDAS</generator_name> \n" +
				" <generator_version>1.0</generator_version>   </generator>");
		_xml.append( "<stream_total_size>"+pdsdown.getFilesize()+"</stream_total_size> \n");

		_xml.append( "<"+XML_NODE_STREAM_PATH+">");
		//2011.05.17 박보아대리 원복요청에 의해 원복
		//	_xml = _xml +  "<"+XML_NODE_STREAM+" isfolder=\"false\" src = \""+pdsdown.getFilename()+"\">"   +pdsdown.getFl_path()+"/"+pdsdown.getRename() + "</"  + XML_NODE_STREAM + "> ";
		_xml.append( "<"+XML_NODE_STREAM+" isfolder=\"false\" src = \""+pdsdown.getFilename()+".mxf"+"\">"   +pdsdown.getRename() +".mxf"+ "</"  + XML_NODE_STREAM + "> \n");


		//	_xml = _xml +  "<"+XML_NODE_STREAM+" isfolder=\"false\" src = \"\">" + "</"  + XML_NODE_STREAM + "> ";;
		_xml.append("</"+XML_NODE_STREAM_PATH+">");	
		_xml.append("<"+XML_NODE_HEAD+">");		  
		_xml.append("<" + XML_NODE_MEDIA_TYPE + ">video</"  + XML_NODE_MEDIA_TYPE + "> \n");
		_xml.append("<"+XML_NODE_SUB_HEAD+">");			
		_xml.append("<" + XML_NODE_STORAGE_NAME + ">" + pdsdown.getStoragename() + "</"  + XML_NODE_STORAGE_NAME + "> \n");
		_xml.append("<" + XML_NODE_CATEGORY + ">" + pdsdown.getCategory() + "</"  + XML_NODE_CATEGORY + "> \n");
		_xml.append("<" + XML_NODE_FILEPATH + ">" + pdsdown.getFl_path() + "</"  + XML_NODE_FILEPATH + "> \n");
		_xml.append("<" + XML_NODE_FILENAME + ">" +pdsdown.getRename()+".mxf"+ "</"  + XML_NODE_FILENAME + "> \n");
		_xml.append("</"+XML_NODE_SUB_HEAD+">\n");	
		_xml.append("<"+XML_NODE_MEDIA_META+">\n");			
		_xml.append("<" + XML_NODE_HDMODE + ">" + pdsdown.getHdmode() + "</"  + XML_NODE_HDMODE + "> \n");
		_xml.append("<" + XML_NODE_PDS_PROGRAM_ID + ">" + pdsdown.getPds_program_id()  + "</"  + XML_NODE_PDS_PROGRAM_ID + "> \n");
		_xml.append("<" + XML_NODE_SOM + ">"+pdsdown.getSom()+"</"  + XML_NODE_SOM + "> \n");
		_xml.append("<" + XML_NODE_EOM + ">"+pdsdown.getEom()+"</"  + XML_NODE_EOM + "> \n");
		_xml.append("<" + XML_NODE_VD_HRESOL + ">"+pdsdown.getVd_hresol()+"</"  + XML_NODE_VD_HRESOL + "> \n");
		_xml.append("<" + XML_NODE_VD_VRESOL + ">"+pdsdown.getVd_vresol()+"</"  + XML_NODE_VD_VRESOL + "> \n");
		_xml.append("<" + XML_NODE_FL_SZ + ">" + pdsdown.getFilesize() + "</"  + XML_NODE_FL_SZ + "> \n");
		_xml.append("<" + XML_NODE_BIT_RT + ">"+pdsdown.getBit_rt()+"</"  + XML_NODE_BIT_RT + "> \n");
		_xml.append("<" + XML_NODE_AUD_SAMP_FRQ + ">" + pdsdown.getAud_samp_frq()  + "</"  + XML_NODE_AUD_SAMP_FRQ + "> \n");
		_xml.append("<" + XML_NODE_AUD_BDWT + ">" + pdsdown.getAud_bdwt()  + "</"  + XML_NODE_AUD_BDWT + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">"+pdsdown.getEpis_no()+"</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("<" + XML_NODE_PRODUCER_NM + ">"+CommonUtl.transXmlText(pdsdown.getProducer_nm())+"</"  + XML_NODE_PRODUCER_NM + "> \n");
		_xml.append("<" + XML_NODE_CMR_PLACE + ">"+CommonUtl.transXmlText(pdsdown.getCmr_place())+"</"  + XML_NODE_CMR_PLACE + "> \n");
		_xml.append("<" + XML_NODE_FM_DT + ">"+pdsdown.getFm_dt()+"</"  + XML_NODE_FM_DT + "> \n");
		_xml.append("<" + XML_NODE_CPRT_NM + ">" + CommonUtl.transXmlText(pdsdown.getCprt_nm()) + "</"  + XML_NODE_CPRT_NM + "> \n");
		_xml.append("<" + XML_NODE_CPRT_CD + ">"+pdsdown.getCprt_cd()+"</"  + XML_NODE_CPRT_CD + "> \n");
		_xml.append("<" + XML_NODE_RIST_CLF_CD + ">" + pdsdown.getRist_clf_cd()  + "</"  + XML_NODE_RIST_CLF_CD + "> \n");
		_xml.append("<" + XML_NODE_DELIBERATION_CD + ">" + pdsdown.getDeliberation_cd()  + "</"  + XML_NODE_DELIBERATION_CD + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">"+pdsdown.getMedia_id()+"</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_RECORD_TYPE_CD + ">"+pdsdown.getRecord_type_cd()+"</"  + XML_NODE_RECORD_TYPE_CD + "> \n");
		_xml.append("<" + XML_NODE_FRM_PER_SEC + ">"+pdsdown.getFrm_per_sec()+"</"  + XML_NODE_FRM_PER_SEC + "> \n");
		_xml.append("<" + XML_NODE_CT_TYP + ">"+pdsdown.getCt_typ()+"</"  + XML_NODE_CT_TYP + "> \n");
		_xml.append("<" + XML_NODE_CT_LENG + ">" + pdsdown.getCt_leng() + "</"  + XML_NODE_CT_LENG + "> \n");
		_xml.append("<" + XML_NODE_CT_CLA + ">"+pdsdown.getCt_cla()+"</"  + XML_NODE_CT_CLA + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + pdsdown.getBrd_leng()  + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_BRD_END_HMS + ">" + pdsdown.getBrd_end_hms()  + "</"  + XML_NODE_BRD_END_HMS + "> \n");
		_xml.append("<" + XML_NODE_BRD_BGN_HMS + ">"+pdsdown.getBrd_bgn_hms()+"</"  + XML_NODE_BRD_BGN_HMS + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">"+pdsdown.getBrd_dd()+"</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_PGM_NM + ">"+CommonUtl.transXmlText(pdsdown.getPgm_nm())+"</"  + XML_NODE_PGM_NM + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">"+CommonUtl.transXmlText(pdsdown.getTitle())+"</"  + XML_NODE_TITLE + "> \n");
		_xml.append( "</"+XML_NODE_MEDIA_META+">\n");			
		_xml.append("<" + XML_NODE_PREVIEWNOTE + ">\n") ;
		_xml.append("<file storagename =\"\" filepath=\"\" filesize = \"\" isfolderbool=\"0\">" + "</file>\n");
		_xml.append("</" + XML_NODE_PREVIEWNOTE + ">\n") ;	
		_xml.append("</" + XML_NODE_HEAD + ">\n") ;	
		_xml.append("</sbspds>\n");

		return _xml.toString();
	}












	public String getSubXML2() {
		PdsDownDO pdsdown = (PdsDownDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<SBSDAS xmlns=\"urn:sbspdsSBSDAS2010\">\n");
		_xml.append(" <generator>      <generator_name>SBSDAS</generator_name> \n" +
				" <generator_version>1.0</generator_version>   </generator>\n");
		_xml.append("<stream_total_size>"+pdsdown.getFilesize()+"</stream_total_size>\n");
		_xml.append( "<"+XML_NODE_STREAMS_PATH+">\n");
		_xml.append( "<"+XML_NODE_STREAM+" isfolder=\"false\">"  +pdsdown.getCart_no()+"_"+pdsdown.getFilename().trim() + "</"  + XML_NODE_STREAM + "> \n");
		//	_xml = _xml +  "<"+XML_NODE_STREAM+" isfolder=\"false\" src = \"\">" + "</"  + XML_NODE_STREAM + "> ";;
		_xml.append( "</"+XML_NODE_STREAMS_PATH+">\n");	
		_xml.append( "<"+XML_NODE_HEAD+">\n");		  
		_xml.append("<" + XML_NODE_MEDIA_TYPE + ">"+pdsdown.getStoragename().trim()+"</"  + XML_NODE_MEDIA_TYPE + "> \n");
		_xml.append( "<"+XML_NODE_MEDIA+">\n");			
		_xml.append("<" + XML_NODE_STORAGE_NAME + ">" + pdsdown.getStoragename().trim() + "</"  + XML_NODE_STORAGE_NAME + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_TYPE_CODE + ">" + pdsdown.getStoragename().trim() + "</"  + XML_NODE_MEDIA_TYPE_CODE + "> \n");
		_xml.append("<" + XML_NODE_MEDIAID + ">" + pdsdown.getMedia_id().trim() + "</"  + XML_NODE_MEDIAID + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" +CommonUtl.transXmlText(pdsdown.getTitle())+ "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_SUB_TTL + ">" + CommonUtl.transXmlText(pdsdown.getSub_ttl()) + "</"  + XML_NODE_SUB_TTL + "> \n");
		_xml.append("<" + XML_NODE_PHYSICALTREE + ">"+pdsdown.getPhysical_tree().trim()+"</"  + XML_NODE_PHYSICALTREE + "> ");
		_xml.append("<" + XML_NODE_LOGICALTREE + ">0</"  + XML_NODE_LOGICALTREE + "> ");
		_xml.append("<" + XML_NODE_WORKERNAME + ">" +CommonUtl.transXmlText(pdsdown.getUser_nm())+ "</"  + XML_NODE_WORKERNAME + "> \n");
		_xml.append( "</"+XML_NODE_MEDIA+">\n");	
		_xml.append( "<"+XML_NODE_METADATA+">\n");
		_xml.append("<" + XML_NODE_PROGRAM_NAME + ">" + CommonUtl.transXmlText(pdsdown.getPgm_nm()) + "</"  + XML_NODE_PROGRAM_NAME + "> \n");
		//String brd_dd = pdsdown.getBrd_dd().replaceAll("-", "");
		_xml.append("<" + XML_NODE_ONAIRDATE + ">" + pdsdown.getBrd_dd().replaceAll("-", "")  + "</"  + XML_NODE_ONAIRDATE + "> \n");
		_xml.append("<" + XML_NODE_ONAIRTIME + ">"+pdsdown.getBrd_dt()+"</"  + XML_NODE_ONAIRTIME + "> \n");
		_xml.append("<" + XML_NODE_CM_NOTE + "></"  + XML_NODE_CM_NOTE + "> \n");
		_xml.append( "</"+XML_NODE_METADATA+">\n");			
		_xml.append("</" + XML_NODE_HEAD + ">\n") ;	
		_xml.append("</SBSDAS>\n");

		return _xml.toString();
	}


	public String getSubXML2ForStorage() {
		PdsDownDO pdsdown = (PdsDownDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<SBSDAS xmlns=\"urn:sbspdsSBSDAS2010\">\n");
		_xml.append(" <generator>      <generator_name>SBSDAS</generator_name> \n" +
				" <generator_version>1.0</generator_version>   </generator>\n");
		_xml.append( "<stream_total_size>"+pdsdown.getFilesize()+"</stream_total_size>\n");

		_xml.append("<"+XML_NODE_STREAMS_PATH+">\n");
		_xml.append("<"+XML_NODE_STREAM+" isfolder=\"false\">"  +pdsdown.getFilename().trim()+".mxf" + "</"  + XML_NODE_STREAM + "> \n");
		//	_xml = _xml +  "<"+XML_NODE_STREAM+" isfolder=\"false\" src = \"\">" + "</"  + XML_NODE_STREAM + "> ";;
		_xml.append( "</"+XML_NODE_STREAMS_PATH+">\n");	
		_xml.append( "<"+XML_NODE_HEAD+">\n");		  
		_xml.append("<" + XML_NODE_MEDIA_TYPE + ">"+pdsdown.getStoragename().trim()+"</"  + XML_NODE_MEDIA_TYPE + "> \n");
		_xml.append( "<"+XML_NODE_MEDIA+">\n");			
		_xml.append("<" + XML_NODE_STORAGE_NAME + ">" + pdsdown.getStoragename().trim() + "</"  + XML_NODE_STORAGE_NAME + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_TYPE_CODE + ">" + pdsdown.getStoragename().trim() + "</"  + XML_NODE_MEDIA_TYPE_CODE + "> \n");
		_xml.append("<" + XML_NODE_MEDIAID + ">" + pdsdown.getMedia_id().trim() + "</"  + XML_NODE_MEDIAID + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" +CommonUtl.transXmlText(pdsdown.getTitle())+ "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_SUB_TTL + ">" + CommonUtl.transXmlText(pdsdown.getSub_ttl()) + "</"  + XML_NODE_SUB_TTL + "> \n");
		_xml.append("<" + XML_NODE_PHYSICALTREE + ">"+pdsdown.getPhysical_tree().trim()+"</"  + XML_NODE_PHYSICALTREE + "> ");
		_xml.append("<" + XML_NODE_LOGICALTREE + ">0</"  + XML_NODE_LOGICALTREE + "> ");
		_xml.append("<" + XML_NODE_WORKERNAME + ">" +CommonUtl.transXmlText(pdsdown.getUser_nm())+ "</"  + XML_NODE_WORKERNAME + "> \n");
		_xml.append( "</"+XML_NODE_MEDIA+">\n");	
		_xml.append( "<"+XML_NODE_METADATA+">\n");			
		_xml.append("<" + XML_NODE_PROGRAM_NAME + ">" + CommonUtl.transXmlText(pdsdown.getPgm_nm()) + "</"  + XML_NODE_PROGRAM_NAME + "> \n");
		//String brd_dd = pdsdown.getBrd_dd().replaceAll("-", "");
		_xml.append("<" + XML_NODE_ONAIRDATE + ">" + pdsdown.getBrd_dd().replaceAll("-", "")  + "</"  + XML_NODE_ONAIRDATE + "> \n");
		_xml.append("<" + XML_NODE_ONAIRTIME + ">"+pdsdown.getBrd_dt()+"</"  + XML_NODE_ONAIRTIME + "> \n");
		_xml.append("<" + XML_NODE_CM_NOTE + "></"  + XML_NODE_CM_NOTE + "> \n");
		_xml.append( "</"+XML_NODE_METADATA+">\n");			
		_xml.append("</" + XML_NODE_HEAD + ">\n") ;	
		_xml.append("</SBSDAS>\n");

		return _xml.toString();
	}


	public long fl_sz(PdsDownDO pdsdown){
		//	DownCartDO pdsdown = (DownCartDO)getDO();

		File oFile = new File(pdsdown.getFl_path()+"/"+pdsdown.getFilename());
		//System.out.println("/mp2/restore/S911911/8037/"+pdsdown.getFilename());
		if (oFile.exists()) {
			long L = oFile.length();
			//System.out.println(L + " bytes : " + oFile.getAbsoluteFile());
			return L;
		}
		else {
			//System.err.println("파일이 없음...");
		}
		return 0;
	}


}
