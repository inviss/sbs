package com.app.das.util;
/**
 * Application Exception 으로 사용자가 입력한 날자 자체는 HolidayManager 의 휴일 정보 리스트의 범위안에는 있으나
 * 지정한 level 로 인하여 전후 휴일 또는 영업일이 휴일 정보 구간을 벗어날 때 던져진다<br>
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @version 1.0
 */
public class DateException extends Exception 
{

	public DateException() 
	{
	}

	public DateException(String s) 
	{
		super(s);
	}
}
