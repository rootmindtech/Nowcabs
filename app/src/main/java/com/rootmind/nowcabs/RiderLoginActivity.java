package com.rootmind.nowcabs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
//import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONArray;

import android.text.TextWatcher;
import android.text.Editable;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import android.graphics.Color;

import android.widget.ProgressBar;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.rootmind.nowcabs.DriverMapActivity.getCroppedBitmap;


//
//import android.os.Handler;
//import android.os.Looper;
//import android.support.v4.app.NotificationCompat.MessagingStyle.Message;

/**
 * Created by rootmindtechsoftprivatelimited on 22/06/17.
 */

public class RiderLoginActivity extends AppCompatActivity {

    private static final String TAG = "RiderLoginActivity";

    public static final int MY_PERMISSIONS_REQUEST = 100;


    String responseData = null;

//    private ImageView image_logo;

    //private ImageView driver_image;

    private EditText txt_name;
    private TextView txt_mobileNo;
    Button btn_login;

    private TextView tv_title;

    //TextView txt_link;

    SharedPreferences sharedPreferences;
    //Shared Preferences
    SharedPreferences.Editor editor;

    public ProgressBar loadingSpinner;

    //FirebaseStorage firebaseStorage;

//    //05-Oct-2018 --Firebase suppress
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference dbRef;

//    private ChildEventListener mChildEventListener;
//
//    private ValueEventListener valueEventListener;

    Rider rider = null;

    public String mobileNo = null;
    public String name = null;

    public Boolean validationSuccess = false;

    Parameter parameter;
    private String userGroup = null;
    String locale=null;

    String fcmToken;
    public Toolbar toolbar;

    //CommonService commonService=null;

    //private static RiderLoginActivity mInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //android.os.NetworkOnMainThreadException
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // mInstance = this;

//        commonService= new CommonService();


        //checkPermissions();

        //---Retrieve device token
        //registration_Id =  FirebaseInstanceId.getInstance().getInstanceId();

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);




//        //05-Oct-2018
//        //To initiate firebase
//        firebaseDatabase = FirebaseDatabase.getInstance();

        //parameter = (Parameter) getIntent().getSerializableExtra("Parameter");
        mobileNo = (String) getIntent().getSerializableExtra("mobileNo");
        userGroup = (String) getIntent().getSerializableExtra("userGroup");
        locale = (String) getIntent().getSerializableExtra("locale");


        fcmToken = sharedPreferences.getString("fcmToken","");

        //to show components, if no sharedPreferences are stored
        setComponentView();




//        //05-Oct-2018
//        //check for userGroup
//        if (sharedPreferences.getString("userGroup", "").equals(GlobalConstants.RIDER_CODE)) {
//
//            //check for sharedPreferences values
//            //if( sharedPreferences.getString("riderMobileNo","") !=null &&  sharedPreferences.getString("riderFirstName","") !=null)
//            if (sharedPreferences.getString("autoLogin", "").equals(GlobalConstants.YES_CODE)) {
//
//                setContentView(R.layout.activity_splash_screen);
//
//                mobileNo = sharedPreferences.getString("riderMobileNo", "");
//                name = sharedPreferences.getString("riderFirstName", "");
//                riderLogin(true);
//
//
//                //riderFBLogin(true);
//
//            } else {
//
//                //to show components
//                setComponentView();
//
//            } //end of if condition for view components
//
//
//        } else if (sharedPreferences.getString("userGroup", "").equals(GlobalConstants.DRIVER_CODE)) {
//
//
//            Intent i = new Intent(getApplicationContext(), DriverLoginActivity.class);
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


    private class ProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            loadingSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //my stuff is here

            riderLogin(false);

            //riderFBLogin(false);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            loadingSpinner.setVisibility(View.GONE);
        }
    }


    public void riderLogin(final boolean autoLogin) {


//                txt_mobileNo.addTextChangedListener(new TextWatcher() {
//                    public void afterTextChanged(Editable s) {
//                        Validation.isPhoneNumber(txt_mobileNo,true);
//                    }
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



//                    String mobileNo = txt_mobileNo.getText().toString().trim();
//                    String name = txt_name.getText().toString().trim();


        RiderLoginActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {



                try {



                    if (!autoLogin) {

                        if (!checkValidation()) {

                            return;
                        }
                    }



                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", "SYSTEM");
                    editor.putString("deviceToken", fcmToken);
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "insertRider";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("mobileNo", mobileNo);
                    messageJson.put("firstName", name);
                    messageJson.put("fcmToken", fcmToken);
                    messageJson.put("locale", locale);
                    messageJson.put("deviceInfo", CommonService.getDeviceInfo());



                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.getJSONObject("insertRider");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


//                            String riderRefNo = wrapperArrayObj.getJSONObject(0).getString("riderRefNo");
//                            String riderID = wrapperArrayObj.getJSONObject(0).getString("riderID");
//
//
//                            //Shared Preferences
//                            editor = sharedPreferences.edit();
//
//                            Log.d(TAG, "SharedPreferences putString ");
//
//                            editor.putString("riderMobileNo", wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
//                            editor.putString("riderFirstName", wrapperArrayObj.getJSONObject(0).getString("firstName"));
//                            editor.putString("userGroup", "RIDER");
//                            editor.putString("riderID", wrapperArrayObj.getJSONObject(0).getString("riderID"));
//
//                            //editor.putString("driverRadius", wrapperArrayObj.getJSONObject(0).getString("driverRadius"));
//
//                            editor.apply();
//
//
//                            Log.d(TAG, "Saved Info: " + sharedPreferences.getString("riderMobileNo", "") + " " + sharedPreferences.getString("riderFirstName", ""));
//
//                            //Log.d(TAG, "Saved Info:2 " + sharedPreferences.getString("userGroup","") + " " + sharedPreferences.getString("id",""));
//
//                            // Log.d(TAG, "Saved Info: " + savePreferences.loadPreferences("riderMobileNo") + " " + savePreferences.loadPreferences("riderFirstName"));


                            Calendar c = Calendar.getInstance();
                            System.out.println("Current time => " + c.getTime());
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                            final String formattedDate = df.format(c.getTime());

                            rider = new Rider();

                            rider.setRiderRefNo(wrapperArrayObj.getJSONObject(0).optString("riderRefNo"));
                            rider.setRiderID(wrapperArrayObj.getJSONObject(0).optString("riderID"));
                            rider.setMobileNo(wrapperArrayObj.getJSONObject(0).optString("mobileNo"));
                            rider.setRiderName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
                            rider.setStatus(wrapperArrayObj.getJSONObject(0).optString("status"));
                            //rider.setImageFound(false);
                            rider.setDatetime(formattedDate);
                            rider.setFcmToken(fcmToken);
                            rider.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));



                            setLoginDetails();


//                            Log.d(TAG, "Before Change Activity ");
//
//
//                            Intent i = new Intent(getApplicationContext(), RiderMapActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("Rider", rider);
//                            i.putExtras(bundle);
//                            startActivity(i);
//
//                            Log.d(TAG, "Going to RiderMap ");



                        }
                        else
                        {

                            Toast.makeText(RiderLoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }



                        //wrapperArrayObj.getJSONObject(0).getString("mobileNo");


                        //Log.d(TAG, "Rider Info: " + riderRefNo + " " + riderID);

                        //Log.d(TAG, "Rider Info:2 " + wrapperArrayObj.getJSONObject(0).getString("mobileNo") + " " + wrapperArrayObj.getJSONObject(0).getString("firstName"));

//                            SavePreferences savePreferences = new SavePreferences();
//                            savePreferences.savedPreferences("riderMobileNo", wrapperArrayObj.getJSONObject(0).getString("mobileNo"),
//                                    "riderFirstName", wrapperArrayObj.getJSONObject(0).getString("firstName"),
//                                    "userGroup", "RIDER",
//                                    "id", wrapperArrayObj.getJSONObject(0).getString("riderID"));


                        //                        sharedProperties.setRiderRefNo(result.riderWrapper[0].riderRefNo);
                        //                        sharedProperties.setRiderID(result.riderWrapper[0].riderID);
                        //                        sharedProperties.setRiderMobileNo(result.riderWrapper[0].mobileNo);
                        //                        sharedProperties.setRiderName(result.riderWrapper[0].firstName);


                        //                        rider.riderRefNo= wrapperArrayObj.getJSONObject(0).getString("riderRefNo");
                        //                        rider.riderID=wrapperArrayObj.getJSONObject(0).getString("riderRefID");
                        //                        rider.riderMobileNo=wrapperArrayObj.getJSONObject(0).getString("riderMobileNo");
                        //                        rider.riderName=wrapperArrayObj.getJSONObject(0).getString("riderName");


                        //To pass:
                        // intent.putExtra("Rider", rider);

                        //                        CustomListing currentListing = new CustomListing();
                        //                        Intent i = new Intent();
                        //                        Bundle b = new Bundle();
                        //                        b.putParcelable(CommonService.CUSTOM_LISTING, currentListing);
                        //                        i.putExtras(b);
                        //                        i.setClass(this, SearchDetailsActivity.class);
                        //                        startActivity(i);


                        // get value from LL Json Object
                        // String str_value=jsonResult.getString("riderWrapper"); //<< get value here


                        //                    object = new JSONObject(jsonStr);
                        //                    JSONObject response = object.getJSONObject("response");
                        //                    JSONArray legislators = response.getJSONArray("legislators");
                        //                    JSONObject first = legislators.getJSONObject(0).getJSONObject("legislator");


                        //JSONObject jsonArraayObj=wrapperArrayObj.getJSONObject(0).getString("recordFound");


                    } else {

                        Toast.makeText(RiderLoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RiderLoginActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                }


                //}//validation

            }//run end

        });//runnable end


    }


//    //05-Oct-2018
//    public void riderFBLogin(boolean autoLogin) {
//
//        try {
//
//            Log.d(TAG, "alert-1");
//
//
//            //if not auto login then do validation
//            if (!autoLogin) {
//                //if (!checkValidation()) return;
//
//
//                RiderLoginActivity.this.runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        Log.d(TAG, "alert-2");
//
//                        //Do your UI operations like dialog opening or Toast here
//
//                        boolean ret = true;
//
//
//                        txt_mobileNo.addTextChangedListener(new TextWatcher() {
//                            public void afterTextChanged(Editable s) {
//                                Validation.isPhoneNumber(txt_mobileNo, true);
//                            }
//
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                            }
//
//                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                            }
//                        });
//                        // TextWatcher would let us check validation error on the fly
//                        txt_name.addTextChangedListener(new TextWatcher() {
//                            public void afterTextChanged(Editable s) {
//                                Validation.hasText(txt_name);
//                            }
//
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                            }
//
//                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                            }
//                        });
//
//
//                        //if (!Validation.isPhoneNumber(txt_mobileNo, true)) ret = false;
//                        //if (!Validation.hasText(txt_name)) ret = false;
//
//                        if (Validation.isPhoneNumber(txt_mobileNo, true) && Validation.hasText(txt_name)) {
//                            mobileNo = txt_mobileNo.getText().toString().trim();
//                            name = txt_name.getText().toString().trim();
//
//                            //updateFirebase();
//                            getParameters();
//                        }
//
//                        //return ret;
//
//                        //validationSuccess=ret;
//
//                    }
//                });
//
//                //if(!validationSuccess) return;
//
//            } else {
//                //For AutoLogin
//                //updateFirebase();
//                getParameters();
//
//            }
//
//
//        } catch (Exception e) {
//            Log.d(TAG, "In riderFBLogin Exception");
//            e.printStackTrace();
//        }
//
//    } // riderFBLogin


//    //05-Oct-2018
//    public void updateFirebase() {
//        try {
//
//            final String riderID = GlobalConstants.RIDER_PREFIX + mobileNo;
//
//
//            Calendar c = Calendar.getInstance();
//            System.out.println("Current time => " + c.getTime());
//            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//            final String formattedDate = df.format(c.getTime());
//
//            rider = new Rider();
//            rider.setDatetime(formattedDate);
//
//
//            dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH + "/" + riderID);
//
//
//            Log.d(TAG, "riderFBLogin " + riderID);
//
//
//            valueEventListener = new ValueEventListener() {
//
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // Get Post object and use the values to update the UI
//                    //Post post = dataSnapshot.getValue(Post.class);
//                    // ...
//                    //Log.d(TAG, "riderFBLogin1:" + dataSnapshot);
//
//                    Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                    //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//                    if (dataSnapshot.getValue() == null) {
//
//                        Log.d(TAG, "Insert new rider: " + riderID);
//
//                        String key = dbRef.child(riderID).push().getKey();
//
//
//                        rider.setRiderID(riderID);
//                        rider.setRiderMobileNo(mobileNo);
//                        rider.setRiderName(name);
//                        rider.setStatus(GlobalConstants.ACTIVE_CODE);
//                        rider.setImageFound(false);
//                        rider.setDatetime(formattedDate);
//                        rider.setFcmToken(fcmToken);
//
//                        Map<String, Object> riderMap = rider.toMap();
//
//                        dbRef.setValue(riderMap);
//
//
//                        //update Log
//                        NowcabsLog nowcabsLog = new NowcabsLog();
//                        nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, riderID, "LOGIN");
//
//
//                    } else {
//
//                        Log.d(TAG, "Udate Rider3:");
//
//                        Map<String, Object> riderMap = new HashMap<>();
//                        riderMap.put("datetime", formattedDate);
//                        riderMap.put("registrationId", fcmToken);
//                        dbRef.updateChildren(riderMap);
//
//                        rider = dataSnapshot.getValue(Rider.class);
//
//
//                        //update Log
//                        NowcabsLog nowcabsLog = new NowcabsLog();
//                        nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, riderID, "UPDATE");
//
//
//                    }
//
//
//                    //--After get login details call setLoginDetails()
//                    setLoginDetails();
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
//        } catch (Exception e) {
//            Log.d(TAG, "In updateFirebase Exception");
//            e.printStackTrace();
//        }
//
//    }

    public void setLoginDetails() {


        //Shared Preferences
        editor = sharedPreferences.edit();

        Log.d(TAG, "SharedPreferences putString ");

//        editor.putString("riderMobileNo", rider.getRiderMobileNo());
        editor.putString("riderFirstName", rider.getRiderName());
        editor.putString("userGroup", GlobalConstants.RIDER_CODE);
        editor.putString("riderRefNo", rider.getRiderRefNo());
        editor.putString("riderID", rider.getRiderID());
        editor.putString("autoLogin", GlobalConstants.YES_CODE);
        editor.putString("fcmToken", fcmToken);
        editor.putString("locale",locale);

        Log.d(TAG, "fcmToken: " + fcmToken);


        //editor.putString("driverRadius", wrapperArrayObj.getJSONObject(0).getString("driverRadius"));

        editor.apply();


        Log.d(TAG, "Saved Info: " + sharedPreferences.getString("riderMobileNo", "") + " " + sharedPreferences.getString("riderFirstName", ""));


        Log.d(TAG, "Before Change Activity " + rider.getStatus());


        if (rider.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {

            //parameter = new Parameter();

            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            Bundle bundle = new Bundle();
            //bundle.putSerializable("Parameter", parameter);
            bundle.putSerializable("Rider", rider);
            bundle.putSerializable("RegisterFlag", true); //only for registration
            i.putExtras(bundle);
            startActivity(i);

            Log.d(TAG, "Going to RiderMap ");
        } else {
            Log.d(TAG, "Access Restricted");

            Toast.makeText(RiderLoginActivity.this, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT).show();

        }


    }

    private boolean checkValidation() {
        boolean ret = true;


//        //13-OCt-2018
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


//        if (!Validation.isPhoneNumber(txt_mobileNo, true)) ret = false;

        if (!Validation.hasText(txt_name)) ret = false;

        if (ret) {
            mobileNo = txt_mobileNo.getText().toString().trim();
            name = txt_name.getText().toString().trim();
        }

        return ret;
    }


//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE= 98;
//    public boolean checkPermissions(){
//
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
//                                ActivityCompat.requestPermissions(RiderLoginActivity.this,
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
//        }
//
//
//        //if CALL_PHONE
//        //CONTACT
//
//
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//            // Asking user if explanation is needed
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    android.Manifest.permission.CALL_PHONE)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//                //Prompt the user once explanation has been shown
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.CALL_PHONE},
//                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
//
//                new AlertDialog.Builder(this)
//                        .setTitle("Call Permission Needed")
//                        .setMessage("This app needs the Call permission, please accept to use Call functionality")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(RiderLoginActivity.this,
//                                        new String[]{Manifest.permission.CALL_PHONE},
//                                        MY_PERMISSIONS_REQUEST_CALL_PHONE );
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.CALL_PHONE},
//                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
//            }
//            return false;
//        }
//
//
//        return true;
//
//    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }

        return false;

    }


    //to show components on the screen
    @SuppressLint("ResourceType")
    public void setComponentView() {

        //if not auto login then show components

        setContentView(R.layout.activity_rider_login);


//        image_logo = (ImageView) findViewById(R.id.image_logo);


//        //05-Oct-2018
//        driver_image = (ImageView) findViewById(R.id.driver_image);
//        txt_link = (TextView) findViewById(R.id.txt_link);
//        txt_link.setText(Html.fromHtml("<a >Driver</a>"));
//        txt_link.setMovementMethod(LinkMovementMethod.getInstance());


        //29-Sep-2018
        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txt_mobileNo = (TextView) findViewById(R.id.txt_mobileNo);
        txt_name = (EditText) findViewById(R.id.txt_name);

        btn_login = (Button) findViewById(R.id.btn_login);

        txt_mobileNo.setEnabled(false);

        loadingSpinner = (ProgressBar) findViewById(R.id.progressBar);
        loadingSpinner.setVisibility(View.GONE);


        //check for sharedPreferences values
//        if (sharedPreferences.getString("riderMobileNo", "") != null) {
//
//            txt_mobileNo.setText(sharedPreferences.getString("riderMobileNo", ""));
//        }
        if (sharedPreferences.getString("riderFirstName", "") != null) {

            txt_name.setText(sharedPreferences.getString("riderFirstName", ""));
        }

        txt_mobileNo.setText(mobileNo);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d(TAG, "onClick:");


                //riderLogin();
                new ProgressTask().execute();
                //riderFBLogin(false);


            }
        });

        txt_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    txt_name.clearFocus();
                    Log.d(TAG, "RiderLogin onFocusChange:");
                    CommonService.hideKeyboardView(RiderLoginActivity.this,txt_name);
                }
            }
        });


//        //05-Oct-2018
//        txt_link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Log.d(TAG, "Are you driver click:");
//
//                //13-Sep-2018
//                //-----Before switching beteween Driver and Rider clear data, else userGroup and autoLogin will not match
//                editor = sharedPreferences.edit();
////                editor.clear();
//                editor.putString("userGroup", GlobalConstants.DRIVER_CODE);
//                editor.putString("autoLogin", GlobalConstants.NO_CODE);
//                editor.apply();
//
//
//                //------------
//
//                Intent i = new Intent(RiderLoginActivity.this, DriverLoginActivity.class);
//                startActivity(i);
//
//
//            }
//        });
//
//        driver_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Log.d(TAG, "Are you driver click:");
//
//                //13-Sep-2018
//                //-----Before switching beteween Driver and Rider clear data, else userGroup and autoLogin will not match
//                editor = sharedPreferences.edit();
////                editor.clear();
//                editor.putString("userGroup", GlobalConstants.DRIVER_CODE);
//                editor.putString("autoLogin", GlobalConstants.NO_CODE);
//                editor.commit();
//
//
//                //------------
//
//                Intent i = new Intent(RiderLoginActivity.this, DriverLoginActivity.class);
//                startActivity(i);
//
//
//            }
//        });


    } //end of setComponentView


//    public void updateLog(String DR_RD_Code, String ID, String activity)
//    {
//
//        dbRef.df
//                insert "/cabs/logs/"+DR_RD_Code+ID
//
//            /cabs/logs/DRIVER/DR98989898/ elements (json --> datetime, activity)
//            /cabs/logs/RIDER/RD9393939393/ element (json --> datetime,activity)
//
//    }


    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
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


//    //05-Oct-2018
//    public void getParameters() {
//
//
//        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_PARAM_PATH);
//
//        Log.d(TAG, "getParameters");
//
//        valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                try {
//
//                    parameter = dataSnapshot.getValue(Parameter.class);
//
//                    Log.d(TAG, "parameter radius " + parameter.getDriverRadius());
//
//                    updateFirebase();
//
//                } catch (Exception ex) {
//                    Log.d(TAG, "getParameters Exception");
//                    ex.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//        dbRef.addListenerForSingleValueEvent(valueEventListener);
//        dbRef.removeEventListener(valueEventListener);
//
//
//    }//-------end of getParameters


//    //05-Oct-2018
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (valueEventListener != null) {
//            dbRef.removeEventListener(valueEventListener);
//        }
//        if (mChildEventListener != null) {
//            dbRef.removeEventListener(mChildEventListener);
//        }
//
//    }


//    //FCM -----to get token
//    Task<InstanceIdResult> task = FirebaseInstanceId.getInstance().getInstanceId()
//            .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//        @Override
//        public void onSuccess(InstanceIdResult authResult) {
//            // Task completed successfully
//            // ...
//            fcmToken = authResult.getToken();
//
//            Log.d(TAG, "fcm token " + fcmToken);
//
//
//
//        }
//    }).addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception e) {
//            // Task failed with an exception
//            // ...
//
//            Log.d(TAG, "fcm token failed " );
//
//        }
//    });
//    //---------end of FCM token


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }






}
