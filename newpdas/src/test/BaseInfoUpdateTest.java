package test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.MetadataMstInfoDO;
import com.app.das.services.MetadataMstInfoDOXML;

public class BaseInfoUpdateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String xml = null;
		try {
			xml = FileUtils.readFileToString(new File("D:/metadata.xml"), "utf-8");

			MetadataMstInfoDOXML _doXML = new MetadataMstInfoDOXML();
			MetadataMstInfoDO _do = (MetadataMstInfoDO) _doXML.setDO(xml);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			//_processor.updateMetadat(_do);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
