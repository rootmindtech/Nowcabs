package com.rootmind.nowcabs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rootmindtechsoftprivatelimited on 23/06/17.
 */

public class Rider implements Serializable {

    private static final long serialVersionUID = 1L;

    public String riderID;
    public String riderRefNo;
    public String riderName;

    public String riderMobileNo;
    public double riderLat;
    public double riderLng;

    public String status;


    public Boolean imageFound=false;
    public String riderImage;


    public String datetime;
    public String riderLocation;

    public String fcmToken;


    public String locale;



    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }






    public String getRiderID() {
        return riderID;
    }

    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }

    public String getRiderRefNo() {
        return riderRefNo;
    }

    public void setRiderRefNo(String riderRefNo) {
        this.riderRefNo = riderRefNo;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderMobileNo() {
        return riderMobileNo;
    }

    public void setRiderMobileNo(String riderMobileNo) {
        this.riderMobileNo = riderMobileNo;
    }

    public double getRiderLat() {
        return riderLat;
    }

    public void setRiderLat(double riderLat) {
        this.riderLat = riderLat;
    }

    public double getRiderLng() {
        return riderLng;
    }

    public void setRiderLng(double riderLng) {
        this.riderLng = riderLng;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }


    public String getRiderLocation() {
        return riderLocation;
    }

    public void setRiderLocation(String riderLocation) {
        this.riderLocation = riderLocation;
    }


    public Boolean getImageFound() {
        return imageFound;
    }

    public void setImageFound(Boolean imageFound) {
        this.imageFound = imageFound;
    }

    public String getRiderImage() {
        return riderImage;
    }

    public void setRiderImage(String riderImage) {
        this.riderImage = riderImage;
    }


    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public  Rider(){


    }


    public Rider(String riderID, String riderRefNo, String riderName, String riderMobileNo, double riderLat, double riderLng, String status, Boolean imageFound, String riderImage, String datetime, String riderLocation, String fcmToken) {
        this.riderID = riderID;
        this.riderRefNo = riderRefNo;
        this.riderName = riderName;
        this.riderMobileNo = riderMobileNo;
        this.riderLat = riderLat;
        this.riderLng = riderLng;
        this.status = status;
        this.imageFound = imageFound;
        this.riderImage = riderImage;
        this.datetime = datetime;
        this.riderLocation = riderLocation;
        this.fcmToken = fcmToken;
    }
//@Exclude

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("riderID", riderID);
        result.put("riderRefNo", riderRefNo);
        result.put("riderName", riderName);
        result.put("riderMobileNo", riderMobileNo);
        result.put("status", status);
        result.put("riderLat", riderLat);
        result.put("riderLng", riderLng);
        result.put("datetime",datetime);
        result.put("riderLocation",riderLocation);
        result.put("imageFound",imageFound);
        result.put("riderImage",riderImage);
        result.put("fcmToken",fcmToken);

        return result;
    }
}