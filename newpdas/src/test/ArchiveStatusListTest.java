package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.services.ArchiveInfoDOXML;

public class ArchiveStatusListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		ArchiveInfoDOXML _DOXML = new ArchiveInfoDOXML();
		try {
			ArchiveInfoDO _DO = (ArchiveInfoDO)_DOXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><archiveinfo><archive_path>D,O,P</archive_path><title></title><req_nm></req_nm><whatdd>REG</whatdd><start_dd>20151110</start_dd><end_dd>20151117</end_dd><cocd>S</cocd><chennel>A</chennel></archiveinfo></das>");

			List _infoList = _processor.getArchiveStatusList(_DO);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ArchiveInfoDOXML _do2 = new ArchiveInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}
				_xml.append("</das>");

				System.out.println(_xml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
