package com.app.das.business.constants;

/**
 * 코드 관련 상수정의 class
 * @author ysk523
 *
 */
public class CodeConstants 
{
	/**
	 * 이 class는 생성할 수 없다.
	 */
	private CodeConstants()
	{
	}

	/**
	 * Code Group 상수 정의
	 */
	public static final class CodeGroup
	{
		/** 
		 * 매체코드             
		 */            
		public static final String CLF_CD_MEDIA = "P001";			
		/** 
		 * 대분류코드           
		 */            
		public static final String CLF_CD_LARGE_CATEGORY = "P002";		
		/** 
		 * 중분류코드          
		 */            
		public static final String CLF_CD_MEDIUM_CATEGORY = "P003";		
		/** 
		 * 소분류코드           
		 */            
		public static final String CLF_CD_SMALL_CATEGORY = "P004";		
		/** 
		 * 제작구분코드         
		 */            
		public static final String CLF_CD_AUTHORING_TYPE = "P005";		
		/** 
		 * 보관장소코드         
		 */            
		public static final String CLF_CD_ARCHIVE_LOCATION = "P006";	
		/** 
		 * 작업우선순위코드     
		 */            
		public static final String CLF_CD_JOB_PRIORITY = "P007";		
		/** 
		 * 테이프매체종류코드   
		 */            
		public static final String CLF_CD_TAPE_TYPE = "P008";		
		/** 
		 * 수집처코드           
		 */            
		public static final String CLF_CD_COLLECTOR = "P009";		
		/** 
		 * 수집구분코드         
		 */            
		public static final String CLF_CD_COLLECTION_TYPE = "P010";		
		/** 
		 * 자료상태코드         
		 */            
		public static final String CLF_CD_DATA_STATUS = "P011";		
		/** 
		 * 보존기간코드         
		 */            
		public static final String CLF_CD_ARCHIVE_PERIOD = "P012";		
		/** 
		 * 저작권형태코드       
		 */            
		public static final String CLF_CD_COPYRIGHT = "P013";		
		/** 
		 * 심의결과코드         
		 */            
		public static final String CLF_CD_DELIBERATION = "P014";		
		/** 
		 * 첨부파일유형코드     
		 */            
		public static final String CLF_CD_ATTACH_FILE_TYPE = "P015";		
		/** 
		 * 코너유형코드         
		 */            
		public static final String CLF_CD_CONER_TYPE = "P016";		
		/** 
		 * 시청등급코드         
		 */            
		public static final String CLF_CD_WATCH_LEVEL = "P017";		
		/** 
		 * 주석구분코드         
		 */            
		public static final String CLF_CD_COMMENT_TYPE = "P018";
		/**
		 * 계열사 코드(회사코드)
		 */
		public static final String CLF_CD_CO_CD="P058";
		/** 
		 * 콘텐츠구분코드       
		 */            
		public static final String CLF_CD_CONTENTS_CATEGORY = "A001";	
		/** 
		 * 컨텐츠유형코드       
		 */            
		public static final String CLF_CD_CONTENTS_TYPE = "A002";		
		/** 
		 * 사용여부코드         
		 */            
		public static final String CLF_CD_USAGE = "A003";			
		/** 
		 * 주관부서코드         
		 */            
		public static final String CLF_CD_OWNER_DEPARTMENT = "A004";	
		/** 
		 * 화질코드             
		 */            
		public static final String CLF_CD_IMAGE_QUALITY = "A005";		
		/** 
		 * 종횡비코드           
		 */            
		public static final String CLF_CD_HOR_VER_RATIO = "A006";		
		/** 
		 * 키프레임자동추출코드 
		 */            
		public static final String CLF_CD_KEYFRAME_EXTRACTION = "A007";	
		/** 
		 * 키프레임해상도코드   
		 */            
		public static final String CLF_CD_KEYFRAME_RESOLUTION = "A008";	
		/** 
		 * 컨텐츠인스턴스코드   
		 */            
		public static final String CLF_CD_CONTENTS_INSTANCE_FORMAT = "A009";	
		/** 
		 * 녹음방식코드         
		 */            
		public static final String CLF_CD_RECORDING_TYPE = "A010";		
		/** 
		 * 오디오언어코드       
		 */            
		public static final String CLF_CD_AUDIO_LANGUAGE = "A011";		
		/** 
		 * 색상코드             
		 */            
		public static final String CLF_CD_COLOR = "A012";			
		/** 
		 * ME분리코드           
		 */            
		public static final String CLF_CD_ME_SEPERATION = "A013";		
		/** 
		 * 아카이브여부코드     
		 */            
		public static final String CLF_CD_ARCHIVE = "A014";		
		/** 
		 * 드롭프레임코드       
		 */            
		public static final String CLF_CD_DROP_FRAME = "A015";		
		/** 
		 * 잡음저감유형코드     
		 */            
		public static final String CLF_CD_NOISE_REDUCTION_TYPE = "A016";	
		/** 
		 * 인코딩품질코드       
		 */            
		public static final String CLF_CD_ENCODING_QUALITY = "A017";	
		/**
		 * 사진종류코드         
		 */            
		public static final String CLF_CD_PHOTO_TYPE = "A018";		
		/** 
		 * 게시판종류코드       
		 */            
		public static final String CLF_CD_BULLETINE_TYPE = "P019";		
		/** 
		 * 답하기여부코드       
		 */            
		public static final String CLF_CD_QnA = "P020";			
		/** 
		 * 사용제한구분코드     
		 */            
		public static final String CLF_CD_USAGE_LIMITATION_TYPE = "P021";	
		/** 
		 * 폐기구분코드         
		 */            
		public static final String CLF_CD_DISCARD_TYPE = "P022";		
		/** 
		 * 자료구분코드         
		 */            
		public static final String CLF_CD_DATA_TYPE = "A019";
		/** 
		 * DAS 작업구분코드      
		 */            
		public static final String CLF_CD_DAS_WORK_TYPE = "A020";	
		/** 
		 * DAS장치구분코드      
		 */            
		public static final String CLF_CD_DAS_DEVICE_TYPE = "A021";		
		/** 
		 * DAS작업상태코드      
		 */            
		public static final String CLF_CD_DAS_JOB_STATUS = "A022";		
		/** 
		 * 첨부구분코드         
		 */            
		public static final String CLF_CD_ATTACH_TYPE = "P023";		
		/** 
		 * 최종방송여부코드  	 
		 */              
		public static final String CLF_CD_FINAL_BROADCAST = "P024";		
		/**
		 * 역할코드
		 */
		public static final String CLF_CD_ROLE_CODE = "A025";
		/**
		 * 권한코드
		 */
		public static final String CLF_CD_AUTH_CODE = "A034";
		/**
		 * 소속코드
		 */
		public static final String CLF_CD_CO_CODE = "P025";
		/**
		 * 카트상태
		 */
		public static final String CLF_CD_CART_STATUS = "A042";
		/**
		 * M4 스토리지
		 */
		public static final String CLF_CD_M4_STORAGE = "A047";
		/** 
		 * DAS장비프로세스코드      
		 */            
		public static final String CLF_CD_DAS_EQ_PROCESS_CODE = "P048";		
		/**
		 * 작업구분코드
		 */
		public static final String CLF_CD_DAS_WORKER_CODE = "P049";
		/**
		 * 인제스트 상태
		 */
		public static final String INGEST_STATUS = "A043";
		/**
		 * SGL 에러코드
		 */
		public static final String ERROR_STATUS = "P054";
		
	}
	
	/**
	 * 자료구분코드
	 */
	public static final class DataClfCode
	{
		/**
		 * 영상
		 */
		public static final String IMAGE = "001";
		/**
		 * 사진
		 */
		public static final String PHOTO = "002";
	}
	/**
	 * 폐기구분코드
	 */
	public static final class DisuseKind
	{
		/** 
		 * 1차선정         
		 */
		public static final String FIRST_CHOICE                = "001";        
		/** 
		 * 폐기위원검토    
		 */
		public static final String INVESTIGATION               = "002";        
		/** 
		 * 데이터정보팀심의
		 */
		public static final String DATA_INFO_DISCUSSION        = "003";        
		/** 
		 * 폐기완료        
		 */
		public static final String DISUSE_COMPLETION           = "004";        
		/** 
		 * 폐기취소        
		 */
		public static final String DISUSE_CANCEL		= "005";
	}
	
	/**
	 * 연장기간코드
	 */
	public static final class ExtensionTermCode
	{
		/** 
		 * 1개월 
		 */
		public static final String MONTH	= "001";        
		/** 
		 * 1년   
		 */
		public static final String YEAR         = "012";        
		/** 
		 * 5년   
		 */
		public static final String FIVE_YEAR    = "060";        
		/** 
		 * 영구  
		 */
		public static final String FOREVER      = "000";
	}
	/**
	 * 자료상태코드
	 */
	public static final class DataStatusCode
	{
		/**
		 * 준비중
		 */
		public static final String STARTINGYN = "000";
		/**
		 * 정리전
		 */
		public static final String ARRANGE_BEFORE = "001";
		/**
		 * 정리중
		 */
		public static final String ARRANGE_ING = "002";
		/**
		 * 정리완료
		 */
		public static final String ARRANGE_COMPLET = "003";
		/**
		 * 검수완료
		 */
		public static final String COMPLET = "007";
		/**
		 * 인제스트 재지시
		 */
		public static final String RE_ORDERS = "004";
		/**
		 * 2차 아카이브  재지시
		 */
		public static final String RE_ARCHIVE = "005";
		/**
		 * 오류
		 */
		public static final String ERROR = "009";
		/**
		 * 아카이브
		 */
		public static final String ARCHIVE = "008";
		/**
		 * 편집중
		 */
		public static final String EDIT_ING = "01";
		/**
		 * 편집준비중
		 */
		public static final String PRE_EDIT = "010";
		
	}
	/**
	 * 장치구분코드
	 */
	public static final class DeviceType
	{
		/** 
		 * SDI 인제스트 
		 */
		public static final String SDI          = "A00";        
		/** 
		 * TypeOut      
		 */
		public static final String TYPE_OUT     = "B00";        
		/** 
		 * File 인제스트
		 */
		public static final String FILE         = "C00";        
		/** 
		 * Archive      
		 */
		public static final String ARCHIVE      = "D00";        
		/** 
		 * NLE          
		 */
		public static final String NLE		= "E00";
	}
	/**
	 * 게시판종류 코드
	 */
	public static final class BoardKind
	{
		/** 
		 * 공지사항   
		 */
		public static final String NOTICE       = "001";        
		/** 
		 * 묻고답하기 
		 */
		public static final String QNA          = "002";        
		/** 
		 * 신고 
		 */      
		public static final String STATEMENT    = "003";        
		/** 
		 * 이용안내 
		 */  
		public static final String USE_INFO	= "004";
	}
	
	/**
	 * 게시판 검색조건
	 */
	public static final class BoardSearchKind
	{
		/**
		 * 추가
		 */
		public static final String ADD = "001";
		/**
		 * 제목
		 */
		public static final String SUBJECT = "002";
		/**
		 * 작성자
		 */
		public static final String USER = "003";
		/**
		 * 작성일
		 */
		public static final String REG_DATE = "004";
		/**
		 * 종류
		 */
		public static final String KIND = "005";
	}
	/**
	 * 외부 사용자 조회시 ID 상태
	 */
	public static final class IDStatus
	{
		/**
		 * 진행
		 */
		public static final String CONTINUE = "001";
		/**
		 * 중지
		 */
		public static final String CLOSE = "002";
		/**
		 * 전체
		 */
		public static final String ALL = "003";
	}
	/**
	 * 미접속 ID 현황 조회의 접속구분
	 */
	public static final class LoginUserFlag
	{
		/**
		 * 미접속ID
		 */
		public static final String NON_LOGIN_ID = "001";
		/**
		 * 중지일자
		 */
		public static final String STOP_DATE = "002";
	}
	
	/**
	 * 작업현황 매체의 날짜 조회 구분
	 */
	public static final class WorkStatusDateFlag
	{
		/**
		 * 촬영일
		 */
		public static final String SHOT_DATE = "001";
		/**
		 * 등록일
		 */
		public static final String REG_DATE = "002";
		/**
		 * 정리완료일
		 */
		public static final String COMPLET_DATE = "003";
		
		/**
		 * 검수완료일
		 */
		public static final String ACCEPT_DATE = "004";
	}
	
	
	/**
	 * 첨부파일 유형코드
	 */
	public static final class AttachFileKind
	{
		/**
		 * 기타
		 */
		public static final String ETC = "009";
	}
	/**
	 * 첨부구분코드
	 */
	public static final class AttachFlag
	{
		/**
		 * 시놈시스
		 */
		public static final String SYNOM = "001";
		/**
		 * 게시판
		 */
		public static final String BOARD = "002";
		/**
		 * 첨부파일
		 */
		public static final String ATTACH = "003";
	}
	/**
	 * 카트 상태코드
	 */
	public static final class CartStatus
	{
		/** 
		 * 사용 중          
		 */
		public static final String USE                  = "001";        
		/** 
		 * 임시저장         
		 */
		public static final String TEMP_REG             = "002";        
		/** 
		 * 승인요청         
		 */
		public static final String APPROVE_REQ          = "003";        
		/** 
		 * 승인             
		 */
		public static final String APPROVE              = "004";        
		/** 
		 * 승인거부         
		 */
		public static final String APPROVE_REJECT       = "005";        
		/** 
		 * 다운로드 진행중  
		 */
		public static final String DOWNLOAD             = "006";        
		/** 
		 * 다운로드 완료    
		 */
		public static final String DOWNLOAD_COMPLET	    = "007";
	}
	
	/**
	 * 보존기간코드
	 */
	public static final class PreservationTermCode
	{
		/** 
		 * 영구  
		 */
		public static final String FOREVER      = "000";        
		/** 
		 * 1개월 
		 */
		public static final String MONTH        = "001";        
		/** 
		 * 3개월 
		 */
		public static final String THREE_MONTH       = "003";        
		/** 
		 * 6개월 
		 */
		public static final String SIX_MONTH       = "006";        
		/** 
		 * 1년   
		 */
		public static final String YEAR         = "012";        
		/** 
		 * 2년   
		 */
		public static final String TWO_YEAR        = "024";        
		/** 
		 * 3년   
		 */
		public static final String THREE_YEAR        = "036";        
		/** 
		 * 5년   
		 */
		public static final String FIVE_YEAR        = "060";        
		/** 
		 * 10년  
		 */
		public static final String TEN_YEAR       = "120";        
		/** 
		 * 20년  
		 */
		public static final String TWENTY_YEAR	= "240";

	}
	
	/**
	 * 주석구분코드
	 */
	public static final class AnnotCode
	{
		/** 
		 * 명장면      
		 */
		public static final String GOOD_SC = "001";     
		/** 
		 * 방송심의제재    
		 */
		public static final String LIMITED_BY_BROADCASTCENTER = "002";     
		/** 
		 * 사용금지    
		 */
		public static final String NOT_USE = "003";     
		/** 
		 * 담당 PD 확인
		 */
		public static final String CHECK_BY_PD   = "004";
		/** 
		 * 사내심의사항
		 */
		public static final String CHECK_BY_COMPANY   = "005";
		/** 
		 * 심의제한
		 */
		public static final String USE_LIMITED   = "006";
		/** 
		 * 무제한
		 */
		public static final String UNLIMITED   = "007";
	}
	
	/**
	 * 사용제한코드
	 */
	public static final class RiskCode
	{
		/** 
		 * 무제한    
		 */
		public static final String UNLIMIT = "001";     
		/** 
		 * 확인후사용
		 */
		public static final String CONFIRM = "002";     
		/** 
		 * 사용제한  
		 */
		public static final String NOT_USE = "003";
	}
	
	/**
	 * 인제스트 상태코드
	 */
	public static final class IngestStatus
	{
		/** 
		 * 준비    
		 */
		public static final String STAND_BY = "001";    
		/** 
		 * 선택됨  
		 */
		public static final String SELECTED = "002";    
		/** 
		 * 작업중  
		 */
		public static final String WORKING  = "003";    
		/** 
		 * 작업완료
		 */
		public static final String COMPLET  = "004";    
		/** 
		 * 오류발생
		 */
		public static final String ERROR    = "005";		
	}
	
	/**
	 * 작업구분코드
	 */
	public static final class WorkKind
	{
		/** 
		 * 인제스트재지시 
		 */
		public static final String INGEST_RE_ORDER			= "001";
		/** 
		 * 2차아카이브재지시 
		 */
		public static final String SECOND_ARCHIVE_RE_ORDER		= "002";
	}
	
	/**
	 * 다운로드 경로 코드
	 */
	public static final class RestoreLoc{
		
		public static final String REPRESENT ="/restore";
		/**
		 * PDS 다운로드 경로
		 */
		public static final String PDS = "/restore";
		/**
		 * NDS 다운로드 경로 
		 */
		public static final String NDS = "/restore";
		/**
		 * 데정팀 다운로드 경로
		 */
		public static final String DATA_INFO_TEAM = "/restore";
		/**
		 * TAPE_OUT 다운로드 경로
		 */
		public static final String TAPE_OUT = "/restore";
		/**
		 * 계열사 다운로드 경로
		 */
		public static final String GROUP_COMPANY = "/restore";
		
	}
	/**
	 * 다운로드  코드
	 */
	public static final class RestoreGubun{
		/**
		 * PDS 다운로드 
		 */
		public static final String PDS = "001";
		/**
		 * NDS 다운로드
		 */
		public static final String NDS = "002";
		/**
		 * 데정팀 다운로드
		 */
		public static final String DATA_INFO_TEAM = "003";
		/**
		 * 테이프 아웃 다운로드
		 */
		public static final String TAPE_OUT = "004";
		/**
		 * 계열사 다운로드
		 */
		public static final String GROUP_COMPANY = "005";
		
	}
	
	
	/**
	 * 계정동기화 코드
	 */
	public static final class SyncCode
	{
		/** 
		 * PDS 
		 */
		public static final String PDS			= "001";
		/** 
		 * NDS 
		 */
		public static final String NDS		= "002";
		/** 
		 * PDS, NDS둘다 
		 */
		public static final String BOTH		= "003";
	}
	
	
	
	/**
	 * PA 연동 코드
	 */
	public static final class Pacode
	{
		/** 
		 * 신청
		 */
		public static final String INSERT			= "001";
		/** 
		 * 수정 
		 */
		public static final String UPDATE		= "002";
		/** 
		 * 삭제
		 */
		public static final String DELETE		= "003";
		
		/** 
		 * AD 관리(정직원)
		 */
		public static final String RA		= "RA";
		/** 
		 * AD 비관리(정직원)
		 */
		public static final String RB		= "RB";
		/** 
		 * AD 관리(비직원)
		 */
		public static final String SA		= "SA";
		/** 
		 * AD 비관리(비직원)
		 */
		public static final String SB		= "SB";
		/** 
		 * AD 비관리(비직원 공용)
		 */
		public static final String SC	= "SC";
	}
	
	
	
	
	/**
	 * tc 구분자
	 */
	public static final class TcGubun
	{
		/** 
		 * 재생성 
		 */
		public static final String RECREATE		= "001";
		/** 
		 * PDS 
		 */
		public static final String PDS			= "002";
		/** 
		 * 수동아카이브 
		 */
		public static final String MANUAL			= "003";
		/** 
		 * IF CMS 아카이브
		 */
		public static final String IFCMS			= "004";
		
	}
	
	
	
	
	
	/**
	 * 계정구분
	 */
	public static final class EmployeeGubun
	{
		/** 
		 * 정직원 
		 */
		public static final String EMPLOYEE			= "001";
		/** 
		 * 계열사
		 */
		public static final String 	SUBSIDIARY	= "002";
		/** 
		 * 비직원
		 */
		public static final String NONEMPLOYEE		= "003";
	}
	
	
	
	/**
	 * 카트 상태코드(das 2.0)
	 */
	public static final class CartStatus2
	{
		/** 
		 * 신청         
		 */
		public static final String REQ                  = "001";        
		/** 
		 * 승인         
		 */
		public static final String APPROVE             = "002";        
		/** 
		 * 승인취소       
		 */
		public static final String CANCLE          = "003";        
		/** 
		 * 1차D/L승인대기             
		 */
		public static final String FIRSTAPP              = "004";        
		/** 
		 * 2차 D/L승인대기         
		 */
		public static final String SECONDAPP      = "005";        
		/** 
		 * 다운로드 중
		 */
		public static final String DOWNLOAD             = "006";        
		/** 
		 * 다운로드완료  
		 */
		public static final String DOWNLOAD_COMPLET	    = "007";
		/** 
		 * 다운로드오류  
		 */
		public static final String DOWNLOAD_ERR	    = "007";
	}
}
