package com.sbs.das.dto.ops;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.sbs.das.commons.utils.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("corner")
public class Corner {
	
	// 코너명
	//@XStreamCDATA
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("corner_nm")
	private String cornerNm;
	
	// 프레임시작구간번호
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("frame_st_sect_no")
	private Integer frameStSectNo;
	
	// 프레임종료구간번호
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("frame_fns_sect_no")
	private Integer frameFnsSectNo;
	
	// 장면메타제목명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamCDATA
	@XStreamAlias("sen_titles")
	private String senTitles;
	
	// 장면 메타내용
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("sen_sbsts")
	private String senSbsts;
	
	// 코너 대표이미지
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("rpimg_kfrm_seq")
	private Integer rpimgKfrmSeq;

	
	public Integer getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}

	public void setRpimgKfrmSeq(Integer rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
	}

	public String getCornerNm() {
		return cornerNm;
	}

	public void setCornerNm(String cornerNm) {
		this.cornerNm = cornerNm;
	}

	public Integer getFrameStSectNo() {
		return frameStSectNo;
	}

	public void setFrameStSectNo(Integer frameStSectNo) {
		this.frameStSectNo = frameStSectNo;
	}

	public Integer getFrameFnsSectNo() {
		return frameFnsSectNo;
	}

	public void setFrameFnsSectNo(Integer frameFnsSectNo) {
		this.frameFnsSectNo = frameFnsSectNo;
	}

	public String getSenTitles() {
		return senTitles;
	}

	public void setSenTitles(String senTitles) {
		this.senTitles = senTitles;
	}

	public String getSenSbsts() {
		return senSbsts;
	}

	public void setSenSbsts(String senSbsts) {
		this.senSbsts = senSbsts;
	}
	
	
}
