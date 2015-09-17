package test;

import com.app.das.business.SearchBusinessProcessor;
import com.app.das.services.SearchInfoDOXML;
import com.konantech.search.data.ParameterVO;

public class SearhEngineTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SearchInfoDOXML _doXML = new SearchInfoDOXML();
		ParameterVO _do = (ParameterVO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><searchInfo><kwd></kwd><scn>program</scn><preKwds></preKwds><srchFd></srchFd><userId>S522522</userId><reSrchFlag>false</reSrchFlag><pageSize>100</pageSize><pageNum>1</pageNum><sort>D</sort><sortcolumn>DAY</sortcolumn><detailSearch>true</detailSearch><grpKwd>318247</grpKwd><grpSrchFd>MASTER_ID</grpSrchFd><grpAndOr>AND</grpAndOr><grpstartdd></grpstartdd><grpenddd></grpenddd><date>false</date><startDate></startDate><endDate></endDate><sceanGubun></sceanGubun><ctgr_l_cd>100</ctgr_l_cd></searchInfo></das>");
		SearchBusinessProcessor _processor = new SearchBusinessProcessor();

		try {
			String sResult = _processor.getSearchText(_do);
			System.out.println(sResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
