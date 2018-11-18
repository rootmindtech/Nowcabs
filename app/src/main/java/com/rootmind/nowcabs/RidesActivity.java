package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Vector;

import java.util.ArrayList;

import android.widget.AdapterView;

import android.view.View;
import android.util.Property;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import android.view.Gravity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rootmindtechsoftprivatelimited on 01/08/17.
 */

public class RidesActivity extends AppCompatActivity implements  RidesAdapter.ItemClickListener {

    public Toolbar toolbar;
    //public DrawerLayout drawer;
    private static final String TAG = "RidesActivity";

    Rider rider;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<Ride> rideList;
    String responseData = null;
    private RecyclerView recyclerView;
    private RidesAdapter ridesAdapter;

    public LinearLayout loadingSpinner;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);
        Context context = this;

        rider = (Rider) getIntent().getSerializableExtra("Rider");

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        rideList = new ArrayList<>();


        //------------------
        recyclerView = (RecyclerView) findViewById(R.id.rides_recycler_view);
        ridesAdapter = new RidesAdapter(rideList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ridesAdapter);
        ridesAdapter.setClickListener(this);
        //----------------

        loadingSpinner = ((LinearLayout) findViewById(R.id.progressBarLayout));

        hideProgressBar();


        new RidesActivity.RideProgressTask().execute();


    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private class RideProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            showProgressBar();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //my stuff is here


            fetchRide(GlobalConstants.RIDER_TYPE);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            hideProgressBar();

        }
    }

    public void fetchRide(final String rideType)
    {

        RidesActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                try {

                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "fetchRide";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());
                    messageJson.put("rideType", rideType);


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "fetchRide responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchRide");
                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("rideWrapper");
                        if (jsonResponseData.getString("success") == "true") {

                            Ride ride=null;

                            if(wrapperArrayObj!=null) {

                                for (int i = 0; i <= wrapperArrayObj.length() - 1; i++) {
                                    if (wrapperArrayObj.optJSONObject(i).optString("recordFound") == "true") {

                                        ride = new Ride();

                                        ride.setRideRefNo(wrapperArrayObj.optJSONObject(i).optString("rideRefNo"));
                                        ride.setRiderRefNo(wrapperArrayObj.optJSONObject(i).optString("riderRefNo"));
                                        ride.setRiderID(wrapperArrayObj.optJSONObject(i).optString("riderID"));
                                        ride.setRiderName(wrapperArrayObj.optJSONObject(i).optString("riderName"));
                                        ride.setRiderMobileNo(wrapperArrayObj.optJSONObject(i).optString("riderMobileNo"));
                                        ride.setServicerRefNo(wrapperArrayObj.optJSONObject(i).optString("servicerRefNo"));
                                        ride.setServicerID(wrapperArrayObj.optJSONObject(i).optString("servicerID"));
                                        ride.setServicerName(wrapperArrayObj.optJSONObject(i).optString("servicerName"));
                                        ride.setServicerMobileNo(wrapperArrayObj.optJSONObject(i).optString("servicerMobileNo"));
                                        ride.setVehicleNo(wrapperArrayObj.optJSONObject(i).optString("vehicleNo"));
                                        ride.setVehicleType(wrapperArrayObj.optJSONObject(i).optString("vehicleType"));
                                        ride.setServiceCode(wrapperArrayObj.optJSONObject(i).optString("serviceCode"));
                                        ride.setRideStatus(wrapperArrayObj.optJSONObject(i).optString("rideStatus"));
                                        ride.setRideStartDate(wrapperArrayObj.optJSONObject(i).optString("rideStartDate"));

                                        rideList.add(ride);
                                        ridesAdapter.notifyDataSetChanged();

                                    }
                                }



                            }//null condition check

                        } else {

                            CommonService.Toast(RidesActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(RidesActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    CommonService.Toast(RidesActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                }

            } //run end


        });


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
}
