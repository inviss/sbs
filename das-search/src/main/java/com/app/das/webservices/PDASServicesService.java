/**
 * PDASServicesService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.app.das.webservices;

public interface PDASServicesService extends javax.xml.rpc.Service {
    public java.lang.String getPDASServicesAddress();

    public com.app.das.webservices.PDASServices getPDASServices() throws javax.xml.rpc.ServiceException;

    public com.app.das.webservices.PDASServices getPDASServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
