package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.transfer.StorageDO;
import com.app.das.services.StorageDOXML;

public class StorageCheckTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			StorageDOXML _DOXML = new StorageDOXML();
			StorageDO ConditionDO = (StorageDO) _DOXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><storage><system_id>SBSNON</system_id></storage></das>");
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			List _infoList = _processor.getStorageCheck(ConditionDO);


			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StorageDOXML _do = new StorageDOXML();
					_do.setDO(_iter.next());

					_xml.append(_do.getStorageCheck());

				}
			}
			_xml.append("</das>");


			System.out.println(_xml.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
