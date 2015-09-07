package com.sbs.das.services;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.CartContTbl;
import com.sbs.das.dto.ContentDownTbl;
import com.sbs.das.dto.ContentLocTbl;
import com.sbs.das.dto.CopyInfoTbl;
import com.sbs.das.dto.DownCartTbl;
import com.sbs.das.dto.ErrorLogTbl;
import com.sbs.das.dto.MasterTbl;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface ArchiveReqService {
	
	/**
	 * <pre>
	 * 다운로드 요청된 리스트 정보를 조회한다.
	 * </pre>
	 * @param cartNo
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<DownCartTbl> findDownCarts(Long cartNo, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * down_cart_tbl에서 다운로드 요청의 단일건을 조회한다.
	 * </pre>
	 * @param cartNo
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public DownCartTbl getDownCart(Long cartNo, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * Cart_no를 입력받아 Cart_cont_tbl에서 여러건의 다운로드 요청 기록을 조회한다.
	 * </pre>
	 * @param cartNo
	 * @param cartSeq
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<CartContTbl> findCartConts(Long cartNo, Integer cartSeq, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * Cart_no를 입력받아 Cart_cont_tbl에서 여러건의 다운로드 요청 기록을 조회한다.
	 * </pre>
	 * @param cartNo
	 * @param cartSeq
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public List<CartContTbl> findCartConts(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * primary key를 입력받아 다운로드 단일건을 조회한다.
	 * </pre>
	 * @param cartNo
	 * @param cartSeq
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public CartContTbl getCartCont(Long cartNo, Integer cartSeq, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * CartCont 메타정보 변경
	 * @param cartContTbl
	 * @throws DaoRollbackException
	 */
	@Transactional
	public void updateCartCont(CartContTbl cartContTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * 복원요청 정보를 저장한다.
	 * </pre>
	 * @param contentDownTbl
	 * @return
	 * @throws DaoRollbackException
	 */
	@Transactional
	public long insertContentDown(ContentDownTbl contentDownTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * 복원요청 정보를 조회한다.
	 * </pre>
	 * @param num
	 * @param wating
	 * @return ContentDownTbl
	 * @throws DaoNonRollbackException
	 */
	public ContentDownTbl getContentDown(Long num, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * 
	 * @param num
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentDownTbl getContentDown(Long cartNo, Integer cartSeq, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentDownTbl getContentDown(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * Tape_item_id로 회차에 해당 정보가 존재한다면 가져온다.
	 * </pre>
	 * @param tapeItemId
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public MasterTbl getMaster(String tapeItemId, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * 청구번호, 회차, 장면번호로 회차에 해당 정보가 존재한다면 가져온다.
	 * </pre>
	 * @param tapeItemId
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public MasterTbl getMaster(Map<String, Object> params) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * 입력받은 mcu_id로 master_tbl에서 max(master_id)를 가져온다.
	 * </pre>
	 * @param mcuId
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public long getMaxMaster(String mcuId, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * 아카이브 요청시 요청관련 컨텐츠 정보를 저장한다.
	 * <pre>
	 * @param contentLocTbl
	 * @throws DaoRollbackException
	 */
	@Transactional
	public Long insertContentLoc(ContentLocTbl contentLocTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * 복본 요청이나 상태를 변경하기 위해 사용한다.
	 * </pre>
	 * @param contentLocTbl
	 * @throws DaoRollbackException
	 */
	@Transactional
	public void updateContentLoc(ContentLocTbl contentLocTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * 아카이브된 Object_name으로 Contents_loc에서 정보를 조회한다.
	 * </pre>
	 * @param ObjName
	 * @param wating
	 * @return
	 * @throws DaoNonRollbackException
	 */
	public ContentLocTbl getContentLoc(String objName, Boolean wating) throws DaoNonRollbackException;
	public ContentLocTbl getContentLoc(Long cti_id, Boolean wating) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * 다운로드 요청 상태를 저장한다.
	 * </pre>
	 * @param contentDownTbl
	 * @throws DaoRollbackException
	 */
	public void updateContentDown(ContentDownTbl contentDownTbl) throws DaoRollbackException;
	
	/**
	 * <pre>
	 * pds_program_id를 받아서 copy_info_tbl에 해당 데이타가 존재하고 결과값이 'Y'라면 자동 복본임.
	 * </pre>
	 * @param pdsProgramId
	 * @return
	 * @throws ServiceException
	 */
	public CopyInfoTbl getCopyInfoObj(String cmsPgmId) throws ServiceException;
	
	
	
	/**
	 * <pre>
	 * 아카이브, 다운로드가 오류가 났을때 해당 오류를 ERROR_LOG_TBL에 기록한다.
	 * </pre>
	 * @param errorLogTbl
	 * @return
	 * @throws ServiceException
	 */
	public void insertErrorLog(ErrorLogTbl errorLogTbl) throws ServiceException;
	
	/**
	 * <pre>
	 * 아카이브된 콘텐츠의 소속이 SBS인지 MediaNet인지 알아온다.
	 * </pre>
	 * @param Object Name
	 * @return
	 * @throws ServiceException
	 */
	public String getCode(String objName) throws ServiceException;
	
}
