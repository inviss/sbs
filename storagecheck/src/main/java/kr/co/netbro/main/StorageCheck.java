package kr.co.netbro.main;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kr.co.d2net.commons.SpringApplication;
import kr.co.d2net.commons.system.WorkflowService;
import kr.co.d2net.commons.utils.UserFilenameFilter;
import kr.co.d2net.commons.utils.Utility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.ContentDownDao;

public class StorageCheck {
	private static final Logger logger = LoggerFactory.getLogger(StorageCheck.class);

	public static void main(String[] args) {
		try {
			SpringApplication springApplication = SpringApplication.getInstance();

			MessageSource messageSource = (MessageSource)springApplication.get("messageSource");
			ContentDao contentDao = (ContentDao)springApplication.get("contentDao");
			ContentDownDao contentDownDao = (ContentDownDao)springApplication.get("contentDownDao");
			WorkflowService workflowService = (WorkflowService)springApplication.get("workflowService");

			String rootDir = "";
			String[] companys = new String[3];
			String[] subDirs = new String[2];

			if (SystemUtils.IS_OS_WINDOWS) {
				rootDir = messageSource.getMessage("win.net.mpeg2.drive", null, Locale.KOREA).replaceAll("\\/", "");
				System.out.println(rootDir);
			} else {
				rootDir = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA); // /nearline
			}
			companys[0] = messageSource.getMessage("das.mpeg2.path", null, Locale.KOREA).replaceAll("\\/", ""); // /SBS
			companys[1] = messageSource.getMessage("net.mpeg2.path", null, Locale.KOREA).replaceAll("\\/", ""); // /MediaNet
			companys[2] = messageSource.getMessage("das.restore.prefix", null, Locale.KOREA).replaceAll("\\/", ""); // /restore

			subDirs[0] = messageSource.getMessage("onair.context.path", null, Locale.KOREA).replaceAll("\\/", ""); // /onAir
			subDirs[1] = messageSource.getMessage("trans.context.path", null, Locale.KOREA).replaceAll("\\/", ""); // /manual

			Map<String, Object> params = new HashMap<String, Object>();

			for (String company : companys) {

				if ("restore".equals(company)) {
					File userDirs = new File(rootDir + File.separator + company);
					String[] userIds = userDirs.list();

					if (userIds == null)
						continue;
					for (String userId : userIds) {
						
						if("mp4".equals(userId)) continue;
						
						if (logger.isDebugEnabled()) {
							logger.debug("restore: " + userDirs.getAbsolutePath() + File.separator + userId);
						}
						File userIdDir = new File(userDirs.getAbsolutePath() + File.separator + userId);
						String[] cartNames = userIdDir.list();

						if ((cartNames == null) || (cartNames.length <= 0)) {
							try {
								Utility.fileForceDelete(userIdDir);
								if (logger.isDebugEnabled())
									logger.debug("delete userIdDir folder path: " + userIdDir);
							} catch (Exception e) {
								logger.error("파일 or 폴더 삭제중 오류발생", e);
							}
						} else {
							for(String cartNo : cartNames) {
								File cartNoDir = new File(userIdDir.getAbsolutePath() + File.separator + cartNo);
								String[] mxfFiles = cartNoDir.list(new UserFilenameFilter());

								if (mxfFiles == null || mxfFiles.length <= 0) {
									try {
										Utility.fileForceDelete(cartNoDir);
										if (logger.isDebugEnabled())
											logger.debug("delete cartNoDir folder path: " + cartNoDir.getAbsolutePath());
									} catch (Exception e) {
										logger.error("파일 or 폴더 삭제중 오류발생", e);
									}
								} else {
									for (String mxf1 : mxfFiles) {
										params.clear();
										int count = 0;

										params.put("cartNo", cartNo);
										params.put("filename", mxf1.substring(0, mxf1.lastIndexOf(".")) + "%");

										count = contentDownDao.getContentDownCount(params).intValue();
										if (mxf1.startsWith("archive")) {
											if (logger.isDebugEnabled()) {
												logger.debug("filename: " + mxf1 + ", count1: " + count);
											}
											count = 1;
											if (logger.isDebugEnabled()) {
												logger.debug("filename: " + mxf1 + ", count2: " + count);
											}
										}
										if (count <= 0) {
											if (logger.isDebugEnabled()) {
												logger.debug("DB not exist - delete mxf file path: " + cartNoDir.getAbsolutePath() + File.separator + mxf1);
											}
											Utility.fileForceDelete(cartNoDir.getAbsolutePath() + File.separator + mxf1);
										}
									}
								}
							}
						}
					}
				} else {
					// 아카이브 요청을 위해 선언
					StringBuffer xml = new StringBuffer();
					
					for(String subDir : subDirs) {
						File dirs = new File(rootDir + File.separator + company + File.separator + subDir);
						if (logger.isDebugEnabled())
							logger.debug(dirs.getAbsolutePath());

						String[] yyyymmNames = dirs.list();
						Arrays.sort(yyyymmNames); // 정렬

						if (yyyymmNames == null || yyyymmNames.length <= 0) {
							continue;
						} else {
							for(String yyyymmName : yyyymmNames) { // 201601

								if ((yyyymmName.length() == 6) && (Utility.isValidDate("yyyyMM", yyyymmName))) {
									if (logger.isDebugEnabled())
										logger.debug(dirs.getAbsolutePath() + File.separator + yyyymmName);

									File ddDirs = new File(dirs.getAbsolutePath() + File.separator + yyyymmName);
									String[] ddNames = ddDirs.list();
									Arrays.sort(ddNames); // 정렬

									if ((ddNames == null) || (ddNames.length <= 0)) {
										try {
											Utility.fileForceDelete(ddDirs);
											if (logger.isDebugEnabled())
												logger.debug("delete date(yyyyMM) folder path: " + ddDirs.getAbsolutePath());
										} catch (Exception e) {
											logger.error("파일 or 폴더 삭제중 오류발생", e);
										}
									} else {
										for(String ddName : ddNames) {
											if ((ddName.length() == 2) && (Utility.isValidDate("dd", ddName))) {
												File ddDir = new File(ddDirs.getAbsolutePath() + File.separator + ddName);
												String[] mxfNames = ddDir.list(new UserFilenameFilter());

												if ((mxfNames == null) || (mxfNames.length <= 0)) {
													try {
														Utility.fileForceDelete(ddDir);
														if (logger.isDebugEnabled())
															logger.debug("delete date(dd) folder path: " + ddDir.getAbsolutePath());
													} catch (Exception e) {
														logger.error("파일 or 폴더 삭제중 오류발생", e);
													}
												} else {
													String uniqueId = null;
													String fileNm = null;
													for (String mxf : mxfNames) {
														params.clear();
														int count = 1;
														if (mxf.startsWith("P")) {
															uniqueId = mxf.substring(0, mxf.indexOf("_"));
															params.put("mediaId", uniqueId);
															fileNm = mxf.substring(0, mxf.lastIndexOf("."));
															params.put("fileNm", fileNm+"%");
														} else if (mxf.startsWith("D")) {
															if (mxf.lastIndexOf("_") > -1)
																uniqueId = mxf.substring(0, mxf.lastIndexOf("_"));
															else {
																uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
															}
															params.put("mediaId", uniqueId);
														} else if (mxf.startsWith("N")) {
															if (mxf.lastIndexOf("_") > -1)
																uniqueId = mxf.substring(0, mxf.lastIndexOf("_"));
															else {
																uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
															}
															params.put("mediaId", uniqueId);
														} else if (mxf.startsWith("M")) {
															if (mxf.lastIndexOf("_") > -1)
																uniqueId = mxf.substring(0, mxf.lastIndexOf("_"));
															else {
																uniqueId = mxf.substring(0, mxf.lastIndexOf("."));
															}
															params.put("mediaId", uniqueId);
														} else if (mxf.startsWith("archive")) {
															String fileName = mxf.substring(0, mxf.lastIndexOf("."));
															params.put("fileNm", fileName + "%");
														} else {
															uniqueId = mxf.substring(0, mxf.lastIndexOf("."));

															params.put("ctiId", uniqueId);
														}

														params.put("wait", true);
														ContentTbl contentTbl = contentDao.getContentCheck(params);
														if(contentTbl != null) {
															if(contentTbl.getMasterId() == null || contentTbl.getCtId() == null || contentTbl.getCtiId() == null) {

																if(contentTbl.getMasterId() == null && (contentTbl.getCtId() != null && contentTbl.getCtiId() != null)) {
																	params.put("ctId", contentTbl.getCtId());
																	Long masterId = contentDao.getMasterJoinCheck(params);
																	if(masterId != null) {
																		params.put("masterId", masterId);
																		ContentMapTbl contentMapTbl = contentDao.getMasterCornerMeta(params);
																		if(contentMapTbl != null) {
																			contentDao.insertContentMap(contentMapTbl);
																			logger.debug("master_id: "+masterId+"가 contents_mapp_tbl에 등록되었습니다.");
																		} else {
																			logger.debug(ddDir.getAbsolutePath()+File.separator+mxf+" mapp 테이블 누락. master_id: "+masterId+", ct_id: "+contentTbl.getCtId());
																		}
																	} else {
																		count = 0;
																		logger.debug(ddDir.getAbsolutePath()+File.separator+mxf+" 가 일부 테이블 정보가 없습니다.");
																	}
																} else {
																	count = 0;
																	logger.debug(ddDir.getAbsolutePath()+File.separator+mxf+" 가 일부 테이블 정보가 없습니다.");
																}
															} else {
																if(contentTbl.getJobStatus() != null) {
																	// 스케줄러에의해 삭제가 되었어야 할 대상
																	if(StringUtils.isBlank(contentTbl.getFlPath())) {
																		if(contentTbl.getJobStatus().equals("C")) {
																			logger.debug(ddDir.getAbsolutePath()+File.separator+mxf+" 가 파일 경로가 없습니다.");
																			count = 0;
																		} else {
																			// 아카이브 요청이 실패난 건임. 재요청(?)
																			logger.debug(ddDir.getAbsolutePath()+File.separator+mxf+" 가 아카이브 실패된 파일임.");
																		}
																	}
																} else {
																	/*
																	 * 아카이브 요청이 없는 콘텐츠
																	 * 사용자가 아카이브 요청을 안한 것은 삭제 대상 아님.
																	 * 단, [2016.02.15]
																	 * SBS/onAir 파일의 경우 자동 아카이브 대상이므로 아카이브 요청이 안된 것은
																	 * 시스템의 문제였을 가능성이 큼. 즉, 발견즉시 아카이브 하도록 함.
																	 */
																	if("onAir".equals(subDir)) {
																		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><info><archive_type>all</archive_type>");
																		if("SBS".equals(company)) {
																			String flpath = ddDir.getAbsolutePath() + File.separator + mxf;
																			flpath = flpath.replaceAll("\\\\", "/").substring(flpath.indexOf("SBS"));
																			flpath = (flpath.startsWith("/")) ? "/nearline"+flpath : "/nearline/"+flpath;
																			
																			xml.append("<cti_id>"+uniqueId+"</cti_id>");
																			xml.append("<file_path>"+flpath+"</file_path>");
																			xml.append("<dtl_type>das</dtl_type>");   // medianet
																		} else {
																			// MediaNet OnAir???
																		}
																		xml.append("</info></das>");
																		
																		try {
																			if(xml.indexOf("cti_id") > -1) {
																				workflowService.regiesterArchive(xml.toString());
																				logger.debug("The content is registering to the Diva flatform: "+ddDir.getAbsolutePath() + File.separator + mxf);
																			}
																		} catch (Exception e) {
																			logger.error("archive request error", e);
																		} finally {
																			xml.setLength(0);
																		}
																	} else {
																		if(StringUtils.isBlank(contentTbl.getFlPath())) {
																			logger.debug(ddDir.getAbsolutePath()+File.separator+mxf+" 가 등록 실패된 파일임.");
																			count = 0;
																		}
																	}
																}
															}
														} else {
															logger.debug(ddDir.getAbsolutePath()+File.separator+mxf+" 가 DB 조회가 안됨.");
															count = 0;
														}

														if (count <= 0) {
															Utility.fileForceDelete(ddDir.getAbsolutePath() + File.separator + mxf);
														}

														contentTbl = null;
														uniqueId = null;
														fileNm = null;
													}
												}
											}
										}
									}
								}
							}

						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("스토리지 체크 구동 중 에러발생", e);
		}
	}
}
