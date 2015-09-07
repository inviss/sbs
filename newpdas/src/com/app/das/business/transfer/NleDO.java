

package com.app.das.business.transfer;
/**
 * NLE 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class NleDO extends DTO {
    
	  
	//마스터 id
	private long master_id; 
	//영상 인스턴스id
	private long cti_id;
	//영상 인스턴스id wmv용
	private long cti_idForLow;
	//영상 id
	private long ct_id;
	//코너id
	private long cn_id;

	//타이틀
	private String title;
	//영상구분
	private String con_cla;
	//미디어id
	private String media_id;  
	//방송길이
	private String brd_leng;
	//프로그램명
	private String pgm_nm;
	//부제
	private String sub_ttl;
	//파일명
	private String file_nm;
	//파일경로
	private String file_path;
	//방송일
	private String brd_dd;
	//스토리지 경로
	private String storage_path;
	//시작점
	private String som;
	//종료점 
	private String eom;
	
	
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
	public String getStorage_path() {
		return storage_path;
	}
	public void setStorage_path(String storagePath) {
		storage_path = storagePath;
	}
	public long getCti_idForLow() {
		return cti_idForLow;
	}
	public void setCti_idForLow(long ctiIdForLow) {
		cti_idForLow = ctiIdForLow;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public long getCti_id() {
		return cti_id;
	}
	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}
	public long getCt_id() {
		return ct_id;
	}
	public void setCt_id(long ctId) {
		ct_id = ctId;
	}
	public long getCn_id() {
		return cn_id;
	}
	public void setCn_id(long cnId) {
		cn_id = cnId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCon_cla() {
		return con_cla;
	}
	public void setCon_cla(String conCla) {
		con_cla = conCla;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public String getBrd_leng() {
		return brd_leng;
	}
	public void setBrd_leng(String brdLeng) {
		brd_leng = brdLeng;
	}
	public String getPgm_nm() {
		return pgm_nm;
	}
	public void setPgm_nm(String pgmNm) {
		pgm_nm = pgmNm;
	}
	public String getSub_ttl() {
		return sub_ttl;
	}
	public void setSub_ttl(String subTtl) {
		sub_ttl = subTtl;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String fileNm) {
		file_nm = fileNm;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String filePath) {
		file_path = filePath;
	}
	
	  
}
	
