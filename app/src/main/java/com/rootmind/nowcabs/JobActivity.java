package com.rootmind.nowcabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class JobActivity extends AppCompatActivity  implements  JobAdapter.ItemClickListener {

    public Toolbar toolbar;
    private static final String TAG = "JobActivity";

    Rider rider;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<Job> jobList;
    String responseData = null;
    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;

    public LinearLayout loadingSpinner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);


        rider = (Rider) getIntent().getSerializableExtra("Rider");


        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        String title="Rate Sheet";

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

                addJob(GlobalConstants.ADD_MODE,null);
            }
        });

        jobList = new ArrayList<>();


        //------------------
        recyclerView = (RecyclerView) findViewById(R.id.job_recycler_view);
        jobAdapter = new JobAdapter(jobList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(jobAdapter);
        jobAdapter.setClickListener(this);
        //----------------

        loadingSpinner = ((LinearLayout) findViewById(R.id.progressBarLayout));

        hideProgressBar();


        new JobActivity.JobProgressTask().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    private class JobProgressTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            showProgressBar();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //my stuff is here


            fetchJob();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            hideProgressBar();

        }
    }

    public void fetchJob()
    {

        JobActivity.this.runOnUiThread(new Runnable() {

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


                    String methodAction = "fetchJob";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "fetchJob responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchJob");
                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("jobWrapper");
                        if (jsonResponseData.getString("success") == "true") {

                            Job job=null;

                            if(wrapperArrayObj!=null) {

                                jobList.clear();
                                jobAdapter.notifyDataSetChanged();

                                for (int i = 0; i <= wrapperArrayObj.length() - 1; i++) {
                                    if (wrapperArrayObj.optJSONObject(i).optString("recordFound") == "true") {

                                        job = new Job();

                                        job.setRiderRefNo(wrapperArrayObj.optJSONObject(i).optString("riderRefNo"));
                                        job.setRiderID(wrapperArrayObj.optJSONObject(i).optString("riderID"));
                                        job.setJobID(wrapperArrayObj.optJSONObject(i).optString("jobID"));
                                        job.setJobName(wrapperArrayObj.optJSONObject(i).optString("jobName"));
                                        job.setServiceCode(wrapperArrayObj.optJSONObject(i).optString("serviceCode"));
                                        job.setRate(wrapperArrayObj.optJSONObject(i).optDouble("rate"));
                                        job.setStatus(wrapperArrayObj.optJSONObject(i).optString("status"));
                                        job.setMakerDateTime(wrapperArrayObj.optJSONObject(i).optString("makerDateTime"));
                                        job.setCurrency(rider.getCurrency());

                                        jobList.add(job);
                                        jobAdapter.notifyDataSetChanged();

                                    }
                                }



                            }//null condition check

                        } else {

                            CommonService.Toast(JobActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(JobActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    CommonService.Toast(JobActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

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
    public void onClickJob(View view, int position) {


        Job job = jobList.get(position);

        addJob(GlobalConstants.EDIT_MODE, job);

    }

    @Override
    public void onClickDelete(View view, int position) {


        Job job = jobList.get(position);

        job.setStatus(GlobalConstants.INACTIVE_CODE);

        new JobActivity.updateJobProgressTask().execute(job);

    }


    public void addJob(String mode,Job job)
    {

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(40, 50, 40, 50);

        final Spinner sp_serviceCode = new Spinner(this);
        final TextView tv_serviceCode = new TextView(this);

        switch (mode) {
            case GlobalConstants.ADD_MODE: {
                String[] servicecode_array = getResources().getStringArray(R.array.servicecode_array);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, servicecode_array);
                sp_serviceCode.setAdapter(adapter);
                sp_serviceCode.setGravity(Gravity.CENTER);
                sp_serviceCode.setLayoutParams(layoutParams);
                linearLayout.addView(sp_serviceCode);
                break;
            }
            case GlobalConstants.EDIT_MODE: {
                tv_serviceCode.setHint("Service Code");
                tv_serviceCode.setMaxLines(1);
                tv_serviceCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                tv_serviceCode.setAllCaps(true);
                tv_serviceCode.setGravity(Gravity.CENTER);
                tv_serviceCode.setLayoutParams(layoutParams);
                linearLayout.addView(tv_serviceCode);
                
                tv_serviceCode.setText(job.getServiceCode());

                break;
            }
        }

        
        final EditText et_jobName = new EditText(this);
        et_jobName.setHint("Job Name, max 10 chars");
        et_jobName.setMaxLines(1);
        et_jobName.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
        et_jobName.setAllCaps(true);
        et_jobName.setGravity(Gravity.CENTER);
        et_jobName.setLayoutParams(layoutParams);
        linearLayout.addView(et_jobName);

        final EditText et_rate = new EditText(this);
        et_rate.setHint("Rate");
        et_rate.setMaxLines(1);
        et_rate.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
        et_rate.addTextChangedListener(new NumberTextWatcher(et_rate)); //number validation
        et_rate.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_rate.setAllCaps(false);
        et_rate.setGravity(Gravity.CENTER);
        et_rate.setLayoutParams(layoutParams);
        linearLayout.addView(et_rate);

        linearLayout.setGravity(Gravity.CENTER);
        Resources res = popDialog.getContext().getResources();
        popDialog.setTitle("New Job");
        popDialog.setView(linearLayout);

        switch (mode) {
            case GlobalConstants.ADD_MODE: {

                break;
            }
            case GlobalConstants.EDIT_MODE: {

                et_jobName.setText(job.getJobName());
                et_rate.setText(new DecimalFormat("#").format(job.getRate()));
                break;
            }
        }


        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //tv_Rating.setText(String.valueOf(rating.getProgress()));



                        if(mode.equals(GlobalConstants.ADD_MODE)) {
                            if (sp_serviceCode.getSelectedItem().toString().length() <= 0 || et_jobName.getText().toString().length() <= 0) {
                                CommonService.Toast(JobActivity.this, "Please enter data", Toast.LENGTH_SHORT);
                                return;
                            }
                        }
                        else if(mode.equals(GlobalConstants.EDIT_MODE))
                        {
                            if (et_jobName.getText().toString().length() <= 0) {
                                CommonService.Toast(JobActivity.this, "Please enter data", Toast.LENGTH_SHORT);
                                return;
                            }
                        }

                        switch (mode)
                        {
                            case GlobalConstants.ADD_MODE:
                            {
                                insertJob(sp_serviceCode.getSelectedItem().toString(), et_jobName.getText().toString(), et_rate.getText().toString());
                                break;
                            }
                            case GlobalConstants.EDIT_MODE:
                            {
                                job.setJobName(et_jobName.getText().toString());
                                job.setRate(Double.parseDouble(et_rate.getText().toString()));
                                job.setServiceCode(tv_serviceCode.getText().toString());
                                job.setStatus(GlobalConstants.ACTIVE_CODE);
                                updateJob(job);
                                break;
                            }
                        }


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
    public void insertJob(String serviceCode,String jobName, String rate) {




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


            String methodAction = "insertJob";

            JSONObject messageJson = new JSONObject();
//                    messageJson.put("service", service);
            messageJson.put("riderRefNo", rider.getRiderRefNo());
            messageJson.put("riderID", rider.getRiderID());
            messageJson.put("serviceCode", serviceCode);
            messageJson.put("jobName", jobName);
            messageJson.put("rate", rate);


            ConnectHost connectHost = new ConnectHost();
            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            Log.d(TAG, "insert Job responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.optJSONObject("insertJob");

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("jobWrapper");

                if (jsonResponseData.getString("success") == "true") {


                    if(wrapperArrayObj!=null) {

                        //Job job=null;

                        if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                            fetchJob();

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

    }//insert job


    private class updateJobProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {
            //loadingSpinner.setVisibility(View.VISIBLE);
            //showProgressBar();
        }

        @Override
        protected Void doInBackground(Object...params ) {

            updateJob((Job)params[0]);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //loadingSpinner.setVisibility(View.GONE);
            //hideProgressBar();





        }
    }

    public void updateJob(Job job) {




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


            String methodAction = "updateJob";

            JSONObject messageJson = new JSONObject();
            messageJson.put("jobName", job.getJobName());
            messageJson.put("rate", job.getRate());
            messageJson.put("status", job.getStatus());
            messageJson.put("riderRefNo", rider.getRiderRefNo());
            messageJson.put("riderID", rider.getRiderID());
            messageJson.put("jobID", job.getJobID());
            messageJson.put("serviceCode", job.getServiceCode());


            ConnectHost connectHost = new ConnectHost();
            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            Log.d(TAG, "update Job responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.optJSONObject("updateJob");

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("jobWrapper");

                if (jsonResponseData.getString("success") == "true") {


                    if(wrapperArrayObj!=null) {


                        if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {

                            fetchJob();


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

    }//update job
}
