package test;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
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
		/*
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		DownCartDOXML _do = new DownCartDOXML();
		try {
			DownCartDO _infoList = _processor.getCartInfo(cartNo, reqUserId);

			StringBuffer buff = new StringBuffer();
			buff.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			_do.setDO(_infoList);
			buff.append(_do.getSubXML());

			buff.append("</das>");

			return buff.toString();
		} catch (Exception e) {
			logger.error("getCartInfo error", e);
		}
		*/
	}

}
