package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 프로그램별 사진정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PgmPhotoInfoDO  extends DTO {
	
	/**
	 * 순번
	 */
	private    int       seq;
	/**
	 * 사진 ID
	 */
	private    long       photId;
	/**
	 * 프로그램 ID
	 */
	private    long       pgmId;
	/**
	 * 끝회차
	 */
	private    int       endEpn;
	/**
	 * 시작회차
	 */
	private    int       bgnEpn;
	/**
	 * 등록일
	 */
	private    String       regDt     = Constants.BLANK;
	
	public int getSeq()
	{
	   return  seq;
	}
	public void setSeq(int seq)
	{
	   this.seq = seq;
	}
	public long getPhotId()
	{
	   return  photId;
	}
	public void setPhotId(long photId)
	{
	   this.photId = photId;
	}
	public long getPgmId()
	{
	   return  pgmId;
	}
	public void setPgmId(long pgmId)
	{
	   this.pgmId = pgmId;
	}
	public int getEndEpn()
	{
	   return  endEpn;
	}
	public void setEndEpn(int endEpn)
	{
	   this.endEpn = endEpn;
	}
	public int getBgnEpn()
	{
	   return  bgnEpn;
	}
	public void setBgnEpn(int bgnEpn)
	{
	   this.bgnEpn = bgnEpn;
	}
	public String getRegDt()
	{
	   return  regDt;
	}
	public void setRegDt(String regDt)
	{
	   this.regDt = regDt;
	}

}
