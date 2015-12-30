package kr.co.d2net.main;

import java.util.Locale;

public class StorageCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "/mp2//nearline/SBS/onAir/201512/23/1031323.mxf";
		String filepath = "";
		if (path.indexOf("mp2") > -1) {
			System.out.println("mp2");
			if(path.indexOf("nearline") > -1) {
				path = path.replace("/nearline", "");
			}
			filepath = (new StringBuilder("")).append(path.replace("/mp2/", "X:"+"/").trim()).toString();
		} else if (path.indexOf("arcreq") > -1) {
			filepath = (new StringBuilder("")).append(path.replace("/arcreq/", "X:"+"/").trim()).toString();
		} else if (path.indexOf("nearline") > -1) {
			System.out.println("nearline");
			filepath = (new StringBuilder("")).append(path.replace("/nearline/", "X:"+"/").trim()).toString();
		}
		
		System.out.println(filepath);
	}

}
