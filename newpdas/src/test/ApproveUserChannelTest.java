package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.ApproveInfoDO;
import com.app.das.services.ApproveInfoDOXML;

public class ApproveUserChannelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ApproveInfoDOXML _doXML = new ApproveInfoDOXML();
		try {
			ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><approveinfo><cocd>E</cocd></approveinfo></das>");


			List _infoList = _processor.getApproveInfoForChennel(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ApproveInfoDOXML _do2 = new ApproveInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
