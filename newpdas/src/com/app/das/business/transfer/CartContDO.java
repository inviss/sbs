package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 다운로드 상세 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class CartContDO extends DTO 
{
	/**
	 * 카트번호
	 */
	private long cartNo;
	/**
	 * 카트내순번
	 */
	private int cartSeq;
	/**
	 * 사용제한구분코드
	 */
	private String ristClfCd = Constants.BLANK;
	/**
	 * 콘텐츠ID
	 */
	private long ctId; 
	/**
	 * 콘텐츠인스탄스ID
	 */
	private long ctiId; 
	/**
	 * 마스터id
	 */
	private long master_id; 
	/**
	 * 시작점
	 */
	private String som	= Constants.BLANK;
	/**
	 * 종료점
	 */
	private String eom      = Constants.BLANK;
	/**
	 * duration
	 */
	private long duration;
	/**
	 * 등록일시
	 */
	private String regDt 	= Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrId   = Constants.BLANK;
	/**
	 * 수정일시
	 */
	private String modDt    = Constants.BLANK;
	/**
	 * 수정자ID
	 */
	private String modrId   = Constants.BLANK;
	/**
	 * 대분류코드
	 */
	private String ctgrLCd  = Constants.BLANK;
	/**
	 * 중분류코드
	 */
	private String ctgrMCd  = Constants.BLANK;
	/**
	 * 소분류코드
	 */
	private String ctgrSCd  = Constants.BLANK;
	/**
	 * 콘텐츠내용
	 */
	private String ctCont   = Constants.BLANK;
	/**
	 * 콘텐츠
	 */
	private String ctNm     = Constants.BLANK;
	
	/**
	 * 시작프레임
	 */
	private String sframe     = Constants.BLANK;
	
	/**
	 * 청구번호
	 */
	private String req_cd 	= Constants.BLANK;
	
	/**
	 * 마스터ID
	 */	
	private long masterId;
	
	/**
	 * 프로그램명
	 */
	private String pgm_nm = Constants.BLANK;
	
	/**
	 * 승인 내용
	 */
	private String app_cont = Constants.BLANK;
	
	/**
	 * 사용제한 명
	 */
	private String rist_clf_nm = Constants.BLANK;
	
	/**
	 * 화질코드(001 HD 002 SD)
	 */
	private String vd_qlty = Constants.BLANK;
	
	/**
	 * 종횡비코드(001 16:9 002 4:3)
	 */
	private String asp_rto_cd = Constants.BLANK;
	
	/**
	 * 다운요청상태(001 사용 중 002 임시저장 003 승인요청 004 승인 005 승인거부 \N 006 다운로드 진행중 007 다운로드 완료)
	 */
	private String down_stat = Constants.BLANK;
	
	/**
	 * 외주사직원요청여부(Y/N)
	 */
	private String outsourcing_yn = Constants.BLANK;
	
	/**
	 * 외주제작승인여부(Y:승인 , N:승인취소)
	 */
	private String outsourcing_approve = Constants.BLANK;
	
	/**
	 * 카트제목명
	 */
	private String down_subj = Constants.BLANK;
	
	/**
	 * 화질명
	 */
	private String vd_qlty_nm = Constants.BLANK;
	/**
	 * 종횡비명
	 */
	private String asp_rto_nm = Constants.BLANK;
	/**
	 * 사용제한건수
	 */
	private String use_limit_count = Constants.BLANK;
	/**
	 * 사용제한
	 */
	private String use_limit_flag = Constants.BLANK;
	/**
	 * 타이틀명
	 */
	private String title = Constants.BLANK;
	/**
	 * 회차
	 */
	private String epis_no = Constants.BLANK;
	/**
	 * 방송일/촬영일
	 */
	private String brd_dd = Constants.BLANK;
	/**
	 * 다운진행률
	 */
	private String down_vol = Constants.BLANK;
	/**
	 * 전송진행률
	 */
	private String trans_vol = Constants.BLANK;
	/**
	 * 다운로드 유형명
	 */
	private String down_typ_nm = Constants.BLANK;
	/**
	 * 다운로드 유형 (FULL : F , PARTIAL : P)
	 */
	private String down_typ = Constants.BLANK; 
	/**
	 * 요청 사유명
	 */
	private String req_cont = Constants.BLANK;
	/**
	 * 다운로드 구분명
	 */
	private String down_gubun_nm = Constants.BLANK;
	
	/**
	 * 이용제한 상태
	 */
	private String setUseLimitFlag = Constants.BLANK;
	/**
	 * 다운로드 상태
	 */
	private String setDown_status = Constants.BLANK;
	
	/**
	 *  full or partial
	 */
	private String full_yn = Constants.BLANK;
	
	
	/**
	 *  파일 경로
	 */
	private String fl_path = Constants.BLANK;
	
	/**
	 *  미디어 id
	 */
	private String media_id = Constants.BLANK;
	/**
	 *  파일 명
	 */
	private String file_nm = Constants.BLANK;
	
	/**
	 *  다운로드 일시 
	 */
	private String down_dt = Constants.BLANK;
	
	/**
	 * job_Status ( Q:대기,  I:진행중,  C:완료)
	 */
	private String job_status = Constants.BLANK;
	
	/**
	 * 진행률
	 */
	private String progress = Constants.BLANK;
	
	/**
	 *  촬영일
	 */
	private String fm_dt = Constants.BLANK;
	
	
	/**
	 *  논리적 저장위치
	 */
	private String logical_tree = Constants.BLANK;
	
	/**
	 *  물리적 저장위치
	 */
	private String physical_tree = Constants.BLANK;
	
	
	/**
	 *  경로
	 */
	private String strg_loc = Constants.BLANK;
	
	/**
	 *  WMV 경로
	 */
	private String path = Constants.BLANK;
	
	/**
	 *  wmv 명
	 */
	private String fl_nm = Constants.BLANK;
	
	/**
	 *  승인자 id
	 */
	private String approve_id = Constants.BLANK;
	
	/**
	 *  승인자 명
	 */
	private String approve_nm = Constants.BLANK;
	
	
	//2012.4.27
	/**
	 *  회사 코드
	 */
	private String cocd = Constants.BLANK;
	
	/**
	 *  채널코드
	 */
	private String chennel = Constants.BLANK;
	
	/**
	 * dtl 구분
	 */
	private String dtl_type = Constants.BLANK;
	

	/**
	 * 채널명
	 */
	private String chennel_nm = Constants.BLANK;
	
	/**
	 * 목적지cmsid
	 */
	private String target_cms_id = Constants.BLANK;
	
	
	/**
	 * 트렌스액션 id
	 */
	private String transcation_id = Constants.BLANK;
	
	/**
	 * group ct id
	 */
	private String group_ct_ids = Constants.BLANK;
	
	
	/**
	 * group cti id
	 */
	private String group_cti_ids = Constants.BLANK;
	
	
	/**
	 * group master id
	 */
	private String group_master_id_ids = Constants.BLANK;
	
	
	
	/**
	 * seq
	 */
	private long num = 0;
	
	
	
	
	
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getGroup_ct_ids() {
		return group_ct_ids;
	}
	public void setGroup_ct_ids(String groupCtIds) {
		group_ct_ids = groupCtIds;
	}
	public String getGroup_cti_ids() {
		return group_cti_ids;
	}
	public void setGroup_cti_ids(String groupCtiIds) {
		group_cti_ids = groupCtiIds;
	}
	public String getGroup_master_id_ids() {
		return group_master_id_ids;
	}
	public void setGroup_master_id_ids(String groupMasterIdIds) {
		group_master_id_ids = groupMasterIdIds;
	}
	public String getTranscation_id() {
		return transcation_id;
	}
	public void setTranscation_id(String transcationId) {
		transcation_id = transcationId;
	}
	public String getTarget_cms_id() {
		return target_cms_id;
	}
	public void setTarget_cms_id(String targetCmsId) {
		target_cms_id = targetCmsId;
	}
	public String getChennel_nm() {
		return chennel_nm;
	}
	public void setChennel_nm(String chennelNm) {
		chennel_nm = chennelNm;
	}
	public String getDtl_type() {
		return dtl_type;
	}
	public void setDtl_type(String dtlType) {
		dtl_type = dtlType;
	}
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getChennel() {
		return chennel;
	}
	public void setChennel(String chennel) {
		this.chennel = chennel;
	}
	public String getApprove_nm() {
		return approve_nm;
	}
	public void setApprove_nm(String approveNm) {
		approve_nm = approveNm;
	}
	public String getApprove_id() {
		return approve_id;
	}
	public void setApprove_id(String approveId) {
		approve_id = approveId;
	}
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public String getFl_nm() {
		return fl_nm;
	}
	public void setFl_nm(String flNm) {
		fl_nm = flNm;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStrg_loc() {
		return strg_loc;
	}
	public void setStrg_loc(String strgLoc) {
		strg_loc = strgLoc;
	}
	public String getLogical_tree() {
		return logical_tree;
	}
	public void setLogical_tree(String logicalTree) {
		logical_tree = logicalTree;
	}
	public String getPhysical_tree() {
		return physical_tree;
	}
	public void setPhysical_tree(String physicalTree) {
		physical_tree = physicalTree;
	}
	public String getFm_dt() {
		return fm_dt;
	}
	public void setFm_dt(String fmDt) {
		fm_dt = fmDt;
	}
	public String getJob_status() {
		return job_status;
	}
	public void setJob_status(String job_status) {
		this.job_status = job_status;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String fileNm) {
		file_nm = fileNm;
	}
	public String getDown_dt() {
		return down_dt;
	}
	public void setDown_dt(String down_dt) {
		this.down_dt = down_dt;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public String getFull_yn() {
		return full_yn;
	}
	public void setFull_yn(String fullYn) {
		full_yn = fullYn;
	}
	public String getSetUseLimitFlag() {
		return setUseLimitFlag;
	}
	public void setSetUseLimitFlag(String setUseLimitFlag) {
		this.setUseLimitFlag = setUseLimitFlag;
	}
	public String getSetDown_status() {
		return setDown_status;
	}
	public void setSetDown_status(String setDownStatus) {
		setDown_status = setDownStatus;
	}
	public String getDown_gubun_nm() {
		return down_gubun_nm;
	}
	public void setDown_gubun_nm(String down_gubun_nm) {
		this.down_gubun_nm = down_gubun_nm;
	}
	public String getReq_cont() {
		return req_cont;
	}
	public void setReq_cont(String req_cont) {
		this.req_cont = req_cont;
	}
	public String getDown_typ() {
		return down_typ;
	}
	public void setDown_typ(String down_typ) {
		this.down_typ = down_typ;
	}
	public String getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(String epis_no) {
		this.epis_no = epis_no;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brd_dd) {
		this.brd_dd = brd_dd;
	}
	public String getDown_vol() {
		return down_vol;
	}
	public void setDown_vol(String down_vol) {
		this.down_vol = down_vol;
	}
	public String getTrans_vol() {
		return trans_vol;
	}
	public void setTrans_vol(String trans_vol) {
		this.trans_vol = trans_vol;
	}
	public String getDown_typ_nm() {
		return down_typ_nm;
	}
	public void setDown_typ_nm(String down_typ_nm) {
		this.down_typ_nm = down_typ_nm;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVd_qlty_nm() {
		return vd_qlty_nm;
	}
	public void setVd_qlty_nm(String vd_qlty_nm) {
		this.vd_qlty_nm = vd_qlty_nm;
	}
	public String getDown_subj() {
		return down_subj;
	}
	public void setDown_subj(String down_subj) {
		this.down_subj = down_subj;
	}
	public String getAsp_rto_nm() {
		return asp_rto_nm;
	}
	public void setAsp_rto_nm(String asp_rto_nm) {
		this.asp_rto_nm = asp_rto_nm;
	}
	public String getUse_limit_count() {
		return use_limit_count;
	}
	public void setUse_limit_count(String use_limit_count) {
		this.use_limit_count = use_limit_count;
	}
	public String getUse_limit_flag() {
		return use_limit_flag;
	}
	public void setUse_limit_flag(String use_limit_flag) {
		this.use_limit_flag = use_limit_flag;
	}
	public String getVd_qlty() {
		return vd_qlty;
	}
	public void setVd_qlty(String vd_qlty) {
		this.vd_qlty = vd_qlty;
	}
	public String getAsp_rto_cd() {
		return asp_rto_cd;
	}
	public void setAsp_rto_cd(String asp_rto_cd) {
		this.asp_rto_cd = asp_rto_cd;
	}
	public String getDown_stat() {
		return down_stat;
	}
	public void setDown_stat(String down_stat) {
		this.down_stat = down_stat;
	}
	public String getOutsourcing_yn() {
		return outsourcing_yn;
	}
	public void setOutsourcing_yn(String outsourcing_yn) {
		this.outsourcing_yn = outsourcing_yn;
	}
	public String getOutsourcing_approve() {
		return outsourcing_approve;
	}
	public void setOutsourcing_approve(String outsourcing_approve) {
		this.outsourcing_approve = outsourcing_approve;
	}
	public String getSframe() {
		return sframe;
	}
	public void setSframe(String sframe) {
		this.sframe = sframe;
	}
	public String getReq_cd() {
		return req_cd;
	}
	public void setReq_cd(String req_cd) {
		this.req_cd = req_cd;
	}
	public String getPgm_nm() {
		return pgm_nm;
	}
	public void setPgm_nm(String pgm_nm) {
		this.pgm_nm = pgm_nm;
	}
	public String getApp_cont() {
		return app_cont;
	}
	public void setApp_cont(String app_cont) {
		this.app_cont = app_cont;
	}
	public String getRist_clf_nm() {
		return rist_clf_nm;
	}
	public void setRist_clf_nm(String rist_clf_nm) {
		this.rist_clf_nm = rist_clf_nm;
	}
	public long getCartNo() {
		return cartNo;
	}
	public void setCartNo(long cartNo) {
		this.cartNo = cartNo;
	}
	public int getCartSeq() {
		return cartSeq;
	}
	public void setCartSeq(int cartSeq) {
		this.cartSeq = cartSeq;
	}
	public String getCtCont() {
		return ctCont;
	}
	public void setCtCont(String ctCont) {
		this.ctCont = ctCont;
	}
	public String getCtgrLCd() {
		return ctgrLCd;
	}
	public void setCtgrLCd(String ctgrLCd) {
		this.ctgrLCd = ctgrLCd;
	}
	public String getCtgrMCd() {
		return ctgrMCd;
	}
	public void setCtgrMCd(String ctgrMCd) {
		this.ctgrMCd = ctgrMCd;
	}
	public String getCtgrSCd() {
		return ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	public long getCtId() {
		return ctId;
	}
	public void setCtId(long ctId) {
		this.ctId = ctId;
	}
	public long getCtiId() {
		return ctiId;
	}
	public void setCtiId(long ctiId) {
		this.ctiId = ctiId;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModrId() {
		return modrId;
	}
	public void setModrId(String modrId) {
		this.modrId = modrId;
	}
	public String getRegDt() {
		return regDt;
	}
	public String getFl_path() {
		return fl_path;
	}
	public void setFl_path(String flPath) {
		fl_path = flPath;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegrId() {
		return regrId;
	}
	public void setRegrId(String regrId) {
		this.regrId = regrId;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}
	
	public String getSFrame() {
		return sframe;
	}
	public void setSFrame(String sframe) {
		this.sframe = sframe;
	}
	
	public String getReq_CD()
	{
		return req_cd;
	}
	
	public void setReq_CD(String req_cd)
	{
		this.req_cd = req_cd;
	}
	
	
}
