package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 첨부파일 관련 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class AttachFileInfoDO extends DTO{
	
	/**
	 * 첨부 파일 이름
	 */
	private String fileName           = Constants.BLANK;
	/**
	 * 첨부 파일 사이즈
	 */
	private long	fileSize; 
	/**
	 * 첨부 파일 경로
	 */
	private String filePath         = Constants.BLANK;
	/**
	 * 상세설명
	 */
	private String desc         = Constants.BLANK;
	/**
	 * 순번
	 */
	private int	seq;
	/**
	 * 모자료 ID
	 */
	private    long       mothrDataId;
	/**
	 * 첨부파일 유형 코드
	 */
	private    String       attcFileTypeCd     = Constants.BLANK;
	/**
	 * 첨부 구분 코드
	 */
	private    String       attcClfCd     = Constants.BLANK;
	/**
	 * 원파일 명
	 */
	private    String       orgFileNm     = Constants.BLANK;
	/**
	 * 등록 일시
	 */
	private    String       regDt     = Constants.BLANK;
	/**
	 * 등록자 ID
	 */
	private    String       regrid     = Constants.BLANK;
	/**
	 * 수정 일시
	 */
	private    String       modDt     = Constants.BLANK;
	/**
	 * 수정자 ID
	 */
	private    String       modrid     = Constants.BLANK;

	
	public long getMothrDataId()
	{
	   return  mothrDataId;
	}
	public void setMothrDataId(long mothrDataId)
	{
	   this.mothrDataId = mothrDataId;
	}
	public String getAttcFileTypeCd()
	{
	   return  attcFileTypeCd;
	}
	public void setAttcFileTypeCd(String attcFileTypeCd)
	{
	   this.attcFileTypeCd = attcFileTypeCd;
	}
	public String getAttcClfCd()
	{
	   return  attcClfCd;
	}
	public void setAttcClfCd(String attcClfCd)
	{
	   this.attcClfCd = attcClfCd;
	}
	public String getOrgFileNm()
	{
	   return  orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm)
	{
	   this.orgFileNm = orgFileNm;
	}
	public String getRegDt()
	{
	   return  regDt;
	}
	public void setRegDt(String regDt)
	{
	   this.regDt = regDt;
	}
	public String getRegrid()
	{
	   return  regrid;
	}
	public void setRegrid(String regrid)
	{
	   this.regrid = regrid;
	}
	public String getModDt()
	{
	   return  modDt;
	}
	public void setModDt(String modDt)
	{
	   this.modDt = modDt;
	}
	public String getModrid()
	{
	   return  modrid;
	}
	public void setModrid(String modrid)
	{
	   this.modrid = modrid;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


}
