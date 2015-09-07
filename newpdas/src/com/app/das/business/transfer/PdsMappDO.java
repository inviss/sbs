package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
/**
 * PDS 맵핑 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PdsMappDO {
	
	/** 
	 * PDS 프로그램 ID
	 */
	private long pds_pgm_id;
	/** 
	 * PDS 프로그램 NM
	 */
	private String pds_pgm_nm = Constants.BLANK;
	
	/** 
	 * DAS 프로그램 ID
	 */
	private long das_pgm_id;
	
	/** 
	 * DAS 프로그램 NM
	 */
	private String das_pgm_nm = Constants.BLANK;
	
	
	public long getPds_pgm_id() {
		return pds_pgm_id;
	}

	public void setPds_pgm_id(long pdsPgmId) {
		pds_pgm_id = pdsPgmId;
	}

	public String getPds_pgm_nm() {
		return pds_pgm_nm;
	}

	public void setPds_pgm_nm(String pdsPgmNm) {
		pds_pgm_nm = pdsPgmNm;
	}

	public long getDas_pgm_id() {
		return das_pgm_id;
	}

	public void setDas_pgm_id(long dasPgmId) {
		das_pgm_id = dasPgmId;
	}

	public String getDas_pgm_nm() {
		return das_pgm_nm;
	}

	public void setDas_pgm_nm(String dasPgmNm) {
		das_pgm_nm = dasPgmNm;
	}

	
	
}
