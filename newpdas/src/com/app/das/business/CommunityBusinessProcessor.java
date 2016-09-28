/***********************************************************
 * 프로그램ID  	: CommunityBusinessProcessor.java
 * 프로그램명  	: CommunityBusinessProcessor
 * 작성일자     	:
 * 작성자       	:
 * 설명         :  커뮤니티 관리 비지니스 프로세서 프로그램
 * 변경이력
 * --------------------------------------------------------------------
 *    변경NO     변경일자        변경자                설   명
 * --------------------------------------------------------------------
 *    1          2008/01/04     ysk523               최초생성
 *    2			 2010/08/09     김동은                DAS2.0 전환 수정.
 ***********************************************************/
package com.app.das.business;

import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.CommunityDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.BoardConditionDO;
import com.app.das.business.transfer.BoardDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.FileInfoDO;
import com.app.das.util.StringUtils;

/**
 * 이용자 마당의 게시판(공지사항, QA, 이용안내, 제안/오류신고 에 대한 등록, 수정, 조회, 삭제가 구현되어 있다.
 * @author ysk523
 *
 */
public class CommunityBusinessProcessor 
{
	private static CommunityDAO communityDAO = CommunityDAO.getInstance();

	private Logger logger = Logger.getLogger(CommunityBusinessProcessor.class);

	/**
	 * 게시판 목록 조회를 한다.
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 조회된 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getBoardList(BoardConditionDO conditionDO) throws Exception
	{

		return communityDAO.selectBoardList(conditionDO);

	}

	/**
	 * 게시판 단일건 조회를 한다.
	 * @param boardId 게시판 ID
	 * @param commonDO 공통정보
	 * @return BoardDO 조회한 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getBoardInfo(BoardDO board) throws Exception
	{

		if(board.getMainId()!= 0){
			//해당 게시판 정보를 조회한다.
			BoardDO boardDO =  new BoardDO();
			//첨부파일이 존재하면 파일정보를 조회한다.
			if(DASBusinessConstants.YesNo.YES.equals(boardDO.getAttachYn()))
			{
				List fileInfoDOList = communityDAO.selectFileInfo(board.getBoardId());
				boardDO.setFileInfoDOList(fileInfoDOList);
			}

			//사용자의 읽은 횟수를 증가 시킨다.
			communityDAO.updateReadCount(board.getMainId());

			return communityDAO.selectBoardInfo(board.getMainId());
		}

		return communityDAO.selectBoardInfoList(board);
	}

	/**
	 * 게시판 정보를 저장한다.
	 * @param boardDO 저장할 게시판 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertBoardInfo(BoardDO boardDO) throws Exception
	{

		if(StringUtils.isEmpty(boardDO.getBoardTypeCd()))
		{
			DASException exception = new DASException(ErrorConstants.NOT_VALID_BOARD_TYPE, "게시판 종류코드 오류입니다.");
			throw exception;
		}

		return communityDAO.insertBoard(boardDO);

	}

	/**
	 * 게시판 대답 정보를 저장한다.
	 * @param boardDO 저장할 게시판 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertReplyBoardInfo(BoardDO boardDO, DASCommonDO commonDO) throws Exception
	{

		if(StringUtils.isEmpty(boardDO.getBoardTypeCd()))
		{
			DASException exception = new DASException(ErrorConstants.NOT_VALID_BOARD_TYPE, "게시판 종류코드 오류입니다.");
			throw exception;
		}

		communityDAO.insertBoardInfo(boardDO, DASBusinessConstants.YesNo.YES);

	}

	/**
	 * 게시판 대답 정보를 저장한다.
	 * @param boardDO 저장할 게시판 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateBoardInfo(BoardDO boardDO) throws Exception
	{

		return communityDAO.updateBoard(boardDO);

	}

	/**
	 * 게시판 정보를삭제한다.
	 * @param boardDO 저장할 게시판 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int  deleteBoardInfo(int boardId) throws Exception
	{

		return communityDAO.deleteBoardInfo(boardId);

	}

	/**
	 * 게시판의 읽은 수가 많은 것을 조회한다.
	 * @param conditionDO 조회조건을 포함하고 있는 DO
	 * @param commonDO 공통정보
	 * @return List BoardDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getBoardReadCountList(BoardConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		return communityDAO.selectBoardListForReadCount(conditionDO, commonDO);

	}

	/**
	 * 첨부파일 정보를 삭제한다.
	 * 
	 * @param boardId 게시판ID
	 * @param seq 시퀀스
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteFileInfo(int boardId, int seq, DASCommonDO commonDO) throws Exception
	{

		communityDAO.deleteFileInfo(boardId, seq, commonDO);

	}

	/**
	 * 특정 글에 대한 질문과 대답을 모두 조회한다.
	 * @param mainId 본문ID
	 * @param commonDO 공통정보
	 * @return List BoardDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getBoardReplyList(String mainId, DASCommonDO commonDO) throws Exception
	{

		return communityDAO.selectBoardReplyList(mainId, commonDO);

	}


	/**
	 * 프리뷰노트 단일건 조회를 한다.
	 * @param previewId 프리뷰 ID
	 *
	 * @return previewDO 조회한 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getPreviewInfo(int master_id) throws Exception
	{

		return communityDAO.selectPreviewInfo(master_id);

	}



	/**
	 * 프리뷰노트 다운로드를 한다.
	 * @param previewAttchId 프리뷰첨부 ID
	 *
	 * @return PreviewAttchDO 조회한 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getPreviewAttachInfo(int master_id) throws Exception
	{

		return communityDAO.selectPreviewAttachInfo(master_id);

	}

	/**
	 * 게시판 목록 조회를 한다.(메인화면용)
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 조회된 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getMainBoardList() throws Exception
	{

		return communityDAO.selectMainBoardList();

	}


	/**
	 * 게시판 목록 조회를 한다.(팝업용)
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 조회된 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectMainBoardList2(String today) throws Exception
	{

		return communityDAO.selectMainBoardList2(today);

	}


	/**
	 * 게시판 첨부파일정보 조회를 한다.
	 * @param boardId 게시판 ID
	 * @param commonDO 공통정보
	 * @return BoardDO 조회한 게시판 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getBoardAtaachInfo(int boardId) throws Exception
	{

		return communityDAO.selectFileInfo(boardId);

	}

	/**
	 * 게시판 정보를 저장한다.
	 * @param boardDO 저장할 게시판 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertBoardAtaachInfo(FileInfoDO fileDO) throws Exception
	{

		communityDAO.insertAttachFile(fileDO);
		return 1;

	}


	/**
	 * 게시판 대답 정보를 저장한다.
	 * @param boardDO 저장할 게시판 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateBoardAtaachInfo(FileInfoDO FileInfoDO) throws Exception
	{

		return communityDAO.updateBoardAtaachInfo(FileInfoDO);
	}

}
