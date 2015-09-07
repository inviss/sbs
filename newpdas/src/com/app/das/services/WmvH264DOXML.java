
package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;


import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.MediaArchiveDO;
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
public class WmvH264DOXML extends DOXml {
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "wmv";
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD2 = "corner";
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD3 = "relation";
	/**
	 * ct_id 그룹
	 */
	private String XML_NODE_CT_IDS = "group";
	/**
	 * 영상 id

	 */
	private String XML_NODE_CT_ID = "ct_id";
	/**
	 * 영상 인스턴스 id

	 */
	private String XML_NODE_CTI_ID = "cti_id";
	/**
	 * 영상명
	 */
	private String XML_NODE_CT_NM = "ct_nm";
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
	private String XML_NODE_GETCOUNT = "req_co";
	/**
	 * 결과

	 */
	private String XML_NODE_RESULT = "result";


	/**
	 * 결과

	 */
	private String XML_NODE_VD_QLTY = "vd_qlty";


	/**
	 * 복본여부

	 */
	private String XML_NODE_COPY_STATUS = "copy_status";
	/**
	 * 기존복본여부

	 */
	private String XML_NODE_OLD_COPY_STATUS = "old_copy_status";
	/**
	 * 소산여부

	 */
	private String XML_NODE_BACKUP_STATUS = "backup_status";

	/**
	 * key

	 */
	private String XML_NODE_KEY = "key";

	/**
	 * 영상길이

	 */
	private String XML_NODE_CT_LENG = "ct_leng";
	/**
	 * 파일크기

	 */
	private String XML_NODE_FL_SZ = "fl_sz";

	/**
	 *DTL 존재여부

	 */
	private String XML_NODE_DTL_YN = "dtl_yn";
	/*	public Object setDO(String _xml) {
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
	}*/


	public Object setDO(String _xml) {
		setDO(new WmvH264DO());
		WmvH264DO item = (WmvH264DO)getDO();
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = getNodeValue(_node);

			if(_nodeName.equals(XML_NODE_GETCOUNT)) {
				//setData((Element)_node);
				item.setGetcount(_nodeValue);
			}
		}

		return item;
	}


	/*public Object setData(Element pElement) {
		WmvH264DO item = (WmvH264DO)getDO();

	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = getNodeValue(_node);

			if(_nodeName.equals(XML_NODE_CTI_ID)) {

				item.setCti_id(Long.parseLong(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_FL_NM)) {
				item.setFl_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FL_PATH)) {
				item.setFl_path(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GETCOUNT)) {
				item.setGetcount(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CT_ID)) {
				item.setCt_id(Long.parseLong(_nodeValue));
			}


        }

	    return item;
	}*/
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
		_xml.append("<" + XML_NODE_CT_ID + ">" + item.getCt_id() + "</"  + XML_NODE_CT_ID + "> \n");
		_xml.append("<" + XML_NODE_CTI_ID + ">" + item.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n");
		_xml.append("<" + XML_NODE_FL_NM + ">" + item.getFl_nm().trim() + "</"  + XML_NODE_FL_NM + "> \n");
		_xml.append("<" + XML_NODE_VD_QLTY + ">" + item.getVd_qlty() + "</"  + XML_NODE_VD_QLTY + "> \n");
		_xml.append("<" + XML_NODE_FL_PATH + ">" + item.getFl_path() + "</"  + XML_NODE_FL_PATH + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}



	public String getResultXML() {
		WmvH264DO item = (WmvH264DO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	

		_xml.append("<" + XML_NODE_CTI_ID + ">" + item.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n");
		_xml.append("<" + XML_NODE_RESULT + ">" + CommonUtl.transXmlText(item.getResult()) + "</"  + XML_NODE_RESULT + "> \n");
		//_xml = _xml + 	"<" + XML_NODE_FL_PATH + ">" + item.getFl_path() + "</"  + XML_NODE_FL_PATH + "> \n";

		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}


	public String getGroupXML() {
		WmvH264DO item = (WmvH264DO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append(   "<" + XML_NODE_HEAD2 + "> \n");	

		_xml.append(	"<" + XML_NODE_CT_IDS + ">" + item.getCti_ids() + "</"  + XML_NODE_CT_IDS + "> \n");
		_xml.append(	"<" + XML_NODE_CT_NM + ">" + CommonUtl.transXmlText(item.getCt_nm()) + "</"  + XML_NODE_CT_NM + "> \n");

		_xml.append("</" + XML_NODE_HEAD2 + ">");
		if(!item.getRel_ct_id().equals("0")||item.getRel_ct_id().equals("")){
			_xml.append("<" + XML_NODE_HEAD3 + ">");
			_xml.append(	"<" + XML_NODE_CT_ID + ">" + item.getRel_ct_id() + "</"  + XML_NODE_CT_ID + "> \n");
			_xml.append(	"<" + XML_NODE_CT_NM + ">" + CommonUtl.transXmlText(item.getRel_ct_nm())+ "</"  + XML_NODE_CT_NM + "> \n");
			_xml.append("</" + XML_NODE_HEAD3 + ">");
		}

		return _xml.toString();
	}


	public String getGroupXML2() {
		WmvH264DO item = (WmvH264DO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD2 + "> \n");	

		_xml.append("<" + XML_NODE_KEY + ">" + item.getKey() + "</"  + XML_NODE_KEY + "> \n");
		_xml.append("<" + XML_NODE_CTI_ID + ">" + item.getCti_id() + "</"  + XML_NODE_CTI_ID + "> \n");
		_xml.append("<" + XML_NODE_CT_NM + ">" + CommonUtl.transXmlText(item.getCt_nm()) + "</"  + XML_NODE_CT_NM + "> \n");
		_xml.append("<" + XML_NODE_COPY_STATUS + ">" + item.getCyn() + "</"  + XML_NODE_COPY_STATUS + "> \n");
		_xml.append("<" + XML_NODE_OLD_COPY_STATUS + ">" + item.getOld_cyn() + "</"  + XML_NODE_OLD_COPY_STATUS + "> \n");
		_xml.append("<" + XML_NODE_BACKUP_STATUS + ">" + CommonUtl.transXmlText(item.getByn()) + "</"  + XML_NODE_BACKUP_STATUS + "> \n");
		_xml.append("<" + XML_NODE_FL_SZ + ">" + item.getFl_sz() + "</"  + XML_NODE_FL_SZ + "> \n");
		_xml.append("<" + XML_NODE_CT_LENG + ">" +item.getCt_leng() + "</"  + XML_NODE_CT_LENG + "> \n");
		_xml.append("<" + XML_NODE_DTL_YN + ">" + item.getDtl_yn() + "</"  + XML_NODE_DTL_YN + "> \n");
		_xml.append("</" + XML_NODE_HEAD2 + ">");

		return _xml.toString();
	}

	@Override
	public Object setData(Element pElement) {
		// TODO Auto-generated method stub
		return null;
	}




}
