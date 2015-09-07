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
public class EmployeeInfoDO extends DTO 
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
		 *  보증 유저id
		 */
		private String ref_user_ID = Constants.BLANK;
		/**
		 *   사용자 이름
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
		 * 직책
		 */
		private String Position = Constants.BLANK;
		/**
		 * 승인여부
		 */
		private String Approve_yn = Constants.BLANK;
		
		/**
		 * 사번
		 */
		private String user_num = Constants.BLANK;
		/**
		 * 계정유형
		 */
		private String acct_code = Constants.BLANK;
		/**
		 * 삭제여부
		 */
		private String delete_yn = Constants.BLANK;
		/**
		 * 암호
		 */
		private String Password = Constants.BLANK;
		
		
		/**
		 * new 암호
		 */
		private String NewPassword = Constants.BLANK;
		
		
		
		/**
		 * 역할
		 */
		private String Role_cd = Constants.BLANK;	
		/**
		 * 역할
		 */
		private String Role_nm = Constants.BLANK;		
		/**
		 * 직원 휴대폰
		 */
		private String Mobile = Constants.BLANK;
		/**
		 * 계열사 전화번호
		 */
		private String Subsi_tel = Constants.BLANK;
		/**
		 * 내선 전화
		 */
		private String inter_phon = Constants.BLANK;
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
		 * 등록자
		 */
		private String reg_id = Constants.BLANK;
		/**
		 * 수정자
		 */
		private String mod_id = Constants.BLANK;
		/**
		 * 등록일시
		 */
		private String reg_dt = Constants.BLANK;
		/**
		 * 수정일시
		 */
		private String mod_dt = Constants.BLANK;
		
		/**
		 * 성공여부
		 */
		private String success_yn = Constants.BLANK;
		

		/**
		 * 수정여부
		 */
		private String update_yn = Constants.BLANK;
		

		/**
		 * 등록자명
		 */
		private String reg_nm = Constants.BLANK;
		
		/**
		 * 승인자명
		 */
		private String mod_nm = Constants.BLANK;
		
		/**
		 * 검색대상
		 */
		private String searchtype = Constants.BLANK;
		
		
		
		/**
		 * 계정유형명
		 */
		private String acct_nm = Constants.BLANK;
		
		
		

		/**
		 * pds 신청여부
		 */
		private String pds = Constants.BLANK;
		
		
		

		/**
		 * nds 신청여부
		 */
		private String nds = Constants.BLANK;
		
		
		

		/**
		 * 외부 시스템 연동
		 */
		private String out_sys = Constants.BLANK;
		
		/**
		 * pds 프로그램 id
		 * 
		 * 		 */
		private String pds_pgm_id = Constants.BLANK;
		
		
		/**
		 * 유형 001-신청 002-수정 003-삭제
		 * 
		 * 		 */
		private String type = Constants.BLANK;
		
		
		/**
		 * 승인/취소사유
		 * 
		 * 		 */
		private String cont = Constants.BLANK;
		
		
		/**
		 * 플래그 (I :inesrt U: password 변경)
		 * 
		 * 		 */
		private String flag = Constants.BLANK;
		
		/**
		 * 모니터링 권한 코드
		 * 
		 * 		 */
		private String monitor_cd = Constants.BLANK;
		
		
		/**
		 * 모니터링 권한명
		 * 
		 * 		 */
		private String monitor_nm = Constants.BLANK;
		
		
		/**
		 * 구분
		 * 
		 * 		 */
		private String gubun = Constants.BLANK;
		
		
		
		
		
		
		public String getGubun() {
			return gubun;
		}



		public void setGubun(String gubun) {
			this.gubun = gubun;
		}



		public String getMonitor_nm() {
			return monitor_nm;
		}



		public void setMonitor_nm(String monitorNm) {
			monitor_nm = monitorNm;
		}



		public String getMonitor_cd() {
			return monitor_cd;
		}



		public void setMonitor_cd(String monitorCd) {
			monitor_cd = monitorCd;
		}



		public String getFlag() {
			return flag;
		}



		public void setFlag(String flag) {
			this.flag = flag;
		}



		public String getOut_sys() {
			return out_sys;
		}



		public String getNewPassword() {
			return NewPassword;
		}



		public void setNewPassword(String newPassword) {
			NewPassword = newPassword;
		}



		public void setOut_sys(String outSys) {
			out_sys = outSys;
		}



		public String getCont() {
			return cont;
		}












		public void setCont(String cont) {
			this.cont = cont;
		}












		public String getType() {
			return type;
		}












		public void setType(String type) {
			this.type = type;
		}












		public String getPds_pgm_id() {
			return pds_pgm_id;
		}












		public void setPds_pgm_id(String pdsPgmId) {
			pds_pgm_id = pdsPgmId;
		}












		public String getPds() {
			return pds;
		}












		public void setPds(String pds) {
			this.pds = pds;
		}












		public String getNds() {
			return nds;
		}












		public void setNds(String nds) {
			this.nds = nds;
		}












		public String getAcct_nm() {
			return acct_nm;
		}












		public void setAcct_nm(String acctNm) {
			acct_nm = acctNm;
		}












		public String getSearchtype() {
			return searchtype;
		}












		public void setSearchtype(String searchtype) {
			this.searchtype = searchtype;
		}












		public String getMod_nm() {
			return mod_nm;
		}












		public void setMod_nm(String modNm) {
			mod_nm = modNm;
		}












		public String getReg_nm() {
			return reg_nm;
		}












		public void setReg_nm(String regNm) {
			reg_nm = regNm;
		}












		public String getUpdate_yn() {
			return update_yn;
		}












		public void setUpdate_yn(String updateYn) {
			update_yn = updateYn;
		}












		public String getSuccess_yn() {
			return success_yn;
		}












		public void setSuccess_yn(String successYn) {
			success_yn = successYn;
		}












		public String getReg_id() {
			return reg_id;
		}












		public void setReg_id(String regId) {
			reg_id = regId;
		}












		public String getMod_id() {
			return mod_id;
		}












		public void setMod_id(String modId) {
			mod_id = modId;
		}












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












		public String getOut_user_nm() {
			return out_user_nm;
		}












		public void setOut_user_nm(String outUserNm) {
			out_user_nm = outUserNm;
		}



		/**
		 * 비직원 이름
		 */
		private String out_user_nm = Constants.BLANK;

		/**
		 * 비직원 ID
		 */
		private String out_user_id = Constants.BLANK;
		
		
		
				public String getOut_user_id() {
			return out_user_id;
		}












		public void setOut_user_id(String outUserId) {
			out_user_id = outUserId;
		}












				public String getDept_cd() {
			return dept_cd;
		}



		


			





		public String getUser_num() {
					return user_num;
				}












				public void setUser_num(String userNum) {
					user_num = userNum;
				}












				public String getAcct_code() {
					return acct_code;
				}












				public void setAcct_code(String acctCode) {
					acct_code = acctCode;
				}












				public String getDelete_yn() {
					return delete_yn;
				}












				public void setDelete_yn(String deleteYn) {
					delete_yn = deleteYn;
				}












		public void setDept_cd(String deptCd) {
			dept_cd = deptCd;
		}



				public String getApprove_status() {
			return approve_status;
		}



		public void setApprove_status(String approveStatus) {
			approve_status = approveStatus;
		}



				public String getPgm_nm() {
			return Pgm_nm;
		}



		public void setPgm_nm(String pgmNm) {
			Pgm_nm = pgmNm;
		}



				/**
		 * 부서장 사번
		 */
		private String dept_chap_emp_no = Constants.BLANK;
		/**
		 * 회사 코드
		 */
		private String cocd = Constants.BLANK;
		/**
		 * SBSi 유저여부
		 */
		private String sbsi_user_yn= Constants.BLANK;
		
		/**
		 * 상위부서코드
		 */
		private String sup_dept_cd= Constants.BLANK;
		
		public String getSup_dept_cd() {
			return sup_dept_cd;
		}



		public void setSup_dept_cd(String supDeptCd) {
			sup_dept_cd = supDeptCd;
		}



		public String getSbsi_user_yn() {
			return sbsi_user_yn;
		}



		public String getCocd() {
			return cocd;
		}



		public String getInter_phon() {
			return inter_phon;
		}



		public void setInter_phon(String interPhon) {
			inter_phon = interPhon;
		}



		public void setCocd(String cocd) {
			this.cocd = cocd;
		}



		public void setSbsi_user_yn(String sbsiUserYn) {
			sbsi_user_yn = sbsiUserYn;
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



		public String getDept_chap_emp_no() {
			return dept_chap_emp_no;
		}



		public void setDept_chap_emp_no(String deptChapEmpNo) {
			dept_chap_emp_no = deptChapEmpNo;
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



		public int getPage() {
			return page;
		}



		public void setPage(int page) {
			this.page = page;
		}



		/**
		 * 직원유형 001 정직원 002 계열사 003 비직원
		 */
		private String employee_type = Constants.BLANK;
		/**
		 * 정직원 유무
		 */
		private String employee_yn = Constants.BLANK;
		
		
		
		private int page;

		public String getRole_nm() {
			return Role_nm;
		}












		public void setRole_nm(String roleNm) {
			Role_nm = roleNm;
		}



		public String getRef_user_ID() {
			return ref_user_ID;
		}



		public void setRef_user_ID(String refUserID) {
			ref_user_ID = refUserID;
		}
	
	
}
