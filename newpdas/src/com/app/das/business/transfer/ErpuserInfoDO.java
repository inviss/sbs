package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * erp 사용자 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ErpuserInfoDO extends DTO{
	
	/**
	 * 유저id
	 */
	private String fileName           = Constants.BLANK;
	/**
	 * 첨부 파일 사이즈
	 */
	private long	fileSize; 
	/**
	 * 첨부 파일 경로
	 */
	private String filePath         = Constants.BLANK;
	/**
	 * 상세설명
	 */
	private String desc         = Constants.BLANK;
	/**
	 * 순번
	 */
	private int	seq;
	/**
	 * 모자료 ID
	 */
	private    long       mothrDataId;
	/**
	 * 첨부파일 유형 코드
	 */
	private    String       attcFileTypeCd     = Constants.BLANK;
	/**
	 * 첨부 구분 코드
	 */
	private    String       attcClfCd     = Constants.BLANK;
	/**
	 * 원파일 명
	 */
	private    String       orgFileNm     = Constants.BLANK;
	/**
	 * 등록 일시
	 */
	private    String       regDt     = Constants.BLANK;
	/**
	 * 등록자 ID
	 */
	private    String       regrid     = Constants.BLANK;
	/**
	 * 수정 일시
	 */
	private    String       modDt     = Constants.BLANK;
	/**
	 * 수정자 ID
	 */
	private    String       modrid     = Constants.BLANK;

	
	
}
