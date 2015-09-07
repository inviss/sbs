package com.sbs.das.commons.exception;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * String 문자열인 XML을 Dom 문서로 변환하는 과정에서 에러가 발생했을경우 반환
 * </pre>
 * @author Administrator
 *
 */
public class XmlParsingException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;

	public XmlParsingException(String message) {
		super(message);
	}

	public XmlParsingException(Throwable cause) {
		super(cause);
	}

	public XmlParsingException(String errorCode, String message) {
		super(errorCode, message);
	}

	public XmlParsingException(String message, Throwable cause) {
		super((StringUtils.isEmpty(message))?cause.getMessage():message, cause);
	}
	
	public XmlParsingException(String errorCode, String message, Throwable cause) {
		super(errorCode, (StringUtils.isEmpty(message))?cause.getMessage():message, cause);
	}

}
