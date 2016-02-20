package com.sbs.das.sample.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.repository.MetadatMstDao;
import com.sbs.das.repository.PgmInfoDao;
import com.sbs.das.sample.BaseConfig;

public class OpsDaoTest extends BaseConfig {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PgmInfoDao pgmInfoDao;
	
	@Autowired
	private MetadatMstDao metadatMstDao;
	
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

}
