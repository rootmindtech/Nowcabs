package com.rootmind.nowcabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

/**
 * Created by rootmindtechsoftprivatelimited on 01/08/17.
 */

public class RideDetailsActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public DrawerLayout drawer;
    private static final String TAG = "RideDetailsActivity";


    Rider rider;
    Driver driver;
    RideDetails rideDetails;

    SharedPreferences sharedPreferences;
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference dbRef;
//
//    private ValueEventListener valueEventListener;

    ImageView driverImage;

    TextView tv_driverName;
    TextView tv_driverMobileNo;
    TextView tv_driverID;

    TextView tv_riderName;
    TextView tv_riderMobileNo;
    TextView tv_riderID;

    TextView tv_source;
    TextView tv_destination;

    TextView tv_rideID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ridedetails);
        Context context = this; // or ActivityNotification.this


        rideDetails = (RideDetails) getIntent().getSerializableExtra("RideDetails");

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //To initiate firebase
       // firebaseDatabase = FirebaseDatabase.getInstance();


        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Ride Details");
        //toolbar.setTitle(rideDetails.rideID);
        //toolbar.setSubtitle(rideDetails.rideID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        driverImage = (ImageView) findViewById(R.id.iv_driverimage);
        tv_driverName = (TextView) findViewById(R.id.tv_drivername);
        tv_driverMobileNo= (TextView) findViewById(R.id.tv_drivermobileno);
        tv_driverID= (TextView) findViewById(R.id.tv_driverID);


        tv_riderName = (TextView) findViewById(R.id.tv_ridername);
        tv_riderMobileNo= (TextView) findViewById(R.id.tv_ridermobileno);
        tv_riderID= (TextView) findViewById(R.id.tv_riderID);


        tv_rideID= (TextView) findViewById(R.id.tv_rideID);
        tv_source = (TextView) findViewById(R.id.tv_source);
        tv_destination= (TextView) findViewById(R.id.tv_destination);


        getDriverDetails();

        getRiderDetails();



    }

    public void getDriverDetails(){


        //final Vector<Object> vector=new Vector<Object>();


//        try{
//
//
//            dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH + "/" + rideDetails.driverID);
//
//
//            Log.d(TAG, "get details driverID "+ rideDetails.driverID);
//
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
//                    //Log.d(TAG, "dataSnapshot driverID : " + dataSnapshot);
//
//                    Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                    //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//                    driver = dataSnapshot.getValue(Driver.class);
//
//
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
//        }
//        catch (Exception e) {
//            Log.d(TAG, "In updateFirebase Exception");
//            e.printStackTrace();
//        }



    }


    public void getRiderDetails(){


        //final Vector<Object> vector=new Vector<Object>();


//        try{
//
//
//            dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH + "/" + rideDetails.riderID);
//
//
//            Log.d(TAG, "get details driverID "+ rideDetails.driverID);
//
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
//                    //Log.d(TAG, "dataSnapshot RiderID : " + dataSnapshot);
//
//                    Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                    //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//                    rider = dataSnapshot.getValue(Rider.class);
//
//
//
//                    //--After get login details call setLoginDetails()
//                    setRideDetails();
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
//        }
//        catch (Exception e) {
//            Log.d(TAG, "In updateFirebase Exception");
//            e.printStackTrace();
//        }



    }


    public void setRideDetails(){





        if (driver.getImageFound() == true) {

            Log.d(TAG, "driverImage Found ");

            // Receiving side
            byte[] data = Base64.decode(driver.getDriverImage(), Base64.DEFAULT);

            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

            driverImage.setImageBitmap(bmp);


        } else {

            driverImage.setImageResource(R.drawable.avatar_black_24dp);

        }

        tv_driverName.setText(driver.driverName);
        tv_driverMobileNo.setText(driver.driverMobileNo);
        tv_driverID.setText(driver.driverID);

        tv_riderID.setText(rider.riderID);
        tv_riderMobileNo.setText(rider.riderMobileNo);
        tv_riderName.setText(rider.riderName);

        tv_rideID.setText(rideDetails.rideID);

        tv_source.setText(rideDetails.source);
        tv_destination.setText(rideDetails.destination);

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
