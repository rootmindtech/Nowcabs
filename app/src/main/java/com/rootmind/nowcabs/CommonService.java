package com.rootmind.nowcabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public  class CommonService {


    SharedPreferences sharedPreferences;
    //Shared Preferences
    SharedPreferences.Editor editor;


    public static final String isNull(String input)
    {
        if(input == null) {

            return "";
        }
        else
        {
            return input;
        }
    }

    public static final boolean isEmpty(String input)
    {
        if(input == null) {

            return true;
        }
        else if(input.trim().equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static final String selectVehicleType(int input)
    {
        String vehicleType=GlobalConstants.AUTO_CODE;

        switch (input)
        {
            case 0:{
                vehicleType = GlobalConstants.AUTO_CODE;
                break;
            }
            case 1:{
                vehicleType = GlobalConstants.CAB_CODE;
                break;
            }

        }
        return vehicleType;
    }

    public static final int populateVehicleType(String input)
    {
        int currentSelect=0;

        if(isEmpty(input))
        {
            input = GlobalConstants.AUTO_CODE;
        }

        switch (input.trim())
        {
            case GlobalConstants.AUTO_CODE:{
                currentSelect = 0;
                break;
            }
            case GlobalConstants.CAB_CODE:{
                currentSelect = 1;
                break;
            }

        }
        return currentSelect;
    }


    public static final String selectLocale(int input)
    {
        String currentLanguage=GlobalConstants.ENGLISH_LOCALE;
        switch (input)
        {
            case 0:{
                currentLanguage = GlobalConstants.ENGLISH_LOCALE;
                break;
            }
            case 1:{
                currentLanguage = GlobalConstants.HINDI_LOCALE;
                break;
            }
            case 2:{
                currentLanguage = GlobalConstants.TELUGU_LOCALE;
                break;
            }
            case 3:{
                currentLanguage = GlobalConstants.TAMIL_LOCALE;
                break;
            }
            case 4:{
                currentLanguage = GlobalConstants.KANNADA_LOCALE;
                break;
            }
            case 5:{
                currentLanguage = GlobalConstants.URDU_LOCALE;
                break;
            }


        }
        return currentLanguage;
    }


    public static final int populateLocale(String input)
    {
        int currentSelect=0;

        if(isEmpty(input))
        {
            input = GlobalConstants.ENGLISH_LOCALE;
        }

        switch (input.trim())
        {
            case GlobalConstants.ENGLISH_LOCALE:{
                currentSelect = 0;
                break;
            }
            case GlobalConstants.HINDI_LOCALE:{
                currentSelect = 1;
                break;
            }
            case GlobalConstants.TELUGU_LOCALE:{
                currentSelect = 2;
                break;
            }
            case GlobalConstants.TAMIL_LOCALE:{
                currentSelect = 3;
                break;
            }
            case GlobalConstants.KANNADA_LOCALE:{
                currentSelect = 4;
                break;
            }
            case GlobalConstants.URDU_LOCALE:{
                currentSelect = 5;
                break;
            }


        }
        return currentSelect;
    }

    public static final Locale getLocale(String input)
    {
        String locale = GlobalConstants.ENGLISH_LOCALE;

        if(isEmpty(input))
        {
            locale = GlobalConstants.ENGLISH_LOCALE;
        }
        else
        {
            locale = input;
        }

        return new Locale(locale);
    }


    public static void  hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void  hideKeyboardView(Context context, View  view) {

        InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public boolean riderAutoLogin(Activity activity, Context context, String mobileNo)
    {

        String responseData = null;
        String fcmToken=null;
        String locale=null;


        try {

            //sharedPreferences initiated
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


            fcmToken = sharedPreferences.getString("fcmToken","");
            locale = getLocale(sharedPreferences.getString("locale", "")).toString();



            Log.d(GlobalConstants.CommonService, "fcm token riderautologin " + fcmToken);


            //Shared Preferences
            editor = sharedPreferences.edit();

            editor.putString("userid", "SYSTEM");
            editor.putString("deviceToken", fcmToken);
            editor.putString("sessionid", "SESSIONID");

            editor.apply();


            //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

            //loadingSpinner.setVisibility(View.VISIBLE);


            String methodAction = "riderAutoLogin";

            JSONObject messageJson = new JSONObject();
            messageJson.put("mobileNo", mobileNo);
            messageJson.put("riderRefNo", sharedPreferences.getString("riderRefNo", ""));
            messageJson.put("riderID", sharedPreferences.getString("riderID", ""));
            messageJson.put("fcmToken", fcmToken);
            messageJson.put("locale", locale);


            ConnectHost connectHost = new ConnectHost();

            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            //loadingSpinner.setVisibility(View.GONE);

            Log.d(GlobalConstants.CommonService, "RiderLogin responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.getJSONObject("riderAutoLogin");

                //Log.d(TAG, "resData insertRider: " + jsonResult);
                //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

                //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                if (jsonResponseData.getString("success") == "true") {


                    if(wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {

                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => " + c.getTime());
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                        final String formattedDate = df.format(c.getTime());

                        Rider rider = new Rider();

                        rider.setRiderRefNo(wrapperArrayObj.getJSONObject(0).getString("riderRefNo"));
                        rider.setRiderID(wrapperArrayObj.getJSONObject(0).getString("riderID"));
                        rider.setRiderMobileNo(wrapperArrayObj.getJSONObject(0).optString("mobileNo"));
                        rider.setRiderName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
                        rider.setStatus(wrapperArrayObj.getJSONObject(0).optString("status"));
                        rider.setImageFound(false);
                        rider.setDatetime(formattedDate);
                        rider.setFcmToken(fcmToken);
                        rider.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));


                        setRiderLogin(activity, context, rider, fcmToken);
                    }
                    else
                    {
                        return false;
                    }

                }
                else
                {

                    Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);


                }




            } else {

                Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

            }


        }

        catch (Exception e) {
            e.printStackTrace();

            Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);


        }

        return true;

    }


    private void setRiderLogin(Activity activity, Context context,Rider rider, String fcmToken) {


        //Shared Preferences
        editor = sharedPreferences.edit();

        Log.d(GlobalConstants.CommonService, "SharedPreferences putString ");

        editor.putString("riderMobileNo", rider.getRiderMobileNo());
//        editor.putString("riderFirstName", rider.getRiderName());
        editor.putString("userGroup", GlobalConstants.RIDER_CODE);
        editor.putString("riderRefNo", rider.getRiderRefNo());
        editor.putString("riderID", rider.getRiderID());
        editor.putString("autoLogin", GlobalConstants.YES_CODE);
        editor.putString("fcmToken", fcmToken);
        editor.putString("locale",CommonService.getLocale(rider.getLocale()).toString());
        //Log.d(TAG, "fcmToken: " + fcmToken);

        editor.apply();

        setLocale(context,CommonService.getLocale(rider.getLocale()).toString());

        //Log.d(TAG, "Saved Info: " + sharedPreferences.getString("riderMobileNo", "") );


        //Log.d(TAG, "Before Change Activity " + rider.getStatus());


        if (rider.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {

            Parameter parameter = new Parameter();

            Intent i = new Intent(context, RiderMapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Parameter", parameter);
            bundle.putSerializable("Rider", rider);
            bundle.putSerializable("locale", CommonService.getLocale(rider.getLocale()).toString());
            i.putExtras(bundle);
            context.startActivity(i);

            Log.d(GlobalConstants.CommonService, "Going to RiderMap ");
        } else {

            Log.d(GlobalConstants.CommonService, "Access Restricted");

            Toast(activity, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT);

        }


    }


    public boolean driverAutoLogin(Activity activity, Context context, String mobileNo) {

        String responseData = null;
        String fcmToken = null;
        String locale = null;

        try {


            //sharedPreferences initiated
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


            fcmToken = sharedPreferences.getString("fcmToken","");
            locale = getLocale(sharedPreferences.getString("locale", "")).toString();

            //Shared Preferences
            editor = sharedPreferences.edit();

            editor.putString("userid", "SYSTEM");
            editor.putString("deviceToken", fcmToken);
            editor.putString("sessionid", "SESSIONID");

            editor.apply();


            Log.d(GlobalConstants.CommonService, "mobileNo: " + mobileNo + " locale" + locale);

            //loadingSpinner.setVisibility(View.VISIBLE);


            String methodAction = "driverAutoLogin";

            JSONObject messageJson = new JSONObject();
            messageJson.put("mobileNo", mobileNo);
            messageJson.put("driverRefNo", sharedPreferences.getString("driverRefNo", ""));
            messageJson.put("driverID", sharedPreferences.getString("driverID", ""));
            messageJson.put("fcmToken", fcmToken);
            messageJson.put("locale", locale);


            ConnectHost connectHost = new ConnectHost();

            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            //loadingSpinner.setVisibility(View.GONE);

            Log.d(GlobalConstants.CommonService, "DriverLogin responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.getJSONObject("driverAutoLogin");

                //Log.d(TAG, "resData insertRider: " + jsonResult);
                //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("driverWrapper");

                //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                if (jsonResponseData.getString("success") == "true"){

                     if(wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                         Calendar c = Calendar.getInstance();
                         System.out.println("Current time => " + c.getTime());
                         SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                         final String formattedDate = df.format(c.getTime());

                         Driver driver = new Driver();

                         driver.setDriverRefNo(wrapperArrayObj.getJSONObject(0).getString("driverRefNo"));
                         driver.setDriverID(wrapperArrayObj.getJSONObject(0).getString("driverID"));
                         driver.setDriverMobileNo(wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
                         driver.setDriverName(wrapperArrayObj.getJSONObject(0).getString("firstName"));
                         driver.setDriverVehicleNo(wrapperArrayObj.getJSONObject(0).getString("vehicleNo"));
                         driver.setStatus(wrapperArrayObj.getJSONObject(0).getString("status"));
                         driver.setDriverVehicleType(wrapperArrayObj.getJSONObject(0).getString("vehicleType"));
                         driver.setImageFound(false);
                         driver.setDatetime(formattedDate);
                         driver.setFcmToken(fcmToken);
                         driver.setLocale(wrapperArrayObj.getJSONObject(0).getString("locale"));

                         setDriverLogin(activity, context, driver, fcmToken);


                     }
                     else
                     {
                         return  false;
                     }
                }
                else
                {

                    Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                }




            } else {

                Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);


        }

        return true;

    }

    private void setDriverLogin(Activity activity, Context context,Driver driver, String fcmToken) {

        //Shared Preferences
        editor = sharedPreferences.edit();

        Log.d(GlobalConstants.CommonService, "SharedPreferences putString ");

        //locale = CommonService.getLocale(driver.getLocale()).toString();

        editor.putString("driverMobileNo", driver.getDriverMobileNo());
        //editor.putString("driverFirstName", driver.getDriverName());
        editor.putString("userGroup", GlobalConstants.DRIVER_CODE);
        editor.putString("driverRefNo", driver.getDriverRefNo());
        editor.putString("driverID", driver.getDriverID());
        editor.putString("vehicleNo", driver.getDriverVehicleNo());
        editor.putString("vehicleType", driver.getDriverVehicleType());
        editor.putString("autoLogin", GlobalConstants.YES_CODE);
        editor.putString("locale", CommonService.getLocale(driver.getLocale()).toString());
        //editor.putString("driverRadius", wrapperArrayObj.getJSONObject(0).getString("driverRadius"));

        editor.apply();

        setLocale(context,CommonService.getLocale(driver.getLocale()).toString());

        Log.d(GlobalConstants.CommonService, "Saved Info: " + sharedPreferences.getString("driverMobileNo", ""));

        Log.d(GlobalConstants.CommonService, "Before Change Activity ");
        if (driver.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {

            Parameter parameter = new Parameter();

            Intent i = new Intent(context, DriverMapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Parameter", parameter);
            bundle.putSerializable("Driver", driver);
            bundle.putSerializable("locale", CommonService.getLocale(driver.getLocale()).toString());
            i.putExtras(bundle);
            context.startActivity(i);

            Log.d(GlobalConstants.CommonService, "Going to DriverMap ");
        } else {
            Toast(activity, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT);

        }


    }

    public void setLocale(Context context, String locale)
    {
        //---------Language setting-----
        Locale defaultLocale = new Locale(locale);
        Locale.setDefault(defaultLocale);

        /**
         * Print the current language
         */
        System.out.println("My current language: "
                + Locale.getDefault());

        Configuration config = new Configuration();
        config.locale = defaultLocale;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());

        //--------end of language setting

    }

    public static final void Toast(Activity activity, String message, int duration )
    {

        Toast.makeText(activity, message, duration).show();

    }
}