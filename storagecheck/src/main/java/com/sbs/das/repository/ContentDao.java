package com.sbs.das.repository;

import java.util.Map;

import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;

import com.sbs.das.dto.ContentMapTbl;
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
	
	/**
	 * <pre>
	 * 2015.01.08
	 * DAS가 관리하는 스토리지 경로의 파일을 대상으로 비정상 파일을 체크.
	 * </pre>
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentTbl getContentCheck(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * 2015.01.08
	 * CT_ID와 RPIMG_CT_ID 조인으로 메타ID 조회가 되는지 확인
	 * 된다면 CONTENTS_MAPP_TBL 테이블에 정보가 없는 것임.
	 * </pre>
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public Long getMasterJoinCheck(Map<String, Object> params) throws DaoNonRollbackException;
	
	public ContentMapTbl getMasterCornerMeta(Map<String, Object> params) throws DaoNonRollbackException;
	
	public void insertContentMap(ContentMapTbl contentMapTbl) throws DaoRollbackException;
}
