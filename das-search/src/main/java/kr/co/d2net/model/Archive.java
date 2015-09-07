package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 아카이브 요청
 * @author Administrator
 *
 */
@XmlRootElement(name="pdsarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class Archive {
	
	@XmlElement(name="brd_dd")
	private String brdDd;				// 방송일
	@XmlElement(name="fm_dt")
	private String fmDt;				// 촬영일
	@XmlElement(name="title")
	private String title;				// 제목
	@XmlElement(name="sub_ttl")
	private String subTtl;				// 부제
	@XmlElement(name="epis_no")
	private Integer episNo;				// 회차
	@XmlElement(name="ctgr_l_cd")
	private String ctgrLCd;				// 대분류
	@XmlElement(name="ctgr_m_cd")
	private String ctgrMCd;				// 중분류
	@XmlElement(name="ctgr_s_cd")
	private String ctgrSCd;				// 소분류
	@XmlElement(name="view_gr_cd")
	private String viewGrCd;       		// 시청등급
	@XmlElement(name="pgm_rage")
	private String pgmRate;				// 시청률
	@XmlElement(name="cprt_cd")
	private String cprtCd;				// 저작권형태코드
	@XmlElement(name="cprt_nm")
	private String cprtNm;				// 저작권자명
	@XmlElement(name="cprt_type_dsc")
	private String cprtTypeDsc;			// 저작권형태설명
	@XmlElement(name="rist_clf_cd")
	private String ristClfCd;			// 사용등급
	@XmlElement(name="annot_clf_cont")
	private String annotClfCont;		// 사용등급설명
	@XmlElement(name="drt_nm")
	private String drtNm;				// 연출
	@XmlElement(name="writer_nm")
	private String writerNm;			// 작가
	@XmlElement(name="producer_nm")
	private String producerNm;			// 프로듀서명
	@XmlElement(name="cmr_drt_nm")
	private String cmrDrtNm;			// 촬영감독
	@XmlElement(name="org_prd_nm")
	private String orgPrdrNm;			// 외주제작사
	@XmlElement(name="prdt_in_out_cd")
	private String prdtInOutsCd;		// 제작구분
	@XmlElement(name="cmr_place")
	private String cmrPlace;			// 촬영장소
	@XmlElement(name="key_words")
	private String keyWords;			// 키워드
	@XmlElement(name="spc_info")
	private String spcInfo;				// 특이사항
	@XmlElement(name="ct_nm")
	private String ctNm;				// 콘텐츠명
	@XmlElement(name="brd_bgn_hms")
	private String brdBgnHms;			// 방송시작시간
	@XmlElement(name="brd_end_hms")
	private String brdEndHms;			// 방송종료시간
	@XmlElement(name="artist_nm")
	private String artistNm;			// 아티스트
	@XmlElement(name="nation_cd")
	private String nationCd;			// 국가구분
	@XmlElement(name="rsv_prd_cd")
	private String rsvPrdCd;			// 보존기간
	@XmlElement(name="cont")
	private String cont;				// 코너내용(화면설명)
	@XmlElement(name="media_id")
	private String mediaId;				// 미디어ID
	@XmlElement(name="pds_cms_pgm_id")
	private String pdsCmsPgmId;			// 프로그램ID
	@XmlElement(name="asp_rto_cd")
	private String aspRtoCd;			// 화면비코드
	@XmlElement(name="vd_qlty")
	private String vdQlty;				// 화질코드
	@XmlElement(name="arch_route")
	private String archRoute;			// 아카이브경로
	@XmlElement(name="comp_cd")
	private String compCd;				// 회사코드
	@XmlElement(name="chan_cd")
	private String chanCd;				// 채널코드
	@XmlElement(name="record_type_cd")
	private String recordTypeCd;		// 녹음방식코드
	@XmlElement(name="brd_leng")
	private String brdLeng;				// 방송길이
	@XmlElement(name="clip_nm")
	private String clipNm;				// 클립명(아카이브 파일명)
	@XmlElement(name="ct_cla")
	private String ctCla;				// 컨텐츠구분
	@XmlElement(name="ct_leng")
	private String ctLeng;				// 컨텐츠길이
	@XmlElement(name="ct_type")
	private String ctType;				// 컨텐츠유형
	@XmlElement(name="das_ep_id")
	private String dasEqId;				// 장비ID
	@XmlElement(name="das_eq_ps_cd")
	private String dasEqPsCd;			// 장비프로세스
	@XmlElement(name="drp_frm_yn")
	private String drpFrmYn;			// 드롭프레임여부
	@XmlElement(name="duration")
	private Long duration;				// 총키프레임 수
	@XmlElement(name="fl_sz")
	private Long flSz;					// 파일크기
	@XmlElement(name="org_file_nm")
	private String orgFileNm;			// 원파일명
	@XmlElement(name="req_dt")
	private String reqDt;				// 등록일
	@XmlElement(name="req_id")
	private String reqId;				// 요청자ID
	@XmlElement(name="som")
	private String som;					// 시작점
	@XmlElement(name="eom")
	private String eom;					// 끝점
	@XmlElement(name="storage_path")
	private String storagePath;			// 스토리지 경로[FL_PATH]
	@XmlElement(name="aud_bdwt")
	private String audBdwt;				// 오디오대역폭
	@XmlElement(name="ingestInfo")
	private String audSampFrq;			// 오디오샘플링
	@XmlElement(name="audio_yn")
	private String audioYn;				// 오디오여부
	@XmlElement(name="bit_rt")
	private String bitRt;				// 비트전송률
	@XmlElement(name="frm_per_sec")
	private String frmPerSec;			// 초당프레임
	@XmlElement(name="vd_hresol")
	private String vdHresol;			// 비디오 수평해상도
	@XmlElement(name="vd_vresol")
	private String vdVresol;			// 비디오 수직해상도
	@XmlElement(name="ifcms_url")
	private String ifcmsUrl;			// Callback URL
	@XmlElement(name="co_cd")
	private String coCd;				// 회사코드
	@XmlElement(name="chennel_cd")
	private String chennelCd;			// 채널코드
	
	public String getCoCd() {
		return coCd;
	}
	public void setCoCd(String coCd) {
		this.coCd = coCd;
	}
	public String getChennelCd() {
		return chennelCd;
	}
	public void setChennelCd(String chennelCd) {
		this.chennelCd = chennelCd;
	}
	public String getIfcmsUrl() {
		return ifcmsUrl;
	}
	public void setIfcmsUrl(String ifcmsUrl) {
		this.ifcmsUrl = ifcmsUrl;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getFmDt() {
		return fmDt;
	}
	public void setFmDt(String fmDt) {
		this.fmDt = fmDt;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTtl() {
		return subTtl;
	}
	public void setSubTtl(String subTtl) {
		this.subTtl = subTtl;
	}
	public Integer getEpisNo() {
		return episNo;
	}
	public void setEpisNo(Integer episNo) {
		this.episNo = episNo;
	}
	public String getCtgrLCd() {
		return ctgrLCd;
	}
	public void setCtgrLCd(String ctgrLCd) {
		this.ctgrLCd = ctgrLCd;
	}
	public String getCtgrMCd() {
		return ctgrMCd;
	}
	public void setCtgrMCd(String ctgrMCd) {
		this.ctgrMCd = ctgrMCd;
	}
	public String getCtgrSCd() {
		return ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	public String getViewGrCd() {
		return viewGrCd;
	}
	public void setViewGrCd(String viewGrCd) {
		this.viewGrCd = viewGrCd;
	}
	public String getPgmRate() {
		return pgmRate;
	}
	public void setPgmRate(String pgmRate) {
		this.pgmRate = pgmRate;
	}
	public String getCprtCd() {
		return cprtCd;
	}
	public void setCprtCd(String cprtCd) {
		this.cprtCd = cprtCd;
	}
	public String getCprtNm() {
		return cprtNm;
	}
	public void setCprtNm(String cprtNm) {
		this.cprtNm = cprtNm;
	}
	public String getCprtTypeDsc() {
		return cprtTypeDsc;
	}
	public void setCprtTypeDsc(String cprtTypeDsc) {
		this.cprtTypeDsc = cprtTypeDsc;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
	}
	public String getAnnotClfCont() {
		return annotClfCont;
	}
	public void setAnnotClfCont(String annotClfCont) {
		this.annotClfCont = annotClfCont;
	}
	public String getDrtNm() {
		return drtNm;
	}
	public void setDrtNm(String drtNm) {
		this.drtNm = drtNm;
	}
	public String getWriterNm() {
		return writerNm;
	}
	public void setWriterNm(String writerNm) {
		this.writerNm = writerNm;
	}
	public String getProducerNm() {
		return producerNm;
	}
	public void setProducerNm(String producerNm) {
		this.producerNm = producerNm;
	}
	public String getCmrDrtNm() {
		return cmrDrtNm;
	}
	public void setCmrDrtNm(String cmrDrtNm) {
		this.cmrDrtNm = cmrDrtNm;
	}
	public String getOrgPrdrNm() {
		return orgPrdrNm;
	}
	public void setOrgPrdrNm(String orgPrdrNm) {
		this.orgPrdrNm = orgPrdrNm;
	}
	public String getPrdtInOutsCd() {
		return prdtInOutsCd;
	}
	public void setPrdtInOutsCd(String prdtInOutsCd) {
		this.prdtInOutsCd = prdtInOutsCd;
	}
	public String getCmrPlace() {
		return cmrPlace;
	}
	public void setCmrPlace(String cmrPlace) {
		this.cmrPlace = cmrPlace;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getSpcInfo() {
		return spcInfo;
	}
	public void setSpcInfo(String spcInfo) {
		this.spcInfo = spcInfo;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getBrdBgnHms() {
		return brdBgnHms;
	}
	public void setBrdBgnHms(String brdBgnHms) {
		this.brdBgnHms = brdBgnHms;
	}
	public String getBrdEndHms() {
		return brdEndHms;
	}
	public void setBrdEndHms(String brdEndHms) {
		this.brdEndHms = brdEndHms;
	}
	public String getArtistNm() {
		return artistNm;
	}
	public void setArtistNm(String artistNm) {
		this.artistNm = artistNm;
	}
	public String getNationCd() {
		return nationCd;
	}
	public void setNationCd(String nationCd) {
		this.nationCd = nationCd;
	}
	public String getRsvPrdCd() {
		return rsvPrdCd;
	}
	public void setRsvPrdCd(String rsvPrdCd) {
		this.rsvPrdCd = rsvPrdCd;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getPdsCmsPgmId() {
		return pdsCmsPgmId;
	}
	public void setPdsCmsPgmId(String pdsCmsPgmId) {
		this.pdsCmsPgmId = pdsCmsPgmId;
	}
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getArchRoute() {
		return archRoute;
	}
	public void setArchRoute(String archRoute) {
		this.archRoute = archRoute;
	}
	public String getCompCd() {
		return compCd;
	}
	public void setCompCd(String compCd) {
		this.compCd = compCd;
	}
	public String getChanCd() {
		return chanCd;
	}
	public void setChanCd(String chanCd) {
		this.chanCd = chanCd;
	}
	public String getRecordTypeCd() {
		return recordTypeCd;
	}
	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}
	public String getBrdLeng() {
		return brdLeng;
	}
	public void setBrdLeng(String brdLeng) {
		this.brdLeng = brdLeng;
	}
	public String getClipNm() {
		return clipNm;
	}
	public void setClipNm(String clipNm) {
		this.clipNm = clipNm;
	}
	public String getCtCla() {
		return ctCla;
	}
	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}
	public String getCtLeng() {
		return ctLeng;
	}
	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}
	public String getCtType() {
		return ctType;
	}
	public void setCtType(String ctType) {
		this.ctType = ctType;
	}
	public String getDasEqId() {
		return dasEqId;
	}
	public void setDasEqId(String dasEqId) {
		this.dasEqId = dasEqId;
	}
	public String getDasEqPsCd() {
		return dasEqPsCd;
	}
	public void setDasEqPsCd(String dasEqPsCd) {
		this.dasEqPsCd = dasEqPsCd;
	}
	public String getDrpFrmYn() {
		return drpFrmYn;
	}
	public void setDrpFrmYn(String drpFrmYn) {
		this.drpFrmYn = drpFrmYn;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getFlSz() {
		return flSz;
	}
	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}
	public String getReqDt() {
		return reqDt;
	}
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
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
	public String getStoragePath() {
		return storagePath;
	}
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	public String getAudBdwt() {
		return audBdwt;
	}
	public void setAudBdwt(String audBdwt) {
		this.audBdwt = audBdwt;
	}
	public String getAudSampFrq() {
		return audSampFrq;
	}
	public void setAudSampFrq(String audSampFrq) {
		this.audSampFrq = audSampFrq;
	}
	public String getAudioYn() {
		return audioYn;
	}
	public void setAudioYn(String audioYn) {
		this.audioYn = audioYn;
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
	public String getVdHresol() {
		return vdHresol;
	}
	public void setVdHresol(String vdHresol) {
		this.vdHresol = vdHresol;
	}
	public String getVdVresol() {
		return vdVresol;
	}
	public void setVdVresol(String vdVresol) {
		this.vdVresol = vdVresol;
	}
	
}			
