package com.konantech.search.module;

import java.io.IOException;

import com.app.das.business.JNI_Des;
import com.konantech.search.data.ResultVO;
import com.konantech.search.util.DCConfig;
import konan.docruzer.*;


/** 검색엔진 API.
 * @author KONAN Technology
 * @since 2010.04.01
 * @version 1.0
 * Copyright ⓒ Konan Technology. All Right Reserved
 * ==================================================
 * 변경자/변경일 : OOO / yyyy.mm.dd
 * 변경사유/내역 : 기능 추가로 변경함.
 */
public class SearchModule {
    /** 실제 검색을 처리하는 메소드 (SubmitQuery).
     * @param scn 시나리오
     * @param query 검색쿼리
     * @param logInfo 로그정보
     * @param hilightTxt 하이라이트 키워드
     * @param orderBy 정렬절
     * @param pageNum 페이지번호
     * @param pageSize 페이지사이즈
     * @param flag rowid 사용 유무 플래그
     * @param rsb 결과셋을 담을 bean
     * @param nLanguage 언어셋
     * @param nCharset 캐릭터셋
     * @return 결과값(int)
     * @throws IOException
     * @throws Exception
     * @throws KonanException
     */
    public int dcSubmitQuery(String scn, String query, String logInfo, 
                            String hilightTxt, String orderBy, int pageNum, 
                            int pageSize, boolean flag, ResultVO rsb, 
							int nLanguage, int nCharset) 
					throws IOException, Exception, KonanException
    {
        long hc;							// 검색 핸들
        int rc;								// SubmitQuery 결과 값
        int total;							// 검색 결과  건수
        int rows;							// 페이지 사이즈에 해당하는 레코드 개수
        int cols;							// 시나리오에 설정된 레코드
        String[] colNames = null;			// 시나리오에 설정된 필드명
        int startNum;						// 검색 결과 시작 오프셋
        int i;								// for 문용 인스턴스 변수
        int j;								// for 문용 인스턴스 변수
        String msg = "";					// 에러 메시지
        int[] rowIds = null;				// rowid 배열
        int[] scores = null;				// score 배열
        String[] tmpFdata = null;			// 임시 결과 배열
        String[][] fData = null;			// 검색 결과 배열

		DOCRUZER crz = new DOCRUZER();		// DOCRUZER 객체 생성
		hc = crz.CreateHandle();			// 핸들 생성
		startNum = (pageNum -1) * pageSize;	// 검색 결과 시작 오프셋 계산
		
        /*
         * -----------------------------------------------------------------
         * 쿼리 인코딩 설정 옵션
         * rc = crz.SetCharacterEncoding(hc, "EUC_KR");
         * -----------------------------------------------------------------
         * connection timeout 시간. 초단위(default : 30초)
         * rc = crz.SetOption(hc, crz.OPTION_SOCKET_TIMEOUT_REQUEST, 30);
         * -----------------------------------------------------------------
         * 병렬 쿼리 수행 방식 (defaul : 0, 1일경우 병렬 쿼리 수행)
         * rc = crz.SetOption(hc, DOCRUZER.OPTION_SOCKET_ASYNC_REQUEST, 1);
         * -----------------------------------------------------------------
         */
		if(System.getProperty("os.arch").equals("x86")){
			   rc = crz.SetCharacterEncoding(hc, "EUC_KR");
		}else if(System.getProperty("os.arch").equals("amd64")){
			   rc = crz.SetCharacterEncoding(hc, "EUC_KR");
		}else{
		}

        // 검색 API - 사용자의 질의어를 서버로 보냄
        rc = crz.SubmitQuery(
        		hc,
                DCConfig.getProperty("DAEMON_SERVICE_IP"),
                Integer.parseInt(DCConfig.getProperty("DAEMON_SERVICE_PORT")),
                "", 
                logInfo, 
                scn, 
                query, 
                orderBy, 
                hilightTxt,
                startNum, 
                pageSize, 
                nLanguage, 
                nCharset
        );
        //For Debug --> System.out.println("rc : " + rc);

		if (rc != 0) {
			msg = "rc : " + rc + " scn : " + scn + "\n query : " + query + "\n orderBy : " + orderBy ;
			throw new KonanException(crz.GetErrorMessage(hc) + msg);
		} else {
			// SubmitQuery 메소드로 실행한 검색 결과의 총 검색 결과 수
			total = crz.GetResult_TotalCount(hc);
			// SubmitQuery 메소드로 실행한 검색 결과 중 실제로 가져온 검색 결과의 개수
			rows = crz.GetResult_RowSize(hc);
			// SubmitQuery 메소드로 실행한 검색 결과의 필드 개수
			cols = crz.GetResult_ColumnSize(hc);
			// SubmitQuery 메소드로 실행한 검색 결과의 필드명 리스트
			colNames = new String[cols];
			rc = crz.GetResult_ColumnName(hc, colNames, cols);
			
			if (flag) {
				rowIds = new int[rows];
				scores = new int[rows];
			
				// SubmitQuery 메소드로 실행한 검색 결과 중 nStartOffset 에서부터 
				// GetResult_RowSize 메소드로 얻어온 값만큼의 레코드 번호
				crz.GetResult_ROWID(hc, rowIds, scores);
			}
			
			tmpFdata = new String[cols];
			fData = new String[rows][cols];
			
			for (i = 0; i < rows; i++) {
				// SubmitQuery 메소드로 실행한 검색 결과 중
				// startNum 에서부터 i 번째 해당되는 레코드의 필드값을 가져옴
				crz.GetResult_Row(hc, tmpFdata, i);
				for (j = 0; j < cols; j++) {
					// 이차원 배열에 검색 결과를 담음
					fData[i][j] = tmpFdata[j];
				}
			}
			
			// ResultVO에 결과를 담음
			rsb.setTotal(total);
			rsb.setRows(rows);
			rsb.setCols(cols);
			rsb.setColNames(colNames);
			rsb.setRowIds(rowIds);
			rsb.setScores(scores);
			rsb.setFdata(fData);
		}
		
		// 핸들 삭제
		crz.DestroyHandle(hc);
		
		return rc;
	}

	/** 실제 검색을 처리하는 메소드 (Search-분산볼륨, String List GroupBy).
	 * @param scn 시나리오
	 * @param query 검색쿼리
	 * @param logInfo 로그정보
	 * @param hilightTxt 하이라이트 키워드
	 * @param orderBy 정렬절
	 * @param pageNum 페이지번호
	 * @param pageSize 페이지사이즈
	 * @param flag rowid 사용 유무 플래그
	 * @param rsb 결과셋을 담을 bean
	 * @param nLanguage 언어셋
	 * @param nCharset 캐릭터셋
	 * @return 결과값(int)
	 * @throws IOException
	 * @throws Exception
	 * @throws KonanException
	 */
	public int dcSearch(String scn, String query, String logInfo, String hilight,
			String orderBy, int pageNum, int pageSize, boolean flag, ResultVO rsb,
			int nLanguage, int nCharset)
		throws IOException, Exception, KonanException
	{
        
		long hc;							// 검색 핸들
        int rc;								// Search 결과 값
        int total;							// 검색 결과  건수
        int rows;							// 페이지 사이즈에 해당하는 레코드 개수
        int cols;							// 시나리오에 설정된 레코드
        int startNum;						// 검색 결과 시작 오프셋
        int i;								// for 문용 인스턴스 변수
        int j;								// for 문용 인스턴스 변수
		String msg = "";					// 에러 메시지
        int nGroupCount;					// 그룹별 건수
		int nGroupKeyCount;					// 그룹 건수
		int[] groupSize = null;				// 그룹건수 배열
		int[] rowIds = null;				// rowid 배열
		int[] scores = null;				// score 배열
		String[][] groupKeyVal = null;		// group by 대표값 배열
		String[][] fData = null;			// 결과셋
		String[] tmpFdata = null;			// 결과셋(임시)

		DOCRUZER crz = new DOCRUZER();		// DOCRUZER 객체 생성
		hc = crz.CreateHandle();			// 핸들 생성
		startNum = (pageNum -1) * pageSize;	// 검색 결과 시작 오프셋 계산
		
        /* 
		 * -----------------------------------------------------------------
         * 쿼리 인코딩 설정 옵션
         * rc = crz.SetCharacterEncoding(hc, "EUC_KR");
         * -----------------------------------------------------------------
         * connection timeout 시간. 초단위(default : 30초)
         * rc = crz.SetOption(hc, crz.OPTION_SOCKET_TIMEOUT_REQUEST, 30);
         * -----------------------------------------------------------------
         * 병렬 쿼리 수행 방식 (defaul : 0, 1일경우 병렬 쿼리 수행)
         * rc = crz.SetOption(hc, DOCRUZER.OPTION_SOCKET_ASYNC_REQUEST, 1);
         * -----------------------------------------------------------------
         */
        rc = crz.SetCharacterEncoding(hc, "UTF8");
        
        /* 검색 API - 사용자의 질의어를 서버로 보냄 */
        rc = crz.Search(
        		hc, 
        		DCConfig.getProperty("DAEMON_SERVICE_ADDRESS"), 
        		scn, 
        		query,
        		orderBy, 
        		hilight, 
        		logInfo, 
        		startNum, 
        		pageSize, 
        		nLanguage, 
        		nCharset
        );
        // For Debug
        // System.out.println("rc : " + rc);
        
        if (rc != 0) {
            msg = " scn : " + scn + "\n query : " + query + "\n orderBy : " + orderBy;
            throw new KonanException(crz.GetErrorMessage(hc) + msg);
        } 
        
        // STRING LIST 타입에 대한 GROUP BY 결과를 받아오기 위한 메소드
        nGroupCount = crz.GetResult_GroupBy_GroupCount(hc);
        // GROUP BY 를 수행한 키(필드)의 개수
		nGroupKeyCount = crz.GetResult_GroupBy_KeyCount(hc);
		
		groupKeyVal = new String[nGroupCount][nGroupKeyCount];
		groupSize = new int[nGroupCount];
		fData = new String[nGroupCount][2];
 
        if (flag) { 
        	// string list group by 일 경우
        	
        	total = 0;
    		rows = crz.GetResult_RowSize(hc);
    		cols = crz.GetResult_ColumnSize(hc);
    		
			rowIds = new int[rows];
			scores = new int[rows];
			
			// 그룹의 대표값과 그룹에 속한 문서의 개수를 받아오기 위한 메소드
			rc = crz.GetResult_GroupBy(hc, groupKeyVal, groupSize, nGroupCount);
			
			if (rc != 0) {
				msg = " scn : " + scn + "\n query : " + query + "\n orderBy : " + orderBy;
				throw new KonanException(crz.GetErrorMessage(hc) + msg);
			}
			
			for (i = 0; i < nGroupCount; i++) {
				fData[i][0] = groupKeyVal[i][0];
				fData[i][1] = Integer.toString(groupSize[i]);
	            total += groupSize[i];
	            // System.out.println(fData[i][0] + "[" + fData[i][1] + "]");
	        }
			
			// ResultVO에 결과를 담음
			rsb.setTotal(total);
			rsb.setRows(nGroupCount);
			rsb.setCols(cols);
			rsb.setRowIds(rowIds);
			
			rsb.setGroupCount(nGroupCount);
			rsb.setGroupSize(groupSize);
			rsb.setGroupResult(fData);
		} else { 
			// 분산 볼륨 검색일 경우
			
			// SubmitQuery 메소드로 실행한 검색 결과의 총 검색 결과 수
			total = crz.GetResult_TotalCount(hc);
			// SubmitQuery 메소드로 실행한 검색 결과 중 실제로 가져온 검색 결과의 개수
			rows = crz.GetResult_RowSize(hc);	
			// SubmitQuery 메소드로 실행한 검색 결과의 필드 개수
			cols = crz.GetResult_ColumnSize(hc);
	        
	        rowIds = new int[rows];
			scores = new int[rows];
		
			// SubmitQuery 메소드로 실행한 검색 결과 중 nStartOffset 에서부터 
			// GetResult_RowSize 메소드로 얻어온 값만큼의 레코드 번호
			crz.GetResult_ROWID(hc, rowIds, scores); 
			
			tmpFdata = new String[cols];
			fData = new String[rows][cols];
			
			for (i = 0; i < rows; i++) {
				// SubmitQuery 메소드로 실행한 검색 결과 중
				// startNum 에서부터 i 번째 해당되는 레코드의 필드값을 가져옴
				crz.GetResult_Row(hc, tmpFdata, i);
			
				for (j = 0; j < cols; j++) {
					fData[i][j] = tmpFdata[j];
				}
				// System.out.println("result : " + fData[i][0]);
			}
			
			// ResultVO에 결과를 담음
			rsb.setTotal(total);
			rsb.setRows(rows);
			rsb.setCols(cols);
			rsb.setRowIds(rowIds);
			rsb.setScores(scores);
			rsb.setFdata(fData);
		}

        // 핸들 삭제
        crz.DestroyHandle(hc);

        return rc;
	}

	/** 오타 교정 (한->영, 영->한).
	* @param kwd
	*
	* @return corrected kwd
	* @throws IOException
	* @throws KonanException
	**/
	public String[] getCorrectedKwd(String kwd) throws IOException , KonanException
	{
		long hc;							// 검색 핸들
        int rc;								// SpellCheck 결과 값
		int MaxOutCount = 10;				// 교정된 단어 갯수(max)
		String[] outWord = null;
		String[] correctedKwd = null;
		outWord = new String[MaxOutCount];
		
		DOCRUZER crz = new DOCRUZER();		// DOCRUZER 객체 생성
		hc = crz.CreateHandle();			// 핸들 생성
		
		// 오타 문자열의 오타 교정된 문자열을 받아옴
		rc = crz.SpellCheck(
				hc, 
				DCConfig.getProperty("DAEMON_SERVICE_ADDRESS"), 
				outWord, 
				MaxOutCount, 
				kwd
		);
		// For Debug
        // System.out.println("rc : " + rc);
        
		if (rc < 0) {
			throw new KonanException(crz.GetErrorMessage(hc));
		} else {
			correctedKwd = new String[rc];
			for (int i=0; i < rc; i++) {
				correctedKwd[i] = outWord[i];
			}
		}

		// 핸들 삭제
		crz.DestroyHandle(hc);

		return correctedKwd ;
	}

	/** 인기검색어 (순위정보[태그] 있음 ).
	*
	* @param domainNo 도메인(사전)번호
	* @param count 가져올 개수
	*
	* @return ArrayList
	* @throws Exception
	*/
	public String[][] getPopularKwdAndTag(int domainNo, int count)
				throws Exception, KonanException
	{
		long hc;							// 검색 핸들
        int rc;								// GetPopularKeyword2 결과 값
		int MaxOutCount = 10;				// 인기검색어 리턴 개수(max)
		String[] outStr = null;				// 인기검색어 배열
		String[] outTag = null;				// 인기검색어의 태그값 배열
		String[][] arrPpk = null;			// 리턴 이차원배열(인기검색어, 태그)

		if (count != 0) {
			MaxOutCount = count;
        }
        outStr = new String[MaxOutCount];
        outTag = new String[MaxOutCount];

        DOCRUZER crz = new DOCRUZER();		// DOCRUZER 객체 생성
        hc = crz.CreateHandle();			// 핸들 생성

        //인기 검색어를 추출함. (태그 정보 포함)
        rc = crz.GetPopularKeyword2(
        		hc,
        		DCConfig.getProperty("DAEMON_SERVICE_ADDRESS"),
        		outStr,
        		outTag,
        		MaxOutCount,
        		domainNo
        );
		// For Debug
        // System.out.println("rc : " + rc); 
        
		if (rc < 0) {
			throw new KonanException(crz.GetErrorMessage(hc));
		} else {
			MaxOutCount = rc;
			
			arrPpk = new String[MaxOutCount][2];
			for(int j=0; j < MaxOutCount; j++) {
				arrPpk[j][0] = outStr[j];
				arrPpk[j][1] = outTag[j];
			}
		}

		// 핸들 삭제
		crz.DestroyHandle(hc);

		return arrPpk;
	}

	/** 추천 검색어.
	*
	* @param kwd 키워드
	* @param domainNo 도메인(사전)번호
	* @param count 가져올 최대 개수
	*
	* @return ArrayList 추천어 배열
	* @throws Exception
	*/
	public String[] getRecommandKwd( String kwd, int domainNo, int count)
				throws Exception, KonanException
	{
		long hc;							// 검색 핸들
        int rc;								// RecommendKeyword 결과 값
		int MaxOutCount = 10;				// 추천검색어 리턴 개수(max)
		String[] outStr = null;				// 키워드에 해당하는 추천어 배열
		String[] arrRecKwd = null;			// 리턴될 추천어 배열
		
		// 사용자 지정 개수가 있을 경우 MaxOutCount을 입력값으로 대체함.
		if (count != 0) {			
			MaxOutCount = count;	
		}
		
		outStr = new String[MaxOutCount];
		
		DOCRUZER crz = new DOCRUZER();		// DOCRUZER 객체 생성
		hc = crz.CreateHandle();			// 핸들 생성
		
		// 사용자가 입력한 검색어를 바탕으로 추천 검색어를 추출.
		rc = crz.RecommendKeyword(
				hc, 
				DCConfig.getProperty("DAEMON_SERVICE_ADDRESS"),
				outStr, 
				MaxOutCount, 
				kwd, 
				domainNo
		);
		// For Debug
        // System.out.println("rc : " + rc); 
    
		if (rc < 0) {						// 예외상황
			throw new KonanException(crz.GetErrorMessage(hc));
		} else if (rc == 0) {				// 입력된 키워드에 해당하는 추천어가 없을 경우.
			arrRecKwd = null;
		} else {							// 입력된 키워드에 해당하는 추천어가 있을 경우.
			arrRecKwd = new String[rc];

			for (int j=0; j < rc; j++) {
				arrRecKwd[j] = outStr[j];
			}
		}

		// 핸들 삭제
		crz.DestroyHandle(hc);

		return arrRecKwd;
	}
}