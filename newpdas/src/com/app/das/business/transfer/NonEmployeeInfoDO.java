package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 외부 직원 정보를 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class NonEmployeeInfoDO extends DTO 
{

	/**
	 * 주민등록번호
	 */
		private String Per_reg_no = Constants.BLANK;
		

		/**
		 *   sbs 사용자 id
		 */
		private String Sbs_user_ID = Constants.BLANK;
		/**
		 *   비직원 사용자 id
		 */
		private String out_user_ID = Constants.BLANK;
		
		/**
		 *   비직원 사용자 이름
		 */
		private String user_nm = Constants.BLANK;
		/**
		 * 소속부서 명
		 */
		private String dept_nm = Constants.BLANK;
		/**
		 * 사용시작일
		 */
		private String Vlddt_bgn = Constants.BLANK;
		/**
		 * 사용종료일
		 */
		private String Vlddt_end = Constants.BLANK;
		
		/**
		 * 승인여부
		 */
		private String Approve_yn = Constants.BLANK;
		/**
		 * 암호
		 */
		private String Password = Constants.BLANK;
		/**
		 * 역할
		 */
		private String Role_cd = Constants.BLANK;		
		/**
		 * 역할명
		 */
		private String Role_nm = Constants.BLANK;		
		/**
		 * 비직원 휴대폰
		 */
		private String Mobile = Constants.BLANK;
		
		/**
		 * 프로그램 ID
		 */
		private int Pgm_id;
		/**
		 * 프로그램 명
		 */
		private String Pgm_nm = Constants.BLANK;
		
		/**
		 * 승인상태
		 */
		private String approve_status = Constants.BLANK;

		/**
		 * 부서코드
		 */
		private String dept_cd = Constants.BLANK;
		
	
		
		/**
		 * 회사 코드
		 */
		private String cocd = Constants.BLANK;



		public String getPer_reg_no() {
			return Per_reg_no;
		}



		public void setPer_reg_no(String perRegNo) {
			Per_reg_no = perRegNo;
		}



		public String getSbs_user_ID() {
			return Sbs_user_ID;
		}



		public void setSbs_user_ID(String sbsUserID) {
			Sbs_user_ID = sbsUserID;
		}



		public String getOut_user_ID() {
			return out_user_ID;
		}



		public void setOut_user_ID(String outUserID) {
			out_user_ID = outUserID;
		}



		public String getUser_nm() {
			return user_nm;
		}



		public void setUser_nm(String userNm) {
			user_nm = userNm;
		}



		public String getDept_nm() {
			return dept_nm;
		}



		public void setDept_nm(String deptNm) {
			dept_nm = deptNm;
		}



		public String getVlddt_bgn() {
			return Vlddt_bgn;
		}



		public void setVlddt_bgn(String vlddtBgn) {
			Vlddt_bgn = vlddtBgn;
		}



		public String getVlddt_end() {
			return Vlddt_end;
		}



		public void setVlddt_end(String vlddtEnd) {
			Vlddt_end = vlddtEnd;
		}



		public String getApprove_yn() {
			return Approve_yn;
		}



		public void setApprove_yn(String approveYn) {
			Approve_yn = approveYn;
		}



		public String getPassword() {
			return Password;
		}



		public void setPassword(String password) {
			Password = password;
		}



		public String getRole_nm() {
			return Role_nm;
		}



		public void setRole_nm(String roleNm) {
			Role_nm = roleNm;
		}



		public String getRole_cd() {
			return Role_cd;
		}



		public void setRole_cd(String roleCd) {
			Role_cd = roleCd;
		}



		public String getMobile() {
			return Mobile;
		}



		public void setMobile(String mobile) {
			Mobile = mobile;
		}



		public int getPgm_id() {
			return Pgm_id;
		}



		public void setPgm_id(int pgmId) {
			Pgm_id = pgmId;
		}



		public String getPgm_nm() {
			return Pgm_nm;
		}



		public void setPgm_nm(String pgmNm) {
			Pgm_nm = pgmNm;
		}



		public String getApprove_status() {
			return approve_status;
		}



		public void setApprove_status(String approveStatus) {
			approve_status = approveStatus;
		}



		public String getDept_cd() {
			return dept_cd;
		}



		public void setDept_cd(String deptCd) {
			dept_cd = deptCd;
		}



		public String getCocd() {
			return cocd;
		}



		public void setCocd(String cocd) {
			this.cocd = cocd;
		}
		
		
	
	
	
}
