package test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.services.AnnotInfoDOXML;

public class InsertAnnotTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String xml = FileUtils.readFileToString(new File("D:/annot.xml"), "utf-8");
			
			AnnotInfoDOXML _doXML = new AnnotInfoDOXML();
			List _list = (List) _doXML.setDO(xml);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			List _infoList = _processor.insertAnnotinfo(308614, _list);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					AnnotInfoDOXML _do = new AnnotInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				System.out.println(_xml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
