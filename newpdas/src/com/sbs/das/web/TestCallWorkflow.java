package com.sbs.das.web;

public class TestCallWorkflow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			archiveService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String archiveService() throws Exception{
		
		System.out.println("call start");
		String IN_inMessage ="<?xml version=\"1.0\" encoding=\"utf-8\"?><das><info><das_eq_id>4</das_eq_id><das_eq_ps_cd>005</das_eq_ps_cd><cti_id>1000034</cti_id><copy_to_group>N</copy_to_group><file_path>arcreq/201103/17//arctest6_P20110317V00600_HR.mxf</file_path><req_id>requester</req_id><regrid>register</regrid></info></das>";
								/*"<das>"+
								"<info>"+		
								"<das_eq_id>4</das_eq_id>"+
								"<das_eq_ps_cd>005</das_eq_ps_cd>"+
								"<cti_id>0</cti_id>"+
								"<priority>3</priority>"+
								"<sgl_group_nm>m2_%25</sgl_group_nm>"+
								"<job_id>005</job_id>"+
								"<som>0</som>"+
								"<eom>0</eom>"+
								"<file_path></file_path>"+ 
								"<req_id>requester</req_id>"+
								"<regrid>register</regrid>"+
								"</info>"+
								"</das>";*/
		
//		String IN_inMessage ="<?xml version=\"1.0\" encoding=\"utf-8\"?><das><info><cti_id>11111</cti_id></info></das>";
		try {
//			ServiceNevigatorService nevigatorService = new ServiceNevigatorServiceLocator();
//			 Nevigator nevigator = nevigatorService.getServiceNevigatorPort();
//			 String sResult = nevigator.archiveService(IN_inMessage);
			 
			 NevigatorProxy sService = new NevigatorProxy();
			 String sResult = sService.archiveService(IN_inMessage);
			 
			System.out.println("sResult:"+sResult);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("call End");
		
		return "";
	}
	
	private String downloadService()throws Exception{
//		String IN_inMessage ="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
		
		return "";
	}

}
