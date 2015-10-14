package test;

import com.app.das.business.ExternalBusinessProcessor;

public class AddTmServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			_processor.DoAddTask(573761);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
