package com.rootmind.nowcabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import android.widget.ArrayAdapter;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import android.view.Gravity;

/**
 * Created by rootmindtechsoftprivatelimited on 01/08/17.
 */

public class RidesActivity extends AppCompatActivity implements OnItemClickListener{

    public Toolbar toolbar;
    public DrawerLayout drawer;
    private static final String TAG = "RidesActivity";


    Rider rider;
    Driver driver;
    RideDetails rideDetails;

    SharedPreferences sharedPreferences;
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference dbRef;
//
//    private ValueEventListener valueEventListener;

    ArrayList<RideDetails> rideDetailsArrayList;

    String userGroup;

    // Array of strings...
//    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
//            "WebOS","Ubuntu","Windows7","Max OS X"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);
        Context context = this; // or ActivityNotification.this

//        userGroup= getIntent().getStringExtra("UserGroup");

//        if(userGroup.equals(GlobalConstants.RIDER_CODE)) {

            rider = (Rider) getIntent().getSerializableExtra("Rider");

//        }
//        else {
//
//            driver = (Driver) getIntent().getSerializableExtra("Driver");
//        }

  //      Log.d(TAG, "userGroup "+ userGroup);

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //To initiate firebase
//        firebaseDatabase = FirebaseDatabase.getInstance();

        rideDetailsArrayList = new ArrayList<RideDetails>();


        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.your_rides);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        //getRidesHistory();


//        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                R.layout.activity_rides_listview, mobileArray);
//
//        ListView listView = (ListView) findViewById(R.id.rides_list);
//        listView.setAdapter(adapter);




    }

//    public void getRidesHistory(){
//
//
//        //final Vector<Object> vector=new Vector<Object>();
//
//
//            try{
//
//
//                if(userGroup.equals(GlobalConstants.RIDER_CODE)) {
//
//                    dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDES_PATH + "/" + GlobalConstants.RIDER_CODE + "/" + rider.riderID);
//
//                    Log.d(TAG, "getRidesHistory riderID "+ rider.riderID);
//
//                }
//                else {
//
//                    dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDES_PATH + "/" + GlobalConstants.DRIVER_CODE + "/" + driver.driverID);
//
//                    Log.d(TAG, "getRidesHistory driverID "+ driver.driverID);
//                }
//
//
//
//                valueEventListener = new ValueEventListener() {
//
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get Post object and use the values to update the UI
//                        //Post post = dataSnapshot.getValue(Post.class);
//                        // ...
//                        Log.d(TAG, "ridersHistory:" + dataSnapshot);
//
//                        Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                        Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//                        //ArrayList<RideDetails> rideDetailsArrayList = new ArrayList<RideDetails>();
//
//
//                        for (DataSnapshot dataSnapshotSub : dataSnapshot.getChildren()) {
//                            //Exercise exercise = postSnapshot.getValue(Exercise.class);
//                            //String temp = exercise.toString();
//                            //Log.d("exercise: ", temp + "\n"); // can log all
//                            //listExercises.add(exercise);
//
//                            Log.d(TAG, "dataSnapshotSub:" + dataSnapshotSub);
//
//                            rideDetails = dataSnapshotSub.getValue(RideDetails.class);
//                            //vector.addElement(rideDetails);
//                            rideDetailsArrayList.add(rideDetails);
//
//                            Log.d(TAG, "rideDetails: add to vector " + rideDetails.source+" "+rideDetails.destination);
//                        }
//
//
//                        RidesArrayAdapter adapter = new RidesArrayAdapter(RidesActivity.this, rideDetailsArrayList);
//                        // Attach the adapter to a ListView
//                        ListView listView = (ListView)findViewById(R.id.tv_rideslist);
//                        listView.setAdapter(adapter);
//
//                        listView.setOnItemClickListener(RidesActivity.this);
//
//                        //listView.setOnItemClickListener(new OnItemClickListenerListViewItem());
//                        if(userGroup.equals(GlobalConstants.RIDER_CODE)) {
//
//                            rider = dataSnapshot.getValue(Rider.class);
//
//                        }
//                        else{
//                            driver=dataSnapshot.getValue(Driver.class);
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                        // ...
//                    }
//                };
//                dbRef.addListenerForSingleValueEvent(valueEventListener);
//                dbRef.removeEventListener(valueEventListener);
//
//
//            }
//            catch (Exception e) {
//                Log.d(TAG, "In updateFirebase Exception");
//                e.printStackTrace();
//            }
//
//
//
//    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        RideDetails rideDetails=rideDetailsArrayList.get(position);


//        Toast toast = Toast.makeText(getApplicationContext(), "Item " + (position + 1) + ": "+rideDetails.datetime ,
//                Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//        toast.show();

        Intent i = new Intent(getApplicationContext(), RideDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("RideDetails", rideDetails);
        i.putExtras(bundle);
        startActivity(i);


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
