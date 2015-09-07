package com.sbs.das.services;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ErpTapeItemTbl;
import com.sbs.das.dto.ErpTapeTbl;
import com.sbs.das.dto.xml.ArchiveStatus;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface TapeErpService {
	public ErpTapeTbl getErpTape(String tapeId) throws DaoNonRollbackException;
	
	public ErpTapeItemTbl getErpTapeItem(String tapeItemId) throws DaoNonRollbackException;
	
	/**
	 * <pre>
	 * Tape Out 진행현황을 Down_Cart_Tbl에 저장한다.
	 * job_id - 009 [tape_out]
	 * job_status -  [Q:큐, I:진행중, C:완료, D:삭제, E:에러]
	 * job_status값에 따라서 down_stat값을 결정한다. [C: 009, D: 010]
	 * </pre>
	 * @param archiveStatus
	 * @throws DaoRollbackException
	 */
	@Transactional
	public void updateTapeOutService(ArchiveStatus archiveStatus) throws DaoRollbackException;
}
