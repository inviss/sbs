package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;


/**
 *  마스터 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class MetaInfoDO extends DTO 
{
	/**
	 * 수정가능여부
	 */
	private String canModify = Constants.BLANK;
	/** 
	 * 마스터 ID    
	 */
	private long masterId;    
	/** 
	 * 자료 상태 코드   
	 */
	private String dataStatCd = Constants.BLANK;
	/** 
	 * 코드설명
	 */
	private String desc = Constants.BLANK;  
	/** 
	 * 타이틀      
	 */ 
	private String title = Constants.BLANK;
	/**
	 * 방송길이
	 */
	private String brdLeng = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDt = Constants.BLANK;
	/**
	 * 아카이브 등록일
	 */
	private String archRegDd = Constants.BLANK;
	/**
	 * 카운트 
	 */
	private int count;
	/**
	 * 에피소드 번호
	 */
	private    int       episNo;
	/**
	 * 에피소드 번호
	 */
	private    String       epis_No= Constants.BLANK;
	/**
	 * 방송일
	 */
	private    String       brdDd     = Constants.BLANK;
	/**
	 * 요청 코드
	 */
	private    String       reqCd     = Constants.BLANK;
	/**
	 * 수정자 ID
	 */
	private    String       modrid     = Constants.BLANK;
	/**
	 * 수정자 명
	 */
	private    String       user_nm     = Constants.BLANK;
	/**
	 * 인제스트 일자
	 */
	private 	String		ING_REG_DD = Constants.BLANK; 		
	/**
	 * 촬영일
	 */
	private 	String		FM_DT = Constants.BLANK; 		
	
	/**
	 * 컨텐츠 id
	 */
	private 	long		ct_id; 		
	
	private		String		lock_stat_cd = Constants.BLANK;
	private		String		error_stat_cd = Constants.BLANK;
	
	private     String      mcuid=Constants.BLANK;
	
	private		String		tape_item_id = Constants.BLANK;
	
	private		int			nQueryResultCount = 0;
	
	private 	long 		sum_brd_leng = 0L;
	/**
	 * 프로그램id
	 */
	private long pgm_id;
	
	/**
	 * 대분류 코드
	 */
	private    String       ctgr_l_Cd     = Constants.BLANK;
	
	/**
	 * 영상 인스턴스 아이디	 */
	private    long       cti_id;
	/**
	 * 대분류 명
	 */
	private    String       ctgr_l_nm     = Constants.BLANK;
	/**
	 * 콘텐츠 구분
	 */
	private    String       ct_cla     = Constants.BLANK;
	
	/**
	 * 스토리지 
	 */
	private    String       storage     = Constants.BLANK;
	/**
	 * 관련영상 존재여부
	 */
	private    String       link_parent     = Constants.BLANK;
	
	/**
	 * 전체 페이지
	 */
	private    long total_page     =0;
	
	/**
	 * 전체 페이지
	 */
	private    long total_count     =0;
	
	
	
	/**
	 * 방송/촬영일 
	 */
	private String brd_dd = Constants.BLANK;
	
	
	/**
	 * 장르 대분류 
	 */
	private String ctgr_l_cd = Constants.BLANK;
	
	
	/**
	 * 아카이브 상태 
	 */
	private String arch_stat = Constants.BLANK;
	
	
	/**
	 * 진행률 
	 */
	private String progress = Constants.BLANK;
	
	/**
	 * 요청일 
	 */
	private String req_dt = Constants.BLANK;
	/**
	 * 요청시작일 
	 */
	private String start_req_dt = Constants.BLANK;
	/**
	 * 요청종료일 
	 */
	private String end_req_dt = Constants.BLANK;
	/**
	 * 요청자id 
	 */
	private String req_id = Constants.BLANK;
	
	
	
	public String getCanModify() {
		return canModify;
	}
	public void setCanModify(String canModify) {
		this.canModify = canModify;
	}
	public long getTotal_count() {
		return total_count;
	}
	public void setTotal_count(long totalCount) {
		total_count = totalCount;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}
	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
	}
	public String getArch_stat() {
		return arch_stat;
	}
	public void setArch_stat(String archStat) {
		arch_stat = archStat;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getReq_dt() {
		return req_dt;
	}
	public void setReq_dt(String reqDt) {
		req_dt = reqDt;
	}
	public String getStart_req_dt() {
		return start_req_dt;
	}
	public void setStart_req_dt(String startReqDt) {
		start_req_dt = startReqDt;
	}
	public String getEnd_req_dt() {
		return end_req_dt;
	}
	public void setEnd_req_dt(String endReqDt) {
		end_req_dt = endReqDt;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String reqId) {
		req_id = reqId;
	}
	public long getTotal_page() {
		return total_page;
	}
	public void setTotal_page(long totalPage) {
		total_page = totalPage;
	}
	public int getnQueryResultCount() {
		return nQueryResultCount;
	}
	public void setnQueryResultCount(int nQueryResultCount) {
		this.nQueryResultCount = nQueryResultCount;
	}
	public long getSum_brd_leng() {
		return sum_brd_leng;
	}
	public void setSum_brd_leng(long sumBrdLeng) {
		sum_brd_leng = sumBrdLeng;
	}
	public String getEpis_No() {
		return epis_No;
	}
	public void setEpis_No(String episNo) {
		epis_No = episNo;
	}
	public String getLink_parent() {
		return link_parent;
	}
	public void setLink_parent(String linkParent) {
		link_parent = linkParent;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String userNm) {
		user_nm = userNm;
	}
	public String getING_REG_DD() {
		return ING_REG_DD;
	}
	public void setING_REG_DD(String iNGREGDD) {
		ING_REG_DD = iNGREGDD;
	}
	public String getFM_DT() {
		return FM_DT;
	}
	public void setFM_DT(String fMDT) {
		FM_DT = fMDT;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getCtgr_l_nm() {
		return ctgr_l_nm;
	}
	public void setCtgr_l_nm(String ctgrLNm) {
		ctgr_l_nm = ctgrLNm;
	}
	public String getCt_cla() {
		return ct_cla;
	}
	public void setCt_cla(String ctCla) {
		ct_cla = ctCla;
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
	public String getCtgr_l_Cd() {
		return ctgr_l_Cd;
	}
	public void setCtgr_l_Cd(String ctgrLCd) {
		ctgr_l_Cd = ctgrLCd;
	}
	public long getPgm_id() {
		return pgm_id;
	}
	public void setPgm_id(long pgmId) {
		pgm_id = pgmId;
	}

	public void setTape_item_id(String tape_item_id)
	{
		this.tape_item_id = tape_item_id;
	}
	public String getTape_item_id()
	{
		return tape_item_id;
	}
	
	public String getMcuid()
	{
		return mcuid;
	}
	
	public void setMcuid(String mcuid)
	{
		this.mcuid = mcuid;
	}
	
	public String getModrid()
	{
	   return  modrid;
	}
	public void setModrid(String modrid)
	{
	   this.modrid = modrid;
	}
	public String getReqCd()
	{
	   return  reqCd;
	}
	public void setReqCd(String reqCd)
	{
	   this.reqCd = reqCd;
	}
	public int getEpisNo()
	{
	   return  episNo;
	}
	public void setEpisNo(int episNo)
	{
	   this.episNo = episNo;
	}
	public String getBrdDd()
	{
	   return  brdDd;
	}
	public void setBrdDd(String brdDd)
	{
	   this.brdDd = brdDd;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}
	public String getDataStatCd() {
		return dataStatCd;
	}
	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrdLeng() {
		return brdLeng;
	}
	public void setBrdLeng(String brdLeng) {
		this.brdLeng = brdLeng;
	}
	public String getArchRegDd() {
		return archRegDd;
	}
	public void setArchRegDd(String archRegDd) {
		this.archRegDd = archRegDd;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getIng_Reg_DD()
	{
	   return  ING_REG_DD;
	}
	public void setIng_Reg_DD(String ING_REG_DD)
	{
	   this.ING_REG_DD = ING_REG_DD;
	}
	
	public String getLock_stat_cd()
	{
	   return  lock_stat_cd;
	}
	public void setLock_stat_cd(String lock_stat_cd)
	{
	   this.lock_stat_cd = lock_stat_cd;
	}
	
	// 에러코드 관련
	public String getError_stat_cd()
	{
	   return  error_stat_cd;
	}
	public void setError_stat_cd(String error_stat_cd)
	{
	   this.error_stat_cd = error_stat_cd;
	}
	public int getQueryResultCount()
	{
		return nQueryResultCount;
	}
	
	public void setQueryResultCount(int nQueryResultCount)
	{
		this.nQueryResultCount = nQueryResultCount;
	}
}

