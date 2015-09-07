<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ page import="com.app.das.business.*"%>
<%@ page import="com.app.das.business.transfer.*"%>
<%@ page import="com.app.das.business.exception.*"%>
<%@ page import="com.app.das.business.constants.*"%>
<%@ page import="com.app.das.business.dao.ExternalDAO"%>
<%@ page import="com.app.das.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.sql.*"%>


<%
	Connection con = null;
	
	try {
		con = DBService.getInstance().getConnection();
		out.println("DAS connection success</br>");
	} catch (Exception e) {
		out.println("DAS connection fail</br>");
	} finally {
		if(con != null) con.close();
	}
	
	try {
		con = DBService.getInstance().getErpConnection();
		out.print("ERP connection success");
	} catch (Exception e) {
		out.print("ERP connection fail");
	} finally {
		if(con != null) con.close();
	}
%>
