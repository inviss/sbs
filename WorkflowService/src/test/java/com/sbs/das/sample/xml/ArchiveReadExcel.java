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

			for(int m=0; m < sheets.length; m++) {
				Sheet sheet = sheets[m];
				
				Map<String, ArchiveRequest> archiveList = new HashMap<String, ArchiveRequest>();
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
					
					
					System.out.println(request.getObjectName()+", m2: "+request.getM2Yn()+", m4: "+request.getM4Yn());
					
					request = null;
					
					//if(j == 4) break;
				}
				
				Iterator<String> iterator = archiveList.keySet().iterator();
				while(iterator.hasNext()) {
					String id = iterator.next();
					ArchiveRequest request3 = archiveList.get(id);
					
					System.out.println(request3.getObjectName()+", m2: "+request3.getM2Yn()+", m4: "+request3.getM4Yn());
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
