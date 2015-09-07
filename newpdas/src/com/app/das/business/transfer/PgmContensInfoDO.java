package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 프로그램 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PgmContensInfoDO  extends DTO 
{
	/**
	 * 콘텐트 ID
	 */
	private long ctId;
	/**
	 * 마스터 ID
	 */
	private long masterId;
	/**
	 * 콘텐트 인스탄스 ID
	 */
	private long ctiId;
	/**
	 * 콘테트 이름
	 */
	private String ctNm 	= 	Constants.BLANK;
	/**
	 * 프로그램 이름
	 */
	private String pgmNm 	= 	Constants.BLANK;
	/**
	 * 에피소드 이름
	 */
	private String pgmEpis 	= 	Constants.BLANK;
	/**
	 * 콘텐트 파일 경로
	 */
	private String flPath 	= 	Constants.BLANK;
	
	public long getCtId() {
		return ctId;
	}
	public void setCtId(long ctId) {
		this.ctId = ctId;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
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
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getPgmEpis() {
		return pgmEpis;
	}
	public void setPgmEpis(String pgmEpis) {
		this.pgmEpis = pgmEpis;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
}
