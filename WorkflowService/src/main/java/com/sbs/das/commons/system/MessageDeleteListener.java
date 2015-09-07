package com.sbs.das.commons.system;

import java.io.File;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.sbs.das.commons.exception.ApplicationException;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.xml.ArchiveRequest;
import com.sbs.das.dto.xml.DeleteRequest;
import com.sbs.das.services.DeleteContentService;

/**
 * <p>컨텐츠 삭제 쓰레드</p>
 * <pre>
 * 삭제요청 오브젝트를 받아 시간순으로 자동정렬을 하여 Queue에 저장하고 저장시간 역순으로 오브젝트를 꺼내(take) 처리한다.
 * 요청 Object는 (archive, download, user)로 구분되며 필요에따라 삭제 후 DB Update를 한다.
 * </pre>
 * @author M.S. Kang
 *
 */
public class MessageDeleteListener implements MessageListener {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private static BlockingQueue<DeleteRequest> queue = new PriorityBlockingQueue<DeleteRequest>(300, getCompare());
	private ExecutorService thread = Executors.newSingleThreadExecutor();

	@Autowired
	private DeleteContentService deleteContentService;
	@Autowired
	private DivaConnectSerivce divaConnectSerivce;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private MessageSource messageSource;

	public void start() throws ApplicationException {
		thread.execute(new ExecuteThread());
	}

	public void mput(DeleteRequest request) throws ApplicationException {
		try {
			queue.put(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class ExecuteThread implements Runnable {

		String dasPrefix = "";
		String netPrefix = "";

		public void run() {
			if(logger.isInfoEnabled()) {
				logger.info("DTLDeleteCycle start now!!");
			}

			dasPrefix = messageSource.getMessage("das.mpeg2.drive", null, Locale.KOREA);
			netPrefix = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA);

			while(true) {
				boolean m2_del = true;
				try {
					DeleteRequest request = queue.take();

					char reqGb = request.getReqGb().charAt(0);
					switch(reqGb) {
					case 'D':
						if(logger.isInfoEnabled()) {
							logger.info("-----------------------------download----------------------------------");
						}
						if(logger.isInfoEnabled()) {
							logger.info("cart_no     : "+request.getCartNo());
							logger.info("cart_seq    : "+request.getCartSeq());
							logger.info("high_cti_id : "+request.getHighCtiId());
							logger.info("high_path   : "+request.getHighPath());
							logger.info("high_fl_nm  : "+request.getHighFlNm());
						}


						try {
							// 고용량 영상이 존재한다면 삭제하고 path를 blank 처리한다.
							if(StringUtils.isNotBlank(request.getHighPath()) && StringUtils.isNotBlank(request.getHighFlNm())) {
								String path = request.getHighPath();
								if(path.indexOf("\\") > -1) path = path.replaceAll("\\\\", "/");
								if(!path.startsWith("/")) path = "/"+path;
								if(!path.endsWith("/")) path = path+"/";
								
								if(request.getHighPath().indexOf(dasPrefix) < 0) {
									request.setHighPath(dasPrefix+path);
								}

								File f = new File(request.getHighPath(), request.getHighFlNm());
								if(logger.isDebugEnabled()) {
									logger.debug("download del path: "+f.getAbsolutePath());
								}
								Utility.fileForceDelete(f);
							}

							deleteContentService.updateDeleteCtDown(request.getCartNo(), request.getCartSeq());
						} catch (Exception e) {
							logger.error("Download Delete Error", e);
							m2_del = false;
						}
						break;
					case 'A':
						if(logger.isInfoEnabled()) {
							logger.info("-----------------------------archive----------------------------------");
						}
						if(logger.isInfoEnabled()) {
							logger.info("high_cti_id    : "+request.getHighCtiId());
							logger.info("high_path      : "+request.getHighPath());
							logger.info("high_fl_nm     : "+request.getHighFlNm());
						}


						try {
							// /nearline/MediaNet/onAir, /nearline/MediaNet/manual, /nearline/SBS/onAir, /nearline/SBS/manual
							// 고용량 영상이 존재한다면 삭제하고 path를 blank 처리한다.
							if(StringUtils.isNotBlank(request.getHighPath()) && StringUtils.isNotBlank(request.getHighFlNm())) {
								String path = request.getHighPath();
								if(path.indexOf("\\") > -1) path = path.replaceAll("\\\\", "/");
								if(!path.startsWith("/")) path = "/"+path;
								if(!path.endsWith("/")) path = path+"/";

								File f = new File(path, request.getHighFlNm());
								if(logger.isDebugEnabled()) {
									logger.debug("dasPrefix		   : "+dasPrefix);
									logger.debug("archive del path : "+f.getAbsolutePath());
								}
								Utility.fileForceDelete(f);
							}

							deleteContentService.updateContentPathBlank(request.getHighCtiId());
						} catch (Exception e) {
							logger.error("Archive Delete Error", e);
						}

						break;
					case 'U':
						if(logger.isInfoEnabled()) {
							logger.info("-----------------------------user----------------------------------");
						}

						String lowDelYn = messageSource.getMessage("user.low.delete.yn", null, Locale.KOREA);
						String dtlDelYn = messageSource.getMessage("user.dtl.delete.yn", null, Locale.KOREA);

						if(logger.isInfoEnabled()) {
							logger.info("high_cti_id    : "+request.getHighCtiId());
							logger.info("high_path      : "+request.getHighPath());
							logger.info("high_fl_nm     : "+request.getHighFlNm());
							logger.info("low_cti_id     : "+request.getLowCtiId());
							logger.info("low_path       : "+request.getLowPath());
							logger.info("low_fl_nm      : "+request.getLowFlNm());
							logger.info("archive_ste_yn : "+request.getArchiveSteYn());
						}

						try {
							// 고용량 영상이 존재한다면 삭제하고 path를 blank 처리한다.
							if(StringUtils.isNotBlank(request.getHighPath()) && StringUtils.isNotBlank(request.getHighFlNm())) {
								String path = request.getHighPath();
								if(path.indexOf("\\") > -1) path = path.replaceAll("\\\\", "/");
								if(!path.startsWith("/")) path = "/"+path;
								if(!path.endsWith("/")) path = path+"/";

								File f = new File(path, request.getHighFlNm());
								if(logger.isDebugEnabled()) {
									logger.debug("user mxf del path: "+f.getAbsolutePath());
								}
								Utility.fileForceDelete(f);
							}
							deleteContentService.updateContentPathBlank(request.getHighCtiId());
						} catch (Exception e) {
							logger.error("User Delete Error", e);
							m2_del = false;
						}

						if(logger.isInfoEnabled()) {
							logger.info("lowDelYn       : "+lowDelYn);
						}
						if(lowDelYn.equals("Y")) {
							try {
								if(StringUtils.isNotBlank(request.getLowPath()) && StringUtils.isNotBlank(request.getLowFlNm())) {
									String path = request.getLowPath();
									if(!path.startsWith("/")) path = "/"+path;
									if(!path.endsWith("/")) path = path+"/";

									File f = new File(path);
									if(logger.isDebugEnabled()) {
										logger.debug("fource wmv del path: "+f.getAbsolutePath());
									}
									Utility.fileForceDelete(f);
								}
							} catch (Exception e) {
								logger.error("저용량 컨텐츠 삭제 에러", e);
							}
						}

						if(logger.isInfoEnabled()) {
							logger.info("dtlDelYn       : "+dtlDelYn);
						}
						if(dtlDelYn.equals("Y")) {
							
							if(m2_del) {
								// 파일만 삭제 기존 정보는 그대로 사용
								deleteContentService.updateDeleteComplete(request.getCtId(), request.getReqDd());
								if(logger.isInfoEnabled()) {
									logger.debug("map del_dd updated!! - ct_id: "+request.getCtId());
								}
							}
							
							// 삭제 여부를 CONTENTS_TBL에 UPDATE한다. DEL_DD = '삭제일', DEL_YN = 'Y'
							// 삭제 여부를 CONTENTS_MAPP_TBL에 UPDATE 한다. DEL_YN = 'Y'
							if(m2_del) {
								deleteContentService.updateDeleteComplete(request.getCtId(), request.getReqDd());
								if(logger.isInfoEnabled()) {
									logger.debug("user delete completed!! - ct_id: "+request.getCtId());
								}

								try {
									// 아카이브가 되어 있다면 DTL에 삭제요청을 한다.
									if(StringUtils.isNotBlank(request.getArchiveSteYn()) && request.getArchiveSteYn().toUpperCase().equals("Y")) {
										ArchiveRequest archiveRequest = new ArchiveRequest();
										archiveRequest.setObjectName("DAS"+request.getHighCtiId());
										archiveRequest.setCategory(DASConstants.DEFAULT_ARCH_CATEGORY);
										archiveRequest.setPriority(DASConstants.DEFAULT_DELETE_PRIORITY);
										archiveRequest.setReqDt(Utility.getTimestamp("yyyyMMddHHmmss"));

										if(logger.isInfoEnabled()) {
											logger.debug("diva delete request: "+xmlStream.toXML(archiveRequest));
										}
										divaConnectSerivce.delete(xmlStream.toXML(archiveRequest));
									}
								} catch (Exception e) {
									logger.error("DTL 삭제 요청 에러", e);
								}
							}
						}
						break;
					case 'F':
						if(logger.isInfoEnabled()) {
							logger.info("-----------------------------force----------------------------------");
						}

						if(logger.isInfoEnabled()) {
							logger.info("high_cti_id    : "+request.getHighCtiId());
							logger.info("high_path      : "+request.getHighPath());
							logger.info("high_fl_nm     : "+request.getHighFlNm());
							logger.info("low_cti_id     : "+request.getLowCtiId());
							logger.info("low_path       : "+request.getLowPath());
							logger.info("low_fl_nm      : "+request.getLowFlNm());
							logger.info("archive_ste_yn : "+request.getArchiveSteYn());
						}

						try {
							// 고용량 영상이 존재한다면 삭제하고 path를 blank 처리한다.
							if(StringUtils.isNotBlank(request.getHighPath()) && StringUtils.isNotBlank(request.getHighFlNm())) {
								String path = request.getHighPath();
								if(path.indexOf("\\") > -1) path = path.replaceAll("\\\\", "/");
								if(!path.startsWith("/")) path = "/"+path;
								if(!path.endsWith("/")) path = path+"/";

								File f = new File(path, request.getHighFlNm());
								if(logger.isDebugEnabled()) {
									logger.debug("fource mxf del path: "+f.getAbsolutePath());
								}
								Utility.fileForceDelete(f);
								
								deleteContentService.updateContentPathBlank(request.getHighCtiId());
							}
							deleteContentService.updateContentMediaIdBlank(request.getCtId());
						} catch (Exception e) {
							logger.error("Fource Delete Error", e);
							m2_del = false;
						}
						if(logger.isInfoEnabled()) {
							logger.debug("Fource delete completed!! - ct_id: "+request.getCtId());
						}

						try {
							if(StringUtils.isNotBlank(request.getLowPath()) && StringUtils.isNotBlank(request.getLowFlNm())) {
								String path = request.getLowPath();
								if(!path.startsWith("/")) path = "/"+path;
								if(!path.endsWith("/")) path = path+"/";

								File f = new File(path);
								if(logger.isDebugEnabled()) {
									logger.debug("fource rowres del path: "+f.getAbsolutePath());
								}
								Utility.fileForceDelete(f);
							}
						} catch (Exception e) {
							logger.error("저용량 컨텐츠 삭제 에러", e);
						}

						// 삭제 여부를 CONTENTS_TBL에 UPDATE한다. DEL_DD = '삭제일', DEL_YN = 'Y'
						// 삭제 여부를 CONTENTS_MAPP_TBL에 UPDATE 한다. DEL_YN = 'Y'
						if(m2_del) {
							deleteContentService.updateDeleteComplete(request.getCtId(), request.getReqDd());
							if(logger.isInfoEnabled()) {
								logger.debug("user delete completed!! - ct_id: "+request.getCtId());
							}

							try {
								// 아카이브가 되어 있다면 DTL에 삭제요청을 한다.
								if(StringUtils.isNotBlank(request.getArchiveSteYn()) && request.getArchiveSteYn().toUpperCase().equals("Y")) {
									ArchiveRequest archiveRequest = new ArchiveRequest();
									archiveRequest.setObjectName("DAS"+request.getHighCtiId());
									archiveRequest.setCategory(DASConstants.DEFAULT_ARCH_CATEGORY);
									archiveRequest.setPriority(DASConstants.DEFAULT_DELETE_PRIORITY);
									archiveRequest.setReqDt(Utility.getTimestamp("yyyyMMddHHmmss"));

									if(logger.isInfoEnabled()) {
										logger.debug("diva delete request: "+xmlStream.toXML(archiveRequest));
									}
									divaConnectSerivce.delete(xmlStream.toXML(archiveRequest));
								}
							} catch (Exception e) {
								logger.error("DTL 삭제 요청 에러", e);
							}
						}
						break;
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
		try {
			if(!thread.isShutdown()) {
				thread.shutdownNow();
				if(logger.isInfoEnabled()) {
					logger.info("DTLDeleteCycle shutdown now!!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
