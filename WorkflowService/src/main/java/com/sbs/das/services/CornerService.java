package com.sbs.das.services;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.ops.Data;

public interface CornerService {
	public void updateCorners(Data data) throws ServiceException;
}
