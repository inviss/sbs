package com.sbs.das.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.CartContTbl;
import com.sbs.das.dto.ErpTapeItemTbl;
import com.sbs.das.dto.ErpTapeTbl;
import com.sbs.das.dto.xml.ArchiveStatus;
import com.sbs.das.repository.CartContDao;
import com.sbs.das.repository.TapeDao;
import com.sbs.das.repository.TapeItemDao;

@Service(value="tapeErpService")
public class TapeErpServiceImpl implements TapeErpService {

	@Autowired
	private TapeDao tapeDao;
	@Autowired
	private TapeItemDao itemDao;
	@Autowired
	private CartContDao cartContDao;
	
	
	public ErpTapeTbl getErpTape(String tapeId) throws DaoNonRollbackException {
		return tapeDao.getTapeObj(tapeId);
	}

	public ErpTapeItemTbl getErpTapeItem(String tapeItemId)
			throws DaoNonRollbackException {
		return itemDao.getTapeItemObj(tapeItemId);
	}

	public void updateTapeOutService(ArchiveStatus archiveStatus)
			throws DaoRollbackException {
		
		CartContTbl cartContTbl = new CartContTbl();
		cartContTbl.setModrid("TapeOut");
		cartContTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
		cartContTbl.setCartNo(archiveStatus.getCartNo());
		cartContTbl.setCartSeq(archiveStatus.getCartSeq());
		
		if(archiveStatus.getJobStatus().equals("C")) {
			cartContTbl.setDownStat("009");
		} else if(archiveStatus.getJobStatus().equals("D")) {
			cartContTbl.setDownStat("010");
		}
		
		cartContDao.updateCartCont(cartContTbl);
	}

}
