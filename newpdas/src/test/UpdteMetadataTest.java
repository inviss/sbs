package test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.MetadataMstInfoDO;
import com.app.das.services.MetadataMstInfoDOXML;

public class UpdteMetadataTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MetadataMstInfoDOXML _doXML = new MetadataMstInfoDOXML();
		try {
			String xml = FileUtils.readFileToString(new File("D:/meta.xml"), "utf-8");
			MetadataMstInfoDO _do = (MetadataMstInfoDO) _doXML.setDO(xml);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			_processor.updateMetadat(_do);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
