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

import com.sbs.das.dto.MediaTapeInfoTbl;
import com.sbs.das.sample.BaseConfig;
import com.sbs.das.services.AddClipForTapeService;

public class ReadExcel extends BaseConfig {

	@Autowired
	private AddClipForTapeService addClipForTapeService;

	//@Ignore
	@Test
	public void testExcelRead(){
		Workbook w;
		try {
			//File inputWorkbook = new File("D:\\tmp\\nick.xls");
			File inputWorkbook = new File("D:\\tmp\\sbs_sports_2.xls");

			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet[] sheets = w.getSheets();

			List<MediaTapeInfoTbl> mediaTapeInfoTbls = new ArrayList<MediaTapeInfoTbl>();

			for(int m=0; m < sheets.length; m++) {
				Sheet sheet = sheets[m];
				for (int j = 0; j < sheet.getRows(); j++) {
					if(j==0) continue;
					
					MediaTapeInfoTbl mediaTapeInfoTbl = new MediaTapeInfoTbl();
					for (int i = 0; i < sheet.getColumns(); i++) {
						//if(j == 2) {
						Cell cell = sheet.getCell(i, j);

						switch((i+1)) {
						case 1:
							mediaTapeInfoTbl.setChennelCd(StringUtils.defaultIfEmpty(cell.getContents(), "M"));   // 채널코드
							break;
						case 2:
							mediaTapeInfoTbl.setCtgrLCd(cell.getContents());									  	// 대분류(자료구분)
							break;
						case 3:
							mediaTapeInfoTbl.setTapeMediaClfCd(cell.getContents());							  	// 테이프종류
							break;
						case 4:
							mediaTapeInfoTbl.setReqCd(cell.getContents());									  	// 청구번호
							break;
						case 5:
							mediaTapeInfoTbl.setSceanNo(Long.valueOf(StringUtils.defaultIfEmpty(cell.getContents(), "0")));	// 장면번호
							break;
						case 6:
							mediaTapeInfoTbl.setTitle(StringUtils.defaultIfEmpty(cell.getContents(), ""));									  	// 제목
							break;
						case 7:
							mediaTapeInfoTbl.setSubTtl(StringUtils.defaultIfEmpty(cell.getContents(), ""));									  	// 부제
							break;
						case 8:
							mediaTapeInfoTbl.setBrdDd(StringUtils.defaultIfEmpty(cell.getContents(), ""));									  	// 방송일(촬영일)
							break;
						case 9:
							mediaTapeInfoTbl.setCmrPlace(StringUtils.defaultIfEmpty(cell.getContents(), ""));								  	// 촬영장소
							break;
						case 10:
							mediaTapeInfoTbl.setVdQlty(StringUtils.defaultIfEmpty(cell.getContents(), ""));									  	// 화질
							break;
						case 11:
							mediaTapeInfoTbl.setCprtType(StringUtils.defaultIfEmpty(cell.getContents(), ""));								// 저작권형태
							break;
						case 12:
							mediaTapeInfoTbl.setCprtNm(StringUtils.defaultIfEmpty(cell.getContents(), ""));									  	// 저작권자
							break;
						case 13:
							mediaTapeInfoTbl.setCprtTypeDsc(StringUtils.defaultIfEmpty(cell.getContents(), ""));								// 저작권 형태설명
							break;
						case 14:
							mediaTapeInfoTbl.setRecordTypeCd(StringUtils.defaultIfEmpty(cell.getContents(), ""));							  	// 녹음방식
							break;
						case 15:
							mediaTapeInfoTbl.setRistClfCd(StringUtils.defaultIfEmpty(cell.getContents(), ""));								  	// 사용등급
							break;
						case 16:
							mediaTapeInfoTbl.setRsvPrdCd(StringUtils.defaultIfEmpty(cell.getContents(), ""));								  	// 보존기간
							break;
						case 17:
							mediaTapeInfoTbl.setCont(StringUtils.defaultIfEmpty(cell.getContents(), ""));									  	// 자료내용
							break;
						case 18:
							mediaTapeInfoTbl.setSpcInfo(StringUtils.defaultIfEmpty(cell.getContents(), ""));									// 특이사항
							break;
						case 19:
							mediaTapeInfoTbl.setCtgrMCd(StringUtils.defaultIfEmpty(cell.getContents(), ""));									// 중분류
							break;
						case 20:
							mediaTapeInfoTbl.setCtgrSCd(StringUtils.defaultIfEmpty(cell.getContents(), ""));									// 소분류
							break;
						case 21:
							mediaTapeInfoTbl.setKeyWords(StringUtils.defaultIfEmpty(cell.getContents(), ""));								  	// 키워드
							break;
						case 22:
							mediaTapeInfoTbl.setArtist(StringUtils.defaultIfEmpty(cell.getContents(), ""));									  	// 아티스트
							break;
						case 23:
							mediaTapeInfoTbl.setCountryCd(StringUtils.defaultIfEmpty(cell.getContents(), ""));								  	// 국가구분
							break;
						case 24:
							mediaTapeInfoTbl.setViewGrCd(StringUtils.defaultIfEmpty(cell.getContents(), ""));								  	// 시청등급
							break;
						case 25:
							mediaTapeInfoTbl.setPrdtInOutsCd(StringUtils.defaultIfEmpty(cell.getContents(), ""));								// 제작구분
							break;
						case 26:
							mediaTapeInfoTbl.setOrgPrdrNm(StringUtils.defaultIfEmpty(cell.getContents(), ""));							  		// 외주제작(사)구분
							break;
						case 27:
							mediaTapeInfoTbl.setDrtNm(StringUtils.defaultIfEmpty(cell.getContents(), ""));									  	// 연출
							break;
						case 28:
							mediaTapeInfoTbl.setEpisNo(Long.valueOf(StringUtils.defaultIfEmpty(cell.getContents(), "0")));						// 회차
							break;
						case 29:
							mediaTapeInfoTbl.setProducerNm(StringUtils.defaultIfEmpty(cell.getContents(), ""));									// 프로듀서
							break;
						case 30:
							mediaTapeInfoTbl.setCmrDrtNm(StringUtils.defaultIfEmpty(cell.getContents(), ""));									// 촬영감독
							break;
						case 31:
							mediaTapeInfoTbl.setMcNm(StringUtils.defaultIfEmpty(cell.getContents(), ""));										// 진행자
							break;
						case 32:
							mediaTapeInfoTbl.setWriterNm(StringUtils.defaultIfEmpty(cell.getContents(), ""));									// 작가
							break;
						case 33:
							mediaTapeInfoTbl.setPrdtDeptNm(StringUtils.defaultIfEmpty(cell.getContents(), ""));									// 제작부서명
							break;
						}
						//}
					}
					if(StringUtils.isNotBlank(mediaTapeInfoTbl.getReqCd()))
						mediaTapeInfoTbls.add(mediaTapeInfoTbl);
					
					mediaTapeInfoTbl = null;
					//if(j == 2) break;
				}
			}

			addClipForTapeService.insertAllMediaTapeInfo(mediaTapeInfoTbls);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
