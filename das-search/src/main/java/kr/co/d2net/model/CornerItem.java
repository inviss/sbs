package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="corner")
@XmlAccessorType(XmlAccessType.FIELD)
public class CornerItem {
	
	@XmlElement(name="cn_id")
	private Long cnId;
	@XmlElement(name="cn_nm")
	private String cnNm;
	@XmlElement(name="rpimg_kfrm_seq")
	private Integer rpimgKfrmSeq;
	@XmlElement(name="som")
	private String som;
	@XmlElement(name="eom")
	private String eom;
	@XmlElement(name="cn_type_cd")
	private String cnTypeCd;
	@XmlElement(name="rpimg_ct_id")
	private Long rpimgCtId;
	@XmlElement(name="cn_info")
	private String cnInfo;
	@XmlElement(name="ct_id")
	private Long ctId;
	@XmlElement(name="ct_nm")
	private String ctNm;
	@XmlElement(name="reg_dt")
	private String regDt;
	@XmlElement(name="ct_leng")
	private String ctLeng;
	@XmlElement(name="duration")
	private String duration;
	@XmlElement(name="ct_seq")
	private Integer ctSeq;
	@XmlElement(name="kfrm_path")
	private String kfrmPath;
	@XmlElement(name="kfrm_px_cd")
	private String kfrmPxCd;
	@XmlElement(name="vd_qlty")
	private String vdQlty;
	@XmlElement(name="asp_rto_cd")
	private String aspRtoCd;
	@XmlElement(name="ct_cla")
	private String ctCla;
	@XmlElement(name="tot_kfrm_nums")
	private Integer totKfrmNums;
	@XmlElement(name="media_id")
	private String mediaId;
	@XmlElement(name="cont")
	private String cont;
	@XmlElement(name="cti_id")
	private Long ctiId;
	@XmlElement(name="ingest_eq_id")
	private String ingestEqId;
	@XmlElement(name="fl_path")
	private String flPath;
	@XmlElement(name="wrk_file_nm")
	private String wrkFileNm;
	@XmlElement(name="cti_fmt")
	private String ctiFmt;
	@XmlElement(name="fl_sz")
	private Long flSz;
	@XmlElement(name="arch_ste_yn")
	private String archSteYn;
	@XmlElement(name="vd_hresol")
	private String vdHresol;
	@XmlElement(name="vd_vresol")
	private String vdvresol;
	@XmlElement(name="bit_rt")
	private String bitRt;
	@XmlElement(name="frm_per_sec")
	private String frmPerSec;
	@XmlElement(name="aud_samp_frq")
	private String audSampFrq;
	@XmlElement(name="audio_bdwt")
	private String audioBdwt;
	@XmlElement(name="s_duration")
	private Long sDuration;
	@XmlElement(name="e_duration")
	private Long eDuration;
	
	
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
	public String getCnInfo() {
		return cnInfo;
	}
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
	public String getCont() {
		return cont;
	}
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
