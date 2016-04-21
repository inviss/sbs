package test;

import com.app.das.business.ExternalBusinessProcessor;

public class RecreateWMVTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			long tmp = 443838L;
			
			System.out.println(_processor.recreateWMVForClient(tmp, "S522522"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
