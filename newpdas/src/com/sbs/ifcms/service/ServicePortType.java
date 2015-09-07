/**
 * ServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sbs.ifcms.service;

public interface ServicePortType extends java.rmi.Remote {

    /**
     * Service definition of function DArcNeo__SOAPInterface
     */
    public java.lang.String SOAPInterface(java.lang.String systemversion, javax.xml.rpc.holders.StringHolder sessionid, javax.xml.rpc.holders.StringHolder opcode, java.lang.String ksccRequest, java.lang.String exvalue1, java.lang.String exvalue2, java.lang.String exvalue3, java.lang.String exvalue4) throws java.rmi.RemoteException;
}
