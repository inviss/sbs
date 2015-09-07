package com.sbs.das.commons.task;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.xml.ServerResource;

@Component("cpuAndMemoryCheckWorker")
public class CpuAndMemoryCheckWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;

	public void work() {
		String[] info = {};
		try {
			new SigarInfo().processCommand(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class SigarInfo extends SigarCommandBase {

		public boolean displayTimes = true;

		public SigarInfo(Shell shell) {
			super(shell);
		}

		public SigarInfo() {
			super();
		}

		public String getUsageShort() {
			return "Display cpu information";
		}

		private void output(CpuPerc cpu, Mem mem) {
			ServerResource resource = new ServerResource();
			resource.setIdleTime(Math.round(cpu.getIdle()*1000)/10.0);
			resource.setUserTime((Math.round((100.0 - resource.getIdleTime())*10)/10.0));

			Utility.RES_CHECK.put("cfree", resource.getIdleTime());
			Utility.RES_CHECK.put("cused", resource.getUserTime());

			if(logger.isDebugEnabled()) {
				logger.debug("cpu free : "+Utility.RES_CHECK.get("cfree")+", cpu used: "+Utility.RES_CHECK.get("cused"));
			}

			Utility.RES_CHECK.put("mtotal", mem.getRam()+"M");
			Utility.RES_CHECK.put("mused", Math.round(mem.getUsedPercent()*10)/10.0);
			Utility.RES_CHECK.put("mfree", Math.round(mem.getFreePercent()*10)/10.0);

			if(logger.isDebugEnabled()) {
				logger.debug("memory total : "+mem.getRam());
				logger.debug("memory used : "+Math.round(mem.getUsedPercent()*10)/10.0);
				logger.debug("memory free : "+Math.round(mem.getFreePercent()*10)/10.0);
			}

			String resourcePath = messageSource.getMessage("das.resource.path", null, Locale.KOREA);
			String resourceName = messageSource.getMessage("das.resource.name", null, Locale.KOREA);
			File f = new File(resourcePath);
			if(!f.exists()) {
				f.mkdirs();
			}
			
			File info = new File(resourcePath+File.separator+resourceName+".res");

			RandomAccessFile rInfo = null;
			try {
				rInfo = new RandomAccessFile(info, "rw");
				rInfo.setLength(0);

				rInfo.writeBytes(Utility.RES_CHECK.get("cused")+"|");
				rInfo.writeBytes(Utility.RES_CHECK.get("cfree")+"|");
				rInfo.writeBytes(Utility.RES_CHECK.get("mused")+"|");
				rInfo.writeBytes(Utility.RES_CHECK.get("mfree")+"\r\n");
			} catch (Exception e) {
				logger.error("Resource Write Error", e);
			} finally {
				try {
					if(rInfo != null) rInfo.close();
				} catch (IOException e) {}
			}
		}

		@Override
		public void output(String[] arg0) throws SigarException {
			output(this.sigar.getCpuPerc(), this.sigar.getMem());
		}

	}
}
