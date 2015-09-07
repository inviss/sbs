package com.app.das.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * WAS의 JNDI 서버에 등록되어 있는 DataSource를 lookup 하여 반환한다.
 * @author ysk523
 *
 */
public class JNDIService 
{
	private static JNDIService instance;
	
	private static final String DATA_SOURCE_NAME = "dasdb";
	//private static final String DATA_SOURCE_NAME = "14.36.147.23:50002/dasdb";
	//private static final String ERP_SOURCE_NAME = "sbsdev";
	
	private static final String ERP_SOURCE_NAME = "erpdev";
	
	/**
	 * A private constructor
	 *
	 */
	private JNDIService() 
	{
	}

	/**
	 * <p>자기 자신의 Instance가 이미 생성되어 있는지 검사한 후, 생성 되어 있지 않으면</p> <p>Instance를 생성한 후, 반환한다.</p>
	 * @return  JNDIService 생성된 자기 자신의 Instance
	 */
	public static synchronized JNDIService getInstance() 
	{
		if (instance == null) 
		{
			instance = new JNDIService();
		}
		return instance;
	}

	public Object lookup(String name) throws NamingException 
	{
		long time = System.currentTimeMillis();
		Object lookupObject = null;
		try 
		{
			Context context = new InitialContext();
			
			if(System.getProperty("os.arch").equals("x86")){
				Context env =(Context)context.lookup("java:comp/env");
				lookupObject = env.lookup("jdbc/"+name);
			}else if(System.getProperty("os.arch").equals("amd64")){
				Context env =(Context)context.lookup("java:comp/env");
				lookupObject = env.lookup("jdbc/"+name);
			}else if(System.getProperty("os.arch").equals("i386")){
				Context env =(Context)context.lookup("java:comp/env");
				lookupObject = env.lookup("jdbc/"+name);
			}else{
				lookupObject = context.lookup(name);
			}
			
		} 
		catch (NamingException ne) 
		{
			throw ne;
		}
		return lookupObject;
	}

	public Object lookup() throws NamingException 
	{
		return lookup(JNDIService.DATA_SOURCE_NAME);
	}
	
	public Object erpLookup() throws NamingException 
	{
		return lookup(JNDIService.ERP_SOURCE_NAME);
	}

}
