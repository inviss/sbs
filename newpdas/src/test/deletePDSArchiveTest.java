package test;

import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.transfer.DeleteDO;
import com.app.das.services.DeletePdsArchiveDOXML;

public class deletePDSArchiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DeletePdsArchiveDOXML _doXML = new DeletePdsArchiveDOXML();
		try {
			DeleteDO _do = (DeleteDO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><delete><media_id>MC20150930V1812</media_id></delete></das>");
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		 
			String result = _processor.deletePDSArchive(_do);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
