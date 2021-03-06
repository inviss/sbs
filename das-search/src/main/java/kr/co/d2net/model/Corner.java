package kr.co.d2net.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 스토리보드
 * @author Administrator
 *
 */
@XmlRootElement(name="corner")
@XmlAccessorType(XmlAccessType.FIELD)
public class Corner {
	
	@XmlElement(name="group")
	private String group;
	@XmlElement(name="kfrm_path")
	private String kfrmPath;
	@XmlElement(name="kfrm_size")
	private Long kfrmSize;
	//20120731 getsubasset에서 바로 영상경로 가져오기
	@XmlElement(name="fl_path")
	private String flPath;
	@XmlElement(name="wrk_file_nm")
	private String wrkFileNm;
	@XmlElement(name="ct_nm")
	private String ctNm;
	@XmlElement(name="cn_info")
	private String cnInfo;
	@XmlElement(name="title")
	private String title;
	@XmlElement(name="media_id")
	private String mediaId;
	@XmlElement(name="ct_leng")
	private String ctLeng;
	@XmlElement(name="channel_cd")
	private String channelCd;
	
	
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getCtLeng() {
		return ctLeng;
	}
	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCnInfo() {
		return cnInfo;
	}
	public void setCnInfo(String cnInfo) {
		this.cnInfo = cnInfo;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String getWrkFileNm() {
		return wrkFileNm;
	}
	public void setWrkFileNm(String wrkFileNm) {
		this.wrkFileNm = wrkFileNm;
	}
	public Long getKfrmSize() {
		return kfrmSize;
	}
	public void setKfrmSize(Long kfrmSize) {
		this.kfrmSize = kfrmSize;
	}
	public String getKfrmPath() {
		return kfrmPath;
	}
	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	@XmlElement(name="item")
	List<CornerItem> items = new ArrayList<CornerItem>();
	public List<CornerItem> getItems() {
		return items;
	}
	public void setItems(List<CornerItem> items) {
		this.items = items;
	}
	public void addItem(CornerItem cornerItem) {
		this.items.add(cornerItem);
	}
	
}
