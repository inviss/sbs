package com.sbs.das.commons.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * <pre>
 * 파일 목록을 반환할때 지정된 확장자의 파일만 반환한다.
 * </pre>
 * @author M.S. Kang
 *
 */
public class UserFileFilter implements FileFilter {

	private final String[] useFileExtensions = new String[] {"mxf", "MXF"};

	public boolean accept(File file) {
		for (String extension : useFileExtensions) {
			if (file.getName().toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

}
