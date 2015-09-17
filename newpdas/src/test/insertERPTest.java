package test;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.app.das.business.EmployeeRoleBusinessProcessor;
import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.services.ErpUserInfoDOXML;

public class insertERPTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ErpUserInfoDOXML _doXML = new ErpUserInfoDOXML();
		
		String xml = "";
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			xml = _processor.getOrderInfo();
			System.out.println("retVal: "+xml);
			if(StringUtils.isNotBlank(xml)) {
				List  _do = (List) _doXML.setDO(xml);		
				System.out.println(_do);
				//EmployeeRoleBusinessProcessor _processor2 = new EmployeeRoleBusinessProcessor();
	
				//_processor2.insertERPUserInfo(_do);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
