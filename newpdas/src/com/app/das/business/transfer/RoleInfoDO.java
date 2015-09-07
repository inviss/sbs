package com.app.das.business.transfer;
import com.app.das.business.constants.Constants;
/**
 * 권한 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class RoleInfoDO  extends DTO {
	/**
	 * 권한그룹명
	 */
	private String role_group_nm = Constants.BLANK;
	/**
	 * 권한그룹id
	 */
	private String role_group_id = Constants.BLANK;
	/**
	 * 권한 id
	 */
	private String role_id = Constants.BLANK;
	/**
	 * 권한 명
	 */
	private String role_nm = Constants.BLANK;
	
	/**
	 * 기능id
	 */
	private String function_cd = Constants.BLANK;
	
	
	//2012.4.19 다스 확장 추가 
	
	/**
	 * 채널코드
	 */
	private String chennel = Constants.BLANK;
	/**
	 * 회사코드
	 */
	private String cocd = Constants.BLANK;
	
	
	
	
	//모니터링용 권한 
	/**
	 * 메뉴id
	 */
	private String menu_id = Constants.BLANK;
	/**
	 * 메뉴 명
	 */
	private String menu_nm = Constants.BLANK;
	/**
	 * 깊이
	 */
	private String depth = Constants.BLANK;
	/**
	 * 사용권한
	 */
	private String use_perm = Constants.BLANK;
	/**
	 * 역활ID
	 */
	private String perm_id = Constants.BLANK;
	
	/**
	 * 구분
	 */
	private String gubun = Constants.BLANK;
	
	/**
	 * 목적
	 */
	private String active = Constants.BLANK;
	
	
	
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getPerm_id() {
		return perm_id;
	}

	public void setPerm_id(String permId) {
		perm_id = permId;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menuId) {
		menu_id = menuId;
	}

	public String getMenu_nm() {
		return menu_nm;
	}

	public void setMenu_nm(String menuNm) {
		menu_nm = menuNm;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getUse_perm() {
		return use_perm;
	}

	public void setUse_perm(String usePerm) {
		use_perm = usePerm;
	}

	public String getChennel() {
		return chennel;
	}

	public void setChennel(String chennel) {
		this.chennel = chennel;
	}

	public String getCocd() {
		return cocd;
	}

	public void setCocd(String cocd) {
		this.cocd = cocd;
	}

	public String getFunction_cd() {
		return function_cd;
	}

	public void setFunction_cd(String functionCd) {
		function_cd = functionCd;
	}

	public String getRole_nm() {
		return role_nm;
	}

	public void setRole_nm(String roleNm) {
		role_nm = roleNm;
	}

	/**
	 * 허용여부
	 */
	private String use_yn = Constants.BLANK;
	
	private int page;
	private int seq;
	
	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String roleId) {
		role_id = roleId;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String useYn) {
		use_yn = useYn;
	}

	public String getRole_group_nm() {
		return role_group_nm;
	}

	public void setRole_group_nm(String roleGroupNm) {
		role_group_nm = roleGroupNm;
	}

	public String getRole_group_id() {
		return role_group_id;
	}

	public void setRole_group_id(String roleGroupId) {
		role_group_id = roleGroupId;
	}

	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}


	
}
