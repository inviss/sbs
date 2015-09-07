package com.app.das.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * String 조작과 관련된 helper function들입니다.
 *
 * @version	1.0
 */
public final class StringUtils
{

	/** 좌측을 나타낸다 */
	public static final int LEFT = 1;

	/** 가운데를 나타낸다 */
	public static final int CENTER = 2;

	/** 우측을 나타낸다 */
	public static final int RIGHT = 3;

	/** 외부에서 생성 불가능 */
	private StringUtils() { }


	// ------------------------------------------------------------ base

	/**
	 * 문자열이 같은 지를 체크한다.
	 *
	 * <blockquote><pre>
	 * boolean b = StringUtil.equals(null, null); // true
	 * </pre></blockquote>
	 *
	 * @param str1	frist String
	 * @param str2	second String
	 * @return 만약 문자열이 같다면 <code>true</code>를 리턴하고 다르면
	 *			<code>false</code>를 리턴한다. 양쪽 모두 null이라면
	 * 			<code>false</code>로 판단한다.
	 */
	public static boolean equals(String str1, String str2)
	{
		return (null == str1 ? null == str2 : str1.equals(str2));
	}
			
	/**
	 * 문자열이 빈 문자열인지를 체크한다.
	 *
	 * <blockquote><pre>
	 * boolean b = StringUtil.isEmpty("  "); // true
	 * String str = null;
	 * beolean b1 = StringUtil.isEmpty(str); // true
	 * </pre></blockquote>
	 *
	 * @param str 체크하고자 하는 문자열을 나타낸다
	 * @return 문자열이 비어있으면 <code>true</code>를 리턴하고 비어있지 않다면
	 *			<code>false</code>를 리턴한다. 문자열이 <code>null</code>이거나
	 * 			trim시에 길이가 <code>0</code>이라면 빈 문자열로 간주한다.
	 */
	public static boolean isEmpty(String str)
	{
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * 문자열의 길이가 1이상이면 true이다. 이때 문자열은
	 * java.lang.String.trim()되지 않은 길이로 판단된다.
	 *
	 * <blockquote><pre>
	 * boolean b = StringUtil.isValid("  "); // true
	 * String str = null;
	 * beolean b1 = StringUtil.isEmpty(str); // false
	 * </pre></blockquote>
	 *
	 * @see	#isValid(String, int)
	 */
	public static boolean isValid(String str)
	{
		return isValid(str, 1);
	}

	/**
	 * 문자열이 특정 길이보다 크면 true를 리턴한다. 이때 문자열은
	 * java.String.trim()되지 않은 길이로 판단된다.
	 *
	 * @param str 체크하고자 하는 문자열을 나타낸다.
	 * @param len 체크하고자 하는 길이를 나타낸다.
	 * @return 문자열이 비어있으면 <code>true</code>를 리턴하고 비어있지 않다면
	 *			<code>false</code>를 리턴한다. 문자열이 <code>null</code>이면
	 *			<code>false</code>를 리턴한다
	 */
	public static boolean isValid(String str, int len)
	{
		return (str != null && str.length() >= len);
	}

	/**
	 * 유니코드 단어인지를 나타낸다.
	 *
	 * @return 단어라면 <code>true</code>를 리턴하고 아니라면
	 *			<code>false</code>를 리턴한다. 문자열이 <code>null</code>이면
	 *			<code>false</code>를 리턴한다
	 */
	public static boolean isWord(String str)
	{
		if (null == str)
			return false;
		int len = str.length();
		for (int i = 0; i < len; i++)
		{
			if (!Character.isLetter(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 유니코드 숫자나 문자인지를 나타낸다.
	 *
	 * @return 숫자나 문자라면 <code>true</code>를 리턴하고 아니라면
	 *			<code>false</code>를 리턴한다. 문자열이 <code>null</code>이면
	 *			<code>false</code>를 리턴한다
	 */
	public static boolean isAlphanumeric(String str)
	{
		if (null == str)
			return false;
		int len = str.length();
		for (int i = 0; i < len; i++)
		{
			if (!Character.isLetterOrDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 유니코드 숫자인지를 나타낸다.
	 *
	 * @return 숫자라면 <code>true</code>를 리턴하고 아니라면
	 *			<code>false</code>를 리턴한다. 문자열이 <code>null</code>이면
	 *			<code>false</code>를 리턴한다
	 */
	public static boolean isNumeric(String str)
	{
		if (null == str)
			return false;
		int len = str.length();
		for (int i = 0; i < len; i++)
		{
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * reqular expression의 meta sybmol를 quotation처리한다. 문자열이 null이면
	 * null를 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.quoteRegularExpression("^a[bc]");
	 * //output --> "\^a\[bc\]"
	 * </pre></blockquote>
	 */
	public static String quoteRegularExpression(String str)
	{
		if (null == str)
			return null;
		char[] chars = str.toCharArray();
		int len = chars.length;
		StringBuffer sb = new StringBuffer(2 * len);
		for (int i = 0; i < len; i++)
		{
			switch(chars[i])
			{
			case '[' :
			case ']' :
			case '?' :
			case '+' :
			case '*' :
			case '/' :
			case '.' :
			case '^' :
			case '$' :
				sb.append("\\");
			default :
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	// ------------------------------------------------------------ count
	public static int getWordCount(String str)
	{
		StringTokenizer tokenizer = new StringTokenizer(str, " ");
		return tokenizer.countTokens();
	}

	public static int getWordCount(String str, String delim)
	{
		StringTokenizer tokenizer = new StringTokenizer(str, delim);
		return tokenizer.countTokens();
	}

	// -------------------------------------------------------------- padding

	/**
	 * java.lang.String.trim()이 문자열이 <code>null</code>인 경우
	 * <code>NullPointException</code> 던지지만 이 메소드는
	 * <codE>null</code>를 리턴한다
	 *
	 * @see #clean(String str)
	 */
	public static String trim(String str)
	{
		return trim(str, null);
	}

	/**
	 * java.lang.String.trim()과 같이 공백을 제거해주고 문자열이 null인
	 * 경우는 빈 문자열을 리턴한다.
	 */
	public static String clean(String str)
	{
		return trim(str, "");
	}

	/**
	 * 문자열의 공백을 제거한다. 문자열이 null이라면 default value를
	 * 리턴한다.
	 *
	 * @param str	trim하고자 하는 string을 나타낸다
	 * @param def	default value를 나타낸다
	 */
	public static String trim(String str, String def)
	{
		return (null == str ? def : str.trim());
	}

	/**
	 * 만약 string이 null이라면 빈문자열을 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.parseString(null);
	 * // output --> ""
	 * </pre></blockquote>
	 *
	 * @see #parseString(String, String)
	 */
	public static String parseString(String str)
	{
		return parseString(str, "");
	}

	/**
	 * 만약 String이 null이라면 default value를 리턴한다
	 */
	public static String parseString(String str, String defaultValue)
	{
		return (str == null ? defaultValue : str);
	}

	/**
	 * 앞쪽의 공백을 제거한다. 만약 문자열이 null이라면 null를 리턴한다.
	 *
	 * @see #stripStart(String, String)
	 */
	public static String ltrim(String str)
	{
		return stripStart(str, null);
	}

	/**
	 * 뒤쪽의 공백을 제거한다. 만약 문자열이 null이라면 null를 리턴한다.
	 *
	 * @see #stripEnd(String, String)
	 */
	public static String rtrim(String str)
	{
		return stripEnd(str, null);
	}

	/**
	 * padding를 나타낸다.
	 *
	 * @param direction	왼쪽, 오른쪽, 가운데를 나타낸다. 값은 LEFT, CENTER, RIGHT
	 *					방향이 틀리면 단순히 string을 리턴한다
	 * @see	#left(String, int, String)
	 * @see	#center(String, int, String)
	 * @see	#right(String, int, String)
	 */
	public static String padding(String str, int n, String delim,	int direction)
	{
		if (direction == LEFT)
			return left(str, n, delim);
		else if (direction == CENTER)
			return center(str, n, delim);
		else if (direction == RIGHT)
			return right(str, n, delim);

		return str;
	}

	/**
	 * 특정 길이보다 문자열의 길이가 작다면 좌측에 공백을 넣어준다.
	 *
	 * @see #left(String, int, String)
	 */
	public static String left(String str, int n)
	{
		return left(str, n, ' ');
	}

	/**
	 * 문자열이 특정 길이보다 작다면 좌측에 delimiter 문자를 삽입해 준다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.left("abcd", 6, ' '); // output --> "  abcd"
	 * </pre></blockquote>
	 *
	 * @see	#left(String, int, String)
	 */
	public static String left(String str, int n, char delim)
	{
		return left(str, n, delim + "");
	}

	/**
	 * 문자열이 특정 길이보다 작다면 좌측에 delimiter를 삽입해 준다. 문자열이
	 * null이라면 delimiter로 모두 채운 후에 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = Stringutil.left("1234", 7, "%&");
	 * // output --> "%&1234"
	 * </pre></blockquote>
	 */
	public static String left(String str, int n, String delim)
	{
		if (null == str) str = "";
		int len = str.length();
		n = (n - len) / delim.length();
		if (n > 0)
		{
			StringBuffer sb = new StringBuffer(len + n);
			repeat(sb, delim, n);
			sb.append(str);
			return sb.toString();
		}
		else
		{
			return str;
		}
	}

	/**
	 * 특정 길이보다 string의 길이가 작다면 우측에 공백을 넣어준다.
	 *
	 * @see #left(String, int, String)
	 */
	public static String right(String str, int n)
	{
		return right(str, n, ' ');
	}

	/**
	 * 문자열이 특정 길이보다 작다면 우측에 delimiter 문자를 넣어준다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.right("abcd", 6, ' '); // output --> "abcd  "
	 * </pre></blockquote>
	 *
	 * @see	#right(String, int, String)
	 */
	public static String right(String str, int n, char delim)
	{
		return right(str, n, delim + "");
	}

	/**
	 * 문자열이 특정 길이보다 작다면 우측에 delimiter 문자를 넣어준다.
	 * 문자열이 null이라면 delimiter로 모두 채운 후에 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = Stringutil.right("1234", 7, "%&");
	 * // output --> "1234%&"
	 * </pre></blockquote>
	 */
	public static String right(String str, int n, String delim)
	{
		if (null == str)
			str = "";
		int len = str.length();
		n = (n - str.length()) / delim.length();
		if (n > 0)
		{
			StringBuffer sb = new StringBuffer(n + len);
			sb.append(str);
			repeat(sb, delim, n);
			return sb.toString();
		}
		else
		{
			return str;
		}
	}

	/**
	 * 특정 길이보다 string의 길이가 작다면 좌우에 공백을 넣어준다.
	 *
	 * @see #left(String, int, String)
	 */
	public static String center(String str, int n)
	{
		return center(str, n, ' ');
	}

	/**
	 * 문자열이 특정 길이보다 작다면 좌우에 delimiter 문자를 삽입해 준다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.center("abcd", 6, ' '); // output --> " abcd "
	 * String str = StringUtil.center("abcd", 7, ' '); // output --> " abcd  "
	 * </pre></blockquote>
	 */
	public static String center(String str, int n, char delim)
	{
		return center(str, n, delim + "");
	}

	/**
	 * 문자열이 특정 길이보다 작다면 좌우에 delimiter를 삽입해 준다.
	 * 만약 여백에 이등분이 안된다면 right에 채운다. 모자라면 채우지 않고 남겨 둔다.
	 * 문자열이 null이라면 delimiter로 무두 채운 후에 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = Stringutil.center("1234", 7, "%&");
	 * // output --> "1234%&"
	 * </pre></blockquote>
	 */
	public static String center(String str, int n, String delim)
	{
		if (null == str)
			str = "";
		int len = str.length();
		int i = (n - str.length()) / delim.length();
		if (i > 0)
		{
			str = left(str, len + i / 2, delim);
			str = right(str, n, delim);
		}
		return str;
	}

	// ------------------------------------------------------------- replacement

	/**
	 * 특정 글자를 특정 문자열로 바꿔줍니다.
	 *
	 * 예를 들어, 특정 글자를 escape 처리하고자 할 때 이용할 수 있다.<br>
	 * <blockquote><pre>
	 * pstmt = con.prepareStatement(...);
	 * pstmt.setString(1, SringUtil.replace("IMS'S HOPE", '\'', "''"));
	 * </pre></blockquote>
	 *
	 * @param source 	원본 문자열이다.
	 * @param ch		바꾸고자 하는 글자이다.
	 * @param replace	대치하고자 하는 문자열이다.
	 *
	 * @see #replace(String source, char ch, String replace, int max)
	 */
	public static String replace(String source, char ch, String replace)
	{
		return replace(source, ch, replace, -1);
	}

	/**
	 * 특정 글자를 특정 문자열로 바꿔줍니다.
	 *
	 * @param source 	원본 문자열이다.
	 * @param ch		바꾸고자 하는 글자이다.
	 * @param replace	대치하고자 하는 문자열이다.
	 * @param max		몇번 바꿀 것인지를 나타낸다. <code>-1</code>이면
	 *					모두 바꾼다
	 *
	 * @see #replace(String source, String original, String replace, int max)
	 */
	public static String replace(String source, char ch, String replace, int max)
	{
		return replace(source, ch + "", replace, max);
	}

	/**
	 * 특정 문자열을 특정 문자열로 바꿔줍니다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.replace("Java \r\n is \r\n Wonderful",
	 *								   "\r\n", "<BR>");
	 * </pre></blockquote>
	 *
	 * @param source 	원본 문자열이다.
	 * @param original	바꾸고자 하는 문자열입니다.
	 * @param replace	대치하고자 하는 문자열이다.
	 */
	public static String replace(String source, String original, String replace)
	{
		return replace(source, original, replace, -1);
	}

	/**
	 * 특정 문자열을 특정 문자열로 바꿔줍니다. 문자열이 null이라면 null를 리턴한다.
	 *
	 * @param source 	원본 문자열이다.
	 * @param original	바꾸고자 하는 문자열입니다.
	 * @param replace	대치하고자 하는 문자열이다.
	 * @param max		몇번 바꿀것인지를 나타낸다. <code>-1</code>이면
	 *					모두 바꾼다.
	 */
	public static String replace(String source, String original, String replace, int max)
	{
		if (null == source)
			return null;
		int nextPos = 0; // 다음 position
		int currentPos = 0; // 현재 position
		int len = original.length();
		StringBuffer result = new StringBuffer(source.length());

		while ((nextPos = source.indexOf(original, currentPos)) != -1)
		{
			result.append(source.substring(currentPos, nextPos));
			result.append(replace);
			currentPos = nextPos + len;
			if (--max == 0)
			{ // 바꿀 횟수를 줄어준다
				break;
			}
		}
		if (currentPos < source.length())
		{
			result.append(source.substring(currentPos));
		}
		return result.toString();
	}

	/**
	 * String을 특정 범위를 다른 문자로 대치한다. 문자열이 null이라면
	 * null를 리턴하다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.overlay("Java", 'V', 2, 3);
	 * // output --> "JaVa"
	 * </pre></blockquote>
	 *
	 * @param ch 대치하고자 하는 문자를 나타낸다
	 * @param start 대치하고자 문자열의 시작 index를 나타낸다
	 * @param start 대치하고자 문자열의 종료 index를 나타낸다
	 */
	public static String overlay(String str, char ch, int start, int end)
	{
		return overlay(str, ch + "", start, end);
	}

	/**
	 * String을 특정 범위를 다른 문자열으로 대치한다. 문자열이 null이라면
	 * null를 리턴하다.
	 *
	 * @exception java.lang.IndexOutOfBoundsException start, end의 index가
	 *				범위를 벗어나면 발생한다
	 */
	public static String overlay(String str, String overlay, int start, int end)
	{
		if (null == str)
			return null;
		String pre  = str.substring(0, start);
		String post = str.substring(end);

		return pre + overlay + post;
	}

	/**
	 * String에 특정 key를 값으로 바꾼다. 만약 text가 null이라면 null를 리턴하고
	 * map이 null이라면 원본 문자열을 리턴한다. 예를 들어 xml에서 특정 속성을
	 * 읽어서 String의 부분을 바구고자 할때 이용한다.
	 *
	 * <blockquote><pre>
	 * String text = "${ip} ${port}";
	 * Map map = HashMap();
	 * map.put("ip", "203.252.157.66");
	 * map.put("port", "2002");
	 * String str = StringUtil.interpolate(text, map);
	 * </pre></blockquote>
	 */
	public static String interpolate(String text, Map map) {
		if (null == text)
			return null;
		if (null == map)
			return text;
		Iterator keys = map.keySet().iterator();
		while (keys.hasNext())
		{
			String key = keys.next().toString();
			String value = map.get(key).toString();
			text = replace(text, "${" + key + "}", value);
			/*if (key.indexOf(" ") == -1) {
                text = replace(text, "$" + key, value);
            }*/
		}
		return text;
	}

	// ----------------------- replacement/case

	/**
	 * 첫 글자만 대문자로 바꾸어 준다. 입력 String이 null이라면 null를
	 * 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.toFirstUpperCase("java"); // result --> "Java"
	 * </pre></blockquote>
	 */
	public static String toFirstUpperCase(String str)
	{
		return (null == str ? null : str.substring(0, 1).toUpperCase() + str.substring(1));
	}

	/**
	 * 특정 길이만큼 글자를 대문자로 바꾸어 준다. 입력 String이 null이라면 null
	 * 를 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.toUpperCase("java", 4) // result -> "JAVA"
	 * </pre></blockquote>
	 */
	public static String toUpperCase(String str, int len)
	{
		if (null == str)
			return null;
		int strLen = str.length();
		int index = 0;
		StringBuffer sb = new StringBuffer(str.length());
		while ((index < len) && (index < strLen))
		{
			sb.append(Character.toUpperCase(str.charAt(index)));
			++index;
		}
		if (index < strLen)
		{
			sb.append(str.substring(index));
		}
		return sb.toString();
	}

	/**
	 * Character.toTitleCase()를 이용하여 대문자 한다
	 *
	 * @see #toUpperCase(String, int)
	 */
	public static String toTitleCase(String str, int len)
	{
		if (null == str)
			return null;
		int strLen = str.length();
		int index = 0;
		StringBuffer sb = new StringBuffer(str.length());
		while ((index < len) && (index < strLen))
		{
			sb.append(Character.toTitleCase(str.charAt(index)));
			++index;
		}
		if (index < strLen)
		{
			sb.append(str.substring(index));
		}
		return sb.toString();
	}

	/**
	 * 첫 글자만 소문자로 바꾸어 준다. 입력 String이 null이라면
	 * null를 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.toFirstLowerCase("Java"); // result --> "java"
	 * </pre></blockquote>
	 */
	public static String toFirstLowerCase(String str)
	{
		return (null == str ? null : str.substring(0, 1).toLowerCase() + str.substring(1));
	}

	/**
	 * 특정 길이만큼 글자를 소문자로 바꾸어 준다. 입력 String이 null이라면 null
	 * 를 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.toLowerCase("JAVA", 4) // result -> "java"
	 * </pre></blockquote>
	 */
	public static String toLowerCase(String str, int len)
	{
		if (null == str)
			return null;
		int strLen = str.length();
		int index = 0;
		StringBuffer sb = new StringBuffer(str.length());
		while ((index < len) && (index < strLen))
		{
			sb.append(Character.toLowerCase(str.charAt(index)));
			++index;
		}
		if (index < strLen)
		{
			sb.append(str.substring(index));
		}
		return sb.toString();
	}

	/**
	 * 문자열의 대소문자를 바꾸어 준다. 문자열이 null이라면 null를 리턴한다
	 */
	public static  String swapCase(String str)
	{
		if (null == str)	return null;
		int size = str.length();
		StringBuffer sb = new StringBuffer(size);

		char ch  = 0;
		char tmp = 0;
		boolean whitespace = false;
		for (int i = 0; i < size; i++)
		{
			ch = str.charAt(i);
			if (Character.isUpperCase(ch) || Character.isTitleCase(ch))
			{
				tmp = Character.toLowerCase(ch);
			}
			else if(Character.isLowerCase(ch))
			{
				if (whitespace)
				{
					tmp = Character.toTitleCase(ch);
				}
				else
				{
					tmp = Character.toUpperCase(ch);
				}
			}
			sb.append(tmp);
			whitespace = Character.isWhitespace(ch);
		}

		return sb.toString();
	}

	/**
	 * 만약 string를 거꾸로 바꾸어 준다. null이라면 null를 리턴한다
	 */
	public static String reverse(String str)
	{
		if (null == str)
			return null;
		return new StringBuffer(str).reverse().toString();
	}

	// ------------------------------------------------------------------- split

	/**
	 * String을 공백으로 분리시켜 문자열을 리턴한다.
	 *
	 * <blockquote><pre>
	 * String[] strs = StringUtil.split("Java is wonderful");
	 * // strs[0] = "Java"; strs[1] = "is"; strs[2] = "wonderful"
	 * </pre></blockquote>
	 *
	 * #see split(String str, String delim)
	 */
	public static String[] split(String str)
	{
		return split(str, " ", -1);
	}

	/**
	 * String을 특정 글자로 분리하여 문자열 배열을 리턴한다.
	 *
	 * #see split(String str, char delim, int max)
	 */
	public static String[] split(String str, char delim)
	{
		return split(str, delim + "", -1);
	}

	/**
	 * String을 특정 글자로 분리하여 문자열 배열을 리턴한다.
	 *
	 * @see #split(String str, String delim, int max)
	 */
	public static String[] split(String str, char delim, int max)
	{
		return split(str, delim + "", max);
	}

	/**
	 * String을 특정 문자열로 분리시켜 문자열 배열을 리턴한다.
	 *
	 * <blockquote><pre>
	 * String[] strs = StringUtil.split("a=b&b=c&c=d", "&");
	 * // strs[0] = "a=b"; strs[1] = "b=c"; strs[2] = "c=d"
	 * </pre></blockquote>
	 *
	 * @see #split(String str, String delim, int max)
	 * @see #tokenize(String str, String delims)
	 */
	public static String[] split(String str, String delim)
	{
		return split(str, delim, -1);
	}

	/**
	 * String을 특정 문자열로 특정 횟수만큼 분리해서 문자열을 리턴한다.
	 *
	 * @param str		분리하고자 하는 문자열을 나타낸다.
	 * @param delim		분리자를 나타낸다.
	 * @param max		분리 횟수를 나타낸다. <code>-1</code>는
	 *					전체를 분리한다.
	 */
	public static String[] split(String str, String delim, int max)
	{
		int nextPos = 0;
		int currentPos = 0;
		int len = delim.length();
		List list = new ArrayList();
		while ((nextPos = str.indexOf(delim, currentPos)) != -1)
		{
			list.add(str.substring(currentPos, nextPos));
			currentPos = nextPos + len;
			if (--max == 0)
			{
				// 분리 횟수를 줄어준다
				break;
			}
		}
		if (currentPos < str.length())
		{
			list.add(str.substring(currentPos));
		}
		return (String[])list.toArray(new String[0]);
	}

	/**
	 * String을 특정 character들을 이용하여 분리시켜 문자열을 리턴한다.
	 * {@link #split(String, String)} 메소드와 다르게 여러개의
	 * delimeter character를 받아 tokenizing을 수행한다.
	 *
	 * <blockquote><pre>
	 * String[] strs = StringUtil.split("a=b&b=c&c=d", "=&");
	 * // strs[0] = "a"; strs[1] = "b"; strs[2] = "c"; ... strs[5] = "d";
	 * </pre></blockquote>
	 *
	 * @see #tokenize(String str, String delims, int max)
	 * @see #split(String, String)
	 */
	public static String[] tokenize(String str, String delims)
	{
		return tokenize(str, delims, -1);
	}

	/**
	 * 문자열을 특정 character들을 이용하여 분리시켜 문자열 배열로 리턴한다.
	 *
	 * @param delims	구분자들을 나타낸다. 글자 단위이고 여러 글자가 가능하다.
	 * @param max		분리 횟수를 나타낸다. <code>-1</code>는
	 *					전체를 분리한다.
	 */
	public static String[] tokenize(String str, String delims, int max)
	{
		StringTokenizer st = new StringTokenizer(str, delims);
		int size = st.countTokens();
		if (max != -1 && size > max)
		{
			size = max;
		}
		String[] list = new String[size];
		int i = 0;
		while (st.hasMoreTokens())
		{
			if ((max != -1) && (i == size - 1))
			{
				break;
			}
			else
			{
				list[i] = st.nextToken();
			}
			i++;
		}
		return list;
	}

	/**
	 * String을 특정 문자열로 분리해 List 리턴한다.
	 *
	 * @see #splitAsList(str, delim, max)
	 */
	public static List splitAsList(String str, String delim)
	{
		return splitAsList(str, delim, -1);
	}

	/**
	 * String을 특정 문자열로 특정 횟수만큼 분리해 List 리턴한다.
	 *
	 * @param str		분리하고자 하는 문자열을 나타낸다
	 * @param delim		분리자를 나타낸다.
	 * @param max		분리 횟수를 나타낸다. <code>-1</code>는
	 *					전체를 분리한다.
	 */
	public static List splitAsList(String str, String delim, int max)
	{
		String[] strs = split(str, delim, max);
		return Arrays.asList(strs);
	}

	// -------------------------------------------------------------------- join

	/**
	 * 객체 배열의 element들을 특정 " "를 이용하여 모두 연결시킨다.
	 * 객체 배열이 null이면 null를 리턴한다
	 *
	 * @see #join(Object[] list, String delim)
	 */
	public static String join(Object[] list)
	{
		return join(list, " ");
	}

	/**
	 * 객체 배열의 element들을 특정 delimiter 문자를 이용하여 모두 연결시킨다.
	 * 객체 배열이 null이면 null를 리턴한다
	 *
	 * @see #join(Object[] list, String delim)
	 */
	public static String join(Object[] list, char delim)
	{
		return join(list, delim + "");
	}

	/**
	 * collection의 element들을 특정 delimiter 문자를 이용하여 모두 연결시킨다.
	 * iterator가 null이면 null를 리턴하고 element가 존재 하지 않으면 ""를
	 * 리턴한다.
	 *
	 * @see #join(Iterator iterator, String delim)
	 */
	public static String join(Iterator iterator, char delim)
	{
		return join(iterator, delim + "");
	}

	/**
	 * 객체 배열의 element들을 특정 delimiter 문자열을 이용하여 모두 연결시킨다.
	 * 객체 배열이 null이면 null를 리턴한다.
	 *
	 * <blockquote><pre>
	 * Strings[] strs = new Strings[] {"a=b", "b=c", "c=d"};
	 * String str = StringUtil.join(strs, "&"); // result --> "a=b&b=c&c=d"
	 * </pre></blockquote>
	 */
	public static String join(Object[] list, String delim)
	{
		if (null == list)
			return null;
		int size = list.length;
		int len = (size == 0 ? 0 : (list[0].toString().length() +	delim.length()) * size);

		StringBuffer sb = new StringBuffer(len);
		if (list.length > 0)
			sb.append(list[0].toString());

		for (int i = 1; i < size; i++)
		{
			sb.append(delim);
			sb.append(list[i]);
		}
		return sb.toString();
	}

	/**
	 * collection의 element들을 특정 delimiter 문자를 이용하여 모두 연결시킨다.
	 * iterator가 null이면 null를 리턴하고 element가 존재 하지 않으면 ""를
	 * 리턴한다.
	 */
	public static String join(Iterator iterator, String delim) {
		if (null == iterator)
			return null;
		StringBuffer sb = new StringBuffer();

		while (iterator.hasNext())
		{
			sb.append(iterator.next());
			if (iterator.hasNext())
			{
				sb.append(delim);
			}
		}
		return sb.toString();
	}

	/**
	 * pattern를 특정 횟수만큼 반복해서 더해준다. pattern이 null이면 null를
	 * 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.repeat("1", 5);
	 * // output --> 11111
	 * </pre></blockquote>
	 */
	public static String repeat(String pattern, int n)
	{
		if (null == pattern)
			return null;
		StringBuffer sb = new StringBuffer(n * pattern.length());
		repeat(sb, pattern, n);

		return sb.toString();
	}

	public static void repeat(StringBuffer sb, char ch, int n)
	{
		repeat(sb, ch + "", n);
	}

	/**
	 * pattern를 특정 횟수만큼 반복해서 buffer에 넣어준다. buffer가 null이면
	 * 그냥 리턴된다
	 */
	public static void repeat(StringBuffer sb, String pattern, int n)
	{
		if (null == pattern)
			return;
		for (int i = 0; i < n; i++)
		{
			sb.append(pattern);
		}
	}

	// ------------------------------------------------------------------ insert

	/**
	 * 숫자의 pos 위치마다 char를 삽입해준다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.insert(1234, 3, '.');
	 * // output --> "1.234"
	 * </pre></blockquote>
	 *
	 * @see #insertRight(String, int, String)
	 */
	public static String insertRight(long l, int pos, char ch)
	{
		return insertRight(Long.toString(l), pos, ch + "");
	}

	/**
	 * 문자열의 특정 위치에 특정 글자를 삽입해 준다.
	 *
	 * @see #insertRight(String, int, String)
	 */
	public static String insertRight(String value, int pos, char ch)
	{
		return insertRight(value, pos, ch + "");
	}

	/**
	 * 숫자의 특정 위치에 특정 문자열를 삽입해 준다.
	 *
	 * @see #insertRight(String, int, String)
	 */
	public static String insertRight(long l, int pos, String sep)
	{
		return insertRight(Long.toString(l), pos, sep);
	}

	/**
	 * 문자열의 특정 위치에 특정 문자열을 삽입해 준다. 문자열이 null이라면 단순히
	 * 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.insert("020311", 2, "-");
	 * // output --> "02-03-01"
	 * </pre></blockquote>
	 */
	public static String insertRight(String value, int pos, String sep)
	{
		if (null == value)
			return null;
		int len = value.length();
		StringBuffer sb = new StringBuffer(value);
		for (int i = len; i > pos ; i -= pos)
		{
			sb.insert(i - pos, sep);
		}
		return sb.toString();
	}

	/**
	 * 숫자의 특정 위치에 특정 문자열를 삽입해 준다.
	 *
	 * @see #insertLeft(String, int, String)
	 */
	public static String insertLeft(long l, int pos, String sep)
	{
		return insertLeft(Long.toString(l), pos, sep);
	}

	/**
	 * 문자열의 특정 위치에 특정 문자열을 삽입해 준다. 앞에서부터 삽입한다.
	 */
	public static String insertLeft(String value, int pos, String sep)
	{
		if (null == value)
			return null;
		int len = value.length();
		StringBuffer sb = new StringBuffer(len);
		int currentPos = 0;
		int nextPos = 0;
		while ((nextPos = currentPos + pos) < len)
		{
			sb.append(value.substring(currentPos, nextPos));
			sb.append(sep);
			currentPos = nextPos;
		}
		if (currentPos < len)
		{
			sb.append(value.substring(currentPos));
		}
		return sb.toString();
	}

	/**
	 * 삽입을 앞쪽부터 수행할 것이지 뒤쪽부터 수행할 것인지를 결정하여 삽입해 준다
	 *
	 * @param direction	LEFT나 RIGHT가 된다
	 * @see	#insertLeft(String, int, String)
	 * @see #insertRight(String, int, String)
	 */
	public static String insert(String value, int pos, String sep, int direction)
	{
		if (LEFT == direction)
			return insertLeft(value, pos, sep);
		else if (RIGHT == direction)
			return insertRight(value, pos, sep);

		return null;
	}

	// ----------------------------------------------------------------- extract

	/**
	 * 문자열의 앞뒤에서 특정 문자열을 잘라낸다. 만약 문자열이 null이라면
	 * null를 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.strip("abcdab", "ab");
	 * // output --> "cd"
	 * </pre></blockquote>
	 */
	public static String strip(String str, String delim)
	{
		str = stripStart(str, delim);
		return stripEnd(str, delim);
	}

	/**
	 * 시작 부분의 매칭되는 substring을 strip한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.stripStart("abcdef", "abcd");
	 * // output --> "ef"
	 * </pre></blockquote>
	 *
	 * @param prefix	strip하고자 하는 문자열이다. null이라면 공백을 제거한다.
	 *					즉 {@link #ltrim(String)}과 같은 역활을 수행한다.
	 *
	 * @return String	string이 null이라면 null를 리턴한다. 만약 string과
	 *					prefix가 같다면 빈문자열을 리턴한다
	 */
	public static String stripStart(String str, String prefix)
	{
		if (str == null)
			return str;
		int start = 0;
		if (prefix == null)
		{
			while (Character.isWhitespace(str.charAt(start)))
			{
				start++;
			}
		}
		else if (str.startsWith(prefix))
		{
			if (str.equals(prefix)) // 만약 같다면 빈문자렬을 리턴
				return "";
			return str.substring(prefix.length());
		}
		return str.substring(start);
	}

	/**
	 * 끝 부분의 매칭되는 substring을 strip한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.stripEnd("abcdef", "ef");
	 * // output --> "abcd"
	 * </pre></blockquote>
	 *
	 * @param postfix	strip하고자 하는 문자열이다. null이라면 공백을 제거한다.
	 *					즉 {@link #rtrim(String)}과 같은 역활을 수행한다.
	 *
	 * @return String	Stirng이 null이라면 null를 리턴한다. string이 postfix와
	 *					같다면 빈문자열을 리턴한다
	 */
	public static String stripEnd(String str, String postfix)
	{
		if (str == null)
			return str;
		int end = str.length();
		if (null == postfix)
		{
			while (Character.isWhitespace(str.charAt(end - 1)))
			{
				end--;
			}
		}
		else if (str.endsWith(postfix))
		{
			if (str.equals(postfix)) // 만약 같다면 빈문자렬을 리턴
				return "";
			return str.substring(0, str.lastIndexOf(postfix));
		}
		return str.substring(0, end);
	}

	/**
	 * string에서 특정 string의 이전 이후 것을 리턴한다
	 *
	 * @param direction 방향을 나타낸다. StringUtil.LEFT인 경우 chompLast()이고
	 *			  		StringUtil.RIGHT인 경우는 chompFirst()로 작동한다
	 *
	 * @see #chompLast(String, String, int)
	 * @see #chompFirst(String, String, int)
	 */
	public static String chomp(String str, String sep, int max, int direction)
	{
		if (direction == LEFT)
			return chompLast(str, sep, max);
		else if (direction == RIGHT)
			return chompFirst(str, sep, max);
		return null;
	}

	/**
	 * string에서 특정 string이 이전 것만 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.chompFirst("abcd\cdef\de", "\\");
	 * output --> "de"
	 * </pre></blockquote>
	 */
	public static String chompFirst(String str, String sep)
	{
		return chompFirst(str, sep, -1);
	}

	/**
	 * string에서 특정 string이 이전 것만 리턴한다. string이 null이면 null를
	 * 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.chompFirst("abcd\cdef\de", "\\", 1);
	 * output --> "cdef\de"
	 * </pre></blockquote>
	 *
	 * @param max	chomp할 횟수를 나타낸다. 만약 -1이라면 모두 제거한다
	 */
	public static String chompFirst(String str, String sep, int max)
	{
		if (null == str)
			return null;
		int index = str.indexOf(sep);
		if (index != -1)
		{
			if (--max != 0)
			{ // chomp할 횟수를 줄어준다
				return chompFirst(str.substring(index + sep.length()),
						sep, max);
			}
			return str.substring(index);
		}
		else
		{
			return str;
		}
	}

	/**
	 * string에서 특정 string의 이전 것만 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.chompLast("abcd\cdef\de", "\\");
	 * output --> "abcd"
	 * </pre></blockquote>
	 */
	public static String chompLast(String str, String sep)
	{
		return chompLast(str, sep, -1);
	}

	/**
	 * string에서 특정 string의 이전 것만 리턴한다. string이 null이라면 null를
	 * 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.chompLast("abcd\cdef\de", "\\", 1);
	 * output --> "abcd\cdef"
	 * </pre></blockquote>
	 *
	 * @param max	chomp할 횟수를 나타낸다. 만약 -1이라면 모두 제거한다
	 */
	public static String chompLast(String str, String sep, int max)
	{
		if (null == str)
			return null;
		int index = str.lastIndexOf(sep);
		if (index != -1)
		{
			if (--max != 0)
			{ // chomp할 횟수를 줄어준다
				return chompLast(str.substring(0, index), sep, max);
			}
			return str.substring(0, index);
		}
		else
		{
			return str;
		}
	}

	/**
	 * String의 끝에서 \n나 \r\n, \r를 제거한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.v("abcd\r\n");
	 * // output --> "abc"
	 * </pre></blockquote>
	 */
	public static String chopNewline(String str)
	{
		if (null == str)
			return null;
		int index = str.length() - 1;
		char last = str.charAt(index);
		if (last == '\n')
		{
			if (str.charAt(index - 1) == '\r')
			{
				index--;
			}
		}
		else if (last != '\r')
		{
			return str;
		}
		return str.substring(0, index);
	}

	/**
	 * 특정 문자열 사이에 있는 글자를 추출한다. matching되는 것이 없다면 null를
	 * 리턴한다. string이 null이면 null를 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = StringUtil.substring("abcdef", "ab", "ef");
	 * // output --> "cd"
	 * </pre></blockquote>
	 */
	public static String substring(String str, String open, String close)
	{
		if (null == str)
			return null;
		int start = str.indexOf(open);
		if (start != -1)
		{
			int end = str.indexOf(close, start + open.length());
			if (end != -1)
			{
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	// ------------------------------------------------------------------- index

	/**
	 * 배열에 담긴 문자열중 문자열을 포함하고 있는 가장 앞쪽에 존재하는 index값을
	 * 리턴한다.
	 *
	 * <blockquote><pre>
	 * int index = StringUtil.indexOf("abcd", new String[] {"bc", "ab"});
	 * // output --> -1
	 * </pre></blockquote>
	 *
	 * @return 배열의 index 값을 리턴한다. string이 <code>null</code>이면
	 *		   이거나 발견되지 되지 않으면 <code>-1</code>를 리턴한다.
	 * @exception java.lang.NullPointerExcetion 만약 <code>strs</code>가
	 *			  null이라면 발생한다.
	 */
	public static int indexOf(String str, String[] strs)
	{
		if (null == str)
			return -1;
		int len = strs.length;
		int tmp = 0;
		int ret = Integer.MAX_VALUE;

		for (int i = 0; i < len; i++)
		{
			tmp = str.indexOf(strs[i]);
			if (tmp == -1)
			{
				continue;
			}
			if (tmp < ret)
			{
				ret = tmp;
				break;
			}
		}
		return (ret == Integer.MAX_VALUE ? -1 : ret);
	}

	/**
	 * 배열에 담긴 문자열중 string의 일부분 위치중 가장 뒤쪽에 있는 것을 찾는다.
	 * 만약 발견되지 않으면 -1를 리턴한다. string이 null이면 -1를 리턴한다.
	 *
	 * <blockquote><pre>
	 * int index = StringUtil.lastIndexOf("abcd", new String[] {"ab", "bc"});
	 * // output --> 1;
	 * </pre></blockquote>
	 */
	public static int lastIndexOf(String str, String[] strs)
	{
		if (null == str)
			return -1;
		int len = strs.length;
		int ret = -1;
		int tmp = 0;
		for (int i = 0; i < len; i++)
		{
			tmp = str.lastIndexOf(strs[i]);
			if (tmp > ret)
			{
				ret = tmp;
			}
		}
		return ret;
	}

	/**
	 * 얼마나 sub string이 matching되는지를 리턴한다. string이 null이면 0을 리턴
	 * 한다
	 */
	public static int countMatches(String str, String sub)
	{
		if (null == str)
			return 0;
		int count = 0;
		int index = 0;
		while ((index = str.indexOf(sub, index)) != -1)
		{
			count++;
			index += sub.length();
		}
		return count;
	}


	/**
	 * 긴 하나의 string line를 일정 길이 만큼 wrap하기 위한 메소드이다. 길이를
	 * 넘어서는 곳에 newline를 삽입해 준다. 내부적으로 사용하기 위한
	 * 메소드이다.
	 *
	 * <blockquote><pre>
	 * String str = wrapLine("Java is Beautiful.", "|", 4);
	 * // output --> "Java| is |Beau|tifu|l."
	 * </pre></blockquote>
	 *
	 * @param line			wrap하기 위한 string을 나타낸다
	 * @param newline		line를 구별하기 위해 사용되는 newline들을 나타낸다.
	 * @param wrapColumn	wrap하기 위한 column의 길이를 나타낸다.
	 */
	protected static String wrapLine(String line, String newline,	int wrapColumn)
	{
		StringBuffer sb = new StringBuffer(line.length());
		int currentPos = 0;
		int nextPos = wrapColumn;
		while (line.length() > nextPos)
		{
			sb.append(line.substring(currentPos, nextPos));
			sb.append(newline);
			currentPos = nextPos;
			nextPos += wrapColumn;
		}
		if (currentPos < line.length())
		{
			sb.append(line.substring(currentPos));
		}
		return sb.toString();
	}

	/**
	 * 긴 하나의 string line를 일정 길이 만큼 wrap하기 위한 메소드이다. 길이를
	 * 넘어서는 곳에 newline를 삽입해 준다. 만약 단어가 분리되면 split를 삽입해
	 * 준다.
	 *
	 * <blockquote><pre>
	 * String str = wrapWordLine("Java is Beautiful.", "\n", "-", 4);
	 * // output --> "Java\n is-\n Be-\naut-\nifu-\nl."
	 * </pre></blockquote>
	 *
	 * @param line			wrap하기 위한 string을 나타낸다
	 * @param newline		line를 구별하기 위해 사용되는 newline들을 나타낸다.
	 * @param split			단어를 구별하기 위해 사용된다
	 * @param wrapColumn	wrap하기 위한 column의 길이를 나타낸다.
	 */
	protected static String wrapWordLine(String line, String newline,	String split, int wrapColumn)
	{
		int len = line.length();
		StringBuffer sb = new StringBuffer(len);
		String str = null;
		for (int i = 0; i < len; i += wrapColumn)
		{
			if (i >= len - wrapColumn)
			{
				sb.append(line.substring(i));
				break;
			}
			str = line.substring(i, i + wrapColumn);
			// 여백이라면 단어가 분리
			if (Character.isWhitespace(line.charAt(i + wrapColumn)))
			{
				sb.append(str);
			}
			else
			{ // 여백이 아니라 글자라면 단어이다
				// 이전 이전 글자가 공백이라면 단어가 분리된 것이다
				if (Character.isWhitespace(str.charAt(wrapColumn - 2)))
				{
					// 이전 글자가 공백이라면
					if (Character.isWhitespace(str.charAt(wrapColumn - 1)))
					{
						sb.append(str);
					}
					else
					{
						sb.append(str.substring(0, wrapColumn -2));
						i-=2;
					}
					//sb.append(str);
				}
				else
				{
					sb.append(str.substring(0, wrapColumn - 1));
					sb.append(split);
					--i; // 뒤로 이동
				}
			}
			sb.append(newline);
		}
		return sb.toString();
	}

	// --------------------------------------------------------------------- set

	/**
	 * 특정 글자를 다른 글자로 바꾼다. target이 null이면 null를 리턴하고 rep나
	 * with가 null이면 원본 문자열을 리턴한다.
	 *
	 * <blockquote><pre>
	 * translate("hello", "ho", "jy") => jelly
	 * </pre></blockquote>
	 */
	public static String translate(String target, String rep, String with)
	{
		if (null == target)
			return null;
		StringBuffer sb = new StringBuffer(target.length());
		char[] chars = target.toCharArray();
		char[] withChars = with.toCharArray();
		int len = chars.length;
		int withMax = with.length() - 1;
		for (int i = 0; i < len; i++)
		{
			int index = rep.indexOf(chars[i]);
			if (index != -1)
			{
				if (index > withMax)
				{
					index = withMax;
				}
				sb.append(withChars[index]);
			}
			else
			{
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	// -------------------------------------------------------------- formatting

	/**
	 * 문자열이 특정 Byte보다 작다면 우측에 공백 문자를 넣어준다.
	 * 문자열이 null이라면 공백으로 로 모두 채운 후에 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = Stringutil.right("1234", 7, "%&");
	 * // output --> "1234%&"
	 * </pre></blockquote>
	 */
	public static String rightByte(String str, int n)
	{
		if (null == str)
		{
			str = "";
		}
		
		int len = str.getBytes().length;
		if(n <= len)
		{
			return str;
		}
		else
		{
			StringBuffer sb = new StringBuffer(n);
			sb.append(str);
			StringUtils.repeat(sb, ' ',  n - len);
			
			return sb.toString();
		}
	}

	/**
	 * 문자열이 특정 Byte보다 작다면 우측에 특정 무자를 넣어준다.
	 * 문자열이 null이라면 공백으로 로 모두 채운 후에 리턴한다.
	 *
	 * <blockquote><pre>
	 * String str = Stringutil.right("1234", 7, '0');
	 * // output --> "12340000"
	 * </pre></blockquote>
	 */
	public static String rightByte(String str, int n, char fill)
	{
		if (null == str)
		{
			str = "";
		}
		
		int len = str.getBytes().length;
		if(n <= len)
		{
			return str;
		}
		else
		{
			StringBuffer sb = new StringBuffer(n);
			sb.append(str);
			StringUtils.repeat(sb, fill,  n - len);
			
			return sb.toString();
		}
	}
	
	public static String leftByte(String str, int n)
	{
		if (null == str)
		{
			str = "";
		}
		
		int len = str.getBytes().length;
		if(n <= len)
		{
			return str;
		}
		else
		{
			StringBuffer sb = new StringBuffer(n);
			StringUtils.repeat(sb, ' ',  n - len);
			sb.append(str);
			
			return sb.toString();
		}
	}

	public static String leftByte(String str, int n, char fill)
	{
		if (null == str)
		{
			str = "";
		}
		
		int len = str.getBytes().length;
		if(n <= len)
		{
			return str;
		}
		else
		{
			StringBuffer sb = new StringBuffer(n);
			StringUtils.repeat(sb, fill,  n - len);
			sb.append(str);
			
			return sb.toString();
		}
	}

	  /**
	    * 한글을 2바이트로 인식하여 반환한다.
	    * @param str
	    * @param sLoc
	    * @param eLoc
	    * @return
	    */
	    public static String subByte(String str, int sLoc, int eLoc) 
	    {
	    	if(str == null)
	    		return "";
	    	
	        byte[] bystStr;
	        String rltStr = "";
	        bystStr = str.getBytes();
	        rltStr = new String(bystStr, sLoc, eLoc - sLoc);

	        return rltStr;
	    }
	    
	    /**
	     * '8859_1' 을 'euc-kr' 로 인코딩한다.
	     * @param source
	     * @return
	     */
	    public static String encodingKo(String source)
	    {
	    	try 
	    	{
//				 String result = new String(source.getBytes("8859_1"), "euc-kr"); //MHCHOI
	    		String result = new String(source.getBytes("8859_1"), "utf-8");
				
				return result;
			} 
	    	catch (UnsupportedEncodingException e) 
	    	{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
				
				return source;
			}
	    }

		   /**
	     * Throwadble 객체에 대한 스택정보에 뉴라인정보를 삽입한다.
	     * @param throwable Throwable
	     * @return String[]
	     */
	    public static String splitStackTrace(Throwable throwable) 
	    {
	        StringWriter sw = new StringWriter();
	        throwable.printStackTrace(new PrintWriter(sw, true));
	        String[] stacks = StringUtils.split(sw.toString(), "\n");
	        
	        StringBuffer buf = new StringBuffer();
	         for (int i = 0; i < stacks.length; i++) 
	         {
	        	 buf.append(stacks[i] + "\n");
	        }
	         
	         return buf.toString();

	    }
	    
	    /**
	     * 주민번호 뒷자리 7자리를 짤라서 리턴한다.
	     * @param perRegNo  주민번호
	     * @return 주민번호 뒤 7자리
	     */
		public static String getInitialPasswd(String perRegNo)
		{
			if(perRegNo == null || "".equals(perRegNo))
			{
				return "";
			}
			
			int perRegNoLength = perRegNo.length();
			String initialPasswd = null;
			if(perRegNoLength == 13)
			{
				return perRegNo.substring(6);
			}
			else if(perRegNoLength == 12)
			{
				return perRegNo.substring(5);
			}
			else
			{
				return "";
			}
		}
		
		/**
		 * 문자열 자르기(기본)
		 * @param sourceText
		 * @param cutLength
		 * @return
		 */
		public static String stringCut(String sourceText, int cutLength) {
			return stringCut(sourceText, null, cutLength, 0, false, false);
		}
		
		/**
		 * 문자열 자르기
		 * @param sourceText
		 * @param startKeyword : 시작 키워드
		 * @param cutLength : 보여줄 문자열 길이
		 * @param startKewordPreviousLength : 시작 키워드 위치값에서 앞으로 얼만큼 이동값
		 * @param isTag : 태그제거 여부
		 * @param isDot : .(dot) 표시 여부
		 * @return 자라낸 문자열
		 */
		public static String stringCut(String sourceText, String startKeyword, int cutLength,
				int startKewordPreviousLength, boolean isTag, boolean isDot) {
			String targetText = sourceText;
			int oF = 0;
			int oL = 0;
			int rF = 0;
			int rL = 0;
			int nLengthPrev = 0;
			
			// 태그 제거 패턴
			Pattern p = Pattern.compile("<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE);

			if (isTag) {
				targetText = p.matcher(targetText).replaceAll("");
			}

			try {
				 // 바이트 보관
				byte[] bytes = targetText.getBytes("UTF-8");
				if (startKeyword != null && !startKeyword.equals("")) {
					nLengthPrev = (targetText.indexOf(startKeyword) == -1) ? 0 : targetText
							.indexOf(startKeyword); // 일단 위치찾고
					// 위치까지길이를  byte로 다시 구한다
					nLengthPrev = targetText.substring(0, nLengthPrev).getBytes("MS949").length;
					// 좀 앞부분부터 가져오도록한다.
					nLengthPrev = (nLengthPrev - startKewordPreviousLength >= 0) ? nLengthPrev - startKewordPreviousLength : 0; 
				}

				// x부터 y길이만큼 잘라낸다. 한글안깨지게.
				int j = 0;

				if (nLengthPrev > 0)
					while (j < bytes.length) {
						if ((bytes[j] & 0x80) != 0) {
							oF += 2;
							rF += 3;
							if (oF + 2 > nLengthPrev) {
								break;
							}
							j += 3;
						} else {
							if (oF + 1 > nLengthPrev) {
								break;
							}
							++oF;
							++rF;
							++j;
						}
					}

				j = rF;

				while (j < bytes.length) {
					if ((bytes[j] & 0x80) != 0) {
						if (oL + 2 > cutLength) {
							break;
						}
						oL += 2;
						rL += 3;
						j += 3;
					} else {
						if (oL + 1 > cutLength) {
							break;
						}
						++oL;
						++rL;
						++j;
					}
				}

				// charset 옵션
				targetText = new String(bytes, rF, rL, "UTF-8");

				 // "..."를 붙일지 말지 옵션
				if (isDot && rF + rL + 3 <= bytes.length) {
					targetText += "...";
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return targetText;
		}


		
		public static String timeCheck(String time, char gb, String[] pattern) {
			String msg = "";
			switch(gb) {
			case 'S':
				if(time.indexOf("/") > -1) {
					msg = "매 "+time.split("\\/")[1]+"초마다";
				}
				break;
			case 'M':
				if(time.indexOf("/") > -1) {
					msg = "매 "+time.split("\\/")[1]+"분마다";
				}
				break;
			case 'H':
				if(time.startsWith("0")) {
					if(time.indexOf("/") > -1) {
						msg = "매 "+time.split("\\/")[1]+"시간마다";
					}
				} else if(!time.equals("*")) {
					msg = "매일 "+time+"시에";
					if(!pattern[1].equals("0")){
						msg = "매일 "+time+"시 " +pattern[1]+"분에";
					}
				}
				break;
			}
			return msg;
		}
	 
}
