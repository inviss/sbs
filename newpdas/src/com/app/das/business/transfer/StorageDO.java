package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 스토리지 임계점 관리 DataObject
 * @author ysk523
 *
 */
public class StorageDO extends DTO 
{
	/**
	 * 스토리지명
	 */
	private String storage_nm = Constants.BLANK;
	/**
	 * 스토리지크기
	 */
	private String storgae_size = Constants.BLANK;
	/**
	 * 스토리지 용량여부
	 */
	private String storage_yn = Constants.BLANK;
	
	/**
	 * 스토리지 한계점
	 * 
	 */
	private String limite = Constants.BLANK;
	
	
	/**
	 * 스토리지크기
	 */
	private long storgae_sz = 0L;
	
	/**
	 * 스토리지사용양
	 */
	private String using_sz  = Constants.BLANK;
	
	
	/**
	 * 시스템 id
	 */
	private String system_id  = Constants.BLANK;
	/**
	 * 경로
	 */
	private String path  = Constants.BLANK;
	/**
	 * 전체 용량
	 */
	private String total_size = Constants.BLANK;
	
	
	
	public String getTotal_size() {
		return total_size;
	}
	public void setTotal_size(String totalSize) {
		total_size = totalSize;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSystem_id() {
		return system_id;
	}
	public void setSystem_id(String systemId) {
		system_id = systemId;
	}
	public long getStorgae_sz() {
		return storgae_sz;
	}
	public void setStorgae_sz(long storgaeSz) {
		storgae_sz = storgaeSz;
	}
	public String getUsing_sz() {
		return using_sz;
	}
	public void setUsing_sz(String usingSz) {
		using_sz = usingSz;
	}
	public String getLimite() {
		return limite;
	}
	public void setLimite(String limite) {
		this.limite = limite;
	}
	public String getStorage_nm() {
		return storage_nm;
	}
	public void setStorage_nm(String storageNm) {
		storage_nm = storageNm;
	}
	public String getStorgae_size() {
		return storgae_size;
	}
	public void setStorgae_size(String storgaeSize) {
		storgae_size = storgaeSize;
	}
	public String getStorage_yn() {
		return storage_yn;
	}
	public void setStorage_yn(String storageYn) {
		storage_yn = storageYn;
	}
	
}
