package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;

/**
 * 콘텐츠 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ContentsPrevInfoDO {
	
	
	/**
	 * 코너 ID
	 */
	private long cornerID;
	/**
	 * 콘텐트 ID
	 */
	private long conentID;       
	/**
	 * 콘텐트 길이
	 */
	private long	duration;
	/**
	 * 콘텐트 Sequence
	 */
	private int contentSeq;
	/**
	 * 키프레임 경로
	 */
	private String kfrmPath		=		Constants.BLANK;;
	
	
	public String getkfrmPath() {
		return kfrmPath;
	}

	public void setkfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public long getCornerID() {
		return cornerID;
	}

	public void setCornerID(long cornerID) {
		this.cornerID = cornerID;
	}
	
	public long getContentID() {
		return conentID;
	}

	public void setContentID(long conentID) {
		this.conentID = conentID;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public int getContentSeq() {
		return contentSeq;
	}
	
	public void setContentSeq(int contentSeq) {
		this.contentSeq = contentSeq;
	}

}
