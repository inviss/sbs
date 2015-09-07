package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 계열사 부서 정보를 포함하고 있는 DataObejct
 * @author ASURA
 *
 */
public class SubCodeDO extends DTO 
{

	/**
	 * 사용자 ID
	 */
	private String Sbs_user_ID = Constants.BLANK;
		

		/**
		 *  부서명
		 */
		private String Dept_nm = Constants.BLANK;
		
		/**
		 *  부서 코드
		 */
		private String Dept_id = Constants.BLANK;
		/**
		 * 계열사 명
		 */
		private String Co_nm = Constants.BLANK;
		/**
		 * 계열사 코드
		 */
		private String Co_cd = Constants.BLANK;
		/**
		 * 본부명
		 */
		private String Seg_nm = Constants.BLANK;
		/**
		 * 본부 코드
		 */
		private String Seg_cd = Constants.BLANK;
		/**
		 * 수정일시
		 */
		private String Mod_dt = Constants.BLANK;
		/**
		 * SBSI사용유무
		 */
		private String Sbsi_yn = Constants.BLANK;
		
		private int page;
		private int SEQ;
		public int getSEQ() {
			return SEQ;
		}
		public void setSEQ(int sEQ) {
			SEQ = sEQ;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public String getSbs_user_ID() {
			return Sbs_user_ID;
		}
		public void setSbs_user_ID(String sbsUserID) {
			Sbs_user_ID = sbsUserID;
		}
		public String getDept_nm() {
			return Dept_nm;
		}
		public void setDept_nm(String deptNm) {
			Dept_nm = deptNm;
		}
		
		public String getDept_id() {
			return Dept_id;
		}
		public void setDept_id(String deptId) {
			Dept_id = deptId;
		}
		public String getCo_nm() {
			return Co_nm;
		}
		public void setCo_nm(String coNm) {
			Co_nm = coNm;
		}
		public String getCo_cd() {
			return Co_cd;
		}
		public void setCo_cd(String coCd) {
			Co_cd = coCd;
		}
		public String getSeg_nm() {
			return Seg_nm;
		}
		public void setSeg_nm(String segNm) {
			Seg_nm = segNm;
		}
		public String getSeg_cd() {
			return Seg_cd;
		}
		public void setSeg_cd(String segCd) {
			Seg_cd = segCd;
		}
		public String getMod_dt() {
			return Mod_dt;
		}
		public void setMod_dt(String modDt) {
			Mod_dt = modDt;
		}
		public String getSbsi_yn() {
			return Sbsi_yn;
		}
		public void setSbsi_yn(String sbsiYn) {
			Sbsi_yn = sbsiYn;
		}
		
		
}
