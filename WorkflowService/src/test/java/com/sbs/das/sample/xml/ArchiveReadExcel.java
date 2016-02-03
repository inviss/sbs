package com.sbs.das.sample.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.sbs.das.dto.xml.ArchiveRequest;
import com.sbs.das.sample.BaseConfig;

public class ArchiveReadExcel extends BaseConfig {


	//@Ignore
	@Test
	public void testExcelRead(){
		Workbook w;
		try {
			File inputWorkbook = new File("D:/archive_check.xls");

			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet[] sheets = w.getSheets();

			Map<String, ArchiveRequest> archiveList = new HashMap<String, ArchiveRequest>();
			
			for(int m=0; m < sheets.length; m++) {

				Sheet sheet = sheets[m];
				// 두번째 탭의 obj_name으로 첫번째 탭과 비교하여 빠진 항목이 있는지 비교
				if(m == 1) {
					for (int j = 0; j < sheet.getRows(); j++) {
						if(j==0) continue;
						
						Cell cell = sheet.getCell(0, j);
						String objName = cell.getContents();
						/* 빠진 항목 출력
						if(!archiveList.containsKey(objName))
							System.out.println(objName);
						*/
					}
					
					/*
					 * 아카이브 상태 전달 XML 생성
					 */
					Iterator<String> iterator = archiveList.keySet().iterator();
					
					int count = 1;
					StringBuffer xml = new StringBuffer();
					while(iterator.hasNext()) {
						String id = iterator.next();
						ArchiveRequest request3 = archiveList.get(id);
						
						String m2 = StringUtils.defaultIfBlank(request3.getM2Yn(), "N");
						if(m2.equals("error")) m2 = "N";
						else if(m2.equals("complete")) m2 = "Y";
						else m2 = "N";
						
						String m4 = StringUtils.defaultIfBlank(request3.getM4Yn(), "N");
						if(m4.equals("error")) m4 = "N";
						else if(m4.equals("complete")) m4 = "Y";
						else m4 = "N";
						
						if(count == 1 || count%10 == 0) {
							xml.setLength(0);
							xml.append("<das>");
						}
						
						if(m2.equals("Y"))
							xml.append("<status><process_cd>MA</process_cd><job_id>005</job_id><job_status>C</job_status><object_name>"+request3.getObjectName()+"</object_name><updt_dtm>20160203011309</updt_dtm><progress>100</progress><restore_id></restore_id><eq_id>10</eq_id></status>");
						
						if(count%10 == 9 || archiveList.size() == count) {
							xml.append("</das>");
							System.out.println(xml.toString());
						}
						
						count++;
					}
				} else {
					ArchiveRequest request = null;
					for (int j = 0; j < sheet.getRows(); j++) {
						if(j==0) continue;

						request = new ArchiveRequest();
						for (int i = 0; i < sheet.getColumns(); i++) {
							Cell cell = sheet.getCell(i, j);

							switch((i+1)) {
							case 1:
								request.setObjectName(cell.getContents());   	// ObjName
								break;
							case 3:
								request.setGroup(cell.getContents());			// Archive_Group
								break;
							case 4:
								if(request.getGroup().indexOf("copy") > -1)
									request.setM4Yn(cell.getContents());			// State
								else
									request.setM2Yn(cell.getContents());			// State
								break;
							}
						}

						if(archiveList.containsKey(request.getObjectName())) {
							ArchiveRequest request2 = archiveList.get(request.getObjectName());
							if(StringUtils.isBlank(request.getM2Yn()))
								request2.setM4Yn(request.getM4Yn());
							else
								request2.setM2Yn(request.getM2Yn());

							archiveList.put(request.getObjectName(), request2);
						} else {
							archiveList.put(request.getObjectName(), request);
						}


						request = null;
					}
				}

			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
