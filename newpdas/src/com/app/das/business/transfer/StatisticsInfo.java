package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="statisticsInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticsInfo {
	
	@XmlElement(name="PGM_ID")
	private Long pgmId;
	@XmlElement(name="MASTER_ID")
	private Long masterId;
	@XmlElement(name="DATEKIND")
	private String dateKind;
	@XmlElement(name="FROMDATE")
	private String fromDate;
	@XmlElement(name="TODATE")
	private String toDate;
	@XmlElement(name="CATEGORYKIND")
	private String categoryKind;
	@XmlElement(name="BY_DY_QTY")
	private Long byDyQty;
	@XmlElement(name="BY_DY_TM")
	private Long byDyTm;
	@XmlElement(name="DD")
	private String dd;
	@XmlElement(name="GRP")
	private String grp;
	@XmlElement(name="SEG")
	private String seg;
	@XmlElement(name="DEPT")
	private String dept;
	@XmlElement(name="GRP_NM")
	private String grpNm;
	@XmlElement(name="SEG_NM")
	private String segNm;
	@XmlElement(name="DEPT_NM")
	private String dept_nm;
	@XmlElement(name="CTGR_L_CD")
	private String ctgrLCD;
	@XmlElement(name="CTGR_M_CD")
	private String ctgrMCD;
	@XmlElement(name="CTGR_S_CD")
	private String ctgrSCD;
	@XmlElement(name="CTGR_L_NM")
	private String ctgrLnm;
	@XmlElement(name="CTGR_M_NM")
	private String ctgrMnm;
	@XmlElement(name="CTGR_S_NM")
	private String ctgrSnm;
	@XmlElement(name="SUM_QTY")
	private String sumQty;
	@XmlElement(name="SUM_TM")
	private String sumTm;
	@XmlElement(name="DAY")
	private String day;
	@XmlElement(name="SOURCE_GUBUN")
	private String sourceGubun;
	@XmlElement(name="PGM_NM")
	private String pgmNm;
	@XmlElement(name="ARRANGE_COUNT")
	private Long arrangeCount;
	@XmlElement(name="ARRANGE_DURATION")
	private Long arrangeDuration;
	@XmlElement(name="COCD")
	private String cocd;
	@XmlElement(name="CHENNEL")
	private String chennel;
	
	
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getChennel() {
		return chennel;
	}
	public void setChennel(String chennel) {
		this.chennel = chennel;
	}
	public Long getPgmId() {
		return pgmId;
	}
	public void setPgmId(Long pgmId) {
		this.pgmId = pgmId;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public String getDateKind() {
		return dateKind;
	}
	public void setDateKind(String dateKind) {
		this.dateKind = dateKind;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCategoryKind() {
		return categoryKind;
	}
	public void setCategoryKind(String categoryKind) {
		this.categoryKind = categoryKind;
	}
	public Long getByDyQty() {
		return byDyQty;
	}
	public void setByDyQty(Long byDyQty) {
		this.byDyQty = byDyQty;
	}
	public Long getByDyTm() {
		return byDyTm;
	}
	public void setByDyTm(Long byDyTm) {
		this.byDyTm = byDyTm;
	}
	public String getDd() {
		return dd;
	}
	public void setDd(String dd) {
		this.dd = dd;
	}
	public String getGrp() {
		return grp;
	}
	public void setGrp(String grp) {
		this.grp = grp;
	}
	public String getSeg() {
		return seg;
	}
	public void setSeg(String seg) {
		this.seg = seg;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getGrpNm() {
		return grpNm;
	}
	public void setGrpNm(String grpNm) {
		this.grpNm = grpNm;
	}
	public String getSegNm() {
		return segNm;
	}
	public void setSegNm(String segNm) {
		this.segNm = segNm;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String deptNm) {
		dept_nm = deptNm;
	}
	public String getCtgrLCD() {
		return ctgrLCD;
	}
	public void setCtgrLCD(String ctgrLCD) {
		this.ctgrLCD = ctgrLCD;
	}
	public String getCtgrMCD() {
		return ctgrMCD;
	}
	public void setCtgrMCD(String ctgrMCD) {
		this.ctgrMCD = ctgrMCD;
	}
	public String getCtgrSCD() {
		return ctgrSCD;
	}
	public void setCtgrSCD(String ctgrSCD) {
		this.ctgrSCD = ctgrSCD;
	}
	public String getCtgrLnm() {
		return ctgrLnm;
	}
	public void setCtgrLnm(String ctgrLnm) {
		this.ctgrLnm = ctgrLnm;
	}
	public String getCtgrMnm() {
		return ctgrMnm;
	}
	public void setCtgrMnm(String ctgrMnm) {
		this.ctgrMnm = ctgrMnm;
	}
	public String getCtgrSnm() {
		return ctgrSnm;
	}
	public void setCtgrSnm(String ctgrSnm) {
		this.ctgrSnm = ctgrSnm;
	}
	public String getSumQty() {
		return sumQty;
	}
	public void setSumQty(String sumQty) {
		this.sumQty = sumQty;
	}
	public String getSumTm() {
		return sumTm;
	}
	public void setSumTm(String sumTm) {
		this.sumTm = sumTm;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getSourceGubun() {
		return sourceGubun;
	}
	public void setSourceGubun(String sourceGubun) {
		this.sourceGubun = sourceGubun;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public Long getArrangeCount() {
		return arrangeCount;
	}
	public void setArrangeCount(Long arrangeCount) {
		this.arrangeCount = arrangeCount;
	}
	public Long getArrangeDuration() {
		return arrangeDuration;
	}
	public void setArrangeDuration(Long arrangeDuration) {
		this.arrangeDuration = arrangeDuration;
	}
	
	
}
