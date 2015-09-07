package com.app.das.webservices;

public class PDASServicesProxy implements com.app.das.webservices.PDASServices {
  private String _endpoint = null;
  private com.app.das.webservices.PDASServices pDASServices = null;
  
  public PDASServicesProxy() {
    _initPDASServicesProxy();
  }
  
  public PDASServicesProxy(String endpoint) {
    _endpoint = endpoint;
    _initPDASServicesProxy();
  }
  
  private void _initPDASServicesProxy() {
    try {
      pDASServices = (new com.app.das.webservices.PDASServicesServiceLocator()).getPDASServices();
      if (pDASServices != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)pDASServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)pDASServices)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (pDASServices != null)
      ((javax.xml.rpc.Stub)pDASServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.app.das.webservices.PDASServices getPDASServices() {
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices;
  }
  
  public java.lang.String getString(java.lang.String name) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getString(name);
  }
  
  public java.lang.String getCode(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCode(codeDO);
  }
  
  public java.lang.String getAuthentication(java.lang.String ID, java.lang.String password, java.lang.String AD_DOMAIN) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAuthentication(ID, password, AD_DOMAIN);
  }
  
  public int getOrderInfo() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getOrderInfo();
  }
  
  public int cancelJob(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.cancelJob(xml);
  }
  
  public java.lang.String getScenario(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getScenario(masterId);
  }
  
  public int updateRoleInfo(java.lang.String roleInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateRoleInfo(roleInfoDO);
  }
  
  public int getBill(int bill) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getBill(bill);
  }
  
  public java.lang.String getDecryption(java.lang.String strSSOKey, java.lang.String strMacHex, java.lang.String strSSOText) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDecryption(strSSOKey, strMacHex, strSSOText);
  }
  
  public java.lang.String getEncription(java.lang.String strSSOKey, java.lang.String strMacHex, java.lang.String strSSOEnc) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getEncription(strSSOKey, strMacHex, strSSOEnc);
  }
  
  public java.lang.String getLogin(java.lang.String userId, java.lang.String password) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getLogin(userId, password);
  }
  
  public java.lang.String getCartInfo(long cartNo, java.lang.String reqUserId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCartInfo(cartNo, reqUserId);
  }
  
  public java.lang.String getVideoPageInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getVideoPageInfo(masterId);
  }
  
  public java.lang.String getFilePathInfo(long contentId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getFilePathInfo(contentId);
  }
  
  public java.lang.String getVideoPageMetaInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getVideoPageMetaInfo(masterId);
  }
  
  public java.lang.String getVideoPageContentsInfoList(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getVideoPageContentsInfoList(masterId);
  }
  
  public int insertBoardInfo(java.lang.String boardInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertBoardInfo(boardInfoDO);
  }
  
  public int updateBoardInfo(java.lang.String boardInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateBoardInfo(boardInfoDO);
  }
  
  public void deleteBoardInfo(int boardId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    pDASServices.deleteBoardInfo(boardId);
  }
  
  public java.lang.String getBoardInfo(java.lang.String boardDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getBoardInfo(boardDO);
  }
  
  public java.lang.String getPreviewInfo(int master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPreviewInfo(master_id);
  }
  
  public java.lang.String getPreviewAttachInfo(int master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPreviewAttachInfo(master_id);
  }
  
  public java.lang.String insertErrorInfo(java.lang.String errorRegisterDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertErrorInfo(errorRegisterDO);
  }
  
  public int insertPhotoinfo(java.lang.String photoInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPhotoinfo(photoInfoDO);
  }
  
  public java.lang.String insertCornerinfo(long masterId, java.lang.String cornerInfo) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertCornerinfo(masterId, cornerInfo);
  }
  
  public int insertContentsMappinfo(long masterId, java.lang.String contentMappInfo) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertContentsMappinfo(masterId, contentMappInfo);
  }
  
  public int insertMyCatalog(java.lang.String myCatalogDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertMyCatalog(myCatalogDO);
  }
  
  public int deleteMyCatalog(java.lang.String myCatalogDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteMyCatalog(myCatalogDO);
  }
  
  public java.lang.String insertAnnotinfo(long masterId, java.lang.String annotInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertAnnotinfo(masterId, annotInfoDO);
  }
  
  public java.lang.String getCodeList(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCodeList(codeDO);
  }
  
  public java.lang.String getCodeInfo(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCodeInfo(codeDO);
  }
  
  public int insertCodeInfo(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertCodeInfo(codeDO);
  }
  
  public int updateCodeInfo(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateCodeInfo(codeDO);
  }
  
  public int deleteCodeInfo(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteCodeInfo(codeDO);
  }
  
  public int insertScenario(java.lang.String scenarioDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertScenario(scenarioDO);
  }
  
  public int deleteScenario(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteScenario(master_id);
  }
  
  public int insertRelationMaster(long parent_master_id, long child_master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertRelationMaster(parent_master_id, child_master_id);
  }
  
  public int deleteRelationMaster(long parent_master_id, long child_master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteRelationMaster(parent_master_id, child_master_id);
  }
  
  public java.lang.String getRelationMaster(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRelationMaster(masterId);
  }
  
  public java.lang.String getRelationTotaly(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRelationTotaly(masterId);
  }
  
  public java.lang.String getScenario2(java.lang.String senario) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getScenario2(senario);
  }
  
  public java.lang.String getMyCatalogBySort(java.lang.String myCatalogDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMyCatalogBySort(myCatalogDO);
  }
  
  public java.lang.String getMyCatalog(java.lang.String myCatalogDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMyCatalog(myCatalogDO);
  }
  
  public java.lang.String deletePhotoFiles(java.lang.String photoInfo) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deletePhotoFiles(photoInfo);
  }
  
  public java.lang.String insertAttachFile(java.lang.String attachFileInfo) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertAttachFile(attachFileInfo);
  }
  
  public int updateDownCartState(long cartNo, java.lang.String cartState, java.lang.String title) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateDownCartState(cartNo, cartState, title);
  }
  
  public int updateDownCart(java.lang.String downCartDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateDownCart(downCartDO);
  }
  
  public int updateCartContInfo(java.lang.String cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateCartContInfo(cartContDO);
  }
  
  public int updateStCartContInfo(java.lang.String cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateStCartContInfo(cartContDO);
  }
  
  public int updateDatastatCd(long masterID, java.lang.String secArchId, java.lang.String secArchNm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateDatastatCd(masterID, secArchId, secArchNm);
  }
  
  public int updateVd_qlty(int ctID, java.lang.String vd_qlty, java.lang.String asp_rto_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateVd_qlty(ctID, vd_qlty, asp_rto_cd);
  }
  
  public int updateMetadataStatusCd(long masterID, java.lang.String statCd, java.lang.String modrid, java.lang.String moddt, java.lang.String lock_stat_cd, java.lang.String error_stat_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateMetadataStatusCd(masterID, statCd, modrid, moddt, lock_stat_cd, error_stat_cd);
  }
  
  public int updateModUserid(long masterId, java.lang.String userId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateModUserid(masterId, userId);
  }
  
  public int updateSDIngestStatus(java.lang.String itemId, java.lang.String ingestStatus) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateSDIngestStatus(itemId, ingestStatus);
  }
  
  public java.lang.String insertTapeinfo(long masterId, java.lang.String IDhead, java.lang.String userId, java.lang.String year) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertTapeinfo(masterId, IDhead, userId, year);
  }
  
  public int getLogRcdPeriod(int dasEqId, java.lang.String dasEqPsCd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getLogRcdPeriod(dasEqId, dasEqPsCd);
  }
  
  public void deleteAllCartInfo(long cartNo) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    pDASServices.deleteAllCartInfo(cartNo);
  }
  
  public java.lang.String deleteContentFiles(int days) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteContentFiles(days);
  }
  
  public java.lang.String deleteKfrmFiles(java.lang.String krfmFileList) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteKfrmFiles(krfmFileList);
  }
  
  public void deleteCartInfoList(long cartNo, int seq) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    pDASServices.deleteCartInfoList(cartNo, seq);
  }
  
  public void deleteContentItemList(java.lang.String masterIdGRP) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    pDASServices.deleteContentItemList(masterIdGRP);
  }
  
  public java.lang.String getAnnotInfoInfoList(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAnnotInfoInfoList(masterId);
  }
  
  public java.lang.String getCartContList(long cartNo) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCartContList(cartNo);
  }
  
  public java.lang.String getAttachFileInfoList(long mothrId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAttachFileInfoList(mothrId);
  }
  
  public java.lang.String getContentsPreInfoList(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getContentsPreInfoList(masterId);
  }
  
  public java.lang.String getClipHeaderImgInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getClipHeaderImgInfo(masterId);
  }
  
  public java.lang.String getContentsInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getContentsInfo(masterId);
  }
  
  public java.lang.String getCornerHeaderImgInfo(long ctId, long cnId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCornerHeaderImgInfo(ctId, cnId);
  }
  
  public java.lang.String getCornerInfoList(long masterId, java.lang.String keyWord) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCornerInfoList(masterId, keyWord);
  }
  
  public java.lang.String getFLIngestCommonCodeList() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getFLIngestCommonCodeList();
  }
  
  public java.lang.String getFlIngestLastCommandList() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getFlIngestLastCommandList();
  }
  
  public java.lang.String getIngestServerList(java.lang.String clfCd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getIngestServerList(clfCd);
  }
  
  public java.lang.String getSDIngestCommonCodeList() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSDIngestCommonCodeList();
  }
  
  public java.lang.String getSDIngestStatusInfo(java.lang.String tapeItemId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSDIngestStatusInfo(tapeItemId);
  }
  
  public java.lang.String getSDIngestRefreshTapeInfo(java.lang.String tapeId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSDIngestRefreshTapeInfo(tapeId);
  }
  
  public java.lang.String getTapeOutIngestCartItemInfo(long cartNo) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTapeOutIngestCartItemInfo(cartNo);
  }
  
  public java.lang.String getTapeOutIngestCommonCodeList() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTapeOutIngestCommonCodeList();
  }
  
  public java.lang.String getTapeOutIngestDownCartInfoList(java.lang.String reqUserId, int resolution, boolean reqDtChk, java.lang.String reqDt) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTapeOutIngestDownCartInfoList(reqUserId, resolution, reqDtChk, reqDt);
  }
  
  public java.lang.String getSDTapeInfoList(java.lang.String reqNum, java.lang.String pgmNm, java.lang.String ingestStatus, boolean onAirDateSearch, java.lang.String onAirDateStart, java.lang.String onAirDateEnd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSDTapeInfoList(reqNum, pgmNm, ingestStatus, onAirDateSearch, onAirDateStart, onAirDateEnd);
  }
  
  public java.lang.String getProgramInfoList(java.lang.String pgmNm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getProgramInfoList(pgmNm);
  }
  
  public java.lang.String getPgmContentsInfoList(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPgmContentsInfoList(masterId);
  }
  
  public java.lang.String getKeyFrameInfoInfoList(long ctId, int fromSeq, int toSeq) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getKeyFrameInfoInfoList(ctId, fromSeq, toSeq);
  }
  
  public java.lang.String getManagementInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getManagementInfo(masterId);
  }
  
  public java.lang.String getMediaInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMediaInfo(masterId);
  }
  
  public java.lang.String getModDatastatcd(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getModDatastatcd(masterId);
  }
  
  public java.lang.String getPhotoInfoList(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPhotoInfoList(masterId);
  }
  
  public java.lang.String getReflectionInfoList(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getReflectionInfoList(masterId);
  }
  
  public java.lang.String getStorageIP() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getStorageIP();
  }
  
  public java.lang.String getCommonInfoList(java.lang.String clfCd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCommonInfoList(clfCd);
  }
  
  public java.lang.String getTapeInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTapeInfo(masterId);
  }
  
  public java.lang.String getMetadataInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMetadataInfo(masterId);
  }
  
  public java.lang.String getErrorInfoList(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getErrorInfoList(masterId);
  }
  
  public java.lang.String getModeUserInfoList(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getModeUserInfoList(masterId);
  }
  
  public java.lang.String getPgmInfoFromName(java.lang.String programInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPgmInfoFromName(programInfoDO);
  }
  
  public java.lang.String getPgmInfoFromName2(java.lang.String pgmNm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPgmInfoFromName2(pgmNm);
  }
  
  public java.lang.String getLastPgmInfoXmllist(java.lang.String programDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getLastPgmInfoXmllist(programDO);
  }
  
  public java.lang.String getLastPgmInfolist(java.lang.String pgmNm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getLastPgmInfolist(pgmNm);
  }
  
  public java.lang.String getLastPgmInfolistByPgmId(long pgmId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getLastPgmInfolistByPgmId(pgmId);
  }
  
  public java.lang.String getLastPgmInfolistByPgmId2(long pgmId, java.lang.String brd_dd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getLastPgmInfolistByPgmId2(pgmId, brd_dd);
  }
  
  public java.lang.String getPgmInfoFromMasterid(int episNo, long pgmId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPgmInfoFromMasterid(episNo, pgmId);
  }
  
  public java.lang.String getPlayMediaInfo(long CTI_ID) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPlayMediaInfo(CTI_ID);
  }
  
  public java.lang.String getPlayContentInfo(long masterID) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPlayContentInfo(masterID);
  }
  
  public java.lang.String getPgm_cd() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPgm_cd();
  }
  
  public int deleteAttachFile(java.lang.String attachFilename, java.lang.String file_type, java.lang.String clf_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteAttachFile(attachFilename, file_type, clf_cd);
  }
  
  public int insertPhotoDownloadInfo(long phot_ID, java.lang.String REQ_ID, long PGM_ID) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPhotoDownloadInfo(phot_ID, REQ_ID, PGM_ID);
  }
  
  public int insertPhotoDownInfo(java.lang.String photo) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPhotoDownInfo(photo);
  }
  
  public int updateContentMediaInfo(long master_ID, java.lang.String aud_type_cd, java.lang.String record_type_cd, java.lang.String me_cd, java.lang.String color_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateContentMediaInfo(master_ID, aud_type_cd, record_type_cd, me_cd, color_cd);
  }
  
  public int updateDatacdWithMasterid_XML(java.lang.String strXML) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateDatacdWithMasterid_XML(strXML);
  }
  
  public java.lang.String insertERPTapeInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertERPTapeInfo(xml);
  }
  
  public java.lang.String updateERPTapeInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateERPTapeInfo(xml);
  }
  
  public java.lang.String getUserAuthCD(java.lang.String userID) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getUserAuthCD(userID);
  }
  
  public java.lang.String getDownCartList(java.lang.String reqUsrID, java.lang.String dateStart, java.lang.String dateEnd, java.lang.String down_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDownCartList(reqUsrID, dateStart, dateEnd, down_nm);
  }
  
  public java.lang.String getBasicPageInfo(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getBasicPageInfo(masterId);
  }
  
  public java.lang.String recreateWMV(long cti_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateWMV(cti_id);
  }
  
  public java.lang.String recreateWMV_NM(long ct_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateWMV_NM(ct_id, user_nm);
  }
  
  public java.lang.String recreateWMV_KFRM(long ct_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateWMV_KFRM(ct_id, user_nm);
  }
  
  public java.lang.String recreateKFRM(long ct_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateKFRM(ct_id, user_nm);
  }
  
  public int unlockByUserID(java.lang.String strUserID) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.unlockByUserID(strUserID);
  }
  
  public java.lang.String getMergedFilenames(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMergedFilenames(master_id);
  }
  
  public java.lang.String getMasterDataTotaly(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMasterDataTotaly(xml);
  }
  
  public java.lang.String getMasterDataAll(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMasterDataAll(xml);
  }
  
  public java.lang.String getRelationScean(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRelationScean(master_id);
  }
  
  public int multiLockUnlock(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.multiLockUnlock(xml);
  }
  
  public java.lang.String getStoryboardData(long masterId, java.lang.String keyword) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getStoryboardData(masterId, keyword);
  }
  
  public java.lang.String getBoardList(java.lang.String boardInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getBoardList(boardInfoDO);
  }
  
  public java.lang.String getOutsiderEmployeeRoleList(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getOutsiderEmployeeRoleList(employeeInfoDO);
  }
  
  public int updateOutEmployeeRole(java.lang.String nonEmployeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateOutEmployeeRole(nonEmployeeInfoDO);
  }
  
  public int amendPassword(java.lang.String userId, java.lang.String beforePasswd, java.lang.String afterPasswd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.amendPassword(userId, beforePasswd, afterPasswd);
  }
  
  public int insertNonEmployeeRole(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertNonEmployeeRole(employeeInfoDO);
  }
  
  public int deleteScreenAuthentication(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteScreenAuthentication(codeDO);
  }
  
  public java.lang.String getTotalChangelist(java.lang.String programDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTotalChangelist(programDO);
  }
  
  public java.lang.String getEmployeeList(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getEmployeeList(employeeInfoDO);
  }
  
  public int updateEmployeeRole(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateEmployeeRole(employeeInfoDO);
  }
  
  public int updateEmployeeRoleYN(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateEmployeeRoleYN(employeeInfoDO);
  }
  
  public java.lang.String insertEmployeeRole(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertEmployeeRole(employeeInfoDO);
  }
  
  public java.lang.String getDisCardList(java.lang.String discardDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDisCardList(discardDO);
  }
  
  public int insertDisuse(java.lang.String discardDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertDisuse(discardDO);
  }
  
  public int cancelDisuse(int master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.cancelDisuse(master_id);
  }
  
  public int cancelDisuse2(java.lang.String master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.cancelDisuse2(master_id);
  }
  
  public int insertUse(java.lang.String discardDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertUse(discardDO);
  }
  
  public java.lang.String getHyenDisCardList(java.lang.String discardDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getHyenDisCardList(discardDO);
  }
  
  public java.lang.String getHyenUseList(java.lang.String discardDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getHyenUseList(discardDO);
  }
  
  public java.lang.String getJanrCodeList(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getJanrCodeList(codeDO);
  }
  
  public int creatBcode(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.creatBcode(codeDO);
  }
  
  public int creatMcode(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.creatMcode(codeDO);
  }
  
  public int creatScode(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.creatScode(codeDO);
  }
  
  public int updateBcode(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateBcode(codeDO);
  }
  
  public int updateMcode(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateMcode(codeDO);
  }
  
  public int updateScode(java.lang.String codeDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateScode(codeDO);
  }
  
  public java.lang.String getPgmList(java.lang.String programInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPgmList(programInfoDO);
  }
  
  public java.lang.String getPgmInfo(java.lang.String pgm_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPgmInfo(pgm_cd);
  }
  
  public java.lang.String getParentsCD(java.lang.String pgm_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getParentsCD(pgm_cd);
  }
  
  public int insertPgmcd(java.lang.String programInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPgmcd(programInfoDO);
  }
  
  public java.lang.String getSearchRelationInfo(java.lang.String programDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSearchRelationInfo(programDO);
  }
  
  public int updatePgmcd(java.lang.String programInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updatePgmcd(programInfoDO);
  }
  
  public java.lang.String getSubsiServerList(java.lang.String subsiInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSubsiServerList(subsiInfoDO);
  }
  
  public int insertSubsiServer(java.lang.String subsiInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertSubsiServer(subsiInfoDO);
  }
  
  public int updateSubsiServer(java.lang.String subsiInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateSubsiServer(subsiInfoDO);
  }
  
  public java.lang.String getCopyList(java.lang.String copyDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCopyList(copyDO);
  }
  
  public int insertCopy(java.lang.String copyDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertCopy(copyDO);
  }
  
  public int updateCopy(java.lang.String copyDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateCopy(copyDO);
  }
  
  public java.lang.String getDownloadRequestList(java.lang.String commonDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDownloadRequestList(commonDO);
  }
  
  public java.lang.String getDownloadRequestDetailList(java.lang.String cartNo, java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDownloadRequestDetailList(cartNo, user_id);
  }
  
  public int updateDownloadRequestDetail(java.lang.String cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateDownloadRequestDetail(cartContDO);
  }
  
  public int updateDownloadRequestOutSourcingDetail(java.lang.String cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateDownloadRequestOutSourcingDetail(cartContDO);
  }
  
  public java.lang.String getSTAT_TOTAL_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_TOTAL_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_COL_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_COL_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_DISUSE_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_DISUSE_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_ARRA_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_ARRA_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_GNR_USE_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_GNR_USE_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_GNR_USE2_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_GNR_USE2_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PGM_USE_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PGM_USE_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PHOT_COL_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PHOT_COL_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PHOT_REG_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PHOT_REG_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PHOT_TOTAL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PHOT_TOTAL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PHOT_PGM_TOTAL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PHOT_PGM_TOTAL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PHOT_DISUSE_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PHOT_DISUSE_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PHOT_USE_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PHOT_USE_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_GNR_ARCH_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_GNR_ARCH_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PGM_ARCH_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PGM_ARCH_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_DEPT_USE_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_DEPT_USE_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_DEPT_ARCH_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_DEPT_ARCH_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_DEPT_ARCH_DTL_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_DEPT_ARCH_DTL_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_DEPT_ARCH_REQ_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_DEPT_ARCH_REQ_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PGM_ARCH_DTL_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PGM_ARCH_DTL_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String getSTAT_PGM_ARCH_REQ_TBL_List(java.lang.String statisticsConditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSTAT_PGM_ARCH_REQ_TBL_List(statisticsConditionDO);
  }
  
  public java.lang.String insertAddTask(java.lang.String addTask) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertAddTask(addTask);
  }
  
  public java.lang.String completeDown(int num) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.completeDown(num);
  }
  
  public java.lang.String completeArchive(java.lang.String job_status, long cti_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.completeArchive(job_status, cti_id);
  }
  
  public java.lang.String getTmStatus(int taskID) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTmStatus(taskID);
  }
  
  public int getTmStatusAll(java.lang.String getTmStatusAll) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTmStatusAll(getTmStatusAll);
  }
  
  public int updateTmStatusAll(java.lang.String getTmStatusAll) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateTmStatusAll(getTmStatusAll);
  }
  
  public java.lang.String getPhotoList(int master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPhotoList(master_id);
  }
  
  public java.lang.String getAttachPhotoList(java.lang.String photInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAttachPhotoList(photInfoDO);
  }
  
  public int updatPhotoCount(java.lang.String photInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updatPhotoCount(photInfoDO);
  }
  
  public int insertAttachPhotoinfo(java.lang.String photoInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertAttachPhotoinfo(photoInfoDO);
  }
  
  public java.lang.String getDepInfoList(java.lang.String depInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDepInfoList(depInfoDO);
  }
  
  public java.lang.String getDepInfo() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDepInfo();
  }
  
  public java.lang.String getDepCocdInfo(java.lang.String cocd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDepCocdInfo(cocd);
  }
  
  public java.lang.String getDepList(java.lang.String depInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDepList(depInfoDO);
  }
  
  public int insertDepInfo(java.lang.String depInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertDepInfo(depInfoDO);
  }
  
  public int updateDepInfo(java.lang.String depInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateDepInfo(depInfoDO);
  }
  
  public java.lang.String getAuthorInfoList(java.lang.String authorDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAuthorInfoList(authorDO);
  }
  
  public int updatAuthorInfo(java.lang.String authorDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updatAuthorInfo(authorDO);
  }
  
  public java.lang.String getERPAppointList(java.lang.String erpappointDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getERPAppointList(erpappointDO);
  }
  
  public java.lang.String getERPAppointMaxSeq() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getERPAppointMaxSeq();
  }
  
  public int insertERPAppoint(java.lang.String erpappointDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertERPAppoint(erpappointDO);
  }
  
  public int updateTotalChange(java.lang.String totalChangeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateTotalChange(totalChangeInfoDO);
  }
  
  public java.lang.String getUseInfoList(java.lang.String useInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getUseInfoList(useInfoDO);
  }
  
  public java.lang.String getProgramInfo(java.lang.String title) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getProgramInfo(title);
  }
  
  public java.lang.String getAvailable() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAvailable();
  }
  
  public java.lang.String getTodayList(java.lang.String todayDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTodayList(todayDO);
  }
  
  public java.lang.String getGoodList(java.lang.String goodDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getGoodList(goodDO);
  }
  
  public java.lang.String getParentsInfo(java.lang.String pgm_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getParentsInfo(pgm_nm);
  }
  
  public java.lang.String getScanDisk() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getScanDisk();
  }
  
  public java.lang.String getStorageInfo(java.lang.String storageDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getStorageInfo(storageDO);
  }
  
  public java.lang.String getPreProcessing(java.lang.String preProcessingDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPreProcessing(preProcessingDO);
  }
  
  public java.lang.String getArchPreProcessing(java.lang.String conditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getArchPreProcessing(conditionDO);
  }
  
  public int insertMetadat(java.lang.String metadataMstInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertMetadat(metadataMstInfoDO);
  }
  
  public int insertCopyMetadat(java.lang.String metadataMstInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertCopyMetadat(metadataMstInfoDO);
  }
  
  public int insertPdsPgmInfoAll(java.lang.String pdsInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPdsPgmInfoAll(pdsInfoDO);
  }
  
  public int insertPdsPgmInfo(java.lang.String pdsInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPdsPgmInfo(pdsInfoDO);
  }
  
  public int insertPdsPgmUserInfo(java.lang.String pgmUserInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPdsPgmUserInfo(pgmUserInfoDO);
  }
  
  public int insertPdsPgmUserInfoAll(java.lang.String pgmUserInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPdsPgmUserInfoAll(pgmUserInfoDO);
  }
  
  public int insertPdsMappInfo(java.lang.String pdsMappDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPdsMappInfo(pdsMappDO);
  }
  
  public int insertNLEandDTL(long ct_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertNLEandDTL(ct_id);
  }
  
  public long insertDTL(long ct_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertDTL(ct_id);
  }
  
  public java.lang.String getMyDownloadRequestList(java.lang.String commonDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMyDownloadRequestList(commonDO);
  }
  
  public java.lang.String getMyDownloadRequestDetailList(java.lang.String cartNo, java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMyDownloadRequestDetailList(cartNo, user_id);
  }
  
  public int deleteNLE(long ct_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteNLE(ct_id);
  }
  
  public int deleteNLEForDown(long cart_no) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteNLEForDown(cart_no);
  }
  
  public java.lang.String getSearchText(java.lang.String searchInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSearchText(searchInfoDO);
  }
  
  public int insertERPUserInfo(java.lang.String erpappointDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertERPUserInfo(erpappointDO);
  }
  
  public java.lang.String insertPassword(java.lang.String pawd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPassword(pawd);
  }
  
  public java.lang.String updateInitPassword(java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateInitPassword(user_id);
  }
  
  public java.lang.String getRelationLink(long masterId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRelationLink(masterId);
  }
  
  public int updateERPAppoint(java.lang.String erpappointDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateERPAppoint(erpappointDO);
  }
  
  public int insertOtherUserInfo(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertOtherUserInfo(employeeInfoDO);
  }
  
  public int insertOtherDepInfo(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertOtherDepInfo(employeeInfoDO);
  }
  
  public java.lang.String getMyDownloadAprroveList(java.lang.String commonDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMyDownloadAprroveList(commonDO);
  }
  
  public java.lang.String getMyDownloadAprroveDetailList(java.lang.String cartNo, java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMyDownloadAprroveDetailList(cartNo, user_id);
  }
  
  public java.lang.String getMainList() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMainList();
  }
  
  public java.lang.String getOtherEmployeeList(java.lang.String sbs_user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getOtherEmployeeList(sbs_user_id);
  }
  
  public java.lang.String getOhterDepInfoList(java.lang.String cocd, java.lang.String dept_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getOhterDepInfoList(cocd, dept_cd);
  }
  
  public java.lang.String getMainKeyList() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMainKeyList();
  }
  
  public int insertRealEmployeeRole(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertRealEmployeeRole(employeeInfoDO);
  }
  
  public int insertPDSArchive(java.lang.String pdasArchiveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPDSArchive(pdasArchiveDO);
  }
  
  public java.lang.String insertIfCmsArchive(java.lang.String pdasArchiveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertIfCmsArchive(pdasArchiveDO);
  }
  
  public int updatePDSArchiveStatus(java.lang.String pdasArchiveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updatePDSArchiveStatus(pdasArchiveDO);
  }
  
  public java.lang.String updateIfCmsArchiveStatus(java.lang.String pdasArchiveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateIfCmsArchiveStatus(pdasArchiveDO);
  }
  
  public java.lang.String updateAccept(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateAccept(master_id);
  }
  
  public java.lang.String updateArrange(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateArrange(master_id);
  }
  
  public java.lang.String updateArrange2(long master_id, java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateArrange2(master_id, user_id);
  }
  
  public java.lang.String getPDSList(java.lang.String pdsDownDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getPDSList(pdsDownDO);
  }
  
  public java.lang.String getNDSList(java.lang.String ndsDownDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getNDSList(ndsDownDO);
  }
  
  public java.lang.String getRepBaseInfo(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRepBaseInfo(master_id);
  }
  
  public java.lang.String isValidUserWithToken(java.lang.String tokenDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.isValidUserWithToken(tokenDO);
  }
  
  public java.lang.String isValidUser(java.lang.String tokenDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.isValidUser(tokenDO);
  }
  
  public java.lang.String insertReqJobTC(java.lang.String tcBeanDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertReqJobTC(tcBeanDO);
  }
  
  public java.lang.String updateTcState(java.lang.String tcBeanDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateTcState(tcBeanDO);
  }
  
  public java.lang.String insertReqComTc(java.lang.String tcBeanDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertReqComTc(tcBeanDO);
  }
  
  public int updateRealEmployeeRole(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateRealEmployeeRole(employeeInfoDO);
  }
  
  public int updateRealEmployeeRoleYN(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateRealEmployeeRoleYN(employeeInfoDO);
  }
  
  public java.lang.String updateRealEmployeeRoleCharYN(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateRealEmployeeRoleCharYN(employeeInfoDO);
  }
  
  public java.lang.String insertPdsFolderAll2(java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPdsFolderAll2(user_id);
  }
  
  public int insertPdsFolderAll() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertPdsFolderAll();
  }
  
  public java.lang.String insertNdsFolderAll(java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertNdsFolderAll(user_id);
  }
  
  public java.lang.String getSupHeadList(java.lang.String COCD) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSupHeadList(COCD);
  }
  
  public java.lang.String getAttachFileInfo(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAttachFileInfo(master_id);
  }
  
  public int updateErrorDownCart(java.lang.String downCartDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateErrorDownCart(downCartDO);
  }
  
  public java.lang.String getSearchTextForDetail(java.lang.String searchInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSearchTextForDetail(searchInfoDO);
  }
  
  public java.lang.String getSearchTextForKey(java.lang.String searchInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSearchTextForKey(searchInfoDO);
  }
  
  public java.lang.String getBoardAtaachInfo(int boardId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getBoardAtaachInfo(boardId);
  }
  
  public int insertBoardAtaachInfo(java.lang.String fileInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertBoardAtaachInfo(fileInfoDO);
  }
  
  public int updateBoardAtaachInfo(java.lang.String fileInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateBoardAtaachInfo(fileInfoDO);
  }
  
  public java.lang.String updateRsv_Prd_DD(java.lang.String rsv_prd_cd, long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateRsv_Prd_DD(rsv_prd_cd, master_id);
  }
  
  public int deleteBoardAttachFile(java.lang.String attachFilename, java.lang.String fl_path, int boardId) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteBoardAttachFile(attachFilename, fl_path, boardId);
  }
  
  public java.lang.String getSupHtpoList(java.lang.String COCD) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSupHtpoList(COCD);
  }
  
  public java.lang.String getSupHeadList2(java.lang.String deptcd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSupHeadList2(deptcd);
  }
  
  public java.lang.String getDepinfoList(java.lang.String deptcd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDepinfoList(deptcd);
  }
  
  public java.lang.String getDepinfoForuserList(java.lang.String deptcd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDepinfoForuserList(deptcd);
  }
  
  public java.lang.String getApproveInfoList(java.lang.String apprveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getApproveInfoList(apprveDO);
  }
  
  public java.lang.String getApproveInfo(java.lang.String apprveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getApproveInfo(apprveDO);
  }
  
  public int insertApproveInfo(java.lang.String apprveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertApproveInfo(apprveDO);
  }
  
  public void deleteApproveInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    pDASServices.deleteApproveInfo(xml);
  }
  
  public int deleteApproveInfo2(java.lang.String user_no, java.lang.String dept_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteApproveInfo2(user_no, dept_cd);
  }
  
  public java.lang.String getProgramInfo2(java.lang.String title) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getProgramInfo2(title);
  }
  
  public java.lang.String recreateWMV_NMforClient(long ct_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateWMV_NMforClient(ct_id, user_nm);
  }
  
  public java.lang.String recreateWMV_NMforMainSean(long master_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateWMV_NMforMainSean(master_id, user_nm);
  }
  
  public java.lang.String recreateWMV_KFRMforClient(long ct_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateWMV_KFRMforClient(ct_id, user_nm);
  }
  
  public java.lang.String recreateWMV_KFRMMainSean(long master_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateWMV_KFRMMainSean(master_id, user_nm);
  }
  
  public java.lang.String recreateKFRMforClient(long ct_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateKFRMforClient(ct_id, user_nm);
  }
  
  public java.lang.String recreateKFRMMainSean(long master_id, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.recreateKFRMMainSean(master_id, user_nm);
  }
  
  public java.lang.String getEmployeeListForApp(java.lang.String dep_cd, java.lang.String user_nm) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getEmployeeListForApp(dep_cd, user_nm);
  }
  
  public java.lang.String getBoardInfoForPopUp(java.lang.String today) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getBoardInfoForPopUp(today);
  }
  
  public int updatePhotInfo(java.lang.String photoInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updatePhotInfo(photoInfoDO);
  }
  
  public java.lang.String getRoleForLogin(java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRoleForLogin(user_id);
  }
  
  public int insertArchiveReq(java.lang.String archiveReqDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertArchiveReq(archiveReqDO);
  }
  
  public java.lang.String updateArchiveReq(java.lang.String archiveReqDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateArchiveReq(archiveReqDO);
  }
  
  public java.lang.String insertComArchivereq(java.lang.String archiveReqDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertComArchivereq(archiveReqDO);
  }
  
  public int updatePasswd(java.lang.String employeeInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updatePasswd(employeeInfoDO);
  }
  
  public int insertNleArchive(java.lang.String nleDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertNleArchive(nleDO);
  }
  
  public java.lang.String testServiceTest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testServiceTest(arg0);
  }
  
  public java.lang.String testDownloadService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testDownloadService(arg0);
  }
  
  public java.lang.String testReportService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testReportService(arg0);
  }
  
  public java.lang.String testGetResArchieve(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testGetResArchieve(arg0);
  }
  
  public java.lang.String testNleIngestService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testNleIngestService(arg0);
  }
  
  public java.lang.String testGetResDelete(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testGetResDelete(arg0);
  }
  
  public java.lang.String testDeleteContentService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testDeleteContentService(arg0);
  }
  
  public java.lang.String testIngestReportService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testIngestReportService(arg0);
  }
  
  public java.lang.String testArchiveReportService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testArchiveReportService(arg0);
  }
  
  public java.lang.String testGetResCopy(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testGetResCopy(arg0);
  }
  
  public java.lang.String testFileIngestService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testFileIngestService(arg0);
  }
  
  public java.lang.String testAddClipInfoService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testAddClipInfoService(arg0);
  }
  
  public java.lang.String testGetResUpdate(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testGetResUpdate(arg0);
  }
  
  public java.lang.String testGetResRestore(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testGetResRestore(arg0);
  }
  
  public java.lang.String testArchiveSchedulerService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testArchiveSchedulerService(arg0);
  }
  
  public java.lang.String testDeleteClipInfoService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testDeleteClipInfoService(arg0);
  }
  
  public java.lang.String testArchiveService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.testArchiveService(arg0);
  }
  
  public java.lang.String getCartInfoForUser(java.lang.String cartItemDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCartInfoForUser(cartItemDO);
  }
  
  public java.lang.String getRoleCode() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRoleCode();
  }
  
  public java.lang.String deleteMasterScean(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteMasterScean(master_id);
  }
  
  public java.lang.String deleteMasterScean2(java.lang.String deleteDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deleteMasterScean2(deleteDO);
  }
  
  public int updateEdtrId(java.lang.String code, java.lang.String ct_ids) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateEdtrId(code, ct_ids);
  }
  
  public java.lang.String getAnnotCode() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAnnotCode();
  }
  
  public java.lang.String updateRetryArchive(long seq) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateRetryArchive(seq);
  }
  
  public java.lang.String updateRetryArchiveByCtId(long ct_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateRetryArchiveByCtId(ct_id);
  }
  
  public java.lang.String insertCornerContinfo(long cn_id, java.lang.String cnInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertCornerContinfo(cn_id, cnInfoDO);
  }
  
  public java.lang.String deletePDSArchive(java.lang.String deleteDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.deletePDSArchive(deleteDO);
  }
  
  public java.lang.String insertMediaId() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertMediaId();
  }
  
  public java.lang.String getRoleInfoList(java.lang.String roleInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRoleInfoList(roleInfoDO);
  }
  
  public java.lang.String updateDataStatCd(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateDataStatCd(xml);
  }
  
  public java.lang.String getClipInfoList(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getClipInfoList(xml);
  }
  
  public java.lang.String getStorageCheck(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getStorageCheck(xml);
  }
  
  public java.lang.String updateIngestStatus(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateIngestStatus(xml);
  }
  
  public java.lang.String getCocdForChennel(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getCocdForChennel(xml);
  }
  
  public java.lang.String getChennelInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getChennelInfo(xml);
  }
  
  public java.lang.String getArchiveRoute(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getArchiveRoute(xml);
  }
  
  public int insertNLEDTL(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertNLEDTL(xml);
  }
  
  public int insertDtlInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertDtlInfo(xml);
  }
  
  public java.lang.String getAutoArchvieList(java.lang.String autoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAutoArchvieList(autoDO);
  }
  
  public int updateAutoArchvie(java.lang.String autoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateAutoArchvie(autoDO);
  }
  
  public int insertManualArchive(java.lang.String media_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertManualArchive(media_id);
  }
  
  public java.lang.String getManualInfo(java.lang.String manualArchiveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getManualInfo(manualArchiveDO);
  }
  
  public int insertManual(java.lang.String manualArchiveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertManual(manualArchiveDO);
  }
  
  public java.lang.String getMetadatInfoList(java.lang.String conditionDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMetadatInfoList(conditionDO);
  }
  
  public java.lang.String getSceanInfo(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSceanInfo(master_id);
  }
  
  public java.lang.String getBaseInfo(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getBaseInfo(master_id);
  }
  
  public int updateMetadat(java.lang.String metadataMstInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateMetadat(metadataMstInfoDO);
  }
  
  public int insertRoleInfo(java.lang.String roleInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertRoleInfo(roleInfoDO);
  }
  
  public java.lang.String insertComMedia(java.lang.String tcBeanDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertComMedia(tcBeanDO);
  }
  
  public java.lang.String getRoleInfoForChennel(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRoleInfoForChennel(xml);
  }
  
  public java.lang.String logHistory(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.logHistory(xml);
  }
  
  public java.lang.String getLogInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getLogInfo(xml);
  }
  
  public java.lang.String getApproveInfoForChennel(java.lang.String apprveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getApproveInfoForChennel(apprveDO);
  }
  
  public int insertApproveInfoForChennel(java.lang.String apprveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertApproveInfoForChennel(apprveDO);
  }
  
  public java.lang.String getApproveInfoListForChennel(java.lang.String apprveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getApproveInfoListForChennel(apprveDO);
  }
  
  public void deleteApproveInfoForChennel(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    pDASServices.deleteApproveInfoForChennel(xml);
  }
  
  public java.lang.String getArchiveStatusList(java.lang.String archiveInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getArchiveStatusList(archiveInfoDO);
  }
  
  public java.lang.String getManualArchiveInfo(java.lang.String manualArchiveDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getManualArchiveInfo(manualArchiveDO);
  }
  
  public java.lang.String getDtlInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDtlInfo(xml);
  }
  
  public java.lang.String insertDownCartInfo(java.lang.String downCartDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertDownCartInfo(downCartDO);
  }
  
  public java.lang.String insertCartContInfo(java.lang.String cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertCartContInfo(cartContDO);
  }
  
  public java.lang.String insertStDownCartInfo(java.lang.String downCartDO_cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertStDownCartInfo(downCartDO_cartContDO);
  }
  
  public java.lang.String insertStCartContInfo(java.lang.String cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertStCartContInfo(cartContDO);
  }
  
  public java.lang.String getLogInOutInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getLogInOutInfo(xml);
  }
  
  public int updateStDownCart(java.lang.String downCartDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateStDownCart(downCartDO);
  }
  
  public java.lang.String getArchiveInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getArchiveInfo(xml);
  }
  
  public java.lang.String getTCinfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTCinfo(xml);
  }
  
  public java.lang.String getTminfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getTminfo(xml);
  }
  
  public int changePriority(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.changePriority(xml);
  }
  
  public java.lang.String getDetailInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDetailInfo(xml);
  }
  
  public java.lang.String getManualDeleteList(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getManualDeleteList(xml);
  }
  
  public int manualDelete(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.manualDelete(xml);
  }
  
  public java.lang.String getErroeList(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getErroeList(xml);
  }
  
  public java.lang.String getServerList(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getServerList(xml);
  }
  
  public java.lang.String updateCopyRequest(java.lang.String useInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateCopyRequest(useInfoDO);
  }
  
  public int updateMediaCilpStatus(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateMediaCilpStatus(xml);
  }
  
  public java.lang.String getJobList(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getJobList(xml);
  }
  
  public java.lang.String updateWmvStatus(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateWmvStatus(xml);
  }
  
  public java.lang.String insertIfCmsDownCartInfo(java.lang.String downCartDO_cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.insertIfCmsDownCartInfo(downCartDO_cartContDO);
  }
  
  public java.lang.String updateIfCmsDownloadApprove(java.lang.String cartContDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateIfCmsDownloadApprove(cartContDO);
  }
  
  public java.lang.String getRoleForLoginInMonitoring(java.lang.String user_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRoleForLoginInMonitoring(user_id);
  }
  
  public java.lang.String getGroupForMaster(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getGroupForMaster(master_id);
  }
  
  public java.lang.String getSceanInfoForIfCms(long ct_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getSceanInfoForIfCms(ct_id);
  }
  
  public java.lang.String tryAgain(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.tryAgain(xml);
  }
  
  public java.lang.String getDowninfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDowninfo(xml);
  }
  
  public java.lang.String getManualJobinfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getManualJobinfo(xml);
  }
  
  public int updateRoleInfoForMonitoring(java.lang.String roleInfoDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateRoleInfoForMonitoring(roleInfoDO);
  }
  
  public java.lang.String getDepinfoForuserListFormonitoring(java.lang.String deptcd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDepinfoForuserListFormonitoring(deptcd);
  }
  
  public java.lang.String getMyArchiveRequestList(java.lang.String commonDO) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getMyArchiveRequestList(commonDO);
  }
  
  public java.lang.String getDowninfoForIfCms(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getDowninfoForIfCms(xml);
  }
  
  public java.lang.String getJobStatus(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getJobStatus(xml);
  }
  
  public int updateEquipMentStatus(java.lang.String xml) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.updateEquipMentStatus(xml);
  }
  
  public java.lang.String getGroupForMasterForClient(long master_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getGroupForMasterForClient(master_id);
  }
  
  public java.lang.String getRoleCodeForMonitoring() throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getRoleCodeForMonitoring();
  }
  
  public java.lang.String getAuthorForMonitoring(java.lang.String role_cd) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.getAuthorForMonitoring(role_cd);
  }
  
  public java.lang.String isVideoReleateYN(long master_id, long ct_id) throws java.rmi.RemoteException{
    if (pDASServices == null)
      _initPDASServicesProxy();
    return pDASServices.isVideoReleateYN(master_id, ct_id);
  }
  
  
}