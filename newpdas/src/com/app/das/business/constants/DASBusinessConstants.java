package com.app.das.business.constants;

/**
 * 우리 시스템의 상수값들이 정의 되어 있는 class
 * @author ysk523
 *
 */
public class DASBusinessConstants 
{
	/**
	 * 이 class는 생성할 수 없다.
	 */
	private DASBusinessConstants()
	{
	}

	
	/**
	 * page 처리시 쿼리 종류
	 */
	public static final class PageQueryFlag
	{
		/**
		 * 총 카운터 쿼리
		 */
		public static final String TOTAL_COUNT = "1";
		/**
		 * 일반 조회 쿼리
		 */
		public static final String NORMAL = "2";
	}
	
	/**
	 * Page 처리시 한 페이지당 디스플레이 건수 정의
	 */
	public static final class PageRowCount
	{
		/**
		 * 공통
		 */
		public static final int COMMON_ROW_COUNT = 50;
		/**
		 * 사용자 조회 건수
		 */
		public static final int USER_ROLE_ROW_COUNT = 20;
		/**
		 * 공통 조회건수
		 */
		public static final int  BASIC_ROW_COUTN = 200;
		/**
		 * ID별 자료이용 현황
		 */
		public static final int CONTENTS_USE_ROW_COUNT = 10;
		/**
		 * 게시판
		 */
		public static final int BOARD_ROW_COUNT = 10;
		/**
		 * 폐기검색
		 */
		public static final int DISCARD_ROW_COUNT = 200;
		/**
		 * 게시판
		 */
		public static final int NEW_BOARD_ROW_COUNT = 100;
		/**
		 * 내목록
		 */
		public static final int MY_CATALOG_ROW_COUNT = 10;
		/**
		 * 내목록(2.0)
		 */
		public static final int NEW_MY_CATALOG_ROW_COUNT = 200;
		/**
		 * 요청영상목록
		 */
		public static final int DOWN_REQ_ROW_COUNT = 10;
		/**
		 * 코드조회
		 */
		public static final int CODE_INFO_ROW_COUNT = 10;
		/**
		 * 사용자조회
		 */
		public static final int USER_INFO_ROW_COUNT = 10;
		/**
		 * 폐기조회
		 */
		public static final int DISUSE_ROW_COUNT = 50;
		/**
		 * 사용자 변경내역
		 */
		public static final int OUT_USER_ROLE_ROW_COUNT = 10;
		/**
		 * 사용자 변경내역
		 */
		public static final int SBS_USER_ROLE_ROW_COUNT = 10;
		/**
		 * 장비로그
		 */
		public static final int EQUIPMENT_ROW_COUNT = 50;
		/**
		 * 매체변환
		 */
		public static final int WORK_LOG_ROW_COUNT = 50;
		/**
		 * 다운로드 승인요청
		 */
		public static final int DOWN_ROW_COUNT = 10;
		

	}
	
	/**
	 * 사용자 검색시 검색 종류 상수 정의
	 */
	public static final class UserRoleSearchFlag
	{
		/** 
		 * 사용자 검색 구분이 이름 
		 */
		public static final String SEARCH_TYPE_NAME = "1";
		/** 
		 * 사용자 검색 구분이 ID 
		 */
		public static final String SEARCH_TYPE_ID = "2";
		
	}
	
	/**
	 * 정직원, 외부직원 여부
	 */
	public static final class EmployeeFlag
	{
		/**
		 * 정직원
		 */
		public static final String REGULAR = "1";
		/**
		 * 외부직원
		 */
		public static final String OUTSIDE ="2";
	}
	
	/**
	 * YES or NO 의 상수값정의
	 */
	public static final class YesNo
	{
		/**
		 * YES
		 */
		public static final String YES = "Y";
		/**
		 * NO
		 */
		public static final String NO = "N";
	}
	
	/**
	 * Properties 설정 파일 정의 함
	 */
	public static final class PropertiesFileName
	{
		/**
		 * Error 메세지 성정 파일
		 */
		public static final String ERROR_MESSAGE = "message";
		/**
		 * Das 설정 관련 환경 파일
		 */
		public static final String DAS_CONFIG = "das";
		
		/**
		 * Search(검색엔진) 설정 관련 환경 파일
		 */
		public static final String SEARCH_CONFIG = "search";
	}
	
	/**
	 * 회사코드, 본부코드, 부서코드의 조직구분
	 */
	public static final class OrganCode
	{
		/**
		 * 회사코드
		 */
		public static final String COMPANY_CODE = "C";
		/**
		 * 본부코드
		 */
		public static final String CENTER_CODE = "S";
		/**
		 * 부서코드
		 */
		public static final String DEPARTMENT_CODE = "D";
			
	}
	/**
	 * DB의 Sequence 이름을 정의한다
	 */
	public static final class SequenceName
	{
		/**
		 * 외부사용자ID
		 */
		public static final String OUT_USER_ID = "SEQ_OUT_ID";
		/**
		 * 폐기번호
		 */
		public static final String SEQ_DISUSE_ID = "SEQ_DISUSE_ID";
		/**
		 * 게시판ID
		 */
		public static final String BOARD_ID = "SEQ_BOARD_ID";
		/**
		 * 대출신청번호
		 */
		public static final String LENDING_APP_NAME = "SEQ_LEND_APLN_NO";
		/**
		 * 카트번호
		 */
		public static final String CART_NO = "SEQ_CART_NO";
		/**
		 * TC JOB Alocate 번호
		 */
		public static final String TC_JOB="SQU";
		/**
		 * TC STATE 상태값 번호
		 */
		public static final String TC_STATE="SQT";
	}
	/**
	 * 작업현황 구분
	 */
	public static final class WorkOrdersKind
	{
		/**
		 * 매체
		 */
		public static final String MESIUM = "1";
		/**
		 * 주조
		 */
		public static final String MAIN_TRANSFER = "2";
	}
	
	/**
	 * 통계의 날짜 구분(연별, 월별, 기간)
	 */
	public static final class StatisticsDateKind
	{
		/**
		 * 연별
		 */
		public static final String YEAR = "1";
		/**
		 * 월별
		 */
		public static final String MONTH = "2";
		/**
		 * 기간별
		 */
		public static final String TERM = "3";
	}
	
	/**
	 * Transaction 속성
	 */
	public static final class TrxKind
	{
		/**
		 * 등록
		 */
		public static final String CREATE = "C";
		/**
		 * 수정
		 */
		public static final String UPDATE = "U";
	}
	
	/**
	 * SSO 계정 관련 상수정의
	 */
	public static final class SSO
	{
		/**
		 * URL
		 */
		public static final String URL = "http://sso.nds.sbs.co.kr:9080/THanQ/THanQManager";
		/**
		 * Admin ID
		 */
		public static final String ADMIN_ID = "sec_master";
		/**
		 * Admin Passwd
		 */
		public static final String PASSWD = "tivsec";
	}
	
	/**
	 * 게시판종류 별 업로드 파일 경로 property 이
	 */
	public static final class FilePathPropName
	{
		/** 
		 * 공지사항   
		 */
		public static final String NOTICE       = "FILE_PATH_NOTICE";        
		/** 
		 * 묻고답하기 
		 */
		public static final String QNA          = "FILE_PATH_QA";        
		/** 
		 * 신고 
		 */      
		public static final String STATEMENT    = "FILE_PATH_STATEMENT";        
		/** 
		 * 이용안내 
		 */  
		public static final String USE_INFO	= "FILE_PATH_USE";
	}
	
	/**
	 * 다운로드 승인 여부
	 */
	public static final class ApproveFlag
	{
		/**
		 * 승인
		 */
		public static final String APPROVE = "001";
		/**
		 * 거부
		 */
		public static final String REJECT = "002";
	}
	
	/**
	 * DAS 외부사용자 ID 첫자
	 */
	public static final String OUT_USER_ID_PREFIX = "D";

	/**
	 * DAS 외부사용자 ID 첫자
	 */
	public static final String SBS_USER_ID_PREFIX = "D";
	/**
	 * 최대 패스워드 실패횟수
	 */
	public static final int PASSWD_FAIL_MAX_COUNT = 3;
	
	/**
	 * 초기 패스워드 
	 */
	public static final String INITIAL_PASSWD = "11111";
	
	/**
	 * 외부 사용자 초기 주민번호 
	 */
	public static final String PER_REG_NO = "1111111111111";
	
	/**
	 * 인제스트 재지시 작업 구분 
	 */
	public static final String RE_INGEST = "001";
	/**
	 * 다운카트 존재 
	 */
	public static final String DOWN_CART_EXIST = "Y";
	/**
	 * 검색 엔진 서버 IP
	 */
	public static final String SEARCH_SERVER_IP = "127.0.0.1";
	/**
	 * 웹서비스 서버
	 */
	public static final String WEBSERVICE_HOST = "dadb1";
	
	/**
	 * 미디어 브라우즈 조회 콤보
	 * @author dekim
	 *
	 */
	public static final class SearchCombo{
		
	
		/**
		 * 화면비
		 */
		public static final String ASP_RTO_CD = "001";
	
		/**
		 * 화질
		 */
		public static final String VD_QLTY = "002";
	
		/**
		 * 사용제한등급
		 */
		public static final String USE_LIMIT = "003";
	
		/**
		 * 시청등급
		 */
		public static final String VIEW_GR_CD = "004";
	
		/**
		 * 저작권형태
		 */
		public static final String CPRT_TYPE = "005";
	
		/**
		 * 보존기간
		 */
		public static final String RSV_PRD_CD = "006";
	
		/**
		 * 콘텐츠 구분코드
		 */
		public static final String CT_CLA = "007";
		
		/**
		 * 테이브 종류
		 */
		public static final String TAPE_KIND= "008";
	}
	/**
	 * 장르 대분류 코드
	 */
	public static final class Ctgr_l_cd{
		/**
		 * 소재
		 */
		public static final String SOJAE = "100";
		/**
		 * 프로그램
		 */
		public static final String PROGRAM = "200";
	}
	
	/**
	 * 일괄수정에 대한 수정필드들.
	 * @author dekim
	 */
	public static final class TotalChangeCombo{
		
		 /**
		 * 수정대상 구분
		 */
		    static public final String GUBUN = "gubun";          
		    /**
			 * 보존기간 코드
			 */
		    static public final String RSV_PRD_CD= "rsv_prd_cd"; 
		    /**
			 * 장르 대표 구분코드
			 */
			static public final String CTGR = "ctgr";            
			 /**
			 * 대분류
			 */
			static public final String CTGR_L_CD = "ctgr_l_cd"; 
			 /**
			 * 중분류
			 */
			static public final String CTGR_M_CD = "ctgr_m_cd"; 
			 /**
			 * 소분류
			 */
			static public final String CTGR_S_CD = "ctgr_s_cd";
			 /**
			 * 제작구분 코드
			 */
			static public final String PRDT_IN_OUTS_CD = "prdt_in_outs_cd"; 
			 /**
			 * 제작부서코드
			 */
			static public final String PRDT_DEPT_CD = "prdt_dept_cd"; 
			 /**
			 * 제작부서명
			 */
			static public final String PRDT_DEPT_NM = "prdt_dept_nm"; 
			 /**
			 * 촬영장소
			 */
			static public final String CMR_PLACE = "cmr_place";    
			 /**
			 * 저작권 형태 코드
			 */
			static public final String CPRT_TYPE = "cprt_type";    
			 /**
			 * 저작권 형태 설명
			 */
			static public final String CPRT_TYPE_DSC = "cprt_type_dsc"; 
			 /**
			 * 저작권자
			 */
			static public final String CPRTR_NM = "cprtr_nm";      
			 /**
			 * 녹음방식 코드
			 */
			static public final String RECORD_TYPE_CD ="record_type_cd";  
			 /**
			 * 단일 마스터 아이디
			 */
			static public final String MASTERID = "masterId";             
			 /**
			 * 마스터 아이디 그룹
			 */
			static public final String MASTERIDGRP = "masterIdGrp";       
			 /**
			 * 수상기록명
			 */
			static public final String award_hstr = "award_hstr";       
			 /**
			 * 원제작자명
			 */
			static public final String ORG_PRDR_DEP_NM = "org_prdr_nm";      
			/**
			 * 녹음방식코드
			 */
			static public final String record_type_cd = "record_type_cd";      
		
			
	}
	
	
	/**
	 * 아카이브 요청 구분
	 * @author dekim
	 *
	 */
	public static final class SourceGubun{
		/**
		 * 매체변환 
		 */
		public static final String SDI = "N";        
		/**
		 * 온에어
		 */
		public static final String ONAIR = "Y";      
		/**
		 * PDS
		 */
		public static final String PDS = "PDS";        
		/**
		 * NDS
		 */
		public static final String NDS = "N";        
		/**
		 * AnyWay 전체조건
		 */
		public static final String ANY = "A";        
	}
	/**
	 * 인터페이스 구분
	 */
	public static final class InterfaceGubun{
		/**
		 * DAS_TM WebService endpoint 주소
		 */
		public static final String DAS_TM = "DAS_TM_URL";  
		/**
		 * PDS_CMS WebService endpoint 주소
		 */
		public static final String PDS_CMS = "PDS_CMS_URL";
		/**
		 * NDS_CMS WebService ENDPOINT  주소
		 */
		public static final String NDS_CMS = "NDS_CMS_URL"; 
		/**
		 * ERP WebService endpoint 주소
		 */
		public static final String ERP = "ERP_URL";    		
//		public static final String DAS_TM_GET_STATUS = "";
	}
	/**
	 * DAS_TM 인터페이스 명
	 */
	public static final class Das_tm{
		/**
		 * ADDTASK 인터페이스 명
		 */
		public static final String ADDTASK ="DAS_TM_JOB_ADDTASK";   
		/**
		 * GETSTATUS 인터페이스명
		 */
		public static final String GETSTATUS = "DAS_TM_JOB_GETSTATUS";  
	}
	
	/**
	 * 폐기 스케줄러가 삭제하려고 하는 삭제 대기 기간일
	 * @author asura
	 *
	 */
	public static final class DeleteAfterDate{
		/**
		 * 삭제 대기 기간일
		 */
		public static final String DELETEAFTERDATE = "deleteAfterDisCard";
	}
	
	/**
	 * 대본검색 시나리오
	 */
	public static final class Search_scenario{
		/**
		 * 주제영상  , 명장면
		 */
		public static final String SCN_DAS_ANNOT = "annot"; 		
		/**
		 * 코너
		 */
		public static final String SCN_DAS_CORNER = "corner"; 		
		/**
		 * 사진
		 */
		public static final String SCN_DAS_PICTURE = "picture";		
		/**
		 * 프로그램
		 */
		public static final String SCN_DAS_PROGRAM = "program";		
		/**
		 * 테이프
		 */
		public static final String SCN_DAS_TAPE = "tape";		
		
	}
	
	
	
	
	
	
	/**
	 * 사용제한코드
	 */
	public static final class Rist_cd
	{
		/**
		 * 사용금지
		 */
		public static final String USE_PROHIBIT = "003";
		/**
		 * 담당PD확인
		 */
		public static final String CONFRIM_PD = "004";
		/**
		 * 사용제한
		 */
		public static final String USE_LIMIT = "006";
		/**
		 * 무제한
		 */
		public static final String  UNLIMIT = "007";
	}
	
}
