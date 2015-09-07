package com.sbs.das.commons.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.hyperic.sigar.DirUsage;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.StorageInfoTbl;
import com.sbs.das.repository.StorageInfoDao;

@Component("highStorageQuotaCheckWorker")
public class HighStorageQuotaCheckWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private StorageInfoDao storageInfoDao;

	public void work() {
		String[] info = {};
		try {
			new Du().processCommand(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class Du extends SigarCommandBase {

		public boolean displayTimes = true;

		public Du(Shell shell) {
			super(shell);
		}

		public Du() {
			super();
		}

		public String getUsageShort() {
			return "Display cpu information";
		}

		@Override
		public void output(String[] paths) throws SigarException {

			if(!SystemUtils.IS_OS_WINDOWS) {
				String dasPrefix = messageSource.getMessage("das.mpeg2.drive", null, Locale.KOREA);
				String netPrefix = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA);
				List<StorageInfoTbl> storages = null;
				try {
					
					// 고용량 영상 스토리지의 최상위 MP2의 총 사이즈를 구한다.
					String[] root = { "/bin/sh", "-c", "df -k | grep "+netPrefix};
					Process ps = Runtime.getRuntime().exec(root);
					ps.waitFor();
					BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
					
					String line = "";
					while ((line = br.readLine()) != null) {
						String[] gu = StringUtils.splitByWholeSeparator(line, null	, 0);
						//Utility.QUOTA_CHECK.put(DASConstants.HIGH_STORAGE_PRIFIX, gu[1]+","+gu[2]+","+gu[3]);
						Utility.QUOTA_CHECK.put(netPrefix, Long.valueOf(gu[1]));
						if(logger.isDebugEnabled()) {
							logger.debug(netPrefix+" - "+Utility.QUOTA_CHECK.get(netPrefix));
						}
					}
					
					storages = storageInfoDao.findStorageInfo(null);

					//String[] dirs = {"/mp2/SBS", "/mp2/SBS/onAir", "/mp2/SBS/manual", "/mp2/MediaNet", "/mp2/MediaNet/onAir", "/mp2/MediaNet/manual"};
					//String[] dirs = {"/nearline/SBS", "/nearline/SBS/onAir", "/nearline/SBS/manual", "/nearline/MediaNet", "/nearline/MediaNet/onAir", "/nearline/MediaNet/manual"};
					for(StorageInfoTbl storageInfoTbl : storages) {
						storageInfoTbl.setPath(storageInfoTbl.getPath());
						storageInfoTbl.setLastModified(Utility.getTimestamp("yyyyMMddHHmmss"));

						DirUsage dirUsage = this.sigar.getDirUsage(storageInfoTbl.getPath());
						storageInfoTbl.setUseVolume((dirUsage.getDiskUsage()/1024));
						
						long passibleSize = ((storageInfoTbl.getTotalVolume() * Integer.valueOf(storageInfoTbl.getLimit())) / 100) - storageInfoTbl.getUseVolume();
						
						storageInfoTbl.setPassibleVolume((passibleSize <= 0) ? 0 : passibleSize);
						storageInfoDao.updateStorageInfo(storageInfoTbl);
						
						Utility.QUOTA_CHECK.put(storageInfoTbl.getPath(), storageInfoTbl.getUseVolume());
						if(logger.isDebugEnabled()) {
							logger.debug(storageInfoTbl.getPath()+" - "+Utility.QUOTA_CHECK.get(storageInfoTbl.getPath()));
						}
					}
				} catch (Exception e) {
					logger.error("HighStorageQuotaCheckWorker", e);
				}
			}
		}

	}

	/*
	public void work() {
		try {
			logger.debug("highStorageQuotaCheckWorker - start");
			String line = "";
			if(!SystemUtils.IS_OS_WINDOWS) {
				// 고용량 영상 스토리지의 최상위 MP2의 총 사이즈를 구한다.
				String[] root = { "/bin/sh", "-c", "df -k | grep "+DASConstants.HIGH_STORAGE_PRIFIX};
				Process ps = Runtime.getRuntime().exec(root);
				ps.waitFor();
				BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
				while ((line = br.readLine()) != null) {
					String[] gu = StringUtils.splitByWholeSeparator(line, null	, 0);
					//Utility.QUOTA_CHECK.put(DASConstants.HIGH_STORAGE_PRIFIX, gu[1]+","+gu[2]+","+gu[3]);
					Utility.QUOTA_CHECK.put(DASConstants.HIGH_STORAGE_PRIFIX, gu[1]);
					logger.debug("/mp2 - "+Utility.QUOTA_CHECK.get("mp2"));
				}

				//String[] dirs = {"/mp2/SBS", "/mp2/SBS/onAir", "/mp2/SBS/manual", "/mp2/MediaNet", "/mp2/MediaNet/onAir", "/mp2/MediaNet/manual"};
				List<StorageInfoTbl> storages = storageInfoDao.findStorageInfo(null);
				for(StorageInfoTbl storageInfoTbl : storages) {

					storageInfoTbl.setPath(storageInfoTbl.getPath());
					storageInfoTbl.setLastModified(Utility.getTimestamp("yyyyMMddHHmmss"));

					root[2] = "du -s "+storageInfoTbl.getPath();
					ps = Runtime.getRuntime().exec(root);
					ps.waitFor();
					br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
					if ((line = br.readLine()) != null) {
						String[] gu = StringUtils.splitByWholeSeparator(line, null	, 0);
						Utility.QUOTA_CHECK.put(gu[1], gu[0]);
						logger.debug(gu[1]+" - "+gu[0]);

						storageInfoTbl.setUseVolume(Long.valueOf(gu[0]));

						storageInfoDao.updateStorageInfo(storageInfoTbl);
					}
				}
			}
		} catch (Exception e) {
			logger.debug("HighStorageQuotaCheckWorker", e);
		}
	}
	 */
}
