/**
 * ServiceNevigatorServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sbs.das.web;

import java.util.ResourceBundle;

public class ServiceNevigatorServiceLocator extends org.apache.axis.client.Service implements com.sbs.das.web.ServiceNevigatorService {
	private static ResourceBundle bundle = ResourceBundle.getBundle("das");
	
    public ServiceNevigatorServiceLocator() {
    }  


    public ServiceNevigatorServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceNevigatorServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiceNevigatorPort
    private String ServiceNevigatorPort_address =  bundle.getString("WF_SOAP_URL");
    
    public java.lang.String getServiceNevigatorPortAddress() {
        return ServiceNevigatorPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceNevigatorPortWSDDServiceName = "ServiceNevigatorPort";

    public java.lang.String getServiceNevigatorPortWSDDServiceName() {
        return ServiceNevigatorPortWSDDServiceName;
    }

    public void setServiceNevigatorPortWSDDServiceName(java.lang.String name) {
        ServiceNevigatorPortWSDDServiceName = name;
    }

    public com.sbs.das.web.Nevigator getServiceNevigatorPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServiceNevigatorPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServiceNevigatorPort(endpoint);
    }

    public com.sbs.das.web.Nevigator getServiceNevigatorPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.sbs.das.web.ServiceNevigatorServiceSoapBindingStub _stub = new com.sbs.das.web.ServiceNevigatorServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getServiceNevigatorPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceNevigatorPortEndpointAddress(java.lang.String address) {
        ServiceNevigatorPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.sbs.das.web.Nevigator.class.isAssignableFrom(serviceEndpointInterface)) {
                com.sbs.das.web.ServiceNevigatorServiceSoapBindingStub _stub = new com.sbs.das.web.ServiceNevigatorServiceSoapBindingStub(new java.net.URL(ServiceNevigatorPort_address), this);
                _stub.setPortName(getServiceNevigatorPortWSDDServiceName());
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
        if ("ServiceNevigatorPort".equals(inputPortName)) {
            return getServiceNevigatorPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://web.das.sbs.com/", "ServiceNevigatorService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://web.das.sbs.com/", "ServiceNevigatorPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServiceNevigatorPort".equals(portName)) {
            setServiceNevigatorPortEndpointAddress(address);
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
