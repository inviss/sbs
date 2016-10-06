package test;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.transfer.DownCartDO;
import com.app.das.services.AllPgmInfoDOXML;
import com.app.das.services.DownCartDOXML;
import com.app.das.services.PhotoInfoDOXML;
import com.app.das.util.CommonUtl;

public class UtilTest {

	public static void main(String[] args) throws Exception {

		//String xml = FileUtils.readFileToString(new File("D:/insertPDS.xml"), "utf-8");
		//System.out.println(CommonUtl.transXMLText(xml));
/*
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		AllPgmInfoDOXML _doXML = new AllPgmInfoDOXML();
		try {
			String xml = FileUtils.readFileToString(new File("D:/insertPDS.xml"), "utf-8");
			List _result = (List)_doXML.setDO(xml);
			System.out.println(_result.size());
			_processor.updateRunScheduleDt();
			_processor.insertPdsPgmInfoAll(_result);

		} catch (Exception e) {
			e.printStackTrace();
		} 
		*/
		
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		PhotoInfoDOXML _do = new PhotoInfoDOXML();

		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
		try {
			List _infoList = _processor.getPhotoInfoList(322706);
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		_xml.append("</das>");
		
		System.out.println(_xml.toString());
	}

}
