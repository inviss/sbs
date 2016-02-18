package com.sbs.das.sample.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.das.dto.PgmInfoTbl;
import com.sbs.das.repository.PgmInfoDao;
import com.sbs.das.sample.BaseConfig;

public class OpsDaoTest extends BaseConfig {
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PgmInfoDao pgmInfoDao;
	
	//@Ignore
	@Test
	public void getMetadata() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("brdDd", "20120101");
			params.put("brdBgnHms", "070000");
			params.put("brdEndHms", "080000");
			
			pgmInfoDao.findPgmInfo(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
