package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.app.das.util.StringUtils;

public class UtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
			Date date = formatter.parse("20160509");
			
			String msg = StringUtils.right("p", 2, "0");
			System.out.println(msg);
			byte[] pids = msg.getBytes();
			
			int days = Integer.parseInt(new String(pids, 1, pids.length-1));
			
			Calendar calendar = Calendar.getInstance();
//			calendar.set(Calendar.YEAR,  9999);
//			calendar.set(Calendar.MONTH,  Calendar.DECEMBER);
//			calendar.set(Calendar.DATE,  31);
//			calendar.set(Calendar.HOUR_OF_DAY,  23);
//			calendar.set(Calendar.MINUTE,  59);
//			calendar.set(Calendar.SECOND,  59);
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, days);
			
			System.out.println(formatter.format(calendar.getTime()));		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
