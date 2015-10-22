package test;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.DownCartDO;
import com.app.das.services.DownCartDOXML;

public class PartialDownloadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DownCartDOXML _doXML = new DownCartDOXML();
			DownCartDO _do = (DownCartDO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><downCart><DOWN_SUBJ>Ǆ¼£Ɨ½ºƮ2</DOWN_SUBJ><nodecaption>PDSƗ½ºƮ</nodecaption><nodeid>3467</nodeid><cellname>PM-CU</cellname><DOWN_GUBUN>001</DOWN_GUBUN><CART_NO>287781</CART_NO><USER_ID>SUPERIT</USER_ID><physicaltree>3368</physicaltree><logicaltree></logicaltree><co_cd>S</co_cd><chennel>A</chennel></downCart></das>");
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			_processor.updateDownCart(_do);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
