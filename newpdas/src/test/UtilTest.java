package test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.app.das.business.transfer.CornerInfoDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.services.CornerInfoDOXML;
import com.app.das.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class UtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String xml = FileUtils.readFileToString(new File("D:/pdas2.xml"), "utf-8");
			if(xml.indexOf("<generator_version>1.0</generator_version>") > -1) {
				System.out.println("OK 1.0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
