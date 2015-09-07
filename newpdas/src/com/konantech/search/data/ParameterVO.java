package com.konantech.search.data;

/** 파라미터  Value Object.
 * @author KONAN Technology
 * @since 2010.04.01
 * @version 1.0
 */

public class ParameterVO {

    /** 검색키워드 */
    private String kwd;
    
    /** 이전검색어 배열 */
    public String[] preKwds;
    
    /** 검색 카테고리(탭) */
    private String category;
		
	/** 검색대상 필드 */
	private String srchFd;

	/** 상세검색 키워드 */
	private String[] grpKwd;
	
	/** 상세검색 대상필드 */
	private String[] grpSrchFd;
	
	/** 상세검색 AndOr 구분 */
	private String[] grpAndOr;
	
	/** 상세검색 날짜 구분(시작일) */
	private String[] grpStartDD;
	/** 상세검색 날짜 구분(종료일) */
	private String[] grpEndDD;
	/** 유저 ID */
	private String userId;
	
	/** 사이트명 */
	private String siteName;

    /** 추천검색어 정보 */
    private String recKwd;

    /** 재검색 여부 (boolean) */
    private boolean reSrchFlag;

    /** 페이지사이즈 */
    private int pageSize;
    
    /** 검색결과페이지번호 */
    private int pageNum;
    
    /** 정렬 */
    private String sort;

	/** 상세검색 여부 플래그 */
    private boolean detailSearch;
    
	/** 제외어 */
    private String exclusiveKwd;
  
	/** 날짜선택사항 */
    private String date;

	/** 시작일 */
	private String startDate;
	
	/** 종료일 */
	private String endDate;
	
	/** 시나리오 */
	private String scn;
	
	/** 주제영상 구분 */
	private String sceanGubun;
	
	/** 정렬 컬럼명 */
	private String sortColunm;
	/** 프로그램 ,소재 구분 */
	private String ctgrlcd="";
	
	/** 검색루트 구분 */
	private String browser="";
	
	/** 채널 구분 */
	private String[] channel_cd={};
	
	/** 프로그램 구분 */
	private String[] program_yn={};
	
	/** 소재 구분 */
	private String[] material_yn={};
	

	
	

	public String[] getChannel_cd() {
		return channel_cd;
	}

	public void setChannel_cd(String[] channelCd) {
		channel_cd = channelCd;
	}

	public String[] getProgram_yn() {
		return program_yn;
	}

	public void setProgram_yn(String[] programYn) {
		program_yn = programYn;
	}

	public String[] getMaterial_yn() {
		return material_yn;
	}

	public void setMaterial_yn(String[] materialYn) {
		material_yn = materialYn;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getCtgrlcd() {
		return ctgrlcd;
	}

	public void setCtgrlcd(String ctgrlcd) {
		this.ctgrlcd = ctgrlcd;
	}

	public String getSortColunm() {
		return sortColunm;
	}

	public void setSortColunm(String sortColunm) {
		this.sortColunm = sortColunm;
	}

	public String[] getGrpStartDD() {
		return grpStartDD;
	}

	public void setGrpStartDD(String[] grpStartDD) {
		this.grpStartDD = grpStartDD;
	}

	public String[] getGrpEndDD() {
		return grpEndDD;
	}

	public void setGrpEndDD(String[] grpEndDD) {
		this.grpEndDD = grpEndDD;
	}

	public String getSceanGubun() {
		return sceanGubun;
	}

	public void setSceanGubun(String sceanGubun) {
		this.sceanGubun = sceanGubun;
	}

	public String[] getGrpAndOr() {
		return grpAndOr;
	}

	public void setGrpAndOr(String[] grpAndOr) {
		this.grpAndOr = grpAndOr;
	}

	public String[] getGrpKwd() {
		return grpKwd;
	}

	public void setGrpKwd(String[] grpKwd) {
		this.grpKwd = grpKwd;
	}

	public String[] getGrpSrchFd() {
		return grpSrchFd;
	}

	public void setGrpSrchFd(String[] grpSrchFd) {
		this.grpSrchFd = grpSrchFd;
	}

	public String getScn() {
		return scn;
	}

	public void setScn(String scn) {
		this.scn = scn;
	}

	public String getKwd() {
		return kwd;
	}

	public void setKwd(String kwd) {
		this.kwd = kwd;
	}
	
	public String[] getPreKwds() {
		return preKwds;
	}

	public void setPreKwds(String[] preKwds) {
		this.preKwds = preKwds;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRecKwd() {
		return recKwd;
	}

	public void setRecKwd(String recKwd) {
		this.recKwd = recKwd;
	}

	public boolean getReSrchFlag() {
		return reSrchFlag;
	}

	public void setReSrchFlag(boolean reSrchFlag) {
		this.reSrchFlag = reSrchFlag;
	}

	public String getSrchFd() {
		return srchFd;
	}

	public void setSrchFd(String srchFd) {
		this.srchFd = srchFd;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
    public boolean getDetailSearch() {
		return detailSearch;
	}

	public void setDetailSearch(boolean detailSearch) {
		this.detailSearch = detailSearch;
	}
	
	public String getExclusiveKwd() {
		return exclusiveKwd;
	}

	public void setExclusiveKwd(String exclusiveKwd) {
		this.exclusiveKwd = exclusiveKwd;
	}	
	  
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}

