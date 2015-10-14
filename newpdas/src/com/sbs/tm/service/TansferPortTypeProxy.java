package com.sbs.tm.service;

public class TansferPortTypeProxy implements TansferPortType {
  private String _endpoint = null;
  private TansferPortType tansferPortType = null;
  
  public TansferPortTypeProxy() {
    _initTansferPortTypeProxy();
  }
  
  public TansferPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initTansferPortTypeProxy();
  }
  
  private void _initTansferPortTypeProxy() {
    try {
      tansferPortType = (new TansferLocator()).getTansferPort();
      if (tansferPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)tansferPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)tansferPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (tansferPortType != null)
      ((javax.xml.rpc.Stub)tansferPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public TansferPortType getTansferPortType() {
    if (tansferPortType == null)
      _initTansferPortTypeProxy();
    return tansferPortType;
  }
  
  public java.lang.String addTask(java.lang.String request) throws java.rmi.RemoteException{
    if (tansferPortType == null)
      _initTansferPortTypeProxy();
    return tansferPortType.addTask(request);
  }
  
  public java.lang.String addTaskPFR(java.lang.String request) throws java.rmi.RemoteException{
    if (tansferPortType == null)
      _initTansferPortTypeProxy();
    return tansferPortType.addTaskPFR(request);
  }
  
  public java.lang.String getTaskStatus(java.lang.String request) throws java.rmi.RemoteException{
    if (tansferPortType == null)
      _initTansferPortTypeProxy();
    return tansferPortType.getTaskStatus(request);
  }
  
  
}