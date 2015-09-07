package com.app.das.util;

import java.text.ParseException;
/**
 * Application Exception 으로 사용자가 입력한 날자가 양력 상에서 실존하지 않을 경우 던져진다<br>
 * 또 다른 경우로 DB 에서 로드한 날자 데이터는 HolidayManager 의 holidays,holiDesc 클래스 변수에 초기화 시
 * 일정한 범위만큼 저장이 된다<br>예를 들면, DB 에 1980년부터 2020 년까지의 40년간 휴일정보가 저장되어 있을 경우
 * 어플리케이션 초기화 시 DB 로부터 어플리케이션에 날자 정보를 원하는 범위 - 1995년부터 2005 년까지 - 를 로드해 올 수 있다 *
 * 이 때, holidays, holiDesc 에 로드해 오지 못하고 비어 있을 경우 이 Exception 이 던져진다
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @version 1.0
 * @see HolidayManager
 * @see DateSet
 */
public class NonExistDateException extends ParseException 
{

	public NonExistDateException(String s) 
	{
		this(s, -1);
	}

	public NonExistDateException(String s,int errOffset) 
	{
		super(s, errOffset);
	}
}
