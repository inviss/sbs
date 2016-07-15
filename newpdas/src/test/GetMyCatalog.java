package test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.app.das.business.SearchBusinessProcessor;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.SearchConditionDO;
import com.app.das.services.DASCommonDOXML;
import com.app.das.services.SearchConditionDOXML;
import com.app.das.util.XmlUtil;

public class GetMyCatalog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			String myCatalogDO = FileUtils.readFileToString(new File("D:/myCatalog.xml"), "utf-8");
			SearchConditionDOXML _doXML1 = new SearchConditionDOXML();
			DASCommonDOXML _doXML2 = new DASCommonDOXML();
			
			SearchConditionDO searchConditionDO = (SearchConditionDO) _doXML1.setDO(myCatalogDO);
			DASCommonDO commonDO = (DASCommonDO) _doXML2.setDO(myCatalogDO);

			SearchBusinessProcessor _processor = new SearchBusinessProcessor();


			String _xml = "";
			_xml = XmlUtil.getToXmlXstream(_processor.getMyCatalogList(searchConditionDO, commonDO));
			//	logger.debug("getMyCatalog [output_xml}"+_xml);
			System.out.println(_xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
