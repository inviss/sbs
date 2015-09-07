package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 파일 경로 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PathInfoDO extends DTO{
	
	private String	kfrmPath	=	 Constants.BLANK;
	
	private String	flPath		=  	Constants.BLANK;
	
	public String getKfrmPath() {
		return kfrmPath;
	}
	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	

}
