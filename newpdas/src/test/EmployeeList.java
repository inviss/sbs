package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.EmployeeRoleBusinessProcessor;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.services.EmployeeInfoDOXML;

public class EmployeeList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();

			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><employeeinfo><sbs_user_id>15G008</sbs_user_id><user_nm></user_nm><employee_type></employee_type><cocd>G</cocd><dept_nm></dept_nm><dept_cd></dept_cd><acct_code></acct_code><approve_status>2</approve_status></employeeinfo></das>");


			List _infoList = _processor.getEmployeeRoleList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				System.out.println(_xml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
