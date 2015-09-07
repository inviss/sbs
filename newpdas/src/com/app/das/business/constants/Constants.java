package com.app.das.business.constants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * 기본적인 상수 정의 class
 * @author ysk523
 *
 */
public final class Constants implements Serializable
{
	/**
	 * System.getProperties().getProperty("line.separator")<br>
	 * 한 줄 내리기
	 */
	public static final String LINE_SEPARATOR = System.getProperties().getProperty("line.separator");

	/**
	 * ""<br>
	 * 빈 문자열
	 */
	public static final String BLANK = "";

	/**
	 * new BigDecimal("0.0")<br>
	 * 값이 0인 BigDecimal
	 */
	public static final BigDecimal ZERO = new BigDecimal("0");

	public static final BigDecimal ONE = new BigDecimal("1");

	public static final BigDecimal HUNDRED = new BigDecimal("100");

	public static final BigDecimal ZERO_DOT_ONE = new BigDecimal("0.1");



	
	/**
	 * 이 class는 생성할 수 없다.
	 */
	private Constants()
	{
	}
}
