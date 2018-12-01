package com.rootmind.nowcabs;

import android.content.res.Resources;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ServiceSelectionAdapter extends RecyclerView.Adapter<ServiceSelectionAdapter.ViewHolder>  {

    private static final String TAG = "ServiceSelectionAdapter";


    public interface ItemClickListener {
        //void onClick(View view, int position);
        void onClickCarpenter(View view, int position);
        void onClickAutoDriver(View view, int position);
        void onClickCabDriver(View view, int position);
        void onClickElectrician(View view, int position);
        void onClickPlumber(View view, int position);
        void onClickTailor(View view, int position);
        void onClickWasher(View view, int position);
        void onClickCourier(View view, int position);
        void onClickMerchant(View view, int position);
        void onClickMovers(View view, int position);
        void onClickHousekeeper(View view, int position);
        void onClickCook(View view, int position);
        void onClickPainter(View view, int position);
        void onClickFlorist(View view, int position);
        void onClickPesticide(View view, int position);
        void onClickTutor(View view, int position);
        void onClickLocksmith(View view, int position);
        void onClickGrinder(View view, int position);


    }


    private List<Rider> servicesList;
    private ServiceSelectionAdapter.ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public TextView tv_servicerName, tv_mobileNo, tv_destination, tv_distance, tv_vacantStatus, tv_yourRating, tv_vehicleNo;
//        public ImageView iv_vehicleImage;
//        public ImageView iv_avatar;
//        public ImageView iv_dialImage;
//        public ImageView iv_smsImage;
//        public ImageButton ib_favorite;
//        public RatingBar ratingBar;
//        public ImageButton ib_rating;
//        public ImageButton ib_location;
//
//        private String mItem;
//        private TextView mTextView;

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



        public ViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);

            cv_carpenter = (CardView) view.findViewById(R.id.carpenter_cardView);
            cv_autoDriver = (CardView) view.findViewById(R.id.auto_driver_cardView);
            cv_cabDriver = (CardView) view.findViewById(R.id.cab_driver_cardView);
            cv_electrician = (CardView) view.findViewById(R.id.electrician_cardView);
            cv_plumber = (CardView) view.findViewById(R.id.plumber_cardView);
            cv_tailor = (CardView) view.findViewById(R.id.tailor_cardView);
            cv_washer = (CardView) view.findViewById(R.id.washer_cardView);
            cv_courier = (CardView) view.findViewById(R.id.courier_cardView);
            cv_merchant = (CardView) view.findViewById(R.id.merchant_cardView);

            cv_movers = (CardView) view.findViewById(R.id.movers_cardView);
            cv_housekeeper = (CardView) view.findViewById(R.id.housekeeper_cardView);
            cv_cook = (CardView) view.findViewById(R.id.cook_cardView);
            cv_painter = (CardView) view.findViewById(R.id.painter_cardView);
            cv_florist = (CardView) view.findViewById(R.id.florist_cardView);
            cv_pesticide = (CardView) view.findViewById(R.id.pesticide_cardView);
            cv_tutor = (CardView) view.findViewById(R.id.tutor_cardView);
            cv_locksmith = (CardView) view.findViewById(R.id.locksmith_cardView);
            cv_grinder = (CardView) view.findViewById(R.id.grinder_cardView);


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


//            tv_servicerName = (TextView) view.findViewById(R.id.tv_servicerName);
////            tv_mobileNo = (TextView) view.findViewById(R.id.tv_mobileNo);
//            tv_destination = (TextView) view.findViewById(R.id.tv_destination);
//
//            //13-Sep-2018
//            tv_distance = (TextView) view.findViewById(R.id.tv_distance);
//            tv_vacantStatus  = (TextView) view.findViewById(R.id.tv_vacantStatus);
//
//
//            iv_vehicleImage=(ImageView) view.findViewById(R.id.iv_vehicleImage);
//            iv_avatar=(ImageView) view.findViewById(R.id.iv_avatar);
//
//            iv_dialImage=(ImageView) view.findViewById(R.id.iv_dialImage);
            //13-Sep-2018
            //iv_smsImage=(ImageView) view.findViewById(R.id.iv_smsImage);

//            ib_favorite = (ImageButton) view.findViewById(R.id.btn_favorite);

//            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

//            ib_rating =(ImageButton) view.findViewById(R.id.btn_rating);
//
//            ib_location =(ImageButton) view.findViewById(R.id.btn_location);

//            tv_yourRating =(TextView) view.findViewById(R.id.tv_yourRating);
//
//            tv_vehicleNo= (TextView) view.findViewById(R.id.tv_vehicleNo);
//
//            //view.setTag(iv_avatar);
//
//            iv_avatar.setOnClickListener(this);
//
//            iv_dialImage.setOnClickListener(this);
            //13-Sep-2018
            //iv_smsImage.setOnClickListener(this);

//            ib_favorite.setOnClickListener(this);
//
//            ib_rating.setOnClickListener(this);
//
//            ib_location.setOnClickListener(this);

//            tv_destination.setTextColor(Color.GRAY);
//            tv_mobileNo.setTextColor(Color.rgb(34, 139, 34)); //FOREST GREEN

            //tv_vacantStatus.setTextColor(Color.rgb(34, 139, 34)); //FOREST GREEN
//            tv_vacantStatus.setTextColor(Color.GRAY);
//
//            tv_yourRating.setTextColor(Color.GRAY);
//
//            tv_vehicleNo.setTextColor(Color.GRAY);

            //------end of favroite button


//            cv_carpenter =(CardView) view.findViewById(R.id.carpenter_cardView);
//
//            iv_carpenter =(ImageView) view.findViewById(R.id.iv_carpenter);
//            iv_carpenter.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {

                Log.i(TAG, "get ID "+ view.getId());

                switch (view.getId())
                {

                    case R.id.carpenter_cardView:{


                        mClickListener.onClickCarpenter(view, getAdapterPosition());
                        break;
                    }
                    case R.id.auto_driver_cardView:{

                        mClickListener.onClickAutoDriver(view, getAdapterPosition());
                        break;
                    }
                    case R.id.cab_driver_cardView:{

                        mClickListener.onClickCabDriver(view, getAdapterPosition());
                        break;

                    }
                    case R.id.electrician_cardView:{

                        mClickListener.onClickElectrician(view, getAdapterPosition());
                        break;
                    }
                    case R.id.plumber_cardView:{

                        mClickListener.onClickPlumber(view, getAdapterPosition());
                        break;
                    }
                    case R.id.tailor_cardView:{

                        mClickListener.onClickTailor(view, getAdapterPosition());
                        break;
                    }
                    case R.id.washer_cardView:{

                        mClickListener.onClickWasher(view, getAdapterPosition());
                        break;
                    }
                    case R.id.courier_cardView:{

                        mClickListener.onClickCourier(view, getAdapterPosition());
                        break;
                    }
                    case R.id.merchant_cardView:{

                        mClickListener.onClickMerchant(view, getAdapterPosition());
                        break;

                    }
                    case R.id.movers_cardView:{

                        mClickListener.onClickMovers(view, getAdapterPosition());
                        break;

                    }
                    case R.id.housekeeper_cardView:{

                        mClickListener.onClickHousekeeper(view, getAdapterPosition());
                        break;

                    }
                    case R.id.cook_cardView:{

                        mClickListener.onClickCook(view, getAdapterPosition());
                        break;

                    }
                    case R.id.painter_cardView:{

                        mClickListener.onClickPainter(view, getAdapterPosition());
                        break;

                    }
                    case R.id.florist_cardView:{

                        mClickListener.onClickFlorist(view, getAdapterPosition());
                        break;

                    }
                    case R.id.pesticide_cardView:{

                        mClickListener.onClickPesticide(view, getAdapterPosition());
                        break;

                    }
                    case R.id.tutor_cardView:{

                        mClickListener.onClickTutor(view, getAdapterPosition());
                        break;

                    }
                    case R.id.locksmith_cardView:{

                        mClickListener.onClickLocksmith(view, getAdapterPosition());
                        break;

                    }
                    case R.id.grinder_cardView:{

                        mClickListener.onClickGrinder(view, getAdapterPosition());
                        break;

                    }


                }

            };

        }


    }



    public ServiceSelectionAdapter(List<Rider> servicesList) {
        this.servicesList = servicesList;
    }

    @Override
    public ServiceSelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottom_service_selection, parent, false);


        return new ServiceSelectionAdapter.ViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(ServiceSelectionAdapter.ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();

        //Rider rider = servicesList.get(position);


//        holder.tv_servicerName.setText("Servicer Name");
//        //holder.tv_mobileNo.setText(rider.getRiderMobileNo());
//        holder.tv_destination.setText(rider.getRiderLocation()); //13-Sep-2018
//
//        holder.tv_distance.setText((String.valueOf(rider.getDistance())) + " " + res.getString(R.string.km)); //13-Sep-2018
//        //holder.tv_vacantStatus.setText(rider.getVacantStatus()); //13-Sep-2018
//        holder.tv_vacantStatus.setText("");

//        //favorite button
//        ImageButton favButton = view.findViewById(R.id.btn_favorite);
//        favButton.setBackgroundResource(R.drawable.fav_yellow);

//        if(rider.getFavorite().equals("N")) {
//            holder.ib_favorite.setImageResource(R.drawable.fav_outline);
//        }
//        else
//        {
//            holder.ib_favorite.setImageResource(R.drawable.fav_yellow);
//        }

//        holder.ratingBar.setRating((float)rider.getAvgRating());

//        holder.tv_yourRating.setText(res.getString(R.string.you_rated) + " " + String.valueOf(rider.getYourRating()));


        //Log.i(TAG, "Rating Adapter "+ rider.getAvgRating());




        //setServiceImage(holder, rider);



        //Log.d(TAG, "riderImage Found "+riderTabs.getJSONObject(i).getString("imageFound"));


        //getDriverImage(holder.iv_driverImage, rider);

//        if (rider.getImageFound() == true) {
//
//           // Log.d(TAG, "driverImage Found 1");
//
//            // Receiving side
////            byte[] data = Base64.decode(driver.getDriverImage(), Base64.DEFAULT);
////            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//////            holder.iv_driverImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 48,48, false));
////            holder.iv_driverImage.setImageBitmap(bmp);
//
//
//        } else {
//
//            holder.iv_driverImage.setImageResource(R.drawable.driver);
//
//        }


//        holder.iv_driverImage.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                /// button click event
//            }
//        });



        //don't display button if already rated
//        if(rider.getYourRating()>0.0)
//        {
//            holder.ib_rating.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            holder.ib_rating.setVisibility(View.VISIBLE);
//
//        }
//
//        if(rider.getYourRating()<=0.0)
//        {
//            holder.tv_yourRating.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            holder.tv_yourRating.setVisibility(View.VISIBLE);
//
//        }



    }


    @Override
    public int getItemCount() {
        return servicesList.size();
    }



//    //to identify the driver position;
//    public int getItemPosition(Rider rider)
//    {
//        Rider rider1=null;
//        int position=-1;
//
//        for(int i=0;i<=servicesList.size()-1;i++)
//        {
//            rider1=servicesList.get(i);
//            if(rider1.getRiderID()==rider.getRiderID())
//            {
//                position=i;
//                break;
//            }
//        }
//        return position;
//    }


    public void setClickListener(ServiceSelectionAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

//    public void setServiceImage(ServiceSelectionAdapter.ViewHolder holder, Rider rider)
//    {
//
//        holder.iv_vehicleImage.setAdjustViewBounds(true);
//        holder.iv_avatar.setAdjustViewBounds(true);
//
//
//        //clear values
//        holder.tv_vehicleNo.setText(rider.getServiceCode());
//
//        //-----set avatar
//        CommonService commonService = new CommonService();
//        commonService.getImage(holder.iv_avatar,CommonService.getImageName(rider,GlobalConstants.IMAGE_AVATAR));
//
//
//        //------set profession image
//        switch (rider.getServiceCode()) {
//
//            case GlobalConstants.SERVICE_CARPENTER:
//            {
//                holder.iv_vehicleImage.setImageResource(R.drawable.carpenter);
//                break;
//            }
//            case GlobalConstants.SERVICE_COURIER:
//            {
//                holder.iv_vehicleImage.setImageResource(R.drawable.courier);
//                break;
//
//            }
//            case GlobalConstants.SERVICE_DRIVER:
//            {
//
//                switch (rider.getVehicleType()){
//
//                    case GlobalConstants.AUTO_CODE:
//                    {
//                        holder.iv_vehicleImage.setImageResource(R.drawable.auto_blue48px);
//                        break;
//
//                    }
//                    case GlobalConstants.CAB_CODE:
//                    {
//                        holder.iv_vehicleImage.setImageResource(R.drawable.taxi_blue48px);
//                        break;
//
//                    }
//
//
//                }
//                holder.tv_vehicleNo.setText(rider.getVehicleNo());
//                break;
//            }
//            case GlobalConstants.SERVICE_ELECTRICIAN:
//            {
//                holder.iv_vehicleImage.setImageResource(R.drawable.electrician);
//                break;
//
//            }
//            case GlobalConstants.SERVICE_MERCHANT:
//            {
//                holder.iv_vehicleImage.setImageResource(R.drawable.merchant);
//                break;
//
//            }
//            case GlobalConstants.SERVICE_PLUMBER:
//            {
//                holder.iv_vehicleImage.setImageResource(R.drawable.plumber);
//                break;
//
//            }
//            case GlobalConstants.SERVICE_TAILOR:
//            {
//                holder.iv_vehicleImage.setImageResource(R.drawable.tailor);
//                break;
//
//            }
//            case GlobalConstants.SERVICE_WASHER:
//            {
//                holder.iv_vehicleImage.setImageResource(R.drawable.washer);
//                break;
//
//            }
//        }
//
//    }
}
