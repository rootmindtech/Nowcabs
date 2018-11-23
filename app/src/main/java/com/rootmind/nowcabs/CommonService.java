package com.rootmind.nowcabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public  class CommonService {


    SharedPreferences sharedPreferences;
    //Shared Preferences
    SharedPreferences.Editor editor;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Camera mCamera;
    private int cameraId = 0;

    private static final String TAG = "CommonService";

    FirebaseStorage firebaseStorage;



    public CommonService() {

        firebaseStorage = FirebaseStorage.getInstance(GlobalConstants.FIREBASE_URL);

    }



    public static final String isNull(String input)
    {
        if(input == null) {

            return "";
        }
        else
        {
            return input;
        }
    }

    public static final boolean isEmpty(String input)
    {
        if(input == null) {

            return true;
        }
        else if(input.trim().equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static final String selectVehicleType(int input)
    {
        String vehicleType=GlobalConstants.AUTO_CODE;

        switch (input)
        {
            case 0:{
                vehicleType = GlobalConstants.AUTO_CODE;
                break;
            }
            case 1:{
                vehicleType = GlobalConstants.CAB_CODE;
                break;
            }

        }
        return vehicleType;
    }

    public static final int populateVehicleType(String input)
    {
        int currentSelect=0;

        if(isEmpty(input))
        {
            input = GlobalConstants.AUTO_CODE;
        }

        switch (input.trim())
        {
            case GlobalConstants.AUTO_CODE:{
                currentSelect = 0;
                break;
            }
            case GlobalConstants.CAB_CODE:{
                currentSelect = 1;
                break;
            }

        }
        return currentSelect;
    }


    public static final String selectLocale(int input)
    {
        String currentLanguage=GlobalConstants.ENGLISH_LOCALE;
        switch (input)
        {
            case 0:{
                currentLanguage = GlobalConstants.ENGLISH_LOCALE;
                break;
            }
            case 1:{
                currentLanguage = GlobalConstants.HINDI_LOCALE;
                break;
            }
            case 2:{
                currentLanguage = GlobalConstants.TELUGU_LOCALE;
                break;
            }
            case 3:{
                currentLanguage = GlobalConstants.TAMIL_LOCALE;
                break;
            }
            case 4:{
                currentLanguage = GlobalConstants.KANNADA_LOCALE;
                break;
            }
            case 5:{
                currentLanguage = GlobalConstants.URDU_LOCALE;
                break;
            }


        }
        return currentLanguage;
    }


    public static final int populateLocale(String input)
    {
        int currentSelect=0;

        if(isEmpty(input))
        {
            input = GlobalConstants.ENGLISH_LOCALE;
        }

        switch (input.trim())
        {
            case GlobalConstants.ENGLISH_LOCALE:{
                currentSelect = 0;
                break;
            }
            case GlobalConstants.HINDI_LOCALE:{
                currentSelect = 1;
                break;
            }
            case GlobalConstants.TELUGU_LOCALE:{
                currentSelect = 2;
                break;
            }
            case GlobalConstants.TAMIL_LOCALE:{
                currentSelect = 3;
                break;
            }
            case GlobalConstants.KANNADA_LOCALE:{
                currentSelect = 4;
                break;
            }
            case GlobalConstants.URDU_LOCALE:{
                currentSelect = 5;
                break;
            }


        }
        return currentSelect;
    }

    public static final Locale getLocale(String input)
    {
        String locale = GlobalConstants.ENGLISH_LOCALE;

        if(isEmpty(input))
        {
            locale = GlobalConstants.ENGLISH_LOCALE;
        }
        else
        {
            locale = input;
        }

        return new Locale(locale);
    }


    public static void  hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void  hideKeyboardView(Context context, View  view) {

        InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public void riderAutoLogin(final Listener<Rider> onCompleteListener, final Activity activity, final Context context, final String mobileNo)
    {

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                String responseData = null;
                String fcmToken=null;
                String locale=null;

                Rider rider = new Rider();



                try {


                    //sharedPreferences initiated
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


                    fcmToken = sharedPreferences.getString("fcmToken", "");
                    locale = getLocale(sharedPreferences.getString("locale", "")).toString();


                    Log.d(GlobalConstants.CommonService, "fcm token riderautologin " + fcmToken);


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", "SYSTEM");
                    editor.putString("deviceToken", fcmToken);
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "riderAutoLogin";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("mobileNo", mobileNo);
                    messageJson.put("riderRefNo", sharedPreferences.getString("riderRefNo", ""));
                    messageJson.put("riderID", sharedPreferences.getString("riderID", ""));
                    messageJson.put("fcmToken", fcmToken);
                    messageJson.put("locale", locale);
                    messageJson.put("deviceInfo", getDeviceInfo());


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.optJSONObject("riderAutoLogin");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true") {

                            //if host response is success
                            rider.setHostResponse(true);

                            if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {

                                Calendar c = Calendar.getInstance();
                                System.out.println("Current time => " + c.getTime());
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                                final String formattedDate = df.format(c.getTime());


                                rider.setRiderRefNo(wrapperArrayObj.optJSONObject(0).optString("riderRefNo"));
                                rider.setRiderID(wrapperArrayObj.optJSONObject(0).optString("riderID"));
                                rider.setMobileNo(wrapperArrayObj.optJSONObject(0).optString("mobileNo"));
                                rider.setRiderName(wrapperArrayObj.optJSONObject(0).optString("firstName"));
                                rider.setStatus(wrapperArrayObj.optJSONObject(0).optString("status"));
                                rider.setRecordFound(wrapperArrayObj.optJSONObject(0).optBoolean("recordFound"));
                                rider.setCustomToken(wrapperArrayObj.optJSONObject(0).optString("customToken"));
                                rider.setDatetime(formattedDate);
                                rider.setFcmToken(fcmToken);
                                rider.setLocale(wrapperArrayObj.optJSONObject(0).optString("locale"));


                                //setRiderLogin(activity, context, rider, fcmToken);

                                onCompleteListener.on(rider);
                            } else {

                                //if valid response and record not found then allow for OTP
                                onCompleteListener.on(rider);

                            }

                        } else {

                            Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                            onCompleteListener.on(rider);

                        }


                    } else {

                        Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);
                        onCompleteListener.on(rider);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    onCompleteListener.on(rider);

                }
            } //run end


        });


    }


//    private void setRiderLogin(Activity activity, Context context,Rider rider, String fcmToken) {
//
//
//        //Shared Preferences
//        editor = sharedPreferences.edit();
//
//        Log.d(GlobalConstants.CommonService, "SharedPreferences putString ");
//
////        editor.putString("riderMobileNo", rider.getRiderMobileNo());
////        editor.putString("riderFirstName", rider.getRiderName());
//        editor.putString("userGroup", GlobalConstants.RIDER_CODE);
//        editor.putString("riderRefNo", rider.getRiderRefNo());
//        editor.putString("riderID", rider.getRiderID());
//        editor.putString("autoLogin", GlobalConstants.YES_CODE);
//        editor.putString("fcmToken", fcmToken);
//        editor.putString("locale",CommonService.getLocale(rider.getLocale()).toString());
//        //Log.d(TAG, "fcmToken: " + fcmToken);
//
//        editor.apply();
//
//        setLocale(context,CommonService.getLocale(rider.getLocale()).toString());
//
//        //Log.d(TAG, "Saved Info: " + sharedPreferences.getString("riderMobileNo", "") );
//
//
//        //Log.d(TAG, "Before Change Activity " + rider.getStatus());
//
//
//        if (rider.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {
//
//            Parameter parameter = new Parameter();
//
//            Intent i = new Intent(context, RiderMapActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("Parameter", parameter);
//            bundle.putSerializable("Rider", rider);
//            bundle.putSerializable("locale", CommonService.getLocale(rider.getLocale()).toString());
//            i.putExtras(bundle);
//            context.startActivity(i);
//
//            Log.d(GlobalConstants.CommonService, "Going to RiderMap ");
//        } else {
//
//            Log.d(GlobalConstants.CommonService, "Access Restricted");
//
//            Toast(activity, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT);
//
//        }
//
//
//    }


    public boolean driverAutoLogin(Activity activity, Context context, String mobileNo) {

        String responseData = null;
        String fcmToken = null;
        String locale = null;

        try {


            //sharedPreferences initiated
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


            fcmToken = sharedPreferences.getString("fcmToken","");
            locale = getLocale(sharedPreferences.getString("locale", "")).toString();

            //Shared Preferences
            editor = sharedPreferences.edit();

            editor.putString("userid", "SYSTEM");
            editor.putString("deviceToken", fcmToken);
            editor.putString("sessionid", "SESSIONID");

            editor.apply();


            Log.d(GlobalConstants.CommonService, "mobileNo: " + mobileNo + " locale" + locale);

            //loadingSpinner.setVisibility(View.VISIBLE);


            String methodAction = "driverAutoLogin";

            JSONObject messageJson = new JSONObject();
            messageJson.put("mobileNo", mobileNo);
            messageJson.put("driverRefNo", sharedPreferences.getString("driverRefNo", ""));
            messageJson.put("driverID", sharedPreferences.getString("driverID", ""));
            messageJson.put("fcmToken", fcmToken);
            messageJson.put("locale", locale);


            ConnectHost connectHost = new ConnectHost();

            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            //loadingSpinner.setVisibility(View.GONE);

            Log.d(GlobalConstants.CommonService, "DriverLogin responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.optJSONObject("driverAutoLogin");

                //Log.d(TAG, "resData insertRider: " + jsonResult);
                //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("driverWrapper");

                //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                if (jsonResponseData.getString("success") == "true"){

                     if(wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                         Calendar c = Calendar.getInstance();
                         System.out.println("Current time => " + c.getTime());
                         SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                         final String formattedDate = df.format(c.getTime());

                         Driver driver = new Driver();

                         driver.setDriverRefNo(wrapperArrayObj.optJSONObject(0).getString("driverRefNo"));
                         driver.setDriverID(wrapperArrayObj.optJSONObject(0).getString("driverID"));
                         driver.setDriverMobileNo(wrapperArrayObj.optJSONObject(0).getString("mobileNo"));
                         driver.setDriverName(wrapperArrayObj.optJSONObject(0).getString("firstName"));
                         driver.setDriverVehicleNo(wrapperArrayObj.optJSONObject(0).getString("vehicleNo"));
                         driver.setStatus(wrapperArrayObj.optJSONObject(0).getString("status"));
                         driver.setDriverVehicleType(wrapperArrayObj.optJSONObject(0).getString("vehicleType"));
                         driver.setImageFound(false);
                         driver.setDatetime(formattedDate);
                         driver.setFcmToken(fcmToken);
                         driver.setLocale(wrapperArrayObj.optJSONObject(0).getString("locale"));

                         setDriverLogin(activity, context, driver, fcmToken);


                     }
                     else
                     {
                         return  false;
                     }
                }
                else
                {

                    Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                }




            } else {

                Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);


        }

        return true;

    }

    private void setDriverLogin(Activity activity, Context context,Driver driver, String fcmToken) {

        //Shared Preferences
        editor = sharedPreferences.edit();

        Log.d(GlobalConstants.CommonService, "SharedPreferences putString ");

        //locale = CommonService.getLocale(driver.getLocale()).toString();

        editor.putString("driverMobileNo", driver.getDriverMobileNo());
        //editor.putString("driverFirstName", driver.getDriverName());
        editor.putString("userGroup", GlobalConstants.DRIVER_CODE);
        editor.putString("driverRefNo", driver.getDriverRefNo());
        editor.putString("driverID", driver.getDriverID());
        editor.putString("vehicleNo", driver.getDriverVehicleNo());
        editor.putString("vehicleType", driver.getDriverVehicleType());
        editor.putString("autoLogin", GlobalConstants.YES_CODE);
        editor.putString("locale", CommonService.getLocale(driver.getLocale()).toString());
        //editor.putString("driverRadius", wrapperArrayObj.optJSONObject(0).getString("driverRadius"));

        editor.apply();

        setLocale(context,CommonService.getLocale(driver.getLocale()).toString());

        Log.d(GlobalConstants.CommonService, "Saved Info: " + sharedPreferences.getString("driverMobileNo", ""));

        Log.d(GlobalConstants.CommonService, "Before Change Activity ");
        if (driver.getStatus().equals(GlobalConstants.ACTIVE_CODE)) {

            Parameter parameter = new Parameter();

            Intent i = new Intent(context, DriverMapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Parameter", parameter);
            bundle.putSerializable("Driver", driver);
            bundle.putSerializable("locale", CommonService.getLocale(driver.getLocale()).toString());
            i.putExtras(bundle);
            context.startActivity(i);

            Log.d(GlobalConstants.CommonService, "Going to DriverMap ");
        } else {
            Toast(activity, GlobalConstants.ACCESS_RESTRICTED, Toast.LENGTH_SHORT);

        }


    }

    public void setLocale(Context context, String locale)
    {
        //---------Language setting-----
        Locale defaultLocale = new Locale(locale);
        Locale.setDefault(defaultLocale);

        /**
         * Print the current language
         */
        System.out.println("My current language: "
                + Locale.getDefault());

        Configuration config = new Configuration();
        config.locale = defaultLocale;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());

        //--------end of language setting

    }

    public static final void Toast(Activity activity, String message, int duration )
    {

        Toast.makeText(activity, message, duration).show();

    }



    //------------Camera function -----------
    public void openFrontCamera(Activity activity) {

        try {

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

            activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            // do we have a camera?
            if (!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                Toast(activity, "No camera on this device", Toast.LENGTH_LONG);
            } else {
                cameraId = findFrontFacingCamera();

                Log.d(TAG, "cameraId  " + cameraId);

                if (cameraId < 0) {
                    Toast(activity, "No front facing camera found.", Toast.LENGTH_LONG);
                } else {

                    Log.d(TAG, "Camera To Open");

                    mCamera = Camera.open(cameraId);

                }


            }


            //Parameters parameters = mCamera.getParameters();
            //parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            //mCamera.setParameters(parameters);

            mCamera.startPreview();
            mCamera.takePicture(null, null, new PhotoHandler(activity.getApplicationContext()));

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

    protected void onActivityResult(final Activity activity,Context context, int requestCode, int resultCode, Intent data, Rider rider, final ImageView imageView, final boolean avatar) {

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//            Log.d(TAG, "imageBitmap "+imageBitmap);
//
//            //mImageView.setImageBitmap(imageBitmap);
//        }

        try{

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

                //Log.d(TAG, "imageBitmap "+imageBitmap);
                // ImageView imageview = (ImageView) findViewById(R.id.ImageView01);
                //imageview.setImageBitmap(image);

                ByteArrayOutputStream bao = new ByteArrayOutputStream();

                //Bitmap circleBitmap=getCircleBitmap(imageBitmap);


                Bitmap circleBitmap=getCroppedBitmap(imageBitmap,imageBitmap.getWidth(), avatar);

                circleBitmap.compress(Bitmap.CompressFormat.PNG, 30, bao); // bmp is bitmap from user image file
                circleBitmap.recycle();

                byte[] byteArray = bao.toByteArray();
                String imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);


                if(firebaseStorage==null) {
                    firebaseStorage = FirebaseStorage.getInstance(GlobalConstants.FIREBASE_URL);
                }

                StorageReference storageRef = firebaseStorage.getReference();

                final StorageReference  avatarRef = storageRef.child(GlobalConstants.FB_IMAGE_FOLDER + rider.getImageName());

                //MySQL Filename update
                updateImage(activity,context,rider);

                UploadTask uploadTask = avatarRef.putBytes(byteArray);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(activity, "Image upload failed", Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        Toast.makeText(activity, "Image uploaded", Toast.LENGTH_SHORT).show();



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

                            setImage(downloadUri, imageView);

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
                //                //05-Oct-2018
                //                dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH +"/"+rider.getRiderID());
                //                Log.d(TAG, "imageBitmap-1");
                //                Map<String, Object> riderImage = new HashMap<>();
                //                riderImage.put("imageFound",true);
                //                riderImage.put("riderImage",imageB64);
                //
                //                Log.d(TAG, "imageBitmap-2");
                //                dbRef.updateChildren(riderImage);



                //--setting profile image immediately
                //setRiderImage(imageB64);

                Log.d(TAG, "imageBitmap-3");
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }


    public void getImage(final ImageView imageView, String imageFileName)
    {

        try {

            if (imageFileName != null && !imageFileName.trim().equals("")) {
                if (firebaseStorage == null) {
                    firebaseStorage = FirebaseStorage.getInstance(GlobalConstants.FIREBASE_URL);
                }

                StorageReference storageRef = firebaseStorage.getReference();

                //Log.d(TAG, "getImage imageFileName  " + imageFileName);

                final StorageReference avatarRef = storageRef.child(GlobalConstants.FB_IMAGE_FOLDER + imageFileName);

                avatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'

                        //Log.d(TAG, "onSuccess  " + uri);

                        setImage(uri, imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        int errorCode = ((StorageException) exception).getErrorCode();
                        String errorMessage = exception.getMessage();
                        Log.d(TAG, "onFailure  no image found " + errorMessage);
                        //Toast.makeText(RiderMapActivity.this, "Image download failed", Toast.LENGTH_SHORT).show();
                        //imageView.setImageResource(R.drawable.avatar_24dp);

                    }
                });
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d(TAG, "getImage Exception");

        }
    }

    public void setImage(Uri uri, ImageView imageView){


        try {

            Picasso.get().load(uri).into(imageView); //http://square.github.io/picasso/
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

//            // Receiving side
//            byte[] data = Base64.decode(riderImageData, Base64.DEFAULT);
//
//            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//            Bitmap bitmap = Bitmap.createScaledBitmap(bmp,120, 120, true);
//
//            riderImage.setImageBitmap(bitmap);


    }

    public void getMarkerImage(final ImageView imageView, String imageFileName, final Marker marker)
    {

        try {

            if (imageFileName != null && !imageFileName.trim().equals("")) {
                if (firebaseStorage == null) {
                    firebaseStorage = FirebaseStorage.getInstance(GlobalConstants.FIREBASE_URL);
                }

                StorageReference storageRef = firebaseStorage.getReference();

                final StorageReference avatarRef = storageRef.child(GlobalConstants.FB_IMAGE_FOLDER + imageFileName);

                avatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'

                        if(imageView!=null) {
                            //Log.d(TAG, "onSuccess  " + uri);

                            Picasso.get()
                                    .load(uri)
                                    .placeholder(R.drawable.avatar_outline48)
                                    .into(imageView, new MarkerCallback(marker));
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        int errorCode = ((StorageException) exception).getErrorCode();
                        String errorMessage = exception.getMessage();
                        Log.d(TAG, "onFailure  no image found " + errorMessage);
                        //Toast.makeText(RiderMapActivity.this, "Image download failed", Toast.LENGTH_SHORT).show();
                        //imageView.setImageResource(R.drawable.avatar_24dp);

                    }
                });
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d(TAG, "getImage Exception");

        }

    }

    public class MarkerCallback implements Callback {
        Marker marker=null;

        MarkerCallback(Marker marker) {
            this.marker=marker;

        }

        @Override
        public void onError(Exception ex) {
            Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
        }

        @Override
        public void onSuccess() {

            //Log.d(TAG, "onSuccess Marker1 " +   marker.isInfoWindowShown());

            if (marker != null && marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
                marker.showInfoWindow();
            }
        }
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());


        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        Bitmap _bmp = Bitmap.createScaledBitmap(output, 120, 120, false);

        return _bmp;
        //return output;
    }


    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius, boolean avatar) {
        Bitmap sbmp;

        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp,
                    (int) (bmp.getWidth() / factor),
                    (int) (bmp.getHeight() / factor), false);
        } else {
            sbmp = bmp;
        }

        Bitmap output=null;
        if(avatar) {

             output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);

        }
        else
        {
            output = Bitmap.createBitmap(radius*2, radius, Bitmap.Config.ARGB_8888);

        }

        Canvas canvas = new Canvas(output);

        final String color = "#BAB399";
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);
        final Rect rect2 = new Rect(0, 0, radius*2, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor(color));
//        canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f,
//                radius / 2 + 0.1f, paint);
        if(avatar) {
            canvas.drawCircle(radius / 2 + 1.2f, radius / 2 + 1.2f,
                    radius / 2 + 0.6f, paint);
        }
        else {
            canvas.drawRoundRect(new RectF(0, 0, radius*2, radius), 50, 50, paint);

        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        if(avatar) {
            canvas.drawBitmap(sbmp, rect, rect, paint);
        }
        else
        {
            canvas.drawBitmap(sbmp, rect, rect2, paint);
        }

        return output;
    }

    public static Bitmap compressImage(Bitmap bmp) {

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        //Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
//            exif = new ExifInterface(filePath);
//
//            int orientation = exif.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION, 0);
//            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
//            if (orientation == 6) {
//                matrix.postRotate(90);
//                Log.d("EXIF", "Exif: " + orientation);
//            } else if (orientation == 3) {
//                matrix.postRotate(180);
//                Log.d("EXIF", "Exif: " + orientation);
//            } else if (orientation == 8) {
//                matrix.postRotate(270);
//                Log.d("EXIF", "Exif: " + orientation);
//            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        FileOutputStream out = null;
//        String filename = getFilename();
//        try {
//            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
        //scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        return scaledBitmap;

    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public boolean updateImage(Activity activity, Context context, Rider rider) {

        String responseData = null;
        String fcmToken = null;
        String locale = null;

        try {


            //sharedPreferences initiated
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


            fcmToken = sharedPreferences.getString("fcmToken","");
            locale = getLocale(sharedPreferences.getString("locale", "")).toString();

            //Shared Preferences
            editor = sharedPreferences.edit();

            editor.putString("userid", rider.getRiderID());
            editor.putString("deviceToken", fcmToken);
            editor.putString("sessionid", "SESSIONID");

            editor.apply();


            //loadingSpinner.setVisibility(View.VISIBLE);


            String methodAction = "updateImage";

            JSONObject messageJson = new JSONObject();
            messageJson.put("riderRefNo", rider.getRiderRefNo());
            messageJson.put("riderID", rider.getRiderID());
            messageJson.put("imageUploadStatus", true);
            messageJson.put("imageName", rider.getImageName());
            messageJson.put("imageID", rider.getImageID());
            messageJson.put("imageFolder", GlobalConstants.FB_IMAGE_FOLDER);
            messageJson.put("status", GlobalConstants.ACTIVE_CODE);




            ConnectHost connectHost = new ConnectHost();

            responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

            //loadingSpinner.setVisibility(View.GONE);

            Log.d(GlobalConstants.CommonService, "updateImage responseData: " + responseData);


            if (responseData != null) {


                // Convert String to json object
                JSONObject jsonResponseData = new JSONObject(responseData);

                // get LL json object
                JSONObject jsonResult = jsonResponseData.optJSONObject("updateImage");

                //Log.d(TAG, "resData insertRider: " + jsonResult);
                //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                JSONArray wrapperArrayObj = jsonResult.getJSONArray("imageWrapper");

                //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                if (jsonResponseData.getString("success") == "true"){

                    if(wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                        //Toast(activity, "Image uploaded", Toast.LENGTH_SHORT);


                    }
                    else
                    {




                    }
                }
                else
                {

                    Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                }




            } else {

                Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);


        }

        return true;

    }
    //-------------end of camera function


    public void updateRider(final Listener<Boolean> onCompleteListener, final Activity activity, final Context context, final Rider rider)
    {

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                String responseData = null;
                String fcmToken=null;
//                String locale=null;

                try {

                    //sharedPreferences initiated
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


                    fcmToken = sharedPreferences.getString("fcmToken", "");
//                    locale = getLocale(sharedPreferences.getString("locale", "")).toString();


                    Log.d(GlobalConstants.CommonService, "fcm token riderautologin " + fcmToken);


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "updateRider";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("firstName", rider.getRiderName());
                    messageJson.put("locale", rider.getLocale());
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());
                    //messageJson.put("vehicleNo", rider.getVehicleNo());
                    //messageJson.put("vehicleType", rider.getVehicleType());
                    messageJson.put("service", GlobalConstants.TRANSPORT_BUSINESS);


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.optJSONObject("updateRider");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true") {


                            if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                                //Rider rider = new Rider();

                                rider.setRiderRefNo(wrapperArrayObj.optJSONObject(0).optString("riderRefNo"));
                                rider.setRiderID(wrapperArrayObj.optJSONObject(0).optString("riderID"));
                                //rider.setRiderMobileNo(wrapperArrayObj.optJSONObject(0).optString("mobileNo"));
                                rider.setRiderName(wrapperArrayObj.optJSONObject(0).optString("firstName"));
                                rider.setLocale(wrapperArrayObj.optJSONObject(0).optString("locale"));

                                //rider.setVehicleNo(wrapperArrayObj.optJSONObject(0).getString("vehicleNo"));
                                //rider.setStatus(wrapperArrayObj.optJSONObject(0).getString("status"));
                                //rider.setVehicleType(wrapperArrayObj.optJSONObject(0).getString("vehicleType"));


                                setRider(activity,context,rider);



                                onCompleteListener.on(true);
                            } else {

                                onCompleteListener.on(false);

                            }

                        } else {

                            Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                            onCompleteListener.on(false);

                        }


                    } else {

                        Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);
                        onCompleteListener.on(false);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    onCompleteListener.on(false);

                }
            } //run end


        });


    }

    public void setRider(Activity activity,Context context,Rider rider) {


        //Shared Preferences
        editor = sharedPreferences.edit();

        Log.d(TAG, "SharedPreferences putString ");

        editor.putString("riderFirstName", rider.getRiderName());
        editor.putString("locale", rider.getLocale());


        editor.apply();

        setLocale(context,CommonService.getLocale(rider.getLocale()).toString());

        Toast.makeText(activity, R.string.update_success, Toast.LENGTH_SHORT).show();


    }

    public void updateDriver(final Listener<Boolean> onCompleteListener, final Activity activity, final Context context, final Rider rider)
    {

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                String responseData = null;
                String fcmToken=null;
//                String locale=null;

                try {

                    //sharedPreferences initiated
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


                    fcmToken = sharedPreferences.getString("fcmToken", "");
//                    locale = getLocale(sharedPreferences.getString("locale", "")).toString();


                    Log.d(GlobalConstants.CommonService, "fcm token riderautologin " + fcmToken);


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "updateDriver";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());
                    messageJson.put("vehicleNo", rider.getVehicleNo());
                    messageJson.put("vehicleType", rider.getVehicleType());


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "Driver update responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.optJSONObject("updateDriver");

                        //Log.d(TAG, "resData insertRider: " + jsonResult);
                        //Log.d(TAG, "resData success: " + jsonResponseData.getString("success"));

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

                        //Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
                        //Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true") {


                            if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                                Rider rider = new Rider();

                                rider.setRiderRefNo(wrapperArrayObj.optJSONObject(0).getString("riderRefNo"));
                                rider.setRiderID(wrapperArrayObj.optJSONObject(0).getString("riderID"));
                                //rider.setRiderMobileNo(wrapperArrayObj.optJSONObject(0).getString("mobileNo"));
                                //rider.setRiderName(wrapperArrayObj.optJSONObject(0).optString("firstName"));
                                //rider.setLocale(wrapperArrayObj.optJSONObject(0).optString("locale"));

                                rider.setVehicleNo(wrapperArrayObj.optJSONObject(0).getString("vehicleNo"));
                                //rider.setStatus(wrapperArrayObj.optJSONObject(0).getString("status"));
                                rider.setVehicleType(wrapperArrayObj.optJSONObject(0).getString("vehicleType"));


                                setRider(activity,context,rider);



                                onCompleteListener.on(true);
                            } else {

                                onCompleteListener.on(false);

                            }

                        } else {

                            Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                            onCompleteListener.on(false);

                        }


                    } else {

                        Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);
                        onCompleteListener.on(false);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    onCompleteListener.on(false);

                }
            } //run end


        });


    }

    public void fetchRider(final Listener<Boolean> onCompleteListener, final Activity activity, final Context context, final Rider rider)
    {

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                String responseData = null;
                String fcmToken=null;
//                String locale=null;

                try {

                    //sharedPreferences initiated
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


                    fcmToken = sharedPreferences.getString("fcmToken", "");
//                    locale = getLocale(sharedPreferences.getString("locale", "")).toString();


                    Log.d(GlobalConstants.CommonService, "fcm token riderautologin " + fcmToken);


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "fetchRider";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());
                    messageJson.put("status", rider.getStatus());
                    messageJson.put("imageID", GlobalConstants.IMAGE_AVATAR);


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "RiderLogin responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchRider");
                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");
                        if (jsonResponseData.getString("success") == "true") {


                            if (wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                                //rider = new Rider();
                                rider.setMobileNo(wrapperArrayObj.optJSONObject(0).optString("mobileNo"));
                                rider.setRiderName(wrapperArrayObj.optJSONObject(0).optString("firstName"));
                                rider.setLocale(wrapperArrayObj.optJSONObject(0).optString("locale"));
                                rider.setVehicleNo(wrapperArrayObj.optJSONObject(0).optString("vehicleNo"));
                                rider.setVehicleType(wrapperArrayObj.optJSONObject(0).optString("vehicleType"));
                                rider.setStatus(wrapperArrayObj.optJSONObject(0).optString("status"));
                                rider.setVacantStatus(wrapperArrayObj.optJSONObject(0).optString("vacantStatus"));

                                //Log.d(TAG, "setVacantStatus: " + rider.getVacantStatus());

                                JSONArray imageWrappers = wrapperArrayObj.optJSONObject(0).optJSONArray("imageWrappers");

                                if(imageWrappers!=null)
                                {

                                    rider.imageWrappers = new Image[imageWrappers.length()];
                                    Image image=null;

                                    for(int i=0;i<imageWrappers.length();i++)
                                    {
                                        if(imageWrappers.optJSONObject(i).optString("recordFound")=="true")
                                        {
                                            image = new Image();

                                            image.setRiderRefNo(imageWrappers.optJSONObject(i).optString("riderRefNo"));
                                            image.setRiderID(imageWrappers.optJSONObject(i).optString("riderID"));
                                            image.setImageID(imageWrappers.optJSONObject(i).optString("imageID"));
                                            image.setImageName(imageWrappers.optJSONObject(i).optString("imageName"));
                                            image.setImageFolder(imageWrappers.optJSONObject(i).optString("imageFolder"));
                                            image.setStatus(imageWrappers.optJSONObject(i).optString("status"));
                                            rider.imageWrappers[i] = image;

                                        }
                                    }

                                }

                                onCompleteListener.on(true);
                            } else {

                                onCompleteListener.on(false);

                            }

                        } else {

                            Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                            onCompleteListener.on(false);

                        }


                    } else {

                        Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);
                        onCompleteListener.on(false);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    Toast(activity, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    onCompleteListener.on(false);

                }
            } //run end


        });


    }


    public static  String getImageName(Rider rider, String imageID)
    {
        String imageName=null;

        if(rider!=null && rider.imageWrappers !=null && rider.imageWrappers.length>0)
        {

            for(int i=0; i<rider.imageWrappers.length;i++)
            {
                if(rider.imageWrappers[i].imageID.equals(imageID))
                {
                    imageName = rider.imageWrappers[i].imageName;
                    break;
                }

            }

        }

        return imageName;
    }


    public static MediaPlayer mediaPlayer(Context context)
    {
        MediaPlayer mediaPlayer=null;
        try {

            if(mediaPlayer==null) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                //ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                mediaPlayer = MediaPlayer.create(context, notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaPlayer;
    }


    public static String getDeviceInfo()
    {
        return   "MANUFACTURER:" + Build.MANUFACTURER + " MODEL:"+Build.MODEL +" SDK_INT:" + Build.VERSION.SDK_INT
                + " RELEASE:" + Build.VERSION.RELEASE + " INCREMENTAL:" + Build.VERSION.INCREMENTAL
                + " BOARD:" + Build.BOARD + " BRAND:" + Build.BRAND + " DEVICE:" + Build.DEVICE + " ID:" + Build.ID;

    }



}