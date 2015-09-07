package com.sbs.pds.service;


public interface Service extends javax.xml.rpc.Service {

/**
 * gSOAP 2.7.13 generated service definition
 */
    public java.lang.String getServiceAddress();

    public ServicePortType getService() throws javax.xml.rpc.ServiceException;

    public ServicePortType getService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
