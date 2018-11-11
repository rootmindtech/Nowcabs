package com.rootmind.nowcabs;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rootmindtechsoftprivatelimited on 31/07/17.
 */

public class RidesHisory {

    private static final String TAG = "RidesHisory";

//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference dbRefRider;
//    DatabaseReference dbRefDriver;
//    private ValueEventListener valueEventListener;

    Rider rider;



    public void saveRideHistory(final String paramRiderID, final String paramDriverID, final String destination)
    {
//        try{
//
//            //To initiate firebase
//            firebaseDatabase = FirebaseDatabase.getInstance();
//
//            //final String riderID = paramRiderID;
//
//
//            dbRefRider = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH + "/" + paramRiderID);
//
//
//            Log.d(TAG, "getRiderLocation riderID "+ paramRiderID);
//
//
//            valueEventListener = new ValueEventListener() {
//
//
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // Get Post object and use the values to update the UI
//                    //Post post = dataSnapshot.getValue(Post.class);
//                    // ...
//                    Log.d(TAG, "rider dataSnapshot:" + dataSnapshot);
//
//                    Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                    Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//                    rider = dataSnapshot.getValue(Rider.class);
//
//                    //--to get source location from rider rider.getRiderLocation()
//
//                    saveRide(paramRiderID,paramDriverID,rider.getRiderLocation(),destination);
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
//            dbRefRider.addListenerForSingleValueEvent(valueEventListener);
//            dbRefRider.removeEventListener(valueEventListener);
//
//
//        }
//        catch (Exception e) {
//            Log.d(TAG, "In updateFirebase Exception");
//            e.printStackTrace();
//        }



    }


    public void saveRide(String paramRiderID, String paramDriverID,final String source, final String destination){



        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        //SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        SimpleDateFormat ddmmmyy = new SimpleDateFormat("dd-MMM-yyyy HH:mm:SS");
        //final String formattedDate = df.format(c.getTime());
        final String formattedDateTime = ddmmmyy.format(c.getTime());


        //final String source= rider.getRiderLocation();

//        //for rider
//        dbRefRider = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDES_PATH + GlobalConstants.RIDER_CODE+"/"+paramRiderID); //"cabs/log/"
//
//        //for driver
//        dbRefDriver = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDES_PATH + GlobalConstants.DRIVER_CODE+"/"+paramDriverID);

        //System.out.println("DR_RD_Code "+DR_RD_Code);

        Log.d(TAG, "RD_DR_Code:" + paramRiderID +" "+ paramDriverID);


        final String riderID=paramRiderID;
        final String driverID=paramDriverID;




//        valueEventListener = new ValueEventListener() {
//
//
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                //Post post = dataSnapshot.getValue(Post.class);
//                // ...
////                Log.d(TAG, "riderFBLogin1:" + dataSnapshot);
////                Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
////                Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//                // if(dataSnapshot.getValue()==null){
//
//                // Log.d(TAG, "Insert new rider: " +riderID);
//                String rideID="RIDE"+"_"+formattedDateTime;
//
//                //String key = dbRef.child(logID).push().getKey();
//
//                //for rider
//                Map<String, Object> riderMap = new HashMap<>();
//
//                riderMap.put("rideID",rideID);
//                riderMap.put("riderID",riderID);
//                riderMap.put("driverID",driverID);
//                riderMap.put("source",source);
//                riderMap.put("destination",destination);
//                riderMap.put("datetime",formattedDateTime);
//
//                dbRefRider.child(rideID).setValue(riderMap);
//
//
//                //for driver
//
//                Map<String, Object> driverMap = new HashMap<>();
//
//                driverMap.put("rideID",rideID);
//                driverMap.put("riderID",riderID);
//                driverMap.put("driverID",driverID);
//                driverMap.put("source",source);
//                driverMap.put("destination",destination);
//                driverMap.put("datetime",formattedDateTime);
//
//                dbRefDriver.child(rideID).setValue(driverMap);
//
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
//        dbRefRider.addListenerForSingleValueEvent(valueEventListener);
//
//        dbRefDriver.addListenerForSingleValueEvent(valueEventListener);
//
   }


}
