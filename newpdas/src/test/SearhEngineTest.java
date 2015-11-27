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
		
		String[] aa = {"296262",
				"290107",
				"283331",
				"279727",
				"257653",
				"248778",
				"246463",
				"240276",
				"239676",
				"234515",
				"232922",
				"229590",
				"17221",
				"6299",
				"295474",
				"290894",
				"67655",
				"19544",
				"18820",
				"13729",
				"2047"};
		
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
