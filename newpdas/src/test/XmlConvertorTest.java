package test;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.util.CommonUtl;
import com.konantech.search.util.CommonUtil;

public class XmlConvertorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			/*
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			String xml =_processor.getSceanInfo(308578); //308578, 108014, 323034
			System.out.println(xml);
			*/
			/*
			String input = "< #^%$%^$&%*^_(&(-**()*!!>><<그랜드,, \r\n" +
					"슬[]램!!을 향하여, 드디어 출발!! > 궂은 날씨 탓에";
			// 가-힣 or 0-9 or a-z or A-Z or , or ! or _ or - or [] or ()
			String match = "[^\\r\\n\\u005F\\u002D\\u005B\\u005D\\uAC00-\\uD7A30-9a-zA-Z\\u002C\\u0021\\u0028-\\u0029\\s]";
		    System.out.println(input.replaceAll(match, " "));
		    */
			
			String timecode = "00:00:17:01";
			System.out.println(CommonUtl.changeTime(timecode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
