package com.app.das.business.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.statement.CommunityStatement;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.BoardConditionDO;
import com.app.das.business.transfer.BoardDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.FileInfoDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.PreviewAttachDO;
import com.app.das.business.transfer.PreviewDO;
import com.app.das.util.CalendarUtil;
import com.app.das.util.DBService;

/**
 * 이용관리에 대한 Databse 관련 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class CommunityDAO extends AbstractDAO 
{
	private static CommunityDAO instance;

	private Logger logger = Logger.getLogger(CommunityDAO.class);
	/**
	 * A private constructor
	 *
	 */
	private CommunityDAO() 
	{
	}
	/**
	 *초기화
	 *
	 */
	public static synchronized CommunityDAO getInstance() 
	{
		if (instance == null) 
		{
			instance = new CommunityDAO();
		}
		return instance;
	}

	/**
	 * 게시판 목록 조회를 한다.
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 조회된 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectBoardList(BoardConditionDO conditionDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(CommunityStatement.selectBoardListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL, DASBusinessConstants.YesNo.NO));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append(" WITH UR	  ");

		//Page에 따른 계산을 한다.
		int page = conditionDO.getPage();
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
//			int totalCount  = 
//					getTotalCount(con, CommunityStatement.selectBoardListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT, DASBusinessConstants.YesNo.NO));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = conditionDO.getRowPerPage();
			if(rowPerPage == 0)
			{
				rowPerPage = DASBusinessConstants.PageRowCount.BOARD_ROW_COUNT;
			}

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				BoardDO item = new BoardDO();
				item.setBoardId( 				rs.getInt("BOARD_ID"));
				item.setBoardTypeCd(    	rs.getString("BOARD_TYPE_CD"));
				item.setAnswerYn(       	rs.getString("ANSWER_YN"));
				String SUBJ = rs.getString("SUBJ");
				SUBJ = SUBJ.replaceAll("&", "&amp;");
				SUBJ = SUBJ.replace("'", "&apos;");
				SUBJ = SUBJ.replaceAll("\"", "&quot;");
				SUBJ = SUBJ.replaceAll("<", "&lt;");
				SUBJ = SUBJ.replaceAll(">", "&gt;");
				item.setSubj(           			SUBJ);
				String CONT = rs.getString("CONT");
				CONT = CONT.replaceAll("&", "&amp;");
				CONT = CONT.replace("'", "&apos;");
				CONT = CONT.replaceAll("\"", "&quot;");
				CONT = CONT.replaceAll("<", "&lt;");
				CONT = CONT.replaceAll(">", "&gt;");
				item.setCont(           			CONT);
				item.setRegDt(          		rs.getString("REG_DT"));
				item.setModDt(          		rs.getString("MOD_DT"));
				item.setRegrid(         		rs.getString("REGRID"));
				item.setRegrNm(				rs.getString("USER_NM"));
				item.setModrid(         		rs.getString("MODRID"));
				item.setRdCount(        		rs.getString("RD_COUNT"));
				item.setMainId(         		rs.getInt("MAIN_ID"));
				item.setPopup_end_dd(        		rs.getString("POPUP_END_DD"));
				item.setPopup_start_dd(         		rs.getString("POPUP_START_DD"));
				int attachCount = rs.getInt("ATTACH_COUNT");
				String attachYn = DASBusinessConstants.YesNo.NO;
				//첨부파일수가 0보다 크면 첨부파일여부를 'Y'로 셋팅한다.
				if(attachCount > 0)
				{
					attachYn = DASBusinessConstants.YesNo.YES;
				}

				item.setAttachYn(    			attachYn);
				item.setSerialNo( 				rs.getInt("rownum"));

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
	 * 게시판의 읽은 수가 많은 것을 조회한다.
	 * @param conditionDO 조회조건을 포함하고 있는 DO
	 * @param commonDO 공통정보
	 * @return List BoardDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectBoardListForReadCount(BoardConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(CommunityStatement.selectBoardListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL, DASBusinessConstants.YesNo.YES));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <= ?                                        	\n");
		buf.append(" WITH UR	  ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectBoardListForReadCount######## con : " + con);
			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setInt(++index, 1);
			stmt.setInt(++index, 10);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				BoardDO item = new BoardDO();
				item.setBoardId( 				rs.getInt("BOARD_ID"));
				item.setBoardTypeCd(    	rs.getString("BOARD_TYPE_CD"));
				item.setAnswerYn(       	rs.getString("ANSWER_YN"));
				item.setSubj(           			rs.getString("SUBJ"));
				item.setCont(           			rs.getString("CONT"));
				item.setRegDt(          		rs.getString("REG_DT"));
				item.setModDt(          		rs.getString("MOD_DT"));
				item.setRegrid(         		rs.getString("REGRID"));
				item.setRegrNm(				rs.getString("USER_NM"));
				item.setModrid(         		rs.getString("MODRID"));
				item.setRdCount(        		rs.getString("RD_COUNT"));
				item.setMainId(         		rs.getInt("MAIN_ID"));
				int attachCount = rs.getInt("ATTACH_COUNT");
				String attachYn = DASBusinessConstants.YesNo.NO;
				if(attachCount > 0)
				{
					attachYn = DASBusinessConstants.YesNo.YES;
				}

				item.setAttachYn(    			attachYn);
				item.setSerialNo( 				rs.getInt("rownum"));

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
	 * 게시판 단일건 조회를 한다.
	 * @param mainId 게시판 ID
	 * @return BoardDO 조회한 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public  List selectBoardInfo(int mainId) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(CommunityStatement.selectBoardInfoQuery());

			int index = 0;
			stmt.setInt(++index, mainId);

			rs = stmt.executeQuery();
			//int indexCount = 0;
			List resultList = new ArrayList();

			if(rs.next())
			{
				BoardDO item = new BoardDO();
				item.setBoardId( 				rs.getInt("BOARD_ID"));
				item.setBoardTypeCd(    	rs.getString("BOARD_TYPE_CD"));
				item.setAnswerYn(       	rs.getString("POPUP_YN"));
				String SUBJ = rs.getString("SUBJ");
				SUBJ = SUBJ.replaceAll("&", "&amp;");
				SUBJ = SUBJ.replace("'", "&apos;");
				SUBJ = SUBJ.replaceAll("\"", "&quot;");
				SUBJ = SUBJ.replaceAll("<", "&lt;");
				SUBJ = SUBJ.replaceAll(">", "&gt;");
				item.setSubj(           			SUBJ);
				String CONT = rs.getString("CONT");
				CONT = CONT.replaceAll("&", "&amp;");
				CONT = CONT.replace("'", "&apos;");
				CONT = CONT.replaceAll("\"", "&quot;");
				CONT = CONT.replaceAll("<", "&lt;");
				CONT = CONT.replaceAll(">", "&gt;");
				item.setCont(           			CONT);
				item.setRegDt(          		rs.getString("REG_DT"));
				item.setRegrid(         		rs.getString("REGRID"));
				item.setUser_nm(				rs.getString("USER_NM"));
				item.setRdCount(        		rs.getString("RD_COUNT"));
				item.setMainId(         		rs.getInt("MAIN_ID"));
				item.setFile_nm(         		rs.getString("ATTACH_YN"));

				item.setPopup_start_dd(         		rs.getString("POPUP_START_DD").trim());
				item.setPopup_end_dd(         		rs.getString("POPUP_END_DD").trim());
 				resultList.add(item);


			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_BOARD_INFO, "해당 게시판 정보가 존재하지 않습니다.");
				throw exception;

			}
			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(mainId);

			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}



	/**
	 * 게시판 단일건 조회를 한다.
	 * @param conditionDO 조회정보
	 * @return BoardDO 조회한 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public  List selectBoardInfoList(BoardDO conditionDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(CommunityStatement.selectBoardInfoListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL, DASBusinessConstants.YesNo.NO));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append(" WITH UR	  ");

		//Page에 따른 계산을 한다.
		int page = conditionDO.getPage();
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
			//logger.debug("######selectBoardInfoList######## con : " + con);
			//총 조회 갯수를 구한다.
//			int totalCount  = 
//					getTotalCount(con, CommunityStatement.selectBoardInfoListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT, DASBusinessConstants.YesNo.NO));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = conditionDO.getRowPerPage();
			if(rowPerPage == 0)
			{
				rowPerPage = DASBusinessConstants.PageRowCount.NEW_BOARD_ROW_COUNT;
			}

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			//int indexCount = 0;

			List resultList = new ArrayList();

			while(rs.next())
			{
				BoardDO item = new BoardDO();
				item.setBoardId( 				rs.getInt("BOARD_ID"));
				item.setBoardTypeCd(    	rs.getString("BOARD_TYPE_CD"));
				item.setPopupyn(       	rs.getString("POPUP_YN"));

				String SUBJ = rs.getString("SUBJ");
				SUBJ = SUBJ.replaceAll("&", "&amp;");
				SUBJ = SUBJ.replace("'", "&apos;");
				SUBJ = SUBJ.replaceAll("\"", "&quot;");
				SUBJ = SUBJ.replaceAll("<", "&lt;");
				SUBJ = SUBJ.replaceAll(">", "&gt;");
				item.setSubj(           			SUBJ);
				String CONT = rs.getString("CONT");
				CONT = CONT.replaceAll("&", "&amp;");
				CONT = CONT.replace("'", "&apos;");
				CONT = CONT.replaceAll("\"", "&quot;");
				CONT = CONT.replaceAll("<", "&lt;");
				CONT = CONT.replaceAll(">", "&gt;");
				item.setCont(           			CONT);
				item.setRegDt(          		rs.getString("REG_DT"));

				item.setRegrid(         		rs.getString("REGRID"));
				item.setUser_nm(				rs.getString("USER_NM"));

				item.setRdCount(        		rs.getString("RD_COUNT"));
				item.setMainId(         		rs.getInt("MAIN_ID"));
				item.setFile_nm(         		rs.getString("ATTACH_YN"));

				item.setPopup_end_dd(         		rs.getString("POPUP_END_DD").trim());
				item.setPopup_start_dd(         		rs.getString("POPUP_START_DD").trim());

				item.setSerialNo( 				rs.getInt("rownum"));

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
	 * 해당 게시물의 읽은 횟수를 1 증가시킨다
	 * @param boardId 게시판 ID
	 * @throws Exception 
	 */
	public void updateReadCount(int boardId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.BOARD_TBL set ");
		buf.append("\n 	RD_COUNT = (RD_COUNT + 1) ");

		buf.append("\n where MAIN_ID = ? ");		

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			stmt.setInt(++index, boardId);

			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_BOARD_INFO, "해당 게시판 정보가 존재하지 않습니다.");
				throw exception;
			}
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
	 * 게시판 정보를 저장한다.
	 * @param boardDO 저장할 게시판 정보
	 * @param replyYn 덧글여부
	 * @throws Exception 
	 */
	public int insertBoardInfo(BoardDO boardDO, String replyYn) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.BOARD_TBL( ");
		buf.append("\n 	BOARD_ID, "); 
		buf.append("\n 	BOARD_TYPE_CD, "); 
		buf.append("\n 	SUBJ, "); 
		buf.append("\n 	CONT, "); 
		buf.append("\n 	REG_DT, "); 
		buf.append("\n 	REGRID, "); 
		buf.append("\n 	RD_COUNT, "); 
		buf.append("\n 	MAIN_ID, "); 
		buf.append("\n 	POPUP_YN ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?)  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);


			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			int boardSeq = Integer.parseInt(getNextSquence(con, DASBusinessConstants.SequenceName.BOARD_ID));

			stmt.setInt(++index, boardSeq);
			stmt.setString(++index, boardDO.getBoardTypeCd());
			stmt.setString(++index, boardDO.getSubj());
			stmt.setString(++index, boardDO.getCont());
			stmt.setString(++index, toDateTime);			
			stmt.setString(++index, boardDO.getRegrid());		
			stmt.setInt(++index, 0);
			stmt.setInt(++index, boardSeq);
			stmt.setString(++index, boardDO.getPopupyn());
			int iTmp = stmt.executeUpdate();

			boardDO.setBoardId(boardSeq);

			//파일정보를 등록한다.
			int seq = 0;
			for(Iterator i = boardDO.getFileInfoDOList().iterator(); i.hasNext();)
			{
				seq = seq + 1;

				insertFileInfo(con, (FileInfoDO)i.next(), boardSeq, seq);
			}

			con.commit();
			return iTmp;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}



	/**
	 * 게시판 정보(공지)를 저장한다.
	 * @param boardDO 저장할 게시판 정보
	 * @throws Exception 
	 */
	public int insertBoard(BoardDO boardDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.BOARD_TBL( ");
		buf.append("\n 	BOARD_ID, "); 
		buf.append("\n 	BOARD_TYPE_CD, "); 
		buf.append("\n 	SUBJ, "); 
		buf.append("\n 	CONT, "); 
		buf.append("\n 	REG_DT, "); 
		buf.append("\n 	REGRID, "); 
		buf.append("\n 	RD_COUNT, "); 
		buf.append("\n 	MAIN_ID, "); 
		buf.append("\n 	POPUP_YN ,");
		buf.append("\n 	POPUP_START_DD ,");
		buf.append("\n 	POPUP_END_DD ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?,? ,?)  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);


			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			int boardSeq = Integer.parseInt(getNextSquence(con, DASBusinessConstants.SequenceName.BOARD_ID));

			stmt.setInt(++index, boardSeq);
			stmt.setString(++index, boardDO.getBoardTypeCd());
			stmt.setString(++index, boardDO.getSubj());
			stmt.setString(++index, boardDO.getCont());
			stmt.setString(++index, toDateTime);			
			stmt.setString(++index, boardDO.getRegrid());		
			stmt.setInt(++index, 0);
			stmt.setInt(++index, boardSeq);
			stmt.setString(++index, boardDO.getPopupyn());
			stmt.setString(++index, boardDO.getPopup_start_dd());
			stmt.setString(++index, boardDO.getPopup_end_dd());
			stmt.executeUpdate();

			boardDO.setBoardId(boardSeq);

			//파일정보를 등록한다.
			//String[] File_nm = boardDO.getFile_nm().split(",");
			//String[] File_sz = boardDO.getFl_size().split(",");
			//String[] File_path = boardDO.getFile_path().split(",");


			//insertFile(con, boardDO.getFile_nm(),boardDO.getFile_size(),boardDO.getFile_path(), boardSeq, seq,boardDO.getRegrid());

			//다중 파일 등록 클라이언트 구현후 재구현 예정


			/*	if(boardDO.getFile_nm()!=null){
			for(int i=0; i<File_nm.length;i++){
				seq = seq + 1;

				insertFile(File_nm[i],boardDO.getFile_path(), boardSeq, seq,boardDO.getRegrid());
			}
			}
			 */
			con.commit();
			return boardSeq;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 게시판 정보를 수정한다.
	 * @param boardDO 수정할 게시판 정보
	 * @throws Exception 
	 */
	public int updateBoardInfo(BoardDO boardDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.BOARD_TBL set ");
		buf.append("\n 	SUBJ = ?, "); 
		buf.append("\n 	CONT = ?, "); 
		buf.append("\n 	MOD_DT = ?, "); 
		buf.append("\n 	MODRID = ? "); 
		buf.append("\n 	POPUP_YN = ? "); 
		buf.append("\n where main_id = ?  ");
		buf.append("\n and board_type_cd = ?  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, boardDO.getSubj());
			stmt.setString(++index, boardDO.getCont());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, boardDO.getRegrid());
			stmt.setInt(++index, boardDO.getMainId());
			stmt.setString(++index, boardDO.getBoardTypeCd());
			stmt.setString(++index, boardDO.getPopupyn());
			int iTmp  = stmt.executeUpdate();

			int maxSeq = selectFileInfoMaxSeq(boardDO.getBoardId()) + 1;

			for(Iterator i = boardDO.getFileInfoDOList().iterator(); i.hasNext();)
			{
				FileInfoDO fileInfoDO =  (FileInfoDO)i.next();
				if(fileInfoDO.getMothrDataId() == 0 && fileInfoDO.getSeq() == 0)
				{
					maxSeq = maxSeq +1;

					insertFileInfo(con, fileInfoDO, boardDO.getBoardId(), maxSeq);
				}
				else
				{
					updateFileInfo(con, fileInfoDO);
				}
			}

			con.commit();
			return iTmp;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}


	/**
	 * 게시판 대답 정보를 저장한다.
	 * @param boardDO 저장할 게시판 정보
	 * @throws Exception 
	 */
	public int updateBoard(BoardDO boardDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.BOARD_TBL set ");
		buf.append("\n 	SUBJ = ?, "); 
		buf.append("\n 	CONT = ?, "); 
		buf.append("\n 	MOD_DT = ?, "); 
		buf.append("\n 	MODRID = ?, "); 
		buf.append("\n 	POPUP_YN = ?, "); 
		buf.append("\n 	POPUP_START_DD = ? ,"); 
		buf.append("\n 	POPUP_END_DD = ? "); 
		buf.append("\n where main_id = ?  ");
		buf.append("\n and board_type_cd = ?  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, boardDO.getSubj());
			stmt.setString(++index, boardDO.getCont());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, boardDO.getRegrid());
			stmt.setString(++index, boardDO.getPopupyn());
			if( boardDO.getPopupyn().equals("Y")){
				stmt.setString(++index, boardDO.getPopup_start_dd());
				stmt.setString(++index, boardDO.getPopup_end_dd());
			}else if( boardDO.getPopupyn().equals("N")){
				stmt.setString(++index,"");
				stmt.setString(++index, "");
			}
			stmt.setInt(++index, boardDO.getMainId());


			stmt.setString(++index, boardDO.getBoardTypeCd());
			int iTmp  = stmt.executeUpdate();

			//int maxSeq = selectFileInfoMaxSeq(boardDO.getBoardId()) + 1;

			if(!boardDO.getFile_nm().equals("")){
				if(isThereFile(boardDO.getMainId())){
					updateFile(con, boardDO.getFile_nm(),boardDO.getFile_size(),boardDO.getFile_path(),  boardDO.getMainId(),boardDO.getRegrid());
				}else{

					int seq = 1;

					insertFile( boardDO.getFile_nm(),boardDO.getFile_path(),boardDO.getMainId() ,seq ,boardDO.getRegrid());

				}
			}
			con.commit();

			return iTmp;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}
	/**
	 * 게시판 정보를 삭제한다.
	 * @param boardId 삭제할 게시판id
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int deleteBoardInfo(int boardId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.BOARD_TBL ");
		buf.append("\n where BOARD_ID = ?  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setInt(++index, boardId);

			int iTmp = stmt.executeUpdate();

			//파일정보를 삭제한다.
			deleteBoardAttachFile(boardId);
			con.commit();
			return iTmp;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 첨부 파일 정보를 조회한다.
	 * @param boardId 게시판 ID
	 * @return List 파일정보인 FileInfoDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectFileInfo(int boardId) throws Exception
	{
		String query = CommunityStatement.selectFileInfoQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setInt(++index, boardId);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				FileInfoDO item = new FileInfoDO();
				item.setMothrDataId(            		rs.getInt("MOTHR_DATA_ID"));
				item.setSeq(								rs.getInt("SEQ"));
				item.setAttcFileTypeCd(         	rs.getString("ATTC_FILE_TYPE_CD"));
				item.setAttcClfCd(              		rs.getString("ATTC_CLF_CD"));
				item.setFlNm(                   			rs.getString("FL_NM"));
				item.setFlSz(                   			rs.getBigDecimal("FL_SZ"));
				item.setFlPath(                 			rs.getString("FL_PATH"));
				item.setOrgFileNm(              		rs.getString("ORG_FILE_NM"));
				item.setRegDt(                  		rs.getString("REG_DT"));
				item.setRegrId(                 		rs.getString("REGRID"));
				item.setModDt(                  		rs.getString("MOD_DT"));
				item.setModrId(						rs.getString("MODRID"));

				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	/**
	 * 파일 존재여부를 파악한다
	 * @param boardId 검색할 게시판 정보
	 * @throws Exception 
	 */
	public boolean isThereFile(int boardId) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		buf.append(" select count(1) FROM  DAS.ATTCH_TBL where MOTHR_DATA_ID = '"+boardId+"' \n");
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//총 조회 갯수를 구한다.
			int totalCount  = getTotalCount(con, buf.toString());

			if(totalCount > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, null, con);
		}
	}



	/**
	 * 첨부파일 정보를 삭제한다.
	 * @param boardId 게시판ID
	 * @param seq 시퀀스
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteFileInfo(int boardId, int seq, DASCommonDO commonDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.ATTCH_TBL  ");
		buf.append("\n where MOTHR_DATA_ID = ? and SEQ = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setInt(++index, boardId);
			stmt.setInt(++index, seq);

			stmt.executeUpdate();
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
	 * 특정 글에 대한 질문과 대답을 모두 조회한다.
	 * @param mainId 본문ID
	 * @param commonDO 공통정보
	 * @return List BoardDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectBoardReplyList(String mainId, DASCommonDO commonDO) throws Exception
	{

		String query = CommunityStatement.selectBoardReplyListQuery(mainId);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();


			List resultList = new ArrayList();

			while(rs.next())
			{
				BoardDO item = new BoardDO();
				item.setBoardId( 				rs.getInt("BOARD_ID"));
				item.setBoardTypeCd(    	rs.getString("BOARD_TYPE_CD"));
				item.setAnswerYn(       	rs.getString("ANSWER_YN"));
				item.setSubj(           			rs.getString("SUBJ"));
				item.setCont(           			rs.getString("CONT"));
				item.setRegDt(          		rs.getString("REG_DT"));
				item.setModDt(          		rs.getString("MOD_DT"));
				item.setRegrid(         		rs.getString("REGRID"));
				item.setRegrNm(				rs.getString("USER_NM"));
				item.setModrid(         		rs.getString("MODRID"));
				item.setRdCount(        		rs.getString("RD_COUNT"));
				item.setMainId(         		rs.getInt("MAIN_ID"));
				int attachCount = rs.getInt("ATTACH_COUNT");
				String attachYn = DASBusinessConstants.YesNo.NO;
				if(attachCount > 0)
				{
					attachYn = DASBusinessConstants.YesNo.YES;
				}

				item.setAttachYn(    			attachYn);
				item.setSerialNo( 				rs.getInt("rownum"));

				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	/**
	 * 첨부파일 메타정보를 저장한다
	 * @param fl_nm 파일명
	 * @param fl_path 파일경로
	 * @param boardId 게시판 id
	 * @param seq 파일 순번
	 * @param regrid 저장사용자id
	 * @throws Exception 
	 * 
	 */
	private void insertFile( String fl_nm,  String fl_path, int boardId, int seq,String regrid) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.ATTCH_TBL( ");
		buf.append("\n 	MOTHR_DATA_ID,  ");
		buf.append("\n 	ATTC_FILE_TYPE_CD,  ");
		buf.append("\n 	ATTC_CLF_CD,  ");
		buf.append("\n 	FL_NM,  ");
		buf.append("\n 	FL_PATH,  ");
		buf.append("\n 	ORG_FILE_NM,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");

		buf.append("\n 	SEQ ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?,  ?, ?, ?,  ?) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setInt(++index, boardId);
			stmt.setString(++index, CodeConstants.AttachFileKind.ETC);
			stmt.setString(++index, CodeConstants.AttachFlag.BOARD);
			stmt.setString(++index, fl_nm);
			stmt.setString(++index, fl_path);
			stmt.setString(++index, fl_nm);
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, regrid);
			stmt.setInt(++index, seq);

			stmt.executeUpdate();

			con.commit();
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}
	}


	/**
	 * 첨부파일 정보를 저장한다.
	 * @param con 커넥션
	 * @param fileInfoDO 파일정보
	 * @param boardId 저장할 게시판id
	 * @param seq 순번
	 * @throws Exception 
	 */
	private void insertFileInfo(Connection con, FileInfoDO fileInfoDO, int boardId, int seq) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.ATTCH_TBL( ");
		buf.append("\n 	MOTHR_DATA_ID,  ");
		buf.append("\n 	ATTC_FILE_TYPE_CD,  ");
		buf.append("\n 	ATTC_CLF_CD,  ");
		buf.append("\n 	FL_NM,  ");
		buf.append("\n 	FL_SZ,  ");
		buf.append("\n 	FL_PATH,  ");
		buf.append("\n 	ORG_FILE_NM,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	MOD_DT,  ");
		buf.append("\n 	MODRID,  ");
		buf.append("\n 	SEQ ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setInt(++index, boardId);
			stmt.setString(++index, CodeConstants.AttachFileKind.ETC);
			stmt.setString(++index, CodeConstants.AttachFlag.BOARD);
			stmt.setString(++index, fileInfoDO.getFlNm());
			stmt.setBigDecimal(++index, fileInfoDO.getFlSz());
			stmt.setString(++index, fileInfoDO.getFlPath());
			stmt.setString(++index, fileInfoDO.getOrgFileNm());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, fileInfoDO.getRegrId());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, fileInfoDO.getRegrId());
			stmt.setInt(++index, seq);

			stmt.executeUpdate();
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

	}


	/**
	 * 첨부파일 정보를 저장한다.
	 * @param con 커넥션
	 * @param fileInfoDO 저장할 첨부파일 정보
	 * @throws Exception 
	 */
	private void updateFileInfo(Connection con, FileInfoDO fileInfoDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.ATTCH_TBL set ");
		buf.append("\n 	FL_NM = ?,  ");
		buf.append("\n 	FL_SZ = ?,  ");
		buf.append("\n 	FL_PATH = ?,  ");
		buf.append("\n 	ORG_FILE_NM = ?,  ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MODRID = ?  ");
		buf.append("\n where MOTHR_DATA_ID = ? ");
		buf.append("\n 	and SEQ = ? ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, fileInfoDO.getFlNm());
			stmt.setBigDecimal(++index, fileInfoDO.getFlSz());
			stmt.setString(++index, fileInfoDO.getFlPath());
			stmt.setString(++index, fileInfoDO.getOrgFileNm());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, fileInfoDO.getRegrId());
			stmt.setInt(++index, fileInfoDO.getMothrDataId());
			stmt.setInt(++index, fileInfoDO.getSeq());

			stmt.executeUpdate();
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

	}


	/**
	 * 첨부파일 정보를 수정한다.
	 * @param con 커넥션
	 * @param fl_nm 파일명
	 * @param fl_sz 파일크기
	 * @param fl_path 파일경로
	 * @param boardId 게시판id
	 * @param regrid 등록자id
	 * @throws Exception 
	 */
	private void updateFile(Connection con, String fl_nm, long fl_sz, String fl_path, int boardId, String regrid) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.ATTCH_TBL set ");
		buf.append("\n 	FL_NM = ?,  ");
		buf.append("\n 	FL_SZ = ?,  ");
		buf.append("\n 	FL_PATH = ?,  ");
		buf.append("\n 	ORG_FILE_NM = ?,  ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MODRID = ?  ");
		buf.append("\n where MOTHR_DATA_ID = ? ");


		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, fl_nm);
			stmt.setLong(++index, fl_sz);
			stmt.setString(++index, fl_path);
			stmt.setString(++index, fl_nm);
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, regrid);
			stmt.setInt(++index, boardId);


			stmt.executeUpdate();
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

	}

	/**
	 * 첨부파일 정보를 삭제한다.
	 * @param con 커넥션
	 * @param boardId 게시판 id
	 * @throws Exception 
	 */
	private void deleteFileInfo(Connection con, int boardId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();

		buf2.append("\n SELECT seq,  ORG_FILE_NM,  substr(fl_path,26) as fl_path from   DAS.ATTCH_TBL  ");
		buf2.append("\n where MOTHR_DATA_ID = ? ");

		buf.append("\n delete from DAS.ATTCH_TBL  ");
		buf.append("\n where MOTHR_DATA_ID = ? ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setInt(++index, boardId);






			/*	File targetFolder = new File("1.txt");
			 File[] childFile = targetFolder.listFiles();
		      boolean confirm = false;
		      int size = childFile.length;

		      if (size > 0) {

		          for (int i = 0; i < size; i++) {

		              if (childFile[i].isFile()) {

		                  confirm = childFile[i].delete();

		                  logger.debug(childFile[i]+":"+confirm + " 삭제");

		              } else {



		              }

		          }

		      }



		      targetFolder.delete();

		      logger.debug(targetFolder + " 폴더삭제됨삭제");
		      logger.debug(targetFolder+":"+confirm + " 삭제");
			 */


			stmt.executeUpdate();
		} 
		catch (Exception e) 
		{
			logger.error(buf2.toString());
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

	}

	/**
	 * 첨부파일 정보를 삭제한다.(실제 파일 삭제)
	 * @param boardId 게시판 id
	 * @throws Exception 
	 */
	public int deleteBoardAttachFile(int board_id) throws Exception
	{	
		//			 필요한 변수들
		StringBuffer buf = new StringBuffer();
		//StringBuffer strResultBuffer = new StringBuffer();
		//			서버에 실제로 저장된 이름을 가져온다.
		StringBuffer buf2 = new StringBuffer();
		buf2.append("\n select ORG_FILE_NM, substr(fl_path,26) as fl_path from DAS.ATTCH_TBL ");
		buf2.append("\n where mothr_data_id = ? "); 
		// SQL 구성		
		buf.append("\n delete from DAS.ATTCH_TBL");
		buf.append("\n where mothr_data_id = ? "); 
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			// 서버에 저장된 파일명을 가져온다.
			psmt = con.prepareStatement(buf2.toString());

			int index = 0;		
			psmt.setInt(++index, board_id);

			rs = psmt.executeQuery();

			String fl_nm = "";
			String fl_path = "";

			while(rs.next()){
				fl_nm = ","+ rs.getString("ORG_FILE_NM");
				fl_path =","+  rs.getString("fl_path");
			}

			psmt = con.prepareStatement(buf.toString());
			index = 0;		


			psmt.setInt(++index, board_id);

			psmt.executeUpdate();

			// 파일 삭제(DB에서 삭제에 성공했을 때만)
			//String strDeleteFilePath = "/was/jeus5/webhome/app_home/DAS/mp4/attach/" + file_type.trim() + "/" + clf_cd.trim() + "/" + attachFilename.trim();
			//String strDeleteFilePath = "/mp4/attach/" + file_type.trim() + "/" + clf_cd.trim() + "/" + attachFilename.trim();
			//  String strDeleteFilePath = "/mp4/attach/" + file_type.trim() + "/" + clf_cd.trim() + "/" + fl_nm.trim();
			// String strDeleteFile = "/mp4/attach/"  + "001" + "/"+reg_id +fl_path  + fl_nm.trim();
			//  String strDeleteFilePath = "/mp4/attach/"  + "001" + "/"+reg_id +fl_path ;


			String[] flpath=fl_path.split(",");
			String[] flname= fl_nm.split(",");
			for(int i=1;i<flpath.length;i++){
				String strDeleteFile = flpath[i]  + flname[i].trim();
				String strDeleteFilePath = flpath[i];

				File file = new File(strDeleteFile);
				File folder = new File(strDeleteFilePath);
				boolean bFileExist = false;			
				try
				{
					bFileExist = file.exists();
				}
				catch (SecurityException e)
				{            
					con.rollback();
					logger.error("SecurityException 발생 : " + e.getMessage());
					return 0;
				}

				if (true == bFileExist)
				{
					if (file.delete())  
					{
						folder.delete();
						con.commit();

						//return 1;
					}            		
					else
					{
						con.rollback();  
						if(logger.isDebugEnabled())
							logger.debug("롤백된 파일 : " + strDeleteFile);
						return 0;
					}            	
				}
				else
				{
					con.rollback();  
					if(logger.isDebugEnabled())
						logger.debug("존재하지 않아 롤백된 파일 : " + strDeleteFile);
					return 0;
				}
			}
			return 1;
		}

		catch (Exception ex)
		{
			logger.error(buf2.toString());
			logger.error(buf.toString());

			throw ex;
		}
		finally
		{
			release(rs, psmt, con);
		}

	}



	/**
	 * 특정 게시물id의 첨부파일에서  첨부파일 개수를 파악한다.
	 * @param boardId 게시판 id
	 * @throws Exception 
	 */
	private int selectFileInfoMaxSeq(int boardId) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(" select value(max(SEQ), 0) FROM  DAS.ATTCH_TBL where MOTHR_DATA_ID = "+boardId+"  \n");

		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			int maxSeq  = getTotalCount(con, buf.toString());

			return maxSeq;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, null, con);
		}
	}

	/**
	 * 프리뷰게시판 단일건 조회를 한다.
	 * @param master_id 마스터 ID
	 * 
	 * @return PreviewDO 조회한 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public  List selectPreviewInfo(int master_id) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt =con.prepareStatement(CommunityStatement.selectPreviewQuery());

			int index = 0;
			stmt.setInt(++index, master_id);

			rs = stmt.executeQuery();



			//int indexCount = 0;
			List resultList = new ArrayList();

			if(rs.next())
			{
				PreviewDO item = new PreviewDO();
				item.setMasterId( 				rs.getInt("Master_Id"));
				item.setPreviewId(    	rs.getInt("Preview_Id"));				
				item.setPreview_subj(           			rs.getString("Preview_subj"));
				item.setPreivew_cont(           			rs.getString("Preview_cont"));
				item.setReg_dt(          		rs.getString("Reg_dt"));
				resultList.add(item);


			}

			return resultList;
		} 
		catch (Exception e) 
		{	
			logger.error(master_id);

			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 프리뷰게시판 첨부파일 조회를 한다.
	 * @param master_id 마스터 ID
	 *
	 * @return PreviewAttchDO 조회한 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectPreviewAttachInfo(int master_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(CommunityStatement.selectPreviewAttachQuery(master_id));


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());


			//int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				PreviewAttachDO item = new PreviewAttachDO();
				item.setMasterId( 				rs.getInt("master_id"));
				item.setPreviewAttatchId(    	rs.getInt("preview_attatch_id"));				
				item.setFL_NM(           			rs.getString("fl_nm"));
				item.setFL_SZ(           			rs.getInt("fl_sz"));
				item.setFL_PATH(           			rs.getString("fl_path"));
				item.setOrg_Flie_NM(           			rs.getString("org_file_nm"));
				item.setReg_dt(           			rs.getString("reg_dt"));
				item.setRegRId(          		rs.getString("regrid"));

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
	 * 게시판 목록 조회를 한다.(메인화면용)
	 *
	 * @return List 조회된 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectMainBoardList() throws Exception
	{

		StringBuffer buf = new StringBuffer();

		buf.append(CommunityStatement.selectMainBoardListQuery());


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
				BoardDO item = new BoardDO();
				item.setBoardId( 				rs.getInt("BOARD_ID"));
				item.setBoardTypeCd(    	rs.getString("BOARD_TYPE_CD"));
				item.setAnswerYn(       	rs.getString("ANSWER_YN"));
				String subj = rs.getString("SUBJ");
				subj = subj.replaceAll("&", "&amp;");
				subj = subj.replace("'", "&apos;");
				subj = subj.replaceAll("\"", "&quot;");
				subj = subj.replaceAll("<", "&lt;");
				subj = subj.replaceAll(">", "&gt;");
				item.setSubj(           			subj);
				String cont = rs.getString("CONT");
				cont = cont.replaceAll("&", "&amp;");
				cont = cont.replace("'", "&apos;");
				cont = cont.replaceAll("\"", "&quot;");
				cont = cont.replaceAll("<", "&lt;");
				cont = cont.replaceAll(">", "&gt;");
				item.setCont(           			cont);
				item.setRegDt(          		rs.getString("REG_DT"));
				item.setModDt(          		rs.getString("MOD_DT"));
				item.setRegrid(         		rs.getString("REGRID"));
				item.setRegrNm(				rs.getString("USER_NM"));
				item.setModrid(         		rs.getString("MODRID"));
				item.setRdCount(        		rs.getString("RD_COUNT"));
				item.setMainId(         		rs.getInt("MAIN_ID"));
				int attachCount = rs.getInt("ATTACH_COUNT");
				String attachYn = DASBusinessConstants.YesNo.NO;
				//첨부파일수가 0보다 크면 첨부파일여부를 'Y'로 셋팅한다.
				if(attachCount > 0)
				{
					attachYn = DASBusinessConstants.YesNo.YES;
				}

				item.setAttachYn(    			attachYn);
				item.setSerialNo( 				rs.getInt("rownum"));

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
	 * 게시판 목록 조회를 한다.(팝업용)오늘 날짜를 기준으로 큰것을 조회한다
	 * @param today 오늘날짜
	 * @param commonDO 공통정보
	 * @return List 조회된 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectMainBoardList2(String today) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		buf.append(CommunityStatement.selectMainBoardListQuery2(today));


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
				BoardDO item = new BoardDO();
				item.setBoardId( 				rs.getInt("BOARD_ID"));
				item.setBoardTypeCd(    	rs.getString("BOARD_TYPE_CD"));
				item.setAnswerYn(       	rs.getString("ANSWER_YN"));
				String SUBJ = rs.getString("SUBJ");
				SUBJ = SUBJ.replaceAll("&", "&amp;");
				SUBJ = SUBJ.replace("'", "&apos;");
				SUBJ = SUBJ.replaceAll("\"", "&quot;");
				SUBJ = SUBJ.replaceAll("<", "&lt;");
				SUBJ = SUBJ.replaceAll(">", "&gt;");
				item.setSubj(           			SUBJ);
				String CONT = rs.getString("CONT");
				CONT = CONT.replaceAll("&", "&amp;");
				CONT = CONT.replace("'", "&apos;");
				CONT = CONT.replaceAll("\"", "&quot;");
				CONT = CONT.replaceAll("<", "&lt;");
				CONT = CONT.replaceAll(">", "&gt;");
				item.setCont(           			CONT);
				item.setRegDt(          		rs.getString("REG_DT"));
				item.setModDt(          		rs.getString("MOD_DT"));
				item.setRegrid(         		rs.getString("REGRID"));
				item.setRegrNm(				rs.getString("USER_NM"));
				item.setModrid(         		rs.getString("MODRID"));
				item.setRdCount(        		rs.getString("RD_COUNT"));
				item.setMainId(         		rs.getInt("MAIN_ID"));
				int attachCount = rs.getInt("ATTACH_COUNT");
				String attachYn = DASBusinessConstants.YesNo.NO;
				//첨부파일수가 0보다 크면 첨부파일여부를 'Y'로 셋팅한다.
				if(attachCount > 0)
				{
					attachYn = DASBusinessConstants.YesNo.YES;
				}

				item.setAttachYn(    			attachYn);
				item.setSerialNo( 				rs.getInt("rownum"));

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



	/*


	public int[] insertAttachFile(List FileInfoDO) throws DASException
	{
		logger.debug("11111");
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.ATTCH_TBL( ");
		buf.append("\n 	MOTHR_DATA_ID,  ");
		buf.append("\n 	ATTC_FILE_TYPE_CD,  ");
		buf.append("\n 	ATTC_CLF_CD,  ");
		buf.append("\n 	FL_NM,  ");
		buf.append("\n 	FL_SZ,  ");
		buf.append("\n 	FL_PATH,  ");
		buf.append("\n 	ORG_FILE_NM,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");

		buf.append("\n 	SEQ ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?,  ?) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{

			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			  for(int i=0;i<FileInfoDO.size();i++){
					index = 0;
					FileInfoDO InfoDOs = (FileInfoDO)FileInfoDO.get(i);

					int maxSeq = selectFileInfoMaxSeq(InfoDOs.getMothrDataId()) + 1;
			stmt.setInt(++index, InfoDOs.getMothrDataId());
			stmt.setString(++index, CodeConstants.AttachFileKind.ETC);
			stmt.setString(++index, CodeConstants.AttachFlag.BOARD);
			stmt.setString(++index, InfoDOs.getFlNm());
			stmt.setLong(++index, InfoDOs.getFl_size());
			stmt.setString(++index, InfoDOs.getFlPath());
			stmt.setString(++index, InfoDOs.getFlNm());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, InfoDOs.getRegrId());
			stmt.setInt(++index, maxSeq);

			stmt.addBatch();
			  }
			logger.debug("22221");
			con.commit();
			  int[] rInt = null;	
		      if(FileInfoDO.size()>0)rInt =stmt.executeBatch();

		      return rInt;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        if(con != null)
	        {
	        	try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
	        }

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;

		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        if(con != null)
	        {
	        	try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
	        }

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}	



	 */



	/**
	 * 첨부파일 메타데이터를 저장한다
	 * @param FileInfoDO 저장할 첨부파일 정보
	 * @throws Exception 
	 * 
	 */

	public int insertAttachFile(FileInfoDO FileInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.ATTCH_TBL( ");
		buf.append("\n 	MOTHR_DATA_ID,  ");
		buf.append("\n 	ATTC_FILE_TYPE_CD,  ");
		buf.append("\n 	ATTC_CLF_CD,  ");
		buf.append("\n 	FL_NM,  ");
		buf.append("\n 	FL_SZ,  ");
		buf.append("\n 	FL_PATH,  ");
		buf.append("\n 	ORG_FILE_NM,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");

		buf.append("\n 	SEQ ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?,  ?) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{

			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			String[] file_nm =  FileInfoDO.getFlNm().split(",");
			String[] file_path =  FileInfoDO.getFlPath().split(",");
			index = 0;

			for(int i=0; i<file_nm.length; i++){
				int maxSeq = selectFileInfoMaxSeq(FileInfoDO.getMothrDataId()) + 1;
				stmt.setInt(++index, FileInfoDO.getMothrDataId());
				stmt.setString(++index, CodeConstants.AttachFileKind.ETC);
				stmt.setString(++index, CodeConstants.AttachFlag.BOARD);
				stmt.setString(++index,file_nm[i]);
				stmt.setLong(++index, FileInfoDO.getFl_size());
				stmt.setString(++index, file_path[i]);
				stmt.setString(++index, file_nm[i]);
				stmt.setString(++index, toDateTime);
				stmt.setString(++index, FileInfoDO.getRegrId());
				stmt.setInt(++index, maxSeq);
				stmt.executeUpdate();
			}
			int updatcount =1;


			con.commit();

			return updatcount;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}	







	/**
	 * 첨부파일 메타데이터를 수정한다
	 * @param FileInfoDO 저장할 첨부파일 정보
	 * @throws Exception 
	 * 
	 */
	public int updateBoardAtaachInfo(FileInfoDO FileInfoDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.ATTCH_TBL set ");
		buf.append("\n 	FL_NM = ?,  ");
		buf.append("\n 	FL_SZ = ?,  ");
		buf.append("\n 	FL_PATH = ?,  ");
		buf.append("\n 	ORG_FILE_NM = ?,  ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MODRID = ?  ");
		buf.append("\n where MOTHR_DATA_ID = ? ");
		buf.append("\n and seq = ? ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{

			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, FileInfoDO.getFlNm());
			stmt.setLong(++index, FileInfoDO.getFl_size());
			stmt.setString(++index, FileInfoDO.getFlPath());
			stmt.setString(++index, FileInfoDO.getFlNm());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, FileInfoDO.getModrId());

			stmt.setInt(++index, FileInfoDO.getMothrDataId());
			stmt.setInt(++index, FileInfoDO.getSeq());
			con.commit();
			return stmt.executeUpdate();


		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}
	}
}
