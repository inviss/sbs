package com.app.das.business.transfer;

import java.io.Serializable;

import com.app.das.util.Reflector;


/**
 * @version  1.0  우리 시스템의 DataObject의 최상위 class로 모든 DataObject가 구현해야 하는 공통 method를 구현한다.
 */

/**
 * 우리 시스템의 DataObject의 최상위 class로 모든 DataObject가 구현해야 하는 공통 method를 구현한다.
 * @author ysk523
 *
 */
public abstract class DTO implements Serializable
{
	/**
	 * @see java.lang.Object#Object()
	 */
	public DTO()
	{
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return Reflector.objectToString(this);
	}

}
