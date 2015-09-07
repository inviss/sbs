package com.konantech.search.module;

/** Docruzer 에러메세지 캡쳐용 Exception Class.
 * @since 2010.04.01
 * @version 1.0
 */

public class KonanException extends Exception {
	
	public KonanException () {
		super();
	}
						
	public KonanException (String msg) {
		super(msg);
	}
}