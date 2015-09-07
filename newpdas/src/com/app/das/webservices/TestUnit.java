package com.app.das.webservices;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;

import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.exception.DASException;
import com.app.das.util.CommonUtl;
import com.app.das.util.jutil;
import com.konantech.search.util.DCConfig;
import com.sbs.tm.service.TansferPortTypeProxy;
/**
 * PDASServices WebService 
 * test용 유닛
 */
public class TestUnit {


	public static void main(String[] args) {
		try {
			testMethod();
		} catch (DASException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
//		jutil ju = new jutil();
		
//		ju.makeFile("teststet", "C:\\12\\"+"test");
//		ju.renameFile(filename+".xml_tmp", filename+".xml");
		
		String xml ="&lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;&lt;Request&gt; &lt;AddTask Type=&quot;Transfer&quot; Action=&quot;queue&quot;&gt;&lt;Source&gt;&lt;file&gt;Y:/restore/S911911/7953/D20110503V00013.mxf&lt;/file&gt;&lt;file&gt;Y:/restore/S911911/7953/D20110503V00013.xml&lt;/file&gt;&lt;/Source&gt;&lt;Target Destination=&quot;PM-CU&quot;&gt;&lt;file&gt;박정근님0503-2_D20110503V00013_HR.mxf&lt;/file&gt;&lt;file&gt;박정근님0503-2_D20110503V00013_HR.xml&lt;/file&gt;&lt;/Target&gt;&lt;/AddTask&gt;&lt;/Request&gt;";
//		String xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?><Request><AddTask Type=\"Transfer\" Action=\"queue\"><Source><file>V:/restore/S911911/7692/P20110404V00002_20110404163944_HR.MXF</file><file>V:/restore/S911911/7692/P20110404V00002_20110404163944_HR.xml</file></Source><Target Destination=\"CM\"><file>캬캬계열사_040411_D20110404V00010_HR.mxf</file><file>캬캬계열사_040411_D20110404V00010_HR.xml</file></Target></AddTask></Request>";
//		String xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?><das><info></info></das>";
//		String xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?><das><info><das_eq_id>4</das_eq_id><das_eq_ps_cd>005</das_eq_ps_cd><copy_to_group>Y</copy_to_group><out_system_id>DAS1000064</out_system_id><regrid>S911911</regrid></info></das>";
//		String xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?><Request></Request>";
//		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><das><employeeinfo><type>001</type> <per_reg_no>             </per_reg_no><sbs_user_id>U539048</sbs_user_id><user_nm>신테스트</user_nm><dept_nm></dept_nm><dept_cd>UAAC01</dept_cd><vlddt_bgn>        </vlddt_bgn><vlddt_end>        </vlddt_end><position>77</position><approve_yn>N</approve_yn><password>lee2322</password><newpassword></newpassword><role_cd>004</role_cd><mobile></mobile><pgm_nm></pgm_nm><employee_yn> </employee_yn><employee_type>001</employee_type><approve_status>1</approve_status><cocd>U</cocd><user_num>539048</user_num><acct_code>RA</acct_code><delete_yn>N</delete_yn><pgm_id>0</pgm_id><reg_dt></reg_dt><reg_id></reg_id><out_user_nm></out_user_nm><role_nm></role_nm><update_yn>N</update_yn><reg_nm></reg_nm><mod_nm></mod_nm><pds_pgm_id>        </pds_pgm_id> <success_yn>200</success_yn></employeeinfo></das>";
		//		NevigatorProxy port = new NevigatorProxy();
//		int paReseult =0;
////		com.sbs.pa.service.Service1SoapProxy port2 = new com.sbs.pa.service.Service1SoapProxy();
//		com.sbs.tm.service.TansferPortTypeProxy port = new com.sbs.tm.service.TansferPortTypeProxy();
//		try {
////			paReseult =  port2.wsPAUserInfoManager(xml);
//			String rXml = port.addTask(xml);
//			System.out.println("rXml["+rXml+"]");
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		//W}
		
			}
	public static void testMethod()throws DASException{
		
		try {
//			System.out.println(new SimpleDateFormat("yyyyMMdd HHmm").format(new Date()));
//			CommonUtl.getLongDate(new SimpleDateFormat("yyyyMMdd HHmm").format(new Date()));
			System.out.println(CommonUtl.getSomeDateTime(null,0,0,0,36));
			System.out.println(CommonUtl.getDate("yyyyMMddHHmm"));
			
			System.out.println(System.getProperty("os.arch"));
			
			System.out.println(    DCConfig.getProperty("DAEMON_SERVICE_IP"));
			if(System.getProperty("os.arch").equals("x86")){
			}else if(System.getProperty("os.arch").equals("amd64")){
			}else{
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
