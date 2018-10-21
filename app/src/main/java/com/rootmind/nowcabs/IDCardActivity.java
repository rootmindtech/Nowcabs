package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
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
    Driver driver;

    public Toolbar toolbar;
    SharedPreferences sharedPreferences;

    Button btn_login;

    FirebaseStorage firebaseStorage;

    private static final String TAG = "IDCardActivity";


    ImageView iv_avatar;

    private Camera mCamera;
    private int cameraId = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    String userGroup=null;
    String refID=null;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);

        rider = (Rider) getIntent().getSerializableExtra("rider");
        driver = (Driver) getIntent().getSerializableExtra("driver");

        userGroup = (String) getIntent().getSerializableExtra("userGroup");


        if(userGroup.equals(GlobalConstants.RIDER_CODE))
        {
            refID = rider.getRiderID();
        }

        if(userGroup.equals(GlobalConstants.DRIVER_CODE))
        {
            refID = driver.getDriverID();
        }

        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(Color.TRANSPARENT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_login = (Button) findViewById(R.id.btn_login);

        iv_avatar = (ImageView) findViewById(R.id.iv_userimage);
        iv_avatar.setImageResource(R.drawable.avatar_24dp);

        getAvatarImage();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                setLoginDetails();

                CommonService.hideKeyboard(IDCardActivity.this);




            }
        });

        iv_avatar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openFrontCamera();

            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }


    //------------Camera function -----------
    public void openFrontCamera() {

        try {

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            // do we have a camera?
            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                        .show();
            } else {
                cameraId = findFrontFacingCamera();

                Log.d(TAG, "cameraId  " + cameraId);

                if (cameraId < 0) {
                    Toast.makeText(this, "No front facing camera found.",
                            Toast.LENGTH_LONG).show();
                } else {

                    Log.d(TAG, "Camera To Open");

                    mCamera = Camera.open(cameraId);

                }


            }


            //Parameters parameters = mCamera.getParameters();
            //parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            //mCamera.setParameters(parameters);

            mCamera.startPreview();
            mCamera.takePicture(null, null, new PhotoHandler(getApplicationContext()));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private int findFrontFacingCamera() {

        try{

            int cameraId = -1;
            // Search for the front facing camera
            int numberOfCameras = Camera.getNumberOfCameras();

            Log.d(TAG, "numberOfCameras "+numberOfCameras);

            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(i, info);

                Log.d(TAG, "info.facing "+i+" "+info.facing);

                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

                    Log.d(TAG, "Camera found");
                    cameraId = i;
                    break;
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return cameraId;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        try{

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

                //Log.d(TAG, "imageBitmap "+imageBitmap);
                // ImageView imageview = (ImageView) findViewById(R.id.ImageView01);
                //imageview.setImageBitmap(image);

                ByteArrayOutputStream bao = new ByteArrayOutputStream();

                //Bitmap circleBitmap=getCircleBitmap(imageBitmap);

                Bitmap circleBitmap=getCroppedBitmap(imageBitmap,imageBitmap.getWidth());

                circleBitmap.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
                circleBitmap.recycle();

                byte[] byteArray = bao.toByteArray();
                String imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                StorageReference storageRef = firebaseStorage.getReference();

                final StorageReference  avatarRef = storageRef.child("images/" + refID +"_avatar.jpg");

                UploadTask uploadTask = avatarRef.putBytes(byteArray);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        CommonService.Toast(IDCardActivity.this, "Image upload failed", Toast.LENGTH_SHORT);

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        CommonService.Toast(IDCardActivity.this, "Image uploaded", Toast.LENGTH_SHORT);



                    }
                });

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return avatarRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            setAvatarImage(downloadUri);

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });



                Log.d(TAG, "imageBitmap-3");
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }


    public void getAvatarImage()
    {

        StorageReference storageRef = firebaseStorage.getReference();
        final StorageReference  avatarRef = storageRef.child("images/" + refID +"_avatar.jpg");

        avatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                Log.d(TAG, "onSuccess  " + uri);

                setAvatarImage(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                //Toast.makeText(RiderMapActivity.this, "Image download failed", Toast.LENGTH_SHORT).show();
                iv_avatar.setImageResource(R.drawable.avatar_24dp);

            }
        });
    }

    public void setAvatarImage(Uri uri){


        Picasso.get().load(uri).into(iv_avatar); //http://square.github.io/picasso/


    }
    //-------------end of camera function


    public void setLoginDetails()
    {

        if(userGroup.equals(GlobalConstants.DRIVER_CODE))
        {

            Parameter parameter = new Parameter();

            Intent i = new Intent(getApplicationContext(), DriverMapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Parameter", parameter);
            bundle.putSerializable("Driver", driver);
            i.putExtras(bundle);
            startActivity(i);

        }

    }
}
