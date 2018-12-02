package com.rootmind.nowcabs;

import java.io.Serializable;

public class Job implements Serializable {


    public String riderRefNo = null;
    public String riderID = null;
    public String jobID=null;
    public String jobName=null;
    public String serviceCode=null;
    public double rate=0;
    public String status= null;

    public String currency=null;

    public boolean recordFound=false;


    public String makerDateTime=null;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getMakerDateTime() {
        return makerDateTime;
    }

    public void setMakerDateTime(String makerDateTime) {
        this.makerDateTime = makerDateTime;
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
