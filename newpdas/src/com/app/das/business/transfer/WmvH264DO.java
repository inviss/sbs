package com.app.das.business.transfer;
import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * wmv h264 전환 beans
 * @author asura207
 *
 */
public class WmvH264DO extends DTO {
	
	/**
	 * 영상 인스턴스 id

	 */
	private long cti_id =0;
	/**
	 * 영상 id

	 */
	private long ct_id =0;
	
	/**
	 * 파일명

	 */
	private String fl_nm = Constants.BLANK;
	
	/**
	 * 파일경로

	 */
	private String fl_path = Constants.BLANK;
	
	/**
	 * 조회 갯수

	 */
	private String getcount = Constants.BLANK;

	/**
	 * 진행상태

	 */
	private String job_status = Constants.BLANK;

	/**
	 * 장비ID

	 */
	private String eq_id = Constants.BLANK;

	

	/**
	 * 영상명

	 */
	private String ct_nm = Constants.BLANK;

	
	
	
	/**
	 * 비트전송률

	 */
	private String bit_rt = Constants.BLANK;
	/**
	 * 초당 키프레임

	 */
	private String frm_per_sec = Constants.BLANK;
	/**
	 * 오디오 대역폭

	 */
	private String drp_frm_yn = Constants.BLANK;
	/**
	 * 오디오 대역폭

	 */
	private String audio_bdwt = Constants.BLANK;
	/**
	 * 파일크기

	 */
	private long fl_sz = 0;
	/**
	 * 오디오 샘플링

	 */
	private String audio_samp_frq = Constants.BLANK;
	
	/**
	 * 결과

	 */
	private String result = Constants.BLANK;
	
	
	/**
	 * 가로 해상도

	 */
	private String vd_hresol = Constants.BLANK;
	
	/**
	 * 세로 해상도

	 */
	private String vd_vresol = Constants.BLANK;
	
	/**
	 * 비디오 화질

	 */
	private String vd_qlty = Constants.BLANK;
	
	/**
	 * 영상 그룹

	 */
	private String cti_ids = Constants.BLANK;
	
	/**
	 * 관련영상 ct_id

	 */
	private String rel_ct_id = Constants.BLANK;
	
	/**
	 * 관련영상 명

	 */
	private String rel_ct_nm= Constants.BLANK;
	
	
	/**
	 * 복본여부

	 */
	private String cyn= Constants.BLANK;
	
	

	/**
	 * 기존 복본여부

	 */
	private String old_cyn= Constants.BLANK;
	
	/**
	 * 소산여부

	 */
	private String byn= Constants.BLANK;
	
	/**
	 * key

	 */
	private long key=0;
	
	
	
	/**
	 * 영상길이

	 */
	private String ct_leng= Constants.BLANK;
	
	/**
	 * 아카이브여부

	 */
	private String  dtl_yn= Constants.BLANK;
	
	
	
	
	
	public String getDtl_yn() {
		return dtl_yn;
	}

	public void setDtl_yn(String dtlYn) {
		dtl_yn = dtlYn;
	}

	public String getOld_cyn() {
		return old_cyn;
	}

	public void setOld_cyn(String oldCyn) {
		old_cyn = oldCyn;
	}

	public String getCt_leng() {
		return ct_leng;
	}

	public void setCt_leng(String ctLeng) {
		ct_leng = ctLeng;
	}

	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	public String getCyn() {
		return cyn;
	}

	public void setCyn(String cyn) {
		this.cyn = cyn;
	}

	public String getByn() {
		return byn;
	}

	public void setByn(String byn) {
		this.byn = byn;
	}

	public String getRel_ct_id() {
		return rel_ct_id;
	}

	public void setRel_ct_id(String relCtId) {
		rel_ct_id = relCtId;
	}


	public String getRel_ct_nm() {
		return rel_ct_nm;
	}

	public void setRel_ct_nm(String relCtNm) {
		rel_ct_nm = relCtNm;
	}

	public String getCt_nm() {
		return ct_nm;
	}

	public void setCt_nm(String ctNm) {
		ct_nm = ctNm;
	}

	public String getCti_ids() {
		return cti_ids;
	}

	public void setCti_ids(String ctiIds) {
		cti_ids = ctiIds;
	}

	public String getVd_qlty() {
		return vd_qlty;
	}

	public void setVd_qlty(String vdQlty) {
		vd_qlty = vdQlty;
	}

	public long getCt_id() {
		return ct_id;
	}

	public void setCt_id(long ctId) {
		ct_id = ctId;
	}

	public String getVd_hresol() {
		return vd_hresol;
	}

	public void setVd_hresol(String vdHresol) {
		vd_hresol = vdHresol;
	}

	public String getVd_vresol() {
		return vd_vresol;
	}

	public void setVd_vresol(String vdVresol) {
		vd_vresol = vdVresol;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getBit_rt() {
		return bit_rt;
	}

	public void setBit_rt(String bitRt) {
		bit_rt = bitRt;
	}

	public String getFrm_per_sec() {
		return frm_per_sec;
	}

	public void setFrm_per_sec(String frmPerSec) {
		frm_per_sec = frmPerSec;
	}

	public String getDrp_frm_yn() {
		return drp_frm_yn;
	}

	public void setDrp_frm_yn(String drpFrmYn) {
		drp_frm_yn = drpFrmYn;
	}

	public String getAudio_bdwt() {
		return audio_bdwt;
	}

	public void setAudio_bdwt(String audioBdwt) {
		audio_bdwt = audioBdwt;
	}

	public long getFl_sz() {
		return fl_sz;
	}

	public void setFl_sz(long flSz) {
		fl_sz = flSz;
	}

	public String getAudio_samp_frq() {
		return audio_samp_frq;
	}

	public void setAudio_samp_frq(String audioSampFrq) {
		audio_samp_frq = audioSampFrq;
	}

	public String getEq_id() {
		return eq_id;
	}

	public void setEq_id(String eqId) {
		eq_id = eqId;
	}

	public String getJob_status() {
		return job_status;
	}

	public void setJob_status(String jobStatus) {
		job_status = jobStatus;
	}

	public long getCti_id() {
		return cti_id;
	}

	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}

	public String getFl_nm() {
		return fl_nm;
	}

	public void setFl_nm(String flNm) {
		fl_nm = flNm;
	}

	public String getFl_path() {
		return fl_path;
	}

	public void setFl_path(String flPath) {
		fl_path = flPath;
	}

	public String getGetcount() {
		return getcount;
	}

	public void setGetcount(String getcount) {
		this.getcount = getcount;
	}
	
	
	
}
