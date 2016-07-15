package test;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class UtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			/*
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
			*/
			List<MyCatalogDO> list = new ArrayList<MyCatalogDO>();
			
			MyCatalogDO item = new MyCatalogDO();
			item.setCont("1995년 9월 백두산, 천지 표석, 천지연 전경 <줄거리 1 - 5부>");
			list.add(item);
			
			item = new MyCatalogDO();
			item.setCont("1995년 10월 백두산, 천지 표석, 천지연 전경 &lt;줄거리 1 - 5부&gt;");
			list.add(item);
			
			XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("-", "_")));
			String xml = XmlUtil.getToXmlXstream(list);
			System.out.println(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
