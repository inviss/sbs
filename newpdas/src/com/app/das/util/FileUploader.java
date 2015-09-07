package com.app.das.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.BoardDO;
import com.app.das.business.transfer.FileInfoDO;
import com.app.das.log.DasPropHandler;

/**
 * 특정 파일을 서버로 업로드 하는 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class FileUploader 
{
 
	private HttpServletRequest request = null;
	private File uploadDir = null;
	private String uploadTempDir = null;
	private List items = null;
	private Map paramMap = null;
	private long requestLimit = 100*1024*1024; //한번에 업로드 용량은 기본 100메가
	private long fileLimit = 5*1024*1024;  //업로드 가능한 파일의 용량은 기본 5메가
	private boolean bUseDupCheck = true;	// 기본값은 체크하는 
	public static final String FILE_INFO_KEY = "FILE_INFO";
	
	private Properties properties = null;
	
	public FileUploader(HttpServletRequest request, String uploadDir) throws Exception
	{
			this.request = request;
			this.uploadDir = new File(uploadDir);
  
			init();
	}
 
	public FileUploader(HttpServletRequest request, File uploadDir) throws Exception
	{
		this.request = request;
		this.uploadDir = uploadDir;
  
		init();
	}
 
	public FileUploader(HttpServletRequest request, String uploadDir, long fileLimit) throws Exception
	{
		this.request = request;
		this.uploadDir = new File(uploadDir);
		this.fileLimit = fileLimit;
		init();
	}
	
	public FileUploader(HttpServletRequest request, String uploadDir, long fileLimit , Properties properties) throws Exception
	{
		this.request = request;
		this.uploadDir = new File(uploadDir);
		this.fileLimit = fileLimit;
		this.properties = properties;
		init();
	}
 
	public FileUploader(HttpServletRequest request, File uploadDir, long fileLimit) throws Exception
	{
		this.request = request;
		this.uploadDir = uploadDir;
		this.fileLimit = fileLimit;
		init();
	}
	
	public FileUploader(HttpServletRequest request, String uploadDir, long fileLimit , Properties properties, boolean bUseDupCheck) throws Exception
	{
		this.request = request;
		this.uploadDir = new File(uploadDir);
		this.uploadTempDir = uploadDir;
		this.fileLimit = fileLimit;
		this.properties = properties;
		this.bUseDupCheck = bUseDupCheck;
		init();
	}
 
	//초기화 함수
	private void init() throws Exception
	{
  
		boolean isMultipart = FileUpload.isMultipartContent(request);
		
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		//System.out.println(isMultipart);
		//if(!isMultipart)
		//{
		//	throw new Exception("form의 enctype을 multipart/form-data로 하세요...");
		//}
		//System.out.println("uploadDir"+uploadDir);
		//임시저장공간 생성
		DiskFileUpload fileUpload = new DiskFileUpload();
		
		fileUpload.setRepositoryPath(uploadTempDir);
		
		fileUpload.setSizeMax(100*1024*1024);
		
		//fileUpload.setSizeThreshold(1024*1000);
		fileUpload.setSizeThreshold(100*1024*1024);
		
//		DiskFileItemFactory factory = new DiskFileItemFactory(); 
//		factory.setSizeThreshold(10*1024*1024);  //메모리에 저장할 최대 size
//		factory.setRepository(uploadDir); //임시 저장할 위치

		//업로드 핸들러 생성
//		ServletFileUpload upload = new ServletFileUpload(factory);
//        upload.setSizeMax(requestLimit);  //Set overall request size constraint
//        upload.setSizeMax(-1);
//        upload.setHeaderEncoding("Windos-31J");
        System.out.println("request.getContentLength()"+request.getContentLength());
        System.out.println("request.getContextPath()"+request.getContextPath());
        System.out.println("request.getContentLength()||fileUpload.getSizeMax()"+request.getContentLength()+"||"+fileUpload.getSizeMax());
//        request.getContentLength()
//        request.getContextPath()
        System.out.println("request.getCharacterEncoding()"+request.getCharacterEncoding());
        
        items = fileUpload.parseRequest(request); //Parse the request
        System.out.println(items.size()+" :::::size:::::");
		//파람값들을 맵에 셋팅
		processFormField( items );

	}
	
  
	//폼의 필드값을 map에 저장한다.
	private void processFormField(List items) throws Exception
	{
		paramMap = new HashMap();
		Iterator iter = items.iterator();
		while (iter.hasNext()==true) 
		{
			FileItem item = (FileItem) iter.next();
  
			System.out.println("####item Value :"+item);
			if (item.isFormField()) 
			{
				String fieldValue = "";

				// 인코딩 여부.
                boolean doEncode = Boolean.valueOf( properties.getProperty("fileUpload_field_encode") ).booleanValue();
                
                // 인코딩 여부에 따라 인코딩 한다.
                if( doEncode == true ) {
                	String encodeType = properties.getProperty("fileUpload_field_encodeType");
                	fieldValue = item.getString( encodeType );
                	System.out.println(">>>>>>>>>> 필드별 인코딩 : " + encodeType);
                	System.out.println(">>>>>>>>>> 필드 value : "+ fieldValue);
                } else {
                	fieldValue = item.getString();
                }
				
				paramMap.put(item.getFieldName(), fieldValue);
				System.out.println("item.getFieldName(),[fieldValue]:"+item.getFieldName()+"["+fieldValue+"]");
			}else{
				 if(item.getSize()>0){                  //파일이 업로드 되었나 안되었나 체크 size>0이면 업로드 성공
				     String fieldName=item.getFieldName();
				     String fileName=item.getName();
				     String contentType=item.getContentType();
				     boolean isInMemory=item.isInMemory();
				     long sizeInBytes=item.getSize();
				     System.out.println("파일 [fieldName] : "+ fieldName +"<br/>");
				     System.out.println("파일 [fileName] : "+ fileName +"<br/>");
				     System.out.println("파일 [contentType] : "+ contentType +"<br/>");
				     System.out.println("파일 [isInMemory] : "+ isInMemory +"<br/>");
				     System.out.println("파일 [sizeInBytes] : "+ sizeInBytes +"<br/>");
				     
				     try{
				      File uploadedFile=new File(uploadDir,fileName);                                                   //실제 디렉토리에 fileName으로 카피 된다.
				      item.write(uploadedFile);
				      item.delete();                                                                                            //카피 완료후 temp폴더의 temp파일을 제거
				     }catch(IOException ex) {} 
				 }
			}
		}
	}
 
	//하나의 파일사이즈를 체크한다.
	private void chkFileLimit() throws Exception
	{
		Iterator iter = items.iterator();
		while (iter.hasNext()) 
		{
			FileItem item = (FileItem) iter.next();
			if (!item.isFormField()) 
			{
				String fileName = new File(item.getName()).getName();
				long fileSize = item.getSize();
				if(fileName != null && !"".equals(fileName))
				{
					if(fileLimit<fileSize)
					{
						throw new Exception(fileName+" 파일이 " + fileLimit/1024/1024 + "M를 초과하였습니다.\n");
					}
				}
			}
		}
	}
 
 
	/**
	 * request상의 모든파일을 업로드한다.<br>
	 * 파일명의 중복을 피하기 위해 중복될경우 파일명 뒤에 '0'을 붙여 업로드한다.<br>
	 * 업로드후 변경된 파일명과 param들을 map으로 리턴받아 처리한다.
	 * @throws Exception
	 */
	public Map getParamAfterUpload() throws Exception
	{
		System.out.println("==================== getParamAfterUpload() ==================== 시작");
		boolean writeToFile = true; //파일에 쓸것인지 구분 플래그
		Iterator iter = items.iterator();

		chkFileLimit(); //파일들의 사이즈 체크
		
		List fileInfoList = new ArrayList();
		System.out.println("fileInfoList"+fileInfoList.size());
		
		while (iter.hasNext()) 
		{
			System.out.println("###################2");
			FileItem item = (FileItem) iter.next();
			//Process a file upload
			if (!item.isFormField()) 
			{
				String filePath = item.getName();
				
				System.out.println("filePath=" + filePath);
				
				if( filePath != null ) {
					filePath = filePath.replace('\\', '/');
				}
				
				System.out.println("filePath=" + filePath);
				
				File file = new File(filePath);
				String fileName = file.getName();
				System.out.println("fileName=" + fileName);
       
				if(fileName != null && !"".equals(fileName))
				{
					System.out.println("###################1");
					String newFileName = "";
					//파일업로드시...
					if (writeToFile) 
					{
						// 동일한 파일명으로 업로드 될수 있기때문에 파일명이 같을경우 파일명 뒤에 '0'을 붙여 업로드한다.
						newFileName = getReNamedFile(uploadDir.toString() , fileName);
						//newFileName = uploadDir.toString();
						
						File newFile = new File( uploadDir.toString() , newFileName );
						
						FileInfoDO fileInfoDO = new FileInfoDO();
						fileInfoDO.setFlNm(newFileName);
						fileInfoDO.setOrgFileNm(fileName);
						fileInfoDO.setFlPath(uploadDir.getName());
						fileInfoDO.setFlSz(new BigDecimal(item.getSize()));
						fileInfoList.add(fileInfoDO);
						
//						paramMap.put(item.getFieldName(), fileName); //새로운 파일명을 리턴헤주기 위해 맵에 담는다.
//						paramMap.put("new_" + item.getFieldName() , newFile.getName()); //새로운 파일명을 리턴헤주기 위해 맵에 담는다.
						System.out.println("newFile.getName()=" + newFile.getName());
						
						item.write(newFile);        //파일을 쓴다.
						item.getInputStream().close();
						item.getOutputStream().close();
					} 
				}
			}
		}
		
		paramMap.put(FileUploader.FILE_INFO_KEY, fileInfoList);
		
		System.out.println("==================== getParamAfterUpload() ==================== 끝");
		return paramMap;
	}
	
	
	private String getReNamedFile( String path , String fileName ) {
		String newFileName = null;
		
		try {
			File tmpFile = new File( path , fileName );
			
			File dir = new File( tmpFile.getParent() );
			File[] dirFiles = dir.listFiles();
			
			newFileName = fileName;
			
			/*for( int i = 0; i < dirFiles.length; i++ ) {
				String alreadyFileName = dirFiles[i].getName();
				
				if ( (alreadyFileName.equals(newFileName)) && (true == this.bUseDupCheck) ) 
				{
					newFileName = newFileName.substring( 0 , newFileName.lastIndexOf(".") ) + "0" 
										+ newFileName.substring( newFileName.lastIndexOf(".") , newFileName.length());
					
				}
			}*/
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return newFileName;
	}
 
	/**
	 * request상의 param들을 map으로 반환한다.
	 * @return Map
	 */
	public Map getParamMap()
	{
		return paramMap;
	}
	
	public void sample(HttpServletRequest request ) throws ServletException
	{
		try
		{
			
			Properties properties = new Properties();
			properties.put("fileUpload_field_encode" , "true");
			properties.put("fileUpload_field_encodeType" , "UTF-8");
			
			/**
			 * das.properties 파일에서 파일 패스를 읽는다.
			 */
			DasPropHandler handler = DasPropHandler.getInstance();
			
			//게시판타입
			String boardType = request.getParameter("boardType");
			
			String fileUploadPath = null;
			//공지사항
			if(CodeConstants.BoardKind.NOTICE.equals(boardType))
			{
				fileUploadPath = handler.getProperty(DASBusinessConstants.FilePathPropName.NOTICE);
			}
			//묻고답하기
			else if(CodeConstants.BoardKind.QNA.equals(boardType))
			{
				fileUploadPath = handler.getProperty(DASBusinessConstants.FilePathPropName.QNA);
			}
			//신고
			else if(CodeConstants.BoardKind.STATEMENT.equals(boardType))
			{
				fileUploadPath = handler.getProperty(DASBusinessConstants.FilePathPropName.STATEMENT);
			}
			//이용안내
			else if(CodeConstants.BoardKind.USE_INFO.equals(boardType))
			{
				fileUploadPath = handler.getProperty(DASBusinessConstants.FilePathPropName.USE_INFO);
			}
			
			
			FileUploader fileUploader = new FileUploader(request, fileUploadPath , 1024*1024*3 , properties);

			Map map = (Map)fileUploader.getParamAfterUpload();
			String name = (String)map.get("name");
			BoardDO boardDO = new BoardDO();
			boardDO.setCont(name);
			
			
			List fileInfoDOList = (List)map.get(FileUploader.FILE_INFO_KEY);
			boardDO.setFileInfoDOList(fileInfoDOList);
			
			
		}
		catch (Exception e)
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
			throw new ServletException(e);
		}

	}
 
}




