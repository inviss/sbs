package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.TransferDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CommonUtl;
/**
 *  스토리지 다운로드  정보 관련 XML파서
 * @author asura207
 *
 */
public class TransferByStorageDOXML extends DOXml{
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * xml해더
	 */
	private String XML_NODE_ADDTASK_HEAD = "Request";
	/**
	 * xml해더
	 */
	private String XML_NODE_GETSTATUS_HEAD = "Response";

	//private String XML_NODE_SOURCE_MEDIA_FILE ="";
	/**
	 * xml해더
	 */
	private String XML_NODE_SOURCE_RESULT ="Result";

	///private String XML_NODE_SOURCE_MEDIA_XML = "";

	//private String XML_NODE_TARGET_MEDIA_FILE = "";

	//private String XML_NODE_TARGET_MEDIA_XML = "";

	//private String XML_NODE_CELL_ID = "";

	//private String XML_NODE_TYPE = "";

	//private String XML_NODE_ACTION = "";
	/**
	 * 태스크아이디
	 */
	private String XML_NODE_TASKID = "TaskID";
	/**
	 * 메세지
	 */
	private String XML_NODE_MESSAGE = "Message";

	//private String XML_NODE_STATUS = "";

	//private String XML_NODE_PROGRESS = "";
	/**
	 * 파일경로
	 */
	private String XML_NODE_FILE_PATH = "file_path";
	/**
	 * 카드번호
	 */
	private String XML_NODE_CART_NO = "cart_no";
	/**
	 * 카트순번
	 */
	private String XML_NODE_CART_SEQ = "cart_seq";
	/**
	 * 성공여부
	 */
	private String XML_NODE_SUCCESS_YN = "success_yn";
	/**
	 * 저장위치
	 */
	private String XML_NODE_STRG_LOC = "strg_loc";

	public Object getStatus(String _xml){
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		//System.out.println("_rootElement"+_rootElement);

		return getDO();
	}

	public Object setDO(String _xml) {
		setDO(new TransferDO());

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			NodeList nList = _node.getChildNodes();



			for(int k=0; k<nList.getLength();k++){
				Node node = nList.item(k);
				String nodeName = node.getNodeName() ;
				if(_nodeName.equals(XML_NODE_SOURCE_RESULT)) {
					setData((Element)_node);
				}
			}
		}
		return getDO();
	}

	public Object setData(Element pElement) {
		TransferDO infoDO = (TransferDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_TASKID)) {
				infoDO.setTaskID(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_MESSAGE)) {
				infoDO.setMessage(StringUtils.defaultIfEmpty(_nodeValue, "0"));
			}else if(_nodeName.equals(XML_NODE_CART_NO)) {
				infoDO.setCart_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_CART_SEQ)) {
				infoDO.setCart_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_FILE_PATH)) {
				infoDO.setFile_path(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_STRG_LOC)){
				infoDO.setStrg_loc(_nodeValue);
			}

		}

		return infoDO;
	}	    

	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}

	public String getSubXML() {
		TransferDO infoDO = (TransferDO)getDO();
		String  file_name = infoDO.getFl_nm().substring(0, infoDO.getFl_nm().lastIndexOf("."));
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_ADDTASK_HEAD+ ">");
		_xml.append("<AddTask Type=\"Transfer\" Action=\"queue\">");
		_xml.append("<Source>");
		_xml.append("<file>" +CommonUtl.transXmlText(infoDO.getStrg_loc())+"/"+ CommonUtl.transXmlText(infoDO.getFl_nm()) + "</file>");
		_xml.append("<file>" +dasHandler.getProperty("MP2")+"/restore/"+infoDO.getUser_id()+"/"+infoDO.getCart_no()+"/"+CommonUtl.transXmlText(file_name) +".xml" + "</file>");
		_xml.append("</Source>");
		_xml.append("<Target Destination=\""+CommonUtl.transXmlText(infoDO.getStoragename())+"\">");
		_xml.append("<file>" + +infoDO.getCart_no()+"_" +CommonUtl.transXmlText(infoDO.getFl_nm()) + "</file>");
		_xml.append("<file>" + file_name +".xml"+ "</file>");
		_xml.append("</Target>");
		_xml.append("</AddTask>");
		_xml.append("</" + XML_NODE_ADDTASK_HEAD + ">");
		//		
		return _xml.toString(); 
	}

	public String getSubXML3() {
		TransferDO infoDO = (TransferDO)getDO();
		StringBuffer _xml = new StringBuffer();
		//String  file_name = infoDO.getFl_nm().substring(0, infoDO.getFl_nm().lastIndexOf("."));
		_xml.append("<" + XML_NODE_ADDTASK_HEAD+ ">");
		if(infoDO.getDown_gubun().equals("007")){
			_xml.append("<AddTask Type=\"Transfer\" Action=\"queue\" tm_gb=\"ifcms\"  system=\""+infoDO.getTarget_cms_id()+"\">");
		}else {
			_xml.append("<AddTask Type=\"Transfer\" Action=\"queue\" >");
		}

		if(infoDO.getDown_typ().equals("P")){
			_xml.append("<Source resolution=\""+infoDO.getVd_qlty()+"\"  tc_in=\""+infoDO.getSom()+"\" tc_out=\""+infoDO.getEom()+"\">");
		}else{
			_xml.append("<Source>");	
		}
		String path = infoDO.getStrg_loc()+"/"+ infoDO.getWrk_fl_nm();
		path = path.replaceAll("/"+dasHandler.getProperty("WINMP2"),dasHandler.getProperty("MP2"));
		path = path.replaceAll(dasHandler.getProperty("WINMP2"),dasHandler.getProperty("MP2"));
		path = path.replaceAll("/"+dasHandler.getProperty("WINARCREQ"),dasHandler.getProperty("ARCREQ"));
		path = path.replaceAll(dasHandler.getProperty("WINARCREQ"),dasHandler.getProperty("ARCREQ"));
		path = path.replaceAll("/"+dasHandler.getProperty("WINNEARLINE"),dasHandler.getProperty("ARCREQ"));
		path = path.replaceAll(dasHandler.getProperty("WINNEARLINE"),dasHandler.getProperty("NEARLINE"));
		_xml.append("<file>" +path.replaceAll("//", "/") + "</file>");
		//_xml = _xml + "<file>" +infoDO.getStrg_loc()+"/"+ infoDO.getFl_nm() + "</file>";
		_xml.append("<file>" +dasHandler.getProperty("MP2")+"/restore/"+infoDO.getUser_id()+"/"+infoDO.getCart_no()+"/"+infoDO.getFl_nm() +".xml" + "</file>");

		_xml.append("</Source>");
		_xml.append("<Target Destination=\""+infoDO.getStoragename()+"\">");

		//다운로드 유형이 파셜인경우 cart_seq + cti_id.mxf, 풀인경우 cti.mxf
		if(infoDO.getDown_typ().equals("P")){
			_xml.append("<file>"+dasHandler.getProperty("MP2")+"/restore/"+infoDO.getUser_id()+"/"+infoDO.getCart_no()+"/"+ infoDO.getCart_seq()+"_"+infoDO.getCti_id()+".mxf" + "</file>");
			_xml.append("<file>" + CommonUtl.transXmlText(infoDO.getFl_nm()) +".xml"+ "</file>");	
		}else{
			_xml.append("<file>"+dasHandler.getProperty("MP2")+"/restore/"+infoDO.getUser_id()+"/"+infoDO.getCart_no()+"/"+ infoDO.getCti_id()+".mxf"  + "</file>");
			_xml.append("<file>" + CommonUtl.transXmlText(infoDO.getFl_nm()) +".xml"+ "</file>");	
		}

		_xml.append("</Target>");
		_xml.append("</AddTask>");
		_xml.append("</" + XML_NODE_ADDTASK_HEAD + ">");
		//		
		return _xml.toString().trim(); 
	}



	public String getSubXML2() {
		TransferDO infoDO = (TransferDO)getDO();
		String  file_name = infoDO.getFl_nm().substring(0, infoDO.getFl_nm().lastIndexOf("."));
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_ADDTASK_HEAD+ ">");
		_xml.append("<AddTask Type=\"Transfer\" Action=\"queue\">");
		_xml.append("<Source>");
		_xml.append("<file>" +dasHandler.getProperty("MP2")+"/restore/"+infoDO.getUser_id()+"/"+infoDO.getCart_no()+"/"+ infoDO.getFl_nm() + "</file>");

		_xml.append("</Source>");
		_xml.append("<Target Destination=\""+infoDO.getStoragename()+"\">");
		//_xml = _xml + "<file>" + infoDO.getFile_path()+"\\"+ infoDO.getRename()+".mxf" + "</file>";
		_xml.append("<file>" + infoDO.getRename()+".mxf" + "</file>");


		_xml.append("</Target>");
		_xml.append("</AddTask>");
		_xml.append("</" + XML_NODE_ADDTASK_HEAD + ">");
		//		
		return _xml.toString(); 
	}

}
