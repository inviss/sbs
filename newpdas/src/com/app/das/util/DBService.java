package com.app.das.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * WAS 의 Connection Pool에서 JNDI 이름으로 Connection을 얻는 로직 구현 class
 * @author ysk523
 *
 */
public class DBService 
{
	private static DBService instance;
	/**
	 * A private constructor
	 *
	 */
	private DBService() 
	{
	}

	/**
	 * <p>자기 자신의 Instance가 이미 생성되어 있는지 검사한 후, 생성 되어 있지 않으면</p> <p>Instance를 생성한 후, 반환한다.</p>
	 * @return  DBService  생성된 DBService Instance
	 * @uml.property  name="instance"
	 */
	public static synchronized DBService getInstance() 
	{
		if (instance == null) 
		{
			instance = new DBService();
		}
		return instance;
	}
	
	/**
	 * Connection Pool에 대한 JNDI Name으로 Connection을 얻어서 반환한다.
	 *
	 * @param jndiPoolName  특정 Connection Pool에 대한 JNDI Name
	 * @return  Connection  Connection Pool로 부터 얻어진 Connection Object
	 */
	
	public Connection getConnection() throws NamingException, SQLException 
	{
		Connection conn = null;
		try 
		{
			DataSource ds = (DataSource) JNDIService.getInstance().lookup();
			conn = ds.getConnection();
			return conn;
		} 
		catch (NamingException ne) 
		{
			throw ne;
		} 
		catch (SQLException se) 
		{
			throw se;
		}
	}
	
	/*
	public Connection getConnection() throws NamingException, SQLException
	{
		Connection conn = null;
		try 
		{
			String url = "jdbc:db2://10.150.12.161:50000/dasdb";
			//String url = "jdbc:db2://10.30.23.48:50000/dasdb";
			String user = "das";
			String password = "das";
			
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} 
		catch (Exception ne) 
		{
			throw new NamingException(ne.getMessage());
		}
	}
	*/
	
	/**
	 * Connection Pool에 대한 JNDI Name으로 ERP DB Connection을 얻어서 반환한다.
	 *
	 * @param jndiPoolName  특정 Connection Pool에 대한 JNDI Name
	 * @return  Connection  Connection Pool로 부터 얻어진 Connection Object
	 */
	
	public Connection getErpConnection() throws NamingException, SQLException 
	{
		Connection conn = null;
		try 
		{
			DataSource ds = (DataSource) JNDIService.getInstance().erpLookup();
			conn = ds.getConnection();
			return conn;
		} 
		catch (NamingException ne) 
		{
			throw ne;
		} 
		catch (SQLException se) 
		{
			throw se;
		}
	}
	
	/*
	public Connection getErpConnection() throws Exception {
		return null;
	}
	*/
}
