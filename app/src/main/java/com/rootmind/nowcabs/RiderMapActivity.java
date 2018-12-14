package com.rootmind.nowcabs;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnFailureListener;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
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


import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.GoogleMap.*;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.ChildEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.util.Log;

import android.app.AlertDialog;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.w3c.dom.Text;

//import  android.graphics.*;


import android.net.Uri;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;


import android.location.Geocoder;
import android.location.Address;

import java.io.IOException;

import android.location.Criteria;


//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//
//import android.util.Base64;


import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

//import static com.rootmind.nowcabs.DriverMapActivity.getCroppedBitmap;


import com.google.android.material.bottomsheet.BottomSheetBehavior;

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
        GroupSelectionAdapter.ItemClickListener,
        OnInfoWindowClickListener, View.OnClickListener

{

    private static final String TAG = "RiderMapActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    Location mLastKnownLocation;
    Marker mCurrLocationMarker;
    Location mMarkerLocation;
    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback;
    ListenerRegistration registration;



    // create map to store
    Map<String, Object> markers = new HashMap<String, Object>();

    private Marker vehicleMarker;



    Rider rider;
    Parameter parameter;


    float bearing = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    PlaceAutocompleteFragment autocompleteFragment;

    private static final int MENU_ITEM1 = 1;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    Double currentLat = 0.0;
    Double currentLng = 0.0;
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
    public DrawerLayout mDrawerLayout;




    private List<Rider> servicesList;
    private List<GroupRider> groupList;
    private List<Service> serviceCountList;


    private RecyclerView recyclerView;
    private RecyclerView servicesRecyclerView;
    private RecyclerView groupRecyclerView;


    private ServiceAdapter serviceAdapter;
    private ServiceSelectionAdapter serviceSelectionAdapter;
    private GroupSelectionAdapter groupSelectionAdapter;



    private AddressResultReceiver mResultReceiver;
    private String mAddressOutput;




    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

    LinearLayout layoutBottomSheetService;
    BottomSheetBehavior serviceSheetBehavior;

    LinearLayout layoutBottomSheetGroup;
    BottomSheetBehavior groupSheetBehavior;


    FloatingActionButton btn_service;
    String currentServiceMarker;

    BottomNavigationView bottomNavigationView;

    private TextView tv_no_records;
    private Button btn_close;
    private Button btn_group_close;



    Configuration config;

    Locale locale;

    String destination = null;

    public LinearLayout loadingSpinner;
    FirebaseFirestore firebaseFirestore;


    public CardView cv_carpenter;
    public CardView cv_autoDriver;
    public CardView cv_cabDriver;
    public CardView cv_electrician;
    public CardView cv_plumber;
    public CardView cv_tailor;
    public CardView cv_washer;
    public CardView cv_courier;
    public CardView cv_merchant;

    public CardView cv_movers;
    public CardView cv_housekeeper;
    public CardView cv_cook;
    public CardView cv_painter;
    public CardView cv_florist;
    public CardView cv_pesticide;
    public CardView cv_tutor;
    public CardView cv_locksmith;
    public CardView cv_grinder;



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



    MyLocation myLocation = new MyLocation();

    protected void startIntentService() {

        if (mMarkerLocation != null) {

            Intent intent = new Intent(this, FetchAddressIntentService.class);
            intent.putExtra(GlobalConstants.RECEIVER, mResultReceiver);
            intent.putExtra(GlobalConstants.LOCATION_DATA_EXTRA, mMarkerLocation);
            //startService(intent);
            FetchAddressIntentService.enqueueWork(this, intent);

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

        setServicesView();

        if (firebaseFirestore == null)
        {
            firebaseFirestore = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build();
            firebaseFirestore.setFirestoreSettings(settings);
        }


        servicesList = new ArrayList<>();
        groupList = new ArrayList<>();
        serviceCountList = new ArrayList<>();





        driverData = new HashMap<>();



        rider = (Rider) getIntent().getSerializableExtra("Rider");

        parameter = new Parameter();

        Log.d(TAG, "RiderMap Rider: " + rider.getRiderID());

        // For Auto compelte of locations
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(15.0f);


        myLocation.getLocation(getApplicationContext(), locationResult);




        //-----------side nav image
        ImageView searchIcon = (ImageView) ((LinearLayout) autocompleteFragment.getView()).getChildAt(0);
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.sidenav));
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
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


        //--- set parameters
        setParameters();

        firestoreListener();

        buildGoogleApiClient();


        //right side service button
        btn_service = (FloatingActionButton) findViewById(R.id.btn_service);
        currentServiceMarker = GlobalConstants.TRANSPORT_AUTO_SERVICE;



        // location permission for map to load
        checkLocationPermission();

        loadingSpinner = ((LinearLayout)findViewById(R.id.progressBarLayout));

        hideProgressBar();




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




        //sidenav header
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //Called when a drawer's position changes.
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                navigationView.getMenu().findItem(R.id.nav_version).setTitle(CommonService.getAppVersion());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // Called when a drawer has settled in a completely closed state.
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Called when the drawer motion state changes. The new state will be one of STATE_IDLE, STATE_DRAGGING or STATE_SETTLING.
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




        //21-Sep-2018
        //------------------
        recyclerView = (RecyclerView) findViewById(R.id.driver_recycler_view);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        btn_close = (Button) findViewById(R.id.btn_close);
        serviceAdapter = new ServiceAdapter(servicesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(serviceAdapter);
        serviceAdapter.setClickListener(this);
        //----------------

        //-----------recycler for list service selecton-------
        servicesRecyclerView = (RecyclerView) findViewById(R.id.services_recycler_view);
        serviceSelectionAdapter = new ServiceSelectionAdapter(serviceCountList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        servicesRecyclerView.setLayoutManager(layoutManager);
        servicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        servicesRecyclerView.setAdapter(serviceSelectionAdapter);
        serviceSelectionAdapter.setClickListener(this);
        //----------------

        //-----------recycler for list group selecton-------
        groupRecyclerView = (RecyclerView) findViewById(R.id.group_bottom_recycler_view);
        btn_group_close = (Button) findViewById(R.id.btn_group_close);
        groupSelectionAdapter = new GroupSelectionAdapter(groupList);
        RecyclerView.LayoutManager groupLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        groupRecyclerView.setLayoutManager(groupLayoutManager);
        groupRecyclerView.setItemAnimator(new DefaultItemAnimator());
        groupRecyclerView.setAdapter(groupSelectionAdapter);
        groupSelectionAdapter.setClickListener(this);
        //----------------


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetClose();
            }
        });

        btn_group_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomGroupSheetClose();
            }
        });



        //18-Sep-2018
        mResultReceiver = new AddressResultReceiver(new Handler());


        // get the bottom sheet view
        layoutBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        // -----service selection sheet view
        layoutBottomSheetService = (LinearLayout) findViewById(R.id.bottom_sheet_service);
        serviceSheetBehavior = BottomSheetBehavior.from(layoutBottomSheetService);

        // -----group selection sheet view
        layoutBottomSheetGroup = (LinearLayout) findViewById(R.id.bottom_sheet_group);
        groupSheetBehavior = BottomSheetBehavior.from(layoutBottomSheetGroup);


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
        bottomServiceSheetClose();
        //bottomServiceSheetOpen();

        //---------end of bottom sheet


        //-----------bottom sheet for group selection
        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        groupSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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
        bottomGroupSheetClose();


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

                                //bottomServiceSheetOpen();


                                break;
                            case R.id.action_group:

                                item.setCheckable(true);
                                item.setChecked(true);


                                new fetchRegisteredGroupsProgressTask().execute();


                                break;

                            case R.id.action_share:



                                //performShare();

                                break;


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

        //do not show button
        //btn_service.setVisibility(View.GONE);





    }//------end of create


    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

        @Override
        public void gotLocation(Location location) {

            riderLat = location.getLatitude();
            riderLng = location.getLongitude();

            rider.setRiderLat(riderLat);
            rider.setRiderLng(riderLng);

            new RiderMapActivity.updateRiderLocation().execute(new Object[]{null, null, false});


        }
    };


    public void bottomSheetOpen() {
        sheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        if (serviceAdapter.getItemCount() > 0) {
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

        serviceCountList.clear();
        Service service = new Service();
        serviceCountList.add(service);
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

    }

    public void bottomGroupSheetOpen() {


        if (groupSelectionAdapter.getItemCount() > 0) {

            groupSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
            groupSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            groupRecyclerView.setVisibility(View.VISIBLE);

        } else {
            groupRecyclerView.setVisibility(View.GONE);
        }

    }

    public void bottomGroupSheetClose() {
        groupSheetBehavior.setPeekHeight(0);
        groupSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        //Toast.makeText(this, "Info window clicked",Toast.LENGTH_SHORT).show();

        Rider serviceGeo = (Rider) marker.getTag();

        //setDialImage(driverGeo);
        callDialer(serviceGeo);

    }


    @Override
    public void onClickAvatarImage(View view, int position) {


        Rider serviceGeo = servicesList.get(position);

        callDialer(serviceGeo);



    }

    @Override
    public void onClickDialImage(View view, int position) {


        Rider serviceGeo = servicesList.get(position);

        Log.i(TAG, "serviceGeo servicecode " + serviceGeo.getServiceCode());

        //this opens the ringer tone
        callDialer(serviceGeo);

        //setDialImage(rider);


    }

    @Override
    public void onClickSMSImage(View view, int position) {


        Rider rider = servicesList.get(position);

        setSMSImage(rider);


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


    }

    //---------Service Selection Click Events
    @Override
    public void onClickCarpenter(View view, int position) {

        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_CARPENTER, null, false,null});

    }
    @Override
    public void onClickAutoDriver(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_AUTO_DRIVER, GlobalConstants.AUTO_CODE, false,null});

    }
    @Override
    public void onClickCabDriver(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_CAB_DRIVER, GlobalConstants.CAB_CODE, false, null});

    }
    @Override
    public void onClickElectrician(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_ELECTRICIAN, null, false, null});

    }
    @Override
    public void onClickPlumber(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_PLUMBER, null, false, null});

    }
    @Override
    public void onClickTailor(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_TAILOR, null, false, null});

    }
    @Override
    public void onClickWasher(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_WASHER, null, false, null});

    }
    @Override
    public void onClickCourier(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_COURIER, null, false, null});

    }
    @Override
    public void onClickMerchant(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_MERCHANT, null, false, null});

    }
    @Override
    public void onClickMovers(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_MOVERS, null, false, null});

    }
    @Override
    public void onClickHousekeeper(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_HOUSEKEEPER, null, false, null});

    }
    @Override
    public void onClickCook(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_COOK, null, false, null});

    }
    @Override
    public void onClickPainter(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_PAINTER, null, false, null});

    }
    @Override
    public void onClickFlorist(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_FLORIST, null, false, null});

    }
    @Override
    public void onClickPesticide(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_PESTICIDE, null, false, null});

    }
    @Override
    public void onClickTutor(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_TUTOR, null, false, null});

    }
    @Override
    public void onClickLocksmith(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_LOCKSMITH, null, false, null});

    }
    @Override
    public void onClickGrinder(View view, int position) {


        bottomServiceSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_GRINDER, null, false, null});

    }

    //--------end of service selection events


    //-------group selection event
    @Override
    public void onClickGroup(View view, int position) {


        GroupRider groupRider = groupList.get(position);

        bottomGroupSheetClose();
        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_GROUP, null, false, groupRider});


    }
    //---------end of group selection

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
        //mMap.setPadding(0, 0, 0, 200);


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

                riderLat = location.getLatitude();
                riderLng = location.getLongitude();

                rider.setRiderLat(riderLat);
                rider.setRiderLng(riderLng);

                new RiderMapActivity.updateRiderLocation().execute(new Object[]{null, null, false});


                //-------to get initial location details
                markerLat = location.getLatitude();
                markerLng = location.getLongitude();

                new fetchServiceCountProgressTask().execute();
                //-------------


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
                bottomGroupSheetClose();
            }
        });


        //------

    }




    protected synchronized void buildGoogleApiClient() {

        //---------Try to set in onCreate itself
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        connectGoogleClient();

        mLocationRequest = new LocationRequest();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        //mLocationRequest.setSmallestDisplacement(10);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                //onLocationChanged(mCurrentLocation);
//                for (Location location : locationResult.getLocations()) {
//                    // Update UI with location data
//                }
            }

            ;
        };
        //-----------



    }

    private void connectGoogleClient() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(this);
        if (resultCode == ConnectionResult.SUCCESS) {
            mGoogleApiClient.connect();
        } else {
            int REQUEST_GOOGLE_PLAY_SERVICE = 988;
            googleAPI.getErrorDialog(this, resultCode, REQUEST_GOOGLE_PLAY_SERVICE);
        }
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

                //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            }
        }


//        //get last known location
//        if (mGoogleApiClient != null) {
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                    mGoogleApiClient);
//            if (mLastLocation != null) {
//                currentLat = mLastLocation.getLatitude();
//                currentLng = mLastLocation.getLongitude();
//                mCurrentLocation = mLastLocation;
//
//                //21-Sep-2018
//                mMarkerLocation = mLastLocation;
//
//                LatLng latLng = new LatLng(currentLat, currentLng);
//                Log.i(TAG, "GoogleAPI Zoom ");
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));
//            }
//        }


        if(mGoogleApiClient !=null) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            mLastKnownLocation = location;

                            // In some rare cases the location returned can be null
                            if (mLastKnownLocation == null) {
                                return;
                            }

                            currentLat = mLastKnownLocation.getLatitude();
                            currentLng = mLastKnownLocation.getLongitude();
                            mCurrentLocation = mLastKnownLocation;

                            rider.setRiderLat(currentLat);
                            rider.setRiderLng(currentLng);
                            //rider.setRiderLocation(mCurrentLocation);

                            //21-Sep-2018
                            mMarkerLocation = mLastKnownLocation;

                            LatLng latLng = new LatLng(currentLat, currentLng);
                            Log.i(TAG, "GoogleAPI Zoom ");
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));

                        }
                    });

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

        mLastKnownLocation = location;


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


            rider.setRiderLat(riderLat);
            rider.setRiderLng(riderLng);
            rider.setRiderLocation(riderLocation);

            new RiderMapActivity.updateRiderLocation().execute(new Object[]{null, null, false});


        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d(TAG, "onLocationChanged Exception");
            Log.e("tag", e.getMessage());
            e.printStackTrace();
        }


//        //stop location updates
//        if (mGoogleApiClient != null) {
//            //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
//
//        }

        //21-Sep-2018
        showReverseGeocoder();

        Log.d(TAG, "onLocationChanged before fetchDriver");

        //default get auto markers on the map
        //new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_DRIVER, GlobalConstants.AUTO_CODE, true});


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


    @Override
    protected void onDestroy() {
        //Remove location update callback here
        if(registration!=null)
        {
            registration.remove();
        }
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        super.onDestroy();
    }




    public void setServiceLocation(int distance, Rider riderGeo) {


        try {


            Log.d(TAG, "driverGeo: " + riderGeo.riderID + " " + riderGeo.status + " " + riderGeo.vacantStatus);

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


            //stop showing red marker when vehicle marker is displayed
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }


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

//                    if (riderGeo.getVehicleType().equals(GlobalConstants.CAB_CODE)) {
//                        //for cab image
//                        vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cartop));
//                    } else {
//                        //For auto image
//                        vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autotop_32x32));
//                    }

                    //------set profession image
                    switch (riderGeo.getServiceCode()) {

                        case GlobalConstants.SERVICE_CARPENTER:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.carpenter));
                            break;
                        }
                        case GlobalConstants.SERVICE_COURIER:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.courier));
                            break;

                        }
                        case GlobalConstants.SERVICE_AUTO_DRIVER:
                        {

                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.auto_outline));
                            break;

                        }
                        case GlobalConstants.SERVICE_CAB_DRIVER:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cab_outline));
                            break;

                        }
                        case GlobalConstants.SERVICE_ELECTRICIAN:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.electrician));
                            break;

                        }
                        case GlobalConstants.SERVICE_MERCHANT:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.merchant));
                            break;

                        }
                        case GlobalConstants.SERVICE_PLUMBER:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.plumber));
                            break;

                        }
                        case GlobalConstants.SERVICE_TAILOR:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tailor));
                            break;

                        }
                        case GlobalConstants.SERVICE_WASHER:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.washer));
                            break;

                        }
                        case GlobalConstants.SERVICE_MOVERS:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.movers));
                            break;

                        }
                        case GlobalConstants.SERVICE_HOUSEKEEPER:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.housekeeper));
                            break;

                        }
                        case GlobalConstants.SERVICE_COOK:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cook));
                            break;

                        }
                        case GlobalConstants.SERVICE_PAINTER:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.painter));
                            break;

                        }
                        case GlobalConstants.SERVICE_FLORIST:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.florist));
                            break;

                        }
                        case GlobalConstants.SERVICE_PESTICIDE:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pesticide));
                            break;

                        }
                        case GlobalConstants.SERVICE_TUTOR:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tutor));
                            break;

                        }
                        case GlobalConstants.SERVICE_LOCKSMITH:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.locksmith));
                            break;

                        }
                        case GlobalConstants.SERVICE_GRINDER:
                        {
                            vehicleMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.grinder));
                            break;

                        }


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
                    callIntent.setData(Uri.parse("tel:" + driverGeo.getMobileNo()));
                    startActivity(callIntent);

                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getMobileNo());

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(RiderMapActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + driverGeo.getMobileNo()));
                    startActivity(callIntent);

                    nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getMobileNo());

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + driverGeo.getMobileNo()));
                startActivity(callIntent);

                nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Dialed to " + driverGeo.getMobileNo());

            }


            Log.d(TAG, "Dial Button Click end: " + driverGeo.getMobileNo());


        } catch (Exception e) {
            Log.d(TAG, "setDialImage Exception");
            e.printStackTrace();
        }


    }//------end of Dial Image




    public void setSMSImage(final Rider driverGeo) {


        try {


            final String riderMobileNo = rider.getMobileNo();


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

        //21-Sep-2018
        createMarker(mMap.getCameraPosition().target);


        //18-Sep-2018
        showReverseGeocoder();


        new fetchServiceCountProgressTask().execute();


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

//        //Place current location marker
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);


    }



    public void setParameters() {


        Log.d(TAG, "setParameters");

        try {
            //String country = parameter.getCountry();
            //driverRadius = parameter.getDriverRadius();
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

        showProgressBar();


        switch (id) {



            case R.id.nav_profile: {

                Intent i = new Intent(getApplicationContext(), RiderProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("RegisterFlag", false);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }

            case R.id.nav_driver: {

                Intent i = new Intent(getApplicationContext(), DriverLoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("RegisterFlag", false);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }
            case R.id.nav_idcard: {

                Intent i = new Intent(getApplicationContext(), IDCardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("RegisterFlag", false);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }
            case R.id.nav_service: {


                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("RegisterFlag", false);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }

            case R.id.nav_myRequests: {


                Intent i = new Intent(getApplicationContext(), RidesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("rideType", GlobalConstants.RIDER_TYPE);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }
            case R.id.nav_myService: {

                Intent i = new Intent(getApplicationContext(), RidesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                bundle.putSerializable("rideType", GlobalConstants.SERVICER_TYPE);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }

            case R.id.nav_group: {

                Intent i = new Intent(getApplicationContext(), GroupActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }

            case R.id.nav_job: {

                Intent i = new Intent(getApplicationContext(), JobActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }

            case R.id.nav_myGroup: {

                Intent i = new Intent(getApplicationContext(), GroupRegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Rider", rider);
                i.putExtras(bundle);
                startActivity(i);
                break;

            }

            case R.id.nav_logout: {

                logout();
                break;

            }



        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);

        hideProgressBar();

        return true;
    }

    @Override
    public void onBackPressed() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
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


//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        mLastKnownLocation = location;
//
//                        // In some rare cases the location returned can be null
//                        if (mLastKnownLocation == null) {
//                            return;
//                        }
//
////                        if (!Geocoder.isPresent()) {
////                            Toast.makeText(RiderMapActivity.this,
////                                    "no_geocoder_available",
////                                    Toast.LENGTH_LONG).show();
////                            return;
////                        }
////
////                        // Start service and update UI to reflect new location
////                        startIntentService();
////
////                        //updateUI();
////                        //autocompleteFragment.setText(mAddressOutput);
//
//                    }
//                });
    }




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

            showProgressBar();
            //tv_searchService.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Object...params ) {

//            Log.d(TAG, "param[0] "+params[0].toString());
//            Log.d(TAG, "param[1] "+params[1].toString());
//            Log.d(TAG, "param[2] "+Boolean.valueOf(params[2].toString()));

            fetchServiceLocation(params[0].toString(),(params[1]==null?null:params[1].toString()),Boolean.valueOf(params[2].toString()), (GroupRider)params[3]);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            //loadingSpinner.setVisibility(View.GONE);

            hideProgressBar();
            //tv_searchService.setVisibility(View.GONE);

        }
    }
                    //--------fetch service Location  in the backend
    public void fetchServiceLocation(final String service, final String vehicleType, final boolean switchService, final GroupRider groupRider) {




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
                    if(service.equals(GlobalConstants.SERVICE_GROUP))
                    {
                        messageJson.put("groupRefNo", groupRider.getGroupRefNo());

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
                                        rider.setMobileNo(wrapperArrayObj.optJSONObject(i).optString("mobileNo"));
                                        rider.setVehicleNo(wrapperArrayObj.optJSONObject(i).optString("vehicleNo"));
                                        rider.setStatus(wrapperArrayObj.optJSONObject(i).optString("status"));
                                        rider.setVehicleType(wrapperArrayObj.optJSONObject(i).optString("vehicleType"));
                                        rider.setRiderLat(wrapperArrayObj.optJSONObject(i).getDouble("currentLat"));
                                        rider.setRiderLng(wrapperArrayObj.optJSONObject(i).getDouble("currentLng"));
                                        rider.setRiderLocation(wrapperArrayObj.optJSONObject(i).optString("currentLocation"));
                                        rider.setVacantStatus(wrapperArrayObj.optJSONObject(i).optString("vacantStatus"));
                                        rider.setDistance(wrapperArrayObj.optJSONObject(i).getDouble("distance"));
                                        rider.setDuration(wrapperArrayObj.optJSONObject(i).getDouble("duration"));
                                        rider.setFavorite(wrapperArrayObj.optJSONObject(i).optString("favorite"));
                                        rider.setAvgRating(wrapperArrayObj.optJSONObject(i).getDouble("avgRating"));
                                        rider.setYourRating(wrapperArrayObj.optJSONObject(i).getDouble("yourRating"));
                                        rider.setServiceCode(wrapperArrayObj.optJSONObject(i).optString("serviceCode"));

                                        Log.d(TAG, "Avg Rating: " + wrapperArrayObj.optJSONObject(i).optString("avgRating"));
                                        Log.d(TAG, "Your Rating: " + wrapperArrayObj.optJSONObject(i).optString("yourRating"));

                                        JSONArray imageWrappers = wrapperArrayObj.optJSONObject(i).optJSONArray("imageWrappers");

                                        if(imageWrappers!=null)
                                        {

                                            rider.imageWrappers = new Image[imageWrappers.length()];
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
                                                    rider.imageWrappers[j] = image;

                                                }
                                            }

                                        }


                                        //Log.d(TAG, "before servicelist add: " + rider.getRiderLat() + " : " + rider.getRiderLng());

                                        servicesList.add(rider);
                                        serviceAdapter.notifyDataSetChanged();

                                        //only if right top button is clicked
                                        if (switchService == true) {
                                            setServiceLocation(0, rider);
                                        }


                                    }
                                }


                                if(switchService==false) {


                                    if (serviceAdapter.getItemCount() > 0) {

                                        if(service.equals(GlobalConstants.SERVICE_GROUP))
                                        {
                                            bottomGroupSheetClose();
                                        }
                                        else {
                                            bottomServiceSheetClose();
                                        }

                                        bottomSheetOpen();
                                    }
                                    else
                                    {
                                        bottomSheetClose();
                                        CommonService.Toast(RiderMapActivity.this,"No service found near to your location !!!",Toast.LENGTH_SHORT);
                                        if(service.equals(GlobalConstants.SERVICE_GROUP))
                                        {
                                            bottomGroupSheetOpen();
                                        }
                                        else {
                                            //bottomServiceSheetOpen();
                                        }
                                    }
                                }

                            }//null condition check

                        }
                        else
                        {

                            CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

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
            new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_AUTO_DRIVER, GlobalConstants.CAB_CODE, true, null});
            currentServiceMarker = GlobalConstants.TRANSPORT_CAB_SERVICE;
            btn_service.setImageResource(R.drawable.cab_outline);
            Drawable fabDr= btn_service.getDrawable();
            DrawableCompat.setTint(fabDr, Color.WHITE);
            btn_service.setEnabled(true);

        }
        else if(currentServiceMarker.equals(GlobalConstants.TRANSPORT_CAB_SERVICE)) {

            btn_service.setEnabled(false);
            new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_CAB_DRIVER, GlobalConstants.UTILITY_SERVICE, true, null});
            currentServiceMarker = GlobalConstants.SERVICE_MERCHANT;
            btn_service.setImageResource(R.drawable.merchant);
            Drawable fabDr= btn_service.getDrawable();
            DrawableCompat.setTint(fabDr, Color.WHITE);
            btn_service.setEnabled(true);


        }
        else if(currentServiceMarker.equals(GlobalConstants.SERVICE_MERCHANT)) {

            btn_service.setEnabled(false);
            new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{GlobalConstants.SERVICE_AUTO_DRIVER, GlobalConstants.AUTO_CODE, true, null});
            currentServiceMarker = GlobalConstants.TRANSPORT_AUTO_SERVICE;
            btn_service.setImageResource(R.drawable.auto_outline);
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

                    Log.d(TAG, "insertFavoriteRating responseData: " + responseData);


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
                                    serviceAdapter.notifyDataSetChanged();

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



//        if(serviceGeo.getVehicleType().equals(GlobalConstants.TRANSPORT_AUTO_SERVICE))
//        {
//            currentServiceMarker = GlobalConstants.SERVICE_MERCHANT;
//        }
//        if(serviceGeo.getVehicleType().equals(GlobalConstants.TRANSPORT_CAB_SERVICE))
//        {
//            currentServiceMarker = GlobalConstants.TRANSPORT_AUTO_SERVICE;
//        }
//
//        switchServiceMarkers();


//        Log.d(TAG, "showServiceLocation: " + serviceGeo.getRiderLat() + " : " + serviceGeo.getRiderLng());

        setServiceLocation(0,serviceGeo);

        LatLng latLng = new LatLng(serviceGeo.getRiderLat(), serviceGeo.getRiderLng());
        //move map camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));
        bottomSheetClose();



    }

    public void clearServiceList(){

        //clear driverlist before getting
        servicesList.clear();
        serviceAdapter.notifyDataSetChanged();

    }



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



    public void callTimer(Ride ride)
    {

        Intent i = new Intent(getApplicationContext(), TimerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Ride", ride);
        bundle.putSerializable("Rider", rider);
        i.putExtras(bundle);
        startActivity(i);


    }

    public void callDialer(Rider serviceGeo)
    {

        Log.d(TAG, "callDialer data: " + rider.getRiderLat() + ":" + rider.getRiderLng() + ":" + rider.getRiderLocation());

        Intent i = new Intent(getApplicationContext(), DialerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ServiceGeo", serviceGeo);
        bundle.putSerializable("Rider", rider);
        i.putExtras(bundle);
        startActivity(i);

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


    public void firestoreListener()
    {

        try {

            //-------delete all listeners while log-in
            firebaseFirestore.collection("service").document(rider.getRiderID())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });

            //-------then listen for call
            final DocumentReference docRef = firebaseFirestore.collection("service").document(rider.getRiderID());
            registration = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, "Current data: " + snapshot.getData());

                        //CommonService.Toast(RiderMapActivity.this,snapshot.getData().toString(),Toast.LENGTH_SHORT);

                        Ride ride = new Ride();
                        ride.setRideRefNo(snapshot.getData().get("rideRefNo").toString());
                        ride.setRiderID(snapshot.getData().get("riderID").toString());
                        ride.setServicerID(snapshot.getData().get("servicerID").toString());
                        ride.setRideStatus(snapshot.getData().get("rideStatus").toString());

                        //if calling status then call the service provider
                        if(ride.getRideStatus().equals(GlobalConstants.CALLING_STATUS)) {
                            callTimer(ride);
                        }

                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                }
            });
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void logout()
    {


                Log.d(TAG, "Logout Button Click: ");


                new AlertDialog.Builder(RiderMapActivity.this)
                        .setTitle(R.string.logout)
                        .setMessage(R.string.logout_confirm)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                //To clear SharedPreferences
                                editor = sharedPreferences.edit();
                                //editor.clear();
                                //Do not clear all as last current lat and current lng required in case onLocationChange is not fired
                                editor.putString("autoLogin", GlobalConstants.NO_CODE);
                                editor.commit();


                                //update Log
                                //NowcabsLog nowcabsLog = new NowcabsLog();
                                //nowcabsLog.updateLog(GlobalConstants.RIDER_CODE, rider.riderID, "Logout");


                                Intent intent = new Intent(RiderMapActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();


                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();

     }

    private class fetchRegisteredGroupsProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {
            showProgressBar();

        }

        @Override
        protected Void doInBackground(Object...params ) {


            fetchRegisteredGroups();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            hideProgressBar();

        }
    }

    public void fetchRegisteredGroups()
    {

        RiderMapActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                try {

                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    //Log.d(TAG, "mobileNo: " + mobileNo + " " + name);

                    //loadingSpinner.setVisibility(View.VISIBLE);


                    String methodAction = "fetchRegisteredGroups";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());


                    ConnectHost connectHost = new ConnectHost();

                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    //loadingSpinner.setVisibility(View.GONE);

                    Log.d(GlobalConstants.CommonService, "fetchRegisteredGroups responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchRegisteredGroups");
                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("groupRiderWrapper");
                        if (jsonResponseData.getString("success") == "true") {

                            GroupRider groupRider=null;
                            Gson gson=new GsonBuilder().create();

                            if(wrapperArrayObj!=null) {

                                groupList.clear();
                                groupSelectionAdapter.notifyDataSetChanged();

                                for (int i = 0; i <= wrapperArrayObj.length() - 1; i++) {
                                    if (wrapperArrayObj.optJSONObject(i).optString("recordFound") == "true") {

                                        groupRider = new GroupRider();

                                        groupRider.setGroupRefNo(wrapperArrayObj.optJSONObject(i).optString("groupRefNo"));
                                        groupRider.setGroupID(wrapperArrayObj.optJSONObject(i).optString("groupID"));
                                        groupRider.setRiderRefNo(wrapperArrayObj.optJSONObject(i).optString("linkRiderRefNo"));
                                        groupRider.setRiderID(wrapperArrayObj.optJSONObject(i).optString("linkRiderID"));
                                        groupRider.setPublicView(wrapperArrayObj.optJSONObject(i).optString("publicView"));
                                        groupRider.setStatus(wrapperArrayObj.optJSONObject(i).optString("status"));
                                        groupRider.setGroup(gson.fromJson(wrapperArrayObj.optJSONObject(i).optJSONObject("groupWrapper").toString(), Group.class));

                                        groupList.add(groupRider);
                                        groupSelectionAdapter.notifyDataSetChanged();

                                    }
                                }

                                bottomGroupSheetOpen();

                            }//null condition check

                        } else {

                            CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                }

            } //run end


        });


    }


    private class fetchServiceCountProgressTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Object...params ) {


            fetchServiceCount();

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }
    //--------fetch service Count  in the backend
    public void fetchServiceCount() {


        RiderMapActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                try {


                    //Shared Preferences
                    editor = sharedPreferences.edit();

                    editor.putString("userid", rider.getRiderID());
                    editor.putString("deviceToken", rider.getFcmToken());
                    editor.putString("sessionid", "SESSIONID");

                    editor.apply();


                    String methodAction = "fetchServiceCount";

                    JSONObject messageJson = new JSONObject();
                    messageJson.put("currentLat", markerLat);
                    messageJson.put("currentLng", markerLng);
                    messageJson.put("riderRefNo", rider.getRiderRefNo());
                    messageJson.put("riderID", rider.getRiderID());

                    ConnectHost connectHost = new ConnectHost();
                    responseData = connectHost.excuteConnectHost(methodAction, messageJson.toString(), sharedPreferences);

                    Log.d(TAG, "fetch service count  responseData: " + responseData);


                    if (responseData != null) {


                        // Convert String to json object
                        JSONObject jsonResponseData = new JSONObject(responseData);

                        // get LL json object
                        JSONObject jsonResult = jsonResponseData.optJSONObject("fetchServiceCount");

                        JSONArray wrapperArrayObj = jsonResult.getJSONArray("serviceWrapper");

                        if (jsonResponseData.optString("success") == "true") {


                            Service service=null;
                            serviceCountList.clear();
                            //serviceSelectionAdapter.notifyDataSetChanged();

                            if(wrapperArrayObj!=null) {

                                for (int i = 0; i <= wrapperArrayObj.length() - 1; i++) {
                                    if (wrapperArrayObj.optJSONObject(i).optString("recordFound") == "true") {

                                        service = new Service();

                                        service.setRiderRefNo(wrapperArrayObj.optJSONObject(i).optString("riderRefNo"));
                                        service.setRiderID(wrapperArrayObj.optJSONObject(i).optString("riderID"));
                                        service.setServiceCode(wrapperArrayObj.optJSONObject(i).optString("serviceCode"));
                                        service.setServiceCount(wrapperArrayObj.optJSONObject(i).optInt("serviceCount"));
                                        service.setDistance(wrapperArrayObj.optJSONObject(i).optDouble("distance"));

                                        Log.d(TAG, "fetch service count serviceCode: " + service.getServiceCode() + " " + service.getServiceCount());

                                        serviceCountList.add(service);

                                    }
                                }
                                //serviceSelectionAdapter.notifyDataSetChanged();
                                showServiceCount();

                            }//null condition check

                        }
                        else
                        {

                            CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                        }


                    } else {

                        CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    CommonService.Toast(RiderMapActivity.this, GlobalConstants.SYSTEM_ERROR, Toast.LENGTH_SHORT);

                }

                //}// validation

            }//run end

        });//runnable end


    }//fetch rider count update End


    public void setServicesView()
    {


        cv_carpenter = (CardView) findViewById(R.id.carpenter_cardView);
        cv_autoDriver = (CardView) findViewById(R.id.auto_driver_cardView);
        cv_cabDriver = (CardView) findViewById(R.id.cab_driver_cardView);
        cv_electrician = (CardView) findViewById(R.id.electrician_cardView);
        cv_plumber = (CardView) findViewById(R.id.plumber_cardView);
        cv_tailor = (CardView) findViewById(R.id.tailor_cardView);
        cv_washer = (CardView) findViewById(R.id.washer_cardView);
        cv_courier = (CardView) findViewById(R.id.courier_cardView);
        cv_merchant = (CardView) findViewById(R.id.merchant_cardView);

        cv_movers = (CardView) findViewById(R.id.movers_cardView);
        cv_housekeeper = (CardView) findViewById(R.id.housekeeper_cardView);
        cv_cook = (CardView) findViewById(R.id.cook_cardView);
        cv_painter = (CardView) findViewById(R.id.painter_cardView);
        cv_florist = (CardView) findViewById(R.id.florist_cardView);
        cv_pesticide = (CardView) findViewById(R.id.pesticide_cardView);
        cv_tutor = (CardView) findViewById(R.id.tutor_cardView);
        cv_locksmith = (CardView) findViewById(R.id.locksmith_cardView);
        cv_grinder = (CardView) findViewById(R.id.grinder_cardView);


        cv_carpenter.setOnClickListener(this);
        cv_autoDriver.setOnClickListener(this);
        cv_cabDriver.setOnClickListener(this);
        cv_electrician.setOnClickListener(this);
        cv_plumber.setOnClickListener(this);
        cv_tailor.setOnClickListener(this);
        cv_washer.setOnClickListener(this);
        cv_courier.setOnClickListener(this);
        cv_merchant.setOnClickListener(this);

        cv_movers.setOnClickListener(this);
        cv_housekeeper.setOnClickListener(this);
        cv_cook.setOnClickListener(this);
        cv_painter.setOnClickListener(this);
        cv_florist.setOnClickListener(this);
        cv_pesticide.setOnClickListener(this);
        cv_tutor.setOnClickListener(this);
        cv_locksmith.setOnClickListener(this);
        cv_grinder.setOnClickListener(this);

        cv_carpenter.setTag(GlobalConstants.SERVICE_CARPENTER);
        cv_autoDriver.setTag(GlobalConstants.SERVICE_AUTO_DRIVER);;
        cv_cabDriver.setTag(GlobalConstants.SERVICE_CAB_DRIVER);;
        cv_electrician.setTag(GlobalConstants.SERVICE_ELECTRICIAN);;
        cv_plumber.setTag(GlobalConstants.SERVICE_PLUMBER);;
        cv_tailor.setTag(GlobalConstants.SERVICE_TAILOR);;
        cv_washer.setTag(GlobalConstants.SERVICE_WASHER);;
        cv_courier.setTag(GlobalConstants.SERVICE_COURIER);;
        cv_merchant.setTag(GlobalConstants.SERVICE_MERCHANT);;

        cv_movers.setTag(GlobalConstants.SERVICE_MOVERS);;
        cv_housekeeper.setTag(GlobalConstants.SERVICE_HOUSEKEEPER);;
        cv_cook.setTag(GlobalConstants.SERVICE_COOK);;
        cv_painter.setTag(GlobalConstants.SERVICE_PAINTER);;
        cv_florist.setTag(GlobalConstants.SERVICE_FLORIST);;
        cv_pesticide.setTag(GlobalConstants.SERVICE_PESTICIDE);;
        cv_tutor.setTag(GlobalConstants.SERVICE_TUTOR);;
        cv_locksmith.setTag(GlobalConstants.SERVICE_LOCKSMITH);;
        cv_grinder.setTag(GlobalConstants.SERVICE_GRINDER);;


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

    }

    @Override
    public void onClick(View view) {

        new RiderMapActivity.fetchServiceLocationProgressTask().execute(new Object[]{view.getTag(), null, false,null});

    }

    public void showServiceCount()
    {
        try{


            Service service=null;
            tv_carpenter_count.setText( "0");
            tv_courier_count.setText( "0");
            tv_cabdriver_count.setText( "0");
            tv_autodriver_count.setText( "0");
            tv_electrician_count.setText( "0");
            tv_merchant_count.setText( "0");
            tv_plumber_count.setText( "0");
            tv_tailor_count.setText( "0");
            tv_washer_count.setText( "0");
            tv_movers_count.setText( "0");
            tv_housekeeper_count.setText( "0");
            tv_cook_count.setText( "0");
            tv_painter_count.setText( "0");
            tv_florist_count.setText( "0");
            tv_pesticide_count.setText( "0");
            tv_tutor_count.setText( "0");
            tv_locksmith_count.setText( "0");
            tv_grinder_count.setText( "0");


            for(int i=0;i<serviceCountList.size();i++) {

                service = serviceCountList.get(i);

                Log.i(TAG, "Service Selection  " + service.getServiceCode());
                Log.i(TAG, "Service Selection Count " + service.getServiceCount());

                //------set service count
                switch (service.getServiceCode()) {

                    case GlobalConstants.SERVICE_CARPENTER: {

                        tv_carpenter_count.setText( String.valueOf(service.getServiceCount()));
                        break;
                    }
                    case GlobalConstants.SERVICE_COURIER: {

                        tv_courier_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_CAB_DRIVER: {

                        tv_cabdriver_count.setText( String.valueOf(service.getServiceCount()));
                        break;
                    }
                    case GlobalConstants.SERVICE_AUTO_DRIVER: {

                        tv_autodriver_count.setText( String.valueOf(service.getServiceCount()));
                        break;
                    }
                    case GlobalConstants.SERVICE_ELECTRICIAN: {

                        tv_electrician_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_MERCHANT: {

                        tv_merchant_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_PLUMBER: {

                        tv_plumber_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_TAILOR: {

                        tv_tailor_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_WASHER: {

                        tv_washer_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_MOVERS: {

                        tv_movers_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_HOUSEKEEPER: {

                        tv_housekeeper_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_COOK: {

                        tv_cook_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_PAINTER: {

                        tv_painter_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_FLORIST: {

                        tv_florist_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_PESTICIDE: {

                        tv_pesticide_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_TUTOR: {

                        tv_tutor_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_LOCKSMITH: {

                        tv_locksmith_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }
                    case GlobalConstants.SERVICE_GRINDER: {

                        tv_grinder_count.setText( String.valueOf(service.getServiceCount()));
                        break;

                    }


                } //switch

            }//for loop

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

//    private void onInviteClicked() {
//        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
//                .setMessage(getString(R.string.invitation_message))
//                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
//                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
//                .setCallToActionText(getString(R.string.invitation_cta))
//                .build();
//        startActivityForResult(intent, REQUEST_INVITE);
//    }


}//class end




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

//private Circle circle;

//    LinearLayout rootLinearLayout;
//    LinearLayout driverInfoLinearLayout;
//JSONArray driverTabs = new JSONArray();
//JSONArray driverData = null;

//    int driverRadius = 0;

//            private RecyclerView mRecyclerView;
//            private RecyclerView.Adapter mAdapter;
//            private RecyclerView.LayoutManager mLayoutManager;

//    ImageView riderImage;
//RatingBar ratingBar;
//protected Location mLastLocation;
//protected Location mLastKnownLocation;

//20-Sep-2018
//        //navigation bar
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        //toolbar.setTitle("");
//
//
//




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

//firebaseStorage = FirebaseStorage.getInstance(GlobalConstants.FIREBASE_URL);


//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

//Log.d(TAG, "Before RiderList: " );

// tv_location=(TextView) findViewById(R.id.tv_location);

//Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

//((AppCompatActivity) getActivity()).getSupportActionBar(myToolbar);
// ((AppCompatActivity) getActivity()).setSupportActionController(myToolbar);
// Toolbar toolbar = (Toolbar) findViewById(R.id.rider_toolbar);
//setSupportActionBar(toolbar);

//Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rider);
//setSupportActionBar(toolbar);


//driverInfoLinearLayout= (LinearLayout)findViewById(R.id.list_driverInfo);

//Commented for RecyclerView
//rootLinearLayout= (LinearLayout)findViewById(R.id.list_driverInfo);
//rootLinearLayout.setBackgroundColor(Color.WHITE);

//Shared Preferences for SideNav
//        editor = sharedPreferences.edit();
//        editor.putString("riderFirstName", rider.getRiderName());
//        editor.apply();
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
//                    serviceAdapter.notifyDataSetChanged();
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
//                serviceAdapter.notifyDataSetChanged();
//
//
//            } //if condition
//            else //in Driver is inactive or hired remove
//            {
//
//                Log.d(TAG, "setDriverInfoList Remove: " + driverGeo.getStatus() + " count " + servicesList.size() + "contains " + servicesList.contains(driverGeo));
//
//                int position = serviceAdapter.getItemPosition(driverGeo);
//                if (position >= 0) {
//                    servicesList.remove(position);
//                    serviceAdapter.notifyItemRemoved(position);
//                    serviceAdapter.notifyItemChanged(position, serviceAdapter.getItemCount() - position);
//                    serviceAdapter.notifyDataSetChanged();
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