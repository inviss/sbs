package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.services.ProgramInfoDOXML;

public class GetSearchRelationInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProgramInfoDOXML _doXML = new ProgramInfoDOXML();
		ProgramInfoDO _doing = (ProgramInfoDO)_doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><programInfo><PGM_NM>인기가요</PGM_NM><BRD_BGN_DD>20160712</BRD_BGN_DD><BRD_END_DD>20160719</BRD_END_DD></programInfo></das>");
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getSearchRelationInfoList(_doing);
			System.out.println(_infoList.size());
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append( _do.getSubXML());
				}
				_xml.append("</das>");
				System.out.println(_xml.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
