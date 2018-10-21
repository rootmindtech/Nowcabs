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
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by rootmindtechsoftprivatelimited on 28/07/17.
 */

public class RiderProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public DrawerLayout drawer;
    private static final String TAG = "RiderProfileActivity";

    private EditText txt_name;
    private EditText txt_mobileNo;
    private Spinner dropDown_locale;

    Button btn_logout;
    Button btn_save;
    Rider rider;

    String responseData = null;


    SharedPreferences sharedPreferences;
    //Shared Preferences
    SharedPreferences.Editor editor;

//    //05-Oct-2018 --Firebase suppress
//    FirebaseDatabase firebaseDatabase;

    //public String mobileNo = null;
    public String name = null;

    public String locale=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_profile);
        Context context = this; // or ActivityNotification.this


        rider = (Rider) getIntent().getSerializableExtra("Rider");

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//        //05-Oct-2018 --Firebase suppress
//        //To initiate firebase
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
        txt_mobileNo = (EditText) findViewById(R.id.txt_mobileNo);
        txt_name = (EditText) findViewById(R.id.txt_name);
        dropDown_locale = (Spinner) findViewById(R.id.dropDown_locale);


        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_save = (Button) findViewById(R.id.btn_save);


        //--disable text boxes
        txt_mobileNo.setEnabled(false);
        //txt_name.setEnabled(false);

        fetchProfile();


        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Log.d(TAG, "Logout Button Click: ");


                new AlertDialog.Builder(RiderProfileActivity.this)
                        .setTitle(R.string.logout)
                        .setMessage(R.string.logout_confirm)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                //To clear SharedPreferences
                                editor = sharedPreferences.edit();
                                //editor.clear();
                                //Do not clear all as last current lat and current lng required in case onLocationChange is not fired
                                editor.putString("autoLogin", GlobalConstants.NO_CODE);
                                editor.commit();


                                //update Log
                                //NowcabsLog nowcabsLog = new NowcabsLog();
                                //nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Logout");


                                Intent intent = new Intent(RiderProfileActivity.this, LoginActivity.class);
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

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                });

                if (Validation.hasText(txt_name)) {

                    name = txt_name.getText().toString().trim();
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
                    CommonService.hideKeyboardView(RiderProfileActivity.this,txt_name);
                }
            }
        });


    }

    public void addListenerOnSpinnerItemSelection() {

        dropDown_locale = (Spinner) findViewById(R.id.dropDown_locale);
        dropDown_locale.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }


//    //05-Oct-2018 --Firebase suppress
//    public void updateFBProfile() {
//
//
//        rider.riderName = txt_name.getText().toString().trim();
//        Map<String, Object> userProfileMap = rider.toMap();
//        firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH + "/" + rider.riderID).updateChildren(userProfileMap);
//
//        //update Log
//        NowcabsLog nowcabsLog = new NowcabsLog();
//        nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Profile Update");
//
//        Toast.makeText(RiderProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void fetchProfile() {


        RiderProfileActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                try {


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", "DEVICETOKEN");
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);

                    String methodAction = "fetchRider";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());
                    messageJson.put("status", rider.getStatus());


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.getJSONObject("fetchRider");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                            //rider = new Rider();
                            rider.setRiderMobileNo(wrapperArrayObj.getJSONObject(0).optString("mobileNo"));
                            rider.setRiderName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
                            rider.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));

                            txt_mobileNo.setText(rider.getRiderMobileNo());
                            txt_name.setText(rider.getRiderName());

                            dropDown_locale.setSelection(CommonService.populateLocale(rider.getLocale()));



                        } else {

                            Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                //}//validation

            }//run end

        });//runnable end


    } //-------end of fetch profile


    public void updateProfile() {


        RiderProfileActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                try {


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", "DEVICETOKEN");
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "updateRider";

                    JSONObject messageJson = new JSONObject();
                    //messageJson.put("mobileNo", mobileNo);
                    messageJson.put("firstName", name);
                    messageJson.put("locale", locale);
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());



                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.getJSONObject("updateRider");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                            rider = new Rider();

                            rider.setRiderRefNo(wrapperArrayObj.getJSONObject(0).getString("riderRefNo"));
                            rider.setRiderID(wrapperArrayObj.getJSONObject(0).getString("riderID"));
                            //rider.setRiderMobileNo(wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
                            rider.setRiderName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
                            rider.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));

                            setLoginDetails();

                        } else {

                            Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
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

        editor.putString("riderFirstName", rider.getRiderName());
        editor.putString("locale", rider.getLocale());


        editor.apply();

        updateLocale(RiderProfileActivity.this, new Locale(rider.getLocale()));

        Toast.makeText(RiderProfileActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();


    }


    @TargetApi(Build.VERSION_CODES.N)
    private void updateLocale(Context context, Locale locale) {

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(configuration,
                getBaseContext().getResources().getDisplayMetrics());

    }
}
