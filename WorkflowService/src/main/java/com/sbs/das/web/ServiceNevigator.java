package com.sbs.das.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.sbs.das.commons.exception.ApplicationException;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.system.DASConstants;
import com.sbs.das.commons.system.DasCmsConnector;
import com.sbs.das.commons.system.DivaConnectSerivce;
import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.AnnotInfoTbl;
import com.sbs.das.dto.AttachTbl;
import com.sbs.das.dto.CartContTbl;
import com.sbs.das.dto.CodeTbl;
import com.sbs.das.dto.ContentDownTbl;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.ContentLocTbl;
import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.CornerTbl;
import com.sbs.das.dto.DasEquipTbl;
import com.sbs.das.dto.DownCartTbl;
import com.sbs.das.dto.ErpTapeItemTbl;
import com.sbs.das.dto.ErpTapeTbl;
import com.sbs.das.dto.ErrorLogTbl;
import com.sbs.das.dto.MasterTbl;
import com.sbs.das.dto.MediaTapeInfoTbl;
import com.sbs.das.dto.MerHistTbl;
import com.sbs.das.dto.NotReportMsgTbl;
import com.sbs.das.dto.StorageInfoTbl;
import com.sbs.das.dto.TimeRistSetTbl;
import com.sbs.das.dto.xml.ArchiveRequest;
import com.sbs.das.dto.xml.ArchiveResponse;
import com.sbs.das.dto.xml.ArchiveStatus;
import com.sbs.das.dto.xml.Attach;
import com.sbs.das.dto.xml.CpuInfo;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.dto.xml.DeleteRequest;
import com.sbs.das.dto.xml.MemoryInfo;
import com.sbs.das.dto.xml.Report;
import com.sbs.das.dto.xml.RequestInfo;
import com.sbs.das.dto.xml.ServerResource;
import com.sbs.das.dto.xml.StorageInfo;
import com.sbs.das.services.AddClipForTapeService;
import com.sbs.das.services.ArchiveReqService;
import com.sbs.das.services.ArchiveStatusService;
import com.sbs.das.services.DasEquipService;
import com.sbs.das.services.DeleteContentAdapter;
import com.sbs.das.services.MerUnCompressService;
import com.sbs.das.services.NotReportedService;
import com.sbs.das.services.ResourceCheckService;
import com.sbs.das.services.TapeErpService;

@WebService(endpointInterface = "com.sbs.das.web.Nevigator")
public class ServiceNevigator implements Nevigator {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private DivaConnectSerivce divaConnector;
	@Autowired
	private ArchiveReqService archiveReqService;
	@Autowired
	private ArchiveStatusService archiveStatusService;
	@Autowired
	private DasCmsConnector dasCmsConnector;
	@Autowired
	private TapeErpService tapeErpService;
	@Autowired
	private DasEquipService dasEquipService;
	@Autowired
	private AddClipForTapeService addClipForTapeService;
	@Autowired
	private DeleteContentAdapter deleteContentAdapter;
	@Autowired
	private NotReportedService notReportedService;
	@Autowired
	private ResourceCheckService resourceCheckService;
	@Autowired
	private MerUnCompressService merUnCompressService;

	public String addClipInfoService(String xml) throws RemoteException{

		if(logger.isDebugEnabled()){
			logger.debug("addClipInfoService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("addClipInfoService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		Map<String, Object> workflow = new HashMap<String, Object>();

		if(das.getDbTable().getAttach() != null) {
			/********************************************************************************************************
			 **************************************** 2014.10.14 ****************************************************
			 **************************************** 자막관련 기능 추가 **********************************************/
			if(das.getDbTable().getAttach() != null) {
				try {
					Attach attach = das.getDbTable().getAttach();
					if(logger.isDebugEnabled()) {
						logger.debug("attach path: "+attach.getFlPath()+"/"+attach.getOrgFileNm());
					}
					if(StringUtils.isNotBlank(attach.getOrgFileNm()) && StringUtils.isNotBlank(attach.getFlPath())) {

						String flPath = attach.getFlPath().replaceAll("\\\\", "/");
						File f = new File(flPath, attach.getOrgFileNm());
						if(logger.isDebugEnabled()) {
							logger.debug("filepath: "+f.getAbsolutePath());
							logger.debug("filesize: "+attach.getFlSz());
						}
						
						AttachTbl attachTbl = new AttachTbl();
						attachTbl.setMothrDataId(attach.getMothrDataId());
						attachTbl.setAttcFileTypeCd(attach.getAttcFileTypeCd()); // 010:한글, 011:영문, 012:중문, 013:일어
						attachTbl.setFlPath(flPath);
						attachTbl.setFlSz(attach.getFlSz());
						attachTbl.setOrgFileNm(attach.getOrgFileNm());
						attachTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						attachTbl.setRegrid("workflow");
						attachTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						attachTbl.setModrid("workflow");
						attachTbl.setAttcClfCd("");
						attachTbl.setFlNm("");

						workflow.put("attach", attachTbl);
					}
				} catch (Exception e) {
					logger.error("Master 정보를 등록중 에러발생", e);
					throw new RemoteException("attach add error", e);
				}
			}
		} else {
			// 등록자 ID가 XML의 여러 부분중 한곳에서만 존재해도 사용함.
			String regrid = "";
			{
				regrid = (StringUtils.isNotBlank(das.getInfo().getRegrid())) ? das.getInfo().getRegrid() : 
					(das.getDbTable() != null && das.getDbTable().getMaster() != null && StringUtils.isNotBlank(das.getDbTable().getMaster().getRegrid())) ? das.getDbTable().getMaster().getRegrid() :
						(das.getDbTable() != null && das.getDbTable().getContent() != null && StringUtils.isNotBlank(das.getDbTable().getContent().getRegrid())) ? das.getDbTable().getContent().getRegrid() :
							(das.getDbTable() != null && das.getDbTable().getContentInst() != null && StringUtils.isNotBlank(das.getDbTable().getContentInst().getRegrid())) ? das.getDbTable().getContentInst().getRegrid() :
								(das.getDbTable() != null && das.getDbTable().getContentMap() != null && StringUtils.isNotBlank(das.getDbTable().getContentMap().getRegrid())) ? das.getDbTable().getContentMap().getRegrid() :
									"";
			}

			String tapeItemId = das.getDbTable().getMaster().getTapeItemId();
			ErpTapeItemTbl erpTapeItemTbl = null;
			ErpTapeTbl erpTapeTbl = null;

			MasterTbl masterTbl = null;
			Long masterId = 0L;

			if(das.getDbTable().getMaster().getMasterId() > 0) {
				try {
					masterId = das.getDbTable().getMaster().getMasterId();
					masterTbl = addClipForTapeService.getMasterObj(das.getDbTable().getMaster().getMasterId());
				} catch (ServiceException ae) {
					logger.error("Master 정보를 조회중 에러발생", ae);
				}
			} else {
				// Tape 인제스트인 경우
				if(StringUtils.isNotBlank(das.getDbTable().getMaster().getTapeItemId())) {
					try {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("tapeItemId", das.getDbTable().getMaster().getTapeItemId());
						masterTbl = addClipForTapeService.getMaster(params);
						if(masterTbl != null){
							masterId=masterTbl.getMasterId();
						}
						if(masterTbl == null) {
							// Tape 정보를 ERP에서 조회한다. 
							erpTapeItemTbl = tapeErpService.getErpTapeItem(das.getDbTable().getMaster().getTapeItemId());
							if(erpTapeItemTbl != null) {
								erpTapeTbl = tapeErpService.getErpTape(erpTapeItemTbl.getTapeId().trim());
								if(erpTapeTbl == null) {
									tapeItemId = "";
									erpTapeItemTbl = null;
								} else {
									if(StringUtils.isNotBlank(erpTapeTbl.getTapeKind())) {
										das.getDbTable().getMaster().setTapeMediaClfCd(erpTapeTbl.getTapeKind());
									} else {
										das.getDbTable().getMaster().setTapeMediaClfCd("999");
									}

									if(StringUtils.isNotBlank(erpTapeItemTbl.getTapeClf())) {
										das.getDbTable().getMaster().setCtgrLCd(erpTapeItemTbl.getTapeClf());
									} else {
										das.getDbTable().getMaster().setCtgrLCd("200");
									}

								}

								if(StringUtils.isNotBlank(tapeItemId) && das.getDbTable().getMaster().getMasterId() <= 0) {
									/*
									 * tape_item_id로 이미 등록이 되어 있는지 확인.
									 * 존재한다면 신규등록을 하지 않는다.
									 */
									masterTbl = archiveReqService.getMaster(tapeItemId, new Boolean(true));
									if(masterTbl != null && masterTbl.getMasterId() > 0) {
										if(logger.isDebugEnabled()){
											logger.debug("get Master_id from tapeItemId: "+masterTbl.getMasterId());
										}
										masterId = masterTbl.getMasterId();
									}
								}
							} else {
								tapeItemId = "";
							}

							if(erpTapeItemTbl == null || erpTapeTbl == null) {
								logger.error("Tape 정보가 ERP에 없습니다.");
								return Boolean.toString(false);
							}
						}
					} catch (ApplicationException ae) {
						logger.error("(Tape or Master) 정보를 조회중 에러가 발생", ae);
					}

				} else if(StringUtils.isNotBlank(das.getDbTable().getMaster().getMcuId())) {
					// 주조 요청인 경우 - 테이프에 대한 정보가 없음
					tapeItemId = "";

					if(das.getDbTable().getMaster().getMasterId() <= 0) {
						try {
							masterId = archiveReqService.getMaxMaster(das.getDbTable().getMaster().getMcuId(), Boolean.valueOf("true"));
							masterTbl = addClipForTapeService.getMasterObj(masterId);
							if(logger.isDebugEnabled()){
								logger.debug("get Master_id from mcuid - ["+das.getDbTable().getMaster().getMcuId()+"] : "+masterId);
							}
						} catch (ApplicationException ae) {
							logger.error("Master 정보를 조회중 에러가 발생 - "+ ae.getMessage());
						}
					}
				} else {
					logger.error("요청에대한 구분값이 없습니다. (tapeid='' or tapeItemid='') && mcuid=''");
					return Boolean.toString(false);
				}
			}

			/****************************************************************************************************/
			/**************************************** Master Set ************************************************/
			/****************************************************************************************************/
			try {
				DecimalFormat df = new DecimalFormat("00,00,00");

				if(masterTbl != null && masterId > 0) { // Master DB Update

					masterId = masterTbl.getMasterId();

					das.getDbTable().getMaster().setMasterId(masterId);
					das.getDbTable().getContentMap().setMasterId(masterId);

					String dataStatCd = das.getDbTable().getMaster().getDataStatCd();
					if(StringUtils.isBlank(dataStatCd)) {
						dataStatCd = "001";
					} else if(dataStatCd.equals("002")) {
						dataStatCd = "001";
					}

					masterTbl.setDataStatCd(dataStatCd);
					masterTbl.setDelDd("");
					masterTbl.setErrorStatCd("000");
					masterTbl.setLockStatCd("N");
					masterTbl.setModrid(regrid);
					masterTbl.setMasterId(masterId);
					
					if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {
						String rpImg = StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getRpimgKfrmSeq(), "0");
						masterTbl.setRpimgKfrmSeq(Integer.parseInt(rpImg));
					}
					
					if(StringUtils.isNotBlank(das.getDbTable().getMaster().getOnAirMediaApprove())) {
						masterTbl.setRistClfCd(das.getDbTable().getMaster().getOnAirMediaApprove());
					} else if(StringUtils.isNotBlank(das.getDbTable().getMaster().getRistClfCd())) {
						masterTbl.setRistClfCd(das.getDbTable().getMaster().getRistClfCd());
					}else{
						masterTbl.setRistClfCd("007");
					}
					
					masterTbl.setAddClip(false);

					workflow.put("master", masterTbl);
				} else if(masterTbl == null && masterId > 0) {

					das.getDbTable().getMaster().setMasterId(masterId);

					masterTbl = new MasterTbl();

					masterTbl.setMasterId(masterId);

					if(StringUtils.isBlank(das.getDbTable().getMaster().getBrdDd())) {
						if(erpTapeItemTbl != null && StringUtils.isBlank(erpTapeItemTbl.getBrdDd())) {
							masterTbl.setFmDt("19000101");
							masterTbl.setBrdDd("19000101");
						} else {
							masterTbl.setBrdDd(erpTapeItemTbl.getBrdDd());
							masterTbl.setFmDt(erpTapeItemTbl.getBrdDd());
						}
					}else{
						masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());
						masterTbl.setFmDt(das.getDbTable().getMaster().getBrdDd());
					}

					masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());
					masterTbl.setFinalBrdYn(das.getDbTable().getMaster().getFinalBrdYn());
					masterTbl.setBrdBgnHms(das.getDbTable().getMaster().getBrdBgnHms());
					masterTbl.setBrdEndHms(das.getDbTable().getMaster().getBrdEndHms());

					if(das.getDbTable().getMaster().getBrdLeng().indexOf(":") > -1) {
						masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng());
					} else if(das.getDbTable().getMaster().getBrdLeng().indexOf(";") > -1) {
						masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng().replaceAll("\\;", ":"));
					} else {
						Integer brdLengInt = Integer.valueOf(das.getDbTable().getMaster().getBrdLeng());
						String brdLeng = df.format(brdLengInt).replaceAll("\\,", ":");
						masterTbl.setBrdLeng(brdLeng);
					}

					masterTbl.setModrid(regrid);
					masterTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					masterTbl.setManualYn("N");
					masterTbl.setDelDd("");
					masterTbl.setAddClip(false);
					masterTbl.setTapeId(das.getDbTable().getMaster().getTapeId());
					masterTbl.setTapeItemId(das.getDbTable().getMaster().getTapeItemId());
					das.getDbTable().getContentMap().setMasterId(masterId);

					if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {
						String rpImg = StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getRpimgKfrmSeq(), "0");
						masterTbl.setRpimgKfrmSeq(Integer.parseInt(rpImg));
					}
					workflow.put("master", masterTbl);
				} else { // Master DB Insert

					Long newMasterId = 0L;
					if (masterTbl == null) masterTbl = new MasterTbl();

					newMasterId = addClipForTapeService.getMasterNewId();
					if(logger.isDebugEnabled()){
						logger.debug("get New Master_id: "+newMasterId);
					}

					masterTbl.setMasterId(newMasterId);
					masterTbl.setRegrid(regrid);
					masterTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					masterTbl.setDataStatCd("001");
					masterTbl.setErrorStatCd("000");
					masterTbl.setFinalBrdYn("N");
					masterTbl.setUseYn("Y");
					masterTbl.setTapeMediaClfCd(das.getDbTable().getMaster().getTapeMediaClfCd());
					masterTbl.setPgmId(0L);

					das.getDbTable().getMaster().setMasterId(newMasterId);
					das.getDbTable().getContentMap().setMasterId(newMasterId);

					String tapeClf = das.getDbTable().getMaster().getCtgrLCd();
					if(StringUtils.isNotBlank(tapeClf)) {
						if(tapeClf.equals("001")) masterTbl.setCtgrLCd("100");
						else if(tapeClf.equals("002")) masterTbl.setCtgrLCd("200");
						else if(tapeClf.equals("003")) masterTbl.setCtgrLCd("300");
						else masterTbl.setCtgrLCd(tapeClf);
					} else {
						masterTbl.setCtgrLCd("200");
					}

					if(StringUtils.isBlank(das.getDbTable().getMaster().getTitle())) {
						if((erpTapeItemTbl != null)) {
							if(StringUtils.isBlank(erpTapeItemTbl.getScnTtl())) {
								masterTbl.setTitle(erpTapeItemTbl.getSubTtl());
							} else {
								masterTbl.setTitle(erpTapeItemTbl.getScnTtl());
							}
						} else masterTbl.setTitle("");
					} else {
						masterTbl.setTitle(das.getDbTable().getMaster().getTitle());
					}

					if(StringUtils.isBlank(das.getDbTable().getMaster().getBrdLeng())) {
						if(erpTapeItemTbl != null && erpTapeItemTbl.getLen() > 0) {
							String brdLeng = df.format(erpTapeItemTbl.getLen()).replaceAll("\\,", ":");
							masterTbl.setBrdLeng(brdLeng);
						}
					} else {
						if(das.getDbTable().getMaster().getBrdLeng().indexOf(":") > -1) {
							masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng());
						} else if(das.getDbTable().getMaster().getBrdLeng().indexOf(";") > -1) {
							masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng().replaceAll("\\;", ":"));
						} else {
							Integer brdLengInt = Integer.valueOf(das.getDbTable().getMaster().getBrdLeng());
							String brdLeng = df.format(brdLengInt).replaceAll("\\,", ":");
							masterTbl.setBrdLeng(brdLeng);
						}
					}

					if(erpTapeItemTbl != null) {
						masterTbl.setTitle(erpTapeItemTbl.getScnTtl());
						masterTbl.setSubTtl(erpTapeItemTbl.getSubTtl());
						masterTbl.setCmrDrtNm(erpTapeItemTbl.getCmrMan());
						masterTbl.setCmrPlace(erpTapeItemTbl.getCmrPlace());
						masterTbl.setFmDt(erpTapeItemTbl.getCmrDd());
						masterTbl.setCastNm(erpTapeItemTbl.getCasting());
						masterTbl.setOrgPrdrNm(erpTapeItemTbl.getPrdtCoNm());
						masterTbl.setDrtNm(erpTapeItemTbl.getPrdtr());
						masterTbl.setEpisNo(erpTapeItemTbl.getEpisNo());

						if(StringUtils.isBlank(erpTapeItemTbl.getBrdDd())) {
							/*
							 * 2014.05.13
							 * 촬영일이 존재한다면 촬영일 셋팅
							 */
							if(StringUtils.isBlank(erpTapeItemTbl.getCmrDd())) {
								masterTbl.setFmDt("19000101");
							} else {
								masterTbl.setFmDt(erpTapeItemTbl.getCmrDd());
							}
							masterTbl.setBrdDd("19000101");
						} else {
							masterTbl.setBrdDd(erpTapeItemTbl.getBrdDd());
							masterTbl.setFmDt(erpTapeItemTbl.getBrdDd());
						}

						masterTbl.setKeyWords(erpTapeItemTbl.getKeyWords());
						masterTbl.setSnps(erpTapeItemTbl.getSnps());
						masterTbl.setCprtrNm(erpTapeItemTbl.getCprtr());
						masterTbl.setCprtType(erpTapeItemTbl.getCprtType());
						masterTbl.setAwardHstr(erpTapeItemTbl.getAwardInfo());
						masterTbl.setDlbrCd(erpTapeItemTbl.getDlbrGr());
						masterTbl.setMcNm(erpTapeItemTbl.getMc());
						masterTbl.setWriterNm(erpTapeItemTbl.getAuthor());
						masterTbl.setOrgPrdrNm(erpTapeItemTbl.getOrgPrdr());
						masterTbl.setPrdtDeptNm(erpTapeItemTbl.getPrdtCoNm());
						masterTbl.setBrdBgnHms(erpTapeItemTbl.getBgnTime());
						masterTbl.setBrdEndHms(erpTapeItemTbl.getEndTime());
						masterTbl.setPgmCd(erpTapeItemTbl.getPgmCd());
						masterTbl.setMusicInfo(erpTapeItemTbl.getMusicInfo());
						masterTbl.setEpisNo(erpTapeItemTbl.getSrisNo());
						masterTbl.setReqCd(erpTapeItemTbl.getReqNo());
						masterTbl.setTapeId(das.getDbTable().getMaster().getTapeId());
						masterTbl.setTapeItemId(das.getDbTable().getMaster().getTapeItemId());
						masterTbl.setSpcInfo(erpTapeItemTbl.getRltText1());
						masterTbl.setProducerNm(erpTapeItemTbl.getRptr());
						masterTbl.setPrdtDeptCd(erpTapeItemTbl.getDeptCd());
					} else {
						masterTbl.setBrdBgnHms(das.getDbTable().getMaster().getBrdBgnHms());
						masterTbl.setBrdEndHms(das.getDbTable().getMaster().getBrdEndHms());
						masterTbl.setMcuid(das.getDbTable().getMaster().getMcuId());
						masterTbl.setViewGrCd(das.getDbTable().getMaster().getViewGrCd());
						masterTbl.setEpisNo(das.getDbTable().getMaster().getEpisNo());

						/*
						 * 2014.05.14
						 * 촬영일이 존재한다면 촬영일 셋팅
						 */
						if(StringUtils.isNotBlank(das.getDbTable().getMaster().getBrdDd())) {
							masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());
							masterTbl.setFmDt(das.getDbTable().getMaster().getBrdDd());
						} else if(StringUtils.isNotBlank(das.getDbTable().getMaster().getFmdt())) {
							masterTbl.setBrdDd(das.getDbTable().getMaster().getFmdt());
							masterTbl.setFmDt(das.getDbTable().getMaster().getFmdt());
						} else {
							masterTbl.setBrdDd("19000101");
							masterTbl.setFmDt("19000101");
						}

						masterTbl.setPdsCmsPgmId(das.getDbTable().getMaster().getPdsCmsPgmId());
						masterTbl.setDrtNm(das.getDbTable().getMaster().getDrtNm());
						masterTbl.setViewGrCd(das.getDbTable().getMaster().getViewGrCd());
						masterTbl.setCprtrNm("SBS");
					}


					if(erpTapeItemTbl != null && StringUtils.isNotBlank(erpTapeItemTbl.getUseGradeCd())) {	// 매체변환 요청
						if(erpTapeItemTbl.getUseGradeCd().equals("002")) {
							masterTbl.setSpcInfo("확인후 사용 : "+erpTapeItemTbl.getRstCont());
						} else if(erpTapeItemTbl.getUseGradeCd().equals("003")) {
							masterTbl.setSpcInfo("제작자만 사용 : "+erpTapeItemTbl.getRstCont());
						} else if(erpTapeItemTbl.getUseGradeCd().equals("004")) {
							masterTbl.setSpcInfo("사용금지 : "+erpTapeItemTbl.getRstCont());
						}
						masterTbl.setRistClfCd(erpTapeItemTbl.getUseGradeCd());
					} else if(StringUtils.isNotBlank(das.getDbTable().getMaster().getMcuId())) {

						/*
						 * 2014.05.13
						 * Live 등록 영상중 특정 시간대에 등록이 되는 콘텐츠에 대하여
						 * 사용등급을 DB에서 조회하여 저장하도록 한다.
						 * OnAir Live 경우에는 사용등급이 없으므로 DAS에서 설정한 사용등급을 적용하고
						 * OnAir Archive는 기존 로직을 유지한다.
						 */
						if(das.getInfo().getDasEqId() == 5) { // Live
							Calendar cal = Calendar.getInstance();

							/*
							 * XML의 brd_dd 방송일을 기준으로 요일을 정함.
							 */
							if(StringUtils.isNotBlank(das.getDbTable().getMaster().getBrdDd())) {
								if(das.getDbTable().getMaster().getBrdDd().indexOf("-") > -1) {
									//2014-06-13
									String[] ymd = das.getDbTable().getMaster().getBrdDd().split("-");
									cal.set(Integer.valueOf(ymd[0]), Integer.valueOf(ymd[1])-1, Integer.valueOf(ymd[2]));
								} else {
									//20140613
									String ymd = das.getDbTable().getMaster().getBrdDd();
									int y = Integer.valueOf(ymd.substring(0, 4));
									int m = Integer.valueOf(ymd.substring(4, 6));
									int d = Integer.valueOf(ymd.substring(6, 8));
									cal.set(Integer.valueOf(y), Integer.valueOf(m)-1, Integer.valueOf(d));
								}
							}

							if(StringUtils.isNotBlank(das.getDbTable().getMaster().getOnAirMediaApprove())) {
								masterTbl.setRistClfCd(das.getDbTable().getMaster().getOnAirMediaApprove());
							} else {
								// 일(1), 월(2), 화(3), 수(4), 목(5), 금(6), 토(7)
								int week = cal.get(Calendar.DAY_OF_WEEK);

								TimeRistSetTbl timeRistSetTbl = addClipForTapeService.getTimeRistSet(week, das.getDbTable().getMaster().getBrdBgnHms());
								if(timeRistSetTbl != null) {
									masterTbl.setRistClfCd(timeRistSetTbl.getRistClfCd());

									// DAS Client에서 해당 시간에 입력된 pds_pgm_id를 입력해준다.
									// 2014-07_07
									masterTbl.setPdsCmsPgmId(timeRistSetTbl.getPdsPgmId());
									if(logger.isDebugEnabled()) {
										logger.debug("db rist_clf_cd: "+timeRistSetTbl.getRistClfCd());
									}
								} else {
									masterTbl.setRistClfCd("007");
								}
							}
							if(logger.isDebugEnabled()) {
								logger.debug("rist_clf_cd: "+das.getDbTable().getMaster().getOnAirMediaApprove());
							}
						} else {	// 주조
							
							/* 주조에서 전달되는 사용제한 코드 리스트
							 * 002: 방송심의제재, 003: 사용금지, 004: 확인 후 사용, 005: 저작권확인, 006: 심의제한, 007: 무제한
							 */
							
							/* 2015.12.11
							 * 주조와 사용제한등급 코드가 동일하므로 편집할 이유가 없음.
							if(das.getDbTable().getMaster().getOnAirMediaApprove().equals("002") || das.getDbTable().getMaster().getOnAirMediaApprove().equals("005")) {
								masterTbl.setRistClfCd("006");
							} else {
								if(StringUtils.isBlank(das.getDbTable().getMaster().getOnAirMediaApprove()))
									masterTbl.setRistClfCd("007");
								else
									masterTbl.setRistClfCd(das.getDbTable().getMaster().getOnAirMediaApprove());
							}
							*/
							if(StringUtils.isBlank(das.getDbTable().getMaster().getOnAirMediaApprove()))
								masterTbl.setRistClfCd("007");
							else
								masterTbl.setRistClfCd(das.getDbTable().getMaster().getOnAirMediaApprove());
						}

						if(logger.isDebugEnabled()) {
							logger.debug("rist_clf_cd: "+das.getDbTable().getMaster().getOnAirMediaApprove());
						}
						
						/*
						 * 2015.12.10 
						 * 박재현 차장님 요청으로 '특이사항' 컬럼에 해당 내용을 등록하도록 함.
						 */
						if(logger.isDebugEnabled()) {
							logger.debug("xLive onairmedia_note: "+das.getDbTable().getMaster().getOnAirMediaNote());
						}
						if(StringUtils.isNotBlank(das.getDbTable().getMaster().getOnAirMediaNote())) { // OnAir 요청
							masterTbl.setSpcInfo(das.getDbTable().getMaster().getOnAirMediaNote());
						} else {
							if(StringUtils.isNotBlank(das.getDbTable().getMaster().getOnAirMediaApprove())) { // OnAir 요청
								StringBuffer spcInfo = new StringBuffer();
								Integer approve = Integer.valueOf(das.getDbTable().getMaster().getOnAirMediaApprove());
								switch(approve) {
								case 2 :
									spcInfo.append("방송심의제재");
									break;
								case 3 : 
									spcInfo.append("사용금지");
									break;
								case 4 : 
									spcInfo.append("확인후 사용");
									break;
								case 5 : 
									spcInfo.append("저작권 확인");
									break;
								case 6 : 
									spcInfo.append("심의제한");
									break;
								case 7 :
									spcInfo.append("무제한");
									break;
								default :
									spcInfo.append("기타");
									break;
								}

								if(StringUtils.isNotBlank(das.getDbTable().getMaster().getSpcInfo())) {
									if(spcInfo.length() > 0) spcInfo.append(" : ");
									spcInfo.append(das.getDbTable().getMaster().getSpcInfo());
								}

								masterTbl.setSpcInfo(spcInfo.toString());
								spcInfo.setLength(0);
							}
						}
					}


					if(StringUtils.isBlank(das.getDbTable().getMaster().getTapeMediaClfCd())) {
						masterTbl.setTapeMediaClfCd("999");
					}

					masterTbl.setPrdtInOutsCd("001");
					masterTbl.setCprtType("001");
					masterTbl.setRsvPrdCd("000");
					masterTbl.setLockStatCd("N");
					masterTbl.setManualYn("N");
					masterTbl.setRsvPrdEndDd("99991231");


					/*
					 * 아카이브경로(OL:온에어 생방, OA : 온에어 송출 , P: PDS , DP:  ERP TAPE 미존재 매체변환, DE :  ERP TAPE 존재  매체변환)
					 */
					if(StringUtils.isNotBlank(das.getDbTable().getMaster().getTapeItemId())) {
						// 매체변환
						masterTbl.setArchRoute("DE");
					} else if(StringUtils.isNotBlank(das.getDbTable().getMaster().getMcuId())) {
						if(das.getInfo().getDasEqId() == 22) { // OnAir
							masterTbl.setArchRoute("OL");
						} else {	// 주조
							masterTbl.setArchRoute("OA");
						}
					}
					masterTbl.setCocd("S");
					masterTbl.setChennelCd("A");
					masterTbl.setCtgrMCd("");
					masterTbl.setCtgrSCd("");
					masterTbl.setAddClip(true);
					//workflow.put("master", masterTbl);
				}

				workflow.put("master", masterTbl);
			} catch (ApplicationException ae) {
				logger.error("Master 정보를 취합하는데 실패했습니다.", ae);
				return Boolean.toString(false);
			}

			/****************************************************************************************************/
			/**************************************** Content Set ***********************************************/
			/****************************************************************************************************/
			String m2Prefix = "";
			boolean openYn = Boolean.valueOf(messageSource.getMessage("das.system.open", null, Locale.KOREA));

			if(openYn) {
				m2Prefix = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA);
			} else {
				m2Prefix = messageSource.getMessage("das.mpeg2.drive", null, Locale.KOREA);
			}

			String m4Prefix = messageSource.getMessage("das.mpeg4.drive", null, Locale.KOREA);
			String m4Path = m4Prefix+"/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd")+"/";

			ContentTbl contentTbl = new ContentTbl();

			try {
				if(das.getDbTable().getContent().getCtId() == null || das.getDbTable().getContent().getCtId() <= 0) {

					contentTbl.setAddClip(true);

					Long ctId = addClipForTapeService.getContentNewId();
					// get Content New ID
					contentTbl.setCtId(ctId);
					contentTbl.setRegrid(regrid);
					contentTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					contentTbl.setDelDd("");
					contentTbl.setCtLeng(das.getDbTable().getContent().getCtLeng());
					contentTbl.setDuration(das.getDbTable().getContent().getDuration());
					contentTbl.setDelYn("N");
					contentTbl.setArchiveYn("N");
					contentTbl.setCopyObjectYn("N");

					das.getDbTable().getContent().setCtId(ctId);
					das.getDbTable().getContentInst().setCtId(ctId);
					das.getDbTable().getContentMap().setCtId(ctId);

					contentTbl.setUseYn("Y");

					if(das.getDbTable().getContent().getCtSeq() == null || das.getDbTable().getContent().getCtSeq() <= 0) {
						contentTbl.setCtSeq(1);
					}
					das.getDbTable().getContentMap().setCtSeq(contentTbl.getCtSeq());

					if(logger.isDebugEnabled()) {
						logger.debug("Content NewID: "+contentTbl.getCtId());
						logger.debug("Master McuID : "+das.getDbTable().getMaster().getMcuId());
					}
					if(StringUtils.isNotBlank(das.getDbTable().getMaster().getMcuId())) { // 주조에서 보낸 요청이라면..
						Integer mcuSeq = das.getDbTable().getContent().getMcuSeq();
						if(mcuSeq == 0) {
							contentTbl.setCtTyp("003");
							contentTbl.setCtNm("본방송");
						} else if(mcuSeq == 10) {
							contentTbl.setCtTyp("001");
							contentTbl.setCtNm("전타이틀");
							contentTbl.setCtSeq(1);
						} else if(mcuSeq == 20) {
							contentTbl.setCtTyp("004");
							contentTbl.setCtNm("전CM");
							contentTbl.setCtSeq(2);
						} else if(mcuSeq >= 100 && mcuSeq < 1000 && ((mcuSeq % 2) == 0)) {
							contentTbl.setCtTyp("003");
							contentTbl.setCtNm("본방송");
							contentTbl.setCtSeq(mcuSeq - 97);
						} else if(mcuSeq >= 100 && mcuSeq < 1000 && ((mcuSeq % 2) == 1)) {
							contentTbl.setCtTyp("004");
							contentTbl.setCtNm("CM");
							contentTbl.setCtSeq(mcuSeq - 97);
						} else if(mcuSeq == 1000) {
							contentTbl.setCtTyp("004");
							contentTbl.setCtNm("후CM");
							contentTbl.setCtSeq(1000);
						} else if(mcuSeq == 2000) {
							contentTbl.setCtTyp("002");
							contentTbl.setCtNm("후타이틀");
							contentTbl.setCtSeq(1001);
						}
					} else { // 매체변환 요청이라면...
						if(StringUtils.isNotBlank(das.getDbTable().getContent().getCtNm())) {
							if(das.getDbTable().getContent().getCtNm().equals("전타이틀")) {
								contentTbl.setCtTyp("001");
							} else if(das.getDbTable().getContent().getCtNm().equals("전CM")) {
								contentTbl.setCtTyp("004");
							} else if(das.getDbTable().getContent().getCtNm().equals("본방송")) {
								contentTbl.setCtTyp("003");
							} else if(das.getDbTable().getContent().getCtNm().equals("후CM")) {
								contentTbl.setCtTyp("004");
							} else if(das.getDbTable().getContent().getCtNm().equals("후타이틀")) {
								contentTbl.setCtTyp("002");
							} else if(das.getDbTable().getContent().getCtNm().equals("전체")) {
								contentTbl.setCtTyp("006");
							}
							contentTbl.setCtNm(das.getDbTable().getContent().getCtNm());
						}
					}

					// [001:원본, 005:clean, 006:방송본, 010:개편본, 011:종편본]
					if(StringUtils.isBlank(das.getDbTable().getContent().getCtCla())) {
						contentTbl.setCtCla("006");
					} else {
						contentTbl.setCtCla(das.getDbTable().getContent().getCtCla());
					}

					if(logger.isDebugEnabled()) {
						logger.debug("CT_NM  : "+contentTbl.getCtNm());
						logger.debug("CT_TYP : "+contentTbl.getCtTyp());
						logger.debug("CT_SEQ : "+contentTbl.getCtSeq());
					}

					if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {
						//  본방송, 전체 영상이라면 카탈로그를 생성을 위한 CT_ID를 셋팅한다.
						if( contentTbl.getCtTyp().equals("003") || contentTbl.getCtTyp().equals("006")) {
							// 대표이미지가 존재한다면
							masterTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
						}
					}

					// 인제스트 등록일
					masterTbl.setIngRegDd(Utility.getTimestamp("yyyyMMdd"));

					// Master Object hash put
					workflow.put("master", masterTbl);

					// 본방송 영상이고 MXF 영상이라면...
					if(contentTbl.getCtTyp().equals("003") && das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') {
						if(erpTapeItemTbl != null) {
							contentTbl.setCont(erpTapeItemTbl.getScnCont());
							contentTbl.setKeyWords(erpTapeItemTbl.getKeyWords());
							contentTbl.setCtOwnDeptCd(erpTapeItemTbl.getDeptCd());
						}
					}
					contentTbl.setVdQlty(das.getDbTable().getContent().getVdQlty());
					contentTbl.setAspRtoCd(das.getDbTable().getContent().getAspRtoCd());

					das.getDbTable().getContentMap().setEDuration(das.getDbTable().getContent().getDuration());
					workflow.put("content", contentTbl);
				} else {

					das.getDbTable().getContentInst().setCtId(das.getDbTable().getContent().getCtId());
					das.getDbTable().getContentMap().setCtId(das.getDbTable().getContent().getCtId());

					if(StringUtils.isNotBlank(das.getDbTable().getMaster().getMcuId())) { // 주조에서 보낸 요청이라면..
						Integer mcuSeq = das.getDbTable().getContent().getMcuSeq();
						if(mcuSeq == 0) {
							contentTbl.setCtTyp("003");
							contentTbl.setCtNm("본방송");
						} else if(mcuSeq == 10) {
							contentTbl.setCtTyp("001");
							contentTbl.setCtNm("전타이틀");
							contentTbl.setCtSeq(1);
						} else if(mcuSeq == 20) {
							contentTbl.setCtTyp("004");
							contentTbl.setCtNm("전CM");
							contentTbl.setCtSeq(2);
						} else if(mcuSeq >= 100 && mcuSeq < 1000 && ((mcuSeq % 2) == 0)) {
							contentTbl.setCtTyp("003");
							contentTbl.setCtNm("본방송");
							contentTbl.setCtSeq(mcuSeq - 97);
						} else if(mcuSeq >= 100 && mcuSeq < 1000 && ((mcuSeq % 2) == 1)) {
							contentTbl.setCtTyp("004");
							contentTbl.setCtNm("CM");
							contentTbl.setCtSeq(mcuSeq - 97);
						} else if(mcuSeq == 1000) {
							contentTbl.setCtTyp("004");
							contentTbl.setCtNm("후CM");
							contentTbl.setCtSeq(1000);
						} else if(mcuSeq == 2000) {
							contentTbl.setCtTyp("002");
							contentTbl.setCtNm("후타이틀");
							contentTbl.setCtSeq(1001);
						}
					} else { // 매체변환 요청이라면...
						if(StringUtils.isNotBlank(das.getDbTable().getContent().getCtNm())) {
							if(das.getDbTable().getContent().getCtNm().equals("전타이틀")) {
								contentTbl.setCtTyp("001");
							} else if(das.getDbTable().getContent().getCtNm().equals("전CM")) {
								contentTbl.setCtTyp("004");
							} else if(das.getDbTable().getContent().getCtNm().equals("본방송")) {
								contentTbl.setCtTyp("003");
							} else if(das.getDbTable().getContent().getCtNm().equals("후CM")) {
								contentTbl.setCtTyp("004");
							} else if(das.getDbTable().getContent().getCtNm().equals("후타이틀")) {
								contentTbl.setCtTyp("002");
							} else if(das.getDbTable().getContent().getCtNm().equals("전체")) {
								contentTbl.setCtTyp("006");
							}
							contentTbl.setCtNm(das.getDbTable().getContent().getCtNm());
						}
					}

					if(logger.isDebugEnabled()) {
						logger.debug("ct_id: "+das.getDbTable().getContent().getCtId());
						logger.debug("cti_fmt: "+das.getDbTable().getContentInst().getCtiFmt());
					}
					// Content update kfrm path
					if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getCtiFmt()) && 
							das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {

						// 본방송, 전체 영상이라면 카탈로그를 생성을 위한 CT_ID를 셋팅한다.
						if( contentTbl.getCtTyp().equals("003") || contentTbl.getCtTyp().equals("006")) {
							// 대표이미지 영상 메타ID
							masterTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
						}

						if(logger.isDebugEnabled()) {
							logger.debug("kfrm path: "+m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
						}
						contentTbl.setKfrmPath(m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
						contentTbl.setCtId(das.getDbTable().getContent().getCtId());
						contentTbl.setModrid(regrid);
						contentTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						contentTbl.setTotKfrmNums(das.getDbTable().getContent().getTotKfrmNums());

						das.getDbTable().getContent().setKfrmPath(m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
					} else {
						contentTbl.setCtId(das.getDbTable().getContent().getCtId());
						contentTbl.setModrid(regrid);
						contentTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					}

					if(StringUtils.isNotBlank(das.getDbTable().getContent().getErrorCont())) {
						contentTbl.setErrorCont(das.getDbTable().getContent().getErrorCont());
					}

					contentTbl.setAddClip(false);
				}

				workflow.put("content", contentTbl);
			} catch (Exception e) {
				logger.error("Contents 정보를 취합하는데 에러가 발생했습니다.", e);
				return Boolean.toString(false);
			}


			/****************************************************************************************************/
			/**************************************** Corner Set ************************************************/
			/****************************************************************************************************/
			CornerTbl cornerTbl = new CornerTbl();
			try {
				if(das.getDbTable().getContentMap().getCnId() <= 0) {

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("clfCd", DASConstants.CLF_CD_CN_Type);
					params.put("desc", das.getDbTable().getContent().getCtNm());

					CodeTbl codeTbl = null;
					try {
						codeTbl = addClipForTapeService.getCodeObj(params, new Boolean(true));
					} catch (Exception e) {
						logger.error("코드 정보를 조회하는데 실패했습니다.", e);
					}


					// New Corner Insert
					Long cnId = addClipForTapeService.getCornerNewId();
					das.getDbTable().getContentMap().setCnId(cnId);
					cornerTbl.setCnId(cnId);
					cornerTbl.setMasterId(das.getDbTable().getMaster().getMasterId());

					if(codeTbl == null || StringUtils.isBlank(codeTbl.getSclCd())) {
						// Master ID에 대한 코너는 존재하고, 입력된 CT_NM이 코너의 유형명이 아님
						//cornerTbl.setCnTypeCd(messageSource.getMessage("das.basic.corner", null, Locale.KOREA)); // 003
						cornerTbl.setCnTypeCd(das.getDbTable().getContent().getCtTyp());
					} else {
						// Master ID에 대한 코너가 존재하고 입력된 CT_NM이 코너의 유형명임
						cornerTbl.setCnTypeCd(codeTbl.getSclCd());
					}

					if(erpTapeItemTbl != null) {
						cornerTbl.setCnNm(StringUtils.defaultString(erpTapeItemTbl.getScnTtl(), ""));
					} else {
						if(contentTbl.getCtTyp().equals("001")) {
							cornerTbl.setCnNm("전타이틀");
						}else if(contentTbl.getCtTyp().equals("002")){
							cornerTbl.setCnNm("후타이틀");
						}else{
							cornerTbl.setCnNm("");
						}
					}

					if(StringUtils.isBlank(cornerTbl.getCnInfo())) {
						if(erpTapeItemTbl != null) {
							cornerTbl.setCnInfo(StringUtils.defaultString(erpTapeItemTbl.getScnCont(), ""));
						}
					}

					if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {
						cornerTbl.setRpimgKfrmSeq(masterTbl.getRpimgKfrmSeq());
						cornerTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
					} else {
						cornerTbl.setRpimgKfrmSeq(0);
						cornerTbl.setRpimgCtId(0L);
					}

					cornerTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
					cornerTbl.setRegrid(regrid);
					cornerTbl.setDuration(das.getDbTable().getContent().getDuration());
					cornerTbl.setSom("00:00:00:00");
					cornerTbl.setEom(Utility.changeDuration(das.getDbTable().getContent().getDuration()));

					cornerTbl.setAddClip(true);
					workflow.put("corner", cornerTbl);
				} else {
					cornerTbl = addClipForTapeService.getCornerObj(das.getDbTable().getContentMap().getCnId(), true);
				}
			} catch (ApplicationException ae) {
				logger.error("Corner 정보를 취합하는데 에러가 발생했습니다.", ae);
				return Boolean.toString(false);
			}


			/****************************************************************************************************/
			/************************************ Content Instance Set ******************************************/
			/****************************************************************************************************/
			try {
				ContentInstTbl contentInstTbl = new ContentInstTbl();
				contentInstTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));

				if(das.getDbTable().getContentInst().getCtiId() > 0) {
					contentInstTbl.setAddClip(false);

					contentInstTbl.setCtiId(das.getDbTable().getContentInst().getCtiId());
					contentInstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					contentInstTbl.setModrid(regrid);

					// 업데이트 [File size 변경]
					contentInstTbl.setFlSz(das.getDbTable().getContentInst().getFlSz());
					workflow.put("contentInst", contentInstTbl);
				} else {
					// 신규 추가
					contentInstTbl.setAddClip(true);

					Long newInstID = addClipForTapeService.getContentInstNewId();
					contentInstTbl.setCtiId(newInstID);
					contentInstTbl.setCtId(das.getDbTable().getContent().getCtId());

					das.getDbTable().getContentInst().setCtiId(newInstID);

					String filePath = "";
					// 영상 포맷 체크, 
					if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getCtiFmt())) {
						if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') { // Mpeg2
							if(openYn) {
								if(StringUtils.isBlank(das.getDbTable().getMaster().getMcuId())){
									filePath = m2Prefix+"/SBS/manual/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");
								}else{
									filePath = m2Prefix+"/SBS/onAir/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");	
								}
							} else {
								filePath = m2Prefix+"/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");
							}
						} else if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '3') { // Mpeg4
							filePath = m4Path + das.getDbTable().getContentInst().getCtId();
						} else {
							logger.error("해당 영상의 format이 비정의된 정보입니다. - "+das.getDbTable().getContentInst().getCtiFmt());
							return Boolean.toString(false);
						}
						das.getDbTable().getContentInst().setFilePath(filePath);

						Map<String, Object> params = new HashMap<String, Object>();
						params.put("clfCd", DASConstants.CLF_CD_ContentsInstanceFormat);
						params.put("sclCd", das.getDbTable().getContentInst().getCtiFmt());

						CodeTbl codeTbl = addClipForTapeService.getCodeObj(params, new Boolean(true));

						if(StringUtils.isNotBlank(codeTbl.getRmk2())) {
							contentInstTbl.setWrkFileNm(das.getDbTable().getContentInst().getCtiId()+"."+codeTbl.getRmk2());
						}

						if(erpTapeItemTbl != null) {
							contentInstTbl.setMeCd(erpTapeItemTbl.getMeCd());
						} else {
							contentInstTbl.setMeCd("002");
						}

						if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getOnAirMediaAudioChannel())) {
							contentInstTbl.setRecordTypeCd(das.getDbTable().getContentInst().getOnAirMediaAudioChannel());
						}

						contentInstTbl.setCtiFmt(das.getDbTable().getContentInst().getCtiFmt());
						contentInstTbl.setFlSz(das.getDbTable().getContentInst().getFlSz());
						/*
						 * 가로*세로 해상도가 바뀌어 있다면 원래 위치로 복원해준다.
						 * 2016.03.30
						 */
						if(das.getDbTable().getContentInst().getVdHresol() < das.getDbTable().getContentInst().getVdVreSol()) {
							contentInstTbl.setVdHresol(das.getDbTable().getContentInst().getVdVreSol());
							contentInstTbl.setVdVresol(das.getDbTable().getContentInst().getVdHresol());
						} else {
							contentInstTbl.setVdHresol(das.getDbTable().getContentInst().getVdHresol());
							contentInstTbl.setVdVresol(das.getDbTable().getContentInst().getVdVreSol());
						}
						
						contentInstTbl.setBitRt(das.getDbTable().getContentInst().getBitRt());
						contentInstTbl.setFrmPerSec("29.97");
						contentInstTbl.setColorCd(das.getDbTable().getContentInst().getColorCd());
						contentInstTbl.setAudioYn(das.getDbTable().getContentInst().getAudioYn());
						contentInstTbl.setAudTypeCd(das.getDbTable().getContentInst().getAudioType());
						contentInstTbl.setRegrid(regrid);


						if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getOnAirMediaAudioChannel())) {
							if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("M")) {
								contentInstTbl.setRecordTypeCd("001");
							} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("S")) {
								contentInstTbl.setRecordTypeCd("002");
							} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("F")) {
								contentInstTbl.setRecordTypeCd("003");
							} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("B")) {
								contentInstTbl.setRecordTypeCd("004");
							} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("V")) {
								contentInstTbl.setRecordTypeCd("005");
							} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("O")) {
								contentInstTbl.setRecordTypeCd("006");
							} 
							contentTbl.setCtNm(das.getDbTable().getContent().getCtNm());
						} else {
							contentInstTbl.setRecordTypeCd("001");
						}

						contentInstTbl.setArchSteYn("N");
						contentInstTbl.setIngestEqId(das.getInfo().getDasEqId());
						contentInstTbl.setFlPath(das.getDbTable().getContentInst().getFilePath());
						contentInstTbl.setAudioBdwt(das.getDbTable().getContentInst().getAudioBdwt());
						contentInstTbl.setAudSampFrq(das.getDbTable().getContentInst().getAudSampFrq());
						contentInstTbl.setAudLanCd(das.getDbTable().getContentInst().getAudLanCd());
						contentInstTbl.setDrpFrmYn(das.getDbTable().getContentInst().getDrpFrmYn());
						contentInstTbl.setWmvYn("Y");
						contentInstTbl.setCatalogYn("Y");

						workflow.put("contentInst", contentInstTbl);
					} else {
						logger.error("영상에대한 format[mpeg2 or mpeg4] 정보가 없습니다.");
						return Boolean.toString(false);
					}

				}
			} catch (ApplicationException ae) {
				logger.error("Content Instance 정보를 취합하는데 실패했습니다", ae.getStackTrace());
				return Boolean.toString(false);
			}

			/****************************************************************************************************/
			/************************************** Content Map Set *********************************************/
			/****************************************************************************************************/
			ContentMapTbl contentMapTbl = new ContentMapTbl();
			contentMapTbl.setRegrid(regrid);

			try {
				contentMapTbl.setCtId(das.getDbTable().getContentMap().getCtId());
				contentMapTbl.setMasterId(das.getDbTable().getContentMap().getMasterId());
				contentMapTbl.setCnId(das.getDbTable().getContentMap().getCnId());
				contentMapTbl.seteDuration(das.getDbTable().getContentMap().getEDuration());
				contentMapTbl.setsDuration(0L);
				contentMapTbl.setPgmId(0L);
				contentMapTbl.setDelDd("");
				int contentMapCount = addClipForTapeService.getContentMapCount(contentMapTbl, new Boolean(true));
				if(contentMapCount == 0) {
					contentMapTbl.setAddClip(true);
					contentMapTbl.setRegrid(regrid);
					contentMapTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				} else {
					contentMapTbl.setAddClip(false);
					contentMapTbl.setModrid(regrid);
					contentMapTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				}
				workflow.put("contentMap", contentMapTbl);
			} catch (ApplicationException ae) {
				logger.error("Conents Map 정보를 취합하는데 에러가 발생했습니다.", ae);
				return Boolean.toString(false);
			}

			/****************************************************************************************************/
			/************************************** AnnotInfo Set ***********************************************/
			/****************************************************************************************************/
			if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') {
				AnnotInfoTbl annotInfoTbl = new AnnotInfoTbl();

				annotInfoTbl.setRegrid(regrid);
				annotInfoTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				annotInfoTbl.setCnId(cornerTbl.getCnId());
				annotInfoTbl.setCtId(contentTbl.getCtId());
				annotInfoTbl.setMasterId(masterTbl.getMasterId());
				annotInfoTbl.setSom(cornerTbl.getSom());
				annotInfoTbl.setEom(cornerTbl.getEom());
				annotInfoTbl.setDuration(cornerTbl.getDuration());
				annotInfoTbl.setEntireYn("Y");
				if(erpTapeItemTbl != null) {
					annotInfoTbl.setAnnotClfCont(erpTapeItemTbl.getRstCont());
					// 매체변환 사용등급 무제한
					annotInfoTbl.setAnnotClfCd("007");
				}else{
					annotInfoTbl.setAnnotClfCont("");
					// Master 사용등급 적용 2014.05.26
					annotInfoTbl.setAnnotClfCd(masterTbl.getRistClfCd());
				}
				annotInfoTbl.setGubun("L");

				annotInfoTbl.setAddClip(true);

				workflow.put("annotInfo", annotInfoTbl);
			}
		}

		if(logger.isDebugEnabled()) {
			logger.debug("return xml: "+xmlStream.toXML(das));
		}

		/****************************************************************************************************/
		/*********************************** DB All Insert or Update ****************************************/
		/****************************************************************************************************/
		try {
			addClipForTapeService.saveAddClipInfo(workflow);
		} catch (Exception ae) {
			logger.error("메타정보를 등록하는 과정에서 에러가 발생했습니다.", ae);
			return Boolean.toString(false);
		}
		workflow.clear();
		workflow = null;

		return xmlStream.toXML(das);
	}

	public String archiveService(String xml) throws RemoteException{

		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		if(logger.isDebugEnabled()){
			logger.debug("archiveService Call XML: "+xml);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("archiveService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		String regrid = "";
		{
			regrid = (StringUtils.isNotBlank(das.getInfo().getRegrid())) ? das.getInfo().getRegrid() : 
				(das.getDbTable() != null && das.getDbTable().getMaster() != null && StringUtils.isNotBlank(das.getDbTable().getMaster().getRegrid())) ? das.getDbTable().getMaster().getRegrid() :
					(das.getDbTable() != null && das.getDbTable().getContent() != null && StringUtils.isNotBlank(das.getDbTable().getContent().getRegrid())) ? das.getDbTable().getContent().getRegrid() :
						(das.getDbTable() != null && das.getDbTable().getContentInst() != null && StringUtils.isNotBlank(das.getDbTable().getContentInst().getRegrid())) ? das.getDbTable().getContentInst().getRegrid() :
							(das.getDbTable() != null && das.getDbTable().getContentMap() != null && StringUtils.isNotBlank(das.getDbTable().getContentMap().getRegrid())) ? das.getDbTable().getContentMap().getRegrid() :
								"";
		}

		/*
		 * 2012.12.26
		 * 생성된 mer 정보를 queue에 등록하여 키프레임 이미지를 자동 생성되도록 로직 추가
		 */
		if(StringUtils.isNotBlank(das.getInfo().getKfrmPath())) {
			try {
				MerHistTbl merHistTbl = new MerHistTbl();
				merHistTbl.setCtId(das.getDbTable().getContentMap().getCtId());
				merHistTbl.setKfrmPath(das.getInfo().getKfrmPath());
				merHistTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
				merHistTbl.setStatus("W");

				merUnCompressService.insertMerJob(merHistTbl);
			} catch (Exception e) {
				logger.error("Mer unCompress insert error", e);
			}
		}

		//DASConstants.DAS_ARCHIVE_PRIFIX = "DAS"
		String uid = DASConstants.DAS_ARCHIVE_PRIFIX;

		/* 아카이브 요청 저장 */
		ContentLocTbl contentLocTbl = new ContentLocTbl();

		/* archive request object 생성 ,*/
		ArchiveRequest request = new ArchiveRequest();

		request.setSource(DASConstants.DEFAULT_ARCH_SOURCE);
		request.setQos(DASConstants.DEFAULT_ARCH_QOS);
		request.setPriority(DASConstants.DEFAULT_BACKUP_PRIORITY);
		request.setGroup(DASConstants.DEFAULT_GROUP_ARCH);

		RequestInfo info = das.getInfo();

		info.setArchiveType(StringUtils.defaultIfEmpty(das.getInfo().getArchiveType(), "mxf"));
		info.setDtlType(StringUtils.defaultIfEmpty(das.getInfo().getDtlType(), "das"));

		String vdQlty = StringUtils.defaultIfEmpty(das.getInfo().getVdQlty(), "001");
		if("002".equals(vdQlty))
			request.setVdQlty("SD");
		else
			request.setVdQlty("HD");

		if(StringUtils.isNotBlank(info.getArchiveType()) && !info.getArchiveType().equals("h264")) {
			request.setObjectName(uid+info.getCtiId());
		} else {
			request.setObjectName(info.getOutSystemId());
		}

		// all, mxf(아카이브), h264(복본요청)
		request.setArchiveType(info.getArchiveType());
		// das or medianet
		request.setDtlType(info.getDtlType());




		if(StringUtils.isNotBlank(info.getArchiveType()) && !info.getArchiveType().equals("h264")) {
			contentLocTbl.setJobStatus("W");
			contentLocTbl.setAstatus("0");
			contentLocTbl.setRegUser(regrid);
			contentLocTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
			contentLocTbl.setProgress("0");
			contentLocTbl.setScount(1);
			contentLocTbl.setUseYn("Y");

			/*
			 * 중복요청 처리[2012-11-15]
			 * 아카이브 요청은 CTI_ID가 유일하므로 해당 ID로 조회하여 데이타가 존재할 경우
			 * 상태값이 에러가 아니라면 대기중이거나 진행중이므로 중복 요청을 막는다.
			 */
			try {
				ContentLocTbl contentLocTbl2 = archiveReqService.getContentLoc(info.getCtiId(), true);


				if(contentLocTbl2 != null && !contentLocTbl2.getJobStatus().equals("E")) {
					logger.info("contents_loc_tbl already exist! - num : "+contentLocTbl2.getNum() + ", cti_id: "+contentLocTbl2.getCtiId() +", status : "+contentLocTbl2.getJobStatus());
					return String.valueOf(contentLocTbl2.getNum());
				}
			} catch (DaoNonRollbackException e) {
				logger.error("getContentLoc error", e);
			}
		} else {

			try {
				ContentLocTbl contentLocTbl2 = archiveReqService.getContentLoc(info.getCtiId(), true);
				// 중복요청 처리[2012-12-06]
				if(info.getDtlType().equals("backup")) { // 수동소산 요청
					if(contentLocTbl2 != null && !StringUtils.defaultIfBlank(contentLocTbl2.getBackupStatus(), "").equals("E")) {
						logger.info(info.getCtiId()+" - Backup request aleady processing : down_status"+contentLocTbl2.getDownStatus() + ", backup_status : "+contentLocTbl2.getBackupStatus());
						return String.valueOf(contentLocTbl2.getNum());
					}
				} else { // 수동복본 요청
					request.setFilename(das.getInfo().getFilename());

					String category = null;

					//Dtl Type가 das 라면 cms를 그외라면 netcms를 넣어준다.
					if(das.getInfo().getDtlType().equals("das")) {
						category = "cms";
					} else {
						category = "netcms";
					}

					request.setCategory(category);



					if(contentLocTbl2 != null && !StringUtils.defaultIfBlank(contentLocTbl2.getCopyStatus(), "").equals("E")) {
						logger.info(info.getCtiId()+" - Backup request aleady processing : down_status"+contentLocTbl2.getDownStatus() + ", chage_status: "+contentLocTbl2.getChangeStatus() +", copy_status : "+contentLocTbl2.getCopyStatus());
						return String.valueOf(contentLocTbl2.getNum());
					}
				}
			} catch (Exception e) {
				logger.error("getContentLoc error", e);
			}

			contentLocTbl.setUpdtUser(regrid);
			contentLocTbl.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
		}

		contentLocTbl.setCyn("N");
		contentLocTbl.setCtiId(info.getCtiId());
		contentLocTbl.setObjName(request.getObjectName());

		// 복원요청(h264)이 아니면 파일 경로가 존재하는지 확인
		if(StringUtils.isNotBlank(info.getArchiveType()) && !info.getArchiveType().equals("h264")) {
			if(StringUtils.isBlank(info.getFilePath())) {
				logger.error("Request XML Content File Path not exist!!");
				return Boolean.toString(false);
			}
		}

		//if(das.getInfo().getArchiveType().equals("h264"))
		// 경로가 존재한다면 아카이브 패스로 변환
		String filepath = "";
		if(!info.getArchiveType().equals("h264")) {
			// Windows Backspace replaceAll '\' -> '/'
			if(info.getFilePath().indexOf("\\") > -1) info.setFilePath(info.getFilePath().replaceAll("\\\\", "/"));
			if(!info.getFilePath().startsWith("/")) info.setFilePath("/"+info.getFilePath().replace("//", "/").trim());

			contentLocTbl.setPath(info.getFilePath().substring(0, info.getFilePath().lastIndexOf("/")));
			contentLocTbl.setFilename(info.getFilePath().substring(info.getFilePath().lastIndexOf("/")+1));

			if (info.getFilePath().indexOf("mp2") > -1) {
				/*
				 * 모니터링에서 재요청할 때 경로가 잘못 입력되어 들어옴.
				 * 일단, 잘못된 경로를 재편집해서 요청하도록 수정함.
				 */
				if(info.getFilePath().indexOf("nearline") > -1) {
					contentLocTbl.setPath(info.getFilePath().replace("/nearline", ""));
				} else if(info.getFilePath().indexOf("arcreq") > -1) {
					contentLocTbl.setPath(info.getFilePath().replace("/arcreq", ""));
				}
				filepath = (new StringBuilder("")).append(contentLocTbl.getPath().replace("/mp2/", messageSource.getMessage("das.storage.drive", null, Locale.KOREA)+"/").trim()).toString();
			} else if (info.getFilePath().indexOf("arcreq") > -1) {
				filepath = (new StringBuilder("")).append(contentLocTbl.getPath().replace("/arcreq/", messageSource.getMessage("pds.storage.drive", null, Locale.KOREA)+"/").trim()).toString();
			} else if (info.getFilePath().indexOf("nearline") > -1) {
				filepath = (new StringBuilder("")).append(contentLocTbl.getPath().replace("/nearline/", messageSource.getMessage("net.storage.drive", null, Locale.KOREA)+"/").trim()).toString();
			} else {
				logger.error("File Path header not exist!! (mp2 or arcreq)");
				return Boolean.toString(false);
			}

			if(logger.isDebugEnabled()) {
				logger.debug("contentLoc path 		: "+contentLocTbl.getPath());
				logger.debug("contentLoc filename 	: "+contentLocTbl.getFilename());
			}

			request.setFilepath(filepath);
			request.setFilename(contentLocTbl.getFilename());
		}

		if(logger.isDebugEnabled()){
			logger.debug("archive fl_path: "+info.getFilePath());
		}

		String req_xml = xmlStream.toXML(request);
		if(logger.isDebugEnabled()){
			logger.debug("archive req_xml: "+req_xml);
		}

		String retMsg = "";
		try {
			retMsg = divaConnector.archive(req_xml);
			if(logger.isDebugEnabled()){
				logger.debug("archive response xml: "+retMsg);
			}
		} catch (Exception e) {
			logger.error("Diva Connection Error !!- "+e.getMessage());
			return Boolean.toString(false);
		}

		// 작업ID 반환하도록 변경 2012.05.22
		Long jobId = 0L;
		try {

			// Diva Connector 요청이 정상적으로 완료 되었다면 Content Loc에 요청 내용을 저장한다.
			ContentLocTbl content = archiveReqService.getContentLoc(contentLocTbl.getObjName(), new Boolean(true));
			if(StringUtils.isNotBlank(retMsg)) {
				ArchiveResponse response = (ArchiveResponse)xmlStream.fromXML(retMsg);
				if(response.getArchiveError() == null || StringUtils.isBlank(response.getArchiveError().getCode())) {

					// 아카이브 신청이 아니고 신규등록이 아니라면..(수동 복본 및 수동 소산)
					if(StringUtils.isNotBlank(das.getInfo().getOutSystemId()) && das.getInfo().getArchiveType().equals("h264")) {
						if(info.getDtlType().equals("backup")) { // 수동소산이라면
							content.setBackupStatus("W");
							content.setBackupProgress("0");
							content.setBackupDt(Utility.getTimestamp("yyyyMMddHHmmss"));
							content.setBackupId(regrid);
						} else {	// 수동복본이라면(medianet)
							content.setCopyStatus("W");
							content.setCopyProgress("0");
							content.setReCopyRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
							content.setCopyId(regrid);
						}
						archiveReqService.updateContentLoc(contentLocTbl);
						if(logger.isInfoEnabled()){
							logger.info("Archive Request updated Content Loc : ObjectName - "+contentLocTbl.getObjName());
						}
						jobId = content.getNum();
					} else  {
						// 재요청시 해당 정보가 loc에 있는 경우는 해당 컬럼에 업데이트 만 한다.
						if(content != null && StringUtils.isNotBlank(content.getFilename())){
							content.setJobStatus("W");
							content.setAstatus("0");
							content.setProgress("0");
							content.setScount(1);
							content.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
							content.setUpdtUser(regrid);
							archiveReqService.updateContentLoc(content);
							if(logger.isInfoEnabled()){
								logger.info("Archive Request updated Content Loc : ObjectName - "+content.getObjName());
							}
						} else {
							jobId = archiveReqService.insertContentLoc(contentLocTbl);
							if(logger.isInfoEnabled()){
								logger.info("Archive Request inserted Content Loc : ObjectName - "+contentLocTbl.getObjName());
							}
						}
					}
				}
			}
		} catch (ApplicationException ae) {
			logger.error("ContentLoc DB Error !!- ", ae);
		}

		return String.valueOf(jobId);
	}

	public String schedulerForceExecute(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("schedulerForceExecute Call XML: "+xml);
		}

		if(StringUtils.isBlank(xml)) {
			throw new RemoteException("Request xml is Blank!!");
		} else {
			Das das = null;
			try {
				das = (Das)xmlStream.fromXML(xml);
			} catch (Exception e) {
				logger.error("schedulerForceExecute xml parsing error!! - "+e.getMessage());
				return Boolean.toString(false);
			}

			try {
				if(logger.isInfoEnabled()) {
					logger.info("req_method : "+das.getInfo().getReqMethod());
					logger.info("limit_day  : "+das.getInfo().getLimitDay());
					logger.info("co_cd  : "+das.getInfo().getCoCd());
				}
				if(StringUtils.isNotBlank(das.getInfo().getReqMethod())) {
					String limitDay = (StringUtils.isBlank(das.getInfo().getLimitDay())) ? null : das.getInfo().getLimitDay();
					String coCd = (StringUtils.isBlank(das.getInfo().getCoCd())) ? null : das.getInfo().getCoCd();
					if(das.getInfo().getReqMethod().equals("all")) {
						deleteContentAdapter.archiveExpiredDelete(coCd, limitDay);
						deleteContentAdapter.downloadExpiredDelete(coCd, limitDay);
					} else if(das.getInfo().getReqMethod().equals("archive")) {
						deleteContentAdapter.archiveExpiredDelete(coCd, limitDay);
					} else if(das.getInfo().getReqMethod().equals("download")) {
						deleteContentAdapter.downloadExpiredDelete(coCd, limitDay);
					} else if(das.getInfo().getReqMethod().equals("scrap")) {
						deleteContentAdapter.scrappedDelete(coCd, limitDay);
					} else if(das.getInfo().getReqMethod().equals("user")) {
						List<DeleteRequest> deleteRequests = das.getDeleteList();
						deleteContentAdapter.userRequestDelete(deleteRequests);
					} else if(das.getInfo().getReqMethod().equals("force")){
						List<DeleteRequest> deleteRequests = das.getDeleteList();
						deleteContentAdapter.pdsRequestDelete(deleteRequests);
					}
				}
			} catch (ServiceException e) {
				logger.error("스케쥴러 강제 실행 에러", e);
				return Boolean.valueOf(false).toString();
			}

		}

		return Boolean.valueOf(true).toString();
	}

	public String archiveSchedulerService(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("archiveSchedulerService Call XML: "+xml);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("archiveSchedulerService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		boolean result = true;
		// Das CMS에 XML 전달
		try {
			Integer retVal = dasCmsConnector.insertArchiveReq(xml);
			if(logger.isDebugEnabled()) {
				logger.debug("Das CMS Called  insertComArchiveReq : "+retVal);
			}
			if(retVal != 1) {
				result = false;
			}
		} catch (Exception e) {
			logger.error("요청 장비의 정보를 조회하는데 에러!! - msg: "+e.getMessage());
			return Boolean.toString(false);
		}

		/*
		 * 요청한 OnAir 장비ID를 이용하여 DB에서 IP, PORT 정보를 알아낸 후 정상적으로 XML정보를 받았다는
		 * return xml을 생성하여 전송한다.
		 */
		Das das2 = new Das();
		Report report = new Report();

		String regrid = "";
		{
			regrid = (StringUtils.isNotBlank(das.getInfo().getRegrid())) ? das.getInfo().getRegrid() : 
				(das.getDbTable() != null && das.getDbTable().getMaster() != null && StringUtils.isNotBlank(das.getDbTable().getMaster().getRegrid())) ? das.getDbTable().getMaster().getRegrid() :
					(das.getDbTable() != null && das.getDbTable().getContent() != null && StringUtils.isNotBlank(das.getDbTable().getContent().getRegrid())) ? das.getDbTable().getContent().getRegrid() :
						(das.getDbTable() != null && das.getDbTable().getContentInst() != null && StringUtils.isNotBlank(das.getDbTable().getContentInst().getRegrid())) ? das.getDbTable().getContentInst().getRegrid() :
							(das.getDbTable() != null && das.getDbTable().getContentMap() != null && StringUtils.isNotBlank(das.getDbTable().getContentMap().getRegrid())) ? das.getDbTable().getContentMap().getRegrid() :
								"";
		}

		report.setReqId(regrid);
		report.setRegrid(regrid);
		report.setDasEqId(das.getInfo().getDasEqId());
		report.setDasEqPsCd(das.getInfo().getDasEqPsCd());

		if(result) {
			DasEquipTbl dasEquipTbl = null;
			try {
				dasEquipTbl = dasEquipService.getDasEquip(das.getInfo().getDasEqId(), das.getInfo().getDasEqPsCd(), new Boolean(true));

				if(dasEquipTbl == null) {
					logger.error("요청 장비의 정보가 없습니다.!! eq_id: "+das.getInfo().getDasEqId()+", eq_ps_cd: "+das.getInfo().getDasEqPsCd());
					return Boolean.toString(false);
				} else {
					/*
					// 개발서버에는 OnAir 연동 테스트를 위해 주조개발장비로 IP를 셋팅한다.
					InetAddress address = InetAddress.getLocalHost(); 
					String ip = address.getHostAddress();

					if(ip.equals("10.30.23.48")) {
						dasEquipTbl.setDasEqUseIp("10.10.110.148");
						dasEquipTbl.setDasEqUsePort("12999");
					}
					 */
					if(logger.isDebugEnabled()) {
						logger.debug("equip ip  	: "+dasEquipTbl.getDasEqUseIp());
						logger.debug("equip port	: "+dasEquipTbl.getDasEqUsePort());
						logger.debug("mcu_id 		: "+das.getDbTable().getMaster().getMcuId());
						logger.debug("mcu_seq 		: "+das.getDbTable().getContent().getMcuSeq());
					}

					if(StringUtils.isNotBlank(das.getDbTable().getMaster().getMcuId())) {
						report.setStatus("007");
						report.setMcuid(das.getDbTable().getMaster().getMcuId());
						report.setMcuSeq(das.getDbTable().getContent().getMcuSeq());

						das2.setReport(report);

						// OnAir에서 요청한 정보라면 Socket으로 응답 메세지를 던져준다.
						// das_eq_id => (onAir: 22, Live:5)

						/*		
					String daseqid = das.getInfo().getDasEqId()+"";
						logger.debug("daseqid		: "+daseqid.trim().equals("22"));
						if(daseqid.trim().equals("22")) { // onair라면
							// 등록 요청한 장비에 Socket으로 응답 XML을 전송한다.
							logger.debug("#################################333333 		: "+das.getInfo().getDasEqId());
							Utility.sendSocketXml(xmlStream.toXML(das2), dasEquipTbl.getDasEqUseIp(), Integer.valueOf(dasEquipTbl.getDasEqUsePort()));
							if(logger.isDebugEnabled()) {
								logger.debug("Socket Send [007] - "+das.getDbTable().getMaster().getMcuId());
							}
						}  */

						CodeTbl codeTbl = addClipForTapeService.getCodeObj(DASConstants.CLF_CD_UID_PREFIX, "001", new Boolean(true));
						if(logger.isDebugEnabled()) {
							logger.debug("Code.desc  : "+codeTbl.getDesc());
						}

						NotReportMsgTbl notReportMsgTbl = new NotReportMsgTbl();
						notReportMsgTbl.setUid(codeTbl.getDesc()+das.getInfo().getDasEqId());
						notReportMsgTbl.setDasEqClfCd(DASConstants.JOB_FILEINGEST);
						notReportMsgTbl.setRetryNo(0);
						notReportMsgTbl.setHost(dasEquipTbl.getDasEqUseIp());
						notReportMsgTbl.setPort(dasEquipTbl.getDasEqUsePort());
						notReportMsgTbl.setMsg(xml);
						notReportMsgTbl.setCheckstamp("NO");
						notReportMsgTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));

						addClipForTapeService.insertNotReportedMsg(notReportMsgTbl);

						// Equip update
						dasEquipTbl.setModrid(regrid);
						dasEquipTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						dasEquipTbl.setFlPath(das.getDbTable().getContentInst().getOrgFileNm());

						dasEquipService.updateDasEquip(dasEquipTbl);
					} else {
						report.setStatus("003");
						report.setMcuid("");
						report.setMcuSeq(0);

						das2.setReport(report);

						// 등록 요청한 장비에 Socket으로 응답 XML을 전송한다.
						Utility.sendSocketXml(xmlStream.toXML(das2), dasEquipTbl.getDasEqUseIp(), Integer.valueOf(dasEquipTbl.getDasEqUsePort()));
						if(logger.isDebugEnabled()) {
							logger.debug("Socket Send [003] - "+das.getDbTable().getMaster().getMcuId());
						}
					}
				}

			} catch (Exception e) {
				logger.error("해당 장비에 Socket 전송 에러.!! use_ip: "+dasEquipTbl.getDasEqUseIp()+", use_port: "+dasEquipTbl.getDasEqUsePort());
				logger.error(e.getMessage());
			}
		}

		return new Boolean(result).toString();
	}

	public String fileIngestService(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("fileIngestService Call XML: "+xml);
		}
		return null;
	}

	public String nleIngestService(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("nleIngestService Call XML: "+xml);
		}
		return null;
	}

	public String downloadService(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("downloadService Call XML: "+xml);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("downloadService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		Long cartNo = das.getInfo().getCartNo();

		BufferedWriter bw = null;
		try {
			List<DownCartTbl> cartTbls = archiveReqService.findDownCarts(cartNo, new Boolean(true));
			if(cartTbls.isEmpty()) {
				logger.error("findDownCarts is size '0'!! - ");
				return Boolean.toString(false);
			} else {

				DownCartTbl cartTbl = (DownCartTbl)cartTbls.get(0);

				/*
				 * DownLoad 파일명 생성 규칙
				 * 파일 Path : /MP2/RESTORE/USER_ID/CART_NO/
				 * 파일 Name :
				 * 001: PDS, 002: NDS, 005: 계열사 => DownCartTbl.downsubj + "_"+ CartContTbl.mediaId+".MXF"
				 * 변경됨: CartContTbl.mediaId+".MXF", 007: IFCMS
				 * 003: 데정팀, 004: tape-out => CartContTbl.cartSeq + CartContTbl.ctiId+".MXF"
				 */
				String downGubun = StringUtils.isBlank(cartTbl.getDownGubun()) ? "003" : cartTbl.getDownGubun();

				boolean useSeq = true;
				if(downGubun.equals("001") || downGubun.equals("002") || downGubun.equals("005")|| downGubun.equals("007")) {
					useSeq = false;
				}

				String restorePath = messageSource.getMessage("das.restore.prefix", null, Locale.KOREA)+"/"+cartTbl.getReqUsrid()+"/"+cartTbl.getCartNo();
				if(logger.isDebugEnabled()) {
					logger.debug("cart no: "+cartTbl.getCartNo()+ ", down gubun: "+cartTbl.getDownGubun());
					logger.debug("useSeq: "+useSeq+ ", restorePath: "+restorePath);
				}

				/* 폴더가 존재하지 않는다면 생성 */
				if(!SystemUtils.IS_OS_WINDOWS) {
					File f = new File(messageSource.getMessage("das.restore.unix.drive", null, Locale.KOREA)+restorePath);
					if(!f.exists()) f.mkdirs();
				}

				/* PARTIAL Restore 의 경우 cart_seq별로 시작점, 끝점을 기록하고 에러와 관계없이 복원 신청은 한다. */
				if(!SystemUtils.IS_OS_WINDOWS) {
					try {
						FileWriter fw = new FileWriter(messageSource.getMessage("das.restore.unix.drive", null, Locale.KOREA)+restorePath+"/list.txt", true);
						bw = new BufferedWriter(fw);
					} catch (Exception e) {
						logger.error("list.txt new file create error - "+e.getMessage());
					}
				}

				String[] cartSeqs = null;
				String[] dtlTypes = null;
				if(StringUtils.isBlank(das.getInfo().getCartSeq())) {
					logger.error("cart_seq is blank !!");
					return Boolean.toString(false);
				} else {
					cartSeqs = das.getInfo().getCartSeq().split("\\,");
					dtlTypes = das.getInfo().getDtlType().split("\\,");

					ArchiveRequest request = null;
					for(int i=0; i<cartSeqs.length; i++) {

						String cartSeq = cartSeqs[i];
						String dtlType = dtlTypes[i];
						if(logger.isDebugEnabled()) {
							logger.debug("String[] cartSeq: "+cartSeq);
						}
						if(StringUtils.isNotBlank(cartSeq)) {

							// CONTENTS_DOWN_TBL에 해당 카트의 번호가 존재한다면 다운로드 요청이 중복이므로 패스 [2012-11-13]
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("cartNo", cartNo);
							params.put("cartSeq", cartSeq);
							params.put("jobStatus", "E"); // 실패를 제외한 나머지건이 존재하는지 검사
							ContentDownTbl  contentDownTbl = archiveReqService.getContentDown(params);
							if(contentDownTbl != null && contentDownTbl.getNum() > 0) {
								logger.info("cart_no: "+cartTbl.getCartNo()+", cart_seq: "+cartSeq+" is existed!! passed!");
								continue;
							}

							/* DB에서 해당 cart_seq 의 정보를 조회한다. */
							List<CartContTbl> cartContTbls = archiveReqService.findCartConts(cartTbl.getCartNo(), Integer.valueOf(cartSeq), new Boolean(true));

							if(cartContTbls.isEmpty()) {
								logger.warn("findCartCont is size '0'!! - cart_seq: "+cartSeq);
							} else {

								CartContTbl cartCont = cartContTbls.get(0);
								if(logger.isDebugEnabled()) {
									logger.debug("seq: "+cartCont.getCartSeq());
									logger.debug("cti_id: "+cartCont.getCtiId());
								}
								/* Diva Connector에 전달할 XML Values */
								request = new ArchiveRequest();
								/* 복원요청을 저장  XML Values. */

								contentDownTbl = new ContentDownTbl();
								// 아카이브된 원본 파일명을 조회한다.
								String orgFileName = "";
								try {
									ContentLocTbl contentLocTbl = archiveReqService.getContentLoc(cartCont.getCtiId(), new Boolean(true));
									if(contentLocTbl != null && StringUtils.isNotBlank(contentLocTbl.getFilename())) {
										orgFileName = contentLocTbl.getFilename().trim();
										request.setObjectName(contentLocTbl.getObjName().trim());
										contentDownTbl.setObjName(contentLocTbl.getObjName().trim());
									} else {
										//실패시 다운로드 실패라고 업데이트 한다.
										ContentDownTbl contentDownTbl2 = new ContentDownTbl();
										contentDownTbl2.setCartNo(das.getInfo().getCartNo());
										contentDownTbl2.setCartSeq(Integer.valueOf(cartSeq));
										contentDownTbl2.setStatus("69");
										contentDownTbl2.setProgress("0");
										contentDownTbl2.setUpdtUser("DIVA");
										contentDownTbl2.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
										archiveStatusService.updateStatusCartCont(contentDownTbl2);

										logger.error("Contents_loc_tbl not exists for obj_name : "+cartCont.getCtiId());
										continue;
										//throw new Exception("Contents_loc_tbl not exists for obj_name : "+cartCont.getCtiId());
									}
								} catch (Exception e) {
									ContentDownTbl contentDownTbl3 = new ContentDownTbl();
									//실패시 다운로드 실패라고 업데이트 한다.
									contentDownTbl3.setCartNo(das.getInfo().getCartNo());
									contentDownTbl3.setCartSeq(Integer.valueOf(cartSeq));
									contentDownTbl3.setStatus("69");
									contentDownTbl3.setProgress("0");
									contentDownTbl3.setUpdtUser("DIVA");
									contentDownTbl3.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
									archiveStatusService.updateStatusCartCont(contentDownTbl3);
									logger.error("요청한 컨텐츠 정보가 Contents_loc_tbl에 존재하지 않습니다. : "+"DAS"+cartCont.getCtiId());
									logger.error("get ContentLoc Error!! - "+e.getMessage());
									return Boolean.toString(false);
								}

								/* Diva Connector에 전달할 XML Values */
								//request.setCategory(DASConstants.DEFAULT_ARCH_CATEGORY);
								request.setArchiveType("mxf");
								request.setDtlType(dtlType);
								request.setPriority(DASConstants.DEFAULT_RESTORE_PRIORITY);
								request.setDestination(DASConstants.DEFAULT_ARCH_DESTINATION);
								request.setQos(DASConstants.DEFAULT_NON_ARCH_QOS);
								request.setGroup(DASConstants.DEFAULT_GROUP_ARCH);
								request.setFilename(orgFileName.trim());

								/* Restore Default Type FULL */
								String downTyp = StringUtils.defaultIfEmpty(cartCont.getDownTyp(), "F");
								if(logger.isDebugEnabled()) {
									logger.debug("base value: "+cartCont.getDownTyp()+", replace value: "+downTyp);
								}

								/* 복원요청을 저장한다. */
								//ContentDownTbl contentDownTbl = new ContentDownTbl();
								contentDownTbl.setCartNo(cartCont.getCartNo());
								contentDownTbl.setCartSeq(cartCont.getCartSeq());
								contentDownTbl.setCtiId(cartCont.getCtiId());
								//contentDownTbl.setObjName(DASConstants.DAS_ARCHIVE_PRIFIX+cartCont.getCtiId());
								contentDownTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
								contentDownTbl.setRegUser("DIVA");
								contentDownTbl.setStatus("0");
								contentDownTbl.setJobStatus("W");
								contentDownTbl.setPath(restorePath);

								/* PARTIAL Restore */
								if(downTyp.equals("P")) {
									request.setTargetFilePath(messageSource.getMessage("das.restore.win.drive", null, Locale.KOREA)+"/"+restorePath);

									if(useSeq) {
										request.setTargetFilename(cartCont.getCartSeq()+"_"+cartCont.getCtiId()+".mxf");
									} else {
										//request.setTargetFilename(cartTbl.getDownSubj()+"_"+cartCont.getMediaId()+".mxf");
										/* Diva 한글문제로인해 downSubj를 제거하고 mediaId만 주기로 결정함. 2011.03.24 */
										request.setTargetFilename(cartCont.getMediaId()+".mxf");
									}

									request.setStartTc(cartCont.getSom());
									request.setEndTc(cartCont.getEom());

									contentDownTbl.setFilename(request.getTargetFilename());
									contentDownTbl.setBegintc(cartCont.getSom());
									contentDownTbl.setEndtc(cartCont.getEom());

									if(bw != null) {
										try {
											bw.write(cartCont.getCtiId() + "," + cartCont.getSom() + "," + cartCont.getEom());
											bw.newLine();
										} catch (IOException e) {
											logger.error("list.txt new file write error - "+e.getMessage());
										}
									}
								} else {
									contentDownTbl.setFilename(orgFileName);
									//request.setTargetFilename(orgFileName);
									request.setFilepath(messageSource.getMessage("das.restore.win.drive", null, Locale.KOREA)+"/"+restorePath);
								}

								/**
								 * <pre>
								 * Content_Down_Tbl 에 요청건에 대한 insert를 실행한다.
								 * 등록 후 반환된 ID값을 Restore 요청시 전달한다. 해당 ID는 상태전달시 key로 사용
								 * </pre>
								 */
								try {
									Long num = archiveReqService.insertContentDown(contentDownTbl);
									if(logger.isDebugEnabled()) {
										logger.debug("insert ContentDown success : "+num);
									}
									request.setRestoreId(num);
								} catch (ApplicationException ae) {
									logger.error("ContentDownTbl restore insert Error", ae);
									return Boolean.toString(false);
								}

								String requestXML = xmlStream.toXML(request);
								if(logger.isDebugEnabled()) {
									logger.debug("DIVA restore Request - "+requestXML);
								}
								String retMsg = "";
								try {
									if(downTyp.equals("P")) {
										retMsg = divaConnector.restorePFR(xmlStream.toXML(request));
									} else {
										retMsg = divaConnector.restore(xmlStream.toXML(request));
									}
									if(logger.isDebugEnabled()) {
										logger.debug("DIVA restore Response - "+retMsg);
									}
								} catch (Exception e) {

									contentDownTbl.setUpdtUser("workflow");
									contentDownTbl.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
									contentDownTbl.setJobStatus("E");
									contentDownTbl.setStatus("69");

									try {
										archiveReqService.updateContentDown(contentDownTbl);
										archiveStatusService.updateStatusCartCont(contentDownTbl);
									} catch (ServiceException se) {
										logger.error("ContentDown Update Error! - ", se);
									}

									throw new ServiceException("Diva Connection Error", e);
								}

							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("findDownCarts error!! - ", e);
			return Boolean.toString(false);
		} finally {
			if(bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					logger.error("list.txt new file close error - "+e.getMessage());
				}
			}
		}

		return Boolean.toString(true);
	}

	public String reportService(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("Batch TC Report Call XML: "+xml);
		}
		return null;
	}

	public String ingestReportService(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("ingestReportService Call XML: "+xml);
		}
		return null;
	}

	public String archiveReportService(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("archiveReportService Call XML: "+xml);
		}
		return null;
	}

	public String serviceTest(String xml) throws RemoteException{
		if(logger.isDebugEnabled()){
			logger.debug("serviceTest Call XML: "+xml);
		}

		return Boolean.toString(true);
	}

	public String archiveStatus(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("archiveStates Call XML: "+xml);
		}
		
		// 2015.11.30 속도 체크 로직 추가
		long start = 0;
		long p_start = 0;
		long end = 0;
		long p_end = 0;
		
		if(logger.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}
		
		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {

			logger.error("getResArchieve xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		List<ArchiveStatus> status = das.getStatus();
		if(!status.isEmpty()) {
			
			for(ArchiveStatus archiveStatus : status ) {
				if(logger.isDebugEnabled()) {
					logger.debug("job_id : "+archiveStatus.getJobId());
					logger.debug("job status: "+archiveStatus.getJobStatus());
				}
				
				try {
					if(archiveStatus.getJobStatus().equals("E")) {
						if(logger.isDebugEnabled()) {
							logger.debug("error_id : "+archiveStatus.getErrorId());
							logger.debug("error_msg : "+archiveStatus.getErrorMsg());
						}
					} else {
						if(logger.isDebugEnabled()) {
							logger.debug("obj_name : "+archiveStatus.getObjectName());
							logger.debug("progress : "+archiveStatus.getProgress());
						}
					}

					if(logger.isDebugEnabled()) {
						p_start = System.currentTimeMillis();
					}
					if(archiveStatus.getJobId().equals("005")) { // archive

						/*
						 * 2013.08.22 배치 아카이브를 위해 추가
						 * 새로 추가한 DB Table(BATCH_DOWN_TBL)에 해당 objName 값이 존재하는지 확인하여 있다면 배치 작업으로 간주한다.
						 * 아니라면 기존 아카이브 요청
						 */
						ContentDownTbl batchDownTbl = archiveStatusService.getBatchDown(archiveStatus.getObjectName(), Boolean.valueOf("true"));
						if(batchDownTbl != null && batchDownTbl.getNum() > 0) {
							if("MA".equals(archiveStatus.getProcessCd())) { // 아카이브경우에만 업데이트
								if(logger.isDebugEnabled()) {
									logger.debug("Batch Archive ObjName: "+archiveStatus.getObjectName()+", jobStatus: "+archiveStatus.getJobStatus() +", progress: "+archiveStatus.getProgress());
								}
								if(archiveStatus.getJobStatus().equals("E") || (archiveStatus.getJobStatus().equals("C") && archiveStatus.getProgress() == 100)) {
									batchDownTbl.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
									batchDownTbl.setDtlYn(archiveStatus.getJobStatus());
									archiveStatusService.updateBatchDown(batchDownTbl);
								}
							}
						} else {
							ContentLocTbl contentLocTbl = new ContentLocTbl();
							contentLocTbl.setObjName(archiveStatus.getObjectName());
							contentLocTbl.setUpdtUser("DIVA");

							if("MA".equals(archiveStatus.getProcessCd())) { // 아카이브
								contentLocTbl.setJobStatus(archiveStatus.getJobStatus());
								contentLocTbl.setProgress(String.valueOf(archiveStatus.getProgress()));
							} else if("MT".equals(archiveStatus.getProcessCd())) { // MXF -> H264 변환
								contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
								contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
								contentLocTbl.setEqId(archiveStatus.getEqId());
							} else if("CA".equals(archiveStatus.getProcessCd())) { // h264 복본 아카이브
								contentLocTbl.setCopyProgress(String.valueOf(archiveStatus.getProgress()));
								contentLocTbl.setCopyStatus(archiveStatus.getJobStatus());
							} else if("BA".equals(archiveStatus.getProcessCd())) { // h264 소산 아카이브
								contentLocTbl.setBackupProgress(String.valueOf(archiveStatus.getProgress()));
								contentLocTbl.setBackupStatus(archiveStatus.getJobStatus());
							}

							if("MA".equals(archiveStatus.getProcessCd())) {
								archiveStatusService.backupStatus(contentLocTbl);
							} else {
								archiveStatusService.updateStatus(contentLocTbl);
							}

							if(archiveStatus.getJobStatus().equals("C") && archiveStatus.getProgress() == 100) {

								String ctiId = archiveStatus.getObjectName().substring(3);
								contentLocTbl.setCtiId(Long.valueOf(ctiId));

								if("MA".equals(archiveStatus.getProcessCd())) { // 아카이브
									if(logger.isDebugEnabled()) {
										logger.debug("archive completed - obj_name: "+ archiveStatus.getObjectName());
									}
									contentLocTbl.setProgress(String.valueOf(archiveStatus.getProgress()));
									contentLocTbl.setUpdtDtm(archiveStatus.getUpdtDtm());
									contentLocTbl.setAstatus("250");
									contentLocTbl.setJobStatus("C");

									/* 아카이브 완료여부를 Master 및 Content_Inst에 update 한다. */
									archiveStatusService.updateCompleteMaster(contentLocTbl);
								} else if("MT".equals(archiveStatus.getProcessCd())) { // MXF -> H264 변환
									if(logger.isDebugEnabled()) {
										logger.debug("transcode completed - obj_name: "+ archiveStatus.getObjectName());
									}
									contentLocTbl.setChangeProgress("100");
									contentLocTbl.setChangeStatus("C");
									contentLocTbl.setEqId(archiveStatus.getEqId());
								} else if("CA".equals(archiveStatus.getProcessCd())) { // h264 복본 아카이브
									if(logger.isDebugEnabled()) {
										logger.debug("copy completed - obj_name: "+ archiveStatus.getObjectName());
									}
									//contentLocTbl.setCyn("N");
									contentLocTbl.setCopyProgress("100");
									contentLocTbl.setCopyStatus("C");
									/*
									contentsInstTbl.setEtc("");
									contentsInstTbl.setCtiId(contentLocTbl.getCtiId());
									contentsInstTbl.setModDt(archiveStatus.getUpdtDtm());

									archiveStatusService.updateContentInst(contentsInstTbl);
									 */
								} else if("BA".equals(archiveStatus.getProcessCd())) { // h264 소산 아카이브
									if(logger.isDebugEnabled()) {
										logger.debug("backup completed - obj_name: "+ archiveStatus.getObjectName());
									}
									contentLocTbl.setBackupProgress("100");
									contentLocTbl.setBackupStatus("C");
									contentLocTbl.setBackupDt(Utility.getTimestamp("yyyyMMddHHmmss"));
								}

								archiveStatusService.updateStatus(contentLocTbl);
							} else if(archiveStatus.getJobStatus().equals("E") && StringUtils.isNotBlank(archiveStatus.getErrorId())){

								String ctiId = archiveStatus.getObjectName().substring(3);
								contentLocTbl.setCtiId(Long.valueOf(ctiId));

								if(logger.isDebugEnabled()) {
									logger.debug("archive error - obj_name: "+ archiveStatus.getObjectName());
									logger.debug("archive error - cti_id: "+ archiveStatus.getObjectName().substring(3));
								}

								if("MA".equals(archiveStatus.getProcessCd())) { // 아카이브
									contentLocTbl.setAstatus("69");
									contentLocTbl.setJobStatus("E");
									contentLocTbl.setUpdtDtm("");

									if(logger.isDebugEnabled()) {
										p_end = System.currentTimeMillis();
										logger.debug( "updateCompleteMaster start: " + (  p_end - p_start )/1000.0 );
									}
									archiveStatusService.updateCompleteMaster(contentLocTbl);
									if(logger.isDebugEnabled()) {
										p_end = System.currentTimeMillis();
										logger.debug( "updateCompleteMaster end: " + (  p_end - p_start )/1000.0 );
									}
								} else if("MT".equals(archiveStatus.getProcessCd())) { // MXF -> H264 변환
									contentLocTbl.setChangeStatus("E");

									// 에러코드 등록
									ErrorLogTbl errorLogTbl = new ErrorLogTbl();
									errorLogTbl.setKey(Long.valueOf(ctiId));
									errorLogTbl.setErrorType(archiveStatus.getErrorId());
									errorLogTbl.setErrorCont(archiveStatus.getErrorMsg());
									errorLogTbl.setEqId(Integer.valueOf(archiveStatus.getEqId()));
									errorLogTbl.setProcessId(archiveStatus.getProcessCd());
									errorLogTbl.setJobId(archiveStatus.getJobId());
									errorLogTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
									errorLogTbl.setServerNm(archiveStatus.getEqId());

									archiveReqService.insertErrorLog(errorLogTbl);

								} else if("CA".equals(archiveStatus.getProcessCd())) { // h264 복본 아카이브
									contentLocTbl.setCopyStatus("E");
								} else if("BA".equals(archiveStatus.getProcessCd())) { // h264 소산 아카이브
									contentLocTbl.setBackupStatus("E");
								}

								archiveStatusService.updateStatus(contentLocTbl);
							}
						}
					} else if(archiveStatus.getJobId().equals("006")) { // copy
						if(logger.isDebugEnabled()) {
							logger.debug("job_id   : "+archiveStatus.getJobId());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}

						ContentLocTbl contentLocTbl = new ContentLocTbl();
						ContentInstTbl contentsInstTbl = new ContentInstTbl();
						if("CD".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setDownProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setDownStatus(archiveStatus.getJobStatus());
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
							contentLocTbl.setEqId(archiveStatus.getEqId());
						} else if("MA".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setJobStatus(archiveStatus.getJobStatus());
						}
						contentLocTbl.setObjName(archiveStatus.getObjectName());

						if(archiveStatus.getJobStatus().equals("C") && archiveStatus.getProgress() == 100) {
							String ctiId = archiveStatus.getObjectName().substring(3);
							contentLocTbl.setCtiId(Long.valueOf(ctiId));
							contentLocTbl.setCyn("Y");
							contentLocTbl.setAstatus("250");
							contentsInstTbl.setEtc("");
							contentsInstTbl.setCtiId(contentLocTbl.getCtiId());
							contentsInstTbl.setModDt(archiveStatus.getUpdtDtm());
							archiveStatusService.updateContentInst(contentsInstTbl);
							archiveStatusService.updateStatus(contentLocTbl);
						} else if(archiveStatus.getJobStatus().equals("E") && StringUtils.isNotBlank(archiveStatus.getErrorId())){
							contentLocTbl.setAstatus("69");
						}

						archiveStatusService.backupStatus(contentLocTbl);
					} else if(archiveStatus.getJobId().equals("007")) { // restore
						if(logger.isDebugEnabled()) {
							logger.debug("restore_job_id : "+archiveStatus.getJobId());
							logger.debug("restore_obj_id : "+archiveStatus.getRestoreId());
						}

						/*
						 * 2012.12.20 배치 다운로드를 위해 추가
						 * 새로 추가한 DB Table(BATCH_DOWN_TBL)에 해당 num 값이 존재하는지 확인하여 있다면 배치 작업으로 간주한다.
						 * 아니라면 기존 사용자 다운로드 요청
						 * 배치 작업이라면 배치용 TC에 작업 요청
						 */
						if(logger.isDebugEnabled()) {
							p_end = System.currentTimeMillis();
							logger.debug( "restore getBatchDown start: " + (  p_end - p_start )/1000.0 );
						}
						ContentDownTbl batchDownTbl = archiveStatusService.getBatchDown(archiveStatus.getRestoreId(), Boolean.valueOf("true"));
						if(logger.isDebugEnabled()) {
							p_end = System.currentTimeMillis();
							logger.debug( "restore getBatchDown end: " + (  p_end - p_start )/1000.0 );
						}
						if(batchDownTbl != null && batchDownTbl.getNum() > 0) {
							if(logger.isDebugEnabled()) {
								logger.debug("Batch Restore Job: "+batchDownTbl.getNum()+", path: "+batchDownTbl.getPath() +", status: "+batchDownTbl.getJobStatus());
							}

							ContentDownTbl contentDownTbl = new ContentDownTbl();
							contentDownTbl.setJobStatus(archiveStatus.getJobStatus());
							contentDownTbl.setProgress(String.valueOf(archiveStatus.getProgress()));
							contentDownTbl.setUpdtUser("DIVA");
							contentDownTbl.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
							contentDownTbl.setNum(archiveStatus.getRestoreId());

							String jobStatus = StringUtils.defaultIfEmpty(archiveStatus.getJobStatus(), "E");
							if(jobStatus.equals("C")) {
								// 다운로드 성공이라면 TC 작업 요청
								contentDownTbl.setFilesize(archiveStatus.getFilesize());
								contentDownTbl.setStatus("250");
							} else if(jobStatus.equals("E")) {
								// 다운로드 실패라면 DB 기록
								contentDownTbl.setStatus("69");
							}

							archiveStatusService.updateBatchDown(contentDownTbl);
						} else {
							try {
								/*
								 * 완료 중복요청을 처리하게 위해 다운로드일경우 DB를 조회하여 이미 완료상태라면
								 * DB를 변경 및 CMS에 요청하지 않고 성공으로 반환 (2012-11-02)
								 */
								ContentDownTbl orgContentDownTbl = archiveStatusService.getConentDown(archiveStatus.getRestoreId(), Boolean.valueOf("true"));
								if(orgContentDownTbl != null && (StringUtils.defaultIfEmpty(orgContentDownTbl.getJobStatus(), "E").equals("C")
										&& StringUtils.defaultIfEmpty(orgContentDownTbl.getStatus(), "69").equals("250"))) {
									continue;
								}

								ContentDownTbl contentDownTbl = new ContentDownTbl();
								contentDownTbl.setJobStatus(archiveStatus.getJobStatus());
								contentDownTbl.setProgress(String.valueOf(archiveStatus.getProgress()));
								contentDownTbl.setUpdtUser("DIVA");
								contentDownTbl.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
								contentDownTbl.setNum(archiveStatus.getRestoreId());

								if(archiveStatus.getJobStatus().trim().equals("C") && archiveStatus.getProgress() == 100) {
									contentDownTbl.setFilesize(archiveStatus.getFilesize());
									contentDownTbl.setStatus("250");
								} else if (archiveStatus.getJobStatus().trim().equals("E") && StringUtils.isNotBlank(archiveStatus.getErrorId())) {
									contentDownTbl.setStatus("69");
									contentDownTbl.setJobStatus(archiveStatus.getJobStatus());
								}

								// contnet_down_tbl에 완료 상태를 저장한다.
								archiveStatusService.restoreStatus(contentDownTbl);
								if(logger.isDebugEnabled()) {
									logger.debug("restore status update - num: "+ archiveStatus.getRestoreId());
								}

								// 완료이거나 에러일경우 CartCont에 상태값을 변경한다.
								if(archiveStatus.getJobStatus().trim().equals("C") || (archiveStatus.getJobStatus() !=null && archiveStatus.getJobStatus().equals("E"))) {
									contentDownTbl = archiveStatusService.getConentDown(archiveStatus.getRestoreId(), Boolean.valueOf("false"));
									archiveStatusService.updateStatusCartCont(contentDownTbl);
									if(logger.isDebugEnabled()) {
										logger.debug("restore cart_cont update - cart_no:"+ contentDownTbl.getCartNo()+", cart_seq: "+contentDownTbl.getCartSeq());
									}
								}

								if(logger.isDebugEnabled()) {
									p_end = System.currentTimeMillis();
									logger.debug( "restore completeDown start: " + (  p_end - p_start )/1000.0 );
								}
								// 완료라면 DAS CMS 서비스를 호출하여 정보를 전달한다. 넘겨주는 정보는 Restore_id
								if(archiveStatus.getJobStatus().trim().equals("C") && archiveStatus.getProgress() == 100) {
									String msg = dasCmsConnector.completeDown(archiveStatus.getRestoreId());
									if(logger.isInfoEnabled()) {
										logger.info("Das CMS Called : "+archiveStatus.getRestoreId()+", return msg: "+msg);
									}
								}
								if(logger.isDebugEnabled()) {
									p_end = System.currentTimeMillis();
									logger.debug( "restore completeDown end: " + ( p_end - p_start )/1000.0 );
								}
							} finally { // 2015.01.19 
								try {
									Thread.sleep(100L);
								} catch (Exception e) {}
							}
							
						}
					} else if(archiveStatus.getJobId().equals("008")) { // delete
						if(logger.isDebugEnabled()) {
							logger.debug("status : "+archiveStatus.getJobStatus());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}
						/* 삭제요청이 완료라면... */
						if(archiveStatus.getJobStatus().trim().equals("E") || archiveStatus.getJobStatus().trim().equals("C")) {
							if(logger.isInfoEnabled()) {
								logger.info("DTL Delete Success!! - "+archiveStatus.getObjectName());
							}

							/*
							 * 2013.08.22 배치 아카이브를 위해 추가
							 * 새로 추가한 DB Table(BATCH_DOWN_TBL)에 해당 objName 값이 존재하는지 확인하여 있다면 배치 작업으로 간주한다.
							 * 아니라면 기존 아카이브 요청
							 */
							ContentDownTbl batchDownTbl = archiveStatusService.getBatchDown(archiveStatus.getObjectName(), Boolean.valueOf("true"));
							if(batchDownTbl != null && batchDownTbl.getNum() > 0) {
								if(logger.isDebugEnabled()) {
									logger.debug("Batch Archive ObjName: "+archiveStatus.getObjectName()+", jobStatus: "+archiveStatus.getJobStatus() +", progress: "+archiveStatus.getProgress());
								}
								if(archiveStatus.getJobStatus().equals("C")) {
									batchDownTbl.setDelDd(Utility.getDate("yyyyMMdd"));
								} else {
									batchDownTbl.setDtlYn("E");
								}
								batchDownTbl.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
								archiveStatusService.updateBatchDown(batchDownTbl);
							}

						} 
					} else if(archiveStatus.getJobId().equals("009")) { // tape_out
						if(logger.isDebugEnabled()) {
							logger.debug("tape_out : "+archiveStatus.getJobId());
							logger.debug("cart_no  : "+archiveStatus.getCartNo());
							logger.debug("cart_seq : "+archiveStatus.getCartSeq());
						}
						tapeErpService.updateTapeOutService(archiveStatus);
					} else if(archiveStatus.getJobId().equals("010")) { // manual copy
						if(logger.isDebugEnabled()) {
							logger.debug("job_id   : "+archiveStatus.getJobId());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}

						ContentLocTbl contentLocTbl = new ContentLocTbl();
						contentLocTbl.setObjName(archiveStatus.getObjectName());

						if("OD".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setDownProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setDownStatus(archiveStatus.getJobStatus());
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
							contentLocTbl.setEqId(archiveStatus.getEqId());
						} else if("CA".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setCopyProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setCopyStatus(archiveStatus.getJobStatus());
						}else if("BA".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setBackupProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setBackupStatus(archiveStatus.getJobStatus());
						}

						if(archiveStatus.getJobStatus().equals("C") && archiveStatus.getProgress() == 100) {
							if("OD".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setDownProgress("100");
								contentLocTbl.setDownStatus("C");
							} else if("HT".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setChangeProgress("100");
								contentLocTbl.setChangeStatus("C");
								contentLocTbl.setEqId(archiveStatus.getEqId());
							} else if("CA".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setCopyProgress("100");
								contentLocTbl.setCopyStatus("C");
							}else if("BA".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setBackupProgress("100");
								contentLocTbl.setBackupStatus("C");
							}
						} else if(archiveStatus.getJobStatus().equals("E") && StringUtils.isNotBlank(archiveStatus.getErrorId())){
							if("OD".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setDownStatus("E");
							} else if("HT".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setChangeStatus("E");

								String ctiId = archiveStatus.getObjectName().substring(3);
								contentLocTbl.setCtiId(Long.valueOf(ctiId));
								contentLocTbl.setUpdtDtm("");

								// 에러코드 등록
								ErrorLogTbl errorLogTbl = new ErrorLogTbl();
								errorLogTbl.setKey(Long.valueOf(ctiId));
								errorLogTbl.setErrorType(archiveStatus.getErrorId());
								errorLogTbl.setErrorCont(archiveStatus.getErrorMsg());
								errorLogTbl.setEqId(Integer.valueOf(archiveStatus.getEqId()));
								errorLogTbl.setProcessId(archiveStatus.getProcessCd());
								errorLogTbl.setJobId(archiveStatus.getJobId());
								errorLogTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
								errorLogTbl.setServerNm(archiveStatus.getEqId());

								archiveReqService.insertErrorLog(errorLogTbl);
							} else if("CA".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setCopyStatus("E");
							}else if("BA".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setBackupStatus("E");
							}
						}

						if("OD".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.backupStatus(contentLocTbl);
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						} else if("CA".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						}else if("BA".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						}

					} else if(archiveStatus.getJobId().equals("011")) { // manual backup

						ContentLocTbl contentLocTbl = new ContentLocTbl();
						contentLocTbl.setObjName(archiveStatus.getObjectName());

						if("CD".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setDownProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setDownStatus(archiveStatus.getJobStatus());
						} else if("BA".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setBackupProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setBackupStatus(archiveStatus.getJobStatus());
						}

						if(archiveStatus.getJobStatus().equals("C") && archiveStatus.getProgress() == 100) {
							if("CD".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setDownProgress("100");
								contentLocTbl.setDownStatus("C");
							} else if("BA".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setBackupProgress("100");
								contentLocTbl.setBackupStatus("C");
							}
						} else if(archiveStatus.getJobStatus().equals("E") && StringUtils.isNotBlank(archiveStatus.getErrorId())){
							if("CD".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setDownStatus("E");
							} else if("BA".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setBackupStatus("E");
							}
						}

						if("CD".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.backupStatus(contentLocTbl);
						} else if("BA".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						}
					} else if(archiveStatus.getJobId().equals("012")) { // recovery
						ContentLocTbl contentLocTbl = new ContentLocTbl();
						contentLocTbl.setObjName(archiveStatus.getObjectName());

						if("CD".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setDownProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setDownStatus(archiveStatus.getJobStatus());
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
							contentLocTbl.setEqId(archiveStatus.getEqId());
						} else if("MA".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setJobStatus(archiveStatus.getJobStatus());
						}

						if(archiveStatus.getJobStatus().equals("C") && archiveStatus.getProgress() == 100) {
							if("CD".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setDownProgress("100");
								contentLocTbl.setDownStatus("C");
							} else if("HT".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setChangeProgress("100");
								contentLocTbl.setChangeStatus("C");
								contentLocTbl.setEqId(archiveStatus.getEqId());
							} else if("MA".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setProgress("100");
								contentLocTbl.setJobStatus("C");
							}
						} else if(archiveStatus.getJobStatus().equals("E") && StringUtils.isNotBlank(archiveStatus.getErrorId())){
							if("CD".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setDownStatus("E");
							} else if("HT".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setChangeStatus("E");

								String ctiId = archiveStatus.getObjectName().substring(3);
								contentLocTbl.setCtiId(Long.valueOf(ctiId));
								contentLocTbl.setUpdtDtm("");

								// 에러코드 등록
								ErrorLogTbl errorLogTbl = new ErrorLogTbl();
								errorLogTbl.setKey(Long.valueOf(ctiId));
								errorLogTbl.setErrorType(archiveStatus.getErrorId());
								errorLogTbl.setErrorCont(archiveStatus.getErrorMsg());
								errorLogTbl.setEqId(Integer.valueOf(archiveStatus.getEqId()));
								errorLogTbl.setProcessId(archiveStatus.getProcessCd());
								errorLogTbl.setJobId(archiveStatus.getJobId());
								errorLogTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
								errorLogTbl.setServerNm(archiveStatus.getEqId());

								archiveReqService.insertErrorLog(errorLogTbl);

							} else if("MA".equals(archiveStatus.getProcessCd())) {
								contentLocTbl.setJobStatus("E");
							}
						}

						if("CD".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.backupStatus(contentLocTbl);
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						} else if("MA".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						}
					}
				} catch (Exception e) {
					logger.error("Archive Status update error!! - ", e);
				}
				
				if(logger.isDebugEnabled()) {
					p_end = System.currentTimeMillis();
					logger.debug( "job gubun:"+archiveStatus.getJobId()+", objname: "+archiveStatus.getObjectName()+", end_time: "+ ( p_end - p_start )/1000.0 );
				}
			}
		}
		
		if(logger.isDebugEnabled()) {
			end = System.currentTimeMillis();
			logger.debug( "archive status end : " + ( end - start )/1000.0 );
		}
		return new Boolean(true).toString();
	}

	public String getStatus(String xml) throws RemoteException {
		return null;
	}

	public String nonErpAddClipService(String xml) throws RemoteException {

		if(logger.isDebugEnabled()){
			logger.debug("nonErpAddClipService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("nonErpAddClipService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		Map<String, Object> workflow = new HashMap<String, Object>();

		// 등록자 ID가 XML의 여러 부분중 한곳에서만 존재해도 사용함.
		String regrid = "";
		{
			regrid = (StringUtils.isNotBlank(das.getInfo().getRegrid())) ? das.getInfo().getRegrid() : 
				(das.getDbTable() != null && das.getDbTable().getMaster() != null && StringUtils.isNotBlank(das.getDbTable().getMaster().getRegrid())) ? das.getDbTable().getMaster().getRegrid() :
					(das.getDbTable() != null && das.getDbTable().getContent() != null && StringUtils.isNotBlank(das.getDbTable().getContent().getRegrid())) ? das.getDbTable().getContent().getRegrid() :
						(das.getDbTable() != null && das.getDbTable().getContentInst() != null && StringUtils.isNotBlank(das.getDbTable().getContentInst().getRegrid())) ? das.getDbTable().getContentInst().getRegrid() :
							(das.getDbTable() != null && das.getDbTable().getContentMap() != null && StringUtils.isNotBlank(das.getDbTable().getContentMap().getRegrid())) ? das.getDbTable().getContentMap().getRegrid() :
								"";
		}

		/****************************************************************************************************/
		/**************************************** Master Set ************************************************/
		/****************************************************************************************************/
		MasterTbl masterTbl = new MasterTbl();
		try {
			Long masterId = 0L;

			// Master 정보가 DB에 존재하는경우 update
			if(das.getDbTable().getMaster().getMasterId() > 0) {
				masterId = das.getDbTable().getMaster().getMasterId();

				if(StringUtils.isNotBlank(das.getDbTable().getMaster().getBrdLeng())) {
					DecimalFormat df = new DecimalFormat("00,00,00,00");
					if(das.getDbTable().getMaster().getBrdLeng().indexOf(":") > -1) {
						masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng());
					} else if(das.getDbTable().getMaster().getBrdLeng().indexOf(";") > -1) {
						masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng().replaceAll("\\;", ":"));
					} else {
						Integer brdLengInt = Integer.valueOf(das.getDbTable().getMaster().getBrdLeng());
						String brdLeng = df.format(brdLengInt).replaceAll("\\,", ":");
						masterTbl.setBrdLeng(brdLeng);
					}
				}
				das.getDbTable().getMaster().setMasterId(masterId);
				das.getDbTable().getContentMap().setMasterId(masterId);
				masterTbl.setModrid(regrid);
				masterTbl.setMasterId(masterId);
				masterTbl.setAddClip(false);
				logger.info("이미 존재하는 마스터 입니다" + masterId);
				workflow.put("master", masterTbl);
			} else {
				masterId = addClipForTapeService.getMasterNewId();
				if(logger.isDebugEnabled()){
					logger.debug("get New Master_id: "+masterId);
				}
				masterTbl.setMasterId(masterId);

				DecimalFormat df = new DecimalFormat("00,00,00,00");
				if(das.getDbTable().getMaster().getBrdLeng().indexOf(":") > -1) {
					masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng());
				} else if(das.getDbTable().getMaster().getBrdLeng().indexOf(";") > -1) {
					masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng().replaceAll("\\;", ":"));
				} else {
					Integer brdLengInt = Integer.valueOf(das.getDbTable().getMaster().getBrdLeng());
					String brdLeng = df.format(brdLengInt).replaceAll("\\,", ":");
					masterTbl.setBrdLeng(brdLeng);
				}

				/*				// 입력받은 방송일이 존재한다면 20110719 박보아대리님의 요청으로 최종화여부 없앰.
				if(StringUtils.isBlank(das.getDbTable().getMaster().getBrdDd())) {
					masterTbl.setFinalBrdYn("N");
				} else {
					Date date1 = Utility.getDate(das.getDbTable().getMaster().getBrdDd(), "yyyyMMdd");
					Date date2 = new Date();

					int val = date1.compareTo(date2);
					if(val > -1) {
						masterTbl.setFinalBrdYn("N");
					} else {
						masterTbl.setFinalBrdYn("Y");
					}
				}*/
				masterTbl.setFinalBrdYn("");
				masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());
				masterTbl.setRistClfCd(das.getDbTable().getMaster().getRistClfCd());
				masterTbl.setFmDt(das.getDbTable().getMaster().getFmdt());
				masterTbl.setDelDd("");
				masterTbl.setMasterId(masterId);
				masterTbl.setRegrid(regrid);
				masterTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				masterTbl.setDataStatCd("001");
				masterTbl.setErrorStatCd("000");
				masterTbl.setUseYn("Y");
				masterTbl.setPgmId(0L);
				masterTbl.setTitle(das.getDbTable().getProgram().getPgmNm());
				masterTbl.setSpcInfo(das.getDbTable().getMaster().getSpcInfo());

				String tapeClf = StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getCtgrLCd(), "200");
				masterTbl.setCtgrLCd(tapeClf);

				masterTbl.setPrdtInOutsCd("001");

				if(StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getCoCd(), "S").equals("S")) {
					masterTbl.setCprtType("003");
					masterTbl.setCprtTypeDsc("SBS");
					masterTbl.setCocd("S");
					masterTbl.setChennelCd("A");
				} else {
					masterTbl.setCprtType("003");
					masterTbl.setCprtTypeDsc("MediaNet");
					masterTbl.setCocd(das.getDbTable().getMaster().getCoCd());
					masterTbl.setChennelCd(das.getDbTable().getMaster().getChennelCd());
				}

				masterTbl.setRsvPrdCd("000");
				masterTbl.setLockStatCd("N");
				masterTbl.setArchRoute("DP");
				masterTbl.setManualYn("Y");
				masterTbl.setCtgrMCd("");
				masterTbl.setCtgrSCd("");

				das.getDbTable().getMaster().setMasterId(masterId);
				das.getDbTable().getContentMap().setMasterId(masterId);

				masterTbl.setAddClip(true);
				//	workflow.put("master", masterTbl);
			}
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Master 정보를 취합하는데 실패했습니다.", ae.getStackTrace());
			return Boolean.toString(false);
		}

		/****************************************************************************************************/
		/**************************************** Content Set ***********************************************/
		/****************************************************************************************************/

		String m2Prefix = "";
		String m4Prefix = "";

		String m4Path = "";
		if(StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getCoCd(), "S").equals("S")) {
			m2Prefix = messageSource.getMessage("das.mpeg2.drive", null, Locale.KOREA);
			m4Prefix = messageSource.getMessage("das.mpeg4.drive", null, Locale.KOREA);
		} else {
			m2Prefix = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA);
			m4Prefix = messageSource.getMessage("net.mpeg4.drive", null, Locale.KOREA);
		}
		m4Path = m4Prefix+"/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd")+"/";

		ContentTbl contentTbl = new ContentTbl();
		Long ctId = 0L;
		try {
			ctId = das.getDbTable().getContent().getCtId();
			if(ctId > 0) {
				das.getDbTable().getContentInst().setCtId(das.getDbTable().getContent().getCtId());
				das.getDbTable().getContentMap().setCtId(das.getDbTable().getContent().getCtId());

				// Content update kfrm path
				if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getCtiFmt()) && 
						das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {
					if(logger.isDebugEnabled()) {
						logger.debug("kfrm path: "+m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
					}
					contentTbl.setKfrmPath(m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
					contentTbl.setCtId(das.getDbTable().getContent().getCtId());
					contentTbl.setModrid(regrid);
					contentTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					contentTbl.setTotKfrmNums(das.getDbTable().getContent().getTotKfrmNums());

					das.getDbTable().getContent().setKfrmPath(m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
				}else {
					contentTbl.setCtId(das.getDbTable().getContent().getCtId());
					contentTbl.setModrid(regrid);
					contentTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				}

				contentTbl.setAddClip(false);
				workflow.put("content", contentTbl);
			} else {
				// get Content New ID
				ctId = addClipForTapeService.getContentNewId();

				contentTbl.setCtId(ctId);
				contentTbl.setRegrid(regrid);
				contentTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				contentTbl.setDelDd("");
				contentTbl.setCtLeng(das.getDbTable().getContent().getCtLeng());
				contentTbl.setDuration(das.getDbTable().getContent().getDuration());
				contentTbl.setDelYn("N");
				contentTbl.setArchiveYn("N");
				contentTbl.setCopyObjectYn("N");
				contentTbl.setUseYn("Y");
				if(das.getDbTable().getContent().getCtSeq() == null || das.getDbTable().getContent().getCtSeq() <= 0) {
					contentTbl.setCtSeq(1);
				}

				if(StringUtils.isNotBlank(das.getDbTable().getContent().getCtNm())) {
					if(das.getDbTable().getContent().getCtNm().equals("전타이틀")) {
						contentTbl.setCtTyp("001");
					} else if(das.getDbTable().getContent().getCtNm().equals("전CM")) {
						contentTbl.setCtTyp("004");
					} else if(das.getDbTable().getContent().getCtNm().equals("본방송")) {
						contentTbl.setCtTyp("003");
					} else if(das.getDbTable().getContent().getCtNm().equals("후CM")) {
						contentTbl.setCtTyp("004");
					} else if(das.getDbTable().getContent().getCtNm().equals("후타이틀")) {
						contentTbl.setCtTyp("002");
					} else if(das.getDbTable().getContent().getCtNm().equals("전체")) {
						contentTbl.setCtTyp("006");
					}
					contentTbl.setCtNm(das.getDbTable().getContent().getCtNm());
				}

				// [001:원본, 005:clean, 006:방송본, 010:개편본, 011:종편본]
				if(StringUtils.isBlank(das.getDbTable().getContent().getCtCla())) {
					contentTbl.setCtCla("006");
				} else {
					contentTbl.setCtCla(das.getDbTable().getContent().getCtCla());
				}

				if(logger.isDebugEnabled()) {
					logger.debug("CT_NM  : "+contentTbl.getCtNm());
					logger.debug("CT_TYP : "+contentTbl.getCtTyp());
					logger.debug("CT_CLA : "+contentTbl.getCtCla());
					logger.debug("CT_SEQ : "+contentTbl.getCtSeq());
				}


				// 전타이틀, 본방송, 전체 영상이라면 카탈로그를 생성을 위한 CT_ID를 셋팅한다.
				if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {
					if(contentTbl.getCtTyp().equals("001") || contentTbl.getCtTyp().equals("003") || contentTbl.getCtTyp().equals("006")) {
						String rpImg = StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getRpimgKfrmSeq(), "0");
						masterTbl.setRpimgKfrmSeq(Integer.valueOf(rpImg));
						masterTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
					}
				}


				// 인제스트 등록일
				masterTbl.setIngRegDd(Utility.getTimestamp("yyyyMMdd"));


				// Master Object hash put

				contentTbl.setVdQlty(das.getDbTable().getContent().getVdQlty());
				contentTbl.setAspRtoCd(das.getDbTable().getContent().getAspRtoCd());

				das.getDbTable().getContentMap().setEDuration(das.getDbTable().getContent().getDuration());
				das.getDbTable().getContentMap().setCtSeq(contentTbl.getCtSeq());
				das.getDbTable().getContent().setCtId(ctId);
				das.getDbTable().getContentInst().setCtId(ctId);
				das.getDbTable().getContentMap().setCtId(ctId);

				contentTbl.setAddClip(true);
				workflow.put("master", masterTbl);
				workflow.put("content", contentTbl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Contents 정보를 취합하는데 에러가 발생했습니다.", e.getStackTrace());
			return Boolean.toString(false);
		}

		/****************************************************************************************************/
		/**************************************** Corner Set ************************************************/
		/****************************************************************************************************/
		CornerTbl cornerTbl = new CornerTbl();
		try {
			if(das.getDbTable().getContentMap().getCnId() <= 0) {

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clfCd", DASConstants.CLF_CD_CN_Type);
				params.put("desc", das.getDbTable().getContent().getCtNm());

				CodeTbl codeTbl = null;
				try {
					codeTbl = addClipForTapeService.getCodeObj(params, new Boolean(true));
				} catch (Exception e) {
					logger.error("코드 정보를 조회하는데 실패했습니다.", e);
				}

				// New Corner Insert
				Long cnId = addClipForTapeService.getCornerNewId();
				das.getDbTable().getContentMap().setCnId(cnId);
				cornerTbl.setCnId(cnId);
				cornerTbl.setMasterId(das.getDbTable().getMaster().getMasterId());

				if(codeTbl == null || StringUtils.isBlank(codeTbl.getSclCd())) {
					// Master ID에 대한 코너는 존재하고, 입력된 CT_NM이 코너의 유형명이 아님
					cornerTbl.setCnTypeCd(das.getDbTable().getContent().getCtTyp());
				} else {
					// Master ID에 대한 코너가 존재하고 입력된 CT_NM이 코너의 유형명임
					cornerTbl.setCnTypeCd(codeTbl.getSclCd());
				}

				cornerTbl.setCnNm(das.getDbTable().getContent().getCtNm());

				if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {
					cornerTbl.setRpimgKfrmSeq(masterTbl.getRpimgKfrmSeq());
					cornerTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
				} else {
					cornerTbl.setRpimgKfrmSeq(0);
					cornerTbl.setRpimgCtId(0L);
				}

				cornerTbl.setRegrid(regrid);
				cornerTbl.setDuration(das.getDbTable().getContent().getDuration());
				cornerTbl.setSom("00:00:00:00");
				cornerTbl.setEom(Utility.changeDuration(das.getDbTable().getContent().getDuration()));

				cornerTbl.setAddClip(true);
				workflow.put("corner", cornerTbl);
			}
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Corner 정보를 취합하는데 에러가 발생했습니다.", ae.getStackTrace());
			return Boolean.toString(false);
		}


		/****************************************************************************************************/
		/************************************ Content Instance Set ******************************************/
		/****************************************************************************************************/

		ContentInstTbl contentInstTbl = new ContentInstTbl();
		try {
			contentInstTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));

			if(das.getDbTable().getContentInst().getCtiId() > 0) {
				contentInstTbl.setAddClip(false);

				contentInstTbl.setCtiId(das.getDbTable().getContentInst().getCtiId());
				contentInstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				contentInstTbl.setModrid(regrid);
				// 업데이트 [File size 변경]
				contentInstTbl.setFlSz(das.getDbTable().getContentInst().getFlSz());
				workflow.put("contentInst", contentInstTbl);
			} else {
				// 신규 추가
				contentInstTbl.setAddClip(true);

				Long newInstID = addClipForTapeService.getContentInstNewId();
				contentInstTbl.setCtiId(newInstID);
				contentInstTbl.setCtId(das.getDbTable().getContent().getCtId());

				das.getDbTable().getContentInst().setCtiId(newInstID);

				// I/F CMS open 여부
				boolean openYn = Boolean.valueOf(messageSource.getMessage("das.system.open", null, Locale.KOREA));

				String filePath = "";
				// 영상 포맷 체크, 
				if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getCtiFmt())) {
					if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') { // Mpeg2
						if(StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getCoCd(), "S").equals("S")) {
							if(openYn) {
								filePath = m2Prefix+"/SBS/manual/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");
							} else {
								filePath = m2Prefix+"/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");
							}
						} else {
							filePath = m2Prefix+"/MediaNet/manual/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");
						}
					} else if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') { // Mpeg4
						filePath = m4Path + das.getDbTable().getContentInst().getCtId();
					} else {
						logger.error("해당 영상의 format이 비정의된 정보입니다. - "+das.getDbTable().getContentInst().getCtiFmt());
						return Boolean.toString(false);
					}
					das.getDbTable().getContentInst().setFilePath(filePath);

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("clfCd", DASConstants.CLF_CD_ContentsInstanceFormat);
					params.put("sclCd", das.getDbTable().getContentInst().getCtiFmt());

					CodeTbl codeTbl = addClipForTapeService.getCodeObj(params, new Boolean(true));

					if(StringUtils.isNotBlank(codeTbl.getRmk2())) {
						contentInstTbl.setWrkFileNm(das.getDbTable().getContentInst().getCtiId()+"."+codeTbl.getRmk2());
					}

					if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getOnAirMediaAudioChannel())) {
						contentInstTbl.setRecordTypeCd(das.getDbTable().getContentInst().getOnAirMediaAudioChannel());
					}

					contentInstTbl.setCtiFmt(das.getDbTable().getContentInst().getCtiFmt());
					contentInstTbl.setFlSz(das.getDbTable().getContentInst().getFlSz());
					/*
					 * 가로*세로 해상도가 바뀌어 있다면 원래 위치로 복원해준다.
					 * 2016.03.30
					 */
					if(das.getDbTable().getContentInst().getVdHresol() < das.getDbTable().getContentInst().getVdVreSol()) {
						contentInstTbl.setVdHresol(das.getDbTable().getContentInst().getVdVreSol());
						contentInstTbl.setVdVresol(das.getDbTable().getContentInst().getVdHresol());
					} else {
						contentInstTbl.setVdHresol(das.getDbTable().getContentInst().getVdHresol());
						contentInstTbl.setVdVresol(das.getDbTable().getContentInst().getVdVreSol());
					}
					contentInstTbl.setBitRt(das.getDbTable().getContentInst().getBitRt());
					contentInstTbl.setFrmPerSec("29.97");
					contentInstTbl.setColorCd(das.getDbTable().getContentInst().getColorCd());
					contentInstTbl.setAudioYn(das.getDbTable().getContentInst().getAudioYn());
					contentInstTbl.setAudTypeCd(das.getDbTable().getContentInst().getAudioType());
					contentInstTbl.setRegrid(regrid);
					contentInstTbl.setRecordTypeCd("001");
					contentInstTbl.setArchSteYn("N");
					contentInstTbl.setIngestEqId(das.getInfo().getDasEqId());
					contentInstTbl.setFlPath(das.getDbTable().getContentInst().getFilePath());
					contentInstTbl.setAudioBdwt(das.getDbTable().getContentInst().getAudioBdwt());
					contentInstTbl.setAudSampFrq(das.getDbTable().getContentInst().getAudSampFrq());
					contentInstTbl.setAudLanCd(das.getDbTable().getContentInst().getAudLanCd());
					contentInstTbl.setDrpFrmYn(das.getDbTable().getContentInst().getDrpFrmYn());
					contentInstTbl.setWmvYn("Y");
					workflow.put("contentInst", contentInstTbl);
				} else {
					logger.error("영상에대한 format[mpeg2 or mpeg4] 정보가 없습니다.");
					return Boolean.toString(false);
				}
			}
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Content Instance 정보를 취합하는데 실패했습니다", ae.getStackTrace());
			return Boolean.toString(false);
		}

		/****************************************************************************************************/
		/************************************** Content Map Set *********************************************/
		/****************************************************************************************************/
		ContentMapTbl contentMapTbl = new ContentMapTbl();
		contentMapTbl.setRegrid(regrid);

		try {
			contentMapTbl.setCtId(das.getDbTable().getContentMap().getCtId());
			contentMapTbl.setMasterId(das.getDbTable().getContentMap().getMasterId());
			contentMapTbl.setCnId(das.getDbTable().getContentMap().getCnId());
			contentMapTbl.seteDuration(das.getDbTable().getContentMap().getEDuration());
			contentMapTbl.setPgmId(0L);
			contentMapTbl.setModrid(regrid);
			contentMapTbl.setDelDd("");

			int contentMapCount = addClipForTapeService.getContentMapCount(contentMapTbl, new Boolean(true));
			if(contentMapCount == 0) {
				contentMapTbl.setAddClip(true);
				contentMapTbl.setRegrid(regrid);
				contentMapTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			} else {
				contentMapTbl.setAddClip(false);

				contentMapTbl.setModrid(regrid);
				contentMapTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			}
			workflow.put("contentMap", contentMapTbl);
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Conents Map 정보를 취합하는데 에러가 발생했습니다.", ae.getStackTrace());
			return Boolean.toString(false);
		}

		/****************************************************************************************************/
		/************************************** AnnotInfo Set ***********************************************/
		/****************************************************************************************************/
		if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') {
			AnnotInfoTbl annotInfoTbl = new AnnotInfoTbl();

			annotInfoTbl.setRegrid(regrid);
			annotInfoTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			annotInfoTbl.setCnId(das.getDbTable().getContentMap().getCnId());
			annotInfoTbl.setCtId(contentTbl.getCtId());
			annotInfoTbl.setMasterId(masterTbl.getMasterId());
			annotInfoTbl.setSom("00:00:00:00");
			annotInfoTbl.setEom(das.getDbTable().getMaster().getBrdLeng());
			annotInfoTbl.setDuration(das.getDbTable().getContent().getDuration());

			if(StringUtils.isBlank(das.getDbTable().getMaster().getRistClfCd())) {
				annotInfoTbl.setAnnotClfCd("007");
			} else {
				annotInfoTbl.setAnnotClfCd(das.getDbTable().getMaster().getRistClfCd());
			}

			annotInfoTbl.setGubun("L");
			annotInfoTbl.setAnnotClfCont("");
			annotInfoTbl.setAddClip(true);

			workflow.put("annotInfo", annotInfoTbl);
		}

		if(logger.isDebugEnabled()) {
			logger.debug("return xml: "+xmlStream.toXML(das));
		}


		/****************************************************************************************************/
		/*********************************** DB All Insert or Update ****************************************/
		/****************************************************************************************************/
		try {
			addClipForTapeService.saveAddClipInfo(workflow);
		} catch (ApplicationException ae) {
			logger.error("메타정보를 등록하는 과정에서 에러가 발생했습니다.", ae.getStackTrace());
			return Boolean.toString(false);
		}

		workflow.clear();
		workflow = null;
		return xmlStream.toXML(das);
	}

	public void tmpService(String xml) throws RemoteException {
		// TODO Auto-generated method stub

	}



	public void testMethod(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("nonErpAddClipService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("nonErpAddClipService xml parsing error!! - "+e.getMessage());
		}

		Report report = das.getReport();

		NotReportMsgTbl notReportMsgTbl = new NotReportMsgTbl();
		notReportMsgTbl.setUid(report.getRegrid());
		notReportMsgTbl.setDasEqClfCd(report.getDasEqPsCd());
		notReportMsgTbl.setRetryNo(10);
		notReportMsgTbl.setHost("120");
		notReportMsgTbl.setCheckstamp("2011");
		notReportMsgTbl.setMsg("hello");
		notReportMsgTbl.setPort("110");
		notReportMsgTbl.setRegDt("2001");

		try {
			notReportedService.insertNotReported(notReportMsgTbl);
		} catch (Exception e) {
			logger.error("testMethod", e);
		}

	}

	@SuppressWarnings("unused")
	public String subAddClipService(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("subAddClipService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("subAddClipService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		//등록자
		String regrid = "";
		{
			regrid = (StringUtils.isNotBlank(das.getInfo().getRegrid())) ? das.getInfo().getRegrid() : 
				(das.getDbTable() != null && das.getDbTable().getMaster() != null && StringUtils.isNotBlank(das.getDbTable().getMaster().getRegrid())) ? das.getDbTable().getMaster().getRegrid() :
					(das.getDbTable() != null && das.getDbTable().getContent() != null && StringUtils.isNotBlank(das.getDbTable().getContent().getRegrid())) ? das.getDbTable().getContent().getRegrid() :
						(das.getDbTable() != null && das.getDbTable().getContentInst() != null && StringUtils.isNotBlank(das.getDbTable().getContentInst().getRegrid())) ? das.getDbTable().getContentInst().getRegrid() :
							(das.getDbTable() != null && das.getDbTable().getContentMap() != null && StringUtils.isNotBlank(das.getDbTable().getContentMap().getRegrid())) ? das.getDbTable().getContentMap().getRegrid() :
								"";
		}

		Map<String, Object> workflow = new HashMap<String, Object>();

		MediaTapeInfoTbl mediaTapeInfoTbl = null;
		MasterTbl masterTbl = null;
		Long masterId = 0L;

		// Master ID가 존재한다면
		if(das.getDbTable().getMaster() != null && das.getDbTable().getMaster().getMasterId() > 0) {
			try {
				masterId = das.getDbTable().getMaster().getMasterId();
				masterTbl = addClipForTapeService.getMasterObj(das.getDbTable().getMaster().getMasterId());

				if(StringUtils.isBlank(das.getDbTable().getMaster().getReqCd())) {
					das.getDbTable().getMaster().setReqCd(masterTbl.getReqCd());
				}
			} catch (ServiceException ae) {
				logger.error("Master 정보를 조회중 에러발생", ae);
			}
		}

		// MediaNet Tape 인제스트인 경우 청구번호가 반드시 존재
		if(StringUtils.isNotBlank(das.getDbTable().getMaster().getReqCd())) {

			Map<String, Object> params = new HashMap<String, Object>();
			if(masterTbl == null) {
				if(das.getDbTable().getMaster() == null || (das.getDbTable().getMaster().getMasterId() == null ||
						das.getDbTable().getMaster().getMasterId() <= 0)) {
					params.put("reqCd", das.getDbTable().getMaster().getReqCd());

					if(das.getDbTable().getMaster().getEpisNo() == null)
						das.getDbTable().getMaster().setEpisNo(0);

					params.put("episNo", das.getDbTable().getMaster().getEpisNo());
					params.put("wait", "true");

					try {
						if(masterTbl == null) {
							masterTbl = archiveReqService.getMaster(params);
							if(masterTbl != null){
								masterId=masterTbl.getMasterId();
							}
						}
					} catch (DaoNonRollbackException e) {
						logger.error("Master 조회중 에러발생", e);
					}
				} else {
					masterId = das.getDbTable().getMaster().getMasterId();
				}
			} else {
				if(logger.isDebugEnabled()){
					logger.debug("get Master_id from reqCd: "+masterTbl.getMasterId());
				}
				masterId = masterTbl.getMasterId();
			}

			// Tape 정보를 DASDB에서 조회한다.
			if(StringUtils.isNotBlank(das.getDbTable().getMaster().getReqCd())) {
				try {
					params.put("reqCd", das.getDbTable().getMaster().getReqCd());
					if(das.getDbTable().getMaster().getEpisNo() == null)
						das.getDbTable().getMaster().setEpisNo(0);

					params.put("episNo", das.getDbTable().getMaster().getEpisNo());
					params.put("sceanNo", das.getDbTable().getContentMap().getCnSeq());
					params.put("wait", "true");

					mediaTapeInfoTbl = addClipForTapeService.getMediaTapeInfo(params);
				} catch (ServiceException e) {
					logger.error("MediaNet Tape 정보를 조회중 에러발생!, 청구번호 - "+das.getDbTable().getMaster().getReqCd(), e);
					return Boolean.toString(false);
				}
			}
		} else {
			logger.error("Tape 정보가 ERP에 없습니다.");
			return Boolean.toString(false);
		}


		/****************************************************************************************************/
		/**************************************** Master Set ************************************************/
		/****************************************************************************************************/
		try {
			DecimalFormat df = new DecimalFormat("00,00,00");
			if(masterTbl != null && masterTbl.getMasterId() > 0) {
				das.getDbTable().getMaster().setMasterId(masterId);
				das.getDbTable().getContentMap().setMasterId(masterId);

				String dataStatCd = das.getDbTable().getMaster().getDataStatCd();
				if(StringUtils.isBlank(dataStatCd)) {
					dataStatCd = "001";
				} else if(dataStatCd.equals("002")) {
					dataStatCd = "001";
				}

				masterTbl.setDataStatCd(dataStatCd);
				masterTbl.setDelDd("");
				masterTbl.setErrorStatCd("000");
				masterTbl.setLockStatCd("N");
				masterTbl.setModrid(regrid);
				masterTbl.setMasterId(masterId);
				masterTbl.setSpcInfo(masterTbl.getSpcInfo() + "  / " + das.getDbTable().getMaster().getSpcInfo());
				if(mediaTapeInfoTbl != null && StringUtils.isNotBlank(mediaTapeInfoTbl.getRistClfCd())) {
					masterTbl.setRistClfCd(mediaTapeInfoTbl.getRistClfCd());
				}

				masterTbl.setAddClip(false);

				workflow.put("master", masterTbl);
			} else if(masterTbl == null && masterId > 0) {

				das.getDbTable().getMaster().setMasterId(masterId);

				masterTbl = new MasterTbl();

				masterTbl.setMasterId(masterId);
				masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());
				masterTbl.setFmDt(das.getDbTable().getMaster().getBrdDd());
				masterTbl.setFinalBrdYn(das.getDbTable().getMaster().getFinalBrdYn());
				masterTbl.setBrdBgnHms(das.getDbTable().getMaster().getBrdBgnHms());
				masterTbl.setBrdEndHms(das.getDbTable().getMaster().getBrdEndHms());

				if(das.getDbTable().getMaster().getBrdLeng().indexOf(":") > -1) {
					masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng());
				} else if(das.getDbTable().getMaster().getBrdLeng().indexOf(";") > -1) {
					masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng().replaceAll("\\;", ":"));
				} else {
					Integer brdLengInt = Integer.valueOf(das.getDbTable().getMaster().getBrdLeng());
					String brdLeng = df.format(brdLengInt).replaceAll("\\,", ":");
					masterTbl.setBrdLeng(brdLeng);
				}

				masterTbl.setModrid(regrid);
				masterTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				masterTbl.setManualYn("N");
				masterTbl.setDelDd("");
				masterTbl.setAddClip(false);
				masterTbl.setTapeId(das.getDbTable().getMaster().getTapeId());
				masterTbl.setTapeItemId(das.getDbTable().getMaster().getTapeItemId());
				if(mediaTapeInfoTbl != null && StringUtils.isNotBlank(mediaTapeInfoTbl.getRistClfCd())) {
					masterTbl.setRistClfCd(mediaTapeInfoTbl.getRistClfCd());
				}
				das.getDbTable().getContentMap().setMasterId(masterId);

				workflow.put("master", masterTbl);
			} else { // Master DB Insert

				Long newMasterId = 0L;
				if (masterTbl == null) masterTbl = new MasterTbl();

				newMasterId = addClipForTapeService.getMasterNewId();
				if(logger.isDebugEnabled()){
					logger.debug("get New Master_id: "+masterId);
				}


				masterTbl.setMasterId(newMasterId);
				masterTbl.setRegrid(regrid);
				masterTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				masterTbl.setDataStatCd("001");
				masterTbl.setErrorStatCd("000");
				masterTbl.setFinalBrdYn("N");
				masterTbl.setUseYn("Y");
				masterTbl.setTapeMediaClfCd(mediaTapeInfoTbl.getTapeMediaClfCd());
				masterTbl.setPgmId(0L);
				masterTbl.setCocd(das.getDbTable().getMaster().getCoCd());
				masterTbl.setChennelCd(das.getDbTable().getMaster().getChennelCd());

				das.getDbTable().getMaster().setMasterId(newMasterId);
				das.getDbTable().getContentMap().setMasterId(newMasterId);



				String tapeClf = (mediaTapeInfoTbl != null) ? mediaTapeInfoTbl.getCtgrLCd() : das.getDbTable().getMaster().getTapeMediaClfCd();
				logger.debug("get New tapeClf: "+tapeClf);
				if(StringUtils.isNotBlank(tapeClf)) {
					if(tapeClf.equals("100")) masterTbl.setCtgrLCd("100");
					else if(tapeClf.equals("200")) masterTbl.setCtgrLCd("200");
					else if(tapeClf.equals("300")) masterTbl.setCtgrLCd("300");
					else masterTbl.setCtgrLCd("200");
				} else {
					masterTbl.setCtgrLCd("200");
				}
				masterTbl.setCtgrMCd(mediaTapeInfoTbl.getCtgrMCd());
				masterTbl.setCtgrSCd(mediaTapeInfoTbl.getCtgrSCd());


				if(StringUtils.isBlank(das.getDbTable().getMaster().getTitle())) {
					if((mediaTapeInfoTbl != null)) {
						if(StringUtils.isNotBlank(mediaTapeInfoTbl.getTitle())) {
							masterTbl.setTitle(mediaTapeInfoTbl.getTitle());
						} else {
							masterTbl.setTitle(mediaTapeInfoTbl.getSubTtl());
						}
					} else masterTbl.setTitle("");
				} else {
					masterTbl.setTitle(das.getDbTable().getMaster().getTitle());
				}


				if(StringUtils.isBlank(das.getDbTable().getMaster().getBrdLeng())) {
					if(mediaTapeInfoTbl != null && StringUtils.isNotBlank(mediaTapeInfoTbl.getTapeLeng())) {
						String brdLeng = mediaTapeInfoTbl.getTapeLeng().replaceAll("\\,", ":");
						masterTbl.setBrdLeng(brdLeng);
					}
				} else {
					if(das.getDbTable().getMaster().getBrdLeng().indexOf(":") > -1) {
						masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng());
					} else if(das.getDbTable().getMaster().getBrdLeng().indexOf(";") > -1) {
						masterTbl.setBrdLeng(das.getDbTable().getMaster().getBrdLeng().replaceAll("\\;", ":"));
					} else {
						Integer brdLengInt = Integer.valueOf(das.getDbTable().getMaster().getBrdLeng());
						String brdLeng = df.format(brdLengInt).replaceAll("\\,", ":");
						masterTbl.setBrdLeng(brdLeng);
					}
				}

				if(mediaTapeInfoTbl != null) {
					masterTbl.setSubTtl(mediaTapeInfoTbl.getSubTtl());
					masterTbl.setCmrDrtNm(mediaTapeInfoTbl.getCmrDrtNm());
					masterTbl.setCmrPlace(mediaTapeInfoTbl.getCmrPlace());

					if(StringUtils.isBlank(mediaTapeInfoTbl.getBrdDd())) {
						masterTbl.setFmDt(das.getDbTable().getMaster().getBrdDd());
						masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());
					} else {
						masterTbl.setFmDt(mediaTapeInfoTbl.getBrdDd());
						masterTbl.setBrdDd(mediaTapeInfoTbl.getBrdDd());
					}

					/*
					if(mediaTapeInfoTbl.getCtgrLCd().equals("100")) 	   // 촬영일
						masterTbl.setFmDt(das.getDbTable().getMaster().getBrdDd());
					else													
						masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());   // 방송일
					 */

					masterTbl.setCastNm(mediaTapeInfoTbl.getCastNm());
					masterTbl.setOrgPrdrNm(mediaTapeInfoTbl.getOrgPrdrNm());
					masterTbl.setDrtNm(mediaTapeInfoTbl.getDrtNm());
					masterTbl.setEpisNo(mediaTapeInfoTbl.getEpisNo().intValue());
					masterTbl.setKeyWords(mediaTapeInfoTbl.getKeyWords());
					masterTbl.setCprtrNm(mediaTapeInfoTbl.getCprtNm());
					masterTbl.setCprtType(mediaTapeInfoTbl.getCprtType());
					masterTbl.setMcNm(mediaTapeInfoTbl.getMcNm());
					masterTbl.setWriterNm(mediaTapeInfoTbl.getWriterNm());
					masterTbl.setPrdtDeptNm(mediaTapeInfoTbl.getOrgPrdrNm());
					masterTbl.setReqCd(mediaTapeInfoTbl.getReqCd());
					masterTbl.setViewGrCd(mediaTapeInfoTbl.getViewGrCd());
					masterTbl.setPrdtInOutsCd(mediaTapeInfoTbl.getPrdtInOutsCd());
					masterTbl.setPrdtDeptNm(mediaTapeInfoTbl.getPrdtDeptNm());
					masterTbl.setProducerNm(mediaTapeInfoTbl.getProducerNm());
					masterTbl.setTapeMediaClfCd(StringUtils.defaultIfEmpty(mediaTapeInfoTbl.getTapeMediaClfCd(), "999"));
					masterTbl.setRistClfCd(StringUtils.defaultIfEmpty(mediaTapeInfoTbl.getRistClfCd(), "007"));
				}

				if(StringUtils.isNotBlank(das.getDbTable().getMaster().getRpimgKfrmSeq()))
					masterTbl.setRpimgKfrmSeq(Integer.parseInt((das.getDbTable().getMaster().getRpimgKfrmSeq())));
				else
					masterTbl.setRpimgKfrmSeq(0);
				masterTbl.setSpcInfo(das.getDbTable().getMaster().getSpcInfo());


				masterTbl.setRsvPrdCd("000");
				masterTbl.setLockStatCd("N");
				masterTbl.setManualYn("N");
				masterTbl.setRsvPrdEndDd("99991231");
				/*
				 * 아카이브경로(OL:온에어 생방, OA : 온에어 송출 , P: PDS , DP:  ERP TAPE 미존재 매체변환, DE :  ERP TAPE 존재  매체변환)
				 */
				masterTbl.setArchRoute("D");

				masterTbl.setDelDd("");
				masterTbl.setCocd("M");
				masterTbl.setArtist(mediaTapeInfoTbl.getArtist());
				masterTbl.setCountryCd(mediaTapeInfoTbl.getCountryCd());
				masterTbl.setAddClip(true);

				workflow.put("master", masterTbl);
			}

			workflow.put("master", masterTbl);
		} catch (Exception e) {
			logger.error("Master 정보를 취합하는데 실패했습니다.", e);
			return Boolean.toString(false);
		}

		/****************************************************************************************************/
		/**************************************** Content Set ***********************************************/
		/****************************************************************************************************/
		ContentTbl contentTbl = new ContentTbl();

		String m2Prefix = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA);
		String m4Prefix = messageSource.getMessage("net.mpeg4.drive", null, Locale.KOREA);

		String m4Path = m4Prefix+"/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd")+"/";
		try {
			if(das.getDbTable().getContent().getCtId() == null || das.getDbTable().getContent().getCtId() <= 0) {

				contentTbl.setAddClip(true);

				Long ctId = addClipForTapeService.getContentNewId();
				// get Content New ID
				contentTbl.setCtId(ctId);
				contentTbl.setRegrid(regrid);
				contentTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				contentTbl.setDelDd("");
				contentTbl.setCtLeng(das.getDbTable().getContent().getCtLeng());
				contentTbl.setDuration(das.getDbTable().getContent().getDuration());
				contentTbl.setDelYn("N");
				contentTbl.setArchiveYn("N");
				contentTbl.setCopyObjectYn("N");

				das.getDbTable().getContent().setCtId(ctId);
				das.getDbTable().getContentInst().setCtId(ctId);
				das.getDbTable().getContentMap().setCtId(ctId);

				contentTbl.setUseYn("Y");

				// 장면번호를 저장
				if(das.getDbTable().getContentMap().getCnSeq() == null || das.getDbTable().getContentMap().getCnSeq() <= 0) {
					contentTbl.setCtSeq(1);
				} else {
					contentTbl.setCtSeq(das.getDbTable().getContentMap().getCnSeq());
				}
				das.getDbTable().getContentMap().setCtSeq(contentTbl.getCtSeq());

				if(logger.isDebugEnabled()) {
					logger.debug("Content NewID: "+contentTbl.getCtId());
					logger.debug("Master McuID : "+das.getDbTable().getMaster().getMcuId());
				}

				if(StringUtils.isNotBlank(das.getDbTable().getContent().getCtNm())) {
					if(das.getDbTable().getContent().getCtNm().equals("전타이틀")) {
						contentTbl.setCtTyp("001");
					} else if(das.getDbTable().getContent().getCtNm().equals("전CM")) {
						contentTbl.setCtTyp("004");
					} else if(das.getDbTable().getContent().getCtNm().equals("본방송")) {
						contentTbl.setCtTyp("003");
					} else if(das.getDbTable().getContent().getCtNm().equals("후CM")) {
						contentTbl.setCtTyp("004");
					} else if(das.getDbTable().getContent().getCtNm().equals("후타이틀")) {
						contentTbl.setCtTyp("002");
					} else if(das.getDbTable().getContent().getCtNm().equals("전체")) {
						contentTbl.setCtTyp("006");
					}
					contentTbl.setCtNm(das.getDbTable().getContent().getCtNm());
				}


				// [001:원본, 005:clean, 006:방송본, 010:개편본, 011:종편본]
				if(StringUtils.isBlank(das.getDbTable().getContent().getCtCla())) {
					contentTbl.setCtCla("006");
				} else {
					contentTbl.setCtCla(das.getDbTable().getContent().getCtCla());
				}

				if(logger.isDebugEnabled()) {
					logger.debug("CT_NM  : "+contentTbl.getCtNm());
					logger.debug("CT_TYP : "+contentTbl.getCtTyp());
					logger.debug("CT_SEQ : "+contentTbl.getCtSeq());
				}

				// 전타이틀, 본방송, 전체 영상이라면 카탈로그를 생성을 위한 CT_ID를 셋팅한다.
				if(contentTbl.getCtTyp().equals("001") || contentTbl.getCtTyp().equals("003") || contentTbl.getCtTyp().equals("006")) {
					masterTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
				}

				// 인제스트 등록일
				masterTbl.setIngRegDd(Utility.getTimestamp("yyyyMMdd"));

				// Master Object hash put
				workflow.put("master", masterTbl);

				// 본방송 영상이고 MXF 영상이라면...
				if(contentTbl.getCtTyp().equals("003") && das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') {
					if(mediaTapeInfoTbl != null) {
						contentTbl.setKeyWords(mediaTapeInfoTbl.getKeyWords());
					}
				}
				contentTbl.setVdQlty(das.getDbTable().getContent().getVdQlty());
				contentTbl.setAspRtoCd(das.getDbTable().getContent().getAspRtoCd());

				das.getDbTable().getContentMap().setEDuration(das.getDbTable().getContent().getDuration());

			} else {

				das.getDbTable().getContentInst().setCtId(das.getDbTable().getContent().getCtId());
				das.getDbTable().getContentMap().setCtId(das.getDbTable().getContent().getCtId());

				if(StringUtils.isNotBlank(das.getDbTable().getContent().getCtNm())) {
					if(das.getDbTable().getContent().getCtNm().equals("전타이틀")) {
						contentTbl.setCtTyp("001");
					} else if(das.getDbTable().getContent().getCtNm().equals("전CM")) {
						contentTbl.setCtTyp("004");
					} else if(das.getDbTable().getContent().getCtNm().equals("본방송")) {
						contentTbl.setCtTyp("003");
					} else if(das.getDbTable().getContent().getCtNm().equals("후CM")) {
						contentTbl.setCtTyp("004");
					} else if(das.getDbTable().getContent().getCtNm().equals("후타이틀")) {
						contentTbl.setCtTyp("002");
					} else if(das.getDbTable().getContent().getCtNm().equals("전체")) {
						contentTbl.setCtTyp("006");
					}
					contentTbl.setCtNm(das.getDbTable().getContent().getCtNm());
				}

				if(logger.isDebugEnabled()) {
					logger.debug("ct_id: "+das.getDbTable().getContent().getCtId());
					logger.debug("cti_fmt: "+das.getDbTable().getContentInst().getCtiFmt());
				}
				// Content update kfrm path
				if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getCtiFmt()) && 
						das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') {

					if(logger.isDebugEnabled()) {
						logger.debug("kfrm path: "+m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
					}
					contentTbl.setKfrmPath(m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
					contentTbl.setCtId(das.getDbTable().getContent().getCtId());
					contentTbl.setModrid(regrid);
					contentTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					contentTbl.setTotKfrmNums(das.getDbTable().getContent().getTotKfrmNums());

					das.getDbTable().getContent().setKfrmPath(m4Path + das.getDbTable().getContent().getCtId() + "/KFRM");
				}

				contentTbl.setAddClip(false);
			}

			workflow.put("content", contentTbl);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Contents 정보를 취합하는데 에러가 발생했습니다.", e.getStackTrace());
			return Boolean.toString(false);
		}


		/****************************************************************************************************/
		/**************************************** Corner Set ************************************************/
		/****************************************************************************************************/
		CornerTbl cornerTbl = new CornerTbl();
		try {
			if(das.getDbTable().getContentMap().getCnId() <= 0) {

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clfCd", DASConstants.CLF_CD_CN_Type);
				params.put("desc", das.getDbTable().getContent().getCtNm());

				CodeTbl codeTbl = null;
				try {
					codeTbl = addClipForTapeService.getCodeObj(params, new Boolean(true));
				} catch (Exception e) {
					logger.error("코드 정보를 조회하는데 실패했습니다.", e);
				}


				// New Corner Insert
				Long cnId = addClipForTapeService.getCornerNewId();
				das.getDbTable().getContentMap().setCnId(cnId);
				cornerTbl.setCnId(cnId);
				cornerTbl.setMasterId(das.getDbTable().getMaster().getMasterId());

				if(codeTbl == null || StringUtils.isBlank(codeTbl.getSclCd())) {
					// Master ID에 대한 코너는 존재하고, 입력된 CT_NM이 코너의 유형명이 아님
					//cornerTbl.setCnTypeCd(messageSource.getMessage("das.basic.corner", null, Locale.KOREA)); // 003
					cornerTbl.setCnTypeCd(das.getDbTable().getContent().getCtTyp());
				} else {
					// Master ID에 대한 코너가 존재하고 입력된 CT_NM이 코너의 유형명임
					cornerTbl.setCnTypeCd(codeTbl.getSclCd());
				}

				if(mediaTapeInfoTbl != null) {
					cornerTbl.setCnNm(StringUtils.defaultString(mediaTapeInfoTbl.getSubTtl(), ""));
					cornerTbl.setCnInfo(StringUtils.defaultString(mediaTapeInfoTbl.getCont(), ""));
				} else {
					if(contentTbl.getCtTyp().equals("001")) {
						cornerTbl.setCnNm("전타이틀");
					}else if(contentTbl.getCtTyp().equals("002")){
						cornerTbl.setCnNm("후타이틀");
					}

				}

				if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) != '1') {
					cornerTbl.setRpimgKfrmSeq(masterTbl.getRpimgKfrmSeq());
					cornerTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
				} else {
					cornerTbl.setRpimgKfrmSeq(0);
					cornerTbl.setRpimgCtId(0L);
				}

				cornerTbl.setRegrid(regrid);
				cornerTbl.setDuration(das.getDbTable().getContent().getDuration());
				cornerTbl.setSom("00:00:00:00");
				cornerTbl.setEom(Utility.changeDuration(das.getDbTable().getContent().getDuration()));

				cornerTbl.setAddClip(true);
				workflow.put("corner", cornerTbl);
			} else {
				cornerTbl = addClipForTapeService.getCornerObj(das.getDbTable().getContentMap().getCnId(), true);
			}
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Corner 정보를 취합하는데 에러가 발생했습니다.", ae.getStackTrace());
			return Boolean.toString(false);
		}


		/****************************************************************************************************/
		/************************************ Content Instance Set ******************************************/
		/****************************************************************************************************/
		ContentInstTbl contentInstTbl = new ContentInstTbl();
		try {
			contentInstTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));

			if(das.getDbTable().getContentInst().getCtiId() > 0) {
				contentInstTbl.setAddClip(false);

				contentInstTbl.setCtiId(das.getDbTable().getContentInst().getCtiId());
				contentInstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				//contentInstTbl.setFlPath( das.getDbTable().getContentInst().getFilePath());

				// 업데이트 [File size 변경]
				contentInstTbl.setFlSz(das.getDbTable().getContentInst().getFlSz());
				workflow.put("contentInst", contentInstTbl);


			} else {
				// 신규 추가
				contentInstTbl.setAddClip(true);

				Long newInstID = addClipForTapeService.getContentInstNewId();
				contentInstTbl.setCtiId(newInstID);
				contentInstTbl.setCtId(das.getDbTable().getContent().getCtId());

				das.getDbTable().getContentInst().setCtiId(newInstID);

				String filePath = "";
				// 영상 포맷 체크, 
				if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getCtiFmt())) {
					if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') { // Mpeg2
						filePath = m2Prefix+"/MediaNet/manual/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");
					} else if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '2') { // Mpeg4
						filePath = m4Path + das.getDbTable().getContentInst().getCtId();
					} else {
						logger.error("해당 영상의 format이 비정의된 정보입니다. - "+das.getDbTable().getContentInst().getCtiFmt());
						return Boolean.toString(false);
					}
					das.getDbTable().getContentInst().setFilePath(filePath);

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("clfCd", DASConstants.CLF_CD_ContentsInstanceFormat);
					params.put("sclCd", das.getDbTable().getContentInst().getCtiFmt());

					CodeTbl codeTbl = addClipForTapeService.getCodeObj(params, new Boolean(true));

					if(StringUtils.isNotBlank(codeTbl.getRmk2())) {
						contentInstTbl.setWrkFileNm(das.getDbTable().getContentInst().getCtiId()+"."+codeTbl.getRmk2());
					}

					if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getOnAirMediaAudioChannel())) {
						contentInstTbl.setRecordTypeCd(das.getDbTable().getContentInst().getOnAirMediaAudioChannel());
					}

					contentInstTbl.setCtiFmt(das.getDbTable().getContentInst().getCtiFmt());
					contentInstTbl.setFlSz(das.getDbTable().getContentInst().getFlSz());
					/*
					 * 가로*세로 해상도가 바뀌어 있다면 원래 위치로 복원해준다.
					 * 2016.03.30
					 */
					if(das.getDbTable().getContentInst().getVdHresol() < das.getDbTable().getContentInst().getVdVreSol()) {
						contentInstTbl.setVdHresol(das.getDbTable().getContentInst().getVdVreSol());
						contentInstTbl.setVdVresol(das.getDbTable().getContentInst().getVdHresol());
					} else {
						contentInstTbl.setVdHresol(das.getDbTable().getContentInst().getVdHresol());
						contentInstTbl.setVdVresol(das.getDbTable().getContentInst().getVdVreSol());
					}
					contentInstTbl.setBitRt(das.getDbTable().getContentInst().getBitRt());
					contentInstTbl.setFrmPerSec("29.97");
					contentInstTbl.setColorCd(das.getDbTable().getContentInst().getColorCd());
					contentInstTbl.setAudioYn(das.getDbTable().getContentInst().getAudioYn());
					contentInstTbl.setAudTypeCd(das.getDbTable().getContentInst().getAudioType());
					contentInstTbl.setRegrid(regrid);


					/*if(StringUtils.isNotBlank(das.getDbTable().getContentInst().getOnAirMediaAudioChannel())) {
						if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("M")) {
							contentInstTbl.setRecordTypeCd("001");
						} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("S")) {
							contentInstTbl.setRecordTypeCd("002");
						} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("F")) {
							contentInstTbl.setRecordTypeCd("003");
						} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("B")) {
							contentInstTbl.setRecordTypeCd("004");
						} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("V")) {
							contentInstTbl.setRecordTypeCd("005");
						} else if(das.getDbTable().getContentInst().getOnAirMediaAudioChannel().equals("O")) {
							contentInstTbl.setRecordTypeCd("006");
						} 
						contentTbl.setCtNm(das.getDbTable().getContent().getCtNm());
					} else {
						contentInstTbl.setRecordTypeCd("001");
					}*/
					if(StringUtils.isNotBlank(mediaTapeInfoTbl.getRecordTypeCd())) {

						contentInstTbl.setRecordTypeCd(mediaTapeInfoTbl.getRecordTypeCd());

						//	contentTbl.setCtNm(das.getDbTable().getContent().getCtNm());
					} else {
						contentInstTbl.setRecordTypeCd("001");
					}

					contentInstTbl.setArchSteYn("N");
					contentInstTbl.setIngestEqId(das.getInfo().getDasEqId());
					contentInstTbl.setFlPath(das.getDbTable().getContentInst().getFilePath());
					contentInstTbl.setAudioBdwt(das.getDbTable().getContentInst().getAudioBdwt());
					contentInstTbl.setAudSampFrq(das.getDbTable().getContentInst().getAudSampFrq());
					contentInstTbl.setAudLanCd(das.getDbTable().getContentInst().getAudLanCd());
					contentInstTbl.setDrpFrmYn(das.getDbTable().getContentInst().getDrpFrmYn());
					contentInstTbl.setWmvYn("Y");


					workflow.put("contentInst", contentInstTbl);
				} else {
					logger.error("영상에대한 format[mpeg2 or mpeg4] 정보가 없습니다.");
					return Boolean.toString(false);
				}
			}
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Content Instance 정보를 취합하는데 실패했습니다", ae.getStackTrace());
			return Boolean.toString(false);
		}

		/****************************************************************************************************/
		/************************************** Content Map Set *********************************************/
		/****************************************************************************************************/
		ContentMapTbl contentMapTbl = new ContentMapTbl();
		contentMapTbl.setRegrid(regrid);

		try {
			contentMapTbl.setCtId(das.getDbTable().getContentMap().getCtId());
			contentMapTbl.setMasterId(das.getDbTable().getContentMap().getMasterId());
			contentMapTbl.setCnId(das.getDbTable().getContentMap().getCnId());
			contentMapTbl.seteDuration(das.getDbTable().getContentMap().getEDuration());
			contentMapTbl.setsDuration(0L);
			contentMapTbl.setPgmId(0L);
			contentMapTbl.setDelDd("");
			int contentMapCount = addClipForTapeService.getContentMapCount(contentMapTbl, new Boolean(true));
			if(contentMapCount == 0) {
				contentMapTbl.setAddClip(true);
				contentMapTbl.setRegrid(regrid);
				contentMapTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			} else {
				contentMapTbl.setAddClip(false);

				contentMapTbl.setModrid(regrid);
				contentMapTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			}
			workflow.put("contentMap", contentMapTbl);
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Conents Map 정보를 취합하는데 에러가 발생했습니다.", ae.getStackTrace());
			return Boolean.toString(false);
		}

		/****************************************************************************************************/
		/************************************** AnnotInfo Set ***********************************************/
		/****************************************************************************************************/
		if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') {
			AnnotInfoTbl annotInfoTbl = new AnnotInfoTbl();

			logger.debug("cn_id: "+cornerTbl.getCnId());
			annotInfoTbl.setRegrid(regrid);
			annotInfoTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			annotInfoTbl.setCnId(cornerTbl.getCnId());
			annotInfoTbl.setCtId(contentTbl.getCtId());
			annotInfoTbl.setMasterId(masterTbl.getMasterId());
			annotInfoTbl.setSom(cornerTbl.getSom());
			annotInfoTbl.setEom(cornerTbl.getEom());
			annotInfoTbl.setDuration(cornerTbl.getDuration());

			if(mediaTapeInfoTbl != null) {
				if(masterTbl != null) {
					if(StringUtils.isBlank(masterTbl.getRistClfCd())) {
						annotInfoTbl.setAnnotClfCd("007");
					} else {
						annotInfoTbl.setAnnotClfCd(masterTbl.getRistClfCd());
					}
				} else {
					if(StringUtils.isBlank(mediaTapeInfoTbl.getRistClfCd())) {
						annotInfoTbl.setAnnotClfCd("007");
					} else{
						annotInfoTbl.setAnnotClfCd(mediaTapeInfoTbl.getRistClfCd());
					}
				}
			} else {
				if(masterTbl != null) {
					if(StringUtils.isBlank(masterTbl.getRistClfCd())) {
						annotInfoTbl.setAnnotClfCd("007");
					} else {
						annotInfoTbl.setAnnotClfCd(masterTbl.getRistClfCd());
					}
				} else {
					annotInfoTbl.setAnnotClfCd("007");
				}
			}

			/*
			if(masterTbl != null) {
				if(StringUtils.isBlank(masterTbl.getRistClfCd())) {
					annotInfoTbl.setAnnotClfCd("007");
				} else {
					annotInfoTbl.setAnnotClfCd(masterTbl.getRistClfCd());
				}
			} else if (masterTbl == null && mediaTapeInfoTbl != null) {
				if(StringUtils.isBlank(mediaTapeInfoTbl.getRistClfCd())) {
					annotInfoTbl.setAnnotClfCd("007");
				} else{
					annotInfoTbl.setAnnotClfCd(mediaTapeInfoTbl.getRistClfCd());
				}
			} else {
				annotInfoTbl.setAnnotClfCd("007");
			}
			 */

			annotInfoTbl.setGubun("L");
			annotInfoTbl.setAddClip(true);
			workflow.put("annotInfo", annotInfoTbl);
		}

		if(logger.isDebugEnabled()) {
			logger.debug("return xml: "+xmlStream.toXML(das));
		}

		/****************************************************************************************************/
		/*********************************** DB All Insert or Update ****************************************/
		/****************************************************************************************************/
		try {
			addClipForTapeService.saveAddClipInfo(workflow);
		} catch (ApplicationException ae) {
			logger.error("메타정보를 등록하는 과정에서 에러가 발생했습니다.", ae);
			return Boolean.toString(false);
		}

		workflow.clear();
		workflow = null;

		return xmlStream.toXML(das);
	}

	public String transcodeService(String xml) throws RemoteException {

		if(logger.isDebugEnabled()){
			logger.debug("transcodeService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("transcodeService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		try {
			StringBuffer tcxml = new StringBuffer();
			//윈도우 드라이버 명을 unix드라이브명으로 변경필요
			tcxml.append("")
			.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			.append("<das>")
			.append("<tcProcessinginfo>")
			.append("<CT_ID>"+das.getDbTable().getContent().getCtId()+"</CT_ID>")
			.append("<INPUT_HR>"+das.getDbTable().getContentInst().getFilePath()+"</INPUT_HR>")
			.append("<INPUT_HR_NM>"+das.getDbTable().getContentInst().getCtiId()+".mxf</INPUT_HR_NM>")
			.append("</tcProcessinginfo>")
			.append("</das>");
			if(logger.isDebugEnabled()) {
				logger.debug("tc req xml: "+tcxml.toString());
			}

			int i=0;
			while(i<3) {
				try {
					dasCmsConnector.insertTranscodeReq(tcxml.toString());
					break;
				} catch (Exception e) {
					i++;
					if(i==3) throw e;
					logger.error("insertTranscodereq Retry ("+i+")", e);
					Thread.sleep(1000L);
				}
			}

		} catch (Exception e) {
			logger.error("트랜스코딩을 등록하는 과정에서 에러가 발생했습니다.", e);
			return Boolean.toString(false);
		}

		return Boolean.toString(true);
	}

	public String recoveryService(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("recoveryService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("recoveryService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		try {
			ArchiveRequest request = new ArchiveRequest();

			request.setObjectName(das.getInfo().getOutSystemId());
			if(das.getInfo().getCoCd().equals("S")){
				request.setCategory("cms_h.264");
			}else{
				request.setCategory("NETcms_h.264");
			}

			request.setFilename(das.getInfo().getFilename()); 

			String req_xml = xmlStream.toXML(request);
			if(logger.isDebugEnabled()){
				logger.debug("recovery req_xml: "+req_xml);
			}

			String result = divaConnector.recovery(req_xml);
			logger.error("recovery result" + result);
		} catch (Exception e) {
			logger.error("divaConnector request error!!", e);
			return Boolean.toString(false);
		}
		return Boolean.toString(true);
	}

	public String changePriorityService(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("changePriorityService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("changePriorityService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		ArchiveRequest request = new ArchiveRequest();
		try {

			if(das.getInfo().getGubun().equals("007")) { // 다운로드 취소
				request.setRestoreId(das.getInfo().getJobId());
			}

			request.setObjectName(das.getInfo().getOutSystemId());
			request.setPriority(Integer.valueOf(das.getInfo().getPriority())*10);

			String req_xml = xmlStream.toXML(request);
			if(logger.isDebugEnabled()){
				logger.debug("changePrioity req_xml: "+req_xml);
			}

			divaConnector.changePrioity(req_xml);
		} catch (Exception e) {
			logger.error("divaConnector request error!!", e);
			return Boolean.toString(false);
		}
		return Boolean.toString(true);
	}

	public String cancelService(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("cancelService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("cancelService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		ArchiveRequest request = new ArchiveRequest();
		try {
			/*
			 * 취소 요청이 '아카이브' 인지 '다운로드' 인지에 대한 구분자 필요
			 */
			if(das.getInfo().getGubun().equals("007")) { // 다운로드 취소
				request.setRestoreId(das.getInfo().getJobId());
			}
			request.setObjectName(das.getInfo().getOutSystemId());
			request.setPriority(100);

			String req_xml = xmlStream.toXML(request);
			if(logger.isDebugEnabled()){
				logger.debug("cancelService req_xml: "+req_xml);
			}
			divaConnector.cancelJob(req_xml);
		} catch (Exception e) {
			logger.error("divaConnector request error!!", e);
			return Boolean.toString(false);
		}
		return Boolean.toString(true);
	}

	public String deleteService(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("deleteService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("deleteService xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		ArchiveRequest request = new ArchiveRequest();
		try {

			String coCd = archiveReqService.getCode(das.getInfo().getOutSystemId());
			String category = null;
			if(coCd.equals("S")) {
				category = "cms";
			} else {
				category = "netcms";
			}
			request.setCategory(category);
			request.setObjectName(das.getInfo().getOutSystemId());
			request.setPriority(90);

			String req_xml = xmlStream.toXML(request);
			if(logger.isDebugEnabled()){
				logger.debug("deleteService req_xml: "+req_xml);
			}
			divaConnector.delete(req_xml);
		} catch (Exception e) {
			logger.error("divaConnector request error!!", e);
			return Boolean.toString(false);
		}
		return Boolean.toString(true);
	}

	public String findDiskQuota(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("findDiskQuota Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findDiskQuota xml parsing error!! - "+e.getMessage());
			return Boolean.toString(false);
		}

		List<StorageInfoTbl> infoTbls = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("wait", "true");
			if(das.getInfo() != null && StringUtils.isNotBlank(das.getInfo().getDiskPath())) {
				params.put("path", das.getInfo().getDiskPath());
			}

			String m2Prefix = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA);
			infoTbls = resourceCheckService.findDiskQuota(params);
			StorageInfo storageInfo = new StorageInfo();
			storageInfo.setPath(m2Prefix);
			storageInfo.setFolderNm("고용량 스토리지");
			storageInfo.setTotalVolume((Long)Utility.QUOTA_CHECK.get(m2Prefix));
			das.addStorages(storageInfo);

			for(StorageInfoTbl storageInfoTbl : infoTbls) {

				if(Utility.QUOTA_CHECK.containsKey(storageInfoTbl.getPath())) {
					storageInfo = new StorageInfo();

					Long diskSize = (Long)Utility.QUOTA_CHECK.get(storageInfoTbl.getPath());

					storageInfo.setPath(storageInfoTbl.getPath());
					storageInfo.setFolderNm(storageInfoTbl.getFolderNm());
					storageInfo.setTotalVolume(storageInfoTbl.getTotalVolume());
					storageInfo.setUseVolume(diskSize);
					storageInfo.setLimit(storageInfoTbl.getLimit());
					storageInfo.setDesc(storageInfoTbl.getDesc());

					das.addStorages(storageInfo);
				}
			}
			das.setInfo(null);
		} catch (Exception e) {
			logger.error("findDiskQuota search error!!", e);
			return Boolean.toString(false);
		}
		return xmlStream.toXML(das);
	}

	public String updateDiskQuota(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("updateDiskQuota Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("updateDiskQuota xml parsing error!!", e);
			return Boolean.toString(false);
		}

		try {
			StorageInfo storageInfo = das.getStorageInfo();

			StorageInfoTbl storageInfoTbl = new StorageInfoTbl();
			storageInfoTbl.setTotalVolume(storageInfo.getTotalVolume());
			storageInfoTbl.setLimit(storageInfo.getLimit());
			storageInfoTbl.setPath(storageInfo.getPath());
			storageInfoTbl.setLastModified(Utility.getTimestamp("yyyyMMddHHmmss"));

			resourceCheckService.updateDiskQuota(storageInfoTbl);
		} catch (Exception e) {
			logger.error("updateDiskQuota update error!!", e);
			return Boolean.toString(false);
		}
		return Boolean.toString(true);
	}

	public String resourceUseInfo(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("resourceUseInfo Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("resourceUseInfo xml parsing error!!", e);
			return Boolean.toString(false);
		}

		try {
			String resPath = messageSource.getMessage("das.resource.path", null, Locale.KOREA);

			String resName = das.getInfo().getDasEqId() == 11 ? "server1" : "server2";
			ServerResource serverResource = new ServerResource();

			String mes = FileUtils.readFileToString(new File(resPath+File.separator+resName+".res"), "UTF-8");
			if(StringUtils.isNotBlank(mes)) {
				if(logger.isDebugEnabled()) {
					logger.debug("res path : "+resPath+File.separator+resName+".res => "+mes);
				}
				String[] mess = mes.split("\\|");

				CpuInfo cpuInfo = new CpuInfo();
				cpuInfo.setUse(Double.valueOf(mess[0]));
				cpuInfo.setFree(Double.valueOf(mess[1]));
				serverResource.setCpuInfo(cpuInfo);

				MemoryInfo memoryInfo = new MemoryInfo();
				memoryInfo.setUse(Double.valueOf(mess[2]));
				memoryInfo.setFree(Double.valueOf(mess[3]));
				serverResource.setMemoryInfo(memoryInfo);
			}
			das.addResources(serverResource);
			das.setInfo(null);
		} catch (Exception e) {
			logger.error("resourceUseInfo search error!!", e);
			return Boolean.toString(false);
		}
		return xmlStream.toXML(das);
	}

	public String manualMasterRegister(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("manualMasterRegister Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("manualMasterRegister xml parsing error!!", e);
			return Boolean.toString(false);
		}

		Map<String, Object> workflow = new HashMap<String, Object>();

		// 등록자 ID가 XML의 여러 부분중 한곳에서만 존재해도 사용함.
		String regrid = "";
		{
			regrid = (StringUtils.isNotBlank(das.getInfo().getRegrid())) ? das.getInfo().getRegrid() : 
				(das.getDbTable() != null && das.getDbTable().getMaster() != null && StringUtils.isNotBlank(das.getDbTable().getMaster().getRegrid())) ? das.getDbTable().getMaster().getRegrid() :
					(das.getDbTable() != null && das.getDbTable().getContent() != null && StringUtils.isNotBlank(das.getDbTable().getContent().getRegrid())) ? das.getDbTable().getContent().getRegrid() :
						(das.getDbTable() != null && das.getDbTable().getContentInst() != null && StringUtils.isNotBlank(das.getDbTable().getContentInst().getRegrid())) ? das.getDbTable().getContentInst().getRegrid() :
							(das.getDbTable() != null && das.getDbTable().getContentMap() != null && StringUtils.isNotBlank(das.getDbTable().getContentMap().getRegrid())) ? das.getDbTable().getContentMap().getRegrid() :
								"";
		}

		/****************************************************************************************************/
		/**************************************** Master Set ************************************************/
		/****************************************************************************************************/
		MasterTbl masterTbl = new MasterTbl();
		try {
			Long masterId = addClipForTapeService.getMasterNewId();
			if(logger.isDebugEnabled()){
				logger.debug("get New Master_id: "+masterId);
			}
			masterTbl.setMasterId(masterId);

			masterTbl.setFinalBrdYn("");
			masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());
			masterTbl.setRistClfCd(das.getDbTable().getMaster().getRistClfCd());
			masterTbl.setFmDt(das.getDbTable().getMaster().getFmdt());
			masterTbl.setDelDd("");
			masterTbl.setMasterId(masterId);
			masterTbl.setRegrid(regrid);
			masterTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			masterTbl.setDataStatCd("001");
			masterTbl.setErrorStatCd("000");
			masterTbl.setUseYn("Y");
			masterTbl.setPgmId(0L);
			masterTbl.setTitle(das.getDbTable().getProgram().getPgmNm());
			masterTbl.setSpcInfo(das.getDbTable().getMaster().getSpcInfo());
			masterTbl.setPrdtInOutsCd("001");
			masterTbl.setFinalBrdYn("N");
			masterTbl.setTapeId("");
			masterTbl.setTapeMediaClfCd("");

			if(StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getCoCd(), "S").equals("S")) {
				masterTbl.setCprtType("003");
				masterTbl.setCprtTypeDsc("SBS");
				masterTbl.setCocd("S");
				masterTbl.setChennelCd("A");
			} else {
				masterTbl.setCprtType("003");
				masterTbl.setCprtTypeDsc("MediaNet");
				masterTbl.setCocd(das.getDbTable().getMaster().getCoCd());
				masterTbl.setChennelCd(das.getDbTable().getMaster().getChennelCd());
			}

			String tapeClf = StringUtils.defaultIfEmpty(das.getDbTable().getMaster().getCtgrLCd(), "200");
			masterTbl.setCtgrLCd(tapeClf);

			masterTbl.setRsvPrdCd("000");
			masterTbl.setLockStatCd("N");
			masterTbl.setArchRoute("DP");
			masterTbl.setManualYn("Y");
			masterTbl.setCtgrMCd("");
			masterTbl.setCtgrSCd("");

			das.getDbTable().getMaster().setMasterId(masterId);

			// 인제스트 등록일
			masterTbl.setIngRegDd(Utility.getTimestamp("yyyyMMdd"));

			masterTbl.setAddClip(true);

			workflow.put("master", masterTbl);
		} catch (ApplicationException ae) {
			logger.error("Master 정보를 취합하는데 실패했습니다.", ae);
			return Boolean.toString(false);
		}

		try {
			addClipForTapeService.saveAddClipInfo(workflow);
		} catch (ApplicationException ae) {
			logger.error("메타정보를 등록하는 과정에서 에러가 발생했습니다.", ae);
			return Boolean.toString(false);
		}

		workflow.clear();
		workflow = null;

		return xmlStream.toXML(das);
	}

}