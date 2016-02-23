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

import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.ops.Corner;
import com.sbs.das.dto.ops.Corners;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.dto.xml.DeleteRequest;
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
		String xml = "";
		try {
			Data data = (Data)xmlStream.fromXML("<data><brad_day>20120310</brad_day><brad_st_time>074000</brad_st_time><brad_fns_time>084200</brad_fns_time></data>");
			List<MetadatMstTbl> metadatMstTbls = metadataService.findMetaDataList(data);
			
			data = new Data();
			for(MetadatMstTbl metadatMstTbl : metadatMstTbls) {
				Metadata metadata = new Metadata();
				metadata.setDasMasterId(metadatMstTbl.getMasterId());
				metadata.setDasPgmCd(metadatMstTbl.getPgmCd());
				metadata.setChId(metadatMstTbl.getChennelCd());
				metadata.setPgmTms(metadatMstTbl.getEpisNo());
				metadata.setPgmTmsTitle(metadatMstTbl.getTitle());
				metadata.setBradDay(metadatMstTbl.getBrdDd());
				metadata.setBradStTime(metadatMstTbl.getBrdBgnHms());
				metadata.setBradFnsTime(metadatMstTbl.getBrdEndHms());
				metadata.setBradLen(metadatMstTbl.getDuration());
				
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
			metadata.setDasMasterId(234234234L);
			metadata.setDasPgmCd("XX1234");
			metadata.setChId("dsfsdfsdf");
			metadata.setPgmTms(234234);
			metadata.setPgmTmsTitle("dsfsdfsdf");
			metadata.setBradDay("dsfsdfsdf");
			metadata.setBradStTime("dsfsdfsdf");
			metadata.setBradFnsTime("dsfsdfsdf");
			metadata.setBradLen(34234234L);
			
			//data.addMetadatas(metadata);
			System.out.println(data);
			xmlStream.toXML(data);
			//System.out.println(xmlStream.toXML(data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Data data = (Data)xmlStream.fromXML(xml);
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
}
