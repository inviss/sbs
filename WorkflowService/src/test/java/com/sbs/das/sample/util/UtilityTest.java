package com.sbs.das.sample.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.sbs.das.commons.utils.UserFilenameFilter;
import com.sbs.das.commons.utils.Utility;

public class UtilityTest {

	@Ignore
	@Test
	public void stringUtils() {
		System.out.println("D00111111".substring(3));
	}

	@Ignore
	@Test
	public void decimalFormat() {
		DecimalFormat format = new DecimalFormat("00,00,00");
		System.out.println(format.format(312).replaceAll("\\,", ":"));
	}

	@Ignore
	@Test
	public void aa() {
		String aa = "11";
		String resName = aa.equals("11") ? "server1" : "server2";
		System.out.println(resName);
	}

	@Ignore
	@Test
	public void userDateFormat() {
		try {
			System.out.println("년월: "+Utility.getTimestamp("yyyyMM"));
			System.out.println("일: "+Utility.getTimestamp("dd"));
			String m4Path = "/mp4/"+Utility.getTimestamp("yyyyMM")+"/"+Utility.getTimestamp("dd")+"/";
			System.out.println("m4Path: "+m4Path);
			System.out.println(Long.valueOf(Utility.getTimestamp("yyyyMMddHHmmssSSSSS")));
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void stringSplite() {
		try {
			String seq1 = "10,20,30";
			System.out.println("split : "+seq1.split("\\,").length);

			String seq2 = "10";
			System.out.println("split : "+seq2.split("\\,").length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void stringBackspace() {
		try {
			String path = "V:\\\\mp2\\";
			if(path.indexOf("\\") > -1) {
				path = path.replaceAll("\\\\", "/");
			}
			System.out.println("backspace : "+path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Ignore
	@Test
	public void bufferWrite() {

		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter("D:\\tmp\\list.txt", true);
			bw = new BufferedWriter(fw);
			for(int i=0; i<10; i++) {
				bw.write("test"+i);
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null) {
					bw.flush();
					bw.close();
				}
				if(fw != null) {
					fw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Ignore
	@Test
	public void getDay() {
		try {
			int d = Integer.valueOf("5").intValue();
			System.out.println(Utility.getDate(-d));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getReplace() {
		try {
			String path = "mp2//restore\\aaaaa\\bbbbb";
			if(path.indexOf("//") > -1)
				path = path.replace("//", "/");
			if(path.indexOf("\\") > -1)
				System.out.println(path.replace("\\", "/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getCalendar() {
		String limitDay = "20120817";
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
		try {
			Date date = formatter.parse(limitDay);

			Calendar calendar = Calendar.getInstance();	
			calendar.setTime(date);

			System.out.println("limitDay: "+formatter.format(calendar.getTime()));

			calendar.add(Calendar.DAY_OF_MONTH, 5);

			System.out.println("limitDay2: "+formatter.format(calendar.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testFileLock() {
		try {
			// Get a file channel for the file
			File file = new File("Y:\\tmp\\aaa.txt");
			FileChannel channel = new RandomAccessFile(file, "rw").getChannel();

			// Use the file channel to create a lock on the file.
			// This method blocks until it can retrieve the lock.
			FileLock lock = channel.lock();

			// Try acquiring the lock without blocking. This method returns
			// null or throws an exception if the file is already locked.
			try {
				lock = channel.tryLock();
				Thread.sleep(10000L);
			} catch (OverlappingFileLockException e) {
				// File is already locked in this thread or virtual machine
				e.printStackTrace();
			}

			// Release the lock
			lock.release();

			// Close the file
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void findDirFiles() {
		String dir = "D:/tmp/A398117/68379";

		try {
			File cartNoDir = new File(dir);
			String[] mxfFiles = cartNoDir.list(new UserFilenameFilter());

			// 하위의 파일이 없을경우 삭제
			if(mxfFiles == null || mxfFiles.length <= 0) {
				try {
					Utility.fileForceDelete(cartNoDir);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getResolution() {
		//20121021202024_180k_HD.mp4
		String filePath = "D:/tmp/kra/742462.mp4";

		try {
			String[] commands = null;
			commands = new String[]{ "cmd", "/c", "D:/tmp/kra/ffmpeg.exe", "-i", filePath };
			Process processor = Runtime.getRuntime().exec(commands);

			String line = null;
			BufferedReader error = new BufferedReader(new InputStreamReader(processor.getErrorStream(), "EUC-KR"));
			while ((line = error.readLine()) != null) {
				if (StringUtils.isNotEmpty(line)) {
					String tmp = line.trim();

					String res = "";
					if (tmp.startsWith("Stream #0.0") && tmp.indexOf("Video:") > -1) {
						res = tmp.substring(tmp.indexOf("Video:")+7).split(",")[2].trim();
					} else if (tmp.startsWith("Stream #0.1") && tmp.indexOf("Video:") > -1) {
						res = tmp.substring(tmp.indexOf("Video:")+7).split(",")[2].trim();
					} else continue;

					if(res.indexOf(" ") > -1) {
						System.out.println(res.split(" ")[0]);
					} else {
						System.out.println(res);
					}
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getDate() {
		try {
			System.out.println(Utility.getDate(-(Integer.valueOf(3)+1)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void getReplaceChars() {
		try {
			String str = "djdg$#$#';(*,%$&^%*^&)*&^[$@-_#$!@$!@]$!{@#%$#^%$^}*&^(*^(-~$^#$^DSDF백두산이";
			//String str = "123%$`한글";
			str = StringUtils.replaceChars(str, "+=!@#$%^&*|\\;:'\"<>,.?/~`", "_");

			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void getTitleName() {
		try {
			String str = URLDecoder.decode("%eb%84%a4%ed%8a%b8%ec%9b%8c%ed%81%ac%ed%98%84%ec%9e%a5!%ea%b3%a0%ed%96%a5%ec%9d%b4%eb%b3%b4%ec%9d%b8%eb%8b%a4(1%ec%9b%9429%ec%9d%bc)_%ed%9b%84%ed%83%80%ec%9d%b4%ed%8b%80.mxf", "utf-8");
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void ristClfTest() {
		try {
			Calendar aDate = Calendar.getInstance();
			
			String ristTerm = "1-18";
			boolean forceRistClf = false;
			if(StringUtils.isNotBlank(ristTerm)) {
				String[] terms = null;
				if(ristTerm.indexOf(",") > -1) {
					terms = ristTerm.split("\\,");
				} else {
					terms = new String[1];
					terms[0] = ristTerm;
				}

				for(String term : terms) {
					String[] hours = term.split("\\-");
					if(aDate.get(Calendar.HOUR_OF_DAY) >= Integer.valueOf(hours[0]) 
							&& aDate.get(Calendar.HOUR_OF_DAY) <= Integer.valueOf(hours[1])) {
						forceRistClf = true;
						break;
					}
				}
			}
			
			System.out.println(forceRistClf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
