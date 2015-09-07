package com.sbs.das.dto;

public class StorageInfoTbl {
	
	private String path;				// VARCHAR(100) 경로
	private String folderNm;			// VARCHAR(300) 폴더명
	private Long totalVolume;			// BIGINT 전체용량
	private Long useVolume;				// BIGINT 사용량
	private Long passibleVolume;		// BIGINT 가용량
	private String limit;				// VARCHAR(4) 임계치
	private String desc;				// VARCHAR(300) 설명
	private String lastModified;		// CHAR(14) 최근 변경일
	
	
	public Long getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(Long totalVolume) {
		this.totalVolume = totalVolume;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
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
	
}
