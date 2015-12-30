package test;

import java.util.List;

import com.app.das.business.CommunityBusinessProcessor;
import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.services.BoardInfoDOXML;
import com.app.das.services.GoodMediaDOXML;
import com.app.das.services.TodayDOXML;

public class MainListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		GoodMediaDOXML _DOXML = new GoodMediaDOXML();
		TodayDOXML _DOXML2 = new TodayDOXML();	
		CommunityBusinessProcessor _processor2 = new CommunityBusinessProcessor();	
		BoardInfoDOXML _doXML3 = new BoardInfoDOXML();
		StringBuffer _xml = new StringBuffer();
		/*try {
			List _infoList = _processor.getGoodMediaList();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
			List _infoList2 = _processor.getTodayList();
			System.out.println(_infoList2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			List _infoList3 = _processor2.getMainBoardList();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
