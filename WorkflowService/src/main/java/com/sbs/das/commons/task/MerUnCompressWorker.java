package com.sbs.das.commons.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbs.das.commons.utils.BitConverter;
import com.sbs.das.commons.utils.ImageFileFilter;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.MerHistTbl;
import com.sbs.das.services.MerUnCompressService;

@Component("merUnCompressJob")
public class MerUnCompressWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MerUnCompressService merUnCompressService;

	public void work() {
		FileInputStream fis = null;
		try {

			byte[] b = new byte[4];
			int imgCount = 0;
			BufferedWriter bw = null;

			List<MerHistTbl> merHistTbls = merUnCompressService.findNewJobs();

			for(MerHistTbl merHistTbl : merHistTbls) {
				try {
					if(StringUtils.isNotBlank(merHistTbl.getKfrmPath())) {
						merHistTbl.setKfrmPath(merHistTbl.getKfrmPath().replaceAll("\\\\", "/"));
						if(!merHistTbl.getKfrmPath().startsWith("/")) merHistTbl.setKfrmPath("/"+merHistTbl.getKfrmPath());
						if(!merHistTbl.getKfrmPath().endsWith("/")) merHistTbl.setKfrmPath(merHistTbl.getKfrmPath()+"/");

						File mer = new File(merHistTbl.getKfrmPath()+merHistTbl.getCtId()+".mer");
						if(mer.exists()) {
							File kfrm = new File(merHistTbl.getKfrmPath());
							File[] kfrms = kfrm.listFiles(new ImageFileFilter());
							if(kfrms.length > 0) {
								Utility.fileForceDelete(kfrms);

								logger.debug(kfrm.getAbsolutePath()+" "+kfrms.length+" images deleted!");
							}

							File txtFile = new File(mer.getParent()+File.separator+merHistTbl.getCtId()+".txt");
							if(txtFile.exists()) txtFile.delete();

							FileWriter fw = new FileWriter(mer.getParent()+File.separator+merHistTbl.getCtId()+".txt", true);
							bw = new BufferedWriter(fw);
							try {
								fis = new FileInputStream(mer);

								// version
								fis.read(b);

								// Image Count
								fis.read(b);
								imgCount = BitConverter.toInt(b, -1);

								byte[] image = null;
								for(int i=0; i<imgCount; i++) {
									// frame num
									fis.read(b);
									int num = BitConverter.toInt(b, -1);

									// image size
									fis.read(b);
									int leng = BitConverter.toInt(b, -1);
									image = new byte[leng];
									fis.read(image);

									if(i == (imgCount-1))
										bw.write(num+"");
									else
										bw.write(num+",");

									Utility.byte2Image(kfrm.getAbsolutePath()+File.separator+num+".jpg", image);
									
									image = null;
								}
								merHistTbl.setStatus("C");
								merUnCompressService.updateMerJob(merHistTbl);
							} catch (Exception e) {
								logger.error("mer unCompress Error", e);
								merHistTbl.setStatus("E");
								merHistTbl.setErrCd("003");
								merUnCompressService.updateMerJob(merHistTbl);
							} finally {
								if(bw != null) {
									for(int i=0; i<3; i++) {
										try {
											bw.flush();

											break;
										} catch (Exception e2) {
											i++;
											Thread.sleep(1000L);
										}
										if(i>=3) {
											logger.error("txt rewrite("+i+") Error");
											merHistTbl.setStatus("E");
											merHistTbl.setErrCd("002");
											merUnCompressService.updateMerJob(merHistTbl);
										}
									}
									bw.close();
									bw = null;
								}
								if(fw != null) {
									fw.close();
									fw = null;
								}
								if(fis != null) {
									fis.close();
									fis = null;
								}
							}

							logger.debug(mer.getAbsolutePath()+" "+imgCount+" unCompressed!");
						}else{
							merHistTbl.setStatus("E");
							merHistTbl.setErrCd("001");
							merUnCompressService.updateMerJob(merHistTbl);
						}

					} else {
						continue;
					}
				} catch (Exception e) {
					logger.error("mer schedule job error", e);
				}
			}
		} catch (Exception e) {
			logger.error("mer jobs find error", e);
		}
	}

	boolean isNumeric(String s) {
		java.util.regex.Pattern pattern = Pattern.compile("[+-]?\\d+"); 
		return pattern.matcher(s).matches(); 
	}

}
