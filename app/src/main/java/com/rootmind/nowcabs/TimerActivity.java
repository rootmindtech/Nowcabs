package com.rootmind.nowcabs;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TimerActivity extends AppCompatActivity  {

    private static final String TAG = "TimerActivity";

    int i = -1;
    ProgressBar mProgressBar, mProgressBar1;

    private TextView textViewShowTime;
    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;

    //Ringtone ringtone=null;
    MediaPlayer mediaPlayer=null;

    Button btn_accept;
    Button btn_reject;
    FirebaseFirestore firebaseFirestore;

    //Rider serviceGeo;
    Rider rider;
    Ride ride=null;
    ListenerRegistration registration;

    String responseData = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    String rideStatus=GlobalConstants.NORESPONSE_STATUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        textViewShowTime = (TextView)
                findViewById(R.id.tv_time);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        ride = (Ride) getIntent().getSerializableExtra("Ride");
        rider = (Rider) getIntent().getSerializableExtra("Rider");


        firebaseFirestore = FirebaseFirestore.getInstance();

        mediaPlayer = CommonService.mediaPlayer(getApplicationContext());


        btn_accept = (Button) findViewById(R.id.btn_accept);
        btn_reject = (Button) findViewById(R.id.btn_reject);


        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptRequest();
            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rejectRequest();
            }
        });


        startRinger();


    }



    private void startRinger()
    {


        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressBar1.setVisibility(View.VISIBLE);
        setTimer();
        startTimer();


    }

    private void setTimer(){
        int time = 20;
        totalTimeCountInMilliseconds =  time * 1000;
        mProgressBar1.setMax( time * 1000);
    }
    private void startTimer() {

        try {

            mediaPlayer.start();
            vibrate();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

                finishPlayer();
            }
        }.start();
    }

    private void vibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(500,10));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(500);
        }
    }

    public void finishPlayer()
    {

        //update backend
        new TimerActivity.updateRideProgressTask().execute();

        Map<String, Object> serviceMap = new HashMap<>();
        serviceMap.put("rideStatus", rideStatus);
        serviceMap.put("datetime", FieldValue.serverTimestamp());


        // Add a new document with a generated ID
        firebaseFirestore.collection("service")
                .document(ride.getServicerID())
                .update(serviceMap)
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

        if(mediaPlayer!=null) {

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        textViewShowTime.setText("00:00");
        textViewShowTime.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar1.setVisibility(View.GONE);

        finish();

    }


    public void acceptRequest(){


        rideStatus = GlobalConstants.ACCEPTED_STATUS;

        finishPlayer();

    }

    public void rejectRequest(){

        rideStatus = GlobalConstants.REJECTED_STATUS;

        finishPlayer();

    }

    private class updateRideProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Object...params ) {

            updateRide();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }
    //--------insert Ride   in the backend
    public void updateRide() {




        TimerActivity.this.runOnUiThread(new Runnable() {

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
            messageJson.put("rideStatus", rideStatus);
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

//                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//
//                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                if (jsonResponseData.getString("success") == "true") {


//                    if(wrapperArrayObj!=null) {
//
//                        if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {
//
//
//
//                        }
//
//
//
//                    }//null condition check

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


    }//insert ride

//    public void firestoreListener()
//    {
//
//        try {
//
//
//            //-------then listen for call
//            final DocumentReference docRef = firebaseFirestore.collection("service").document(rider.getRiderID());
//            registration = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                    @Nullable FirebaseFirestoreException e) {
//                    if (e != null) {
//                        Log.w(TAG, "Listen failed.", e);
//                        return;
//                    }
//
//                    if (snapshot != null && snapshot.exists()) {
//                        Log.d(TAG, "Current data: " + snapshot.getData());
//
//                        //CommonService.Toast(RiderMapActivity.this,snapshot.getData().toString(),Toast.LENGTH_SHORT);
//
//                        Ride ride = new Ride();
//                        ride.setRideRefNo(snapshot.getData().get("rideRefNo").toString());
//                        ride.setRiderID(snapshot.getData().get("riderID").toString());
//                        ride.setDriverID(snapshot.getData().get("serviceID").toString());
//                        ride.setRideStatus(snapshot.getData().get("rideStatus").toString());
//
//                        //if calling status then call the service provider
//                        if(ride.getRideStatus().equals(GlobalConstants.CALLING_STATUS)) {
//                            callTimer(ride);
//                        }
//
//                    } else {
//                        Log.d(TAG, "Current data: null");
//                    }
//                }
//            });
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//    }

}
