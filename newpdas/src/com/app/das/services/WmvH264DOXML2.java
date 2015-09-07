
package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;


import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.MediaArchiveDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.NonEmployeeInfoDO;
import com.app.das.business.transfer.WmvH264DO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CommonUtl;


/**
 * wmv h264 전환 xml 파서
 * @author asura207
 *
 */
public class WmvH264DOXML2 extends DOXml {

	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "ct_inst";
	/**
	 * 영상 인스턴스 id

	 */
	private String XML_NODE_CTI_ID = "cti_id";

	/**
	 * 영상id

	 */
	private String XML_NODE_CT_ID = "ct_id";
	/**
	 * 파일명

	 */
	private String XML_NODE_FL_NM = "fl_nm";
	/**
	 * 파일경로

	 */
	private String XML_NODE_FL_PATH = "fl_path";
	/**
	 * 조회 갯수

	 */
	private String XML_NODE_GETCOUNT = "getcount";

	/**
	 * 진행상태

	 */
	private String XML_NODE_JOB_STATUS = "job_status";

	/**
	 * 장비id

	 */
	private String XML_NODE_EQ_ID = "eq_id";

	/**
	 * 비트전송률

	 */
	private String XML_NODE_BIT_RT  = "bit_rt";

	/**
	 * 초당 키프레임

	 */
	private String XML_NODE_FRM_PER_SEC  = "frm_per_sec";

	/**
	 * 드랍여부

	 */
	private String XML_NODE_DRP_FRM_YN  = "drp_frm_yn";

	/**
	 * 오디오 대역폭

	 */
	private String XML_NODE_AUDIO_BDWT  = "audio_bdwt";

	/**
	 * 파일크기

	 */
	private String XML_NODE_FL_SZ  = "fl_sz";
	/**
	 * 오디오 샘플링

	 */
	private String XML_NODE_AUDIO_SAMP_FRQ  = "audio_samp_frq";

	/**
	 * 가로 해상도

	 */
	private String XML_NODE_VD_VRESOL  = "vd_vresol";


	/**
	 * 세로 해상도

	 */
	private String XML_NODE_VD_HRESOL  = "vd_hresol";


	/**
	 * 화질코드

	 */
	private String XML_NODE_VD_QLTY  = "vd_qlty";


	/**
	 * 결과

	 */
	private String XML_NODE_RESULT  = "result";

	public Object setDO(String _xml) {
		setDO(new WmvH264DO());

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
		WmvH264DO item = (WmvH264DO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();

		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CTI_ID)) {

				item.setCti_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_CT_ID)) {

				item.setCt_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_FL_NM)) {
				item.setFl_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FL_PATH)) {
				item.setFl_path(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GETCOUNT)) {
				item.setGetcount(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_JOB_STATUS)) {
				item.setJob_status(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EQ_ID)) {
				item.setEq_id(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_BIT_RT)) {
				item.setBit_rt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FRM_PER_SEC)) {
				item.setFrm_per_sec(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DRP_FRM_YN)) {
				item.setDrp_frm_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_AUDIO_BDWT)) {
				item.setAudio_bdwt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FL_SZ)) {
				item.setFl_sz(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_AUDIO_SAMP_FRQ)) {
				item.setAudio_samp_frq(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VD_VRESOL)) {
				item.setVd_vresol(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VD_HRESOL)) {
				item.setVd_hresol(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VD_QLTY)) {
				item.setVd_qlty(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RESULT)) {
				item.setResult(_nodeValue);
			}

		}

		return item;
	}
	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}


	public String getSubXML() {
		WmvH264DO item = (WmvH264DO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_CTI_ID + ">" + item.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n");
		_xml.append("<" + XML_NODE_FL_NM + ">" + CommonUtl.transXmlText(item.getFl_nm()) + "</"  + XML_NODE_FL_NM + "> \n");
		_xml.append("<" + XML_NODE_FL_PATH + ">" + item.getFl_path() + "</"  + XML_NODE_FL_PATH + "> \n");
		_xml.append("<" + XML_NODE_VD_QLTY + ">" + item.getVd_qlty() + "</"  + XML_NODE_VD_QLTY + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}





}
