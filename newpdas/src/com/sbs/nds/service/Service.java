/**
 * Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sbs.nds.service;

public interface Service extends javax.xml.rpc.Service {

/**
 * gSOAP 2.7.13 generated service definition
 */
    public java.lang.String getServiceAddress();

    public ServicePortType getService() throws javax.xml.rpc.ServiceException;

    public ServicePortType getService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
