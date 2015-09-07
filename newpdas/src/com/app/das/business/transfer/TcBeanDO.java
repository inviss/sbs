package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;

/**
 * TC 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class TcBeanDO extends DTO 
{
	/**
	 * TC 아이디
	 */
	private String tc_id      = Constants.BLANK;
	/**
	 *  TC 명
	 */
	private String tc_nm      = Constants.BLANK;
	/**
	 * 채녈 구분
	 */
	private String ch_seq      = Constants.BLANK;
	/**
	 * 상태코드(B:Busy , I:Idle , F:Fail )
	 */
	private String work_stat      = Constants.BLANK;
	/**
	 * 미디어 아이디
	 */
	private String media_id = Constants.BLANK;
	/**
	 *  LR: LR 생성, CT: 카탈로그 생성, LRCT:LR+카탈로그 생성
	 */
	private String req_cd      = Constants.BLANK;
	/**
	 * 파일경로(MXF)
	 */
	private String input_hr      = Constants.BLANK;
	/**
	 * 파일경로(WMV)
	 */
	private String input_lr      = Constants.BLANK;
	/**
	 * 파일경로(파일경로+CTI_ID)
	 */
	private String out_put_lr_path      = Constants.BLANK;
	/**
	 * 파일경로(파일경로+CT_ID)
	 */
	private String out_put_ct_path      = Constants.BLANK;
	/**
	 * 파일네임(CTI_ID)
	 */
	private String out_put_lr_nm      = Constants.BLANK;
	/**
	 * 파일네임(CT_ID)
	 */
	private String out_put_ct_nm      = Constants.BLANK;
	
	/**
	 *  결과
	 */
	private String result      = Constants.BLANK;
	/**
	 *  컨텐츠id
	 */
	private long ct_id;
	/**
	 *  컨텐츠 인스트 id
	 */
	private long cti_id ;
	/**
	 *  순번
	 */
	private long seq ;

	/**
	 *  파일준비여부
	 */
	private String file_ready      = Constants.BLANK;
	/**
	 *  잡준비여부
	 */
	private String job_alocate      = Constants.BLANK;
	/**
	 *  등록일
	 */
	private String reg_dt      = Constants.BLANK;
	/**
	 *  고화질 영상명
	 */
	private String input_hr_nm      = Constants.BLANK;
	/**
	 *  저화질 영상명 
	 */
	private String input_lr_nm      = Constants.BLANK;
	/**
	 *  저화질 영상명 (h264)
	 */
	private String input_h264_nm      = Constants.BLANK;
	
	/**
	 *  wmv 재생성일
	 */
	private String re_wmv_dt = Constants.BLANK;
	/**
	 *  복본 재생성일
	 */
	private String re_copy_dt      = Constants.BLANK;
	
	/**
	 *  대표화면 키프레임 
	 */
	private long rpimg_kfrm ;
		
	/**
	 *  wmv 여부
	 */
	private String wmv_yn = Constants.BLANK;
	
	
	/**
	 * 오디오비트
	 */
	private String lr_audiobit      = Constants.BLANK;
	/**
	 * 파일사이즈
	 */
	private String lr_file_size      = Constants.BLANK;
	/**
	 * LR 해상도
	 */
	private String lr_resol      = Constants.BLANK;
	/**
	 *  LR 카탈로그
	 */
	private String ct_resol      = Constants.BLANK;
	/**
	 *  CT 파일 사이즈
	 */
	private String ct_file_size      = Constants.BLANK;
	
	/**
	 * 비트전송율
	 */
	private String LR_BIT_RT  = Constants.BLANK;
	/**
	 * 초당프레임
	 */
	private String LR_FRM_PER_SEC  = Constants.BLANK;
	/**
	 *  드롭프레임여부
	 */
	private String LR_DRP_FRM_YN = Constants.BLANK;
	/**
	 *  오디오대역폭
	 */
	private String LR_AUDIO_BDWT  = Constants.BLANK;
	/**
	 *  해상도
	 */
	private String LR_RESOL_LR  = Constants.BLANK;
	/**
	 *  파일사이즈 여부
	 */
	private String LR_FL_SZ  = Constants.BLANK;
	/**
	 *  오디오샘플링
	 */
	private String LR_AUDIO_SAMP_FRQ  = Constants.BLANK;
	/**
	 *  비트전송율

	 */
	private String HR_BIT_RT  = Constants.BLANK;
	/**
	 *  해상도
	 */
	private String HR_RESOL_HR  = Constants.BLANK;
	/**
	 *  초당프레임
	 */
	private String HR_FRM_PER_SEC  = Constants.BLANK;
	/**
	 *  드롭프레임여부 
	 */
	private String HR_DRP_FRM_YN  = Constants.BLANK;
	/**
	 *  오디오대역폭
	 */
	private String HR_AUDIO_BDWT  = Constants.BLANK;
	/**
	 *  파일사이즈

	 */
	private String HR_FL_SZ   = Constants.BLANK;
	/**
	 *  오디오샘플링
	 */
	private String HR_AUDIO_SAMP_FRQ  = Constants.BLANK;
	/**
	 *  콘텐츠 길이 
	 */
	private String HR_CT_LENG   = Constants.BLANK;
	/**
	 *  총키프렘임수 
	 */
	private String DURATION    = Constants.BLANK;
	
	/**
	 *  카트 번호
	 */
	private long cart_no ;
	
	
	//20110526  코너 재생성용
	
	/**
	 *  프로그램id
	 */
	private long pgm_id ;
	/**
	 *  코너id
	 */
	private long cn_id ;
	/**
	 *  마스터id
	 */
	private long master_id ;
	/**
	 *  등록자
	 */
	private String regrid  = Constants.BLANK;
	/**
	 *  종료일
	 */
	private String eom  = Constants.BLANK;
	/**
	 *  코너내용
	 */
	private String cn_info  = Constants.BLANK;
	
	/**
	 *  코너id
	 */
	private long cti_idForLow ;
	//2012.5.14 
	
	/**
	 *  요청자id
	 */
	private String req_id  = Constants.BLANK;
	/**
	 *  진행률
	 */
	private String progress  = Constants.BLANK;
	/**
	 *  진행상황(W : 대기중, I : 진행중, C : 완료, E: 오류)
	 */
	private String job_status  = Constants.BLANK;
	
	/**
	 *  JOB ID
	 */
	private long job_id =0;
	
	/**
	 *  코너 명
	 */
	private String cn_nm  = Constants.BLANK;
	
	/**
	 *  회사코드
	 */
	private String cocd  = Constants.BLANK;
	
	/**
	 *  저화질 영상명 (h264)
	 */
	private String output_h264_nm      = Constants.BLANK;
	
	
	
	
	
	
	//h264
	

	/**
	 * 오디오비트
	 */
	private String h264_audiobit      = Constants.BLANK;
	/**
	 * 파일사이즈
	 */
	private String h264_file_size      = Constants.BLANK;
	/**
	 * LR 해상도
	 */
	private String h264_resol      = Constants.BLANK;

	
	/**
	 * 비트전송율
	 */
	private String H264_BIT_RT  = Constants.BLANK;
	/**
	 * 초당프레임
	 */
	private String H264_FRM_PER_SEC  = Constants.BLANK;
	/**
	 *  드롭프레임여부
	 */
	private String H264_DRP_FRM_YN = Constants.BLANK;
	/**
	 *  오디오대역폭
	 */
	private String H264_AUDIO_BDWT  = Constants.BLANK;
	/**
	 *  해상도
	 */
	private String H264_RESOL_LR  = Constants.BLANK;
	/**
	 *  파일사이즈 여부
	 */
	private String H264_FL_SZ  = Constants.BLANK;
	/**
	 *  오디오샘플링
	 */
	private String H264_AUDIO_SAMP_FRQ  = Constants.BLANK;
	
	
	
	/**
	 * 수동아카이브 여부
	 */
	private String manual_yn  = Constants.BLANK;
	
	
	
	
	public String getManual_yn() {
		return manual_yn;
	}
	public void setManual_yn(String manualYn) {
		manual_yn = manualYn;
	}
	public String getH264_audiobit() {
		return h264_audiobit;
	}
	public void setH264_audiobit(String h264Audiobit) {
		h264_audiobit = h264Audiobit;
	}
	public String getH264_file_size() {
		return h264_file_size;
	}
	public void setH264_file_size(String h264FileSize) {
		h264_file_size = h264FileSize;
	}
	public String getH264_resol() {
		return h264_resol;
	}
	public void setH264_resol(String h264Resol) {
		h264_resol = h264Resol;
	}
	public String getH264_BIT_RT() {
		return H264_BIT_RT;
	}
	public void setH264_BIT_RT(String h264BITRT) {
		H264_BIT_RT = h264BITRT;
	}
	public String getH264_FRM_PER_SEC() {
		return H264_FRM_PER_SEC;
	}
	public void setH264_FRM_PER_SEC(String h264FRMPERSEC) {
		H264_FRM_PER_SEC = h264FRMPERSEC;
	}
	public String getH264_DRP_FRM_YN() {
		return H264_DRP_FRM_YN;
	}
	public void setH264_DRP_FRM_YN(String h264DRPFRMYN) {
		H264_DRP_FRM_YN = h264DRPFRMYN;
	}
	public String getH264_AUDIO_BDWT() {
		return H264_AUDIO_BDWT;
	}
	public void setH264_AUDIO_BDWT(String h264AUDIOBDWT) {
		H264_AUDIO_BDWT = h264AUDIOBDWT;
	}
	public String getH264_RESOL_LR() {
		return H264_RESOL_LR;
	}
	public void setH264_RESOL_LR(String h264RESOLLR) {
		H264_RESOL_LR = h264RESOLLR;
	}
	public String getH264_FL_SZ() {
		return H264_FL_SZ;
	}
	public void setH264_FL_SZ(String h264FLSZ) {
		H264_FL_SZ = h264FLSZ;
	}
	public String getH264_AUDIO_SAMP_FRQ() {
		return H264_AUDIO_SAMP_FRQ;
	}
	public void setH264_AUDIO_SAMP_FRQ(String h264AUDIOSAMPFRQ) {
		H264_AUDIO_SAMP_FRQ = h264AUDIOSAMPFRQ;
	}
	public String getOutput_h264_nm() {
		return output_h264_nm;
	}
	public void setOutput_h264_nm(String outputH264Nm) {
		output_h264_nm = outputH264Nm;
	}
	public String getInput_h264_nm() {
		return input_h264_nm;
	}
	public void setInput_h264_nm(String inputH264Nm) {
		input_h264_nm = inputH264Nm;
	}
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getCn_nm() {
		return cn_nm;
	}
	public void setCn_nm(String cnNm) {
		cn_nm = cnNm;
	}
	public long getJob_id() {
		return job_id;
	}
	public void setJob_id(long jobId) {
		job_id = jobId;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String reqId) {
		req_id = reqId;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getJob_status() {
		return job_status;
	}
	public void setJob_status(String jobStatus) {
		job_status = jobStatus;
	}
	public long getCti_idForLow() {
		return cti_idForLow;
	}
	public void setCti_idForLow(long ctiIdForLow) {
		cti_idForLow = ctiIdForLow;
	}
	public String getCn_info() {
		return cn_info;
	}
	public void setCn_info(String cnInfo) {
		cn_info = cnInfo;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public long getCn_id() {
		return cn_id;
	}
	public void setCn_id(long cnId) {
		cn_id = cnId;
	}
	public long getPgm_id() {
		return pgm_id;
	}
	public void setPgm_id(long pgmId) {
		pgm_id = pgmId;
	}
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public long getCart_no() {
		return cart_no;
	}
	public void setCart_no(long cartNo) {
		cart_no = cartNo;
	}
	public String getLR_BIT_RT() {
		return LR_BIT_RT;
	}
	public void setLR_BIT_RT(String lRBITRT) {
		LR_BIT_RT = lRBITRT;
	}
	public String getLR_FRM_PER_SEC() {
		return LR_FRM_PER_SEC;
	}
	public void setLR_FRM_PER_SEC(String lRFRMPERSEC) {
		LR_FRM_PER_SEC = lRFRMPERSEC;
	}
	public String getLR_DRP_FRM_YN() {
		return LR_DRP_FRM_YN;
	}
	public void setLR_DRP_FRM_YN(String lRDRPFRMYN) {
		LR_DRP_FRM_YN = lRDRPFRMYN;
	}
	public String getLR_AUDIO_BDWT() {
		return LR_AUDIO_BDWT;
	}
	public void setLR_AUDIO_BDWT(String lRAUDIOBDWT) {
		LR_AUDIO_BDWT = lRAUDIOBDWT;
	}
	public String getLR_RESOL_LR() {
		return LR_RESOL_LR;
	}
	public void setLR_RESOL_LR(String lRRESOLLR) {
		LR_RESOL_LR = lRRESOLLR;
	}
	public String getLR_FL_SZ() {
		return LR_FL_SZ;
	}
	public void setLR_FL_SZ(String lRFLSZ) {
		LR_FL_SZ = lRFLSZ;
	}
	public String getLR_AUDIO_SAMP_FRQ() {
		return LR_AUDIO_SAMP_FRQ;
	}
	public void setLR_AUDIO_SAMP_FRQ(String lRAUDIOSAMPFRQ) {
		LR_AUDIO_SAMP_FRQ = lRAUDIOSAMPFRQ;
	}
	public String getHR_BIT_RT() {
		return HR_BIT_RT;
	}
	public void setHR_BIT_RT(String hRBITRT) {
		HR_BIT_RT = hRBITRT;
	}
	public String getHR_RESOL_HR() {
		return HR_RESOL_HR;
	}
	public void setHR_RESOL_HR(String hRRESOLHR) {
		HR_RESOL_HR = hRRESOLHR;
	}
	public String getHR_FRM_PER_SEC() {
		return HR_FRM_PER_SEC;
	}
	public void setHR_FRM_PER_SEC(String hRFRMPERSEC) {
		HR_FRM_PER_SEC = hRFRMPERSEC;
	}
	public String getHR_DRP_FRM_YN() {
		return HR_DRP_FRM_YN;
	}
	public void setHR_DRP_FRM_YN(String hRDRPFRMYN) {
		HR_DRP_FRM_YN = hRDRPFRMYN;
	}

	public String getHR_AUDIO_BDWT() {
		return HR_AUDIO_BDWT;
	}
	public void setHR_AUDIO_BDWT(String hRAUDIOBDWT) {
		HR_AUDIO_BDWT = hRAUDIOBDWT;
	}
	public String getHR_FL_SZ() {
		return HR_FL_SZ;
	}
	public void setHR_FL_SZ(String hRFLSZ) {
		HR_FL_SZ = hRFLSZ;
	}
	public String getHR_AUDIO_SAMP_FRQ() {
		return HR_AUDIO_SAMP_FRQ;
	}
	public void setHR_AUDIO_SAMP_FRQ(String hRAUDIOSAMPFRQ) {
		HR_AUDIO_SAMP_FRQ = hRAUDIOSAMPFRQ;
	}
	public String getHR_CT_LENG() {
		return HR_CT_LENG;
	}
	public void setHR_CT_LENG(String hRCTLENG) {
		HR_CT_LENG = hRCTLENG;
	}
	public String getDURATION() {
		return DURATION;
	}
	public void setDURATION(String dURATION) {
		DURATION = dURATION;
	}
	public long getRpimg_kfrm() {
		return rpimg_kfrm;
	}
	public void setRpimg_kfrm(long rpimgKfrm) {
		rpimg_kfrm = rpimgKfrm;
	}
	public String getWmv_yn() {
		return wmv_yn;
	}
	public void setWmv_yn(String wmvYn) {
		wmv_yn = wmvYn;
	}
	public String getRe_wmv_dt() {
		return re_wmv_dt;
	}
	public void setRe_wmv_dt(String reWmvDt) {
		re_wmv_dt = reWmvDt;
	}
	public String getRe_copy_dt() {
		return re_copy_dt;
	}
	public void setRe_copy_dt(String reCopyDt) {
		re_copy_dt = reCopyDt;
	}

	public String getInput_hr_nm() {
		return input_hr_nm;
	}
	public void setInput_hr_nm(String inputHrNm) {
		input_hr_nm = inputHrNm;
	}
	public String getInput_lr_nm() {
		return input_lr_nm;
	}
	public void setInput_lr_nm(String inputLrNm) {
		input_lr_nm = inputLrNm;
	}
	public String getFile_ready() {
		return file_ready;
	}
	public void setFile_ready(String fileReady) {
		file_ready = fileReady;
	}
	public String getJob_alocate() {
		return job_alocate;
	}
	public void setJob_alocate(String jobAlocate) {
		job_alocate = jobAlocate;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public long getCt_id() {
		return ct_id;
	}
	public void setCt_id(long ctId) {
		ct_id = ctId;
	}
	
	public long getCti_id() {
		return cti_id;
	}
	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTc_id() {
		return tc_id;
	}
	public void setTc_id(String tcId) {
		tc_id = tcId;
	}
	public String getTc_nm() {
		return tc_nm;
	}
	public void setTc_nm(String tcNm) {
		tc_nm = tcNm;
	}
	public String getCh_seq() {
		return ch_seq;
	}
	public void setCh_seq(String chSeq) {
		ch_seq = chSeq;
	}
	public String getWork_stat() {
		return work_stat;
	}
	public void setWork_stat(String workStat) {
		work_stat = workStat;
	}

	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public String getReq_cd() {
		return req_cd;
	}
	public void setReq_cd(String reqCd) {
		req_cd = reqCd;
	}
	public String getInput_hr() {
		return input_hr;
	}
	public void setInput_hr(String inputHr) {
		input_hr = inputHr;
	}
	public String getInput_lr() {
		return input_lr;
	}
	public void setInput_lr(String inputLr) {
		input_lr = inputLr;
	}
	public String getOut_put_lr_path() {
		return out_put_lr_path;
	}
	public void setOut_put_lr_path(String outPutLrPath) {
		out_put_lr_path = outPutLrPath;
	}
	public String getOut_put_ct_path() {
		return out_put_ct_path;
	}
	public void setOut_put_ct_path(String outPutCtPath) {
		out_put_ct_path = outPutCtPath;
	}
	public String getOut_put_lr_nm() {
		return out_put_lr_nm;
	}
	public void setOut_put_lr_nm(String outPutLrNm) {
		out_put_lr_nm = outPutLrNm;
	}
	public String getOut_put_ct_nm() {
		return out_put_ct_nm;
	}
	public void setOut_put_ct_nm(String outPutCtNm) {
		out_put_ct_nm = outPutCtNm;
	}
	public String getLr_audiobit() {
		return lr_audiobit;
	}
	public void setLr_audiobit(String lrAudiobit) {
		lr_audiobit = lrAudiobit;
	}
	public String getLr_file_size() {
		return lr_file_size;
	}
	public void setLr_file_size(String lrFileSize) {
		lr_file_size = lrFileSize;
	}
	public String getLr_resol() {
		return lr_resol;
	}
	public void setLr_resol(String lrResol) {
		lr_resol = lrResol;
	}
	public String getCt_resol() {
		return ct_resol;
	}
	public void setCt_resol(String ctResol) {
		ct_resol = ctResol;
	}
	public String getCt_file_size() {
		return ct_file_size;
	}
	public void setCt_file_size(String ctFileSize) {
		ct_file_size = ctFileSize;
	}

}
