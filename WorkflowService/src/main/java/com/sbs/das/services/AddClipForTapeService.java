package com.sbs.das.services;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.AnnotInfoTbl;
import com.sbs.das.dto.CodeTbl;
import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.CornerTbl;
import com.sbs.das.dto.MasterTbl;
import com.sbs.das.dto.MediaTapeInfoTbl;
import com.sbs.das.dto.NotReportMsgTbl;
import com.sbs.das.dto.TimeRistSetTbl;
import com.sbs.das.dto.xml.Das;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface AddClipForTapeService {
	public CodeTbl getCodeObj(String clfCd, String sclCd, Boolean wating) throws ServiceException;
	
	@Transactional
	public void insertNotReportedMsg(NotReportMsgTbl notReportMsgTbl) throws ServiceException;
	
	
	/**
	 * <pre>
	 * 신규로 Master 정보를 저장할때 사용할 Unique ID를 받는다.
	 * </pre>
	 * @return long
	 * @throws DaoNonRollbackException
	 */
	public Long getMasterNewId() throws ServiceException;
	
	/**
	 * <pre>
	 * 한 회차정보를 조회한다.
	 * </pre>
	 */
	public MasterTbl getMasterObj(Long masterId) throws ServiceException;
	
	/**
	 * <pre>
	 * 신규로 Content 정보를 저장할때 사용할 Unique ID를 받는다.
	 * </pre>
	 * @return long
	 * @throws DaoNonRollbackException
	 */
	public Long getContentNewId() throws ServiceException;
	
	/**
	 * <pre>
	 * Master에 대한 코너의 count를 조회한다.
	 * </pre>
	 * @param das
	 * @param wating
	 * @return Integer
	 * @throws DaoNonRollbackException
	 */
	public Integer getCornerCount(Das das, Boolean wating) throws ServiceException;
	
	/**
	 * Master에대한 코너ID(cn_id)의 max를 가져온다.
	 * @param das
	 * @param wating
	 * @return
	 * @throws ServiceException
	 */
	public Long getCornerMax(Das das, Boolean wating) throws ServiceException;
	
	/**
	 * <pre>
	 * Corner에대한 정보를 조회한다.
	 * </pre>
	 * @param cnId
	 * @param wating
	 * @return CornerTbl
	 * @throws DaoNonRollbackException
	 */
	public CornerTbl getCornerObj(Long cnId, Boolean wating) throws ServiceException;
	
	/**
	 * <pre>
	 * 신규 Corner ID를 발급받는다.
	 * </pre>
	 * @return long
	 * @throws DaoNonRollbackException
	 */
	public Long getCornerNewId() throws ServiceException;
	
	/**
	 * <pre>
	 * 클라이언트 장비에 대한 정보를 조회한다.
	 * </pre>
	 * @param params
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public CodeTbl getCodeObj(Map<String, Object> params, Boolean wating) throws ServiceException;
	
	/**
	 * <pre>
	 * Content Instance에 대한 신규 ID를 발급받는다.
	 * </pre>
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public Long getContentInstNewId() throws ServiceException;
	
	/**
	 * <pre>
	 * 포맷별 영상 등록 건수를 조회한다.
	 * </pre>
	 * @param contentMapTbl
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public Integer getContentMapCount(ContentMapTbl contentMapTbl, Boolean wating) throws ServiceException;
	
	 /**
	  * <pre>
	  * Master, Content, Content Instance, Content Map등
	  * 영상에 관련된 모든 메타정보를 일괄 저장한다.
	  * </pre>
	  * @param Map<String, Object>
	  * @throws DaoRollbackException
	  */
	@Transactional
	public void saveAddClipInfo(Map<String, Object> params) throws ServiceException;
	
	/**
	 * <pre>
	 * MediaNet Tape 정보를 조회
	 * </pre>
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public MediaTapeInfoTbl getMediaTapeInfo(Map<String, Object> params) throws ServiceException;
	
	/**
	 * 
	 * @param mediaTapeInfoTbl
	 * @throws ServiceException
	 */
	@Transactional
	public void insertMediaTapeInfo(MediaTapeInfoTbl mediaTapeInfoTbl) throws ServiceException;
	
	/**
	 * 
	 * @param java.util.List mediaTapeInfoTbls
	 * @throws ServiceException
	 */
	@Transactional
	public void insertAllMediaTapeInfo(List<MediaTapeInfoTbl> mediaTapeInfoTbls) throws ServiceException;
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public MasterTbl getMaster(Map<String, Object> params) throws ServiceException;
	
	/**
	 * <p>
	 * 2014.05.13 add
	 * 전달된 현재 시간이 DB 테이블에 설정된 시간 구간에 포함이 되는지 조회
	 * </>
	 * @param params 현재시간을 시분초 형식
	 * @return
	 * @throws ServiceException
	 */
	public TimeRistSetTbl getTimeRistSet(Integer week, String nowtime) throws ServiceException;
	 
}
