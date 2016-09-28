package test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.CornerInfoDO;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.services.CornerInfoDOXML;
import com.app.das.services.MonitoringDOXML;
import com.app.das.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class UtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		try {
			String xml = FileUtils.readFileToString(new File("D:/pdas2.xml"), "utf-8");
			if(xml.indexOf("<generator_version>1.0</generator_version>") > -1) {
				System.out.println("OK 1.0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		CornerInfoDOXML _doXML = new CornerInfoDOXML();
		CornerInfoDOXML _do = new CornerInfoDOXML();
		try {
			List _list = (List) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><cornerInfo><CT_ID>3234234234</CT_ID><KFRM_PX_CD></KFRM_PX_CD><VD_QLTY></VD_QLTY><ASP_RTO_CD></ASP_RTO_CD><CN_ID>1</CN_ID><CN_NM>열혈남아</CN_NM><SOM></SOM><EOM></EOM><CN_TYPE_CD></CN_TYPE_CD><DURATION>0</DURATION><CT_SEQ>0</CT_SEQ><KFRM_PATH></KFRM_PATH><FL_PATH></FL_PATH><CTI_ID>0</CTI_ID><S_DURATION>0</S_DURATION><E_DURATION>0</E_DURATION><RPIMG_KFRM_SEQ>0</RPIMG_KFRM_SEQ><CT_NM></CT_NM><CONT></CONT><REG_DT></REG_DT><MASTER_ID>0</MASTER_ID><REGRID></REGRID><RPIMG_CT_ID>0</RPIMG_CT_ID><FILE_LIST></FILE_LIST><S_FRAME>0</S_FRAME></cornerInfo><cornerInfo><CT_ID>11111111111</CT_ID><KFRM_PX_CD></KFRM_PX_CD><VD_QLTY></VD_QLTY><ASP_RTO_CD></ASP_RTO_CD><CN_ID>1</CN_ID><CN_NM>열혈남아2222222222222</CN_NM><SOM></SOM><EOM></EOM><CN_TYPE_CD></CN_TYPE_CD><DURATION>0</DURATION><CT_SEQ>0</CT_SEQ><KFRM_PATH></KFRM_PATH><FL_PATH></FL_PATH><CTI_ID>0</CTI_ID><S_DURATION>0</S_DURATION><E_DURATION>0</E_DURATION><RPIMG_KFRM_SEQ>0</RPIMG_KFRM_SEQ><CT_NM></CT_NM><CONT></CONT><REG_DT></REG_DT><MASTER_ID>0</MASTER_ID><REGRID></REGRID><RPIMG_CT_ID>0</RPIMG_CT_ID><FILE_LIST></FILE_LIST><S_FRAME>0</S_FRAME></cornerInfo></das>");
			CornerInfoDO info = (CornerInfoDO)_list.get(0);
			System.out.println(info.getCtId()+", title: "+info.getCnNm());
			info = (CornerInfoDO)_list.get(1);
			System.out.println(info.getCtId()+", title: "+info.getCnNm());
			
			List _infoList = new ArrayList();
			CornerInfoDO infoDO = new CornerInfoDO();
			infoDO.setCnId(1L);
			infoDO.setCtId(1L);
			infoDO.setCnNm("hello1");
			_infoList.add(infoDO);
			
			infoDO = new CornerInfoDO();
			infoDO.setCnId(2L);
			infoDO.setCtId(2L);
			infoDO.setCnNm("hello2");
			_infoList.add(infoDO);
			
			infoDO = new CornerInfoDO();
			infoDO.setCnId(3L);
			infoDO.setCtId(3L);
			infoDO.setCnNm("hello3");
			_infoList.add(infoDO);
			
			infoDO = new CornerInfoDO();
			infoDO.setCnId(4L);
			infoDO.setCtId(4L);
			infoDO.setCnNm("hello4");
			_infoList.add(infoDO);

			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					_doXML.setDO(_iter.next());
					_xml.append(_doXML.getSubXML());
				}
				_xml.append("</das>");
				System.out.println(_xml.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
