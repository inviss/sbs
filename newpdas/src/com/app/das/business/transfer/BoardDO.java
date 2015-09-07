package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;

/**
 * 게시판 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class BoardDO extends DTO 
{
	/**
	 * 순번
	 */
	private int serialNo;
	/**
	 * 게시판ID
	 */
	private int boardId;
	/**
	 * 게시판종류코드
	 */
	private String boardTypeCd      = Constants.BLANK;
	/**
	 * 대답여부
	 */
	private String answerYn         = Constants.BLANK;
	/**
	 * 제목
	 */
	private String subj             = Constants.BLANK;
	/**
	 * 내용
	 */
	private String cont             = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDt            = Constants.BLANK;
	/**
	 * 수정일
	 */
	private String modDt            = Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrid           = Constants.BLANK;
	/**
	 * 등록자명
	 */
	private String regrNm = Constants.BLANK;
	/**
	 * 수정자ID
	 */
	private String modrid           = Constants.BLANK;
	/**
	 * 읽은횟수
	 */
	private String rdCount 		= Constants.BLANK;
	/**
	 * 본문ID
	 */
	private int mainId;
	/**
	 * 첨부파일 여부
	 */
	private String attachYn        = DASBusinessConstants.YesNo.NO;
	/**
	 * 첨부파일 정보(FileInfoDO)를 포함하고 있는 List
	 */
	private List fileInfoDOList = null;
	/**
	 * 첨부파일 정보(FileInfoDO)를 포함하고 있는 DO
	 */
	private FileInfoDO fileInfoDO = new FileInfoDO();
	
	/**
	 *  팝업여부
	 */
	private String popupyn        = DASBusinessConstants.YesNo.NO;

	/**
	 * 조회구분
	 */
	private String searchKind = Constants.BLANK;
	/**
	 * 조회값
	 */
	private String searchValue = Constants.BLANK;
	/**
	 * 유저명
	 */
	private String user_nm = Constants.BLANK;

	/**
	 * 파일명
	 */
	private String file_nm = Constants.BLANK;
	/**
	 * 파일크기
	 */
	private long file_size;
	/**
	 * 파일크기
	 */
	private String fl_size= Constants.BLANK;
	/**
	 * 파일경로
	 */
	private String file_path = Constants.BLANK;
	/**
	 * 조회할 페이지
	 */
	private int page;
	
	/**
	 * 한 페이지당 갯수
	 */
	private int rowPerPage;
	
	
	
	/**
	 * 팝업시작일
	 */
	private String popup_start_dd = Constants.BLANK;
	/**
	 * 팝업종료일
	 */
	private String popup_end_dd = Constants.BLANK;
	
	
	
	
	
	
	
	public String getFl_size() {
		return fl_size;
	}

	public void setFl_size(String flSize) {
		fl_size = flSize;
	}

	public String getPopup_start_dd() {
		return popup_start_dd;
	}

	public void setPopup_start_dd(String popupStartDd) {
		popup_start_dd = popupStartDd;
	}

	public String getPopup_end_dd() {
		return popup_end_dd;
	}

	public void setPopup_end_dd(String popupEndDd) {
		popup_end_dd = popupEndDd;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String filePath) {
		file_path = filePath;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String userNm) {
		user_nm = userNm;
	}

	public String getFile_nm() {
		return file_nm;
	}

	public long getFile_size() {
		return file_size;
	}

	public void setFile_size(long fileSize) {
		file_size = fileSize;
	}

	public FileInfoDO getFileInfoDO() {
		return fileInfoDO;
	}

	public void setFileInfoDO(FileInfoDO fileInfoDO) {
		this.fileInfoDO = fileInfoDO;
	}

	public void setFile_nm(String fileNm) {
		file_nm = fileNm;
	}


	
	public BoardDO()
	{
		fileInfoDOList = new ArrayList();
	}
	
	public String getAnswerYn() {
		return answerYn;
	}
	public void setAnswerYn(String answerYn) {
		this.answerYn = answerYn;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardTypeCd() {
		return boardTypeCd;
	}
	public void setBoardTypeCd(String boardTypeCd) {
		this.boardTypeCd = boardTypeCd;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getPopupyn() {
		return popupyn;
	}

	public void setPopupyn(String popupyn) {
		this.popupyn = popupyn;
	}

	public int getMainId() {
		return mainId;
	}
	public void setMainId(int mainId) {
		this.mainId = mainId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getRdCount() {
		return rdCount;
	}
	public void setRdCount(String rdCount) {
		this.rdCount = rdCount;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getSubj() {
		return subj;
	}
	public void setSubj(String subj) {
		this.subj = subj;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public String getSearchKind() {
		return searchKind;
	}

	public void setSearchKind(String searchKind) {
		this.searchKind = searchKind;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getAttachYn() {
		return attachYn;
	}

	public void setAttachYn(String attachYn) {
		this.attachYn = attachYn;
	}

	public List getFileInfoDOList() {
		return fileInfoDOList;
	}

	public void setFileInfoDOList(List fileInfoDOList) {
		this.fileInfoDOList = fileInfoDOList;
	}

	public String getRegrNm() {
		return regrNm;
	}

	public void setRegrNm(String regrNm) {
		this.regrNm = regrNm;
	}	

}
