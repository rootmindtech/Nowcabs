package com.rootmind.nowcabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.HashMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

class DriverInfoWindowAdapter implements InfoWindowAdapter {
    private View popup=null;
    private LayoutInflater inflater=null;
    private HashMap<String, Uri> images=null;
    private Context ctxt=null;
    private int iconWidth=-1;
    private int iconHeight=-1;
    private Marker lastMarker=null;

    DriverInfoWindowAdapter(Context ctxt) {
        this.ctxt=ctxt;

        iconWidth=
                ctxt.getResources().getDimensionPixelSize(R.dimen.icon_width);
        iconHeight=
                ctxt.getResources().getDimensionPixelSize(R.dimen.icon_height);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {


        if (popup == null) {

            popup = ((Activity) ctxt).getLayoutInflater()
                    .inflate(R.layout.content_infowindow, null);

//            popup=inflater.inflate(R.layout.content_infowindow, null);
        }

        if (lastMarker == null
                || !lastMarker.getId().equals(marker.getId())) {
            lastMarker=marker;

//            TextView tv=(TextView)popup.findViewById(R.id.title);
//
//            tv.setText(marker.getTitle());

            //tv=(TextView)popup.findViewById(R.id.snippet);
            //tv.setText(marker.getSnippet());

            //Uri image=images.get(marker.getId());

            RatingBar ratingBar = (RatingBar) popup.findViewById(R.id.ratingBar);

            TextView tv_driverName = popup.findViewById(R.id.tv_driverName);
            TextView tv_serviceName = popup.findViewById(R.id.tv_servicerName);

            tv_driverName.setText(marker.getTitle());
            tv_serviceName.setText(marker.getSnippet());

            ImageView icon=(ImageView)popup.findViewById(R.id.iv_driverImage);

            Rider rider = (Rider) marker.getTag();

            //-----set avatar
            if(rider!=null) {

                //Log.d("InfoWindow", "InfoWindow " + rider.images[0].getImageName());

                tv_serviceName.setText(rider.getServiceCode());
                ratingBar.setRating((float) rider.getAvgRating());
                icon.setImageResource(R.drawable.avatar_outline48);


                if(rider.images!=null && rider.images.length>0)
                {
                    CommonService commonService = new CommonService();
                    String imageFileName = CommonService.getImageName(rider, GlobalConstants.IMAGE_AVATAR);
                    if(imageFileName!=null && !imageFileName.trim().equals("")) {
                        commonService.getMarkerImage(icon, imageFileName, marker);
                    }
                    else
                    {
                        icon.setImageResource(R.drawable.avatar_outline48);
                    }

                }


        }



//            if (image == null) {
//                icon.setVisibility(View.GONE);
//            }
//            else {
//                Picasso.get().load(image).resize(iconWidth, iconHeight)
//                        .centerCrop().noFade()
//                        .placeholder(R.drawable.driver)
//                        .into(icon, new MarkerCallback(marker));
//            }
        }

        return(popup);
    }

    static class MarkerCallback implements Callback {
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
            if (marker != null && marker.isInfoWindowShown()) {
                marker.showInfoWindow();
            }
        }
    }
}


//
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.Uri;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.Marker;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;
//
//public class DriverInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
//
//    private Context context;
//
//
//    public DriverInfoWindowAdapter(Context context) {
//        this.context = context;
//    }
//
//
//    @Override
//    public View getInfoWindow(Marker marker) {
//        return null;
//    }
//
//    @Override
//    public View getInfoContents(Marker marker) {
//
//        View view=null;
//
//        try {
//
//            view = ((Activity) context).getLayoutInflater()
//                    .inflate(R.layout.content_infowindow, null);
//
//            ImageView iv_driverImage = view.findViewById(R.id.iv_driverImage);
//
//            TextView tv_driverName = view.findViewById(R.id.tv_driverName);
//            TextView tv_destination = view.findViewById(R.id.tv_destination);
//
//            tv_driverName.setText(marker.getTitle());
//            tv_destination.setText(marker.getSnippet());
//
//            Rider rider = (Rider) marker.getTag();
//
//
//            //-----set avatar
//            if(rider!=null && rider.images!=null && rider.images.length>0) {
//
//                Log.d("InfoWindow", "InfoWindow " + rider.images[0].getImageName());
//
//                CommonService commonService = new CommonService();
//                commonService.getMarkerImage(iv_driverImage, CommonService.getImageName(rider, GlobalConstants.IMAGE_AVATAR), marker);
//
//            }
//
//
////            int imageId = context.getResources().getIdentifier(rider.getImageName().toLowerCase(),
////                    "drawable", context.getPackageName());
////            iv_driverImage.setImageResource(imageId);
//
//
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//        return view;
//
//    }
//
//
//
//}
//
