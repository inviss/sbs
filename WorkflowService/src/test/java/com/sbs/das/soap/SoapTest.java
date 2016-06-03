package com.sbs.das.soap;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.das.commons.system.DasCmsConnector;
import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.ops.Annot;
import com.sbs.das.dto.ops.CartContent;
import com.sbs.das.dto.ops.Corner;
import com.sbs.das.dto.ops.Corners;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.DownCart;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.dto.xml.DeleteRequest;
import com.sbs.das.services.ContentDownloadService;
import com.sbs.das.services.MetadataService;
import com.sbs.das.web.DasCMS;

public class SoapTest extends BaseConfig{
	//@Autowired
	//private JaxWsProxyFactoryBean nevigatorProxyFactory;

	@Autowired
	private JaxWsProxyFactoryBean dasCmsProxyFactory;

	@Autowired
	private MetadataService metadataService;

	//@Autowired
	//private DasCmsConnector dasCmsConnector;

	@Autowired
	private XmlStream xmlStream;

	@Autowired
	private ContentDownloadService contentDownloadService;

	@Autowired
	private DasCmsConnector cmsConnector;


	@Ignore
	@Test
	public void soapConnTest() {
		try {

			//String xml = FileUtils.readFileToString(new File("D:/tmp/archive2.xml"), "utf-8");
			//Nevigator navigator = (Nevigator)jaxWsProxyFactoryBean.create();
			//System.out.println(navigator.schedulerForceExecute("<das><info><req_method>scrap</req_method><limit_day>20121224</limit_day><co_cd>S</co_cd></info></das>"));

			//System.out.println(navigator.addClipInfoService(xml));

			//System.out.println(navigator.archiveStatus(xml));

			//System.out.println(navigator.serviceTest("hello"));

			/*File f = new File("D:/tmp/archive");
			File[] xmlFiles = f.listFiles(new UserFileFilter());

			for(File fx : xmlFiles) {
				String xml = FileUtils.readFileToString(fx, "utf-8");
				navigator.archiveService(xml);
				fx.renameTo(new File(fx.getAbsolutePath()+".bak"));
			}*/


			/*for(int i=0; i<1000; i++) {
				dasCmsConnector.getMetadatInfoList(xml);
			}*/
			//dasCmsConnector.insertArchiveReq(xml);

			DasCMS dasCMS = (DasCMS)dasCmsProxyFactory.create();

			String xml = FileUtils.readFileToString(new File("D:/tt.xml"), "utf-8");
			dasCMS.findEpisodeList(xml);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class UserFileFilter implements FileFilter {

		private final String[] useFileExtensions = new String[] {"xml"};

		public boolean accept(File file) {
			for (String extension : useFileExtensions) {
				if (file.getName().toLowerCase().endsWith(extension)) {
					return true;
				}
			}
			return false;
		}

	}

	//@Ignore
	@Test
	public void findMetadataTest() {
		
		try {
			String xml = FileUtils.readFileToString(new File("D:/find_episode.xml"), "utf-8");
			//Data data = (Data)xmlStream.fromXML("<data><brad_day>20120310</brad_day><brad_st_time>074000</brad_st_time><brad_fns_time>084200</brad_fns_time></data>");
			Data data = (Data)xmlStream.fromXML(xml);
			List<MetadatMstTbl> metadatMstTbls = metadataService.findMetaDataList(data);

			data = new Data();
			for(MetadatMstTbl metadatMstTbl : metadatMstTbls) {
				Metadata metadata = new Metadata();
				metadata.setDasMasterId(metadatMstTbl.getMasterId().toString());
				metadata.setDasPgmCd(metadatMstTbl.getPgmCd());
				metadata.setChId(metadatMstTbl.getChennelCd());
				metadata.setPgmTms(metadatMstTbl.getEpisNo());
				metadata.setPgmTmsTitle(metadatMstTbl.getTitle());
				metadata.setBradDay(metadatMstTbl.getBrdDd());
				metadata.setBradStTime(metadatMstTbl.getBrdBgnHms());
				metadata.setBradFnsTime(metadatMstTbl.getBrdEndHms());
				metadata.setBradLen(metadatMstTbl.getDuration());

				/*
				 * 2016.06.02
				 * OPS 추가요청 메타정보
				 */
				metadata.setIsLinked(null);
				metadata.setKeyWords(metadatMstTbl.getKeyWords());
				metadata.setFinalBrdYn(metadatMstTbl.getFinalBrdYn());
				metadata.setPrdtInOutsCd(metadatMstTbl.getPrdtInOutsCd());
				metadata.setOrgPrdrNm(metadatMstTbl.getOrgPrdrNm());
				metadata.setCmrDrtNm(metadatMstTbl.getCmrDrtNm());
				metadata.setPrdtDeptNm(metadatMstTbl.getPrdtDeptNm());
				metadata.setBrdBgnDd(metadatMstTbl.getBrdBgnDd());
				metadata.setBrdEndDd(metadatMstTbl.getBrdEndDd());
				
				data.addMetadatas(metadata);
			}

			System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+xmlStream.toXML(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void xmlMetadatLoadTest() {
		String xml = "";
		try {
			//xml = FileUtils.readFileToString(new File("D:/metadat.xml"), "utf-8");
			//Data data = (Data)xmlStream.fromXML(xml);

			Data data = new Data();
			Metadata metadata = new Metadata();
			metadata.setDasMasterId("234234234");
			metadata.setDasPgmCd("XX1234");
			metadata.setChId("dsfsdfsdf");
			metadata.setPgmTms(234234);
			metadata.setPgmTmsTitle("한글을 사용합니다.");
			metadata.setBradDay("dsfsdfsdf");
			metadata.setBradStTime("dsfsdfsdf");
			metadata.setBradFnsTime("dsfsdfsdf");
			metadata.setBradLen(34234234L);

			data.addMetadatas(metadata);
			//xmlStream.toXML(data);
			System.out.println(xmlStream.toXML(data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Ignore
	@Test
	public void xmlCornerLoadTest() {
		String xml = "";
		try {
			xml = FileUtils.readFileToString(new File("D:/corners.xml"), "utf-8");
			Data data = (Data)xmlStream.fromXML(xml);
			Corners corners = data.getCorners();
			List<Corner> corners2 = corners.getCorners();
			for(Corner corner : corners2) {
				System.out.println("corner_nm: "+corner.getCornerNm());
				System.out.println("sen_titles: "+corner.getSenTitles());
				List<Annot> annots = corner.getAnnots();
				for(Annot annot : annots) {
					System.out.println(annot.getClfCd()+", "+annot.getClfCont());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Data data = (Data)xmlStream.fromXML(xml);
	}


	@Ignore
	@Test
	public void testPath() {
		try {
			String high = "/resotre/admin/64008";
			String name = "737981.mxf";
			String dasPrefix = "/mp2";

			DeleteRequest request = new DeleteRequest();
			request.setHighPath(high);
			request.setHighFlNm(name);

			// 고용량 영상이 존재한다면 삭제하고 path를 blank 처리한다.
			if(StringUtils.isNotBlank(request.getHighPath()) && StringUtils.isNotBlank(request.getHighFlNm())) {
				String path = request.getHighPath();
				if(path.indexOf("\\") > -1) path = path.replaceAll("\\\\", "/");
				if(!path.startsWith("/")) path = "/"+path;
				if(!path.endsWith("/")) path = path+"/";

				System.out.println(request.getHighPath().indexOf(dasPrefix));
				if(request.getHighPath().indexOf(dasPrefix) < 0) {
					request.setHighPath(dasPrefix+path);
				}

				File f = new File(request.getHighPath(), request.getHighFlNm());

				System.out.println(f.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void insertDownRequest() {
		try {
			Das das = new Das();
			ContentInstTbl contentInstTbl = contentDownloadService.getContentInstObj(60326L);
			if(contentInstTbl == null || (contentInstTbl.getCtId() == null || contentInstTbl.getCtId() < 0L))
				System.out.println("There is no keys. master_id or ct_id: "+contentInstTbl.getCtId());

			DownCart downCart = new DownCart();
			downCart.setCtId(contentInstTbl.getCtId());
			downCart.setCtiId(contentInstTbl.getCtiId());
			downCart.setReqUsrid("hello");
			downCart.setReqDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			downCart.setCartStat("001");

			das.setDownCart(downCart);		
			String downCartXml = xmlStream.toXML(das);
			System.out.println("insertDownCart request: "+downCartXml);
			
			String rtnXML = cmsConnector.insertDownCart(downCartXml);
			System.out.println("insertDownCart response: "+rtnXML);
			if(StringUtils.isNotBlank(rtnXML)) {
				das = (Das)xmlStream.fromXML(rtnXML);
				
				CartContent cartContent = new CartContent();
				cartContent.setCartNo(das.getDownCart().getCartNo());
				cartContent.setCartSeq(1);
				cartContent.setCtId(239659L);
				cartContent.setCtiId(474036L);
				cartContent.setMasterId(60326L);
				cartContent.setDownTyp("F");
				cartContent.setSom("");
				cartContent.setEom("");
				cartContent.setsFrame(0L);
				cartContent.setDownStat("001");
				cartContent.setRegrid(das.getDownCart().getRegrid());
				cartContent.setReqCont("");
				
				das.setCartContent(cartContent);
				
				String cartContXml = xmlStream.toXML(das);
				System.out.println("insertCartCont request: "+cartContXml);
				String rtnCartXML = cmsConnector.insertCartCont(cartContXml);
				System.out.println("insertCartCont response: "+rtnCartXML);
				
				if(StringUtils.isNotBlank(rtnCartXML)) {
					das = (Das)xmlStream.fromXML(rtnCartXML);
					
					downCart = new DownCart();
					downCart.setDownSubj("");
					downCart.setCellname(""); 											// 폴더ID
					downCart.setDownGubun("008"); 										// ifcms 구분자, PDS(001), NDS(002), 데정팀(003), TAPE_OUT(004), OPS(008)
					downCart.setCartNo(das.getCartContent().getCartNo());
					downCart.setUserId(das.getCartContent().getRegrid());
					downCart.setPhysicaltree(""); 										// 
					downCart.setUrl(""); 												// 입력 url
					downCart.setReqCont("");
					das.setDownCart(downCart);
					
					String downCartXML = xmlStream.toXML(das);
					Integer result = cmsConnector.updateDownCart(downCartXML);
					if(result == 1) {
						System.out.println("updateDownCart success: "+result);
					}
					System.out.println("returnId  : "+result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
