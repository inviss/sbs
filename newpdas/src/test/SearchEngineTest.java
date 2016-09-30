package test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.app.das.business.SearchBusinessProcessor;
import com.app.das.services.SearchInfoDOXML;
import com.konantech.search.data.ParameterVO;

public class SearchEngineTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		SearchInfoDOXML _doXML = new SearchInfoDOXML();
		try {
			String xml = FileUtils.readFileToString(new File("D:/search.xml"), "utf-8");
			
			ParameterVO _do = (ParameterVO) _doXML.setDO(xml);
			SearchBusinessProcessor _processor = new SearchBusinessProcessor();
			String sResult = _processor.getSearchText(_do);
		 
			System.out.println(sResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
