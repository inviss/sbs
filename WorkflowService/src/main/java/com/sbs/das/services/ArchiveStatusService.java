package com.sbs.das.services;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.ContentDownTbl;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.ContentLocTbl;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface ArchiveStatusService {
	/**
	 * <pre>
	 * Backup 진행 현황을 update 한다.
	 * </pre>
	 * @param contentLocTbl
	 * @throws DaoRollbackException
	 */
	@Transactional
	public void backupStatus(ContentLocTbl contentLocTbl) throws ServiceException;
	
	/**
	 * <pre>
	 * Backup 진행 현황을 update 한다.
	 * </pre>
	 * @param contentLocTbl
	 * @throws DaoRollbackException
	 */
	@Transactional
	public void updateStatus(ContentLocTbl contentLocTbl) throws ServiceException;
	
	/**
	 * <pre>
	 * Restore 진행현황을 update 한다.
	 * </pre>
	 * @param contentDownTbl
	 * @throws DaoRollbackException
	 */
	@Transactional
	public void restoreStatus(ContentDownTbl contentDownTbl) throws ServiceException;
	
	/**
	 * <pre>
	 * Restore Complete 상태를 받았을때 Content_down 테이블에서 cti_id를 조회한 후
	 * Master 테이블에 완료일 및 수정일을 update한다.
	 * </pre>
	 * @param ContentDownTbl
	 * @throws DaoRollbackException
	 */
	@Transactional
	public void updateCompleteMaster(ContentLocTbl contentLocTbl) throws ServiceException;
	
	/**
	 * <pre>
	 * 복원이 완료되었거나 오류가 발생했을경우 CartCont에 상태를 저장한다.
	 * 007[완료], 008[오류]
	 * </pre>
	 * @param DownCartTbl
	 * @throws DaoRollbackException
	 */
	@Transactional
	public void updateStatusCartCont(ContentDownTbl contentDownTbl) throws ServiceException;
	
	/**
	 * <pre>
	 * Full Content 복원 완료시 원본 파일명을 Diva에 전달해야한다.
	 * </pre>
	 * @param num
	 * @param wating
	 * @throws DaoNonRollbackException
	 */
	public void getContentLoc(String objName, Boolean wating) throws ServiceException;
	
	/**
	 * <pre>
	 * Content_Down_Tbl의 key값인 num을 입력받아 해당 메타정보를 조회한다.
	 * </pre>
	 * @param params
	 * @return ContentDownTbl
	 * @throws DaoNonRollbackException
	 */
	public ContentDownTbl getConentDown(Long num, Boolean wating) throws ServiceException;
	
	/**
	 * <pre>
	 * 수동작업 완료후 contentsinsttbl의 etc값을 지워준다.
	 * </pre>
	 * @param num
	 * @param wating
	 * @throws DaoNonRollbackException
	 */
	public void updateContentInst(ContentInstTbl ContentInstTbl) throws ServiceException;
	
	/**
	 * <pre>
	 * BatchDown 영상을 재아카이브 요청했을경우
	 * </pre>
	 * @param objName
	 * @param wating
	 * @return
	 * @throws ServiceException
	 */
	public ContentDownTbl getBatchDown(String objName, Boolean wating) throws ServiceException;
	
	/**
	 * <pre>
	 * 2012.12.20 Rowres 영상 배치 다운로드를 위해 추가
	 * </pre>
	 * @param num
	 * @param wating
	 * @return
	 * @throws ServiceException
	 */
	public ContentDownTbl getBatchDown(Long num, Boolean wating) throws ServiceException;
	
	/**
	 * <pre>
	 * 2013.01.09 배치 다운로드용 인터페이스
	 * </pre>
	 * @param contentDownTbl
	 * @throws ServiceException
	 */
	public void updateBatchDown(ContentDownTbl contentDownTbl) throws ServiceException;
	
	public void insertBatchDown(ContentDownTbl contentDownTbl) throws ServiceException;
	
	/**
	 * <pre>
	 * 2015.12.11
	 * 다운로드 요청번호 이전 데이타 중 해당 번호의 영상ID(CTI_ID)와 동일한 요청 건들 중
	 * 진행중인 건이 있다면 모두 실패처리 한다. 24시간 이전 데이타에 한 함.
	 * </pre>
	 * @param ctiId
	 * @return
	 * @throws ServiceException
	 */
	public List<ContentDownTbl> findContentDown(ContentDownTbl contentDownTbl) throws ServiceException;
	
}
