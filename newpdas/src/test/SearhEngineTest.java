package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.app.das.business.SearchBusinessProcessor;
import com.app.das.services.SearchInfoDOXML;
import com.konantech.search.data.ParameterVO;

public class SearhEngineTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SearchInfoDOXML _doXML = new SearchInfoDOXML();
		ParameterVO _do = (ParameterVO) _doXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><searchInfo><kwd></kwd><scn>program</scn><preKwds></preKwds><srchFd></srchFd><userId>S522522</userId><reSrchFlag>false</reSrchFlag><pageSize>100</pageSize><pageNum>1</pageNum><sort>D</sort><sortcolumn>DAY</sortcolumn><detailSearch>true</detailSearch><grpKwd>219802</grpKwd><grpSrchFd>MASTER_ID</grpSrchFd><grpAndOr>AND</grpAndOr><grpstartdd></grpstartdd><grpenddd></grpenddd><date>false</date><startDate></startDate><endDate></endDate><sceanGubun></sceanGubun><ctgr_l_cd></ctgr_l_cd></searchInfo></das>");
		SearchBusinessProcessor _processor = new SearchBusinessProcessor();
		
		String[] aa = {"253364",
				"271923",
				"271909",
				"271914",
				"271915",
				"271920",
				"288359",
				"288393",
				"288394",
				"288392",
				"288395",
				"288410",
				"288408",
				"288406",
				"288414",
				"288796",
				"288793",
				"288427",
				"288425",
				"288430",
				"288792",
				"288417",
				"288426",
				"289383",
				"288883",
				"288880",
				"288800",
				"289388",
				"288884",
				"289384",
				"288879",
				"297010",
				"297102",
				"297210",
				"297175",
				"297200",
				"297207",
				"297256"
};
		
		String[] bb = {"219802"};
		List<String> blank = new ArrayList<String>();
		try {
			for(String a : aa) {
				_do.setGrpKwd(new String[]{a});
				String sResult = _processor.getSearchText(_do);
				System.out.println(sResult);
				if(StringUtils.isNotBlank(sResult)) {
					blank.add(a);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("size: "+blank.size()+", ids: "+blank.toString());
	}

}
