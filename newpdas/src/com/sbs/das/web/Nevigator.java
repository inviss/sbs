/**
 * Nevigator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sbs.das.web;

public interface Nevigator extends java.rmi.Remote {  
    public java.lang.String transcodeService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String archiveReportService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String getStatus(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String cancelService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String ingestReportService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String resourceUseInfo(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String nleIngestService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String archiveStatus(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String archiveSchedulerService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String serviceTest(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String fileIngestService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String updateDiskQuota(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String archiveService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String downloadService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String changePriorityService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String addClipInfoService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String subAddClipService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String reportService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String schedulerForceExecute(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String findDiskQuota(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String nonErpAddClipService(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String recoveryService(java.lang.String arg0) throws java.rmi.RemoteException;
    public void testMethod(java.lang.String arg0) throws java.rmi.RemoteException;
}
