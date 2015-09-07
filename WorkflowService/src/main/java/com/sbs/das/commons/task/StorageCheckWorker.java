package com.sbs.das.commons.task;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.sbs.das.commons.utils.UserFilenameFilter;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.ContentDownDao;
import com.sbs.das.repository.ContentInstMetaDao;

@Component("storageCheckWorker")
public class StorageCheckWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ContentInstMetaDao contentInstMetaDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private ContentDownDao contentDownDao;

	public void work() {
		String threadName = Thread.currentThread().getName();
		if(logger.isInfoEnabled()) {
			logger.info("Scheduling Start - StorageCheck - thread[" + threadName + "] has began working.");
		}
		try {

			String rootDir = "";
			String[] companys = new String[3];
			String[] subDirs = new String[2];
			if(SystemUtils.IS_OS_WINDOWS) {
				rootDir = "X:/"+messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA).replaceAll("\\/", "");
			} else {
				rootDir = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA);
			}
			companys[0] = messageSource.getMessage("das.mpeg2.path", null, Locale.KOREA).replaceAll("\\/", "");
			companys[1] = messageSource.getMessage("net.mpeg2.path", null, Locale.KOREA).replaceAll("\\/", "");
			companys[2] = messageSource.getMessage("das.restore.prefix", null, Locale.KOREA).replaceAll("\\/", "");
			
			subDirs[0] = messageSource.getMessage("onair.context.path", null, Locale.KOREA).replaceAll("\\/", "");
			subDirs[1] = messageSource.getMessage("trans.context.path", null, Locale.KOREA).replaceAll("\\/", "");

			Map<String, Object> params = new HashMap<String, Object>();
			
			for(String company : companys) {
				/*
				 * 기존 SBS 영상관리에 사용하던 폴더구조
				 */
				if(company.length() == 6 && Utility.isValidDate("yyyyMM", company)) {
					File ddDirs = new File(rootDir+File.separator+company);
					String[] ddNames = ddDirs.list();

					// 하위의 파일이 없을경우 삭제
					if(ddNames == null || ddNames.length <= 0) {
						try {
							Utility.fileForceDelete(ddDirs);
							if(logger.isDebugEnabled()) {
								logger.debug("delete date(yyyyMM) folder path: "+ddDirs.getAbsolutePath());
							}
						} catch (Exception e) {
							logger.error("파일 or 폴더 삭제중 오류발생", e);
						}

					} else {
						for(String ddName : ddNames) {
							// 파일명이 일(日) - (01 ~ 31) 이고 날짜형식일경우 진행
							if(ddName.length() == 2 && Utility.isValidDate("dd", ddName)) {
								File ddDir = new File(ddDirs.getAbsolutePath()+File.separator+ddName);
								String[] mxfNames = ddDir.list(new UserFilenameFilter());

								// 하위의 파일이 없을경우 삭제
								if(mxfNames == null || mxfNames.length <= 0) {
									try {
										Utility.fileForceDelete(ddDir);
										if(logger.isDebugEnabled()) {
											logger.debug("delete date(dd) folder path: "+ddDir.getAbsolutePath());
										}
									} catch (Exception e) {
										logger.error("파일 or 폴더 삭제중 오류발생", e);
									}
								} else {
									
									String uniqueId = "";
									for(String mxf : mxfNames) {
										params.clear();
										
										int count = 0;
										if(mxf.startsWith("P")) {
											uniqueId = mxf.substring(0, mxf.indexOf("_"));
											
											params.put("mediaId", uniqueId);
											count = contentDao.getContentCount(params);
										} else if(mxf.startsWith("D")) {
											uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
											
											params.put("mediaId", uniqueId);
											count = contentDao.getContentCount(params);
										} else if(mxf.startsWith("N")) {
											uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
											
											params.put("mediaId", uniqueId);
											count = contentDao.getContentCount(params);
										} else {
											uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
											
											params.put("ctiId", uniqueId);
											count = contentInstMetaDao.getContentInstCount(params);
										}
										
										// DB에 해당 영상이 존재하지 않으므로 삭제처리함.
										if(count <= 0) {
											if(logger.isDebugEnabled()) {
												logger.debug("DB not exist - delete mxf file path: "+ddDir.getAbsolutePath()+File.separator+mxf);
											}
											Utility.fileForceDelete(ddDir.getAbsolutePath()+File.separator+mxf);
										}
									}
								}
							} else continue;
						}
					}
				} else if("restore".equals(company)) {
					File userDirs = new File(rootDir+File.separator+company);
					String[] userIds = userDirs.list();

					for(String userId : userIds) {
						// 폴더명[user_id]
						File userIdDir = new File(userDirs.getAbsolutePath()+File.separator+userId);
						String[] cartNames = userIdDir.list();

						// 하위의 파일이 없을경우 삭제
						if(cartNames == null || cartNames.length <= 0) {
							try {
								Utility.fileForceDelete(userIdDir);
								if(logger.isDebugEnabled()) {
									logger.debug("delete userIdDir folder path: "+userIdDir);
								}
							} catch (Exception e) {
								logger.error("파일 or 폴더 삭제중 오류발생", e);
							}
						} else {
							for(String cartNo : cartNames) {
								// 폴더명[user_id]
								File cartNoDir = new File(userIdDir.getAbsolutePath()+File.separator+cartNo);
								String[] mxfFiles = cartNoDir.list(new UserFilenameFilter());

								// 하위의 파일이 없을경우 삭제
								if(mxfFiles == null || mxfFiles.length <= 0) {
									try {
										Utility.fileForceDelete(cartNoDir);
										if(logger.isDebugEnabled()) {
											logger.debug("delete cartNoDir folder path: "+cartNoDir.getAbsolutePath());
										}
									} catch (Exception e) {
										logger.error("파일 or 폴더 삭제중 오류발생", e);
									}
								} else {
									/*
									 * cartNo + 파일명으로 DB에서 조회를 하여 존재여부를 판단한다.
									 * where cart_no = #cartNo# and filename like #mxf%#
									 * count가 0이면 삭제처리
									 */
									for(String mxf : mxfFiles) {
										params.clear();
										int count = 0;
										
										params.put("cartNo", cartNo);
										params.put("filename", mxf.substring(0, mxf.lastIndexOf("."))+"%");
										
										count = contentDownDao.getContentDownCount(params);
										if(count <= 0) {
											if(logger.isDebugEnabled()) {
												logger.debug("DB not exist - delete mxf file path: "+cartNoDir.getAbsolutePath()+File.separator+mxf);
											}
											Utility.fileForceDelete(cartNoDir.getAbsolutePath()+File.separator+mxf);
										}
									}
								}
							}
						}
					}
				} else {
					for(String subDir : subDirs) {
						File dirs = new File(rootDir+File.separator+company+File.separator+subDir);
						String[] folds = dirs.list();
						for(String yyyymmName : folds) {
							// 파일명이 년월(201101) 이고 날짜형식일경우 진행
							if(yyyymmName.length() == 6 && Utility.isValidDate("yyyyMM", yyyymmName)) {
								File ddDirs = new File(dirs.getAbsolutePath()+File.separator+yyyymmName);
								String[] ddNames = ddDirs.list();

								// 하위의 파일이 없을경우 삭제
								if(ddNames == null || ddNames.length <= 0) {
									try {
										Utility.fileForceDelete(ddDirs);
										if(logger.isDebugEnabled()) {
											logger.debug("delete date(yyyyMM) folder path: "+ddDirs.getAbsolutePath());
										}
									} catch (Exception e) {
										logger.error("파일 or 폴더 삭제중 오류발생", e);
									}

								} else {
									for(String ddName : ddNames) {
										// 파일명이 일(日) - (01 ~ 31) 이고 날짜형식일경우 진행
										if(ddName.length() == 2 && Utility.isValidDate("dd", ddName)) {
											File ddDir = new File(ddDirs.getAbsolutePath()+File.separator+ddName);
											String[] mxfNames = ddDir.list(new UserFilenameFilter());

											// 하위의 파일이 없을경우 삭제
											if(mxfNames == null || mxfNames.length <= 0) {
												try {
													Utility.fileForceDelete(ddDir);
													if(logger.isDebugEnabled()) {
														logger.debug("delete date(dd) folder path: "+ddDir.getAbsolutePath());
													}
												} catch (Exception e) {
													logger.error("파일 or 폴더 삭제중 오류발생", e);
												}
											} else {
												
												String uniqueId = "";
												for(String mxf : mxfNames) {
													params.clear();
													
													int count = 0;
													if(mxf.startsWith("P")) {
														uniqueId = mxf.substring(0, mxf.indexOf("_"));
														
														params.put("mediaId", uniqueId);
														count = contentDao.getContentCount(params);
													} else if(mxf.startsWith("D")) {
														if(mxf.lastIndexOf("_") > -1)
															uniqueId = mxf.substring(0, mxf.lastIndexOf("_"));
														else
															uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
														
														params.put("mediaId", uniqueId);
														count = contentDao.getContentCount(params);
													} else if(mxf.startsWith("N")) {
														if(mxf.lastIndexOf("_") > -1)
															uniqueId = mxf.substring(0, mxf.lastIndexOf("_"));
														else
															uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
														
														params.put("mediaId", uniqueId);
														count = contentDao.getContentCount(params);
													} else if(mxf.startsWith("M")) {
														if(mxf.lastIndexOf("_") > -1)
															uniqueId = mxf.substring(0, mxf.lastIndexOf("_"));
														else
															uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
														
														params.put("mediaId", uniqueId);
														count = contentDao.getContentCount(params);
													} else {
														uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
														
														params.put("ctiId", uniqueId);
														count = contentInstMetaDao.getContentInstCount(params);
													}
													
													// DB에 해당 영상이 존재하지 않으므로 삭제처리함.
													if(count <= 0) {
														if(logger.isDebugEnabled()) {
															logger.debug("DB not exist - delete mxf file path: "+ddDir.getAbsolutePath()+File.separator+mxf);
														}
														Utility.fileForceDelete(ddDir.getAbsolutePath()+File.separator+mxf);
													}
												}
											}
										} else continue;
									}
								}
							} else {
								continue;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(logger.isInfoEnabled()) {
			logger.info("Scheduling end - StorageCheck - thread[" + threadName + "] has completed work.");
		}
	}

}
