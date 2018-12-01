package com.rootmind.nowcabs;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import androidx.appcompat.widget.Toolbar;

/**
 * Created by saikirangandham on 7/22/17.
 */


public class DriverMoveActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener
{

    private static final String TAG = "DriverMoveActivity";



    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;

//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference dbRef;
//
//    private ChildEventListener mChildEventListener;
//
//
//    private ValueEventListener valueEventListener;

    TextView tv_location;

    //String[] markers;

    // create map to store
    Map<String, Object> markers = new HashMap<String, Object>();

    private Marker vehicleMarker;

//    LinearLayout rootLinearLayout;
//    LinearLayout driverInfoLinearLayout;


    Rider rider;
    Driver driver;
    Parameter parameter;


    //JSONArray driverTabs = new JSONArray();
    //JSONArray driverData = null;
    SharedPreferences sharedPreferences;

    //PlaceAutocompleteFragment autocompleteFragment;

    private static final int MENU_ITEM1 = 1;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE=1;

    Double currentLat=0.0;
    Double currentLng=0.0;
    int driverRadius=0;
    int mapZoom=0;

    float bearing=0;

    double riderLat=0.0;
    double riderLng=0.0;
    String riderLocation=null;
    Geocoder geocoder;
    List<Address> addresses;

    Map<String, Object> driverData=null;
    public Toolbar toolbar;

    public TextView tv_driverName, tv_mobileNo, tv_destination;
    public ImageView iv_driverImage;
    public ImageView iv_dialImage;
    public ImageView iv_smsImage;



//


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_movemap);
        Context context = this; // or ActivityNotification.this


        //To initiate firebase
//        firebaseDatabase = FirebaseDatabase.getInstance();

        //initialize autocomplete to specific area
        //initMapBoundaries();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        GoogleMapOptions options = new GoogleMapOptions();
//        options.liteMode(true);
//        options.compassEnabled(false);
//        options.rotateGesturesEnabled(false);
//        options.tiltGesturesEnabled(false);
//        mapFragment.newInstance(options);




        // Tabbar icon
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.nowcabs_icon48x48);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(30,144,255))); //Twitter Color

        //navigation bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Driver Location");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        driverData= new HashMap<>();

        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);





        buildGoogleApiClient();



        //---------Try to set in onCreate itself
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(5000);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(1000);

        //-----------

        // location permission for map to load
        checkLocationPermission();


        //LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        //Log.d(TAG, "Before RiderList: " );

        // tv_location=(TextView) findViewById(R.id.tv_location);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        //((AppCompatActivity) getActivity()).getSupportActionBar(myToolbar);
        // ((AppCompatActivity) getActivity()).setSupportActionController(myToolbar);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.rider_toolbar);
        //setSupportActionBar(toolbar);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rider);
        //setSupportActionBar(toolbar);



        parameter = (Parameter) getIntent().getSerializableExtra("Parameter");
        rider = (Rider) getIntent().getSerializableExtra("Rider");
        driver = (Driver) getIntent().getSerializableExtra("Driver");

        //Log.d(TAG, "DriverMove Rider: "+rider.getRiderID() );
        Log.d(TAG, "DriverMove Driver: "+driver.getDriverID());

        driverRadius=parameter.getDriverRadius();
        mapZoom = parameter.getMapZoom();


        tv_driverName = (TextView) this.findViewById(R.id.tv_driverName);
        tv_mobileNo = (TextView) this.findViewById(R.id.tv_mobileNo);
        tv_destination = (TextView) this.findViewById(R.id.tv_destination);
        iv_driverImage=(ImageView) this.findViewById(R.id.iv_driverImage);
        iv_dialImage=(ImageView) this.findViewById(R.id.iv_dialImage);
        iv_smsImage=(ImageView) this.findViewById(R.id.iv_smsImage);

        tv_destination.setTextColor(Color.RED);
        tv_mobileNo.setTextColor(Color.rgb(34, 139, 34)); //FOREST GREEN


        iv_dialImage.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                setDialImage(driver);

                                            }
                                        });

        iv_smsImage.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                //setSMSImage(driver);

                                            }
                                        });

        //on load assign values from RiderMapActivity
        setDriverTabs(driver);


//        // For Auto compelte of locations
//        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//
//
//                //move map camera
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),mapZoom));
//
//
//                Log.i(TAG, "Place: " + place.getName());
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
//            }
//        });// auto complete end



//        //driverInfoLinearLayout= (LinearLayout)findViewById(R.id.list_driverInfo);
//        rootLinearLayout= (LinearLayout)findViewById(R.id.list_driverInfo);
//
//        rootLinearLayout.setBackgroundColor(Color.WHITE);





    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "App onPaused: ");

//        //stop location updates when Activity is no longer active
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }
    }






    @Override
    public void onMapReady(GoogleMap googleMap) {


        Log.i(TAG, "App onMapReady: ");


        mMap = googleMap;

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        mMap.getUiSettings().setMapToolbarEnabled(false);



//        //Initialize Google Play Services
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                //buildGoogleApiClient();
//                mMap.setMyLocationEnabled(true);
//            }
//        }
//        else {
//            //buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//        }



//        //-----------
//        try {
//            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            Criteria crit = new Criteria();
//            String towers = lm.getBestProvider(crit, false);
//
//            Location location = lm.getLastKnownLocation(towers);
//
//            if (location != null) {
//
//
//                LatLng initLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//                Log.i(TAG, "Tower Zoom ");
//                //move map camera
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, mapZoom));
//
//            }
//        }
//        catch(Exception ex)
//        {
//            Log.d(TAG, "Unable to get tower");
//            ex.printStackTrace();
//        }




        //------






    }







    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }



    @Override
    public void onConnected(Bundle bundle) {

        Log.i(TAG, "App onConnected: ");

//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            if(mGoogleApiClient !=null){

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }


        //get last known location
        if(mGoogleApiClient !=null) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                currentLat = mLastLocation.getLatitude();
                currentLng = mLastLocation.getLongitude();
                mCurrentLocation=mLastLocation;

                LatLng latLng = new LatLng(currentLat, currentLng);
                Log.i(TAG, "GoogleAPI Zoom ");
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,mapZoom));
            }
        }

        //initialize autocomplete to specific area
        //initMapBoundaries();

        //Don't call tabs until onConnected is called;
        //readDriverLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {


        Log.i(TAG, "App onLocationChanged: ");

        mLastLocation = location;


        //Create Marker in the center
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //createMarker(latLng);

        //----capture current location details
        currentLat=location.getLatitude();
        currentLng=location.getLongitude();
        mCurrentLocation=location;


        //-----always store current location, so that it can be used while reloaidng in case OnLocationChanged is not fired
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentLat",currentLat.toString());
        editor.putString("currentLng", currentLng.toString());
        editor.commit();
        //-----------

        //fill current address in the auto complete fragment
        //autocompleteFragment.setText(GetAddress(location.getLatitude(),location.getLongitude()));



        //move map camera
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,mapZoom));



        Log.d(TAG, "DriverMove: Location " +  latLng.latitude  + ", Longitude:"+ latLng.longitude );

        // tv_location.setText("Latitude:" +  latLng.latitude  + ", Longitude:"+ latLng.longitude );


//        try {
//            geocoder = new Geocoder(this, Locale.ENGLISH);
//            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            Address returnAddress = addresses.get(0);
//
//            //String localityString = returnAddress.getLocality();
//
//            riderLocation = returnAddress.getLocality();
//
//            riderLat = location.getLatitude();
//            riderLng = location.getLongitude();
//
//            //updateRiderLocation();
//        }
//        catch (Exception e) {
//            // TODO Auto-generated catch block
//            Log.d(TAG, "onLocationChanged Exception");
//            Log.e("tag", e.getMessage());
//            e.printStackTrace();
//        }


        //stop location updates
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(DriverMoveActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {

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

            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "call phone permission granted", Toast.LENGTH_LONG).show();

                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "call phone permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }


//    public void readDriverLocation(){
//
//        //Log.d(TAG, "readDriverLocation driverID : " + driver.driverID);
//
//        //only for specified driver Id
//        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH+ "/" +driver.driverID); //"cabs/driver/location/ +"/"+driver.driverID"
//
//        //dbRef.child("VacantStatus").setValue("HIRED");
//
//        valueEventListener = new ValueEventListener() {
//
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                //Post post = dataSnapshot.getValue(Post.class);
//                // ...
//                //Log.d(TAG, "readDriverLocation:" + dataSnapshot);
//
//                Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//                Driver driverGeo= dataSnapshot.getValue(Driver.class);
//
//                Log.d(TAG, "readDriverLocation onChildChanged driverid:" + driverGeo.getDriverID());
//
//                setDriverLocation(driverGeo);
//
//                //Driver info tabs will create after excution complete of setDriverTabs(driverGeo) method
//                //setDriverInfoList();
//
//
////                //Create Marker in the center
////                LatLng latLng = new LatLng(driverGeo.driverLat, driverGeo.driverLng);
////                createMarker(latLng,driverGeo.driverVehicleType);
////
////                //fill current address in the auto complete fragment
////                autocompleteFragment.setText(GetAddress(driverGeo.driverLat,driverGeo.driverLng));
////
////
////                //move map camera
////                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,mapZoom));
//
//
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
//        dbRef.addValueEventListener(valueEventListener);
//        //dbRef.removeEventListener(valueEventListener);
//
//
////        mChildEventListener = new ChildEventListener() {
////            @Override
////            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
////
////                Log.d(TAG, "dataSnapshot: " + dataSnapshot);
////
////                Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());
////
////                // A new comment has been added, add it to the displayed list
////                // Comment comment = dataSnapshot.getValue(Comment.class);
////
////                // ...
////
////                Driver driverGeo= dataSnapshot.getValue(Driver.class);
////
////                setDriverTabs(driverGeo);
////                //Driver info tabs will create after excution complete of setDriverTabs(driverGeo) method
////                //setDriverInfoList(driverGeo);
////
////
////
////
////
////            }
////
////            @Override
////            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
////                Log.d(TAG, "readDriverLocation onChildChanged:" + dataSnapshot.getKey());
////
////                // A comment has changed, use the key to determine if we are displaying this
////                // comment and if so displayed the changed comment.
////                //Comment newComment = dataSnapshot.getValue(Comment.class);
////                //String commentKey = dataSnapshot.getKey();
////
////                Log.d(TAG, "readDriverLocation onChildChanged value:" + dataSnapshot.getValue());
////
////                Driver driverGeo= dataSnapshot.getValue(Driver.class);
////
////                Log.d(TAG, "readDriverLocation onChildChanged driverid:" + driverGeo.getDriverID());
////
////                setDriverTabs(driverGeo);
////                //Driver info tabs will create after excution complete of setDriverTabs(driverGeo) method
////                //setDriverInfoList();
////
////
////                // ...
////            }
////
////            @Override
////            public void onChildRemoved(DataSnapshot dataSnapshot) {
////                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
////
////                // A comment has changed, use the key to determine if we are displaying this
////                // comment and if so remove it.
////                String commentKey = dataSnapshot.getKey();
////
////                // ...
////            }
////
////            @Override
////            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
////                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
////
////                // A comment has changed position, use the key to determine if we are
////                // displaying this comment and if so move it.
////                //Comment movedComment = dataSnapshot.getValue(Comment.class);
////                String commentKey = dataSnapshot.getKey();
////
////                // ...
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//////                Toast.makeText(mContext, "Failed to load comments.",
//////                        Toast.LENGTH_SHORT).show();
////            }
////
////
////        };
////
////        dbRef.addChildEventListener(mChildEventListener);
//
//
//
//
//
//    }

    public void setDriverLocation(Driver driverGeo){


        try {



            Log.d(TAG, "driverGeo: " + driverGeo.driverID+" "+ driverGeo.status+" "+driverGeo.vacantStatus);

            //Log.d(TAG, "distance: " + distance+" driverRadius: "+ driverRadius);


            if(driverGeo.status.equals(GlobalConstants.ACTIVE_CODE) ){


                 Log.d(TAG, "Both conditions true: ");

                //double de = Double.parseDouble(driverGeo.driverLat);
//                Builder boundsBuilder = new LatLngBounds.Builder();
//                boundsBuilder.include(mCurrLocationMarker.getPosition());
//                LatLngBounds bounds = boundsBuilder.build();




                //Log.d(TAG, "Befor checking marker map: ");

                // Key might be present...
                if (!markers.containsKey(driverGeo.driverID)) {
                    // Okay, there's a key but the value is null


                    Log.d(TAG, "New Marker  : ");

                    vehicleMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(driverGeo.driverLat,driverGeo.driverLng))
                            .draggable(true));


                    if(driverGeo.driverVehicleType.equals(GlobalConstants.CAB_CODE))
                    {
                        //for cab image
                        vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cab_outline));
                    }
                    else
                    {
                        //For auto image
                        vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.auto_outline));
                    }

                    //extend the bounds to include each marker's position
                    Log.d(TAG, " put vehicleMarker  : "+vehicleMarker);
                    markers.put(driverGeo.driverID,vehicleMarker);


                    //move map camera to driver marker
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(driverGeo.driverLat,driverGeo.driverLng),mapZoom));



                } else {
                    // Definitely no such key

                    Log.d(TAG, "Marker Existed : Update ");

                    vehicleMarker= (Marker)markers.get(driverGeo.driverID);

                    //Log.d(TAG, " vehicleMarker  : "+vehicleMarker);

                    Log.d(TAG, "Marker Update to: "+driverGeo.driverLat + " "+ driverGeo.driverLng );


                    Log.d(TAG, "start bearing " );

                    Location lastLocation = new Location("last");
                    lastLocation.setLatitude(driverGeo.lastDriverLat);
                    lastLocation.setLongitude(driverGeo.lastDriverLng);

                    Log.d(TAG, "start bearing last location" );


                    Location currentLocation = new Location("current");
                    currentLocation.setLatitude(driverGeo.driverLat);
                    currentLocation.setLongitude(driverGeo.driverLng);

                    Log.d(TAG, "start bearing current location" );

                    bearing = lastLocation.bearingTo(currentLocation);

                    Log.d(TAG, "Marker bearing : "+ bearing );

                    vehicleMarker.setVisible(true);
                    vehicleMarker.setPosition(new LatLng(driverGeo.driverLat,driverGeo.driverLng));
                    vehicleMarker.setRotation(bearing);
                    vehicleMarker.setAnchor(0.5f, 0.5f);
                    vehicleMarker.setFlat(true);



                }


            }
            else
            {

                Log.d(TAG, "setDriverLocation remove marker if exists on the map");

                //remove marker if exists on the map
                if (markers.containsKey(driverGeo.driverID)) {
                    vehicleMarker= (Marker)markers.get(driverGeo.driverID);
                    vehicleMarker.setVisible(false);
                }

            }





        }

        catch (Exception e) {
            Log.d(TAG, "setDriverLocation Exception");
            e.printStackTrace();
        }
    }




//    public void setDriverTabs(Driver driverGeo){
//
//        try {
//
//            Log.d(TAG, "DriverMove setDriverTabs");
//
//            if (driverGeo.driverID != null && !driverGeo.driverID.trim().isEmpty()) {
//
//
//                boolean driverIndex = findDriverID(driverGeo.driverID);
//
//                setDriverInfoList(driverIndex, driverGeo);
//
//
////                Location targetLocation = new Location("target");
////                targetLocation.setLatitude(driverGeo.driverLat);
////                targetLocation.setLongitude(driverGeo.driverLng);
////
////
////                //get LastLocation
////                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
////
////                    if(mGoogleApiClient !=null && mLocationRequest !=null && mCurrentLocation==null){
////
////                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
////
////                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
////                                mGoogleApiClient);
////                        if (mLastLocation != null) {
////                            currentLat = mLastLocation.getLatitude();
////                            currentLng = mLastLocation.getLongitude();
////                            mCurrentLocation=mLastLocation;
////
////                        }
////
////                    }
////                }
////
////
////                //---in case onLocationChanged is not fired then take from last settings
////                if(mCurrentLocation==null)
////                {
////
////                    Log.d(TAG, "mCurren Lat: "+Double.parseDouble(sharedPreferences.getString("currentLat","")) );
////                    Log.d(TAG, "mCurren Lng: "+Double.parseDouble(sharedPreferences.getString("currentLng","")) );
////
////                    mCurrentLocation = new Location("CurrentLocation");
////                    mCurrentLocation.setLatitude(Double.parseDouble(sharedPreferences.getString("currentLat","")));
////                    mCurrentLocation.setLongitude(Double.parseDouble(sharedPreferences.getString("currentLng","")));
////
////                }
////
////                Log.d(TAG, "distance calc: "+mCurrentLocation.distanceTo(targetLocation) );
////
////                float distance = mCurrentLocation.distanceTo(targetLocation) /1000; //in kms
////
////                Log.d(TAG, "distance in kms: "+distance +" driverRadius: "+driverRadius );
////
////                if( distance <= driverRadius) {
////                    //driverTabs.put(driverIndex, buildJSONData(driverGeo));
////
////                    setDriverInfoList(driverIndex, driverGeo);
////                }
////
////                setDriverLocation(distance, driverGeo);
//
//
////                if (driverIndex != -1) {
////
////                    Log.d(TAG, "existing driver tab " + driverGeo.driverID);
////
////                    if( distance <= java.lang.Integer.parseInt(driverRadius)) {
////                        //driverTabs.put(driverIndex, buildJSONData(driverGeo));
////
////
////                            setDriverInfoList(driverIndex, driverGeo);
////                    }
////
////
////                } else {
////
////
////                    if(distance <= java.lang.Integer.parseInt(driverRadius))
////                    {
////
////
////                        setDriverInfoList(driverIndex, driverGeo);
////
////                    }
////
////                }
//
//
//
//            } //if driverID is not null
//
//
//
//
//
//
//
//        }
//        catch (Exception e){
//            Log.d(TAG, "setDriverTabs Exception");
//            e.printStackTrace();
//        }
//
//
//    }


    public void setDriverTabs(Driver driverGeo){

        try {


            tv_driverName.setText(driverGeo.getDriverName());
            tv_mobileNo.setText(driverGeo.getDriverMobileNo());
            tv_destination.setText(driverGeo.getDriverLocation()); //13-Sep-2018 changed from destination to driverLocation

            iv_driverImage.setAdjustViewBounds(true);
            if (driverGeo.getImageFound() == true) {
                // Receiving side
                byte[] data = Base64.decode(driverGeo.getDriverImage(), Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                iv_driverImage.setImageBitmap(bmp);


            }
            else {

                iv_driverImage.setImageResource(R.drawable.avatar_outline48);

            }

        }
        catch (Exception e){
            Log.d(TAG, "setDriverTabs Exception");
            e.printStackTrace();
        }


    }


//    public void setSMSImage(final Driver driverGeo)
//    {
//
//
//        try {
//
//
//            final String riderMobileNo = rider.getRiderMobileNo();
//
//
//            final String driverID = driverGeo.getDriverID();
//
//
//            Log.d(TAG, "SMS Button Click: ");
//
//
//            new AlertDialog.Builder(DriverMoveActivity.this)
//                    .setTitle("Book")
//                    .setMessage("Do you want to book cab?")
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//
//                            Calendar c = Calendar.getInstance();
//                            System.out.println("Current time => " + c.getTime());
//                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//                            String formattedDate = df.format(c.getTime());
//
//                            //datetime included to update firebase key with latest date so that driver can read
//                            Map<String, Object> smsMap = new HashMap<>();
//                            smsMap.put("riderSMS", riderMobileNo + "_" + formattedDate);
//                            smsMap.put("datetime", formattedDate);
//                            firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_ALERT_PATH + "/" + driverID).updateChildren(smsMap);
//
//                            // update Log
//                            //NowcabsLog nowcabsLog = new NowcabsLog();
//                            //nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "SMS to " + driverGeo.getDriverID());
//
//
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                            dialogInterface.dismiss();
//                        }
//                    })
//                    .create()
//                    .show();
//
//
//        } catch (Exception e) {
//            Log.d(TAG, "setSMSImage Exception");
//            e.printStackTrace();
//        }
//
//
//    }//end of set SMSImage




    public boolean findDriverID(String driverIDParam){

        try {


            Object driverID=driverData.get(driverIDParam);

            if(driverID!=null)
            {
                return true;
            }

//            for (int i = 0; i < driverData.size(); i++) {
//
//                JSONObject jObject = driverData.getJSONObject(i);
//
//                String driverID=jObject.getString("driverID");
//                if(driverID.equals(driverIDParam) ){
//
//                    return i;
//
//                }
//            }
        }
        catch (Exception e){
            Log.d(TAG, "findDriverID Exception");
            e.printStackTrace();

        }


        return false;

    }


//    public void buildJSONData(Driver driverGeo)
//    {
//        //JSONObject driverData=null;
//
//        try {
//
//
//            String destination;
//
//            if (driverGeo.destination == null || driverGeo.destination.trim().isEmpty()) {
//                destination = "ANY";
//            } else {
//                destination = driverGeo.destination;
//
//            }
//
//            driverData.put(driverGeo.driverID);
//
//
//            //driverData = new JSONObject();
////            driverData.put("destination", destination);
////            driverData.put("mobileNo", driverGeo.driverMobileNo);
////            driverData.put("status", driverGeo.status);
////            driverData.put("vehicleType", driverGeo.driverVehicleType);
////            driverData.put("driverName", driverGeo.driverName);
////            driverData.put("disabled", GlobalConstants.NO_CODE);
////
////            driverData.put("imageFound", driverGeo.imageFound);
////            driverData.put("driverImage", driverGeo.driverImage);
//
//
////            getDistanceInfo(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),driverGeo.getDriverLat(),driverGeo.getDriverLng());
//
//        }
//        catch (Exception e){
//            Log.d(TAG, "buildJSONData Exception");
//            e.printStackTrace();
//
//        }
//
//        //return driverData;
//
//    }


//    public void setDriverInfoList(boolean driverIndex, final Driver driverGeo){
//
//        try{
//
//
//            Log.d(TAG, "driverTabs status: " + driverGeo.getStatus());
//            //Log.d(TAG, "driverTabs disabled: " + driverTabs.getJSONObject(i).getString("disabled"));
//
//            if(driverGeo.getStatus().equals(GlobalConstants.ACTIVE_CODE) && driverGeo.getVacantStatus().equals(GlobalConstants.VACANT_CODE)) {
//
//                //new driver
//                if(driverIndex==false) {
//
//                    driverData.put(driverGeo.getDriverID(),driverGeo.getDriverID());
//
//                    driverInfoLinearLayout = new LinearLayout(this);
//
//
//                    TextView tv_mobileNo = new TextView(getApplicationContext());
//                    TextView tv_destination = new TextView(getApplicationContext());
//                    TextView tv_driverName = new TextView(getApplicationContext());
//
//
//                    driverInfoLinearLayout.setBackgroundColor(Color.WHITE);
//
//
//                    tv_driverName.setText(driverGeo.getDriverName());
//                    tv_driverName.setPadding(0, 0, 0, 0);
//                    tv_driverName.setTextColor(Color.DKGRAY);
//                    tv_driverName.setGravity(Gravity.CENTER);
//
//
//                    tv_destination.setText(driverGeo.getDestination());
//                    tv_destination.setPadding(2, 2, 2, 0);
//                    tv_destination.setTextColor(Color.RED);
//                    tv_destination.setGravity(Gravity.CENTER);
//
//
//                    tv_mobileNo.setText(driverGeo.getDriverMobileNo());
//                    tv_mobileNo.setPadding(2, 2, 2, 2);
//
//                    tv_mobileNo.setTextColor(Color.rgb(34, 139, 34)); //FOREST GREEN
//                    // Set TextView font/text size to 25 dp
//                    tv_mobileNo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
//                    tv_mobileNo.setGravity(Gravity.CENTER | Gravity.TOP);
//
//                    Log.d(TAG, "driverTabs drivername: " + driverGeo.getDriverName());
//
//
//                    //driver image adding
//                    ImageView driverImage = setDriverImage(driverGeo);
//
//                    //driverImage.setPadding(1, 1, 1, 1);
//                    //driverImage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//
//
//                    ImageView vehicleImage = new ImageView(this);
//
//
//                    if (driverGeo.getDriverVehicleType().equals(GlobalConstants.CAB_CODE)) {
//
//                        vehicleImage.setImageResource(R.drawable.caroutline);
//                    } else {
//                        vehicleImage.setImageResource(R.drawable.autotop_32x32);
//                    }
//                    vehicleImage.setAdjustViewBounds(true);
//                    //vehicleImage.setBackgroundResource(R.drawable.background_circle);
//                    //vehicleImage.setPadding(1, 1, 1, 1);
//
//
//                    //image adding
//                    //final ImageView dialImage = new ImageView(this);
//                    //dialImage.setPadding(10, 10, 10, 10);
//
//                    //ImageButton imageButton = new ImageButton(this);
//
//                    //imageButton.setImageResource(R.drawable.dial_icon);
//
//
//                    //set dial image
//                    //final ImageView dialImage = setDialImage(driverGeo);
//
//
//                    //-----start sms adding
//                    //final ImageView smsImage = setSMSImage(driverGeo);
//
//                    //-----end of SMS Image
//
//
//                    driverInfoLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//                    driverInfoLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    driverInfoLinearLayout.setGravity(Gravity.CENTER);
//
//                    driverInfoLinearLayout.addView(vehicleImage);
//
//                    //adding multiple textviews
//                    LinearLayout ll = new LinearLayout(this);
//                    ll.setOrientation(LinearLayout.VERTICAL);
//                    ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    ll.setGravity(Gravity.CENTER);
//                    ll.addView(tv_driverName);
//                    ll.addView(tv_destination);
//                    ll.addView(tv_mobileNo);
//
//
//                    //adding multiple textviews
//                    //                    LinearLayout dialLL = new LinearLayout(this);
//                    //                    dialLL.setOrientation(LinearLayout.HORIZONTAL);
//                    //                    dialLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//                    //                    dialLL.setGravity(Gravity.CENTER);
//                    //                    dialLL.addView(dialImage);
//                    //                    dialLL.addView(smsImage);
//
//
//                    driverInfoLinearLayout.addView(ll);
//
//
//                    //driverInfoLinearLayout.addView(dialImage);
//                    //driverInfoLinearLayout.addView(smsImage);
//                    driverInfoLinearLayout.setTag(driverGeo.getDriverID());
//                    //driverInfoLinearLayout.addView(dialLL);
//
//                    driverInfoLinearLayout.addView(driverImage);
//
//
//                    rootLinearLayout.addView(driverInfoLinearLayout);
//
//                } //if driverIndex==-1 //new driver
//
//            } //if condition
//            else //in Driver is inactive or hired remove
//            {
//
//                driverData.remove(driverGeo.driverID);
//
//                //to remove driver tab
//                rootLinearLayout.post(new Runnable() {
//                    public void run() {
//
//                        rootLinearLayout.removeView (rootLinearLayout.findViewWithTag(driverGeo.getDriverID()));
//                    }
//                });
//
//
//            }
//
//            setDriverLocation(driverGeo);
//
//
//        }
//        catch (Exception e){
//            Log.d(TAG, "setDriverInfoList Exception");
//            e.printStackTrace();
//        }
//
//    }


    public ImageView setDriverImage(Driver driverGeo)

    {

        final ImageView driverImage = new ImageView(this);

        try {



            driverImage.setAdjustViewBounds(true);


            //Log.d(TAG, "driverImage Found "+driverTabs.getJSONObject(i).getString("imageFound"));

            if (driverGeo.getImageFound() == true) {

                Log.d(TAG, "driverImage Found 1");

                // Receiving side
                byte[] data = Base64.decode(driverGeo.getDriverImage(), Base64.DEFAULT);

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                driverImage.setImageBitmap(bmp);


            } else {

                driverImage.setImageResource(R.drawable.avatar_outline48);

            }



        } catch (Exception e) {
            Log.d(TAG, "setDriverImage Exception");
            e.printStackTrace();
        }

        return driverImage;

    }//------end of Driver Image



    public ImageView setDialImage_old(Driver driverGeo)

    {

        final ImageView dialImage = new ImageView(this);

        try {


            dialImage.setImageResource(R.drawable.dialer_icon48x48);

            dialImage.setTag(driverGeo.getDriverMobileNo());

            dialImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Log.d(TAG, "Dial Button Click: ");

                    //callIntent.setData(Uri.parse("tel:9030209883"));
                    // update Log
                    NowcabsLog nowcabsLog = new NowcabsLog();


                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(DriverMoveActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(DriverMoveActivity.this, android.Manifest.permission.CALL_PHONE)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
                            startActivity(callIntent);

                            nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + dialImage.getTag());

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(DriverMoveActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
                            startActivity(callIntent);

                            nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + dialImage.getTag());

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
                        startActivity(callIntent);

                        nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + dialImage.getTag());

                    }


                    Log.d(TAG, "Dial Button Click end: " + dialImage.getTag());


                }
            });


        } catch (Exception e) {
            Log.d(TAG, "setDialImage Exception");
            e.printStackTrace();
        }

        return dialImage;

    }//------end of Dial Image




    public ImageView setSMSImage_old(final Driver driverGeo)
    {

        final ImageView smsImage = new ImageView(this);

        try {

            smsImage.setImageResource(R.drawable.sms_icon48x48);
            //smsImage.setPadding(10, 10, 10, 10);

            final String riderMobileNo = rider.getMobileNo();


            final String driverID = driverGeo.getDriverID();
            smsImage.setTag(driverID);
            smsImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Log.d(TAG, "SMS Button Click: ");


                    new AlertDialog.Builder(DriverMoveActivity.this)
                            .setTitle("Book")
                            .setMessage("Do you want to book cab?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    Calendar c = Calendar.getInstance();
                                    System.out.println("Current time => " + c.getTime());
                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                                    String formattedDate = df.format(c.getTime());

                                    //datetime included to update firebase key with latest date so that driver can read
                                    Map<String, Object> smsMap = new HashMap<>();
                                    smsMap.put("riderSMS", riderMobileNo + "_" + formattedDate);
                                    smsMap.put("datetime", formattedDate);
                                    //firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_ALERT_PATH + "/" + driverID).updateChildren(smsMap);

                                    // update Log
                                    NowcabsLog nowcabsLog = new NowcabsLog();
                                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "SMS to " + driverGeo.getDriverID());


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


                }
            });

        } catch (Exception e) {
            Log.d(TAG, "setSMSImage Exception");
            e.printStackTrace();
        }

        return smsImage;

    }//end of set SMSImage


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        // menu.add(0, MENU_ITEM1, Menu.NONE,"Rider");
//        getMenuInflater().inflate(R.menu.menu_rider_map, menu);
//
//        menu.add(Menu.NONE, R.menu.menu_rider_map, Menu.NONE,"Menu1");


        //menu.add(Menu.NONE,R.id.menu_mobileNo,0,rider.getRiderMobileNo()).setIcon(R.drawable.avatar_24dp);
        //menu.add(Menu.NONE,R.id.menu_name,1,rider.getRiderName());
        // menu.add(Menu.NONE,R.id.menu_riderID,2,rider.getRiderID());
        //menu.add(Menu.NONE,R.id.menu_logout,3,"Logout");


//        SubMenu themeMenu = menu.findItem(R.menu.menu_rider_map).getSubMenu();
//
//        themeMenu.clear();
//        themeMenu.add(0, R.id.menu_mobileNo, Menu.NONE, "Mobile No");
//        themeMenu.add(0, R.id.menu_riderID, Menu.NONE, "Rider ID");
//        themeMenu.add(0, R.id.menu_name, Menu.NONE, "Name");



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_morevert) {
            Toast.makeText(DriverMoveActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.menu_logout) {
            //Toast.makeText(DriverMoveActivity.this, "Action Logout", Toast.LENGTH_LONG).show();

            //To clear SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //editor.clear();
            //Do not clear all as last current lat and current lng required in case onLocationChange is not fired
//            editor.putString("mobileNoKey", "");
//            editor.putString("firstNameKey", "");
//            editor.putString("userGroupKey", "");
//            editor.putString("idKey", "");
            editor.putString("autoLoginKey", GlobalConstants.NO_CODE);
            editor.commit();


            //update Log
            NowcabsLog nowcabsLog=new NowcabsLog();
            nowcabsLog.updateLog(GlobalConstants.RIDER_CODE,rider.riderID,"Logout");


            Intent intent = new Intent(DriverMoveActivity.this, RiderLoginActivity.class);
            startActivity(intent);
            finish();



            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onDestroy(){
        super.onDestroy();

//        if (mChildEventListener != null) {
//            dbRef.removeEventListener(mChildEventListener);
//        }

    }


    public String GetAddress(Double lat, Double lng)
    {
        // Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        Geocoder geocoder;
        String city = "";
        try {



            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            if(addresses != null && addresses.size()>0) {

                String address=null;
                if(addresses.get(0).getMaxAddressLineIndex()>0)
                {
                    address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                }

                if(address!=null)
                {
                    city = address + ", " + addresses.get(0).getLocality();
                }
                else
                {
                    city = addresses.get(0).getLocality();
                }

                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
            }
            else{
                city = "No Address returned!";
            }

            Log.d("Tag", "city "+ city);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, "GetAddress Exception");
            e.printStackTrace();
            city = "Can't get Address!";
        }
        return city;
    }


    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            //Toast.makeText(this, "The user gestured on the map.",
            //Toast.LENGTH_SHORT).show();
            //autocompleteFragment.setText(GlobalConstants.FINDING_ADDRESS);

        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            //Toast.makeText(this, "The user tapped something on the map.",
            //Toast.LENGTH_SHORT).show();
            //autocompleteFragment.setText(GlobalConstants.FINDING_ADDRESS);

        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            //Toast.makeText(this, "The app moved the camera.",
            //Toast.LENGTH_SHORT).show();
            //autocompleteFragment.setText(GlobalConstants.FINDING_ADDRESS);

        }
    }

    @Override
    public void onCameraMove() {
        //Toast.makeText(this, "The camera is moving.",
        //Toast.LENGTH_SHORT).show();
        //autocompleteFragment.setText(GlobalConstants.FINDING_ADDRESS);
    }

    @Override
    public void onCameraMoveCanceled() {
        //Toast.makeText(this, "Camera movement canceled.",
        //Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        //Toast.makeText(this, "The camera has stopped moving.",
        //Toast.LENGTH_SHORT).show();

        //createMarker(mMap.getCameraPosition().target);
        //autocompleteFragment.setText(GetAddress(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude));

    }


    //Create Marker in the center of the map
    public void createMarker(LatLng latLng,String driverVehicleType) {

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        if(driverVehicleType.equals(GlobalConstants.CAB_CODE))
        {
            //for cab image
            //vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cartop));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cab_outline));
        }
        else
        {
            //For auto image
            //vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autotop_32x32));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.auto_outline));
        }

        mCurrLocationMarker = mMap.addMarker(markerOptions);

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
//                try
//                {
////                                String parameter=dataSnapshot.getValue().toString();
////
////                                // Convert String to json object
////                                JSONObject json = new JSONObject(parameter);
////
////                                // get value from LL Json Object
////                                String country=json.getString("country");
////                                Double radiusLng=Double.parseDouble(json.getString("radiusLng"));
////                                Double radiusLat=Double.parseDouble(json.getString("radiusLat"));
////                                driverRadius=json.getString("driverRadius");
////                                mapZoom = Integer.parseInt(json.getString("mapZoom"));
//
//
//                    Parameter parameter = dataSnapshot.getValue(Parameter.class);
//                    String country=parameter.getCountry();
//                    driverRadius=parameter.getDriverRadius();
//                    mapZoom = parameter.getMapZoom();
//
//                    Log.d(TAG, "country :" + country + " " +driverRadius);
//
//                    if(country!=null && country.trim().length()>=2) {
//
//                        // Create Filter
//                        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                                .setCountry(country)
//                                .build();
//                        // Attach to autocomplete fragment / widget
//                        autocompleteFragment.setFilter(typeFilter);
//                        //                        autocompleteFragment.setBoundsBias(new LatLngBounds(
//                        //                                new LatLng(16, radiusLng),
//                        //                                new LatLng(80, radiusLat)));
//
//                    }
//
//                    //call after initialize parameters
//                    //readDriverLocation();
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

    public void updateRiderLocation(){


        rider.riderLat=riderLat;
        rider.riderLng=riderLng;
        rider.riderLocation=riderLocation;
        Map<String, Object> riderMap = rider.toMap();
        //firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH+"/"+ rider.riderID).updateChildren(riderMap);


//        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH+"/"+ rider.getRiderID());
//
//        Log.d(TAG, " updateRiderLocation " );
//
//        valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//
//                // Log.d(TAG, "DataSnapshot:" + dataSnapshot);
//
//                // Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//                // riderLat: riderLat, riderLng:riderLng, riderLocation:$scope.riderLocation
//
//                if(dataSnapshot.getValue() != null){
//
//                    Log.d(TAG, "updateRiderLocation in if:" );
//
//                    rider.riderLat=riderLat;
//                    rider.riderLng=riderLng;
//                    rider.riderLocation=riderLocation;
//                    Map<String, Object> riderMap = rider.toMap();
//                    firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH+"/"+ rider.riderID).updateChildren(riderMap);
//
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
//        dbRef.addValueEventListener(valueEventListener);



    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            moveTaskToBack(true);
            return true;
        }

        return false;

    }


    private double getDistanceInfo(double sourceLat, double sourceLng, double destLat, double destLng) {
        Double dist = 0.0;

        String distanceData = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{

            String urlParameter = "mode=driving&origins="+sourceLat+","+sourceLng+"|"+destLat+","+destLng;
            URL url = new URL(GlobalConstants.GOOGLE_MAPS_DISTANCE_MATRIX_URL);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            distanceData = sb.toString();

            br.close();

        }catch(Exception e){

            Log.d(TAG, e.toString());
            e.printStackTrace();

        }finally{

            try {
                iStream.close();
                urlConnection.disconnect();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(distanceData);

            if(jsonObject.getJSONObject("status").equals("OK")) {

                JSONArray array = jsonObject.getJSONArray("routes");

                JSONObject routes = array.getJSONObject(0);

                JSONArray legs = routes.getJSONArray("legs");

                JSONObject steps = legs.getJSONObject(0);

                JSONObject distance = steps.getJSONObject("distance");

                Log.i("Distance", distance.toString());
                dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]", ""));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dist;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    public void setDialImage(Driver driverGeo)

    {


        try {


            Log.d(TAG, "Dial Button Click: ");

            //callIntent.setData(Uri.parse("tel:9030209883"));
            // update Log
            NowcabsLog nowcabsLog = new NowcabsLog();


            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(DriverMoveActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(DriverMoveActivity.this, android.Manifest.permission.CALL_PHONE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + driverGeo.getDriverMobileNo()));
                    startActivity(callIntent);

                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getDriverMobileNo());

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(DriverMoveActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + driverGeo.getDriverMobileNo()));
                    startActivity(callIntent);

                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getDriverMobileNo());

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + driverGeo.getDriverMobileNo()));
                startActivity(callIntent);

                nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getDriverMobileNo());

            }


            Log.d(TAG, "Dial Button Click end: " + driverGeo.getDriverMobileNo());




        } catch (Exception e) {
            Log.d(TAG, "setDialImage Exception");
            e.printStackTrace();
        }


    }//------end of Dial Image


}//class end
