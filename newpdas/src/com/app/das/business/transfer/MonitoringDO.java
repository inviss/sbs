package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 모니터링 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class MonitoringDO extends DTO 
{
	/**
	 * 상태
	 */
	private String status = Constants.BLANK;
	/**
	 * 청구코드
	 */
	private String req_cd = Constants.BLANK;
	
	/**
	 * 작업명
	 */
	private String job_nm = Constants.BLANK;
	
	/**
	 * 작업 id
	 */
	private String job_id = Constants.BLANK;
	
	/**
	 * 소재명
	 */
	private String ctgr_l_nm = Constants.BLANK;
	
	/**
	 * 요청시간
	 */
	private String req_dt = Constants.BLANK;
	
	/**
	 * 요청자id
	 */
	private String req_id = Constants.BLANK;
	
	/**
	 * 요청자명
	 */
	private String req_nm = Constants.BLANK;
	
	/**
	 * 미디어id
	 */
	private String media_id = Constants.BLANK;
	
	/**
	 * 우선순위
	 */
	private int priority =0;

	/**
	 * 총갯수
	 */
	private int totalcount =0;

	
	/**
	 * 시작페이지
	 */
	private int start_page =0;

	
	
	//상세 검색 컬럼
	/**
	 * key 값
	 */
	private long  key =0;

	/**
	 * 구분(001 아카이브, 002 tc, 003 tm)
	 */
	private String gubun = Constants.BLANK;

	/**
	 * 제목
	 */
	private String title = Constants.BLANK;

	/**
	 * 방송/촬영일
	 */
	private String brd_dd = Constants.BLANK;

	/**
	 * 진행률
	 */
	private String progress = Constants.BLANK;

	

	/**
	 * 컨텐츠 구분명
	 */
	private String ct_cla_nm = Constants.BLANK;

	/**
	 * 컨텐츠 유형명
	 */
	private String ct_typ_nm = Constants.BLANK;


	/**
	 * 오브젝트명
	 */
	private String obj_name = Constants.BLANK;

	/**
	 * cti_id
	 */
	private long cti_id = 0;

	/**
	 * ct_id
	 */
	private long ct_id = 0;

	/**
	 * master_id
	 */
	private long master_id = 0;

	/**
	 * dtl 유형
	 */
	private String dtl_type = Constants.BLANK;
	
	/**
	 * cart_no
	 */
	private long cart_no = 0;

	/**
	 * cart_seq
	 */
	private long cart_seq = 0;
	
	
	/**
	 * 다운 진행률
	 */
	private String down_progress = Constants.BLANK;
	
	/**
	 * 다운 상태
	 */
	private String down_state = Constants.BLANK;
	
	
	/**
	 * 다운 우선순위
	 */
	private String down_priority = Constants.BLANK;
	
	/**
	 * 변환진행률
	 */
	private String change_progress = Constants.BLANK;
	
	/**
	 * 변환상태
	 */
	private String change_state = Constants.BLANK;
	
	/**
	 * tc 진행률
	 */
	private String tc_progress = Constants.BLANK;
	
	/**
	 * tc 상태
	 */
	private String tc_state = Constants.BLANK;
	
	/**
	 * tm 진행률
	 */
	private String tm_progress = Constants.BLANK;
	
	/**
	 * tm 상태
	 */
	private String tm_state = Constants.BLANK;
	

	/**
	 * tm 우선순위
	 */
	private String tm_priority = Constants.BLANK;
	
	/**
	 * 아카이브 진행률
	 */
	private String archive_progress = Constants.BLANK;
	
	/**
	 * 아카이브 상태
	 */
	private String archive_state = Constants.BLANK;
	


	
	/**
	 * 복본 진행률
	 */
	private String copy_progress = Constants.BLANK;
	
	/**
	 * 복본  상태
	 */
	private String copy_state = Constants.BLANK;
	

	
	/**
	 * 소산 진행률
	 */
	private String backup_progress = Constants.BLANK;
	
	/**
	 * 소산 상태
	 */
	private String backup_state = Constants.BLANK;
	
	/**
	 * 승인상태
	 */
	private String approve_status = Constants.BLANK;
	

	/**
	 * 다운제목
	 */
	private String down_title = Constants.BLANK;
	

	/**
	 * 시작일시
	 */
	private String start_time = Constants.BLANK;
	
	/**
	 * 종료일시
	 */
	private String end_time = Constants.BLANK;
	

	
	

	
	
	
	
	
	
	/**
	 * 검색시작일
	 */
	private String start_search_dd = Constants.BLANK;
	
	/**
	 * 검색종료일
	 */
	private String end_serach_dd = Constants.BLANK;
	
	/**
	 * 키값
	 */
	private String keyid = Constants.BLANK;
	/**
	 * 영상구분
	 */
	private String ct_typ = Constants.BLANK;
	
	
	/**
	 * 계열사명
	 */
	private String conm = Constants.BLANK;
	
	
	
	/**
	 * 타겟스토리지명
	 */
	private String storagenm = Constants.BLANK;
	


	/**
	 * 시작점
	 */
	private String som = Constants.BLANK;
	
	/**
	 * 종료점
	 */
	private String eom = Constants.BLANK;
	
	/**
	 * 사용제한명
	 */
	private String rist_clf_nm = Constants.BLANK;
	
	/**
	 * 승인자명
	 */
	private String approvenm = Constants.BLANK;
	
	/**
	 * 승인구분
	 */
	private String approve_gubun = Constants.BLANK;
	
	
	/**
	 * 외주직원 구분
	 */
	private String out_user_yn = Constants.BLANK;
	
	/**
	 * tc_ip
	 */
	private String tc_ip = Constants.BLANK;
	
	
	
	/**
	 * change_ip
	 */
	private String change_ip = Constants.BLANK;
	
	

	/**
	 * tm_ip
	 */
	private String tm_ip = Constants.BLANK;
	/**
	 * tm 장비명
	 */
	private String tm_nm = Constants.BLANK;
	
	/**
	 * 거절사유
	 */
	private String refuse_cont = Constants.BLANK;
	
	
	/**
	 * tc_id
	 */
	private String tc_id = Constants.BLANK;
	
	

	/**
	 * tc 장비명
	 */
	private String tc_nm = Constants.BLANK;
	
	
	/**
	 * change_장비명
	 */
	private String change_nm = Constants.BLANK;
	
	
	/**
	 * 체널명
	 */
	private String chennel_nm = Constants.BLANK;
	
	
	
	/**
	 * 경로명
	 */
	private String route_nm = Constants.BLANK;
	
	
	public String getRoute_nm() {
		return route_nm;
	}

	public void setRoute_nm(String routeNm) {
		route_nm = routeNm;
	}

	public String getChennel_nm() {
		return chennel_nm;
	}

	public void setChennel_nm(String chennelNm) {
		chennel_nm = chennelNm;
	}

	public String getChange_nm() {
		return change_nm;
	}

	public void setChange_nm(String changeNm) {
		change_nm = changeNm;
	}

	public String getTm_nm() {
		return tm_nm;
	}

	public void setTm_nm(String tmNm) {
		tm_nm = tmNm;
	}

	public String getTc_nm() {
		return tc_nm;
	}

	public void setTc_nm(String tcNm) {
		tc_nm = tcNm;
	}

	public String getTc_id() {
		return tc_id;
	}

	public void setTc_id(String tcId) {
		tc_id = tcId;
	}

	public String getRefuse_cont() {
		return refuse_cont;
	}

	public void setRefuse_cont(String refuseCont) {
		refuse_cont = refuseCont;
	}

	public String getTm_ip() {
		return tm_ip;
	}

	public void setTm_ip(String tmIp) {
		tm_ip = tmIp;
	}

	public String getChange_ip() {
		return change_ip;
	}

	public void setChange_ip(String changeIp) {
		change_ip = changeIp;
	}

	public String getTc_ip() {
		return tc_ip;
	}

	public void setTc_ip(String tcIp) {
		tc_ip = tcIp;
	}

	
	public String getApprove_gubun() {
		return approve_gubun;
	}

	public void setApprove_gubun(String approveGubun) {
		approve_gubun = approveGubun;
	}

	public String getOut_user_yn() {
		return out_user_yn;
	}

	public void setOut_user_yn(String outUserYn) {
		out_user_yn = outUserYn;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String startTime) {
		start_time = startTime;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String endTime) {
		end_time = endTime;
	}

	public String getDown_title() {
		return down_title;
	}

	public void setDown_title(String downTitle) {
		down_title = downTitle;
	}

	public String getConm() {
		return conm;
	}

	public void setConm(String conm) {
		this.conm = conm;
	}

	public String getStoragenm() {
		return storagenm;
	}

	public void setStoragenm(String storagenm) {
		this.storagenm = storagenm;
	}

	public String getSom() {
		return som;
	}

	public void setSom(String som) {
		this.som = som;
	}

	public String getEom() {
		return eom;
	}

	public void setEom(String eom) {
		this.eom = eom;
	}

	public String getRist_clf_nm() {
		return rist_clf_nm;
	}

	public void setRist_clf_nm(String ristClfNm) {
		rist_clf_nm = ristClfNm;
	}

	public String getApprovenm() {
		return approvenm;
	}

	public void setApprovenm(String approvenm) {
		this.approvenm = approvenm;
	}

	public String getCt_typ() {
		return ct_typ;
	}

	public void setCt_typ(String ctTyp) {
		ct_typ = ctTyp;
	}

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approveStatus) {
		approve_status = approveStatus;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

	public String getCopy_progress() {
		return copy_progress;
	}

	public void setCopy_progress(String copyProgress) {
		copy_progress = copyProgress;
	}

	public String getCopy_state() {
		return copy_state;
	}

	public void setCopy_state(String copyState) {
		copy_state = copyState;
	}

	public String getBackup_progress() {
		return backup_progress;
	}

	public void setBackup_progress(String backupProgress) {
		backup_progress = backupProgress;
	}

	public String getBackup_state() {
		return backup_state;
	}

	public void setBackup_state(String backupState) {
		backup_state = backupState;
	}

	public String getReq_cd() {
		return req_cd;
	}

	public void setReq_cd(String reqCd) {
		req_cd = reqCd;
	}

	public long getCart_no() {
		return cart_no;
	}

	public void setCart_no(long cartNo) {
		cart_no = cartNo;
	}

	public long getCart_seq() {
		return cart_seq;
	}

	public void setCart_seq(long cartSeq) {
		cart_seq = cartSeq;
	}

	public String getDown_priority() {
		return down_priority;
	}

	public void setDown_priority(String downPriority) {
		down_priority = downPriority;
	}

	public String getTm_progress() {
		return tm_progress;
	}

	public void setTm_progress(String tmProgress) {
		tm_progress = tmProgress;
	}

	public String getTm_state() {
		return tm_state;
	}

	public void setTm_state(String tmState) {
		tm_state = tmState;
	}

	public String getTm_priority() {
		return tm_priority;
	}

	public void setTm_priority(String tmPriority) {
		tm_priority = tmPriority;
	}

	public String getStart_search_dd() {
		return start_search_dd;
	}

	public void setStart_search_dd(String startSearchDd) {
		start_search_dd = startSearchDd;
	}

	public String getEnd_serach_dd() {
		return end_serach_dd;
	}

	public void setEnd_serach_dd(String endSerachDd) {
		end_serach_dd = endSerachDd;
	}

	public String getDtl_type() {
		return dtl_type;
	}

	public String getDown_progress() {
		return down_progress;
	}

	public void setDown_progress(String downProgress) {
		down_progress = downProgress;
	}

	public String getDown_state() {
		return down_state;
	}

	public void setDown_state(String downState) {
		down_state = downState;
	}

	public String getChange_progress() {
		return change_progress;
	}

	public void setChange_progress(String changeProgress) {
		change_progress = changeProgress;
	}

	public String getChange_state() {
		return change_state;
	}

	public void setChange_state(String changeState) {
		change_state = changeState;
	}

	public String getTc_progress() {
		return tc_progress;
	}

	public void setTc_progress(String tcProgress) {
		tc_progress = tcProgress;
	}

	public String getTc_state() {
		return tc_state;
	}

	public void setTc_state(String tcState) {
		tc_state = tcState;
	}

	public String getArchive_progress() {
		return archive_progress;
	}

	public void setArchive_progress(String archiveProgress) {
		archive_progress = archiveProgress;
	}

	public String getArchive_state() {
		return archive_state;
	}

	public void setArchive_state(String archiveState) {
		archive_state = archiveState;
	}

	public void setDtl_type(String dtlType) {
		dtl_type = dtlType;
	}

	public long getCti_id() {
		return cti_id;
	}

	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}

	public long getCt_id() {
		return ct_id;
	}

	public void setCt_id(long ctId) {
		ct_id = ctId;
	}

	public long getMaster_id() {
		return master_id;
	}

	public void setMaster_id(long masterId) {
		master_id = masterId;
	}

	public String getObj_name() {
		return obj_name;
	}

	public void setObj_name(String objName) {
		obj_name = objName;
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

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getCt_cla_nm() {
		return ct_cla_nm;
	}

	public void setCt_cla_nm(String ctClaNm) {
		ct_cla_nm = ctClaNm;
	}

	public String getCt_typ_nm() {
		return ct_typ_nm;
	}

	public void setCt_typ_nm(String ctTypNm) {
		ct_typ_nm = ctTypNm;
	}

	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getStart_page() {
		return start_page;
	}

	public void setStart_page(int startPage) {
		start_page = startPage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJob_nm() {
		return job_nm;
	}

	public void setJob_nm(String jobNm) {
		job_nm = jobNm;
	}

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String jobId) {
		job_id = jobId;
	}

	public String getCtgr_l_nm() {
		return ctgr_l_nm;
	}

	public void setCtgr_l_nm(String ctgrLNm) {
		ctgr_l_nm = ctgrLNm;
	}

	public String getReq_dt() {
		return req_dt;
	}

	public void setReq_dt(String reqDt) {
		req_dt = reqDt;
	}

	public String getReq_id() {
		return req_id;
	}

	public void setReq_id(String reqId) {
		req_id = reqId;
	}

	public String getReq_nm() {
		return req_nm;
	}

	public void setReq_nm(String reqNm) {
		req_nm = reqNm;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
	
}
