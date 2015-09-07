package com.sbs.das.sample.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.das.commons.utils.Utility;
import com.sbs.das.repository.ContentDownDao;
import com.sbs.das.sample.BaseConfig;
import com.sbs.das.services.DeleteContentAdapter;
import com.sbs.das.services.DeleteContentService;

public class DeleteTest extends BaseConfig {

	@Autowired
	private DeleteContentAdapter deleteContentAdapter;
	@Autowired
	private ContentDownDao contentDownDao;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private DeleteContentService deleteContentService;
	
	@Ignore
	@Test
	public void userDeleteTest() {
		try {
			String xml = FileUtils.readFileToString(new File("D:\\tmp\\user_del.xml"), "utf-8");
			System.out.println(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void storageCheck() {
		try {
			String mxf = "774571.mxf";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cartNo", 78941);
			params.put("filename", mxf.substring(0, mxf.lastIndexOf("."))+"%");
			
			System.out.println(contentDownDao.getContentDownCount(params));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void downloadCheck() {
		try {
			String coCd = "S";
			String limitDay = "20121123";
			deleteContentAdapter.downloadExpiredDelete(coCd, limitDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void insertArchiveJob() {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			String xml = FileUtils.readFileToString(new File("D:/20121229.xml"), "utf-8");
			connection = dataSource.getConnection();
			ps = connection.prepareStatement("select  NEXTVAL FOR ARCHIVE_SEQ_ID from sysibm.sysdummy1");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Long seq = rs.getLong("1");
				
				System.out.println("seq: "+seq);
				System.out.println(xml);
				ps = connection.prepareStatement("insert into archive_job_tbl (seq, xml_cont, job_alocate, reg_dt) values (?, ?, ?, ?)");
				ps.setLong(1, seq);
				ps.setString(2, xml);
				ps.setString(3, "N");
				ps.setString(4, Utility.getTimestamp("yyyyMMddHHmmss"));
				
				ps.executeUpdate();
				System.out.println("insert success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (Exception e2) {}
		}
	}
	
	@Test
	public void insertKwKlog() {
		try {
			deleteContentService.updateContentPathBlank(36729L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
