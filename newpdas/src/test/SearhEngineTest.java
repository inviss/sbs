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
		
		String[] aa = {"205278",
				"218924",
				"218925",
				"218926",
				"219759",
				"219775",
				"219779",
				"219786",
				"219787",
				"219788",
				"219799",
				"219800",
				"219801",
				"219802",
				"219803",
				"219804",
				"226397",
				"240783",
				"229282",
				"229285",
				"229296",
				"229363",
				"229366",
				"229380",
				"229427",
				"229444",
				"229463",
				"229570",
				"229723",
				"230071",
				"230072",
				"230194",
				"230229",
				"230277",
				"230279",
				"230281",
				"230349",
				"230376",
				"231396",
				"231540",
				"231708",
				"231736",
				"231738",
				"231744",
				"231776",
				"240784",
				"240785",
				"240786",
				"240787",
				"240788",
				"240789",
				"240790",
				"240791",
				"240792",
				"240793",
				"240794",
				"240795",
				"240088",
				"240796",
				"240797",
				"240777",
				"240778",
				"240779",
				"240780",
				"240781",
				"240782",
				"240798",
				"240799",
				"240800",
				"240802",
				"240803",
				"241283",
				"250463",
				"250464",
				"250465",
				"250466",
				"250467",
				"250477",
				"250792",
				"323355",
				"323375",
				"323684"
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
