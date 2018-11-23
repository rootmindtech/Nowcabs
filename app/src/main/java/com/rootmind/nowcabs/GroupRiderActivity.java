package com.rootmind.nowcabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupRiderActivity extends AppCompatActivity implements  GroupRiderAdapter.ItemClickListener {

    public Toolbar toolbar;
    private static final String TAG = "GroupRiderActivity";

    Rider rider;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<GroupRider> groupRiderList;
    String responseData = null;
    private RecyclerView recyclerView;
    private GroupRiderAdapter groupRiderAdapter;

    public LinearLayout loadingSpinner;

    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_rider);


        rider = (Rider) getIntent().getSerializableExtra("Rider");
        group = (Group) getIntent().getSerializableExtra("Group");


        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        String title=group.getGroupID();

        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_18px);


        Button btn_add  = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addRider();
            }
        });

        groupRiderList = new ArrayList<>();


        //------------------
        recyclerView = (RecyclerView) findViewById(R.id.group_rider_recycler_view);
        groupRiderAdapter = new GroupRiderAdapter(groupRiderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(groupRiderAdapter);
        groupRiderAdapter.setClickListener(this);
        //----------------

        loadingSpinner = ((LinearLayout) findViewById(R.id.progressBarLayout));

        hideProgressBar();


        new GroupRiderActivity.GroupProgressTask().execute();

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


            fetchGroupRider();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            hideProgressBar();

        }
    }

    public void fetchGroupRider()
    {

        GroupRiderActivity.this.runOnUiThread(new Runnable() {

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


                    String methodAction = "fetchGroupRider";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("groupRefNo", group.getGroupRefNo());


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "fetchGroupRider responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchGroupRider");
                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("groupRiderWrapper");
                        if (jsonResponseData.getString("success") == "true") {


                            if(wrapperArrayObj!=null) {

                                GroupRider groupRider=null;
                                Gson gson=new GsonBuilder().create();
                                groupRiderList.clear();
                                groupRiderAdapter.notifyDataSetChanged();

                                for (int i = 0; i <= wrapperArrayObj.length() - 1; i++) {
                                    if (wrapperArrayObj.optJSONObject(i).optString("recordFound") == "true") {

                                        groupRider = new GroupRider();

                                        groupRider.setGroupRefNo(wrapperArrayObj.optJSONObject(i).optString("groupRefNo"));
                                        groupRider.setGroupID(wrapperArrayObj.optJSONObject(i).optString("groupID"));
                                        groupRider.setRiderRefNo(wrapperArrayObj.optJSONObject(i).optString("riderRefNo"));
                                        groupRider.setRiderID(wrapperArrayObj.optJSONObject(i).optString("riderID"));
                                        groupRider.setLinkRiderRefNo(wrapperArrayObj.optJSONObject(i).optString("linkRiderRefNo"));
                                        groupRider.setLinkRiderID(wrapperArrayObj.optJSONObject(i).optString("linkRiderID"));
                                        groupRider.setPublicView(wrapperArrayObj.optJSONObject(i).optString("publicView"));
                                        groupRider.setStatus(wrapperArrayObj.optJSONObject(i).optString("status"));
                                        groupRider.setMakerDateTime(wrapperArrayObj.optJSONObject(i).optString("makerDateTime"));

                                        groupRider.setRider(gson.fromJson(wrapperArrayObj.optJSONObject(i).optJSONObject("riderWrapper").toString(), Rider.class));

                                        //Log.d(TAG, "in the loop getImage ID  " + groupRider.rider.getImageWrappers()[0].getImageName());

                                        groupRiderList.add(groupRider);
                                        groupRiderAdapter.notifyDataSetChanged();

                                    }
                                }



                            }//null condition check

                        } else {

                            CommonService.Toast(GroupRiderActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(GroupRiderActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    CommonService.Toast(GroupRiderActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

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
    public void onClickDelete(View view, int position) {


        GroupRider groupRider = groupRiderList.get(position);

        groupRider.setStatus(GlobalConstants.INACTIVE_CODE);

        new GroupRiderActivity.updatePublicProgressTask().execute(groupRider);

    }

    @Override
    public void onClickPublic(View view, int position) {


        GroupRider groupRider = groupRiderList.get(position);

        //switch between on and off
        if(groupRider.getPublicView().equals(GlobalConstants.YES_CODE))
        {
            groupRider.setPublicView(GlobalConstants.NO_CODE);
        }
        else
        {
            groupRider.setPublicView(GlobalConstants.YES_CODE);
        }

        new GroupRiderActivity.updatePublicProgressTask().execute(groupRider);

    }


    public void addRider()
    {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(40, 100, 40, 100);


        final int mobileLength = rider.getMobileNo().length();

        final EditText et_mobileNo = new EditText(this);
        et_mobileNo.setHint(rider.getMobileNo().replaceFirst(".{6}$","******"));
        et_mobileNo.setMaxLines(1);
        et_mobileNo.setFilters(new InputFilter[] { new InputFilter.LengthFilter(mobileLength) });
        et_mobileNo.setAllCaps(true);
        et_mobileNo.setGravity(Gravity.CENTER);
        et_mobileNo.setLayoutParams(layoutParams);
        linearLayout.addView(et_mobileNo);

        linearLayout.setGravity(Gravity.CENTER);
        Resources res = popDialog.getContext().getResources();
        popDialog.setTitle("Add mobile number");
        popDialog.setView(linearLayout);

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //tv_Rating.setText(String.valueOf(rating.getProgress()));

                        if(et_mobileNo.getText().toString().length()<=6) {
                            CommonService.Toast(GroupRiderActivity.this,"Please enter mobile number", Toast.LENGTH_SHORT);
                            return;
                        }

                        insertGroupRider(et_mobileNo.getText().toString());

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
    public void insertGroupRider(String mobileNo) {




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


            String methodAction = "insertGroupRider";

            JSONObject messageJson = new JSONObject();
//                    messageJson.put("service", service);
            messageJson.put("groupRefNo", group.getGroupRefNo());
            messageJson.put("groupID", group.getGroupID());
            messageJson.put("pubicView", GlobalConstants.YES_CODE);
            messageJson.put("riderRefNo", rider.getRiderRefNo());
            messageJson.put("riderID", rider.getRiderID());
            messageJson.put("mobileNo", mobileNo);



            ConnectHost connectHost = new ConnectHost();
            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            Log.d(TAG, "insert GroupRider responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.optJSONObject("insertGroupRider");

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("groupRiderWrapper");

                if (jsonResponseData.getString("success") == "true") {


                    if(wrapperArrayObj!=null) {

                        if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                            fetchGroupRider();


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


        private class updatePublicProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {
            //loadingSpinner.setVisibility(View.VISIBLE);
            //showProgressBar();
        }

        @Override
        protected Void doInBackground(Object...params ) {

            updateGroupRider((GroupRider)params[0]);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //loadingSpinner.setVisibility(View.GONE);
            //hideProgressBar();





        }
    }


    public void updateGroupRider(GroupRider groupRider) {




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


            String methodAction = "updateGroupRider";

            JSONObject messageJson = new JSONObject();
//                    messageJson.put("service", service);
            messageJson.put("groupRefNo", groupRider.getGroupRefNo());
            messageJson.put("groupID", groupRider.getGroupID());
            messageJson.put("publicView", groupRider.getPublicView());
            messageJson.put("linkRiderRefNo", groupRider.getLinkRiderRefNo());
            messageJson.put("linkRiderID", groupRider.getLinkRiderID());
            messageJson.put("riderID", rider.getRiderID());
            messageJson.put("status", groupRider.getStatus());



            ConnectHost connectHost = new ConnectHost();
            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            Log.d(TAG, "update GroupRider responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.optJSONObject("updateGroupRider");

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("groupRiderWrapper");

                if (jsonResponseData.getString("success") == "true") {


                    if(wrapperArrayObj!=null) {

                        if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                            fetchGroupRider();

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
}
