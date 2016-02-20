package com.sbs.das.services;

import java.util.List;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.Metadata;

public interface MetadataService {

	public List<MetadatMstTbl> findMetaDataList(Data data) throws ServiceException;
	
	public void updateMetadataInfo(Metadata metadata) throws ServiceException;
}
