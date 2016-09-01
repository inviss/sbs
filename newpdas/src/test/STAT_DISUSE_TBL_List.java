package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.StatisticsBusinessProcessor;
import com.app.das.business.transfer.StatisticsConditionDO;
import com.app.das.services.StatisticsInfoDOXML;

public class STAT_DISUSE_TBL_List {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><statisticsInfo><DATEKIND>3</DATEKIND><FROMDATE>20160701</FROMDATE><TODATE>20160731</TODATE><COCD>S</COCD><CHENNEL>A</CHENNEL></statisticsInfo></das>");
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_DISUSE_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_DISUSE_TBL_List][ouput]"+_xml);
				System.out.println(_xml.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
