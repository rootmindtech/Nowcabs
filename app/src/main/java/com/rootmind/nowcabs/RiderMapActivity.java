package com.rootmind.nowcabs;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.rootmind.nowcabs.ServiceAdapter.ItemClickListener;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
//import android.hardware.Camera;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;

import android.content.SharedPreferences;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import android.location.LocationManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.gms.maps.model.Circle;


import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.GoogleMap.*;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.ChildEventListener;
import com.squareup.picasso.Picasso;

import android.util.Log;

import android.app.AlertDialog;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

//import  android.graphics.*;


import android.net.Uri;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DefaultItemAnimator;


import android.location.Geocoder;
import android.location.Address;

import java.io.IOException;

import android.location.Criteria;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Base64;


import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;

import static com.rootmind.nowcabs.DriverMapActivity.getCroppedBitmap;


import android.support.design.widget.BottomSheetBehavior;

/**
 * Created by rootmindtechsoftprivatelimited on 19/06/17.
 */

public class RiderMapActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnCameraMoveStartedListener,
        OnCameraMoveListener,
        OnCameraMoveCanceledListener,
        OnCameraIdleListener,
        ServiceAdapter.ItemClickListener,
        ServiceSelectionAdapter.ItemClickListener,
        OnInfoWindowClickListener

{

    private static final String TAG = "RiderMapActivity";


    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Location mMarkerLocation;
    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;

//    //05-Oct-2018 --Firebase suppress
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference dbRef;

    //FirebaseStorage firebaseStorage;


//    private ChildEventListener mChildEventListener;
//
//
//    private ValueEventListener valueEventListener;

    //TextView tv_location;

    //String[] markers;

    // create map to store
    Map<String, Object> markers = new HashMap<String, Object>();

    private Marker vehicleMarker;

    //private Circle circle;

//    LinearLayout rootLinearLayout;
//    LinearLayout driverInfoLinearLayout;


    Rider rider;
    Parameter parameter;


    float bearing = 0;


    //JSONArray driverTabs = new JSONArray();
    //JSONArray driverData = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    PlaceAutocompleteFragment autocompleteFragment;

    private static final int MENU_ITEM1 = 1;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    Double currentLat = 0.0;
    Double currentLng = 0.0;
    int driverRadius = 0;
    int mapZoom = 0;


    double riderLat = 0.0;
    double riderLng = 0.0;
    String riderLocation = null;
    Geocoder geocoder;
    List<Address> addresses;

    Map<String, Object> driverData = null;

    String responseData = null;

    double markerLat = 0.0;
    double markerLng = 0.0;

    public Toolbar toolbar;
    public DrawerLayout drawer;



//            private RecyclerView mRecyclerView;
//            private RecyclerView.Adapter mAdapter;
//            private RecyclerView.LayoutManager mLayoutManager;


    private List<Rider> servicesList;
    private List<Rider> serviceSelectionList;

    private RecyclerView recyclerView;
    private RecyclerView servicesRecyclerView;

    private ServiceAdapter sAdapter;
    private ServiceSelectionAdapter serviceSelectionAdapter;

//    ImageView riderImage;
    //RatingBar ratingBar;


    //protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;
    private String mAddressOutput;
    //protected Location mLastKnownLocation;


    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

    LinearLayout layoutBottomSheetService;
    BottomSheetBehavior serviceSheetBehavior;


    FloatingActionButton btn_service;
    String currentServiceMarker;

    BottomNavigationView bottomNavigationView;

    private TextView tv_no_records;


    TextView tv_searchService;

    Configuration config;

    Locale locale;

    String destination = null;

    protected void startIntentService() {

        if (mMarkerLocation != null) {

            Intent intent = new Intent(this, FetchAddressIntentService.class);
            intent.putExtra(GlobalConstants.RECEIVER, mResultReceiver);
            intent.putExtra(GlobalConstants.LOCATION_DATA_EXTRA, mMarkerLocation);
            startService(intent);

            Log.d(TAG, "startIntentService started ");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        //sharedPreferences initiated
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d(TAG, "Locale in RiderMap1 : " + sharedPreferences.getString("locale", ""));
        locale = CommonService.getLocale(sharedPreferences.getString("locale", ""));
        Log.d(TAG, "Locale in RiderMap2 : " + locale);
        setLocale();

        setContentView(R.layout.activity_rider_map);
        Context context = this; // or ActivityNotification.this

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        servicesList = new ArrayList<>();
        serviceSelectionList = new ArrayList<>();


        //20-Sep-2018
//        //navigation bar
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        //toolbar.setTitle("");
//
//
//
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


        driverData = new HashMap<>();

        tv_searchService = (TextView) findViewById(R.id.tv_searchService);
        tv_searchService.setVisibility(View.GONE);


//        //05-Oct-2018 --Firebase suppress
//        //To initiate firebase
//        firebaseDatabase = FirebaseDatabase.getInstance();

        //firebaseStorage = FirebaseStorage.getInstance(GlobalConstants.FIREBASE_URL);


        rider = (Rider) getIntent().getSerializableExtra("Rider");
        //parameter = (Parameter) getIntent().getSerializableExtra("Parameter");

        parameter = new Parameter();

        Log.d(TAG, "RiderMap Rider: " + rider.getRiderID());

        // For Auto compelte of locations
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(15.0f);


        //-----------side nav image
        ImageView searchIcon = (ImageView) ((LinearLayout) autocompleteFragment.getView()).getChildAt(0);

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
        LinearLayout directionLayout = ((LinearLayout) autocompleteFragment.getView());
        RelativeLayout.LayoutParams buttonParam = new RelativeLayout.LayoutParams(
                90,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Button btnDirection = new Button(this);
        btnDirection.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.directions, 0);
        btnDirection.setLayoutParams(buttonParam);
        btnDirection.setGravity(Gravity.CENTER_HORIZONTAL);
        btnDirection.setPadding(0, 0, 10, 0);
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


        //initialize autocomplete to specific area
        //initMapBoundaries();

        //--- set parameters
        setParameters();


        buildGoogleApiClient();


        //right side service button
        btn_service = (FloatingActionButton) findViewById(R.id.btn_service);
        currentServiceMarker = GlobalConstants.TRANSPORT_AUTO_SERVICE;


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


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                //move map camera
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), mapZoom));

                destination = place.getName().toString();

                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });// auto complete end


        //driverInfoLinearLayout= (LinearLayout)findViewById(R.id.list_driverInfo);

        //Commented for RecyclerView
        //rootLinearLayout= (LinearLayout)findViewById(R.id.list_driverInfo);
        //rootLinearLayout.setBackgroundColor(Color.WHITE);

        //Shared Preferences for SideNav
//        editor = sharedPreferences.edit();
//        editor.putString("riderFirstName", rider.getRiderName());
//        editor.apply();

        //sidenav header
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//
//        View headerView = navigationView.getHeaderView(0);
//        //riderImage = (ImageView) headerView.findViewById(R.id.iv_userimage);
//        TextView riderName = (TextView) headerView.findViewById(R.id.tv_name);
//        TextView riderID = (TextView) headerView.findViewById(R.id.tv_id);
//        //drawerImage.setImageDrawable(R.drawable.ic_menu_camera);
//        //riderImage.setImageResource(R.drawable.avatar_24dp);
//        riderName.setText(sharedPreferences.getString("riderFirstName", "")); //to get latest name
//        riderID.setText(rider.riderID);

        //getRiderImage();


//        if (rider.getImageFound() == true) {
//
//            //setRiderImage(rider.getRiderImage());
//
//            getRiderImage();
//
//        } else {
//
//            riderImage.setImageResource(R.drawable.avatar_24dp);
//
//        }

//        riderImage.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                openFrontCamera();
//
//            }
//        });


//        //--navigation header onclick
//        View headerview = navigationView.getHeaderView(0);
//
//        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.nav_header);
//        header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(RiderMapActivity.this, "clicked", Toast.LENGTH_SHORT).show();
//
//                Intent i = new Intent(getApplicationContext(), RiderProfileActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("Rider", rider);
//                i.putExtras(bundle);
//                startActivity(i);
//
//
//                drawer.closeDrawer(GravityCompat.START);
//            }
//        });


        //to bring locationbutton to bottom right----MyLocationEnabled Button
        View locationButton = ((View) this.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 30);
        //-----end setMyLocationEnabled----


//        mRecyclerView = (RecyclerView) findViewById(R.id.driver_recycler_view);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // specify an adapter (see also next example)
//        mAdapter = new DriverAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);


        //21-Sep-2018
        //------------------
        recyclerView = (RecyclerView) findViewById(R.id.driver_recycler_view);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        sAdapter = new ServiceAdapter(servicesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);
        sAdapter.setClickListener(this);
        //----------------


        //-----------recycler for list service selecton-------
        servicesRecyclerView = (RecyclerView) findViewById(R.id.services_recycler_view);
        serviceSelectionAdapter = new ServiceSelectionAdapter(serviceSelectionList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        servicesRecyclerView.setLayoutManager(layoutManager);
        servicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        servicesRecyclerView.setAdapter(serviceSelectionAdapter);
        serviceSelectionAdapter.setClickListener(this);
        //----------------


        //recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));

        //Commented for RecyclerView
        //recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));


//        recyclerView.addOnItemTouchListener(
//                new RecyclerTouchListener(context, new RecyclerTouchListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        // TODO Handle item click
//
//                        Driver driver = servicesList.get(position);
//
//                        Toast.makeText(getApplicationContext(),  driver.driverName+ " is selected!", Toast.LENGTH_SHORT).show();
//
//
//                    }
//                })
//        );

        //18-Sep-2018
        mResultReceiver = new AddressResultReceiver(new Handler());


        // get the bottom sheet view
        layoutBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        // -----service selection sheet view
        layoutBottomSheetService = (LinearLayout) findViewById(R.id.bottom_sheet_service);
        serviceSheetBehavior = BottomSheetBehavior.from(layoutBottomSheetService);


        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //Toast.makeText(RiderMapActivity.this, "Close Sheet", Toast.LENGTH_LONG).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //Toast.makeText(RiderMapActivity.this, "Expand Sheet", Toast.LENGTH_LONG).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetClose();

        //---------end of bottom sheet


        //-----------bottom sheet for service selection
        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        serviceSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //Toast.makeText(RiderMapActivity.this, "Close Sheet", Toast.LENGTH_LONG).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //Toast.makeText(RiderMapActivity.this, "Expand Sheet", Toast.LENGTH_LONG).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomServiceSheetOpen();

        //---------end of bottom sheet


        //-----
        //-------bottom nav
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false); //remove default selection
        bottomNavigationView.setVisibility(View.GONE);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_public:

                                item.setCheckable(true);

                                item.setChecked(true);

                                bottomServiceSheetOpen();

                                //new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.AUTO_CODE, false});


                                //bottomSheetOpen();

                                //Toast.makeText(getApplicationContext(),  "Auto clicked", Toast.LENGTH_SHORT).show();

                                break;
                            case R.id.action_group:

                                item.setChecked(true);

                                //new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.CAB_CODE, false});

                                //bottomServiceSheetOpen();

                                break;
//                            case R.id.action_tool:
//
//                                item.setChecked(true);
//
//                                new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_PLUMBER, GlobalConstants.UTILITY_SERVICE, false});
//
//                                break;
////                            case R.id.action_tool:
////
////                                break;

                        }
                        return false;
                    }
                });
        //---------end of bottom nav


        //-------fab
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switchServiceMarkers();

            }
        });
        Drawable fabDr = btn_service.getDrawable();
        DrawableCompat.setTint(fabDr, Color.WHITE);


        //------rating

//        ratingBar = (RatingBar) findViewById(R.id.yourRatingBar);

        //------end of rating


    }//------end of create


    public void bottomSheetOpen() {
        sheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        if (sAdapter.getItemCount() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tv_no_records.setVisibility(View.GONE);

        } else {
            recyclerView.setVisibility(View.GONE);
            tv_no_records.setVisibility(View.VISIBLE);
        }

    }

    public void bottomSheetClose() {
        sheetBehavior.setPeekHeight(0);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        clearServiceList();

    }


    public void bottomServiceSheetOpen() {
        serviceSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        serviceSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        serviceSelectionList.clear();
        serviceSelectionList.add(rider);
        serviceSelectionAdapter.notifyDataSetChanged();

        if (serviceSelectionAdapter.getItemCount() > 0) {
            servicesRecyclerView.setVisibility(View.VISIBLE);

        } else {
            servicesRecyclerView.setVisibility(View.GONE);
        }

    }

    public void bottomServiceSheetClose() {
        serviceSheetBehavior.setPeekHeight(0);
        serviceSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //clearDriverList();

    }


    //on item selection

//    @Override
//    public void onClick(View view, int position) {
//
//
//
//        Driver driver = servicesList.get(position);
//
//        Toast.makeText(getApplicationContext(),  driver.driverMobileNo+" "+view.getId()+ " is selected!", Toast.LENGTH_SHORT).show();
//
//    }


    @Override
    public void onClickAvatarImage(View view, int position) {


        Rider rider = servicesList.get(position);

        //13-Sep-2018
        //trackDriver(driver);

        //Toast.makeText(getApplicationContext(), view.getId()+ " DriverImage is selected!" + driver.getDriverID(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClickDialImage(View view, int position) {


        Rider rider = servicesList.get(position);

        //this opens the ringer tone
        //callTimer();

        setDialImage(rider);

        //Toast.makeText(getApplicationContext(),  driver.driverMobileNo+" "+view.getId()+ " dial is selected!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClickSMSImage(View view, int position) {


        Rider rider = servicesList.get(position);

        setSMSImage(rider);

        //Toast.makeText(getApplicationContext(),  driver.driverMobileNo+" "+view.getId()+ " sms is selected!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClickFavorite(View view, int position) {


        Resources res = view.getContext().getResources();

        Rider rider = servicesList.get(position);

        insertFavoriteRating(position, rider, GlobalConstants.FAVORITE);

        if (rider.getFavorite().equals("F")) {
            Toast.makeText(getApplicationContext(), rider.getRiderName() + " " + res.getString(R.string.favorite_added), Toast.LENGTH_SHORT).show();
        } else if (rider.getFavorite().equals("N")) {
            Toast.makeText(getApplicationContext(), rider.getRiderName() + " " + res.getString(R.string.favorite_removed), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClickRating(View view, int position) {


        Rider rider = servicesList.get(position);

        showRatingDialog(position, rider);

        //Toast.makeText(getApplicationContext(),  driver.getDriverName()+ " rating is selected!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClickLocation(View view, int position) {


        Rider rider = servicesList.get(position);

        showServiceLocation(rider);

        //showRatingDialog(position, driver);

        //Toast.makeText(getApplicationContext(),  driver.getDriverName()+ " rating is selected!", Toast.LENGTH_SHORT).show();

    }

    //---------Service Selection Click Events
    @Override
    public void onClickCarpenter(View view, int position) {

        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_CARPENTER, null, false});

    }
    @Override
    public void onClickAutoDriver(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.AUTO_CODE, false});

    }
    @Override
    public void onClickCabDriver(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.CAB_CODE, false});

    }
    @Override
    public void onClickElectrician(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_ELECTRICIAN, null, false});

    }
    @Override
    public void onClickPlumber(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_PLUMBER, null, false});

    }
    @Override
    public void onClickTailor(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_TAILOR, null, false});

    }
    @Override
    public void onClickWasher(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_WASHER, null, false});

    }
    @Override
    public void onClickCourier(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_COURIER, null, false});

    }
    @Override
    public void onClickMerchant(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_MERCHANT, null, false});

    }
    //--------end of service selection events


    //------------------------------

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

        //to move google logo and current location logo
        mMap.setPadding(0, 0, 0, 200);


        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            //buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        //-----------
        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            Criteria crit = new Criteria();
            String towers = lm.getBestProvider(crit, false);

            Location location = lm.getLastKnownLocation(towers);

            if (location != null) {


                LatLng initLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                Log.i(TAG, "Tower Zoom ");
                //move map camera
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, mapZoom));

            }
        } catch (Exception ex) {
            Log.d(TAG, "Unable to get tower");
            ex.printStackTrace();
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TAG, "onMapClick");
                bottomSheetClose();
                bottomServiceSheetClose();
            }
        });


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

        //already set these in onCreate
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            if (mGoogleApiClient != null) {

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }


        //get last known location
        if (mGoogleApiClient != null) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                currentLat = mLastLocation.getLatitude();
                currentLng = mLastLocation.getLongitude();
                mCurrentLocation = mLastLocation;

                //21-Sep-2018
                mMarkerLocation = mLastLocation;

                LatLng latLng = new LatLng(currentLat, currentLng);
                Log.i(TAG, "GoogleAPI Zoom ");
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));
            }
        }

        //20-Sep-2018
        //Don't call tabs until onConnected is called;
        //readDriverLocation();


        //enable after getting latlng
        bottomNavigationView.setVisibility(View.VISIBLE);


        //Marker InfoWindow
        DriverInfoWindowAdapter driverInfoWindowAdapter = new DriverInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(driverInfoWindowAdapter);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {


        Log.i(TAG, "App onLocationChanged: ");

        mLastLocation = location;


//        21-Sep-2018
//        //Create Marker in the center
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        createMarker(latLng);

        //----capture current location details
        currentLat = location.getLatitude();
        currentLng = location.getLongitude();
        mCurrentLocation = location;


        //-----always store current location, so that it can be used while reloaidng in case OnLocationChanged is not fired
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentLat", currentLat.toString());
        editor.putString("currentLng", currentLng.toString());
        editor.apply();
        //-----------

        //fill current address in the auto complete fragment
        //18-Sep-2018
        //autocompleteFragment.setText(GetAddress(location.getLatitude(),location.getLongitude()));

        //move map camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        //move map camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));


        Log.d(TAG, "RiderMap: Location " + latLng.latitude + ", Longitude:" + latLng.longitude);

        // tv_location.setText("Latitude:" +  latLng.latitude  + ", Longitude:"+ latLng.longitude );


        try {
            geocoder = new Geocoder(this, Locale.getDefault()); //Locale.ENGLISH
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address returnAddress = addresses.get(0);

            //String localityString = returnAddress.getLocality();

            riderLocation = returnAddress.getLocality();

            riderLat = location.getLatitude();
            riderLng = location.getLongitude();


            new RiderMapActivity.updateRiderLocation().execute(new Object[]{null, null, false});


        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d(TAG, "onLocationChanged Exception");
            Log.e("tag", e.getMessage());
            e.printStackTrace();
        }


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        //21-Sep-2018
        showReverseGeocoder();

        Log.d(TAG, "onLocationChanged before fetchDriver");

        //default get auto markers on the map
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.AUTO_CODE, true});


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(RiderMapActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();


        Rider driverGeo = (Rider) marker.getTag();

        setDialImage(driverGeo);

        //marker.showInfoWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

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
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

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


//    //05-Oct-2018 --Firebase suppress
//    public void readDriverLocation(){
//
//
//        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_PATH); //"cabs/driver/location/"
//
//        //dbRef.child("VacantStatus").setValue("HIRED");
//
//
//        mChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//
//                //Log.d(TAG, "dataSnapshot: " + dataSnapshot);
//
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//
//                // A new comment has been added, add it to the displayed list
//                // Comment comment = dataSnapshot.getValue(Comment.class);
//
//                // ...
//
//                Driver driverGeo= dataSnapshot.getValue(Driver.class);
//
//
//                setDriverTabs(driverGeo);
//                //Driver info tabs will create after excution complete of setDriverTabs(driverGeo) method
//                //setDriverInfoList(driverGeo);
//
//
//
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "readDriverLocation onChildChanged:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                //Comment newComment = dataSnapshot.getValue(Comment.class);
//                //String commentKey = dataSnapshot.getKey();
//
//                //Log.d(TAG, "readDriverLocation onChildChanged value:" + dataSnapshot.getValue());
//
//                Driver driverGeo= dataSnapshot.getValue(Driver.class);
//
//                Log.d(TAG, "readDriverLocation onChildChanged driverid:" + driverGeo.getDriverID());
//
//                setDriverTabs(driverGeo);
//                //Driver info tabs will create after excution complete of setDriverTabs(driverGeo) method
//                //setDriverInfoList();
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
//
//        };
//
//        dbRef.addChildEventListener(mChildEventListener);
//
//
//
//
//
//    }


    public void setServiceLocation(int distance, Rider riderGeo) {


        try {


            Log.d(TAG, "driverGeo: " + riderGeo.riderID + " " + riderGeo.status + " " + riderGeo.vacantStatus);


            //13-Sep-2018
            if (riderGeo.status.equals(GlobalConstants.ACTIVE_CODE) && riderGeo.vacantStatus.equals(GlobalConstants.VACANT_CODE)) {


                Log.d(TAG, "Befor checking marker map: ");

                // Key might be present...
                if (!markers.containsKey(riderGeo.getRiderID())) {
                    // Okay, there's a key but the value is null


                    vehicleMarker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(riderGeo.getRiderLat(), riderGeo.getRiderLng()))
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.cartop))
                                    .draggable(false)
                                    .title(riderGeo.getRiderName())
                                    //.snippet(riderGeo.getDistance())
                    );

                    vehicleMarker.setTag(riderGeo);

                    if (riderGeo.getVehicleType().equals(GlobalConstants.CAB_CODE)) {
                        //for cab image
                        vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cartop));
                    } else {
                        //For auto image
                        vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autotop_32x32));
                    }

                    //extend the bounds to include each marker's position
                    Log.d(TAG, " put vehicleMarker  : " + vehicleMarker);
                    markers.put(riderGeo.getRiderID(), vehicleMarker);


                } else {
                    Log.d(TAG, "Marker Existed : Update ");

                    vehicleMarker = (Marker) markers.get(riderGeo.getRiderID());

                    Log.d(TAG, "Marker Update to: " + riderGeo.getRiderLat() + " " + riderGeo.getRiderLng());

                    vehicleMarker.setVisible(true);
                    vehicleMarker.setPosition(new LatLng(riderGeo.getRiderLat(), riderGeo.getRiderLng()));
                    //vehicleMarker.setRotation(bearing);
                    vehicleMarker.setAnchor(0.5f, 0.5f);
                    vehicleMarker.setFlat(true);


                }


            } else {

                //remove marker if exists on the map
                if (markers.containsKey(riderGeo.getRiderID())) {
                    vehicleMarker = (Marker) markers.get(riderGeo.getRiderID());
                    vehicleMarker.setVisible(false);
                }

            }


        } catch (Exception e) {
            Log.d(TAG, "setDriverLocation Exception");
            e.printStackTrace();
        }
    }


//    public void setDriverTabs(Driver driverGeo) {
//
//        try {
//
//
//            if (driverGeo.driverID != null && !driverGeo.driverID.trim().isEmpty()) {
//
//
//                boolean driverIndex = findDriverID(driverGeo.driverID);
//
//                Location targetLocation = new Location("target");
//                targetLocation.setLatitude(driverGeo.driverLat);
//                targetLocation.setLongitude(driverGeo.driverLng);
//
//
//                //get LastLocation
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                    if (mGoogleApiClient != null && mLocationRequest != null && mMarkerLocation == null) {
//
//                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//
//                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                                mGoogleApiClient);
//                        if (mLastLocation != null) {
//                            currentLat = mLastLocation.getLatitude();
//                            currentLng = mLastLocation.getLongitude();
//                            mMarkerLocation = mLastLocation;
//
//                        }
//
//                    }
//                }
//
//
//                //---in case onLocationChanged is not fired then take from last settings
//                if (mMarkerLocation == null) {
//
//                    Log.d(TAG, "mCurren Lat: " + Double.parseDouble(sharedPreferences.getString("currentLat", "")));
//                    Log.d(TAG, "mCurren Lng: " + Double.parseDouble(sharedPreferences.getString("currentLng", "")));
//
//                    mMarkerLocation = new Location("MarkerLocation");
//                    mMarkerLocation.setLatitude(Double.parseDouble(sharedPreferences.getString("currentLat", "")));
//                    mMarkerLocation.setLongitude(Double.parseDouble(sharedPreferences.getString("currentLng", "")));
//
//                }
//
//                //18-Sep-2018
////                Log.d(TAG, "distance calc: "+mCurrentLocation.distanceTo(targetLocation) );
////
////                int distance = Math.round(mCurrentLocation.distanceTo(targetLocation) /1000); //in kms
//
//                Log.d(TAG, "distance calc: " + mMarkerLocation.distanceTo(targetLocation));
//
//                int distance = Math.round(mMarkerLocation.distanceTo(targetLocation) / 1000); //in kms
//
//                //13-Sep-2018
//                driverGeo.distance = distance;
//
//                Log.d(TAG, "distance: " + distance + " driverRadius " + driverRadius);
//
//
//                //13-Sep-2018
//                if (distance <= driverRadius) {
//
//
//                    //driverTabs.put(driverIndex, buildJSONData(driverGeo));
//
//
//                    //Commented for RecyclerView
//                    setDriverInfoList(driverIndex, driverGeo);
//
//
//                }
//
//                setServiceLocation(distance, driverGeo);
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
//            } //if driverID is not null
//
//
//        } catch (Exception e) {
//            Log.d(TAG, "setDriverTabs Exception");
//            e.printStackTrace();
//        }
//
//
//    }


    public boolean findDriverID(String driverIDParam) {

        try {


            Object driverID = driverData.get(driverIDParam);

            if (driverID != null) {
                return true;
            }

//            for (int i = 0; i < driverData.size(); i++) {
//
//                JSONObject jObject = driverData.optJSONObject(i);
//
//                String driverID=jObject.getString("driverID");
//                if(driverID.equals(driverIDParam) ){
//
//                    return i;
//
//                }
//            }
        } catch (Exception e) {
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


//    public void setDriverInfoList_old(boolean driverIndex, final Driver driverGeo){
//
//        try{
//
//
//                Log.d(TAG, "driverTabs status: " + driverGeo.getStatus());
//                //Log.d(TAG, "driverTabs disabled: " + driverTabs.optJSONObject(i).getString("disabled"));
//
//                if(driverGeo.getStatus().equals(GlobalConstants.ACTIVE_CODE) && driverGeo.getVacantStatus().equals(GlobalConstants.VACANT_CODE)) {
//
//
//
//                    //Add to Horizontal list
//                    driversList.add(driverGeo);
//                    sAdapter.notifyDataSetChanged();
//
//
//                    //new driver
//                    if(driverIndex==false) {
//
//
//
//                        driverData.put(driverGeo.getDriverID(),driverGeo.getDriverID());
//
//                        driverInfoLinearLayout = new LinearLayout(this);
//
//
//                        TextView tv_mobileNo = new TextView(getApplicationContext());
//                        TextView tv_destination = new TextView(getApplicationContext());
//                        TextView tv_driverName = new TextView(getApplicationContext());
//
//
//                        driverInfoLinearLayout.setBackgroundColor(Color.WHITE);
//
//
//                        tv_driverName.setText(driverGeo.getDriverName());
//                        tv_driverName.setPadding(0, 0, 0, 0);
//                        tv_driverName.setTextColor(Color.DKGRAY);
//                        tv_driverName.setGravity(Gravity.CENTER);
//
//
//                        tv_destination.setText(driverGeo.getDestination());
//                        tv_destination.setPadding(2, 2, 2, 0);
//                        tv_destination.setTextColor(Color.RED);
//                        tv_destination.setGravity(Gravity.CENTER);
//
//
//                        tv_mobileNo.setText(driverGeo.getDriverMobileNo());
//                        tv_mobileNo.setPadding(2, 2, 2, 2);
//
//                        tv_mobileNo.setTextColor(Color.rgb(34, 139, 34)); //FOREST GREEN
//                        // Set TextView font/text size to 25 dp
//                        tv_mobileNo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
//                        tv_mobileNo.setGravity(Gravity.CENTER | Gravity.TOP);
//
//
//                        //driver image adding
//                        ImageView driverImage = setDriverImage(driverGeo);
//
//
//                        //driverImage.setPadding(1, 1, 1, 1);
//                        //driverImage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//
//
//                        ImageView vehicleImage = new ImageView(this);
//
//
//                        if (driverGeo.getDriverVehicleType().equals(GlobalConstants.CAB_CODE)) {
//
//                            vehicleImage.setImageResource(R.drawable.caroutline);
//                        } else {
//                            vehicleImage.setImageResource(R.drawable.autotop_32x32);
//                        }
//                        vehicleImage.setAdjustViewBounds(true);
//                        //vehicleImage.setBackgroundResource(R.drawable.background_circle);
//                        //vehicleImage.setPadding(1, 1, 1, 1);
//
//
//                        //image adding
//                        //final ImageView dialImage = new ImageView(this);
//                        //dialImage.setPadding(10, 10, 10, 10);
//
//                        //ImageButton imageButton = new ImageButton(this);
//
//                        //imageButton.setImageResource(R.drawable.dial_icon);
//
//
//                        //set dial image
//                        final ImageView dialImage = setDialImage_old(driverGeo);
//
//
//                        //-----start sms adding
//                        final ImageView smsImage = setSMSImage_old(driverGeo);
//
//                        //-----end of SMS Image
//
//
//                        driverInfoLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//                        driverInfoLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//                        driverInfoLinearLayout.setGravity(Gravity.CENTER);
//
//                        driverInfoLinearLayout.addView(vehicleImage);
//                        driverInfoLinearLayout.addView(driverImage);
//
//                        //adding multiple textviews
//                        LinearLayout ll = new LinearLayout(this);
//                        ll.setOrientation(LinearLayout.VERTICAL);
//                        ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//                        ll.setGravity(Gravity.CENTER);
//                        ll.addView(tv_driverName);
//                        ll.addView(tv_destination);
//                        ll.addView(tv_mobileNo);
//
//
//                        //adding multiple textviews
//                        //                    LinearLayout dialLL = new LinearLayout(this);
//                        //                    dialLL.setOrientation(LinearLayout.HORIZONTAL);
//                        //                    dialLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//                        //                    dialLL.setGravity(Gravity.CENTER);
//                        //                    dialLL.addView(dialImage);
//                        //                    dialLL.addView(smsImage);
//
//
//                        driverInfoLinearLayout.addView(ll);
//
//
//                        driverInfoLinearLayout.addView(dialImage);
//                        driverInfoLinearLayout.addView(smsImage);
//                        driverInfoLinearLayout.setTag(driverGeo.getDriverID());
//                        //driverInfoLinearLayout.addView(dialLL);
//
//
//                        rootLinearLayout.addView(driverInfoLinearLayout);
//
//
//
//                    } //if driverIndex==-1 //new driver
//
//                } //if condition
//                else //in Driver is inactive or hired remove
//                {
//
//                    driverData.remove(driverGeo.driverID);
//
//                    //to remove driver tab
//                    rootLinearLayout.post(new Runnable() {
//                        public void run() {
//
//                            rootLinearLayout.removeView (rootLinearLayout.findViewWithTag(driverGeo.getDriverID()));
//                        }
//                    });
//
//
//                }
//
//
//
//        }
//        catch (Exception e){
//            Log.d(TAG, "setDriverInfoList Exception");
//            e.printStackTrace();
//        }
//
//    }

//    public void setDriverInfoList(boolean driverIndex, Rider driverGeo) {
//
//        try {
//
//
//            Log.d(TAG, "driverTabs status: " + driverGeo.getStatus());
//            //Log.d(TAG, "driverTabs disabled: " + driverTabs.optJSONObject(i).getString("disabled"));
//
//            if (driverGeo.getStatus().equals(GlobalConstants.ACTIVE_CODE) && driverGeo.getVacantStatus().equals(GlobalConstants.VACANT_CODE)) {
//
//
//                //Add to Horizontal list
//                servicesList.add(driverGeo);
//                sAdapter.notifyDataSetChanged();
//
//
//            } //if condition
//            else //in Driver is inactive or hired remove
//            {
//
//                Log.d(TAG, "setDriverInfoList Remove: " + driverGeo.getStatus() + " count " + servicesList.size() + "contains " + servicesList.contains(driverGeo));
//
//                int position = sAdapter.getItemPosition(driverGeo);
//                if (position >= 0) {
//                    servicesList.remove(position);
//                    sAdapter.notifyItemRemoved(position);
//                    sAdapter.notifyItemChanged(position, sAdapter.getItemCount() - position);
//                    sAdapter.notifyDataSetChanged();
//                }
//
//            }
//
//
//        } catch (Exception e) {
//            Log.d(TAG, "setDriverInfoList Exception");
//            e.printStackTrace();
//        }
//
//    }


//    public ImageView setDriverImage(final Driver driverGeo) {
//
//        final ImageView driverImage = new ImageView(this);
//
//        try {
//
//            driverImage.setAdjustViewBounds(true);
//
//            //Log.d(TAG, "driverImage Found "+driverTabs.optJSONObject(i).getString("imageFound"));
//
//            if (driverGeo.getImageFound() == true) {
//
//                Log.d(TAG, "driverImage Found 1");
//
//                // Receiving side
//                byte[] data = Base64.decode(driverGeo.getDriverImage(), Base64.DEFAULT);
//
//                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//                driverImage.setImageBitmap(bmp);
//
//
//            } else {
//
//                driverImage.setImageResource(R.drawable.avatar_outline48);
//
//            }
//
//            driverImage.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    trackDriver(driverGeo);
//
//                }
//            });
//
//
//        } catch (Exception e) {
//            Log.d(TAG, "setDriverImage Exception");
//            e.printStackTrace();
//        }
//
//        return driverImage;
//
//    }//------end of Driver Image


//    //05-Oct-2018
//    public ImageView setDialImage_old(Driver driverGeo)
//
//    {
//
//        final ImageView dialImage = new ImageView(this);
//
//        try {
//
//
//            dialImage.setImageResource(R.drawable.dialer_icon48x48);
//
//            dialImage.setTag(driverGeo.getDriverMobileNo());
//
//            dialImage.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//
//                    Log.d(TAG, "Dial Button Click: ");
//
//                    //callIntent.setData(Uri.parse("tel:9030209883"));
//                    // update Log
//                    NowcabsLog nowcabsLog = new NowcabsLog();
//
//
//                    // Here, thisActivity is the current activity
//                    if (ContextCompat.checkSelfPermission(RiderMapActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//                        // Should we show an explanation?
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(RiderMapActivity.this, Manifest.permission.CALL_PHONE)) {
//
//                            // Show an explanation to the user *asynchronously* -- don't block
//                            // this thread waiting for the user's response! After the user
//                            // sees the explanation, try again to request the permission.
//
//                            Intent callIntent = new Intent(Intent.ACTION_CALL);
//                            callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
//                            startActivity(callIntent);
//
//                            nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + dialImage.getTag());
//
//                        } else {
//
//                            // No explanation needed, we can request the permission.
//
//                            ActivityCompat.requestPermissions(RiderMapActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
//
//                            Intent callIntent = new Intent(Intent.ACTION_CALL);
//                            callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
//                            startActivity(callIntent);
//
//                            nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + dialImage.getTag());
//
//                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                            // app-defined int constant. The callback method gets the
//                            // result of the request.
//                        }
//                    } else {
//                        Intent callIntent = new Intent(Intent.ACTION_CALL);
//                        callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
//                        startActivity(callIntent);
//
//                        nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + dialImage.getTag());
//
//                    }
//
//
//                    Log.d(TAG, "Dial Button Click end: " + dialImage.getTag());
//
//
//                }
//            });
//
//
//        } catch (Exception e) {
//            Log.d(TAG, "setDialImage Exception");
//            e.printStackTrace();
//        }
//
//        return dialImage;
//
//    }//------end of Dial Image


    public void setDialImage(Rider driverGeo)

    {


        try {


            Log.d(TAG, "Dial Button Click: ");

            //callIntent.setData(Uri.parse("tel:9030209883"));
            // update Log
            NowcabsLog nowcabsLog = new NowcabsLog();


            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(RiderMapActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(RiderMapActivity.this, Manifest.permission.CALL_PHONE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + driverGeo.getRiderMobileNo()));
                    startActivity(callIntent);

                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getRiderMobileNo());

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(RiderMapActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + driverGeo.getRiderMobileNo()));
                    startActivity(callIntent);

                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getRiderMobileNo());

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + driverGeo.getRiderMobileNo()));
                startActivity(callIntent);

                nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getRiderMobileNo());

            }


            Log.d(TAG, "Dial Button Click end: " + driverGeo.getRiderMobileNo());


        } catch (Exception e) {
            Log.d(TAG, "setDialImage Exception");
            e.printStackTrace();
        }


    }//------end of Dial Image


//    //05-Oct-2018 --Firebase suppress
//    public ImageView setSMSImage_old(final Driver driverGeo)
//    {
//
//        final ImageView smsImage = new ImageView(this);
//
//        try {
//
//            smsImage.setImageResource(R.drawable.sms_icon48x48);
//            //smsImage.setPadding(10, 10, 10, 10);
//
//            final String riderMobileNo = rider.getRiderMobileNo();
//
//
//            final String driverID = driverGeo.getDriverID();
//            smsImage.setTag(driverID);
//            smsImage.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//
//                    Log.d(TAG, "SMS Button Click: ");
//
//
//                    new AlertDialog.Builder(RiderMapActivity.this)
//                            .setTitle("Book")
//                            .setMessage("Do you want to book cab?")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//
//                                    Calendar c = Calendar.getInstance();
//                                    System.out.println("Current time => " + c.getTime());
//                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//                                    String formattedDate = df.format(c.getTime());
//
//                                    //datetime included to update firebase key with latest date so that driver can read
//                                    Map<String, Object> smsMap = new HashMap<>();
//                                    smsMap.put("riderSMS", riderMobileNo + "_" + formattedDate);
//                                    smsMap.put("datetime", formattedDate);
//                                    firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_ALERT_PATH + "/" + driverID).updateChildren(smsMap);
//
//                                    // update Log
//                                    NowcabsLog nowcabsLog = new NowcabsLog();
//                                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "SMS to " + driverGeo.getDriverID());
//
//
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    dialogInterface.dismiss();
//                                }
//                            })
//                            .create()
//                            .show();
//
//
//                }
//            });
//
//        } catch (Exception e) {
//            Log.d(TAG, "setSMSImage Exception");
//            e.printStackTrace();
//        }
//
//        return smsImage;
//
//    }//end of set SMSImage


    public void setSMSImage(final Rider driverGeo) {


        try {


            final String riderMobileNo = rider.getRiderMobileNo();


            final String driverID = driverGeo.getRiderID();


            Log.d(TAG, "SMS Button Click: ");


            new AlertDialog.Builder(RiderMapActivity.this)
                    .setTitle("Book")
                    .setMessage("Do you want to book cab?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            Calendar c = Calendar.getInstance();
                            System.out.println("Current time => " + c.getTime());
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                            String formattedDate = df.format(c.getTime());


//                                    //05-Oct-2018 --Firebase suppress
//                                    //datetime included to update firebase key with latest date so that driver can read
//                                    Map<String, Object> smsMap = new HashMap<>();
//                                    smsMap.put("riderSMS", riderMobileNo + "_" + formattedDate);
//                                    smsMap.put("datetime", formattedDate);
//                                    firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_ALERT_PATH + "/" + driverID).updateChildren(smsMap);
//
//                                    // update Log
//                                    NowcabsLog nowcabsLog = new NowcabsLog();
//                                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "SMS to " + driverGeo.getDriverID());


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


        } catch (Exception e) {
            Log.d(TAG, "setSMSImage Exception");
            e.printStackTrace();
        }


    }//end of set SMSImage

    public void trackDriver(Driver driver) {


        Intent i = new Intent(getApplicationContext(), DriverMoveActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("Parameter", parameter);
        bundle.putSerializable("Rider", rider);
        bundle.putSerializable("Driver", driver);
        i.putExtras(bundle);
        startActivity(i);


    }



    public String GetAddress(Double lat, Double lng) {
        // Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        Geocoder geocoder;
        String city = "";
        try {


            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault()); //Locale.getDefault()

            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            if (addresses != null && addresses.size() > 0) {

                String address = null;
                if (addresses.get(0).getMaxAddressLineIndex() > 0) {
                    address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                }

                if (address != null) {
                    city = address + ", " + addresses.get(0).getLocality();
                } else {
                    city = addresses.get(0).getLocality();
                }

                Log.d("Tag", "city1 " + city);

                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

//                        Log.d("Tag", "state "+ state);
//                        Log.d("Tag", "country "+ country);
//                        Log.d("Tag", "postalcode "+ postalCode);
//                        Log.d("Tag", "knownname "+ knownName);

            } else {
                city = "No Address returned!";
            }

            Log.d("Tag", "city " + city);
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

        if (reason == OnCameraMoveStartedListener.REASON_GESTURE) {
            //Toast.makeText(this, "The user gestured on the map.",
            //Toast.LENGTH_SHORT).show();

            bottomSheetClose();

            autocompleteFragment.setText(GlobalConstants.FINDING_ADDRESS);

        } else if (reason == OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            //Toast.makeText(this, "The user tapped something on the map.",
            //Toast.LENGTH_SHORT).show();

            bottomSheetClose();

            autocompleteFragment.setText(GlobalConstants.FINDING_ADDRESS);

        } else if (reason == OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            //Toast.makeText(this, "The app moved the camera.",
            //Toast.LENGTH_SHORT).show();

            bottomSheetClose();

            autocompleteFragment.setText(GlobalConstants.FINDING_ADDRESS);

        }
    }

    @Override
    public void onCameraMove() {
        //Toast.makeText(this, "The camera is moving.",
        //Toast.LENGTH_SHORT).show();


//        GoogleMapOptions options = new GoogleMapOptions();
//        options.liteMode(true);
//        mapFragment.newInstance(options);

        autocompleteFragment.setText(GlobalConstants.FINDING_ADDRESS);


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


//        GoogleMapOptions options = new GoogleMapOptions();
//        options.liteMode(true);
//        mapFragment.newInstance(options);


        //21-Sep-2018
        createMarker(mMap.getCameraPosition().target);


        //18-Sep-2018
        //autocompleteFragment.setText(GetAddress(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude));
        showReverseGeocoder();


    }


    //Create Marker in the center of the map
    public void createMarker(LatLng latLng) {

        //21-Sep-2018
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

//        if (circle != null) {
//            circle.remove();
//        }


        Location markerLocation = new Location("MarkerLocation");
        markerLocation.setLatitude(latLng.latitude);
        markerLocation.setLongitude(latLng.longitude);
        markerLat = latLng.latitude;
        markerLng = latLng.longitude;
        mMarkerLocation = markerLocation;


//        circle = mMap.addCircle(new CircleOptions()
//                .center(new LatLng(latLng.latitude, latLng.longitude))
//                .radius(200)
//                .strokeWidth(1)
//                .strokeColor(Color.BLUE)
//                .fillColor(Color.parseColor("0x8800CCFF")));
//                //.clickable(true));

        //21-Sep-2018
        //Place current location marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mMap.addMarker(markerOptions);


    }


//    public void initMapBoundaries()
//    {
//
//
//                dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_PARAM_PATH);
//
//                Log.d(TAG, "initMapBoundaries" );
//
//                valueEventListener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get Post object and use the values to update the UI
//                        //Post post = dataSnapshot.getValue(Post.class);
//                        // ...
//                        //Log.d(TAG, "initMapBoundaries:" + dataSnapshot);
//
//                        //Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
//                        //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
//
//
//                            try
//                            {
//
//                                parameter = dataSnapshot.getValue(Parameter.class);
//                                String country=parameter.getCountry();
//                                driverRadius=parameter.getDriverRadius();
//                                mapZoom = parameter.getMapZoom();
//
//                                Log.d(TAG, "country :" + country + " " +driverRadius);
//
//                                if(country!=null && country.trim().length()>=2) {
//
//                                    // Create Filter
//                                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                                            .setCountry(country)
//                                            .build();
//                                    // Attach to autocomplete fragment / widget
//                                    autocompleteFragment.setFilter(typeFilter);
//        //                        autocompleteFragment.setBoundsBias(new LatLngBounds(
//        //                                new LatLng(16, radiusLng),
//        //                                new LatLng(80, radiusLat)));
//
//                                }
//
//                            }
//                            catch (Exception ex){
//                                Log.d(TAG, "initMapBoundaries Exception");
//                                ex.printStackTrace();
//                            }
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                        // ...
//                    }
//                };
//                dbRef.addListenerForSingleValueEvent(valueEventListener);
//                dbRef.removeEventListener(valueEventListener);
//
//
//    }

    public void setParameters() {


        Log.d(TAG, "setParameters");

        try {
            String country = parameter.getCountry();
            driverRadius = parameter.getDriverRadius();
            mapZoom = parameter.getMapZoom();

//            //13-OCt-2018
//            Log.d(TAG, "country :" + country + " " +driverRadius + " " + mapZoom);
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

        } catch (Exception ex) {
            Log.d(TAG, "setParameters Exception");
            ex.printStackTrace();
        }


    } //---end of setParameters


//     //05-Oct-2018 --Firebase suppress
//     public void updateRiderLocationFB(){
//
//
//        rider.riderLat=riderLat;
//        rider.riderLng=riderLng;
//        rider.riderLocation=riderLocation;
//        Map<String, Object> riderMap = new HashMap<>();
//        riderMap.put("riderLat",riderLat);
//        riderMap.put("riderLng",riderLng);
//        riderMap.put("riderLocation",riderLocation);
//        firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH+"/"+ rider.riderID).updateChildren(riderMap);
//
//
////        dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH+"/"+ rider.getRiderID());
////
////        Log.d(TAG, " updateRiderLocation " );
////
////        valueEventListener = new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                // Get Post object and use the values to update the UI
////
////                // Log.d(TAG, "DataSnapshot:" + dataSnapshot);
////
////                // Log.d(TAG, "dataSnapshot.getKey() :" + dataSnapshot.getKey());
////                //Log.d(TAG, "dataSnapshot.getValue() :" + dataSnapshot.getValue());
////                // riderLat: riderLat, riderLng:riderLng, riderLocation:$scope.riderLocation
////
////                if(dataSnapshot.getValue() != null){
////
////                    Log.d(TAG, "updateRiderLocation in if:" );
////
////                    rider.riderLat=riderLat;
////                    rider.riderLng=riderLng;
////                    rider.riderLocation=riderLocation;
////                    Map<String, Object> riderMap = rider.toMap();
////                    firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH+"/"+ rider.riderID).updateChildren(riderMap);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
        try {

            String urlParameter = "mode=driving&origins=" + sourceLat + "," + sourceLng + "|" + destLat + "," + destLng;
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
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            distanceData = sb.toString();

            br.close();

        } catch (Exception e) {

            Log.d(TAG, e.toString());
            e.printStackTrace();

        } finally {

            try {
                iStream.close();
                urlConnection.disconnect();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(distanceData);

            if (jsonObject.optJSONObject("status").equals("OK")) {

                JSONArray array = jsonObject.getJSONArray("routes");

                JSONObject routes = array.optJSONObject(0);

                JSONArray legs = routes.getJSONArray("legs");

                JSONObject steps = legs.optJSONObject(0);

                JSONObject distance = steps.optJSONObject("distance");

                Log.i("Distance", distance.toString());
                dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]", ""));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dist;
    }

    //--------navigation

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {


//                case R.id.nav_rides: {
//
//                    Intent i = new Intent(getApplicationContext(), RidesActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("Rider", rider);
//                    i.putExtras(bundle);
//                    startActivity(i);
//                    break;
//
//
//                }

            case R.id.nav_profile: {

                Intent i = new Intent(getApplicationContext(), RiderProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("RegisterFlag", false);
                i.putExtras(bundle);
                //i.putExtra("UserGroup", GlobalConstants.RIDER_CODE);
                startActivity(i);
                break;

            }

            case R.id.nav_driver: {

                Intent i = new Intent(getApplicationContext(), DriverLoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("RegisterFlag", false);
                i.putExtras(bundle);
                //i.putExtra("UserGroup", GlobalConstants.RIDER_CODE);
                startActivity(i);
                break;

            }
//                case R.id.nav_avatar: {
//
//                    Log.i("inside avatar nav id", Integer.toString(R.id.nav_avatar));
//
//                    Intent i = new Intent(getApplicationContext(), AvatarActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("Rider", rider);
//                    bundle.putSerializable("RegisterFlag", false);
//                    i.putExtras(bundle);
//                    //i.putExtra("UserGroup", GlobalConstants.RIDER_CODE);
//                    startActivity(i);
//                    break;
//
//                }
            case R.id.nav_idcard: {

                Intent i = new Intent(getApplicationContext(), IDCardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("RegisterFlag", false);
                i.putExtras(bundle);
                //i.putExtra("UserGroup", GlobalConstants.RIDER_CODE);
                startActivity(i);
                break;

            }
            case R.id.nav_service: {

                Log.i("inside service nav id", Integer.toString(R.id.nav_service));


                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("RegisterFlag", false);
                i.putExtras(bundle);
                //i.putExtra("UserGroup", GlobalConstants.RIDER_CODE);
                startActivity(i);
                break;

            }

        }


//                else if (id == R.id.nav_camera) {
//                // Handle the camera action
//                } else if (id == R.id.nav_gallery) {
//
//                } else if (id == R.id.nav_slideshow) {
//
//                } else if (id == R.id.nav_manage) {
//
//                } else if (id == R.id.nav_share) {
//
//                } else if (id == R.id.nav_send) {
//
//                }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultData == null) {
                return;
            }

            // Display the address string
            // or an error message sent from the intent service.
            mAddressOutput = resultData.getString(GlobalConstants.RESULT_DATA_KEY);
            if (mAddressOutput == null) {
                mAddressOutput = "";
            }

            //Log.d(TAG, "Address " + mAddressOutput);

            //autocompleteFragment.setText(mAddressOutput);

            //displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == GlobalConstants.SUCCESS_RESULT) {


                Log.d(TAG, "Address Found " + mAddressOutput);

                autocompleteFragment.setText(mAddressOutput);

                //showToast(getString(R.string.address_found));
            }

        }
    }

    private void showReverseGeocoder() {


        if (!Geocoder.isPresent()) {
            Log.d(TAG, "No Geocoder available ");

            return;
        }

        // Start service and update UI to reflect new location
        startIntentService();

    }
//            mFusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            mLastKnownLocation = location;
//
//                            // In some rare cases the location returned can be null
//                            if (mLastKnownLocation == null) {
//                                return;
//                            }
//
//                            if (!Geocoder.isPresent()) {
//                                Toast.makeText(RiderMapActivity.this,
//                                        "no_geocoder_available",
//                                        Toast.LENGTH_LONG).show();
//                                return;
//                            }
//
//                            // Start service and update UI to reflect new location
//                            startIntentService();
//
//                            //updateUI();
//                            //autocompleteFragment.setText(mAddressOutput);
//
//                        }
//                    });


    //--------update Rider Location udpate in the backend
//    public void updateRiderLocation() {
//
//
//        RiderMapActivity.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
    private class updateRiderLocation extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {
            //loadingSpinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object... params) {


            try {


                //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);


                //Shared Preferences
                editor = sharedPreferences.edit();

                editor.putString("userid", rider.getRiderID());
                editor.putString("deviceToken", rider.getFcmToken());
                editor.putString("sessionid", "SESSIONID");

                editor.apply();


                String methodAction = "updateRiderLocation";

                JSONObject messageJson = new JSONObject();
                messageJson.put("currentLat", riderLat);
                messageJson.put("currentLng", riderLng);
                messageJson.put("currentLocation", riderLocation);
                messageJson.put("riderRefNo", rider.getRiderRefNo());
                messageJson.put("riderID", rider.getRiderID());


                ConnectHost connectHost = new ConnectHost();
                responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                Log.d(TAG, "update rider Location responseData: " + responseData);


                if (responseData != null) {


                    // Convert String to json object
                    JSONObject jsonResponseData = new JSONObject(responseData);

                    // get LL json object
                    JSONObject jsonResult = jsonResponseData.optJSONObject("updateRiderLocation");

                    JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

//                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//
//                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                    if (jsonResponseData.getString("success") == "true" && wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


                        //String driverRefNo = wrapperArrayObj.optJSONObject(0).getString("driverRefNo");
                        //String driverID = wrapperArrayObj.optJSONObject(0).getString("driverID");
                        //wrapperArrayObj.optJSONObject(0).getString("mobileNo");


                        //Log.d(TAG, "Driver Info: " + driverRefNo + " " + driverID);


                    } else {

                        Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

            }

            //}// validation
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //loadingSpinner.setVisibility(View.GONE);
        }


}
//            }//run end
//
//        });//runnable end
//
//
//    }//riderlocation update End




    private class fetchServiceLocationProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {
            //loadingSpinner.setVisibility(View.VISIBLE);

            tv_searchService.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Object...params ) {

//            Log.d(TAG, "param[0] "+params[0].toString());
//            Log.d(TAG, "param[1] "+params[1].toString());
//            Log.d(TAG, "param[2] "+Boolean.valueOf(params[2].toString()));

            fetchServiceLocation(params[0].toString(),(params[1]==null?null:params[1].toString()),Boolean.valueOf(params[2].toString()));

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //loadingSpinner.setVisibility(View.GONE);

            tv_searchService.setVisibility(View.GONE);

        }
    }
                    //--------fetch service Location  in the backend
    public void fetchServiceLocation(final String service, final String vehicleType, final boolean switchService) {




        RiderMapActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                try {


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);


//                    tv_searchService.setText("Searching " +   services....");

                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    String methodAction = "fetchServiceLocation";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("serviceCode", service);
                    messageJson.put("currentLat", markerLat);
                    messageJson.put("currentLng", markerLng);
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());
                    if(vehicleType!=null) {
                        messageJson.put("vehicleType", vehicleType);
                    }


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    Log.d(TAG, "fetch driver location  Location responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchServiceLocation");

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");

//                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//
//                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));

                        if (jsonResponseData.optString("success") == "true") {


                            clearServiceList();

                            Rider rider=null;

                            if(wrapperArrayObj!=null) {

                                for (int i = 0; i <= wrapperArrayObj.length() - 1; i++) {
                                    if (wrapperArrayObj.optJSONObject(i).optString("recordFound") == "true") {

                                        rider = new Rider();

                                        rider.setRiderRefNo(wrapperArrayObj.optJSONObject(i).optString("riderRefNo"));
                                        rider.setRiderID(wrapperArrayObj.optJSONObject(i).optString("riderID"));
                                        rider.setRiderName(wrapperArrayObj.optJSONObject(i).optString("firstName"));
                                        rider.setRiderMobileNo(wrapperArrayObj.optJSONObject(i).optString("mobileNo"));
                                        rider.setVehicleNo(wrapperArrayObj.optJSONObject(i).optString("vehicleNo"));
                                        rider.setStatus(wrapperArrayObj.optJSONObject(i).optString("status"));
                                        rider.setVehicleType(wrapperArrayObj.optJSONObject(i).optString("vehicleType"));
                                        rider.setRiderLat(wrapperArrayObj.optJSONObject(i).getDouble("currentLat"));
                                        rider.setRiderLng(wrapperArrayObj.optJSONObject(i).getDouble("currentLng"));
                                        rider.setRiderLocation(wrapperArrayObj.optJSONObject(i).optString("currentLocation"));
                                        rider.setVacantStatus(wrapperArrayObj.optJSONObject(i).optString("vacantStatus"));
                                        rider.setDistance(wrapperArrayObj.optJSONObject(i).getDouble("distance"));
                                        rider.setDuration(wrapperArrayObj.optJSONObject(i).getDouble("duration"));
                                        rider.setRiderLat(markerLat);
                                        rider.setRiderLng(markerLng);
                                        rider.setFavorite(wrapperArrayObj.optJSONObject(i).optString("favorite"));
                                        rider.setAvgRating(wrapperArrayObj.optJSONObject(i).getDouble("avgRating"));
                                        rider.setYourRating(wrapperArrayObj.optJSONObject(i).getDouble("yourRating"));
                                        rider.setServiceCode(wrapperArrayObj.optJSONObject(i).optString("serviceCode"));

                                        Log.d(TAG, "Avg Rating: " + wrapperArrayObj.optJSONObject(i).optString("avgRating"));
                                        Log.d(TAG, "Your Rating: " + wrapperArrayObj.optJSONObject(i).optString("yourRating"));

                                        JSONArray imageWrappers = wrapperArrayObj.optJSONObject(i).optJSONArray("imageWrappers");

                                        if(imageWrappers!=null)
                                        {

                                            rider.images = new Image[imageWrappers.length()];
                                            Image image=null;

                                            for(int j=0;j<imageWrappers.length();j++)
                                            {
                                                if(imageWrappers.optJSONObject(j).optString("recordFound")=="true")
                                                {
                                                    image = new Image();

                                                    image.setRiderRefNo(imageWrappers.optJSONObject(j).optString("riderRefNo"));
                                                    image.setRiderID(imageWrappers.optJSONObject(j).optString("riderID"));
                                                    image.setImageID(imageWrappers.optJSONObject(j).optString("imageID"));
                                                    image.setImageName(imageWrappers.optJSONObject(j).optString("imageName"));
                                                    image.setImageFolder(imageWrappers.optJSONObject(j).optString("imageFolder"));
                                                    image.setStatus(imageWrappers.optJSONObject(j).optString("status"));
                                                    rider.images[j] = image;

                                                }
                                            }

                                        }


                                        servicesList.add(rider);
                                        sAdapter.notifyDataSetChanged();

                                        //only if right top button is clicked
                                        if (switchService == true) {
                                            setServiceLocation(0, rider);
                                        }


                                    }
                                }


                                if(switchService==false) {
                                    bottomSheetOpen();
                                }

                            }//null condition check

                        }
                        else
                        {

                            Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                }

                //}// validation

            }//run end

        });//runnable end


    }//fetch rider location update End




    public void switchServiceMarkers(){



        //---------first remove all markers from screen
        Iterator it = markers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            //it.remove(); // avoids a ConcurrentModificationException
            vehicleMarker= (Marker)pair.getValue();
            vehicleMarker.setVisible(false);
        }
        //-----------------


        if(currentServiceMarker.equals(GlobalConstants.TRANSPORT_AUTO_SERVICE)) {

            btn_service.setEnabled(false);
            new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.CAB_CODE, true});
            currentServiceMarker = GlobalConstants.TRANSPORT_CAB_SERVICE;
            btn_service.setImageResource(R.drawable.car_24px);
            Drawable fabDr= btn_service.getDrawable();
            DrawableCompat.setTint(fabDr, Color.WHITE);
            btn_service.setEnabled(true);

        }
        else if(currentServiceMarker.equals(GlobalConstants.TRANSPORT_CAB_SERVICE)) {

            btn_service.setEnabled(false);
            new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.UTILITY_SERVICE, true});
            currentServiceMarker = GlobalConstants.SERVICE_MERCHANT;
            btn_service.setImageResource(R.drawable.merchant);
            Drawable fabDr= btn_service.getDrawable();
            DrawableCompat.setTint(fabDr, Color.WHITE);
            btn_service.setEnabled(true);


        }
        else if(currentServiceMarker.equals(GlobalConstants.SERVICE_MERCHANT)) {

            btn_service.setEnabled(false);
            new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.AUTO_CODE, true});
            currentServiceMarker = GlobalConstants.TRANSPORT_AUTO_SERVICE;
            btn_service.setImageResource(R.drawable.auto_24px);
            Drawable fabDr= btn_service.getDrawable();
            DrawableCompat.setTint(fabDr, Color.WHITE);
            btn_service.setEnabled(true);


        }


    } //------end of switch service markers



    //-------- insertFavorite  udpate in the backend
    public void insertFavoriteRating(final int position, final Rider serviceGeo, final String actionType) {




        RiderMapActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                try {


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    String methodAction = null;

                    switch (actionType)
                    {
                        case GlobalConstants.FAVORITE:
                        {
                            methodAction = "insertFavorite";
                            break;
                        }
                        case GlobalConstants.RATING:
                        {
                            methodAction = "insertRating";
                            break;
                        }

                    }

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("favoriteRefNo", serviceGeo.getRiderRefNo());
                    messageJson.put("favoriteID", serviceGeo.getRiderID());
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());

                    switch (actionType)
                    {
                        case GlobalConstants.FAVORITE:
                        {
                            messageJson.put("favorite", (serviceGeo.getFavorite().equals("N")?"F":"N"));
                            break;
                        }
                        case GlobalConstants.RATING:
                        {
                            messageJson.put("rating", serviceGeo.getYourRating());
                            break;
                        }

                    }


                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    Log.d(TAG, "update rider Location responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = null;

                        switch (actionType)
                        {
                            case GlobalConstants.FAVORITE:
                            {
                                jsonResult = jsonResponseData.optJSONObject("insertFavorite");
                                break;
                            }
                            case GlobalConstants.RATING:
                            {
                                jsonResult = jsonResponseData.optJSONObject("insertRating");
                                break;
                            }

                        }

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("favoriteWrapper");

//                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
//
//                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).optString("recordFound"));

                        if (jsonResponseData.getString("success") == "true")
                        {

                                if(wrapperArrayObj.optJSONObject(0).getString("recordFound") == "true") {


//                                    String favoriteRefNo = wrapperArrayObj.optJSONObject(0).getString("favoriteRefNo");
//                                    String favoriteID = wrapperArrayObj.optJSONObject(0).getString("favoriteID");
//        //                            wrapperArrayObj.optJSONObject(0).getString("mobileNo");
//
//
//                                    Log.d(TAG, "Driver Info: " + favoriteRefNo + " " + favoriteID);

                                    switch (actionType)
                                    {
                                        case GlobalConstants.FAVORITE:
                                        {
                                            serviceGeo.setFavorite(wrapperArrayObj.optJSONObject(0).optString("favorite"));
                                            break;
                                        }
                                        case GlobalConstants.RATING:
                                        {
                                            serviceGeo.setAvgRating(wrapperArrayObj.optJSONObject(0).optDouble("avgRating"));
                                            serviceGeo.setYourRating(wrapperArrayObj.optJSONObject(0).optDouble("rating"));
                                            break;
                                        }

                                    }
                                    servicesList.set(position, serviceGeo);
                                    sAdapter.notifyDataSetChanged();

                                }


                        }
                        else
                        {

                            Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();

                }

                //}// validation

            }//run end

        });//runnable end


    }//insertfavorite update End


    //-----rating dialog
    public void showRatingDialog(final int position, final Rider serviceGeo)
    {


        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LinearLayout linearLayout = new LinearLayout(this);
        final RatingBar rating = new RatingBar(this);
        rating.setNumStars(5);
        rating.setMax(5);
        rating.setStepSize(0.5f);
        linearLayout.addView(rating);
        linearLayout.setGravity(Gravity.CENTER);

        Resources res = popDialog.getContext().getResources();

//        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle(res.getString(R.string.rate) + " - " + serviceGeo.getRiderName() + "!!!");
        popDialog.setView(linearLayout);

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //tv_Rating.setText(String.valueOf(rating.getProgress()));

                        if(rating.getProgress()>0.0) {

                            serviceGeo.setYourRating(rating.getProgress() / 2.0f);

                            //call to update rating
                            insertFavoriteRating(position, serviceGeo, GlobalConstants.RATING);

                            Toast.makeText(getApplicationContext(), serviceGeo.getRiderName() + " rating is " + serviceGeo.getYourRating(), Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }


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

    public void showServiceLocation(Rider serviceGeo)
    {



        if(serviceGeo.getVehicleType().equals(GlobalConstants.TRANSPORT_AUTO_SERVICE))
        {
            currentServiceMarker = GlobalConstants.SERVICE_MERCHANT;
        }
        if(serviceGeo.getVehicleType().equals(GlobalConstants.TRANSPORT_CAB_SERVICE))
        {
            currentServiceMarker = GlobalConstants.TRANSPORT_AUTO_SERVICE;
        }

        switchServiceMarkers();


        LatLng latLng = new LatLng(serviceGeo.getRiderLat(), serviceGeo.getRiderLng());
        //move map camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));
        bottomSheetClose();


    }

    public void clearServiceList(){

        //clear driverlist before getting
        servicesList.clear();
        sAdapter.notifyDataSetChanged();

    }

//    private class fetchServiceLocationProgressTask extends AsyncTask<Object, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            //loadingSpinner.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected Void doInBackground(Object...params ) {
//
////            Log.d(TAG, "param[0] "+params[0].toString());
////            Log.d(TAG, "param[1] "+params[1].toString());
////            Log.d(TAG, "param[2] "+Boolean.valueOf(params[2].toString()));
//
//            fetchServiceLocation(params[0].toString(),params[1].toString(),Boolean.valueOf(params[2].toString()));
//
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            //loadingSpinner.setVisibility(View.GONE);
//        }
//    }
    //--------fetch driver Location  in the backend
//    public void fetchServiceLocation(final String service, final String vehicleType, final boolean switchService) {
//
//
//
//
//        RiderMapActivity.this.runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                try {
//
//
//                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);
//
//
//                    //Shared Preferences
//                    editor = sharedPreferences.edit();
//
//                    editor.putString("userid", rider.getRiderID());
//                    editor.putString("deviceToken", rider.getFcmToken());
//                    editor.putString("sessionid", "SESSIONID");
//
//                    editor.apply();
//
//
//                    String methodAction = "fetchServiceLocation";
//
//                    JSONObject messageJson = new JSONObject();
//                    messageJson.put("service", service);
//                    messageJson.put("currentLat", markerLat);
//                    messageJson.put("currentLng", markerLng);
//                    messageJson.put("riderRefNo", rider.getRiderRefNo());
//                    messageJson.put("riderID", rider.getRiderID());
//
//
//                    ConnectHost connectHost = new ConnectHost();
//                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);
//
//                    Log.d(TAG, "fetch driver location  Location responseData: " + responseData);
//
//
//                    if (responseData != null) {
//
//
//                        // Convert String to json object
//                        JSONObject jsonResponseData = new JSONObject(responseData);
//
//                        // get LL json object
//                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchServiceLocation");
//
//                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("riderWrapper");
//
////                            Log.d(TAG, "wrapperArrayObj: " + wrapperArrayObj);
////
////                            Log.d(TAG, "wrapperArrayObj[0] recordFound " + wrapperArrayObj.optJSONObject(0).getString("recordFound"));
//
//                        if (jsonResponseData.getString("success") == "true") {
//
//
//                            clearDriverList();
//
//                            Driver driver=null;
//
//                            if(wrapperArrayObj!=null) {
//
//                                for (int i = 0; i <= wrapperArrayObj.length() - 1; i++) {
//                                    if (wrapperArrayObj.optJSONObject(i).getString("recordFound") == "true") {
//
//                                        driver = new Driver();
//
//                                        driver.setDriverRefNo(wrapperArrayObj.optJSONObject(i).getString("driverRefNo"));
//                                        driver.setDriverID(wrapperArrayObj.optJSONObject(i).getString("driverID"));
//                                        driver.setDriverName(wrapperArrayObj.optJSONObject(i).getString("firstName"));
//                                        driver.setDriverMobileNo(wrapperArrayObj.optJSONObject(i).getString("mobileNo"));
//                                        driver.setDriverVehicleNo(wrapperArrayObj.optJSONObject(i).getString("vehicleNo"));
//                                        driver.setStatus(wrapperArrayObj.optJSONObject(i).getString("status"));
//                                        driver.setDriverVehicleType(wrapperArrayObj.optJSONObject(i).getString("vehicleType"));
//                                        driver.setDriverLat(wrapperArrayObj.optJSONObject(i).getDouble("currentLat"));
//                                        driver.setDriverLng(wrapperArrayObj.optJSONObject(i).getDouble("currentLng"));
//                                        driver.setDriverLocation(wrapperArrayObj.optJSONObject(i).getString("currentLocation"));
//                                        driver.setVacantStatus(wrapperArrayObj.optJSONObject(i).getString("vacantStatus"));
//                                        driver.setDistance(wrapperArrayObj.optJSONObject(i).getDouble("distance"));
//                                        driver.setDuration(wrapperArrayObj.optJSONObject(i).getDouble("duration"));
//                                        driver.setRiderLat(markerLat);
//                                        driver.setRiderLng(markerLng);
//                                        driver.setFavorite(wrapperArrayObj.optJSONObject(i).getString("favorite"));
//                                        driver.setAvgRating(wrapperArrayObj.optJSONObject(i).getDouble("avgRating"));
//                                        driver.setYourRating(wrapperArrayObj.optJSONObject(i).getDouble("yourRating"));
//
//                                        Log.d(TAG, "Avg Rating: " + wrapperArrayObj.optJSONObject(i).getString("avgRating"));
//                                        Log.d(TAG, "Your Rating: " + wrapperArrayObj.optJSONObject(i).getString("yourRating"));
//
//
//                                        servicesList.add(driver);
//                                        sAdapter.notifyDataSetChanged();
//
//                                        //only if right top button is clicked
//                                        if (switchService == true) {
//                                            setDriverLocation(0, driver);
//                                        }
//
//
//                                    }
//                                }
//
//
//                                if(switchService==false) {
//                                    bottomSheetOpen();
//                                }
//
//                            }//null condition check
//
//                        }
//                        else
//                        {
//
//                            Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                    } else {
//
//                        Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT).show();
//
//                }
//
//                //}// validation
//
//            }//run end
//
//        });//runnable end
//
//
//    }//fetch driver location update End

    public void getGoogleDirections()
    {

        new AlertDialog.Builder(RiderMapActivity.this)
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

                            new AlertDialog.Builder(RiderMapActivity.this)
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



    public void callTimer()
    {

        Intent i = new Intent(getApplicationContext(), TimerActivity.class);
        Bundle bundle = new Bundle();
        i.putExtras(bundle);
        startActivity(i);


    }



}//class end


//    public void setDriverInfoList_old(){
//
//        try{
//
//            //remove all views before add
//
//            if(((LinearLayout) rootLinearLayout).getChildCount() > 0)
//                ((LinearLayout) rootLinearLayout).removeAllViews();
//
//
//
//            Log.d(TAG, "driverTabs length: " + driverTabs.length());
//
//            //--to give space betweeen drivers
//
//            for (int i = 0; i <= driverTabs.length()-1; i++) {
//
//
//                Log.d(TAG, "driverTabs status: " + driverTabs.optJSONObject(i).getString("status"));
//                Log.d(TAG, "driverTabs disabled: " + driverTabs.optJSONObject(i).getString("disabled"));
//
//                if(driverTabs.optJSONObject(i).getString("status").equals(GlobalConstants.ACTIVE_CODE) && driverTabs.optJSONObject(i).getString("disabled").equals(GlobalConstants.NO_CODE)) {
//
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
//                    tv_driverName.setText(driverTabs.optJSONObject(i).getString("driverName"));
//                    tv_driverName.setPadding(0, 0, 0, 0);
//                    tv_driverName.setTextColor(Color.DKGRAY);
//                    tv_driverName.setGravity(Gravity.CENTER);
//
//
//                    tv_destination.setText(driverTabs.optJSONObject(i).getString("destination"));
//                    tv_destination.setPadding(2, 2, 2, 0);
//                    tv_destination.setTextColor(Color.RED);
//                    tv_destination.setGravity(Gravity.CENTER);
//
//
//                    tv_mobileNo.setText(driverTabs.optJSONObject(i).getString("mobileNo"));
//                    tv_mobileNo.setPadding(2, 2, 2, 2);
//
//                    tv_mobileNo.setTextColor(Color.rgb(34,139,34)); //FOREST GREEN
//                    // Set TextView font/text size to 25 dp
//                    tv_mobileNo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
//                    tv_mobileNo.setGravity(Gravity.CENTER | Gravity.TOP);
//
//
//                    //image adding
//                    ImageView driverImage = new ImageView(this);
//                    driverImage.setAdjustViewBounds(true);
//
//
//                    //Log.d(TAG, "driverImage Found "+driverTabs.optJSONObject(i).getString("imageFound"));
//
//                    if(driverTabs.optJSONObject(i).getBoolean("imageFound")==true){
//
//                        Log.d(TAG, "driverImage Found 1");
//
//                        // Receiving side
//                        byte[] data = Base64.decode(driverTabs.optJSONObject(i).getString("driverImage"), Base64.DEFAULT);
//
//                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//                        driverImage.setImageBitmap(bmp);
//
//
//                    }else {
//
//                        driverImage.setImageResource(R.drawable.avatar_black_24dp);
//
//                    }
//
//                    //driverImage.setPadding(1, 1, 1, 1);
//                    //driverImage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//
//
//                    ImageView vehicleImage = new ImageView(this);
//
//
//                    if(driverTabs.optJSONObject(i).getString("vehicleType").equals(GlobalConstants.CAB_CODE)) {
//
//                        vehicleImage.setImageResource(R.drawable.caroutline);
//                    }
//                    else{
//                        vehicleImage.setImageResource(R.drawable.autotop_32x32);
//                    }
//                    vehicleImage.setAdjustViewBounds(true);
//                    //vehicleImage.setBackgroundResource(R.drawable.background_circle);
//                    //vehicleImage.setPadding(1, 1, 1, 1);
//
//
//                    //image adding
//                    final ImageView dialImage = new ImageView(this);
//                    dialImage.setImageResource(R.drawable.dialer_icon48x48);
//                    //dialImage.setPadding(10, 10, 10, 10);
//
//                    //ImageButton imageButton = new ImageButton(this);
//
//                    //imageButton.setImageResource(R.drawable.dial_icon);
//
//                    dialImage.setTag(driverTabs.optJSONObject(i).getString("mobileNo"));
//
//                    dialImage.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//
//
//                            Log.d(TAG, "Dial Button Click: ");
//
//                            //callIntent.setData(Uri.parse("tel:9030209883"));
//                            // update Log
//                            NowcabsLog nowcabsLog=new NowcabsLog();
//
//
//
//                            // Here, thisActivity is the current activity
//                            if (ContextCompat.checkSelfPermission(RiderMapActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//                                // Should we show an explanation?
//                                if (ActivityCompat.shouldShowRequestPermissionRationale(RiderMapActivity.this, Manifest.permission.CALL_PHONE)) {
//
//                                    // Show an explanation to the user *asynchronously* -- don't block
//                                    // this thread waiting for the user's response! After the user
//                                    // sees the explanation, try again to request the permission.
//
//                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                                    callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
//                                    startActivity(callIntent);
//
//                                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE,rider.riderID,"Dialed to "+dialImage.getTag());
//
//                                } else {
//
//                                    // No explanation needed, we can request the permission.
//
//                                    ActivityCompat.requestPermissions(RiderMapActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
//
//                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                                    callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
//                                    startActivity(callIntent);
//
//                                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE,rider.riderID,"Dialed to "+dialImage.getTag());
//
//                                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                                    // app-defined int constant. The callback method gets the
//                                    // result of the request.
//                                }
//                            } else {
//                                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                                callIntent.setData(Uri.parse("tel:" + dialImage.getTag()));
//                                startActivity(callIntent);
//
//                                nowcabsLog.updateLog(GlobalConstants.RIDER_CODE,rider.riderID,"Dialed to "+dialImage.getTag());
//
//                            }
//
//
//                            Log.d(TAG, "Dial Button Click end: " + dialImage.getTag());
//
//                        }
//                    });
//
//                    //------end of Dial Image
//
//
//                    //-----start sms adding
//                    final ImageView smsImage = new ImageView(this);
//                    smsImage.setImageResource(R.drawable.sms_icon48x48);
//                    //smsImage.setPadding(10, 10, 10, 10);
//
//                    final String riderMobileNo = rider.getRiderMobileNo();
//
//
//                    final String driverID = driverTabs.optJSONObject(i).getString("driverID");
//                    smsImage.setTag(driverID);
//                    smsImage.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//
//
//                            Log.d(TAG, "SMS Button Click: ");
//
//
//                            new AlertDialog.Builder(RiderMapActivity.this)
//                                    .setTitle("Book")
//                                    .setMessage("Do you want to book cab?")
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//
//
//
//                                            Calendar c = Calendar.getInstance();
//                                            System.out.println("Current time => "+c.getTime());
//                                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//                                            String formattedDate = df.format(c.getTime());
//
//                                            Map<String, Object> smsMap = new HashMap<>();
//                                            smsMap.put("riderSMS", riderMobileNo);
//                                            smsMap.put("datetime", formattedDate);
//                                            firebaseDatabase.getReference(GlobalConstants.FIREBASE_DRIVER_ALERT_PATH+"/"+ driverID).updateChildren(smsMap);
//
//                                            // update Log
//                                            NowcabsLog nowcabsLog=new NowcabsLog();
//                                            nowcabsLog.updateLog(GlobalConstants.RIDER_CODE,rider.riderID,"SMS to "+dialImage.getTag());
//
//
//                                        }
//                                    })
//                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                                            dialogInterface.dismiss();
//                                        }
//                                    })
//                                    .create()
//                                    .show();
//
//
//
//                        }
//                    });
//
//                    //-----end of SMS Image
//
//
//                    driverInfoLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//                    driverInfoLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//                    driverInfoLinearLayout.setGravity(Gravity.CENTER);
//
//                    driverInfoLinearLayout.addView(vehicleImage);
//                    driverInfoLinearLayout.addView(driverImage);
//
//                    //adding multiple textviews
//                    LinearLayout ll = new LinearLayout(this);
//                    ll.setOrientation(LinearLayout.VERTICAL);
//                    ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//                    ll.setGravity(Gravity.CENTER);
//                    ll.addView(tv_driverName);
//                    ll.addView(tv_destination);
//                    ll.addView(tv_mobileNo);
//
//
//                    //adding multiple textviews
////                    LinearLayout dialLL = new LinearLayout(this);
////                    dialLL.setOrientation(LinearLayout.HORIZONTAL);
////                    dialLL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
////                    dialLL.setGravity(Gravity.CENTER);
////                    dialLL.addView(dialImage);
////                    dialLL.addView(smsImage);
//
//
//                    driverInfoLinearLayout.addView(ll);
//
//
//                    driverInfoLinearLayout.addView(dialImage);
//                    driverInfoLinearLayout.addView(smsImage);
//                    //driverInfoLinearLayout.addView(dialLL);
//
//
//                    rootLinearLayout.addView(driverInfoLinearLayout);
//
//                    //layoutParams.setMargins(0, 0, 100, 0);
//
//
//                    //int dividerHeight = getResources().getDisplayMetrics().density * 1; // 1dp to pixels
//                    // viewDivider.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight));
//                } //if condition for disabled
//
//            }//for loop
//
//
//
//
//
//        }
//        catch (Exception e){
//            Log.d(TAG, "setDriverInfoList Exception");
//            e.printStackTrace();
//        }
//
//    } //end of setDriverInfoList_old


//---------rightside dotted menu hide it
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//
//        menu.add(Menu.NONE,R.id.menu_mobileNo,0,rider.getRiderMobileNo()).setIcon(R.drawable.avatar_24dp);
//        menu.add(Menu.NONE,R.id.menu_name,1,rider.getRiderName());
//        menu.add(Menu.NONE,R.id.menu_riderID,2,rider.getRiderID());
//        menu.add(Menu.NONE,R.id.menu_logout,3,"Logout");
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
//            Toast.makeText(RiderMapActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        if (id == R.id.menu_logout) {
//            //Toast.makeText(RiderMapActivity.this, "Action Logout", Toast.LENGTH_LONG).show();
//
//            //To clear SharedPreferences
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            //editor.clear();
//            //Do not clear all as last current lat and current lng required in case onLocationChange is not fired
////            editor.putString("mobileNoKey", "");
////            editor.putString("firstNameKey", "");
////            editor.putString("userGroupKey", "");
////            editor.putString("idKey", "");
//            editor.putString("autoLoginKey", GlobalConstants.NO_CODE);
//            editor.commit();
//
//
//            //update Log
//            NowcabsLog nowcabsLog=new NowcabsLog();
//            nowcabsLog.updateLog(GlobalConstants.RIDER_CODE,rider.riderID,"Logout");
//
//
//            Intent intent = new Intent(RiderMapActivity.this, RiderLoginActivity.class);
//            startActivity(intent);
//            finish();
//
//
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    //05-Oct-2018 --Firebase suppress
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//
//        if (mChildEventListener != null) {
//            dbRef.removeEventListener(mChildEventListener);
//        }
//
//    }

//        //------------Camera function -----------
//        public void openFrontCamera() {
//
//        try {
//
//            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//
//            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//
//            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//
//            // do we have a camera?
//            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
//                Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
//                        .show();
//            } else {
//                cameraId = findFrontFacingCamera();
//
//                Log.d(TAG, "cameraId  " + cameraId);
//
//                if (cameraId < 0) {
//                    Toast.makeText(this, "No front facing camera found.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//
//                    Log.d(TAG, "Camera To Open");
//
//                    mCamera = Camera.open(cameraId);
//
//                }
//
//
//            }
//
//
//            //Parameters parameters = mCamera.getParameters();
//            //parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
//            //mCamera.setParameters(parameters);
//
//            mCamera.startPreview();
//            mCamera.takePicture(null, null, new PhotoHandler(getApplicationContext()));
//
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//        }
//
//        private int findFrontFacingCamera() {
//
//        try{
//
//                    int cameraId = -1;
//                    // Search for the front facing camera
//                    int numberOfCameras = Camera.getNumberOfCameras();
//
//                    Log.d(TAG, "numberOfCameras "+numberOfCameras);
//
//                    for (int i = 0; i < numberOfCameras; i++) {
//                        Camera.CameraInfo info = new Camera.CameraInfo();
//                        Camera.getCameraInfo(i, info);
//
//                        Log.d(TAG, "info.facing "+i+" "+info.facing);
//
//                        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//
//                            Log.d(TAG, "Camera found");
//                            cameraId = i;
//                            break;
//                        }
//                    }
//
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//            return cameraId;
//
//
//        }
//
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
////        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
////            Bundle extras = data.getExtras();
////            Bitmap imageBitmap = (Bitmap) extras.get("data");
////
////            Log.d(TAG, "imageBitmap "+imageBitmap);
////
////            //mImageView.setImageBitmap(imageBitmap);
////        }
//
//            try{
//
//                    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//                        Bundle extras = data.getExtras();
//                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
//
//                        //Log.d(TAG, "imageBitmap "+imageBitmap);
//                        // ImageView imageview = (ImageView) findViewById(R.id.ImageView01);
//                        //imageview.setImageBitmap(image);
//
//                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//
//                        //Bitmap circleBitmap=getCircleBitmap(imageBitmap);
//
//                        Bitmap circleBitmap=getCroppedBitmap(imageBitmap,imageBitmap.getWidth());
//
//                        circleBitmap.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
//                        circleBitmap.recycle();
//
//                        byte[] byteArray = bao.toByteArray();
//                        String imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//                        StorageReference storageRef = firebaseStorage.getReference();
//
//                        final StorageReference  avatarRef = storageRef.child(GlobalConstants.FB_IMAGE_FOLDER + rider.getRiderID()+"_avatar.jpg");
//
//                        UploadTask uploadTask = avatarRef.putBytes(byteArray);
//                        uploadTask.addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle unsuccessful uploads
//                                Toast.makeText(RiderMapActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                                // ...
//                                Toast.makeText(RiderMapActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
//
//
//
//                            }
//                        });
//
//                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                            @Override
//                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                if (!task.isSuccessful()) {
//                                    throw task.getException();
//                                }
//
//                                // Continue with the task to get the download URL
//                                return avatarRef.getDownloadUrl();
//                            }
//                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                if (task.isSuccessful()) {
//                                    Uri downloadUri = task.getResult();
//
//                                    setRiderImage(downloadUri);
//
//                                } else {
//                                    // Handle failures
//                                    // ...
//                                }
//                            }
//                        });
//        //                //05-Oct-2018
//        //                dbRef = firebaseDatabase.getReference(GlobalConstants.FIREBASE_RIDER_PATH +"/"+rider.getRiderID());
//        //                Log.d(TAG, "imageBitmap-1");
//        //                Map<String, Object> riderImage = new HashMap<>();
//        //                riderImage.put("imageFound",true);
//        //                riderImage.put("riderImage",imageB64);
//        //
//        //                Log.d(TAG, "imageBitmap-2");
//        //                dbRef.updateChildren(riderImage);
//
//
//
//                        //--setting profile image immediately
//                        //setRiderImage(imageB64);
//
//                        Log.d(TAG, "imageBitmap-3");
//                    }
//
//            }
//            catch (Exception ex)
//            {
//                ex.printStackTrace();
//            }
//
//
//        }
//
//
//        public void getRiderImage()
//        {
//
//            StorageReference storageRef = firebaseStorage.getReference();
//            final StorageReference  avatarRef = storageRef.child(GlobalConstants.FB_IMAGE_FOLDER + rider.getRiderID()+"_avatar.jpg");
//
//             avatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    // Got the download URL for 'users/me/profile.png'
//
//                    Log.d(TAG, "onSuccess  " + uri);
//
//                    setRiderImage(uri);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                    //Toast.makeText(RiderMapActivity.this, "Image download failed", Toast.LENGTH_SHORT).show();
//                    riderImage.setImageResource(R.drawable.avatar_24dp);
//
//                }
//            });
//        }
//
//        public void setRiderImage(Uri uri){
//
//
//            Picasso.get().load(uri).into(riderImage); //http://square.github.io/picasso/
//
//
////            // Receiving side
////            byte[] data = Base64.decode(riderImageData, Base64.DEFAULT);
////
////            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
////
////            Bitmap bitmap = Bitmap.createScaledBitmap(bmp,120, 120, true);
////
////            riderImage.setImageBitmap(bitmap);
//
//
//        }
//        //-------------end of camera function
//