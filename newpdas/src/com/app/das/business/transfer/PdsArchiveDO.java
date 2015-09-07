package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * pds 아카이브 정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * 
 * @author ysk523
 * 
 */
public class PdsArchiveDO extends DTO {
	/**
	 * T/C 시작점
	 */
	private String som= Constants.BLANK;
	/**
	 * T/C 종료점
	 */
	private String eom= Constants.BLANK;
	/**
	 * 비디오수평해상도
	 */
	private int vd_hresol;
	/**
	 * 비디오수직해상도
	 */
	private int vd_vresol;
	/**
	 * 파일사이즈
	 */
	private long fl_sz;
	/**
	 * 비트전송율
	 */
	private String bit_rt= Constants.BLANK;
	/**
	 * 오디오샘플링
	 */
	private float aud_samp_frq;
	/**
	 * 오디오대역폭
	 */
	private long audio_bdwt;

	/**
	 * 회차번호
	 */
	private int epis_no;
	/**
	 * 제작PD
	 */
	private String producer_nm = Constants.BLANK;
	/**
	 * 촬영지
	 */
	private String cmr_place = Constants.BLANK;
	/**
	 * 촬영일시
	 */
	private String fm_dt = Constants.BLANK;

	/**
	 * 저작권자
	 */
	private String cprt_nm = Constants.BLANK;
	/**
	 * 저작권수준
	 */
	private String cprt_cd = Constants.BLANK;

	/**
	 * 사용등급
	 */
	private String rist_clf_cd = Constants.BLANK;

	/**
	 * 요청자
	 */
	private String req_nm = Constants.BLANK;
	/**
	 * 요청자ID
	 */
	private String req_id = Constants.BLANK;
	/**
	 * 요청일시
	 */
	private String req_dt = Constants.BLANK;
	/**
	 * 미디어ID
	 */
	private String media_id = Constants.BLANK;
	/**
	 * 녹음방식코드
	 */
	private String record_type_cd = Constants.BLANK;
	/**
	 * 초당프레임
	 */
	private String frm_per_sec = Constants.BLANK;
	/**
	 * 원파일명
	 */
	private String org_file_nm = Constants.BLANK;
	/**
	 * 아카이브요청스토리지의 Path
	 */
	private String storage_path = Constants.BLANK;
	/**
	 * 오디오여부
	 */
	private String audio_yn = Constants.BLANK;
	/**
	 * 드롭프레임여부
	 */
	private String drop_yn = Constants.BLANK;
	/**
	 * 콘텐츠유형
	 */
	private String ct_typ = Constants.BLANK;
	/**
	 * 콘텐츠길이
	 */
	private String ct_leng = Constants.BLANK;
	/**
	 * 종횡비코드
	 */
	private String asp_rto_cd = Constants.BLANK;
	/**
	 * 화질코드
	 */
	private String vd_qulty = Constants.BLANK;
	/**
	 * 데이타구분코드
	 */
	private String data_stat = Constants.BLANK;
	/**
	 * 콘텐츠구분
	 */
	private String con_cla = Constants.BLANK;
	/**
	 * 클립명
	 */
	private String clip_nm = Constants.BLANK;

	/**
	 * 방송길이
	 */
	private String brd_leng = Constants.BLANK;
	/**
	 * 방송종료시간(시분초)
	 */
	private String brd_end_hms = Constants.BLANK;
	/**
	 * 방송시작시간(시분초)
	 */
	private String brd_bgn_hms = Constants.BLANK;
	/**
	 * 방송일자
	 */
	private String brd_dd = Constants.BLANK;
	/**
	 * 타이틀
	 */
	private String title = Constants.BLANK;

	/**
	 * 프로그램명
	 */
	private String pgm_nm = Constants.BLANK;
	/**
	 * PDS TM IP
	 */
	private String tm_ip = Constants.BLANK;

	/**
	 * 자료상태
	 */
	private String status = Constants.BLANK;
	/**
	 * 프리뷰파일명
	 */
	private String preview_file_nm = Constants.BLANK;
	/**
	 * pds cms 프로그램 id
	 */
	private String pds_cms_id = Constants.BLANK;
	/**
	 * 프리뷰 id
	 */
	private long preview_id;
	/**
	 * 프리뷰 파일 id
	 */
	private long preview_file_id;

	/**
	 * 파일 경로
	 */
	private String fl_path = Constants.BLANK;

	/**
	 * 파일 명
	 */
	private String fl_nm = Constants.BLANK;

	/**
	 * 영상구분
	 */
	private String ct_cla = Constants.BLANK;
	/**
	 * 시청등급
	 */
	private String view_gr_cd = Constants.BLANK;

	/**
	 * 비고
	 */
	private String archivecoments = Constants.BLANK;

	/**
	 * 촬영감독
	 */
	private String cmr_drt_nm = Constants.BLANK;

	/**
	 * 프리뷰 유형
	 */
	private String kind = Constants.BLANK;

	// key값들

	private long master_id;
	private long cn_id;
	private long cti_id;
	private long ct_id;
	private long cti_idForlow;

	private String copy_to_group = Constants.BLANK;

	/**
	 * 사용제한 설명
	 */
	private String annot_clf_cont = Constants.BLANK;

	
	/**
	 * 프리뷰 경로
	 */
	private String preview_path = Constants.BLANK;
	
	/**
	 * 프리뷰 
	 */
	private String previewnotefile  = Constants.BLANK;
	
	/**
	 * 프리뷰 첨부파일명
	 */
	private String attatchedfile  = Constants.BLANK;
	/**
	 * 프리뷰 제목
	 */
	private String preview_subj = Constants.BLANK;
	/**
	 * 프리뷰 내용
	 */
	private String preview_cont = Constants.BLANK;

	/**
	 * 카운트
	 */
	private int count=0;
	
	
	//2012.4.26
	
	/**
	 * 회사코드
	 */
	private String cocd = Constants.BLANK;
	
	/**
	 * 채널코드
	 */
	private String chennel = Constants.BLANK;
	
	/**
	 * 아카이브 경로
	 */
	private String arch_route = Constants.BLANK;
	/**
	 * DTL 구분
	 */
	private String dtl_gubun = Constants.BLANK;
	
	
	
	/**
	 *대분류
	 */
	private String ctgr_l_cd = Constants.BLANK;
	
	
	
	
	

	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}

	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
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

	public String getArch_route() {
		return arch_route;
	}

	public void setArch_route(String archRoute) {
		arch_route = archRoute;
	}

	public String getDtl_gubun() {
		return dtl_gubun;
	}

	public void setDtl_gubun(String dtlGubun) {
		dtl_gubun = dtlGubun;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPreview_path() {
		return preview_path;
	}

	public void setPreview_path(String previewPath) {
		preview_path = previewPath;
	}

	public String getPreviewnotefile() {
		return previewnotefile;
	}

	public void setPreviewnotefile(String previewnotefile) {
		this.previewnotefile = previewnotefile;
	}

	public String getAttatchedfile() {
		return attatchedfile;
	}

	public void setAttatchedfile(String attatchedfile) {
		this.attatchedfile = attatchedfile;
	}

	public String getPreview_subj() {
		return preview_subj;
	}

	public void setPreview_subj(String previewSubj) {
		preview_subj = previewSubj;
	}

	public String getPreview_cont() {
		return preview_cont;
	}

	public void setPreview_cont(String previewCont) {
		preview_cont = previewCont;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getAnnot_clf_cont() {
		return annot_clf_cont;
	}

	public void setAnnot_clf_cont(String annotClfCont) {
		annot_clf_cont = annotClfCont;
	}

	public String getCmr_drt_nm() {
		return cmr_drt_nm;
	}

	public void setCmr_drt_nm(String cmrDrtNm) {
		cmr_drt_nm = cmrDrtNm;
	}

	public String getArchivecoments() {
		return archivecoments;
	}

	public void setArchivecoments(String archivecoments) {
		this.archivecoments = archivecoments;
	}

	public String getCopy_to_group() {
		return copy_to_group;
	}

	public void setCopy_to_group(String copy_to_group) {
		this.copy_to_group = copy_to_group;
	}

	public String getView_gr_cd() {
		return view_gr_cd;
	}

	public void setView_gr_cd(String viewGrCd) {
		view_gr_cd = viewGrCd;
	}

	public String getCt_cla() {
		return ct_cla;
	}

	public void setCt_cla(String ctCla) {
		ct_cla = ctCla;
	}

	public long getCti_idForlow() {
		return cti_idForlow;
	}

	public void setCti_idForlow(long ctiIdForlow) {
		cti_idForlow = ctiIdForlow;
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

	public String getPds_cms_id() {
		return pds_cms_id;
	}

	public void setPds_cms_id(String pdsCmsId) {
		pds_cms_id = pdsCmsId;
	}

	public long getPreview_file_id() {
		return preview_file_id;
	}

	public void setPreview_file_id(long previewFileId) {
		preview_file_id = previewFileId;
	}

	public long getPreview_id() {
		return preview_id;
	}

	public void setPreview_id(long previewId) {
		preview_id = previewId;
	}

	public String getPreview_file_nm() {
		return preview_file_nm;
	}

	public void setPreview_file_nm(String previewFileNm) {
		preview_file_nm = previewFileNm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getMaster_id() {
		return master_id;
	}

	public void setMaster_id(long masterId) {
		master_id = masterId;
	}

	public long getCn_id() {
		return cn_id;
	}

	public void setCn_id(long cnId) {
		cn_id = cnId;
	}

	public long getCti_id() {
		return cti_id;
	}

	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}

	public long getCt_id() {
		return ct_id;
	}

	public void setCt_id(long ctId) {
		ct_id = ctId;
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

	public int getVd_hresol() {
		return vd_hresol;
	}

	public void setVd_hresol(int vdHresol) {
		vd_hresol = vdHresol;
	}

	public int getVd_vresol() {
		return vd_vresol;
	}

	public void setVd_vresol(int vdVresol) {
		vd_vresol = vdVresol;
	}

	public long getFl_sz() {
		return fl_sz;
	}

	public void setFl_sz(long flSz) {
		fl_sz = flSz;
	}

	public String getBit_rt() {
		return bit_rt;
	}

	public void setBit_rt(String bitRt) {
		bit_rt = bitRt;
	}

	public float getAud_samp_frq() {
		return aud_samp_frq;
	}

	public void setAud_samp_frq(float audSampFrq) {
		aud_samp_frq = audSampFrq;
	}

	public long getAudio_bdwt() {
		return audio_bdwt;
	}

	public void setAudio_bdwt(long audioBdwt) {
		audio_bdwt = audioBdwt;
	}

	public int getEpis_no() {
		return epis_no;
	}

	public void setEpis_no(int episNo) {
		epis_no = episNo;
	}

	public String getProducer_nm() {
		return producer_nm;
	}

	public void setProducer_nm(String producerNm) {
		producer_nm = producerNm;
	}

	public String getCmr_place() {
		return cmr_place;
	}

	public void setCmr_place(String cmrPlace) {
		cmr_place = cmrPlace;
	}

	public String getFm_dt() {
		return fm_dt;
	}

	public void setFm_dt(String fmDt) {
		fm_dt = fmDt;
	}

	public String getCprt_nm() {
		return cprt_nm;
	}

	public void setCprt_nm(String cprtNm) {
		cprt_nm = cprtNm;
	}

	public String getCprt_cd() {
		return cprt_cd;
	}

	public void setCprt_cd(String cprtCd) {
		cprt_cd = cprtCd;
	}

	public String getRist_clf_cd() {
		return rist_clf_cd;
	}

	public void setRist_clf_cd(String ristClfCd) {
		rist_clf_cd = ristClfCd;
	}

	public String getReq_nm() {
		return req_nm;
	}

	public void setReq_nm(String reqNm) {
		req_nm = reqNm;
	}

	public String getReq_id() {
		return req_id;
	}

	public void setReq_id(String reqId) {
		req_id = reqId;
	}

	public String getReq_dt() {
		return req_dt;
	}

	public void setReq_dt(String reqDt) {
		req_dt = reqDt;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}

	public String getRecord_type_cd() {
		return record_type_cd;
	}

	public void setRecord_type_cd(String recordTypeCd) {
		record_type_cd = recordTypeCd;
	}

	public String getFrm_per_sec() {
		return frm_per_sec;
	}

	public void setFrm_per_sec(String frmPerSec) {
		frm_per_sec = frmPerSec;
	}

	public String getOrg_file_nm() {
		return org_file_nm;
	}

	public void setOrg_file_nm(String orgFileNm) {
		org_file_nm = orgFileNm;
	}

	public String getStorage_path() {
		return storage_path;
	}

	public void setStorage_path(String storagePath) {
		storage_path = storagePath;
	}

	public String getAudio_yn() {
		return audio_yn;
	}

	public void setAudio_yn(String audioYn) {
		audio_yn = audioYn;
	}

	public String getDrop_yn() {
		return drop_yn;
	}

	public void setDrop_yn(String dropYn) {
		drop_yn = dropYn;
	}

	public String getCt_typ() {
		return ct_typ;
	}

	public void setCt_typ(String ctTyp) {
		ct_typ = ctTyp;
	}

	public String getCt_leng() {
		return ct_leng;
	}

	public void setCt_leng(String ctLeng) {
		ct_leng = ctLeng;
	}

	public String getAsp_rto_cd() {
		return asp_rto_cd;
	}

	public void setAsp_rto_cd(String aspRtoCd) {
		asp_rto_cd = aspRtoCd;
	}

	public String getVd_qulty() {
		return vd_qulty;
	}

	public void setVd_qulty(String vdQulty) {
		vd_qulty = vdQulty;
	}

	public String getData_stat() {
		return data_stat;
	}

	public void setData_stat(String dataStat) {
		data_stat = dataStat;
	}

	public String getCon_cla() {
		return con_cla;
	}

	public void setCon_cla(String conCla) {
		con_cla = conCla;
	}

	public String getClip_nm() {
		return clip_nm;
	}

	public void setClip_nm(String clipNm) {
		clip_nm = clipNm;
	}

	public String getBrd_leng() {
		return brd_leng;
	}

	public void setBrd_leng(String brdLeng) {
		brd_leng = brdLeng;
	}

	public String getBrd_end_hms() {
		return brd_end_hms;
	}

	public void setBrd_end_hms(String brdEndHms) {
		brd_end_hms = brdEndHms;
	}

	public String getBrd_bgn_hms() {
		return brd_bgn_hms;
	}

	public void setBrd_bgn_hms(String brdBgnHms) {
		brd_bgn_hms = brdBgnHms;
	}

	public String getBrd_dd() {
		return brd_dd;
	}

	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPgm_nm() {
		return pgm_nm;
	}

	public void setPgm_nm(String pgmNm) {
		pgm_nm = pgmNm;
	}

	public String getTm_ip() {
		return tm_ip;
	}

	public void setTm_ip(String tmIp) {
		tm_ip = tmIp;
	}

}
