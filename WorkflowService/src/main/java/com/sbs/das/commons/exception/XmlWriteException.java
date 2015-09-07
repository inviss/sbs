package com.sbs.das.commons.exception;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * XML형식의 String 문자열을 파일로 저장중 에러가 발생했을경우 반환
 * </pre>
 * @author Administrator
 *
 */
public class XmlWriteException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;

	public XmlWriteException(String message) {
		super(message);
	}

	public XmlWriteException(Throwable cause) {
		super(cause);
	}

	public XmlWriteException(String errorCode, String message) {
		super(errorCode, message);
	}

	public XmlWriteException(String message, Throwable cause) {
		super((StringUtils.isEmpty(message))?cause.getMessage():message, cause);
	}
	
	public XmlWriteException(String errorCode, String message, Throwable cause) {
		super(errorCode, (StringUtils.isEmpty(message))?cause.getMessage():message, cause);
	}

}
