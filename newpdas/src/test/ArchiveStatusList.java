package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.services.MonitoringDOXML;

public class ArchiveStatusList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		MonitoringDOXML _doXML = new MonitoringDOXML();
		MonitoringDOXML _do2 = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><monitoring><req_id /><req_nm /><title>토요</title><start_search_dd>20151204</start_search_dd><end_search_dd>20151209</end_search_dd><start_page>1</start_page><status>E</status></monitoring></das>");

			List _infoList = _processor.getArchiveInfo(_do);


			StringBuffer _xml= new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();


				int i = 1;
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getArchiveXML());

				}

				_xml.append("</das>"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
