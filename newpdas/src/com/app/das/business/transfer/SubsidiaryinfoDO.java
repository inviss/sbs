package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
/**
 * 계열사 정보를  포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class SubsidiaryinfoDO {


	
	
	
	private String seg_nm = Constants.BLANK;//본부 명
	private String seg_cd = Constants.BLANK;//본부 코드
	private String co_cd = Constants.BLANK;//회사 코드
	private String co_cm = Constants.BLANK;//회사명
	
	
	
	public String getSeg_nm() {
		return seg_nm;
	}
	public void setSeg_nm(String segNm) {
		seg_nm = segNm;
	}
	public String getSeg_cd() {
		return seg_cd;
	}
	public void setSeg_cd(String segCd) {
		seg_cd = segCd;
	}
	public String getCo_cd() {
		return co_cd;
	}
	public void setCo_cd(String coCd) {
		co_cd = coCd;
	}
	public String getCo_cm() {
		return co_cm;
	}
	public void setCo_cm(String coCm) {
		co_cm = coCm;
	}
	/**
	 * 주민등록번호
	 */
		private String Per_reg_no = Constants.BLANK;
		

		/**
		 * 계열사 sbs 사용자 id
		 */
		private String Sbs_user_ID = Constants.BLANK;
		
		/**
		 * 계열사 사용자 이름
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
		 * 계열사 직원 휴대폰
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
		 * 등록일시
		 */
		private String reg_dt = Constants.BLANK;
		/**
		 * 등록자ID
		 */
		private String reg_rid = Constants.BLANK;
		/**
		 * 수정일시
		 */
		private String Mod_dt = Constants.BLANK;
		/**
		 * 수정자 ID
		 */
		private String Mod_rid = Constants.BLANK;
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
		
		private String pgm_nm = Constants.BLANK;
		public String getPgm_nm() {
			return pgm_nm;
		}
		public void setPgm_nm(String pgmNm) {
			pgm_nm = pgmNm;
		}
		private int page;
		
		
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
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
		public String getReg_dt() {
			return reg_dt;
		}
		public void setReg_dt(String regDt) {
			reg_dt = regDt;
		}
		public String getReg_rid() {
			return reg_rid;
		}
		public void setReg_rid(String regRid) {
			reg_rid = regRid;
		}
		public String getMod_dt() {
			return Mod_dt;
		}
		public void setMod_dt(String modDt) {
			Mod_dt = modDt;
		}
		public String getMod_rid() {
			return Mod_rid;
		}
		public void setMod_rid(String modRid) {
			Mod_rid = modRid;
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

