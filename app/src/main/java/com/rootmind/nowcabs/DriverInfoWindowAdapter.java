package com.rootmind.nowcabs;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class DriverInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;


    public DriverInfoWindowAdapter(Context context) {
        this.context = context;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.content_infowindow, null);

        ImageView iv_driverImage = view.findViewById(R.id.iv_driverImage);

        TextView tv_driverName = view.findViewById(R.id.tv_driverName);
        TextView tv_destination = view.findViewById(R.id.tv_destination);

        tv_driverName.setText(marker.getTitle());
        tv_destination.setText(marker.getSnippet());

        Driver driverInfoWindowData = (Driver) marker.getTag();

//        int imageId = context.getResources().getIdentifier(driverInfoWindowData.getDriverImage().toLowerCase(),
//                "drawable", context.getPackageName());
//        iv_driverImage.setImageResource(imageId);


        return view;
    }

}
