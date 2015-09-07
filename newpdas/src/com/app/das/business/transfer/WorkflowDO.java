package com.app.das.business.transfer;


/**
 * 사용 정보를 포함하고있는 datdObject
 * @author asura207
 *
 */
public class WorkflowDO {

	/** 마스터 ID */
	private String master_id = "";
	
	/** 프로그램 ID */
	private String pgm_id = "";
	
	/** 제목 */
	private String title = "";
	
	/** 방송일 */
	private String brd_dd = "";
	
	/** 촬영일 */
	private String fm_dd = "";
	
	/** 등록일 */
	private String reg_dt = "";
	
	/** 길이 */
	private String brd_leng = "";
	/** 분류명 */
	private String desc = "";
	/** 등록자 id */
	private String user_id = "";
	
	
	/** 이용횟수 */
	private String use_cont = "";
	/** 이용플래그 */
	private String use_flag = "";
	
	/** 복본여부 */
	private String copy_object_yn = "";
	/** 이용횟수 시작 */
	private String use_start_cont = "";
	/** 이용횟수 종료 */
	private String use_end_cont = "";
	/** 등록자 id */
	private String reg_id = "";
	/** outsystemid */
	private String out_sys_id = "";
	
	/** 조건값  */
	private String start_reg_dt = "";
	private String end_reg_dt = "";
	private String start_brd_dd = "";
	private String end_brd_dd = "";
	private String clf_cd = "";
	private String flag = "";
	private String etc = "";
	private String ctgr_l_cd = "";
	/** 컨텐츠 인스턴스 ID */
	private long cti_id=0;
	
	/** 시작페이지 */
	private long start_page=0;
	
	/** 페이지 */
	private int page=0;
	
	/** 총조회페이지 */
	private int total=0;
	/** 영상 id */
	private long ct_id=0;
	/** 회차 */
	private long epis_no=0;
	/** 회차 */
	private String episno= "";

	/** 총길이 */
	private long total_leng=0;
	
	/** master_ids */
	private String master_ids = "";

	
	
	//2012.5.14
	/** 소산 여부 */
	private String backup_yn = "";

	/** 소산 신청자 */
	private String backup_id = "";

	/** 소산 신청일 */
	private String backup_dt = "";

	
	/** 구분 001 : 복본 002 : 소산 003 : 복원 */
	private String gubun = "";

	
	/** dtl_type */
	private String dtl_type = "";

	
	
	
	
	public String getDtl_type() {
		return dtl_type;
	}

	public void setDtl_type(String dtlType) {
		dtl_type = dtlType;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getBackup_yn() {
		return backup_yn;
	}

	public void setBackup_yn(String backupYn) {
		backup_yn = backupYn;
	}

	public String getBackup_id() {
		return backup_id;
	}

	public void setBackup_id(String backupId) {
		backup_id = backupId;
	}

	public String getBackup_dt() {
		return backup_dt;
	}

	public void setBackup_dt(String backupDt) {
		backup_dt = backupDt;
	}

	public String getEpisno() {
		return episno;
	}
	
	public void setEpisno(String episno) {
		this.episno = episno;
	}
	public String getStart_brd_dd() {
		return start_brd_dd;
	}

	public void setStart_brd_dd(String startBrdDd) {
		start_brd_dd = startBrdDd;
	}

	public String getEnd_brd_dd() {
		return end_brd_dd;
	}

	public void setEnd_brd_dd(String endBrdDd) {
		end_brd_dd = endBrdDd;
	}

	public String getMaster_ids() {
		return master_ids;
	}

	public void setMaster_ids(String masterIds) {
		master_ids = masterIds;
	}

	public long getTotal_leng() {
		return total_leng;
	}

	public void setTotal_leng(long totalLeng) {
		total_leng = totalLeng;
	}

	public long getEpis_no() {
		return epis_no;
	}

	public void setEpis_no(long episNo) {
		epis_no = episNo;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public long getCt_id() {
		return ct_id;
	}

	public void setCt_id(long ctId) {
		ct_id = ctId;
	}

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String regId) {
		reg_id = regId;
	}

	public String getOut_sys_id() {
		return out_sys_id;
	}

	public void setOut_sys_id(String outSysId) {
		out_sys_id = outSysId;
	}

	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}

	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getStart_page() {
		return start_page;
	}

	public void setStart_page(long startPage) {
		start_page = startPage;
	}

	public long getCti_id() {
		return cti_id;
	}

	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getUse_start_cont() {
		return use_start_cont;
	}

	public void setUse_start_cont(String useStartCont) {
		use_start_cont = useStartCont;
	}

	public String getUse_end_cont() {
		return use_end_cont;
	}

	public void setUse_end_cont(String useEndCont) {
		use_end_cont = useEndCont;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStart_reg_dt() {
		return start_reg_dt;
	}

	public void setStart_reg_dt(String startRegDt) {
		start_reg_dt = startRegDt;
	}

	public String getUse_flag() {
		return use_flag;
	}

	public void setUse_flag(String useFlag) {
		use_flag = useFlag;
	}

	public String getEnd_reg_dt() {
		return end_reg_dt;
	}

	public void setEnd_reg_dt(String endRegDt) {
		end_reg_dt = endRegDt;
	}

	public String getClf_cd() {
		return clf_cd;
	}

	public void setClf_cd(String clfCd) {
		clf_cd = clfCd;
	}

	public String getScl_cd() {
		return scl_cd;
	}

	public void setScl_cd(String sclCd) {
		scl_cd = sclCd;
	}

	private String scl_cd = "";
	
	
	public String getMaster_id() {
		return master_id;
	}

	public void setMaster_id(String masterId) {
		master_id = masterId;
	}

	public String getPgm_id() {
		return pgm_id;
	}

	

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setPgm_id(String pgmId) {
		pgm_id = pgmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrd_dd() {
		return brd_dd;
	}

	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}

	public String getFm_dd() {
		return fm_dd;
	}

	public void setFm_dd(String fmDd) {
		fm_dd = fmDd;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}

	public String getBrd_leng() {
		return brd_leng;
	}

	public void setBrd_leng(String brdLeng) {
		brd_leng = brdLeng;
	}

	public String getUse_cont() {
		return use_cont;
	}

	public void setUse_cont(String useCont) {
		use_cont = useCont;
	}

	public String getCopy_object_yn() {
		return copy_object_yn;
	}

	public void setCopy_object_yn(String copyObjectYn) {
		copy_object_yn = copyObjectYn;
	}
	
	
	
	
	
}
