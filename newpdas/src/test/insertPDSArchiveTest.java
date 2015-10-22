package test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.transfer.IfCmsArchiveDO;
import com.app.das.business.transfer.PdsArchiveDO;
import com.app.das.services.IfCmsArchiveDOXML;
import com.app.das.services.PdsArchiveDOXML;

public class insertPDSArchiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		PdsArchiveDOXML _doXML = new PdsArchiveDOXML();
		try {
			String xml = FileUtils.readFileToString(new File("D:/222.xml"), "utf-8");
			
			PdsArchiveDO _do = (PdsArchiveDO) _doXML.setDO(xml);	


			IfCmsArchiveDOXML _doXML2 = new IfCmsArchiveDOXML();
			IfCmsArchiveDO _do2 = (IfCmsArchiveDO) _doXML2.setDO(xml);	

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			if(!_do2.getVersion().equals("2.0")){
				
				int result = _processor.insertPdasArchive(_do);
				System.out.println("result: "+result);
			}else{ 
				
				int result = _processor.insertIfCmsArchive(_do2);
				System.out.println("result: "+result);
				
			}
			//} catch (Exception e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
