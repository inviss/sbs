package test;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.services.TcBeanDOXML;

public class UpdateTcStatusTest {

	public static void main(String[] args) {
		try {
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			TcBeanDOXML _doXML = new TcBeanDOXML();
			TcBeanDO _do = (TcBeanDO)_doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><tcProcessinginfo><SEQ>10</SEQ><TC_ID>TC5_2</TC_ID><TC_NM>192.168.0.198</TC_NM><CT_ID>572835</CT_ID><CH_SEQ>2</CH_SEQ><WORK_STAT>F</WORK_STAT><PROGRESS>100</PROGRESS><JOB_STATUS>5</JOB_STATUS><TC_SEQ>120463</TC_SEQ><REQ_CD>LRCT</REQ_CD><COCD>M</COCD></tcProcessinginfo></das>");



			boolean result = _processor.updateTCState(_do);
			System.out.println("resutl: "+result);
			//return Boolean.toString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
