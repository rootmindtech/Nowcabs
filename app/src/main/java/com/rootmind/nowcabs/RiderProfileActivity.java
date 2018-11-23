package com.rootmind.nowcabs;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by rootmindtechsoftprivatelimited on 28/07/17.
 */

//implements NavigationView.OnNavigationItemSelectedListener

public class RiderProfileActivity extends AppCompatActivity {

    public Toolbar toolbar;
    //public DrawerLayout drawer;
    private static final String TAG = "RiderProfileActivity";

    private EditText txt_name;
    private EditText txt_mobileNo;
    private Spinner dropDown_locale;

//    Button btn_logout;
    Button btn_save;
    Switch swt_vacant;

    Rider rider;

    //String responseData = null;


    SharedPreferences sharedPreferences;
    //Shared Preferences
    SharedPreferences.Editor editor;

//    //05-Oct-2018 --Firebase suppress
//    FirebaseDatabase firebaseDatabase;

    //public String mobileNo = null;
    public String name = null;

    public String locale = null;

    ImageView iv_avatar;

    CommonService commonService = null;
    String responseData = null;
    public LinearLayout loadingSpinner;


    @SuppressLint("ResourceType")
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
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        commonService = new CommonService();


        //---input text boxes
        txt_mobileNo = (EditText) findViewById(R.id.txt_mobileNo);
        txt_name = (EditText) findViewById(R.id.txt_name);
        dropDown_locale = (Spinner) findViewById(R.id.dropDown_locale);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);


//        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_save = (Button) findViewById(R.id.btn_save);
        swt_vacant = (Switch) findViewById(R.id.swt_vacant);


        loadingSpinner = ((LinearLayout) findViewById(R.id.progressBarLayout));

        hideProgressBar();


        //--disable text boxes
        txt_mobileNo.setEnabled(false);
        //txt_name.setEnabled(false);



//        btn_logout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//                Log.d(TAG, "Logout Button Click: ");
//
//
//                new AlertDialog.Builder(RiderProfileActivity.this)
//                        .setTitle(R.string.logout)
//                        .setMessage(R.string.logout_confirm)
//                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//                                //To clear SharedPreferences
//                                editor = sharedPreferences.edit();
//                                //editor.clear();
//                                //Do not clear all as last current lat and current lng required in case onLocationChange is not fired
//                                editor.putString("autoLogin", GlobalConstants.NO_CODE);
//                                editor.commit();
//
//
//                                //update Log
//                                //NowcabsLog nowcabsLog = new NowcabsLog();
//                                //nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Logout");
//
//
//                                Intent intent = new Intent(RiderProfileActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                                finish();
//
//
//                            }
//                        })
//                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            }
//        });


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


                    rider.setRiderName(name);
                    rider.setLocale(locale);

                    new RiderProfileActivity.updateProfileProgressTask().execute();
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
                    CommonService.hideKeyboardView(RiderProfileActivity.this, txt_name);
                }
            }
        });

        iv_avatar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                commonService.openFrontCamera(RiderProfileActivity.this);

            }
        });

        swt_vacant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                if (isChecked) {
                    new RiderProfileActivity.VacantProgressTask().execute(GlobalConstants.VACANT_CODE, null, null);
                    swt_vacant.setText(GlobalConstants.VACANT_CODE);

                } else {

                    new RiderProfileActivity.VacantProgressTask().execute(GlobalConstants.HIRED_CODE, null, null);
                    swt_vacant.setText(GlobalConstants.HIRED_CODE);

                }

            }
        });

        new RiderProfileActivity.ProfileProgressTask().execute(null,null,null);

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


//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
////        if (id == R.id.nav_camera) {
////            // Handle the camera action
////        } else if (id == R.id.nav_gallery) {
////
////        } else if (id == R.id.nav_slideshow) {
////
////        } else if (id == R.id.nav_manage) {
////
////        } else if (id == R.id.nav_share) {
////
////        } else if (id == R.id.nav_send) {
////
////        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    public void fetchProfile()
//    {
//
//        //onCreate
//        final CommonService commonService = new CommonService();
//        commonService.fetchRider(new Listener<Boolean>() {
//            @Override
//            public void on(Boolean arg) {
//
//                if(arg)
//                {
//
//
//
//                    txt_mobileNo.setText(rider.getRiderMobileNo());
//                    txt_name.setText(rider.getRiderName());
//                    dropDown_locale.setSelection(CommonService.populateLocale(rider.getLocale()));
//
//                    commonService.getImage(iv_avatar,rider.getImageName());
//
//
//                }
//
//            }
//        }, RiderProfileActivity.this, getApplicationContext(), rider);
//
//
//    }


    private class ProfileProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            //showProgressBar();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //my stuff is here


            //button click event
            final CommonService commonService = new CommonService();
            commonService.fetchRider(new Listener<Boolean>() {
                @Override
                public void on(Boolean arg) {

                    if (arg) {
                        txt_mobileNo.setText(rider.getMobileNo());
                        txt_name.setText(rider.getRiderName());
                        dropDown_locale.setSelection(CommonService.populateLocale(rider.getLocale()));

                        commonService.getImage(iv_avatar, CommonService.getImageName(rider, GlobalConstants.IMAGE_AVATAR));

                        Log.d(TAG, "getVacantStatus: " + rider.getVacantStatus());

                        if (rider.getVacantStatus().equals(GlobalConstants.HIRED_CODE)) {
                            swt_vacant.setText(GlobalConstants.HIRED_CODE);
                            swt_vacant.setChecked(false);
                        } else {
                            swt_vacant.setText(GlobalConstants.VACANT_CODE);
                            swt_vacant.setChecked(true);

                        }

                    } else {
                        btn_save.setVisibility(View.GONE);
                    }


                }

            }, RiderProfileActivity.this, getApplicationContext(), rider);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            //hideProgressBar();

        }


    }







//    public void fetchProfile() {
//
//
//        RiderProfileActivity.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
//
//                try {
//
//
//                    //Shared Preferences
//                    editor = sharedPreferences.edit();
//
//                    editor.putString("userid", rider.getRiderID());
//                    editor.putString("deviceToken", rider.getFcmToken());
//                    editor.putString("sessionid", "SESSIONID");
//
//                    editor.apply();
//
//
//                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);
//
//                    //loadingSpinner.setVisibility(View.VISIBLE);
//
//                    String methodAction = "fetchRider";
//
//                    JSONObject messageJson = new JSONObject();
//                    messageJson.put("riderRefNo", rider.getRiderRefNo());
//                    messageJson.put("riderID", rider.getRiderID());
//                    messageJson.put("status", rider.getStatus());
//                    messageJson.put("imageID", GlobalConstants.IMAGE_AVATAR);
//
//
//
//                    ConnectHost connectHost = new ConnectHost();
//                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
//                    // loadingSpinner.setVisibility(View.GONE);
//
//                    Log.d(TAG, "RiderLogin responseData: " + responseData);
//
//
//                    if (responseData != null) {
//
//
//                        JSONObject jsonResponseData = new JSONObject(responseData);
//                        JSONObject jsonResult = jsonResponseData.getJSONObject("fetchRider");
//
//                        //Log.d(TAG, "resData insertRider: " + jsonResult);
//                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));
//
//                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");
//
//                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));
//
//                        if (jsonResponseData.optString("success") == "true" && wrapperArrayObj.getJSONObject(0).optString("recordFound") == "true") {
//
//
//                            //rider = new Rider();
//                            rider.setRiderMobileNo(wrapperArrayObj.getJSONObject(0).optString("mobileNo"));
//                            rider.setRiderName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
//                            rider.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));
//
//                            txt_mobileNo.setText(rider.getRiderMobileNo());
//                            txt_name.setText(rider.getRiderName());
//
//                            dropDown_locale.setSelection(CommonService.populateLocale(rider.getLocale()));
//
//                            JSONArray imageWrappers = wrapperArrayObj.getJSONObject(0).getJSONArray("imageWrappers");
//
//                            if(imageWrappers!=null)
//                            {
//
//                                for(int i=0;i<imageWrappers.length();i++)
//                                {
//                                   if(imageWrappers.getJSONObject(i).optString("recordFound")=="true" &&
//                                           imageWrappers.getJSONObject(i).optString("imageID").equals(GlobalConstants.IMAGE_AVATAR))
//                                   {
//
//                                       rider.setImageName(imageWrappers.getJSONObject(i).optString("imageName"));
//                                       //Toast.makeText(RiderProfileActivity.this, rider.getImageName(), Toast.LENGTH_SHORT).show();
//
//                                       commonService.getImage(iv_avatar,rider.getImageName());
//                                       break;
//                                   }
//                                }
//
//                            }
//
//
//
//                        } else {
//
//                            Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                    } else {
//
//                        Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//
//
//                }
//
//
//                //}//validation
//
//            }//run end
//
//        });//runnable end
//
//
//    } //-------end of fetch profile


    private class updateProfileProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            showProgressBar();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //my stuff is here


            try {
                //button click event
                CommonService.hideKeyboardView(RiderProfileActivity.this, btn_save);


                CommonService commonService = new CommonService();
                commonService.updateRider(new Listener<Boolean>() {
                    @Override
                    public void on(Boolean arg) {


                    }
                }, RiderProfileActivity.this, getApplicationContext(), rider);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            hideProgressBar();

        }


    }


//    public void updateProfile()
//    {
//
//        RiderProfileActivity.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                try {
//                    //button click event
//                    showProgressBar();
//                    CommonService.hideKeyboardView(RiderProfileActivity.this, btn_save);
//
//
//                    CommonService commonService = new CommonService();
//                    commonService.updateRider(new Listener<Boolean>() {
//                        @Override
//                        public void on(Boolean arg) {
//
//
//                            hideProgressBar();
//
//                        }
//                    }, RiderProfileActivity.this, getApplicationContext(), rider);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    hideProgressBar();
//                }
//            }
//        });
//
//    }
//    public void updateProfile() {
//
//
//        RiderProfileActivity.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
//
//                try {
//
//
//                    //Shared Preferences
//                    editor = sharedPreferences.edit();
//
//                    editor.putString("userid", rider.getRiderID());
//                    editor.putString("deviceToken", "DEVICETOKEN");
//                    editor.putString("sessionid", "SESSIONID");
//
//                    editor.apply();
//
//
//                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);
//
//                    //loadingSpinner.setVisibility(View.VISIBLE);
//
//
//                    String methodAction = "updateRider";
//
//                    JSONObject messageJson = new JSONObject();
//                    //messageJson.put("mobileNo", mobileNo);
//                    messageJson.put("firstName", name);
//                    messageJson.put("locale", locale);
//                    messageJson.put("riderRefNo", rider.getRiderRefNo());
//                    messageJson.put("riderID", rider.getRiderID());
//
//
//
//                    ConnectHost connectHost = new ConnectHost();
//
//                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
//
//                    // loadingSpinner.setVisibility(View.GONE);
//
//                    Log.d(TAG, "RiderLogin responseData: " + responseData);
//
//
//                    if (responseData != null) {
//
//
//                        // Convert String to json object
//                        JSONObject jsonResponseData = new JSONObject(responseData);
//
//                        // get LL json object
//                        JSONObject jsonResult = jsonResponseData.getJSONObject("updateRider");
//
//                        //Log.d(TAG, "resData insertRider: " + jsonResult);
//                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));
//
//                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");
//
//                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));
//
//                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {
//
//
//                            rider = new Rider();
//
//                            rider.setRiderRefNo(wrapperArrayObj.getJSONObject(0).getString("riderRefNo"));
//                            rider.setRiderID(wrapperArrayObj.getJSONObject(0).getString("riderID"));
//                            //rider.setRiderMobileNo(wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
//                            rider.setRiderName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
//                            rider.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));
//
//                            setLoginDetails();
//
//                        } else {
//
//                            Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                    } else {
//
//                        Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                //}//validation
//
//            }//run end
//
//        });//runnable end
//
//
//    } //-------end of update profile
//
//
//
//    public void setLoginDetails() {
//
//
//        //Shared Preferences
//        editor = sharedPreferences.edit();
//
//        Log.d(TAG, "SharedPreferences putString ");
//
//        editor.putString("riderFirstName", rider.getRiderName());
//        editor.putString("locale", rider.getLocale());
//
//
//        editor.apply();
//
//        updateLocale(RiderProfileActivity.this, new Locale(rider.getLocale()));
//
//        Toast.makeText(RiderProfileActivity.this, R.string.update_success, Toast.LENGTH_SHORT).show();
//
//
//    }
//
//
//    @TargetApi(Build.VERSION_CODES.N)
//    private void updateLocale(Context context, Locale locale) {
//
//        Configuration configuration = context.getResources().getConfiguration();
//        configuration.setLocale(locale);
//
//        getBaseContext().getResources().updateConfiguration(configuration,
//                getBaseContext().getResources().getDisplayMetrics());
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(commonService==null) {
            commonService = new CommonService();
        }

        rider.setImageName(rider.getRiderID()+"_avatar.jpg");
        rider.setImageID(GlobalConstants.IMAGE_AVATAR);
        commonService.onActivityResult(RiderProfileActivity.this, getApplicationContext() , requestCode,resultCode,data, rider, iv_avatar, true);

    }

    private class VacantProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {

            showProgressBar();

        }

        @Override
        protected Void doInBackground(Object... arg0) {
            //my stuff is here


            updateVacantStatus((String) arg0[0]);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            hideProgressBar();

        }
    }

    //--------update Driver Vacant Status in the backend
    public void updateVacantStatus(final String vacantStatus) {




                try {


                    //Log.d(TAG, "driverLocation: " + driverLocation );


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    String methodAction = "updateVacantStatus";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("currentLat", rider.getRiderLat());
                    messageJson.put("currentLng", rider.getRiderLng());
                    messageJson.put("currentLocation", rider.getRiderLocation());
                    messageJson.put("vacantStatus", vacantStatus);
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    Log.d(TAG, "updateVacantStatus responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.getJSONObject("updateVacantStatus");

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

//                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//
//                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                            //String driverRefNo = wrapperArrayObj.getJSONObject(0).getString("driverRefNo");
                            //String driverID = wrapperArrayObj.getJSONObject(0).getString("driverID");
                            //wrapperArrayObj.getJSONObject(0).getString("mobileNo");


                            //Log.d(TAG, "Driver Info: " + driverRefNo + " " + driverID);




                        }
                        else
                        {

                            Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RiderProfileActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                }

                //}// validation



    }//updateVacantStatus End

    public  void showProgressBar() {

        loadingSpinner.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    public  void hideProgressBar()
    {

        loadingSpinner.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

}
