package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RegionIterator;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {


    String mobileNo;
    //Parameter parameter;
    String locale;

    private static final String TAG = "RegisterActivity";

    Rider rider;
    Service service;

    Service services[];

    public Toolbar toolbar;
    SharedPreferences sharedPreferences;
    //String serviceCode;
    SharedPreferences.Editor editor;


    CardView carpenterView;
    CardView driverView;
    CardView electricianView;
    CardView plumberView ;
    CardView tailorView;
    CardView washerView;
    CardView courierView;
    CardView merchantView;

    boolean registerFlag=false;

    Button btn_skip;
    Button btn_login;


    String responseData = null;

    private ProgressBar loadingSpinner;


    ArrayList<String> serviceArrayList = new ArrayList<String>();
    ArrayList<String> activeServiceArrayList = new ArrayList<String>();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        rider = (Rider) getIntent().getSerializableExtra("Rider");
        registerFlag = (boolean) getIntent().getSerializableExtra("RegisterFlag");

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

//        parameter = (Parameter) getIntent().getSerializableExtra("Parameter");
//        locale = (String) getIntent().getSerializableExtra("locale");


        //29-Sep-2018
        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setServiceArrayList();

        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new RegisterActivity.registerProgressTask().execute();
            }
        });


        btn_skip = (Button) findViewById(R.id.btn_skip);

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                skip();
            }
        });

//        final CardView riderView = (CardView) findViewById(R.id.rider_card_view);
//        riderView.setRadius(10);
//        riderView.setCardElevation(10);
//
//        final CardView driverView = (CardView) findViewById(R.id.driver_card_view);
//        driverView.setRadius(10);
//        driverView.setCardElevation(10);
//        driverView.setTag(GlobalConstants.SERVICE_DRIVER);

        //Button riderButton = (Button) findViewById(R.id.btn_rider);
        //Button driverButton = (Button) findViewById(R.id.btn_driver);


        carpenterView = (CardView) findViewById(R.id.carpenter_cardView);
        driverView = (CardView) findViewById(R.id.driver_cardView);
        electricianView = (CardView) findViewById(R.id.electrician_cardView);
        plumberView = (CardView) findViewById(R.id.plumber_cardView);
        tailorView = (CardView) findViewById(R.id.tailor_cardView);
        washerView = (CardView) findViewById(R.id.washer_cardView);
        courierView = (CardView) findViewById(R.id.courier_cardView);
        merchantView = (CardView) findViewById(R.id.merchant_cardView);


        carpenterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_CARPENTER);

            }
        });

        driverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_DRIVER);

            }
        });

        electricianView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_ELECTRICIAN);

            }
        });

        plumberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_PLUMBER);

            }
        });

        tailorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_TAILOR);

            }
        });

        washerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_WASHER);

            }
        });

        courierView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_COURIER);

            }
        });
        merchantView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_MERCHANT);

            }
        });


        if(!registerFlag)
        {
            btn_skip.setVisibility(View.GONE);
            btn_login.setText(R.string.save);

        }


        loadingSpinner = (ProgressBar) findViewById(R.id.progressBar);
        hideProgressBar();


        new RegisterActivity.fetchServiceProgressTask().execute();


//        riderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                riderView.setBackgroundColor(getResources().getColor(R.color.colorOrange));
//                driverView.setBackgroundColor(Color.WHITE);
//                userGroup = GlobalConstants.RIDER_CODE;
//
//            }
//        });
//
//
//        driverButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                driverView.setBackgroundColor(getResources().getColor(R.color.colorOrange));
//                riderView.setBackgroundColor(Color.WHITE);
//                userGroup = GlobalConstants.DRIVER_CODE;
//
//            }
//        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    private class registerProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {

            showProgressBar();

        }

        @Override
        protected Void doInBackground(Object...params ) {


            register();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            hideProgressBar();

        }
    }

    public void register() {

        RegisterActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                if(activeServiceArrayList.size()<=0)
                {
                    CommonService.Toast(RegisterActivity.this,"Please select your profession",Toast.LENGTH_SHORT);
                    return;
                }

//                if (userGroup != null && !userGroup.isEmpty()) {
//
//                    Log.d("Register", "Usergroup in Register: " + userGroup);
//
//
//                    if (userGroup.equals(GlobalConstants.RIDER_CODE)) {


                services = new Service[serviceArrayList.size()];

                for(int i=0; i<serviceArrayList.size();i++)
                {
                    Service service = new Service();
                    service.setRiderRefNo(rider.riderRefNo);
                    service.setRiderID(rider.riderID);
                    service.setServiceCode(serviceArrayList.get(i));

                    if(activeServiceArrayList.contains(serviceArrayList.get(i))) {
                        service.setStatus(GlobalConstants.ACTIVE_CODE);
                    }
                    else
                    {
                        service.setStatus(GlobalConstants.INACTIVE_CODE);
                    }
                    services[i] = service;
                }

                insertService();

                //move to next screen only during registration
                if(registerFlag) {

                    if (activeServiceArrayList.contains(GlobalConstants.SERVICE_DRIVER)) {
                        setDriverLogin();
                    } else {
                        setAvatar();
                    }
                }

            }//run
        });//runnable

    }

    public void skip()
    {

//        CommonService commonService = new CommonService();
//        commonService.updateRider(new Listener<Boolean>() {
//            @Override
//            public void on(Boolean arg) {
//                if (arg == true) {

                    Intent i = new Intent(getApplicationContext(), RiderMapActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Rider", rider);
                    i.putExtras(bundle);
                    startActivity(i);

//                }
//            }
//        }, RegisterActivity.this, getApplicationContext(), rider);

    }

    public void setDriverLogin()
    {

        Intent i = new Intent(getApplicationContext(), DriverLoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Rider", rider);
        bundle.putSerializable("RegisterFlag",registerFlag);
        i.putExtras(bundle);
        startActivity(i);

    }

    public void setAvatar()
    {

        Intent i = new Intent(getApplicationContext(), AvatarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Rider", rider);
        bundle.putSerializable("RegisterFlag",registerFlag);
        i.putExtras(bundle);
        startActivity(i);

    }

    public void setServiceArrayList()
    {
        serviceArrayList.add(GlobalConstants.SERVICE_CARPENTER);
        serviceArrayList.add(GlobalConstants.SERVICE_COURIER);
        serviceArrayList.add(GlobalConstants.SERVICE_DRIVER);
        serviceArrayList.add(GlobalConstants.SERVICE_ELECTRICIAN);
        serviceArrayList.add(GlobalConstants.SERVICE_MERCHANT);
        serviceArrayList.add(GlobalConstants.SERVICE_PLUMBER);
        serviceArrayList.add(GlobalConstants.SERVICE_TAILOR);
        serviceArrayList.add(GlobalConstants.SERVICE_WASHER);

    }

    public void setActiveServiceArrayList(View view,String serviceCode)
    {
        if(activeServiceArrayList.contains(serviceCode))
        {
            view.setBackgroundColor(Color.WHITE);
            activeServiceArrayList.remove(serviceCode);
        }
        else
        {
            view.setBackgroundColor(Color.YELLOW);
            activeServiceArrayList.add(serviceCode);
        }
    }


    public void insertService() {



//        showProgressBar();
//
//
//        RegisterActivity.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {



                try {



                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "insertService";

                    JSONArray messageJsonArray = new JSONArray();

                    for(int i=0;i<services.length;i++) {

                        JSONObject messageJson = new JSONObject();
                        messageJson.put("riderRefNo", services[i].getRiderRefNo());
                        messageJson.put("riderID", services[i].getRiderID());
                        messageJson.put("serviceCode", services[i].getServiceCode());
                        messageJson.put("status", services[i].getStatus());
                        messageJsonArray.put(messageJson);
                    }


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJsonArray.toString(), sharedPreferences);

                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "Register responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.getJSONObject("insertService");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("serviceWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {



                            Toast.makeText(RegisterActivity.this, "Update successful", Toast.LENGTH_SHORT).show();



                        }
                        else
                        {

                            Toast.makeText(RegisterActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(RegisterActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                }


                //}//validation


//            }//run end
//
//        });//runnable end
//
//        hideProgressBar();


    }//end of update service


    private class fetchServiceProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {

            showProgressBar();

        }

        @Override
        protected Void doInBackground(Object...params ) {


            String result =fetchService();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            hideProgressBar();

            if(result!=null)
            {
                CommonService.Toast(RegisterActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

            }

        }
    }

    public String fetchService() {


                String result=null;

                try {


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    String methodAction = "fetchService";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());



                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
                    // loadingSpinner.setVisibility(View.GONE);

                    Log.d(TAG, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchService");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.optJSONArray("serviceWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.optString("success") == "true")
                        {

                            if (wrapperArrayObj.optJSONObject(0).optString("recordFound") == "true") {

                                services = new Service[wrapperArrayObj.length()];
                            }

                            for(int i=0;i<wrapperArrayObj.length();i++) {

                                if (wrapperArrayObj.optJSONObject(i).optString("recordFound") == "true") {


                                    service = new Service();
                                    service.setRiderRefNo(wrapperArrayObj.getJSONObject(i).optString("riderRefNo"));
                                    service.setRiderID(wrapperArrayObj.getJSONObject(i).optString("riderID"));
                                    service.setServiceCode(wrapperArrayObj.getJSONObject(i).optString("serviceCode"));
                                    service.setStatus(wrapperArrayObj.getJSONObject(i).optString("status"));

                                    services[i]=service;

                                }

                            }

                            setService();

                        } else {

                            //Toast.makeText(RegisterActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                            result = GlobalConstants.SYSTEM_ERROR;

                        }


                    } else {

                        //Toast.makeText(RegisterActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                        result = GlobalConstants.SYSTEM_ERROR;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(RegisterActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    btn_login.setVisibility(View.GONE);
                    result = GlobalConstants.SYSTEM_ERROR;


                }


                return result;

    } //-------end of fetch service


    public void setService()
    {

        carpenterView.setBackgroundColor(Color.WHITE);
        courierView.setBackgroundColor(Color.WHITE);
        driverView.setBackgroundColor(Color.WHITE);
        electricianView.setBackgroundColor(Color.WHITE);
        merchantView.setBackgroundColor(Color.WHITE);
        plumberView.setBackgroundColor(Color.WHITE);
        tailorView.setBackgroundColor(Color.WHITE);
        washerView.setBackgroundColor(Color.WHITE);

        activeServiceArrayList.clear();

        for(int i=0;i< (services!=null ? services.length : 0);i++)
        {

            if(services[i].getStatus().equals(GlobalConstants.ACTIVE_CODE))
            {

                switch (services[i].getServiceCode()) {


                    case GlobalConstants.SERVICE_CARPENTER: {

                        carpenterView.setBackgroundColor(Color.YELLOW);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_CARPENTER);
                        break;
                    }
                    case GlobalConstants.SERVICE_COURIER: {

                        courierView.setBackgroundColor(Color.YELLOW);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_COURIER);
                        break;

                    }
                    case GlobalConstants.SERVICE_DRIVER: {

                        driverView.setBackgroundColor(Color.YELLOW);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_DRIVER);
                        break;

                    }
                    case GlobalConstants.SERVICE_ELECTRICIAN: {

                        electricianView.setBackgroundColor(Color.YELLOW);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_ELECTRICIAN);
                        break;

                    }
                    case GlobalConstants.SERVICE_MERCHANT: {

                        merchantView.setBackgroundColor(Color.YELLOW);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_MERCHANT);
                        break;

                    }
                    case GlobalConstants.SERVICE_PLUMBER: {

                        plumberView.setBackgroundColor(Color.YELLOW);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_PLUMBER);
                        break;

                    }
                    case GlobalConstants.SERVICE_TAILOR: {

                        tailorView.setBackgroundColor(Color.YELLOW);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_TAILOR);
                        break;

                    }
                    case GlobalConstants.SERVICE_WASHER: {

                        washerView.setBackgroundColor(Color.YELLOW);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_WASHER);
                        break;

                    }

                }
            }

        }

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


