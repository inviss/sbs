package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.MasterTbl;

public interface MasterDao {
	
	/**
	 * <pre>
	 * 회차정보 목록을 조회한다.
	 * </pre>
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<MasterTbl> findMaster(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * master_id의 max값을 조회한다.
	 * </pre>
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public Long getMaxMaster(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * Archive가 완료되었을때 cti_id를 인자로 받아서 복원 완료일을 등록한다.
	 * </pre>
	 * @param params
	 * @throws DaoRollbackException
	 */
	public void updateArchiveComplete(Map<String, Object> params) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * Master Table의 신규 ID를 발급받는다.
	 * </pre>
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public Long getMasterNewId() throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * Master 정보를 변경한다.
	 * </pre>
	 * @param masterTbl
	 * @throws DaoRollbackException
	 */
	public void updateMaster(MasterTbl masterTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * Master 정보를 저장한다.
	 * </pre>
	 * @param masterTbl
	 * @throws DaoRollbackException
	 */
	public void insertMaster(MasterTbl masterTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * Master 정보를 조회한다.
	 * </pre>
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public MasterTbl getMaster(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * 콘텐츠의 회사를 조회한다.
	 * <pre>
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public String getCocd(Map<String, Object> params) throws DaoNonRollbackException;
}
