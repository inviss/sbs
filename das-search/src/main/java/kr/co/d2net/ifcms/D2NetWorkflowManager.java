package kr.co.d2net.ifcms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import kr.co.d2net.commons.system.DasCmsConnector;
import kr.co.d2net.commons.system.DasCmsConnectorImpl;
import kr.co.d2net.commons.utils.Utility;
import kr.co.d2net.model.CartContent;
import kr.co.d2net.model.CartItem;
import kr.co.d2net.model.Das;
import kr.co.d2net.model.DownCart;
import kr.co.d2net.model.Monitoring;
import kr.co.d2net.service.XmlConvertorService;
import kr.co.d2net.service.XmlConvertorServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sbs.ifcms.Activity;
import com.sbs.ifcms.ActivityState;
import com.sbs.ifcms.CMSRuntimeException;
import com.sbs.ifcms.ItemNotFoundException;
import com.sbs.ifcms.ObjectFactory;
import com.sbs.ifcms.OperationNotSupportedException;
import com.sbs.ifcms.WorkItem;
import com.sbs.ifcms.spi.WorkflowManager;

/**
 * 사용자 인증 및 아카이브, 다운로드 요청 과 진행률
 * @author Administrator
 *
 */
public class D2NetWorkflowManager implements WorkflowManager {

	private static Logger logger = Logger.getLogger(D2NetWorkflowManager.class);

	private D2NETCMSSession session;
	public D2NetWorkflowManager(D2NETCMSSession session) {
		this.session = session;
		logger.debug("workflow login id: "+session.getUserId());
	}

	/**
	 * txID : IFCMS를 통해서 아카이브 요청을 할 경우 txID는 요청된 xml 파일에 존해하며, txID를 이용하여
	 *        아카이브 진행현황 목록을 조회한다.
	 * workflowId : 다운로드 요청을 할 경우 DAS CMS에 요청 후 cart_no를 반환하게 되는데 해당 ID가 workflowId가 되며,
	 *              다운로드에 관련된 진행현황 목록은 cart_no를 이용하여 확인한다.
	 */
	public List<Activity> getActivities(String txID, String workflowId) {

		if(logger.isDebugEnabled()) {
			logger.debug("txID: "+txID);
			logger.debug("workflowId: "+workflowId);
		}

		List<Activity> activities = new ArrayList<Activity>();
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(session.getURL());
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();

			Monitoring monitoring = new Monitoring();
			// 다운로드 진행현황 목록 조회
			if(StringUtils.isNotBlank(workflowId) && !workflowId.equals("0")) {
				monitoring.setGubun("002");
				monitoring.setKeyId(workflowId);
			} else {
				// 아카이브 진행현황 목록 조회
				monitoring.setGubun("001");
				monitoring.setKeyId(txID);
			}
			das.addMonitorings(monitoring);
			String xml = convertorService.createMarshaller(das);
			if(logger.isDebugEnabled())
				logger.debug("request: "+xml);

			xml = cmsConnector.getJobState(xml);
			if(logger.isDebugEnabled())
				logger.debug("response: "+xml);

			if(StringUtils.isNotBlank(xml)) {
				das = convertorService.unMarshaller(xml);

				if(!das.getMonitorings().isEmpty()) {
					for(Monitoring monitoring2 : das.getMonitorings()) {
						Activity activity = ObjectFactory.createActivity();
						logger.debug("title : "+monitoring2.getTitle());

						String startTime ="";
						String endTime="";


						SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


						if(monitoring2.getStartTime() != null)
							startTime = df2.format(monitoring2.getStartTime());
						if(monitoring2.getEndTime() != null)
							endTime = df2.format(monitoring2.getEndTime());

						logger.debug("job_nm : "+monitoring2.getJobNm());
						String workname ="";
						if(monitoring2.getJobNm().equals("transaction")){
							workname="변환";
						}else if(monitoring2.getJobNm().equals("approve")){
							workname="승인";
						}else if(monitoring2.getJobNm().equals("archive")){
							workname="아카이브";
						}else if(monitoring2.getJobNm().equals("download")){
							workname="다운로드";
						}else if(monitoring2.getJobNm().equals("transfer")){
							workname="전송";
						}
						activity.setName(workname);
						activity.setProgress(Integer.valueOf(monitoring2.getProgress()));
						activity.setProperty("name_requester_approval", "");
						activity.setProperty("subsystem", "DAS");
						activity.setProperty("media_id", "");
						activity.setProperty("comments", monitoring2.getAppCont());
						activity.setComments(monitoring2.getAppCont());
						logger.debug("comments    :   "+  monitoring2.getAppCont());
						//activity.setProperty("note_approval_request",startTime);
						//activity.setProperty("datetime_approval_request",endTime);
						activity.setStartTime(monitoring2.getStartTime());
						activity.setEndTime(monitoring2.getEndTime());
						logger.debug("activity.setStartTime    :   "+  monitoring2.getStartTime());
						logger.debug("activity.setEndTime    :   "+  monitoring2.getEndTime());
						char dstatus = StringUtils.defaultIfEmpty(monitoring2.getStatus(),"D").charAt(0);
						String status = "";
						logger.debug("dstatus : "+dstatus);
						switch(dstatus) {
						case 'W':	// 대기
							status = ActivityState.NOTSTARTED;
							break;
						case 'I':	// 진행
							status = ActivityState.RUNNING;
							if(activity.getProgress() == 0) {
								activity.setStartTime(monitoring2.getStartTime());
							}
							break;
						case 'C':	// 완료
							status = ActivityState.COMPLETED;
							activity.setEndTime(monitoring2.getEndTime());
							break;
						case 'E':	// 오류
							status = ActivityState.ERROR;
							activity.setEndTime(monitoring2.getEndTime());
							break;
						case 'T':	// 취소
							status = ActivityState.TERMINATED;
							break;
						case 'J':	// 승인거절
							status = ActivityState.REJECTED;
							break;
						default :	// 없음
							status = ActivityState.UNDEFINED;
							break;
						}

						activity.setState(status);
						activities.add(activity);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new CMSRuntimeException(e);
		}

		if(activities.isEmpty()) {
			throw new ItemNotFoundException();
		} else 
			return activities;
	}

	/**
	 * 관리자가 로그인시 승인할 목록 조회
	 */
	public List<WorkItem> getWorkList(String userId) {
		List<WorkItem> items = new ArrayList<WorkItem>();

		if(logger.isDebugEnabled()) {
			logger.debug("userId: "+userId);
		}
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(session.getURL());
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();

			CartItem cartItem = new CartItem();
			cartItem.setUserid(session.getUserId());
			cartItem.setFromdate(Utility.getDate(-7));
			cartItem.setEnddate(Utility.getDate(0));
			cartItem.setSystem("ifcms");
			das.addCartItem(cartItem);

			String xml = convertorService.createMarshaller(das);
			if(logger.isDebugEnabled())
				logger.debug("request : "+xml);

			xml = cmsConnector.getApproveList(xml);
			/*	if(logger.isDebugEnabled())
				logger.debug("response : "+xml);
			 */
			if(StringUtils.isNotBlank(xml)) {

				das = convertorService.unMarshaller(xml);

				if(!das.getCartItems().isEmpty()) {

					for(CartItem cartItem2 : das.getCartItems()) {
						WorkItem workItem = ObjectFactory.createWorkItem();
						workItem.setName(cartItem2.getDownSubj());
						workItem.setIdentifier(cartItem2.getCartNo()+","+cartItem2.getCartSeq());
						workItem.setProperty("title", cartItem2.getDownSubj());
						workItem.setProperty("name_requester_approval", cartItem2.getReqNm());
						workItem.setProperty("subsystem", "DAS");
						workItem.setProperty("media_id", cartItem2.getMediaId());
						workItem.setProperty("note_approval_request",cartItem2.getCtCont());
						workItem.setProperty("workflow_type","다운로드");
						workItem.setProperty("asset_id","M"+cartItem2.getMasterId());
						workItem.setProperty("usegrade",cartItem2.getRistClfCd());
						workItem.setProperty("usegrade_desc",cartItem2.getRistClfNm());

						//형식에 맞게 날짜포멧 변환 
						String date = cartItem2.getReqDt();

						SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
						SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						Date d1;
						try {
							d1 = df1.parse(date);
							date = df2.format(d1);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						workItem.setProperty("datetime_approval_request", date);
						items.add(workItem);
					}
				} else
					throw new ItemNotFoundException();
			}
		} catch (Exception e) {
			throw new CMSRuntimeException(e);
		}

		return items;
	}

	/**
	 * witemId == cart_no (다운로드 요청번호)
	 * 관리자가 승인을 처리하는 프로세스
	 */
	public void processWorkItem(String command, String txID, String username,
			String comment) {

		if(logger.isDebugEnabled()) {
			logger.debug("processWorkItem command: "+command);
			logger.debug("processWorkItem txID: "+txID);
			logger.debug("processWorkItem username: "+username);
			logger.debug("processWorkItem comment: "+comment);
		}
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(session.getURL());
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();

			CartContent cartContent = new CartContent();
			
			if(txID.indexOf(",") > -1) {
				cartContent.setCartNo(Long.valueOf(txID.substring(0, txID.indexOf(","))));
				if(Integer.valueOf(txID.substring(txID.indexOf(",")+1))==0){
				cartContent.setCartSeq(1);
				}else{
					cartContent.setCartSeq(Integer.valueOf(txID.substring(txID.indexOf(",")+1)));
				}
			} else {
				cartContent.setCartNo(Long.valueOf(txID));
				cartContent.setCartSeq(1);
			}
			
			cartContent.setRegrid(username);


			if ("confirm".equals(command)) {			// 승인
				cartContent.setAppCont(comment);
				cartContent.setDownStat("002");
			} else if ("reject".equals(command)) {		// 거부
				cartContent.setAppCont(comment);
				cartContent.setDownStat("003");
			} else {
				throw new OperationNotSupportedException("Action '" + command + "' is not supported");
			}

			das.setCartContent(cartContent);
			String xml = convertorService.createMarshaller(das);
			if(logger.isDebugEnabled()) {
				logger.debug("processWorkItem request: "+xml);
			}

			int result = cmsConnector.requestApprove(xml);

			if(logger.isDebugEnabled()) {
				logger.debug("processWorkItem result: "+result);
			}

			if(result == -1) {
				throw new ItemNotFoundException();
			}else if(result == 0) {
				// 요청 실패시 오류 처리?
			}
		} catch (Exception e) {
			if(e instanceof ItemNotFoundException)
				throw new ItemNotFoundException();
			else
				throw new CMSRuntimeException(e);
		}
	}

	/**
	 * collback : I/F CMS url, '완료'가 되었을때 DAS CMS에서 해당 url로 직접 결과를 통보해준다.
	 * 요청에 대한 결과정보(workflow,[구분자]job_id)를 반환해준다.
	 */
	public String startWorkflow(String txID, String workflow,
			String identifier, String callback, Properties parameters) {

		String returnId = "";
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(session.getURL());

			Das das = new Das();

			if(logger.isDebugEnabled()) {
				logger.debug("workflow : "+workflow);
				logger.debug("workflow : "+parameters);
			}

			if ("archive".equals(workflow)) {
				throw new OperationNotSupportedException("Workflow '" + workflow + "' is not supported");
				/*
				XmlConvertorService<Sbsdas> convertorService = new XmlConvertorServiceImpl<Sbsdas>();

				Sbsdas sbsdas = new Sbsdas();
				Archive archive = new Archive();
				archive.setBrdDd(parameters.getProperty("date_onair", ""));
				archive.setFmDt(parameters.getProperty("date_shooting", ""));
				archive.setTitle(parameters.getProperty("title", ""));
				archive.setSubTtl(parameters.getProperty("title_sub", ""));
				archive.setEpisNo(Integer.valueOf(parameters.getProperty("program_sequence", "0")));
				archive.setCtgrLCd(parameters.getProperty("genre_l", ""));
				archive.setCtgrMCd(parameters.getProperty("genre_m", ""));
				archive.setCtgrSCd(parameters.getProperty("genre_s", ""));
				archive.setRistClfCd(parameters.getProperty("usegrade", ""));
				archive.setPgmRate(parameters.getProperty("viewing_rate", ""));
				archive.setCprtCd(parameters.getProperty("copyright_type", ""));
				archive.setCprtNm(parameters.getProperty("copyright_owner", ""));
				archive.setCprtTypeDsc(parameters.getProperty("copyright_desc", ""));
				archive.setViewGrCd(parameters.getProperty("parents_guidence", ""));
				archive.setAnnotClfCont(parameters.getProperty("usegrade_desc", ""));
				//use_range 사용범위
				archive.setDrtNm(parameters.getProperty("director_name", ""));
				archive.setWriterNm(parameters.getProperty("writer_name", ""));
				archive.setProducerNm(parameters.getProperty("producer_name", ""));
				archive.setCmrDrtNm(parameters.getProperty("director_shooting", ""));
				archive.setOrgPrdrNm(parameters.getProperty("publisher_external", ""));
				archive.setPrdtInOutsCd(parameters.getProperty("production_type", ""));
				archive.setCmrPlace(parameters.getProperty("location_shooting", ""));
				archive.setKeyWords(parameters.getProperty("keyword", ""));
				archive.setSpcInfo(parameters.getProperty("special_info", ""));
				archive.setCtNm(parameters.getProperty("contents_name", ""));

				//archive.setBrdBgnHms(parameters.getProperty("brd_bgn_hms", ""));
				//archive.setBrdEndHms(parameters.getProperty("brd_end_hms", ""));

				archive.setArtistNm(parameters.getProperty("artist", ""));
				archive.setNationCd(parameters.getProperty("country", ""));
				archive.setRsvPrdCd(parameters.getProperty("retention_period", ""));
				archive.setCont(parameters.getProperty("contents_desc", ""));
				archive.setMediaId(parameters.getProperty("media_id", ""));
				archive.setPdsCmsPgmId(parameters.getProperty("program_id", ""));
				archive.setAspRtoCd(parameters.getProperty("aspectratio", ""));
				archive.setVdQlty(parameters.getProperty("resolution", ""));
				archive.setArchRoute(parameters.getProperty("arch_apth", ""));
				archive.setRecordTypeCd(parameters.getProperty("audio_type", ""));
				archive.setBrdLeng(parameters.getProperty("duration", ""));
				archive.setClipNm(parameters.getProperty("clip_name", ""));
				archive.setCtCla(parameters.getProperty("contents_class", ""));
				archive.setCtLeng(parameters.getProperty("contents_length", ""));
				archive.setCtType(parameters.getProperty("contents_type", ""));

				//archive.setDasEqId(parameters.getProperty("das_eq_id", ""));
				//archive.setDasEqPsCd(parameters.getProperty("das_eq_ps_cd", ""));
				//archive.setDrpFrmYn(parameters.getProperty("drp_frm_yn", ""));

				archive.setFlSz(Long.valueOf(parameters.getProperty("file_size", "0")));

				//archive.setOrgFileNm(parameters.getProperty("org_file_nm", ""));

				archive.setReqDt(parameters.getProperty("datetime_request", ""));
				archive.setReqId(parameters.getProperty("req_id", ""));
				archive.setSom(parameters.getProperty("som", ""));
				archive.setEom(parameters.getProperty("eom", ""));
				//archive.setStoragePath(parameters.getProperty("storage_path", ""));
				//archive.setAudBdwt(parameters.getProperty("aud_bdwt", ""));
				//archive.setAudSampFrq(parameters.getProperty("aud_samp_frq", ""));
				//archive.setAudioYn(parameters.getProperty("audio_yn", ""));
				//archive.setBitRt(parameters.getProperty("bit_rt", ""));
				//archive.setFrmPerSec(parameters.getProperty("frm_per_sec", ""));
				archive.setVdHresol(parameters.getProperty("vd_hresol", ""));
				archive.setVdVresol(parameters.getProperty("vd_vresol", ""));
				archive.setCoCd(parameters.getProperty("company_cd", ""));
				archive.setChennelCd(parameters.getProperty("channel_cd", ""));

				archive.setIfcmsUrl(callback);

				das.setArchive(archive);
				sbsdas.setDas(das);

				// 아카이브 등록 후 cti_id 반환
				returnId = "archive,"+cmsConnector.archiveReq(convertorService.createMarshaller(sbsdas));
				 */
			} else if ("download".equals(workflow)) {

				if(logger.isDebugEnabled()) {
					logger.debug("download identifier: "+identifier);
					logger.debug("userid: "+session.getUserId());
				}

				if(StringUtils.isBlank(identifier)) {
					throw new CMSRuntimeException("다운로드에 필요한 영상ID가 없습니다.");
				}

				XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

				// CT_ID 입력
				DownCart downCart = new DownCart();

				Long ctId = 0L;
				if(identifier.startsWith("C")) {
					ctId = Long.valueOf(identifier.substring(1));
				} else {
					ctId = Long.valueOf(identifier);
				}
				downCart.setCtId(ctId);
				downCart.setReqUsrid(session.getUserId());
				downCart.setReqDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				downCart.setCartStat("001");
				das.setDownCart(downCart);

				String xml = convertorService.createMarshaller(das);
				if(logger.isDebugEnabled()) {
					logger.debug("insertDownCart request: "+xml);
				}

				xml = cmsConnector.insertDownCart(xml);
				if(logger.isDebugEnabled()) {
					logger.debug("insertDownCart response: "+xml);
				}
				das = convertorService.unMarshaller(xml);
				Das das2 = new Das();
				logger.debug("this job cart_no: "+das.getDownCart().getCartNo());
				CartContent cartContent = new CartContent();
				cartContent.setCartNo(das.getDownCart().getCartNo());
				cartContent.setCartSeq(1);
				cartContent.setCtId(Long.valueOf(ctId));
				cartContent.setCtiId(0L);
				cartContent.setTargetCmsId(parameters.getProperty("target_cms_id", ""));
				cartContent.setTranscationId(txID);
				// Full, Partial 여부가 필요. 

				if(StringUtils.isNotBlank(parameters.getProperty("mark_in", "")) && StringUtils.isNotBlank(parameters.getProperty("mark_out", ""))){
					cartContent.setDownTyp("P"); // F:Full, P:Partial
				}else{
					cartContent.setDownTyp("F"); // F:Full, P:Partial	
				}
				if("P".equals(cartContent.getDownTyp())) {
					cartContent.setSom(parameters.getProperty("mark_in", "").replaceAll(";", ":"));
					cartContent.setEom(parameters.getProperty("mark_out", "").replaceAll(";", ":"));
					if(StringUtils.isNotBlank(cartContent.getEom()) && StringUtils.isNotBlank(cartContent.getSom())) {
						cartContent.setDuration(Utility.changeTimeCode(cartContent.getEom()) - Utility.changeTimeCode(cartContent.getSom()));
					}
					cartContent.setsFrame(Utility.changeTimeCode(cartContent.getSom()));
				} else {
					cartContent.setSom("");
					cartContent.setEom("");
					cartContent.setsFrame(0L);
				}

				cartContent.setMasterId(das.getDownCart().getMasterId());
				
				cartContent.setDownStat("001");
				cartContent.setRegrid(session.getUserId());
				cartContent.setReqCont(parameters.getProperty("comments", ""));
				cartContent.setCtiId(das.getDownCart().getCtiId());

				das2.setCartContent(cartContent);
				xml = convertorService.createMarshaller(das2);

				if(logger.isDebugEnabled()) {
					logger.debug("insertCartCont request: "+xml);
				}
				xml = cmsConnector.insertCartCont(xml);

				if(logger.isDebugEnabled()) {
					logger.debug("insertCartCont response: "+xml);
				}
				das = convertorService.unMarshaller(xml);

				Das das3 = new Das();
				downCart = new DownCart();
				downCart.setDownSubj("");
				downCart.setCellname(parameters.getProperty("target_storage_id", "")); // 폴더ID
				downCart.setDownGubun("007"); // ifcms 구분자, PDS(001), NDS(002), 데정팀(003), TAPE_OUT(004)
				downCart.setCartNo(das.getCartContent().getCartNo());
				downCart.setUserId(das.getCartContent().getRegrid());
				downCart.setPhysicaltree(parameters.getProperty("target_folder_id", "")); // 
				downCart.setUrl(callback); // 입력 url
				logger.debug("comments "+parameters.getProperty("comments", ""));
				downCart.setReqCont(parameters.getProperty("comments", ""));
				das3.setDownCart(downCart);

				xml = convertorService.createMarshaller(das3);
				if(logger.isDebugEnabled()) {
					logger.debug("updateDownCart request: "+xml);
				}

				Integer result = cmsConnector.updateDownCart(xml);
			

				// 성공하면 1
				if(result == 1) {
					if(logger.isDebugEnabled()) {
						logger.debug("updateDownCart success: "+result);
					}
				}
				returnId = String.valueOf(downCart.getCartNo());
				logger.debug("returnId  : "+returnId);
			} else if ("transfer".equals(workflow)) {
				throw new OperationNotSupportedException("Workflow '" + workflow + "' is not supported");
			} else {
				throw new OperationNotSupportedException("Workflow '" + workflow + "' is not supported");
			}
		} catch (Exception e) {
			throw new CMSRuntimeException(e);
		}

		return returnId;
	}

	/**
	 * 현재 진행중인 프로세스에 대한 액션 ( 현재 '취소')
	 */
	public void processWorkflow(String command, String txID, String workflowId,
			String userID) {

		if(logger.isDebugEnabled()) {
			logger.debug("processWorkItem command: "+command);
			logger.debug("processWorkItem txID: "+txID);
			logger.debug("processWorkItem workflowId: "+workflowId);
			logger.debug("processWorkItem userID: "+userID);
		}

		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(session.getURL());
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();
			Monitoring monitoring = new Monitoring();
			if("cancel".equals(command)) {
				if(StringUtils.isNotBlank(workflowId) && !workflowId.equals("0")) {
					monitoring.setKeyId(workflowId);
					monitoring.setGubun("007");
				} else {
					monitoring.setKeyId(txID);
					monitoring.setGubun("001");
				}
			}
			das.addMonitorings(monitoring);

			String xml = convertorService.createMarshaller(das);
			if(logger.isDebugEnabled()) {
				logger.debug("cancel request: "+xml);
			}
			Integer result = cmsConnector.requestJobCacel(xml);
			if(logger.isDebugEnabled()) {
				logger.debug("cancel result: "+result);
			}
			if(result == 0) {
				// 오류처리?
			}
		} catch (Exception e) {
			throw new CMSRuntimeException(e);
		}

	}

}
