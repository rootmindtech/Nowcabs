package com.rootmind.nowcabs;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupActivity extends AppCompatActivity implements  GroupAdapter.ItemClickListener {

    public Toolbar toolbar;
    private static final String TAG = "GroupActivity";

    Rider rider;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<Group> groupList;
    String responseData = null;
    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;

    public LinearLayout loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        rider = (Rider) getIntent().getSerializableExtra("Rider");


        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        String title="My Groups";

        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setBackgroundResource(Color.TRANSPARENT);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button btn_add  = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                addGroup();
            }
        });

        groupList = new ArrayList<>();


        //------------------
        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);
        groupAdapter = new GroupAdapter(groupList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(groupAdapter);
        groupAdapter.setClickListener(this);
        //----------------

        loadingSpinner = ((LinearLayout) findViewById(R.id.progressBarLayout));

        hideProgressBar();


        new GroupActivity.GroupProgressTask().execute();

    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private class GroupProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            showProgressBar();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //my stuff is here


            fetchGroup();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            hideProgressBar();

        }
    }

    public void fetchGroup()
    {

        GroupActivity.this.runOnUiThread(new Runnable() {

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


                    String methodAction = "fetchGroup";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "fetchGroup responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchGroup");
                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("groupWrapper");
                        if (jsonResponseData.getString("success") == "true") {

                            Group group=null;

                            if(wrapperArrayObj!=null) {

                                groupList.clear();
                                groupAdapter.notifyDataSetChanged();

                                for (int i = 0; i <= wrapperArrayObj.length() - 1; i++) {
                                    if (wrapperArrayObj.optJSONObject(i).optString("recordFound") == "true") {

                                        group = new Group();

                                        group.setGroupRefNo(wrapperArrayObj.optJSONObject(i).optString("groupRefNo"));
                                        group.setGroupID(wrapperArrayObj.optJSONObject(i).optString("groupID"));
                                        group.setRiderRefNo(wrapperArrayObj.optJSONObject(i).optString("riderRefNo"));
                                        group.setRiderID(wrapperArrayObj.optJSONObject(i).optString("riderID"));
                                        group.setName(wrapperArrayObj.optJSONObject(i).optString("name"));
                                        group.setStatus(wrapperArrayObj.optJSONObject(i).optString("status"));

                                        groupList.add(group);
                                        groupAdapter.notifyDataSetChanged();

                                    }
                                }



                            }//null condition check

                        } else {

                            CommonService.Toast(GroupActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(GroupActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    CommonService.Toast(GroupActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

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
    public void onClickGroup(View view, int position) {


        Group group = groupList.get(position);

        Intent i = new Intent(getApplicationContext(), GroupRiderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Rider", rider);
        bundle.putSerializable("Group", group);
        i.putExtras(bundle);
        startActivity(i);

    }

    @Override
    public void onClickDelete(View view, int position) {


        Group group = groupList.get(position);

        group.setStatus(GlobalConstants.INACTIVE_CODE);

        new GroupActivity.updateGroupProgressTask().execute(group);

    }


    public void addGroup()
    {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(40, 100, 40, 100);

        final EditText et_groupID = new EditText(this);
        et_groupID.setHint("Group ID, max 10 chars");
        et_groupID.setMaxLines(1);
        et_groupID.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
        et_groupID.setAllCaps(true);
        et_groupID.setGravity(Gravity.CENTER);
        et_groupID.setLayoutParams(layoutParams);
        linearLayout.addView(et_groupID);

        final EditText et_name = new EditText(this);
        et_name.setHint("Name, max 20 chars");
        et_name.setMaxLines(1);
        et_name.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
        et_name.setAllCaps(false);
        et_name.setGravity(Gravity.CENTER);
        et_name.setLayoutParams(layoutParams);
        linearLayout.addView(et_name);

        linearLayout.setGravity(Gravity.CENTER);
        Resources res = popDialog.getContext().getResources();
        popDialog.setTitle("New Group");
        popDialog.setView(linearLayout);

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //tv_Rating.setText(String.valueOf(rating.getProgress()));

                        if(et_groupID.getText().toString().length()<=0 || et_name.getText().toString().length()<=0) {
                            CommonService.Toast(GroupActivity.this,"Please enter data", Toast.LENGTH_SHORT);
                            return;
                        }

                        insertGroup(et_groupID.getText().toString(), et_name.getText().toString());

                        dialog.dismiss();
                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }


//    private class insertGroupProgressTask extends AsyncTask<Object, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            //loadingSpinner.setVisibility(View.VISIBLE);
//            //showProgressBar();
//        }
//
//        @Override
//        protected Void doInBackground(Object...params ) {
//
//            insertGroup();
//
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            //loadingSpinner.setVisibility(View.GONE);
//            //hideProgressBar();
//
//
//
//
//
//        }
//    }
    //--------insert Ride   in the backend
    public void insertGroup(String groupID, String name) {




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


            String methodAction = "insertGroup";

            JSONObject messageJson = new JSONObject();
//                    messageJson.put("service", service);
            messageJson.put("groupID", groupID);
            messageJson.put("name", name);
            messageJson.put("riderRefNo", rider.getRiderRefNo());
            messageJson.put("riderID", rider.getRiderID());


            ConnectHost connectHost = new ConnectHost();
            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            Log.d(TAG, "insert Group responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.optJSONObject("insertGroup");

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("groupWrapper");

                if (jsonResponseData.getString("success") == "true") {


                    if(wrapperArrayObj!=null) {

                        //Group group=null;

                        if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                            fetchGroup();

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

        }

        //}// validation

//            }//run end
//
//        });//runnable end
//

    }//insert group


    private class updateGroupProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {
            //loadingSpinner.setVisibility(View.VISIBLE);
            //showProgressBar();
        }

        @Override
        protected Void doInBackground(Object...params ) {

            updateGroup((Group)params[0]);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //loadingSpinner.setVisibility(View.GONE);
            //hideProgressBar();





        }
    }

    public void updateGroup(Group group) {




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


            String methodAction = "updateGroup";

            JSONObject messageJson = new JSONObject();
//                    messageJson.put("service", service);
            messageJson.put("status", group.getStatus());
            messageJson.put("groupRefNo", group.getGroupRefNo());
            messageJson.put("riderID", rider.getRiderID());


            ConnectHost connectHost = new ConnectHost();
            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            Log.d(TAG, "update Group responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.optJSONObject("updateGroup");

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("groupWrapper");

                if (jsonResponseData.getString("success") == "true") {


                    if(wrapperArrayObj!=null) {


                        if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {

                            fetchGroup();


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

        }

        //}// validation

//            }//run end
//
//        });//runnable end
//

    }//update group
}
