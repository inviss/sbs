package com.app.das.util;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * 태그를 변환 관련 함수가 정의되어진CLASS
 
 */
public class ConvertBr {

public ConvertBr() {}
/**
 * 태그를 변환한다
 *
 * @param _text
 * @return String
 */
public String convertTag(String _text) {
int length=0;
StringBuffer buffer=new StringBuffer();
String comp=null;

length=_text.length();
for(int i=0;i<length;++i) {
comp=_text.substring(i,i+1);
if("&".compareTo(comp) == 0) { buffer.append("&amp;"); }
else if("\"".compareTo(comp) == 0) { buffer.append("\""); }
else if("\'".compareTo(comp) == 0) { buffer.append("`"); }
/*else if("<".compareTo(comp) == 0) { buffer.append("&lt;"); }
else if(">".compareTo(comp) == 0) { buffer.append("&gt;"); }*/
else { buffer.append(comp); }
}
return buffer.toString();
}


/**
 * 문자열을 변환한다
 *
 * @param str
 * @return String
 */
public String decodeStr(String str) {
	ByteArrayOutputStream out=new ByteArrayOutputStream(str.length());

	for(int i=0;i<str.length();i++) {
		int c=(int)str.charAt(i);
		if(c == '+') {
			out.write(' ');
		}
		else if(c == '%') {
			int c1=Character.digit(str.charAt(++i),16);
			int c2=Character.digit(str.charAt(++i),16);
			out.write((char)(c1*16+c2));
		}
		else {
			out.write(c);
		}
	}
	return out.toString();
}

	/**
	 * ival( val, rep )
	 */
public static int ivl( int val ) { return ivl( "" + val, 0 ); }
/**
 * ival( val, rep )
 */
public  static int ivl( int val, int rep) { return ivl( "" + val, rep ); }
/**
 * ival( val, rep )
 */
public  static int ivl( String val ) {return ivl( val, 0 ); }

/**
 * ival( val, rep )
 */
public  static int ivl( String val, int rep ) {
		if ( val == null ) return rep;
		if ( val.trim().length() <= 0 ) return rep;

		int value = rep;

		try {
			value = Integer.parseInt( val );
		} catch ( NumberFormatException e ) {
			System.out.println("ival(), val=[" + val + "], " + e );
			value = rep;
		}

		return value;
	}

/**
 * inverse( str, key )
 * red로 치환다
 */
public String inverse(String str,String key) {
	return inverse(str,key,"red");
}

 
/**
 * inverse( str, key )
 * 입력받은 컬러로 값을 치환한다
 */
public String inverse(String str,String key,String color) {
	StringBuffer sb=new StringBuffer();
	int j=0;
	boolean flag=true;

	while(flag) {
		if((j=str.indexOf(key)) != -1) {
			if(j > 0)
				sb.append(str.substring(0,j));
			sb.append("<font color=\""+color+"\">");
			sb.append(str.substring(j,j+key.length()));
			sb.append("</font>");
			if(j+key.length() >= str.length())
				break;
			str=str.substring(j+key.length());
		}
		else {
			sb.append(str);
			break;
		}
	}
	return sb.toString();
}
/**
 * inverse( str, key )
 * red로 치환다
 */
public String inverse(String str,String[] keys) {
	return inverse(str,keys,"red");
}

/**
 * inverse( str, key )
 * 입력받은 컬러로 값을 치환한다
 */
public String inverse(String str,String[] keys,String color) {
	
	String str0=str;
	String key=new String();
	int j=0;
	boolean flag=true;

	for(int i=0;i<keys.length;i++) {
		key=keys[i].trim();
		StringBuffer sb=new StringBuffer();
		while(true) {
			if((j=str0.indexOf(key)) != -1) {
				if(j > 0)
					sb.append(str0.substring(0,j));
				sb.append("<font color=\""+color+"\">");
				sb.append(str0.substring(j,j+key.length()));
				sb.append("</font>");
				if(j+key.length() >= str0.length())
					break;
				str0=str0.substring(j+key.length());
			}
			else {
				sb.append(str0);
				break;
			}
		}
		str0=sb.toString();
		sb=null;
	}
	return str0;
}
/**
 * 벡터값을 체크한다
 */
public static boolean checkVector(Vector vec,String word) {
	
	String str0=word;
        if(word.length()>6) { str0=word.substring(0,6); }
	String key=new String();
        boolean sv=false;
	for(int i=0;i<vec.size();i++) {
		key=(String)vec.elementAt(i);
                if(key.length() >6){ key=key.substring(0,6);}
		if(str0.indexOf(key) != -1) {
                     sv=true;
		     break;
		}else {
                     sv= false;
		}
	}
        return sv;
}

public  static String nvl( String val ) { return nvl( val, "" ); }
public  static String nvl( String val, String rep ) {
		return ( val == null ) ? rep : val;
	}

public  static String nvl2( String val, String rep ) {
		if (val == null )	{ return rep; }
		return ( val.trim().length() <= 0 ) ? rep : val;
	}


public  static boolean bvl2( String val) {
              return ( val.equals("Y") ? true : false );
	}
public  static boolean bvl( String val, boolean rep ) {
		if ( val == null ) return rep;
		if ( val.trim().length() <= 0 ) return rep;

		int value;

		try {
			value = Integer.parseInt( val );
		} catch ( NumberFormatException e ) {
			System.out.println("ival(), val=[" + val + "], " + e );
			value = 0;
		}

		
		return ( value == 1 ? true : false );
}


}
