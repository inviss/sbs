package com.sbs.das.commons.exception;

import org.apache.commons.lang.StringUtils;

public class ServiceException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String errorCode, String message) {
		super(errorCode, message);
	}

	public ServiceException(String message, Throwable cause) {
		super((StringUtils.isEmpty(message))?cause.getMessage():message, cause);
	}
	
	public ServiceException(String errorCode, String message, Throwable cause) {
		super(errorCode, (StringUtils.isEmpty(message))?cause.getMessage():message, cause);
	}

}
