package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * <pre>
 * 수동 및 자동 삭제요청시 사용할 xml
 * </pre>
 * @author M. S. Kang
 *
 */
@XStreamAlias("delete")
public class DeleteRequest {
	
	// 회차 단위로 삭제
	// 여러건을 삭제처리할경우 구분자(',')를 사용하여 나열한다.
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("master_id")
	private Long masterId;
	
	// 컨텐츠별 사용자 수동 삭제
	// 여러건을 요청할경우 구분자(',')를 사용하여 나열한다. 예) 1234,5678,....
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("ct_id")
	private Long ctId;
	
	// 컨텐츠별 사용자 수동 삭제
	// 여러건을 요청할경우 구분자(',')를 사용하여 나열한다. 예) 1234,5678,....
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cart_no")
	private Long cartNo;
	
	// 컨텐츠별 사용자 수동 삭제
	// 여러건을 요청할경우 구분자(',')를 사용하여 나열한다. 예) 1234,5678,....
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cart_seq")
	private Integer cartSeq;
	
	// 고화질 영상 ID
	// 여러건을 삭제처리할경우 구분자(',')를 사용하여 나열한다.
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("high_cti_id")
	private Long highCtiId;
	
	// 저화질 영상 ID
	// 여러건을 삭제처리할경우 구분자(',')를 사용하여 나열한다.
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("low_cti_id")
	private Long lowCtiId;
	
	// DTL 삭제
	// 여러건을 삭제처리할경우 구분자(',')를 사용하여 나열한다.
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("out_system_id")
	private String outSystemId;
	
	// 등록시간
	private String reqDt;
	// 삭제요청일
	private String reqDd;
	// 삭제요청구분
	private String reqGb;
	
	// 고화질 컨텐츠 경로
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("high_path")
	private String highPath;
	
	// 저화질 컨텐츠 경로
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("low_path")
	private String lowPath;
	
	// 고화질 영상 파일명
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("high_fl_nm")
	private String highFlNm;
	
	// 저화질 영상 파일명
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("low_fl_nm")
	private String lowFlNm;
	
	// 아카이브 상태
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("archive_ste_yn")
	private String archiveSteYn;
	
	// KFRM 경로
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("kfrm_path")
	private String kfrmPath;
	
	// Low Res 삭제여부
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("low_del_yn")
	private String lowDelYn;
	
	// contents_mapp_tbl에서 del_yn = 'N'인 ct_id count
	private Integer m1Cnt;
	// contents_mapp_tbl에서 del_dd가 입력된 del_yn = 'N'인 ct_id count
	private Integer m2Cnt;
	
	public String getLowDelYn() {
		return lowDelYn;
	}

	public void setLowDelYn(String lowDelYn) {
		this.lowDelYn = lowDelYn;
	}

	public String getReqDd() {
		return reqDd;
	}

	public void setReqDd(String reqDd) {
		this.reqDd = reqDd;
	}

	public Long getCartNo() {
		return cartNo;
	}

	public void setCartNo(Long cartNo) {
		this.cartNo = cartNo;
	}

	public Integer getCartSeq() {
		return cartSeq;
	}

	public void setCartSeq(Integer cartSeq) {
		this.cartSeq = cartSeq;
	}

	public Integer getM1Cnt() {
		return m1Cnt;
	}

	public void setM1Cnt(Integer m1Cnt) {
		this.m1Cnt = m1Cnt;
	}

	public Integer getM2Cnt() {
		return m2Cnt;
	}

	public void setM2Cnt(Integer m2Cnt) {
		this.m2Cnt = m2Cnt;
	}

	public String getKfrmPath() {
		return kfrmPath;
	}

	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}

	public Long getHighCtiId() {
		return highCtiId;
	}

	public void setHighCtiId(Long highCtiId) {
		this.highCtiId = highCtiId;
	}

	public Long getLowCtiId() {
		return lowCtiId;
	}

	public void setLowCtiId(Long lowCtiId) {
		this.lowCtiId = lowCtiId;
	}

	public String getArchiveSteYn() {
		return archiveSteYn;
	}

	public void setArchiveSteYn(String archiveSteYn) {
		this.archiveSteYn = archiveSteYn;
	}

	public String getReqGb() {
		return reqGb;
	}

	public void setReqGb(String reqGb) {
		this.reqGb = reqGb;
	}

	public String getHighFlNm() {
		return highFlNm;
	}

	public void setHighFlNm(String highFlNm) {
		this.highFlNm = highFlNm;
	}

	public String getLowFlNm() {
		return lowFlNm;
	}

	public void setLowFlNm(String lowFlNm) {
		this.lowFlNm = lowFlNm;
	}

	public String getHighPath() {
		return highPath;
	}

	public void setHighPath(String highPath) {
		this.highPath = highPath;
	}

	public String getLowPath() {
		return lowPath;
	}

	public void setLowPath(String lowPath) {
		this.lowPath = lowPath;
	}

	public String getReqDt() {
		return reqDt;
	}

	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public String getOutSystemId() {
		return outSystemId;
	}

	public void setOutSystemId(String outSystemId) {
		this.outSystemId = outSystemId;
	}
	
}
