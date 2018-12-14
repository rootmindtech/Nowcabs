package com.rootmind.nowcabs;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public Toolbar toolbar;
    public DrawerLayout drawer;
    private static final String TAG = "ServiceActivity";


    Rider rider;
    Service service;
    Button btn_save;

    ArrayList<Service> serviceArrayList = new ArrayList<Service>();

    String responseData = null;


    SharedPreferences sharedPreferences;
    //Shared Preferences
    SharedPreferences.Editor editor;


    public TextView tv_carpenter_count;
    public TextView tv_autodriver_count;
    public TextView tv_cabdriver_count;
    public TextView tv_electrician_count;
    public TextView tv_plumber_count;
    public TextView tv_tailor_count;
    public TextView tv_washer_count;
    public TextView tv_courier_count;
    public TextView tv_merchant_count;

    public TextView tv_movers_count;
    public TextView tv_housekeeper_count;
    public TextView tv_cook_count;
    public TextView tv_painter_count;
    public TextView tv_florist_count;
    public TextView tv_pesticide_count;
    public TextView tv_tutor_count;
    public TextView tv_locksmith_count;
    public TextView tv_grinder_count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);



        rider = (Rider) getIntent().getSerializableExtra("Rider");

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.profile_and_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        btn_save = (Button) findViewById(R.id.btn_save);

        fetchService();


        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


//                // TextWatcher would let us check validation error on the fly
//                txt_name.addTextChangedListener(new TextWatcher() {
//                    public void afterTextChanged(Editable s) {
//                        Validation.hasText(txt_name);
//                    }
//
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    }
//                });

//                if (Validation.hasText(txt_name)) {
//
//                    name = txt_name.getText().toString().trim();
//                    locale = CommonService.selectLocale(dropDown_locale.getSelectedItemPosition());


                    updateProfile();
//                }


            }
        });


        tv_carpenter_count= (TextView)findViewById(R.id.tv_carpenter_count);
        tv_autodriver_count= (TextView)findViewById(R.id.tv_autodriver_count);
        tv_cabdriver_count= (TextView)findViewById(R.id.tv_cabdriver_count);
        tv_electrician_count= (TextView)findViewById(R.id.tv_electrician_count);
        tv_plumber_count= (TextView)findViewById(R.id.tv_plumber_count);
        tv_tailor_count= (TextView)findViewById(R.id.tv_tailor_count);
        tv_washer_count= (TextView)findViewById(R.id.tv_washer_count);
        tv_courier_count= (TextView)findViewById(R.id.tv_courier_count);
        tv_merchant_count= (TextView)findViewById(R.id.tv_merchant_count);

        tv_movers_count= (TextView)findViewById(R.id.tv_movers_count);
        tv_housekeeper_count= (TextView)findViewById(R.id.tv_housekeeper_count);
        tv_cook_count= (TextView)findViewById(R.id.tv_cook_count);
        tv_painter_count= (TextView)findViewById(R.id.tv_painter_count);
        tv_florist_count= (TextView)findViewById(R.id.tv_florist_count);
        tv_pesticide_count= (TextView)findViewById(R.id.tv_pesticide_count);
        tv_tutor_count= (TextView)findViewById(R.id.tv_tutor_count);
        tv_locksmith_count= (TextView)findViewById(R.id.tv_locksmith_count);
        tv_grinder_count= (TextView)findViewById(R.id.tv_grinder_count);

        tv_carpenter_count.setVisibility(View.GONE);
        tv_autodriver_count.setVisibility(View.GONE);
        tv_cabdriver_count.setVisibility(View.GONE);
        tv_electrician_count.setVisibility(View.GONE);
        tv_plumber_count.setVisibility(View.GONE);
        tv_tailor_count.setVisibility(View.GONE);
        tv_washer_count.setVisibility(View.GONE);
        tv_courier_count.setVisibility(View.GONE);
        tv_merchant_count.setVisibility(View.GONE);

        tv_movers_count.setVisibility(View.GONE);
        tv_housekeeper_count.setVisibility(View.GONE);
        tv_cook_count.setVisibility(View.GONE);
        tv_painter_count.setVisibility(View.GONE);
        tv_florist_count.setVisibility(View.GONE);
        tv_pesticide_count.setVisibility(View.GONE);
        tv_tutor_count.setVisibility(View.GONE);
        tv_locksmith_count.setVisibility(View.GONE);
        tv_grinder_count.setVisibility(View.GONE);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void fetchService() {


        ServiceActivity.this.runOnUiThread(new Runnable() {

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

                    String methodAction = "fetchService";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());
                    messageJson.put("status", rider.getStatus());


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.getJSONObject("fetchService");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("serviceWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true")
                        {

                            if(wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {

                                for(int i=0;i<=wrapperArrayObj.length()-1;i++) {

                                    service = new Service();
                                    service.setRiderRefNo(wrapperArrayObj.getJSONObject(i).optString("riderRefNo"));
                                    service.setRiderID(wrapperArrayObj.getJSONObject(i).optString("riderID"));
                                    service.setServiceCode(wrapperArrayObj.getJSONObject(i).optString("serviceCode"));
                                    service.setStatus(wrapperArrayObj.getJSONObject(i).optString("status"));

                                    serviceArrayList.add(service);
                                }

                                buildServiceList();
                            }


                        } else {

                            CommonService.Toast(ServiceActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(ServiceActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                //}//validation

            }//run end

        });//runnable end


    } //-------end of fetch profile


    public void updateProfile() {


        ServiceActivity.this.runOnUiThread(new Runnable() {

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


                    String methodAction = "updateService";

                    JSONObject messageJson = new JSONObject();
                    //messageJson.put("mobileNo", mobileNo);
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());



                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.getJSONObject("updateRider");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                            rider = new Rider();

                            rider.setRiderRefNo(wrapperArrayObj.getJSONObject(0).getString("riderRefNo"));
                            rider.setRiderID(wrapperArrayObj.getJSONObject(0).getString("riderID"));
                            //rider.setRiderMobileNo(wrapperArrayObj.getJSONObject(0).getString("mobileNo"));
                            rider.setRiderName(wrapperArrayObj.getJSONObject(0).optString("firstName"));
                            rider.setLocale(wrapperArrayObj.getJSONObject(0).optString("locale"));


                        } else {

                            CommonService.Toast(ServiceActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(ServiceActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                //}//validation

            }//run end

        });//runnable end


    } //-------end of update profile

    private void buildServiceList(){

        try{

            int j=30;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            RelativeLayout linearLayout = findViewById(R.id.switchLayout);

            for(int i=0;i<=serviceArrayList.size()-1;i++) {

                j=j+100;

                Service service = serviceArrayList.get(i);
                // Create Switch programmatically.
                final Switch sw = new Switch(this);
                layoutParams.setMargins(30, j, 30, 30);
                sw.setLayoutParams(layoutParams);
                sw.setText(service.getServiceCode());

                // Add Switch to LinearLayout
                if (linearLayout != null) {
                    linearLayout.addView(sw);
                }

            }

        } catch (Exception e) {
        e.printStackTrace();
    }

}
}
