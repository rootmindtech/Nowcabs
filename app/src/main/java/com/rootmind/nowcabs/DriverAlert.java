package com.rootmind.nowcabs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saikirangandham on 7/21/17.
 */

public class DriverAlert {

    String riderSMS;
    String datetime;


    public String getRiderSMS() {
        return riderSMS;
    }

    public void setRiderSMS(String riderSMS) {
        this.riderSMS = riderSMS;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public DriverAlert() {
    }

    public DriverAlert(String riderSMS, String datetime) {
        this.riderSMS = riderSMS;
        this.datetime = datetime;
    }

    //@Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("riderSMS",riderSMS);
        result.put("datetime", datetime);

        return result;
    }


}
