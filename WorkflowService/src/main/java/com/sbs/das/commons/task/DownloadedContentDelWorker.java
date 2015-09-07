package com.sbs.das.commons.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbs.das.commons.exception.ApplicationException;
import com.sbs.das.services.DeleteContentAdapter;

@Component("downloadedContentDelWorker")
public class DownloadedContentDelWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DeleteContentAdapter deleteContentAdapter;
	
	public void work() {
		String threadName = Thread.currentThread().getName();
		if(logger.isInfoEnabled()) {
			logger.info("Scheduling Start - DownloadedContentDelete - thread[" + threadName + "] has began working.");
		}
		try {
			deleteContentAdapter.downloadExpiredDelete(null, null);
		} catch (ApplicationException ae) {
			logger.error("UserRequestContentDelete Error", ae);
		}
		if(logger.isInfoEnabled()) {
			logger.info("Scheduling end - DownloadedContentDelete - thread[" + threadName + "] has completed work.");
		}
	}

}
