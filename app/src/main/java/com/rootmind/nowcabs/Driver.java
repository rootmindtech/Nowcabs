package com.rootmind.nowcabs;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.*;
/**
 * Created by rootmindtechsoftprivatelimited on 20/06/17.
 */

public class Driver implements  Serializable{

//    public String driverID="DRV201700666";
//    public String driverRefNo="DRV20JUN2017000666";
//    public String driverName="TestAndroidUpdate";
//    public String driverMobileNo="0606060606";
//    public String driverVehicleNo="VR666";
//    public String driverStatus="ACTIVE";
//    public String driverVacantStatus="VACANT";
//    public String driverLat="16.512";
//    public String driverLng="81.213";
//    public String driverLocation="DRIVER LOCATION";
//    public String destination="NARASAPURAM";
//    public String datetime="20/6/2017 @ 17:59:47";
//    public String driverVehicleType="CAB";


    public String driverID;
    public String driverRefNo;
    public String driverName;
    public String driverMobileNo;
    public String driverVehicleNo;



    // public String driverStatus;
    //public String status;
   // public String driverVacantStatus;

//    public String driverLat;
//    public String driverLng;

    public double lastDriverLat;
    public double lastDriverLng;

    public double driverLat;
    public double driverLng;

    public String driverLocation;
    public String destination;


    public String datetime;
    public String driverVehicleType;

    public String vacantStatus;

    public String status;

    public Boolean imageFound=false;

    public String driverImage;


    public String riderSMS;

    public double distance;


    public double duration;

    public double riderLat;
    public double riderLng;

    public String fcmToken;

    public String favorite;

    public double avgRating;

    public double yourRating;

    public String locale;


    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }


    public double getYourRating() {
        return yourRating;
    }

    public void setYourRating(double yourRating) {
        this.yourRating = yourRating;
    }




    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }



    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }



    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }



    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
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



    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public String getDriverImage() {
        return driverImage;
    }

    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }




    public Boolean getImageFound() {
        return imageFound;
    }

    public void setImageFound(Boolean imageFound) {
        this.imageFound = imageFound;
    }




    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }



    public String getVacantStatus() {
        return vacantStatus;
    }

    public void setVacantStatus(String vacantStatus) {
        this.vacantStatus = vacantStatus;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDriverRefNo() {
        return driverRefNo;
    }

    public void setDriverRefNo(String driverRefNo) {
        this.driverRefNo = driverRefNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobileNo() {
        return driverMobileNo;
    }

    public void setDriverMobileNo(String driverMobileNo) {
        this.driverMobileNo = driverMobileNo;
    }

    public String getDriverVehicleNo() {
        return driverVehicleNo;
    }

    public void setDriverVehicleNo(String driverVehicleNo) {
        this.driverVehicleNo = driverVehicleNo;
    }

    public String getRiderSMS() {
        return riderSMS;
    }

    public void setRiderSMS(String riderSMS) {
        this.riderSMS = riderSMS;
    }


//    public String getDriverStatus() {
//        return driverStatus;
//    }
//
//    public void setDriverStatus(String driverStatus) {
//        this.driverStatus = driverStatus;
//    }

//    public String getDriverVacantStatus() {
//        return driverVacantStatus;
//    }
//
//    public void setDriverVacantStatus(String driverVacantStatus) {
//        this.driverVacantStatus = driverVacantStatus;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public String getDriverLat() {
//        return driverLat;
//    }
//
//    public void setDriverLat(String driverLat) {
//        this.driverLat = driverLat;
//    }
//
//    public String getDriverLng() {
//        return driverLng;
//    }
//
//    public void setDriverLng(String driverLng) {
//        this.driverLng = driverLng;
//    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

//    public String getdatetime() {
//        return datetime;
//    }
//
//    public void setdatetime(String datetime) {
//        this.datetime = datetime;
//    }

    public String getDriverVehicleType() {
        return driverVehicleType;
    }

    public void setDriverVehicleType(String driverVehicleType) {
        this.driverVehicleType = driverVehicleType;
    }

    public double getDriverLat() {
        return driverLat;
    }

    public void setDriverLat(double driverLat) {
        this.driverLat = driverLat;
    }

    public double getDriverLng() {
        return driverLng;
    }

    public void setDriverLng(double driverLng) {
        this.driverLng = driverLng;
    }


    public double getLastDriverLat() {
        return lastDriverLat;
    }

    public void setLastDriverLat(double lastDriverLat) {
        this.lastDriverLat = lastDriverLat;
    }

    public double getLastDriverLng() {
        return lastDriverLng;
    }

    public void setLastDriverLng(double lastDriverLng) {
        this.lastDriverLng = lastDriverLng;
    }

    public  Driver(){


    }


    public Driver(String driverID, String driverRefNo, String driverName, String driverMobileNo, String driverVehicleNo, double lastDriverLat, double lastDriverLng, double driverLat, double driverLng, String driverLocation, String destination, String datetime, String driverVehicleType, String vacantStatus, String status, Boolean imageFound, String driverImage, String riderSMS, int distance) {
        this.driverID = driverID;
        this.driverRefNo = driverRefNo;
        this.driverName = driverName;
        this.driverMobileNo = driverMobileNo;
        this.driverVehicleNo = driverVehicleNo;
        this.lastDriverLat = lastDriverLat;
        this.lastDriverLng = lastDriverLng;
        this.driverLat = driverLat;
        this.driverLng = driverLng;
        this.driverLocation = driverLocation;
        this.destination = destination;
        this.datetime = datetime;
        this.driverVehicleType = driverVehicleType;
        this.vacantStatus = vacantStatus;
        this.status = status;
        this.imageFound = imageFound;
        this.driverImage = driverImage;
        this.riderSMS = riderSMS;
        this.distance = distance;
    }


    //@Exclude

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("driverID", driverID);
        result.put("driverRefNo", driverRefNo);
        result.put("driverName", driverName);
        result.put("driverMobileNo", driverMobileNo);
        result.put("driverVehicleNo", driverVehicleNo);
        result.put("status", status);
        result.put("vacantStatus", vacantStatus);
        result.put("lastDriverLat", lastDriverLat);
        result.put("lastDriverLng", lastDriverLng);
        result.put("driverLat", driverLat);
        result.put("driverLng", driverLng);
        result.put("driverLocation", driverLocation);
        result.put("destination", destination);
        result.put("datetime", datetime);
        result.put("driverVehicleType", driverVehicleType);
        result.put("imageFound",imageFound);
        result.put("driverImage",driverImage);
        result.put("riderSMS",riderSMS);
        result.put("distance", distance);

        return result;
    }


}
