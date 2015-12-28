package com.app.das.business.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.app.das.util.DBService;

/**
 * @version 1.30
 *
 * 우리 시스템의 모든 DAO의 최상위 class로
 * AbstractDAO에서는 하위 DAO에서 공통으로 사용할 operation들을 정의한다.
 */

/**
 * 우리 시스템의 모든 DAO의 최상위 class로
 * AbstractDAO에서는 하위 DAO에서 공통으로 사용할 operation들을 정의한다.
 */
public abstract class AbstractDAO 
{

	private Logger logger = Logger.getLogger(AbstractDAO.class);
	
	/**
	 * 생성자
	 */
	public AbstractDAO() 
	{
		super();
	}

	/**
	 * ConnectionPool에서 connection을 하나 얻어온다.
	 *
	 * @throws NamingException
	 * @throws SQLException
	 * @return Connection
	 */
	public Connection getConnection() throws NamingException, SQLException 
	{
		return DBService.getInstance().getConnection();
	}
	
	
	/**
	 * ConnectionPool에서 ERP DB connection을 하나 얻어온다.
	 *
	 * @throws NamingException
	 * @throws SQLException
	 * @return Connection
	 */
	public Connection getErpConnection() throws Exception 
	{
		return DBService.getInstance().getErpConnection();
	}
	
	/**
	 * 입력받은 Count 쿼리를 수행하고 count를 리턴한다.
	 * @param con Connection 객체
	 * @param totalQuery Count 쿼리
	 * @return int 질의 결과인 count 
	 * @throws SQLException
	 */
    public int getTotalCount(Connection con, String totalQuery) throws SQLException
    {
    	
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try
        {
            psmt = con.prepareStatement(totalQuery);
            
            rs = psmt.executeQuery();
            rs.next();

            return rs.getInt(1);
        }
        catch (SQLException ex)
        {
        	logger.error(totalQuery);
            throw ex;
        }
        finally
        {
            release(rs, psmt, null);
        }
    }
    
    /**
     * 입력받은 시퀀스명에 해당하는 시퀀스를 채번한다.
     * @param con Connection 객체
     * @param seqName 시퀀스명
     * @return String 채번한 시퀀
     * @throws SQLException
     */
    public String getNextSquence(Connection con, String seqName) throws SQLException
    {
    	StringBuffer buf = new StringBuffer();
    	
    	buf.append("\n SELECT (NEXTVAL FOR DAS."+seqName+") AS SEQUENCE  ");
    	buf.append("\n FROM SYSIBM.SYSDUMMY1												");
    		
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try
        {
            psmt = con.prepareStatement(buf.toString());
            rs = psmt.executeQuery();
            rs.next();

            return rs.getString(1);
        }
        catch (SQLException ex)
        {
        	logger.error(buf.toString());
            throw ex;
        }
        finally
        {
            release(rs, psmt, null);
        }
    }
    
    
    /**
     * ERP에서 입력받은 시퀀스명에 해당하는 시퀀스를 채번한다.
     * @param con Connection 객체
     * @param seqName 시퀀스명
     * @return String 채번한 시퀀
     * @throws SQLException
     */
    public String getErpNextSquence(Connection con, String seqName) throws SQLException
    {
    	StringBuffer buf = new StringBuffer();
    	
    	buf.append("\n SELECT (NEXTVAL FOR DAT."+seqName+") AS SEQUENCE  ");
    	buf.append("\n FROM SYSIBM.SYSDUMMY1												");
    		
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try
        {
            psmt = con.prepareStatement(buf.toString());
            rs = psmt.executeQuery();
            rs.next();

            return rs.getString(1);
        }
        catch (SQLException ex)
        {
        	logger.error(buf.toString());
            throw ex;
        }
        finally
        {
            release(rs, psmt, null);
        }
    }
    
   
	/**
	 * 커넥션을 끊는다
	 * @param con Connection
	 */
	public void releaseConnection(Connection con) 
	{
		if (con != null) 
		{
			try 
			{
				con.close();
			} 
			catch (SQLException e) {}
		}
	}

	/**
	 * Statement를 해제한다
	 * @param statement Statement
	 */
	public void releaseStatement(Statement statement) 
	{
		if (statement != null) 
		{
			try 
			{
				statement.close();
			} 
			catch (SQLException e) {}
		}
	}

	/**
	 * ResultSet 을 해제한한다
	 * @param resultSet ResultSet
	 */
	public void releaseResultSet(ResultSet resultSet) 
	{
		if (resultSet != null) 
		{
			try 
			{
				resultSet.close();
			} 
			catch (SQLException e) {}
		}
	}

	/**
	 * ResultSet 과 Statement를 해제한다
	 * @param rs ResultSet
	 * @param stmt Statement
	 */
	public void release(ResultSet rs, Statement stmt) 
	{
		release(rs, stmt, null);
	}

	/**
	 * ResultSet 과 Statement과 Connection를 해제한다
	 * @param rs ResultSet
	 * @param stmt Statement
	 * @param con Connection
	 */
	public void release(ResultSet rs, Statement stmt, Connection con) 
	{
		try 
		{
			if (rs != null) 
			{
				rs.close();
				rs = null;
			}
		} 
		catch (SQLException e) {}
		try 
		{
			if (stmt != null) 
			{
				stmt.close();
				stmt = null;
			}
		} 
		catch (SQLException e) {}
		try 
		{
			if (con != null) {
			
				con.close();
				con = null;
			}
		} 
		catch (SQLException e) {}
	}
}
