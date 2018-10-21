package com.rootmind.nowcabs;

import android.Manifest;
import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by rootmindtechsoftprivatelimited on 23/06/17.
 */

public class DriverLoginActivity extends AppCompatActivity {


    private static final String TAG = "DriverLoginActivity";

    public static final int MY_PERMISSIONS_REQUEST = 100;

    String responseData = null;

//    private ImageView image_logo;
    private ImageView rider_image;

    private Spinner dropDown_vehicleType;
    private EditText txt_mobileNo;
    private EditText txt_name;
    private EditText txt_vehicleNo;
    Button btn_login;
    TextView txt_link;
    private TextView tv_title;


//    //05-Oct-2018 --Firebase suppress
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference dbRef;

    private ValueEventListener valueEventListener;
    private ValueEventListener alertValueEventListener;


    private ProgressBar loadingSpinner;


    //Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Driver driver = null;

    public String mobileNo = null;
    public String name = null;
    public String vehicleNo = null;
    public String vehicleType = null;
    public String locale=null;

    public Boolean validationSuccess = false;

    Parameter parameter;
    private String userGroup = null;

    private String fcmToken=null;
    public Toolbar toolbar;


    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //location access permission
        //checkLocationPermission();

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//        //05-Oct-2018 --Firebase suppress
//        //To initiate firebase
//        firebaseDatabase = FirebaseDatabase.getInstance();

        parameter = (Parameter) getIntent().getSerializableExtra("Parameter");
        mobileNo = (String) getIntent().getSerializableExtra("mobileNo");
        userGroup = (String) getIntent().getSerializableExtra("userGroup");
        locale = (String) getIntent().getSerializableExtra("locale");


        fcmToken = sharedPreferences.getString("fcmToken","");


        //Log.d(TAG, "DriverLoginActivity:" );

        //to show components, if no sharedPreferences are stored
        setComponentView();


//        //05-Oct-2018
//        //check for userGroup
//        if (sharedPreferences.getString("userGroup", "").equals(GlobalConstants.DRIVER_CODE)) {
//
//            //check for sharedPreferences values
//            //if (sharedPreferences.getString("driverMobileNo", "") != null && sharedPreferences.getString("driverFirstName", "") != null)
//            if (sharedPreferences.getString("autoLogin", "").equals(GlobalConstants.YES_CODE)) {
//
//                setContentView(R.layout.activity_splash_screen);
//
//                mobileNo = sharedPreferences.getString("driverMobileNo", "");
//                name = sharedPreferences.getString("driverFirstName", "");
//                vehicleNo = sharedPreferences.getString("vehicleNo", "");
//                vehicleType = sharedPreferences.getString("vehicleType", "");
//
//                driverLogin(true);
//                //driverFBLogin(true);
//            } else {
//
//                //to show components
//                setComponentView();
//
//            } //end of if condition for view components
//
//        } else if (sharedPreferences.getString("userGroup", "").equals(GlobalConstants.RIDER_CODE)) {
//
//            Intent i = new Intent(getApplicationContext(), RiderLoginActivity.class);
//            startActivity(i);
//
//
//        } else {
//
//            //to show components, if no sharedPreferences are stored
//            setComponentView();
//
//        }


    }

    public void addListenerOnSpinnerItemSelection() {
        dropDown_vehicleType = (Spinner) findViewById(R.id.dropDown_vehicleType);
        dropDown_vehicleType.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }


    private class ProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            loadingSpinner.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //my stuff is here

            driverLogin(false);
            //driverFBLogin(false);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            loadingSpinner.setVisibility(View.GONE);

        }
    }

    public void driverLogin(boolean autoLogin) {



//                txt_mobileNo.addTextChangedListener(new TextWatcher() {
//                        public void afterTextChanged(Editable s) {
//                            Validation.isPhoneNumber(txt_mobileNo,true);
//                        }
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
//                    public void onTextChanged(CharSequence s, int start, int before, int count){}
//                });
//                // TextWatcher would let us check validation error on the fly
//                txt_name.addTextChangedListener(new TextWatcher() {
//                    public void afterTextChanged(Editable s) {
//                        Validation.hasText(txt_name);
//                    }
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
//                    public void onTextChanged(CharSequence s, int start, int before, int count){}
//                });
//
//                txt_vehicleNo.addTextChangedListener(new TextWatcher() {
//                    public void afterTextChanged(Editable s) {
//                        Validation.hasText(txt_vehicleNo);
//                    }
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
//                    public void onTextChanged(CharSequence s, int start, int before, int count){}
//                });


            if (!autoLogin) {

                if (!checkValidation()) {

                    return;
                }
            }

            DriverLoginActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    try {

//                        String mobileNo = txt_mobileNo.getText().toString().trim();
//                        String name = txt_name.getText().toString().trim();
//                        String vehicleNo = txt_vehicleNo.getText().toString().trim();
//                        String vehicleType = dropDown_vehicleType.getSelectedItem().toString().trim();

                        Log.d(TAG, "mobileNo: " + mobileNo + " " + name);


                        //Shared Preferences
                        editor = sharedPreferences.edit();

                        editor.putString("userid", "SYSTEM");
                        editor.putString("deviceToken", fcmToken);
                        editor.putString("sessionid", "SESSIONID");

                        editor.apply();


                        String methodAction = "insertDriver";

                        JSONObject messageJson = new JSONObject();
                        messageJson.put("mobileNo", mobileNo);
                        messageJson.put("firstName", name);
                        messageJson.put("vehicleNo", vehicleNo);
                        messageJson.put("vehicleType", vehicleType);
                        messageJson.put("service", GlobalConstants.TRANSPORT_BUSINESS);
                        messageJson.put("fcmToken", fcmToken);
                        messageJson.put("locale", locale);


                        ConnectHost connectHost = new ConnectHost();
                        responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                        Log.d(TAG, "insertDriver responseData: " + responseData);


                        if (responseData != null) {


                            // Convert String to json object
                            JSONObject jsonResponseData = new JSONObject(responseData);

                            // get LL json object
                            JSONObject jsonResult = jsonResponseData.getJSONObject("insertDriver");

//                            Log.d(TAG, "resData insertDriver: " + jsonResult);
//
//                            Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                            JSONArray wrapperArrayObj = jsonResult.getJSONArray("driverWrapper");

//                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//
//                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                            if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                                //String driverRefNo = wrapperArrayObj.getJSONObject(0).getString("driverRefNo");
                                //String driverID = wrapperArrayObj.getJSONObject(0).getString("driverID");
                                //wrapperArrayObj.getJSONObject(0).getString("mobileNo");


                                //Log.d(TAG, "Driver Info: " + driverRefNo + " " + driverID);

//                            SavePreferences savePreferences = new SavePreferences();
//                            savePreferences.savedPreferences("driverMobileNo", wrapperArrayObj.getJSONObject(0).getString("mobileNo"),
//                                    "driverFirstName", wrapperArrayObj.getJSONObject(0).getString("firstName"),
//                                    "userGroup", "DRIVER",
//                                    "id", wrapperArrayObj.getJSONObject(0).getString("driverID"));


//                                //Shared Preferences
//                                editor = sharedPreferences.edit();
//
//                                Log.d(TAG, "SharedPreferences putString ");
//
//                                editor.putString("driverMobileNo", wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
//                                editor.putString("driverFirstName", wrapperArrayObj.getJSONObject(0).getString("firstName"));
//                                editor.putString("userGroup", "DRIVER");
//                                editor.putString("id", wrapperArrayObj.getJSONObject(0).getString("driverID"));
//
//                                editor.putString("vehicleNo", wrapperArrayObj.getJSONObject(0).getString("vehicleNo"));
//                                editor.putString("vehicleType", wrapperArrayObj.getJSONObject(0).getString("vehicleType"));
//
//                                editor.commit();
//
//
//                                Log.d(TAG, "Saved Info: " + sharedPreferences.getString("driverMobileNo", "") + " " + sharedPreferences.getString("driverFirstName", ""));
//
//                                Log.d(TAG, "Saved Info:2 " + sharedPreferences.getString("userGroup", "") + " " + sharedPreferences.getString("id", ""));


                                driver = new Driver();

                                driver.setDriverRefNo(wrapperArrayObj.getJSONObject(0).getString("driverRefNo"));
                                driver.setDriverID(wrapperArrayObj.getJSONObject(0).getString("driverID"));
                                driver.setDriverMobileNo(wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
                                driver.setDriverName(wrapperArrayObj.getJSONObject(0).getString("firstName"));
                                driver.setDriverVehicleNo(wrapperArrayObj.getJSONObject(0).getString("vehicleNo"));
                                //driver.setDriverStatus(wrapperArrayObj.getJSONObject(0).getString("status"));
                                driver.setStatus(wrapperArrayObj.getJSONObject(0).getString("status"));
                                driver.setDriverVehicleType(wrapperArrayObj.getJSONObject(0).getString("vehicleType"));
                                driver.setFcmToken(fcmToken);
                                driver.setLocale(wrapperArrayObj.getJSONObject(0).getString("locale"));


                                setLoginDetails();

                                //Intent i = new Intent(DriverLoginActivity.this, DriverMapActivity.class);
                                //startActivity(i);

//                                Intent i = new Intent(getApplicationContext(), DriverMapActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("Driver", driver);
//                                i.putExtras(bundle);
//                                startActivity(i);


                            }
                            else
                            {

                                Toast.makeText(DriverLoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                            }

                            // get value from LL Json Object
                            // String str_value=jsonResult.getString("riderWrapper"); //<< get value here


                            //                    object = new JSONObject(jsonStr);
                            //                    JSONObject response = object.getJSONObject("response");
                            //                    JSONArray legislators = response.getJSONArray("legislators");
                            //                    JSONObject first = legislators.getJSONObject(0).getJSONObject("legislator");


                            //JSONObject jsonArraayObj=wrapperArrayObj.getJSONObject(0).getString("recordFound");

                        } else {

                            Toast.makeText(DriverLoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(DriverLoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                    }

                    //}// validation

                }//run end

            });//runnable end


        }//driverLogin End


//    //05-Oct-2018 --Firebase suppress
//        public void driverFBLogin ( boolean autoLogin){
//
//            try {
//
//
//                //if not auto login then do validation
//                if (!autoLogin) {
//
//
//                    //if (!checkValidation()) return;
//
//
//                    DriverLoginActivity.this.runOnUiThread(new Runnable() {
//                        public void run() {
//                            //Do your UI operations like dialog opening or Toast here
//
//
//                            boolean ret = false;
//
//                            txt_mobileNo.addTextChangedListener(new TextWatcher() {
//                                public void afterTextChanged(Editable s) {
//                                    Validation.isPhoneNumber(txt_mobileNo, true);
//                                }
//
//                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                }
//
//                                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                }
//                            });
//                            // TextWatcher would let us check validation error on the fly
//                            txt_name.addTextChangedListener(new TextWatcher() {
//                                public void afterTextChanged(Editable s) {
//                                    Validation.hasText(txt_name);
//                                }
//
//                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                }
//
//                                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                }
//                            });
//
//                            txt_vehicleNo.addTextChangedListener(new TextWatcher() {
//                                public void afterTextChanged(Editable s) {
//                                    Validation.hasText(txt_vehicleNo);
//                                }
//
//                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                }
//
//                                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                }
//                            });
//
////                            if (!Validation.isPhoneNumber(txt_mobileNo, true)) ret = false;
////                            if (!Validation.hasText(txt_name)) ret = false;
////                            if (!Validation.hasText(txt_vehicleNo)) ret = false;
//
//                            if (Validation.isPhoneNumber(txt_mobileNo, true) && Validation.hasText(txt_name) && Validation.hasText(txt_vehicleNo)) {
//
//                                mobileNo = txt_mobileNo.getText().toString().trim();
//                                name = txt_name.getText().toString().trim();
//                                vehicleNo = txt_vehicleNo.getText().toString().trim();
//                                vehicleType = dropDown_vehicleType.getSelectedItem().toString().trim();
//
//                                //updateFirebase();
//                                getParameters();
//
//                            }
//
//
//                        }
//                    });
//
//
//                    //if(!validationSuccess) return;
//
//
//                } else {
//                    //For AutoLogin
//                    //updateFirebase();
//                    getParameters();
//                }
//
//
//            } catch (Exception e) {
//                Log.d(TAG, "In driverFBLogin Exception");
//                e.printStackTrace();
//            }
//
//        } // driverFBLogin


//    //05-Oct-2018 --Firebase suppress
//    public void updateFirebase () {
//
//            try {
//
//                final String driverID = GlobalConstants.DRIVER_PREFIX + mobileNo;
//
//
//                Calendar c = Calendar.getInstance();
//                System.out.println("Current time => " + c.getTime());
//                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//                String formattedDate = df.format(c.getTime());
//
//                driver = new Driver();
//                driver.setDatetime(formattedDate);
//
//
//                dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH + "/" + driverID);
//
//
//                Log.d(TAG, "driverFBLogin");
//
//                valueEventListener = new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get Post object and use the values to update the UI
//                        //Post post = dataSnapshot.getValue(Post.class);
//                        // ...
//                        //Log.d(TAG, "driverFBLogin1:" + dataSnapshot);
//
//                        Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                        //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//                        if (dataSnapshot.getValue() == null) {
//
//                            Log.d(TAG, "Insert new Driver: " + driverID);
//
//                            String key = dbRef.child(driverID).push().getKey();
//
//
//                            driver.setDriverID(driverID);
//                            driver.setDriverMobileNo(mobileNo);
//                            driver.setDriverName(name);
//                            driver.setDriverVehicleNo(vehicleNo);
//                            driver.setDriverVehicleType(vehicleType);
//                            driver.setStatus(GlobalConstants.ACTIVE_CODE);
//                            driver.setDestination(GlobalConstants.ANY_CODE);
//                            driver.setVacantStatus(GlobalConstants.VACANT_CODE);
//                            driver.setImageFound(false);
//
//                            Map<String, Object> driverMap = driver.toMap();
//
//                            firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH + "/" + driverID).setValue(driverMap);
//
//
//                            //Create DriverAlert and keep it
//                            createDriverAlert();
//
//
//                            //update Log
//                            NowcabsLog nowcabsLog = new NowcabsLog();
//                            nowcabsLog.updateLog(GlobalConstants.DRIVER_CODE, driverID, "LOGIN");
//
//
//                        } else {
//
//                            Log.d(TAG, "Udate Driver3:");
//
//                            Map<String, Object> driverMap = new HashMap<>();
//                            driverMap.put("datetime", driver.getDatetime());
//
//                            firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH + "/" + driverID).updateChildren(driverMap);
//
//                            driver = dataSnapshot.getValue(Driver.class);
//
//                            //update Log
//                            NowcabsLog nowcabsLog = new NowcabsLog();
//                            nowcabsLog.updateLog(GlobalConstants.DRIVER_CODE, driverID, "UPDATE");
//
//
//                        }
//
//
//                        //--After get login details call setLoginDetails()
//                        setLoginDetails();
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                        // ...
//                    }
//
//
//                };
//                dbRef.addListenerForSingleValueEvent(valueEventListener);
//                dbRef.removeEventListener(valueEventListener);
//
//
//            } catch (Exception e) {
//                Log.d(TAG, "In updateFirebase Exception");
//                e.printStackTrace();
//            }
//
//
//        }

        public void setLoginDetails () {


            //Shared Preferences
            editor = sharedPreferences.edit();

            Log.d(TAG, "SharedPreferences putString ");

            editor.putString("driverMobileNo", driver.getDriverMobileNo());
            editor.putString("driverFirstName", driver.getDriverName());
            editor.putString("userGroup", GlobalConstants.DRIVER_CODE);
            editor.putString("driverID", driver.getDriverID());
            editor.putString("vehicleNo", driver.getDriverVehicleNo());
            editor.putString("vehicleType", driver.getDriverVehicleType());
            editor.putString("autoLogin", GlobalConstants.YES_CODE);
            //editor.putString("driverRadius", wrapperArrayObj.getJSONObject(0).getString("driverRadius"));
            editor.putString("fcmToken", fcmToken);
            editor.putString("locale",locale);

            Log.d(TAG, "fcmToken: " + fcmToken);

            editor.apply();


            Log.d(TAG, "Saved Info: " + sharedPreferences.getString("driverMobileNo", "") + " " + sharedPreferences.getString("driverFirstName", ""));

            Log.d(TAG, "Before Change Activity ");
            if (driver.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {

                parameter = new Parameter();

                Intent i = new Intent(getApplicationContext(), AvatarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Parameter", parameter);
                bundle.putSerializable("Driver", driver);
                i.putExtras(bundle);
                startActivity(i);

                Log.d(TAG, "Going to DriverMap ");
            } else {
                CommonService.Toast(DriverLoginActivity.this, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT);

            }
        }


//    //05-Oct-2018 --Firebase suppress
//    //This method is to create dummay record in the driveralert other during valuelistener onload message is coming, to avoid this
//        public void createDriverAlert () {
//
//            try {
//
//
//                Calendar c = Calendar.getInstance();
//                System.out.println("Current time => " + c.getTime());
//                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//                final String formattedDate = df.format(c.getTime());
//
//
//                dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_ALERT_PATH + "/" + driver.getDriverID());
//
//
//                Log.d(TAG, "createDriverAlert");
//
//                alertValueEventListener = new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get Post object and use the values to update the UI
//                        //Post post = dataSnapshot.getValue(Post.class);
//                        // ...
//                        //Log.d(TAG, "driverFBLogin1:" + dataSnapshot);
//
//                        Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                        //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//                        if (dataSnapshot.getValue() == null) {
//
//                            Log.d(TAG, "Insert new Driver: " + driver.getDriverID());
//
//                            String key = dbRef.child(driver.getDriverID()).push().getKey();
//
//
//                            Map<String, Object> driverAlertMap = new HashMap<>();
//                            driverAlertMap.put("riderSMS", driver.getDriverMobileNo()); //assign driver mobile number as temporary but this will be replaced with rider mobile number
//                            driverAlertMap.put("datetime", formattedDate);
//
//                            firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_ALERT_PATH + "/" + driver.getDriverID()).setValue(driverAlertMap);
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                        // ...
//                    }
//
//
//                };
//                dbRef.addListenerForSingleValueEvent(alertValueEventListener);
//                dbRef.removeEventListener(alertValueEventListener);
//
//
//            } catch (Exception e) {
//                Log.d(TAG, "In createDriverAlert Exception");
//                e.printStackTrace();
//            }
//
//
//        }


        private boolean checkValidation () {


            boolean ret = true;

//            //13-OCt-2018
//            txt_mobileNo.addTextChangedListener(new TextWatcher() {
//                public void afterTextChanged(Editable s) {
//                    Validation.isPhoneNumber(txt_mobileNo, true);
//                }
//
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                }
//
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                }
//            });
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

            txt_vehicleNo.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    Validation.hasText(txt_vehicleNo);
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

//            if (!Validation.isPhoneNumber(txt_mobileNo, true)) ret = false;

            if (!Validation.hasText(txt_name)) ret = false;
            if (!Validation.hasText(txt_vehicleNo)) ret = false;

            if (ret) {

                mobileNo = txt_mobileNo.getText().toString().trim();
                name = txt_name.getText().toString().trim();
                vehicleNo = txt_vehicleNo.getText().toString().trim();
                vehicleType = CommonService.selectVehicleType(dropDown_vehicleType.getSelectedItemPosition());



            }

            return ret;
        }


//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//
//    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 98;
//
//    public boolean checkLocationPermission(){
//
//
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            // Asking user if explanation is needed
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//                //Prompt the user once explanation has been shown
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//
//                new AlertDialog.Builder(this)
//                        .setTitle("Location Permission Needed")
//                        .setMessage("This app needs the Location permission, please accept to use location functionality")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(DriverLoginActivity.this,
//                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION );
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }


        public boolean onKeyDown ( int keyCode, KeyEvent event){

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                moveTaskToBack(true);
                return true;
            }

            return false;

        }


        //to show components on the screen
        @SuppressLint("ResourceType")
        public void setComponentView () {

            //if not auto login then show components
            setContentView(R.layout.activity_driver_login);

//            image_logo = (ImageView) findViewById(R.id.image_logo);

            //04-Oct-2018
//            rider_image = (ImageView) findViewById(R.id.rider_image);
//            txt_link = (TextView) findViewById(R.id.txt_link);
//            txt_link.setClickable(true);
//            txt_link.setText(Html.fromHtml("<a >Passenger</a>"));
//            txt_link.setMovementMethod(LinkMovementMethod.getInstance());


            //29-Sep-2018
            //navigation bar
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(Color.TRANSPARENT);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            dropDown_vehicleType = (Spinner) findViewById(R.id.dropDown_vehicleType);

            txt_mobileNo = (EditText) findViewById(R.id.txt_mobileNo);
            txt_name = (EditText) findViewById(R.id.txt_name);
            txt_vehicleNo = (EditText) findViewById(R.id.txt_vehicleNo);
            btn_login = (Button) findViewById(R.id.btn_login);


            txt_mobileNo.setEnabled(false);

            //Progressbar
            loadingSpinner = (ProgressBar) findViewById(R.id.progressBar);
            loadingSpinner.setVisibility(View.GONE);


            //addListenerOnButton();
            addListenerOnSpinnerItemSelection();


            //check for sharedPreferences values
            if (sharedPreferences.getString("driverMobileNo", "") != null) {
                txt_mobileNo.setText(sharedPreferences.getString("driverMobileNo", ""));
            }
            if (sharedPreferences.getString("driverFirstName", "") != null) {
                txt_name.setText(sharedPreferences.getString("driverFirstName", ""));
            }
            if (sharedPreferences.getString("vehicleNo", "") != null) {
                txt_vehicleNo.setText(sharedPreferences.getString("vehicleNo", ""));
            }
            if (sharedPreferences.getString("vehicleNo", "") != null) {

                dropDown_vehicleType.setSelection(CommonService.populateVehicleType(sharedPreferences.getString("vehicleType", "")));

//                int i = 0;
//                if (sharedPreferences.getString("vehicleType", "").equals(GlobalConstants.AUTO_CODE)) {
//                    i = 0; //AUTO
//                } else {
//                    i = 1; //CAB
//                }
//                dropDown_vehicleType.setSelection(i);
            }

            txt_mobileNo.setText(mobileNo);

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.d(TAG, "onClick:");

                    //driverLogin(false);

                    new ProgressTask().execute();
                    //driverFBLogin(false);

                }
            });

            txt_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        txt_name.clearFocus();
                        Log.d(TAG, "RiderLogin onFocusChange:");
                        CommonService.hideKeyboardView(DriverLoginActivity.this,txt_name);
                    }
                }
            });


            txt_vehicleNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        txt_vehicleNo.clearFocus();
                        Log.d(TAG, "RiderLogin onFocusChange:");
                        CommonService.hideKeyboardView(DriverLoginActivity.this,txt_vehicleNo);
                    }
                }
            });



            //Log.d(TAG, "DriverLoginActivity: 2" );

//            //04-Oct-2018
//            txt_link.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    Log.d(TAG, "Text click:");
//
//                    //13-Sep-2018
//                    //-----Before switching beteween Driver and Rider clear data, else userGroup and autoLogin will not match
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
////                editor.clear();
//                    editor.putString("userGroup", GlobalConstants.RIDER_CODE);
//                    editor.putString("autoLogin", GlobalConstants.NO_CODE);
//                    editor.apply();
//
//                    //------------
//
//                    Intent i = new Intent(DriverLoginActivity.this, RiderLoginActivity.class);
//                    startActivity(i);
//
//
//                }
//            });
//
//            rider_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    Log.d(TAG, "Text click:");
//
//                    //13-Sep-2018
//                    //-----Before switching beteween Driver and Rider clear data, else userGroup and autoLogin will not match
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
////                editor.clear();
//                    editor.putString("userGroup", GlobalConstants.RIDER_CODE);
//                    editor.putString("autoLogin", GlobalConstants.NO_CODE);
//                    editor.apply();
//
//
//                    //------------
//
//                    Intent i = new Intent(DriverLoginActivity.this, RiderLoginActivity.class);
//                    startActivity(i);
//
//
//                }
//            });


        } //end of setComponentView


        @Override
        protected void onStart () {
            super.onStart();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int hasCameraPermission = checkSelfPermission(android.Manifest.permission.CAMERA);
                int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCallPhonePermission = checkSelfPermission(Manifest.permission.CALL_PHONE);


                List<String> permissions = new ArrayList<String>();

                if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.CAMERA);

                }
                if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

                }

                if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.CALL_PHONE);

                }

                if (!permissions.isEmpty()) {
                    requestPermissions(permissions.toArray(new String[permissions.size()]), MY_PERMISSIONS_REQUEST);


                }
            }


        }


        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
        int[] grantResults){
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

//    //05-Oct-2018 --Firebase suppress
//    public void getParameters ()
//        {
//
//
//            dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_PARAM_PATH);
//
//            Log.d(TAG, "getParameters");
//
//            valueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                    try {
//
//                        parameter = dataSnapshot.getValue(Parameter.class);
//
//                        Log.d(TAG, "parameter radius " + parameter.getDriverRadius());
//
//                        dbRef.addListenerForSingleValueEvent(valueEventListener);
//                        dbRef.removeEventListener(valueEventListener);
//
//
//                        updateFirebase();
//
//                    } catch (Exception ex) {
//                        Log.d(TAG, "getParameters Exception");
//                        ex.printStackTrace();
//                    }
//
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Getting Post failed, log a message
//                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                    // ...
//                }
//            };
//            dbRef.addListenerForSingleValueEvent(valueEventListener);
//            dbRef.removeEventListener(valueEventListener);
//
//
//        }//-------end of getParameters
//
//        @Override
//        protected void onDestroy () {
//            super.onDestroy();
//
//            if (valueEventListener != null) {
//                dbRef.removeEventListener(valueEventListener);
//            }
//
//        }




//    //FCM -----to get token
//    Task<InstanceIdResult> task = FirebaseInstanceId.getInstance().getInstanceId()
//            .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//                @Override
//                public void onSuccess(InstanceIdResult authResult) {
//                    // Task completed successfully
//                    // ...
//                    fcmToken = authResult.getToken();
//
//                    Log.d(TAG, "fcm token " + fcmToken);
//
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    // Task failed with an exception
//                    // ...
//
//                    Log.d(TAG, "fcm token failed " );
//
//                }
//            });
//    //---------end of FCM token


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }



}
