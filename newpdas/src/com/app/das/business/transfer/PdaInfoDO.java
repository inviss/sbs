package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 외브 프로그램 정보를 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class PdaInfoDO extends DTO 
{

	/**
	 * 프로그램 id(pds)
	 */
		private String programid;
		

		/**
		 *  프로그램 id(das)
		 */
		private long das_pgm_id;
		
		/**
		 *   책임 pd 여부
		 */
		private String cp_yn = Constants.BLANK;
		/**
		 * 방송종료일
		 */
		private String brd_end_dd = Constants.BLANK;
		
		/**
		 * 방송종료일
		 */
		private String brd_end_yn = Constants.BLANK;
		
		/**
		 * 사번
		 */
		private String USER_NO = Constants.BLANK;
		
		
		
		
		public String getUSER_NO() {
			return USER_NO;
		}
		public void setUSER_NO(String uSERNO) {
			USER_NO = uSERNO;
		}
		public String getBrd_end_yn() {
			return brd_end_yn;
		}
		public void setBrd_end_yn(String brdEndYn) {
			brd_end_yn = brdEndYn;
		}
		
		public String getProgramid() {
			return programid;
		}
		public void setProgramid(String programid) {
			this.programid = programid;
		}
		public long getDas_pgm_id() {
			return das_pgm_id;
		}
		public void setDas_pgm_id(long dasPgmId) {
			das_pgm_id = dasPgmId;
		}
		public String getCp_yn() {
			return cp_yn;
		}
		public void setCp_yn(String cpYn) {
			cp_yn = cpYn;
		}
		public String getBrd_end_dd() {
			return brd_end_dd;
		}
		public void setBrd_end_dd(String brdEndDd) {
			brd_end_dd = brdEndDd;
		}
		
	
}
