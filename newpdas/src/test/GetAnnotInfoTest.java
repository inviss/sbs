package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.services.AnnotInfoDOXML;
import com.app.das.services.CartContDOXML;

public class GetAnnotInfoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getAnnotInfoInfoList(308614);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					AnnotInfoDOXML _do = new AnnotInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");

				System.out.println(_xml.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
