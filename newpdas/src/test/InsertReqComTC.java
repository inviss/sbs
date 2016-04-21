package test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.services.TcBeanDOXML;

public class InsertReqComTC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String xml = FileUtils.readFileToString(new File("D:/insertREQTC.xml"), "utf-8");
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			TcBeanDOXML _doXML = new TcBeanDOXML();
			TcBeanDO _do = (TcBeanDO)_doXML.setDO(xml);

			StringBuffer _xml = new StringBuffer();
			TcBeanDO do2 = _processor.insertReqJobTC(_do); //updateReqComTc(_do);

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" );
			_xml.append("<das> \n" );
			TcBeanDOXML _doing = new TcBeanDOXML();
			if(do2==null){
				_doing.setDO(_do);
				_do.setResult("F");
			}else{
				_doing.setDO(do2);
				do2.setResult("S");
			}

			_xml.append( _doing.getSubXML2());
			_xml.append("</das> \n" );

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
