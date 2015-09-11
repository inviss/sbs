package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.services.MonitoringDOXML;

public class TCMorniteringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><monitoring><req_id /><req_nm /><title /><start_search_dd>20150909</start_search_dd><end_search_dd>20150909</end_search_dd><start_page>1</start_page><status /></monitoring></das>");


			List _infoList = _processor.getTCinfo(_do);

			System.out.println("infoList: "+_infoList.size()+", _obj: "+_infoList);
			StringBuffer _xml = new StringBuffer();
			if (_infoList != null && _infoList.size() > 0) {

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();

				MonitoringDOXML _do2 = new MonitoringDOXML();
 
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getTCXML());
				}
				_xml.append("</das>"); 
				 
				System.out.println(_xml.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
