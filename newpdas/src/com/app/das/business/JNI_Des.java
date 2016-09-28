/***********************************************************
 * 프로그램ID  	: JNI_Des
 * 프로그램명  	: JNI_Des
 * 작성일자     	:
 * 작성자       	:
 * 설명         : AD 의 JNI 통한 인증 프로그램 
 *               ( 절대 package : com.app.das.business 바꾸지 말껏 : 바꾸려면 libADCrypto.so make 를 다시 할 것.)
 * 변경이력
 * --------------------------------------------------------------------
 *    변경NO     변경일자        변경자                설   명
 * --------------------------------------------------------------------
 *    1          2011/02/21     DEKIM               최초생성
 ***********************************************************/
package com.app.das.business;

import org.apache.log4j.Logger;

/**
 *  AD 의 JNI 통한 인증 프로그램 
 * @author ysk523
 *
 */
public class JNI_Des {

	private static JNI_Des instance;
	private static Logger logger = Logger.getLogger(JNI_Des.class);
		
	public JNI_Des(){		
	}
	
	 static
	  {
		 System.loadLibrary("ADCrypto"); //lib[ADCrypto].so
	  } 
	 /**
	  * JNI_Des 싱크
	  * @param arg1 KEY 값
	  */
//	 public static synchronized JNI_Des getInstance() 
//		{
//			if (instance == null) 
//			{
//				synchronized (JNI_Des.class) {
//					if(instance==null){
//						instance = new JNI_Des();
//					}
//				}
//			}
//			return instance;
//		}
	 
	 /**
	  * 암호화(Encryption)
	  * @param arg1 KEY 값
	  * @param arg2 HEX 값
	  * @param arg3 token_text 값(예 : 토큰 스티링)
	  * @return 암호화된 토큰 스트링
	  */
	 public static native String setEncryption(String KEY, String HEX,
            String token_text );

	 /**
	  * 복호화(decryption) ( Key : Hex : Text)
	  * @param arg1  KEY 값
	  * @param arg2  HEX 값
	  * @param arg3  token_text 값(예 : 암호화된 토큰 스트링)
	  * @return  복호화된 토큰 스트링
	  */
	 public static native String getDecryption(String KEY, String HEX,
            String token_text );
	 
	 /**
	  * AD 인증
	  * @param arg1  ID
	  * @param arg2  PASSWORD
	  * @param arg3  DOMAIN="tpds"
	  * @return '리턴코드값'|'패스원드 사용기간 만료일'
	  */
	 public static native String getAuthentication(String ID,String PASSWORD,
			 String DOMAIN);	
	}
