<%@ page language="java" contentType="application/xml; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="com.konantech.search.util.CommonUtil" %>
<jsp:useBean id="export" class="com.konantech.search.util.ExportXML" scope="request" />

<%
        String scn = CommonUtil.null2Str(request.getParameter("scn"), "");
        String query = CommonUtil.null2Str(request.getParameter("query"), "");
        int pageNum = CommonUtil.null2Int(request.getParameter("pageNum"), 1);
        int pageSize = CommonUtil.null2Int(request.getParameter("pageSize"), 10);
        String root = CommonUtil.null2Str(request.getParameter("root"), "root");
        String wrapper = CommonUtil.null2Str(request.getParameter("wrapper"), "");

		scn = "program";
		query = " text_idx=\'ÇÁ¶û½º\' allword";
		   
		System.out.println("query.toString()["+query.toString()+"] ");
       // query = CommonUtil.changeEncode(query, "ISO-8859-1", "EUC-KR");
        System.out.println("query.toString()["+query.toString()+"] ");
		
        // EUC-KR : nLanguage=1, nCharset=1
        // UTF-8 : nLanguage=1, nCharset=4
        int nLanguage = 1;
        int nCharset = 1;

        if( !"".equals(scn) && !"".equals(query) ) {
                try {
                        export.search(scn, query, pageNum, pageSize, nLanguage, nCharset);
                        out.print(export.makeXML(root, wrapper, "EUC-KR"));
                } catch (Exception e) {
                        e.printStackTrace();
                        out.print("Err:" + e.getMessage());
                }
        }

%>