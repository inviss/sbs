package com.sbs.das.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.UserInfoTbl;

@Transactional
public interface UserManagerService {
	
	@Transactional
	public void updateUser(List<UserInfoTbl> userList) throws ServiceException;
}
