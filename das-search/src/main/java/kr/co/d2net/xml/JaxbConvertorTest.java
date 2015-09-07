package kr.co.d2net.xml;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import kr.co.d2net.commons.system.DasCmsConnector;
import kr.co.d2net.commons.system.DasCmsConnectorImpl;
import kr.co.d2net.commons.utils.BitConverter;
import kr.co.d2net.commons.utils.PropertyLoader;
import kr.co.d2net.commons.utils.Utility;
import kr.co.d2net.model.Annot;
import kr.co.d2net.model.AnnotInfo;
import kr.co.d2net.model.Archive;
import kr.co.d2net.model.CartContent;
import kr.co.d2net.model.CartItem;
import kr.co.d2net.model.Corner;
import kr.co.d2net.model.CornerItem;
import kr.co.d2net.model.Das;
import kr.co.d2net.model.DasSearch;
import kr.co.d2net.model.DownCart;
import kr.co.d2net.model.SearchInfo;
import kr.co.d2net.model.Storage;
import kr.co.d2net.model.TokenInfo;
import kr.co.d2net.service.XmlConvertorService;
import kr.co.d2net.service.XmlConvertorServiceImpl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.sbs.ifcms.Asset;
import com.sbs.ifcms.ObjectFactory;
import com.sbs.ifcms.query.QueryResult;

public class JaxbConvertorTest {
	private static Logger logger = Logger.getLogger(JaxbConvertorTest.class);

	@Ignore
	@Test
	public void searchTest() {
		try {
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();
			SearchInfo search = new SearchInfo();
			search.setKwd("동화속 과학탐험");
			search.setScn("");
			search.setDetailSearch("true");
			search.setUserId("");
			search.setReSrchFlag("false");
			search.setSort("D");
			search.setPageSize(2);
			search.setPageNum(1);
			search.setReSrchFlag("false");
			search.setSortcolumn("DAY");

			das.setSearchInfo(search);

			System.out.println(convertorService.createMarshaller(das));

			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			String resultMsg = cmsConnector.findContents(convertorService.createMarshaller(das));

			System.out.println(resultMsg.toLowerCase());
			if(StringUtils.isNotBlank(resultMsg)) {
				QueryResult result = new QueryResult();
				das = convertorService.unMarshaller(resultMsg.toLowerCase());

				//result.setTotalCount(das);
				//result.setOffset(offset);
				for(DasSearch dasSearch : das.getSearchList()) {
					Asset asset = ObjectFactory.createAsset(dasSearch.getMasterId());
					asset.setProperty("title", StringUtils.defaultString(dasSearch.getTitle(), ""));
					asset.setProperty("title_sub", StringUtils.defaultString(dasSearch.getTitle(), ""));
					asset.setProperty("program_name", "");
					asset.setProperty("corner_contents", StringUtils.defaultString(dasSearch.getCnNm(), ""));
					asset.setProperty("corner_title", StringUtils.defaultString(dasSearch.getCnInfo(), ""));
					asset.setProperty("program_sequence", StringUtils.defaultString(dasSearch.getEpisNo(), ""));
					asset.setProperty("program_sequence_total", "");
					asset.setProperty("creator", StringUtils.defaultString(dasSearch.getProducerNm(), ""));
					asset.setProperty("creator_sub", StringUtils.defaultString(dasSearch.getDrtNm(), ""));
					asset.setProperty("publisher", "");
					asset.setProperty("publisher_external", StringUtils.defaultString(dasSearch.getOrgPrdrNm(), ""));
					asset.setProperty("genre_l", StringUtils.defaultString(dasSearch.getCtgrLCd(), ""));
					asset.setProperty("genre_m", StringUtils.defaultString(dasSearch.getCtgrMCd(), ""));
					asset.setProperty("genre_s", StringUtils.defaultString(dasSearch.getCtgrSCd(), ""));

					result.addAsset(asset);
				}
				System.out.println(result.getAssets().size());
			} else {
				System.out.println("Search Result Not Found!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void loginTest() {
		try {
			Das das = new Das();

			TokenInfo tokenDO = new TokenInfo();
			tokenDO.setHex("DAS");
			tokenDO.setUserId("S522522");
			tokenDO.setPasswd("522522");
			das.setTokenInfo(tokenDO);

			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			System.out.println(cmsConnector.login(convertorService.createMarshaller(das)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void loginValidTest() {
		try {
			Das das = new Das();

			TokenInfo tokenDO = new TokenInfo();
			tokenDO.setHex("DAS");
			tokenDO.setToken("FSsvT/6EkS4uTEluOAeS/AP+o7qnI16dv2YNR7R313MNaJhDLl51N7ZcsGExQ1oU");
			das.setTokenInfo(tokenDO);

			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			System.out.println(cmsConnector.loginValid(convertorService.createMarshaller(das)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getBaseInfoTest() {
		try {

			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			String xml = cmsConnector.getBaseInfo(225621L).toLowerCase();
			System.out.println(xml);
			//Das das = convertorService.unMarshaller(xml);
			//MetaDataInfo metaDataInfo = das.getMetaDataInfo();
			//System.out.println(metaDataInfo.getMasterId());
			//System.out.println(metaDataInfo.getIngest().getItems().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getSubAssets() {
		try {

			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			Das das = convertorService.unMarshaller(cmsConnector.getGroupForMaster(206813L));

			if(das.getCorner() != null) {
				if(StringUtils.isNotBlank(das.getCorner().getGroup())) {
					String[] ctIds = null;
					if(das.getCorner().getGroup().indexOf(",") > -1) {
						ctIds = das.getCorner().getGroup().split("\\,");
					} else {
						ctIds = new String[1];
						ctIds[0] = das.getCorner().getGroup();
					}

					for(String ctId : ctIds) {
						Asset asset = ObjectFactory.createAsset(ctId);
						asset.setName(ctId);
						System.out.println("getSubAsset CT_ID: "+ctId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getSceanInfoTest() {
		try {

			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			Das das = convertorService.unMarshaller(cmsConnector.getSceanInfo(206057L).toLowerCase());
			System.out.println(das.getCorner().getItems().size());
			for(CornerItem cornerItem : das.getCorner().getItems()) {
				System.out.println(cornerItem.getCtId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Ignore
	@Test
	public void merDownload() {
		try {
			java.util.Map<String, Object> configMap = PropertyLoader.getPropertyHash("config");
			System.out.println(configMap.get("file.download.url"));

			Utility.download((String)configMap.get("file.download.url"), "/net_mp4/201206/26/411406/KFRM/411406.mer");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void masterByCtiIdsTest() {
		try {
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();

			String xml = cmsConnector.getGroupForMaster(207043L).toLowerCase();
			Das das = convertorService.unMarshaller(xml);
			System.out.println(das.getCorner().getGroup());

			if(StringUtils.isNotBlank(das.getCorner().getGroup())) {
				String[] ctIds = null;
				if(das.getCorner().getGroup().indexOf(",") > -1) {
					ctIds = das.getCorner().getGroup().split("\\,");
				} else {
					ctIds = new String[1];
					ctIds[0] = das.getCorner().getGroup();
				}

				for(String ctId : ctIds) {
					System.out.println(cmsConnector.getSceanInfoForIfCms(Long.valueOf(ctId)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void unCompressTest() {
		try {
			java.util.Map<String, Object> configMap = PropertyLoader.getPropertyHash("config");

			//String tmpPath = (String)configMap.get("file.download.tmp");
			String tmpPath = "";
			String kfrmPath = "D:/tmp/435748/";

			//String[] ids = {"437122", "437121", "437101", "437100", "437087", "437086", "437073", "437069"};
			String[] ids = {"435748"};
			for(String id : ids) {
				//String kfrmPath = "/mp4/201211/20/"+id+"/kfrm/";

				//Utility.download((String)configMap.get("file.download.url"), kfrmPath+id+".mer");

				//URL conURL = new URL(urlPath+"/mp4/200904/21/207018/KFRM/207018.mer");
				//URLConnection conn = conURL.openConnection();

				//InputStream fis = conn.getInputStream();
				//BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				//java.io.FileInputStream fis = new java.io.FileInputStream(tmpPath+kfrmPath+id+".mer");
				java.io.FileInputStream fis = new java.io.FileInputStream("D:\\435748.mer");
				byte[] b = new byte[4];

				// version
				fis.read(b);
				//System.out.println("version : "+BitConverter.toInt(b, -1));

				// image count
				fis.read(b);
				int size = BitConverter.toInt(b, -1);
				//System.out.println("size : "+size);

				//File f = new File(tmpPath+"/net_mp4/201206/26/411406/KFRM/411406/");
				File f = new File(kfrmPath+id);
				if(!f.exists()) f.mkdirs();

				byte[] image = null;
				for(int i=0; i<size; i++) {
					// frame num
					fis.read(b);
					int num = BitConverter.toInt(b, -1);

					// image size
					fis.read(b);
					int leng = BitConverter.toInt(b, -1);
					image = new byte[leng];
					fis.read(image);


					//Utility.byte2Image(tmpPath+"/net_mp4/201206/26/411406/KFRM/411406/"+num+".jpg", image);
					Utility.byte2Image(tmpPath+kfrmPath+num+".jpg", image);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void streamURLLinkTest() {
		BufferedReader in = null;
		try {
			URL txtURL = new URL("http://10.40.24.62/mp4/201210/02/420662/KFRM/420662.txt");
			in = new BufferedReader(new InputStreamReader(txtURL.openStream()));

			List<Long> frameList = new ArrayList<Long>();
			String readLine = "";
			while ((readLine = in.readLine()) != null) {
				if(logger.isDebugEnabled()) {
					logger.debug("readLine : "+readLine);
				}
				if(readLine.indexOf(",") > -1) {
					String[] indexes = readLine.split("\\,");
					for(String index : indexes) {
						if(StringUtils.isNotBlank(index) && Utility.isNumeric(index))
							frameList.add(Long.valueOf(index));
					}
				} else {
					if(StringUtils.isNotBlank(readLine) && Utility.isNumeric(readLine))
						frameList.add(Long.valueOf(readLine));
				}
			}
			for(Long index : frameList) {
				System.out.println(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}

	}

	@Ignore
	@Test
	public void compressTest() {
		try {
			File f = new File("Y:\\201211\\15\\436521\\KFRM");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("D:\\tmp\\436521.mer")));
			if(f.exists()) {
				File[] files = f.listFiles(new UserFileFilter());

				// version
				bos.write(BitConverter.getBytes(2, -1));

				// file count
				int fileSize = files.length;
				bos.write(BitConverter.getBytes(fileSize, -1));

				FileInputStream fis = null;
				byte[] kfrm = null;
				for(File img : files) {
					// frame number
					String fileName = img.getName();
					int frame = Integer.valueOf(fileName.substring(0, fileName.indexOf("."))).intValue();
					bos.write(BitConverter.getBytes(frame, -1));

					// file size
					bos.write(BitConverter.getBytes((int)img.length(), -1));

					kfrm = new byte[(int)img.length()];
					fis = new FileInputStream(img);

					// file bytes
					fis.read(kfrm);
					bos.write(kfrm);
				}
				bos.flush();
				fis.close();
				bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class UserFileFilter implements FileFilter {

		private final String[] useFileExtensions = new String[] {"jpg", "JPG"};

		public boolean accept(File file) {
			for (String extension : useFileExtensions) {
				if (file.getName().toLowerCase().endsWith(extension)) {
					return true;
				}
			}
			return false;
		}

	}

	@Ignore
	@Test
	public void archiveReq() {
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			Das das = new Das();
			java.util.Properties parameters = new Properties();
			parameters.put("som", "00:00:14:01");
			parameters.put("eom", "00:00:32:01");
			parameters.put("vd_hresol", "1920");
			parameters.put("vd_vresol", "247");
			parameters.put("fl_sz", "0");
			parameters.put("bit_rt", "796000");
			parameters.put("aud_samp_frq", "48000");
			parameters.put("aud_bdwt", "1536000");
			parameters.put("epis_no", "1");
			parameters.put("producer_nm", "김사탕");
			parameters.put("cmr_place", "뉴질랜드");
			parameters.put("fm_dt", "19990909");
			parameters.put("cprt_nm", "sbs");
			parameters.put("cprt_cd", "001");
			parameters.put("rist_clf_cd", "240");
			parameters.put("req_nm", "신세계");
			parameters.put("req_id", "D080009");
			parameters.put("req_dt", "20100909");
			parameters.put("media_id", "34567");
			parameters.put("record_type_cd", "001");
			parameters.put("frm_per_sec", "29.97");
			parameters.put("org_file_nm", "1234");
			parameters.put("storage_path", "Y\\TEST\\test</storage_path");
			parameters.put("audio_yn", "N");
			parameters.put("drop_yn", "N");
			parameters.put("ct_typ", "001");
			parameters.put("ct_leng", "1212");
			parameters.put("asp_rto_cd", "001");
			parameters.put("vd_qlty", "001");
			parameters.put("data_stat", "003");
			parameters.put("ct_cla", "001");
			parameters.put("clip_nm", "새아침");
			parameters.put("brd_leng", "00:42:49");
			parameters.put("brd_end_hms", "1737000 ");
			parameters.put("brd_bgn_hms", "1737000 ");
			parameters.put("brd_dd", "20100909");
			parameters.put("title", "새벽");
			parameters.put("pgm_nm", "5678");
			parameters.put("co_cd", "M");
			parameters.put("channel_cd", "M");

			Archive archive = new Archive();
			archive.setBrdDd(parameters.getProperty("brd_dd", ""));
			archive.setFmDt(parameters.getProperty("fm_dt", ""));
			archive.setTitle(parameters.getProperty("title", ""));
			archive.setSubTtl(parameters.getProperty("sub_ttl", ""));
			archive.setEpisNo(Integer.valueOf(parameters.getProperty("epis_no", "0")));
			archive.setCtgrLCd(parameters.getProperty("ctgr_l_cd", ""));
			archive.setCtgrMCd(parameters.getProperty("ctgr_m_cd", ""));
			archive.setCtgrSCd(parameters.getProperty("ctgr_s_cd", ""));
			archive.setRistClfCd(parameters.getProperty("rist_clf_cd", ""));
			archive.setPgmRate(parameters.getProperty("pgm_rate", ""));
			archive.setCprtCd(parameters.getProperty("cprt_cd", ""));
			archive.setCprtNm(parameters.getProperty("cprt_nm", ""));
			archive.setCprtTypeDsc(parameters.getProperty("cprt_type_dsc", ""));
			archive.setViewGrCd(parameters.getProperty("view_gr_cd", ""));
			archive.setAnnotClfCont(parameters.getProperty("annot_clf_cont", ""));
			archive.setDrtNm(parameters.getProperty("drt_nm", ""));
			archive.setWriterNm(parameters.getProperty("writer_nm", ""));
			archive.setProducerNm(parameters.getProperty("producer_nm", ""));
			archive.setCmrDrtNm(parameters.getProperty("cmr_drt_nm", ""));
			archive.setOrgPrdrNm(parameters.getProperty("org_prdr_nm", ""));
			archive.setPrdtInOutsCd(parameters.getProperty("prdt_in_outs_cd", ""));
			archive.setCmrPlace(parameters.getProperty("cmr_place", ""));
			archive.setKeyWords(parameters.getProperty("key_words", ""));
			archive.setSpcInfo(parameters.getProperty("spc_info", ""));
			archive.setCtNm(parameters.getProperty("ct_nm", ""));
			//archive.setBrdBgnHms(parameters.getProperty("brd_bgn_hms", ""));
			//archive.setBrdEndHms(parameters.getProperty("brd_end_hms", ""));
			archive.setArtistNm(parameters.getProperty("artist_nm", ""));
			archive.setNationCd(parameters.getProperty("nation_cd", ""));
			archive.setRsvPrdCd(parameters.getProperty("rsv_prd_cd", ""));
			archive.setMediaId(parameters.getProperty("media_id", ""));
			archive.setPdsCmsPgmId(parameters.getProperty("pds_cms_pgm_id", ""));
			archive.setAspRtoCd(parameters.getProperty("asp_rto_cd", ""));
			archive.setVdQlty(parameters.getProperty("vd_qlty", ""));
			archive.setArchRoute(parameters.getProperty("arch_route", ""));
			archive.setCompCd(parameters.getProperty("comp_cd", ""));
			archive.setChanCd(parameters.getProperty("chan_cd", ""));
			archive.setRecordTypeCd(parameters.getProperty("record_type_cd", ""));
			archive.setBrdLeng(parameters.getProperty("brd_leng", ""));
			archive.setClipNm(parameters.getProperty("clip_nm", ""));
			archive.setCtCla(parameters.getProperty("ct_cla", ""));
			archive.setCtLeng(parameters.getProperty("ct_leng", ""));
			archive.setCtType(parameters.getProperty("ct_type", ""));
			//archive.setDasEqId(parameters.getProperty("das_eq_id", ""));
			//archive.setDasEqPsCd(parameters.getProperty("das_eq_ps_cd", ""));
			//archive.setDrpFrmYn(parameters.getProperty("drp_frm_yn", ""));
			archive.setDuration(Long.valueOf(parameters.getProperty("duration", "0")));
			archive.setFlSz(Long.valueOf(parameters.getProperty("fl_sz", "0")));
			//archive.setOrgFileNm(parameters.getProperty("org_file_nm", ""));
			archive.setReqDt(parameters.getProperty("req_dt", ""));
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
			archive.setCoCd(parameters.getProperty("co_cd", ""));
			archive.setChennelCd(parameters.getProperty("channel_cd", ""));

			das.setArchive(archive);
			System.out.println(cmsConnector.archiveReq(convertorService.createMarshaller(das)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDownCart() {
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			Das das = new Das();

			// CT_ID 입력
			DownCart downCart = new DownCart();
			downCart.setCtId(411729L);
			//downCart.setDataClfCd("001");  //
			//downCart.setVdQlty("002");////
			//downCart.setAspRtoCd("002");//
			downCart.setReqUsrid("D080009");
			downCart.setReqDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			downCart.setCartStat("001");
			//downCart.setCoCd("M");//
			//downCart.setOutStrgLoc("AAA");
			das.setDownCart(downCart);

			String xml = convertorService.createMarshaller(das);
			System.out.println("request: "+xml);

			xml = cmsConnector.insertDownCart(xml);
			System.out.println("insertDownCart : "+xml);


			das = convertorService.unMarshaller(xml.toLowerCase());

			Das das2 = new Das();

			CartContent cartContent = new CartContent();
			cartContent.setCartNo(das.getDownCart().getCartNo());
			cartContent.setCartSeq(1);
			cartContent.setCtId(46511L);
			cartContent.setCtiId(0L);
			cartContent.setDownTyp("P"); // F:Full, P:Partial

			if("P".equals(cartContent.getDownTyp())) {
				cartContent.setSom("00:00:14:01");
				cartContent.setEom("00:00:32:01");
				if(StringUtils.isNotBlank(cartContent.getEom()) && StringUtils.isNotBlank(cartContent.getSom())) {
					cartContent.setDuration(Utility.changeTimeCode(cartContent.getEom()) - Utility.changeTimeCode(cartContent.getSom()));
				}
			} else {
				cartContent.setSom("00:00:00:00");
				cartContent.setEom("");
			}
			cartContent.setMasterId(das.getDownCart().getMasterId());
			cartContent.setsFrame(Utility.changeTimeCode(cartContent.getSom()));
			cartContent.setDownStat("001");
			cartContent.setPhysicalTree("bb");
			cartContent.setRegrid("S522522");
			cartContent.setCtiId(das.getDownCart().getCtiId());

			das2.setCartContent(cartContent);
			xml = convertorService.createMarshaller(das2);
			System.out.println("request2: "+xml);

			xml = cmsConnector.insertCartCont(xml);
			System.out.println("insertCartCont : "+xml);

			das = convertorService.unMarshaller(xml.toLowerCase());

			Das das3 = new Das();
			downCart = new DownCart();
			downCart.setDownSubj("test");// 입력한 다운로드 요청제목(사용자 입력)
			//downCart.setNodecaption("개발 테스트"); // 폴더명
			downCart.setNodeid(3467);
			downCart.setCellname("PM-CU"); // 폴더ID
			downCart.setDownGubun("005");
			downCart.setCartNo(das.getCartContent().getCartNo());
			downCart.setUserId(das.getCartContent().getRegrid());
			downCart.setPhysicaltree("3368"); // 
			downCart.setUrl("http://"); // 입력 url

			das3.setDownCart(downCart);
			xml = convertorService.createMarshaller(das3);
			System.out.println("request3: "+xml);

			Integer result = cmsConnector.updateDownCart(xml);
			System.out.println("updateDownCart - result: "+result+", "+downCart.getCartNo());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDownloadList() {
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();
			CartItem cartItem = new CartItem();
			cartItem.setUserid("D522522");
			das.addCartItem(cartItem);

			String xml = convertorService.createMarshaller(das);
			System.out.println(xml);

			System.out.println(cmsConnector.getDownloadList(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testApproveList() {
		try {

			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();
			CartItem cartItem = new CartItem();
			cartItem.setFromdate(Utility.getDate(-7));
			cartItem.setEnddate(Utility.getDate(0));
			cartItem.setUserid("S522522");
			das.addCartItem(cartItem);

			String xml = convertorService.createMarshaller(das);
			System.out.println("request : "+xml);

			xml = cmsConnector.getApproveList(xml);
			System.out.println("response : "+xml);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDateConverter() {
		try {
			String date = "20120805143010";

			SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

			Date d1 = df1.parse(date);

			System.out.println(df2.format(d1));


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testSceanInfo() {
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			//System.out.println(cmsConnector.getSceanInfoForIfCms(46517L));
			//String xml = FileUtils.readFileToString(new File("D:/tmp/annot.xml"), "utf-8");
			System.out.println(cmsConnector.getSceanInfoForIfCms(448286L));
			
			Das das = convertorService.unMarshaller(cmsConnector.getSceanInfoForIfCms(448159L));
			//Das das = convertorService.unMarshaller(xml);
			Corner corner = das.getCorner();
			for(CornerItem cornerItem : corner.getItems()) {
				Annot annot = cornerItem.getAnnot();
				System.out.println(annot);
				if(annot != null) {
					for(AnnotInfo item : annot.getItems()) {
						System.out.println(item.getAnnotClfCd());
					}
				}
				/*if(cornerItem.getRpimgKfrmSeq() != null && cornerItem.getRpimgKfrmSeq() > 0) {
					System.out.println(cornerItem.getRpimgKfrmSeq());
					break;
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDiskCheck() {
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl();
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();
			Storage storage = new Storage();

			storage.setSystemId("SBSNON");
			das.setStorage(storage);
			String xml = cmsConnector.getDiskSpace(convertorService.createMarshaller(das));
			System.out.println("return xml : "+xml);

			das = convertorService.unMarshaller(xml);
			System.out.println(das.getStorage().getPassibleSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testEscape() {
		try {

			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			convertorService.unMarshaller("<?xml version=\"1.0\" encoding=\"utf-8\"?><das><monitoring><job_nm>download</job_nm><title>수동테스트 인점3Aa&amp;Bb</title><status>C</status><progress>100</progress><start_time>20130128195014</start_time><app_cont></app_cont><end_time>20130128195207</end_time></monitoring></das>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Ignore
	@Test
	public void testRelatedYn() {
		try {
			String masterId = "228407";
			String ctId = "441828";
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl("http://10.30.23.48:8088/PDAS/services/PDASServices");
			
			if(ctId.startsWith("M")) {
				masterId = masterId.substring(1);
			}
			if(ctId.startsWith("C")) {
				ctId = ctId.substring(1);
			}
			
			String relatedYn = cmsConnector.isVideoRelatedYN(Long.valueOf(masterId), Long.valueOf(ctId));
			System.out.println(relatedYn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDownCart2() {
		/*
		String txID ="";
		String workflow="download";
		String identifier="418671";
		String callback="22222";
		Properties parameters = new Properties();
		String returnId = "";
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl("http://localhost:9900/url");
			parameters.setProperty("mark_in", "00:00:01:00");
			parameters.setProperty("mark_out", "00:00:02:00");
			parameters.setProperty("comments", "");
			parameters.setProperty("target_storage_id", "PM-CU");
			parameters.setProperty("target_folder_id", "pdssystem");
			parameters.setProperty("target_cms_id", "pdssystem_1_3304");
			Das das = new Das();

			if(logger.isDebugEnabled()) {
				logger.debug("workflow : "+workflow);
				logger.debug("workflow : "+parameters);
			}

			if ("archive".equals(workflow)) {
				throw new OperationNotSupportedException("Workflow '" + workflow + "' is not supported");

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

			} else if ("download".equals(workflow)) {

				if(logger.isDebugEnabled()) {
					logger.debug("download identifier: "+identifier);
					//logger.debug("userid: "+session.getUserId());
				}

				if(StringUtils.isBlank(identifier)) {
					throw new CMSRuntimeException("다운로드에 필요한 영상ID가 없습니다.");
				}

				XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

				// CT_ID 입력
				DownCart downCart = new DownCart();
				downCart.setCtId(Long.valueOf(identifier));
				downCart.setReqUsrid("S522522");
				downCart.setReqDt(Utility.getTimestamp("yyyyMMddHHmmss"));
				downCart.setCartStat("001");
				das.setDownCart(downCart);

				System.out.println("=====================================xml" + das.getDownCart().getCartStat());
				System.out.println("=====================================xml" + das.getDownCart().getCartStat());
				System.out.println("=====================================xml" + das.getDownCart().getCartStat());
				System.out.println("=====================================xml" + das.getDownCart().getCartStat());
				System.out.println("=====================================xml" +das);
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

				CartContent cartContent = new CartContent();
				cartContent.setCartNo(das.getDownCart().getCartNo());
				cartContent.setCartSeq(1);
				cartContent.setCtId(Long.valueOf(identifier));
				cartContent.setCtiId(0L);

				// Full, Partial 여부가 필요. 
				cartContent.setDownTyp("P"); // F:Full, P:Partial

				if("P".equals(cartContent.getDownTyp())) {
					cartContent.setSom(parameters.getProperty("mark_in", ""));
					cartContent.setEom(parameters.getProperty("mark_out", ""));
					if(StringUtils.isNotBlank(cartContent.getEom()) && StringUtils.isNotBlank(cartContent.getSom())) {
						cartContent.setDuration(Utility.changeTimeCode(cartContent.getEom()) - Utility.changeTimeCode(cartContent.getSom()));
					}
				} else {
					cartContent.setSom("00:00:00:00");
					cartContent.setEom("");
				}

				cartContent.setMasterId(das.getDownCart().getMasterId());
				cartContent.setsFrame(Utility.changeTimeCode(cartContent.getSom()));
				cartContent.setDownStat("001");
				cartContent.setRegrid("S522522");
				cartContent.setCtCont(parameters.getProperty("comments", ""));
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
				downCart.setDownSubj("S52");// 요청한 사용자명
				downCart.setCellname(parameters.getProperty("target_storage_id", "")); // 폴더ID
				downCart.setDownGubun("007"); // ifcms 구분자, PDS(001), NDS(002), 데정팀(003), TAPE_OUT(004)
				downCart.setCartNo(das.getCartContent().getCartNo());
				downCart.setUserId(das.getCartContent().getRegrid());
				downCart.setPhysicaltree(parameters.getProperty("target_folder_id", "")); // 
				downCart.setUrl(callback); // 입력 url

				das3.setDownCart(downCart);

				xml = convertorService.createMarshaller(das3);
				if(logger.isDebugEnabled()) {
					logger.debug("updateDownCart request: "+xml);
				}

				Integer result = cmsConnector.updateDownCart(xml);
				if(logger.isDebugEnabled()) {
					logger.debug("updateDownCart response: "+xml);
				}

				// 성공하면 1
				if(result == 1) {
					if(logger.isDebugEnabled()) {
						logger.debug("updateDownCart response: "+xml);
					}
				} else {

				}
				returnId = String.valueOf(downCart.getCartNo());
			} else if ("transfer".equals(workflow)) {
				throw new OperationNotSupportedException("Workflow '" + workflow + "' is not supported");
			} else {
				throw new OperationNotSupportedException("Workflow '" + workflow + "' is not supported");
			}
		} catch (Exception e) {
			throw new CMSRuntimeException(e);
		}

		//return returnId;
		 */
	}

}
