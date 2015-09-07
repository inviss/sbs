package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;

/**
 * pds 다운정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class PdsDownDO extends DTO 
{

	/**
	 * 메타데이터 genrator 이름
	 */
	private String generator_name      = Constants.BLANK;
	/**
	 *  메타데이터 genrator 버전
	 */
	private String generator_ver         = Constants.BLANK;
	/**
	 * 프로그램 id
	 */
	private String program_id     = Constants.BLANK;
	/**
	 *  프로그램명
	 */
	private String   program_name          = Constants.BLANK;
	/**
	 *  제목
	 */
	private String   program_title          = Constants.BLANK;
	/**
	 * 부순번호
	 */
	private String part_number          = Constants.BLANK;
	/**
	 * 부제
	 */
	private String program_subtitle          = Constants.BLANK;
	
	/**
	 * 파일제목명
	 */
	private String clip_title            = Constants.BLANK;
	/**
	 * 회차번호
	 */
	private String program_sequence_number           = Constants.BLANK;
	/**
	 * 소재 종류
	 */
	private String broadcast_event_kind = Constants.BLANK;
	/**
	 * 미디어 id
	 */
	private String media_id           = Constants.BLANK;
	/**
	 * 파일 이름
	 */
	private String clip_name 		= Constants.BLANK;
	/**
	 * 카트번호
	 */
	private int cart_no;
	/**
	 * 카트시퀀스
	 * 	 */
	private int cart_seq;
	/**
	 * 다운로드명
	 * 	 */
	private String down_nm 		= Constants.BLANK;
	
	/**
	 * 다운 경로
	 * 	 */
	private String down_path 		= Constants.BLANK;
	/**
	 * pds node_id
	 * 	 */
	private String node_id 		= Constants.BLANK;
	
	
	/**
	 * 파일 경로
	 * 	 */
	private String fl_path 		= Constants.BLANK;
	
	/**
	 * 카테고리
	 * 
	 * 	 */
	private String category 		= Constants.BLANK;
	
	/**
	 * 스토리지명
	 * 	 */
	private String storagename 		= Constants.BLANK;
	
	
	
	/**
	 * 미디어 종류
	 * 	 */
	private String media_type 		= Constants.BLANK;
	
	
	/**
	 * 파일명(pds)
	 * 	 */
	private String filename 		= Constants.BLANK;
	
	/**
	 *  파일명(nds,on-air)
	 * 	 */
	private String wrkfilename 		= Constants.BLANK;
	/**
	 * HD여부
	 * 	 */
	private String hdmode 		= Constants.BLANK;
	
	
	/**
	 * 프로그램id
	 * 	 */
	private String pds_program_id 		= Constants.BLANK;
	
	
	/**
	 * 시작점
	 * 	 */
	private String som 		= Constants.BLANK;
	
	
	/**
	 * 종료점
	 * 	 */
	private String eom 		= Constants.BLANK;
	
	
	/**
	 * 수직해상도
	 * 	 */
	private String vd_hresol 		= Constants.BLANK;
	
	
	/**
	 * 수평해상도
	 * 	 */
	private String vd_vresol 		= Constants.BLANK;
	
	
	/**
	 * 비트전송률
	 * 	 */
	private String bit_rt 		= Constants.BLANK;
	
	
	/**
	 * 오디오 샘플링
	 * 	 */
	private String aud_samp_frq 		= Constants.BLANK;
	
	
	/**
	 * 오디오 대역폭
	 * 	 */
	private String aud_bdwt 		= Constants.BLANK;
	
	
	/**
	 * 회차
	 * 	 */
	private long epis_no;
	
	
	/**
	 * 프로듀서명
	 * 	 */
	private String producer_nm 		= Constants.BLANK;
	
	
	/**
	 * 촬영장소
	 * 	 */
	private String cmr_place 		= Constants.BLANK;
	
	
	/**
	 * 촬영일
	 * 	 */
	private String fm_dt 		= Constants.BLANK;
	
	
	/**
	 * 저작권자
	 * 	 */
	private String cprt_nm 		= Constants.BLANK;
	
	
	/**
	 * 저작권유형
	 * 	 */
	private String cprt_cd 		= Constants.BLANK;
	
	
	/**
	 * 사용등급
	 * 	 */
	private String rist_clf_cd 		= Constants.BLANK;
	
	
	/**
	 * 시청등급
	 * 	 */
	private String deliberation_cd 		= Constants.BLANK;
	
	
	/**
	 * 녹음방식코드
	 * 	 */
	private String record_type_cd 		= Constants.BLANK;
	
	
	/**
	 * 초당프레임
	 * 	 */
	private String frm_per_sec 		= Constants.BLANK;
	
	
	/**
	 * 콘텐츠 유형
	 * 	 */
	private String ct_typ 		= Constants.BLANK;
	
	
	/**
	 * 콘텐츠 길이
	 * 	 */
	private String ct_leng 		= Constants.BLANK;
	
	
	/**
	 * 종횡비
	 * 	 */
	private String asp_rto_cd 		= Constants.BLANK;
	
	
	/**
	 * 콘텐츠구분
	 * 	 */
	private String ct_cla 		= Constants.BLANK;
	
	
	/**
	 * 방송길이
	 * 	 */
	private String brd_leng 		= Constants.BLANK;
	
	
	/**
	 * 방송종료시각
	 * 	 */
	private String brd_end_hms 		= Constants.BLANK;
	
	
	
	/**
	 * 방송시작시각
	 * 	 */
	private String brd_bgn_hms 		= Constants.BLANK;
	
	
	
	
	/**
	 * 방송일자
	 * 	 */
	private String brd_dd 		= Constants.BLANK;
	
	
	
	
	/**
	 * 타이틀
	 * 	 */
	private String title 		= Constants.BLANK;
	
	
	
	/**
	 * 프로그램명
	 * 	 */
	private String pgm_nm 		= Constants.BLANK;
	
	
	
	/**
	 * 파일크기
	 * 	 */
	private long fl_sz;
	
	/**
	 * 파일크기(실제 다운)
	 * 	 */
	private long filesize;
	
	
	/**
	 * 유저id
	 * 	 */
	private String user_id 		= Constants.BLANK;
	
	/**
	 * 다운 구분
	 * 	 */
	private String down_gubun 		= Constants.BLANK;
	
	/**
	 * DAS-TM 전송시 사용할 파일(mxf,xml)명
	 */
	private String rename = Constants.BLANK;
	

	/**
	 * 미디어 구분코드
	 */
	private String media_type_cd = Constants.BLANK;
	

	/**
	 * 부제
	 */
	private String sub_ttl = Constants.BLANK;
	

	/**
	 *  등록자 ID
	 */
	private String reg_id = Constants.BLANK;
	

	/**
	 * 방송시간
	 */
	private String brd_dt = Constants.BLANK;
	

	/**
	 * 논리적 저장위치
	 */
	private String logical_tree = Constants.BLANK;
	
	

	/**
	 * 물리적 저장위치
	 */
	private String physical_tree = Constants.BLANK;
	
	

	/**
	 * 유저명
	 */
	private String user_nm = Constants.BLANK;
	
	
	
	
	
	public String getWrkfilename() {
		return wrkfilename;
	}
	public void setWrkfilename(String wrkfilename) {
		this.wrkfilename = wrkfilename;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String userNm) {
		user_nm = userNm;
	}
	public String getLogical_tree() {
		return logical_tree;
	}
	public void setLogical_tree(String logicalTree) {
		logical_tree = logicalTree;
	}
	public String getPhysical_tree() {
		return physical_tree;
	}
	public void setPhysical_tree(String physicalTree) {
		physical_tree = physicalTree;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public String getMedia_type_cd() {
		return media_type_cd;
	}
	public void setMedia_type_cd(String mediaTypeCd) {
		media_type_cd = mediaTypeCd;
	}
	public String getSub_ttl() {
		return sub_ttl;
	}
	public void setSub_ttl(String subTtl) {
		sub_ttl = subTtl;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String regId) {
		reg_id = regId;
	}
	public String getBrd_dt() {
		return brd_dt;
	}
	public void setBrd_dt(String brdDt) {
		brd_dt = brdDt;
	}
	public String getRename() {
		return rename;
	}
	public void setRename(String rename) {
		this.rename = rename;
	}
	public String getDown_gubun() {
		return down_gubun;
	}
	public void setDown_gubun(String downGubun) {
		down_gubun = downGubun;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public long getFl_sz() {
		return fl_sz;
	}
	public void setFl_sz(long flSz) {
		fl_sz = flSz;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String mediaType) {
		media_type = mediaType;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getHdmode() {
		return hdmode;
	}
	public void setHdmode(String hdmode) {
		this.hdmode = hdmode;
	}
	public String getPds_program_id() {
		return pds_program_id;
	}
	public void setPds_program_id(String pdsProgramId) {
		pds_program_id = pdsProgramId;
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
	public String getBit_rt() {
		return bit_rt;
	}
	public void setBit_rt(String bitRt) {
		bit_rt = bitRt;
	}
	public String getAud_samp_frq() {
		return aud_samp_frq;
	}
	public void setAud_samp_frq(String audSampFrq) {
		aud_samp_frq = audSampFrq;
	}
	public String getAud_bdwt() {
		return aud_bdwt;
	}
	public void setAud_bdwt(String audBdwt) {
		aud_bdwt = audBdwt;
	}
	public long getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(long episNo) {
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
	public String getDeliberation_cd() {
		return deliberation_cd;
	}
	public void setDeliberation_cd(String deliberationCd) {
		deliberation_cd = deliberationCd;
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
	public String getCt_cla() {
		return ct_cla;
	}
	public void setCt_cla(String ctCla) {
		ct_cla = ctCla;
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
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFl_path() {
		return fl_path;
	}
	public void setFl_path(String flPath) {
		fl_path = flPath;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String programName) {
		program_name = programName;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String nodeId) {
		node_id = nodeId;
	}
	public String getDown_path() {
		return down_path;
	}
	public void setDown_path(String downPath) {
		down_path = downPath;
	}
	public String getDown_nm() {
		return down_nm;
	}
	public void setDown_nm(String downNm) {
		down_nm = downNm;
	}

	public int getCart_no() {
		return cart_no;
	}
	public void setCart_no(int cartNo) {
		cart_no = cartNo;
	}
	public int getCart_seq() {
		return cart_seq;
	}
	public void setCart_seq(int cartSeq) {
		cart_seq = cartSeq;
	}
	public String getPart_number() {
		return part_number;
	}
	public void setPart_number(String partNumber) {
		part_number = partNumber;
	}
	public String getGenerator_name() {
		return generator_name;
	}
	public void setGenerator_name(String generatorName) {
		generator_name = generatorName;
	}
	public String getGenerator_ver() {
		return generator_ver;
	}
	public void setGenerator_ver(String generatorVer) {
		generator_ver = generatorVer;
	}
	public String getProgram_id() {
		return program_id;
	}
	public void setProgram_id(String programId) {
		program_id = programId;
	}
	public String getProgram_title() {
		return program_title;
	}
	public void setProgram_title(String programTitle) {
		program_title = programTitle;
	}
	public String getProgram_subtitle() {
		return program_subtitle;
	}
	public void setProgram_subtitle(String programSubtitle) {
		program_subtitle = programSubtitle;
	}
	public String getClip_title() {
		return clip_title;
	}
	public void setClip_title(String clipTitle) {
		clip_title = clipTitle;
	}
	public String getProgram_sequence_number() {
		return program_sequence_number;
	}
	public void setProgram_sequence_number(String programSequenceNumber) {
		program_sequence_number = programSequenceNumber;
	}
	public String getBroadcast_event_kind() {
		return broadcast_event_kind;
	}
	public void setBroadcast_event_kind(String broadcastEventKind) {
		broadcast_event_kind = broadcastEventKind;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public String getClip_name() {
		return clip_name;
	}
	public void setClip_name(String clipName) {
		clip_name = clipName;
	}
	
	
	
	
	
	
	
	
}
