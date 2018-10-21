package com.rootmind.nowcabs;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by rootmindtechsoftprivatelimited on 01/08/17.
 */

public class RidesArrayAdapter extends ArrayAdapter<RideDetails> {

    Context mContext;
    int layoutResourceId;
    RideDetails data[] = null;

//    public RidesArrayAdapter(Context mContext, int layoutResourceId, RideDetails[] data) {
//
//        super(mContext, layoutResourceId, data);
//
//        this.layoutResourceId = layoutResourceId;
//        this.mContext = mContext;
//        this.data = data;
//    }

    public RidesArrayAdapter(Context mcontext, ArrayList<RideDetails> users) {
        super(mcontext, 0, users);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        /*
//         * The convertView argument is essentially a "ScrapView" as described is Lucas post
//         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
//         * It will have a non-null value when ListView is asking you recycle the row layout.
//         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
//         */
//        if(convertView==null){
//            // inflate the layout
//            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//            convertView = inflater.inflate(layoutResourceId, parent, false);
//        }
//
//        // object item based on the position
//        RideDetails rideDetails = data[position];
//
//        // get the TextView and then set the text (item name) and tag (item ID) values
//        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
//        textViewItem.setText(rideDetails.riderID);
//        textViewItem.setTag(rideDetails.riderID);
//
//        return convertView;
//
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //User user = getItem(position);

        RideDetails rideDetails=getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_rides_listview, parent, false);
        }
        // Lookup view for data population
        //TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        //TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        //tvName.setText(user.name);
        //tvHome.setText(user.hometown);


        TextView tv_rideID = (TextView) convertView.findViewById(R.id.tv_rideID);
        tv_rideID.setText(rideDetails.rideID);

        TextView tv_driverID = (TextView) convertView.findViewById(R.id.tv_driverID);
        tv_driverID.setText(rideDetails.driverID);


        TextView tv_source = (TextView) convertView.findViewById(R.id.tv_source);
        tv_source.setText(rideDetails.source);

        TextView tv_destination = (TextView) convertView.findViewById(R.id.tv_destination);
        tv_destination.setText(rideDetails.destination);


        TextView tv_datetime = (TextView) convertView.findViewById(R.id.tv_datetime);
        tv_datetime.setText(rideDetails.datetime);






        // Return the completed view to render on screen



        return convertView;
    }

}
