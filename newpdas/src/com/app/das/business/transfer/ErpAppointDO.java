package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * erp 승인 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ErpAppointDO extends DTO 
{
	
	/** 
	 * 회사코드 
	 */       
	private String co_cd  	= Constants.BLANK;
	/** 
	 * 사원번호 
	 */       
	private String user_no  	= Constants.BLANK;
	/** 
	 * 발령일자 
	 */       
	private String oder_dd  	= Constants.BLANK;
	/** 
	 * 발령순번 
	 */       
	private int oder_seq;

	
	/** 
	 * 발령코드 
	 */       
	private String oder_cd  	= Constants.BLANK;
	/** 
	 * 사용자명 
	 */       
	private String user_nm  	= Constants.BLANK;
	/** 
	 * 사용자구분 
	 */       
	private String user_clf  	= Constants.BLANK;
	/** 
	 * 부서코드 
	 */       
	private String dept_cd  	= Constants.BLANK;
	/** 
	 * 핸드폰 
	 */       
	private String hand_phon  	= Constants.BLANK;
	/** 
	 * 직책코드 
	 */       
	private String job_cd  	= Constants.BLANK;
	/** 
	 * 팀장여부 
	 */       
	private String team_yn  	= Constants.BLANK;
	/** 
	 * 적용여부 
	 */       
	private String apdat_yn  	= Constants.BLANK;
	
	/** 
	 * 조회여부 
	 */       
	private String search_yn  	= Constants.BLANK;

	/** 
	 * 순번 
	 */       
	private int seq_no;
	
	
	/** 
	 * ? 
	 */       
	private String ocpn_gr_cd  	= Constants.BLANK;
	/** 
	 * 제목 
	 */       
	private String title  	= Constants.BLANK;
	
	/** 
	 * 부서명 
	 */       
	private String dept_nm  	= Constants.BLANK;
	/** 
	 * 본부코드
	 */       
	private String seg_cd  	= Constants.BLANK;
	/** 
	 * 본부명
	 */       
	private String seg_nm  	= Constants.BLANK;

	/** 
	 * 발령이름
	 */       
	private String oder_nm  	= Constants.BLANK;

	/** 
	 * 발령코드 D = 삭제 , U = 갱신 
	 */       
	private String oder_flag  	= Constants.BLANK;

	/** 
	 * 아이디 
	 */       
	private String user_id  	= Constants.BLANK;

	
	
	
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public String getOder_nm() {
		return oder_nm;
	}

	public void setOder_nm(String oderNm) {
		oder_nm = oderNm;
	}

	public String getOder_flag() {
		return oder_flag;
	}

	public void setOder_flag(String oderFlag) {
		oder_flag = oderFlag;
	}

	public String getOcpn_gr_cd() {
		return ocpn_gr_cd;
	}

	public void setOcpn_gr_cd(String ocpnGrCd) {
		ocpn_gr_cd = ocpnGrCd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDept_nm() {
		return dept_nm;
	}

	public void setDept_nm(String deptNm) {
		dept_nm = deptNm;
	}

	public String getSeg_cd() {
		return seg_cd;
	}

	public void setSeg_cd(String segCd) {
		seg_cd = segCd;
	}

	public String getSeg_nm() {
		return seg_nm;
	}

	public void setSeg_nm(String segNm) {
		seg_nm = segNm;
	}

	public int getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(int seqNo) {
		seq_no = seqNo;
	}

	public String getCo_cd() {
		return co_cd;
	}
	
	public void setCo_cd(String coCd) {
		co_cd = coCd;
	}
	
	public String getUser_no() {
		return user_no;
	}
	
	public void setUser_no(String userNo) {
		user_no = userNo;
	}
	
	public String getOder_dd() {
		return oder_dd;
	}
	
	public void setOder_dd(String oderDd) {
		oder_dd = oderDd;
	}
	
	public int getOder_seq() {
		return oder_seq;
	}
	
	public void setOder_seq(int oderSeq) {
		oder_seq = oderSeq;
	}
	
	public String getOder_cd() {
		return oder_cd;
	}
	
	public void setOder_cd(String oderCd) {
		oder_cd = oderCd;
	}
	
	public String getUser_nm() {
		return user_nm;
	}
	
	public void setUser_nm(String userNm) {
		user_nm = userNm;
	}
	
	public String getUser_clf() {
		return user_clf;
	}
	
	public void setUser_clf(String userClf) {
		user_clf = userClf;
	}
	
	public String getDept_cd() {
		return dept_cd;
	}
	
	public void setDept_cd(String deptCd) {
		dept_cd = deptCd;
	}
	
	public String getHand_phon() {
		return hand_phon;
	}
	
	public void setHand_phon(String handPhon) {
		hand_phon = handPhon;
	}
	
	public String getJob_cd() {
		return job_cd;
	}
	
	public void setJob_cd(String jobCd) {
		job_cd = jobCd;
	}
	
	public String getTeam_yn() {
		return team_yn;
	}
	
	public void setTeam_yn(String teamYn) {
		team_yn = teamYn;
	}
	
	public String getApdat_yn() {
		return apdat_yn;
	}
	
	public void setApdat_yn(String apdatYn) {
		apdat_yn = apdatYn;
	}
	
	public String getSearch_yn() {
		return search_yn;
	}
	
	public void setSearch_yn(String searchYn) {
		search_yn = searchYn;
	}
	
	
}
