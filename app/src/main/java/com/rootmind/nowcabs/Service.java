package com.rootmind.nowcabs;

import java.io.Serializable;

public class Service implements Serializable {


    private String riderRefNo =null;
    private String riderID =null;
    private String serviceCode  =null;
    private String status  =null;

    private int serviceCount=0;

    public double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }



    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
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

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
