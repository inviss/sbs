package com.sbs.das.commons.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * <pre>
 * 파일 목록을 String으로 반환할때 지정된 확장자만 반환한다.
 * </pre>
 * @author M.S. Kang
 *
 */
public class UserFilenameFilter implements FilenameFilter {

	private final String[] useFileExtensions = new String[] {"mxf", "MXF"};
	
	public boolean accept(File dir, String name) {
		for (String extension : useFileExtensions) {
			if (name.toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

}
