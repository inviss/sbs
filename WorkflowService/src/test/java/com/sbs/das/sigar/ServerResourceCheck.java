package com.sbs.das.sigar;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sbs.das.dto.xml.ServerResource;

public class ServerResourceCheck {
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void cpuCheck() {
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
		
		private Long format(long value) {
	        return new Long(value / 1024);
	    }
		
		private void output(CpuPerc cpu) {
			ServerResource resource = new ServerResource();
			resource.setIdleTime(Math.round(cpu.getIdle()*1000)/10.0);
			resource.setUserTime((Math.round((100.0 - resource.getIdleTime())*10)/10.0));
			
			logger.debug("idle : "+resource.getIdleTime()+", user: "+resource.getUserTime());
	    }
		
		@Override
		public void output(String[] arg0) throws SigarException {
			Mem mem   = this.sigar.getMem();
	        Swap swap = this.sigar.getSwap();
	        System.out.println(mem.getFreePercent());
	        System.out.println(Math.round(mem.getFreePercent()*10)/10.0);

	        printf("RAM:    %10ls", new Object[] { mem.getRam() + "MB" });
		}

	}
}
