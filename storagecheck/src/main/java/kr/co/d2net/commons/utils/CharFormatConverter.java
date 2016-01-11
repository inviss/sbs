package kr.co.d2net.commons.utils;

public class CharFormatConverter {

	public static final byte[] toLittleEndian(int i)
	{
		byte dest[] = new byte[4];
		dest[0] = (byte)(i & 0xff);
		dest[1] = (byte)(i >>> 8 & 0xff);
		dest[2] = (byte)(i >>> 16 & 0xff);
		dest[3] = (byte)(i >>> 24 & 0xff);
		return dest;
	} 


	public static String getEncoding(String str, String pFormat){
		try{
			if(str == null) {
				return "";
			}
			str = java.net.URLEncoder.encode(str, pFormat);
		} catch(Exception e){
			e.printStackTrace();
		}

		for(int i=0; i < str.length(); i++){
			if(str.charAt(i) =='+'){
				str=str.substring(0, i) + "%20" + str.substring(i+1, str.length());
				i=i+3;
			}
		}
		return str;
	}

	public static String getDecoding(String str, String pFormat){
		try{
			if(str == null) {
				return "";
			}
			str = java.net.URLDecoder.decode(str, pFormat);
		} catch(Exception e){
			e.printStackTrace();
		}

		return str;
	}

	public static String convertDuration(long _duration){
		String _result = "";
		try{
			long j = _duration % (30 * 60 * 60);
			long k = _duration % (30 * 60 );
			long l = _duration % (30);
			long m = (_duration - j) / (30 * 60 * 60);
			long n = (j - k) / (30 * 60);
			long o = (k - l) / 30;
			String p = "0" + m;
			String q = "0" + n;
			String r = "0" + o;
			String s = "0" + l;
			_result = p.substring(p.length()-2, p.length())+":"+q.substring(q.length()-2, q.length())+":"+r.substring(r.length()-2, r.length())+":"+s.substring(s.length()-2, s.length());

		}catch(Exception e){
			e.printStackTrace();
		}
		return _result;
	}

	public static String getStringFromCLOB(java.sql.Clob clob){
		StringBuffer _strBuff = new StringBuffer();
		java.io.Reader _br = null;
		char[] _buf = new char[1024];
		int _readcnt;
		try{
			_br = clob.getCharacterStream();
			while ((_readcnt=_br.read(_buf, 0, 1024)) != -1) {
				_strBuff.append(_buf, 0, _readcnt);
			}

		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (_br != null)
				try {
					_br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		return _strBuff.toString().trim();
	}
}
