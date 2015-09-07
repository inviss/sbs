package com.sbs.pds.service;


public interface ServicePortType extends java.rmi.Remote {

    /**
     * Service definition of function DArcNeo__SOAPInterface
     */
    public java.lang.String SOAPInterface(java.lang.String systemversion, javax.xml.rpc.holders.StringHolder sessionid, javax.xml.rpc.holders.StringHolder opcode, java.lang.String ksccRequest, java.lang.String exvalue1, java.lang.String exvalue2, java.lang.String exvalue3, java.lang.String exvalue4) throws java.rmi.RemoteException;
}
