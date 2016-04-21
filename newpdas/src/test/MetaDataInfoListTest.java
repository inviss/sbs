package test;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.services.ConditionDOXML;

public class MetaDataInfoListTest {

	private static ExternalDAO externalDAO = ExternalDAO.getInstance();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ConditionDOXML _DOXML = new ConditionDOXML();
		try {
			TcBeanDO newTcBeanDO = new TcBeanDO();
			newTcBeanDO.setSeq(8);
/*
			TcBeanDO jobTcBeanDO = externalDAO.selectMediaTcJob();
			TcBeanDO stateTcBeanDO = externalDAO.selectTcAllocationState(newTcBeanDO);
			externalDAO.getTCJob(jobTcBeanDO, stateTcBeanDO, "");
*/
			String xml = FileUtils.readFileToString(new File("D:/ddd.xml"), "utf-8");
			WorkStatusConditionDO _DO = (WorkStatusConditionDO) _DOXML.setDO(xml);

			List _infoList = _processor.getNewMetadatInfoList(_DO);
			System.out.println(_infoList);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
