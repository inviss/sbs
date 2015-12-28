package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.xml.DeleteRequest;

public interface DeleteContentDao {
	/**
	 * <pre>
	 * 아카이브된 컨텐츠중에서 스토리지 보관일이 지난 영상을 조회한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public List<DeleteRequest> findArchiveDeleteList(String coCd, String limitDay) throws DaoNonRollbackException;
	
	
	
	/**
	 * <pre>
	 * 다운로드된 컨텐츠중에서 스토리지 보관일이 지난 영상을 조회한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public List<DeleteRequest> findDownloadDeleteList(Map<String, String> params) throws DaoNonRollbackException;
	//public List<DeleteRequest> findDownloadDeleteList(String limitDay) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * 사용자가 폐기삭제 요청한 컨텐츠 리스트를 조회한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public List<DeleteRequest> findScrappedList(String coCd, String limitDay) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * 사용자가 삭제 요청한 컨텐츠 리스트를 조회한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public DeleteRequest getUserDeleteConent(Long ctId) throws ServiceException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param ctId
	 * @return
	 * @throws ServiceException
	 */
	public List<DeleteRequest> findUserDeleteConent(Long ctId) throws ServiceException;
	
	/**
	 * <pre>
	 * 삭제완료후 메타정보를 update한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public List<DeleteRequest> updateDeleteComplete(Map<String, Long> params) throws ServiceException;
	
	/**
	 * <pre>
	 * 검색엔진의 메타정보를 업데이트한다.
	 * </pre>
	 * @param masterId
	 * @throws ServiceException
	 */
	public void insertKwKlog(Long masterId) throws ServiceException;
	
	/**
	 * <pre>
	 * 검색엔진의 메타정보를 삭제한다.
	 * </pre>
	 * @param masterId
	 * @throws ServiceException
	 */
	public void deleteKwKlog(Long masterId) throws ServiceException;
}
