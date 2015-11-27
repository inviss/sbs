package test;

import java.util.Iterator;
import java.util.List;

import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.UseInfoDO;
import com.app.das.services.UseInfoDOXML;

public class GetUseInfoListTest {
	
	public static void main(String[] args) {
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		UseInfoDOXML _DOXML = new UseInfoDOXML();
		/** 2015.11.19 생성 */
		try {
			UseInfoDO _DO = (UseInfoDO) _DOXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><useinfo><start_reg_dt>20151010</start_reg_dt><end_reg_dt>20151017</end_reg_dt><use_start_count></use_start_count><use_end_count></use_end_count><pgm_nm></pgm_nm><coulmnname></coulmnname><scl_cd></scl_cd><ctgr_l_cd></ctgr_l_cd><page>1</page><cocd>S</cocd><chennel>A</chennel></useinfo></das>");

			UseInfoDO useInfo = _processor.getUseInfoCount(_DO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT);
			
			StringBuffer _xml = new StringBuffer();
			
			// 조회 건수가 존재하면 리스트 조회
			if(useInfo.getTotal() > 0) {
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				
				UseInfoDOXML infoXML = new UseInfoDOXML();
				
				// total 건수와 전체 duration 길이를 xml에 append 함.
				infoXML.setDO(useInfo);
				_xml.append(infoXML.getSubXML2());
				
				// 리스트를 조회.
				List _infoList = _processor.getNewUseInfoList(_DO, DASBusinessConstants.PageQueryFlag.NORMAL);
				int size = _infoList.size();
				
				infoXML = new UseInfoDOXML();
				for(int i=0; i<size; i++) {
					infoXML.setDO(_infoList.get(i));

					_xml.append(infoXML.getSubXML());
				}
				
				_xml.append("</das>");
			}
			System.out.println(_xml.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/** 기존 소스
		try {
			UseInfoDO _DO = (UseInfoDO) _DOXML.setDO("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><useinfo><start_reg_dt>20151010</start_reg_dt><end_reg_dt>20151017</end_reg_dt><use_start_count></use_start_count><use_end_count></use_end_count><pgm_nm></pgm_nm><coulmnname></coulmnname><scl_cd></scl_cd><ctgr_l_cd></ctgr_l_cd><page>1</page><cocd>S</cocd><chennel>A</chennel></useinfo></das>");


			List _infoList = _processor.getUseInfoList(_DO);
			List _infoList2 = _processor.getUseInfoList2(_DO);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				Iterator _iter2 = _infoList2.iterator();
				UseInfoDOXML _do3 = new UseInfoDOXML();
				_do3.setDO(_iter2.next());
				_xml.append(_do3.getSubXML2());

				while (_iter.hasNext()) {
					UseInfoDOXML _do2 = new UseInfoDOXML();
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
}
