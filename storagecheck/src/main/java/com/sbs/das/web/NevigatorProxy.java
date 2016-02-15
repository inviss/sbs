package com.sbs.das.web;

public class NevigatorProxy implements com.sbs.das.web.Nevigator {
  private String _endpoint = null;
  private com.sbs.das.web.Nevigator nevigator = null;
  
  public NevigatorProxy() {
    _initNevigatorProxy();
  }
  
  public NevigatorProxy(String endpoint) {
    _endpoint = endpoint;
    _initNevigatorProxy();
  }
  
  private void _initNevigatorProxy() {
    try {
      nevigator = (new com.sbs.das.web.ServiceNevigatorServiceLocator()).getServiceNevigatorPort();
      if (nevigator != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)nevigator)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)nevigator)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (nevigator != null)
      ((javax.xml.rpc.Stub)nevigator)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.sbs.das.web.Nevigator getNevigator() {
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator;
  }
  
  public java.lang.String serviceTest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.serviceTest(arg0);
  }
  
  public java.lang.String recoveryService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.recoveryService(arg0);
  }
  
  public java.lang.String resourceUseInfo(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.resourceUseInfo(arg0);
  }
  
  public java.lang.String schedulerForceExecute(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.schedulerForceExecute(arg0);
  }
  
  public java.lang.String deleteService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.deleteService(arg0);
  }
  
  public java.lang.String downloadService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.downloadService(arg0);
  }
  
  public java.lang.String reportService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.reportService(arg0);
  }
  
  public java.lang.String nonErpAddClipService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.nonErpAddClipService(arg0);
  }
  
  public void testMethod(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    nevigator.testMethod(arg0);
  }
  
  public java.lang.String changePriorityService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.changePriorityService(arg0);
  }
  
  public java.lang.String archiveStatus(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.archiveStatus(arg0);
  }
  
  public java.lang.String nleIngestService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.nleIngestService(arg0);
  }
  
  public java.lang.String ingestReportService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.ingestReportService(arg0);
  }
  
  public java.lang.String findDiskQuota(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.findDiskQuota(arg0);
  }
  
  public java.lang.String archiveReportService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.archiveReportService(arg0);
  }
  
  public java.lang.String subAddClipService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.subAddClipService(arg0);
  }
  
  public java.lang.String fileIngestService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.fileIngestService(arg0);
  }
  
  public java.lang.String getStatus(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.getStatus(arg0);
  }
  
  public java.lang.String cancelService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.cancelService(arg0);
  }
  
  public java.lang.String transcodeService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.transcodeService(arg0);
  }
  
  public java.lang.String addClipInfoService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.addClipInfoService(arg0);
  }
  
  public java.lang.String archiveSchedulerService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.archiveSchedulerService(arg0);
  }
  
  public java.lang.String updateDiskQuota(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.updateDiskQuota(arg0);
  }
  
  public java.lang.String archiveService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (nevigator == null)
      _initNevigatorProxy();
    return nevigator.archiveService(arg0);
  }
  
  
}