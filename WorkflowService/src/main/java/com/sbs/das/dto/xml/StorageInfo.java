package com.sbs.das.dto.xml;

import java.util.Date;

import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("storage")
public class StorageInfo {
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("real_path")
	private String path;				// VARCHAR(100) 경로
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("name")
	private String folderNm;			// VARCHAR(300) 폴더명
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("tot_size")
	private Long totalVolume;			// BIGINT 전체용량
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("use_size")
	private Long useVolume;				// BIGINT 사용량
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("free_size")
	private Long passibleVolume;		// BIGINT 가용량
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("limit")
	private String limit;				// VARCHAR(4) 임계치
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("desc")
	private String desc;				// VARCHAR(300) 설명
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("last_modified")
	private Date lastModified;			// CHAR(14) 최근 변경일

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFolderNm() {
		return folderNm;
	}

	public void setFolderNm(String folderNm) {
		this.folderNm = folderNm;
	}

	public Long getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Long totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Long getUseVolume() {
		return useVolume;
	}

	public void setUseVolume(Long useVolume) {
		this.useVolume = useVolume;
	}

	public Long getPassibleVolume() {
		return passibleVolume;
	}

	public void setPassibleVolume(Long passibleVolume) {
		this.passibleVolume = passibleVolume;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
