package com.rootmind.nowcabs;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rootmindtechsoftprivatelimited on 06/07/17.
 */

public class NowcabsLog {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    private ValueEventListener valueEventListener;

    private static final String TAG = "NowcabsLog";

    public void updateLog(String DR_RD_Code, String ID, final String activity){

//        //To initiate firebase
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        Calendar c = Calendar.getInstance();
//        System.out.println("Current time => "+c.getTime());
//        //SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//        SimpleDateFormat ddmmmyy = new SimpleDateFormat("dd-MMM-yyyy HH:mm:SS");
//        //final String formattedDate = df.format(c.getTime());
//        final String formattedDateTime = ddmmmyy.format(c.getTime());
//
//        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_LOG_PATH + DR_RD_Code+"/"+ID); //"cabs/log/"
//
//
//        //System.out.println("DR_RD_Code "+DR_RD_Code);
//
//        Log.d(TAG, "DR_RD_Code:" + DR_RD_Code +" "+ ID);
//
//
//        final String id=ID;
//
//
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
//               // if(dataSnapshot.getValue()==null){
//
//                   // Log.d(TAG, "Insert new rider: " +riderID);
//                    String logID=id+"_"+formattedDateTime;
//
//                    String key = dbRef.child(logID).push().getKey();
//
//
//                    Map<String, Object> logMap = new HashMap<>();
//
//                    logMap.put("id",id);
//                    logMap.put("activity",activity);
//                    logMap.put("datetime",formattedDateTime);
//
//                   // Map<String, Object> riderMap = logMap.toMap();
//
//                    dbRef.child(logID).setValue(logMap);
//
//
//                //}
////                else
////                {
////
////                    Log.d(TAG, "Udate Rider3:" );
////
////                    Map<String, Object> riderMap = new HashMap<>();
////                    riderMap.put("datetime",formattedDate);
////                    dbRef.updateChildren(riderMap);
////
////                    rider = dataSnapshot.getValue(Rider.class);
////
////
////                }
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
//        dbRef.addListenerForSingleValueEvent(valueEventListener);




    }
}
