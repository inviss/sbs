package test;

import com.app.das.business.LoginBusinessProcessor;
import com.app.das.business.transfer.TokenDO;
import com.app.das.services.TokenDOXML;

public class IsValidUserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TokenDOXML _doXML = new TokenDOXML();
			TokenDO _do = (TokenDO)_doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><tokeninfo><hex>00:90:F5:D2:9B:B8</hex><user_id>S522522</user_id><password>522522</password></tokeninfo></das>");

			LoginBusinessProcessor _processor1 = new LoginBusinessProcessor();

			TokenDO _tdo = _processor1.isValidUser(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?> <das>\n" );
			if(_tdo != null){
				TokenDOXML _doing = new TokenDOXML();
				_doing.setDO(_tdo);
				_xml.append( _doing.getSubXML());
				//	logger.debug(_xml.toString());
				_xml.append("</das> \n" );
				System.out.println(_xml.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
