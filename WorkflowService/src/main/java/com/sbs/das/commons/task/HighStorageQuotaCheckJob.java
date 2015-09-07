package com.sbs.das.commons.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <pre>
 * 자동.수동 요청으로 아카이브가 완료된 컨텐츠에 한하여 고화질 영상의 경로를 Blank('') 처리.
 * </pre>
 * @author  M.S. Kang
 *
 */
public class HighStorageQuotaCheckJob extends QuartzJobBean implements StatefulJob {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private Worker worker;
	
	private ApplicationContext ctx;
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
	throws JobExecutionException {

		worker = (Worker)ctx.getBean("highStorageQuotaCheckWorker");
		
		worker.work();
	}
	
}
