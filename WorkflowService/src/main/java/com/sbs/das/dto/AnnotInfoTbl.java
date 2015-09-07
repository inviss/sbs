package com.sbs.das.dto;

public class AnnotInfoTbl extends BaseObject {

	private static final long serialVersionUID = -622321559853781076L;
	
	private Long annotId;			//ANNOT_ID				BIGINT	주석정보ID
	private Long cnId;				//CN_ID						BIGINT	코너ID: 한 코너에 대해 부여하는 ID
	private Long ctId;				//CT_ID						BIGINT	콘텐츠D: 콘텐츠 하나에 대해 부여하는 ID
	private Long masterId;			//MASTER_ID				BIGINT	마스터ID : 프로그램 한 회에 대한 ID
	private String annotClfCd;		//ANNOT_CLF_CD		CHARACTER(3)	주석구분코드 (001 명장면 002 방송심의제재 003 사용금지 004 담당PD확인 005 사내심의사항)
	private String annotClfCont;	//ANNOT_CLF_CONT	주석구분내용
	private String som;				//SOM							CHARACTER(11)	시작점(hh:mm:ss:ff)
	private String eom;				//EOM							CHARACTER(11)	종료점(hh:mm:ss:ff)
	private String cont;			//CONT						LONG VARCHAR	정리 시 입력하는 주석 내용
	private String regrid;			//REGRID					VARCHAR(15)	등록자ID
	private String regDt;			//REG_DT					CHARACTER(14)	등록일시
	private String modrid;			//MODRID					VARCHAR(15)	수정자ID
	private String modDt;			//MOD_DT					CHARACTER(14)	수정일시
	private Long duration;			//DURATION				BIGINT	DURATION : 해당 키프레임 수
	private Long sFrame	;			//S_FRAME					BIGINT	NOT NULL	DEFAULT ,
	private String gubun;			//GUBUN						CHARACTER(1)	주제영상:S , 사용제한등급:L
	private String entireYn;			//ENTIRE_YN						CHARACTER(1)	Y : 전체등급, '' OR N : 부분지정 
	
	
	public String getEntireYn() {
		return entireYn;
	}
	public void setEntireYn(String entireYn) {
		this.entireYn = entireYn;
	}
	public Long getAnnotId() {
		return annotId;
	}
	public void setAnnotId(Long annotId) {
		this.annotId = annotId;
	}
	public Long getCnId() {
		return cnId;
	}
	public void setCnId(Long cnId) {
		this.cnId = cnId;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public String getAnnotClfCd() {
		return annotClfCd;
	}
	public void setAnnotClfCd(String annotClfCd) {
		this.annotClfCd = annotClfCd;
	}
	public String getAnnotClfCont() {
		return annotClfCont;
	}
	public void setAnnotClfCont(String annotClfCont) {
		this.annotClfCont = annotClfCont;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getsFrame() {
		return sFrame;
	}
	public void setsFrame(Long sFrame) {
		this.sFrame = sFrame;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	
}
