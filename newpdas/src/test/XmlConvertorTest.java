package test;

import com.app.das.util.CommonUtl;

public class XmlConvertorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			/*
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			String xml =_processor.getSceanInfo(323077); //308578, 108014, 323034,323823,322859
			System.out.println(xml);
			*/
			/*
			String input = "< \'\"#^%$%^$&%*^_(&(-**()*!!>><<그랜드,, \r\n34234234DSFDSFSDFSDsdfsdf" +
					"はんなり明朝惑世誣民龥슬[]램!!을 향하여, 드디:어 출;발!! > 궂은~~ 날씨 탓에.. /";
			// 가-힣 or 0-9 or a-z or A-Z or , or ! or _ or - or [] or ()
			//String match = "[^\\u007E\\u002A\\u0024\\u0025\\u002E\\u003F\\u0023\\u003B\\u002F\\u0026\\u0022\\u0027\\u003C\\u003E\\r\\n\\u003A\\u005F\\u002D\\u005B\\u005D\\uAC00-\\uD7A30-9a-zA-Z\\u002C\\u0021\\u0028-\\u0029\\s]";
			String match = "[^u3400-\\u4DBF\\u4E00-\\u9FBF\\uF900-\\uFAFF\\u3040-\\u31FF\\u007E\\u002A\\u0024\\u0025\\u002E\\u003F\\u0023\\u003B\\u002F\\u0026\\u0022\\u0027\\u003C\\u003E\\r\\n\\u003A\\u005F\\u002D\\u005B\\u005D\\uAC00-\\uD7A30-9a-zA-Z\\u002C\\u0021\\u0028-\\u0029\\s]";
		    System.out.println(input.replaceAll(match, " "));
		    
			*/
			String timecode = "01:21:23:19";
			System.out.println(CommonUtl.changeTime(timecode));
			//System.out.println(CommonUtl.changeDuration(146362));
			
			/*
			String orgPath = "X:\\SBS\\manual\\201510\\14\\";
			String orgName = "P20151014V00064_20151014114700_HR.mxf";
			orgPath = orgPath.replaceAll("X:", "arcreq").replace('\\', '/');
			System.out.println(orgPath);
			*/
			/*
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		    System.out.println(sdf.format(new Date(0))); //1970-01-01-00:00:00
		    */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
