package com.app.das.business.transfer;

import java.util.StringTokenizer;

import com.app.das.business.JNI_Des;
import com.app.das.business.constants.Constants;
import com.app.das.log.DasPropHandler;

/**
 * 로그인 TOKEN 정보를 포함하고 있는 DataObejct
 * @author asura207
 *
 */
public class TokenDO extends DTO 
{

	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	
	public final static String DELIM = ";";
		/**
		 * 사번
		 */
		private String user_num="0"; // LGCNS 오경진 대리 요청 사항_20110221(비직원 사번 default ="0")
		/**
		 * 주민등록번호
		 */
		private String Per_reg_no = "0";  // LGCNS 오경진 대리 요청 사항)20110221( 정직원 주민번호  default ="0")
		/**
		 * 유저id
		 */
		private String user_id = Constants.BLANK;
		/**
		 * 회사코드
		 */
		private String cocd = Constants.BLANK;
		/**
		 * 소스시스템
		 */
		private String sourceSYS = Constants.BLANK;
		/**
		 * 토큰만료일
		 */
		private String end_token_dd = Constants.BLANK;
		/**
		 * 직원유형
		 */
		private String acct_code = Constants.BLANK; // ex)  RA,RB -정직원 SA,SB,SC-비직원
		/**
		 * 결과값.
		 */
		private String result = Constants.BLANK;
		/**
		 * 설명
		 */
		private String desc = Constants.BLANK;
		/**
		 * 토큰
		 */
		private String token = Constants.BLANK;
		/**
		 * HEX (맥어드레스 : 클라이언트)
		 */
		private String hex = Constants.BLANK;
		/**
		 * KEY (암호화 키)
		 */
		private String key = Constants.BLANK;
		
		/**
		 * 직원유형(정직원 개별ID: S,비직원 개별ID: N,비직원 공용ID: C )
		 */
		private String empType =Constants.BLANK;
		/**
		 * 실행시스템 Count 
		 */
		private int exeCnt = 0;
		/**
		 * 패스워드
		 */
		private String password = Constants.BLANK;
		/**
		 * 인증 결과 값.
		 */
		private String auth_result = Constants.BLANK;
		/**
		 * 패스워드 기간만료 남은 날짜
		 */
		private String end_password_dd = Constants.BLANK;
		
		/**
		 * 사용자 명
		 */
		private String user_nm = Constants.BLANK;
				
		/**
		 * 상세 권한 값(000:궈한없음, 001:시스템관리자, 002:콘텐츠관리자, 003:대출담당, 004:인제스터, 005:일반검색자, 006:단순검색자):1~4:관리자, 나머지:사용자
		 */
		private String role_cd = Constants.BLANK;
		
		/**
		 * 승인여부
		 */
		private String approve_yn = Constants.BLANK;
		
		
		
		public String getApprove_yn() {
			return approve_yn;
		}
		public void setApprove_yn(String approveYn) {
			approve_yn = approveYn;
		}
		public String getUser_nm() {
			return user_nm;
		}
		public String getRole_cd() {
			return role_cd;
		}
		public void setRole_cd(String role_cd) {
			this.role_cd = role_cd;
		}
		public void setUser_nm(String user_nm) {
			this.user_nm = user_nm;
		}
		public String getEnd_password_dd() {
			return end_password_dd;
		}
		public void setEnd_password_dd(String end_password_dd) {
			this.end_password_dd = end_password_dd;
		}
		public String getAuth_result() {
			return auth_result;
		}
		public void setAuth_result(String auth_result) {
			this.auth_result = auth_result;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getEmpType() {
			return empType;
		}
		public void setEmpType(String empType) {
			this.empType = empType;
		}
		public int getExeCnt() {
			return exeCnt;
		}
		public void setExeCnt(int exeCnt) {
			this.exeCnt = exeCnt;
		}
		public String getHex() {
			return hex;
		}
		public void setHex(String hex) {
			this.hex = hex;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getResult() {
			return result;
		}
		
		public void setResult(String result) {
			StringTokenizer st = new StringTokenizer(result,DELIM);
			int iCnt = 0;
			while(st.hasMoreTokens()){
				switch (iCnt) {
				case 0:
					this.result = st.nextElement()+"";  // 사번
					break;
				case 1:
					this.end_password_dd = st.nextElement()+"";  // 주민번호
					break;
					
				default:
					
					break;
				}
				iCnt++;
			}
			
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public String getUser_num() {
			if("".equals(user_num)){
				return "0";
			}
			return user_num;
		}
		public void setUser_num(String userNum) {
			user_num = userNum;
		}
		public String getPer_reg_no() {
			if("".equals(Per_reg_no)){
				return "0";
			}
			return Per_reg_no;
		}
		public void setPer_reg_no(String perRegNo) {
			Per_reg_no = perRegNo;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String userId) {
			user_id = userId;
		}
		public String getCocd() {
			return cocd;
		}
		public void setCocd(String cocd) {
			this.cocd = cocd;
		}
		public String getSourceSYS() {
			return sourceSYS;
		}
		public void setSourceSYS(String sourceSYS) {
			this.sourceSYS = sourceSYS;
		}
		public String getEnd_token_dd() {
			return end_token_dd;
		}
		public void setEnd_token_dd(String endTokenDd) {
			end_token_dd = endTokenDd;
		}
		public String getAcct_code() {
			return acct_code;
		}
		public void setAcct_code(String acctCode) {
			acct_code = acctCode;
		}
		
		


		
		
}
