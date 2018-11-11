package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
//import android.hardware.Camera;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static com.rootmind.nowcabs.DriverMapActivity.getCroppedBitmap;

public class IDCardActivity extends AppCompatActivity {


    Rider rider;
    //Driver driver;

    public Toolbar toolbar;
    SharedPreferences sharedPreferences;

    Button btn_login;

    //FirebaseStorage firebaseStorage;

    private static final String TAG = "IDCardActivity";


    ImageView iv_id_front;
    ImageView iv_id_back;

    String imageID=null;
//    private Camera mCamera;
//    private int cameraId = 0;
//    static final int REQUEST_IMAGE_CAPTURE = 1;


//    String userGroup=null;
//    String refID=null;

    CommonService commonService=null;
    boolean frontPhotoTaken=false;
    boolean backPhotoTaken=false;
    boolean registerFlag=false;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);

        rider = (Rider) getIntent().getSerializableExtra("Rider");
        registerFlag = (boolean) getIntent().getSerializableExtra("RegisterFlag");

        //driver = (Driver) getIntent().getSerializableExtra("driver");

        //userGroup = (String) getIntent().getSerializableExtra("userGroup");


//        if(userGroup.equals(GlobalConstants.RIDER_CODE))
//        {
//            refID = rider.getRiderID();
//        }
//
//        if(userGroup.equals(GlobalConstants.DRIVER_CODE))
//        {
//            refID = driver.getDriverID();
//        }

        commonService = new CommonService();


        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_login = (Button) findViewById(R.id.btn_login);

        iv_id_front = (ImageView) findViewById(R.id.iv_id_front);
        //iv_id_front.setImageResource(R.drawable.avatar_24dp);

        iv_id_back = (ImageView) findViewById(R.id.iv_id_back);

        ImageView nowcabs = (ImageView) findViewById(R.id.nowcabs);

        fetchProfile();


        if(!registerFlag)
        {

            //nowcabs.setVisibility(View.GONE);
            btn_login.setText(R.string.save);
            btn_login.setVisibility(View.GONE);



        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!frontPhotoTaken)
                {
                    CommonService.Toast(IDCardActivity.this,"Please take front side of ID card photo",Toast.LENGTH_SHORT);
                    return;
                }

                if(!backPhotoTaken)
                {
                    CommonService.Toast(IDCardActivity.this,"Please take back side of ID card photo",Toast.LENGTH_SHORT);
                    return;
                }


                setRider();

                CommonService.hideKeyboard(IDCardActivity.this);

            }
        });

        iv_id_front.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                imageID = GlobalConstants.IMAGE_LICENSE_FRONT;
                commonService.openFrontCamera(IDCardActivity.this);

            }
        });

        iv_id_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                imageID= GlobalConstants.IMAGE_LICENSE_BACK;
                commonService.openFrontCamera(IDCardActivity.this);

            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }




    public void setRider()
    {

        if(registerFlag)
        {

            //Parameter parameter = new Parameter();

            Intent i = new Intent(getApplicationContext(), RiderMapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Rider", rider);
            bundle.putSerializable("RegisterFlag", registerFlag);
            i.putExtras(bundle);
            startActivity(i);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(commonService==null) {
            commonService = new CommonService();
        }




        if(imageID.equals(GlobalConstants.IMAGE_LICENSE_FRONT)) {

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                frontPhotoTaken=true;
            }

            rider.setImageName(rider.getRiderID() + "_id_front.jpg");
            rider.setImageID(GlobalConstants.IMAGE_LICENSE_FRONT);
            commonService.onActivityResult(IDCardActivity.this, getApplicationContext(), requestCode, resultCode, data, rider, iv_id_front, false);
        }
        else if(imageID.equals(GlobalConstants.IMAGE_LICENSE_BACK))
        {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                backPhotoTaken=true;
            }
            rider.setImageName(rider.getRiderID() + "_id_back.jpg");
            rider.setImageID(GlobalConstants.IMAGE_LICENSE_BACK);
            commonService.onActivityResult(IDCardActivity.this, getApplicationContext(), requestCode, resultCode, data, rider, iv_id_back, false);
        }


    }

    public void fetchProfile()
    {
        //button click event
        final CommonService commonService = new CommonService();
        commonService.fetchRider(new Listener<Boolean>() {
            @Override
            public void on(Boolean arg) {

                if(arg)
                {

                    commonService.getImage(iv_id_front,CommonService.getImageName(rider,GlobalConstants.IMAGE_LICENSE_FRONT));
                    commonService.getImage(iv_id_back,CommonService.getImageName(rider,GlobalConstants.IMAGE_LICENSE_BACK));

                }
                else
                {
                    btn_login.setVisibility(View.GONE);
                }



            }

        }, IDCardActivity.this, getApplicationContext(), rider);




    }

}
