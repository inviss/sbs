package com.sbs.das.web;

import java.rmi.RemoteException;

import javax.jws.WebService;

@WebService
public interface Nevigator {
	
	/**
	 * <pre>
	 * 마스터 정보 및 컨텐츠 메타정보를 신규 저장하는 서비스.
	 * 고화질, 저화질 영상에 대한 메타정보 및 물리적인 영상의 위치정보를 저장한다.
	 * TC에서는 고화질 영상을 저장 요청한 후 반환된 xml에서 parent 정보를 받아서 저화질 영상 정보를 추가한 후 등록 요청한다.
	 * 요청된 메타정보는 ERP에 관련 정보가 존재하여 tape_item_id를 이용하여 회차 정보를 조회하여 저장한다.
	 * </pre>
	 * @param xml
	 * @return string(true, false)
	 * @throws RemoteException
	 */
    public String addClipInfoService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * ERP에 존재하지 않는 영상을 매체변환한다.
     * </pre>
     * @param xml
     * @return xml
     * @throws RemoteException
     */
    public String nonErpAddClipService(String xml) throws RemoteException;
    
    /**
     * 2012-04-21 new
     * <pre>
     * 미디어넷에 포함된 하위 계열사에서 매체변환 영상을 등록.
     * ERP 정보와 연동하지 않고 Excel Data를 수동으로 DB에 import한 후 Das Client에서 청구번호를
     * 이용하여 조회한 후 영상과 연결하여 사용한다.
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String subAddClipService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 스토리지에 존재하는 영상을 아카이브 요청하는 서비스. 동시에 복본 생성 요청여부를 처리한다.
     * info에서 cti_id가 존재한다면('0'이 아니라면) 아카이브 저장 요청이며 copy_to_group 값이 'Y'라면 복본 생성을 요청한다.
     * 하지만, cti_id가 존재한다면 아카이브 백업된 영상이며 복본 생성 요청 처리만 하게 된다.
     * 아카이브 저장시 ID는 [DASConstants.DAS_ARCHIVE_PRIFIX("DAS")+CTI_ID] 체계를 사용한다.
     * </pre>
     * @param xml
     * @return string(true, false)
     * @throws RemoteException
     */
    public String archiveService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * MXF 원본 영상을 이용하여 트랜스코드 요청을 하는 서비스
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String transcodeService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 관리자가 스케쥴러 정상 실행시간외에 재실행을 원할 경우 요청하는 서비스.
     * 실행할 스케쥴러를 개별로 요청할 수도 있고, 모든 스케쥴러를 요청할 수도 있다.
     * 
     * </pre>
     * @param xml
     * @return string(true, false)
     * @throws RemoteException
     */
    public String schedulerForceExecute(String xml) throws RemoteException;
    
    /**
     * <pre>
     * OnAir SDI Ingest에서 아카이브 요청을 하는 서비스.
     * 전달받은 XML 정보를 DAS CMS에 forward한다.
     * </pre>
     * @param xml
     * @return string(true, false)
     * @throws RemoteException
     */
    public String archiveSchedulerService(String xml) throws RemoteException;
    
    public String fileIngestService(String xml) throws RemoteException;
    public String nleIngestService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 아카이브에 저장된 영상을 복원 요청하는 서비스.
	 * cart_seq는 ','를 구분자로 하여 요청 수만큼 묶어서 전달한다.
	 * </pre>
     * @param xml
     * @return string(true, false)
     * @throws RemoteException
     */
    public String downloadService(String xml) throws RemoteException;
    
    /**
     * <pre>
	 * Batch TC에서 mp4 생성이 완료 되었을경우 호출하는 인터페이스.
	 * TC 완료 상태값을 변경 한다.
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String reportService(String xml) throws RemoteException;
    
    public String ingestReportService(String xml) throws RemoteException;
    public String archiveReportService(String xml) throws RemoteException;
    public String serviceTest(String xml) throws RemoteException;
    
    /**
     * <pre>
     * Diva에서 아카이브 백업 및 복원 진행상태를 받는다.
     * 진행중인 모든 작업을 일괄로 전송받고 <status/> 로 감싸여 보내준다. job_id로 작업을 구분하게 되며
     * job_id : 005[archive_save], 006[archvie_back], 007[archive_restore], 008[archive_delete], 009[tape_out]
     * 에러가 발생했을경우 에러코드 및 에러 메세지를 받는다.
     * </pre>
     * @param xml
     * @return string(true, false)
     * @throws RemoteException
     */
    public String archiveStatus(String xml) throws RemoteException;

    /**
     * <pre>
     * 한 컨텐츠에대하여 현재의 아카이브 상태를 조회하여 반환한다.
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String getStatus(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 원본 영상에 문제가 있을경우 복본 영상을 이용하여 원본을 재생성 하도록 요청.
     * 복복(h.264)을 다운로드 받아 h.264 -> MXF 변환한 후 재아카이브.
     * 요청은 사용자가 하지만, 다운로드부터 재아카이브까지는 내부 프로세스로 동작.
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String recoveryService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String changePriorityService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String cancelService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String deleteService(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 현재 사용중인 폴더별 스토리지 사용량을 보여준다.
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String findDiskQuota(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 지정한 폴더의 사용제한 용량을 설정한다.
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String updateDiskQuota(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 서버의 CPU 및 Memory 사용량을 보여준다.
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String resourceUseInfo(String xml) throws RemoteException;
    
    /**
     * <pre>
     * 사용자 수동등록시 입력하는 메타정보를 받아 DB 저장 후 MASTER_ID를 생성하여 반환
     * </pre>
     * @param xml
     * @return
     * @throws RemoteException
     */
    public String manualMasterRegister(String xml) throws RemoteException;
    
    public void testMethod(String xml) throws RemoteException;
    
}
