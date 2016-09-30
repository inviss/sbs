package test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.services.ConditionDOXML;
import com.app.das.services.MetaInfoDOXML;

public class MetaDataInfoListTest {

	private static ExternalDAO externalDAO = ExternalDAO.getInstance();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ConditionDOXML _DOXML = new ConditionDOXML();


		StringBuffer buf = new StringBuffer();
		buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
		try {
			String xml = FileUtils.readFileToString(new File("D:/metadat.xml"), "utf-8");
			WorkStatusConditionDO _DO = (WorkStatusConditionDO) _DOXML.setDO(xml);

			List _infoList = _processor.getNewMetadatInfoList(_DO);
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					MetaInfoDOXML _do = new MetaInfoDOXML();
					_do.setDO(_iter.next());
					buf.append(_do.getSubXML());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		buf.append("</das>");

		System.out.println(buf.toString());
	}

}
