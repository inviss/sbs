package com.sbs.das.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.UserInfoTbl;
import com.sbs.das.repository.UserDao;

@Service(value="userManagerService")
public class UserManagerServiceImpl implements UserManagerService {

	@Autowired
	private UserDao userDao;
	
	public void updateUser(List<UserInfoTbl> userList) throws ServiceException {
		for(UserInfoTbl userInfoTbl : userList) {
			userDao.updateUser(userInfoTbl);
		}
	}

}
