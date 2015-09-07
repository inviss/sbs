package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ModeUserInfoDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.util.CommonUtl;
/**
 *  내목록 정보 관련 XML파서
 * @author asura207
 *
 */
public class MyCatalogInfoDOXML2 extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "mycataloginfo";
	/**
	 * 일련번호
	 */
	private String XML_NODE_SERIALNO = "serialno";
	/**
	 * 요청자ID
	 */
	private String XML_NODE_REQUSRID = "requsrid";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ = "seq";
	/**
	 * 마스타ID
	 */
	private String XML_NODE_MASTERID = "masterid";
	/**
	 * 코너ID
	 */
	private String XML_NODE_CNID = "cnid";
	/**
	 * 프로그램ID
	 */
	private String XML_NODE_PGMID = "pgmid";
	/**
	 * 프로그램제목
	 */
	private String XML_NODE_PGMNM = "pgmnm";
	/**
	 * 회차
	 */
	private String XML_NODE_EPN = "epn";
	/**
	 * 타이틀
	 */
	private String XML_NODE_TITLE ="title";
	/**
	 * 방송일자
	 */
	private String XML_NODE_BRDDD ="brddd";
	/**
	 * 방송길이
	 */
	private String XML_NODE_BRDLENG = "brdleng";
	/**
	 * 코너길이
	 */
	private String XML_NODE_CNLENG = "cnleng";
	/**
	 * 주석구분코드
	 */
	private String XML_NODE_ANNOTCLFCD = "annotclfcd";
	/**
	 * 내용
	 */
	private String XML_NODE_CONT = "cont";
	/**
	 * 대표화면
	 */
	private String XML_NODE_RPIMG = "rpimg";
	/**
	 * 명장면
	 */
	private String XML_NODE_GOODSC = "goodsc";
	/**
	 * 사용금지
	 */
	private String XML_NODE_NOTUSE = "notuse";
	/**
	 * 심의지적
	 */
	private String XML_NODE_DILBRT = "dilbrt";
	/**
	 * 확인 후 사용
	 */
	private String XML_NODE_CHECK = "check";
	/**
	 * 키프레임경로(대표사진 파일 경로)
	 */
	private String XML_NODE_KFRMPATH = "kfrmpath";
	/**
	 * 키프레임일련번호
	 */
	private String XML_NODE_KFRMSEQ = "kfrmseq";
	/**
	 * 등록일시
	 */
	private String XML_NODE_REGDT = "regdt";
	/**
	 * 등록자ID
	 */
	private String XML_NODE_REGRID = "regrid";
	/**
	 * 수정일시
	 */
	private String XML_NODE_MODDT = "moddt";
	/**
	 * 수정자ID
	 */
	private String XML_NODE_MODRID = "modrid";
	/**
	 * 대표화면 콘텐츠ID
	 */	
	private String XML_NODE_RPIMGCTID = "rpimgctid";
	/**
	 * 비디오 상태 유형
	 */	
	private String XML_NODE_VDQLTY = "vdqlty";
	/**
	 * 비디오 비율
	 */
	private String XML_NODE_ASPRTOCD = "asprtocd";
	/**
	 * 편성명
	 */
	private String XML_NODE_SCHD_PGM_NM = "schd_pgm_nm";
	/**
	 * 파일럿
	 */
	private String XML_NODE_PILOT_YN = "pilot_yn";
	/**
	 * 부제
	 */
	private String XML_NODE_SUB_TTL = "sub_ttl";
	/**
	 * 방송요일
	 */
	private String XML_NODE_DAY = "day";
	/**
	 * 내용
	 */
	private String XML_NODE_SNPS = "snps";
	/**
	 * 최종회 여부 
	 */
	private String XML_NODE_FINAL_BRD_YN = "final_brd_yn";
	
	
	/**
	 * 삭제 순번 그룹(예: 16,17)
	 */
	private String XML_NODE_DEL_SEQ = "del_seq";
	
	
	public Object setDO(String _xml) {
		setDO(new MyCatalogDO());
		
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
		MyCatalogDO infoDO = (MyCatalogDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_SERIALNO)) {
				infoDO.setSerialNo(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQUSRID)) {
				infoDO.setReqUsrId((_nodeValue));
			}	
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SEQ)) {
				infoDO.setSeq(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MASTERID)) {
				infoDO.setMasterId(Long.parseLong((StringUtils.defaultIfEmpty(_nodeValue, "0"))));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CNID)) {
				infoDO.setCnId(Long.parseLong((StringUtils.defaultIfEmpty(_nodeValue, "0"))));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PGMID)) {
				infoDO.setPgmId(Long.parseLong((StringUtils.defaultIfEmpty(_nodeValue, "0"))));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PGMNM)) {
				infoDO.setPgmNm((_nodeValue));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_EPN)) {

				if(!_nodeValue.equals("")){
				infoDO.setEpn(_nodeValue);
				}else{
					infoDO.setEpn("0");	
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TITLE)) {
				infoDO.setTitle((_nodeValue));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRDDD)) {
				infoDO.setBrdDd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRDLENG)) {
				infoDO.setBrdLeng(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CNLENG)) {
				infoDO.setCnLeng(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ANNOTCLFCD)) {
				infoDO.setAnnotClfCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CONT)) {
				infoDO.setCont(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RPIMG)) {
				infoDO.setRpImg(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GOODSC)) {
				infoDO.setGoodSc(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_NOTUSE)) {
				infoDO.setNotUse(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DILBRT)) {
				infoDO.setDilbrt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CHECK)) {
				infoDO.setCheck(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_KFRMPATH)) {
				infoDO.setKfrmPath(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_KFRMSEQ)) {
				infoDO.setKfrmSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REGDT)) {
				infoDO.setRegDt((_nodeValue));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REGRID)) {
				infoDO.setRegrId((_nodeValue));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MODDT)) {
				infoDO.setModDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MODRID)) {
				infoDO.setModrId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RPIMGCTID)) {
				infoDO.setRpimgCtId(Long.parseLong((StringUtils.defaultIfEmpty(_nodeValue, "0"))));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_VDQLTY)) {
				infoDO.setVdQlty(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ASPRTOCD)) {
				infoDO.setAspRtoCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SCHD_PGM_NM)) {
				infoDO.setSchd_pgm_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PILOT_YN)) {
				infoDO.setPilot_yn(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SUB_TTL)) {
				infoDO.setSub_ttl(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DAY)) {
				infoDO.setDay(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SNPS)) {
				infoDO.setSnps(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FINAL_BRD_YN)) {
				infoDO.setFinal_brd_yn(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DEL_SEQ)) {
				infoDO.setDel_seq(_nodeValue);
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
		MyCatalogDO infoDO = (MyCatalogDO)getDO();
		
		StringBuffer _xml = new StringBuffer();
		_xml.append( "<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_ANNOTCLFCD + ">" + infoDO.getAnnotClfCd() + "</"  + XML_NODE_ANNOTCLFCD + ">");
		_xml.append("<" + XML_NODE_ASPRTOCD + ">" + infoDO.getAspRtoCd() + "</"  + XML_NODE_ASPRTOCD + ">");
		_xml.append("<" + XML_NODE_BRDDD + ">" + infoDO.getBrdDd() + "</"  + XML_NODE_BRDDD + ">");
		_xml.append("<" + XML_NODE_BRDLENG + ">" + infoDO.getBrdLeng() + "</"  + XML_NODE_BRDLENG + ">");
		_xml.append("<" + XML_NODE_CHECK + ">" + infoDO.getCheck() + "</"  + XML_NODE_CHECK + ">");
		_xml.append("<" + XML_NODE_CNID + ">" + infoDO.getCnId() + "</"  + XML_NODE_CNID + ">");
		_xml.append("<" + XML_NODE_CNLENG + ">" + infoDO.getCnLeng() + "</"  + XML_NODE_CNLENG + ">");
		_xml.append("<" + XML_NODE_CONT + ">" + CommonUtl.transXmlText(infoDO.getCont()) + "</"  + XML_NODE_CONT + ">");
		_xml.append("<" + XML_NODE_DAY + ">" + infoDO.getDay() + "</"  + XML_NODE_DAY + ">");
		_xml.append("<" + XML_NODE_DILBRT + ">" + infoDO.getDilbrt() + "</"  + XML_NODE_DILBRT + ">");
		_xml.append("<" + XML_NODE_EPN + ">" + infoDO.getEpn() + "</"  + XML_NODE_EPN + ">");
		_xml.append("<" + XML_NODE_FINAL_BRD_YN + ">" + infoDO.getFinal_brd_yn() + "</"  + XML_NODE_FINAL_BRD_YN + ">");
		_xml.append("<" + XML_NODE_GOODSC + ">" + infoDO.getGoodSc() + "</"  + XML_NODE_GOODSC + ">");
		_xml.append("<" + XML_NODE_KFRMPATH + ">" + infoDO.getKfrmPath() + "</"  + XML_NODE_KFRMPATH + ">");
		_xml.append("<" + XML_NODE_KFRMSEQ + ">" + infoDO.getKfrmSeq() + "</"  + XML_NODE_KFRMSEQ + ">");
		_xml.append("<" + XML_NODE_MASTERID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTERID + ">");
		_xml.append("<" + XML_NODE_MODDT + ">" + infoDO.getModDt() + "</"  + XML_NODE_MODDT + ">");
		_xml.append("<" + XML_NODE_MODRID + ">" + infoDO.getModrId() + "</"  + XML_NODE_MODRID + ">");
		_xml.append("<" + XML_NODE_NOTUSE + ">" + infoDO.getNotUse() + "</"  + XML_NODE_NOTUSE + ">");
		_xml.append("<" + XML_NODE_PGMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PGMID + ">");
		_xml.append("<" + XML_NODE_PGMNM + ">" + CommonUtl.transXmlText(infoDO.getPgmNm()) + "</"  + XML_NODE_PGMNM + ">");
		_xml.append("<" + XML_NODE_PILOT_YN + ">" + infoDO.getPilot_yn() + "</"  + XML_NODE_PILOT_YN + ">");
		_xml.append("<" + XML_NODE_REGDT + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGDT + ">");
		_xml.append("<" + XML_NODE_REGRID + ">" + infoDO.getRegrId() + "</"  + XML_NODE_REGRID + ">");
		_xml.append("<" + XML_NODE_RPIMG + ">" + infoDO.getRpImg() + "</"  + XML_NODE_RPIMG + ">");
		_xml.append("<" + XML_NODE_RPIMGCTID + ">" + infoDO.getRpimgCtId()+ "</"  + XML_NODE_RPIMGCTID + ">");
		_xml.append("<" + XML_NODE_SCHD_PGM_NM + ">" + CommonUtl.transXmlText(infoDO.getSchd_pgm_nm()) + "</"  + XML_NODE_SCHD_PGM_NM + ">");
		_xml.append("<" + XML_NODE_SEQ + ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQ + ">");
		_xml.append("<" + XML_NODE_SERIALNO + ">" + infoDO.getSerialNo() + "</"  + XML_NODE_SERIALNO + ">");
		_xml.append("<" + XML_NODE_SNPS + ">" + CommonUtl.transXmlText(infoDO.getSnps()) + "</"  + XML_NODE_SNPS + ">");
		_xml.append("<" + XML_NODE_SUB_TTL + ">" + CommonUtl.transXmlText(infoDO.getSub_ttl()) + "</"  + XML_NODE_SUB_TTL + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_VDQLTY + ">" + infoDO.getVdQlty() + "</"  + XML_NODE_VDQLTY + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
		
		
		
		
	}
}
