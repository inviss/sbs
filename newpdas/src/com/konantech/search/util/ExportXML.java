package com.konantech.search.util;

import com.konantech.search.data.ResultVO;
import com.konantech.search.module.SearchModule;


/** 
 * 검색결과를 xml로 변환 해 주는 유틸.
 * 
 * @author KONAN Technology, 박혜경
 * @since 2010.11.22
 * @version 1.0
 * Copyright ⓒ Konan Technology. All Right Reserved
 * ==================================================
 * 변경자/변경일 : OOO / yyyy.mm.dd
 * 변경사유/내역 : 기능 추가로 변경함.
 */
public class ExportXML {
	
	/** 
	 * search()를 통해 세팅되는 결과값 객체
	 */
	private static ResultVO rsb = new ResultVO();
	
	/**
	 * 
	 * @param scn
	 * @param query
	 * @param startNum
	 * @param pageSize
	 * @param nLanguage
	 * @param nCharset
	 * @throws Exception
	 */
	public static String search(String scn, String query,String orderBy, int startNum, int pageSize, int nLanguage, int nCharset) throws Exception {
		SearchModule module = new SearchModule();
		module.dcSubmitQuery(scn, query, "", "", orderBy, startNum, pageSize, false, rsb, nLanguage, nCharset);
		return makeXML("das", "DAS_SEARCH", "utf-8",orderBy,startNum,pageSize,rsb.getTotal());
	}
	
	/**
	 * 
	 * @param scn
	 * @param query
	 * @param startNum
	 * @param pageSize
	 * @param nLanguage
	 * @param nCharset
	 * @return
	 * @throws Exception
	 */
	public static String search(String scn, String query, int startNum, int pageSize, int nLanguage, int nCharset) throws Exception {
		SearchModule module = new SearchModule();
		module.dcSubmitQuery(scn, query, "", "", "", startNum, pageSize, false, rsb, nLanguage, nCharset);
		return makeXML("das", "DAS_SEARCH", "utf-8","",startNum,pageSize,rsb.getTotal());
	}

	/**
	 * 
	 * @param root 루트태그
	 * @param wrapper 건별로 감싸는 태그
	 * @param encoding xml 인코딩
	 * @return
	 * @throws Exception
	 */
	public static String makeXML(String root, String wrapper, String encoding,String orderBy,int startNum,int pageSize,int total) throws Exception {
		StringBuffer buf = new StringBuffer();
//		buf.append("");

		if( rsb.getTotal() <= 0 ) {
			return buf.toString();
		}
		buf.append("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n");
		buf.append("<" + root + ">\n");
		buf.append("<orderBy>"+orderBy+"</orderBy>");
		buf.append("<startNum>"+startNum+"</startNum>");
		buf.append("<pageSize>"+pageSize+"</pageSize>");
		buf.append("<total>"+total+"</total>");
		buf.append("<rows>"+rsb.getRows()+"</rows>");
		for(int i=0; i<rsb.getRows(); i++) {
			if( wrapper != null && wrapper.length() > 0 )
				buf.append("\t<" + wrapper + ">\n");
			
			for(int j=0; j<rsb.getCols(); j++) {
				if( wrapper != null && wrapper.length() > 0 )
					buf.append("\t");
				
				if(rsb.getColNames()[j].equals("SCENARIO_TITLE")||rsb.getColNames()[j].equals("SCENARIO_DESC")){
					//대본명과 대본내용이 담겨져있는 경우 클라이언트에서 그 속도가 현저히 느려지는 현상이 발생 
					//클라이언트에서는 위 두개를 파싱은 하지만 사용하지 않으므로 xml을 생성하는 시점에서 해당 값을 제거
					//2014.11.11 by asura
				}else{
					if( rsb.getFdata()[i][j] == null || rsb.getFdata()[i][j].trim().length() <= 0  ) {
					    buf.append("\t<" + rsb.getColNames()[j] + "></" + rsb.getColNames()[j] + ">\n");
					} else {
						buf.append("\t<" + rsb.getColNames()[j] + ">" + CommonUtil.formatHtml(rsb.getFdata()[i][j]) + "</" + rsb.getColNames()[j] + ">\n");
	//					buf.append("\t<" + rsb.getColNames()[j] + ">" + rsb.getFdata()[i][j] + "</" + rsb.getColNames()[j] + ">\n");
					}
				}
			}
			if( wrapper != null && wrapper.length() > 0 )
				buf.append("\t</" + wrapper + ">\n");
		}
		buf.append("</" + root + ">\n");
		
		return buf.toString();
	}
	
	   public String makeXML(String root, String wrapper, String encoding) throws Exception {
           StringBuffer buf = new StringBuffer();
           
           buf.append("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n");

           if( rsb.getTotal() <= 0 ) {
                   return buf.toString();
           }
           
           buf.append("<" + root + ">\n");
           for(int i=0; i<rsb.getRows(); i++) {
                   if( wrapper != null && wrapper.length() > 0 )
                           buf.append("\t<" + wrapper + ">\n");
                   
                   for(int j=0; j<rsb.getCols(); j++) {
                           if( wrapper != null && wrapper.length() > 0 )
                                   buf.append("\t");
                       	if(rsb.getColNames()[j].equals("SCENARIO_TITLE")||rsb.getColNames()[j].equals("SCENARIO_DESC")){
                       		//대본명과 대본내용이 담겨져있는 경우 클라이언트에서 그 속도가 현저히 느려지는 현상이 발생 
        					//클라이언트에서는 위 두개를 파싱은 하지만 사용하지 않으므로 xml을 생성하는 시점에서 해당 값을 제거
        					//2014.11.11 by asura
                       	}else{
                           if( rsb.getFdata()[i][j] == null || rsb.getFdata()[i][j].trim().length() <= 0  ) {
                                   buf.append("\t<" + rsb.getColNames()[j] + "/>\n");
                           } else {
                                   buf.append("\t<" + rsb.getColNames()[j] + ">" + CommonUtil.formatHtml(rsb.getFdata()[i][j]) + "</" + rsb.getColNames()[j] + ">\n");
                           }
                           }
                   }
                   if( wrapper != null && wrapper.length() > 0 )
                           buf.append("\t</" + wrapper + ">\n");
           }
           buf.append("</" + root + ">\n");
           
           return buf.toString();
   }
//	
//	public static void main(String[] args) {
//		ExportXML export = new ExportXML();
//		
//		try {
//			export.search("annot", "text_idx='1' allword", 1, 10, 1, 1);
//			System.out.print(export.makeXML("root", "wrapper", "EUC-KR"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
