package test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.dao.statement.ExternalStatement;
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.services.ProgramInfoDOXML;

public class findTotalChangeListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			String xml = FileUtils.readFileToString(new File("D:/change.xml"), "utf-8");
			
			ProgramInfoDOXML _doXML = new ProgramInfoDOXML();
			ProgramInfoDO _doing = (ProgramInfoDO)_doXML.setDO(xml);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			int totalCount  = _processor.getTotalChangeCount(_doing);
			_doing.setTotalpage(totalCount);
			System.out.println("totalCount : "+totalCount);
			
			
			StringBuffer _xml = new StringBuffer();
			List _infoList = null;
			if(totalCount > 0) {
				_infoList = _processor.getTotalChangelist(_doing);
				
				if (_infoList != null && _infoList.size() > 0) {
					_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
					Iterator _iter = _infoList.iterator();

					_doXML.setDO(_doing);
					_xml.append(_doXML.getSubXML3());

					while (_iter.hasNext()) {
						ProgramInfoDOXML _do = new ProgramInfoDOXML();
						_do.setDO(_iter.next());
						_xml.append(_do.getSubXML());
						break;
					}
					_xml.append("</das>");
				}
			}
			
			System.out.println(_xml.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
