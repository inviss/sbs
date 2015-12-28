package com.app.das.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import com.app.das.business.dao.CodeInfoDAO;
/**
 * 각종 유틸 함수가 모여있는 CLASS
 *
 */
public class jutil {

	private Logger logger = Logger.getLogger(CodeInfoDAO.class);

	public jutil() {
	}

	public boolean isFileCheck(String path, String fn) throws Exception {

		try {
			File file = new File(path, fn);
			if (file.exists() && file.isFile()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new Exception("isFileCheck():" + e.getMessage());
		}
	}

	private boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			File f;

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					f = new File(files[i] + "/");
					String[] fList = f.list();
					if (fList.length > 0) {
						for (int j = 0; j < fList.length; j++) {

							f = new File(files[i] + "/" + fList[j]);
							f.delete();
							if(logger.isDebugEnabled()) {
								logger.debug("delete file: "+fList[j]);
							}
						}
					}
					deleteDirectory(files[i]);
				} else {
					if(logger.isDebugEnabled()) {
						logger.debug("delete file: "+files[i]);
					}
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	public int deleteAdapter(File path) {

		boolean exist = path.exists();
		boolean result;
		if (exist == false) {
			return 1;
		} else {
			result = deleteDirectory(path);
		}
		if (result) {
			return 0;
		} else {
			return 3;
		}
	}

	public boolean makeFile(String contents, String filename) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename + ".xml_tmp"), "UTF8"));

			bw.write(contents);
			bw.flush();
			bw.close();

			renameFile(filename+".xml_tmp", filename+".xml");
		} catch (IOException ie) {
			logger.error(ie);
			return false;
		} finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {}
			}
		}
		
		return true;
	}

	public boolean makeFile2(String contents, String filename) {
		FileOutputStream headerFileOutputStream = null;
		OutputStreamWriter headerOutputStreamWriter = null;
		try {
			headerFileOutputStream = new FileOutputStream(filename + ".xml_tmp");
			headerOutputStreamWriter = new OutputStreamWriter(headerFileOutputStream, "UTF-8");
			headerOutputStreamWriter.flush();
			headerOutputStreamWriter.close();

			renameFile(filename+".xml_tmp", filename+".xml");
		} catch (Exception ex) {
			logger.error(ex);
			return false;

		} finally {
			try {
				if(headerOutputStreamWriter != null) headerOutputStreamWriter.close();
				if(headerFileOutputStream != null) headerFileOutputStream.close();
			} catch (Exception e) {}
		}
		return true;

	}

	public boolean makeFile3(String contents, String filename, String filepath) {

		FileOutputStream headerFileOutputStream = null;
		try {
			File path = new File(filepath);
			path.mkdirs();
			headerFileOutputStream = new FileOutputStream(
					filepath+"/"+filename + ".xml_tmp");

			OutputStreamWriter headerOutputStreamWriter = new OutputStreamWriter(
					headerFileOutputStream, "UTF-8");
			headerOutputStreamWriter.write(contents);
			headerOutputStreamWriter.flush();
			headerOutputStreamWriter.close();

			renameFile(	filepath+"/"+filename+".xml_tmp", 	filepath+"/"+filename+".xml");
		} catch (Exception ex) {
			logger.error(ex);
			return false;
		} finally {
			if(headerFileOutputStream != null) {
				try {
					headerFileOutputStream.close();
				} catch (Exception e) {}
			}
		}
		return true;

	}

	public void renameFile(String file, String toFile) {

		File toBeRenamed = new File(file);

		if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
			if(logger.isInfoEnabled()) {
				logger.info("File does not exist: " + file);
			}
			return;
		}

		File newFile = new File(toFile);

		//Rename
		if (toBeRenamed.renameTo(newFile)) {
			if(!SystemUtils.IS_OS_WINDOWS) {
				try {
					Runtime runtime = Runtime.getRuntime();
					Process p = runtime.exec("chmod 777 "+newFile.getAbsolutePath());
					p.waitFor();
				} catch (Exception e) {
					logger.error("runtime exec error", e);
				}
			}
			logger.debug("File has been renamed.");
		} else {
			logger.error("File rename error : org_name: "+toBeRenamed.getAbsolutePath()+", new_name: "+newFile.getAbsolutePath());
		}



	}


	public void moveFile(String file) {
		String path = "/mp4/preview_notes/";
		File copyfile = new File(file);
		String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

		if (!copyfile.exists() || copyfile.isDirectory()) {
			if(logger.isInfoEnabled()) {
				logger.info("File does not exist: " + file);
			}
			return;
		}
		File saveFile = new File(dateTime);
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		saveFile.mkdirs();
		try {
			inputStream = new FileInputStream(path+"/"+copyfile);
			outputStream = new FileOutputStream(path+"/"+saveFile+"/"+copyfile);

			byte[] buf = new byte[2048];
			int len;

			while ((len=inputStream.read(buf)) > 0 ){
				outputStream.write(buf, 0, len);
			}
			inputStream.close();
			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				if(outputStream != null) outputStream.close();
				if(inputStream != null) inputStream.close();
			} catch (Exception e2) {}
		}

	}



	public String readFile(String file) {
		String path = "/mp4/preview_notes/";
		File orgfile = new File(file);

		if (!orgfile.exists() || orgfile.isDirectory()) {

			if(logger.isInfoEnabled()) {
				logger.info("File does not exist: " + file);
			}
			return "";
		}

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(path+orgfile));
			String cont;
			String cont2="";
			while ((cont = in.readLine()) != null){
				cont2+=cont;
			}
			in.close();
			return cont2;
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				if(in != null) in.close();
			} catch (Exception e2) {}
		}

		return "";


	}


}
