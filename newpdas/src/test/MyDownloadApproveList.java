package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.WorkBusinessProcessor;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.CartItemDO;
import com.app.das.services.CartItemDOXML;

public class MyDownloadApproveList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WorkBusinessProcessor _processor = new WorkBusinessProcessor();
		CartItemDOXML _doXML = new CartItemDOXML();
		try {
			CartItemDO _do = (CartItemDO)_doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><cartItems><DOWN_SUBJ></DOWN_SUBJ><FROMDATE>20151221</FROMDATE><ENDDATE>20151228</ENDDATE><USERID>G15G009</USERID><system>ifcms</system></cartItems></das>");

			List _infolist = _processor.getMyDownloadAprroveList(_do);
			System.out.println("retur size: "+_infolist.size());
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					CartItemDOXML _doing = new CartItemDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//		logger.debug("[getMyDownloadAprroveList][ouput]"+_xml);
				//System.out.println(_xml);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
