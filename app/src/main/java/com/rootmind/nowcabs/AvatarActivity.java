package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class AvatarActivity extends AppCompatActivity {



    Rider rider;
    //Driver driver;
    
    public Toolbar toolbar;
    SharedPreferences sharedPreferences;

    Button btn_login;

    FirebaseStorage firebaseStorage;

    private static final String TAG = "AvatarActivity";


    ImageView iv_avatar;

//    private Camera mCamera;
//    private int cameraId = 0;
//    static final int REQUEST_IMAGE_CAPTURE = 1;


    //String userGroup=null;
    //String refID=null;

    CommonService commonService=null;
    boolean photoTaken=false;
    boolean registerFlag=false;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        rider = (Rider) getIntent().getSerializableExtra("Rider");
        registerFlag = (boolean) getIntent().getSerializableExtra("RegisterFlag");

        //driver = (Driver) getIntent().getSerializableExtra("driver");

//        userGroup = (String) getIntent().getSerializableExtra("userGroup");
//
//
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

        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        //iv_avatar.setImageResource(R.drawable.avatar_24dp);


        commonService.getImage(iv_avatar,rider.getImageName());

        if(!registerFlag)
        {
            btn_login.setText(R.string.save);
            btn_login.setVisibility(View.GONE);
        }

        commonService.getImage(iv_avatar,rider.getImageName());


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!photoTaken)
                {
                    CommonService.Toast(AvatarActivity.this,"Please take photo",Toast.LENGTH_SHORT);
                    return;
                }

                setRider();

                CommonService.hideKeyboard(AvatarActivity.this);




            }
        });

        iv_avatar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                commonService.openFrontCamera(AvatarActivity.this);

            }
        });

//        if(!registerFlag) {
//
//            Button btn_close = (Button) findViewById(R.id.btn_close);
//            btn_close.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    // TODO:
//                    // This function closes Activity Two
//                    // Hint: use Context's finish() method
//                    finish();
//                }
//            });
//
//        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }



    public void setRider()
    {

        //only during registration move to next screen
        if(registerFlag)
        {

            //Parameter parameter = new Parameter();

            Intent i = new Intent(getApplicationContext(), IDCardActivity.class);
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

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            photoTaken=true;
        }
            rider.setImageName(rider.getRiderID()+"_avatar.jpg");
        rider.setImageID(GlobalConstants.IMAGE_AVATAR);
        commonService.onActivityResult(AvatarActivity.this, getApplicationContext() , requestCode,resultCode,data, rider, iv_avatar, true);

    }



}
