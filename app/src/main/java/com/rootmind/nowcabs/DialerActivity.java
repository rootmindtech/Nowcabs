package com.rootmind.nowcabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.wdullaer.materialdatetimepicker.*;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DialerActivity extends  AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    int i = -1;
    ProgressBar mProgressBar, mProgressBar1;

    private Button btn_dial, btn_close;
//    private EditText edtTimerValue;
    private TextView textViewShowTime, tv_appointDate, tv_appointTime;
    private ImageView iv_calendar;
    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;

    //Ringtone ringtone=null;
    Rider serviceGeo;
    Rider rider;
    Ride ride=null;

    MediaPlayer mediaPlayer=null;

    FirebaseFirestore firebaseFirestore;

    String responseData = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ListenerRegistration registration;
    String appointDate=null;
    String appointTime=null;
    Calendar now;
//    public LinearLayout loadingSpinner;



    private static final String TAG = "DialerActivity";

//    Button btn_accept;
//    Button btn_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        serviceGeo = (Rider) getIntent().getSerializableExtra("ServiceGeo");
        rider = (Rider) getIntent().getSerializableExtra("Rider");

        btn_dial = (Button) findViewById(R.id.btn_dial);
        btn_close = (Button) findViewById(R.id.btn_close);

        textViewShowTime = (TextView)
                findViewById(R.id.tv_time);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressBar1);


        if (firebaseFirestore == null)
        {
            firebaseFirestore = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
            firebaseFirestore.setFirestoreSettings(settings);
        }

//        edtTimerValue.setEnabled(false);
//        edtTimerValue.setText("20");


        mediaPlayer = CommonService.mediaPlayer(getApplicationContext());


        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        TextView tv_driverName = findViewById(R.id.tv_driverName);
        TextView tv_serviceName = findViewById(R.id.tv_servicerName);
        ImageView icon=(ImageView)findViewById(R.id.iv_driverImage);

        tv_appointDate = (TextView)findViewById(R.id.tv_appointDate);
        tv_appointTime = (TextView)findViewById(R.id.tv_appointTime);
        iv_calendar = (ImageView) findViewById(R.id.iv_calendar);


        //-----set avatar
        if(serviceGeo!=null) {

            //Log.d("InfoWindow", "InfoWindow " + rider.images[0].getImageName());
            tv_driverName.setText(serviceGeo.getRiderName());
            tv_serviceName.setText(serviceGeo.getServiceCode());
            ratingBar.setRating((float) serviceGeo.getAvgRating());
            icon.setImageResource(R.drawable.avatar);


            if(serviceGeo.imageWrappers!=null && serviceGeo.imageWrappers.length>0)
            {
                CommonService commonService = new CommonService();
                String imageFileName = CommonService.getImageName(serviceGeo, GlobalConstants.IMAGE_AVATAR);
                if(imageFileName!=null && !imageFileName.trim().equals("")) {
                    commonService.getImage (icon, imageFileName);
                }
                else
                {
                    icon.setImageResource(R.drawable.avatar);
                }

            }


        }


//        loadingSpinner = ((LinearLayout)findViewById(R.id.progressBarLayout));
//        hideProgressBar();


        btn_dial = (Button) findViewById(R.id.btn_dial);
        btn_close = (Button) findViewById(R.id.btn_close);


        btn_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               dial();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(countDownTimer!=null) {
                    countDownTimer.cancel();
                    countDownTimer.onFinish();
                }
                finishPlayer();

            }
        });



        now = Calendar.getInstance();

        iv_calendar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  showDatePicker();

              }
          }
        );


        tv_appointDate.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  showDatePicker();

              }
          }
        );

        tv_appointTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    showTimePicker();

            }
        });


//        Calendar cal = Calendar.getInstance();

//        findViewById(R.id.tv_appointDate)
//                .setOnClickListener(view -> {
//                            /*DatePickerDialog
//                                    .newInstance(null, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
//                                    .show(getFragmentManager(), null);*/
//                            DatePickerDialog dpd = new DatePickerDialog(DialerActivity.this, (view1, year, month, dayOfMonth) -> {
//                                //Toast.makeText(DialerActivity.this, String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth), Toast.LENGTH_SHORT).show();
//                                tv_appointDate.setText(String.format("%02d", dayOfMonth)+"/"+ String.format("%02d", month + 1) + "/" + String.format("%d", year));
//                            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//                            dpd.show();
//                        }
//                );
//
//        findViewById(R.id.tv_appointTime)
//                .setOnClickListener(view -> {
//                        /*TimePickerDialog
//                        .newInstance(null, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
//                        .show(getFragmentManager(), null);*/
//                            TimePickerDialog tpd = new TimePickerDialog(DialerActivity.this, (view1, hourOfDay, minute) -> {
//                                //Toast.makeText(DialerActivity.this, String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute), Toast.LENGTH_SHORT).show();
//                                tv_appointTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
//                            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(DialerActivity.this));
//                            tpd.show();
//                        }
//                );

    }



    private void dial()
    {

        if(tv_appointDate.getText().equals("") || tv_appointTime.getText().equals(""))
        {
            CommonService.Toast(DialerActivity.this,"Please select Appointment Date & Time",Toast.LENGTH_SHORT);
            return;

        }

        DocumentReference docRef = firebaseFirestore.collection("service").document(serviceGeo.getRiderID());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        Log.d(TAG, "DocumentSnapshot rideStatus: " + document.getData().get("rideStatus"));

                        if(document.getData().get("rideStatus")==null || !document.getData().get("rideStatus").equals(GlobalConstants.CALLING_STATUS))
                        {

                            startTimer();

                        }
                        else
                        {
                            CommonService.Toast(DialerActivity.this,"Service provider busy, please try again",Toast.LENGTH_SHORT);
                        }



                    } else {
                        Log.d(TAG, "No such document");

                        startTimer();

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void showDatePicker()
    {
        DatePickerDialog dpd =  DatePickerDialog.newInstance(
                DialerActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        // If you're calling this from an AppCompatActivity
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        dpd.setMinDate(now);

    }

    private void showTimePicker()
    {
        TimePickerDialog tpd =  TimePickerDialog.newInstance(DialerActivity.this,true);
        // If you're calling this from an AppCompatActivity
        tpd.show(getSupportFragmentManager(), "Timepickerdialog");
    }
    private void startTimer() {

        try {

                int time = 20;
                totalTimeCountInMilliseconds =  time * 1000;
                mProgressBar1.setMax( time * 1000);

                btn_dial.setEnabled(false);
                btn_close.setEnabled(false);
                iv_calendar.setClickable(false);
                tv_appointDate.setEnabled(false);
                tv_appointTime.setEnabled(false);
                mProgressBar.setVisibility(View.INVISIBLE);
                mProgressBar1.setVisibility(View.VISIBLE);


                new DialerActivity.insertRideProgressTask().execute();


                if(mediaPlayer==null)
                {
                    mediaPlayer = CommonService.mediaPlayer(getApplicationContext());

                }
                mediaPlayer.start();


                countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1) {
                    @Override
                    public void onTick(long leftTimeInMilliseconds) {
                        long seconds = leftTimeInMilliseconds / 1000;
                        mProgressBar1.setProgress((int) (leftTimeInMilliseconds));

                        textViewShowTime.setText(String.format("%02d", seconds / 60)
                                + ":" + String.format("%02d", seconds % 60));
                    }
                    @Override
                    public void onFinish() {

                        updateRide();
                        finishPlayer();
                    }
                }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void finishPlayer()
    {
        if(mediaPlayer!=null) {

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }

        if(registration!=null)
        {
            registration.remove();
        }

        textViewShowTime.setText("00:00");
        textViewShowTime.setVisibility(View.VISIBLE);
        btn_dial.setEnabled(true);
        btn_close.setEnabled(true);
        iv_calendar.setClickable(true);
        tv_appointDate.setEnabled(true);
        tv_appointTime.setEnabled(true);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar1.setVisibility(View.GONE);

        finish();



    }

    private class insertRideProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {
            //loadingSpinner.setVisibility(View.VISIBLE);
            //showProgressBar();
        }

        @Override
        protected Void doInBackground(Object...params ) {

            insertRide();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //loadingSpinner.setVisibility(View.GONE);
            //hideProgressBar();





        }
    }
    //--------insert Ride   in the backend
    public void insertRide() {




//        DialerActivity.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {

                try {


                    Log.d(TAG, "diler data: " + rider.getRiderLat() + ":" + rider.getRiderLng() + ":" + rider.getRiderLocation());


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    String methodAction = "insertRide";

                    JSONObject messageJson = new JSONObject();
//                    messageJson.put("service", service);
                    messageJson.put("pickupLat", rider.getRiderLat());
                    messageJson.put("pickupLng", rider.getRiderLng());
                    messageJson.put("rideStartPoint", rider.getRiderLocation());
                    messageJson.put("serviceCode", serviceGeo.getServiceCode());
                    messageJson.put("servicerRefNo", serviceGeo.getRiderRefNo());
                    messageJson.put("servicerID", serviceGeo.getRiderID());
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());
                    messageJson.put("appointDateTime", appointDate + " " + appointTime);


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    Log.d(TAG, "insert Ride responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.optJSONObject("insertRide");

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("rideWrapper");

//                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//
//                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true") {


                            if(wrapperArrayObj!=null) {

                                if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {

                                    ride = new Ride();

                                    ride.setRideRefNo(wrapperArrayObj.optJSONObject(0).getString("rideRefNo"));
                                    ride.setServicerRefNo(wrapperArrayObj.optJSONObject(0).getString("servicerRefNo"));
                                    ride.setServicerID(wrapperArrayObj.optJSONObject(0).getString("servicerID"));



//                                        driver.setDriverName(wrapperArrayObj.optJSONObject(i).getString("firstName"));
//                                        driver.setDriverMobileNo(wrapperArrayObj.optJSONObject(i).getString("mobileNo"));
//                                        driver.setDriverVehicleNo(wrapperArrayObj.optJSONObject(i).getString("vehicleNo"));
//                                        driver.setStatus(wrapperArrayObj.optJSONObject(i).getString("status"));
//                                        driver.setDriverVehicleType(wrapperArrayObj.optJSONObject(i).getString("vehicleType"));
//                                        driver.setDriverLat(wrapperArrayObj.optJSONObject(i).getDouble("currentLat"));
//                                        driver.setDriverLng(wrapperArrayObj.optJSONObject(i).getDouble("currentLng"));
//                                        driver.setDriverLocation(wrapperArrayObj.optJSONObject(i).getString("currentLocation"));
//                                        driver.setVacantStatus(wrapperArrayObj.optJSONObject(i).getString("vacantStatus"));
//                                        driver.setDistance(wrapperArrayObj.optJSONObject(i).getDouble("distance"));
//                                        driver.setDuration(wrapperArrayObj.optJSONObject(i).getDouble("duration"));
//                                        driver.setFavorite(wrapperArrayObj.optJSONObject(i).getString("favorite"));
//                                        driver.setAvgRating(wrapperArrayObj.optJSONObject(i).getDouble("avgRating"));
//                                        driver.setYourRating(wrapperArrayObj.optJSONObject(i).getDouble("yourRating"));
//

                                            // Create a new user with a first and last name
                                            Map<String, Object> serviceMap = new HashMap<>();
                                            serviceMap.put("servicerID", serviceGeo.getRiderID());
                                            serviceMap.put("riderID", rider.getRiderID());
                                            serviceMap.put("rideRefNo", ride.getRideRefNo());
                                            serviceMap.put("rideStatus", GlobalConstants.CALLING_STATUS);
                                            serviceMap.put("datetime", FieldValue.serverTimestamp());


                                            // Add a new document with a generated ID
                                            firebaseFirestore.collection("service")
                                                    .document(serviceGeo.getRiderID())
                                                    .set(serviceMap)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error writing document", e);
                                                        }
                                                    });



                                            //start listening once clicked
                                            firestoreListener();

                                }



                            }//null condition check

                        }
                        else
                        {

                            //Toast.makeText(DialerActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        //Toast.makeText(DialerActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(DialerActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    finishPlayer();

                }

                //}// validation

//            }//run end
//
//        });//runnable end
//

    }//insert ride



    public void firestoreListener()
    {

        try{

            Log.d(TAG, "Dialer Listener "+serviceGeo.getRiderID());

            //-------then listen for call
        final DocumentReference docRef = firebaseFirestore.collection("service").document(serviceGeo.getRiderID());
        registration =docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());

                    //CommonService.Toast(DialerActivity.this,snapshot.getData().toString(),Toast.LENGTH_SHORT);

                    Ride ride = new Ride();
                    ride.setRideRefNo(snapshot.getData().get("rideRefNo").toString());
                    ride.setRiderID(snapshot.getData().get("riderID").toString());
                    ride.setServicerID(snapshot.getData().get("servicerID").toString());
                    ride.setRideStatus(snapshot.getData().get("rideStatus").toString());


                    switch (ride.getRideStatus()) {


                        case GlobalConstants.ACCEPTED_STATUS: {

                            showResponse(GlobalConstants.ACCEPTED_STATUS,"Your request has been accepted" );
                            //CommonService.Toast(DialerActivity.this, "Your request has been accepted", Toast.LENGTH_SHORT);
                            break;
                        }
                        case GlobalConstants.REJECTED_STATUS: {

                            showResponse(GlobalConstants.REJECTED_STATUS, "Your request has been rejected");
                            //CommonService.Toast(DialerActivity.this, "Your request has been rejected", Toast.LENGTH_SHORT);
                            break;
                        }
                        case GlobalConstants.NORESPONSE_STATUS: {

                            showResponse(GlobalConstants.NORESPONSE_STATUS, "No response from service provider");
                            //CommonService.Toast(DialerActivity.this, "No response from service provider", Toast.LENGTH_SHORT);
                            break;
                        }

                    } //switch


                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

//    public void closeActivity()
//    {
//
//        try{
//
//
//            finish();
//
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }




    //--------update Ride   in the backend
    public void updateRide() {




        DialerActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                try {


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    String methodAction = "updateRide";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("rideStatus", GlobalConstants.NORESPONSE_STATUS);
                    messageJson.put("rideRefNo", ride.getRideRefNo());


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    Log.d(TAG, "update Ride responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.optJSONObject("updateRide");

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("rideWrapper");


                        if (jsonResponseData.getString("success") == "true") {

                            resetStatus();

                        }
                        else
                        {

                            //Toast.makeText(DialerActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        //Toast.makeText(DialerActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(DialerActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                }

                //}// validation

            }//run end

        });//runnable end


    }//update ride

    //in case no response from servicer reset to NORESPONSE status
    public void resetStatus()
    {

        try {

            DocumentReference docRef = firebaseFirestore.collection("service").document(serviceGeo.getRiderID());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                            Log.d(TAG, "DocumentSnapshot rideStatus: " + document.getData().get("rideStatus"));

                            if (document.getData().get("rideStatus") != null)
                            {

                                if(document.getData().get("riderID").equals(rider.getRiderID()) && document.getData().get("rideStatus").equals(GlobalConstants.CALLING_STATUS)) {


                                    // Create a new user with a first and last name
                                    Map<String, Object> serviceMap = new HashMap<>();
                                    serviceMap.put("servicerID", serviceGeo.getRiderID());
                                    serviceMap.put("riderID", rider.getRiderID());
                                    serviceMap.put("rideRefNo", ride.getRideRefNo());
                                    serviceMap.put("rideStatus", GlobalConstants.NORESPONSE_STATUS);
                                    serviceMap.put("datetime", FieldValue.serverTimestamp());


                                    // Add a new document with a generated ID
                                    firebaseFirestore.collection("service")
                                            .document(serviceGeo.getRiderID())
                                            .set(serviceMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
                                                }
                                            });


                                }

                            }


                        } else {
                            Log.d(TAG, "No such document");

                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void showResponse(final String dialingStatus, String message)
    {

        new AlertDialog.Builder(DialerActivity.this)
                .setTitle("Response")
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {


                        switch (dialingStatus) {

                            case GlobalConstants.ACCEPTED_STATUS: {

                                finishPlayer();

                                Intent i = new Intent(getApplicationContext(), RidesActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Rider", rider);
                                bundle.putSerializable("rideType", GlobalConstants.RIDER_TYPE);
                                i.putExtras(bundle);
                                startActivity(i);
                                break;
                            }
                            case GlobalConstants.REJECTED_STATUS: {

                                finishPlayer();
                                break;
                            }
                            case GlobalConstants.NORESPONSE_STATUS: {

                                finishPlayer();
                                break;
                            }

                        } //switch

                        dialogInterface.dismiss();




                    }
                })
                .create()
                .show();


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        appointDate = year + "-" + (monthOfYear+1)+ "-" + dayOfMonth;
        tv_appointDate.setText(date);
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String format = "%1$02d"; // two digits
        String time = String.format(format,hourOfDay)+":"+String.format(format,minute); //hourOfDay+"h"+minute+"m"+second;
        appointTime = hourOfDay+":"+minute + ":00";
        tv_appointTime.setText(time);
    }



//    public  void showProgressBar() {
//
//        loadingSpinner.setVisibility(View.VISIBLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//
//    }
//
//    public  void hideProgressBar()
//    {
//
//        loadingSpinner.setVisibility(View.GONE);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//
//    }

}
