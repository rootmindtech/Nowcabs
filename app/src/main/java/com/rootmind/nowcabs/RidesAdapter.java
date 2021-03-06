package com.rootmind.nowcabs;

import android.content.res.Resources;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rootmindtechsoftprivatelimited on 01/08/17.
 */

public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder> {

    private static final String TAG = "RidesAdapter";


    public interface ItemClickListener {
        void onClickDialImage(View view, int position);


    }

    private List<Ride> rideList;

    private RidesAdapter.ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_servicerName, tv_servicerMobileNo, tv_rideStatus, tv_serviceCode, tv_vehicleNo, tv_rideStartDate, tv_appointDateTime;
        public ImageView iv_avatar;
        public ImageView iv_vehicleImage;
        public ImageView iv_dialImage;

        public ImageButton ib_location;

        public ViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);



            tv_servicerName = (TextView) view.findViewById(R.id.tv_servicerName);
            tv_servicerMobileNo = (TextView) view.findViewById(R.id.tv_servicerMobileNo);
            tv_serviceCode = (TextView) view.findViewById(R.id.tv_serviceCode);
            tv_rideStatus = (TextView) view.findViewById(R.id.tv_rideStatus);
            iv_vehicleImage=(ImageView) view.findViewById(R.id.iv_vehicleImage);
            tv_vehicleNo= (TextView) view.findViewById(R.id.tv_vehicleNo);
            iv_dialImage=(ImageView) view.findViewById(R.id.iv_dialImage);
            iv_avatar=(ImageView) view.findViewById(R.id.iv_avatar);
            tv_rideStartDate=(TextView) view.findViewById(R.id.tv_rideStartDate);
            tv_appointDateTime = (TextView) view.findViewById(R.id.tv_appointDateTime);

            iv_dialImage.setOnClickListener(this);

            iv_dialImage.setVisibility(View.INVISIBLE);
            tv_servicerMobileNo.setVisibility(View.INVISIBLE);


        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {

                Log.i(TAG, "get ID "+ view.getId());


                switch (view.getId()) {

                    case R.id.iv_dialImage: {


                        mClickListener.onClickDialImage(view, getAdapterPosition());
                        break;
                    }


                }




            };

        }


    }



    public RidesAdapter(List<Ride> rideList) {

        this.rideList = rideList;
    }

    @Override
    public RidesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ride_list_row, parent, false);


        return new RidesAdapter.ViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(RidesAdapter.ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();

        Ride ride = rideList.get(position);

        if(ride.getRideType().equals(GlobalConstants.RIDER_TYPE)) {

            holder.tv_servicerName.setText(ride.getServicerName());
            holder.tv_servicerMobileNo.setText(ride.getServicerMobileNo());
        }
        else if(ride.getRideType().equals(GlobalConstants.SERVICER_TYPE))
        {
            holder.tv_servicerName.setText(ride.getRiderName());
            holder.tv_servicerMobileNo.setText(ride.getRiderMobileNo());

        }
        
        holder.tv_serviceCode.setText(ride.getServiceCode());
        holder.tv_rideStatus.setText(ride.getRideStatus());
        holder.tv_rideStartDate.setText(ride.getRideStartDate());


        holder.iv_dialImage.setVisibility(View.INVISIBLE);
        holder.tv_servicerMobileNo.setVisibility(View.INVISIBLE);

        holder.tv_appointDateTime.setText(ride.getAppointDateTime());

        switch (ride.getRideStatus()) {


            case GlobalConstants.ACCEPTED_STATUS: {

                holder.iv_dialImage.setVisibility(View.VISIBLE);
                holder.tv_servicerMobileNo.setVisibility(View.VISIBLE);

                holder.tv_rideStatus.setBackgroundColor(Color.parseColor(GlobalConstants.DARKGREEN));  //darkgreen
                holder.tv_rideStatus.setTextColor(Color.WHITE);

                break;
            }
            case GlobalConstants.REJECTED_STATUS: {

                holder.tv_rideStatus.setBackgroundColor(Color.parseColor(GlobalConstants.DARKRED));  //darkred
                holder.tv_rideStatus.setTextColor(Color.WHITE);

                break;
            }
            case GlobalConstants.NORESPONSE_STATUS: {

                holder.tv_rideStatus.setText(GlobalConstants.NORESPONSE_STATUS_DISPLAY);
                holder.tv_rideStatus.setBackgroundColor(Color.BLACK);  //black
                holder.tv_rideStatus.setTextColor(Color.WHITE);

                break;
            }

        } //switch





        setServiceImage(holder, ride);


    }


    public void setServiceImage(RidesAdapter.ViewHolder holder, Ride ride)
    {

        holder.iv_vehicleImage.setAdjustViewBounds(true);
        holder.iv_avatar.setAdjustViewBounds(true);


        //clear values
        holder.tv_vehicleNo.setText(ride.getServiceCode());

//        //-----set avatar
//        CommonService commonService = new CommonService();
//
//        //Log.d(TAG, "setServiceImage getImageName1  " + ride.getrideID());
//
//        //Log.d(TAG, "setServiceImage getImageName  " + CommonService.getImageName(ride,GlobalConstants.IMAGE_AVATAR));
//
//        String imageFileName= CommonService.getImageName(ride,GlobalConstants.IMAGE_AVATAR);
//
//        Log.d(TAG, "setServiceImage getImage ID  " + ride.getrideID());
//
//        Log.d(TAG, "setServiceImage getImageName  " + imageFileName);
//
//        if(imageFileName!=null && !imageFileName.trim().equals("")) {
//            commonService.getImage(holder.iv_avatar, imageFileName);
//        }
//        else
//        {
//            holder.iv_avatar.setImageResource(R.drawable.avatar);
//        }

        //------set profession image
        switch (ride.getServiceCode()) {

            case GlobalConstants.SERVICE_CARPENTER:
            {
                holder.iv_vehicleImage.setImageResource(R.drawable.carpenter);
                break;
            }
            case GlobalConstants.SERVICE_COURIER:
            {
                holder.iv_vehicleImage.setImageResource(R.drawable.courier);
                break;

            }
            case GlobalConstants.SERVICE_AUTO_DRIVER:
            {

//                switch (ride.getVehicleType()){
//
//                    case GlobalConstants.AUTO_CODE:
//                    {
//                        holder.iv_vehicleImage.setImageResource(R.drawable.auto_outline);
//                        break;
//
//                    }
//                    case GlobalConstants.CAB_CODE:
//                    {
//                        holder.iv_vehicleImage.setImageResource(R.drawable.cab_outline);
//                        break;
//
//                    }
//
//
//                }

                holder.iv_vehicleImage.setImageResource(R.drawable.auto_outline);
                holder.tv_vehicleNo.setText(ride.getVehicleNo());
                break;
            }
            case GlobalConstants.SERVICE_CAB_DRIVER:
            {
                holder.iv_vehicleImage.setImageResource(R.drawable.cab_outline);
                holder.tv_vehicleNo.setText(ride.getVehicleNo());
                break;
            }
            case GlobalConstants.SERVICE_ELECTRICIAN:
            {
                holder.iv_vehicleImage.setImageResource(R.drawable.electrician);
                break;

            }
            case GlobalConstants.SERVICE_MERCHANT:
            {
                holder.iv_vehicleImage.setImageResource(R.drawable.merchant);
                break;

            }
            case GlobalConstants.SERVICE_PLUMBER:
            {
                holder.iv_vehicleImage.setImageResource(R.drawable.plumber);
                break;

            }
            case GlobalConstants.SERVICE_TAILOR:
            {
                holder.iv_vehicleImage.setImageResource(R.drawable.tailor);
                break;

            }
            case GlobalConstants.SERVICE_WASHER:
            {
                holder.iv_vehicleImage.setImageResource(R.drawable.washer);
                break;

            }
        }

    }
    @Override
    public int getItemCount() {
        return rideList.size();
    }




    public void setClickListener(RidesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }





}
