package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;


/**
 * 계열사 회사 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class AuthorDO {


		

	
		/**
		 *  계열사 명
		 */
		private String co_nm = Constants.BLANK;

		/**
		 *  회사코드
		 */
		private String co_cd = Constants.BLANK;
		/**
		 * 본부명
		 */
		private String sup_head_nm = Constants.BLANK;
		/**
		 * 부서명
		 */
		private String dept_nm = Constants.BLANK;
		/**
		 * 사용자 id
		 */
		private String sbs_user_id = Constants.BLANK;
		/**
		 * 이름
		 */
		private String user_nm = Constants.BLANK;
		/**
		 * 권한
		 */
		private String role_cd = Constants.BLANK;
		/**
		 * 핸드폰
		 */
		private String mobile = Constants.BLANK;
		/**
		 * 내선번호
		 */
		private String inter_phon = Constants.BLANK;
		
		/**
		 * SBSI 유저여부
		 */
		private String sbsi_user_yn = Constants.BLANK;
		
		/**
		 * 계정유형
		 */
		private String acct_code = Constants.BLANK;
		/**
		 * 부서코드
		 */
		private String dept_cd = Constants.BLANK;
		
		
		/**
		 * 시스템 구분코드
		 */
		private String system = Constants.BLANK;
		
		
		
		
		public String getSystem() {
			return system;
		}



		public void setSystem(String system) {
			this.system = system;
		}



		private int page;
		public String getAcct_code() {
			return acct_code;
		}



		public String getDept_cd() {
			return dept_cd;
		}



		public void setDept_cd(String deptCd) {
			dept_cd = deptCd;
		}



		public void setAcct_code(String acctCode) {
			acct_code = acctCode;
		}





		public String getSbsi_user_yn() {
			return sbsi_user_yn;
		}



		public void setSbsi_user_yn(String sbsiUserYn) {
			sbsi_user_yn = sbsiUserYn;
		}



		public String getCo_nm() {
			return co_nm;
		}



		public void setCo_nm(String coNm) {
			co_nm = coNm;
		}



		public String getCo_cd() {
			return co_cd;
		}



		public void setCo_cd(String coCd) {
			co_cd = coCd;
		}



		public String getSup_head_nm() {
			return sup_head_nm;
		}



		public void setSup_head_nm(String supHeadNm) {
			sup_head_nm = supHeadNm;
		}



		public String getDept_nm() {
			return dept_nm;
		}



		public void setDept_nm(String deptNm) {
			dept_nm = deptNm;
		}



		public String getSbs_user_id() {
			return sbs_user_id;
		}



		public void setSbs_user_id(String sbsUserId) {
			sbs_user_id = sbsUserId;
		}



		public String getUser_nm() {
			return user_nm;
		}



		public void setUser_nm(String userNm) {
			user_nm = userNm;
		}



		public String getRole_cd() {
			return role_cd;
		}



		public void setRole_cd(String roleCd) {
			role_cd = roleCd;
		}



		public String getMobile() {
			return mobile;
		}



		public void setMobile(String mobile) {
			this.mobile = mobile;
		}



		public String getInter_phon() {
			return inter_phon;
		}



		public void setInter_phon(String interPhon) {
			inter_phon = interPhon;
		}



		public int getPage() {
			return page;
		}



		public void setPage(int page) {
			this.page = page;
		}

}
