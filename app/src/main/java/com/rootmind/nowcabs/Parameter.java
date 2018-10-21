package com.rootmind.nowcabs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saikirangandham on 7/22/17.
 */

public class Parameter implements Serializable {

    String country="IN";
    int driverRadius=50;
    int mapZoom=15;
    Double radiusLat=null;
    Double radiusLng=null;


    public Parameter() {
    }

    public Parameter(String country, int driverRadius, int mapZoom, Double radiusLat, Double radiusLng) {
        this.country = country;
        this.driverRadius = driverRadius;
        this.mapZoom = mapZoom;
        this.radiusLat = radiusLat;
        this.radiusLng = radiusLng;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDriverRadius() {
        return driverRadius;
    }

    public void setDriverRadius(int driverRadius) {
        this.driverRadius = driverRadius;
    }

    public int getMapZoom() {
        return mapZoom;
    }

    public void setMapZoom(int mapZoom) {
        this.mapZoom = mapZoom;
    }

    public Double getRadiusLat() {
        return radiusLat;
    }

    public void setRadiusLat(Double radiusLat) {
        this.radiusLat = radiusLat;
    }

    public Double getRadiusLng() {
        return radiusLng;
    }

    public void setRadiusLng(Double radiusLng) {
        this.radiusLng = radiusLng;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("country", country);
        result.put("driverRadius", driverRadius);
        result.put("mapZoom", mapZoom);
        result.put("radiusLat", radiusLat);
        result.put("radiusLng", radiusLng);

        return result;
    }

}
