package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TcBeanDO;
import com.app.das.util.CommonUtl;
/**
 *  BACKENDTC 정보  정보 관련 XML파서
 * @author asura207
 *
 */
public class TcBeanDOXML extends DOXml{
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "tcProcessinginfo";
	/**
	 * TC 아이디
	 */
	private String XML_NODE_TC_ID = "TC_ID";
	/**
	 *  TC 명
	 */
	private String XML_NODE_TC_NM = "TC_NM";
	/**
	 * 채녈 구분
	 */
	private String XML_NODE_CH_SEQ = "CH_SEQ";
	/**
	 *  순번
	 */
	private String XML_NODE_SEQ = "SEQ";
	/**
	 * 상태코드(B:Busy , I:Idle , F:Fail )
	 */
	private String XML_NODE_WORK_STAT  = "WORK_STAT"; 
	/**
	 * 미디어 아이디
	 */
	private String XML_NODE_MEDIA_ID  = "MEDIA_ID"; 
	/**
	 *  LR: LR 생성, CT: 카탈로그 생성, LRCT:LR+카탈로그 생성
	 */
	private String XML_NODE_REQ_CD = "REQ_CD"; 
	/**
	 * 파일경로(MXF)
	 */
	private String XML_NODE_INPUT_HR = "INPUT_HR";
	/**
	 * 파일경로(WMV)
	 */
	private String XML_NODE_INPUT_LR  = "INPUT_LR";



	/**
	 * 파일경로(파일경로+CTI_ID)
	 */
	private String XML_NODE_OUTPUT_LR_PATH = "OUTPUT_LR_PATH";
	/**
	 * 파일경로(파일경로+CT_ID)
	 */
	private String XML_NODE_OUTPUT_CT_PATH  = "OUTPUT_CT_PATH";
	/**
	 * 파일네임(CTI_ID)
	 */
	private String XML_NODE_OUTPUT_LR_NM  = "OUTPUT_LR_NM";
	/**
	 * 파일네임(CT_ID)
	 */
	private String XML_NODE_OUTPUT_CT_NM  = "OUTPUT_CT_NM";
	/**
	 * 오디오비트
	 */
	private String XML_NODE_LR_AUDIOBIT  = "LR_AUDIOBIT";
	/**
	 * 파일사이즈
	 */
	private String XML_NODE_LR_FILE_SIZE = "LR_FILE_SIZE";
	/**
	 * LR 해상도
	 */
	private String XML_NODE_LR_RESOL   = "LR_RESOL";
	/**
	 *  LR 카탈로그
	 */
	private String XML_NODE_CT_RESOL   = "CT_RESOL";
	/**
	 *  CT 파일 사이즈
	 */
	private String XML_NODE_CT_FILE_SIZE    = "CT_FILE_SIZE";
	/**
	 *  결과
	 */
	private String XML_NODE_RESULT    = "RESULT";
	/**
	 *  저화질 영상명 
	 */
	private String XML_NODE_INPUT_LR_NM  = "INPUT_LR_NM";

	/**
	 *  고화질 영상명
	 */
	private String XML_NODE_INPUT_HR_NM  = "INPUT_HR_NM";
	/**
	 *  컨텐츠id
	 */
	private String XML_NODE_CT_ID  = "CT_ID";
	/**
	 *  대표화면 키프레임 
	 */
	private String XML_NODE_RPIMG_KFRM_SEQ  = "RPIMG_KFRM_SEQ";
	/**
	 *  순번
	 */
	private String XML_NODE_TC_SEQ  = "TC_SEQ";


	//3월 14일 추가분
	/**
	 * 비트전송율
	 */
	private String XML_NODE_LR_BIT_RT = "LR_BIT_RT";
	/**
	 * 초당프레임
	 */
	private String XML_NODE_LR_FRM_PER_SEC  = "LR_FRM_PER_SEC";
	/**
	 *  드롭프레임여부
	 */
	private String XML_NODE_LR_DRP_FRM_YN = "LR_DRP_FRM_YN";
	/**
	 *  오디오대역폭
	 */
	private String XML_NODE_LR_AUDIO_BDWT = "LR_AUDIO_BDWT";
	/**
	 *  파일사이즈 여부
	 */
	private String XML_NODE_LR_FL_SZ  = "LR_FL_SZ"; 
	/**
	 *  오디오샘플링
	 */
	private String XML_NODE_LR_AUDIO_SAMP_FRQ   = "LR_AUDIO_SAMP_FRQ"; 
	/**
	 *  비트전송율

	 */
	private String XML_NODE_HR_BIT_RT  = "HR_BIT_RT"; 
	/**
	 *  해상도
	 */
	private String XML_NODE_HR_RESOL_HR  = "HR_RESOL_HR";
	/**
	 *  초당프레임
	 */
	private String XML_NODE_HR_FRM_PER_SEC   = "HR_FRM_PER_SEC";
	/**
	 *  드롭프레임여부 
	 */
	private String XML_NODE_HR_DRP_FRM_YN  = "HR_DRP_FRM_YN";
	/**
	 *  오디오대역폭
	 */
	private String XML_NODE_HR_AUDIO_BDWT   = "HR_AUDIO_BDWT";
	/**
	 *  파일사이즈

	 */
	private String XML_NODE_HR_FL_SZ   = "HR_FL_SZ";
	/**
	 *  오디오샘플링
	 */
	private String XML_NODE_HR_AUDIO_SAMP_FRQ   = "HR_AUDIO_SAMP_FRQ";
	/**
	 *  콘텐츠 길이 
	 */
	private String XML_NODE_HR_CT_LENG   = "HR_CT_LENG";
	/**
	 *  총키프렘임수 
	 */
	private String XML_NODE_DURATION  = "DURATION";

	//2012.5.14
	/**
	 *  요청자id
	 */
	private String XML_NODE_REQ_ID= "REQ_ID";
	/**
	 *  진행률
	 */
	private String XML_NODE_PROGRESS  = "PROGRESS";
	/**
	 *  진행상황(W : 대기중, I : 진행중, C : 완료, E: 오류)
	 */
	private String XML_NODE_JOB_STATUS  = "JOB_STATUS";
	/**
	 *  job_id
	 */
	private String XML_NODE_JOB_ID  = "JOB_ID";

	/**
	 *  회사 코드
	 */
	private String XML_NODE_COCD  = "COCD";

	/**
	 * 파일명(H.264)
	 */
	private String XML_NODE_OUTPUT_H264_NM  = "OUTPUT_H264_NM";
	/**
	 * 파일명(H.264)
	 */
	//private String XML_NODE_INPUT_H264_NM  = "INPUT_H264_NM";
	//H264

	/**
	 * 오디오비트
	 */
	private String XML_NODE_H264_AUDIOBIT  = "H264_AUDIOBIT";
	/**
	 * 파일사이즈
	 */
	private String XML_NODE_H264_FILE_SIZE = "H264_FILE_SIZE";
	/**
	 * H264 해상도
	 */
	private String XML_NODE_H264_RESOL   = "H264_RESOL_HR";

	/**
	 * 비트전송율
	 */
	private String XML_NODE_H264_BIT_RT = "H264_BIT_RT";
	/**
	 * 초당프레임
	 */
	private String XML_NODE_H264_FRM_PER_SEC  = "H264_FRM_PER_SEC";
	/**
	 *  드롭프레임여부
	 */
	private String XML_NODE_H264_DRP_FRM_YN = "H264_DRP_FRM_YN";
	/**
	 *  오디오대역폭
	 */
	private String XML_NODE_H264_AUDIO_BDWT = "H264_AUDIO_BDWT";
	/**
	 *  파일사이즈 여부
	 */
	private String XML_NODE_H264_FL_SZ  = "H264_FL_SZ"; 
	/**
	 *  오디오샘플링
	 */
	private String XML_NODE_H264_AUDIO_SAMP_FRQ   = "H264_AUDIO_SAMP_FRQ"; 

	public Object setDO(String _xml) {
		setDO(new TcBeanDO());

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
		TcBeanDO infoDO = (TcBeanDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_TC_ID)) {
				infoDO.setTc_id(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_TC_NM)) {
				infoDO.setTc_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CH_SEQ)) {
				infoDO.setCh_seq(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_WORK_STAT)) {
				infoDO.setWork_stat(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MEDIA_ID)) {
				infoDO.setMedia_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQ_CD)) {
				infoDO.setReq_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_INPUT_HR)) {
				infoDO.setInput_hr(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_INPUT_LR)) {
				infoDO.setInput_lr(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_OUTPUT_LR_PATH)) {
				infoDO.setOut_put_lr_path(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_OUTPUT_CT_PATH)) {
				infoDO.setOut_put_ct_path(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_OUTPUT_LR_NM)) {
				infoDO.setOut_put_lr_nm(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_OUTPUT_CT_NM)) {
				infoDO.setOut_put_ct_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LR_AUDIOBIT)) {
				infoDO.setLr_audiobit(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LR_FILE_SIZE)) {
				if(_nodeValue.equals("")){
					infoDO.setLr_file_size("0");	
				}else{
					infoDO.setLr_file_size(_nodeValue);
				}
			}

			else if(_nodeName.equals(XML_NODE_LR_RESOL)) {
				infoDO.setLr_resol(_nodeValue.toUpperCase());
			}

			else if(_nodeName.equals(XML_NODE_CT_RESOL)) {
				infoDO.setCt_resol(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CT_FILE_SIZE)) {
				if(_nodeValue.equals("")){
					infoDO.setCt_file_size("0");	
				}else{
					infoDO.setCt_file_size(_nodeValue);
				}

			}
			else if(_nodeName.equals(XML_NODE_RESULT)) {
				infoDO.setResult(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				infoDO.setSeq(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_INPUT_LR_NM)) {
				infoDO.setInput_lr_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_INPUT_HR_NM)) {
				infoDO.setInput_hr_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CT_ID)) {
				if(_nodeValue.equals("")){
					infoDO.setCt_id(0);	
				}else{
					infoDO.setCt_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_RPIMG_KFRM_SEQ)) {
				infoDO.setRpimg_kfrm(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}



			else if(_nodeName.equals(XML_NODE_HR_FL_SZ)) {
				if(_nodeValue.equals("")){
					infoDO.setHR_FL_SZ("0");	
				}else{
					infoDO.setHR_FL_SZ(_nodeValue);
				}

			}
			else if(_nodeName.equals(XML_NODE_LR_BIT_RT)) {
				infoDO.setLR_BIT_RT(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LR_FRM_PER_SEC)) {
				infoDO.setLR_FRM_PER_SEC(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LR_DRP_FRM_YN)) {
				infoDO.setLR_DRP_FRM_YN(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LR_AUDIO_BDWT)) {
				infoDO.setLR_AUDIO_BDWT(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LR_FL_SZ)) {
				if(_nodeValue.equals("")){
					infoDO.setLR_FL_SZ("0");	
				}else{
					infoDO.setLR_FL_SZ(_nodeValue);
				}


			}
			else if(_nodeName.equals(XML_NODE_LR_AUDIO_SAMP_FRQ)) {
				infoDO.setLR_AUDIO_SAMP_FRQ(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_HR_BIT_RT)) {
				infoDO.setHR_BIT_RT(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_HR_RESOL_HR)) {
				infoDO.setHR_RESOL_HR(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_HR_FRM_PER_SEC)) {
				infoDO.setHR_FRM_PER_SEC(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_HR_AUDIO_SAMP_FRQ)) {
				infoDO.setHR_AUDIO_SAMP_FRQ(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_HR_CT_LENG)) {
				infoDO.setHR_CT_LENG(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DURATION)) {
				infoDO.setDURATION(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_HR_DRP_FRM_YN)) {
				infoDO.setHR_DRP_FRM_YN(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_HR_AUDIO_BDWT)) {
				infoDO.setHR_AUDIO_BDWT(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_TC_SEQ)) {
				if(_nodeValue.equals("")){
					infoDO.setJob_id(0);	
				}else{
					infoDO.setJob_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
				//infoDO.setJob_id(Long.parseLong(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_REQ_ID)) {
				infoDO.setReq_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PROGRESS)) {
				infoDO.setProgress(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_JOB_STATUS)) {
				infoDO.setJob_status(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_JOB_ID)) {
				infoDO.setJob_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_COCD)) {
				infoDO.setCocd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_OUTPUT_H264_NM)) {
				infoDO.setOutput_h264_nm(_nodeValue);
			}



			else if(_nodeName.equals(XML_NODE_H264_AUDIOBIT)) {
				infoDO.setH264_AUDIO_BDWT(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_H264_FILE_SIZE)) {
				if(_nodeValue.equals("")){
					infoDO.setH264_file_size("0");	
				}else{
					infoDO.setH264_file_size(_nodeValue);
				}
			}

			else if(_nodeName.equals(XML_NODE_H264_RESOL)) {
				infoDO.setH264_resol(_nodeValue);
			}


			else if(_nodeName.equals(XML_NODE_H264_BIT_RT)) {
				infoDO.setH264_BIT_RT(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_H264_FRM_PER_SEC)) {
				infoDO.setH264_FRM_PER_SEC(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_H264_DRP_FRM_YN)) {
				infoDO.setH264_DRP_FRM_YN(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_H264_AUDIO_BDWT)) {
				infoDO.setH264_AUDIO_BDWT(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_H264_FL_SZ)) {
				if(_nodeValue.equals("")){
					infoDO.setH264_FL_SZ("0");	
				}else{
					infoDO.setH264_FL_SZ(_nodeValue);
				}


			}
			else if(_nodeName.equals(XML_NODE_H264_AUDIO_SAMP_FRQ)) {
				infoDO.setH264_AUDIO_SAMP_FRQ(_nodeValue);
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
		TcBeanDO infoDO = (TcBeanDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_TC_SEQ + ">" + infoDO.getSeq() + "</"  + XML_NODE_TC_SEQ + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + infoDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</"  + XML_NODE_CT_ID + "> \n");
		_xml.append("<" + XML_NODE_REQ_CD + ">" + infoDO.getReq_cd().trim() + "</"  + XML_NODE_REQ_CD + "> \n");
		_xml.append("<" + XML_NODE_INPUT_HR + ">" + infoDO.getInput_hr().trim() + "</"  + XML_NODE_INPUT_HR + "> \n");
		_xml.append("<" + XML_NODE_INPUT_LR + ">" + infoDO.getOut_put_lr_path().trim() + "</"  + XML_NODE_INPUT_LR + "> \n");
		_xml.append("<" + XML_NODE_OUTPUT_LR_PATH + ">" + infoDO.getOut_put_lr_path().trim() + "</"  + XML_NODE_OUTPUT_LR_PATH + "> \n");		
		_xml.append("<" + XML_NODE_OUTPUT_CT_PATH + ">" + infoDO.getOut_put_ct_path().trim() + "</"  + XML_NODE_OUTPUT_CT_PATH + "> \n");	
		_xml.append("<" + XML_NODE_OUTPUT_LR_NM + ">" + infoDO.getOut_put_lr_nm().trim() + "</"  + XML_NODE_OUTPUT_LR_NM + "> \n");
		_xml.append("<" + XML_NODE_OUTPUT_CT_NM + ">" + infoDO.getOut_put_ct_nm().trim() + "</"  + XML_NODE_OUTPUT_CT_NM + "> \n");
		_xml.append("<" + XML_NODE_OUTPUT_H264_NM + ">" + infoDO.getOutput_h264_nm().trim() + "</"  + XML_NODE_OUTPUT_H264_NM + "> \n");
		_xml.append("<" + XML_NODE_INPUT_LR_NM + ">" + infoDO.getInput_lr_nm().trim() + "</"  + XML_NODE_INPUT_LR_NM + "> \n");
		_xml.append("<" + XML_NODE_INPUT_HR_NM + ">" + infoDO.getInput_hr_nm().trim() + "</"  + XML_NODE_INPUT_HR_NM + "> \n");
		_xml.append("<" + XML_NODE_WORK_STAT + ">I</"  + XML_NODE_WORK_STAT + "> \n");
		_xml.append("<" + XML_NODE_RESULT + ">" + infoDO.getResult() + "</"  + XML_NODE_RESULT + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

	public String getSubXML2() {
		TcBeanDO infoDO = (TcBeanDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD + "> \n");


		_xml.append("<" + XML_NODE_RESULT + ">" + infoDO.getResult() + "</"  + XML_NODE_RESULT + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}



}
