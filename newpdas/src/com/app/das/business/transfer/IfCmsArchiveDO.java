package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * ifcms 아카이브 정보를 포함하고 있는 DataObject 
 * 
 * @author ysk523
 * 
 */
public class IfCmsArchiveDO extends DTO {
	/**
	 * 버전 
	 */
	private String version = Constants.BLANK;
	
	/**
	 * 트렌젝션id
	 */
	private long transaction_id = 0;
	/**
	 * 그룹소속여부(프로그램 여부판단)
	 */
	private String belong_to_group = Constants.BLANK;
	/**
	 * 에셋id
	 */
	private long asset_id = 0;
	/**
	 * 그룹id 식별자
	 */
	private long group_id;
	/**
	 * 스토리지id
	 */
	private String storage_id = Constants.BLANK;
	/**
	 * 폴더id
	 */
	private String folder_id = Constants.BLANK;
	/**
	 * 파일총합
	 */
	private long total_fl_sz = 0;
	/**
	 * 파일명
	 */
	private String fl_nm = Constants.BLANK;
	/**
	 * 파일크기
	 */
	private long fl_sz = 0;
	/**
	 * 파일 타입
	 */
	private String fl_type = Constants.BLANK;
	/**
	 * 방송일시
	 */
	private String brd_dd = Constants.BLANK;
	/**
	 * 촬영일시
	 */
	private String fm_dt = Constants.BLANK;
	/**
	 * 제목
	 */
	private String  title= Constants.BLANK;
	/**
	 * 프로그램명
	 */
	private String  pgm_nm= Constants.BLANK;
	/**
	 * 부제
	 */
	private String sub_ttl= Constants.BLANK;
	/**
	 * 회차명
	 */
	private String epis_no = Constants.BLANK;
	/**
	 * 대분류
	 */
	private String ctgr_l_cd = Constants.BLANK;
	/**
	 * 중분류
	 */
	private String ctgr_m_cd = Constants.BLANK;
	/**
	 * 소분류
	 */
	private String ctgr_s_cd = Constants.BLANK;
	/**
	 * 시청등급
	 */
	private String view_gr_cd = Constants.BLANK;
	/**
	 * 시청률
	 */
	private String view_rate = Constants.BLANK;
	/**
	 * 저작권형태
	 */
	private String cprt_type= Constants.BLANK;
	/**
	 * 사용등급
	 */
	private String limited_use = Constants.BLANK;
	/**
	 * 사용등급 설명
	 */
	private String limited_use_cont= Constants.BLANK;
	/**
	 * 사용범위
	 */
	private String user_range= Constants.BLANK;
	/**
	 * 연출
	 */
	private String creator_sub= Constants.BLANK;
	/**
	 * 작가
	 */
	private String writer_name= Constants.BLANK;
	/**
	 * 프로듀서명
	 */
	private String creator= Constants.BLANK;
	/**
	 * 촬영감독
	 */
	private String director_shooting= Constants.BLANK;
	/**
	 * 외부제작사
	 */
	private String publisher_external= Constants.BLANK;
	/**
	 * 제작구분
	 */
	private String production_type = Constants.BLANK;
	/**
	 * 촬영장소
	 */
	private String location_shooting = Constants.BLANK;
	/**
	 * 키워드
	 */
	private String keyword = Constants.BLANK;
	/**
	 * 특이사항
	 */
	private String special_info= Constants.BLANK;
	/**
	 * 콘텐츠명
	 */
	private String contents_name= Constants.BLANK;
	/**
	 * 아티스트
	 */
	private String artist= Constants.BLANK;
	/**
	 * 국가구분, 001:한국어, 002:영어권, 003:유럽권, 004:제3세계권, 005:일어권, 006:중국어권, 007:기타
	 */
	private String country= Constants.BLANK;
	/**
	 * 보존 기간, d1:1일, d2:2일, d3:3일, d4:4일, d5:5일, w1:1주, w2:2주, w3:3주, w4:4주, m1:한달, m6: 6개월
    			y1:1년, y2:2년, y3:3년, 5:5년, y10:10년, y20:20년, p:permanent
	 */
	private String retention_period= Constants.BLANK;
	
	/**
	 * 화면설명
	 */
	private String contents_desc= Constants.BLANK;
	/**
	 * 미디어ID
	 */
	private String media_id= Constants.BLANK;
	/**
	 * 프로그램ID
	 */
	private String program_id= Constants.BLANK;
	/**
	 * 화면비율, 002:4:3, 001:16:9, 003:16:9SP, 004:14:9
	 */
	private String aspectratio = Constants.BLANK;
	/**
	 * 화질, 0:SD, 1:HD, 2:SD(HD), 3:4K, 4:3D-
	 */
	private String resolution= Constants.BLANK;
	/**
	 * 아카이브 경로
	 */
	private String arch_path= Constants.BLANK;
	/**
	 * 회사코드=발행처=콘텐츠 소속사 company_cd=publisher=contents_owner, A:SBS 아트텍, C:SBS 콘텐츠 허브, E:SBS CNBC, G:SBS 골프, H:SBS 미디어 홀딩스, L:SBS 인터네셔널, N:SBS 뉴스텍, 
      Q:SBS 스포츠, S:SBS, U:SBS 플러스, Z:SBS E플러스, D:SBS 상조, R:SBS 미디어렙, T:SBS MTV, K:SBS 니켈로디언, V:SBS 바이아컴
	 */
	private String publisher = Constants.BLANK;
	/**
	 * 채널코드, A:SBS, P:SBS Plus, E:SBS E!, M:SBS MTV, N:NICK, S:SBS ESPN, G:SBS Golp, C:SBS CNBC
	 */
	private String channel_cd= Constants.BLANK;
	/**
	 * 녹음방식=오디오 송출형태, M:모노, S:스테레오, B:음성다중, F:5.1CH, Y:화면해설, O:8채널, A:4채널
	 */
	private String audio_type = Constants.BLANK;
	/**
	 * 방송길이
	 */
	private  String brd_leng = Constants.BLANK;
	/**
	 * ORG_FILE_NM = 파일명
	 */
	private String file_name= Constants.BLANK;
	/**
	 * 파일크기
	 */
	private String file_size= Constants.BLANK;
	/**
	 * 콘텐츠유형 001:전타이틀, 002:후타이틀, 003:본방송, 004:CM, 005:타이틀, 006:ID, 007:MovingCM, 009:이어서, 010:PR
	 */
	private String contents_type = Constants.BLANK;
	/**
	 * 요청일시
	 */
	private String datatime_request= Constants.BLANK;
	/**
	 * 요청자ID
	 */
	private String request_id= Constants.BLANK;
	/**
	 * som
	 */
	private String som = Constants.BLANK;
	/**
	 * eom
	 */
	private String eom = Constants.BLANK;
	/**
	 * 수평 해상도
	 */
	private String vd_hresol= Constants.BLANK;
	/**
	 * 수직 해상도
	 */
	private String vd_vresol = Constants.BLANK;
	/**
	 * 자막파일(한국어) 파일명
	 */
	private String caption_kr = Constants.BLANK;
	/**
	 * 자막파일(일본어) 파일명
	 */
	private String caption_jp = Constants.BLANK;
	/**
	 * 자막파일(영어) 파일명
	 */
	private String caption_en = Constants.BLANK;
	/**
	 * 자막파일(중국어) 파일명
	 */
	private String caption_cn = Constants.BLANK;
	/**
	 * Preview Note 파일명
	 */
	private String preview_kr = Constants.BLANK;
	
	
	/**
	 * 에피소드 번호
	 */
	private String episode_no = Constants.BLANK;
	/**
	 * Drop frame 여부, Y/N
	 */
	private String drp_frm_yn = Constants.BLANK;
	/**
	 * 스토리지경로
	 */
	private String storage_path = Constants.BLANK;
	/**
	 * 오디오 여부
	 */
	private String audio_yn = Constants.BLANK;
	/**
	 * 비트레이트
	 */
	private String bit_rt = Constants.BLANK;
	/**
	 * 초당 프래임수
	 */
	private String frm_per_sec = Constants.BLANK;
	/**
	 * 오디오 샘플링레이트
	 */
	private String aud_samp_frq = Constants.BLANK;
	
	
	/**
	 * 자막파일(한국어) 크기
	 */
	private String kr_size = Constants.BLANK;
	/**
	 * 자막파일(일본어) 크기
	 */
	private String jp_size = Constants.BLANK;
	/**
	 * 자막파일(영어)  크기
	 */
	private String en_size = Constants.BLANK;
	/**
	 * 자막파일(중국어)  크기
	 */
	private String cn_size = Constants.BLANK;
	/**
	 * 미디어파일 크기
	 */
	private String mxf_size = Constants.BLANK;
	/**
	 * Preview Note 크기
	 */
	private String pre_size = Constants.BLANK;
	
	/**
	 * type(파일종류), isfolder(폴더여부) attribute, source(소스파일명) attribute, 타겟파일명 element value
      type 코드값 : 001(미디어파일), 002(프리뷰노트), 003(한국어자막), 004(일본어자막), 005(영어자막), 006(중국어자막)
	 */
	private String type = Constants.BLANK;
	
	
	/**
	 * mxf 파일명
	 */
	private String source = Constants.BLANK;
	
	
	/**
	 * 유형값
	 */
	private String xmf_yn = "N";
	private String cn_yn = "N";
	private String kr_yn = "N";
	private String jp_yn = "N";
	private String en_yn = "N";
	
	
	
	//키값
	
	/**
	 * 마스터id
	 */
	private long master_id=0;
	/**
	 * 영상id
	 */
	private long ct_id=0;
	/**
	 * 고해상도 영상 인스턴스id
	 */
	private long cti_idForHigh=0;
	/**
	 * 저해상도 영상 인스턴스id
	 */
	private long cti_idForLow=0;
	/**
	 * 코너id
	 */
	private long cn_id=0;
	/**
	 * 주석 id
	 */
	private long annot_id=0;
	/**
	 * 첨부파일id
	 */
	private long attach_id=0;
	
	/**
	 * 프리뷰id
	 */
	private long preview_id=0;
	

	/**
	 * 카트넘버
	 */
	private long cart_no=0;
	
	/**
	 * 카트순번
	 */
	private long cart_seq=0;
	
	
	
	/**
	 * 코너제목
	 */
	private String corner_title = Constants.BLANK;
	
	
	/**
	 * 저작권형태, 001:일체소유, 002:일부소유, 003:저작권없음, 004:미확인
	 */
	private String copyright_type = Constants.BLANK;
	
	/**
	 * 저작권자
	 */
	private String copyright_owner = Constants.BLANK;
	
	/**
	 * 저작권설명
	 */
	private String copyright_desc = Constants.BLANK;
	
	/**
	 * 코너내용
	 */
	private String corner_contents = Constants.BLANK;
	
	/**
	 * 사용등급내용
	 */
	private String usegrade_desc = Constants.BLANK;
	
	
	/**
	 * 진행자명
	 */
	private String name_host = Constants.BLANK;
	
	/**
	 * 출연자명
	 */
	private String name_guest = Constants.BLANK;
	
	/**
	 * 음악정보
	 */
	private String music_info = Constants.BLANK;
	/**
	 * 미디어형식
	 */
	private String media_format = Constants.BLANK;
	
	/**
	 * 오디오 대역폭
	 */
	private String aud_bandwidth = Constants.BLANK;
	
	/**
	 *콘텐츠유형=>방송이벤트구분으로 변경, 001:전타이틀, 002:후타이틀, 003:본방송, 004:CM, 005:타이틀, 006:ID, 007:MovingCM, 009:이어서, 010:PR
	 */
	private String broadcast_event_type = Constants.BLANK;
	/**
	 *콘텐츠구분>콘텐츠제작구분으로 변경, 001:원본, 010:개편본, 011:종편본, 005:Clean, 006:방송본
	 */
	private String contents_class = Constants.BLANK;
	/**
	 *방송시작시간,HHMMSS
	 */
	private String bgn_time_onair = Constants.BLANK;
	/**
	 *방송종료시각
	 */
	private String end_time_onair = Constants.BLANK;
	
	/**
	 *작업자
	 */
	private String worker_id = Constants.BLANK;
	
	/**
	 *다운로드 사유
	 */
	private String download_comment = Constants.BLANK;
	
	/**
	 * 프로그램명
	 */
	private String program_name = Constants.BLANK;

	/**
	 * 물리적트리
	 */
	private String phyical_tree = Constants.BLANK;

	/**
	 * 논리적트리
	 */
	private String logical_tree = Constants.BLANK;

	/**
	 *스토리지 명
	 */
	private String storage_nm = Constants.BLANK;
	/**
	 *url
	 */
	private String callback_url = Constants.BLANK;

	/**
	 *다운로드완료일시
	 */
	private String complete_dt = Constants.BLANK;

	/**
	 *TX_ID
	 */
	private String tx_id = Constants.BLANK;


	/**
	 * 콘텐츠 소속 채널, A:SBS, P:SBS Plus, E:SBS E!, M:SBS MTV, N:NICK, S:SBS ESPN, G:SBS Golf, C:SBS CNBC
	 */
	private String contents_channel = Constants.BLANK;

	

	/**
	 *소재 구분, PGM:프로그램, Material:소재
	 */
	private String material_type = Constants.BLANK;

	
	/**
	 *변경된 파일명
	 */
	private String refile_nm = Constants.BLANK;

	
	/**
	 * 프리뷰노트경로
	 */
	private String preview_path = Constants.BLANK;

	
	/**
	 * 자막경로
	 */
	private String caption_path = Constants.BLANK;

	/**
	 * 등록자
	 */
	private String reg_nm = Constants.BLANK;

	
	
	
	
	public String getPgm_nm() {
		return pgm_nm;
	}
	public void setPgm_nm(String pgmNm) {
		pgm_nm = pgmNm;
	}
	public long getCart_seq() {
		return cart_seq;
	}
	public void setCart_seq(long cartSeq) {
		cart_seq = cartSeq;
	}
	public String getReg_nm() {
		return reg_nm;
	}
	public void setReg_nm(String regNm) {
		reg_nm = regNm;
	}
	public String getPreview_path() {
		return preview_path;
	}
	public void setPreview_path(String previewPath) {
		preview_path = previewPath;
	}
	public String getCaption_path() {
		return caption_path;
	}
	public void setCaption_path(String captionPath) {
		caption_path = captionPath;
	}
	public String getRefile_nm() {
		return refile_nm;
	}
	public void setRefile_nm(String refileNm) {
		refile_nm = refileNm;
	}
	public String getContents_channel() {
		return contents_channel;
	}
	public void setContents_channel(String contentsChannel) {
		contents_channel = contentsChannel;
	}
	public String getMaterial_type() {
		return material_type;
	}
	public void setMaterial_type(String materialType) {
		material_type = materialType;
	}
	public String getTx_id() {
		return tx_id;
	}
	public void setTx_id(String txId) {
		tx_id = txId;
	}
	public String getComplete_dt() {
		return complete_dt;
	}
	public void setComplete_dt(String completeDt) {
		complete_dt = completeDt;
	}
	public String getCallback_url() {
		return callback_url;
	}
	public void setCallback_url(String callbackUrl) {
		callback_url = callbackUrl;
	}
	public String getStorage_nm() {
		return storage_nm;
	}
	public void setStorage_nm(String storageNm) {
		storage_nm = storageNm;
	}
	public String getPhyical_tree() {
		return phyical_tree;
	}
	public void setPhyical_tree(String phyicalTree) {
		phyical_tree = phyicalTree;
	}
	public String getLogical_tree() {
		return logical_tree;
	}
	public void setLogical_tree(String logicalTree) {
		logical_tree = logicalTree;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String programName) {
		program_name = programName;
	}
	public String getBgn_time_onair() {
		return bgn_time_onair;
	}
	public void setBgn_time_onair(String bgnTimeOnair) {
		bgn_time_onair = bgnTimeOnair;
	}
	public String getEnd_time_onair() {
		return end_time_onair;
	}
	public void setEnd_time_onair(String endTimeOnair) {
		end_time_onair = endTimeOnair;
	}
	public String getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(String workerId) {
		worker_id = workerId;
	}
	public String getDownload_comment() {
		return download_comment;
	}
	public void setDownload_comment(String downloadComment) {
		download_comment = downloadComment;
	}
	public String getContents_class() {
		return contents_class;
	}
	public void setContents_class(String contentsClass) {
		contents_class = contentsClass;
	}
	public String getBroadcast_event_type() {
		return broadcast_event_type;
	}
	public void setBroadcast_event_type(String broadcastEventType) {
		broadcast_event_type = broadcastEventType;
	}
	public String getAud_bandwidth() {
		return aud_bandwidth;
	}
	public void setAud_bandwidth(String audBandwidth) {
		aud_bandwidth = audBandwidth;
	}
	public String getMedia_format() {
		return media_format;
	}
	public void setMedia_format(String mediaFormat) {
		media_format = mediaFormat;
	}
	public String getMusic_info() {
		return music_info;
	}
	public void setMusic_info(String musicInfo) {
		music_info = musicInfo;
	}
	public String getName_host() {
		return name_host;
	}
	public void setName_host(String nameHost) {
		name_host = nameHost;
	}
	public String getName_guest() {
		return name_guest;
	}
	public void setName_guest(String nameGuest) {
		name_guest = nameGuest;
	}
	public String getUsegrade_desc() {
		return usegrade_desc;
	}
	public void setUsegrade_desc(String usegradeDesc) {
		usegrade_desc = usegradeDesc;
	}
	public String getCopyright_type() {
		return copyright_type;
	}
	public void setCopyright_type(String copyrightType) {
		copyright_type = copyrightType;
	}
	public String getCopyright_owner() {
		return copyright_owner;
	}
	public void setCopyright_owner(String copyrightOwner) {
		copyright_owner = copyrightOwner;
	}
	public String getCopyright_desc() {
		return copyright_desc;
	}
	public void setCopyright_desc(String copyrightDesc) {
		copyright_desc = copyrightDesc;
	}
	public String getCorner_title() {
		return corner_title;
	}
	public void setCorner_title(String cornerTitle) {
		corner_title = cornerTitle;
	}
	public String getCorner_contents() {
		return corner_contents;
	}
	public void setCorner_contents(String cornerContents) {
		corner_contents = cornerContents;
	}
	public long getCart_no() {
		return cart_no;
	}
	public void setCart_no(long cartNo) {
		cart_no = cartNo;
	}
	public long getAttach_id() {
		return attach_id;
	}
	public void setAttach_id(long attachId) {
		attach_id = attachId;
	}
	public long getPreview_id() {
		return preview_id;
	}
	public void setPreview_id(long previewId) {
		preview_id = previewId;
	}
	public String getXmf_yn() {
		return xmf_yn;
	}
	public void setXmf_yn(String xmfYn) {
		xmf_yn = xmfYn;
	}
	public String getCn_yn() {
		return cn_yn;
	}
	public void setCn_yn(String cnYn) {
		cn_yn = cnYn;
	}
	public String getKr_yn() {
		return kr_yn;
	}
	public void setKr_yn(String krYn) {
		kr_yn = krYn;
	}
	public String getJp_yn() {
		return jp_yn;
	}
	public void setJp_yn(String jpYn) {
		jp_yn = jpYn;
	}
	public String getEn_yn() {
		return en_yn;
	}
	public void setEn_yn(String enYn) {
		en_yn = enYn;
	}
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public long getCt_id() {
		return ct_id;
	}
	public void setCt_id(long ctId) {
		ct_id = ctId;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getKr_size() {
		return kr_size;
	}
	public void setKr_size(String krSize) {
		kr_size = krSize;
	}
	public String getJp_size() {
		return jp_size;
	}
	public void setJp_size(String jpSize) {
		jp_size = jpSize;
	}
	public String getEn_size() {
		return en_size;
	}
	public void setEn_size(String enSize) {
		en_size = enSize;
	}
	public String getCn_size() {
		return cn_size;
	}
	public void setCn_size(String cnSize) {
		cn_size = cnSize;
	}
	public String getMxf_size() {
		return mxf_size;
	}
	public void setMxf_size(String mxfSize) {
		mxf_size = mxfSize;
	}

	public String getPre_size() {
		return pre_size;
	}
	public void setPre_size(String preSize) {
		pre_size = preSize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEpisode_no() {
		return episode_no;
	}
	public void setEpisode_no(String episodeNo) {
		episode_no = episodeNo;
	}
	public String getDrp_frm_yn() {
		return drp_frm_yn;
	}
	public void setDrp_frm_yn(String drpFrmYn) {
		drp_frm_yn = drpFrmYn;
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
	public String getAud_samp_frq() {
		return aud_samp_frq;
	}
	public void setAud_samp_frq(String audSampFrq) {
		aud_samp_frq = audSampFrq;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public long getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(long transactionId) {
		transaction_id = transactionId;
	}
	public String getBelong_to_group() {
		return belong_to_group;
	}
	public void setBelong_to_group(String belongToGroup) {
		belong_to_group = belongToGroup;
	}
	public long getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(long assetId) {
		asset_id = assetId;
	}
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long groupId) {
		group_id = groupId;
	}
	public String getStorage_id() {
		return storage_id;
	}
	public void setStorage_id(String storageId) {
		storage_id = storageId;
	}
	public String getFolder_id() {
		return folder_id;
	}
	public void setFolder_id(String folderId) {
		folder_id = folderId;
	}
	public long getTotal_fl_sz() {
		return total_fl_sz;
	}
	public void setTotal_fl_sz(long totalFlSz) {
		total_fl_sz = totalFlSz;
	}
	public String getFl_nm() {
		return fl_nm;
	}
	public void setFl_nm(String flNm) {
		fl_nm = flNm;
	}
	public long getFl_sz() {
		return fl_sz;
	}
	public void setFl_sz(long flSz) {
		fl_sz = flSz;
	}
	public String getFl_type() {
		return fl_type;
	}
	public void setFl_type(String flType) {
		fl_type = flType;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	public String getFm_dt() {
		return fm_dt;
	}
	public void setFm_dt(String fmDt) {
		fm_dt = fmDt;
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
	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}
	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
	}
	public String getCtgr_m_cd() {
		return ctgr_m_cd;
	}
	public void setCtgr_m_cd(String ctgrMCd) {
		ctgr_m_cd = ctgrMCd;
	}
	public String getCtgr_s_cd() {
		return ctgr_s_cd;
	}
	public void setCtgr_s_cd(String ctgrSCd) {
		ctgr_s_cd = ctgrSCd;
	}
	public String getView_gr_cd() {
		return view_gr_cd;
	}
	public void setView_gr_cd(String viewGrCd) {
		view_gr_cd = viewGrCd;
	}
	public String getView_rate() {
		return view_rate;
	}
	public void setView_rate(String viewRate) {
		view_rate = viewRate;
	}
	public String getCprt_type() {
		return cprt_type;
	}
	public void setCprt_type(String cprtType) {
		cprt_type = cprtType;
	}
	public String getLimited_use() {
		return limited_use;
	}
	public void setLimited_use(String limitedUse) {
		limited_use = limitedUse;
	}
	public String getLimited_use_cont() {
		return limited_use_cont;
	}
	public void setLimited_use_cont(String limitedUseCont) {
		limited_use_cont = limitedUseCont;
	}
	public String getUser_range() {
		return user_range;
	}
	public void setUser_range(String userRange) {
		user_range = userRange;
	}
	public String getCreator_sub() {
		return creator_sub;
	}
	public void setCreator_sub(String creatorSub) {
		creator_sub = creatorSub;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writerName) {
		writer_name = writerName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getDirector_shooting() {
		return director_shooting;
	}
	public void setDirector_shooting(String directorShooting) {
		director_shooting = directorShooting;
	}
	public String getPublisher_external() {
		return publisher_external;
	}
	public void setPublisher_external(String publisherExternal) {
		publisher_external = publisherExternal;
	}
	public String getProduction_type() {
		return production_type;
	}
	public void setProduction_type(String productionType) {
		production_type = productionType;
	}
	public String getLocation_shooting() {
		return location_shooting;
	}
	public void setLocation_shooting(String locationShooting) {
		location_shooting = locationShooting;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSpecial_info() {
		return special_info;
	}
	public void setSpecial_info(String specialInfo) {
		special_info = specialInfo;
	}
	public String getContents_name() {
		return contents_name;
	}
	public void setContents_name(String contentsName) {
		contents_name = contentsName;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRetention_period() {
		return retention_period;
	}
	public void setRetention_period(String retentionPeriod) {
		retention_period = retentionPeriod;
	}
	public String getContents_desc() {
		return contents_desc;
	}
	public void setContents_desc(String contentsDesc) {
		contents_desc = contentsDesc;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public String getProgram_id() {
		return program_id;
	}
	public void setProgram_id(String programId) {
		program_id = programId;
	}
	public String getAspectratio() {
		return aspectratio;
	}
	public void setAspectratio(String aspectratio) {
		this.aspectratio = aspectratio;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getArch_path() {
		return arch_path;
	}
	public void setArch_path(String archPath) {
		arch_path = archPath;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getChannel_cd() {
		return channel_cd;
	}
	public void setChannel_cd(String channelCd) {
		channel_cd = channelCd;
	}
	public String getAudio_type() {
		return audio_type;
	}
	public void setAudio_type(String audioType) {
		audio_type = audioType;
	}
	public String getBrd_leng() {
		return brd_leng;
	}
	public void setBrd_leng(String brdLeng) {
		brd_leng = brdLeng;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String fileName) {
		file_name = fileName;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String fileSize) {
		file_size = fileSize;
	}
	public String getContents_type() {
		return contents_type;
	}
	public void setContents_type(String contentsType) {
		contents_type = contentsType;
	}
	public String getDatatime_request() {
		return datatime_request;
	}
	public void setDatatime_request(String datatimeRequest) {
		datatime_request = datatimeRequest;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String requestId) {
		request_id = requestId;
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
	public String getCaption_kr() {
		return caption_kr;
	}
	public void setCaption_kr(String captionKr) {
		caption_kr = captionKr;
	}
	public String getCaption_jp() {
		return caption_jp;
	}
	public void setCaption_jp(String captionJp) {
		caption_jp = captionJp;
	}
	public String getCaption_en() {
		return caption_en;
	}
	public void setCaption_en(String captionEn) {
		caption_en = captionEn;
	}
	public String getCaption_cn() {
		return caption_cn;
	}
	public void setCaption_cn(String captionCn) {
		caption_cn = captionCn;
	}
	public String getPreview_kr() {
		return preview_kr;
	}
	public void setPreview_kr(String previewKr) {
		preview_kr = previewKr;
	}
	
	
	
	
}
