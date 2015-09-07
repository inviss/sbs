package com.app.das.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.das.log.DasPropHandler;
/**
 * 서버의 특정 위치에 있는 파일을 Client에게 다운로드 시켜준다..
  * @version 1.0
 */
public class DownloadServlet extends HttpServlet 
{
	/**
	 * constructor.
	 */
	public DownloadServlet() 
	{
	}
	/**
	 * init.
	 * @throws ServletException
	 */
	public void init() throws ServletException 
	{
	}
	/**
	 * doGet.
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException 
	{
		doPost(req, res);
	}
	/**
	 * doPost.
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException 
	{
		//여기서 Session Check를 한다.
		
		
		logging("============================== 파일 다운로드 ========== 시작 ");
		String fileName = req.getParameter("fileName");
		String originFileName = req.getParameter("originFileName");
		String filePathName = req.getParameter("filePathName");
		String mode = req.getParameter("mode");
		
		String filePath = DasPropHandler.getInstance().getProperty(filePathName);
		
		logging("fileUploadPath + downLoadFileName=" + filePath + "/" + fileName);
		
		String uploadName = filePath + "/" + fileName;
		
		File downLoadFile = new File( uploadName );
		logging("downLoadFile.exists() = " + downLoadFile.exists());
		
		BufferedInputStream inStream = null;		
		BufferedOutputStream outStream = null;
				
		try
		{
			// 업로드되어 있는 파일 InputStream 생성.
			inStream = new BufferedInputStream(new FileInputStream(uploadName));

			if (mode == null || mode.equals("") || mode.equals("down")) 
			{
				// 헤더 정보 할당.
				String strClient = req.getHeader("User-Agent");
				
				String newFileName = 	java.net.URLEncoder.encode(originFileName,"UTF-8");
				
				logging("newFileName=" + newFileName);
				
				if (strClient.indexOf("MSIE 5.5") != -1) 
				{
					res.setHeader("Content-Type", "doesn/matter; charset=euc-kr");
					setDBCSHeader("Content-Disposition", "filename="+newFileName	+ ";", res);

				} 
				else 
				{
					res.setHeader("Content-Type", "application/octet-stream; charset=euc-kr");
					setDBCSHeader("Content-Disposition", "attachment;filename="+newFileName	+ ";", res);
				}
				res.setHeader("Content-Transfer-Encoding", "binary;");
				res.setHeader("Pragma", "no-cache;");
				res.setHeader("Expires", "-1;");
				
			} 
			else 
			{				
				
				String ext = "";
				if (uploadName != null) 
				{
					int index = uploadName.lastIndexOf(".");
					if (index > 0) 
					{
						ext = uploadName.substring(index + 1);
					}
				}
				if ("xls".equalsIgnoreCase(ext)) 
				{
					res.setContentType("application/vnd.ms-excel");
				} 
				else 
				{
					res.setContentType("application/unknown");
				}
				res.setHeader("Content-Disposition", "inline;filename=" + uploadName + ";");
			}
			
			// 다운로드할 파일 OutputStream 생성.
			outStream = new BufferedOutputStream( res.getOutputStream() );
			
			
			byte[] buffer = new byte[8 * 1024];
			
			for (int i; (i = inStream.read(buffer)) != -1; ) 
			{
				outStream.write(buffer, 0, i);
				outStream.flush();
			}
			
		} 
		catch (IOException e) 
		{
			res.getWriter().println("FileDownlod Fail:" + e.toString());
			return;

		} 
		finally 
		{
			if( inStream != null ) 
			{
				inStream.close();
				logging("inputStream close()");
			}
			
			if( outStream != null ) 
			{
				outStream.flush();
				outStream.close();
				logging("outputStream flush() / close()");
			}
		}
		
		logging("============================== 파일 다운로드 ========== 끝 ");
	}
	
	
	/**
	 * 
	 * @param header
	 * @param value
	 * @param response
	 */
	private void setDBCSHeader(String header, String value, HttpServletResponse response) {
		byte b[];
		try 
		{
			b = value.getBytes(response.getCharacterEncoding());
		} 
		catch (Exception ex) 
		{
			b = value.getBytes();
		}

		char c[] = new char[b.length];
		for (int i = 0; i < b.length; i++) 
		{
			c[i] = (char)(((char)b[i])&0xff);
		}

		response.setHeader(header, new String(c));
	}
	
	
	private void logging( String msg ) 
	{
		System.out.println( msg );
	}
}
