
package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * DTL 정보 를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class DtlInfoDO extends DTO 
{
	
	/**
	 * DTL명
	 */
	private String dtl_nm           = Constants.BLANK;

	/**
	 * alias
	 */
	private String alias           = Constants.BLANK;

	
	/**
	 * DTL 위치
	 */
	private String dtl_cont           = Constants.BLANK;


	public String getDtl_nm() {
		return dtl_nm;
	}


	public void setDtl_nm(String dtlNm) {
		dtl_nm = dtlNm;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getDtl_cont() {
		return dtl_cont;
	}


	public void setDtl_cont(String dtlCont) {
		dtl_cont = dtlCont;
	}

	
	
	
	
}
