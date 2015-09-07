package com.sbs.pds.service;


public class ServicePortTypeProxy implements ServicePortType {
  private String _endpoint = null;
  private ServicePortType servicePortType = null;
  
  public ServicePortTypeProxy() {
    _initServicePortTypeProxy();
  }
  
  public ServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initServicePortTypeProxy();
  }
  
  private void _initServicePortTypeProxy() {
    try {
      servicePortType = (new ServiceLocator()).getService();
      if (servicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)servicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)servicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (servicePortType != null)
      ((javax.xml.rpc.Stub)servicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ServicePortType getServicePortType() {
    if (servicePortType == null)
      _initServicePortTypeProxy();
    return servicePortType;
  }
  
  public java.lang.String SOAPInterface(java.lang.String systemversion, javax.xml.rpc.holders.StringHolder sessionid, javax.xml.rpc.holders.StringHolder opcode, java.lang.String ksccRequest, java.lang.String exvalue1, java.lang.String exvalue2, java.lang.String exvalue3, java.lang.String exvalue4) throws java.rmi.RemoteException{
    if (servicePortType == null)
      _initServicePortTypeProxy();
    return servicePortType.SOAPInterface(systemversion, sessionid, opcode, ksccRequest, exvalue1, exvalue2, exvalue3, exvalue4);
  }
  
  
}
