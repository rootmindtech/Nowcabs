package com.rootmind.nowcabs;

import java.io.Serializable;

public class Ride implements Serializable {


    public String rideRefNo = null;
    public String riderRefNo = null;
    public String riderID = null;
    public String riderName=null;
    public String riderMobileNo=null;
    public String servicerRefNo = null;
    public String servicerID = null;
    public String servicerName=null;
    public String servicerMobileNo=null;
    public String vehicleNo=null;
    public String vehicleType = null;
    public String rideStartDate = null;
    public String rideEndDate  = null;
    public String rideStartPoint= null;
    public String rideEndPoint= null;
    public String pickupLat = null;
    public String pickupLng = null;
    public String dropLat = null;
    public String dropLng = null;

    public String pickupTime = null;
    public String pickupDistance = null;  //distance is driver current location to traveller location distance
    public String category = null;
    public String approxDistance = null;
    public String actualDistance = null;
    public String approxTime = null;
    public String actualTime = null;
    public String approxFare = null;
    public String baseFare= null;
    public String additionalKMFare= null;
    public String additionalTimeFare= null;
    public String totalFare= null;
    public String serviceTax= null;
    public String couponCode= null;

    public String discount= null;
    public String totalBill= null;
    public String futureRideDate= null;
    public String rideStatus= null;

    public String serviceCode=null;

    public String rideType=null;

    public String appointDateTime=null;

    public boolean recordFound=false;

    public String getAppointDateTime() {
        return appointDateTime;
    }

    public void setAppointDateTime(String appointDateTime) {
        this.appointDateTime = appointDateTime;
    }


    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }


    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }



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

    public String getServicerName() {
        return servicerName;
    }

    public void setServicerName(String servicerName) {
        this.servicerName = servicerName;
    }

    public String getServicerMobileNo() {
        return servicerMobileNo;
    }

    public void setServicerMobileNo(String servicerMobileNo) {
        this.servicerMobileNo = servicerMobileNo;
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

    public String getRideStartDate() {
        return rideStartDate;
    }

    public void setRideStartDate(String rideStartDate) {
        this.rideStartDate = rideStartDate;
    }

    public String getRideEndDate() {
        return rideEndDate;
    }

    public void setRideEndDate(String rideEndDate) {
        this.rideEndDate = rideEndDate;
    }

    public String getRideStartPoint() {
        return rideStartPoint;
    }

    public void setRideStartPoint(String rideStartPoint) {
        this.rideStartPoint = rideStartPoint;
    }

    public String getRideEndPoint() {
        return rideEndPoint;
    }

    public void setRideEndPoint(String rideEndPoint) {
        this.rideEndPoint = rideEndPoint;
    }

    public String getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(String pickupLat) {
        this.pickupLat = pickupLat;
    }

    public String getPickupLng() {
        return pickupLng;
    }

    public void setPickupLng(String pickupLng) {
        this.pickupLng = pickupLng;
    }

    public String getDropLat() {
        return dropLat;
    }

    public void setDropLat(String dropLat) {
        this.dropLat = dropLat;
    }

    public String getDropLng() {
        return dropLng;
    }

    public void setDropLng(String dropLng) {
        this.dropLng = dropLng;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickupDistance() {
        return pickupDistance;
    }

    public void setPickupDistance(String pickupDistance) {
        this.pickupDistance = pickupDistance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getApproxDistance() {
        return approxDistance;
    }

    public void setApproxDistance(String approxDistance) {
        this.approxDistance = approxDistance;
    }

    public String getActualDistance() {
        return actualDistance;
    }

    public void setActualDistance(String actualDistance) {
        this.actualDistance = actualDistance;
    }

    public String getApproxTime() {
        return approxTime;
    }

    public void setApproxTime(String approxTime) {
        this.approxTime = approxTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getApproxFare() {
        return approxFare;
    }

    public void setApproxFare(String approxFare) {
        this.approxFare = approxFare;
    }

    public String getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(String baseFare) {
        this.baseFare = baseFare;
    }

    public String getAdditionalKMFare() {
        return additionalKMFare;
    }

    public void setAdditionalKMFare(String additionalKMFare) {
        this.additionalKMFare = additionalKMFare;
    }

    public String getAdditionalTimeFare() {
        return additionalTimeFare;
    }

    public void setAdditionalTimeFare(String additionalTimeFare) {
        this.additionalTimeFare = additionalTimeFare;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(String totalBill) {
        this.totalBill = totalBill;
    }

    public String getFutureRideDate() {
        return futureRideDate;
    }

    public void setFutureRideDate(String futureRideDate) {
        this.futureRideDate = futureRideDate;
    }

    public String getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(String rideStatus) {
        this.rideStatus = rideStatus;
    }

    public boolean isRecordFound() {
        return recordFound;
    }

    public void setRecordFound(boolean recordFound) {
        this.recordFound = recordFound;
    }


}
