package com.sbs.das.commons.system;

import com.sbs.das.commons.exception.ApplicationException;
import com.sbs.das.dto.xml.DeleteRequest;

public interface MessageListener {
	/**
	 * Thread initialize
	 * @throws ApplicationException
	 */
	public void start() throws ApplicationException;
	
	/**
	 * <pre>
	 * 입력받은 ArchiveRequest 객체를 BlockingQueue 변수에 저장한다.
	 * 사용자 요청시간을 nono초 단위로 비교하여 먼저 들어온 요청건이 먼저 처리되도록 한다.
	 * </pre>
	 * @param msg
	 * @throws ApplicationException
	 */
	public void mput(DeleteRequest request) throws ApplicationException;
	
	/**
	 * Thread Stop
	 * @throws ApplicationException
	 */
	public void stop() throws ApplicationException;
}
