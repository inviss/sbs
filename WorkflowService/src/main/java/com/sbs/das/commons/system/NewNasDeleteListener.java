package com.sbs.das.commons.system;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sbs.das.commons.exception.ApplicationException;
import com.sbs.das.dto.xml.DeleteRequest;

public class NewNasDeleteListener implements MessageListener {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private static BlockingQueue<DeleteRequest> queue = new PriorityBlockingQueue<DeleteRequest>(300, getCompare());

	private ExecutorService thread = Executors.newSingleThreadExecutor();

	public void start() throws ApplicationException {
		thread.execute(new ExecuteThread());
	}

	public void mput(DeleteRequest request) throws ApplicationException {

	}

	class ExecuteThread implements Runnable {

		public void run() {
			if(logger.isInfoEnabled()) {
				logger.info("DTLDeleteCycle start now!!");
			}

			while(true) {
				boolean m2_del = true;
				try {
					DeleteRequest request = queue.take();

					char reqGb = request.getReqGb().charAt(0);
					switch(reqGb) {
					
					}
				} catch (InterruptedException ie) {
					logger.error("Thread stop!", ie);
				} catch (Exception e) {
					logger.error("message get Error!!", e);
				}

				try {
					Thread.sleep(100L);
				} catch (Exception e) {}
			}
		}

	}

	public void stop() throws ApplicationException {

	}

	/**
	 * <pre>
	 * Queue 등록시 요청시간을 비교해서 순위를 정한다.
	 * 요청시간은 사용자별 삭제 요청시간이다.
	 * </pre>
	 * @return
	 */
	private static Comparator<? super DeleteRequest> getCompare() {
		return new Comparator<DeleteRequest>() {
			public int compare(DeleteRequest d1, DeleteRequest d2) {
				if(Long.valueOf(d1.getReqDt()) < Long.valueOf(d2.getReqDt())) return -1;
				else return 1;
			}
		};
	}

}
