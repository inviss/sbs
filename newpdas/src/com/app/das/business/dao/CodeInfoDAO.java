package com.app.das.business.dao;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.statement.CodeInfoStatement;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.AutoArchiveDO;
import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.JanreInfoDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.util.CalendarUtil;
import com.app.das.util.DBService;

/**
 * 공통코드에 대한 Databse 관련 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class CodeInfoDAO extends AbstractDAO 
{
	private static CodeInfoDAO instance;

	private Logger logger = Logger.getLogger(CodeInfoDAO.class);

	/**
	 * A private constructor
	 *
	 */
	private CodeInfoDAO() 
	{
	}

	/**
	 * 초기화 함수.
	 * 
	 */
	public static synchronized CodeInfoDAO getInstance() 
	{
		if (instance == null) 
		{
			instance = new CodeInfoDAO();
		}
		return instance;
	}

	/**
	 * 코드 테이블에서 구분코드에 해당하는 코드 List를 조회한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectCodeList(CodeDO codeDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append(CodeInfoStatement.selectCodeListQuery(codeDO));
		buf.append(" WITH UR	");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				CodeDO item = new CodeDO();
				item.setSeq(   rs.getInt("rownum"));
				item.setClfCd(		rs.getString("CLF_CD"));
				item.setSclCd(		rs.getString("SCL_CD"));
				item.setClfNm(		rs.getString("CLF_NM"));
				item.setDesc(		rs.getString("DESC"));
				item.setMainGubun(		rs.getString("RMK_1"));
				item.setRmk2(		rs.getString("RMK_2"));
				item.setRegDt(		rs.getString("REG_DT"));
				item.setRegrId(		rs.getString("REGRID"));
				item.setModDt(		rs.getString("MOD_DT"));
				item.setModrId(	rs.getString("MODRID"));
				item.setUseYn(		rs.getString("USE_YN"));
				item.setGubun(rs.getString("GUBUN"));

				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}




	/**
	 * 코드 테이블에서 구분코드에 해당하는 코드 List를 조회한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectCode(String codeDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append(CodeInfoStatement.selectCodeQuery(codeDO));
		buf.append(" WITH UR	");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				CodeDO item = new CodeDO();

				item.setSclCd(		rs.getString("SCL_CD"));			
				item.setDesc(		rs.getString("DESC"));

				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}



	/**
	 * 특정 코드에 대한 정보를 조회한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param code 코드테이블의 구분상세코드
	 * @param commonDO 공통정보
	 * @return CodeDO 특정 코드정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public CodeDO selectCodeInfo(CodeDO codeDO) throws Exception 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	CLF_CD, "); 
		buf.append("\n 	SCL_CD, "); 
		buf.append("\n 	CLF_NM, ");  
		buf.append("\n 	DESC, "); 
		buf.append("\n 	RMK_1, "); 
		buf.append("\n 	RMK_2, "); 
		buf.append("\n 	REG_DT, ");
		buf.append("\n 	REGRID, ");
		buf.append("\n 	MOD_DT, ");
		buf.append("\n 	MODRID, ");
		buf.append("\n 	USE_YN, ");
		buf.append("\n 	GUBUN ");
		buf.append("\n from DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = 'P018'	 ");			
		buf.append("\n    and SCL_CD = ? ");		

		buf.append("\n    and GUBUN = ?	 ");

		buf.append("\n WITH UR	 ");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, codeDO.getSclCd());
			stmt.setString(++index, codeDO.getGubun());

			rs = stmt.executeQuery();


			CodeDO item = new CodeDO();
			if(rs.next())
			{
				item.setClfCd(		rs.getString("CLF_CD"));
				item.setSclCd(		rs.getString("SCL_CD"));
				item.setClfNm(		rs.getString("CLF_NM"));
				item.setDesc(		rs.getString("DESC"));
				item.setRmk1(		rs.getString("RMK_1"));
				item.setRmk2(		rs.getString("RMK_2"));
				item.setRegDt(		rs.getString("REG_DT"));
				item.setRegrId(		rs.getString("REGRID"));
				item.setModDt(		rs.getString("MOD_DT"));
				item.setModrId(	rs.getString("MODRID"));
				item.setUseYn(		rs.getString("USE_YN"));
				item.setGubun(rs.getString("GUBUN"));

			}
			return item;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 코드 테이블에 코드 정보를 등록한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertCodeInfo(CodeDO codeDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.CODE_TBL(  ");
		buf.append("\n 	CLF_CD,  ");
		buf.append("\n 	SCL_CD,  ");
		buf.append("\n 	CLF_NM,  ");
		buf.append("\n 	DESC, ");
		buf.append("\n 	RMK_1, ");
		buf.append("\n 	RMK_2, ");
		buf.append("\n 	REG_DT, ");
		buf.append("\n 	REGRID, ");
		buf.append("\n 	MOD_DT, ");
		buf.append("\n 	MODRID, ");
		buf.append("\n 	GUBUN, ");
		buf.append("\n 	USE_YN  ");
		buf.append("\n )    ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)	");		
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			stmt.setString(++index, codeDO.getClfCd());
			stmt.setString(++index, codeDO.getSclCd());
			stmt.setString(++index, codeDO.getClfNm());
			stmt.setString(++index, codeDO.getDesc());
			stmt.setString(++index, "");
			stmt.setString(++index, codeDO.getRmk2());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, codeDO.getGubun());
			stmt.setString(++index, DASBusinessConstants.YesNo.YES);

			int iTmp = stmt.executeUpdate();
			return iTmp;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;

		} 

		finally
		{
			release(null, stmt, con);
		}

	}
	/**
	 * 주제영상 & 사용제한 등급 코드를 등록한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertCodeInfoGubun(CodeDO codeDO ) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.CODE_TBL(  ");
		buf.append("\n 	CLF_CD,  ");
		buf.append("\n 	SCL_CD,  ");
		buf.append("\n 	CLF_NM,  ");
		buf.append("\n 	DESC, ");
		buf.append("\n 	RMK_1, ");
		buf.append("\n 	RMK_2, ");
		buf.append("\n 	REG_DT, ");
		buf.append("\n 	REGRID, ");
		buf.append("\n 	MOD_DT, ");
		buf.append("\n 	MODRID, ");
		buf.append("\n 	USE_YN,  ");
		buf.append("\n 	GUBUN  ");
		buf.append("\n )    ");
		buf.append("\n VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)	");		
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######insertCodeInfoGubun######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			String code = selectCodeNum(codeDO.getGubun());
			stmt.setString(++index, codeDO.getClfCd());
			stmt.setString(++index, code);
			stmt.setString(++index, codeDO.getClfNm());
			stmt.setString(++index, codeDO.getDesc());
			if(codeDO.getGubun().equals("L")){
				stmt.setString(++index, " ");
			}else if(codeDO.getGubun().equals("S")){

				stmt.setString(++index, codeDO.getRmk1());
			}
			stmt.setString(++index, " ");
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, DASBusinessConstants.YesNo.YES);
			stmt.setString(++index, codeDO.getGubun());
			int iTmp = stmt.executeUpdate();
			return iTmp;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 코드 테이블에 코드 정보를 수정한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateCodeInfo(CodeDO codeDO ) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.CODE_TBL set ");
		buf.append("\n 	CLF_NM = ?,  ");
		buf.append("\n 	DESC = ?,  ");
		buf.append("\n 	RMK_1 = ?,  ");
		buf.append("\n 	RMK_2 = ?,  ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MODRID = ?, ");
		buf.append("\n 	USE_YN = ? ");
		buf.append("\n where CLF_CD = ?  ");
		buf.append("\n 	and SCL_CD = ?  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			stmt.setString(++index, codeDO.getClfNm());
			stmt.setString(++index, codeDO.getDesc());
			if(codeDO.getGubun().equals("S")){
				stmt.setString(++index, codeDO.getRmk1());
			}else if(codeDO.getGubun().equals("L")){
				stmt.setString(++index, "");
			}else {
				stmt.setString(++index, "");
			}
			stmt.setString(++index, "");
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, codeDO.getUseYn());
			stmt.setString(++index, codeDO.getClfCd());
			stmt.setString(++index, codeDO.getSclCd());

			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}	
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 코드 테이블에 코드 정보를 삭제한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int deleteCodeInfo(CodeDO codeDO ) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from  DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = ?  ");
		buf.append("\n 	and SCL_CD = ?  ");
		buf.append("\n 	with UR  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, codeDO.getClfCd());
			stmt.setString(++index, codeDO.getSclCd());

			int deleteCount = stmt.executeUpdate();

			if(deleteCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_CODE_INFO, "해당 코드값이 존재하지 않습니다.");
				throw exception;
			}
			return deleteCount;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;

		} 
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 장르 정보를 목록조회 한다.
	 * @param commonDO 공통정보
	 * @return List JanreInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectjanreList(String mainKey) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	code.CLF_CD, "); 
		buf.append("\n 	code.SCL_CD, "); 
		buf.append("\n 	code.CLF_NM, "); 
		buf.append("\n 	code.DESC,  ");
		buf.append("\n 	code.RMK_1,  ");
		buf.append("\n 	code.RMK_2,  ");
		buf.append("\n 	code.USE_YN, ");
		buf.append("\n 	(select USER_NM from DAS.ERP_COM_USER_TBL where USER_ID = code.RMK_2) AS USER_NM, ");
		buf.append("\n 	(select DEPT_NM from DAS.ERP_COM_USER_TBL where USER_ID = code.RMK_2) AS DEPT_NM ");
		buf.append("\n from DAS.CODE_TBL code ");
		buf.append("\n where code.CLF_CD = ? ");		
		buf.append("\n order by code.RMK_1 ");	
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectjanreList######## con : " + con);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, mainKey);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				JanreInfoDO item = new JanreInfoDO();
				item.setClfCd(		rs.getString("CLF_CD"));
				item.setSclCd(		rs.getString("SCL_CD"));
				item.setClfNm(		rs.getString("CLF_NM"));
				item.setDesc(		rs.getString("DESC"));
				item.setRmk1(		rs.getString("RMK_1"));
				item.setRmk2(		rs.getString("RMK_2"));
				item.setUseYn(		rs.getString("USE_YN"));
				item.setUserNm(	rs.getString("USER_NM")); 
				item.setDeptNm(	rs.getString("DEPT_NM"));

				resultList.add(item);
			}


			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	/**
	 * 특정 코드에 대한 정보를 조회한다.
	 * @return String 수집처 코드 증가값....
	 * @throws Exception 
	 */
	public String selectMaxCode() throws Exception
	{
		String result = "";

		String scl_cd = "";
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MAX(SCL_CD) as SCL_CD "); 
		buf.append("\n from DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = 'P009' ");
		buf.append("\n WITH UR	 ");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMaxCode######## con : " + con);

			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			if(rs.next())
			{
				scl_cd = rs.getString("SCL_CD");
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_CODE_INFO, "해당 코드값이 존재하지 않습니다.");
				throw exception;

			}

			result = "" + (Integer.parseInt(scl_cd) + 1);

			for (int i = result.length(); i<3; i++) {
				result = "0" + result;
			}

			return result;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * PDS 시스템 구축에 따른 미디어ID 식별체계에 따라서 DAS의 미디어 ID를 발급
	 * 'SYYYYMMDDGXXXXX'
	 * 'S':생성시스템구분(D:DAS(default))
	 * 'YYYYMMDD':생성일자
	 * 'G':미디어 구분(V-video 파일)
	 * 'XXXXX':seq 번호(5자리: 매일 초기화)
	 * @return DAS 비디오 미디어 ID
	 * @throws Exception 
	 */
	public String getMediaId() throws Exception{
		Connection con = null;
		CallableStatement stmt = null;
		String mediaId ="";
		StringBuffer buf = new StringBuffer();
		buf.append(" CALL MEDIA_ID(?)");
		try {
			con = DBService.getInstance().getConnection();
			stmt = con.prepareCall(buf.toString());

			stmt.registerOutParameter(1, Types.VARCHAR);

			stmt.execute();
			mediaId = stmt.getString(1);

			return mediaId;
		} catch (Exception e) {
			throw e;
		} finally {
			release(null, stmt, con);
		}
	}

	public String getMediaId(Connection con) throws Exception{
		CallableStatement stmt = null;
		String mediaId ="";
		StringBuffer buf = new StringBuffer();
		buf.append(" CALL MEDIA_ID(?)");
		try {
			if(con == null)
				con = DBService.getInstance().getConnection();

			stmt = con.prepareCall(buf.toString());

			stmt.registerOutParameter(1, Types.VARCHAR);

			stmt.execute();
			mediaId = stmt.getString(1);

			return mediaId;
		} catch (Exception e) {
			throw e;
		} finally {
			release(null, stmt, null);
		}
	}


	/**
	 * PDS 시스템 구축에 따른 미디어ID 식별체계에 따라서 DAS의 미디어 ID를 발급
	 * 'SYYYYMMDDGXXXXX'
	 * 'S':생성시스템구분(D:DAS(default))
	 * 'YYYYMMDD':생성일자
	 * 'G':미디어 구분(V-video 파일)
	 * 'XXXXX':seq 번호(5자리: 매일 초기화)
	 * @return DAS 비디오 미디어 ID
	 * @throws Exception 
	 */
	public String getMediaId(String reg_dt) throws Exception{
		Connection con = null;
		CallableStatement stmt = null;
		String mediaId ="";
		StringBuffer buf = new StringBuffer();
		buf.append(" CALL MEDIA_ID_MIG(?,?)");
		try {
			con = DBService.getInstance().getConnection();
			stmt = con.prepareCall(buf.toString());

			stmt.setString(1, reg_dt);
			stmt.registerOutParameter(2, Types.VARCHAR);

			stmt.execute();

			mediaId = stmt.getString(2);

			return mediaId;
		} catch (Exception e) {
			throw e;
		} finally {
			release(null, stmt, con);
		}
	}

	/**
	 * 장르 정보를 목록조회 한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getJanrCodeList11(CodeDO condition) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(CodeInfoStatement.selecJanrList11(condition));

		buf.append(" WITH UR	 ");


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//int index = 0;
			rs = stmt.executeQuery();
			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CodeDO item = new CodeDO();

				item.setSeq(		rs.getInt("rownum"));
				item.setSclCd(				rs.getString("SCL_CD"));
				item.setDesc(rs.getString("DESC"));
				item.setUseYn(rs.getString("USE_YN"));

				resultList.add(item);
			}

			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 장르코드 관리를 조회한다.(다중조회)
	 * @param discardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getJanrCodeList(CodeDO condition) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(CodeInfoStatement.selecJanrList(condition));

		buf.append(" WITH UR	 ");


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());
			//int index = 0;
			rs = stmt.executeQuery();
			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CodeDO item = new CodeDO();

				item.setBig_code(rs.getString("CTGR_L_CD"));
				item.setBig_desc(rs.getString("CTGR_L_DESC"));
				item.setBig_nm(rs.getString("CTGR_L_NM"));
				item.setBig_use_yn(rs.getString("CTGR_L_USE_YN"));
				item.setMid_code(rs.getString("CTGR_M_CD"));
				item.setMid_desc(rs.getString("CTGR_M_DESC"));
				item.setMid_nm(rs.getString("CTGR_M_NM"));
				item.setMid_use_yn(rs.getString("CTGR_M_USE_YN"));
				item.setSml_code(rs.getString("CTGR_S_CD"));
				item.setSml_desc(rs.getString("CTGR_S_DESC"));
				item.setSml_nm(rs.getString("CTGR_S_NM"));
				item.setSml_use_yn(rs.getString("CTGR_S_USE_YN"));



				resultList.add(item);
			}

			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	/**
	 * 장르코드 정보를 등록 한다.
	 * @param roleDO 부서 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int insertJanrCode(CodeDO roleDO) throws Exception
	{


		StringBuffer buf = new StringBuffer();

		buf.append("\n insert into DAS.CODE_TBL( ");	
		buf.append("\n 	SCL_CD,  ");		
		buf.append("\n 	DESC, ");
		buf.append("\n 	USE_YN ");
		buf.append("\n values  ");
		buf.append("\n ( ? , ?,?)  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			//int index = 0;

		} catch(Exception e) {
			con.rollback();
			throw e;
		}finally{
			try {
				con.setAutoCommit(true);
				release(null, stmt, con);
			} catch (SQLException e) {}
		}
		return 0;

	}

	/**
	 * 대분류코드를 생성 한다.
	 * @param roleDO 부서 정보가 포함되어 있는 DataObject
	 * @param 
	 */
	public int creatBcode() throws DASException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT MAX(SCL_CD) as SCL_CD ");
		buf.append("\n FROM DAS.CODE_TBL ");
		buf.append("\n WHERE CLF_CD  = 'P002' ");
		buf.append("\n WITH UR	 ");

		String scl_cd = "";
		int scl_cd_value = 0;
		//String scl_cd_val="";
		String scl_cd_result = "";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			while(rs.next()) {
				CodeDO item = new CodeDO();

				item.setSclCd(rs.getString("SCL_CD"));
				if (scl_cd != null) {
					try{
						if(!item.getSclCd().equals("900")){
							scl_cd_value = Integer.parseInt(item.getSclCd());
							scl_cd_value = scl_cd_value + 100;
							scl_cd_result = scl_cd_result + scl_cd_value;
						}
					}catch (Exception e){
						return Integer.parseInt(scl_cd_result);
					}
				}
			}

			return Integer.parseInt(scl_cd_result);
		} catch (Exception e) {
			logger.error(buf.toString());

			return Integer.parseInt(scl_cd_result);
		} finally {
			release(rs, stmt, con);
		}
	}


	/**
	 * 중분류코드를 생성 한다.
	 * @param roleDO 부서 정보가 포함되어 있는 DataObject
	 * @param 
	 */
	public String creatMcode(String Mcode) throws DASException
	{
		int Mcode1 = Integer.parseInt(Mcode);	
		int Mcode2 = Integer.parseInt(Mcode) + 100;
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT MAX(SCL_CD) as SCL_CD ");
		buf.append("\n FROM DAS.CODE_TBL ");
		buf.append("\n WHERE CLF_CD  = 'P003' ");
		buf.append("\n and SCL_CD  >= '"+Mcode1+"' ");
		buf.append("\n and SCL_CD  < '"+Mcode2+"' ");
		buf.append("\n WITH UR	 ");

		//String scl_cd = "";

		String scl_cd_result = "";
		String scl_cd_value1[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String scl_cd_value2[] = {"1","2","3","4","5","6","7","8","9"};
		Mcode.substring(0, 2);
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######creatMcode######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			try{
				while(rs.next())
				{
					CodeDO item = new CodeDO();
					item.setSclCd(				rs.getString("SCL_CD"));

					if (rs.getString("SCL_CD")!="") {
						for(int i=0 ; i<scl_cd_value2.length; i++){
							if(item.getSclCd().substring(1,2).equals("9")){
								scl_cd_result = Mcode.substring(0, 1) + scl_cd_value1[0]+"0";;
								i++;
							}else if(item.getSclCd().substring(1,2).equals(scl_cd_value2[i])){
								scl_cd_result = Mcode.substring(0, 1) + scl_cd_value2[i+1]+"0";
								return scl_cd_result;
							}
						}

						for(int i=0 ; i<scl_cd_value1.length; i++){
							if(item.getSclCd().substring(1,2).equals(scl_cd_value1[i])){
								scl_cd_result = Mcode.substring(0, 1) + scl_cd_value1[i+1]+"0";;
								return scl_cd_result;
							}
						}
					}
				}
			}catch(Exception e) {
				scl_cd_result= Mcode.substring(0, 1)+"10";
				return scl_cd_result;
			}
			return scl_cd_result;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			return scl_cd_result;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}


	/**
	 * 소분류코드를 생성 한다.
	 * @param roleDO 부서 정보가 포함되어 있는 DataObject
	 * @param 
	 */
	public String creatScode(String Scode) throws DASException
	{
		String scode = Scode.substring(0, 2);

		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT MAX(SCL_CD) as SCL_CD ");
		buf.append("\n FROM DAS.CODE_TBL ");
		buf.append("\n WHERE CLF_CD  = 'P004' ");
		buf.append("\n and SCL_CD  >= '"+Scode+"' ");
		buf.append("\n and SCL_CD  <= '"+scode+"Z"+"' ");
		buf.append("\n WITH UR	 ");

		String scl_cd_value[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String scl_cd_value2[] = {"1","2","3","4","5","6","7","8","9"};
		String scl_cd_result = "";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			try{
				while(rs.next())
				{

					CodeDO item = new CodeDO();

					item.setSclCd(				rs.getString("SCL_CD"));

					if (rs.getString("SCL_CD") != "") {
						for(int i=0 ; i<=scl_cd_value2.length -1; i++){
							if(item.getSclCd().substring(2).equals(scl_cd_value2[i])){
								if(!item.getSclCd().substring(2).equals("9")){
									scl_cd_result = scode + scl_cd_value2[i+1];
									return scl_cd_result;
								}
							}
						}

						for(int i=0 ; i<=scl_cd_value.length; i++){

							if(item.getSclCd().substring(2).equals(scl_cd_value[i])||item.getSclCd().substring(2).equals("9")){
								if(item.getSclCd().substring(2).equals("9")){
									scl_cd_result = scode + scl_cd_value[i];
								}else{
									scl_cd_result = scode + scl_cd_value[i+1];
								}

								return scl_cd_result;
							}


						}
					}

				}
			}catch(Exception e) {

				scl_cd_result = scode + "1";
				return scl_cd_result;
			}
			return scl_cd_result;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			return scl_cd_result;
		}
		finally
		{
			release(rs, stmt, con);
		}


	}



	//	/**
	//	 * 대분류 코드를 생성한다.
	//	 * @return  
	//	 */
	/*
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

			stmt = con.prepareStatement(buf.toString());

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

	 */



	/**
	 * 대분류 코드를 등록한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertBcode(CodeDO codeDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.CODE_TBL(  ");
		buf.append("\n 	CLF_CD,  ");
		buf.append("\n 	SCL_CD,  ");
		buf.append("\n 	CLF_NM,  ");
		buf.append("\n 	DESC, ");
		buf.append("\n 	RMK_1, ");
		buf.append("\n 	RMK_2, ");
		buf.append("\n 	REG_DT, ");
		buf.append("\n 	REGRID, ");
		buf.append("\n 	MOD_DT, ");
		buf.append("\n 	MODRID, ");
		buf.append("\n 	USE_YN,  ");
		buf.append("\n 	GUBUN  ");
		buf.append("\n )    ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)	");		
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######insertBcode######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			stmt.setString(++index, "P002");
			stmt.setString(++index, String.valueOf(creatBcode()));
			stmt.setString(++index, "대분류코드");
			stmt.setString(++index, codeDO.getDesc());
			stmt.setString(++index, codeDO.getRmk1());
			stmt.setString(++index, codeDO.getRmk2());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, codeDO.getUseYn());
			stmt.setString(++index, codeDO.getGubun());

			int iTmp = stmt.executeUpdate();
			return iTmp;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}


	/**
	 * 중분류 코드를 등록한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int insertMcode(CodeDO codeDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.CODE_TBL(  ");
		buf.append("\n 	CLF_CD,  ");
		buf.append("\n 	SCL_CD,  ");
		buf.append("\n 	CLF_NM,  ");
		buf.append("\n 	DESC, ");
		buf.append("\n 	RMK_1, ");
		buf.append("\n 	RMK_2, ");
		buf.append("\n 	REG_DT, ");
		buf.append("\n 	REGRID, ");
		buf.append("\n 	MOD_DT, ");
		buf.append("\n 	MODRID, ");
		buf.append("\n 	USE_YN,  ");
		buf.append("\n 	GUBUN  ");
		buf.append("\n )    ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)	");		
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			stmt.setString(++index, "P003");
			stmt.setString(++index, String.valueOf(creatMcode(codeDO.getSclCd())));
			stmt.setString(++index, "중분류코드");
			stmt.setString(++index, codeDO.getDesc());
			stmt.setString(++index, codeDO.getRmk1());
			stmt.setString(++index, codeDO.getRmk2());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, codeDO.getUseYn());
			stmt.setString(++index, codeDO.getGubun());

			int iTmp = stmt.executeUpdate();
			return iTmp;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}


	/**
	 * 소분류 코드를 등록한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int insertScode(CodeDO codeDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.CODE_TBL(  ");
		buf.append("\n 	CLF_CD,  ");
		buf.append("\n 	SCL_CD,  ");
		buf.append("\n 	CLF_NM,  ");
		buf.append("\n 	DESC, ");
		buf.append("\n 	RMK_1, ");
		buf.append("\n 	RMK_2, ");
		buf.append("\n 	REG_DT, ");
		buf.append("\n 	REGRID, ");
		buf.append("\n 	MOD_DT, ");
		buf.append("\n 	MODRID, ");
		buf.append("\n 	USE_YN,  ");
		buf.append("\n 	GUBUN  ");
		buf.append("\n )    ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)	");		
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			stmt.setString(++index, "P004");
			stmt.setString(++index, creatScode(codeDO.getSclCd()));
			stmt.setString(++index, "소분류코드");
			stmt.setString(++index, codeDO.getDesc());
			stmt.setString(++index, codeDO.getRmk1());
			stmt.setString(++index, codeDO.getRmk2());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, codeDO.getRegrId());
			stmt.setString(++index, codeDO.getUseYn());
			stmt.setString(++index, codeDO.getGubun());

			int iTmp = stmt.executeUpdate();
			return iTmp;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}


	/**
	 * 대분류를 수정한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateBcode(CodeDO codeDO ) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.CODE_TBL set ");

		buf.append("\n 	DESC = ?,  ");

		buf.append("\n 	MOD_DT = ?,  ");

		buf.append("\n 	USE_YN = ? ");
		buf.append("\n where CLF_CD = ?  ");
		buf.append("\n 	and SCL_CD = ?  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, codeDO.getDesc());

			stmt.setString(++index, toDateTime);

			stmt.setString(++index, codeDO.getUseYn());
			stmt.setString(++index, "P002");
			stmt.setString(++index, codeDO.getSclCd());

			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}	
		finally
		{
			release(null, stmt, con);
		}

	}


	/**
	 * 중분류를 수정한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateMcode(CodeDO codeDO ) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.CODE_TBL set ");
		buf.append("\n 	DESC = ?,  ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	USE_YN = ? ");
		buf.append("\n where CLF_CD = ?  ");
		buf.append("\n 	and SCL_CD = ?  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, codeDO.getDesc());

			stmt.setString(++index, toDateTime);

			stmt.setString(++index, codeDO.getUseYn());
			stmt.setString(++index, "P003");
			stmt.setString(++index, codeDO.getSclCd());

			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}	
		finally
		{
			release(null, stmt, con);
		}

	}



	/**
	 * 소분류를 수정한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateScode(CodeDO codeDO ) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.CODE_TBL set ");
		buf.append("\n 	DESC = ?,  ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	USE_YN = ? ");
		buf.append("\n where CLF_CD = ?  ");
		buf.append("\n 	and SCL_CD = ?  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, codeDO.getDesc());

			stmt.setString(++index, toDateTime);

			stmt.setString(++index, codeDO.getUseYn());
			stmt.setString(++index, "P004");
			stmt.setString(++index, codeDO.getSclCd());

			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}	
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 자동복본여부 를 목록조회 한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getAutoArchvieList(AutoArchiveDO condition) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append("\n select * FROM                                                      										\n");
		buf.append("\n (                                                                  												\n");
		buf.append(CodeInfoStatement.selecAutoArchvieList(condition, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append("\n ) AS temp                                                           											\n");
		buf.append("\n where temp.rownum >= ? and temp.rownum <=?                                            	\n");
		buf.append("\n WITH UR	 ");

		//Page에 따른 계산을 한다.
		int page = condition.getPage();
		if(page == 0)
		{
			page = 1;
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//총 조회 갯수를 구한다.
			int totalCount  = 0;



			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * DASBusinessConstants.PageRowCount.DISUSE_ROW_COUNT; 
			int startNum = endNum - (DASBusinessConstants.PageRowCount.DISUSE_ROW_COUNT  -1);

			int index = 0;

			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				AutoArchiveDO item = new AutoArchiveDO();

				item.setSeq(		rs.getInt("rownum"));
				item.setScl_cd(				rs.getString("scl_cd"));
				item.setDesc(rs.getString("desc"));
				item.setClf_cd(rs.getString("clf_cd"));
				item.setAuto_yn( rs.getString("auto_yn"));
				//2012.4.23 추가
				item.setCocd( rs.getString("COCD"));
				item.setConm( rs.getString("conm"));
				item.setChennel( rs.getString("CHENNEL"));
				item.setChennelnm( rs.getString("chennel_nm"));
				item.setArch_route( rs.getString("ARCH_ROUTE").trim());
				item.setArch_route_nm( rs.getString("ARCH_ROUTE_NM"));


				resultList.add(item);
			}

			int totalPageCount = totalCount / DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT  + (totalCount % DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1 : 0);

			//검색된 List를 셋팅한다.
			pageDO.setPageItems(resultList);
			//계산된 총 Page 수를 셋팅한다.
			pageDO.setTotalPageCount(totalPageCount);


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}




	/**
	 * 프로그램 복본관리를 검색후 없는 것을 삽입한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertAutoArchvie() throws Exception
	{
		//logger.debug(condition);
		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO AUTO_ARCHVIE_TBL   ");
		buf.append("\n  select CODE.CLF_CD, CODE.SCL_CD, 'N' AS AUTO_YN   ");
		buf.append("\n  FROM AUTO_ARCHVIE_TBL  AUTO  ");
		buf.append("\n RIGHT JOIN CODE_TBL CODE ON AUTO.CLF_CD=CODE.CLF_CD ");		
		buf.append("\n AND AUTO.SCL_CD=CODE.SCL_CD ");
		buf.append("\n WHERE AUTO.CLF_CD IS NULL AND AUTO.SCL_CD IS NULL AND CODE.CLF_CD='A001' ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());


			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
				logger.debug("[Inserted Count]" + updateCount);

			con.commit();
			return updateCount;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			throw e;
		}
		finally
		{
			try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			release(null, stmt, con);
		}
	}

	/**
	 * 자동 아카이브관리를 수정한다
	 * @param  autoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateAutoArchvie(AutoArchiveDO autoDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n UPDATE DAS.AUTO_ARCHVIE_TBL ");
		buf.append("\n SET  AUTO_YN  = ? ");
		buf.append("\n  WHERE SCL_CD  = ? ");
		buf.append("\n  AND COCD  = ? ");
		buf.append("\n  AND CHENNEL  = ? ");
		buf.append("\n  AND ARCH_ROUTE  = ? ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			stmt.setString(++index, autoDO.getAuto_yn());
			stmt.setString(++index, autoDO.getScl_cd());
			stmt.setString(++index, autoDO.getCocd());
			stmt.setString(++index, autoDO.getChennel());
			stmt.setString(++index, autoDO.getArch_route());
			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}	
		finally
		{
			release(null, stmt, con);
		}
	}





	/**
	 * 메인화면 ㄱㄴㄷㄹㄷ...
	 * @param clf_cd
	 * @param scl_cd
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectMainKeyList() throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(CodeInfoStatement.selectMainKeyList());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());
			//int index = 0;
			rs = stmt.executeQuery();
			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CodeDO item = new CodeDO();

				item.setClfCd(		rs.getString("CLF_CD"));
				item.setSclCd(		rs.getString("SCL_CD"));
				item.setClfNm(		rs.getString("CLF_NM"));
				item.setDesc(		rs.getString("DESC"));
				item.setMainGubun(		rs.getString("RMK_1"));


				resultList.add(item);

			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}



	/**
	 * 주제영상 & 사용등급코드 생성한다
	 * @return String 수집처 코드 증가값....
	 * @throws Exception 
	 */
	public String selectCodeNum(String gubun) throws Exception
	{
		String result = "";

		String scl_cd = "";
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MAX(SCL_CD) as SCL_CD "); 
		buf.append("\n from DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = 'P018' ");
		buf.append("\n WITH UR	 ");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			if(rs.next())
			{
				scl_cd = rs.getString("SCL_CD");
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_CODE_INFO, "해당 코드값이 존재하지 않습니다.");
				throw exception;

			}
			String cd =scl_cd.substring(1, 3);

			int scl= (Integer.parseInt(cd))+1;

			if(scl < 10){
				result = "00"+scl;
			}else {
				result =  "0"+scl;
			}


			return result;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	// 2012.4.18 das 확장 추가 개발 함수 
	/**
	 * 아카이브 경로를 조회한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getArchiveRoute(String xml ) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(CodeInfoStatement.selectArchrouteQuery());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());
			//int index = 0;
			rs = stmt.executeQuery();
			//int indexCount = 0;
			List resultList = new ArrayList();
			while(rs.next())
			{
				CodeDO item = new CodeDO();


				item.setSclCd(		rs.getString("SCL_CD"));
				item.setDesc(		rs.getString("DESC"));
				resultList.add(item);

			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	//2012.4.20 다스 확장
	/**
	 * 채널 코드 조회
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getChennelInfo(CodeDO codeDO) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(CodeInfoStatement.selectChennelListQuery(codeDO));

		buf.append(" WITH UR	");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());
			//int index = 0;
			rs = stmt.executeQuery();
			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CodeDO item = new CodeDO();

				item.setSclCd(		rs.getString("SCL_CD"));				
				item.setDesc(		rs.getString("DESC"));

				resultList.add(item);

			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 채널 구성을 위한 회사 정보를 조회한다
	 * 코드테이블의 구분코드에 의해 해당하는 코드값을 조회하여 리턴한다.
	 * @param codeDO 회사정보
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getCocdForChennel() throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(CodeInfoStatement.getCocdForChennel());

		buf.append(" WITH UR	");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());


			//int index = 0;

			rs = stmt.executeQuery();

			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CodeDO item = new CodeDO();

				item.setSclCd(		rs.getString("SCL_CD"));				
				item.setDesc(		rs.getString("DESC"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	/**
	 * 코드 생성한다
	 * @return String 수집처 코드 증가값....
	 * @throws Exception 
	 */
	public String selectNewCode(String gubun) throws Exception
	{
		String result = "";

		String scl_cd = "";
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MAX(SCL_CD) as SCL_CD "); 
		buf.append("\n from DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = '" +gubun+"'");
		buf.append("\n WITH UR	 ");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			if(rs.next())
			{
				scl_cd = rs.getString("SCL_CD");
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_CODE_INFO, "해당 코드값이 존재하지 않습니다.");
				throw exception;

			}
			String cd =scl_cd.substring(1, 3);
			int scl= (Integer.parseInt(cd))+1;

			if(scl < 10){
				result = "00"+scl;
			}else {
				result =  "0"+scl;
			}

			return result;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}




	/**
	 * 코드 테이블에서 구분코드에 해당하는 코드 List를 조회한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectCodeInfo2(CodeDO codeDO) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(CodeInfoStatement.selectCodeListQuery2(codeDO));

		buf.append(" WITH UR	");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());


			//int index = 0;

			rs = stmt.executeQuery();

			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CodeDO item = new CodeDO();
				item.setSeq(   rs.getInt("rownum"));
				item.setClfCd(		rs.getString("CLF_CD"));
				item.setSclCd(		rs.getString("SCL_CD"));
				item.setClfNm(		rs.getString("CLF_NM"));
				item.setDesc(		rs.getString("DESC"));
				item.setMainGubun(		rs.getString("RMK_1"));
				item.setRmk2(		rs.getString("RMK_2"));
				item.setRegDt(		rs.getString("REG_DT"));
				item.setRegrId(		rs.getString("REGRID"));
				item.setModDt(		rs.getString("MOD_DT"));
				item.setModrId(	rs.getString("MODRID"));
				item.setUseYn(		rs.getString("USE_YN"));
				item.setGubun(rs.getString("GUBUN"));

				resultList.add(item);

			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}




}
