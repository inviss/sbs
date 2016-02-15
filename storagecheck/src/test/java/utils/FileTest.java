package utils;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class FileTest {

	@Ignore
	@Test
	public void filePathTest() {
		String ddDir = "\\\\10.150.11.11\\SBS\\201602\\15";
		String mxf = "12345.mxf";
		String flpath = ddDir + File.separator + mxf;
		flpath = flpath.replaceAll("\\\\", "/").substring(flpath.indexOf("SBS"));
		flpath = (flpath.startsWith("/")) ? "/nearline"+flpath : "/nearline/"+flpath;
		
		System.out.println(flpath);
	}
	
	@Test
	public void stringTest() {
		StringBuffer xml = new StringBuffer();
		xml.append("<hello><ok>aaaa</ok></hello>");
		
		System.out.println(xml.indexOf("bbbb"));
	}
}
