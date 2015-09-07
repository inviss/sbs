package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 콘텐트 mapp 정보를 포함하고 있는 DataObject
 * @author mhchoi
 *
 */
public class ContentMappInfoDO extends DTO{
	
	/** 
	 * 콘텐트 ID 
	 */
	private    long       ctId;
	/** 
	 * 프로그램 ID 
	 */
	private    long       pgmId;
	/** 
	 * 마스터 ID 
	 */
	private    long       masterId;
	/** 
	 * 코너 ID 
	 */
	private    long       cnId;
	/** 
	 * 등록일 
	 */
	private    String       regDt     = Constants.BLANK;
	/** 
	 * 등록자  ID 
	 */
	private    String       regrid     = Constants.BLANK;
	/** 
	 * 시작 Duration 
	 */
	private    long       sDuration;
	/** 
	 * 종료 Duration 
	 */
	private    long       eDuration;
	/** 
	 * 코너 순번 
	 */
	private    int       cnSeq;
	/** 
	 * 콘텐츠 순번
	 */
	private    int       ctSeq;
	
	
	
	/** 
	 * ct_id 그룹
	 */
	private    String       ct_ids     = Constants.BLANK;
	/** 
	 * 삭제일 그룹
	 */
	private    String       del_dds     = Constants.BLANK;
	
	
	
	
	public long getsDuration() {
		return sDuration;
	}
	public void setsDuration(long sDuration) {
		this.sDuration = sDuration;
	}
	public long geteDuration() {
		return eDuration;
	}
	public void seteDuration(long eDuration) {
		this.eDuration = eDuration;
	}
	public String getCt_ids() {
		return ct_ids;
	}
	public void setCt_ids(String ctIds) {
		ct_ids = ctIds;
	}
	public String getDel_dds() {
		return del_dds;
	}
	public void setDel_dds(String delDds) {
		del_dds = delDds;
	}
	public int getCnSeq()
	{
	   return  cnSeq;
	}
	public void setCnSeq(int cnSeq)
	{
	   this.cnSeq = cnSeq;
	}
	public int getCtSeq()
	{
	   return  ctSeq;
	}
	public void setCtSeq(int ctSeq)
	{
	   this.ctSeq = ctSeq;
	}
	public long getCtId()
	{
	   return  ctId;
	}
	public void setCtId(long ctId)
	{
	   this.ctId = ctId;
	}
	public long getPgmId()
	{
	   return  pgmId;
	}
	public void setPgmId(long pgmId)
	{
	   this.pgmId = pgmId;
	}
	public long getMasterId()
	{
	   return  masterId;
	}
	public void setMasterId(long masterId)
	{
	   this.masterId = masterId;
	}
	public long getCnId()
	{
	   return  cnId;
	}
	public void setCnId(long cnId)
	{
	   this.cnId = cnId;
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
	public long getSDuration()
	{
	   return  sDuration;
	}
	public void setSDuration(long sDuration)
	{
	   this.sDuration = sDuration;
	}
	public long getEDuration()
	{
	   return  eDuration;
	}
	public void setEDuration(long eDuration)
	{
	   this.eDuration = eDuration;
	}


}
