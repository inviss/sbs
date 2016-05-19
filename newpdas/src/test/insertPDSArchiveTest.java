package test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.transfer.AttachItem;
import com.app.das.business.transfer.IfCmsArchiveDO;
import com.app.das.business.transfer.PdsArchiveDO;
import com.app.das.services.IfCmsArchiveDOXML;
import com.app.das.services.PdsArchiveDOXML;
import com.app.das.util.CommonUtl;

public class insertPDSArchiveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {



		PdsArchiveDOXML _doXML = new PdsArchiveDOXML();
		try {
			String xml = FileUtils.readFileToString(new File("D:/ifcms.xml"), "utf-8");

			PdsArchiveDO _do = (PdsArchiveDO) _doXML.setDO(xml);	


			IfCmsArchiveDOXML _doXML2 = new IfCmsArchiveDOXML();
			IfCmsArchiveDO _do2 = (IfCmsArchiveDO) _doXML2.setDO(xml);

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			if(_do2.getVersion().startsWith("1.")){
				/*System.out.println("attach: "+_do.getAttatches());
				if(_do.getAttatches() != null) {
					for(int i=0; i<_do.getAttatches().size(); i++) {
						AttachItem item = (AttachItem)_do.getAttatches().get(i);
						System.out.println("filename: "+item.getOrgFileNm());
					}
				}*/
				int result = _processor.insertNewPdasArchive(_do);
				System.out.println("result 1.0 : "+result);
			}else{ 
				int result = _processor.insertNewIfCmsArchive(_do2);
				System.out.println("result 2.0 : "+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
