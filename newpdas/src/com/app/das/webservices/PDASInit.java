package com.app.das.webservices;

import org.apache.log4j.Logger;

import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.exception.DASException;
/**
 * pdas 초기화및 동기화 함수가 정의 되있다
 * 
 */
public class PDASInit {
	
	private Logger logger = Logger.getLogger(PDASInit.class);

	private static PDASInit instance;
	private static ExternalDAO externalDAO = ExternalDAO.getInstance();
	
	/**
	 * 초기화
	 * 
	 */	
	private PDASInit(){		
	}
	/**
	 * 동기화
	 * 
	 */	
	 public static synchronized PDASInit getInstance() 
		{
			if (instance == null) 
			{
				synchronized (PDASInit.class) {
					if(instance==null){
						instance = new PDASInit();
					}
				}
			}
			return instance;
		}
	 /**
		 * 모든lock을 해제한다
	 * @throws Exception 
		 * 
		 */	
	 public void updateLockStatCd() throws Exception{
			try 
			{	
				externalDAO.updateLockStatCd();
			} 
			catch (DASException e) 
			{
				logger.error(e);
				throw e;
			}
		 
	 }
	 
	 /**
		 * 초기 실행함수
		 * 
		 */	
	 public static void init(){
	/*	 try {
			updateLockStatCd();
		} catch (DASException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	 }
}
