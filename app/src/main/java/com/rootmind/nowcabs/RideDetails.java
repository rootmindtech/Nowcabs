package com.rootmind.nowcabs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rootmindtechsoftprivatelimited on 01/08/17.
 */

public class RideDetails implements Serializable {

    private static final long serialVersionUID = 1L;




    public String rideID;
    public String riderID;
    public String driverID;
    public String source;
    public String destination;
    public String datetime;


    public String getRideID() {
        return rideID;
    }

    public void setRideID(String rideID) {
        this.rideID = rideID;
    }

    public String getRiderID() {
        return riderID;
    }

    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public  RideDetails(){


    }

    public RideDetails(String rideID,String riderID, String driverID, String source, String destination, String datetime) {

        this.rideID = rideID;
        this.riderID = riderID;
        this.driverID = driverID;
        this.source = source;
        this.destination = destination;
        this.datetime = datetime;
    }


    //@Exclude

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("rideID", rideID);
        result.put("riderID", riderID);
        result.put("driverID", driverID);
        result.put("source", source);
        result.put("destination", destination);
        result.put("datetime",datetime);

        return result;
    }



}
