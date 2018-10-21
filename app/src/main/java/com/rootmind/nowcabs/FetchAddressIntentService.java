package com.rootmind.nowcabs;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {


    protected ResultReceiver mReceiver;


    public FetchAddressIntentService()
    {
        super("FetchAddressIntentService");

    }


    @Override
    protected void onHandleIntent(Intent intent) {

        mReceiver = intent.getParcelableExtra(GlobalConstants.RECEIVER);


        if(mReceiver==null) {
            return;
        }


        if (intent == null) {
            return;
        }
        String errorMessage = "";

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(
                GlobalConstants.LOCATION_DATA_EXTRA);


        // ...

        // Locale.getDefault()
        //Locale aLocale = new Locale.Builder().setLanguage("te").setRegion("IN").build();
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
        // ...

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);


            // Handle case where no address was found.
            if (addresses == null || addresses.size()  == 0) {
                if (errorMessage.isEmpty()) {
                    errorMessage = "no_address_found"; //getString(R.string.no_address_found);
                    //Log.e(TAG, errorMessage);
                }
                deliverResultToReceiver(GlobalConstants.FAILURE_RESULT, errorMessage);
            } else {

                //this is default code from Google
//            Address address = addresses.get(0);
//            ArrayList<String> addressFragments = new ArrayList<String>();
//
//            // Fetch the address lines using getAddressLine,
//            // join them, and send them to the thread.
//            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
//                addressFragments.add(address.getAddressLine(i));
//            }
//
//            //Log.i(TAG, getString(R.string.address_found));
//            deliverResultToReceiver(CommonService.SUCCESS_RESULT,
//                    TextUtils.join(System.getProperty("line.separator"),
//                            addressFragments));



                String city = "";
                if(addresses != null && addresses.size()>0) {

//                String address=null;
//                if(addresses.get(0).getMaxAddressLineIndex()>0)
//                {
//                    address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                }
//
//                if(address!=null)
//                {
//                    city = address + ", " + addresses.get(0).getFeatureName() + ", "+ addresses.get(0).getLocality() + ", "+ addresses.get(0).getAdminArea();
//                }

                    String locality = CommonService.isNull(addresses.get(0).getLocality());
                    String state = CommonService.isNull(addresses.get(0).getAdminArea());
                    String country = CommonService.isNull(addresses.get(0).getCountryName());
                    String postalCode = CommonService.isNull(addresses.get(0).getPostalCode());
                    String knownName = CommonService.isNull(addresses.get(0).getFeatureName());


                    if(knownName!=null)
                    {
                        city = knownName;
                    }

                    if(!city.isEmpty())
                    {
                        city = city +  ", "+ locality;
                    }
                    else
                    {
                        city =  locality;
                    }

                    if(!city.isEmpty())
                    {
                        city = city +  ", "+ state;
                    }
                    else
                    {
                        city =  state;
                    }

                    Log.d("Tag", "city1 "+ city);

//
//                Log.d("Tag", "locality "+ locality);
//                Log.d("Tag", "state "+ state);
//                Log.d("Tag", "country "+ country);
//                Log.d("Tag", "postalcode "+ postalCode);
//                Log.d("Tag", "knownname "+ knownName);

                }
                else{
                    city = "No Address returned!";
                }


                //Log.i(TAG, getString(R.string.address_found));
                deliverResultToReceiver(GlobalConstants.SUCCESS_RESULT, city);
            }


        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "service_not_available"; //getString(R.string.service_not_available);
            //Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "invalid_lat_long_used"; //getString(R.string.invalid_lat_long_used);
//            Log.e(TAG, errorMessage + ". " +
//                    "Latitude = " + location.getLatitude() +
//                    ", Longitude = " +
//                    location.getLongitude(), illegalArgumentException);
        }
        catch (Exception exception) {
            // Catch network or other I/O problems.
            errorMessage = "error occured in FetchAddress"; //getString(R.string.service_not_available);
            //Log.e(TAG, errorMessage, ioException);
        }



    }


    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(GlobalConstants.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }

}

