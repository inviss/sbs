package com.sbs.das.soap;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.xml.DeleteRequest;
import com.sbs.das.web.DasCMS;

public class SoapTest extends BaseConfig{
	//@Autowired
	//private JaxWsProxyFactoryBean nevigatorProxyFactory;
	
	@Autowired
	private JaxWsProxyFactoryBean dasCmsProxyFactory;
	
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
	public void xmlLoadTest() {
		String xml = "";
		try {
			xml = FileUtils.readFileToString(new File("D:/tt.xml"), "utf-8");
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
