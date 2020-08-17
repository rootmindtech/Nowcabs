package com.rootmind.nowcabs;
import android.util.Log;

import org.json.JSONObject;


import android.content.SharedPreferences;

import com.squareup.okhttp.Call;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.Arrays;


/**
 * Created by rootmindtechsoftprivatelimited on 21/06/17.
 */

public class ConnectHost {


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


            Log.d(TAG, "excuteConnectHost messageJson... : "+ messageJson.toString());


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


    public void excuteAsyncConnectHost(String methodAction,String message, SharedPreferences sharedPreferences, GenericCallback genericCallback) throws IOException {

        String httpResponse=null;
        try {

            JSONObject messageJson = new JSONObject();

            messageJson.put("userid", sharedPreferences.getString("userid", ""));
            messageJson.put("deviceToken", sharedPreferences.getString("deviceToken", ""));
            messageJson.put("sessionid", sharedPreferences.getString("sessionid", ""));

            messageJson.putOpt("methodAction", methodAction);
            messageJson.putOpt("message", message);


            Log.d(TAG, "excuteAsyncConnectHost messageJson... : "+ messageJson.toString());


            RequestBody body = RequestBody.create(JSON, messageJson.toString());
            Request request = new Request.Builder()
                    .url(GlobalConstants.HOST_URL)
                    .post(body)
                    .build();

            Response response = okHttpClient.newCall(request).execute();

            //httpResponse = response.body().string();


            okHttpClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {

                            String res = response.body().string();
                            genericCallback.onValue(res);

                        }
//                        @Override
//                        public void onFailure(final Call call, IOException e) {
//                            // Error
//
//                        }
//
//                        @Override
//                        public void onResponse(Call call, final Response response) throws IOException {
//                            String res = response.body().string();
//                            genericCallback.onValue(res);
//
//
//                        }
                    });




        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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


