package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 마스터 메터 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class MetadataMstInfoDO extends DTO {
	
	private    long         masterId;                        // 마스터아이디
	private    long         pgmId;                           // 프로그램 아이디
	private    String       pgmCd     = Constants.BLANK;     // 프로그램코드
	private    int          episNo;                          // 프로그램 회차
	private    String       epis_no     = Constants.BLANK;     // 프로그램 회차
	private    String       title     = Constants.BLANK;     // 타이틀
	private    String       ctgrLCd     = Constants.BLANK;		// 대분류코드
	private    String       ctgrMCd     = Constants.BLANK;		// 중분류코드
	private    String       ctgrSCd     = Constants.BLANK;		// 소분류코드
	private    String       brdDd     = Constants.BLANK;		// 방송일
	private    String       finalBrdYn     = Constants.BLANK;	// 최종방송여부		
	private    String       snps     = Constants.BLANK;			// 시놉시스
	private    String       keyWords     = Constants.BLANK;		// 키워드
	private    String       brdBgnHms     = Constants.BLANK;	// 방송시작시간		
	private    String       brdEndHms     = Constants.BLANK;	// 방송종료시간
	private    String       brdLeng     = Constants.BLANK;		// 방송시간
	private    String       pgmRate     = Constants.BLANK;		// 시청률
	private    String       drtNm     = Constants.BLANK;		// 연출자명
	private    String       producerNm     = Constants.BLANK;	// 프로듀서명
	private    String       writerNm     = Constants.BLANK;		// 작가명
	private    String       prdtInOutsCd     = Constants.BLANK;	// 제작구분코드
	private    String       prdtDeptCd     = Constants.BLANK;	// 제작부서코드
	private    String       prdtDeptNm     = Constants.BLANK;	// 제작부서명
	private    String       orgPrdrNm     = Constants.BLANK;	// 원제작사명
	private    String       mcNm     = Constants.BLANK;			// 진행자명
	private    String       castNm     = Constants.BLANK;		// 출연자명
	private    String       cmrDrtNm     = Constants.BLANK;		// 촬영감독명
	private    String       fmDt     = Constants.BLANK;			// 촬영일
	private    String       cmrPlace     = Constants.BLANK;		// 촬영장소
	private    String       spcInfo     = Constants.BLANK;		// 특이사항
	private    String       reqCd     = Constants.BLANK;		// 청구번호코드
	private    String       secArchNm     = Constants.BLANK;	// 아카이버명	
	private    String       secArchId     = Constants.BLANK;	// 아카이브아이디
	private    String       gathCoCd     = Constants.BLANK;		// 수집처코드
	private    String       gathClfCd     = Constants.BLANK;	// 수집구분코드
	private    String       archRegDd     = Constants.BLANK;	// 아카이브등록일
	private    String       arrgEndDt     = Constants.BLANK;	// 정리완료일
	private    String       workPrioCd     = Constants.BLANK;	// 작업우선순위코드
	private    String       rsvPrdCd     = Constants.BLANK;		// 보존기간코드
	private    String       cprtrNm     = Constants.BLANK;		// 저작권자명
	private    String       cprtType     = Constants.BLANK;		// 저작권자 타입
	private    String       cprtTypeDsc     = Constants.BLANK;	// 저작권형태설명
	private    String       viewGrCd     = Constants.BLANK;		// 시청등급코드
	private    String       dlbrCd     = Constants.BLANK;		// 심의결과코드
	private    String       awardHstr     = Constants.BLANK;	// 수상내역
	private    int       rpimgKfrmSeq;							// 대표화면키프레임일련번호
	private    String       tapeId     = Constants.BLANK;		// 테이프 식별자ID
	private    String       tapeItemId     = Constants.BLANK;	// 테이프아이템식별자ID
	private    String       tapeMediaClfCd     = Constants.BLANK;// 테이프 매체종류코드
	private    String       rsvPrdEndDd     = Constants.BLANK;	// 보존기간만료일
	private    String       delDd     = Constants.BLANK;		// 삭제일자
	private    String       useYn     = Constants.BLANK;		// 사용여부
	private    String       regrid     = Constants.BLANK;		// 등록자ID
	private    String       regDt     = Constants.BLANK;		// 등록일자
	private    String       modrid     = Constants.BLANK;		// 수정자ID
	private    String       modDt     = Constants.BLANK;		// 수정일자
	private    String       gathDeptCd     = Constants.BLANK;	// 수집부서코드
	private    String       mcuid     = Constants.BLANK;		// 주조ID
	private    long       rpimgCtId;							// 대표화면 콘텐츠 ID: 콘텐츠 하나에 부여하는 ID
	private    String       dataStatCd     = Constants.BLANK;   // 자료상태코드
	private    String       ingRegDd     = Constants.BLANK;		// 인제스트 완료일
	private    String       copyKeep     = Constants.BLANK;		// 부본보관
	private    String       cleanKeep     = Constants.BLANK;	// 클린보관
	private    String       musicInfo     = Constants.BLANK;	// 음악정보
	private    String       rstCont     = Constants.BLANK;		// 제한사항
	private    String       rerun     = Constants.BLANK;		// 재방송
	private    String       acceptorId     = Constants.BLANK;	// 검수자 ID
	private    String       subTtl     = Constants.BLANK;		// 부제
	private    String       pgmNm     = Constants.BLANK;		// 프로그램명
	private    String       mediaCd     = Constants.BLANK;		// 미디어코드
	private    String       mediaId     = Constants.BLANK;		// 미디어id
	private    String 		brdBgnDd    = Constants.BLANK;		// 방송시작일
	private    String 		brdEndDd    = Constants.BLANK;		// 방송종료일
	private 	String		arrange_nm	= Constants.BLANK;		// 아카이버명
	private 	String		lock_stat_cd	= Constants.BLANK;	// lock 기능
	private 	String		error_stat_cd	= Constants.BLANK;	// 에러코드 상태
	private		String		AcceptorNm		= Constants.BLANK;	// 검수자명
	private 	String		record_type_cd = Constants.BLANK;	// 레코드 타입코드

	private    String 		aud_type_cd    = Constants.BLANK;		// 오디오타입

	private 	String		me_cd	= Constants.BLANK;		// me분리코드
	private 	String		color_cd	= Constants.BLANK;	// 생삭코드
	private 	String		ct_id;	// 컨텐츠 id

	private		String		vd_qlty		= Constants.BLANK;	// 화질코드
	private 	String		asp_rto_cd = Constants.BLANK;	// 종횡비코드
	private long target_master_id;                              // DTL 등록(카피 프로세스) 에서 타겟에 대한 파람
	private 	String		rel_brd_dd	= Constants.BLANK;	// 방송일
	private 	String		rel_req_dd	= Constants.BLANK;	// 등록일 
	private		String		rel_brd_leng		= Constants.BLANK;	// 방송길이
	private 	String		rel_sub_ttl = Constants.BLANK;	// 부제
	private 	String		rel_asp_rto_cd = Constants.BLANK;	// 종횡비코드
	private 	int		rel_ct_id;	// 컨텐츠 id
	private    long         rel_masterId;
	
	
	
	//추가수정분(10.11.18)
		private 	String		use_yn = Constants.BLANK;	//
	
		private 	String		pgm_nm = Constants.BLANK;	//
		private 	String		pgm_cd = Constants.BLANK;	//
		private 	String		brd_bgn_dd = Constants.BLANK;	//
		private 	String		brd_end_dd = Constants.BLANK;	//
		private 	String		media_cd = Constants.BLANK;	//
		private 	String		pilot_yn = Constants.BLANK;	//

		private 	String		chan_cd = Constants.BLANK;	//
		private 	String		spc_info = Constants.BLANK;	//
	
		
		
		

		private 	String		bit_rate = Constants.BLANK;	//비트전송률
		private 	String		audio_bdwt = Constants.BLANK;	// 오디오 대역폭
		private 	String		aud_samp_frq = Constants.BLANK;	// 오디오 샘플링
		private 	String		frm_per_sec = Constants.BLANK;	// 초당프레임
		private 	String		tot_fram = Constants.BLANK;	// 총키프레임
	
		private 	String		hresol = Constants.BLANK;	// 해상도
		
		private 	String		ct_cla = Constants.BLANK;	// 컨텐츠 유형
		private 	String		ct_typ = Constants.BLANK;	// 컨텐츠 구분
		private 	String		master_ids = Constants.BLANK;
		private 	String		pds_cms_id = Constants.BLANK;

//2012.4.30
		private 	String		country_cd = Constants.BLANK;// 국가구분코드
		private 	String		artist = Constants.BLANK;// 아티스트
		private 	String		error_id = Constants.BLANK;// 에러등록자
	
		private 	String		sceanario_title = Constants.BLANK;// 대본등록자
		private 	long	    duration=0;
		private 	String		vd_hresol = Constants.BLANK;// 가로길이
		private 	String		vd_vresol = Constants.BLANK;// 세로길이
		private 	String		preview_subj = Constants.BLANK;// 프리뷰제목
		private 	String		archive_reqid = Constants.BLANK;// 아카이브요청자
		private 	String		pds_cms_pgm_id = Constants.BLANK;// pds 프로그램id
		private 	String		storage = Constants.BLANK;// 스토리지
		private 	String		cocd = Constants.BLANK;// 회사코드
		private 	String		annot_clf_cd = Constants.BLANK;// 사용제한 코드
		private 	String		annot_clf_desc = Constants.BLANK;// 사용제한 설명
		private 	long		ingest_eq_id = 0;//인제스트 장비  
		private 	String		chennel_cd = Constants.BLANK;// 체널코드
		
		
		
		
		
	public String getChennel_cd() {
			return chennel_cd;
		}

		public void setChennel_cd(String chennelCd) {
			chennel_cd = chennelCd;
		}

	public long getIngest_eq_id() {
			return ingest_eq_id;
		}

		public void setIngest_eq_id(long ingestEqId) {
			ingest_eq_id = ingestEqId;
		}

	public String getVd_vresol() {
			return vd_vresol;
		}

		public void setVd_vresol(String vdVresol) {
			vd_vresol = vdVresol;
		}

	public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}

		public String getSceanario_title() {
			return sceanario_title;
		}

		public void setSceanario_title(String sceanarioTitle) {
			sceanario_title = sceanarioTitle;
		}

		public long getDuration() {
			return duration;
		}

		public void setDuration(long duration) {
			this.duration = duration;
		}

		public String getVd_hresol() {
			return vd_hresol;
		}

		public void setVd_hresol(String vdHresol) {
			vd_hresol = vdHresol;
		}

		public String getPreview_subj() {
			return preview_subj;
		}

		public void setPreview_subj(String previewSubj) {
			preview_subj = previewSubj;
		}

		public String getArchive_reqid() {
			return archive_reqid;
		}

		public void setArchive_reqid(String archiveReqid) {
			archive_reqid = archiveReqid;
		}

		public String getPds_cms_pgm_id() {
			return pds_cms_pgm_id;
		}

		public void setPds_cms_pgm_id(String pdsCmsPgmId) {
			pds_cms_pgm_id = pdsCmsPgmId;
		}

		public String getStorage() {
			return storage;
		}

		public void setStorage(String storage) {
			this.storage = storage;
		}

		public String getCocd() {
			return cocd;
		}

		public void setCocd(String cocd) {
			this.cocd = cocd;
		}

		public String getAnnot_clf_cd() {
			return annot_clf_cd;
		}

		public void setAnnot_clf_cd(String annotClfCd) {
			annot_clf_cd = annotClfCd;
		}

		public String getAnnot_clf_desc() {
			return annot_clf_desc;
		}

		public void setAnnot_clf_desc(String annotClfDesc) {
			annot_clf_desc = annotClfDesc;
		}

	public String getError_id() {
			return error_id;
		}

		public void setError_id(String errorId) {
			error_id = errorId;
		}

	public String getCountry_cd() {
			return country_cd;
		}

		public void setCountry_cd(String countryCd) {
			country_cd = countryCd;
		}

		public String getArtist() {
			return artist;
		}

		public void setArtist(String artist) {
			this.artist = artist;
		}

	public String getEpis_no() {
			return epis_no;
		}

		public void setEpis_no(String episNo) {
			epis_no = episNo;
		}

	public String getPds_cms_id() {
			return pds_cms_id;
		}

		public void setPds_cms_id(String pdsCmsId) {
			pds_cms_id = pdsCmsId;
		}

	public String getMaster_ids() {
			return master_ids;
		}

		public void setMaster_ids(String masterIds) {
			master_ids = masterIds;
		}

	public String getCt_cla() {
			return ct_cla;
		}

		public void setCt_cla(String ctCla) {
			ct_cla = ctCla;
		}

		public String getCt_typ() {
			return ct_typ;
		}

		public void setCt_typ(String ctTyp) {
			ct_typ = ctTyp;
		}

	public String getHresol() {
			return hresol;
		}

		public void setHresol(String hresol) {
			this.hresol = hresol;
		}

	public String getBit_rate() {
			return bit_rate;
		}

		public void setBit_rate(String bitRate) {
			bit_rate = bitRate;
		}

		public String getAudio_bdwt() {
			return audio_bdwt;
		}

		public void setAudio_bdwt(String audioBdwt) {
			audio_bdwt = audioBdwt;
		}

		public String getAud_samp_frq() {
			return aud_samp_frq;
		}

		public void setAud_samp_frq(String audSampFrq) {
			aud_samp_frq = audSampFrq;
		}

		public String getFrm_per_sec() {
			return frm_per_sec;
		}

		public void setFrm_per_sec(String frmPerSec) {
			frm_per_sec = frmPerSec;
		}

		public String getTot_fram() {
			return tot_fram;
		}

		public void setTot_fram(String totFram) {
			tot_fram = totFram;
		}

	public String getSpc_info() {
			return spc_info;
		}

		public void setSpc_info(String spcInfo) {
			spc_info = spcInfo;
		}

	public String getChan_cd() {
			return chan_cd;
		}

		public void setChan_cd(String chanCd) {
			chan_cd = chanCd;
		}

	public String getPgm_nm() {
			return pgm_nm;
		}

		public void setPgm_nm(String pgmNm) {
			pgm_nm = pgmNm;
		}

		public String getPgm_cd() {
			return pgm_cd;
		}

		public void setPgm_cd(String pgmCd) {
			pgm_cd = pgmCd;
		}

		public String getBrd_bgn_dd() {
			return brd_bgn_dd;
		}

		public void setBrd_bgn_dd(String brdBgnDd) {
			brd_bgn_dd = brdBgnDd;
		}

		public String getBrd_end_dd() {
			return brd_end_dd;
		}

		public void setBrd_end_dd(String brdEndDd) {
			brd_end_dd = brdEndDd;
		}

		public String getMedia_cd() {
			return media_cd;
		}

		public void setMedia_cd(String mediaCd) {
			media_cd = mediaCd;
		}

		public String getPilot_yn() {
			return pilot_yn;
		}

		public void setPilot_yn(String pilotYn) {
			pilot_yn = pilotYn;
		}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String useYn) {
		use_yn = useYn;
	}

	

	public int getRel_ct_id() {
		return rel_ct_id;
	}

	public void setRel_ct_id(int relCtId) {
		rel_ct_id = relCtId;
	}

	public long getRel_masterId() {
		return rel_masterId;
	}

	public void setRel_masterId(long relMasterId) {
		rel_masterId = relMasterId;
	}

	public String getRel_brd_dd() {
		return rel_brd_dd;
	}

	public void setRel_brd_dd(String relBrdDd) {
		rel_brd_dd = relBrdDd;
	}

	public String getRel_req_dd() {
		return rel_req_dd;
	}

	public void setRel_req_dd(String relReqDd) {
		rel_req_dd = relReqDd;
	}

	public String getRel_brd_leng() {
		return rel_brd_leng;
	}

	public void setRel_brd_leng(String relBrdLeng) {
		rel_brd_leng = relBrdLeng;
	}

	public String getRel_sub_ttl() {
		return rel_sub_ttl;
	}

	public void setRel_sub_ttl(String relSubTtl) {
		rel_sub_ttl = relSubTtl;
	}

	public String getRel_asp_rto_cd() {
		return rel_asp_rto_cd;
	}

	public void setRel_asp_rto_cd(String relAspRtoCd) {
		rel_asp_rto_cd = relAspRtoCd;
	}


	public String getCt_id() {
		return ct_id;
	}

	public void setCt_id(String ctId) {
		ct_id = ctId;
	}

	public String getAud_type_cd() {
		return aud_type_cd;
	}

	public void setAud_type_cd(String audTypeCd) {
		aud_type_cd = audTypeCd;
	}

	public String getMe_cd() {
		return me_cd;
	}

	public void setMe_cd(String meCd) {
		me_cd = meCd;
	}

	public String getColor_cd() {
		return color_cd;
	}

	public void setColor_cd(String colorCd) {
		color_cd = colorCd;
	}

	

	public String getVd_qlty() {
		return vd_qlty;
	}

	public void setVd_qlty(String vdQlty) {
		vd_qlty = vdQlty;
	}

	public String getAsp_rto_cd() {
		return asp_rto_cd;
	}

	public void setAsp_rto_cd(String aspRtoCd) {
		asp_rto_cd = aspRtoCd;
	}

	public String getMediaCd() {
		return mediaCd;
	}

	public long getTarget_master_id() {
		return target_master_id;
	}

	public void setTarget_master_id(long target_master_id) {
		this.target_master_id = target_master_id;
	}

	public String getRecord_type_cd() {
		return record_type_cd;
	}

	public void setRecord_type_cd(String record_type_cd) {
		this.record_type_cd = record_type_cd;
	}

	public String getBrdBgnDd() {
		return brdBgnDd;
	}

	public void setBrdBgnDd(String brdBgnDd) {
		this.brdBgnDd = brdBgnDd;		
	}
	
	public String getBrdEndDd() {
		return brdEndDd;
	}

	public void setBrdEndDd(String brdEndDd) {
		this.brdEndDd = brdEndDd;		
	}
	public String getPgmNm()
	{
	   return  pgmNm;
	}
	public void setPgmNm(String pgmNm)
	{
	   this.pgmNm = pgmNm;
	}
	public String getmediaCd()
	{
	   return  mediaCd;
	}
	public void setMediaCd(String mediaCd)
	{
	   this.mediaCd = mediaCd;
	}
	public long getMasterId()
	{
	   return  masterId;
	}
	public void setMasterId(long masterId)
	{
	   this.masterId = masterId;
	}
	public long getPgmId()
	{
	   return  pgmId;
	}
	public void setPgmId(long pgmId)
	{
	   this.pgmId = pgmId;
	}
	public String getPgmCd()
	{
	   return  pgmCd;
	}
	public void setPgmCd(String pgmCd)
	{
	   this.pgmCd = pgmCd;
	}
	public int getEpisNo()
	{
	   return  episNo;
	}
	public void setEpisNo(int episNo)
	{
	   this.episNo = episNo;
	}
	public String getTitle()
	{
	   return  title;
	}
	public void setTitle(String title)
	{
	   this.title = title;
	}
	public String getCtgrLCd()
	{
	   return  ctgrLCd;
	}
	public void setCtgrLCd(String ctgrLCd)
	{
	   this.ctgrLCd = ctgrLCd;
	}
	public String getCtgrMCd()
	{
	   return  ctgrMCd;
	}
	public void setCtgrMCd(String ctgrMCd)
	{
	   this.ctgrMCd = ctgrMCd;
	}
	public String getCtgrSCd()
	{
	   return  ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd)
	{
	   this.ctgrSCd = ctgrSCd;
	}
	public String getBrdDd()
	{
	   return  brdDd;
	}
	public void setBrdDd(String brdDd)
	{
	   this.brdDd = brdDd;
	}
	public String getFinalBrdYn()
	{
	   return  finalBrdYn;
	}
	public void setFinalBrdYn(String finalBrdYn)
	{
	   this.finalBrdYn = finalBrdYn;
	}
	public String getSnps()
	{
	   return  snps;
	}
	public void setSnps(String snps)
	{
	   this.snps = snps;
	}
	public String getKeyWords()
	{
	   return  keyWords;
	}
	public void setKeyWords(String keyWords)
	{
	   this.keyWords = keyWords;
	}
	public String getBrdBgnHms()
	{
	   return  brdBgnHms;
	}
	public void setBrdBgnHms(String brdBgnHms)
	{
	   this.brdBgnHms = brdBgnHms;
	}
	public String getBrdEndHms()
	{
	   return  brdEndHms;
	}
	public void setBrdEndHms(String brdEndHms)
	{
	   this.brdEndHms = brdEndHms;
	}
	public String getBrdLeng()
	{
	   return  brdLeng;
	}
	public void setBrdLeng(String brdLeng)
	{
	   this.brdLeng = brdLeng;
	}
	public String getPgmRate()
	{
	   return  pgmRate;
	}
	public void setPgmRate(String pgmRate)
	{
	   this.pgmRate = pgmRate;
	}
	public String getDrtNm()
	{
	   return  drtNm;
	}
	public void setDrtNm(String drtNm)
	{
	   this.drtNm = drtNm;
	}
	public String getProducerNm()
	{
	   return  producerNm;
	}
	public void setProducerNm(String producerNm)
	{
	   this.producerNm = producerNm;
	}
	public String getWriterNm()
	{
	   return  writerNm;
	}
	public void setWriterNm(String writerNm)
	{
	   this.writerNm = writerNm;
	}
	public String getPrdtInOutsCd()
	{
	   return  prdtInOutsCd;
	}
	public void setPrdtInOutsCd(String prdtInOutsCd)
	{
	   this.prdtInOutsCd = prdtInOutsCd;
	}
	public String getPrdtDeptCd()
	{
	   return  prdtDeptCd;
	}
	public void setPrdtDeptCd(String prdtDeptCd)
	{
	   this.prdtDeptCd = prdtDeptCd;
	}
	public String getPrdtDeptNm()
	{
	   return  prdtDeptNm;
	}
	public void setPrdtDeptNm(String prdtDeptNm)
	{
	   this.prdtDeptNm = prdtDeptNm;
	}
	public String getOrgPrdrNm()
	{
	   return  orgPrdrNm;
	}
	public void setOrgPrdrNm(String orgPrdrNm)
	{
	   this.orgPrdrNm = orgPrdrNm;
	}
	public String getMcNm()
	{
	   return  mcNm;
	}
	public void setMcNm(String mcNm)
	{
	   this.mcNm = mcNm;
	}
	public String getCastNm()
	{
	   return  castNm;
	}
	public void setCastNm(String castNm)
	{
	   this.castNm = castNm;
	}
	public String getCmrDrtNm()
	{
	   return  cmrDrtNm;
	}
	public void setCmrDrtNm(String cmrDrtNm)
	{
	   this.cmrDrtNm = cmrDrtNm;
	}
	public String getFmDt()
	{
	   return  fmDt;
	}
	public void setFmDt(String fmDt)
	{
	   this.fmDt = fmDt;
	}
	public String getCmrPlace()
	{
	   return  cmrPlace;
	}
	public void setCmrPlace(String cmrPlace)
	{
	   this.cmrPlace = cmrPlace;
	}
	public String getSpcInfo()
	{
	   return  spcInfo;
	}
	public void setSpcInfo(String spcInfo)
	{
	   this.spcInfo = spcInfo;
	}
	public String getReqCd()
	{
	   return  reqCd;
	}
	public void setReqCd(String reqCd)
	{
	   this.reqCd = reqCd;
	}
	public String getSecArchNm()
	{
	   return  secArchNm;
	}
	public void setSecArchNm(String secArchNm)
	{
	   this.secArchNm = secArchNm;
	}
	public String getSecArchId()
	{
	   return  secArchId;
	}
	public void setSecArchId(String secArchId)
	{
	   this.secArchId = secArchId;
	}
	public String getGathCoCd()
	{
	   return  gathCoCd;
	}
	public void setGathCoCd(String gathCoCd)
	{
	   this.gathCoCd = gathCoCd;
	}
	public String getGathClfCd()
	{
	   return  gathClfCd;
	}
	public void setGathClfCd(String gathClfCd)
	{
	   this.gathClfCd = gathClfCd;
	}
	public String getArchRegDd()
	{
	   return  archRegDd;
	}
	public void setArchRegDd(String archRegDd)
	{
	   this.archRegDd = archRegDd;
	}
	public String getArrgEndDt()
	{
	   return  arrgEndDt;
	}
	public void setArrgEndDt(String arrgEndDt)
	{
	   this.arrgEndDt = arrgEndDt;
	}
	public String getWorkPrioCd()
	{
	   return  workPrioCd;
	}
	public void setWorkPrioCd(String workPrioCd)
	{
	   this.workPrioCd = workPrioCd;
	}
	public String getRsvPrdCd()
	{
	   return  rsvPrdCd;
	}
	public void setRsvPrdCd(String rsvPrdCd)
	{
	   this.rsvPrdCd = rsvPrdCd;
	}
	public String getCprtrNm()
	{
	   return  cprtrNm;
	}
	public void setCprtrNm(String cprtrNm)
	{
	   this.cprtrNm = cprtrNm;
	}
	public String getCprtType()
	{
	   return  cprtType;
	}
	public void setCprtType(String cprtType)
	{
	   this.cprtType = cprtType;
	}
	public String getCprtTypeDsc()
	{
	   return  cprtTypeDsc;
	}
	public void setCprtTypeDsc(String cprtTypeDsc)
	{
	   this.cprtTypeDsc = cprtTypeDsc;
	}
	public String getViewGrCd()
	{
	   return  viewGrCd;
	}
	public void setViewGrCd(String viewGrCd)
	{
	   this.viewGrCd = viewGrCd;
	}
	public String getDlbrCd()
	{
	   return  dlbrCd;
	}
	public void setDlbrCd(String dlbrCd)
	{
	   this.dlbrCd = dlbrCd;
	}
	public String getAwardHstr()
	{
	   return  awardHstr;
	}
	public void setAwardHstr(String awardHstr)
	{
	   this.awardHstr = awardHstr;
	}
	public int getRpimgKfrmSeq()
	{
	   return  rpimgKfrmSeq;
	}
	public void setRpimgKfrmSeq(int rpimgKfrmSeq)
	{
	   this.rpimgKfrmSeq = rpimgKfrmSeq;
	}
	public String getTapeId()
	{
	   return  tapeId;
	}
	public void setTapeId(String tapeId)
	{
	   this.tapeId = tapeId;
	}
	public String getTapeItemId()
	{
	   return  tapeItemId;
	}
	public void setTapeItemId(String tapeItemId)
	{
	   this.tapeItemId = tapeItemId;
	}
	public String getTapeMediaClfCd()
	{
	   return  tapeMediaClfCd;
	}
	public void setTapeMediaClfCd(String tapeMediaClfCd)
	{
	   this.tapeMediaClfCd = tapeMediaClfCd;
	}
	public String getRsvPrdEndDd()
	{
	   return  rsvPrdEndDd;
	}
	public void setRsvPrdEndDd(String rsvPrdEndDd)
	{
	   this.rsvPrdEndDd = rsvPrdEndDd;
	}
	public String getDelDd()
	{
	   return  delDd;
	}
	public void setDelDd(String delDd)
	{
	   this.delDd = delDd;
	}
	public String getUseYn()
	{
	   return  useYn;
	}
	public void setUseYn(String useYn)
	{
	   this.useYn = useYn;
	}
	public String getRegrid()
	{
	   return  regrid;
	}
	public void setRegrid(String regrid)
	{
	   this.regrid = regrid;
	}
	public String getRegDt()
	{
	   return  regDt;
	}
	public void setRegDt(String regDt)
	{
	   this.regDt = regDt;
	}
	public String getModrid()
	{
	   return  modrid;
	}
	public void setModrid(String modrid)
	{
	   this.modrid = modrid;
	}
	public String getModDt()
	{
	   return  modDt;
	}
	public void setModDt(String modDt)
	{
	   this.modDt = modDt;
	}
	public String getGathDeptCd()
	{
	   return  gathDeptCd;
	}
	public void setGathDeptCd(String gathDeptCd)
	{
	   this.gathDeptCd = gathDeptCd;
	}
	public String getMcuid()
	{
	   return  mcuid;
	}
	public void setMcuid(String mcuid)
	{
	   this.mcuid = mcuid;
	}
	public long getRpimgCtId()
	{
	   return  rpimgCtId;
	}
	public void setRpimgCtId(long rpimgCtId)
	{
	   this.rpimgCtId = rpimgCtId;
	}
	public String getDataStatCd()
	{
	   return  dataStatCd;
	}
	public void setDataStatCd(String dataStatCd)
	{
	   this.dataStatCd = dataStatCd;
	}
	public String getIngRegDd()
	{
	   return  ingRegDd;
	}
	public void setIngRegDd(String ingRegDd)
	{
	   this.ingRegDd = ingRegDd;
	}
	public String getCopyKeep()
	{
	   return  copyKeep;
	}
	public void setCopyKeep(String copyKeep)
	{
	   this.copyKeep = copyKeep;
	}
	public String getCleanKeep()
	{
	   return  cleanKeep;
	}
	public void setCleanKeep(String cleanKeep)
	{
	   this.cleanKeep = cleanKeep;
	}
	public String getMusicInfo()
	{
	   return  musicInfo;
	}
	public void setMusicInfo(String musicInfo)
	{
	   this.musicInfo = musicInfo;
	}
	public String getRstCont()
	{
	   return  rstCont;
	}
	public void setRstCont(String rstCont)
	{
	   this.rstCont = rstCont;
	}
	public String getRerun()
	{
	   return  rerun;
	}
	public void setRerun(String rerun)
	{
	   this.rerun = rerun;
	}
	public String getAcceptorId()
	{
	   return  acceptorId;
	}
	public void setAcceptorId(String acceptorId)
	{
	   this.acceptorId = acceptorId;
	}
	public String getSubTtl()
	{
	   return  subTtl;
	}
	public void setSubTtl(String subTtl)
	{
	   this.subTtl = subTtl;
	}
	
	// 편성명
	public String getArrange_nm()
	{
		return arrange_nm;		
	}
	public void setArrange_nm(String arrange_nm)
	{
		this.arrange_nm = arrange_nm;
	}
	
	// 락정보
	public String getLock_stat_cd()
	{
		return lock_stat_cd.trim();
	}
	public void setLock_stat_cd(String lock_stat_cd)
	{
		this.lock_stat_cd = lock_stat_cd.trim(); 
	}
	
	// 에러정보
	public String getError_stat_cd()
	{
		return error_stat_cd.trim();
	}
	public void setError_stat_cd(String error_stat_cd)
	{
		this.error_stat_cd = error_stat_cd.trim(); 
	}
//	 검수자 이름
	public String getAcceptorNm()
	{
		return AcceptorNm.trim();			
	}
	
	public void setAcceptorNm(String AcceptorNm)
	{
		this.AcceptorNm = AcceptorNm;
	}
}
