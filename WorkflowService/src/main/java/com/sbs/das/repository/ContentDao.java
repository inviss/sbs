package com.sbs.das.repository;

import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ContentTbl;

public interface ContentDao {
	/**
	 * <pre>
	 * Contents_Tbl의 sequansce를 얻어온다.
	 * </pre>
	 * @return long
	 * @throws DaoNonRollbackException
	 */
	public long getContentNewId() throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * Content MetaData를 저장한다.
	 * </pre>
	 * @param contentTbl
	 * @throws DaoRollbackException
	 */
	public void insertContent(ContentTbl contentTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * Content MetaData를 수정한다.
	 * </pre>
	 * @param contentTbl
	 * @throws DaoRollbackException
	 */
	public void updateContent(ContentTbl contentTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentTbl getContent(Map<String, Object> params) throws DaoNonRollbackException; 
	
	/**
	 * <pre>
	 * </pre>
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentTbl getContentForInst(Map<String, Object> params) throws DaoNonRollbackException; 
	
	/**
	 * <pre>
	 * DAS용 Media_id를 발급받는다.
	 * </pre>
	 * @return
	 * @throws DaoRollbackException
	 */
	public Map<String, String> getMediaId(Map<String, String> param) throws DaoRollbackException;
	
	public Integer getContentCount(Map<String, Object> params) throws DaoNonRollbackException;
}
