package com.konantech.search.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


/** Property 제공을 위한 클래스. 
 * 
 * @author KONAN Technology
 * @since 2010.01.21
 * @version 1.0
 */
public class DCConfig {

	private static final DCConfig instance = new DCConfig();

	private	ResourceBundle cfg = null;

	/* Constructor */
	private DCConfig () {

		String configPath = "search";	

		try {
			cfg = ResourceBundle.getBundle( configPath );
		} catch(MissingResourceException ex) {
			ex.printStackTrace();
		}
	}
	
	/** 
	 * DOCRUZER property 반환
	 *
	 * @param key property의 key
	 * @return key's value or null
	 */
	public static String getProperty( String key ) {

		String value = null;

		try {
			value = instance.cfg.getString( key );
		}catch (MissingResourceException ex) {
			ex.printStackTrace();
			value = null;
		}

		return value;
	}
	
	/** 
	 * DOCRUZER property 반환
	 *
	 * @param key property의 key
	 * @return key's value or null
	 */
	public static int getIntProperty (String key) {
		int value = 0;
		
		try {
			value = Integer.parseInt(instance.cfg.getString( key ));
		}catch (MissingResourceException ex) {
			ex.printStackTrace();
			value = 0;
		}
		
		return value;
	}
	
	/* for debug */
	/*
	public static void main(String[] args){
		System.out.println(getProperty("SERVER_IP"));
	}
	*/
}

