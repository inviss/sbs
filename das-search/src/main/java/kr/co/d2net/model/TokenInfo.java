package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tokeninfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class TokenInfo {

	@XmlElement(name="hex")
	private String hex;
	@XmlElement(name="user_id")
	private String userId;
	@XmlElement(name="user_nm")
	private String userNm;
	@XmlElement(name="password")
	private String passwd;
	@XmlElement(name="role_cd")
	private String roleCd;
	@XmlElement(name="result")
	private String result;
	@XmlElement(name="authresult")
	private String authresult;
	@XmlElement(name="desc")
	private String desc;
	@XmlElement(name="token")
	private String token;
	
	
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getRoleCd() {
		return roleCd;
	}
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getAuthresult() {
		return authresult;
	}
	public void setAuthresult(String authresult) {
		this.authresult = authresult;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getHex() {
		return hex;
	}
	public void setHex(String hex) {
		this.hex = hex;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
