package com.app.das.business.transfer;
import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 수동아카이브 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ManualArchiveDO extends DTO {
	
	/**
	 * 파일경로
	 */
	private String fl_path = Constants.BLANK;
	/**
	 * 원본 미디어 ID
	 */
	private String org_media_id = Constants.BLANK;
	/**
	 * 새 미디어 ID
	 */
	private String new_media_id = Constants.BLANK;
	/**
	 * CMS ID
	 */
	private String cms_id = Constants.BLANK;
	/**
	 * 대분류코드(100 소재 200 프로그램)
	 */
	private String ctgr_l_cd = Constants.BLANK;
	/**
	 * 제목
	 */
	private String title = Constants.BLANK;
	/**
	 * 부제
	 */
	private String sub_ttl = Constants.BLANK;
	/**
	 * 회차
	 */
	private String epis_no = Constants.BLANK;
	/**
	 * 콘텐츠 구분
	 */
	private String ct_cla = Constants.BLANK;
	/**
	 * 사용등급코드
	 */
	private String rist_clf_cd = Constants.BLANK;
	/**
	 * 촬영일
	 */
	private String fm_dt = Constants.BLANK;
	/**
	 * 촬영지
	 */
	private String cmr_place = Constants.BLANK;
	/**
	 * 제작PD
	 */
	private String producer_nm = Constants.BLANK;
	/**
	 * 가로 해상도
	 */
	private String vd_hresol = "0";
	/**
	 * 세로 해상도
	 */
	private String vd_vresol = "0";
	/**
	 * 화면비
	 */
	private String asp_rto_cd= Constants.BLANK;
	/**
	 * 오디오 송출형태
	 */
	private String audio_type= Constants.BLANK;
	
	/**
	 * 아카이브 경로
	 */
	private String arch_route= Constants.BLANK;
	/**
	 * 영상id(고화질)
	 */
	private long cti_idForHigh= 0;
	/**
	 * 영상id(저화질)
	 */
	private long cti_idForLow= 0;
	/**
	 * 영상id
	 */
	private long ct_id= 0;
	/**
	 * 코너id
	 */
	private long cn_id= 0;
	
	/**
	 * 주석id
	 */
	private long annot_id= 0;
	
	/**
	 * 마스터id
	 */
	private long master_id=0;
	
	/**
	 * 파일 크기
	 */
	private long file_size= 0;
	
	/**
	 * 컨텐츠 유형
	 */
	private String ct_typ= Constants.BLANK;
	
	
	/**
	 * 미디어id 존재 여부
	 */
	private String ismedia_yn= Constants.BLANK;
	/**
	 * 녹음방식코드
	 */
	private String recode_yn= Constants.BLANK;
	
	
	
	
	//2012.4.26 
	/**
	 * 회사코드
	 */
	private String cocd= Constants.BLANK;
	
	
	/**
	 * 채널코드
	 */
	private String chennel= Constants.BLANK;
	
	/**
	 * DTL 구분코드
	 */
	private String dtl_gubun= Constants.BLANK;
	
	
	

	public String getDtl_gubun() {
		return dtl_gubun;
	}
	public void setDtl_gubun(String dtlGubun) {
		dtl_gubun = dtlGubun;
	}
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
	public String getRecode_yn() {
		return recode_yn;
	}
	public void setRecode_yn(String recodeYn) {
		recode_yn = recodeYn;
	}
	public String getIsmedia_yn() {
		return ismedia_yn;
	}
	public void setIsmedia_yn(String ismediaYn) {
		ismedia_yn = ismediaYn;
	}
	public String getCt_typ() {
		return ct_typ;
	}
	public void setCt_typ(String ctTyp) {
		ct_typ = ctTyp;
	}
	public long getCti_idForHigh() {
		return cti_idForHigh;
	}
	public void setCti_idForHigh(long ctiIdForHigh) {
		cti_idForHigh = ctiIdForHigh;
	}
	public long getCti_idForLow() {
		return cti_idForLow;
	}
	public void setCti_idForLow(long ctiIdForLow) {
		cti_idForLow = ctiIdForLow;
	}
	public long getCt_id() {
		return ct_id;
	}
	public void setCt_id(long ctId) {
		ct_id = ctId;
	}
	public long getCn_id() {
		return cn_id;
	}
	public void setCn_id(long cnId) {
		cn_id = cnId;
	}
	public long getAnnot_id() {
		return annot_id;
	}
	public void setAnnot_id(long annotId) {
		annot_id = annotId;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long fileSize) {
		file_size = fileSize;
	}
	public String getArch_route() {
		return arch_route;
	}
	public void setArch_route(String archRoute) {
		arch_route = archRoute;
	}
	
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public String getFl_path() {
		return fl_path;
	}
	public void setFl_path(String flPath) {
		fl_path = flPath;
	}
	public String getOrg_media_id() {
		return org_media_id;
	}
	public void setOrg_media_id(String orgMediaId) {
		org_media_id = orgMediaId;
	}
	public String getNew_media_id() {
		return new_media_id;
	}
	public void setNew_media_id(String newMediaId) {
		new_media_id = newMediaId;
	}
	public String getCms_id() {
		return cms_id;
	}
	public void setCms_id(String cmsId) {
		cms_id = cmsId;
	}
	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}
	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSub_ttl() {
		return sub_ttl;
	}
	public void setSub_ttl(String subTtl) {
		sub_ttl = subTtl;
	}
	public String getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(String episNo) {
		epis_no = episNo;
	}
	public String getCt_cla() {
		return ct_cla;
	}
	public void setCt_cla(String ctCla) {
		ct_cla = ctCla;
	}
	public String getRist_clf_cd() {
		return rist_clf_cd;
	}
	public void setRist_clf_cd(String ristClfCd) {
		rist_clf_cd = ristClfCd;
	}
	public String getFm_dt() {
		return fm_dt;
	}
	public void setFm_dt(String fmDt) {
		fm_dt = fmDt;
	}
	public String getCmr_place() {
		return cmr_place;
	}
	public void setCmr_place(String cmrPlace) {
		cmr_place = cmrPlace;
	}
	public String getProducer_nm() {
		return producer_nm;
	}
	public void setProducer_nm(String producerNm) {
		producer_nm = producerNm;
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
	public String getAsp_rto_cd() {
		return asp_rto_cd;
	}
	public void setAsp_rto_cd(String aspRtoCd) {
		asp_rto_cd = aspRtoCd;
	}
	public String getAudio_type() {
		return audio_type;
	}
	public void setAudio_type(String audioType) {
		audio_type = audioType;
	}
	
	
}
