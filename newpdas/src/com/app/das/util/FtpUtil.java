package com.app.das.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
/**
 * FTP 통신 관련 함수가 정의되어진CLASS
 
 */
public class FtpUtil { 

   public FtpUtil(){}
   public static void ftpTransport(String server,String username,String password,String savefile,String sendfile){

	try {

		FTPClient ftpClient = null;
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect(server);
		int reply = ftpClient.getReplyCode();
		if(!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			System.out.println("5FTP server refused connection.");				
                }else{
			System.out.println("connect............");				
                        ftpClient.login(username,password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
                         //FTP.ASCII_FILE_TYPE, FTP.EBCDIC_FILE_TYPE, FTP.IMAGE_FILE_TYPE , FTP.LOCAL_FILE_TYPE 
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
                          //FTP.BLOCK_TRANSFER_MODE, FTP.COMPRESSED_TRANSFER_MODE 	
			System.out.println("login............");				
		        boolean result = false;
		        InputStream inputStream = null;
		        OutputStream outputStream = null;
		        File put_file = null;
	                put_file = new File(savefile);
         	        inputStream = new FileInputStream(put_file);
			System.out.println("file............"+inputStream);				
		        result = ftpClient.storeFile(sendfile,inputStream);
			System.out.println("file............"+result);				
		        inputStream.close();
	
                 	if (result)
		          System.out.println("put successful");
		        else
		           System.out.println("put failure!");
                        
                         ftpClient.logout();

                }
		}catch(Exception e) {
                       e.printStackTrace();
		}

             
   }	
   public static void ftpReciept(String server,String username,String password,String savefile,String sendfile){

	try {

		FTPClient ftpClient = null;
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect(server);
		int reply = ftpClient.getReplyCode();
		if(!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			System.out.println("5FTP server refused connection.");				
                }else{
			System.out.println("connect............");				
                        ftpClient.login(username,password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
                         //FTP.ASCII_FILE_TYPE, FTP.EBCDIC_FILE_TYPE, FTP.IMAGE_FILE_TYPE , FTP.LOCAL_FILE_TYPE 
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
                          //FTP.BLOCK_TRANSFER_MODE, FTP.COMPRESSED_TRANSFER_MODE 	
		        boolean result = false;
		        InputStream inputStream = null;
		        OutputStream outputStream = null;
		        File get_file = null;
	                get_file = new File(savefile);
         	        outputStream = new FileOutputStream(get_file);
		        result = ftpClient.retrieveFile(sendfile, outputStream);
		        outputStream.close();
	
                 	if (result)
		          System.out.println("get successful");
		        else
		           System.out.println("get failure!");
                        
                         ftpClient.logout();

                }
		}catch(Exception e) {
                       e.printStackTrace();
		}

             
   }	


}
