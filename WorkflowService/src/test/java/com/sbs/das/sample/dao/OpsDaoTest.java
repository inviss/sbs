package com.sbs.das.sample.dao;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.repository.MetadatMstDao;
import com.sbs.das.repository.PgmInfoDao;
import com.sbs.das.sample.BaseConfig;
import com.sbs.das.services.CornerService;
import com.sbs.das.services.MetadataService;
import com.sbs.das.services.PgmInfoService;

public class OpsDaoTest extends BaseConfig {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PgmInfoService pgmInfoService;
	
	@Autowired
	private MetadatMstDao metadatMstDao;
	
	@Autowired
	private XmlStream xmlStream;
	
	@Autowired
	private MetadataService metadataService;
	
	@Autowired
	private CornerService cornerService;
	
	@Ignore
	@Test
	public void findMetadataList() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("brdDd", "20120310");
			params.put("brdBgnHms", "074000");
			params.put("brdEndHms", "084200");
			
			List<MetadatMstTbl> metadatMstTbls = metadatMstDao.findMetadataList(params);
			for(MetadatMstTbl metadatMstTbl : metadatMstTbls) {
				System.out.println(metadatMstTbl.getTitle());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void getMetadataObj() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("masterId", "205298");
			
			MetadatMstTbl metadatMstTbl = metadatMstDao.getMetadata(params);
			System.out.println(metadatMstTbl.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void savePgmInfoTest() {
		try {
			String xml = FileUtils.readFileToString(new File("D:/savePgm.xml"), "utf-8");
			
			Data data = (Data)xmlStream.fromXML(xml);
			pgmInfoService.savePgmInfo(data.getProgram());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void saveMetadatInfoTest() {
		try {
			String xml = FileUtils.readFileToString(new File("D:/metadata.xml"), "utf-8");
			
			Data data = (Data)xmlStream.fromXML(xml);
			Metadata mst = (Metadata)data.getMetadatas().get(0);
			metadataService.updateMetadataInfo(mst);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void saveCornerInfoTest() {
		try {
			String xml = FileUtils.readFileToString(new File("D:/corners.xml"), "utf-8");
			
			Data data = (Data)xmlStream.fromXML(xml);
			Metadata mst = (Metadata)data.getMetadatas().get(0);
			cornerService.updateCorners(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
