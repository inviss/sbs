package com.sbs.das.commons.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 사용중인 스토리지에서 Workflow에서 관리되고 사용되어지는 영역에서
 * 파일이 없는 빈폴더를 제거한다.
 * </pre>
 * @author  M.S. Kang
 */
@Component(value="storageCheckJob")
public class StorageCheckJob extends QuartzJobBean implements StatefulJob {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Worker worker;
	
	private ApplicationContext ctx;
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.ctx = applicationContext;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
	throws JobExecutionException {
		
		worker = (Worker)ctx.getBean("storageCheckWorker");

		worker.work();

	}

}
