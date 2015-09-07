package com.sbs.das.commons.system;


public interface DASConstants {

	
	//static final public String  DAS_DB_RESOURCE_NAME = "java:comp/env/jdbc/DASDB";
	static final public String  DAS_DB_RESOURCE_NAME = "dasdb";
	
	// for DAS Archive prifix
	static final public String  DAS_ARCHIVE_PRIFIX = "DAS";
	
	static final public String  HIGH_STORAGE_PRIFIX = "/mp2"; // V
	static final public String  LOW_STORAGE_PRIFIX = "/mp4"; // V
	static final public String  NET_LOW_STORAGE_PRIFIX = "/net_mp4"; // V
	static final public String  NET_STORAGE_PRIFIX = "/nearline"; // V
	
	// Diva Storage Drive
	static final public String  DAS_STORAGE_DRIVE = "V:"; // V
	static final public String  PDS_STORAGE_DRIVE = "X:"; // V
	static final public String  NET_STORAGE_DRIVE = "X:"; // V

	// for LDAP access
	static public String PROP_LDAP_URL = "LDAP.URL";
	static public String PROP_LDAP_USER = "LDAP.USER";
	static public String PROP_LDAP_PASSWORD = "LDAP.PASSWORD";

	// for NDS DB access
	static public String PROP_NDSDB_URL = "NDS.COMMDB.URL";
	static public String PROP_NDSDB_USER = "NDS.COMMDB.USER";
	static public String PROP_NDSDB_PASSWORD = "NDS.COMMDB.PASSWORD";
	
	// for DAS DB access
	static public String PROP_DASDB_URL = "DAS.DB.URL";
	static public String PROP_DASDB_USER = "DAS.DB.USER";
	static public String PROP_DASDB_PASSWORD = "DAS.DB.PASSWORD";
	
	// Business Logic related
	static public String CONFIG_RETRY_INTERVAL = "DAS.SVC.retryInterval";
	static public String CONFIG_BASIC_CORNER_TYPE = "DAS.SVC.BasicCornerType";
	static public String CONFIG_SGL_NUMBER = "SGL.Achiver.Number";
	static public String CONFIG_FILEINGESTER_NUMBER = "Archive.Scheduler.Number";
	static public String CONFIG_JOB_FILEINGEST = "Job.File.Ingest";
	static public String CONFIG_JOB_SGL = "Job.SGL.Clf";
	static public String JOB_FILEINGEST = "C00";
	static public String JOB_SGL = "D00";
	static public String JOB_ARCHIVESCHEDULER = "Q00";
	

	// Web Service related

	static public String MEDIAHUB_SVC_PROFILEID = "MediaHub.ProfileID";
	static public String MEDIAHUB_SVC_CSDID = "MediaHub.CSDID";
	static public String MEDIAHUB_SVC_URL = "MediaHub.URL";
	static public String MEDIAHUB_SVC_URN_ARCHIVE = "MediaHub.URN.Archive";
	static public String MEDIAHUB_SVC_URN_ARCHIVE_SCHEDULER = "MediaHub.URN.ArchiveScheduler";
	static public String MEDIAHUB_SVC_URN_DATABASE = "MediaHub.URN.Database";
	static public String MEDIAHUB_SVC_URN_FILE_INGEST = "MediaHub.URN.FileIngest";
	static public String JEUS_SVC_STATUS_REPORT_ADDRESS = "Jeus.StatusReport.Address";
	
	/*
	 * General
	 */
	// CODE (구분코드)
	static public String	CLF_CD_Media = "P001";				// 매체코드
	static public String	CLF_CD_LargeCategory = "P002";		// 대분류코드
	static public String	CLF_CD_MediumCategory = "P003";		// 중분류코드
	static public String	CLF_CD_SmallCategory = "P004";		// 소분류코드
	static public String	CLF_CD_AuthoringType = "P005";		// 제작구분코드
	static public String	CLF_CD_ArchiveLocation = "P006";	// 보관장소코드
	//static public String	CLF_CD_JobPriority = "P007";		// 작업우선순위코드
	static public String	CLF_CD_TapeType = "P008";			// 테이프매체종류코드
	static public String	CLF_CD_Collector = "P009";			// 수집처코드
	static public String	CLF_CD_CollectionType = "P010";		// 수집구분코드
	static public String	CLF_CD_DataStatus = "P011";			// 자료상태코드
	static public String	CLF_CD_ArchivePeriod = "P012";		// 보존기간코드
	static public String	CLF_CD_Copyright = "P013";			// 저작권형태코드
	static public String	CLF_CD_Deliberation = "P014";		// 심의결과코드
	static public String	CLF_CD_AttachFileType = "P015";		// 첨부파일유형코드
	static public String	CLF_CD_ConerType = "P016";			// 코너유형코드
	static public String	CLF_CD_WatchLevel = "P017";			// 시청등급코드
	static public String	CLF_CD_CommentType = "P018";		// 주석구분코드
	static public String	CLF_CD_BoardType = "P019";			// 게시판종류코드
	static public String	CLF_CD_AnswerYN = "P020";			// 답하기여부
	static public String	CLF_CD_UseRestractionType = "P021";	// 사용제한구분코드
	static public String 	CLF_CD_UID_PREFIX = "P053";
	
	
	
	static public String	CLF_CD_ContentsCategory = "A001";	// 콘텐츠구분코드
	static public String	CLF_CD_ContentsType = "A002";		// 컨텐츠유형코드
	static public String	CLF_CD_Usage = "A003";				// 사용여부코드
	static public String	CLF_CD_OwnerDepartment = "A004";	// 주관부서코드
	static public String	CLF_CD_ImageQuality = "A005";		// 화질코드
	static public String	CLF_CD_HorVerRatio = "A006";		// 종횡비코드
	static public String	CLF_CD_KeyframeExtraction = "A007";	// 키프레임자동추출코드
	static public String	CLF_CD_KeyframeResolution = "A008";	// 키프레임해상도코드
	static public String	CLF_CD_ContentsInstanceFormat = "A009";		// 컨텐츠인스턴스코드
	static public String	CLF_CD_RecordingType = "A010";		// 녹음방식코드
	static public String	CLF_CD_AudioLanguage = "A011";		// 오디오언어코드
	static public String	CLF_CD_Color = "A012";				// 색상코드
	static public String	CLF_CD_MESeperation = "A013";		// ME분리코드
	static public String	CLF_CD_Archive = "A014";			// 아카이브여부코드
	static public String	CLF_CD_DropFrame = "A015";			// 드롭프레임코드
	static public String	CLF_CD_NoiseReductionType = "A016";	// 잡음저감유형코드
	static public String	CLF_CD_EncodingQuality = "A017";	// 인코딩품질코드
	static public String	CLF_CD_PhotoType = "A018";			// 사진종류코드
	static public String	CLF_CD_CN_Type = "P016";
	static public String	CLF_CD_BulletineType = "P019";		// 게시판종류코드
	static public String	CLF_CD_QnA = "P020";				// 답하기여부코드
	static public String	CLF_CD_UsageLimitationType = "P021";// 사용제한구분코드
	static public String	CLF_CD_DiscardType = "P022";		// 폐기구분코드
	static public String	CLF_CD_DataType = "A019";			// 자료구분코드
	static public String	CLF_CD_DASDeviceType = "A021";		// DAS장치구분코드
	static public String	CLF_CD_DASJobStatus = "A022";		// DAS작업상태코드
	static public String	CLF_CD_AttachType = "P023";			// 첨부구분코드
	static public String	CLF_CD_FinalBroadcast = "P024";		// 최종방송여부코드
	static public String	CLF_CD_M2Storage = "A046";
	static public String	CLF_CD_M4Storage = "A047";
	static public String	CLF_CD_ArchiveStorage = "A048";
	
	// Programming Support
	static public String	DATE_FORMAT_NUMBER_ONLY = "yyyyMMdd";	// 19991212 형식
	static public String	DATE_FORMAT_DOT_STYLE = "yyyy.MM.dd";	// 1999.12.12 형식
	static public String	DATE_FORMAT_DASH_STYLE = "yyyy/MM/dd";	// 1999/12/12 형식
	static public String	DATE_FORMAT_HYPN_STYLE = "yyyy-MM-dd";	// 1999-12-12 형식
	static public String	DATE_TIME_FORMAT_NUMBER_ONLY = "yyyyMMddhhmmss";	// 19991212245959 형식
	
	// Etc
	static public String	DEFAULT_CHAR_FORMAT = "utf-8";
	
	// Archive Config
	static public String	DEFAULT_ARCH_CATEGORY = "cms";
	static public String	DEFAULT_ARCH_SOURCE = "archive";
	static public String	DEFAULT_ARCH_DESTINATION = "archive";
	static public Integer	DEFAULT_ARCH_QOS = 3; // 0: direct, 3: cache
	static public Integer	DEFAULT_NON_ARCH_QOS = 0; // Non Archive is 0
	static public Integer	DEFAULT_BACKUP_PRIORITY = 40;
	static public Integer	DEFAULT_RESTORE_PRIORITY = 70;
	static public Integer	DEFAULT_DELETE_PRIORITY = 90;
	static public String	DEFAULT_GROUP_COPY = "archive_copy";
	static public String	DEFAULT_GROUP_ARCH = "archive";
}
