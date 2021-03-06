package com.sbs.das.sample.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.ApplicationException;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.system.DASConstants;
import com.sbs.das.commons.system.DasCmsConnector;
import com.sbs.das.commons.system.DivaConnectSerivce;
import com.sbs.das.commons.system.MessageDeleteListener;
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
import com.sbs.das.dto.CopyInfoTbl;
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
import com.sbs.das.dto.xml.ArchiveStatus;
import com.sbs.das.dto.xml.Attach;
import com.sbs.das.dto.xml.DBTable;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.dto.xml.DeleteRequest;
import com.sbs.das.dto.xml.Master;
import com.sbs.das.dto.xml.Report;
import com.sbs.das.dto.xml.StorageInfo;
import com.sbs.das.repository.AnnotInfoDao;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.ContentInstMetaDao;
import com.sbs.das.repository.ContentLocDao;
import com.sbs.das.repository.DeleteContentDao;
import com.sbs.das.repository.MasterDao;
import com.sbs.das.repository.TapeDao;
import com.sbs.das.repository.TapeItemDao;
import com.sbs.das.sample.BaseConfig;
import com.sbs.das.services.AddClipForTapeService;
import com.sbs.das.services.ArchiveReqService;
import com.sbs.das.services.ArchiveStatusService;
import com.sbs.das.services.DasEquipService;
import com.sbs.das.services.DeleteContentAdapter;
import com.sbs.das.services.DeleteContentService;
import com.sbs.das.services.MerUnCompressService;
import com.sbs.das.services.NotReportedService;
import com.sbs.das.services.QuartzManagerService;
import com.sbs.das.services.ResourceCheckService;
import com.sbs.das.services.TapeErpService;

public class ArchiveRequestTest extends BaseConfig {

	@Autowired
	private DasEquipService dasEquipService;
	@Autowired
	private ArchiveReqService archiveReqService;
	@Autowired
	private ArchiveStatusService statusService;
	@Autowired
	private ContentInstMetaDao contentInstMetaDao;
	@Autowired
	private TapeDao tapeDao;
	@Autowired
	private TapeItemDao itemDao;
	@Autowired
	private AddClipForTapeService addClipService;
	@Autowired
	private ContentLocDao contentLocDao;
	@Autowired
	private ContentInstMetaDao contentInstDao;
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private DeleteContentService deleteContentService;
	@Autowired
	private TapeErpService erpService;
	@Autowired
	private AnnotInfoDao annotInfoDao;
	@Autowired
	private QuartzManagerService scheduleJobService;
	@Autowired
	private NotReportedService notReportedService;
	@Autowired
	private TapeErpService tapeErpService;
	@Autowired
	private DeleteContentDao deleteContentDao;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private DivaConnectSerivce divaConnector;
	@Autowired
	private MessageDeleteListener messageDeleteListener;
	@Autowired
	private ArchiveStatusService archiveStatusService;
	@Autowired
	private DasCmsConnector dasCmsConnector;
	@Autowired
	private ResourceCheckService resourceCheckService;
	@Autowired
	private AddClipForTapeService addClipForTapeService;
	@Autowired
	private DeleteContentAdapter deleteContentAdapter;
	@Autowired
	private MerUnCompressService merUnCompressService;

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Ignore
	@Test
	public void findEquip() {
		try {
			List<DasEquipTbl> equipList = dasEquipService.findDasEquip("D00", null);
			System.out.println("equipList size: "+equipList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getEquip() {
		try {
			DasEquipTbl equip = dasEquipService.getDasEquip(22, "001", new Boolean(true));
			System.out.println("equip UseIp: "+equip.getDasEqUseIp());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void findCartList() {
		try {
			List<DownCartTbl> cartTbls = archiveReqService.findDownCarts(673L, new Boolean(true));
			System.out.println("cartTbls size: "+cartTbls.size());

			List<CartContTbl> cartContTbls = archiveReqService.findCartConts(673L, 1, new Boolean(true));
			System.out.println("cartContTbls size: "+cartContTbls.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void insertContentDown() {
		try {
			ContentDownTbl contentDownTbl = new ContentDownTbl();
			contentDownTbl.setCartNo(7440L);
			contentDownTbl.setCartSeq(10);
			contentDownTbl.setCtiId(559338L);
			contentDownTbl.setObjName("DAS"+559338);
			contentDownTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
			contentDownTbl.setRegUser("DIVA");
			contentDownTbl.setStatus("0");
			contentDownTbl.setPath("/restore/7440/");
			contentDownTbl.setFilename("559338.mxf");
			System.out.println("num: "+archiveReqService.insertContentDown(contentDownTbl));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void updateContentLoc() {
		try {
			ContentLocTbl contentLocTbl = new ContentLocTbl();
			contentLocTbl.setJobStatus("E");
			contentLocTbl.setObjName("DAS1000247");
			contentLocTbl.setProgress("0");
			contentLocTbl.setUpdtUser("DIVA");
			contentLocTbl.setUpdtDtm("20114205120231");
			statusService.backupStatus(contentLocTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void updateContentDown() {
		try {
			ContentDownTbl contentDownTbl = new ContentDownTbl();
			contentDownTbl.setJobStatus("I");
			contentDownTbl.setProgress("30");
			contentDownTbl.setUpdtUser("DIVA");
			contentDownTbl.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
			contentDownTbl.setNum(1L);
			statusService.restoreStatus(contentDownTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getDownCart() {
		try {
			DownCartTbl downCartTbl = archiveReqService.getDownCart(7508L, new Boolean(true));
			System.out.println(downCartTbl.getStrgLoc());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getCartCont() {
		try {
			CartContTbl cartContTbl = archiveReqService.getCartCont(7504L, 2, new Boolean(true));
			System.out.println( cartContTbl.getCtiId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void updateContentInst() {
		try {
			ContentInstTbl contentInstTbl = new ContentInstTbl();
			String objname = "DAS711750";
			contentInstTbl.setCtiId(Long.parseLong(objname.substring(3)));
			System.out.println(contentInstTbl.getCtiId());
			contentInstTbl.setEtc("");
			contentInstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmm"));
			System.out.println(contentInstTbl.getModDt());
			contentInstTbl.setModrid("DIVA");

			contentInstMetaDao.updateContentInst(contentInstTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Ignore
	@Test
	public void getErpTape() {
		try {
			ErpTapeTbl erpTapeTbl = tapeDao.getTapeObj("100000");
			System.out.println("tape: "+erpTapeTbl.getTitle());

			ErpTapeItemTbl itemTbl = itemDao.getTapeItemObj("100000");
			System.out.println("tapeItem : "+itemTbl.getScnTtl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void findMasterItem() {
		try {
			MasterTbl masterTbl = archiveReqService.getMaster("167847", new Boolean(true));
			System.out.println("title : "+masterTbl.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void getCodeObj() {
		try {
			CodeTbl codeTbl = addClipService.getCodeObj(DASConstants.CLF_CD_UID_PREFIX, "001", new Boolean(true));
			System.out.println("clf_nm : "+codeTbl.getClfNm());

			NotReportMsgTbl notReportMsgTbl = new NotReportMsgTbl();
			notReportMsgTbl.setUid(codeTbl.getDesc()+"005");
			notReportMsgTbl.setDasEqClfCd(DASConstants.JOB_FILEINGEST);
			notReportMsgTbl.setRetryNo(0);
			notReportMsgTbl.setHost("192.168.0.1");
			notReportMsgTbl.setPort("12999");
			notReportMsgTbl.setMsg("TEST");
			notReportMsgTbl.setCheckstamp("NO");
			notReportMsgTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));

			addClipService.insertNotReportedMsg(notReportMsgTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void updateEquip() {
		try {
			DasEquipTbl dasEquipTbl = dasEquipService.getDasEquip(22, "001", new Boolean(true));

			dasEquipTbl.setModrid("kms");
			dasEquipTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			dasEquipTbl.setFlPath("/arcreq/12345/12");

			dasEquipService.updateDasEquip(dasEquipTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void insertContentLoc() {
		try {
			ContentLocTbl contentLocTbl = new ContentLocTbl();
			contentLocTbl.setJobStatus("W");
			contentLocTbl.setAstatus("0");
			contentLocTbl.setRegUser("kms");
			contentLocTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
			contentLocTbl.setProgress("0");
			contentLocTbl.setScount(1);
			contentLocTbl.setUseYn("Y");
			contentLocTbl.setCtiId(20000001L);
			contentLocTbl.setObjName("DAS20000000001");
			contentLocTbl.setPath("/mp2");
			contentLocTbl.setFilename("DASsdfsdfsdf.mxf");
			contentLocTbl.setCyn("N");


			contentLocDao.insertContentLoc(contentLocTbl);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getContentLoc() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ctiId", 1000024);
			params.put("wait", new Boolean(true).toString());

			ContentLocTbl contentLocTbl = contentLocDao.getContentLoc(params);
			System.out.println(contentLocTbl.getFilename());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void updateMaster() {
		try {
			/*
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("archRegDd", Utility.getTimestamp("yyyyMMddHHmmss"));
			params.put("modDt", Utility.getTimestamp("yyyyMMddHHmmss"));
			params.put("ctiId", 1000024);

			masterDao.updateArchiveComplete(params);
			 */
			MasterTbl masterTbl = new MasterTbl();
			masterTbl.setMasterId(74806L);
			masterTbl.setArchRegDd(Utility.getTimestamp("yyyyMMddHHmmss"));
			masterTbl.setModrid("kms");
			masterTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			masterTbl.setDelDd("");

			masterDao.updateMaster(masterTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void insertMaster() {
		try {

			MasterTbl masterTbl = new MasterTbl();
			masterTbl.setMasterId(118384L);
			masterTbl.setArchRegDd(Utility.getTimestamp("yyyyMMddHHmmss"));
			masterTbl.setModrid("kms");
			masterTbl.setPgmCd("001");
			masterTbl.setEpisNo(1);
			masterTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));

			masterDao.insertMaster(masterTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getCornerCount() {
		try {
			Das das = new Das();
			DBTable dbTable = new DBTable();
			Master master = new Master();
			master.setMasterId(1198L);
			dbTable.setMaster(master);
			das.setDbTable(dbTable);

			System.out.println(addClipService.getCornerCount(das, new Boolean(true)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void insertContent() {
		try {
			long ctId = 282211;
			ContentTbl contentTbl = new ContentTbl();
			contentTbl.setCtId(ctId);
			contentTbl.setCtNm("컨텐츠명을 넣어봅니다");
			contentDao.insertContent(contentTbl);

			/*
			ContentInstTbl contentInstTbl = new ContentInstTbl();
			contentInstTbl.setCtId(282210);
			contentInstTbl.setCtiId(1000257);

			contentInstMetaDao.insertContentInst(contentInstTbl);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void updateTapeOutStatus() {
		try {
			ArchiveStatus archiveStatus = new ArchiveStatus();
			archiveStatus.setCartNo(673L);
			archiveStatus.setCartSeq(1);
			archiveStatus.setJobStatus("C");

			erpService.updateTapeOutService(archiveStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 
	@Transactional
	@Test
	@Ignore
	public void insertAnnotInfo() {
		try {
			AnnotInfoTbl annotInfoTbl = new AnnotInfoTbl();
			annotInfoTbl.setRegrid("aaa");
			annotInfoTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			annotInfoTbl.setCnId(123456L);
			annotInfoTbl.setCtId(123456L);
			annotInfoTbl.setMasterId(123456L);
			annotInfoTbl.setSom("00:00:00");
			annotInfoTbl.setEom("00:00:00");
			annotInfoTbl.setDuration(123456L);
			annotInfoTbl.setAnnotClfCd("009");
			annotInfoTbl.setGubun("L");
			annotInfoTbl.setEntireYn("Y");
			annotInfoDao.insertAnnotInfo(annotInfoTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getMediaId() {
		try {
			Map<String, String> param = new HashMap<String, String>();
			System.out.println("media_id: "+contentDao.getMediaId(param));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Transactional
	@Test
	public void deleteContent() {
		try {
			long ctId = 282561;
			deleteContentService.updateDeleteCtMap(ctId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void quartzManager() {
		try {
			List<String> tableNames = scheduleJobService.findQuartzTables();
			scheduleJobService.createQuartzTables(tableNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getCopyInfo() {
		try {
			CopyInfoTbl copyInfoTbl = archiveReqService.getCopyInfoObj("P00003");
			System.out.println("copyInfoTbl copy_yn : "+copyInfoTbl.getCopyYn());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@Ignore
	@Test
	public void insertNotReported() {
		try {


			Report report = new Report();
			report.setRegrid("1004");
			report.setDasEqPsCd("123");

			NotReportMsgTbl notReportMsgTbl = new NotReportMsgTbl();
			notReportMsgTbl.setUid(report.getRegrid());
			notReportMsgTbl.setDasEqClfCd(report.getDasEqPsCd());
			notReportMsgTbl.setRetryNo(10);
			notReportMsgTbl.setHost("120");
			notReportMsgTbl.setCheckstamp("2011");
			notReportMsgTbl.setMsg("hello");
			notReportMsgTbl.setPort("110");
			notReportMsgTbl.setRegDt("2001");

			notReportedService.insertNotReported(notReportMsgTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	 
	//@Transactional
    @Ignore
	@Test
	public void addClip() {
		//mxf 영상 메타 삽입
		//String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><das>  <info>    <das_eq_id>5</das_eq_id>    <das_eq_ps_cd>001</das_eq_ps_cd>  </info>  <db_table>    <pgm_info_tbl>      <pgm_id>0</pgm_id>      <media_cd />      <pgm_nm>%eb%93%9c%eb%9d%bc%eb%a7%88%ec%8a%a4%ed%8e%98%ec%85%9c(%ed%94%bc%eb%85%b8%ed%82%a4%ec%98%a4)(%ec%83%9d%eb%b0%a9VCR)(11%ec%9b%9412%ec%9d%bc(%ec%88%98))(11%ec%9b%9412%ec%9d%bc)</pgm_nm>      <chan_cd />      <pgm_cd />      <reg_dt>20141112110226</reg_dt>      <regrid>user</regrid>    </pgm_info_tbl>    <metadat_mst_tbl>      <title>%eb%93%9c%eb%9d%bc%eb%a7%88%ec%8a%a4%ed%8e%98%ec%85%9c(%ed%94%bc%eb%85%b8%ed%82%a4%ec%98%a4)(%ec%83%9d%eb%b0%a9VCR)(11%ec%9b%9412%ec%9d%bc(%ec%88%98))(11%ec%9b%9412%ec%9d%bc)</title>     <master_id>0</master_id>      <brd_dd>20141112</brd_dd>      <brd_bgn_hms>215505</brd_bgn_hms>      <brd_end_hms>230218</brd_end_hms>      <brd_leng>01%3a07%3a11%3a20</brd_leng>      <mcuid>3fa2ce69-99d0-45cb-a36f-a038ae78ea89</mcuid>      <reg_dt>20141112110226</reg_dt>      <regrid>user</regrid>      <rpimg_kfrm_seq>41724</rpimg_kfrm_seq>    </metadat_mst_tbl>    <contents_tbl>      <reg_dt>20141112110226</reg_dt>      <regrid>user</regrid>      <mcu_seq>0</mcu_seq>      <ct_nm>      </ct_nm>      <ct_cla>006</ct_cla>      <ct_id>0</ct_id>      <vd_qlty>001</vd_qlty>      <asp_rto_cd>001</asp_rto_cd>      <ct_leng>01%3a07%3a11%3a20</ct_leng>      <duration>120828</duration>      <error_cont>      </error_cont>    </contents_tbl>    <contents_inst_tbl>      <cti_id>0</cti_id>      <cti_fmt>105</cti_fmt>      <bit_rt>50000000</bit_rt>      <frm_per_sec>29.97</frm_per_sec>      <drp_frm_yn>Y</drp_frm_yn>      <vd_hresol>1920</vd_hresol>      <vd_vresol>1080</vd_vresol>      <color_cd />      <record_type_cd>      </record_type_cd>      <audio_yn>Y</audio_yn>      <audio_lan_cd />      <audio_samp_frq>48000</audio_samp_frq>      <audio_bdwt>1536000</audio_bdwt>      <noi_rduc_typ_cd />      <file_path>X%3a%5cSBS%5cfileingest</file_path>      <org_file_nm>%eb%93%9c%eb%9d%bc%eb%a7%88%ec%8a%a4%ed%8e%98%ec%85%9c(%ed%94%bc%eb%85%b8%ed%82%a4%ec%98%a4)(%ec%83%9d%eb%b0%a9VCR)(11%ec%9b%9412%ec%9d%bc(%ec%88%98))(11%ec%9b%9412%ec%9d%bc)_2014-11-12-234421.MXF</org_file_nm>      <fl_sz>30326082096</fl_sz>      <reg_dt>20141112110226</reg_dt>      <regrid>user</regrid>      <ct_id>0</ct_id>    </contents_inst_tbl>    <contents_mapp_tbl>      <ct_id>0</ct_id>      <master_id>      </master_id>      <pgm_id>      </pgm_id>      <s_duration>0</s_duration>      <e_duration>120828</e_duration>      <cn_id>0</cn_id>    </contents_mapp_tbl>  </db_table></das>";
		String xml =" <das>  <info>    <das_eq_id>5</das_eq_id>    <das_eq_ps_cd>001</das_eq_ps_cd>    <cti_id>0</cti_id>    <master_id>0</master_id>    <cart_no>0</cart_no>  </info>  <db_table>    <pgm_info_tbl>      <pgm_id>0</pgm_id>      <media_cd></media_cd>      <pgm_nm>%EB%93%9C%EB%9D%BC%EB%A7%88%EC%8A%A4%ED%8E%98%EC%85%9C%28%ED%94%BC%EB%85%B8%ED%82%A4%EC%98%A4%29%28%EC%83%9D%EB%B0%A9VCR%29%2811%EC%9B%9412%EC%9D%BC%28%EC%88%98%29%29%2811%EC%9B%9412%EC%9D%BC%29</pgm_nm>      <chan_cd></chan_cd>      <reg_dt>20141112110226</reg_dt>      <pgm_cd></pgm_cd>      <regrid>user</regrid>    </pgm_info_tbl>    <metadat_mst_tbl>      <master_id>1000100</master_id>      <title>%EB%93%9C%EB%9D%BC%EB%A7%88%EC%8A%A4%ED%8E%98%EC%85%9C%28%ED%94%BC%EB%85%B8%ED%82%A4%EC%98%A4%29%28%EC%83%9D%EB%B0%A9VCR%29%2811%EC%9B%9412%EC%9D%BC%28%EC%88%98%29%29%2811%EC%9B%9412%EC%9D%BC%29</title>      <brd_dd>20141112</brd_dd>      <brd_bgn_hms>215505</brd_bgn_hms>      <brd_end_hms>230218</brd_end_hms>      <brd_leng>01%3A07%3A11%3A20</brd_leng>      <reg_dt>20141112110226</reg_dt>      <regrid>user</regrid>      <mcuid>3fa2ce69-99d0-45cb-a36f-a038ae78ea89</mcuid>      <rpimg_kfrm_seq>41724</rpimg_kfrm_seq>    </metadat_mst_tbl>    <contents_tbl>      <ct_id>550124</ct_id>      <ct_nm></ct_nm>      <ct_cla>006</ct_cla>      <reg_dt>20141112110226</reg_dt>      <mcu_seq>0</mcu_seq>      <regrid>user</regrid>      <duration>120828</duration>      <ct_leng>01%3A07%3A11%3A20</ct_leng>      <vd_qlty>001</vd_qlty>      <asp_rto_cd>001</asp_rto_cd>      <error_cont></error_cont>    </contents_tbl>    <contents_inst_tbl>      <cti_id>0</cti_id>      <ct_id>550124</ct_id>      <cti_fmt>301</cti_fmt>      <drp_frm_yn>Y</drp_frm_yn>      <vd_hresol>1920</vd_hresol>      <vd_vresol>1080</vd_vresol>      <audio_yn>Y</audio_yn>      <file_path>%2Fmp2%2FSBS%2FonAir%2F201411%2F14</file_path>      <org_file_nm>%EB%93%9C%EB%9D%BC%EB%A7%88%EC%8A%A4%ED%8E%98%EC%85%9C%28%ED%94%BC%EB%85%B8%ED%82%A4%EC%98%A4%29%28%EC%83%9D%EB%B0%A9VCR%29%2811%EC%9B%9412%EC%9D%BC%28%EC%88%98%29%29%2811%EC%9B%9412%EC%9D%BC%29_2014-11-12-234421.MXF</org_file_nm>      <fl_sz>30326082096</fl_sz>      <reg_dt>20141112110226</reg_dt>      <regrid>user</regrid>      <bit_rt>50000000</bit_rt>      <color_cd></color_cd>      <record_type_cd></record_type_cd>      <audio_lan_cd></audio_lan_cd>      <audio_samp_frq>48000</audio_samp_frq>      <audio_bdwt>1536000</audio_bdwt>      <noi_rduc_typ_cd></noi_rduc_typ_cd>      <frm_per_sec>29.97</frm_per_sec>    </contents_inst_tbl>    <contents_mapp_tbl>      <ct_id>550124</ct_id>      <master_id>1000100</master_id>      <pgm_id>0</pgm_id>      <cn_id>2000284</cn_id>      <ct_seq>1</ct_seq>      <cn_seq>1</cn_seq>      <s_duration>0</s_duration>      <e_duration>120828</e_duration>    </contents_mapp_tbl>  </db_table></das>";
		if(logger.isDebugEnabled()){
			logger.debug("addClipInfoService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			//return Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("addClipInfoService xml parsing error!! - "+e.getMessage());
			//return Boolean.toString(false);
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
						}
						
						AttachTbl attachTbl = new AttachTbl();
						attachTbl.setMothrDataId(attach.getMothrDataId());
						attachTbl.setAttcFileTypeCd(attach.getAttcFileTypeCd()); // 010:한글, 011:영문, 012:중문, 013:일어
						attachTbl.setFlPath(flPath);
						attachTbl.setFlSz(f.length());
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
				//	throw new RemoteException("attach add error", e);
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
								//return Boolean.toString(false);
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
					//return Boolean.toString(false);
				}
			}

			logger.debug("***************************************: ");
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
					if(StringUtils.isNotBlank(das.getDbTable().getMaster().getOnAirMediaApprove())){

						masterTbl.setRistClfCd(das.getDbTable().getMaster().getOnAirMediaApprove());

					}else if(StringUtils.isNotBlank(das.getDbTable().getMaster().getRistClfCd())){

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
				//return Boolean.toString(false);
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

						// 전타이틀, 본방송, 전체 영상이라면 카탈로그를 생성을 위한 CT_ID를 셋팅한다.
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
				//return Boolean.toString(false);
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
				//return Boolean.toString(false);
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
							//return Boolean.toString(false);
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
						//return Boolean.toString(false);
					}

				}
			} catch (ApplicationException ae) {
				logger.error("Content Instance 정보를 취합하는데 실패했습니다", ae.getStackTrace());
				//return Boolean.toString(false);
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
				//return Boolean.toString(false);
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
			logger.debug("//return xml: "+xmlStream.toXML(das));
		}

		/****************************************************************************************************/
		/*********************************** DB All Insert or Update ****************************************/
		/****************************************************************************************************/
		try {
			addClipForTapeService.saveAddClipInfo(workflow);
		} catch (Exception ae) {
			logger.error("메타정보를 등록하는 과정에서 에러가 발생했습니다.", ae);
			//return Boolean.toString(false);
		}
		workflow.clear();
		workflow = null;

		//return xmlStream.toXML(das);
	}





	@Transactional
	@Ignore
	@Test
	public void downtest() {

		String xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?><das><info><cart_no>34662</cart_no><cart_seq>1</cart_seq><regrid>S522522</regrid><req_id></req_id></info></das>";
		if(logger.isDebugEnabled()){
			logger.debug("downloadService Call XML: "+xml);
		}
		System.out.println("downloadService Call XML: "+xml);
		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("downloadService xml parsing error!! - "+e.getMessage());
			//// Boolean.toString(false);
		}

		Long cartNo = das.getInfo().getCartNo();

		BufferedWriter bw = null;
		try {
			List<DownCartTbl> cartTbls = archiveReqService.findDownCarts(cartNo, new Boolean(true));
			if(cartTbls.isEmpty()) {
				logger.error("findDownCarts is size '0'!! - ");
				//	// Boolean.toString(false);
			} else {

				DownCartTbl cartTbl = (DownCartTbl)cartTbls.get(0);

				/*
				 * DownLoad 파일명 생성 규칙
				 * 파일 Path : /MP2/RESTORE/USER_ID/CART_NO/
				 * 파일 Name :
				 * 001: PDS, 002: NDS, 005: 계열사 => DownCartTbl.downsubj + "_"+ CartContTbl.mediaId+".MXF"
				 * 변경됨: CartContTbl.mediaId+".MXF"
				 * 003: 데정팀, 004: tape-out => CartContTbl.cartSeq + CartContTbl.ctiId+".MXF"
				 */
				String downGubun = StringUtils.isBlank(cartTbl.getDownGubun()) ? "003" : cartTbl.getDownGubun();

				boolean useSeq = true;
				if(downGubun.equals("001") || downGubun.equals("002") || downGubun.equals("005")) {
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
				if(StringUtils.isBlank(das.getInfo().getCartSeq())) {
					logger.error("cart_seq is blank !!");
					//	// Boolean.toString(false);
				} else {
					cartSeqs = das.getInfo().getCartSeq().split("\\,");

					ArchiveRequest request = null;
					for(String cartSeq : cartSeqs) {
						if(logger.isDebugEnabled()) {
							logger.debug("String[] cartSeq: "+cartSeq);
						}
						if(StringUtils.isNotBlank(cartSeq)) {
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
								ContentDownTbl contentDownTbl = new ContentDownTbl();
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
										System.out.println("요청한 컨텐츠 정보가 Contents_loc_tbl에 존재하지 않습니다. : "+"DAS"+cartCont.getCtiId());

										throw new Exception("Contents_loc_tbl not exists for obj_name : "+cartCont.getCtiId());
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
									System.out.println("요청한 컨텐츠 정보가 Contents_loc_tbl에 존재하지 않습니다. : "+"DAS"+cartCont.getCtiId());
									//System.out.println("downloadService Call XML: "+xml);
									System.out.println("get ContentLoc Error!! - "+e.getMessage());
									//// Boolean.toString(false);
								}

								/* Diva Connector에 전달할 XML Values */
								request.setCategory(DASConstants.DEFAULT_ARCH_CATEGORY);
								//request.setObjectName(DASConstants.DAS_ARCHIVE_PRIFIX+cartCont.getCtiId());
								request.setPriority(DASConstants.DEFAULT_RESTORE_PRIORITY);
								request.setDestination(DASConstants.DEFAULT_ARCH_DESTINATION);
								request.setQos(DASConstants.DEFAULT_NON_ARCH_QOS);
								request.setGroup(DASConstants.DEFAULT_GROUP_ARCH);
								request.setFilename(orgFileName);

								/* Restore Default Type FULL */
								String downTyp = StringUtils.defaultIfEmpty(cartCont.getDownTyp(), "F");
								if(logger.isDebugEnabled()) {
									logger.debug("base value: "+cartCont.getDownTyp()+", replace value: "+downTyp);
								}

								/* 복원요청을 저장한다. */
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
									ae.printStackTrace();
									logger.error("ContentDownTbl restore insert Error - "+ae.getMessage());
									//	// Boolean.toString(false);
								}

								String retMsg = "";
								try {
									if(downTyp.equals("P")) {
										if(logger.isDebugEnabled()) {
											logger.debug("DIVA restore xml: "+xmlStream.toXML(request));
											System.out.println("DIVA restore xml: "+xmlStream.toXML(request));
										}
										retMsg = divaConnector.restorePFR(xmlStream.toXML(request));
									} else {
										if(logger.isDebugEnabled()) {
											logger.debug("DIVA restorePFR xml: "+xmlStream.toXML(request));
											System.out.println("DIVA restorePFR xml: "+xmlStream.toXML(request));
										}
										retMsg = divaConnector.restore(xmlStream.toXML(request));
									}
									if(logger.isDebugEnabled()) {
										logger.debug("DIVA restore // Msg: "+retMsg);
										System.out.println("DIVA restore // Msg: "+retMsg);
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
			//	// Boolean.toString(false);
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

		//	// Boolean.toString(true);

	}



	@Transactional
	@Ignore
	@Test
	public void callProceduder() {
		/*try {

			long cti_id =705041;
			System.out.println("11222");
			contentInstDao.callProceduer(cti_id);

			System.out.println("update complete");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		DeleteRequest request  = new DeleteRequest();
		request.setHighCtiId(705041L);
		request.setHighFlNm("mp22.xmf");
		request.setHighPath("/mp2/2011");
		if(logger.isInfoEnabled()) {
			logger.info("-----------------------------archive----------------------------------");
		}
		if(logger.isInfoEnabled()) {
			logger.info("high_cti_id    : "+request.getHighCtiId());
			logger.info("high_path      : "+request.getHighPath());
			logger.info("high_fl_nm     : "+request.getHighFlNm());
			System.out.println("high_cti_id    : "+request.getHighCtiId());
			System.out.println("high_path      : "+request.getHighPath());
			System.out.println("high_fl_nm     : "+request.getHighFlNm());
		}


		try {
			// 고용량 영상이 존재한다면 삭제하고 path를 blank 처리한다.
			if(StringUtils.isNotBlank(request.getHighPath()) && StringUtils.isNotBlank(request.getHighFlNm())) {
				String path = request.getHighPath();
				if(path.indexOf("\\") > -1) path = path.replaceAll("\\\\", "/");
				if(!path.startsWith("/")) path = "/"+path;
				if(!path.endsWith("/")) path = path+"/";

				File f = new File(path, request.getHighFlNm());
				Utility.fileForceDelete(f);
			}

			deleteContentService.updateContentPathBlank(request.getHighCtiId());
			//Long cti_id = request.getHighCtiId();
			contentInstDao.callProceduer(request.getHighCtiId());
			System.out.println("complete");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}



	@Ignore
	@Transactional
	@Test
	public void downDelte() throws DaoNonRollbackException {
		try {
			String limitDay ="20120305";

			List<DeleteRequest> storageExpirList = deleteContentService.findDownloadDeleteList("S", limitDay);
			if(logger.isDebugEnabled()) {
				logger.debug("archive size       : "+storageExpirList.size());
			}
			if(!storageExpirList.isEmpty()) {
				for(DeleteRequest deleteRequest : storageExpirList) {
					if(logger.isDebugEnabled()) {
						logger.debug("cart_no     : "+deleteRequest.getCartNo());
						logger.debug("cart_seq    : "+deleteRequest.getCartSeq());
						logger.debug("high_cti_id : "+deleteRequest.getHighCtiId());
						System.out.println("cart_no     : "+deleteRequest.getCartNo());
						System.out.println("cart_seq    : "+deleteRequest.getCartSeq());
						System.out.println("high_cti_id : "+deleteRequest.getHighCtiId());
					}

					if(StringUtils.isNotBlank(deleteRequest.getHighPath())) {
						deleteRequest.setReqDd(limitDay);
						deleteRequest.setReqDt(Utility.getTimestamp("yyyyMMddHHmmssSSSSS"));
						//messageDeleteListener.mput(deleteRequest);
						System.out.println("delete scheduler is complete!!!!!!!!!");
					}
				}
			}
		} catch (ApplicationException e) {
			logger.error("downloadExpiredDelete error - ", e);
			e.printStackTrace();
		}
	}



	@Transactional
	@Ignore
	@Test
	public void archiveStatus() throws DaoNonRollbackException {

		String xml ="<das><status><filesize>11390760784</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841090</object_name><updt_dtm>20141031153419</updt_dtm><progress>100</progress><restore_id>466651</restore_id><eq_id>10</eq_id></status><status><filesize>241525344</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841086</object_name><updt_dtm>20141031153420</updt_dtm><progress>100</progress><restore_id>466652</restore_id><eq_id>10</eq_id></status><status><filesize>11671858036</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841084</object_name><updt_dtm>20141031153423</updt_dtm><progress>100</progress><restore_id>466653</restore_id><eq_id>10</eq_id></status><status><filesize>8993691088</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841082</object_name><updt_dtm>20141031153424</updt_dtm><progress>100</progress><restore_id>466654</restore_id><eq_id>10</eq_id></status><status><filesize>246296160</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841142</object_name><updt_dtm>20141031153426</updt_dtm><progress>100</progress><restore_id>466655</restore_id><eq_id>10</eq_id></status><status><filesize>10644960472</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841094</object_name><updt_dtm>20141031153426</updt_dtm><progress>100</progress><restore_id>466656</restore_id><eq_id>10</eq_id></status><status><filesize>123518024</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841092</object_name><updt_dtm>20141031153427</updt_dtm><progress>100</progress><restore_id>466657</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841088</object_name><updt_dtm>20141031153428</updt_dtm><progress>2</progress><restore_id>466658</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841080</object_name><updt_dtm>20141031153429</updt_dtm><progress>1</progress><restore_id>466659</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841072</object_name><updt_dtm>20141031153430</updt_dtm><progress>0</progress><restore_id>466660</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841058</object_name><updt_dtm>20141031153431</updt_dtm><progress>0</progress><restore_id>466661</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841056</object_name><updt_dtm>20141031153432</updt_dtm><progress>0</progress><restore_id>466662</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841144</object_name><updt_dtm>20141031153433</updt_dtm><progress>0</progress><restore_id>466663</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841078</object_name><updt_dtm>20141031153434</updt_dtm><progress>0</progress><restore_id>466664</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841074</object_name><updt_dtm>20141031153436</updt_dtm><progress>0</progress><restore_id>466665</restore_id><eq_id>10</eq_id></status><status><process_cd>CA</process_cd><job_id>005</job_id><job_status>I</job_status><object_name>DAS928249</object_name><updt_dtm>20141031153437</updt_dtm><progress>0</progress><restore_id></restore_id><eq_id>10</eq_id></status><status><process_cd>MA</process_cd><job_id>005</job_id><job_status>C</job_status><object_name>DAS928265</object_name><updt_dtm>20141031153439</updt_dtm><progress>100</progress><restore_id></restore_id><eq_id>10</eq_id></status></das>";
		if(logger.isDebugEnabled()){
			logger.debug("archiveStates Call XML: "+xml);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {

			logger.error("getResArchieve xml parsing error!! - "+e.getMessage());
			//// Boolean.toString(false);
		}

		List<ArchiveStatus> status = das.getStatus();
		if(!status.isEmpty()) {
			for(ArchiveStatus archiveStatus : status) {
				try {
					if(archiveStatus.getJobStatus().equals("E")) {
						if(logger.isDebugEnabled()) {
							logger.debug("error_id : "+archiveStatus.getErrorId());
							logger.debug("error_msg : "+archiveStatus.getErrorMsg());
						}
					} else {
						if(logger.isDebugEnabled()) {
							logger.debug("job_id : "+archiveStatus.getJobId());
							logger.debug("job_status : "+archiveStatus.getJobStatus());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
							logger.debug("progress : "+archiveStatus.getProgress());
						}
					}

					if(archiveStatus.getJobId().equals("005")) { // archive
						if(logger.isDebugEnabled()) {
							logger.debug("archive : "+archiveStatus.getJobId());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}
						ContentLocTbl contentLocTbl = new ContentLocTbl();
						ContentInstTbl contentsInstTbl = new ContentInstTbl();
						contentLocTbl.setObjName(archiveStatus.getObjectName());
						contentLocTbl.setUpdtUser("DIVA");

						if("MA".equals(archiveStatus.getProcessCd())) { // 아카이브
							contentLocTbl.setJobStatus(archiveStatus.getJobStatus());
							contentLocTbl.setProgress(String.valueOf(archiveStatus.getProgress()));
						} else if("MT".equals(archiveStatus.getProcessCd())) { // MXF -> H264 변환
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
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
								contentLocTbl.setCyn("N");
								contentLocTbl.setCopyProgress("100");
								contentLocTbl.setCopyStatus("C");
								contentLocTbl.setAstatus("250");

								contentsInstTbl.setEtc("");
								contentsInstTbl.setCtiId(contentLocTbl.getCtiId());
								contentsInstTbl.setModDt(archiveStatus.getUpdtDtm());

								archiveStatusService.updateContentInst(contentsInstTbl);
								archiveStatusService.updateStatus(contentLocTbl);
							} else if("BA".equals(archiveStatus.getProcessCd())) { // h264 소산 아카이브
								if(logger.isDebugEnabled()) {
									logger.debug("backup completed - obj_name: "+ archiveStatus.getObjectName());
								}
								contentLocTbl.setBackupProgress("100");
								contentLocTbl.setBackupStatus("C");
							}
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

								archiveStatusService.updateCompleteMaster(contentLocTbl);
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
					} else if(archiveStatus.getJobId().equals("006")) { // copy
						if(logger.isDebugEnabled()) {
							logger.debug("job_id   : "+archiveStatus.getJobId());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}

						ContentLocTbl contentLocTbl = new ContentLocTbl();
						ContentInstTbl contentsInstTbl = new ContentInstTbl();
						if("CD".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setDownProgress(archiveStatus.getJobStatus());
							contentLocTbl.setDownStatus(String.valueOf(archiveStatus.getProgress()));
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
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

						// 복원 요청이 완료이거나 에러일경우 CartCont에 상태값을 변경한다.
						if(archiveStatus.getJobStatus().trim().equals("C") || (archiveStatus.getJobStatus() !=null && archiveStatus.getJobStatus().equals("E"))) {
							contentDownTbl = archiveStatusService.getConentDown(archiveStatus.getRestoreId(), Boolean.valueOf("false"));
							archiveStatusService.updateStatusCartCont(contentDownTbl);
							if(logger.isDebugEnabled()) {
								logger.debug("restore cart_cont update - cart_no:"+ contentDownTbl.getCartNo()+", cart_seq: "+contentDownTbl.getCartSeq());
							}
						}

						// 복원 상태가 완료라면 DAS CMS 서비스를 호출하여 정보를 전달한다. 넘겨주는 정보는 Restore_id
						if(archiveStatus.getJobStatus().trim().equals("C") && archiveStatus.getProgress() == 100) {
							dasCmsConnector.completeDown(archiveStatus.getRestoreId());
							if(logger.isDebugEnabled()) {
								logger.debug("Das CMS Called : "+archiveStatus.getRestoreId());
							}
						}

					} else if(archiveStatus.getJobId().equals("008")) { // delete
						if(logger.isDebugEnabled()) {
							logger.debug("delete : "+archiveStatus.getJobId());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}
						/* 삭제요청이 완료라면... */
						if(archiveStatus.getJobStatus().trim().equals("C") && archiveStatus.getProgress() == 100) {
							if(logger.isInfoEnabled()) {
								logger.info("DTL Delete Success!! - "+archiveStatus.getObjectName());
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
							contentLocTbl.setDownProgress(archiveStatus.getJobStatus());
							contentLocTbl.setDownStatus(String.valueOf(archiveStatus.getProgress()));
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							logger.debug("obj_name :3333333333333333 ");
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
							contentLocTbl.setEqId(archiveStatus.getEqId());
						} else if("CA".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setCopyProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setCopyStatus(archiveStatus.getJobStatus());
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
							}
						}

						if("OD".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.backupStatus(contentLocTbl);
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						} else if("CA".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						}

					} else if(archiveStatus.getJobId().equals("011")) { // manual backup

						ContentLocTbl contentLocTbl = new ContentLocTbl();
						contentLocTbl.setObjName(archiveStatus.getObjectName());

						if("CD".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setDownProgress(archiveStatus.getJobStatus());
							contentLocTbl.setDownStatus(String.valueOf(archiveStatus.getProgress()));
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

			}
		}
		//	// new Boolean(true).toString();
	}





	@Transactional
	 @Ignore
	@Test
	public void archiveStatus2() {

		String xml ="<das><status><filesize>9943473772</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841088</object_name><updt_dtm>20141031153710</updt_dtm><progress>100</progress><restore_id>466658</restore_id><eq_id>10</eq_id></status><status><filesize>11620022132</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841080</object_name><updt_dtm>20141031153710</updt_dtm><progress>100</progress><restore_id>466659</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841072</object_name><updt_dtm>20141031153712</updt_dtm><progress>70</progress><restore_id>466660</restore_id><eq_id>10</eq_id></status><status><filesize>12164785608</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841058</object_name><updt_dtm>20141031153714</updt_dtm><progress>100</progress><restore_id>466661</restore_id><eq_id>10</eq_id></status><status><filesize>11581202792</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841056</object_name><updt_dtm>20141031153716</updt_dtm><progress>100</progress><restore_id>466662</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841144</object_name><updt_dtm>20141031153717</updt_dtm><progress>0</progress><restore_id>466663</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841078</object_name><updt_dtm>20141031153720</updt_dtm><progress>0</progress><restore_id>466664</restore_id><eq_id>10</eq_id></status><status><filesize>243340384</filesize><process_cd></process_cd><job_id>007</job_id><job_status>C</job_status><object_name>DAS841074</object_name><updt_dtm>20141031153721</updt_dtm><progress>100</progress><restore_id>466665</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841070</object_name><updt_dtm>20141031153722</updt_dtm><progress>0</progress><restore_id>466666</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841068</object_name><updt_dtm>20141031153723</updt_dtm><progress>0</progress><restore_id>466667</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841064</object_name><updt_dtm>20141031153724</updt_dtm><progress>0</progress><restore_id>466668</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841062</object_name><updt_dtm>20141031153725</updt_dtm><progress>0</progress><restore_id>466669</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841060</object_name><updt_dtm>20141031153727</updt_dtm><progress>0</progress><restore_id>466670</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841050</object_name><updt_dtm>20141031153728</updt_dtm><progress>0</progress><restore_id>466671</restore_id><eq_id>10</eq_id></status><status><process_cd></process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS841048</object_name><updt_dtm>20141031153730</updt_dtm><progress>0</progress><restore_id>466672</restore_id><eq_id>10</eq_id></status><status><process_cd>PD</process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS780649</object_name><updt_dtm>20141031153732</updt_dtm><progress>0</progress><restore_id>466708</restore_id><eq_id>10</eq_id></status><status><process_cd>PD</process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS783384</object_name><updt_dtm>20141031153733</updt_dtm><progress>0</progress><restore_id>466709</restore_id><eq_id>10</eq_id></status><status><process_cd>PD</process_cd><job_id>007</job_id><job_status>I</job_status><object_name>DAS782806</object_name><updt_dtm>20141031153735</updt_dtm><progress>0</progress><restore_id>466710</restore_id><eq_id>10</eq_id></status><status><process_cd>CA</process_cd><job_id>005</job_id><job_status>C</job_status><object_name>DAS928249</object_name><updt_dtm>20141031153745</updt_dtm><progress>100</progress><restore_id></restore_id><eq_id>10</eq_id></status></das>";
		if(logger.isDebugEnabled()){
			logger.debug("archiveStates Call XML: "+xml);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {

			logger.error("getResArchieve xml parsing error!! - "+e.getMessage());
			//// Boolean.toString(false);
		}

		List<ArchiveStatus> status = das.getStatus();
		if(!status.isEmpty()) {
			for(ArchiveStatus archiveStatus : status) {
				try {
					if(archiveStatus.getJobStatus().equals("E")) {
						if(logger.isDebugEnabled()) {
							logger.debug("error_id : "+archiveStatus.getErrorId());
							logger.debug("error_msg : "+archiveStatus.getErrorMsg());
						}
					} else {
						if(logger.isDebugEnabled()) {
							logger.debug("job_id : "+archiveStatus.getJobId());
							logger.debug("job_status : "+archiveStatus.getJobStatus());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
							logger.debug("progress : "+archiveStatus.getProgress());
						}
					}

					if(archiveStatus.getJobId().equals("005")) { // archive
						if(logger.isDebugEnabled()) {
							logger.debug("archive : "+archiveStatus.getJobId());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}
						ContentLocTbl contentLocTbl = new ContentLocTbl();
						ContentInstTbl contentsInstTbl = new ContentInstTbl();
						contentLocTbl.setObjName(archiveStatus.getObjectName());
						contentLocTbl.setUpdtUser("DIVA");

						if("MA".equals(archiveStatus.getProcessCd())) { // 아카이브
							contentLocTbl.setJobStatus(archiveStatus.getJobStatus());
							contentLocTbl.setProgress(String.valueOf(archiveStatus.getProgress()));
						} else if("MT".equals(archiveStatus.getProcessCd())) { // MXF -> H264 변환
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
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
								contentLocTbl.setCyn("N");
								contentLocTbl.setCopyProgress("100");
								contentLocTbl.setCopyStatus("C");
								contentLocTbl.setAstatus("250");

								contentsInstTbl.setEtc("");
								contentsInstTbl.setCtiId(contentLocTbl.getCtiId());
								contentsInstTbl.setModDt(archiveStatus.getUpdtDtm());

								archiveStatusService.updateContentInst(contentsInstTbl);
								archiveStatusService.updateStatus(contentLocTbl);
							} else if("BA".equals(archiveStatus.getProcessCd())) { // h264 소산 아카이브
								if(logger.isDebugEnabled()) {
									logger.debug("backup completed - obj_name: "+ archiveStatus.getObjectName());
								}
								contentLocTbl.setBackupProgress("100");
								contentLocTbl.setBackupStatus("C");
							}
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

								archiveStatusService.updateCompleteMaster(contentLocTbl);
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
					} else if(archiveStatus.getJobId().equals("006")) { // copy
						if(logger.isDebugEnabled()) {
							logger.debug("job_id   : "+archiveStatus.getJobId());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}

						ContentLocTbl contentLocTbl = new ContentLocTbl();
						ContentInstTbl contentsInstTbl = new ContentInstTbl();
						if("CD".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setDownProgress(archiveStatus.getJobStatus());
							contentLocTbl.setDownStatus(String.valueOf(archiveStatus.getProgress()));
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
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

						// 복원 요청이 완료이거나 에러일경우 CartCont에 상태값을 변경한다.
						if(archiveStatus.getJobStatus().trim().equals("C") || (archiveStatus.getJobStatus() !=null && archiveStatus.getJobStatus().equals("E"))) {
							contentDownTbl = archiveStatusService.getConentDown(archiveStatus.getRestoreId(), Boolean.valueOf("false"));
							archiveStatusService.updateStatusCartCont(contentDownTbl);
							if(logger.isDebugEnabled()) {
								logger.debug("restore cart_cont update - cart_no:"+ contentDownTbl.getCartNo()+", cart_seq: "+contentDownTbl.getCartSeq());
							}
						}

						// 복원 상태가 완료라면 DAS CMS 서비스를 호출하여 정보를 전달한다. 넘겨주는 정보는 Restore_id
						if(archiveStatus.getJobStatus().trim().equals("C") && archiveStatus.getProgress() == 100) {
							dasCmsConnector.completeDown(archiveStatus.getRestoreId());
							if(logger.isDebugEnabled()) {
								logger.debug("Das CMS Called : "+archiveStatus.getRestoreId());
							}
						}

					} else if(archiveStatus.getJobId().equals("008")) { // delete
						if(logger.isDebugEnabled()) {
							logger.debug("delete : "+archiveStatus.getJobId());
							logger.debug("obj_name : "+archiveStatus.getObjectName());
						}
						/* 삭제요청이 완료라면... */
						if(archiveStatus.getJobStatus().trim().equals("C") && archiveStatus.getProgress() == 100) {
							if(logger.isInfoEnabled()) {
								logger.info("DTL Delete Success!! - "+archiveStatus.getObjectName());
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
							contentLocTbl.setDownProgress(archiveStatus.getJobStatus());
							contentLocTbl.setDownStatus(String.valueOf(archiveStatus.getProgress()));
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							logger.debug("obj_name :3333333333333333 ");
							contentLocTbl.setChangeProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setChangeStatus(archiveStatus.getJobStatus());
							contentLocTbl.setEqId(archiveStatus.getEqId());
						} else if("CA".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setCopyProgress(String.valueOf(archiveStatus.getProgress()));
							contentLocTbl.setCopyStatus(archiveStatus.getJobStatus());
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
							}
						}

						if("OD".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.backupStatus(contentLocTbl);
						} else if("HT".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						} else if("CA".equals(archiveStatus.getProcessCd())) {
							archiveStatusService.updateStatus(contentLocTbl);
						}

					} else if(archiveStatus.getJobId().equals("011")) { // manual backup

						ContentLocTbl contentLocTbl = new ContentLocTbl();
						contentLocTbl.setObjName(archiveStatus.getObjectName());

						if("CD".equals(archiveStatus.getProcessCd())) {
							contentLocTbl.setDownProgress(archiveStatus.getJobStatus());
							contentLocTbl.setDownStatus(String.valueOf(archiveStatus.getProgress()));
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

			}
		}
		//	// new Boolean(true).toString();
	}

	@Transactional
	@Ignore
	@Test
	public void testSubAddClip() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("reqCd", "S522522");
			params.put("episNo", 1);
			params.put("sceanNo", 1);
			MediaTapeInfoTbl mediaTapeInfoTbl = addClipForTapeService.getMediaTapeInfo(params);
			System.out.println(mediaTapeInfoTbl.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Ignore
	@Transactional
	@Test
	public void testSubAddClip2() {
		String xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?><das><info><das_eq_id>10</das_eq_id><das_eq_clf_cd>A00</das_eq_clf_cd><das_eq_ps_cd>C:</das_eq_ps_cd><regrid>D080017</regrid></info><db_table><pgm_info_tbl><pgm_id>0</pgm_id><media_cd /><pgm_nm>2008+PGA</pgm_nm><reg_dt>20121012121710</reg_dt><regrid>D080017</regrid></pgm_info_tbl><metadat_mst_tbl><master_id /><brd_leng>00%3a00%3a10%3a00</brd_leng><arch_route>DE</arch_route><rist_clf_cd>004</rist_clf_cd><cocd>M</cocd><chennel_cd>G</chennel_cd><req_cd>MGP000075</req_cd><epis_no>0</epis_no><brd_dd>20080511</brd_dd><spc_info /></metadat_mst_tbl><contents_tbl><ct_id>0</ct_id><ct_cla>006</ct_cla><ct_nm>%eb%b3%b8%eb%b0%a9%ec%86%a1</ct_nm><ct_seq>0</ct_seq><ct_leng>00%3a00%3a10%3a00</ct_leng><duration>300</duration><vd_qlty>001</vd_qlty><asp_rto_cd>001</asp_rto_cd></contents_tbl><contents_inst_tbl><cti_id>0</cti_id><audio_yn>Y</audio_yn><audio_type>001</audio_type><audio_samp_frq>48000</audio_samp_frq><audio_bdwt>1536000</audio_bdwt><cti_fmt>102</cti_fmt><drp_frm_yn>Y</drp_frm_yn><record_type_cd>002</record_type_cd><arch_ste_yn>N</arch_ste_yn><bit_rt>50000000</bit_rt><frm_per_sec>29.97</frm_per_sec><vd_hresol>1920</vd_hresol><vd_vresol>1080</vd_vresol><ingest_eq_id>10</ingest_eq_id><org_file_nm /><file_path /><fl_sz>74736700</fl_sz></contents_inst_tbl><contents_mapp_tbl><cn_id>0</cn_id><ct_id>0</ct_id><master_id /><pgm_id /><cn_seq>0</cn_seq></contents_mapp_tbl></db_table></das>";

		if(logger.isDebugEnabled()){
			logger.debug("subAddClipService Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			//// Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("subAddClipService xml parsing error!! - "+e.getMessage());
			//// Boolean.toString(false);
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
							masterId=masterTbl.getMasterId();
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
					//// Boolean.toString(false);
				}
			}
		} else {
			logger.error("Tape 정보가 ERP에 없습니다.");
			//// Boolean.toString(false);
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
					if(mediaTapeInfoTbl.getCtgrLCd().equals("100")) 	   // 촬영일
						masterTbl.setFmDt(das.getDbTable().getMaster().getBrdDd());
					else													
						masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());   // 방송일

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
				}
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
			//// Boolean.toString(false);
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
					masterTbl.setRpimgCtId(contentTbl.getCtId());
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
			//// Boolean.toString(false);
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

				cornerTbl.setRpimgKfrmSeq(0);
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
			ae.printStackTrace();
			logger.error("Corner 정보를 취합하는데 에러가 발생했습니다.", ae.getStackTrace());
			//// Boolean.toString(false);
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
						//// Boolean.toString(false);
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
					contentInstTbl.setVdHresol(das.getDbTable().getContentInst().getVdHresol());
					contentInstTbl.setVdVresol(das.getDbTable().getContentInst().getVdVreSol());
					contentInstTbl.setBitRt(das.getDbTable().getContentInst().getBitRt());
					contentInstTbl.setFrmPerSec(das.getDbTable().getContentInst().getFrmPerSec());
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
					//// Boolean.toString(false);
				}
			}
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Content Instance 정보를 취합하는데 실패했습니다", ae.getStackTrace());
			//// Boolean.toString(false);
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
				contentMapTbl.setAddClip(true);

				contentMapTbl.setModrid(regrid);
				contentMapTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			}
			workflow.put("contentMap", contentMapTbl);
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Conents Map 정보를 취합하는데 에러가 발생했습니다.", ae.getStackTrace());
			//// Boolean.toString(false);
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

			annotInfoTbl.setGubun("L");
			annotInfoTbl.setAddClip(true);
			workflow.put("annotInfo", annotInfoTbl);
		}

		if(logger.isDebugEnabled()) {
			logger.debug("// xml: "+xmlStream.toXML(das));
		}

		/****************************************************************************************************/
		/*********************************** DB All Insert or Update ****************************************/
		/****************************************************************************************************/
		try {
			addClipForTapeService.saveAddClipInfo(workflow);
		} catch (ApplicationException ae) {
			logger.error("메타정보를 등록하는 과정에서 에러가 발생했습니다.", ae);
			//// Boolean.toString(false);
		}

		workflow.clear();
		workflow = null;

		//// xmlStream.toXML(das);
	}




	@Ignore
	//@Transactional
	@Test
	public void testNonErp() {
		//logger.debug("i`m still running");
		//File f = new File("/mp4/onAirTest");
		//File[] xmls = f.listFiles(new XmlFileFilter());
		//for(File xmlFile : xmls) {
		String xml = " <?xml version=\"1.0\" encoding=\"utf-8\"?><das>  <info>    <das_eq_id>5</das_eq_id>    <das_eq_ps_cd>001</das_eq_ps_cd>  </info>  <db_table>    <pgm_info_tbl>      <pgm_id>0</pgm_id>      <media_cd />      <pgm_nm>%eb%82%98%ec%9d%b4%ed%8a%b8%eb%9d%bc%ec%9d%b8(%ec%9e%90)(05%ec%9b%9427%ec%9d%bc)</pgm_nm>      <chan_cd />      <pgm_cd />      <reg_dt>20140527011416</reg_dt>      <regrid>user</regrid>    </pgm_info_tbl>    <metadat_mst_tbl>     <title>%eb%82%98%ec%9d%b4%ed%8a%b8%eb%9d%bc%ec%9d%b8(%ec%9e%90)(05%ec%9b%9427%ec%9d%bc)</title>      <master_id>0</master_id>      <brd_dd>20140527</brd_dd>      <brd_bgn_hms>125046</brd_bgn_hms>      <brd_end_hms>011407</brd_end_hms>      <brd_leng>00%3a23%3a19%3a19</brd_leng>      <mcuid>b6beb033-cba4-4e10-8ef4-78ff77f91354</mcuid>      <reg_dt>20140527011416</reg_dt>      <regrid>user</regrid>      <rpimg_kfrm_seq>28006</rpimg_kfrm_seq>    </metadat_mst_tbl>    <contents_tbl>      <reg_dt>20140527011416</reg_dt>      <regrid>user</regrid>      <mcu_seq>0</mcu_seq>      <ct_nm>      </ct_nm>      <ct_cla>006</ct_cla>      <ct_id>0</ct_id>      <vd_qlty>001</vd_qlty>      <asp_rto_cd>001</asp_rto_cd>      <ct_leng>00%3a23%3a19%3a19</ct_leng>      <duration>41947</duration>      <error_cont>      </error_cont>    </contents_tbl>    <contents_inst_tbl>      <cti_id>0</cti_id>      <cti_fmt>105</cti_fmt>      <bit_rt>50000000</bit_rt>      <frm_per_sec>29.97</frm_per_sec>      <drp_frm_yn>Y</drp_frm_yn>      <vd_hresol>1920</vd_hresol>      <vd_vresol>1080</vd_vresol>      <color_cd />      <record_type_cd>      </record_type_cd>      <audio_yn>Y</audio_yn>      <audio_lan_cd />      <audio_samp_frq>48000</audio_samp_frq>      <audio_bdwt>1536000</audio_bdwt>      <noi_rduc_typ_cd />      <file_path>X%3a%5cSBS%5cfileingest</file_path>      <org_file_nm>%eb%82%98%ec%9d%b4%ed%8a%b8%eb%9d%bc%ec%9d%b8(%ec%9e%90)(05%ec%9b%9427%ec%9d%bc)_2014-05-27-020514.MXF</org_file_nm>      <fl_sz>10529984120</fl_sz>      <reg_dt>20140527011416</reg_dt>      <regrid>user</regrid>      <ct_id>0</ct_id>    </contents_inst_tbl>    <contents_mapp_tbl>      <ct_id>0</ct_id>      <master_id>      </master_id>      <pgm_id>      </pgm_id>      <s_duration>0</s_duration>      <e_duration>41947</e_duration>      <cn_id>0</cn_id>    </contents_mapp_tbl>  </db_table></das>";
		/*try {
				xml = FileUtils.readFileToString(xmlFile, "utf-8");
				logger.debug("addClipInfoService Call XML: "+xml);
			} catch (IOException e1) {
				logger.error("XML Not Found!!");
				try {
					FileUtils.forceDelete(xmlFile);
				} catch (IOException e) {}
				continue;
			}*/

		/*if(StringUtils.isBlank(xml)) {
				logger.error("Request XML is Blank!!");
				try {
					FileUtils.forceDelete(xmlFile);
				} catch (IOException e) {}
				continue;
			}*/

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("addClipInfoService xml parsing error!! - "+e.getMessage());
			/*try {
					FileUtils.forceDelete(xmlFile);
				} catch (IOException ee) {}*/
			//continue;
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


								System.out.println("=====================================================  " +erpTapeItemTbl.getTapeClf());
								if(StringUtils.isNotBlank(erpTapeItemTbl.getTapeClf())) {
									das.getDbTable().getMaster().setCtgrLCd(erpTapeItemTbl.getTapeClf());

								} else {
									das.getDbTable().getMaster().setCtgrLCd("200");
								}
								System.out.println("=====================================================  " +das.getDbTable().getMaster().getCtgrLCd());

							}
							//logger.debug("das.getDbTable().getMaster().getTapeMediaClfCd()  "+ das.getDbTable().getMaster().getTapeMediaClfCd());

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
							//		// Boolean.toString(false);
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
				//	// Boolean.toString(false);
			}
		}

		logger.debug("***************************************: ");
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

				masterTbl.setAddClip(false);

				workflow.put("master", masterTbl);
			} else if(masterTbl == null && masterId > 0) {
				das.getDbTable().getMaster().setMasterId(masterId);

				masterTbl = new MasterTbl();

				masterTbl.setMasterId(masterId);
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
				workflow.put("master", masterTbl);
			} else { // Master DB Insert

				Long newMasterId = 0L;
				if (masterTbl == null) masterTbl = new MasterTbl();

				newMasterId = addClipForTapeService.getMasterNewId();
				if(logger.isDebugEnabled()){
					logger.debug("get New Master_id: "+masterId);
				}
				System.out.println("");
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

				//	String tapeClf = das.getDbTable().getMaster().getTapeMediaClfCd();
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
					masterTbl.setBrdDd(erpTapeItemTbl.getBrdDd());
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
					masterTbl.setBrdDd(das.getDbTable().getMaster().getBrdDd());
					masterTbl.setPdsCmsPgmId(das.getDbTable().getMaster().getPdsCmsPgmId());
					masterTbl.setDrtNm(das.getDbTable().getMaster().getDrtNm());
					masterTbl.setViewGrCd(das.getDbTable().getMaster().getViewGrCd());
					masterTbl.setCprtrNm("SBS");
				}

				masterTbl.setRpimgKfrmSeq(0);
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
					 * OnAir 등록 영상중 특정 시간대에 등록이 되는 콘텐츠에 대하여
					 * 사용등급을 DB에서 조회하여 저장하도록 한다.
					 * OnAir Live 경우에는 사용등급이 없으므로 DAS에서 설정한 사용등급을 적용하고
					 * OnAir Archive는 기존 로직을 유지한다.
					 */
					if(das.getInfo().getDasEqId() == 22) { // OnAir
						Calendar cal = Calendar.getInstance();
						// 일(1), 월(2), 화(3), 수(4), 목(5), 금(6), 토(7)
						int week = cal.get(Calendar.DAY_OF_WEEK);

						TimeRistSetTbl timeRistSetTbl = addClipForTapeService.getTimeRistSet(week, das.getDbTable().getMaster().getBrdBgnHms());
						if(timeRistSetTbl != null) {
							masterTbl.setRistClfCd(timeRistSetTbl.getRistClfCd());
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
			ae.printStackTrace();
			logger.error("Master 정보를 취합하는데 실패했습니다.", ae.getStackTrace());
			//// Boolean.toString(false);
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

				// 전타이틀, 본방송, 전체 영상이라면 카탈로그를 생성을 위한 CT_ID를 셋팅한다.
				if(contentTbl.getCtTyp().equals("001") || contentTbl.getCtTyp().equals("003") || contentTbl.getCtTyp().equals("006")) {
					masterTbl.setRpimgCtId(contentTbl.getCtId());
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
						(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '2' || das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '3')) {

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

				contentTbl.setAddClip(false);
			}

			workflow.put("content", contentTbl);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Contents 정보를 취합하는데 에러가 발생했습니다.", e.getStackTrace());
			//	// Boolean.toString(false);
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
					}

				}

				if(StringUtils.isBlank(cornerTbl.getCnInfo())) {
					if(erpTapeItemTbl != null) {
						cornerTbl.setCnInfo(StringUtils.defaultString(erpTapeItemTbl.getScnCont(), ""));
					}
				}

				cornerTbl.setRpimgKfrmSeq(0);
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
			ae.printStackTrace();
			logger.error("Corner 정보를 취합하는데 에러가 발생했습니다.", ae.getStackTrace());
			//	// Boolean.toString(false);
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
							filePath = m2Prefix+"/SBS/manual/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");
						} else {
							filePath = m2Prefix+"/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd");
						}
					} else if(das.getDbTable().getContentInst().getCtiFmt().charAt(0) == '2') { // Mpeg4
						filePath = m4Path + das.getDbTable().getContentInst().getCtId();
					} else {
						logger.error("해당 영상의 format이 비정의된 정보입니다. - "+das.getDbTable().getContentInst().getCtiFmt());
						//	// Boolean.toString(false);
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
					//// Boolean.toString(false);
				}

			}
		} catch (ApplicationException ae) {
			ae.printStackTrace();
			logger.error("Content Instance 정보를 취합하는데 실패했습니다", ae.getStackTrace());
			//// Boolean.toString(false);
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
			//// Boolean.toString(false);
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
			logger.debug("dddddd"+masterTbl.getRistClfCd());
			if(erpTapeItemTbl != null) {
				annotInfoTbl.setAnnotClfCont(erpTapeItemTbl.getRstCont());
				// 매체변환 사용등급 무제한
				annotInfoTbl.setAnnotClfCd("007");
			}else{
				annotInfoTbl.setAnnotClfCont("");
				// Master 사용등급 적용 2014.05.26
				logger.debug("##########################masterTbl.getRistClfCd()   "+masterTbl.getRistClfCd());
				annotInfoTbl.setAnnotClfCd(masterTbl.getRistClfCd());
			}
			annotInfoTbl.setGubun("L");

			annotInfoTbl.setAddClip(true);

			workflow.put("annotInfo", annotInfoTbl);
		}

		if(logger.isDebugEnabled()) {
			logger.debug("// xml: "+xmlStream.toXML(das));
		}

		/****************************************************************************************************/
		/*********************************** DB All Insert or Update ****************************************/
		/****************************************************************************************************/
		try {
			addClipForTapeService.saveAddClipInfo(workflow);
		} catch (ApplicationException ae) {
			logger.error("메타정보를 등록하는 과정에서 에러가 발생했습니다.", ae.getStackTrace());
			//// Boolean.toString(false);
		}
		workflow.clear();
		workflow = null;


		/*	try {
				Thread.sleep(500L);
			} catch (Exception e) {}

			try {
				FileUtils.forceDelete(xmlFile);
			} catch (IOException e) {}*/
		//}

		/*try {
			Thread.sleep(THREAD_WAIT_TIME);
		} catch (Exception e) {}*/
	}


	@Transactional
	@Ignore
	@Test
	public void insertErrorLog() {
		try {
			ErrorLogTbl errorLogTbl = new ErrorLogTbl();
			errorLogTbl.setServerNm("1234");
			errorLogTbl.setErrorType("001");
			errorLogTbl.setRegDt("201207");
			errorLogTbl.setErrorCont("test");
			archiveReqService.insertErrorLog(errorLogTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	@Transactional
	@Ignore
	@Test
	public void checkStorage() {
		String xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?><das><info><disk_path /></info></das>";
		if(logger.isDebugEnabled()){
			logger.debug("findDiskQuota Call XML: "+xml);
		}
		if(StringUtils.isBlank(xml)) {
			logger.error("Request XML is Blank!!");
			//// Boolean.toString(false);
		}

		Das das = null;
		try {
			das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findDiskQuota xml parsing error!! - "+e.getMessage());
			//// Boolean.toString(false);
		}
		List<StorageInfoTbl> infoTbls = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("wait", "true");
			if(das.getInfo() != null && StringUtils.isNotBlank(das.getInfo().getDiskPath())) {
				params.put("path", das.getInfo().getDiskPath());
			}

			String m2Prefix = messageSource.getMessage("net.mpeg2.drive", null, Locale.KOREA);
			logger.debug("m2Prefix              "+m2Prefix);
			infoTbls = resourceCheckService.findDiskQuota(params);
			StorageInfo storageInfo = new StorageInfo();
			storageInfo.setPath(m2Prefix);
			storageInfo.setFolderNm("고용량 스토리지");
			storageInfo.setTotalVolume((Long)Utility.QUOTA_CHECK.get(m2Prefix));
			das.addStorages(storageInfo);

			for(StorageInfoTbl storageInfoTbl : infoTbls) {

				if(Utility.QUOTA_CHECK.containsKey(storageInfoTbl.getPath())) {

					storageInfo = new StorageInfo();
					logger.debug("11111111"+33333);
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
			//// Boolean.toString(false);
		}

		System.out.println(xmlStream.toXML(das));
		//// xmlStream.toXML(das);
	}

	@Transactional
	@Ignore
	@Test
	public void insertMerHistory() {
		try {
			MerHistTbl merHistTbl = new MerHistTbl();
			merHistTbl.setCtId(421319L);
			merHistTbl.setKfrmPath("/mp4/201210/23/421319/KFRM/");
			merHistTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
			merHistTbl.setStatus("W");

			merUnCompressService.insertMerJob(merHistTbl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	@Transactional
	public void insertBatchDown() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ctiFmt", "201");
			params.put("vdVresol", 480);
			params.put("vdHresol", 270);
			List<ContentInstTbl> contentInstTbls = contentInstMetaDao.findContentInst(params);

			ContentDownTbl contentDownTbl = null;
			for(ContentInstTbl contentInstTbl : contentInstTbls) {
				contentDownTbl = new ContentDownTbl();
				contentDownTbl.setTcCtiId(contentInstTbl.getCtiId());
				contentDownTbl.setStatus("N");
				contentDownTbl.setJobStatus("N");
				contentDownTbl.setRegUser("workflow");
				contentDownTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
				contentDownTbl.setProgress("0");
				contentDownTbl.setUseYn("Y");
				contentDownTbl.setTcStatus("N");
				contentDownTbl.setTcPath(contentInstTbl.getFlPath());

				params.put("ctId", contentInstTbl.getCtId());
				params.put("ctiFmt", "1%");
				ContentInstTbl contentInstTbl2 = contentInstMetaDao.getContentInst(params);
				contentDownTbl.setCtiId(contentInstTbl2.getCtiId());
				contentDownTbl.setObjName("DAS"+contentInstTbl2.getCtiId());

				archiveStatusService.insertBatchDown(contentDownTbl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	@Transactional
	public void insertBatchDown2() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String[] fmts = {"103", "104"};
			for(String fmt : fmts) {
				params.put("ctiFmt", fmt);
				params.put("startDt", "20080220");
				params.put("endDt", "20080424");
				List<ContentInstTbl> contentInstTbls = contentInstMetaDao.findContentInst(params);

				ContentDownTbl contentDownTbl = null;
				for(ContentInstTbl contentInstTbl : contentInstTbls) {
					contentDownTbl = new ContentDownTbl();
					contentDownTbl.setTcCtiId(contentInstTbl.getCtiId());
					contentDownTbl.setStatus("N");
					contentDownTbl.setJobStatus("N");
					contentDownTbl.setRegUser("workflow");
					contentDownTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
					contentDownTbl.setProgress("0");
					contentDownTbl.setUseYn("Y");
					contentDownTbl.setTcStatus("N");
					contentDownTbl.setTcPath(contentInstTbl.getFlPath());
					contentDownTbl.setTcGb("H");
					contentDownTbl.setDtlYn("N");

					contentDownTbl.setCtiId(contentInstTbl.getCtiId());
					contentDownTbl.setObjName("DAS"+contentInstTbl.getCtiId());

					archiveStatusService.insertBatchDown(contentDownTbl);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	@Transactional
	public void updateRpimgCtId() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ctId", 435154);

			//String[] regDts = {"200812"};
			String[] regDts = {
					"201301","201212","201211","201210","201209","201208","201207","201206","201205","201204","201203","201202","201201",
					"201112","201111","201110","201109","201108","201107","201106","201105","201104","201103","201102","201101",
					"201012","201011","201010","201009","201008","201007","201006","201005","201004","201003","201002","201001",
					"200912","200911","200910","200909","200908","200907","200906","200905","200904","200903","200902","200901",
					"200812","200811","200810","200809","200808","200807","200806","200805","200804","200803","200802","200801"
			};

			for(String regDt : regDts) {
				List<MasterTbl> masterIds = resourceCheckService.findMetaData(regDt);

				System.out.println(masterIds.size());

				for(MasterTbl masterTbl : masterIds) {
					Long ctId = resourceCheckService.getCtMeta(masterTbl.getMasterId());
					System.out.println("masterId: "+masterTbl.getMasterId()+", ctId: "+ctId);

					try {
						resourceCheckService.updateMetaData(masterTbl.getMasterId(), ctId);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void updateStatCd() {
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> params2 = new HashMap<String, Object>();
			String[] regDts = {
					"201212","201211","201210","201209","201208","201207","201206","201205","201204","201203","201202","201201",
					"201112","201111","201110","201109","201108","201107","201106","201105","201104","201103","201102","201101",
					"201012","201011","201010","201009","201008","201007","201006","201005","201004","201003","201002","201001",
					"200912","200911","200910","200909","200908","200907","200906","200905","200904","200903","200902","200901",
					"200812","200811","200810","200809","200808","200807","200806","200805","200804","200803","200802","200801"
			};

			int days = 31;
			for(String regDt : regDts) {
				if(regDt.equals("201301")) days = 6;
				for(int i=1; i<=days; i++) {
					String day = regDt+Utility.padLeft(String.valueOf(i), "0", 2);
					//System.out.println(day);
					params.put("startDt", day+"000000");
					params.put("endDt", day+"235959");

					List<CartContTbl> cartContTbls = archiveReqService.findCartConts(params);
					for(CartContTbl cartContTbl : cartContTbls) {
						params2.put("cartNo", cartContTbl.getCartNo());
						params2.put("cartSeq", cartContTbl.getCartSeq());
						/*CartContTbl cartContTbl2 = cartContDao2.getCartCont(params2);
						if(cartContTbl2 != null) {
							//System.out.println(cartContTbl.getDownStat()+", old:"+cartContTbl2.getDownStat());
							cartContTbl.setDownStat(cartContTbl2.getDownStat());
							archiveReqService.updateCartCont(cartContTbl2);
						}*/
					}
					Thread.sleep(100);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Ignore
	@Test
	public void timeRistTest() {
		try {
			String hhmmss = Utility.getDate("HHmmss");
			System.out.println(hhmmss);
			
			Calendar cal = Calendar.getInstance();
			// 일(1), 월(2), 화(3), 수(4), 목(5), 금(6), 토(7)
			int week = cal.get(Calendar.DAY_OF_WEEK);
			
			TimeRistSetTbl timeRistSetTbl = addClipForTapeService.getTimeRistSet(week, hhmmss);
			if(timeRistSetTbl != null) {
				System.out.println("rist_clf_cd: "+timeRistSetTbl.getRistClfCd());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void attachTest(){
		
		try {
			Map<String, Object> workflow = new HashMap<String, Object>();
			AttachTbl attachTbl = new AttachTbl();
			attachTbl.setMothrDataId(1000060);
			//attachTbl.setSeq(4);
			attachTbl.setAttcFileTypeCd("010"); // 010:한글, 011:영문, 012:중문, 013:일어
			attachTbl.setFlPath("/mp4/201410/16/550057/Caption");
			attachTbl.setFlSz(85);
			attachTbl.setOrgFileNm("LIVETEST15(10월16일).smi");
			attachTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			attachTbl.setRegrid("workflow");
			attachTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			attachTbl.setModrid("workflow");
			attachTbl.setAttcClfCd("11");
			attachTbl.setFlNm("");
			workflow.put("attach", attachTbl);
			addClipService.saveAddClipInfo(workflow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	
	@Ignore
	@Test
	public void readTest(){
		
		try {
			String path = "c:\\ctidList.txt";
			FileReader fr = new FileReader(path);
			BufferedReader bf = new BufferedReader(fr);
			String ctIdList;
			
			while((ctIdList = bf.readLine()) != null){
				long ctId = Long.parseLong(ctIdList.trim());
				

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ctiFmt", "3%");
				params.put("ctId", ctId);
			 
				List<ContentInstTbl> contentInstTbls = contentInstMetaDao.findContentInst(params);

				ContentDownTbl contentDownTbl = null;
				for(ContentInstTbl contentInstTbl : contentInstTbls) {
				 
					contentDownTbl = new ContentDownTbl();
					contentDownTbl.setTcCtiId(contentInstTbl.getCtiId());
					contentDownTbl.setStatus("N");
					contentDownTbl.setJobStatus("N");
					contentDownTbl.setRegUser("work");
					contentDownTbl.setRegDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
					contentDownTbl.setProgress("0");
					contentDownTbl.setUseYn("Y");
					contentDownTbl.setTcStatus("N");
					contentDownTbl.setTcPath(contentInstTbl.getFlPath());
					contentDownTbl.setTcGb("S");
					contentDownTbl.setDtlYn("N");
					params.put("ctId", contentInstTbl.getCtId());
					params.put("ctiFmt", "1%");
					ContentInstTbl contentInstTbl2 = contentInstMetaDao.getContentInst(params);
					contentDownTbl.setCtiId(contentInstTbl2.getCtiId());
					contentDownTbl.setObjName(contentInstTbl2.getOutSystemId());

					archiveStatusService.insertBatchDown(contentDownTbl);
				 }
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void insertRistInfo(){
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<AnnotInfoTbl> annotInfos =  annotInfoDao.findRistList();
			long tempMasterId =0;
			for(AnnotInfoTbl annotInfoTbl : annotInfos){
			  
				params.put("masterId", annotInfoTbl.getMasterId());
				params.put("ctId", annotInfoTbl.getCtId());
				params.put("cnId", annotInfoTbl.getCnId());
			    /*if(tempMasterId != 0 && tempMasterId != annotInfoTbl.getMasterId()){
			    	break;
			    	
			    }*/
			    tempMasterId = annotInfoTbl.getMasterId();
			    annotInfoTbl = annotInfoDao.getRistInfo(params);
			 
			    annotInfoTbl.setAnnotClfCd("004");
			    annotInfoTbl.setGubun("L");
			    annotInfoTbl.setEntireYn("Y");
			    annotInfoTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			    annotInfoTbl.setRegrid("system");
			    annotInfoDao.insertAnnotInfo(annotInfoTbl);
			  
			    logger.debug("job finish master_id : "+annotInfoTbl.getMasterId()+"  ct_id : "+annotInfoTbl.getCnId());
			    MasterTbl masterTbl = new MasterTbl();
			    masterTbl.setMasterId(annotInfoTbl.getMasterId());
			    masterTbl.setRistClfCd(annotInfoTbl.getAnnotClfCd());
			    masterTbl.setModrid("system");
			    masterDao.updateMaster(masterTbl);
			    deleteContentDao.insertKwKlog(masterTbl.getMasterId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void getRistInfo(){
		try {
			MasterTbl masterTbl = new MasterTbl();
			TimeRistSetTbl timeRistSetTbl = addClipForTapeService.getTimeRistSet(5, "204440");
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
			System.out.println(masterTbl.getRistClfCd());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertKwgKlog() {
		try {
			deleteContentService.updateContentPathBlank(303L);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
