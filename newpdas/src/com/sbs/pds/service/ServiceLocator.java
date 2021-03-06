package com.sbs.pds.service;
import java.util.ResourceBundle;



public class ServiceLocator extends org.apache.axis.client.Service implements Service {

/**
 * gSOAP 2.7.13 generated service definition
 */
	private static ResourceBundle bundle = ResourceBundle.getBundle("das");
	
    public ServiceLocator() {
    }

    public ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Service
    private java.lang.String Service_address = bundle.getString("PDS_SOAP_URL");

    public java.lang.String getServiceAddress() {
        return Service_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServiceWSDDServiceName = "Service";

    public java.lang.String getServiceWSDDServiceName() {
        return ServiceWSDDServiceName;
    }

    public void setServiceWSDDServiceName(java.lang.String name) {
        ServiceWSDDServiceName = name;
    }

    public ServicePortType getService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Service_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getService(endpoint);
    }

    public ServicePortType getService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ServiceStub _stub = new ServiceStub(portAddress, this);
            _stub.setPortName(getServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServiceEndpointAddress(java.lang.String address) {
        Service_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ServiceStub _stub = new ServiceStub(new java.net.URL(Service_address), this);
                _stub.setPortName(getServiceWSDDServiceName());
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
        if ("Service".equals(inputPortName)) {
            return getService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/DArcNeo.xsd/Service.wsdl", "Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/DArcNeo.xsd/Service.wsdl", "Service"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Service".equals(portName)) {
            setServiceEndpointAddress(address);
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
