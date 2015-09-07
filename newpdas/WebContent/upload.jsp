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
	System.out.println("##### START UPLOAD #####");
	try {
		Properties properties = new Properties();
		properties.put("fileUpload_field_encode", "true");
		properties.put("fileUpload_field_encodeType", "UTF-8");

		// ========================================================================
		String today = CalendarUtil.getToday();
		String getDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
		String getDate = CalendarUtil.getDateTime("yyyyMMdd");
		System.out.println("getDateTime :" + getDateTime);
		// kfrm Upload directory
		String kfrmDir = "/mp4/" + today.substring(0, 6) + "/"+ today.substring(6) + "/ctid/kfrm/";

		// photo Upload directory
		String photoDir = "/mp4/photo/clf_code/";

		// attach Upload directory
		String attachDir = "/mp4/attach/" + today.substring(0, 6) + "/"+ today.substring(6) + "/";

		// =================================================== =====================

		// Gubun(kfrm, photo, attach)

		String select = StringUtils.parseString(request
				.getParameter("select"));
		String ct_id = StringUtils.parseString(request
				.getParameter("CTID"));
		String clf_code = StringUtils.parseString(request
				.getParameter("clf_code"));
		String file_type = StringUtils.parseString(request
				.getParameter("file_type"));
		String clf_cd = StringUtils.parseString(request
				.getParameter("clf_cd"));
		String file_path = StringUtils.parseString(request
				.getParameter("filePath"));
		String file_name = StringUtils.parseString(request
				.getParameter("fileName"));
		String userId = StringUtils.parseString(request
				.getParameter("userId"));

		System.out.println("select=[" + select + "] ");
		System.out.println("ct_id=[" + ct_id + "] ");
		System.out.println("clf_code=[" + clf_code + "] ");
		System.out.println("file_type=[" + file_type + "] ");
		System.out.println("clf_cd=[" + clf_cd + "] ");
		System.out.println("file_path=[" + file_path + "] ");
		System.out.println("file_name=[" + file_name + "] ");

		System.out.println("####subj####:"+ StringUtils.parseString(request.getParameter("subj")));
		
		String fileUploadPath = "";
		//  select="attach";
		if (select.equals("kfrm") || select.equals("KFRM")) {

			String strKeyframePath = "";
			Connection con = null;
			ResultSet rs = null;
			try {
				String strQuery = "select KFRM_PATH from DAS.CONTENTS_TBL where CT_ID="+ ct_id;
				out.println(strQuery);
				
				con = DBService.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(strQuery);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					strKeyframePath = rs.getString("KFRM_PATH");
					//out.println("<br>keyframe Path = " + strKeyframePath + "<br>");
				}

				fileUploadPath = "/" + strKeyframePath;
				// fileUploadPath = "\\mp4\\kfrm\\" + strKeyframePath+"\\";  
			} catch (SQLException e) {
				e.printStackTrace();
				//throw exception;
			} finally {
				try{
					if(con != null) con.close();
				} catch (Exception e) {}
			}
		} else if (select.equals("photo")) {
			//   fileUploadPath = "/mp4/photo/" + clf_code + "/";
		} else if (select.equals("attach")) {
			//fileUploadPath = attachDir;
			fileUploadPath = "\\mp4\\attach\\" + getDateTime + "\\";
			// fileUploadPath = "C:\\workspace\\PDAS\\WebContent\\mp4\\attach\\"+getDateTime;
			//fileUploadPath = kfrmDir;
		} else if (select.equals("test")) {
			fileUploadPath = "/mp4/uploadTest/";
		} else if (select.equals("caption")) {
			fileUploadPath = file_path;// "\\mp4\\caption\\" +getDate+"\\";
		} else if (select.equals("previewnote")) {
			fileUploadPath = file_path;// "\\mp4\\previewnote\\" +getDate+"\\";
		} else {
			fileUploadPath = properties
					.getProperty(DASBusinessConstants.FilePathPropName.NOTICE);
		}

		//fileUploadPath = "/was/jeus5/webhome/app_home/DAS" + fileUploadPath;
		//fileUploadPath = "/app/jeus/DASBefore" + fileUploadPath;

		//   fileUploadPath = "/app/jeus/PDAS" + fileUploadPath;
		//  out.println(fileUploadPath);
		System.out.println("Content type :" + request.getContentType());
		//fileUploadPath = "C:\\workspace\\PDAS\\WebContent\\mp4\\attach\\"+getDateTime;
		fileUploadPath = file_path;

		System.out.println("[fileUploadPath]" + fileUploadPath);
		File dir = new File(fileUploadPath);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		FileUploader fileUploader = null;
		if (select.equals("kfrm") || select.equals("KFRM"))
			fileUploader = new FileUploader(request, fileUploadPath,
					1024 * 1024 * 20, properties, false);
		else
			fileUploader = new FileUploader(request, fileUploadPath,
					1024 * 1024 * 20, properties, true);
		System.out.println("##########start_upload");
		Map map = (Map) fileUploader.getParamAfterUpload();
		System.out.println("##########end_upload");
		List fileInfoDOList = (List) map.get("FILE_INFO");

		int count = 0;
		for (Iterator i = fileInfoDOList.iterator(); i.hasNext();) {
			out.println(i.next());
			out.println("<br>");
			out.println(count++);

		}

		//response.sendRedirect("/sample/uploadTest.jsp");
		out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
				+ (String) map.get("file"));
		out.println((String) map.get("text2"));
		out.println((String) map.get("text3"));
		out.println((String) map.get("text4"));
		out.println((String) map.get("text5"));
		if (select.equals("kfrm") || select.equals("KFRM")) {
			if (file_name.endsWith("mer")) {
				System.out.println("##########remotehost  =====  "
						+ request.getRemoteHost().toString());
				System.out.println("##########ct_id = " + ct_id);
				ExternalDAO ex = ExternalDAO.getInstance();

				file_path = file_path.replaceAll(
						"/jeus/PDAS/WebContent/", "");
				ex.insertMergeInfo(request.getRemoteHost().toString(),
						Long.parseLong(ct_id), file_path);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
