package com.app.das.business;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.UserInfoDAO;
import com.app.das.business.dao.UserRoleDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.TokenDO;
import com.app.das.log.DasPropHandler;
import com.app.das.log.ErrorPropHandler;
import com.app.das.util.CalendarUtil;
import com.app.das.util.CommonUtl;
import com.app.das.util.DateTime;
import com.app.das.util.StringUtils;

/**
 * 사용자 로그인 및 로그아웃에 대한 로직이 구현되어 있다.
 * 
 * @author ysk523
 * 
 */
public class LoginBusinessProcessor {
	private Logger logger = Logger.getLogger(LoginBusinessProcessor.class);

	private static final UserInfoDAO userInfoDAO = UserInfoDAO.getInstance();

	private static final UserRoleDAO userRoleDAO = UserRoleDAO.getInstance();



	private static ErrorPropHandler errorHandler = ErrorPropHandler.getInstance();
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * ; 표기
	 * 
	 */
	public final static String DELIM = ";";


	/**
	 * 1.0 소스 -  사용하지 않음
	 * @param _do
	 * @return 유효하지 않은 토큰 유효한 사용자 존재하지 않는 사용자
	 * @throws Exception 
	 */
	public DASCommonDO login(String userId, String passwd) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("[UserID]" + userId);
			logger.debug("[Password]" + passwd);
		}

		try {
			DASCommonDO commonDO = null;

			// 정직원의 경우 SSO 로그인 처리를 하고 정직원이 아닌 경우 자체 DAS DB의 로그인처리를 한다.
			if ((userId.toUpperCase().startsWith("S"))
					|| (userId.toUpperCase().startsWith("N"))
					|| (userId.toUpperCase().startsWith("A"))) {
				// SSO 로그인 처리를 한다. 만약 리턴되는 Map 객체에 데이타가 존재하지 않으면 ERP User Table
				// 를 통한 로그인 처리를 한다.
				Map userInfoMap = null;
				// COMM DB 를 통한 로그인 처리를 한다.
				if (userInfoMap.isEmpty()) {
					commonDO = userInfoDAO.selectEmployeeRoleLogin(userId,
							passwd);
				}
				// 정상 로그인이 되었기 때문에 사용자 정보를 조회한다.
				else {
					commonDO = userInfoDAO.selectEmployeeInfo(userId);
					commonDO.setToken((String) userInfoMap.get("TOKEN"));
					String warningMsg = (String) userInfoMap.get("warningMsg");
					if (!StringUtils.isEmpty(warningMsg)) {
						commonDO.setWarningMsg(warningMsg);
					}

				}
			} else {
				commonDO = userInfoDAO.selectNonEmployeeInfo(userId, passwd);

				// 비밀번호 실패횟수를 0으로 클리어 시킨다.
				userInfoDAO.updateLoginSucess(userId);
			}

			// 사용자 로그를 남기남긴다.
			userInfoDAO.insertIdLog(userId);

			return commonDO;
		} catch (Exception e){

			throw e;
		}
	}


	/**
	 * 1.0 소스 -  사용하지 않음
	 * @param _do
	 * @return 유효하지 않은 토큰 유효한 사용자 존재하지 않는 사용자
	 * @throws DASException
	 */
	public void logout(DASCommonDO commonDO) throws DASException {

	}

	/**
	 * 토큰을 통한 사용자 인증
	 * @param TokenDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public TokenDO isValidUserWithToken(TokenDO _do) throws Exception {


		/**
		 * 복호화 부분 추가 확인.
		 */
		if(_do.getToken().equals("")){
			_do.setAuth_result(errorHandler
					.getProperty(ErrorConstants.INVALID_TOKEN)); // authResult
			// 존재하지
			// 않는
			// 사용자
		}else{

			decryptionDO(_do);

		}
		logger.debug("isValidUserWithToken  decryption TokenDO [" + _do + "] ");

		String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmm");

		if (org.apache.commons.lang.StringUtils.isBlank(_do.getEnd_token_dd()))
			return null;

		if (Long.parseLong(_do.getEnd_token_dd().substring(0, 8)) < Long
				.parseLong(CommonUtl.getDate(dateTime).substring(0, 8))) { // 유효하지 않은 토큰
			_do.setAuth_result(errorHandler.getProperty(ErrorConstants.INVALID_TOKEN));
		} else if(_do.getEmpType().equals("SC")){
			_do.setAuth_result(errorHandler
					.getProperty(ErrorConstants.INVALID_TOKEN));
		} else { // 유효한 토큰
			// // key는 정직원:사번, 비직원개별ID:주민번호, 비직원공용ID: UserID
			// Local DB에 사용자존재유무 확인;
			TokenDO token = userRoleDAO.selecTokenInfo(_do.getUser_id());
			/**
			 * 신과장님 추가 사항 : 리턴값에 사용자 명, role 코드 추가 요청
			 */
			if (token != null) {
				_do.setRole_cd(token.getRole_cd());
				_do.setUser_nm(token.getUser_nm());
			}
			/**
			 * AD API 활용하여 진행할 것.
			 */
			String sResult="";

			JNI_Des hj =new JNI_Des();
			sResult = hj.getAuthentication(_do.getUser_id(),
					_do.getPassword(), dasHandler.getProperty("AD_DOMAIN"));

			_do.setResult(sResult);

			logger.debug("JNI_Des getAuthentication Result [" + sResult + "] ");
			if (_do.getResult().startsWith("0")&& token.getApprove_yn().equals("2")) // 사용자 인증성공
			{
				// // Token 만료일자 갱신,
				updateToken(_do);
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.VALID_USER)); // authResult
				// = 유효한
				// 사용자;
				/**
				 * 복호화 및 암호화 추가해야할 부분. DEKIM 20101129
				 */
			} else // 인증실패
			{
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.NOT_EXIST_USER)); // authResult
				// =
				// 존재하지
				// 않는
				// 사용자
			}
		}

		/**
		 * 암호화 부분 추가.
		 */
		logger.debug("isValidUserWithToken  before encryption TokenDO [" + _do
				+ "] ");
		encryptionDO(_do);

		return _do;
	}


	/**
	 * 토큰 없이 사용자 인증
	 * @param TokenDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	//sbs용 로그인 모듈. sbs 패치시 반드시 이걸 적용하여 패치한다. 2013.05.28
	public TokenDO isValidUser(TokenDO _do) throws Exception {

		// key는 정직원:사번, 비직원개별ID:주민번호, 비직원공용ID: UserID Local DB에서 사용자 계정유형 확인;

		// if ( Local DB에 해당 사용자가 없을 경우)

		TokenDO token = userRoleDAO.selecTokenInfo(_do.getUser_id().trim());

		String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmm");

		/**
		 * 신과장님 추가 사항 : 리턴값에 사용자 명, role 코드 추가 요청
		 */
		if (token != null) {
			_do.setRole_cd(token.getRole_cd());
			_do.setUser_nm(token.getUser_nm());
		}


		if (token.getUser_num().equals("")||(token.getUser_num().equals("0")&&token.getPer_reg_no().equals("0"))) {
			_do.setAuth_result(errorHandler.getProperty(ErrorConstants.NOT_EXIST_USER));
		} // *DAS의 경우 ‘비직원AD관리’Type이라도 비직원AD비관리 대상으로 로직처리함
		else if (token.getAcct_code().equals("RA") // 정직원 AD관리

				// NDS 비직원 개별 아이디에 대해서 타시스템 인터페이스로 들어온다.
				|| token.getAcct_code().equals("SC")) // 비직원공용ID
		{
			logger.debug("_do.getUser_id()() [" + _do.getUser_id() + "]");
			logger.debug("_do.getPassword() [" + _do.getPassword() + "]");
			logger.debug("dasHandler.getProperty(\"AD_DOMAIN\") ["+ dasHandler.getProperty("AD_DOMAIN") + "]");
			
			/**
			 * AD API 활용하여 진행할 것.
			 */
			String sResult="";

			/*
			 * 회사 개발서버에서 사용할 때는 AD 인증 서버가 없으므로 주석처리
			 * 운영에 적용할 때는 반드시 주석을 제거해야 함.
			 * JNI_Des hj =new JNI_Des();
			 * sResult = hj.getAuthentication(_do.getUser_id(), _do.getPassword(), dasHandler.getProperty("AD_DOMAIN"));
			 */
			
			/*
			 * 무조건 성공 메세지를 반환 (회사 개발서버)
			 * 운영에 적용할 때는 주석처리 해야 함.
			 */
		    sResult="0:365";  

			_do.setResult(sResult);

			logger.debug("JNI_Des getAuthentication Result [" + _do.getResult() + "] ");

			if (_do.getResult().startsWith("0")) { // _do 의 result 값이 '0'으로 시작하면
				// 성공 임당. ex) 0:365
				_do.setAuth_result(errorHandler.getProperty(ErrorConstants.VALID_USER));
			}
		} else // 정직원AD비관리, 비직원 AD비관리: AD 비관리 대상 (Local DB로 인증확인)
		{
			//Local DB에 존재하지 않음
			if(token == null) {
				_do.setAuth_result(errorHandler.getProperty(ErrorConstants.NOT_EXIST_USER));
			} else {
				// 패스워드 체크
				if(token.getPassword().trim().equals(userRoleDAO.getPasswd(_do.getPassword().trim()))) {
					_do.setAuth_result(errorHandler.getProperty(ErrorConstants.VALID_USER));
				} else {
					_do.setAuth_result(errorHandler.getProperty(ErrorConstants.INVALID_PASSWORD));
				}

				try {
					if(org.apache.commons.lang.StringUtils.isNotBlank(token.getEnd_token_dd()) &&
							DateTime.daysBetween(token.getEnd_token_dd(), DateTime.getDateString2(), "yyyyMMdd") < 0) {

					} else {
						_do.setAuth_result(errorHandler.getProperty(ErrorConstants.INVALID_TOKEN));
					}
				} catch (Exception e) {
					logger.error("유효기간 체크 오류", e);
				}

			}

			if(token.getAcct_code().equals("RB")){
				token.setEnd_token_dd("99991231");
			}


			if (token != null
					&& !token.getPassword().equals(
							userRoleDAO.getPasswd(_do.getPassword()))) {
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.INVALID_PASSWORD));
			} 


			else	if (Long.parseLong(token.getEnd_token_dd().substring(0, 8)) < Long
					.parseLong(CommonUtl.getDate(dateTime).substring(0, 8))) { // 유효하지
				// 않은
				// 토큰


				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.EXCESS_VALID_END_DATE));


			}else if (token == null) // if(Local DB에 존재하지 않음)
			{
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.NOT_EXIST_USER)); // authResult
				// =‘존재하지
				// 않는
				// 사용자’;
			} else if (token != null
					&& token.getPassword().trim().equals(
							userRoleDAO.getPasswd(_do.getPassword().trim()))) // else
				// if
				// (Local
				// DB에
				// 존재&&
				// Password일치)
			{
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.VALID_USER));
			}else if (token != null
					&& !token.getPassword().equals(
							userRoleDAO.getPasswd(_do.getPassword()))) {
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.INVALID_PASSWORD));
				_do.setAuth_result("D0029");

			} else if(token != null && token.getPassword().trim().equals(
					userRoleDAO.getPasswdByAD(_do.getPassword().trim()))&&token.getAcct_code().equals("RB")) {
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.VALID_USER));
			}
		}

		if (_do.getAuth_result().equals(
				errorHandler.getProperty(ErrorConstants.VALID_USER))) // AD인증성공
		{
			_do.setResult("0:365");
			createToken(_do);
			encryptionDO(_do);
		}
		//result = authResult + "|" + encrToken; // result = authResult & “구분자”
		// &
		// encrToken;
		/**
		 * 암호화 부분 추가해서 전달할 것.
		 */

		return _do;

	}



	//내부 시연용 로그인모듈 ad 인증을 걷어내고 무조건 로그인되도록 설정
	/*public TokenDO isValidUser(TokenDO _do) throws DASException {

		// key는 정직원:사번, 비직원개별ID:주민번호, 비직원공용ID: UserID Local DB에서 사용자 계정유형 확인;

		// if ( Local DB에 해당 사용자가 없을 경우)
		logger.debug("###_do.getUser_id() =" + _do.getUser_id());
		TokenDO token = userRoleDAO.selecTokenInfo(_do.getUser_id().trim());

		logger.debug("token passwod" +  token.getPassword() + "]");
		logger.debug("token passwod" +  _do.getPassword() + "]");
		logger.debug(" Password() [" + userRoleDAO.getPasswd(_do.getPassword().trim()) + "]");
		String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmm");

	 *//**
	 * 신과장님 추가 사항 : 리턴값에 사용자 명, role 코드 추가 요청
	 *//*
		if (token != null) {
			// logger.debug("token!=null");
			_do.setRole_cd(token.getRole_cd());
			_do.setUser_nm(token.getUser_nm());
		}


		if (token.getUser_num().equals("")||(token.getUser_num().equals("0")&&token.getPer_reg_no().equals("0"))) {

			// authResult = ‘존재하지 않는 사용자’;
			_do.setAuth_result(errorHandler
					.getProperty(ErrorConstants.NOT_EXIST_USER));
		} // *DAS의 경우 ‘비직원AD관리’Type이라도 비직원AD비관리 대상으로 로직처리함
		// else if ( 계정유형 ==‘정직원 AD관리’ or ‘비직원AD관리’ or ‘비직원공용ID’)
		else if (token.getAcct_code().equals("RA") // 정직원 AD관리

				// ||_do.getAcct_code().equals("SA") //비직원AD관리 오경진 대리 20101129
				// NDS 비직원 개별 아이디에 대해서 타시스템 인터페이스로 들어온다.
				|| token.getAcct_code().equals("SC")) // 비직원공용ID
		{

	  *//**
	  * AD API 활용하여 진행할 것.
	  *//*
			String sResult="";
			if(System.getProperty("os.arch").equals("x86")){
				sResult="0:365";
			}else if(System.getProperty("os.arch").equals("amd64")){
				sResult="0:365";
			}else{
				//JNI_Des hj =new JNI_Des();
				//sResult = hj.getAuthentication(_do.getUser_id(),
					//	_do.getPassword(), dasHandler.getProperty("AD_DOMAIN"));
			}

			//개발소스에만 적용 운영에는 삭제

				sResult="0:365";  


			_do.setResult(sResult);

			logger.debug("JNI_Des getAuthentication Result [" + _do.getResult() + "] ");

			if (_do.getResult().startsWith("0")) { // _do 의 result 값이 '0'으로 시작하면
				// 성공 임당. ex) 0:365
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.VALID_USER));
			}
		} else // 정직원AD비관리, 비직원 AD비관리: AD 비관리 대상 (Local DB로 인증확인)
		{


			if(token.getAcct_code().equals("RB")){
				token.setEnd_token_dd("99991231");
			}


			if (token != null
					&& !token.getPassword().equals(
							userRoleDAO.getPasswd(_do.getPassword()))) {
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.INVALID_PASSWORD));
				//_do.setAuth_result("D0029");
				logger.debug("#### 0 ####"  +  _do.getAuth_result());
			} 


			else	if (Long.parseLong(token.getEnd_token_dd().substring(0, 8)) < Long
					.parseLong(CommonUtl.getDate(dateTime).substring(0, 8))) { // 유효하지
				// 않은
				// 토큰
				logger.debug("#### 1 ####");

				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.EXCESS_VALID_END_DATE));
				//.getProperty(ErrorConstants.INVALID_TOKEN));

				logger.debug("#### 2 #### : "  + _do.getAuth_result());
				// authResult == 유효하지 않은 토큰;
			}else if (token == null) // if(Local DB에 존재하지 않음)
			{
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.NOT_EXIST_USER)); // authResult
				logger.debug("#### 3 #### : "  + _do.getAuth_result());														// =‘존재하지
				// 않는
				// 사용자’;
			} else if (token != null
					&& token.getPassword().trim().equals(
							userRoleDAO.getPasswd(_do.getPassword().trim()))) // else
				// if
				// (Local
				// DB에
				// 존재&&
				// Password일치)
			{
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.VALID_USER));
				// authResult= ‘존재하는 사용자 & Password일치’;
			}else if (token != null
					&& !token.getPassword().equals(
							userRoleDAO.getPasswd(_do.getPassword()))) {
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.INVALID_PASSWORD));
				_do.setAuth_result("D0029");
				logger.debug("#### 4 #### : "  +  _do.getAuth_result());
			} else if(token != null && token.getPassword().trim().equals(
					token.getPassword().trim())&&token.getAcct_code().equals("RB")) {
				_do.setAuth_result(errorHandler
						.getProperty(ErrorConstants.VALID_USER));
			}
			// _do.setAuth_result(errorHandler.getProperty(ErrorConstants.VALID_USER));
		}

		if (_do.getAuth_result().equals(
				errorHandler.getProperty(ErrorConstants.VALID_USER))) // AD인증성공
		{
			_do.setResult("0:365");
			createToken(_do);
		//	encryptionDO(_do);
			logger.debug("Success create Token!");
		}

	   *//**
	   * 암호화 부분 추가해서 전달할 것.
	   *//*

		return _do;

	}*/

	/**
	 * 토큰을 생성한다
	 * @param TokenDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public void createToken(TokenDO _do) throws Exception {
		String strToken = "";

		/**********************************************
		 * /** Token 규약 - 사번;주민번호;유저 ID;회사코드;소스시스템; Token 만료일;직원유형 (예 :
		 * 901679;710523-1396521; S901679;S;NCMS; YYYYMMDDHHMM;RA) 사번 = 로그인 사용자의
		 * 사번; // 정직원의 경우 주민번호 = 로그인 사용자의 주민번호; // 비직원의 경우 유저 ID = 로그인 사용자의
		 * UserID; 회사코드 = 로그인 사용자의 회사코드 소스 시스템 = 실행된 시스템 코드 Token 만료일 =
		 * 현재시간(sysdate); 직원유형 = 로그인 사용자의 계정유형코드
		 ****************************************************/
		TokenDO item = userRoleDAO.selecTokenInfo(_do.getUser_id());
		strToken = item.getUser_num() + DELIM + item.getPer_reg_no() + DELIM
		+ item.getUser_id() + DELIM + item.getCocd() + DELIM
		+ item.getSourceSYS() + DELIM
		+ CommonUtl.getSomeDateTime(null,0,0,0,36) + DELIM  // mailFrom : SSO 토큰 생성 방식 수정 요청
		+ item.getAcct_code();
		_do.setToken(strToken);
		logger.debug("create token [" + strToken + "] ");
		/**
		 * strToken 암호화 처리하여 전달 준비할 것.
		 */
	}

	/**
	 * 시간소인 업데이트
	 * 
	 * @param token
	 * @return
	 * @throws DASException
	 */
	public void updateToken(TokenDO _do) throws DASException {
		// // Token의 만료일 Update(local PC의 시간을 가져오지 않도록 함)
		_do.setEnd_token_dd(CommonUtl.getSomeDateTime(null,0,0,0,36)); // mailFrom : SSO 토큰 생성 방식 수정 요청
		// Token의 Token 만료일 현재시간(sysdate);
	}


	/**
	 * 복호화모듈
	 * @param TokenDO
	 * @return
	 * @throws RemoteException
	 */
	public void decryptionDO(TokenDO _do) throws DASException {
		/**
		 * Ex) Token 정보 Token 규약 - 사번;주민번호;유저 ID;회사코드;소스시스템; Token 만료일;직원유형 (예 :
		 * 901679;710523-1396521; S901679;S;NCMS; YYYYMMDDHHMM;RA)
		 */

		JNI_Des hj = new JNI_Des();

		String strToken = "";
		strToken = hj.getDecryption(dasHandler.getProperty("AD_CRYPTO_KEY"), _do.getHex(), _do.getToken());
		logger.debug("strToken=" + strToken);

		if (strToken != null) {
			_do.setToken(strToken);
		}

		// strToken="080009;1234567890123;D080009;D;DAS;201102282117;SA";
		// 가끔 이렇게 오기도 함 strToken=";0;SUPERIT;S;NCMS;201303012036;RA";
		// 무조건 배열길이가 7이 나와야 정상

		String[] tokens = strToken.trim().split(";");
		_do.setUser_num(tokens[0]);		 	// 사번
		_do.setPer_reg_no(tokens[1]);		// 주민번호
		_do.setUser_id(tokens[2]);			// 유저 ID
		_do.setCocd(tokens[3]);				// 회사코드
		_do.setSourceSYS(tokens[4]);		// 소스시스템
		_do.setEnd_token_dd(tokens[5]);		// token 만료일
		_do.setEmpType(tokens[6]);			// 직원유형

		/*
		StringTokenizer st = new StringTokenizer(strToken, DELIM);

		int iCnt = 0;

		while (st.hasMoreElements()) {

			switch (iCnt) {
			case 0:
				_do.setUser_num(st.nextElement() + ""); // 사번
				iCnt++;
				break;
			case 1:
				_do.setPer_reg_no(st.nextElement() + ""); // 주민번호
				iCnt++;
				break;
			case 2:
				_do.setUser_id(st.nextElement() + "");// 유저 ID
				iCnt++;
				break;
			case 3:
				_do.setCocd(st.nextElement() + ""); // 회사코드
				iCnt++;
				break;
			case 4:
				_do.setSourceSYS(st.nextElement() + ""); // 소스시스템
				iCnt++;
				break;
			case 5:
				_do.setEnd_token_dd(st.nextElement() + ""); // token 만료일
				iCnt++;
				break;
			case 6:
				_do.setEmpType(st.nextElement() + ""); // 직원유형
				iCnt++;
				break;

			default:
				break;
			}
		}
		 */
	}


	/**
	 * 암호화 모듈
	 * @param TokenDO
	 * @return
	 * @throws RemoteException
	 */
	public void encryptionDO(TokenDO _do) throws DASException {

		String strEncToken = "";

		if(System.getProperty("os.arch").equals("x86")){
			strEncToken="0:365";
		}else if(System.getProperty("os.arch").equals("amd64")){
			strEncToken="0:365";
		}else{
			JNI_Des hj = new JNI_Des();
			if (_do != null) {
				strEncToken = hj.setEncryption(
						dasHandler.getProperty("AD_CRYPTO_KEY"), _do.getHex(),
						_do.getToken());
			}
		}
		_do.setToken(strEncToken);
	}
}
