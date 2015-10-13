package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.app.das.util.AdaptorCDATA;
import com.sun.xml.txw2.annotation.XmlCDATA;

@XmlRootElement(name="corner")
@XmlAccessorType(XmlAccessType.FIELD)
public class CornerItem {
	
	@XmlElement(name="CN_ID")
	private Long cnId;
	@XmlElement(name="CN_NM")
	private String cnNm;
	@XmlElement(name="RPIMG_KFRM_SEQ")
	private Integer rpimgKfrmSeq;
	@XmlElement(name="SOM")
	private String som;
	@XmlElement(name="EOM")
	private String eom;
	@XmlElement(name="CN_TYPE_CD")
	private String cnTypeCd;
	@XmlElement(name="RPIMG_CT_ID")
	private Long rpimgCtId;
	@XmlElement(name="META_RPIMG_KFRM_SEQ")
	private Long metaRpimgKfrmSeq;
	@XmlElement(name="META_RPIMG_CT_ID")
	private Long metaRpimgCtId;
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="CN_INFO")
	private String cnInfo;
	@XmlElement(name="CT_ID")
	private Long ctId;
	@XmlElement(name="CT_NM")
	private String ctNm;
	@XmlElement(name="REG_DT")
	private String regDt;
	@XmlElement(name="CT_LENG")
	private String ctLeng;
	@XmlElement(name="DURATION")
	private String duration;
	@XmlElement(name="CT_SEQ")
	private Integer ctSeq;
	@XmlElement(name="KFRM_PATH")
	private String kfrmPath;
	@XmlElement(name="KFRM_PX_CD")
	private String kfrmPxCd;
	@XmlElement(name="VD_QLTY")
	private String vdQlty;
	@XmlElement(name="ASP_RTO_CD")
	private String aspRtoCd;
	@XmlElement(name="CT_CLA")
	private String ctCla;
	@XmlElement(name="TOT_KFRM_NUMS")
	private Integer totKfrmNums;
	@XmlElement(name="MEDIA_ID")
	private String mediaId;
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="CONT")
	private String cont;
	@XmlElement(name="CTI_ID")
	private Long ctiId;
	@XmlElement(name="INGEST_EQ_ID")
	private String ingestEqId;
	@XmlElement(name="FL_PATH")
	private String flPath;
	@XmlElement(name="WRK_FILE_NM")
	private String wrkFileNm;
	@XmlElement(name="CTI_FMT")
	private String ctiFmt;
	@XmlElement(name="FL_SZ")
	private Long flSz;
	@XmlElement(name="ARCH_STE_YN")
	private String archSteYn;
	@XmlElement(name="VD_HRESOL")
	private String vdHresol;
	@XmlElement(name="VD_VRESOL")
	private String vdvresol;
	@XmlElement(name="BIT_RT")
	private String bitRt;
	@XmlElement(name="FRM_PER_SEC")
	private String frmPerSec;
	@XmlElement(name="AUD_SAMP_FRQ")
	private String audSampFrq;
	@XmlElement(name="AUDIO_BDWT")
	private String audioBdwt;
	@XmlElement(name="S_DURATION")
	private Long sDuration;
	@XmlElement(name="E_DURATION")
	private Long eDuration;
	
	
	public Long getMetaRpimgKfrmSeq() {
		return metaRpimgKfrmSeq;
	}
	public void setMetaRpimgKfrmSeq(Long metaRpimgKfrmSeq) {
		this.metaRpimgKfrmSeq = metaRpimgKfrmSeq;
	}
	public Long getMetaRpimgCtId() {
		return metaRpimgCtId;
	}
	public void setMetaRpimgCtId(Long metaRpimgCtId) {
		this.metaRpimgCtId = metaRpimgCtId;
	}
	public Long getCnId() {
		return cnId;
	}
	public void setCnId(Long cnId) {
		this.cnId = cnId;
	}
	public String getCnNm() {
		return cnNm;
	}
	public void setCnNm(String cnNm) {
		this.cnNm = cnNm;
	}
	public Integer getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}
	public void setRpimgKfrmSeq(Integer rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
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
	public String getCnTypeCd() {
		return cnTypeCd;
	}
	public void setCnTypeCd(String cnTypeCd) {
		this.cnTypeCd = cnTypeCd;
	}
	public Long getRpimgCtId() {
		return rpimgCtId;
	}
	public void setRpimgCtId(Long rpimgCtId) {
		this.rpimgCtId = rpimgCtId;
	}
	@XmlCDATA
	public String getCnInfo() {
		return cnInfo;
	}
	@XmlCDATA
	public void setCnInfo(String cnInfo) {
		this.cnInfo = cnInfo;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getCtLeng() {
		return ctLeng;
	}
	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Integer getCtSeq() {
		return ctSeq;
	}
	public void setCtSeq(Integer ctSeq) {
		this.ctSeq = ctSeq;
	}
	public String getKfrmPath() {
		return kfrmPath;
	}
	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public String getKfrmPxCd() {
		return kfrmPxCd;
	}
	public void setKfrmPxCd(String kfrmPxCd) {
		this.kfrmPxCd = kfrmPxCd;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public String getCtCla() {
		return ctCla;
	}
	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}
	public Integer getTotKfrmNums() {
		return totKfrmNums;
	}
	public void setTotKfrmNums(Integer totKfrmNums) {
		this.totKfrmNums = totKfrmNums;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@XmlCDATA
	public String getCont() {
		return cont;
	}
	@XmlCDATA
	public void setCont(String cont) {
		this.cont = cont;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public String getIngestEqId() {
		return ingestEqId;
	}
	public void setIngestEqId(String ingestEqId) {
		this.ingestEqId = ingestEqId;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String getWrkFileNm() {
		return wrkFileNm;
	}
	public void setWrkFileNm(String wrkFileNm) {
		this.wrkFileNm = wrkFileNm;
	}
	public String getCtiFmt() {
		return ctiFmt;
	}
	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}
	public Long getFlSz() {
		return flSz;
	}
	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}
	public String getArchSteYn() {
		return archSteYn;
	}
	public void setArchSteYn(String archSteYn) {
		this.archSteYn = archSteYn;
	}
	public String getVdHresol() {
		return vdHresol;
	}
	public void setVdHresol(String vdHresol) {
		this.vdHresol = vdHresol;
	}
	public String getVdvresol() {
		return vdvresol;
	}
	public void setVdvresol(String vdvresol) {
		this.vdvresol = vdvresol;
	}
	public String getBitRt() {
		return bitRt;
	}
	public void setBitRt(String bitRt) {
		this.bitRt = bitRt;
	}
	public String getFrmPerSec() {
		return frmPerSec;
	}
	public void setFrmPerSec(String frmPerSec) {
		this.frmPerSec = frmPerSec;
	}
	public String getAudSampFrq() {
		return audSampFrq;
	}
	public void setAudSampFrq(String audSampFrq) {
		this.audSampFrq = audSampFrq;
	}
	public String getAudioBdwt() {
		return audioBdwt;
	}
	public void setAudioBdwt(String audioBdwt) {
		this.audioBdwt = audioBdwt;
	}
	public Long getsDuration() {
		return sDuration;
	}
	public void setsDuration(Long sDuration) {
		this.sDuration = sDuration;
	}
	public Long geteDuration() {
		return eDuration;
	}
	public void seteDuration(Long eDuration) {
		this.eDuration = eDuration;
	}
	
	@XmlElement(name="Annot")
	private Annot annot;
	public Annot getAnnot() {
		return annot;
	}
	public void setAnnot(Annot annot) {
		this.annot = annot;
	}
}
