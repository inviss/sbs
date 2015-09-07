package com.app.das.business.exception;

/**
 * 우리 시스템에서 발생하는 Application Exception을 구현한 class
 * @author ysk523
 *
 */

public class DASException extends Exception 
{
	/**
	 * 에러코드
	 */
	private String exceptionCode;
	/**
	 * 에러메세지
	 */
	private String exceptionMsg;
	/**
	 * 원인
	 */
	private Throwable cause;
	
	/**
	 * 에러코드와 에러 메세지를 출력한다
	 */
	public DASException(String exceptionCode, String exceptionMsg)
	{
		super(exceptionMsg);
		
		this.exceptionCode = exceptionCode;
		this.exceptionMsg = exceptionMsg;
	}
	
	public DASException(String exceptionMsg, Throwable cause)
	{
		super(exceptionMsg, cause);
		
		this.exceptionMsg = exceptionMsg;
	}
	
	/**
	 * 에러코드와 원인, 에러 메세지를 출력한다
	 */
	public DASException(String exceptionCode, String exceptionMsg, Throwable cause)
	{
		super(exceptionMsg, cause);

		this.exceptionCode = exceptionCode;
		this.exceptionMsg = exceptionMsg;
	}

	public String getExceptionCode() 
	{
		return exceptionCode;
	}
	
	public void setExceptionCode(String exceptionCode) 
	{
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMsg() 
	{
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) 
	{
		this.exceptionMsg = exceptionMsg;
	}
	
    public final Throwable getCausedByException()
    {
        return cause;
    }
	
}
