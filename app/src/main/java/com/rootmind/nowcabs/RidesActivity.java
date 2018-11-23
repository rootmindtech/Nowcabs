package com.rootmind.nowcabs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import android.widget.LinearLayout;

import java.util.List;

import java.util.ArrayList;

import android.view.View;
import android.widget.Toast;

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
    String rideType;

    public LinearLayout loadingSpinner;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);
        Context context = this;

        rider = (Rider) getIntent().getSerializableExtra("Rider");
        rideType = (String) getIntent().getSerializableExtra("rideType");


        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        String title="Request";
        if(rideType!=null) {
            if (rideType.equals(GlobalConstants.RIDER_TYPE))
            {
                title = "My Requests";
            }
            else if(rideType.equals(GlobalConstants.SERVICER_TYPE))
            {
                title = "My Services";
            }

        }

        loadingSpinner = ((LinearLayout) findViewById(R.id.progressBarLayout));

        hideProgressBar();


        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_18px);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.BLACK,Color.BLACK);
        tabLayout.setSelectedTabIndicatorColor(Color.BLACK);
        tabLayout.addTab(tabLayout.newTab().setText(GlobalConstants.ACCEPTED_STATUS));
        tabLayout.addTab(tabLayout.newTab().setText(GlobalConstants.REJECTED_STATUS));
        tabLayout.addTab(tabLayout.newTab().setText(GlobalConstants.NORESPONSE_STATUS_DISPLAY)); //for display
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                new RidesActivity.RideProgressTask().execute(tab.getText().toString());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();

        new RidesActivity.RideProgressTask().execute(GlobalConstants.ACCEPTED_STATUS);




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





    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.action_bar_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.accepted:
//                //count=(TextView)findViewById(R.id.textView);
//                //count.setText("Add is clicked");
//                return (true);
//            case R.id.rejected:
//                //count=(TextView)findViewById(R.id.textView);
//                //count.setText("Nothing is selected");
//                return (true);
//            case R.id.noresponse:
//                //Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
//                return (true);
//
//        }
//
//        return (super.onOptionsItemSelected(item));
//
//    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private class RideProgressTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {

            showProgressBar();

        }

        @Override
        protected Void doInBackground(String... arg0) {
            //my stuff is here


            //replace the status
            if(arg0[0].equals("NO RESPONSE")) {
                arg0[0] = GlobalConstants.NORESPONSE_STATUS;
            }

            fetchRide(arg0[0]);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            hideProgressBar();

        }
    }

    public void fetchRide(final String rideStatus)
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
                    if(rideType.equals(GlobalConstants.RIDER_TYPE)) {
                        messageJson.put("riderRefNo", rider.getRiderRefNo());
                        messageJson.put("riderID", rider.getRiderID());
                    }
                    else if(rideType.equals(GlobalConstants.SERVICER_TYPE))
                    {
                        messageJson.put("servicerRefNo", rider.getRiderRefNo());
                        messageJson.put("servicerID", rider.getRiderID());
                    }
                    messageJson.put("rideType", rideType);
                    messageJson.put("rideStatus", rideStatus);


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
                            rideList.clear();
                            ridesAdapter.notifyDataSetChanged();

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
                                        ride.setAppointDateTime(wrapperArrayObj.optJSONObject(i).optString("appointDateTime"));
                                        ride.setRideType(rideType);

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

    @Override
    public void onClickDialImage(View view, int position) {


        Ride ride = rideList.get(position);

        //this opens the ringer tone
        setDialImage(ride);


    }


    public void setDialImage(Ride ride)

    {

        try {


            Log.d(TAG, "Dial Button Click: ");

            //callIntent.setData(Uri.parse("tel:9030209883"));
            // update Log
            //NowcabsLog nowcabsLog = new NowcabsLog();


            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(RidesActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(RidesActivity.this, Manifest.permission.CALL_PHONE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + ride.getServicerMobileNo()));
                    startActivity(callIntent);

                    //nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getRiderMobileNo());

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(RidesActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + ride.getServicerMobileNo()));
                    startActivity(callIntent);

                    //nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getRiderMobileNo());

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ride.getServicerMobileNo()));
                startActivity(callIntent);

                //nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getRiderMobileNo());

            }


            Log.d(TAG, "Dial Button Click end: " + ride.getServicerMobileNo());


        } catch (Exception e) {
            Log.d(TAG, "setDialImage Exception");
            e.printStackTrace();
        }


    }//------end of Dial Image
}
