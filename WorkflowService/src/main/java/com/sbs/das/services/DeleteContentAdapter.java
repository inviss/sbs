package com.sbs.das.services;

import java.util.List;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.xml.DeleteRequest;


public interface DeleteContentAdapter {
	/**
	 * <pre>
	 * 아카이브된 컨텐츠에대하여 스토리지의 보관일이 지난 영상을 조회한다.
	 * config.properties에서 'storage.archived.day' 항목에 스토리지 보관일이 설정되어 있다.
	 * 하지만, 사용자가 만료일을 'yyyymmdd' 형식으로 지정하여 요청한다면 우선순위에서 사용자 요청이
	 * 우선이므로 config 파일을 참조하지 않고 사용자 입력값을 적용하게 된다.
	 * </pre>
	 * @throws ServiceException
	 */
	public void archiveExpiredDelete(String coCd, String limitDay) throws ServiceException;
	
	/**
	 * <pre>
	 * 다운로드된 컨텐츠에대하여 스토리지의 보관일이 지난 영상을 조회한다.
	 * config.properties에서 'storage.restored.day' 항목에 스토리지 보관일이 설정되어 있다.
	 * 하지만, 사용자가 만료일을 'yyyymmdd' 형식으로 지정하여 요청한다면 우선순위에서 사용자 요청이
	 * 우선이므로 config 파일을 참조하지 않고 사용자 입력값을 적용하게 된다.
	 * </pre>
	 * @throws ServiceException
	 */
	public void downloadExpiredDelete(String coCd, String limitDay) throws ServiceException;
	
	/**
	 * <pre>
	 * 사용자가 삭제 요청한 컨텐츠를 조회한다.
	 * 삭제요청한 영상은 아카이브가 안된 영상일 수도 있고 아카이브된 영상에 대하여 폐기처리일 수도 있다.
	 * 사용자 요청삭제는 config 정보를 참조하지 않고 현재일 기준으로 조회하며, contents_mapp_tbl의 del_dd를 참조한다.
	 * 하지만, 사용자가 만료일을 'yyyymmdd' 형식으로 지정하여 요청한다면 우선순위에서 사용자 요청이 우선한다.
	 * </pre>
	 * @throws ServiceException
	 */
	public void scrappedDelete(String coCd, String limitDay) throws ServiceException;
	
	/**
	 * <pre>
	 * 사용자가 지정한 컨텐츠를 즉시 삭제한다.
	 * </pre>
	 * @param deleteRequests
	 * @throws ServiceException
	 */
	public void userRequestDelete(List<DeleteRequest> deleteRequests) throws ServiceException;
	
	/**
	 * <pre>
	 * 강제로 삭제한다.
	 * </pre>
	 * @param deleteRequests
	 * @throws ServiceException
	 */
	public void pdsRequestDelete(List<DeleteRequest> deleteRequests) throws ServiceException;
}
