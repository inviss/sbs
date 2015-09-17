package test;

import java.util.List;

import com.app.das.business.EmployeeRoleBusinessProcessor;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.services.EmployeeInfoDOXML;

public class EmployeeListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();

			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><employeeinfo><sbs_user_id>SU05150</sbs_user_id><user_nm></user_nm><employee_type>001,003</employee_type><cocd></cocd><dept_nm></dept_nm><dept_cd></dept_cd><acct_code></acct_code><approve_status>1</approve_status></employeeinfo></das>");


			List _infoList = _processor.getEmployeeRoleList(_do);
			System.out.println(_infoList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
