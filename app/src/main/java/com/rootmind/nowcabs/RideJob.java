package com.rootmind.nowcabs;

import java.io.Serializable;

public class RideJob  implements Serializable {

    private String rideRefNo = null;
    private String riderRefNo = null;
    private String riderID = null;
    private String servicerRefNo = null;
    private String servicerID = null;
    private String jobID=null;
    private String jobName=null;
    private String serviceCode=null;
    private double rate=0;
    private String status= null;

    private boolean recordFound=false;


    public String getRideRefNo() {
        return rideRefNo;
    }

    public void setRideRefNo(String rideRefNo) {
        this.rideRefNo = rideRefNo;
    }

    public String getRiderRefNo() {
        return riderRefNo;
    }

    public void setRiderRefNo(String riderRefNo) {
        this.riderRefNo = riderRefNo;
    }

    public String getRiderID() {
        return riderID;
    }

    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }

    public String getServicerRefNo() {
        return servicerRefNo;
    }

    public void setServicerRefNo(String servicerRefNo) {
        this.servicerRefNo = servicerRefNo;
    }

    public String getServicerID() {
        return servicerID;
    }

    public void setServicerID(String servicerID) {
        this.servicerID = servicerID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRecordFound() {
        return recordFound;
    }

    public void setRecordFound(boolean recordFound) {
        this.recordFound = recordFound;
    }



}
