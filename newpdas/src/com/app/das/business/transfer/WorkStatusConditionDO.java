package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;

/**
 * 작업현황 조회의 매체변환, 주조송출의 조회 조건을 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class WorkStatusConditionDO extends DTO 
{
	/**
	 * 시작일
	 */
	private String fromDate = Constants.BLANK;
	/**
	 * 스토리지
	 */
	private String storage = Constants.BLANK;
	/**
	 * 종료일
	 */
	private String toDate = Constants.BLANK;
	/**
	 * 날짜 구분 ( 촬영일 = '001', 등록일 = '002', 정리완료일 = '003')
	 */
	private String dateKind = Constants.BLANK;
	/**
	 * 정리전여부
	 */
	private String arrangeBfYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 준비중 여부
	 */
	private String startingYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 정리중여부
	 */
	private String arrangeIngYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 정리완료여부
	 */
	private String arrangeCompYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 검수완료여부
	 */
	private String completYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 인제스트 재지시여부
	 */
	private String reOrdersYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 아카이브상태여부
	 */
	private String archiveYn = DASBusinessConstants.YesNo.NO;	
	/**
	 * 2차 아카이브 재지시 여부
	 */
	private String secondArchiveYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 오류 여부
	 */
	private String errorYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 주조 인제스트  
	 */
	private String mcuidYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 편집준비중
	 */
	private String editreadyYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 편집중
	 */
	private String editYn = DASBusinessConstants.YesNo.NO;
	/**
	 * 한 페이지당 보여줄 Row 갯수
	 */
	private int rowPerPage=0;
	
	/**
	 * 현재 페이지
	 */
	private int page=0;
	/**
	 * 정렬 
	 */
	private String sort = Constants.BLANK;
	
	private String sortColume = Constants.BLANK;
	
	private String sortValue = Constants.BLANK;
	/**
	 * 오름차순/내림차순 
	 */
	private String asceding = Constants.BLANK;
	/**
	 * 검색어 
	 */
	private String searchKey = Constants.BLANK;
	/**
	 * 검색 컬럼 
	 */
	private String columnName = Constants.BLANK;
	
	/**
	 * 정렬 순서
	 */
	private String oderby = Constants.BLANK;
	
	private String searchCombo = Constants.BLANK; 
	
	private String searchComboValue = Constants.BLANK;
	// 시작 위치
	private long nStartPos = 0;
	
	// 끝 위치
	private long nEndPos = 0;
	
	private boolean bQueryResultCount = false;
	
	private String Ctgr_l_cd = Constants.BLANK;
	
	private long pgm_id= 0;
	
	
	private long master_id= 0;
	
	
	private long cti_id= 0;
	
	private long totalcount= 0;
	
	
	
	private long start_page= 0;
	private long end_page= 0;
	//2012.4.18 
	/**
	 * 회사  
	 */
	private String cocd = Constants.BLANK;
	
	/**
	 * 채널  
	 */
	private String chennel = Constants.BLANK;
	/**
	 * 제목 
	 */
	private String title = Constants.BLANK;
	
	
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
	
	
	
	
	public long getStart_page() {
		return start_page;
	}
	public void setStart_page(long startPage) {
		start_page = startPage;
	}
	public long getEnd_page() {
		return end_page;
	}
	public void setEnd_page(long endPage) {
		end_page = endPage;
	}
	public long getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(long totalcount) {
		this.totalcount = totalcount;
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
	public long getCti_id() {
		return cti_id;
	}
	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}
	public long getnStartPos() {
		return nStartPos;
	}
	public void setnStartPos(long nStartPos) {
		this.nStartPos = nStartPos;
	}
	public long getnEndPos() {
		return nEndPos;
	}
	public void setnEndPos(long nEndPos) {
		this.nEndPos = nEndPos;
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
	public void setMcuidYn(String mcuidYn) {
		this.mcuidYn = mcuidYn;
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
	public String getStartingYn() {
		return startingYn;
	}
	public void setStartingYn(String startingYn) {
		this.startingYn = startingYn;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public long getPgm_id() {
		return pgm_id;
	}
	public void setPgm_id(long pgmId) {
		pgm_id = pgmId;
	}
	public String getCtgr_l_cd() {
		return Ctgr_l_cd;
	}
	public void setCtgr_l_cd(String ctgr_l_cd) {
		Ctgr_l_cd = ctgr_l_cd;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getAsceding() {
		return asceding;
	}
	public void setAsceding(String asceding) {
		this.asceding = asceding;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getMcuidYn() {
		return mcuidYn;
	}
	public void setMcuid(String mcuidYn) {
		this.mcuidYn = mcuidYn;
	}
	
	public int getRowPerPage() {
		return rowPerPage;
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDateKind() {
		return dateKind;
	}

	public void setDateKind(String dateKind) {
		this.dateKind = dateKind;
	}

	public String getArchiveYn() {
		return archiveYn;
	}

	public void setArchiveYn(String archiveYn) {
		this.archiveYn = archiveYn;
	}

	public String getArrangeBfYn() {
		return arrangeBfYn;
	}

	public void setArrangeBfYn(String arrangeBfYn) {
		this.arrangeBfYn = arrangeBfYn;
	}

	public String getArrangeCompYn() {
		return arrangeCompYn;
	}

	public String getOderby() {
		return oderby;
	}
	public void setOderby(String oderby) {
		this.oderby = oderby;
	}
	public void setArrangeCompYn(String arrangeCompYn) {
		this.arrangeCompYn = arrangeCompYn;
	}

	public String getArrangeIngYn() {
		return arrangeIngYn;
	}

	public void setArrangeIngYn(String arrangeIngYn) {
		this.arrangeIngYn = arrangeIngYn;
	}

	public String getCompletYn() {
		return completYn;
	}

	public void setCompletYn(String completYn) {
		this.completYn = completYn;
	}

	public String getReOrdersYn() {
		return reOrdersYn;
	}

	public void setReOrdersYn(String reOrdersYn) {
		this.reOrdersYn = reOrdersYn;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	public String getSecondArchiveYn() {
		return secondArchiveYn;
	}

	public void setSecondArchiveYn(String secondArchiveYn) {
		this.secondArchiveYn = secondArchiveYn;
	}
	public String getErrorYn() {
		return errorYn;
	}

	public void setErrorYn(String errorYn) {
		this.errorYn = errorYn;
	}
	public String getEditreadyYn() {
		return editreadyYn;
	}
	public void setEditreadyYn(String editreadyYn) {
		this.editreadyYn = editreadyYn;
	}
	public String getEditYn() {
		return editYn;
	}
	public void setEditYn(String editYn) {
		this.editYn = editYn;
	}
	
	public void setStartPos(long nStartPos)
	{
		this.nStartPos = nStartPos;		
	}
	
	public long getStartPos()
	{
		return this.nStartPos;
	}
	
	public void setEndPos(long nEndPos)
	{
		this.nEndPos = nEndPos;
	}
	
	public long getEndPos()
	{
		return this.nEndPos;
	}
	
	public void setQueryResultCount(boolean bQueryResultCount)
	{
		this.bQueryResultCount = bQueryResultCount;
	}
	
	public boolean getQueryResultCount()
	{
		return this.bQueryResultCount;		
	}
	public String getSearchCombo() {
		return searchCombo;
	}
	public void setSearchCombo(String searchCombo) {
		this.searchCombo = searchCombo;
	}
	public String getSearchComboValue() {
		return searchComboValue;
	}
	public void setSearchComboValue(String searchComboValue) {
		this.searchComboValue = searchComboValue;
	}
	public String getSortColume() {
		return sortColume;
	}
	public void setSortColume(String sortColume) {
		this.sortColume = sortColume;
	}
	public String getSortValue() {
		return sortValue;
	}
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
	
}
