package com.app.das.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.app.das.business.EmployeeRoleBusinessProcessor;
import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.DASCommonDO;

/**
 * 공통 코드 관련 로직을 구현해 놓았다. <p> 
 * 코드를 조회하고 HTML Combo Tag 등을 생성하는 메소드들로 구성되어 있다.
 * @author ysk523
 *
 */
public class CodeUtil 
{
	private static Logger logger = Logger.getLogger(CodeUtil.class);
	
	/**
	 * 해당 코드 값들을 HashMap으로 리턴 한다. <br>
	 * @param inputKeys
	 * @return
	 */
	public static HashMap getCodeData(String[] inputKeys)
	{
		String dinamicSql = "";
		for(int a=0; a<inputKeys.length; a++)
		{
			if(a == 0)
			{
				dinamicSql = dinamicSql + " ? ";
			}
			else
			{
				dinamicSql = dinamicSql + ", ? ";
			}
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append(" select                             				\n");
		buf.append(" 	CLF_CD,                         			\n");
		buf.append(" 	SCL_CD,                         			\n");
		buf.append(" 	DESC                            			\n");
		buf.append(" from DAS.CODE_TBL                  	\n");
		buf.append(" where CLF_CD in (" + dinamicSql + ")   	\n");
		buf.append("     AND USE_YN = 'Y'			\n");
		buf.append(" order by CLF_CD, SCL_CD		\n");
		buf.append(" WITH UR \n");
		
		HashMap resultMap = new HashMap();
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			
//			stmt = LoggableStatement.getInstance(con, buf.toString());
			
			stmt = con.prepareStatement(buf.toString());
			
			//입력받은 인자만큼의 '?' 에 셋팅을 한다.
			int index = 0;
			for(int a=0; a<inputKeys.length; a++)
			{
				stmt.setString(++index, inputKeys[a]);
			}			
			
			rs = stmt.executeQuery();
			
			HashMap itemHash = null;
			String beforeMainCode = "";
			String mainCode = "";
			
			//StringBuffer tag = null;
			HashMap subMap = null;
			//List subList = null;
			
			int indexCount = 0;
			while(rs.next())
			{
				mainCode = rs.getString("CLF_CD");
				//이전의 공통대분류코드가 지금의 공통대분류코드와 틀리다면 새로운 HashMap을 생성한다.
				if(!beforeMainCode.equals(mainCode))
				{
					if(indexCount > 0)
					{
						resultMap.put(beforeMainCode,  subMap);
						//resultMap.put(beforeMainCode,  subList);
					}
					
					//tag = new StringBuffer();
					subMap = new HashMap();
					//subList = new ArrayList();
				}
				
				subMap.put( rs.getString("SCL_CD") , rs.getString("DESC") );
				
				beforeMainCode = mainCode;
				
				indexCount++;
			}
			
			//마지막 공통대분류 코드에 대한 처리를 해야한다.
			if( subMap != null )
			{
				resultMap.put(beforeMainCode,  subMap);
			}
			
			return resultMap;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			
			return resultMap;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			return resultMap;
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}
	
	/**
	 * 상세검색 코드를 리턴. <br>
	 * @param inputKeys
	 * @return
	 */
	public static HashMap getCodeDetailData(String inputKeys)
	{
		StringBuffer buf = new StringBuffer();
		buf.append(" select                             				\n");
		buf.append(" 	SCL_CD,                         			\n");
		buf.append(" 	DESC                            			\n");
		buf.append(" from DAS.CODE_TBL                  	\n");
		buf.append(" where CLF_CD = '" + inputKeys + "'   	\n");
		buf.append("     AND USE_YN = 'Y'			\n");
		buf.append(" order by DESC		\n");
		buf.append(" WITH UR \n");
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap subMap = new HashMap();
		try 
		{
			con = DBService.getInstance().getConnection();
			
//			stmt = LoggableStatement.getInstance(con, buf.toString());
			
			stmt = con.prepareStatement(buf.toString());
			
			rs = stmt.executeQuery();
			
			//StringBuffer tag = null;
			//List subList = null;
		
			while(rs.next())
			{
				subMap.put( rs.getString("DESC") , rs.getString("SCL_CD") );
			}

		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
		return subMap;
	}
	
	/**
	 * 옵션태그를 생성 후 리턴 한다.
	 * @param objMap		콤보박스에 출력 할 데이터
	 * @param selectionKey	기본 선택 값
	 * @param maskType	마스크 0 이면 안함.
	 * @param viewCode	코드 출력 여부.
	 * @return
	 */
	public static String generateOptionTag( Object objMap , String selectionKey , int maskType , boolean viewCode ) {
		
		StringBuffer resultStr = new StringBuffer();
		
		if( objMap != null ) {
			
			HashMap hashMap = (HashMap)objMap;
			
			Map mm = (Map)hashMap;
			TreeMap sm = new TreeMap(mm);
			
			resultStr.append("<option value=\"\">선택하세요</option>");
			
			for( Iterator it = sm.keySet().iterator(); it.hasNext(); ) 
			{
				String key = (String)it.next();
				String name = (String)sm.get( key );
				
				resultStr.append("<option ");
				resultStr.append(" value=\"" + key + "\" ");
				if( key.equals( selectionKey ) == true ) 
				{
					resultStr.append(" SELECTED ");
				}
				resultStr.append(">");

				if( viewCode == true ) 
				{
					resultStr.append( key + ":" );
				}
				
				if( maskType == 0 ) 
				{
					resultStr.append( name );
				} 
				else 
				{
					resultStr.append( MaskUtil.getMaskString( maskType , name ) );
				}
				resultStr.append("</option>\n");
			}
		} else {
			
		}
		
		
		return resultStr.toString();
	}
	
	/**
	 * 옵션태그를 생성 후 리턴 한다.
	 * @param objMap		콤보박스에 출력 할 데이터
	 * @param selectionKey	기본 선택 값
	 * @param maskType	마스크 0 이면 안함.
	 * @param viewCode	코드 출력 여부.
	 * @return
	 */
	public static String generateOptionTag2( Object objMap , String selectionKey , int maskType , boolean viewCode ) {
		
		StringBuffer resultStr = new StringBuffer();
		
		if( objMap != null ) {
			
			HashMap hashMap = (HashMap)objMap;
			
			Map mm = (Map)hashMap;
			TreeMap sm = new TreeMap(mm);
			
			resultStr.append("<option value=\"\">선택하세요</option>");
			
			for( Iterator it = sm.keySet().iterator(); it.hasNext(); ) 
			{
				String key = (String)it.next();
				String name = (String)sm.get( key );
				
				resultStr.append("<option ");
				resultStr.append(" value=\"" + name + "\" ");
				if( name.equals( selectionKey ) == true ) 
				{
					resultStr.append(" SELECTED ");
				}
				resultStr.append(">");

				if( viewCode == true ) 
				{
					resultStr.append( name + ":" );
				}
				
				if( maskType == 0 ) 
				{
					resultStr.append( key );
				} 
				else 
				{
					resultStr.append( MaskUtil.getMaskString( maskType , key ) );
				}
				resultStr.append("</option>\n");
			}
		} else {
			
		}
		
		
		return resultStr.toString();
	}
	/**
	 * HashMap에 있는 데이터로 HTML option 태그를 생성하여 리턴 한다.
	 * 
	 * @param objMap Object
	 * @param selectionKey option태그의 SELECTED할 key(code)값.
	 * @return
	 */
	public static StringBuffer generateOptionTag( Object objMap , String selectionKey ) {
		
		StringBuffer resultStr = null;
		
		if( objMap != null ) {
			
			HashMap hashMap = (HashMap)objMap;
			
			Map mm = (Map)hashMap;
			TreeMap sm = new TreeMap(mm);
			
			resultStr = new StringBuffer();
			
			resultStr.append("<option value=\"\">선택하세요</option>");
			
			for( Iterator it = sm.keySet().iterator(); it.hasNext(); ) 
			{
				String key = (String)it.next();
				String name = (String)sm.get( key );
				
				resultStr.append("<option ");
				resultStr.append(" value=\"" + key + "\" ");
				if( key.equals( selectionKey ) == true ) 
				{
					resultStr.append(" SELECTED ");
				}
				resultStr.append(">");
				resultStr.append( name );
				resultStr.append("</option>\n");
			}
		} else {
			
		}

		return resultStr;
	}
	
	/**
	 * 대분류, 중분류, 소분류에 따른 해당 Code를 검색한다. <br>
	 * @param code 하휘
	 * @return
	 */
	public static HashMap getJanreCodeData(String code, int depth)
	{
		if(StringUtils.isEmpty(code))
		{
			return new HashMap();
		}
		
		
		StringBuffer buf = new StringBuffer();
		buf.append(" select                             				\n");	
		buf.append(" 	SCL_CD,                         			\n");
		buf.append(" 	DESC                            			\n");
		buf.append(" from DAS.CODE_TBL                  	\n");	
		buf.append(" where CLF_CD = ?                   	\n");
		buf.append("     AND USE_YN = 'Y'			\n");
		if(depth > 1)
		{
			buf.append(" 	and substr(SCL_CD, 1, ?) = ?  	\n");
		}
		buf.append(" order by RMK_1							\n");
		
		//코드 테이블의 구분코드를 구해온다.
		String groupCode = null;
		String startStr = null;
		if(depth == 1)
		{
			groupCode = CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY;
		}
		else if(depth == 2)
		{
			groupCode = CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY;
			startStr = code.substring(0, 1);
		}
		else
		{
			groupCode = CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY;
			startStr = code.substring(0, 2);
		}
		
		HashMap resultMap = new HashMap();
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			
			stmt = LoggableStatement.getInstance(con, buf.toString());
			
//			stmt = con.prepareStatement(buf.toString());
			
			int index = 0;
			stmt.setString(++index, groupCode);
			if(depth == 2)
			{
				stmt.setInt(++index, 1);
				stmt.setString(++index, startStr);
			}
			else if(depth == 3)
			{
				stmt.setInt(++index, 2);
				stmt.setString(++index, startStr);
			}
			
			
			rs = stmt.executeQuery();
			
			int indexCount = 0;
			while(rs.next())
			{
				resultMap.put( rs.getString("SCL_CD") , rs.getString("DESC") );
				indexCount++;
			}
			
			return resultMap;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			
			return resultMap;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			return resultMap;
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}

	/**
	 * 회사코드, 본부코드, 부서 코드를 조회한다.<br>
	 * @param code 선택된 코드
	 * @param organCode 회사코드 조회시 'C', 본부코드 조회시 'S', 부서코드 조회시 'D'
	 * @return
	 */
	public static HashMap getOrganCodeData(String code, String organCode)
	{
		if(StringUtils.isEmpty(code) && !DASBusinessConstants.OrganCode.COMPANY_CODE.equals(organCode))
		{
			return new HashMap();
		}
		
		
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	ORG_CD, "); 
		buf.append("\n 	ORG_NM ");
		buf.append("\n from DAS.ERP_COM_BAS_TBL ");
		buf.append("\n where UPPER(ORG_TYP) = ? ");
		buf.append("\n 	and STOP_FLAG = ? ");
		if(!DASBusinessConstants.OrganCode.COMPANY_CODE.equals(organCode))
		{
			buf.append("\n 	and HSEG_CD = ?	 ");
		}
		buf.append("\n order by ORG_CD	");
		
		
		
		HashMap resultMap = new HashMap();
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			
//			stmt = LoggableStatement.getInstance(con, buf.toString());
			
			stmt = con.prepareStatement(buf.toString());
			
			int index = 0;
			stmt.setString(++index, organCode.toUpperCase());
			stmt.setString(++index, DASBusinessConstants.YesNo.NO);
			if(!DASBusinessConstants.OrganCode.COMPANY_CODE.equals(organCode))
			{
				stmt.setString(++index, code);
			}
			
			
			rs = stmt.executeQuery();
			
			int indexCount = 0;
			while(rs.next())
			{
				resultMap.put( rs.getString("ORG_CD") , rs.getString("ORG_NM") );
				indexCount++;
			}
			
			return resultMap;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			
			return resultMap;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			return resultMap;
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}
	
	/**
	 * 보증인 ID에 해당하는 대리인 목록을 조회한다.
	 * @param userId 보증인 ID
	 * @return 
	 */
	public static HashMap getOutUserList(String userId)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	usr.OUT_USER_ID, ");
		buf.append("\n 	usr.OUT_USER_NM ");
		buf.append("\n from DAS.ERP_AGNTDTL_TBL dt, DAS.ERP_AGNTMST_TBL ms, DAS.OUTSIDER_INFO_TBL usr ");
		buf.append("\n where dt.AGNT = ms.AGNT ");
		buf.append("\n 	and dt.AGNT_SEQ = ms.AGNT_SEQ ");
		buf.append("\n 	and RTRIM(REPLACE(ms.PER_REG_NO, '-', '')) = usr.PER_REG_NO ");
		buf.append("\n 	and dt.EMP_NO = ? ");
		buf.append("\n group by usr.OUT_USER_ID, OUT_USER_NM  ");		
		
		
		HashMap resultMap = new HashMap();
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			
			stmt = LoggableStatement.getInstance(con, buf.toString());
			
//			stmt = con.prepareStatement(buf.toString());
			
			int index = 0;
			stmt.setString(++index, userId);
			
			rs = stmt.executeQuery();
			
			int indexCount = 0;
			while(rs.next())
			{
				resultMap.put( rs.getString("OUT_USER_ID") , rs.getString("OUT_USER_NM") );
				indexCount++;
			}
			
			return resultMap;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			
			return resultMap;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			return resultMap;
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}

	/**
	 * 보증인 ID에 해당하는 대리인 목록을 조회한다.
	 * @param userId 보증인 ID
	 * @return 
	 */
	public static HashMap getOutUserList(String userId, String endCheck, String nowDate)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	usr.OUT_USER_ID, ");
		buf.append("\n 	usr.OUT_USER_NM ");
//		buf.append("\n from DAS.ERP_AGNTDTL_TBL dt, DAS.ERP_AGNTMST_TBL ms, DAS.OUTSIDER_INFO_TBL usr ");
//		buf.append("\n where dt.AGNT = ms.AGNT ");
//		buf.append("\n 	and dt.AGNT_SEQ = ms.AGNT_SEQ ");
//		buf.append("\n 	and RTRIM(REPLACE(ms.PER_REG_NO, '-', '')) = usr.PER_REG_NO ");
//		buf.append("\n 	and dt.EMP_NO = ? ");
		buf.append("\n from DAS.OUTSIDER_INFO_TBL usr ");
		buf.append("\n where usr.GAURANTOR_ID = ? ");
		if(endCheck.equals("1")) {
			buf.append("\n 	and usr.VLDDT_END >= '" + nowDate + "' ");
		} else if(endCheck.equals("2")) {
			buf.append("\n 	and usr.VLDDT_END < '" + nowDate + "' ");
		}
//		buf.append("\n group by usr.OUT_USER_ID, OUT_USER_NM  ");		
		buf.append("\n WITH UR	 ");
		
		
		HashMap resultMap = new HashMap();
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			
			stmt = LoggableStatement.getInstance(con, buf.toString());
			
//			stmt = con.prepareStatement(buf.toString());
			
			int index = 0;
			stmt.setString(++index, userId);
			
			rs = stmt.executeQuery();
			
			int indexCount = 0;
			while(rs.next())
			{
				resultMap.put( rs.getString("OUT_USER_ID") , rs.getString("OUT_USER_NM") );
				indexCount++;
			}
			
			return resultMap;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			
			return resultMap;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			return resultMap;
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}

	/**
	 * 대리인에 대한 해당하는 보증인 목록을 조회한다.
	 * @param perRegNo 대리인 주민번호
	 * @return 
	 */
	public static List getGuarantorList(String perRegNo)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	usr.USER_NO, ");
		buf.append("\n 	usr.USER_NM, ");
		buf.append("\n 	usr.DEPT_NM ");
		buf.append("\n from DAS.ERP_AGNTMST_TBL agn, DAS.ERP_AGNTDTL_TBL mp, DAS.ERP_COM_USER_TBL usr ");
		buf.append("\n where agn.AGNT = mp.AGNT ");
		buf.append("\n 	and agn.AGNT_SEQ = mp.AGNT_SEQ ");
		buf.append("\n 	and mp.EMP_NO = usr.USER_ID ");
		buf.append("\n 	and RTRIM(REPLACE(agn.PER_REG_NO, '-', '')) = ? ");
		buf.append("\n 	and agn.PD_END_DD >= ? ");
		buf.append("\n group by usr.USER_NO, usr.USER_NM, usr.DEPT_NM	 ");
		buf.append("\n order by USER_NM	 ");
		buf.append("\n WITH UR	 ");
		
				
		HashMap resultMap = new HashMap();
		List resultList = new ArrayList();
		
		
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			
			stmt = LoggableStatement.getInstance(con, buf.toString());
			
//			stmt = con.prepareStatement(buf.toString());
			
			int index = 0;
			stmt.setString(++index, perRegNo);
			stmt.setString(++index, CalendarUtil.getToday());
			
			rs = stmt.executeQuery();
			
			//int indexCount = 0;
			
			while(rs.next())
			{
				DASCommonDO item = new DASCommonDO();
				
				item.setUserNo(rs.getString("USER_NO")); 
				item.setName(rs.getString("USER_NM"));
				item.setDeptNm(rs.getString("DEPT_NM"));
				
				resultList.add(item);
				/*
				resultMap.put( rs.getString("USER_NO") , rs.getString("USER_NM") );
				indexCount++;
				*/
			}
			
			//return resultMap;
			return resultList;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			
			return resultList;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			return resultList;
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}
	
	/**
	 * 장비 목록을 조회한다.
	 * @return  
	 */
	public static ArrayList getEqNmList()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT DAS_EQ_ID, DAS_EQ_NM ");
		buf.append("\n FROM DAS.DAS_EQUIPMENT_TBL ");
		buf.append("\n WHERE USE_YN = 'Y' ");
		buf.append("\n GROUP BY DAS_EQ_ID, DAS_EQ_NM ");
		buf.append("\n ORDER BY DAS_EQ_ID ASC");
		buf.append("\n WITH UR	 ");
		
		ArrayList subMap = new ArrayList();
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			
			stmt = LoggableStatement.getInstance(con, buf.toString());
			
			//stmt = con.prepareStatement(buf.toString());
			
			rs = stmt.executeQuery();
			
			//StringBuffer tag = null;
			
			//List subList = null;
			
			while(rs.next())
			{
				subMap.add(rs.getString("DAS_EQ_ID"));
				subMap.add(rs.getString("DAS_EQ_NM"));
			}
			
			if (logger.isDebugEnabled()) 
		    {
			logger.debug("[subMap size]" + subMap.size());
		    }
			return subMap;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			
			return subMap;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			return subMap;
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}
	
	
	/**
	 * 프로그램 코드를 가져온다.
	 * @return  
	 */
	public static String getPgm_cd()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT MAX(PGM_CD) as PGM_CD ");
		buf.append("\n FROM DAS.PGM_INFO_TBL ");
		buf.append("\n WHERE SUBSTR(PGM_CD, 1, 2) = 'ZZ' ");
		buf.append("\n WITH UR	 ");

		String pgm_cd = "";
		int pgm_cd_value = 0;
		String pgm_cd_result = "ZZ";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			
			stmt = LoggableStatement.getInstance(con, buf.toString());
			
			//stmt = con.prepareStatement(buf.toString());
			
			rs = stmt.executeQuery();
			
			//StringBuffer tag = null;
			
			//List subList = null;
			
			if(rs.next())
			{
				pgm_cd = rs.getString("PGM_CD");
			}
			
			if (pgm_cd != null) {
				pgm_cd_value = Integer.parseInt(pgm_cd.substring(2));
			}
			
			pgm_cd_value = pgm_cd_value + 1;
			
			int length = String.valueOf(pgm_cd_value).length();
			
			for( int i=0; i<6-length; i++) {
				pgm_cd_result = pgm_cd_result + "0";
			}

			pgm_cd_result = pgm_cd_result + pgm_cd_value;
			
			if (logger.isDebugEnabled()) 
		    {
			logger.debug("[subMap size]" + pgm_cd);
		    }
			return pgm_cd_result;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			
			return pgm_cd_result;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			
	        if (logger.isDebugEnabled()) 
	        {
	                logger.debug("[NamingException]" + e);
	        }

			return pgm_cd_result;
		}
		finally
		{
			try { 	if (rs != null)  rs.close();	} catch (SQLException e) {}
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}
	/**
	 * 버튼동작을 인식하고 값을 반환한다
	 * @return  
	 * @throws Exception 
	 */
	public static boolean getButtonUse(String button_Nm, DASCommonDO commonDO) throws Exception {
		boolean result = false;
		
		try {
			EmployeeRoleBusinessProcessor employeeRoleBusinessProcessor = new EmployeeRoleBusinessProcessor();
			String authScreen = employeeRoleBusinessProcessor.getAuthScreen(button_Nm, commonDO) ;
			
			if( authScreen.equals("Y") ){
				result = true;
			}
		} catch (DASException e){
			e.printStackTrace();
		}
		
		return result;
 	}
}
