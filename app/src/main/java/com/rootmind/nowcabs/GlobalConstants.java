package com.rootmind.nowcabs;

/**
 * Created by rootmindtechsoftprivatelimited on 20/06/17.
 */

public  final class GlobalConstants {


    public static final String  FIREBASE_DRIVER_PATH = "cabs/driver/location";
    public static final String  FIREBASE_RIDER_PATH = "cabs/rider/location";
    public static final String  FIREBASE_PARAM_PATH = "cabs/parameter/";
    public static final String  FIREBASE_LOG_PATH = "cabs/log/";
    public static final String  FIREBASE_RIDES_PATH = "cabs/rides/";
    public static final String  FIREBASE_DRIVER_ALERT_PATH = "cabs/driver/alert";
    public static final String  HOST_URL = "https://rootmindtech.ddns.net/NowcabsWeb/Nowcabs";
    //public static final String  HOST_URL = "http://192.168.1.110:8084/NowcabsWeb/Nowcabs";
    public static final String SYSTEM_ERROR="Network unavailable, please try again";

    //public static final float MAP_ZOOM=17;
    public static final String FINDING_ADDRESS="Getting address...";

    public static final String  DRIVER_PREFIX = "DR";
    public static final String  RIDER_PREFIX = "RD";
    public static final String  VACANT_CODE = "VACANT";
    public static final String  HIRED_CODE = "HIRED";
    public static final String  ACTIVE_CODE = "ACTIVE";
    public static final String  NO_CODE = "NO";
    public static final String  YES_CODE = "YES";
    public static final String  RIDER_CODE = "RIDER";
    public static final String  DRIVER_CODE = "DRIVER";
    public static final String  CAB_CODE = "CAB";
    public static final String  AUTO_CODE = "AUTO";
    public static final String  ANY_CODE = "ANY";
    public static final String ACCESS_RESTRICTED="Access restricted";

    public static final String TRANSPORT_BUSINESS="TRANSPORT";
    public static final String RETAIL_BUSINESS="RETAIL";
    public static final String UTILITY_BUSINESS ="UTILITY";

    public static final String TRANSPORT_AUTO_SERVICE="AUTO";
    public static final String TRANSPORT_CAB_SERVICE="CAB";
    public static final String UTILITY_SERVICE="UTILITY";

    public static final String FAVORITE="FAVORITE";
    public static final String RATING="RATING";

    public static final String ENGLISH_LOCALE="en";
    public static final String HINDI_LOCALE="hi";
    public static final String TELUGU_LOCALE="te";
    public static final String TAMIL_LOCALE="ta";
    public static final String KANNADA_LOCALE="kn";
    public static final String URDU_LOCALE="ur";


    public static final String GOOGLE_MAPS_KEY="AIzaSyDASwkBLMvv2fchepUL1iZZBV_DWYkk9F8";
    public static final String GOOGLE_MAPS_DIRECTIONS_URL="https://maps.googleapis.com/maps/api/directions/";
    public static final String GOOGLE_MAPS_DISTANCE_MATRIX_URL="https://maps.googleapis.com/maps/api/distancematrix/json?";

    public static final String FCM_URL="https://fcm.googleapis.com/fcm/send";

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";

    public static final String CommonService = "CommonService";

}
