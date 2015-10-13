/**
 * OrderCallServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sbs.erp.service;

public class OrderCallServiceServiceLocator extends org.apache.axis.client.Service implements OrderCallServiceService {

    public OrderCallServiceServiceLocator() {
    }


    public OrderCallServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OrderCallServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OrderCallService
    private java.lang.String OrderCallService_address = "http://wise.sbs.co.kr/ErpOrderService/services/OrderCallService";

    public java.lang.String getOrderCallServiceAddress() {
        return OrderCallService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OrderCallServiceWSDDServiceName = "OrderCallService";

    public java.lang.String getOrderCallServiceWSDDServiceName() {
        return OrderCallServiceWSDDServiceName;
    }

    public void setOrderCallServiceWSDDServiceName(java.lang.String name) {
        OrderCallServiceWSDDServiceName = name;
    }

    public OrderCallService getOrderCallService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OrderCallService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOrderCallService(endpoint);
    }

    public OrderCallService getOrderCallService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            OrderCallServiceSoapBindingStub _stub = new OrderCallServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getOrderCallServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOrderCallServiceEndpointAddress(java.lang.String address) {
        OrderCallService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (OrderCallService.class.isAssignableFrom(serviceEndpointInterface)) {
                OrderCallServiceSoapBindingStub _stub = new OrderCallServiceSoapBindingStub(new java.net.URL(OrderCallService_address), this);
                _stub.setPortName(getOrderCallServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("OrderCallService".equals(inputPortName)) {
            return getOrderCallService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://kr.co.sbs.com", "OrderCallServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://kr.co.sbs.com", "OrderCallService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("OrderCallService".equals(portName)) {
            setOrderCallServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
