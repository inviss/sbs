package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.AttachItem;
import com.app.das.business.transfer.PdsArchiveDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CommonUtl;
import com.app.das.util.XmlUtil;

/**
 *  pds 아카이브 정보 관련 XML파서
 * @author asura207
 *
 */
public class PdsArchiveDOXML extends DOXml{

	private static Logger logger = Logger.getLogger(PdsArchiveDOXML.class);

	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	XmlUtil xmlutil = new XmlUtil();
	/**
	 * xml 해더
	 */
	static public String XML_NODE_HEAD5 = "sbsdas";	
	/**
	 * 파일 크기
	 */
	static public String XML_NODE_HEAD4 = "stream_total_size";	
	/**
	 * xml 해더
	 */
	static public String XML_NODE_HEAD3 = "generator";	
	/**
	 * xml 해더
	 */
	static public String XML_NODE_HEAD2 = "generator_name";	
	/**
	 * xml 해더
	 */
	static public String XML_NODE_HEAD6 = "das";	
	/**
	 * xml 해더
	 */
	static public String XML_NODE_HEAD = "pdsarhchive";	

	/**
	 * T/C 시작점
	 */
	static public String XML_NODE_SOM = "som"; 
	/**
	 * T/C 종료점
	 */
	static public String XML_NODE_EOM = "eom";
	/**
	 * 비디오수평해상도
	 */
	static public String XML_NODE_VD_HRESOL = "vd_hresol"; 
	/**
	 * 비디오수직해상도
	 */
	static public String XML_NODE_VD_VRESOL = "vd_vresol"; 
	/**
	 * 파일사이즈
	 */
	static public String XML_NODE_FL_SZ = "fl_sz";
	/**
	 * 비트전송율
	 */
	static public String XML_NODE_BIT_RT = "bit_rt"; 
	/**
	 * 오디오샘플링
	 */
	static public String XML_NODE_AUD_SAMP_FRQ = "aud_samp_frq"; 
	/**
	 * 오디오대역폭
	 */
	static public String XML_NODE_AUD_BDWT = "aud_bdwt";
	/**
	 * 회차번호
	 */
	static public String XML_NODE_EPIS_NO = "epis_no"; 
	/**
	 * 제작PD
	 */
	static public String XML_NODE_PRODUCER_NM = "producer_nm"; 
	/**
	 * 촬영지
	 */
	static public String XML_NODE_CMR_PLACE = "cmr_place";
	/**
	 * 촬영일시
	 */
	static public String XML_NODE_FM_DT = "fm_dt"; 
	/**
	 * 저작권자
	 */
	static public String XML_NODE_CPRT_NM = "cprt_nm"; 
	/**
	 * 저작권수준
	 */
	static public String XML_NODE_CPTR_CD = "cprt_cd";
	/**
	 * 사용등급
	 */
	static public String XML_NODE_RIST_CLF_CD = "rist_clf_cd"; 
	/**
	 * 요청자
	 */
	static public String XML_NODE_REQ_NM = "req_nm"; 
	/**
	 * 요청자ID
	 */
	static public String XML_NODE_REQ_ID = "req_id";
	/**
	 * 요청일시
	 */
	static public String XML_NODE_REQ_DT= "req_dt"; 
	/**
	 * 미디어ID
	 */
	static public String XML_NODE_MEDIA_ID = "media_id"; 
	/**
	 * 녹음방식코드
	 */
	static public String XML_NODE_RECORD_TYPE_CD = "record_type_cd";
	/**
	 * 초당프레임
	 */
	static public String XML_NODE_FRM_PER_SEC = "frm_per_sec"; 

	/**
	 * 원파일명
	 */
	static public String XML_NODE_ORG_FILE_NM = "org_file_nm"; 
	/**
	 * 아카이브요청스토리지의 Path
	 */
	static public String XML_NODE_STORAGE_PATH = "storage_path";
	/**
	 * 드롭프레임여부
	 */
	static public String XML_NODE_DROP_YN = "drop_yn"; 
	/**
	 * 콘텐츠유형
	 */
	static public String XML_NODE_CT_TYP = "ct_typ"; 
	/**
	 * 콘텐츠길이
	 */
	static public String XML_NODE_CT_LENG = "ct_leng";
	/**
	 * 종횡비코드
	 */
	static public String XML_NODE_ASP_RTO_CD = "asp_rto_cd"; 
	/**
	 * 화질코드
	 */
	static public String XML_NODE_VD_QLTY = "vd_qlty"; 
	/**
	 * 데이타구분코드
	 */
	static public String XML_NODE_DATA_STAT = "data_stat";
	/**
	 * 콘텐츠구분
	 */
	static public String XML_NODE_CT_CLA = "ct_cla"; 
	/**
	 * 클립명
	 */
	static public String XML_NODE_CLIP_NM = "clip_nm"; 
	/**
	 * 방송길이
	 */
	static public String XML_NODE_BRD_LENG = "brd_leng";
	/**
	 * 방송종료시간(시분초)
	 */
	static public String XML_NODE_BRD_END_HMS = "brd_end_hms"; 
	/**
	 * 방송시작시간(시분초)
	 */
	static public String XML_NODE_BRD_BGN_HMS = "brd_bgn_hms"; 
	/**
	 * 방송일자
	 */
	static public String XML_NODE_BRD_DD = "brd_dd";
	/**
	 * 타이틀
	 */
	static public String XML_NODE_TITLE = "title"; 

	/**
	 * 프로그램명
	 */
	static public String XML_NODE_PGM_NM = "pgm_nm"; 
	/**
	 * pds cms 프로그램 id
	 */
	static public String XML_NODE_PDS_PGM_ID = "pds_program_id"; 
	/**
	 * 파일 경로
	 */
	static public String XML_NODE_FL_PATH = "fl_path"; 
	/**
	 * 마스터id
	 */
	static public String XML_NODE_MASTER_ID = "master_id"; 
	/**코너id
	 */
	static public String XML_NODE_CN_ID = "cn_id";
	/**
	 * 컨텐츠 인스턴스id
	 */
	static public String XML_NODE_CTI_ID = "cti_id"; 
	/**
	 * 컨텐츠id
	 */
	static public String XML_NODE_CT_ID = "ct_id"; 
	/**
	 * 상태
	 */
	static public String XML_NODE_STATUS= "status"; 
	/**
	 * xml 해더
	 */
	static public String XML_NODE_PREVIEW_INFO= "previewinfo"; 
	/**
	 * 프리뷰파일명
	 */
	static public String XML_NODE_PREVIEW_FILE_NM= "previewnotefile"; 
	/**
	 * 등급코드
	 */
	static public String XML_NODE_DELIBERATION_CD= "deliberation_cd"; 
	/**
	 * 코멘츠
	 */
	static public String XML_NODE_ARCHIVECOMENTS= "archivecomments"; 
	/**
	 * 촬영감독명
	 */
	static public String XML_NODE_CMR_DRT_NM= "cmr_drt_nm"; 
	/**
	 * 사용제한 설명
	 */
	static public String XML_NODE_ANNOT_CLF_CONT= "annot_clf_cont"; 
	/**
	 * 프리뷰노트
	 */
	static public String XML_NODE_PREVIEWNOTE= "previewnote"; 
	/**
	 * 첨부파일
	 */
	static public String XML_NODE_ATTATCHEDFILE= "attatchedfile"; 
	/**
	 * xml 해더
	 */
	static public String XML_NODE_PREVIEWNOTEDB= "previewnotedb"; 
	/**
	 * 프리뷰 경로
	 */
	static public String XML_NODE_PREVIEWPATH= "preview_path"; 
	/**
	 * xml 해더
	 */
	static public String XML_NODE_SEGMENT= "segment"; 
	/**
	 * xml 해더
	 */
	static public String XML_NODE_MARKIN= "markin"; 

	static public String XML_ATTACH_FILE= "attach_tbl"; 

	static public String XML_FILE_SIZE= "filesize";

	static public String XML_ORG_FILENAME= "org_file_name";

	static public String XML_FILE_TYPE= "attc_file_type_cd";

	static public List attatches = new ArrayList();

	public Object setDO(String _xml) {
		if(logger.isDebugEnabled())
			logger.debug(_xml);
		
		setDO(new PdsArchiveDO());

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;

			/*	
		NodeList node2= _node.getChildNodes();
			Node _node2 = node2.item(i);
			String _nodeName2 = _node2.getNodeName() ;
			NodeList node3= _node2.getChildNodes();
			Node _node3 = node3.item(i);
			String _nodeName3 = _node3.getNodeName() ;*/


			if(_nodeName.equals(XML_NODE_HEAD6)) {
				/*NodeList node2= _node.getChildNodes();
				Node _node2 = node2.item(i);
				String _nodeName2 = _node2.getNodeName() ;
				 */
				setData((Element)_node);

			}else if(_nodeName.equals(XML_NODE_HEAD)) {
				/*NodeList node2= _node.getChildNodes();
				Node _node2 = node2.item(i);
				String _nodeName2 = _node2.getNodeName() ;
				 */
				setData2((Element)_node);

			}


		}
		return getDO();
	}





	public Object setData(Element pElement) {
		PdsArchiveDO infoDO = (PdsArchiveDO)getDO();
		String file_nms="";
		String preview_subj="";
		String preview_cont="";
		String total ="";
		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			//			String _nodeName = _node.getNodeName() ;
			//			String _nodeValue = getNodeValue(_node);
			NodeList _nodeList2 =_node.getChildNodes();

			for(int k = 0; k < _nodeList2.getLength(); k++) {
				Node _node2 = _nodeList2.item(k);
				String _nodeName2 = _node2.getNodeName() ;
				String _nodeValue2 = CommonUtl.transXMLText(StringUtils.defaultIfEmpty(getNodeValue(_node2), ""));

				if(_nodeName2.equals(XML_NODE_SOM)) {
					String som = _nodeValue2.replace(';', ':');

					infoDO.setSom(som.trim());
				} else if(_nodeName2.equals(XML_NODE_EOM)) {
					String eom = _nodeValue2.replace(';', ':');
					infoDO.setEom(eom.trim());
				} else if(_nodeName2.equals(XML_NODE_VD_HRESOL)) {
					infoDO.setVd_hresol(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue2, "0").replaceAll("[^0-9]", "")));
				} else if(_nodeName2.equals(XML_NODE_VD_VRESOL)) {
					infoDO.setVd_vresol(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue2, "0").replaceAll("[^0-9]", "")));
				} else if(_nodeName2.equals(XML_NODE_FL_SZ)) {
					infoDO.setFl_sz(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue2, "0").replaceAll("[^0-9]", "")));
				} else if(_nodeName2.equals(XML_NODE_BIT_RT)) {
					infoDO.setBit_rt(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_AUD_SAMP_FRQ)) {
					infoDO.setAud_samp_frq(Float.parseFloat(StringUtils.defaultIfEmpty(_nodeValue2, "0")));
				} else if(_nodeName2.equals(XML_NODE_AUD_BDWT)) {
					infoDO.setAudio_bdwt(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue2, "0")));
				} else if(_nodeName2.equals(XML_NODE_EPIS_NO)) {
					infoDO.setEpis_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue2, "0")));
				}	else if(_nodeName2.equals(XML_NODE_PRODUCER_NM)) {
					infoDO.setProducer_nm(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CMR_PLACE)) {
					infoDO.setCmr_place(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_FM_DT)) {
					String values = _nodeValue2.replace("-", "");
					infoDO.setFm_dt(values);
				} else if(_nodeName2.equals(XML_NODE_CPRT_NM)) {
					infoDO.setCprt_nm(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CPTR_CD)) {
					infoDO.setCprt_cd(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_RIST_CLF_CD)) {
					infoDO.setRist_clf_cd(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_REQ_NM)) {
					infoDO.setReq_nm(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_REQ_ID)) {
					infoDO.setReq_id(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_REQ_DT)) {
					infoDO.setReq_dt(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_MEDIA_ID)) {
					infoDO.setMedia_id(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_RECORD_TYPE_CD)) {
					String recode_Cd = xmlutil.changRecordCode(_nodeValue2);

					infoDO.setRecord_type_cd(recode_Cd);
				} else if(_nodeName2.equals(XML_NODE_FRM_PER_SEC)) {
					infoDO.setFrm_per_sec(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_ORG_FILE_NM)) {
					infoDO.setOrg_file_nm(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_STORAGE_PATH)) {
					String value = _nodeValue2.replaceAll(dasHandler.getProperty("ARCREQ"), dasHandler.getProperty("WINARCREQ"));

					infoDO.setStorage_path(value.replace('\\', '/'));
				} else if(_nodeName2.equals(XML_NODE_DROP_YN)) {
					infoDO.setDrop_yn(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CT_TYP)) {
					infoDO.setCt_typ(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CT_LENG)) {
					String ct_leng = _nodeValue2.replace(';', ':');
					infoDO.setCt_leng(ct_leng);
				} else if(_nodeName2.equals(XML_NODE_ASP_RTO_CD)) {
					infoDO.setAsp_rto_cd(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_VD_QLTY)) {
					infoDO.setVd_qulty(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_DATA_STAT)) {
					infoDO.setData_stat(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CT_CLA)) {
					infoDO.setCt_cla(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CLIP_NM)) {
					infoDO.setClip_nm(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_BRD_LENG)) {
					infoDO.setBrd_leng(_nodeValue2);

					// ''일경우 0을 넣어준다
					if(_nodeValue2.trim().equals("")){
						infoDO.setBrd_leng("00:00:00");
					}else{
						infoDO.setBrd_leng(_nodeValue2);
					}
				} else if(_nodeName2.equals(XML_NODE_BRD_END_HMS)) {
					//				logger.debug("XML_NODE_BRD_END_HMS start");
					////				String value = _nodeValue2.substring(0,8);
					//				logger.debug("XML_NODE_BRD_END_HMS end");
					infoDO.setBrd_end_hms(_nodeValue2);
					// 00:00:00:00 값으로 들어오므로 8자리로 파싱
					//	String value = _nodeValue2.substring(0,8);
					infoDO.setBrd_end_hms(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_BRD_BGN_HMS)) {
					// 00:00:00:00 값으로 들어오므로 8자리로 파싱
					//	String value = _nodeValue2.substring(0,8);
					infoDO.setBrd_bgn_hms(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_BRD_DD)) {
					infoDO.setBrd_dd(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_TITLE)) {

					infoDO.setTitle(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_PGM_NM)) {

					infoDO.setPgm_nm(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_PDS_PGM_ID)) {
					infoDO.setPds_cms_id(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_MASTER_ID)) {
					infoDO.setBrd_leng(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CN_ID)) {
					infoDO.setBrd_end_hms(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CTI_ID)) {
					infoDO.setBrd_bgn_hms(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CT_ID)) {
					infoDO.setBrd_dd(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_STATUS)) {
					infoDO.setStatus(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_FL_PATH)) {

					String value = _nodeValue2.replaceAll(dasHandler.getProperty("ARCREQ"), dasHandler.getProperty("WINARCREQ"));

					infoDO.setFl_path(value);
				} else if(_nodeName2.equals(XML_NODE_DELIBERATION_CD)) {
					String viewgr=xmlutil.changViewGrade(_nodeValue2);
					infoDO.setView_gr_cd(viewgr);
				} else if(_nodeName2.equals(XML_NODE_PREVIEW_INFO)) {

					NodeList nList = _node2.getChildNodes();

					int leng = nList.getLength();	
					for(int q=0; q<leng ; q++){
						Node nde = nList.item(q);
						String nName = nde.getNodeName() ;
						String nValue = getNodeValue(nde);

						//						NamedNodeMap CpdAttr = nde.getAttributes();
						//						String _nValue = getNodeValue(nde);
						NodeList nList_1 = nde.getChildNodes();
						int leng_1 = nList_1.getLength();	
						if(nName.equals(XML_NODE_PREVIEW_FILE_NM)) {
							for(int x=0; x<leng_1 ; x++){
								Node nde_1 = nList_1.item(x);
								String nName_1 = nde_1.getNodeName() ;
								String nValue_1 = getNodeValue(nde_1);

								//								NamedNodeMap CpdAttr_1 = nde_1.getAttributes();
								//								String _nValue_1 = getNodeValue(nde_1);

								if(nName_1.equals(XML_NODE_ATTATCHEDFILE)) {

									if(nName_1.equals(XML_NODE_ATTATCHEDFILE)) {

										file_nms += ","+nValue_1;

									}
								}
							}//프리뷰 파일 분석 파싱 끝
						}else if(nName.equals(XML_NODE_PREVIEWNOTEDB)) {//db 건에대한 파싱시작
							for(int a=0; a<leng_1 ; a++){
								Node nde_2 = nList_1.item(a);
								String nName_2 = nde_2.getNodeName() ;
								String nValue_2 = getNodeValue(nde_2);

								NamedNodeMap startAttr = nde_2.getAttributes();

								if(nName_2.equals(XML_NODE_SEGMENT)) {
									for(int w = 0; w<startAttr.getLength();w++){
										Node attr = startAttr.item(w);
										String nodeName = attr.getNodeName() ;
										String att= attr.getNodeValue();

										if(nodeName.equals(XML_NODE_MARKIN)) {

											preview_subj+= ","+att;

										}

										preview_cont+="," + nValue_2;

									}
								}

							}
						}else if(nName.equals(XML_NODE_PREVIEWPATH)) {

							String value = nValue.replaceAll(dasHandler.getProperty("MP4"), dasHandler.getProperty("WINMP4"));

							infoDO.setPreview_path(value);
						}


					}
					infoDO.setPreview_file_nm(file_nms);
					//System.out.println("preview_subj "  + preview_subj);
					//System.out.println("preview_cont "  + preview_cont);
					String[] preview_sub = preview_subj.split(",");
					String[] preview_con = preview_cont.split(",");

					for(int n =1; n <preview_sub.length;n++){
						total += preview_sub[n]+"    "+preview_con[n] + "\n";
					}
					// infoDO.setPreview_subj(preview_subj);							
					infoDO.setPreview_cont(total);

				} else if(_nodeName2.equals(XML_NODE_ARCHIVECOMENTS)) {
					infoDO.setArchivecoments(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_CMR_DRT_NM)) {
					infoDO.setCmr_drt_nm(_nodeValue2);
				} else if(_nodeName2.equals(XML_NODE_ANNOT_CLF_CONT)) {
					infoDO.setAnnot_clf_cont(_nodeValue2);
				} else if(_nodeName2.equals(XML_ATTACH_FILE)) {
					NodeList nList = _node2.getChildNodes();

					AttachItem item = null;
					
					int leng = nList.getLength();	
					for(int q=0; q<leng ; q++){
						Node nde = nList.item(q);
						String nName = nde.getNodeName() ;
						String nValue = getNodeValue(nde);
						
						if(StringUtils.isNotBlank(nValue)) {
							if(nName.equals(XML_ORG_FILENAME)) {
								infoDO.setOrgFileName(nValue);
							} else if(nName.equals(XML_FILE_TYPE)) {
								infoDO.setAttcFileTypeCd(nValue);
							} else if(nName.equals(XML_FILE_SIZE)) {
								infoDO.setFilesize(Long.valueOf(nValue));
							}
						}
					}
					if(StringUtils.isNotBlank(infoDO.getAttcFileTypeCd())) {
						item = new AttachItem();
						item.setOrgFileNm(infoDO.getOrgFileName());
						item.setAttcFileType(infoDO.getAttcFileTypeCd());
						item.setFlSz(infoDO.getFilesize());
						
						infoDO.addAttatches(item);
					}

				}
			}

		}

		return infoDO;
	}	    


	public Object setData2(Element pElement) {

		PdsArchiveDO infoDO = (PdsArchiveDO)getDO();
		String file_nms="";
		String preview_subj="";
		String preview_cont="";
		String total ="";

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(StringUtils.defaultIfEmpty(getNodeValue(_node), ""));

			NodeList _nodeList2 =_node.getChildNodes();

			for(int k = 0; k < _nodeList2.getLength(); k++) {
				Node _node2 = _nodeList2.item(k);
				//				String _nodeName2 = _node2.getNodeName() ;
				String _nodeValue2 = CommonUtl.transXMLText(StringUtils.defaultIfEmpty(getNodeValue(_node2), ""));

				if(_nodeName.equals(XML_NODE_SOM)) {
					String som = _nodeValue.replace(';', ':');

					infoDO.setSom(som.trim());
				}

				else if(_nodeName.equals(XML_NODE_EOM)) {
					String eom = _nodeValue.replace(';', ':');
					infoDO.setEom(eom.trim());
				}
				else if(_nodeName.equals(XML_NODE_VD_HRESOL)) {
					// ''일경우 0을 넣어준다
					if(_nodeValue.trim().equals("")){
						infoDO.setVd_hresol(0);
					}else{
						infoDO.setVd_hresol(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
					}

				}	else if(_nodeName.equals(XML_NODE_VD_VRESOL)) {

					// ''일경우 0을 넣어준다
					if(_nodeValue.trim().equals("")){
						infoDO.setVd_vresol(0);
					}else{
						infoDO.setVd_vresol(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
					}

				}
				else if(_nodeName.equals(XML_NODE_FL_SZ)) {

					// ''일경우 0을 넣어준다
					if(_nodeValue.trim().equals("")){
						infoDO.setFl_sz(0);
					}else{
						infoDO.setFl_sz(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
					}

				}	

				else if(_nodeName.equals(XML_NODE_BIT_RT)) {
					infoDO.setBit_rt(_nodeValue);
				}
				else if(_nodeName.equals(XML_NODE_AUD_SAMP_FRQ)) {


					// ''일경우 0을 넣어준다
					if(_nodeValue.trim().equals("")){
						infoDO.setAud_samp_frq(0);
					}else{
						infoDO.setAud_samp_frq(Float.parseFloat(StringUtils.defaultIfEmpty(_nodeValue, "0")));
					}

				}

				else if(_nodeName.equals(XML_NODE_AUD_BDWT)) {

					// ''일경우 0을 넣어준다
					if(_nodeValue2.trim().equals("")){
						infoDO.setAudio_bdwt(0);
					}else{
						infoDO.setAudio_bdwt(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
					}

				}

				else if(_nodeName.equals(XML_NODE_EPIS_NO)) {

					// ''일경우 0을 넣어준다
					if(_nodeName.trim().equals("")){
						infoDO.setEpis_no(0);
					}else{
						infoDO.setEpis_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
					}

				}	else if(_nodeName.equals(XML_NODE_PRODUCER_NM)) {
					infoDO.setProducer_nm(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_CMR_PLACE)) {
					infoDO.setCmr_place(_nodeValue);
				}
				else if(_nodeName.equals(XML_NODE_FM_DT)) {
					String values = _nodeValue.replace("-", "");
					infoDO.setFm_dt(values);
				}

				else if(_nodeName.equals(XML_NODE_CPRT_NM)) {
					infoDO.setCprt_nm(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_CPTR_CD)) {
					infoDO.setCprt_cd(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_RIST_CLF_CD)) {
					infoDO.setRist_clf_cd(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_REQ_NM)) {
					infoDO.setReq_nm(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_REQ_ID)) {
					infoDO.setReq_id(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_REQ_DT)) {
					infoDO.setReq_dt(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_MEDIA_ID)) {
					infoDO.setMedia_id(_nodeValue);

				}	

				else if(_nodeName.equals(XML_NODE_RECORD_TYPE_CD)) {
					String recode_Cd = xmlutil.changRecordCode(_nodeValue);

					infoDO.setRecord_type_cd(recode_Cd);
				}

				else if(_nodeName.equals(XML_NODE_FRM_PER_SEC)) {
					infoDO.setFrm_per_sec(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_ORG_FILE_NM)) {
					infoDO.setOrg_file_nm(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_STORAGE_PATH)) {
					String value = _nodeValue.replaceAll(dasHandler.getProperty("ARCREQ"), dasHandler.getProperty("WINARCREQ"));

					infoDO.setStorage_path(value.replace('\\', '/'));
				}	

				else if(_nodeName.equals(XML_NODE_DROP_YN)) {
					infoDO.setDrop_yn(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_CT_TYP)) {
					infoDO.setCt_typ(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_CT_LENG)) {
					String ct_leng = _nodeValue.replace(';', ':');
					infoDO.setCt_leng(ct_leng);
				}

				else if(_nodeName.equals(XML_NODE_ASP_RTO_CD)) {
					infoDO.setAsp_rto_cd(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_VD_QLTY)) {
					infoDO.setVd_qulty(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_DATA_STAT)) {
					infoDO.setData_stat(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_CT_CLA)) {
					infoDO.setCt_cla(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_CLIP_NM)) {
					infoDO.setClip_nm(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_BRD_LENG)) {
					infoDO.setBrd_leng(_nodeValue);

					// ''일경우 0을 넣어준다
					if(_nodeName.trim().equals("")){
						infoDO.setBrd_leng("00:00:00");
					}else{
						infoDO.setBrd_leng(_nodeValue);
					}
				}

				else if(_nodeName.equals(XML_NODE_BRD_END_HMS)) {
					//				logger.debug("XML_NODE_BRD_END_HMS start");
					////				String value = _nodeValue2.substring(0,8);
					//				logger.debug("XML_NODE_BRD_END_HMS end");
					infoDO.setBrd_end_hms(_nodeValue);
					// 00:00:00:00 값으로 들어오므로 8자리로 파싱
					//	String value = _nodeValue2.substring(0,8);
					infoDO.setBrd_end_hms(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_BRD_BGN_HMS)) {
					// 00:00:00:00 값으로 들어오므로 8자리로 파싱
					//	String value = _nodeValue2.substring(0,8);
					infoDO.setBrd_bgn_hms(_nodeValue);
				}
				else if(_nodeName.equals(XML_NODE_BRD_DD)) {
					infoDO.setBrd_dd(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_TITLE)) {
					infoDO.setTitle(_nodeName);
				}

				else if(_nodeName.equals(XML_NODE_PGM_NM)) {
					infoDO.setPgm_nm(_nodeName);
				}

				else if(_nodeName.equals(XML_NODE_PDS_PGM_ID)) {
					infoDO.setPds_cms_id(_nodeValue);
				}
				else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
					infoDO.setBrd_leng(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_CN_ID)) {
					infoDO.setBrd_end_hms(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_CTI_ID)) {
					infoDO.setBrd_bgn_hms(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_CT_ID)) {
					infoDO.setBrd_dd(_nodeValue);
				}	

				else if(_nodeName.equals(XML_NODE_STATUS)) {
					infoDO.setStatus(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_FL_PATH)) {

					String value = _nodeValue.replaceAll(dasHandler.getProperty("ARCREQ"), dasHandler.getProperty("WINARCREQ"));

					infoDO.setFl_path(value);
				}
				else if(_nodeName.equals(XML_NODE_DELIBERATION_CD)) {
					String viewgr=xmlutil.changViewGrade(_nodeValue);
					infoDO.setView_gr_cd(viewgr);
				}


				else if(_nodeName.equals(XML_NODE_PREVIEW_INFO)) {

					NodeList nList = _node2.getChildNodes();

					int leng = nList.getLength();	
					for(int q=0; q<leng ; q++){
						Node nde = nList.item(q);
						String nName = nde.getNodeName() ;
						String nValue = getNodeValue(nde);

						//NamedNodeMap CpdAttr = nde.getAttributes();
						//String _nValue = getNodeValue(nde);
						NodeList nList_1 = nde.getChildNodes();
						int leng_1 = nList_1.getLength();	
						if(nName.equals(XML_NODE_PREVIEW_FILE_NM)) {
							for(int x=0; x<leng_1 ; x++){
								Node nde_1 = nList_1.item(x);
								String nName_1 = nde_1.getNodeName() ;
								String nValue_1 = getNodeValue(nde_1);

								//								NamedNodeMap CpdAttr_1 = nde_1.getAttributes();
								//								String _nValue_1 = getNodeValue(nde_1);

								if(nName_1.equals(XML_NODE_ATTATCHEDFILE)) {

									if(nName_1.equals(XML_NODE_ATTATCHEDFILE)) {

										file_nms += ","+nValue_1;

									}
								}
							}//프리뷰 파일 분석 파싱 끝
						}else if(nName.equals(XML_NODE_PREVIEWNOTEDB)) {//db 건에대한 파싱시작
							for(int a=0; a<leng_1 ; a++){
								Node nde_2 = nList_1.item(a);
								String nName_2 = nde_2.getNodeName() ;
								String nValue_2 = getNodeValue(nde_2);

								NamedNodeMap startAttr = nde_2.getAttributes();

								if(nName_2.equals(XML_NODE_SEGMENT)) {
									for(int w = 0; w<startAttr.getLength();w++){
										Node attr = startAttr.item(w);
										String nodeName = attr.getNodeName() ;
										String att= attr.getNodeValue();

										if(nodeName.equals(XML_NODE_MARKIN)) {

											preview_subj+= ","+att;

										}

										preview_cont+="," + nValue_2;

									}
								}

							}
						}else if(nName.equals(XML_NODE_PREVIEWPATH)) {

							String value = nValue.replaceAll(dasHandler.getProperty("MP4"), dasHandler.getProperty("WINMP4"));

							infoDO.setPreview_path(value);
						}


					}
					infoDO.setPreview_file_nm(file_nms);
					//System.out.println("preview_subj "  + preview_subj);
					//System.out.println("preview_cont "  + preview_cont);
					String[] preview_sub = preview_subj.split(",");
					String[] preview_con = preview_cont.split(",");

					for(int n =1; n <preview_sub.length;n++){
						total += preview_sub[n]+"    "+preview_con[n] + "\n";
					}
					// infoDO.setPreview_subj(preview_subj);							
					infoDO.setPreview_cont(total);

				}


				else if(_nodeName.equals(XML_NODE_ARCHIVECOMENTS)) {
					infoDO.setArchivecoments(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_CMR_DRT_NM)) {
					infoDO.setCmr_drt_nm(_nodeValue);
				}

				else if(_nodeName.equals(XML_NODE_ANNOT_CLF_CONT)) {
					infoDO.setAnnot_clf_cont(_nodeValue);
				}
			}

		}

		return infoDO;
	}	    

	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}

	public String getSubXML() {
		//PdsArchiveDO infoDO = (PdsArchiveDO)getDO();

		String _xml = "<" + XML_NODE_HEAD + "> \n";
		_xml = _xml + "</" + XML_NODE_HEAD + ">";

		return _xml;
	}




}
