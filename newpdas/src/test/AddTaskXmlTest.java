package test;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.TransferDO;
import com.app.das.services.TransferDOXML;

public class AddTaskXmlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int num = 606157;
		
		try {
			long sTime1 = System.currentTimeMillis();
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			String getMessage =  _processor.DoAddTask(num); //606157
			long sTime2 = System.currentTimeMillis();
			System.out.println("regist job transfer tm : "+ (sTime2-sTime1)/1000.0 );
			System.out.println(getMessage);
			
			TransferDO _do2 = _processor.getCartInfo(num);

			_processor.compledownprocess(_do2.getCart_no(),_do2.getCart_seq());

			TransferDOXML _doXML = new TransferDOXML();
			TransferDO _do = (TransferDO) _doXML.setDO(getMessage);
			_do.setCart_no(_do2.getCart_no());
			_do.setCart_seq(_do2.getCart_seq());

			_processor.insertAddTask(_do);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
