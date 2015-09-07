package com.sbs.erp.service;

public class OrderCallServiceProxy implements OrderCallService {
  private String _endpoint = null;
  private OrderCallService orderCallService = null;
  
  public OrderCallServiceProxy() {
    _initOrderCallServiceProxy();
  }
  
  public OrderCallServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initOrderCallServiceProxy();
  }
  
  private void _initOrderCallServiceProxy() {
    try {
      orderCallService = (new OrderCallServiceServiceLocator()).getOrderCallService();
      if (orderCallService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)orderCallService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)orderCallService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (orderCallService != null)
      ((javax.xml.rpc.Stub)orderCallService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public OrderCallService getOrderCallService() {
    if (orderCallService == null)
      _initOrderCallServiceProxy();
    return orderCallService;
  }
  
  public java.lang.String getOrderInfo() throws java.rmi.RemoteException{
    if (orderCallService == null)
      _initOrderCallServiceProxy();
    return orderCallService.getOrderInfo();
  }
  
  public java.lang.String getOrderInfoList(int num) throws java.rmi.RemoteException{
    if (orderCallService == null)
      _initOrderCallServiceProxy();
    return orderCallService.getOrderInfoList(num);
  }
  
  
}
