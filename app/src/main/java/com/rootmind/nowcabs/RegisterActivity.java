package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
    CardView autoDriverView;
    CardView cabDriverView;
    CardView electricianView;
    CardView plumberView ;
    CardView tailorView;
    CardView washerView;
    CardView courierView;
    CardView merchantView;

    CardView moversView;
    CardView housekeeperView;
    CardView cookView;
    CardView painterView;
    CardView floristView;
    CardView pesticideView;
    CardView tutorView;
    CardView locksmithView;
    CardView grinderView;


    boolean registerFlag=false;

    Button btn_skip;
    Button btn_login;


    String responseData = null;

    private LinearLayout loadingSpinner;

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
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("Select profession");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_18px);




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
        autoDriverView = (CardView) findViewById(R.id.auto_driver_cardView);
        cabDriverView = (CardView) findViewById(R.id.cab_driver_cardView);
        electricianView = (CardView) findViewById(R.id.electrician_cardView);
        plumberView = (CardView) findViewById(R.id.plumber_cardView);
        tailorView = (CardView) findViewById(R.id.tailor_cardView);
        washerView = (CardView) findViewById(R.id.washer_cardView);
        courierView = (CardView) findViewById(R.id.courier_cardView);
        merchantView = (CardView) findViewById(R.id.merchant_cardView);

        moversView = (CardView) findViewById(R.id.movers_cardView);
        housekeeperView = (CardView) findViewById(R.id.housekeeper_cardView);
        cookView = (CardView) findViewById(R.id.cook_cardView);
        painterView = (CardView) findViewById(R.id.painter_cardView);
        floristView = (CardView) findViewById(R.id.florist_cardView);
        pesticideView = (CardView) findViewById(R.id.pesticide_cardView);
        tutorView = (CardView) findViewById(R.id.tutor_cardView);
        locksmithView = (CardView) findViewById(R.id.locksmith_cardView);
        grinderView = (CardView) findViewById(R.id.grinder_cardView);

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


        carpenterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_CARPENTER);

            }
        });

        autoDriverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_AUTO_DRIVER);

            }
        });

        cabDriverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_CAB_DRIVER);

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

        moversView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_MOVERS);

            }
        });
        housekeeperView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_HOUSEKEEPER);

            }
        });
        cookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_COOK);

            }
        });
        painterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_PAINTER);

            }
        });
        floristView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_FLORIST);

            }
        });
        pesticideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_PESTICIDE);

            }
        });
        tutorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_TUTOR);

            }
        });
        locksmithView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_LOCKSMITH);

            }
        });
        grinderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                setActiveServiceArrayList(v,GlobalConstants.SERVICE_GRINDER);

            }
        });


        if(!registerFlag)
        {
            btn_skip.setVisibility(View.GONE);
            btn_login.setText(R.string.save);

        }


        loadingSpinner = ((LinearLayout)findViewById(R.id.progressBarLayout));
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

                    if (activeServiceArrayList.contains(GlobalConstants.SERVICE_AUTO_DRIVER)) {
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
        serviceArrayList.add(GlobalConstants.SERVICE_AUTO_DRIVER);
        serviceArrayList.add(GlobalConstants.SERVICE_CAB_DRIVER);
        serviceArrayList.add(GlobalConstants.SERVICE_ELECTRICIAN);
        serviceArrayList.add(GlobalConstants.SERVICE_MERCHANT);
        serviceArrayList.add(GlobalConstants.SERVICE_PLUMBER);
        serviceArrayList.add(GlobalConstants.SERVICE_TAILOR);
        serviceArrayList.add(GlobalConstants.SERVICE_WASHER);

        serviceArrayList.add(GlobalConstants.SERVICE_MOVERS);
        serviceArrayList.add(GlobalConstants.SERVICE_HOUSEKEEPER);
        serviceArrayList.add(GlobalConstants.SERVICE_COOK);
        serviceArrayList.add(GlobalConstants.SERVICE_PAINTER);
        serviceArrayList.add(GlobalConstants.SERVICE_FLORIST);
        serviceArrayList.add(GlobalConstants.SERVICE_PESTICIDE);
        serviceArrayList.add(GlobalConstants.SERVICE_TUTOR);
        serviceArrayList.add(GlobalConstants.SERVICE_LOCKSMITH);
        serviceArrayList.add(GlobalConstants.SERVICE_GRINDER);

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
            view.setBackgroundColor(Color.GRAY);
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
                    //btn_login.setVisibility(View.GONE);
                    result = GlobalConstants.SYSTEM_ERROR;


                }


                return result;

    } //-------end of fetch service


    public void setService()
    {

        carpenterView.setBackgroundColor(Color.WHITE);
        courierView.setBackgroundColor(Color.WHITE);
        autoDriverView.setBackgroundColor(Color.WHITE);
        cabDriverView.setBackgroundColor(Color.WHITE);
        electricianView.setBackgroundColor(Color.WHITE);
        merchantView.setBackgroundColor(Color.WHITE);
        plumberView.setBackgroundColor(Color.WHITE);
        tailorView.setBackgroundColor(Color.WHITE);
        washerView.setBackgroundColor(Color.WHITE);

        moversView.setBackgroundColor(Color.WHITE);
        housekeeperView.setBackgroundColor(Color.WHITE);
        cookView.setBackgroundColor(Color.WHITE);
        painterView.setBackgroundColor(Color.WHITE);
        floristView.setBackgroundColor(Color.WHITE);
        pesticideView.setBackgroundColor(Color.WHITE);
        tutorView.setBackgroundColor(Color.WHITE);
        locksmithView.setBackgroundColor(Color.WHITE);
        grinderView.setBackgroundColor(Color.WHITE);



        activeServiceArrayList.clear();

        for(int i=0;i< (services!=null ? services.length : 0);i++)
        {

            if(services[i].getStatus().equals(GlobalConstants.ACTIVE_CODE))
            {

                switch (services[i].getServiceCode()) {


                    case GlobalConstants.SERVICE_CARPENTER: {

                        carpenterView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_CARPENTER);
                        break;
                    }
                    case GlobalConstants.SERVICE_COURIER: {

                        courierView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_COURIER);
                        break;

                    }
                    case GlobalConstants.SERVICE_AUTO_DRIVER: {

                        autoDriverView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_AUTO_DRIVER);
                        break;

                    }
                    case GlobalConstants.SERVICE_CAB_DRIVER: {

                        cabDriverView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_CAB_DRIVER);
                        break;

                    }
                    case GlobalConstants.SERVICE_ELECTRICIAN: {

                        electricianView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_ELECTRICIAN);
                        break;

                    }
                    case GlobalConstants.SERVICE_MERCHANT: {

                        merchantView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_MERCHANT);
                        break;

                    }
                    case GlobalConstants.SERVICE_PLUMBER: {

                        plumberView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_PLUMBER);
                        break;

                    }
                    case GlobalConstants.SERVICE_TAILOR: {

                        tailorView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_TAILOR);
                        break;

                    }
                    case GlobalConstants.SERVICE_WASHER: {

                        washerView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_WASHER);
                        break;

                    }
                    case GlobalConstants.SERVICE_MOVERS: {

                        moversView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_MOVERS);
                        break;

                    }
                    case GlobalConstants.SERVICE_HOUSEKEEPER: {

                        housekeeperView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_HOUSEKEEPER);
                        break;

                    }
                    case GlobalConstants.SERVICE_COOK: {

                        cookView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_COOK);
                        break;

                    }
                    case GlobalConstants.SERVICE_PAINTER: {

                        painterView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_PAINTER);
                        break;

                    }
                    case GlobalConstants.SERVICE_FLORIST: {

                        floristView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_FLORIST);
                        break;

                    }
                    case GlobalConstants.SERVICE_PESTICIDE: {

                        pesticideView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_PESTICIDE);
                        break;

                    }
                    case GlobalConstants.SERVICE_TUTOR: {

                        tutorView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_TUTOR);
                        break;

                    }
                    case GlobalConstants.SERVICE_LOCKSMITH: {

                        locksmithView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_LOCKSMITH);
                        break;

                    }
                    case GlobalConstants.SERVICE_GRINDER: {

                        grinderView.setBackgroundColor(Color.GRAY);
                        activeServiceArrayList.add(GlobalConstants.SERVICE_GRINDER);
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


