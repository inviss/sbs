package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.DisuseBusinessProcessor;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.services.DiscardInfoDOXML;

public class DiscardInfoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DisuseBusinessProcessor _processor = new DisuseBusinessProcessor();
			DiscardInfoDOXML _doXML = new DiscardInfoDOXML();

			DiscardDO _do = (DiscardDO)_doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><discardinfo><start_brd_dd>20150123</start_brd_dd><end_brd_dd>20150130</end_brd_dd><rsv_prd_cd></rsv_prd_cd><media_id></media_id><master_id></master_id><ctgr_l_cd></ctgr_l_cd><from_use>0</from_use><to_use>0</to_use><title></title><page>7</page><cocd>S</cocd><chennel>A</chennel><archive_path>D,O,P</archive_path></discardinfo></das>");


			List _infoList = _processor.getDisCardList(_do);
			List _infoList2 = _processor.getSumDiscard(_do);


			StringBuffer _xml = new StringBuffer();

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			if (_infoList2 != null && _infoList2.size() > 0) {
				Iterator _iter2 = _infoList2.iterator();
				while (_iter2.hasNext()) {
					DiscardInfoDOXML _do2 = new DiscardInfoDOXML();
					_do2.setDO(_iter2.next());
					_xml.append(_do2.getSubXML2());
				}
			}

			_xml.append("<discard>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DiscardInfoDOXML _do2 = new DiscardInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}
			}
			_xml.append("</discard>");
			 
			_xml.append("</das>");
		 
			System.out.println(_xml.toString());

		} catch (Exception e) {
		 e.printStackTrace();
		}
	}

}
