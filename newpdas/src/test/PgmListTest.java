package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.services.ProgramInfoDOXML;

public class PgmListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			ProgramInfoDOXML _doXML = new ProgramInfoDOXML();

			ProgramInfoDO _do = (ProgramInfoDO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><programInfo><SRCH_TYPE>2</SRCH_TYPE><PGM_NM>TT005</PGM_NM><PGM_CD>TT005</PGM_CD><PARENTS_CD>TT005</PARENTS_CD><USE_YN></USE_YN></programInfo></das>");


			List _infoList = _processor.getPgmList(_do);
			
			StringBuffer _xml = new StringBuffer();
			if (_infoList != null && _infoList.size() > 0) {
				
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {

					ProgramInfoDOXML _do2 = new ProgramInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());

				}

				_xml.append("</das>");
			}
			//System.out.println("result: "+_xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
