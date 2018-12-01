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


    public String firstName;


    public String mobileNo;
    public double riderLat;
    public double riderLng;

    public String status;


    //public Boolean imageFound=false;
    //public String riderImage;


    public String datetime;
    public String riderLocation;

    public String fcmToken;

    public String favorite;

    public double avgRating;

    public double yourRating;


    public String locale;


    public double distance;


    public double duration;


    public String imageName;

    public String imageID;

    public String vehicleNo;

    public String vehicleType;

    //public Image images[]=null;

    public Service services[]=null;

    public String vacantStatus=null;

    public String serviceCode=null;

    public boolean recordFound=false;

    public String customToken=null;

    public boolean hostResponse=false;

    public Image[] imageWrappers=null;

    public String publicView=null;

    public int radius;

    public String currency=null;

    public String deviceID=null;

    public boolean authDevice=false;

    public boolean isAuthDevice() {
        return authDevice;
    }

    public void setAuthDevice(boolean authDevice) {
        this.authDevice = authDevice;
    }


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }


    public String getPublicView() {
        return publicView;
    }

    public void setPublicView(String publicView) {
        this.publicView = publicView;
    }


    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Image[] getImageWrappers() {
        return imageWrappers;
    }

    public void setImageWrappers(Image[] imageWrappers) {
        this.imageWrappers = imageWrappers;
    }


    public boolean isHostResponse() {
        return hostResponse;
    }

    public void setHostResponse(boolean hostResponse) {
        this.hostResponse = hostResponse;
    }


    public String getCustomToken() {
        return customToken;
    }

    public void setCustomToken(String customToken) {
        this.customToken = customToken;
    }


    public boolean isRecordFound() {
        return recordFound;
    }

    public void setRecordFound(boolean recordFound) {
        this.recordFound = recordFound;
    }



    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }


    public String getVacantStatus() {
        return vacantStatus;
    }

    public void setVacantStatus(String vacantStatus) {
        this.vacantStatus = vacantStatus;
    }


    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }



    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


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


//    public Boolean getImageFound() {
//        return imageFound;
//    }
//
//    public void setImageFound(Boolean imageFound) {
//        this.imageFound = imageFound;
//    }
//
//    public String getRiderImage() {
//        return riderImage;
//    }
//
//    public void setRiderImage(String riderImage) {
//        this.riderImage = riderImage;
//    }


    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

//    public Image[] getImages() {
//        return images;
//    }
//
//    public void setImages(Image[] images) {
//        this.images = images;
//    }


    public  Rider(){


    }


    public Rider(String riderID, String riderRefNo, String riderName, String mobileNo, double riderLat, double riderLng, String status, String datetime, String riderLocation, String fcmToken) {
        this.riderID = riderID;
        this.riderRefNo = riderRefNo;
        this.riderName = riderName;
        this.mobileNo = mobileNo;
        this.riderLat = riderLat;
        this.riderLng = riderLng;
        this.status = status;
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
        result.put("mobileNo", mobileNo);
        result.put("status", status);
        result.put("riderLat", riderLat);
        result.put("riderLng", riderLng);
        result.put("datetime",datetime);
        result.put("riderLocation",riderLocation);
//        result.put("imageFound",imageFound);
//        result.put("riderImage",riderImage);
        result.put("fcmToken",fcmToken);

        return result;
    }
}