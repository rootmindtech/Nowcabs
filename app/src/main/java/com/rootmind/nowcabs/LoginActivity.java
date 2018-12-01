package com.rootmind.nowcabs;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";


    public static final int MY_PERMISSIONS_REQUEST = 100;


    String responseData = null;


    private EditText txt_mobileNo;
    Button btn_login;

    private TextView tv_title;

    private TextView tv_versionCode;

    SharedPreferences sharedPreferences;
    //Shared Preferences
    SharedPreferences.Editor editor;

    public LinearLayout loadingSpinner;

    //Rider rider = null;
    Driver driver = null;

    public String mobileNo = null;

    public Boolean validationSuccess = false;

    //Parameter parameter;

    String fcmToken;

    String locale;
    Configuration config;

    CountryCodePicker ccp;
    EditText editTextCarrierNumber;

    CommonService commonService = null;
//     AlertDialog.Builder dialogBuilder;

//    Button btn_english;
//    Button btn_telugu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        //setContentView(R.layout.activity_login);
//        setContentView(R.layout.activity_splash_screen);
//        loadingSpinner = ((LinearLayout)findViewById(R.id.progressBarLayout));
//
//        hideProgressBar();



        //Flow 27-Oct-2018
        //LoginActivity (Auto) --> OTPActivity --> RiderLoginActivity (Record Created)
        // --> RegisterActivity --> DriverLoginActivity (Update) --> AvatarActivity (Store image)
        //--> IDCardActivity (Store image) --> RiderMapActivity


        //findViewById(R.id.loginActivity).setOnTouchListener(LoginActivity.this);


//        news.setText(R.string.note);
//        news.setTypeface(teluguFont);
//


        //android.os.NetworkOnMainThreadException
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // mInstance = this;


        //checkPermissions();


//        btn_english = (Button) findViewById(R.id.btn_english);
//        btn_telugu = (Button) findViewById(R.id.btn_telugu);
//

        //to comment
//        editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();



        //loadingSpinner = (LinearLayout) findViewById(R.id.progressBarLayout);
        //loadingSpinner.setVisibility(View.INVISIBLE);



        fcmToken = sharedPreferences.getString("fcmToken", "");

        Log.d(TAG, "fcm token shared " + fcmToken);

//        String manufacturer = Build.MANUFACTURER;
//        String model = Build.MODEL;
//        int version = Build.VERSION.SDK_INT;
//        String versionRelease = Build.VERSION.RELEASE;


        commonService = new CommonService();


//        //FCM -----to get token
//        Task<InstanceIdResult> task = FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//                    @Override
//                    public void onSuccess(InstanceIdResult instanceIdResult) {
//                        // Task completed successfully
//                        // ...
//                        fcmToken = instanceIdResult.getToken();
//
//                        editor = sharedPreferences.edit();
//                        editor.putString("fcmToken", fcmToken);
//                        editor.putString("deviceToken", fcmToken);
//                        editor.apply();
//
//
//                        Log.d(TAG, "fcm token " + fcmToken);
//
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Task failed with an exception
//                        // ...
//
//                        Log.d(TAG, "fcm token failed " );
//
//                    }
//                });
//        //---------end of FCM token

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = "default_notification_channel_id"; //getString(R.string.default_notification_channel_id);
            String channelName = "default_notification_channel_name"; //getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }




         // Get token
         FirebaseInstanceId.getInstance().getInstanceId()
                 .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                     @Override
                     public void onComplete(@NonNull Task<InstanceIdResult> task) {
                         if (!task.isSuccessful()) {
                             Log.w(TAG, "getInstanceId failed", task.getException());
                             return;
                         }

                         // Get new Instance ID token
                         String fcmToken = task.getResult().getToken();

                         String deviceID = getUUID();

                         editor = sharedPreferences.edit();
                         editor.putString("fcmToken", fcmToken);
                         editor.putString("deviceToken", fcmToken);
                         editor.putString("deviceID", deviceID);

                         editor.apply();


                         Log.d(TAG, "fcm token " + fcmToken);
                         Log.d(TAG, "device ID  " + deviceID);

                         //check for userGroup
                         //if (sharedPreferences.getString("userGroup", "").equals(GlobalConstants.RIDER_CODE)) {

                         //check for sharedPreferences values
                         //if( sharedPreferences.getString("riderMobileNo","") !=null &&  sharedPreferences.getString("riderFirstName","") !=null)
                         if (sharedPreferences.getString("autoLogin", "").equals(GlobalConstants.YES_CODE)) {


                             setContentView(R.layout.activity_splash_screen);

                             mobileNo = sharedPreferences.getString("mobileNo", "");

                             //riderLogin(true);

                             //CommonService.showProgressDialog(LoginActivity.this, getApplicationContext());

                             //auto login
                             //CommonService commonService = new CommonService();
                             //showProgressBar();

                             commonService.riderAutoLogin(new Listener<Rider>() {
                                 @Override
                                 public void on(Rider rider) {

                                     //CommonService.hideProgressDialog();

                                     //hideProgressBar();

                                     if (rider.isHostResponse()) {


                                         if (!rider.isRecordFound() || !rider.isAuthDevice()) {

                                             setComponentView();

                                         } else {
                                             firebaseCustomTokenAuth(rider);
                                         }
                                     } else {
                                         setComponentView();
                                     }

                                 }
                             }, LoginActivity.this, getApplicationContext(), mobileNo);

                         } else {

                             //to show components
                             //hideProgressBar();

                             setComponentView();

                             //CommonService.hideProgressDialog();

                         } //end of if condition for view components


                         //}


                     }
                 });


//        // [START subscribe_topics]
//        FirebaseMessaging.getInstance().subscribeToTopic("news")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "msg_subscribed"; //getString(R.string.msg_subscribed);
//                        if (!task.isSuccessful()) {
//                            msg = "msg_subscribe_failed"; //getString(R.string.msg_subscribe_failed);
//                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
//        // [END subscribe_topics]


        locale = CommonService.getLocale(sharedPreferences.getString("locale", "")).toString();


    }


    private class ProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {



            showProgressBar();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //my stuff is here


            editor = sharedPreferences.edit();
            editor.putString("mobileNo", mobileNo);
            editor.putString("locale", locale);
            editor.apply();

            //Log.d(TAG, "riderMobileNo " + sharedPreferences.getString("riderMobileNo", ""));
            //Log.d(TAG, "driverMobileNo " + sharedPreferences.getString("driverMobileNo", ""));
            //Log.d(TAG, "userGroup " + sharedPreferences.getString("userGroup", ""));


            //if matching mobile number then auto login
//            if(mobileNo.equals( sharedPreferences.getString("riderMobileNo", ""))
//                    &&  sharedPreferences.getString("userGroup", "").equals(GlobalConstants.RIDER_CODE))
//            {
//
//                riderLogin(true);
//
//            }
//            else if(mobileNo.equals( sharedPreferences.getString("driverMobileNo", ""))
//                    &&  sharedPreferences.getString("userGroup", "").equals(GlobalConstants.DRIVER_CODE))
//            {
//                driverLogin(true);
//            }


            //button click event
            commonService.riderAutoLogin(new Listener<Rider>() {
                @Override
                public void on(Rider rider) {

                    if(rider.isHostResponse()) {

                        if (!rider.isRecordFound() || !rider.isAuthDevice()) {

                            //parameter = new Parameter();

                            Intent i = new Intent(getApplicationContext(), OTPActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("mobileNo", mobileNo);
                            //bundle.putSerializable("Parameter", parameter);
                            bundle.putSerializable("locale", locale);

                            i.putExtras(bundle);
                            startActivity(i);

                        } else {
                            firebaseCustomTokenAuth(rider);
                        }
                    }

                }
            }, LoginActivity.this, getApplicationContext(), mobileNo);


            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            hideProgressBar();

        }
    }

    private void setRiderLogin(Rider rider) {


        //Shared Preferences
        editor = sharedPreferences.edit();

        Log.d(GlobalConstants.CommonService, "SharedPreferences putString ");

//        editor.putString("riderMobileNo", rider.getRiderMobileNo());
//        editor.putString("riderFirstName", rider.getRiderName());
        editor.putString("userGroup", GlobalConstants.RIDER_CODE);
        editor.putString("riderRefNo", rider.getRiderRefNo());
        editor.putString("riderID", rider.getRiderID());
        editor.putString("autoLogin", GlobalConstants.YES_CODE);
        editor.putString("fcmToken", fcmToken);
        editor.putString("locale", CommonService.getLocale(rider.getLocale()).toString());
        //Log.d(TAG, "fcmToken: " + fcmToken);

        editor.apply();

        commonService.setLocale(this, CommonService.getLocale(rider.getLocale()).toString());

        //Log.d(TAG, "Saved Info: " + sharedPreferences.getString("riderMobileNo", "") );


        //Log.d(TAG, "Before Change Activity " + rider.getStatus());


        if (rider.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {

            Parameter parameter = new Parameter();

            Intent i = new Intent(getApplicationContext(), RiderMapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Parameter", parameter);
            bundle.putSerializable("Rider", rider);
            bundle.putSerializable("locale", CommonService.getLocale(rider.getLocale()).toString());
            i.putExtras(bundle);
            startActivity(i);

            Log.d(GlobalConstants.CommonService, "Going to RiderMap ");
        } else {

            Log.d(GlobalConstants.CommonService, "Access Restricted");

            CommonService.Toast(this, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT);

        }


    }

    public void firebaseCustomTokenAuth(final Rider rider) {

        if (rider.getCustomToken() != null) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.signInWithCustomToken(rider.getCustomToken())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "signInWithCustomToken:success");
                                //FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);

                                setRiderLogin(rider);

                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Invalid custom token.", Toast.LENGTH_SHORT).show();

        }

    }


//    public boolean riderLogin(boolean autoLogin) {
//
//
//        final boolean result=false;
//
//        if (!autoLogin) {
//
//        }
//
//
////        LoginActivity.this.runOnUiThread(new Runnable() {
////
////            @Override
////            public void run() {
//
//
//                CommonService commonService = new CommonService();
//                commonService.riderAutoLogin(new Listener<Boolean>() {
//                    @Override
//                    public void on(Boolean arg) {
//
//                        result=arg;
//                    }
//                }, LoginActivity.this, getApplicationContext(), mobileNo);
//
//
////                try {
////
////
////                    Log.d(TAG, "fcm token riderautologin " + fcmToken);
////
////
////                    //Shared Preferences
////                    editor = sharedPreferences.edit();
////
////                    editor.putString("userid", "SYSTEM");
////                    editor.putString("deviceToken", fcmToken);
////                    editor.putString("sessionid", "SESSIONID");
////
////                    editor.apply();
////
////
////                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);
////
////                    loadingSpinner.setVisibility(View.VISIBLE);
////
////
////                    String methodAction = "riderAutoLogin";
////
////                    JSONObject messageJson = new JSONObject();
////                    messageJson.put("mobileNo", mobileNo);
////                    messageJson.put("riderRefNo", sharedPreferences.getString("riderRefNo", ""));
////                    messageJson.put("riderID", sharedPreferences.getString("riderID", ""));
////                    messageJson.put("fcmToken", fcmToken);
////                    messageJson.put("locale", locale);
////
////
////                    ConnectHost connectHost = new ConnectHost();
////
////                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
////
////                    loadingSpinner.setVisibility(View.GONE);
////
////                    Log.d(TAG, "RiderLogin responseData: " + responseData);
////
////
////                    if (responseData != null) {
////
////
////                        // Convert String to json object
////                        JSONObject jsonResponseData = new JSONObject(responseData);
////
////                        // get LL json object
////                        JSONObject jsonResult = jsonResponseData.getJSONObject("riderAutoLogin");
////
////                        //Log.d(TAG, "resData insertRider: " + jsonResult);
////                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));
////
////                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");
////
////                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
////                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));
////
////                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {
////
////
////
////                            Calendar c = Calendar.getInstance();
////                            System.out.println("Current time => " + c.getTime());
////                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
////                            final String formattedDate = df.format(c.getTime());
////
////                            rider = new Rider();
////
////                            rider.setRiderRefNo(wrapperArrayObj.getJSONObject(0).getString("riderRefNo"));
////                            rider.setRiderID(wrapperArrayObj.getJSONObject(0).getString("riderID"));
////                            rider.setRiderMobileNo(wrapperArrayObj.getJSONObject(0).optString("mobileNo"));
////                            rider.setRiderName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
////                            rider.setStatus(wrapperArrayObj.getJSONObject(0).optString("status"));
////                            rider.setImageFound(false);
////                            rider.setDatetime(formattedDate);
////                            rider.setFcmToken(fcmToken);
////                            rider.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));
////
////
////                            setRiderLogin();
////
////
////
////                        }
////                        else
////                        {
////
////                            Toast.makeText(LoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
////
////
////                        }
////
////
////
////
////                    } else {
////
////                        Toast.makeText(LoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
////
////                    }
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////
////                    Toast.makeText(LoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
////
////
////                }
//
//
//                //}//validation
//
////            }//run end
////
////        });//runnable end
//
//
//    }


//    public void setRiderLogin() {
//
//
//        //Shared Preferences
//        editor = sharedPreferences.edit();
//
//        Log.d(TAG, "SharedPreferences putString ");
//
//        locale =  CommonService.getLocale(rider.getLocale()).toString();
//        editor.putString("riderMobileNo", rider.getRiderMobileNo());
////        editor.putString("riderFirstName", rider.getRiderName());
//        editor.putString("userGroup", GlobalConstants.RIDER_CODE);
//        editor.putString("riderRefNo", rider.getRiderRefNo());
//        editor.putString("riderID", rider.getRiderID());
//        editor.putString("autoLogin", GlobalConstants.YES_CODE);
//        editor.putString("fcmToken", fcmToken);
//        editor.putString("locale",locale);
//        Log.d(TAG, "fcmToken: " + fcmToken);
//
//        editor.apply();
//
//        setLocale();
//
//        Log.d(TAG, "Saved Info: " + sharedPreferences.getString("riderMobileNo", "") );
//
//
//        Log.d(TAG, "Before Change Activity " + rider.getStatus());
//
//
//        if (rider.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {
//
//            parameter = new Parameter();
//
//            Intent i = new Intent(getApplicationContext(), RiderMapActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("Parameter", parameter);
//            bundle.putSerializable("Rider", rider);
//            bundle.putSerializable("locale", CommonService.getLocale(rider.getLocale()).toString());
//            i.putExtras(bundle);
//            startActivity(i);
//
//            Log.d(TAG, "Going to RiderMap ");
//        } else {
//            Log.d(TAG, "Access Restricted");
//
//            Toast.makeText(LoginActivity.this, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT).show();
//
//        }
//
//
//    }


//    public void driverLogin(boolean autoLogin) {
//
//
//        if (!autoLogin) {
//
//        }
//
//
//        LoginActivity.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
//
//                CommonService commonService = new CommonService();
//                commonService.driverAutoLogin(LoginActivity.this, getApplicationContext(), mobileNo);
//
//
////                try {
////
////
////                    //Shared Preferences
////                    editor = sharedPreferences.edit();
////
////                    editor.putString("userid", "SYSTEM");
////                    editor.putString("deviceToken", fcmToken);
////                    editor.putString("sessionid", "SESSIONID");
////
////                    editor.apply();
////
////
////                    Log.d(TAG, "mobileNo: " + mobileNo + " locale" + locale);
////
////                    loadingSpinner.setVisibility(View.VISIBLE);
////
////
////                    String methodAction = "driverAutoLogin";
////
////                    JSONObject messageJson = new JSONObject();
////                    messageJson.put("mobileNo", mobileNo);
////                    messageJson.put("driverRefNo", sharedPreferences.getString("driverRefNo", ""));
////                    messageJson.put("driverID", sharedPreferences.getString("driverID", ""));
////                    messageJson.put("fcmToken", fcmToken);
////                    messageJson.put("locale", locale);
////
////
////                    ConnectHost connectHost = new ConnectHost();
////
////                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
////
////                    loadingSpinner.setVisibility(View.GONE);
////
////                    Log.d(TAG, "DriverLogin responseData: " + responseData);
////
////
////                    if (responseData != null) {
////
////
////                        // Convert String to json object
////                        JSONObject jsonResponseData = new JSONObject(responseData);
////
////                        // get LL json object
////                        JSONObject jsonResult = jsonResponseData.getJSONObject("driverAutoLogin");
////
////                        //Log.d(TAG, "resData insertRider: " + jsonResult);
////                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));
////
////                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("driverWrapper");
////
////                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
////                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));
////
////                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {
////
////
////
////                            Calendar c = Calendar.getInstance();
////                            System.out.println("Current time => " + c.getTime());
////                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
////                            final String formattedDate = df.format(c.getTime());
////
////                            driver = new Driver();
////
////                            driver.setDriverRefNo(wrapperArrayObj.getJSONObject(0).getString("driverRefNo"));
////                            driver.setDriverID(wrapperArrayObj.getJSONObject(0).getString("driverID"));
////                            driver.setDriverMobileNo(wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
////                            driver.setDriverName(wrapperArrayObj.getJSONObject(0).getString("firstName"));
////                            driver.setDriverVehicleNo(wrapperArrayObj.getJSONObject(0).getString("vehicleNo"));
////                            driver.setStatus(wrapperArrayObj.getJSONObject(0).getString("status"));
////                            driver.setDriverVehicleType(wrapperArrayObj.getJSONObject(0).getString("vehicleType"));
////                            driver.setImageFound(false);
////                            driver.setDatetime(formattedDate);
////                            driver.setFcmToken(fcmToken);
////                            driver.setLocale(wrapperArrayObj.getJSONObject(0).getString("locale"));
////
////                            setDriverLogin();
////
////
////
////                        }
////                        else
////                        {
////
////                            Toast.makeText(LoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
////
////                        }
////
////
////
////
////                    } else {
////
////                        Toast.makeText(LoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
////
////                    }
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                    Toast.makeText(LoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
////
////
////                }
//
//
//                //}//validation
//
//            }//run end
//
//        });//runnable end
//
//
//    }


//    public void setDriverLogin () {
//
//
//        //Shared Preferences
//        editor = sharedPreferences.edit();
//
//        Log.d(TAG, "SharedPreferences putString ");
//
//        locale = CommonService.getLocale(driver.getLocale()).toString();
//
//        editor.putString("driverMobileNo", driver.getDriverMobileNo());
//        //editor.putString("driverFirstName", driver.getDriverName());
//        editor.putString("userGroup", GlobalConstants.DRIVER_CODE);
//        editor.putString("driverRefNo", driver.getDriverRefNo());
//        editor.putString("driverID", driver.getDriverID());
//        editor.putString("vehicleNo", driver.getDriverVehicleNo());
//        editor.putString("vehicleType", driver.getDriverVehicleType());
//        editor.putString("autoLogin", GlobalConstants.YES_CODE);
//        editor.putString("locale", locale);
//        //editor.putString("driverRadius", wrapperArrayObj.getJSONObject(0).getString("driverRadius"));
//
//        editor.apply();
//
//        setLocale();
//
//        Log.d(TAG, "Saved Info: " + sharedPreferences.getString("driverMobileNo", ""));
//
//        Log.d(TAG, "Before Change Activity ");
//        if (driver.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {
//
//            parameter = new Parameter();
//
//            Intent i = new Intent(getApplicationContext(), DriverMapActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("Parameter", parameter);
//            bundle.putSerializable("Driver", driver);
//            bundle.putSerializable("locale", CommonService.getLocale(driver.getLocale()).toString());
//            i.putExtras(bundle);
//            startActivity(i);
//
//            Log.d(TAG, "Going to DriverMap ");
//        } else {
//            Toast.makeText(LoginActivity.this, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT).show();
//
//        }
//    }

    private boolean checkValidation() {
        boolean ret = true;


//        txt_mobileNo.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//                Validation.isPhoneNumber(txt_mobileNo, true);
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        });
//
//
//        if (!Validation.isPhoneNumber(txt_mobileNo, true)) ret = false;
//
//        if (ret) {
//            mobileNo = txt_mobileNo.getText().toString().trim();
//        }

        if (ccp.isValidFullNumber()) {
            mobileNo = ccp.getFullNumber();

            Log.d(TAG, "checkValidation: " + mobileNo);
            ret = true;

        } else {
            ret = false;
            Toast.makeText(LoginActivity.this, R.string.invalid_mobile, Toast.LENGTH_SHORT).show();


        }

        return ret;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }

        return false;

    }


    //to show components on the screen
    public void setComponentView() {

        try {

            //if not auto login then show components

            setContentView(R.layout.activity_login);

            ccp = (CountryCodePicker) findViewById(R.id.ccp);
            editTextCarrierNumber = (EditText) findViewById(R.id.editText_carrierNumber);

            ccp.registerCarrierNumberEditText(editTextCarrierNumber);

//        txt_mobileNo = (EditText) findViewById(R.id.txt_mobileNo);

            btn_login = (Button) findViewById(R.id.btn_login);

            tv_versionCode = (TextView) findViewById(R.id.tv_versionCode);


            loadingSpinner = ((LinearLayout)findViewById(R.id.progressBarLayout));
            hideProgressBar();


//        locale = CommonService.getLocale(sharedPreferences.getString("locale", "")).toString();

//        txt_mobileNo.setText(sharedPreferences.getString("mobileNo", ""));


            mobileNo = sharedPreferences.getString("mobileNo", "");

            Log.d(TAG, "mobileNo shared: " + mobileNo);

            ccp.setFullNumber(mobileNo);


            //check for sharedPreferences values
//            if (sharedPreferences.getString("userGroup", "").equals(GlobalConstants.RIDER_CODE) &&
//                    !CommonService.isEmpty(sharedPreferences.getString("riderMobileNo", ""))) {
//
//                //txt_mobileNo.setText(sharedPreferences.getString("riderMobileNo", ""));
//                ccp.setFullNumber(sharedPreferences.getString("riderMobileNo", ""));
//
//
//            }
//            if (sharedPreferences.getString("userGroup", "").equals(GlobalConstants.DRIVER_CODE) &&
//                    !CommonService.isEmpty(sharedPreferences.getString("driverMobileNo", ""))) {
//
//                Log.d(TAG, "driver is empty: " + sharedPreferences.getString("driverMobileNo", ""));
//
//                //txt_mobileNo.setText(sharedPreferences.getString("driverMobileNo", ""));
//                ccp.setFullNumber(sharedPreferences.getString("driverMobileNo", ""));
//
//            }

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.d(TAG, "onClick: " + mobileNo);


                    if (!checkValidation()) {
                        return;
                    }

                    editTextCarrierNumber.clearFocus();
                    CommonService.hideKeyboard(LoginActivity.this);


                    showLocale();


                }
            });

            ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
                @Override
                public void onValidityChanged(boolean isValidNumber) {
                    // your code
                    if (isValidNumber) {
                        editTextCarrierNumber.clearFocus();
                        CommonService.hideKeyboard(LoginActivity.this);
                    }
                }
            });

            editTextCarrierNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        editTextCarrierNumber.clearFocus();
                        Log.d(TAG, "Login onFocusChange:");
                        CommonService.hideKeyboard(LoginActivity.this);
                        CommonService.hideKeyboardView(LoginActivity.this, editTextCarrierNumber);
                    }
                }
            });


            editTextCarrierNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        editTextCarrierNumber.clearFocus();
                        Log.d(TAG, "Login onFocusChange:");
                        CommonService.hideKeyboardView(LoginActivity.this, editTextCarrierNumber);
                    }
                }
            });


            tv_versionCode.setText("v " + BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    } //end of setComponentView

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
            int hasLocationPermission = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCallPhonePermission = checkSelfPermission(android.Manifest.permission.CALL_PHONE);
            int hasStatePhonePermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);


            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);

            }

            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);

            }

            if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CALL_PHONE);

            }


            if (hasStatePhonePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);

            }


            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), MY_PERMISSIONS_REQUEST);


            }
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);


                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);

                    }
                }
            }

            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }


    //-----locale dialog
    public void showLocale() {

        locale = sharedPreferences.getString("locale", "");

        if (CommonService.isEmpty(locale)) {

            String[] values = getResources().getStringArray(R.array.locale_array);

            new AlertDialog.Builder(this)
                    .setTitle(R.string.select_language)
                    .setSingleChoiceItems(values, -1, null)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                            if (selectedPosition >= 0) {

                                dialog.dismiss();

                                // Do something useful withe the position of the selected radio button
                                locale = CommonService.selectLocale(selectedPosition);

                                editor = sharedPreferences.edit();
                                editor.putString("locale", locale);
                                editor.apply();

                                dialog.dismiss();

                                new LoginActivity.ProgressTask().execute();
                            } else {
                                Toast.makeText(LoginActivity.this, R.string.select_language, Toast.LENGTH_SHORT).show();

                            }

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    })

                    .show();

        } else {

            new LoginActivity.ProgressTask().execute();


        }


    }

    public  void showProgressBar() {

        loadingSpinner.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    public  void hideProgressBar()
    {

        loadingSpinner.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }


    private String getUUID(){
        TelephonyManager teleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        String tmSerial = teleManager.getSimSerialNumber();
        String tmDeviceId  = teleManager.getDeviceId();

        String androidId = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (tmSerial  == null) tmSerial   = "1";
        if (tmDeviceId== null) tmDeviceId = "1";
        if (androidId == null) androidId  = "1";
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDeviceId.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }



}





//
//    public void setLocale()
//    {
//        //---------Language setting-----
//        Locale defaultLocale = new Locale(locale);
//        Locale.setDefault(defaultLocale);
//
//        /**
//         * Print the current language
//         */
//        System.out.println("My current language: "
//                + Locale.getDefault());
//
//        config = new Configuration();
//        config.locale = defaultLocale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());
//
//        //--------end of language setting
//
//    }


//    public void hideSplashScreen()
//    {
//
//        try {
//            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.splashLayout);
//            linearLayout.setVisibility(View.GONE);
//            setContentView(R.layout.activity_login);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            Toast.makeText(LoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//
//        }
//
//    }


//        btn_english.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Log.d(TAG, "onClick: English");
//
//                currentLanguage=GlobalConstants.ENGLISH_LOCALE;
//
//                updateResourcesLocale(LoginActivity.this, new Locale(GlobalConstants.ENGLISH_LOCALE));
//
//            }
//        });
//
//        btn_telugu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Log.d(TAG, "onClick: Telugu");
//
//                currentLanguage=GlobalConstants.TELUGU_LOCALE;
//                updateResourcesLocale(LoginActivity.this, new Locale(GlobalConstants.TELUGU_LOCALE));
//
//            }
//        });

// else if (sharedPreferences.getString("userGroup", "").equals(GlobalConstants.DRIVER_CODE)) {
//
//
//                    if (sharedPreferences.getString("autoLogin", "").equals(GlobalConstants.YES_CODE)) {
//
////                locale = CommonService.getLocale(sharedPreferences.getString("locale", "")).toString();
//
//                        setContentView(R.layout.activity_splash_screen);
//
//                        mobileNo = sharedPreferences.getString("driverMobileNo", "");
//
//                        driverLogin(true);
//
//
//                    } else {
//
//                        //to show components
//                        setComponentView();
//
//                    } //end of if condition for view components
//
//
//                }
//                    else {
//
//                    //to show components, if no sharedPreferences are stored
//                    setComponentView();
//
//
//                }


                // Log and toast
                //String msg = getString(R.string.msg_token_fmt, token);
                //Log.d(TAG, msg);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
