package com.rootmind.nowcabs;



import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by rootmindtechsoftprivatelimited on 28/07/17.
 */

public class DriverProfileActivity extends AppCompatActivity  {

    public Toolbar toolbar;
    public DrawerLayout drawer;
    private static final String TAG = "DriverProfileActivity";

    private EditText txt_name;
    private EditText txt_mobileNo;

    private Spinner dropDown_vehicleType;
    private EditText txt_vehicleNo;


    private Spinner dropDown_locale;

    Button btn_login;

    Button btn_logout;
    Button btn_save;
    Driver driver;

    String responseData = null;

    SharedPreferences sharedPreferences;

    //Shared Preferences
    SharedPreferences.Editor editor;


//    FirebaseDatabase firebaseDatabase;

    public String name = null;
    public String vehicleNo=null;
    public String vehicleType=null;

    public String locale=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        Context context = this; // or ActivityNotification.this


        driver = (Driver) getIntent().getSerializableExtra("Driver");

        //addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //To initiate firebase
//        firebaseDatabase = FirebaseDatabase.getInstance();


        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.profile_and_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //sidenav header
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();


        //---input text boxes
        txt_mobileNo = (EditText)findViewById(R.id.txt_mobileNo);
        txt_name = (EditText)findViewById(R.id.txt_name);
        txt_vehicleNo = (EditText)findViewById(R.id.txt_vehicleNo);

        dropDown_vehicleType = (Spinner) findViewById(R.id.dropDown_vehicleType);
        dropDown_locale = (Spinner) findViewById(R.id.dropDown_locale);



        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_save = (Button)findViewById(R.id.btn_save);

        txt_mobileNo.setText(driver.getDriverMobileNo());
        txt_name.setText(driver.getDriverName());
        txt_vehicleNo.setText(driver.getDriverVehicleNo());



        //--disable text boxes
        txt_mobileNo.setEnabled(false);
        //txt_name.setEnabled(false);

        fetchProfile();



        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Log.d(TAG, "Logout Button Click: ");


                new AlertDialog.Builder(DriverProfileActivity.this)
                        .setTitle(R.string.logout)
                        .setMessage(R.string.logout_confirm)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                //To clear SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                //editor.clear();
                                //Do not clear all as last current lat and current lng required in case onLocationChange is not fired
                                editor.putString("autoLogin", GlobalConstants.NO_CODE);
                                editor.apply();


                                //update Log
                                //NowcabsLog nowcabsLog=new NowcabsLog();
                                //nowcabsLog.updateLog(GlobalConstants.DRIVER_CODE,driver.driverID,"Logout");


                                Intent intent = new Intent(DriverProfileActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();


                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();




            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // TextWatcher would let us check validation error on the fly
                txt_name.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        Validation.hasText(txt_name);
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                });

                txt_vehicleNo.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        Validation.hasText(txt_vehicleNo);
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                });

                if (Validation.hasText(txt_name) && Validation.hasText(txt_vehicleNo)) {


                    name = txt_name.getText().toString().trim();
                    vehicleNo = txt_vehicleNo.getText().toString().trim();
                    vehicleType = CommonService.selectVehicleType(dropDown_vehicleType.getSelectedItemPosition());
                    locale = CommonService.selectLocale(dropDown_locale.getSelectedItemPosition());

                    updateProfile();

                    //updateFBProfile();
                }



            }
        });


        txt_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    txt_name.clearFocus();
                    Log.d(TAG, "RiderLogin onFocusChange:");
                    CommonService.hideKeyboardView(DriverProfileActivity.this,txt_name);
                }
            }
        });


        txt_vehicleNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    txt_vehicleNo.clearFocus();
                    Log.d(TAG, "RiderLogin onFocusChange:");
                    CommonService.hideKeyboardView(DriverProfileActivity.this,txt_vehicleNo);
                }
            }
        });



    }

    public void addListenerOnSpinnerItemSelection() {

        dropDown_vehicleType = (Spinner) findViewById(R.id.dropDown_vehicleType);
        dropDown_vehicleType.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        dropDown_locale = (Spinner) findViewById(R.id.dropDown_locale);
        dropDown_locale.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

//    public void updateFBProfile(){
//
//
//        driver.driverName=txt_name.getText().toString().trim();
//        driver.driverVehicleNo=txt_vehicleNo.getText().toString().trim();
//        driver.driverVehicleType = dropDown_vehicleType.getSelectedItem().toString().trim();
//        Map<String, Object> userProfileMap = driver.toMap();
//        firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH+"/"+ driver.driverID).updateChildren(userProfileMap);
//
//        //update Log
//        NowcabsLog nowcabsLog=new NowcabsLog();
//        nowcabsLog.updateLog(GlobalConstants.DRIVER_CODE,driver.driverID,"Profile Update");
//
//        Toast.makeText(DriverProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
//    }






    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void fetchProfile() {


        DriverProfileActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                try {


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", driver.getDriverID());
                    editor.putString("deviceToken", "DEVICETOKEN");
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);

                    String methodAction = "fetchDriver";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("driverRefNo", driver.getDriverRefNo());
                    messageJson.put("driverID", driver.getDriverID());
                    messageJson.put("status", driver.getStatus());


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "DriverLogin responseData: " + responseData);


                    if (responseData != null) {


                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.getJSONObject("fetchDriver");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("driverWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                            //driver = new Driver();
                            driver.setDriverMobileNo(wrapperArrayObj.getJSONObject(0).optString("mobileNo"));
                            driver.setDriverName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
                            driver.setDriverVehicleNo(wrapperArrayObj.getJSONObject(0).optString("vehicleNo"));
                            driver.setDriverVehicleType(wrapperArrayObj.getJSONObject(0).optString("vehicleType"));
                            driver.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));



                            txt_mobileNo.setText(driver.getDriverMobileNo());
                            txt_name.setText(driver.getDriverName());
                            txt_vehicleNo.setText(driver.getDriverVehicleNo());

                            Log.d(TAG, "vtype: " + driver.getDriverVehicleType() + " locale" + CommonService.populateVehicleType(driver.getDriverVehicleType()));


                            dropDown_vehicleType.setSelection(CommonService.populateVehicleType(driver.getDriverVehicleType()));
                            dropDown_locale.setSelection(CommonService.populateLocale(driver.getLocale()));

//                            dropDownSelection();

//                            //drop down selection
//                            int i = 0;
//                            if (driver.getDriverVehicleType().equals(GlobalConstants.AUTO_CODE)) {
//                                i = 0; //AUTO
//                            } else {
//                                i = 1; //CAB
//                            }
//                            dropDown_vehicleType.setSelection(i);






                        } else {

                            Toast.makeText(DriverProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(DriverProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                //}//validation

            }//run end

        });//runnable end


    } //-------end of fetch profile


    public void updateProfile() {


        DriverProfileActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                try {


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", driver.getDriverID());
                    editor.putString("deviceToken", "DEVICETOKEN");
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "updateDriver";

                    JSONObject messageJson = new JSONObject();
                    //messageJson.put("mobileNo", mobileNo);
                    messageJson.put("firstName", name);
                    messageJson.put("vehicleNo", vehicleNo);
                    messageJson.put("vehicleType", vehicleType);
                    messageJson.put("locale", locale);
                    messageJson.put("driverRefNo", driver.getDriverRefNo());
                    messageJson.put("driverID", driver.getDriverID());



                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "DriverLogin responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.getJSONObject("updateDriver");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("driverWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                            driver = new Driver();

                            driver.setDriverRefNo(wrapperArrayObj.getJSONObject(0).getString("driverRefNo"));
                            driver.setDriverID(wrapperArrayObj.getJSONObject(0).getString("driverID"));
                            //rider.setRiderMobileNo(wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
                            driver.setDriverName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
                            driver.setDriverVehicleNo(wrapperArrayObj.getJSONObject(0).optString("vehicleNo"));
                            driver.setDriverVehicleType(wrapperArrayObj.getJSONObject(0).optString("vehicleType"));
                            driver.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));


                            setLoginDetails();

                        } else {

                            Toast.makeText(DriverProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(DriverProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                //}//validation

            }//run end

        });//runnable end


    } //-------end of update profile



    public void setLoginDetails() {


        //Shared Preferences
        editor = sharedPreferences.edit();

        Log.d(TAG, "SharedPreferences putString ");

        editor.putString("driverFirstName", driver.getDriverName());
        editor.putString("vehicleNo", driver.getDriverVehicleNo());
        editor.putString("vehicleType", driver.getDriverVehicleType());
        editor.putString("locale", driver.getLocale());

        editor.apply();

        updateLocale(DriverProfileActivity.this, new Locale(driver.getLocale()));

        Toast.makeText(DriverProfileActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();


    }


    @TargetApi(Build.VERSION_CODES.N)
    private void updateLocale(Context context, Locale locale) {

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(configuration,
                getBaseContext().getResources().getDisplayMetrics());

    }


}
