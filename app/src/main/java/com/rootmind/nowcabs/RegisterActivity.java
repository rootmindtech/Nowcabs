package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RegionIterator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.widget.Button;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    String mobileNo;
    Parameter parameter;
    String locale;

    public Toolbar toolbar;
    SharedPreferences sharedPreferences;
    String userGroup;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        mobileNo = (String) getIntent().getSerializableExtra("mobileNo");
        parameter = (Parameter) getIntent().getSerializableExtra("Parameter");
        locale = (String) getIntent().getSerializableExtra("locale");


        //29-Sep-2018
        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                register();
            }
        });

        final CardView riderView = (CardView) findViewById(R.id.rider_card_view);
        riderView.setRadius(10);
        riderView.setCardElevation(10);

        final CardView driverView = (CardView)findViewById(R.id.driver_card_view);
        driverView.setRadius(10);
        driverView.setCardElevation(10);

        Button riderButton = (Button) findViewById(R.id.btn_rider);
        Button driverButton = (Button) findViewById(R.id.btn_driver);


        riderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                driverView.setBackgroundColor(Color.WHITE);
                userGroup = GlobalConstants.RIDER_CODE;

            }
        });


        driverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                riderView.setBackgroundColor(Color.WHITE);
                userGroup = GlobalConstants.DRIVER_CODE;

            }
        });

        riderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                riderView.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                driverView.setBackgroundColor(Color.WHITE);
                userGroup = GlobalConstants.RIDER_CODE;

            }
        });


        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                driverView.setBackgroundColor(getResources().getColor(R.color.colorOrange));
                riderView.setBackgroundColor(Color.WHITE);
                userGroup = GlobalConstants.DRIVER_CODE;

            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    public void register()
    {

        RegisterActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                if (userGroup != null && !userGroup.isEmpty()) {

                    Log.d("Register", "Usergroup in Register: " + userGroup);


                    if (userGroup.equals(GlobalConstants.RIDER_CODE)) {


                        CommonService commonService = new CommonService();
                        boolean result = commonService.riderAutoLogin(RegisterActivity.this, getApplicationContext(), mobileNo);

                        if(result==false) {

                            Intent i = new Intent(getApplicationContext(), RiderLoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Parameter", parameter);
                            bundle.putSerializable("mobileNo", mobileNo);
                            bundle.putSerializable("userGroup", userGroup);
                            bundle.putSerializable("locale", locale);
                            i.putExtras(bundle);
                            startActivity(i);

                            Log.d("Register", "moved to RiderLogin: " + userGroup);
                        }

                    }
                    if (userGroup.equals(GlobalConstants.DRIVER_CODE)) {

                        CommonService commonService = new CommonService();
                        boolean result = commonService.driverAutoLogin(RegisterActivity.this, getApplicationContext(), mobileNo);

                        if(result==false) {

                            Intent i = new Intent(getApplicationContext(), DriverLoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Parameter", parameter);
                            bundle.putSerializable("mobileNo", mobileNo);
                            bundle.putSerializable("userGroup", userGroup);
                            bundle.putSerializable("locale", locale);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    }

                } else {

                    Toast.makeText(RegisterActivity.this, R.string.select_rider_driver, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
