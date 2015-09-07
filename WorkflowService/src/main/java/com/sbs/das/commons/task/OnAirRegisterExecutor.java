package com.sbs.das.commons.task;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.sbs.das.commons.exception.ApplicationException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.system.DASConstants;
import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.commons.utils.XmlFileFilter;
import com.sbs.das.dto.AnnotInfoTbl;
import com.sbs.das.dto.CodeTbl;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.CornerTbl;
import com.sbs.das.dto.ErpTapeItemTbl;
import com.sbs.das.dto.ErpTapeTbl;
import com.sbs.das.dto.MasterTbl;
import com.sbs.das.dto.TimeRistSetTbl;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.services.AddClipForTapeService;
import com.sbs.das.services.ArchiveReqService;
import com.sbs.das.services.TapeErpService;

@Component("onAirRegisterExecutor")
public class OnAirRegisterExecutor {

	final Logger logger = LoggerFactory.getLogger(getClass());

	private ExecutorService thread = Executors.newSingleThreadExecutor();
	private final long THREAD_WAIT_TIME = 1000L * 10;

	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private AddClipForTapeService addClipForTapeService;
	@Autowired
	private TapeErpService tapeErpService;
	@Autowired
	private ArchiveReqService archiveReqService;
	@Autowired
	private MessageSource messageSource;

	@PostConstruct
	public void start() {
		thread.execute(new OnAirRegisterJob());
		if(logger.isInfoEnabled()) {
			//logger.info("OnAirRegisterJob Thread start !!");
		}
	}

	public class OnAirRegisterJob implements Runnable {
		public void run() {
			while(true) {/*
				//logger.debug("OnAirRegisterJob Thread running");
				File f = new File("/mp4/onAirTest");
				File[] xmls = f.listFiles(new XmlFileFilter());
				for(File xmlFile : xmls) {
					String xml = "";
					try {
						xml = FileUtils.readFileToString(xmlFile, "utf-8");
						logger.debug("addClipInfoService Call XML: "+xml);
					} catch (IOException e1) {
						logger.error("XML Not Found!!");
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException e) {}
						continue;
					}

					if(StringUtils.isBlank(xml)) {
						logger.error("Request XML is Blank!!");
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException e) {}
						continue;
					}

					Das das = null;
					try {
						das = (Das)xmlStream.fromXML(xml);
					} catch (Exception e) {
						logger.error("addClipInfoService xml parsing error!! - "+e.getMessage());
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException ee) {}
						continue;
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
							try {
								FileUtils.forceDelete(xmlFile);
							} catch (IOException e) {}
							continue;
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
											
											 * tape_item_id로 이미 등록이 되어 있는지 확인.
											 * 존재한다면 신규등록을 하지 않는다.
											 
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
										try {
											FileUtils.forceDelete(xmlFile);
										} catch (IOException e) {}
										continue;
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
							try {
								FileUtils.forceDelete(xmlFile);
							} catch (IOException e) {}
							continue;
						}
					}

					*//****************************************************************************************************//*
					*//**************************************** Master Set ************************************************//*
					*//****************************************************************************************************//*
					try {
						DecimalFormat df = new DecimalFormat("00,00,00");

						logger.debug("***************************************masterTbl : "+masterTbl);
						logger.debug("***************************************masterId  : "+masterId);

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
								logger.debug("get New Master_id: "+masterId);
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
									
									 * 2014.05.13
									 * 촬영일이 존재한다면 촬영일 셋팅
									 
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

								
								 * 2014.05.14
								 * 촬영일이 존재한다면 촬영일 셋팅
								 
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
								
								 * 2014.05.13
								 * OnAir 등록 영상중 특정 시간대에 등록이 되는 콘텐츠에 대하여
								 * 사용등급을 DB에서 조회하여 저장하도록 한다.
								 * OnAir Live 경우에는 사용등급이 없으므로 DAS에서 설정한 사용등급을 적용하고
								 * OnAir Archive는 기존 로직을 유지한다.
								 
								if(das.getInfo().getDasEqId() == 5) { // Live
									Calendar cal = Calendar.getInstance();
									
									 * XML의 brd_dd 방송일을 기준으로 요일을 정함.
									 
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
									
									
									// 일(1), 월(2), 화(3), 수(4), 목(5), 금(6), 토(7)
									int week = cal.get(Calendar.DAY_OF_WEEK);
									
									TimeRistSetTbl timeRistSetTbl = addClipForTapeService.getTimeRistSet(week, das.getDbTable().getMaster().getBrdBgnHms());
									if(timeRistSetTbl != null) {
										masterTbl.setRistClfCd(timeRistSetTbl.getRistClfCd());
										if(logger.isDebugEnabled()) {
											logger.debug("db rist_clf_cd: "+timeRistSetTbl.getRistClfCd());
										}
										
										// DAS Client에서 해당 시간에 입력된 pds_pgm_id를 입력해준다.
										// 2014-07_07
										masterTbl.setPdsCmsPgmId(timeRistSetTbl.getPdsPgmId());
										if(logger.isDebugEnabled()) {
											logger.debug("db rist_clf_cd: "+timeRistSetTbl.getRistClfCd());
										}
									} else {
										masterTbl.setRistClfCd("007");
									}
								} else {	// 주조
									if(das.getDbTable().getMaster().getOnAirMediaApprove().equals("002")||das.getDbTable().getMaster().getOnAirMediaApprove().equals("005")){
										masterTbl.setRistClfCd("006");
									} else {
										if(StringUtils.isBlank(das.getDbTable().getMaster().getOnAirMediaApprove()))
											masterTbl.setRistClfCd("007");
										else
											masterTbl.setRistClfCd(das.getDbTable().getMaster().getOnAirMediaApprove());
									}
								}

								if(logger.isDebugEnabled()) {
									logger.debug("rist_clf_cd: "+das.getDbTable().getMaster().getOnAirMediaApprove());
								}
								
								if(StringUtils.isNotBlank(das.getDbTable().getMaster().getOnAirMediaApprove())) { // OnAir 요청
									StringBuffer spcInfo = new StringBuffer();
									Integer approve = Integer.valueOf(das.getDbTable().getMaster().getOnAirMediaApprove());
									switch(approve) {
									case 2 :
										spcInfo.append("사용제한");
										break;
									case 3 : 
										spcInfo.append("사용금지");
										break;
									case 4 : 
										spcInfo.append("담당PD확인");
										break;
									case 5 : 
										spcInfo.append("사용제한");
										break;
									case 6 : 
										spcInfo.append("사용제한");
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


							if(StringUtils.isBlank(das.getDbTable().getMaster().getTapeMediaClfCd())) {
								masterTbl.setTapeMediaClfCd("999");
							}

							masterTbl.setPrdtInOutsCd("001");
							masterTbl.setCprtType("001");
							masterTbl.setRsvPrdCd("000");
							masterTbl.setLockStatCd("N");
							masterTbl.setManualYn("N");
							masterTbl.setRsvPrdEndDd("99991231");


							
							 * 아카이브경로(OL:온에어 생방, OA : 온에어 송출 , P: PDS , DP:  ERP TAPE 미존재 매체변환, DE :  ERP TAPE 존재  매체변환)
							 
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
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException e) {}
						continue;
					}

					*//****************************************************************************************************//*
					*//**************************************** Content Set ***********************************************//*
					*//****************************************************************************************************//*
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
								// 전타이틀, 본방송, 전체 영상이라면 카탈로그를 생성을 위한 CT_ID를 셋팅한다.
								if(contentTbl.getCtTyp().equals("001") || contentTbl.getCtTyp().equals("003") || contentTbl.getCtTyp().equals("006")) {
									// 대표이미지가 존재한다면
									masterTbl.setRpimgCtId(das.getDbTable().getContent().getCtId());
								}
							}
							logger.debug("111111111111111111111111");
							// 인제스트 등록일
							masterTbl.setIngRegDd(Utility.getTimestamp("yyyyMMdd"));

							// Master Object hash put
							workflow.put("master", masterTbl);
							logger.debug("3333333333333333333333333333");
							// 본방송 영상이고 MXF 영상이라면...
							if(contentTbl.getCtTyp().equals("003") && das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '1') {
								if(erpTapeItemTbl != null) {
									contentTbl.setCont(erpTapeItemTbl.getScnCont());
									contentTbl.setKeyWords(erpTapeItemTbl.getKeyWords());
									contentTbl.setCtOwnDeptCd(erpTapeItemTbl.getDeptCd());
								}
							}
							logger.debug("2222222222222222222222222");
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

								// 전타이틀, 본방송, 전체 영상이라면 카탈로그를 생성을 위한 CT_ID를 셋팅한다.
								if(contentTbl.getCtTyp().equals("001") || contentTbl.getCtTyp().equals("003") || contentTbl.getCtTyp().equals("006")) {
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
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException ie) {}
						continue;
					}


					*//****************************************************************************************************//*
					*//**************************************** Corner Set ************************************************//*
					*//****************************************************************************************************//*
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
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException e) {}
						continue;
					}


					*//****************************************************************************************************//*
					*//************************************ Content Instance Set ******************************************//*
					*//****************************************************************************************************//*
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
									try {
										FileUtils.forceDelete(xmlFile);
									} catch (IOException e) {}
									continue;
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
								contentInstTbl.setVdHresol(das.getDbTable().getContentInst().getVdHresol());
								contentInstTbl.setVdVresol(das.getDbTable().getContentInst().getVdVreSol());
								contentInstTbl.setBitRt(das.getDbTable().getContentInst().getBitRt());
								contentInstTbl.setFrmPerSec(das.getDbTable().getContentInst().getFrmPerSec());
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
								try {
									FileUtils.forceDelete(xmlFile);
								} catch (IOException e) {}
								continue;
							}

						}
					} catch (ApplicationException ae) {
						logger.error("Content Instance 정보를 취합하는데 실패했습니다", ae);
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException e) {}
						continue;
					}

					*//****************************************************************************************************//*
					*//************************************** Content Map Set *********************************************//*
					*//****************************************************************************************************//*
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
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException e) {}
						continue;
					}

					*//****************************************************************************************************//*
					*//************************************** AnnotInfo Set ***********************************************//*
					*//****************************************************************************************************//*
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

						if(erpTapeItemTbl != null) {
							annotInfoTbl.setAnnotClfCont(erpTapeItemTbl.getRstCont());
							// 매체변환 사용등급 무제한
							annotInfoTbl.setAnnotClfCd("007");
						}else{
							logger.debug("#############################rist_clf cd  "+masterTbl.getRistClfCd());
							annotInfoTbl.setAnnotClfCont("");
							// Master 사용등급 적용 2014.05.26
							annotInfoTbl.setAnnotClfCd(masterTbl.getRistClfCd());
						}
						annotInfoTbl.setGubun("L");

						annotInfoTbl.setAddClip(true);

						workflow.put("annotInfo", annotInfoTbl);
					}

					if(logger.isDebugEnabled()) {
						logger.debug("return xml: "+xmlStream.toXML(das));
					}

					*//****************************************************************************************************//*
					*//*********************************** DB All Insert or Update ****************************************//*
					*//****************************************************************************************************//*
					try {
						logger.debug("###########################################################################3");
						logger.debug("delete file path : "+xmlFile.getAbsolutePath());
						logger.debug("delete file naem : "+xmlFile.getName());
						addClipForTapeService.saveAddClipInfo(workflow);
					} catch (ApplicationException ae) {
						logger.error("메타정보를 등록하는 과정에서 에러가 발생했습니다.", ae);
						try {
							FileUtils.forceDelete(xmlFile);
						} catch (IOException e) {}
						continue;
					}
					workflow.clear();
					workflow = null;
					
					
					try {
						Thread.sleep(500L);
					} catch (Exception e) {}
					
					try {
					
						FileUtils.forceDelete(xmlFile);
					} catch (IOException e) {continue;}
					continue;
				}
				
				try {
					Thread.sleep(THREAD_WAIT_TIME);
				} catch (Exception e) {}
			*/}
		}
	}

	@PreDestroy
	public void stop() {
		if(!thread.isShutdown()) {
			thread.shutdownNow();
			if(logger.isInfoEnabled()) {
				logger.info("OnAirRegisterJob shutdown now!!");
			}
		}
	}
}
