package com.rootmind.nowcabs;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.DialogInterface;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;


//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.common.GoogleApiAvailability;

import  android.location.*;


//import com.google.android.gms.maps.i;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.*;

import android.widget.Button;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import android.app.AlertDialog;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


import  android.hardware.Camera.CameraInfo;

import android.hardware.Camera;

//import android.hardware.camera2.*;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import android.util.Base64;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;

import android.widget.ImageView;

/**
 * Created by rootmindtechsoftprivatelimited on 19/06/17.
 */

public class DriverMapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{



    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker mDestinationMarker;

    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;

//    //05-Oct-2018 --Firebase suppress
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference dbRef;


    private ValueEventListener valueEventListener;


    private static final String TAG = "DriverMapActivity";

    Driver driver=null;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    double lastDriverLat=0.0;
    double lastDriverLng=0.0;

    double driverLat=0.0;
    double driverLng=0.0;

    String driverLocation=null;
    Geocoder geocoder;
    List<Address> addresses;

    Button btn_vacant;
    Button btn_hired;

    //Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    PlaceAutocompleteFragment autocompleteFragment;

    int mapZoom=0;


    private Camera mCamera;
    private int cameraId = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    String destination=null;

    private ChildEventListener mChildEventListener;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE=1;

    public Toolbar toolbar;
    public DrawerLayout drawer;

    ImageView driverImage;

    Parameter parameter;

    String vacantStatus=null;
    String responseData = null;

    Configuration config;
    Locale locale;

    FirebaseStorage firebaseStorage;


    // Create a LatLngBounds that includes the state of andhra .
//    private LatLngBounds andhraPradesh = new LatLngBounds(
//            new LatLng(-35.0, 15.9129), new LatLng(-34.9, 79.7400));

//


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d(TAG, "Locale in DriverMap1 : "+sharedPreferences.getString("locale", "") );
        locale=CommonService.getLocale(sharedPreferences.getString("locale", ""));
        Log.d(TAG, "Locale in DriverMap2 : "+locale );
        setLocale();



        setContentView(R.layout.activity_driver_map);

        //android.os.NetworkOnMainThreadException
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

        Context context = this; // or ActivityNotification.this
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


//        24-Sep-2018
//        //navigation bar
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();




        // Tabbar icon
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.nowcabs_icon48x48);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(30,144,255))); //Twitter Color



//        //05-Oct-2018 --Firebase suppress
//        //To initiate firebase
//        firebaseDatabase = FirebaseDatabase.getInstance();

        // location permission for map to load
       // checkLocationPermission();

        //initialize autocomplete to specific area
        //initMapBoundaries();

        firebaseStorage = FirebaseStorage.getInstance("gs://nowcabs.appspot.com");


        btn_vacant = (Button) findViewById(R.id.btn_vacant);
        btn_hired = (Button) findViewById(R.id.btn_hired);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(15.0f);

        autocompleteFragment.setHint("Enter Destination");


        //-----------side nav image
        ImageView searchIcon = (ImageView)((LinearLayout)autocompleteFragment.getView()).getChildAt(0);

        // Set the desired icon
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.sidenav));

        // Set the desired behaviour on click
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapsActivity.this, "YOUR DESIRED BEHAVIOUR HERE", Toast.LENGTH_SHORT).show();
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //-----------end of side nav


        //-----------directions image
        LinearLayout directionLayout = ((LinearLayout)autocompleteFragment.getView());
        RelativeLayout.LayoutParams buttonParam = new RelativeLayout.LayoutParams(
                90,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Button btnDirection = new Button(this);
        btnDirection.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.directions,0);
        btnDirection.setLayoutParams(buttonParam);
        btnDirection.setGravity(Gravity.CENTER_HORIZONTAL);
        btnDirection.setPadding(0,0,10,0);
        btnDirection.setBackgroundColor(Color.TRANSPARENT);
        directionLayout.addView(btnDirection);

        // Set the desired behaviour on click
        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapsActivity.this, "YOUR DESIRED BEHAVIOUR HERE", Toast.LENGTH_SHORT).show();
                getGoogleDirections();
            }
        });
        //-----------end of side nav



        // initalize data
        loadData();


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.


                Log.i(TAG, "autocomplete Place: " + place.getName());

                //29-Sep-2018
                destination = place.getName().toString();

                //28-Sep-2018
                //updateDestination(place);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        // Build the Play services client for use by the Fused Location Provider and the Places API.
        // Use the addApi() method to request the Google Places API and the Fused Location Provider.
        buildGoogleApiClient();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(5000);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(1000);



        btn_vacant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateVacantStatus(GlobalConstants.VACANT_CODE);

            }
        });

        btn_hired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updateVacantStatus(GlobalConstants.HIRED_CODE);


                getGoogleDirections();

            }
        });


        //Shared Preferences for SideNav
        editor = sharedPreferences.edit();
        editor.putString("driverFirstName", driver.getDriverName());
        editor.apply();

        //sidenav header
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        driverImage = (ImageView) headerView.findViewById(R.id.iv_userimage);
        TextView driverName = (TextView) headerView.findViewById(R.id.tv_name);
        TextView driverID = (TextView) headerView.findViewById(R.id.tv_id);
        //drawerImage.setImageDrawable(R.drawable.ic_menu_camera);
        driverImage.setImageResource(R.drawable.avatar_24dp);


        driverName.setText(sharedPreferences.getString("driverFirstName", "")); //to get latest name
        driverID.setText(driver.driverID);

        getDriverImage();


//        if (driver.getImageFound() == true) {
//
//
//            getDriverImage();
//            //setDriverImage(driver.getDriverImage());
//
////            // Receiving side
////            byte[] data = Base64.decode(driver.getDriverImage(), Base64.DEFAULT);
////
////            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
////
////            driverImage.setImageBitmap(bmp);
//
//
//        } else {
//
//            driverImage.setImageResource(R.drawable.avatar_24dp);
//
//        }



        driverImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openFrontCamera();

            }
        });


        //--navigation header onclick
        View headerview = navigationView.getHeaderView(0);

        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.nav_header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DriverMapActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), DriverProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Driver", driver);
                i.putExtras(bundle);
                startActivity(i);


                drawer.closeDrawer(GravityCompat.START);
            }
        });



        //to bring locationbutton to bottom right----MyLocationEnabled Button
        View locationButton = ((View) this.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 30);
        //-----end setMyLocationEnabled----

    }



    @Override
    public void onPause() {

        //camera
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }

        super.onPause();

        //stop location updates when Activity is no longer active
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//
//
//                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//
//        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //to move google logo and current location logo
        mMap.setPadding(0,0,0,200);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            //buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }



    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mGoogleApiClient.connect();
    }



    @Override
    public void onConnected(Bundle bundle) {


        //mLocationRequest.setSmallestDisplacement(0.1F); //added
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //changed
        //mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if(mGoogleApiClient !=null && mGoogleApiClient.isConnected()) {

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }

        //get last known location
        if(mGoogleApiClient !=null) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {

                lastDriverLat=driverLat;
                lastDriverLng=driverLng;

                driverLat = mLastLocation.getLatitude();
                driverLng = mLastLocation.getLongitude();
                mCurrentLocation=mLastLocation;

                LatLng latLng = new LatLng(driverLat, driverLng);
                Log.d(TAG, "GoogleAPI Zoom");
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,mapZoom));
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


        if (mCurrLocationMarker != null) {
            //mCurrLocationMarker.remove();
            mCurrLocationMarker.setPosition(latLng);
        }
        else{


            //Place current location marker
            //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mCurrLocationMarker = mMap.addMarker(markerOptions);

        }


        Log.d(TAG, "onLocationChanged Zoom");
        //move map camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,mapZoom));

        //stop location updates
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }




        try {
            geocoder = new Geocoder(this, Locale.getDefault());   //Locale.ENGLISH
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address returnAddress = addresses.get(0);

            //String localityString = returnAddress.getLocality();

            driverLocation = returnAddress.getLocality();

            lastDriverLat=driverLat;
            lastDriverLng=driverLng;

            driverLat = location.getLatitude();
            driverLng = location.getLongitude();

            driverVacantStatus(vacantStatus);

            //updateDriverLocation();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d(TAG, "onLocationChanged Exception");
            Log.e("tag", e.getMessage());
            e.printStackTrace();
        }






    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//
//    public boolean checkLocationPermission(){
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Asking user if explanation is needed
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//                //Prompt the user once explanation has been shown
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//
//                new AlertDialog.Builder(this)
//                        .setTitle("Location Permission Needed")
//                        .setMessage("This app needs the Location permission, please accept to use location functionality")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(DriverMapActivity.this,
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION );
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // Permission was granted.
//                    if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//                        mMap.setMyLocationEnabled(true);
//                    }
//
//                } else {
//
//                    // Permission denied, Disable the functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other permissions this app might request.
//            //You can add here other case statements according to your requirement.
//        }
//    }

    public void loadData( ){


        parameter = (Parameter) getIntent().getSerializableExtra("Parameter");
        driver = (Driver) getIntent().getSerializableExtra("Driver");

        Log.d(TAG, "DriverMap driver: "+driver.getDriverID() );

        setParameters();

        driver.setVacantStatus(GlobalConstants.VACANT_CODE);

        if(driver.getDestination()==null)
            driver.setDestination(GlobalConstants.ANY_CODE);

       //initDriverStatus();
        updateVacantStatus(GlobalConstants.VACANT_CODE);
        vacantStatus = GlobalConstants.VACANT_CODE;


//        23-Sep-2018
        //readRiderSMS();

    }


//    public void initDriverStatus(){
//
//
//
//
//        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH+"/"+ this.driverData.driverID);
//
//        Log.d(TAG, "initDriverStatus" );
//
//        valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                //Post post = dataSnapshot.getValue(Post.class);
//                // ...
//                Log.d(TAG, "DataSnapshot:" + dataSnapshot);
//
//                Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//                if(dataSnapshot.getValue()==null){
//
//                    Log.d(TAG, "Insert new driver: " +driverData.driverID);
//
//
//                       String key = dbRef.child(driverData.driverID).push().getKey();
//
//                        Map<String, Object> driverMap = driverData.toMap();
//
//
//                        dbRef.setValue(driverMap);
//
//                }
//                else
//                {
//
//                    Log.d(TAG, "Udate Driver:" );
//
//                    Map<String, Object> driverMap = driverData.toMap();
//                    dbRef.updateChildren(driverMap);
//
//
//                }
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//        dbRef.addListenerForSingleValueEvent(valueEventListener);
//
//
//
//
//    }



//    //05-Oct-2018 --Firebase suppress
//    public void updateDriverLocation(){
//
//
//
//        Map<String, Object> driverLocationMap = new HashMap<String, Object>();
//        driverLocationMap.put("lastDriverLat",lastDriverLat);
//        driverLocationMap.put("lastDriverLng",lastDriverLng);
//        driverLocationMap.put("driverLat",driverLat);
//        driverLocationMap.put("driverLng",driverLng);
//        driverLocationMap.put("driverLocation",driverLocation);
//        firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH+"/"+ driver.driverID).updateChildren(driverLocationMap);
//
//
//
//
//
////        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH+"/"+ this.driverData.driverID);
////
////        Log.d(TAG, " updateDriverLocation " );
////
////        valueEventListener = new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                // Get Post object and use the values to update the UI
////
////               // Log.d(TAG, "DataSnapshot:" + dataSnapshot);
////
////               // Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
////                //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
////               // driverLat: driverLat, driverLng:driverLng, driverLocation:$scope.driverLocation
////
////                if(dataSnapshot.getValue() != null){
////
////                    Log.d(TAG, "updateDriverLocation in if:" );
////
////                    driverData.driverLat=driverLat;
////                    driverData.driverLng=driverLng;
////                    driverData.driverLocation=driverLocation;
////                    Map<String, Object> driverMap = driverData.toMap();
////                    firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH+"/"+ driverData.driverID).updateChildren(driverMap);
////
////                }
////
////
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////                // Getting Post failed, log a message
////                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
////                // ...
////            }
////        };
////        dbRef.addValueEventListener(valueEventListener);
//
//
//
//    }


//    //05-Oct-2018 --Firebase suppress
//    public void updateDestination(Place place){
//
//        destination=(String)place.getName();
//
//
//        Log.d(TAG,"destination " + driver.driverID + " - " + destination);
//
//        Map<String, Object> destinationMap = new HashMap<String, Object>();
//        destinationMap.put("destination",destination);
//        firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH+"/"+ driver.driverID).updateChildren(destinationMap);
//
//
//        //Place current location marker
//        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        //Remove existing destination marker
//        if(mDestinationMarker !=null)
//            mDestinationMarker.remove();
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(place.getLatLng());
//        markerOptions.title(destination);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        mDestinationMarker=mMap.addMarker(markerOptions);
//
//
//        //update Log
//        NowcabsLog nowcabsLog=new NowcabsLog();
//        nowcabsLog.updateLog(GlobalConstants.DRIVER_CODE,driver.driverID,"Destination changed to "+driver.destination);
//
//
//
////        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
////        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
////        context.startActivity(intent);
//
//
////        // Assign your origin and destination----Not used
////        // These points are your markers coordinates
////        LatLng origin = new LatLng(driverLat, driverLng);
////        LatLng dest = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
////
////        // Getting URL to the Google Directions API
////        String url = getDirectionsUrl(origin, dest);
////
////        DownloadTask downloadTask = new DownloadTask();
////
////        // Start downloading json data from Google Directions API
////        downloadTask.execute(url);
////      -------------Not used ----end
//
//
//
//    }


    public void updateVacantStatus(String vacantStatus){

        btn_vacant.setTextColor(Color.WHITE);
        btn_hired.setTextColor(Color.WHITE);

        if(vacantStatus==GlobalConstants.VACANT_CODE)
        {
            btn_vacant.setEnabled(false);
            btn_hired.setEnabled(true);

            btn_vacant.setBackgroundColor(Color.rgb(34,139,34)); //FOREST GREEN
            btn_hired.setBackgroundColor(Color.GRAY);

        }
        else if (vacantStatus==GlobalConstants.HIRED_CODE)
        {

            btn_vacant.setEnabled(true);
            btn_hired.setEnabled(false);

            btn_vacant.setBackgroundColor(Color.GRAY);
            btn_vacant.setTextColor(Color.BLACK);

            btn_hired.setBackgroundColor(Color.rgb(255,140,0)); //DARK ORANGE
            btn_hired.setTextColor(Color.BLACK);

//13-Sep-2018
//            //---------To open Google Maps app-------
//            Uri gmmIntentUri = Uri.parse("google.navigation:q="+ Uri.encode(destination)+"&mode=d&avoid=tf");
//            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//            mapIntent.setPackage("com.google.android.apps.maps");
//            if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                startActivity(mapIntent);
//            }
//            //---------------


        }


        //28-Sep-2018
        driverVacantStatus(vacantStatus);

        //28-Sep-2018
//        Map<String, Object> vacantStatusMap = new HashMap<String, Object>();
//        vacantStatusMap.put("vacantStatus",vacantStatus);
//        firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH+"/"+ driver.driverID).updateChildren(vacantStatusMap);


    }




    //---------rightside dotted menu hide it
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getMenuInflater().inflate(R.menu.menu_rider_map, menu);
//
//
//        menu.add(Menu.NONE,R.id.menu_mobileNo,0,driver.getDriverMobileNo());//.setIcon(R.drawable.avatar_24dp);//.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        menu.add(Menu.NONE,R.id.menu_name,1,driver.getDriverName());
//        menu.add(Menu.NONE,R.id.menu_driverID,2,driver.getDriverID());
//        menu.add(Menu.NONE,R.id.menu_vehicleNo,3,driver.getDriverVehicleNo());
//        menu.add(Menu.NONE,R.id.menu_vehicleType,4,driver.getDriverVehicleType());
//
//        menu.add(Menu.NONE,R.id.menu_camera,5,"Camera");
//        menu.add(Menu.NONE,R.id.menu_logout,6,"Logout");
//
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.menu_morevert) {
//            //Toast.makeText(DriverMapActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        if (id == R.id.menu_logout) {
//            //Toast.makeText(RiderMapActivity.this, "Action Logout", Toast.LENGTH_LONG).show();
//
//            //To clear SharedPreferences
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            //editor.clear();
//            editor.putString("autoLoginKey", GlobalConstants.NO_CODE);
//            editor.commit();
//
//
//            //update Log
//            NowcabsLog nowcabsLog=new NowcabsLog();
//            nowcabsLog.updateLog(GlobalConstants.DRIVER_CODE,driver.driverID,"Logout");
//
//
//            Intent intent = new Intent(DriverMapActivity.this, DriverLoginActivity.class);
//            startActivity(intent);
//            finish();
//
//            return true;
//        }
//
//        if (id == R.id.menu_camera) {
//            //Toast.makeText(DriverMapActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//
//            openFrontCamera();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if(mGoogleApiClient !=null && mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
    }


//    public void initMapBoundaries()
//    {
//
//
//        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_PARAM_PATH);
//
//        Log.d(TAG, "initMapBoundaries" );
//
//        valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                //Post post = dataSnapshot.getValue(Post.class);
//                // ...
//                //Log.d(TAG, "initMapBoundaries:" + dataSnapshot);
//
//                //Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//
//                try
//                {
////                    String parameter=dataSnapshot.getValue().toString();
////                    // Convert String to json object
////                    JSONObject json = new JSONObject(parameter);
////
////                    // get value from jsonParameter Json Object
////                    String country=json.getString("country");
////                    Double radiusLng=Double.parseDouble(json.getString("radiusLng"));
////                    Double radiusLat=Double.parseDouble(json.getString("radiusLat"));
////                    mapZoom = Integer.parseInt(json.getString("mapZoom"));
//
//
//                    Parameter parameter = dataSnapshot.getValue(Parameter.class);
//                    String country=parameter.getCountry();
//                    mapZoom = parameter.getMapZoom();
//
//                    Log.d(TAG, "country :" + country);
//
//
//                    if(country!=null && country.trim().length()>=2) {
//                        // Create Filter
//                        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                                .setCountry(country)
//                                .build();
//                        // Attach to autocomplete fragment / widget
//                        autocompleteFragment.setFilter(typeFilter);
//
//                        //                    autocompleteFragment.setBoundsBias(new LatLngBounds(
////                            new LatLng(16, radiusLng),
////                            new LatLng(80, radiusLat)));
//
//                    }
//
//                }
//                catch (Exception ex){
//                    Log.d(TAG, "initMapBoundaries Exception");
//                    ex.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//        dbRef.addListenerForSingleValueEvent(valueEventListener);
//        dbRef.removeEventListener(valueEventListener);
//
//
//
//    }

//    //05-Oct-2018 --Firebase suppress
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//
//        if (valueEventListener != null) {
//            dbRef.removeEventListener(valueEventListener);
//        }
//        if (mChildEventListener != null) {
//            dbRef.removeEventListener(mChildEventListener);
//        }
//
//
//
//
//
//    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            moveTaskToBack(true);
            return true;
        }

        return false;

    }


//------------This code is for polyline draw for destination
//    private String getDirectionsUrl(LatLng origin,LatLng dest){
//
//
//        // Origin of route
//        String str_origin = "origin="+origin.latitude+","+origin.longitude;
//
//        // Destination of route
//        String str_dest = "destination="+dest.latitude+","+dest.longitude;
//
//        // Sensor enabled
//        String sensor = "sensor=false";
//
//        // Building the parameters to the web service
//        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&key="+GlobalConstants.GOOGLE_MAPS_KEY;
//
//        // Output format
//        String output = "json";
//
//        // Building the url to the web service
//        String url = GlobalConstants.GOOGLE_MAPS_DIRECTIONS_URL+output+"?"+parameters;
//
//        Log.d(TAG,"directions url " + url);
//
//        return url;
//    }
//
//    /** A method to download json data from url */
//    private String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try{
//            URL url = new URL(strUrl);
//
//            // Creating an http connection to communicate with url
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            // Connecting to url
//            urlConnection.connect();
//
//            // Reading data from url
//            iStream = urlConnection.getInputStream();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while( ( line = br.readLine()) != null){
//                sb.append(line);
//            }
//
//            data = sb.toString();
//
//            br.close();
//
//        }catch(Exception e){
//
//            Log.d(TAG, e.toString());
//
//        }finally{
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }
//
//    // Fetches data from url passed
//    private class DownloadTask extends AsyncTask<String, Void, String>{
//
//        // Downloading data in non-ui thread
//        @Override
//        protected String doInBackground(String... url) {
//
//            // For storing data from web service
//            String data = "";
//
//            try{
//                // Fetching the data from web service
//                data = downloadUrl(url[0]);
//            }catch(Exception e){
//                Log.d("Background Task",e.toString());
//            }
//            return data;
//        }
//
//        // Executes in UI thread, after the execution of
//        // doInBackground()
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            ParserTask parserTask = new ParserTask();
//
//            // Invokes the thread for parsing the JSON data
//            parserTask.execute(result);
//        }
//    }
//
//    /** A class to parse the Google Places in JSON format */
//    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {
//
//        // Parsing the data in non-ui thread
//        @Override
//        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
//
//            JSONObject jObject;
//            List<List<HashMap<String, String>>> routes = null;
//
//            try{
//
//                Log.d(TAG,"in ParserTask " + jsonData[0]);
//
//                jObject = new JSONObject(jsonData[0]);
//                DirectionsJSONParser parser = new DirectionsJSONParser();
//
//                // Starts parsing data
//                routes = parser.parse(jObject);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            return routes;
//        }
//
//        // Executes in UI thread, after the parsing process
//        @Override
//        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//            ArrayList<LatLng> points = null;
//            PolylineOptions lineOptions = null;
//            MarkerOptions markerOptions = new MarkerOptions();
//            Polyline polyline=null;
//
//            // Traversing through all the routes
//            for(int i=0;i<result.size();i++){
//                points = new ArrayList<LatLng>();
//                lineOptions = new PolylineOptions();
//
//                // Fetching i-th route
//                List<HashMap<String, String>> path = result.get(i);
//
//                // Fetching all the points in i-th route
//                for(int j=0;j<path.size();j++){
//                    HashMap<String,String> point = path.get(j);
//
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//                    LatLng position = new LatLng(lat, lng);
//
//                    points.add(position);
//                }
//
//                // Adding all the points in the route to LineOptions
//                lineOptions.addAll(points);
//                lineOptions.width(2);
//                lineOptions.color(Color.BLUE);
//            }
//
//            if(lineOptions!=null) {
//
//                //remove existing line
//                if(polyline!=null)
//                    polyline.remove();
//
//                // Drawing polyline in the Google Map for the i-th route
//                polyline= mMap.addPolyline(lineOptions);
//                moveToBounds(polyline);
//            }
//        }
//    }
//
//    private void moveToBounds(Polyline p){
//
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        for(int i = 0; i < p.getPoints().size();i++){
//            builder.include(p.getPoints().get(i));
//        }
//
//        LatLngBounds bounds = builder.build();
//        int padding = 0; // offset from edges of the map in pixels
//
//        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//        mMap.animateCamera(cu);
//    }
//
//    public class DirectionsJSONParser {
//
//        /**
//         * Receives a JSONObject and returns a list of lists containing latitude and longitude
//         */
//        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
//
//            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
//            JSONArray jRoutes = null;
//            JSONArray jLegs = null;
//            JSONArray jSteps = null;
//
//            try {
//
//                jRoutes = jObject.getJSONArray("routes");
//
//                /** Traversing all routes */
//                for (int i = 0; i < jRoutes.length(); i++) {
//                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
//                    List path = new ArrayList<HashMap<String, String>>();
//
//                    /** Traversing all legs */
//                    for (int j = 0; j < jLegs.length(); j++) {
//                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
//
//                        /** Traversing all steps */
//                        for (int k = 0; k < jSteps.length(); k++) {
//                            String polyline = "";
//                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
//                            List<LatLng> list = decodePoly(polyline);
//
//                            /** Traversing all points */
//                            for (int l = 0; l < list.size(); l++) {
//                                HashMap<String, String> hm = new HashMap<String, String>();
//                                hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
//                                hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
//                                path.add(hm);
//                            }
//                        }
//                        routes.add(path);
//                    }
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//            }
//
//            return routes;
//        }
//
//        private List<LatLng> decodePoly(String encoded) {
//
//            List<LatLng> poly = new ArrayList<LatLng>();
//            int index = 0, len = encoded.length();
//            int lat = 0, lng = 0;
//
//            while (index < len) {
//                int b, shift = 0, result = 0;
//                do {
//                    b = encoded.charAt(index++) - 63;
//                    result |= (b & 0x1f) << shift;
//                    shift += 5;
//                } while (b >= 0x20);
//                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//                lat += dlat;
//
//                shift = 0;
//                result = 0;
//                do {
//                    b = encoded.charAt(index++) - 63;
//                    result |= (b & 0x1f) << shift;
//                    shift += 5;
//                } while (b >= 0x20);
//                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//                lng += dlng;
//
//                LatLng p = new LatLng((((double) lat / 1E5)),
//                        (((double) lng / 1E5)));
//                poly.add(p);
//            }
//
//            return poly;
//        }
//    }
//------------This code is for polyline draw for destination------End


    public void openFrontCamera() {


        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);


        // do we have a camera?
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        } else {
            cameraId = findFrontFacingCamera();

            Log.d(TAG, "cameraId  "+cameraId);

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

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();

        Log.d(TAG, "numberOfCameras "+numberOfCameras);

        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);

            Log.d(TAG, "info.facing "+i+" "+info.facing);

            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {

                Log.d(TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//            Log.d(TAG, "imageBitmap "+imageBitmap);
//
//            //mImageView.setImageBitmap(imageBitmap);
//        }

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

            final StorageReference  avatarRef = storageRef.child("images/" + driver.getDriverID()+"_avatar.jpg");

            UploadTask uploadTask = avatarRef.putBytes(byteArray);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(DriverMapActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(DriverMapActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();



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

                        setDriverImage(downloadUri);

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });


//            //05-Oct-2018 --Firebase suppress
//            dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH +"/"+driver.getDriverID());
//            Log.d(TAG, "imageBitmap-1");
//            Map<String, Object> driverImage = new HashMap<>();
//            driverImage.put("imageFound",true);
//            driverImage.put("driverImage",imageB64);
//            Log.d(TAG, "imageBitmap-2");
//            dbRef.updateChildren(driverImage);

            //--setting profile image immediately
            //setDriverImage(imageB64);

            Log.d(TAG, "imageBitmap-3");
        }
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
//
//            List<String> permissions = new ArrayList<String>();
//
//            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
//                permissions.add(Manifest.permission.CAMERA);
//
//            }
//            if (!permissions.isEmpty()) {
//                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
//            }
//        }
//
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 111: {
//                for (int i = 0; i < permissions.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
//
//
//                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
//
//                    }
//                }
//            }
//            break;
//            default: {
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            }
//        }
//    }
//






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


    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
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

        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final String color = "#BAB399";
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor(color));
//        canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f,
//                radius / 2 + 0.1f, paint);
        canvas.drawCircle(radius / 2 + 1.2f, radius / 2 + 1.2f,
                radius / 2 + 0.6f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }




//    //05-Oct-2018 --Firebase suppress
//    //----------start of readRiderSMS listener
//    public void readRiderSMS(){
//
//
//        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_ALERT_PATH + "/" + driver.getDriverID());
//
//        mChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//
//                // A new comment has been added, add it to the displayed list
//                // Comment comment = dataSnapshot.getValue(Comment.class);
//
//                // ...
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                //Comment newComment = dataSnapshot.getValue(Comment.class);
//                //String commentKey = dataSnapshot.getKey();
//
//                //Log.d(TAG, "DataSnapshot:" + dataSnapshot);
//
//                Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue().toString());
//
//                try
//                {
//
//                    //Do not use DriverAlert class as we are listening until DriverID which will not give full json
//                     // Convert String to json object
//
//                    setDriverAlert(dataSnapshot.getValue().toString());
//
//                }
//                catch (Exception ex){
//                    Log.d(TAG, "riderSMS onDataChange Exception");
//                    ex.printStackTrace();
//                }
//
//
//                // ...
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                String commentKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//                //Comment movedComment = dataSnapshot.getValue(Comment.class);
//                String commentKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
////                Toast.makeText(mContext, "Failed to load comments.",
////                        Toast.LENGTH_SHORT).show();
//            }
//
//        };
//
//        dbRef.addChildEventListener(mChildEventListener);
//
//
//
//    }
//    //-------end of readRiderSMS listener



    //-----Set Driver Alert from Rider
    public void setDriverAlert(String mobileNo) {


        //in order to get update on the same riderSMS, datetime included
        final String riderMobileNo=mobileNo.substring(0,mobileNo.indexOf("_"));
        final String riderID=GlobalConstants.RIDER_PREFIX+riderMobileNo;


        try {

            new AlertDialog.Builder(DriverMapActivity.this)
                    .setTitle("Hire")
                    .setMessage(riderMobileNo + " wants to book cab, are you ready?")
                    .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            // update Log
                            NowcabsLog nowcabsLog = new NowcabsLog();
                            nowcabsLog.updateLog(GlobalConstants.DRIVER_CODE, driver.driverID, "Accept Booking to ");


                            //Rides History
                            RidesHisory ridesHisory=new RidesHisory();
                            ridesHisory.saveRideHistory(riderID,driver.driverID,driver.destination);


                            // Here, thisActivity is the current activity
                            if (ContextCompat.checkSelfPermission(DriverMapActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                // Should we show an explanation?
                                if (ActivityCompat.shouldShowRequestPermissionRationale(DriverMapActivity.this, Manifest.permission.CALL_PHONE)) {

                                    // Show an explanation to the user *asynchronously* -- don't block
                                    // this thread waiting for the user's response! After the user
                                    // sees the explanation, try again to request the permission.

                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + riderMobileNo));
                                    startActivity(callIntent);


                                } else {

                                    // No explanation needed, we can request the permission.

                                    ActivityCompat.requestPermissions(DriverMapActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + riderMobileNo));
                                    startActivity(callIntent);

                                }
                            } else {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + riderMobileNo));
                                startActivity(callIntent);

                            }

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();

        } catch (Exception ex) {
            Log.d(TAG, "setDriverAlert Exception");
            ex.printStackTrace();
        }
    }//end of setDriverAlert


    //--------navigation

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_rides) {

            Intent i = new Intent(getApplicationContext(), RidesActivity.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable("Driver", driver);
            i.putExtra("UserGroup", GlobalConstants.DRIVER_CODE);
            i.putExtras(bundle);
            startActivity(i);


        }

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getDriverImage()
    {

        StorageReference storageRef = firebaseStorage.getReference();
        final StorageReference  avatarRef = storageRef.child("images/" + driver.getDriverID()+"_avatar.jpg");

        avatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                Log.d(TAG, "onSuccess  " + uri);

                setDriverImage(uri);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                //Toast.makeText(DriverMapActivity.this, "Image download failed", Toast.LENGTH_SHORT).show();
                driverImage.setImageResource(R.drawable.avatar_24dp);


            }
        });
    }

    public void setDriverImage(Uri uri){


        Picasso.get().load(uri).into(driverImage); //http://square.github.io/picasso/


//        // Receiving side
//        byte[] data = Base64.decode(driverImageData, Base64.DEFAULT);
//
//        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//        Bitmap bitmap = Bitmap.createScaledBitmap(bmp,120, 120, true);
//
//        driverImage.setImageBitmap(bitmap);


    }

    public void setParameters()
    {


        Log.d(TAG, "setParameters" );

        try
        {
            String country=parameter.getCountry();
            //driverRadius=parameter.getDriverRadius();
            mapZoom = parameter.getMapZoom();


//            //13-Oct-2018
//            Log.d(TAG, "country :" + country );
//
//            if(country!=null && country.trim().length()>=2) {
//
//                // Create Filter
//                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                        .setCountry(country)
//                        .build();
//                // Attach to autocomplete fragment / widget
//                autocompleteFragment.setFilter(typeFilter);
//
//            }

        }
        catch (Exception ex){
            Log.d(TAG, "setParameters Exception");
            ex.printStackTrace();
        }


    } //---end of setParameters


    public void getGoogleDirections()
    {

        new AlertDialog.Builder(DriverMapActivity.this)
                .setTitle(R.string.directions)
                .setMessage(R.string.directions_confirm)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                        if (destination==null) {

                            new AlertDialog.Builder(DriverMapActivity.this)
                                    .setTitle(R.string.destination)
                                    .setMessage(R.string.please_enter_destination)
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        else {


                            //---------To open Google Maps app-------
                            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(destination) + "&mode=d&avoid=tf");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                            //---------------

                        }

                    }
                })
                .create()
                .show();

    } //------end of google directions




    //--------update Driver Vacant Status in the backend
    public void driverVacantStatus(final String vacantStatus) {




        DriverMapActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                try {


                    Log.d(TAG, "driverLocation: " + driverLocation );


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", driver.getDriverID());
                    editor.putString("deviceToken", "DEVICETOKEN");
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    String methodAction = "updateVacantStatus";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("currentLat", driverLat);
                    messageJson.put("currentLng", driverLng);
                    messageJson.put("currentLocation", driverLocation);
                    messageJson.put("vacantStatus", vacantStatus);
                    messageJson.put("driverRefNo", driver.getDriverRefNo());
                    messageJson.put("driverID", driver.getDriverID());


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    Log.d(TAG, "updateVacantStatus responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.getJSONObject("updateVacantStatus");

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("driverWrapper");

//                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//
//                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.getJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.getJSONObject(0).getString("recordFound") == "true") {


                            //String driverRefNo = wrapperArrayObj.getJSONObject(0).getString("driverRefNo");
                            //String driverID = wrapperArrayObj.getJSONObject(0).getString("driverID");
                            //wrapperArrayObj.getJSONObject(0).getString("mobileNo");


                            //Log.d(TAG, "Driver Info: " + driverRefNo + " " + driverID);




                        }
                        else
                        {

                            Toast.makeText(DriverMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(DriverMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DriverMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                }

                //}// validation

            }//run end

        });//runnable end


    }//driverVacantStatus End

    public void setLocale()
    {
        //---------Language setting-----
        Locale.setDefault(locale);

        /**
         * Print the current language
         */
        System.out.println("My current language: "
                + Locale.getDefault());

        config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        //--------end of language setting

    }
}
