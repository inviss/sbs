package com.sbs.das.repository;

import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.UserInfoTbl;

public interface UserDao {
	public void updateUser(UserInfoTbl userInfoTbl) throws DaoRollbackException;
}
