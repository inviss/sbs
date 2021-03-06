package com.sbs.pa.service;

public class Service1SoapProxy implements Service1Soap {
  private String _endpoint = null;
  private Service1Soap service1Soap = null;
  
  public Service1SoapProxy() {
    _initService1SoapProxy();
  }
  
  public Service1SoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initService1SoapProxy();
  }
  
  private void _initService1SoapProxy() {
    try {
      service1Soap = (new Service1Locator()).getService1Soap();
      if (service1Soap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)service1Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)service1Soap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (service1Soap != null)
      ((javax.xml.rpc.Stub)service1Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Service1Soap getService1Soap() {
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap;
  }
  
  public java.lang.String helloWorld() throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.helloWorld();
  }
  
  public int wsPAUserInfoManager(java.lang.String inputXmlString) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.wsPAUserInfoManager(inputXmlString);
  }
  
  
}
