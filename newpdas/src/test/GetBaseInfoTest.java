package test;

import com.app.das.business.ExternalBusinessProcessor;

public class GetBaseInfoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			String xml = _processor.getBaseInfo(60981);
		 
			System.out.println(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
