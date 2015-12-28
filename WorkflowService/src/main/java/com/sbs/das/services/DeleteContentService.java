package com.sbs.das.services;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.xml.DeleteRequest;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface DeleteContentService {
	
	/**
	 * <pre>
	 * 아카이브된 컨텐츠중에서 스토리지 보관일이 지난 영상을 조회한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public List<DeleteRequest> findArchiveDeleteList(String coCd, String limitDay) throws ServiceException;
	
	/**
	 * <pre>
	 * 다운로드된 컨텐츠중에서 스토리지 보관일이 지난 영상을 조회한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public List<DeleteRequest> findDownloadDeleteList(String coCd, String limitDay) throws ServiceException;
	
	/**
	 * <pre>
	 * 사용자가 삭제 요청한 컨텐츠 리스트를 조회한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public List<DeleteRequest> findContentScrappedDeleteList(String coCd, String limitDay) throws ServiceException;
	
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
	 * 요청한 컨텐츠를 삭제처리한 후 최종 완료정보를 업데이트한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public void updateDeleteComplete(Long ctId, String limitDay) throws ServiceException;
	
	/**
	 * <pre>
	 * 삭제요청한 컨텐츠가 다른 회차에서 사용중일경우 컨텐츠 메타정보 및 영상은 삭제를 하지 않고
	 * 요청한 회차의 컨텐츠정보만을 CONTENTS_MAPP_TBL에서 삭제처리를 한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public void updateDeleteCtMap(Long ctId) throws ServiceException;
	
	/**
	 * <pre>
	 * 아카이브가 완료된 영상에서 스토리지 보관일이 만료된 영상은 스토리지에서 물리적인 영상을
	 * 삭제한 후 DB에 저장된 파일경로를 초기화 한다.
	 * </pre>
	 * @param limitDay
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public void updateContentPathBlank(Long ctiId) throws ServiceException;
	
	/**
	 * <pre>
	 * 다운로드된 영상중 스토리지 보관일이 지난 영상에 대해 삭제를 했을경우 삭제일을 등록한다.
	 * </pre>
	 * @param cartNo
	 * @param cartSeq
	 * @throws ServiceException
	 */
	public void updateDeleteCtDown(Long cartNo, Integer cartSeq) throws ServiceException;
	
	
	/**
	 * <pre>
	 * PDS,IFCMS에서 넘어온 영상의 삭제요청이 온다면 물리적 고해상도 파일을 삭제한후
	 * MEDIA_ID의 값을 공백으로 처리한다.
	 * </pre>
	 * @param ctId
	 * @return List<DeleteRequest>
	 * @throws DaoNonRollbackException
	 */
	public void updateContentMediaIdBlank(Long ctId) throws ServiceException;
	
	/**
	 * <pre>
	 * 검색엔진에 정보를 업데이트 요청한다.
	 * </pre>
	 * @param masterId
	 * @throws ServiceException
	 */
	public void insertKwKlog(Long masterId) throws ServiceException;
	
	/**
	  *<pre>
	 * 검색엔진에 삭제 정보를 업데이트 요청한다.
	 * </pre>
	 * @param masterId
	 * @throws ServiceException
	 */
	public void deleteKwKlog(Long masterId) throws ServiceException;
	
}
