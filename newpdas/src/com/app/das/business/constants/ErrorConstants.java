package com.app.das.business.constants;

/**
 * 에러 코드의 정보를 상수로 정의해 놓은 class
 * @author ysk523
 *
 */
public class ErrorConstants 
{
	/**
	 * 이 class는 생성할 수 없다.
	 */
	private ErrorConstants()
	{
	}

	/**
	 * 시스템 장애입니다. 
	 */
	public static final String SYSTEM_ERR = "D0001";
	/**
	 * 해당 사용자가 존재하지 않습니다.
	 */
	public static final String NOT_EXIST_USER = "D0002";
	/**
	 * 계정이 만료되었습니다. 관리자에게 문의하세요.
	 */
	public static final String EXPIRE_USER_ACCOUNT = "D0003";
	/**
	 * SSO에 존재하지 않는 사용자 입니다. 관리자에게 문의하세요
	 */
	public static final String NOT_EXIST_SSO_USER = "D0004";
	/**
	 * SSO 관리자 계정 정보가 맞지 않습니다. 관리자에게 문의 하세요
	 */
	public static final String NOT_VALID_SSO_ADMIN_ACCOUNT = "D0005";
	/**
	 * 이미 존재하는 사용자 입니다.
	 */
	public static final String ALREADY_EXIST_USER = "D0006";
	/**
	 * 해당 코드값이 존재하지 않습니다.
	 */
	public static final String NOT_EXIST_CODE_INFO = "D0007";
	/**
	 * 폐기구분코드가 입력되지 않았습니다.
	 */
	public static final String NOT_INPUT_DISUSE_CODE = "D0008";
	/**
	 * 해당 오류정보가 존재하지 않습니다.
	 */
	public static final String NOT_EXIST_ERROR_INFO = "D0009";
	/**
	 * 해당 게시판 정보가 존재하지 않습니다.
	 */
	public static final String NOT_EXIST_BOARD_INFO = "D0010";
	/**
	 * ID 또는 Password 가 정확하지 않습니다.
	 */
	public static final String NOT_CORRECT_ID_OR_PASSWD = "D0011";
	/**
	 * 비밀번호 최대 실패 횟수를 초과했습니다.
	 */
	public static final String EXCESS_MAX_PASSWD_FAIL_COUNT = "D0012";
	/**
	 * 처음 접속하셨습니다.	비밀번호를 변경하십시요.
	 */
	public static final String FIRST_LOGIN_USER = "D0013";
	/**
	 * 비밀번호 변경일이 12개월을 초과했습니다. 관리자에게 문의하세요.
	 */
	public static final String EXCESS_12MONTH_CHANGE_PASSWD = "D0014";
	/**
	 * 비밀번호 변경일이 11개월을 초과했습니다. 비밀번호를 변경하십시요
	 */
	public static final String EXCESS_11MONTH_CHANGE_PASSWD = "D0015";
	/**
	 * 사용자 계정이 유효 만료일을 초과했습니다. 관리자에게 문의하세요.
	 */
	public static final String EXCESS_VALID_END_DATE = "D0016";
	/**
	 * 게시판 종류코드 오류입니다.
	 */
	public static final String NOT_VALID_BOARD_TYPE = "D0017";
	/**
	 * 이미 등록되어 있는 데이터입니다.
	 */
	public static final String ALREADY_DISUSE_DATA = "D0018";
	/**
	 * 동일 요청자아이디, 화질코드, 종횡비코드에 해당하는 다운로드 카트 정보가 존재합니다.
	 */
	public static final String ALREADY_DOWN_CART = "D0019";
	/**
	 * 해당 카트의 상태가 사용중이 아니므로 카트 내용을 추가할 수 없습니다.
	 */
	public static final String NOT_VALID_CART_STATUS = "D0020";
	/**
	 * 해당 다운로드 카트정보가 존재하지 않습니다.
	 */
	public static final String NOT_EXIST_CART_INFO = "D0021";
	/**
	 * 사용자에 해당하는 카트정보가 존재하지 않습니다.
	 */
	public static final String NOT_EXIST_CART_INFO_OF_REQ_USER = "D0022";
	/**
	 * 해당 테이프 대출 신청 정보를 찾을 수 없습니다.
	 */
	public static final String NOT_EXIST_TAPE_LENDING_INFO = "D0023";
	/**
	 * 해당 프로그램 정보를 찾을 수 없습니다.
	 */
	public static final String NOT_EXIST_PROGRAM_INFO = "D0024";
	/**
	 * SSO 로그인을 할 수 없어 ERP User Table 를 통한 로그인 처리를 합니다.
	 */
	public static final String NO_SSO_LOGIN = "D0025";
	/**
	 * UPDATE 또는 DELETE에 대한 행이 없습니다.
	 */
	public static final String NO_MACHING_FILED = "D0026";
	
	//////////////////////////////////////////////////////////
	/**
	 * 유효하지 않는 토큰입니다.
	 */
	public static final String INVALID_TOKEN = "D0027";
	
	/**
	 * 유효한 사용자 입니다.
	 */
	public static final String VALID_USER = "D0028";
	
	/**
	 * 패스원드 불일치 합니다.
	 */
	public static final String INVALID_PASSWORD ="D0029";
	
	
	
}
