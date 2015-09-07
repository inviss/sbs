
package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;


import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.DtlInfoDO;
import com.app.das.business.transfer.EquipMentInfoDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.NonEmployeeInfoDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CommonUtl;


/**
 * 장비 정보관련 파서 
 * @author asura207
 *
 */
public class EquipMentDOXML extends DOXml {
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * xml 헤더
	 */
	private String equipment = "equipment";
	/**
	 * DAS장비ID
	 */
	private String das_eq_id = "das_eq_id";

	/**
	 *DAS작업구분코드\N(A00 SDI 인제스트 B00 TAPEOUT C00 FILE 인제스트 D00 SGL ARCHIVE E00 NLE)
	 */
	private String das_eq_clf_cd = "das_eq_clf_cd";

	/**
	 *DAS장비명
	 */
	private String das_eq_nm = "das_eq_nm";
	
	/**
	 *CTI_ID
	 */
	private String cti_id = "cti_id";

	/**
	 *진행률
	 */
	private String progress = "progress";

	/**
	 *제목
	 */
	private String title = "title";
	
	/**
	 *상태
	 */
	private String status = "status";
	
	
	
	public Object setDO(String _xml) {
		setDO(new EquipMentInfoDO());
		
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(equipment)) {
				setData((Element)_node);
			}
        }
		
		return getDO();
	}
	
	public Object setData(Element pElement) {
		EquipMentInfoDO InfoDO = (EquipMentInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			 if(_nodeName.equals(das_eq_clf_cd)) {
				 InfoDO.setDas_eq_clf_cd(_nodeValue);
			}
			else if(_nodeName.equals(das_eq_id)) {
				InfoDO.setDas_eq_id(_nodeValue);
			}
			else if(_nodeName.equals(das_eq_nm)) {
				InfoDO.setDas_eq_nm(_nodeValue);
			}else if(_nodeName.equals(cti_id)) {
				InfoDO.setCti_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(progress)) {
				InfoDO.setProgress(_nodeValue);
			}else if(_nodeName.equals(title)) {
				InfoDO.setTitle(_nodeValue);
			}else if(_nodeName.equals(status)) {
				InfoDO.setStatus(_nodeValue);
			}
			
        }
	    
	    return InfoDO;
	}
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}
	
	
	public String getSubXML() {
		DtlInfoDO dtlInfoDO = (DtlInfoDO)getDO();
		String _xml =  "";
		/*String _xml =   "<" + XML_NODE_HEAD + "> \n";	
		_xml = _xml + 	"<" + XML_NODE_DTL_NM + ">" + dtlInfoDO.getDtl_nm() + "</"  + XML_NODE_DTL_NM + "> \n";
		_xml = _xml + 	"<" + XML_NODE_ALIAS + ">" + dtlInfoDO.getAlias() + "</"  + XML_NODE_ALIAS + "> \n";
		_xml = _xml + 	"<" + XML_NODE_DTL_CONT + ">" + dtlInfoDO.getDtl_cont() + "</"  + XML_NODE_DTL_CONT + "> \n";
		
		
		_xml = _xml + "</" + XML_NODE_HEAD + ">";
	*/
		
		
		return _xml;
	}

	
	
}
