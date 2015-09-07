package com.sbs.das.dto;

public class CodeTbl extends BaseObject {

	private static final long serialVersionUID = 6977790454746813209L;
	
	String 	clfCd;			// CHAR(4)		CLF_CD			// 구분코드
	String 	sclCd;			// VARCHAR(15)	SCL_CD			// 구분상세코드
	String 	clfNm;			// VARCHAR(150)	CLF_NM			// 구분명
	String	desc;			// VARCHAR(300)	DESC			// 설명
	String	rmk1;			// VARCHAR(150)	RMK_1			// 비고1
	String	rmk2;			// VARCHAR(150)	RMK_2			// 비고2
	String	regDt;			// CHAR(14)		REG_DT;			// 작성일시
	String	regrid;			// VARCHAR(15)	REGRID;			// 작성자ID
	String	modDt;			// CHAR(14)		MOD_DT;			// 수정일시
	String	modrid;			// VARCHAR(15)	MODRID;			// 수정자ID
	String	useYn;			// CHAR(1)		USE_YN			// 사용여부 
	String  gubun;			// CHAR(1)		GUBUN;			// 구분코드
	
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getClfCd() {
		return clfCd;
	}
	public void setClfCd(String clfCd) {
		this.clfCd = clfCd;
	}
	public String getSclCd() {
		return sclCd;
	}
	public void setSclCd(String sclCd) {
		this.sclCd = sclCd;
	}
	public String getClfNm() {
		return clfNm;
	}
	public void setClfNm(String clfNm) {
		this.clfNm = clfNm;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRmk1() {
		return rmk1;
	}
	public void setRmk1(String rmk1) {
		this.rmk1 = rmk1;
	}
	public String getRmk2() {
		return rmk2;
	}
	public void setRmk2(String rmk2) {
		this.rmk2 = rmk2;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

}
