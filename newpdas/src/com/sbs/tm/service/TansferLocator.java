/**
 * TansferLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sbs.tm.service;

public class TansferLocator extends org.apache.axis.client.Service implements Tansfer {

    public TansferLocator() {
    }


    public TansferLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TansferLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TansferPort
    private java.lang.String TansferPort_address = "http://10.150.22.61:8070/php";

    public java.lang.String getTansferPortAddress() {
        return TansferPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TansferPortWSDDServiceName = "TansferPort";

    public java.lang.String getTansferPortWSDDServiceName() {
        return TansferPortWSDDServiceName;
    }

    public void setTansferPortWSDDServiceName(java.lang.String name) {
        TansferPortWSDDServiceName = name;
    }

    public TansferPortType getTansferPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TansferPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTansferPort(endpoint);
    }

    public TansferPortType getTansferPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            TansferBindingStub _stub = new TansferBindingStub(portAddress, this);
            _stub.setPortName(getTansferPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTansferPortEndpointAddress(java.lang.String address) {
        TansferPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (TansferPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                TansferBindingStub _stub = new TansferBindingStub(new java.net.URL(TansferPort_address), this);
                _stub.setPortName(getTansferPortWSDDServiceName());
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
        if ("TansferPort".equals(inputPortName)) {
            return getTansferPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:TMService", "Tansfer");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:TMService", "TansferPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TansferPort".equals(portName)) {
            setTansferPortEndpointAddress(address);
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
