package com.sbs.das.sample.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.UserInfoTbl;
import com.sbs.das.sample.BaseConfig;
import com.sbs.das.services.UserManagerService;

public class NonEmployeesAddAll extends BaseConfig {

	@Autowired
	private UserManagerService userManagerService;
	
	@Test
	public void insertAllEmployee() {
		Workbook w;
		
		List<UserInfoTbl> userList = new ArrayList<UserInfoTbl>();
		try {
			File inputWorkbook = new File("D:\\tmp\\비직원 사용자 리스트_20130201(IT관리실체크).xls");
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet[] sheets = w.getSheets();
			for(int m=0; m < sheets.length; m++) {
				Sheet sheet = sheets[m];
				UserInfoTbl userInfoTbl = null;
				for (int j = 0; j < sheet.getRows(); j++) {
					userInfoTbl = new UserInfoTbl();
					userInfoTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					
					for (int i = 0; i < sheet.getColumns(); i++) {
						Cell cell = sheet.getCell(i, j);
						switch((i+1)) {
						case 1:
							userInfoTbl.setSbsUserId(cell.getContents());  // SBS ID
							break;
						case 2:
							userInfoTbl.setUserNm(StringUtils.defaultIfEmpty(cell.getContents(), "")); // 사용자명
							break;
						case 3:
							break;
						case 4:
							break;
						case 5:
							break;
						case 6:
							break;
						case 7:
							userInfoTbl.setMobile(cell.getContents());	// 전화번호
							userInfoTbl.setPerRegNo(cell.getContents());
							
							if(StringUtils.isBlank(cell.getContents()))
								userInfoTbl.setDeleteYn("Y");
							else
								userInfoTbl.setDeleteYn("N");
							break;
						}
					}
					userList.add(userInfoTbl);
				}
			}
			
			userManagerService.updateUser(userList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
