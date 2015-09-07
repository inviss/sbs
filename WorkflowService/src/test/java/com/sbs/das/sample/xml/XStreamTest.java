package com.sbs.das.sample.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.das.commons.system.DivaConnectSerivce;
import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.xml.ArchiveError;
import com.sbs.das.dto.xml.ArchiveResponse;
import com.sbs.das.dto.xml.ArchiveStatus;
import com.sbs.das.dto.xml.ContentMap;
import com.sbs.das.dto.xml.CpuInfo;
import com.sbs.das.dto.xml.DBTable;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.dto.xml.MemoryInfo;
import com.sbs.das.dto.xml.Preview;
import com.sbs.das.dto.xml.PreviewFile;
import com.sbs.das.dto.xml.Program;
import com.sbs.das.dto.xml.RequestInfo;
import com.sbs.das.dto.xml.ServerResource;
import com.sbs.das.dto.xml.StorageInfo;
import com.sbs.das.dto.xml.WorkLog;
import com.sbs.das.repository.ContentInstMetaDao;
import com.sbs.das.sample.BaseConfig;
import com.sbs.das.web.Nevigator;

public class XStreamTest extends BaseConfig{

	private static Log logger = LogFactory.getLog(XStreamTest.class);

	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private DivaConnectSerivce divaConnector;
	@Autowired
	private ContentInstMetaDao contentInstMetaDao;
	@Autowired
	private JaxWsProxyFactoryBean jaxWsProxyFactoryBean;

	@Ignore
	@Test
	public void xmlWorkflow() {
		try {
			Das das = new Das();

			RequestInfo info = new RequestInfo();
			info.setDasEqId(5);
			info.setDasEqPsCd("001");
			info.setRegrid("aaaaaa");
			das.setInfo(info);

			DBTable dbTable = new DBTable();

			Program program = new Program();
			program.setPdsProgramId("P001");
			program.setPgmId(100L);
			program.setPgmNm("프로그램명- #@$@#%$#^$%&^%&%^&%^&%^-----아헿헿");
			dbTable.setProgram(program);

			WorkLog workLog = new WorkLog();
			workLog.setDasEqNm("ㅋㅋㅋㅋㅋㅋ");
			workLog.setRegDt("20110110");
			dbTable.setWorkLog(workLog);

			das.setDbTable(dbTable);

			logger.debug(xmlStream.toXML(das));

			Das das2 = (Das)xmlStream.fromXML(xmlStream.toXML(das));
			RequestInfo info2 = das2.getInfo();
			logger.debug("pgm_nm===>"+das2.getDbTable().getProgram().getPgmNm());
			logger.debug(info2.getDasEqId());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Ignore
	@Test
	public void xmlArchive() {
		try {
			Das das = new Das();

			ArchiveStatus status = new ArchiveStatus();
			status.setJobId("005");
			status.setJobStatus("W");
			status.setObjectName("DAS001");
			das.addStatus(status);

			status = new ArchiveStatus();
			status.setJobId("005");
			status.setJobStatus("W");
			status.setObjectName("DAS002");
			das.addStatus(status);

			status = new ArchiveStatus();
			status.setJobId("005");
			status.setJobStatus("W");
			status.setObjectName("DAS002");
			das.addStatus(status);

			System.out.println(xmlStream.toXML(das));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void responseXML() {
		try {
			ArchiveResponse response = (ArchiveResponse)xmlStream.fromXML("<response><task_id>180</task_id><object_name>DAS1000024</object_name></response>");
			System.out.println("error = "+response.getArchiveError());
			ArchiveError archiveError = response.getArchiveError();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void filePathParsing() {
		try {
			String path = "\\restore\\D080009\\7445";
			if(path.indexOf("\\") > -1) {
				path = path.replaceAll("\\\\", "/");
			}
			System.out.println(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void masterXML() {
		try {
			String xml = "<das><db_table><master><title>&lt;비타민&amp;진짜고향의맛&quot;&gt;44446666</title></master></db_table></das>";
			//Das das = (Das)xmlStream.fromXML(xml);
			//System.out.println(das.getDbTable().getMaster().getTitle());
			System.out.println(Utility.unescapeXml(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void arrayStatus() {

		try {
			xmlStream.setAnnotationAlias(Preview.class);
			xmlStream.setAnnotationAlias(PreviewFile.class);

			Preview preview = new Preview();

			PreviewFile file = new PreviewFile();
			file.setPath("/aaa/bbb");
			file.setName("hello1");
			preview.addPreviews(file);

			file = new PreviewFile();
			file.setPath("/aaa/bbb");
			file.setName("hello2");
			preview.addPreviews(file);

			file = new PreviewFile();
			file.setPath("/aaa/bbb");
			file.setName("hello3");
			preview.addPreviews(file);

			logger.debug(xmlStream.toXML(preview));

			String xml = xmlStream.toXML(preview);

			Preview preview2 = (Preview)xmlStream.fromXML(xml);
			List<PreviewFile> files = preview2.getPreviews();

			for(PreviewFile previewFile : files) {
				logger.debug(previewFile.getPath());
				logger.debug(previewFile.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void arrayResources() {

		try {
			xmlStream.setAnnotationAlias(Das.class);
			xmlStream.setAnnotationAlias(ServerResource.class);
			xmlStream.setAnnotationAlias(StorageInfo.class);

			Das das = new Das();

			ServerResource serverResource = new ServerResource();
			CpuInfo cpuInfo = new CpuInfo();
			cpuInfo.setFree(30.02);
			cpuInfo.setUse(58.80);
			serverResource.setCpuInfo(cpuInfo);

			MemoryInfo memoryInfo = new MemoryInfo();
			memoryInfo.setFree(60.00);
			memoryInfo.setUse(40.00);
			serverResource.setMemoryInfo(memoryInfo);

			das.addResources(serverResource);

			logger.debug(xmlStream.toXML(das));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void archiveRequest2() {
		try {
			xmlStream.setAnnotationAlias(Das.class);
			xmlStream.setAnnotationAlias(RequestInfo.class);
			
			Das das = new Das();
			RequestInfo info = new RequestInfo();
			info.setDasEqClfCd("3");
			info.setDasEqClfCd("005");
			info.setArchiveType("all");
			info.setDtlType("das");
			info.setCtiId(779806L);
			info.setPriority("6");
			info.setFilePath("/nearline/SBS/manual/201212/12/779806.mxf");
			das.setInfo(info);
			
			DBTable dbTable = new DBTable();
			ContentMap contentMap = new ContentMap();
			contentMap.setCtId(439648L);
			contentMap.setMasterId(97402L);
			dbTable.setContentMap(contentMap);
			
			das.setDbTable(dbTable);
			
			String xml = xmlStream.toXML(das);
			System.out.println(xml);
			
			Nevigator navigator = (Nevigator)jaxWsProxyFactoryBean.create();
			navigator.archiveService(xml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Ignore
	@Test
	public void archiveRequest() {

		try {
			xmlStream.setAnnotationAlias(Das.class);
			xmlStream.setAnnotationAlias(RequestInfo.class);

			Das das = new Das();

			RequestInfo info = new RequestInfo();
			info.setArchiveType("mxf");
			info.setDtlType("das");

			File f = new File("Z:/temp");
			File[] xmlFiles = f.listFiles(new UserFileFilter());
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ctiFmt", "105");
			
			for(File fx : xmlFiles) {
				
				das.setInfo(null);
				
				String xml = FileUtils.readFileToString(fx, "utf-8");
				Das das2 = (Das)xmlStream.fromXML(xml);
				Long ctId = das2.getDbTable().getContent().getCtId();
				logger.debug("ct_id=====>"+ctId);
				params.put("ctId", ctId);
				ContentInstTbl contentInstTbl = contentInstMetaDao.getContentInst(params);
				logger.debug("cti_id =====>"+contentInstTbl.getCtiId());
				info.setCtiId(contentInstTbl.getCtiId());
				
				info.setFilePath(contentInstTbl.getFlPath()+"/"+contentInstTbl.getCtiId()+".mxf");
				
				if(info.getFilePath().indexOf("\\") > -1) info.setFilePath(info.getFilePath().replaceAll("\\\\", "/"));
				if(!info.getFilePath().startsWith("/")) info.setFilePath("/"+info.getFilePath().replace("//", "/").trim());
				
				info.setVdQlty("001");
				//logger.debug(xmlStream.toXML(das));
				das.setInfo(info);
				
				FileUtils.writeStringToFile(new File("D:/tmp/archive/"+contentInstTbl.getCtiId()+".xml"), xmlStream.toXML(das), "utf-8");
			}
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


	@Ignore
	@Test
	public void testExcel2Read(){
		Workbook w;
		try {
			// success.txt 내용을 읽어서 id를 임시로 저장
			List<String> s = new ArrayList<String>();
			// fail.txt 내용을 읽어서 id를 임시로 저장
			List<String> f = new ArrayList<String>();

			File success = new File("D:\\success.txt");
			File fail = new File("D:\\fail.txt");

			if(success.exists() && success.length() > 0) {
				BufferedReader in = new BufferedReader(new FileReader(success));
				String clip = "";
				while((clip = in.readLine()) != null) {
					s.add(clip);
				}
			}

			if(success.exists() && success.length() > 0) {
				BufferedReader in = new BufferedReader(new FileReader(success));
				String clip = "";
				while((clip = in.readLine()) != null) {
					f.add(clip);
				}
			}

			RandomAccessFile raf1 = new RandomAccessFile(success, "rw");
			RandomAccessFile raf2 = new RandomAccessFile(fail, "rw");

			//File inputWorkbook = new File("D:\\tmp\\nick.xls");
			File inputWorkbook = new File("D:\\tmp\\rms.xls");

			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet[] sheets = w.getSheets();

			for(Sheet sheet : sheets) {
				if("KBS미디어".equals(sheet.getName())) {
					for (int j = 1; j < sheet.getRows(); j++) {

						// id가 success list에 존재한다면 skip
						if(j < 3) continue;

						String id = "";
						try {
							for (int i = 0; i < sheet.getColumns(); i++) {

								Cell cell = sheet.getCell(i, j);

								/*
								 * 필요할 경우 bean을 하나 생성해서 컬럼별로 setter에 주입
								 * 아마도 metadata, content, content_inst 등.... 필요에 맞게 생성해야할듯..
								 */
								switch((i+1)) {
								case 1:
									id = cell.getContents();
									// bean.setId(id)
									break;
								case 2:
									// 소스구분 (비디오, 오디오)
									// bean.setAvGubun(V)
									break;
								}
							}

							// 동일한 ID가 있다면 pass
							if(s.contains(id)) continue;

							/*
							 * 여기에서 bean에 취합된 정보를 DB에 등록하고 파일도 이동시킴
							 */

							// bean에 주입된 데이타를 DB에 저장
							// 등록이 성공하면 success에 add, 실패하면 fail에 add
							// 엑셀 자료에 중복 자료가 있을지 모르므로 성공 ID값을 List에 add
							s.add(id);
							raf1.seek(success.length());
							raf1.writeBytes(id+"\r\n");
						} catch (Exception e) {
							e.printStackTrace();
							raf2.seek(fail.length());
							raf2.writeBytes(id+"\r\n");
						} finally {
							if(j > 5) break;
						}

					}
				}
			}

			if(raf1 != null) raf1.close();
			if(raf2 != null) raf2.close();

			// 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void downloadXmlTest() {
		try {
			String xml = FileUtils.readFileToString(new File("D:/workflow.xml"), "utf-8");
			Das das = (Das)xmlStream.fromXML(xml);
			List<ArchiveStatus> status = das.getStatus();
			for(ArchiveStatus archiveStatus : status) {
				if(archiveStatus.getJobId().equals("007")) {
					if(logger.isDebugEnabled()) {
						logger.debug("restore_obj_name : "+archiveStatus.getObjectName());
						logger.debug("restore_restore_id : "+archiveStatus.getRestoreId());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
