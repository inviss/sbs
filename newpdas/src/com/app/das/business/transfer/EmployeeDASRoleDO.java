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
public class EmployeeDASRoleDO extends DTO 
{

	/**
	 * 주민등록번호
	 */
		private String Per_reg_no = Constants.BLANK;
		

		/**
		 *  비직원 sbs 사용자 id
		 */
		private String Sbs_user_ID = Constants.BLANK;
		
		/**
		 *   비직원 사용자 이름
		 */
		private String user_nm = Constants.BLANK;
		/**
		 * 소속부서
		 */
		private String W_Dept = Constants.BLANK;
		/**
		 * 사용시작일
		 */
		private String Vlddt_bgn = Constants.BLANK;
		/**
		 * 사용종료일
		 */
		private String Vlddt_end = Constants.BLANK;
		/**
		 * 직책
		 */
		private String Position = Constants.BLANK;
		/**
		 * 승인여부
		 */
		private String Approve_yn = Constants.BLANK;
		/**
		 * 암호
		 */
		private String Password = Constants.BLANK;
		/**
		 * 역활
		 */
		private String Role = Constants.BLANK;		
		/**
		 * 직원 휴대폰
		 */
		private String Mobile = Constants.BLANK;
		/**
		 * 계열사 전화번호
		 */
		private String Subsi_tel = Constants.BLANK;
		
		/**
		 * 프로그램 ID
		 */
		private int Pgm_id;
		
		/**
		 * 삭제일
		 */
		private String Del_DD = Constants.BLANK;
		/**
		 * 마지막 비밀번호 바꾼기록
		 */
		private String Pw_lst_chg = Constants.BLANK;
		/**
		 * 비밀번호 틀린횟수
		 */
		private int Pw_ern;
		/**
		 * 직원유형 001 정직원 002 계열사 003 비직원
		 */
		private String employee_type = Constants.BLANK;
		/**
		 * 정직원 유무
		 */
		private String employee_yn = Constants.BLANK;
		
		private int SEQ;
		
		private int page;
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		private String Pgm_nm = Constants.BLANK;
		private String reg_dt = Constants.BLANK;
		private String mod_dt = Constants.BLANK;
		public String getReg_dt() {
			return reg_dt;
		}
		public void setReg_dt(String regDt) {
			reg_dt = regDt;
		}
		public String getMod_dt() {
			return mod_dt;
		}
		public void setMod_dt(String modDt) {
			mod_dt = modDt;
		}
		public String getPgm_nm() {
			return Pgm_nm;
		}
		public void setPgm_nm(String pgmNm) {
			Pgm_nm = pgmNm;
		}
		public int getSEQ() {
			return SEQ;
		}
		public void setSEQ(int sEQ) {
			SEQ = sEQ;
		}
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
		public String getUser_nm() {
			return user_nm;
		}
		public void setUser_nm(String userNm) {
			user_nm = userNm;
		}
		public String getW_Dept() {
			return W_Dept;
		}
		public void setW_Dept(String wDept) {
			W_Dept = wDept;
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
		public String getPosition() {
			return Position;
		}
		public void setPosition(String position) {
			Position = position;
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
		public String getRole() {
			return Role;
		}
		public void setRole(String role) {
			Role = role;
		}
		public String getMobile() {
			return Mobile;
		}
		public void setMobile(String mobile) {
			Mobile = mobile;
		}
		public String getSubsi_tel() {
			return Subsi_tel;
		}
		public void setSubsi_tel(String subsiTel) {
			Subsi_tel = subsiTel;
		}
		public int getPgm_id() {
			return Pgm_id;
		}
		public void setPgm_id(int pgmId) {
			Pgm_id = pgmId;
		}
		
		public String getDel_DD() {
			return Del_DD;
		}
		public void setDel_DD(String delDD) {
			Del_DD = delDD;
		}
		public String getPw_lst_chg() {
			return Pw_lst_chg;
		}
		public void setPw_lst_chg(String pwLstChg) {
			Pw_lst_chg = pwLstChg;
		}
		public int getPw_ern() {
			return Pw_ern;
		}
		public void setPw_ern(int pwErn) {
			Pw_ern = pwErn;
		}
		public String getEmployee_type() {
			return employee_type;
		}
		public void setEmployee_type(String employeeType) {
			employee_type = employeeType;
		}
		public String getEmployee_yn() {
			return employee_yn;
		}
		public void setEmployee_yn(String employeeYn) {
			employee_yn = employeeYn;
		}
		
}
