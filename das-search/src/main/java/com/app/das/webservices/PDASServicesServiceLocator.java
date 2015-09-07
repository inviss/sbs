/**
 * PDASServicesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.app.das.webservices;

public class PDASServicesServiceLocator extends org.apache.axis.client.Service implements com.app.das.webservices.PDASServicesService {

    public PDASServicesServiceLocator() {
    }


    public PDASServicesServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PDASServicesServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PDASServices
    private java.lang.String PDASServices_address = "http://10.30.23.48:8088/PDAS/services/PDASServices";

    public java.lang.String getPDASServicesAddress() {
        return PDASServices_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PDASServicesWSDDServiceName = "PDASServices";

    public java.lang.String getPDASServicesWSDDServiceName() {
        return PDASServicesWSDDServiceName;
    }

    public void setPDASServicesWSDDServiceName(java.lang.String name) {
        PDASServicesWSDDServiceName = name;
    }

    public com.app.das.webservices.PDASServices getPDASServices() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PDASServices_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPDASServices(endpoint);
    }

    public com.app.das.webservices.PDASServices getPDASServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.app.das.webservices.PDASServicesSoapBindingStub _stub = new com.app.das.webservices.PDASServicesSoapBindingStub(portAddress, this);
            _stub.setPortName(getPDASServicesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPDASServicesEndpointAddress(java.lang.String address) {
        PDASServices_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.app.das.webservices.PDASServices.class.isAssignableFrom(serviceEndpointInterface)) {
                com.app.das.webservices.PDASServicesSoapBindingStub _stub = new com.app.das.webservices.PDASServicesSoapBindingStub(new java.net.URL(PDASServices_address), this);
                _stub.setPortName(getPDASServicesWSDDServiceName());
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
        if ("PDASServices".equals(inputPortName)) {
            return getPDASServices();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservices.das.app.com", "PDASServicesService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservices.das.app.com", "PDASServices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PDASServices".equals(portName)) {
            setPDASServicesEndpointAddress(address);
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
