package com.rootmind.nowcabs;
import android.util.Log;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.*;
import org.json.JSONObject;

import java.util.*;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import android.content.SharedPreferences;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;


/**
 * Created by rootmindtechsoftprivatelimited on 21/06/17.
 */

public   class ConnectHost {


    public static final String TAG = "ConnectHost";

    OkHttpClient okHttpClient=null;
    public static final MediaType JSON  = MediaType.parse("application/json; charset=utf-8");

    public ConnectHost()
    {

        okHttpClient = OkHttpSingleton.getInstance().getClient();

    }

    public String excuteConnectHost(String methodAction,String message, SharedPreferences sharedPreferences) throws IOException {

        String httpResponse=null;
        try {

            JSONObject messageJson = new JSONObject();

            messageJson.put("userid", sharedPreferences.getString("userid", ""));
            messageJson.put("deviceToken", sharedPreferences.getString("deviceToken", ""));
            messageJson.put("sessionid", sharedPreferences.getString("sessionid", ""));

            messageJson.putOpt("methodAction", methodAction);
            messageJson.putOpt("message", message);


            Log.d(TAG, "messageJson... : "+ messageJson.toString());


            RequestBody body = RequestBody.create(JSON, messageJson.toString());
            Request request = new Request.Builder()
                    .url(GlobalConstants.HOST_URL)
                    .post(body)
                    .build();

            Response response = okHttpClient.newCall(request).execute();

            httpResponse = response.body().string();



        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return httpResponse;
    }


//    public  String excuteConnectHost(String methodAction,String message, SharedPreferences sharedPreferences) {
//
//        URL url;
//        String response = "";
//        try
//        {
//            Log.d(TAG, "URL : "+GlobalConstants.HOST_URL);
//
//            url = new URL(GlobalConstants.HOST_URL); //http://rootmindtech.ddns.net:8084/NowcabsWeb/Nowcabs
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(15000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            JSONObject userProfileJson   = new JSONObject();
//
//            userProfileJson.put("userid" , sharedPreferences.getString("userid",""));
//            userProfileJson.put("deviceToken",sharedPreferences.getString("deviceToken",""));
//            userProfileJson.put("sessionid" , sharedPreferences.getString("sessionid",""));
//
//            HashMap<String, String> map = new HashMap<>();
//            map.put("userProfile",userProfileJson.toString());
//            map.put("methodAction", methodAction);
//            map.put("message", message);
//
//
//
//            Log.d(TAG, "map: "+map);
//
//            conn.connect();
//
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//
//            writer.write(getPostDataString(map));
//
//            writer.flush();
//            writer.close();
//            os.close();
//
//            int responseCode=conn.getResponseCode();
//
//
//            Log.d(TAG, "responseCode: " +responseCode);
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                String line;
//                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                while ((line=br.readLine()) != null) {
//                    response+=line;
//                }
//            }
//            else {
//                response="";
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//        return response;
//    }
//
//    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//        for(Map.Entry<String, String> entry : params.entrySet()){
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//        }
//
//        return result.toString();
//    }




}


