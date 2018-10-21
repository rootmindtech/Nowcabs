package com.rootmind.nowcabs;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.io.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.*;
import java.net.URLEncoder;

import android.content.SharedPreferences;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;

import static com.rootmind.nowcabs.RiderMapActivity.MY_PERMISSIONS_REQUEST_LOCATION;

/**
 * Created by rootmindtechsoftprivatelimited on 21/06/17.
 */

public   class ConnectHost {


    public static final String TAG = "ConnectHost";





    public  String excuteConnectHost(String methodAction,String message, SharedPreferences sharedPreferences) {

        //performPostCall(String requestURL, HashMap<String, String> postDataParams)
        URL url;
        String response = "";
        try
        {
            Log.d(TAG, "URL : "+GlobalConstants.HOST_URL);

            url = new URL(GlobalConstants.HOST_URL); //http://rootmindtech.ddns.net:8084/NowcabsWeb/Nowcabs

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //JSONObject jsonData   = new JSONObject();

           JSONObject userProfileJson   = new JSONObject();
//            userProfileJson.put("userid" , "ram");
//            userProfileJson.put("deviceToken","DEV1243");
//            userProfileJson.put("sessionid" , "SESS123");


            userProfileJson.put("userid" , sharedPreferences.getString("userid",""));
            userProfileJson.put("deviceToken",sharedPreferences.getString("deviceToken",""));
            userProfileJson.put("sessionid" , sharedPreferences.getString("sessionid",""));


//            JSONObject messageJson   = new JSONObject();
//            messageJson.put("mobileNo" , "0404040404");
//            messageJson.put("firstName","AnddroidPost");



            HashMap<String, String> map = new HashMap<>();
            map.put("userProfile",userProfileJson.toString());
//            map.put("methodAction", "insertRider");
//            map.put("message", messageJson.toString());
            map.put("methodAction", methodAction);
            map.put("message", message);



            Log.d(TAG, "map: "+map);

            conn.connect();


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(getPostDataString(map));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();


            Log.d(TAG, "responseCode: " +responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }

//            Log.d(TAG, "ConnectHost response: " +response);
//
//            // Convert String to json object
//            JSONObject json = new JSONObject(response);
//
//            // get LL json object
//            JSONObject json_LL = json.getJSONObject("insertRider");
//
//            Log.d(TAG, "resData insertRider: " +json_LL);
//
//            // get value from LL Json Object
//            String str_value=json_LL.getString("riderWrapper"); //<< get value here
//
//            Log.d(TAG, "ConnectHost resData: riderWrapper " +str_value);



        } catch (Exception e) {
            e.printStackTrace();

        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            //result.append(URLEncoder.encode(entry.toString(), "UTF-8"));
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


}


