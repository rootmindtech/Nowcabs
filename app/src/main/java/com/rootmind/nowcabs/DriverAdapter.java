package com.rootmind.nowcabs;

import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by saikirangandham on 7/31/17.
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {


    private static final String TAG = "DriverAdapter";


    public interface ItemClickListener {
        //void onClick(View view, int position);
        void onClickDriverImage(View view, int position);
        void onClickDialImage(View view, int position);
        void onClickSMSImage(View view, int position);
        void onClickFavorite(View view, int position);
        void onClickRating(View view, int position);
        void onClickLocation(View view, int position);

    }


    private List<Driver> driversList;
    private  ItemClickListener  mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_driverName, tv_mobileNo, tv_destination, tv_distance, tv_vacantStatus, tv_yourRating, tv_vehicleNo;
        public ImageView iv_vehicleImage;
        public ImageView iv_driverImage;
        public ImageView iv_dialImage;
        public ImageView iv_smsImage;
        public ImageButton ib_favorite;
        public RatingBar ratingBar;
        public ImageButton ib_rating;
        public ImageButton ib_location;

        private String mItem;
        private TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);


            tv_driverName = (TextView) view.findViewById(R.id.tv_driverName);
//            tv_mobileNo = (TextView) view.findViewById(R.id.tv_mobileNo);
            tv_destination = (TextView) view.findViewById(R.id.tv_destination);

            //13-Sep-2018
            tv_distance = (TextView) view.findViewById(R.id.tv_distance);
            tv_vacantStatus  = (TextView) view.findViewById(R.id.tv_vacantStatus);


            iv_vehicleImage=(ImageView) view.findViewById(R.id.iv_vehicleImage);
            iv_driverImage=(ImageView) view.findViewById(R.id.iv_driverImage);

            iv_dialImage=(ImageView) view.findViewById(R.id.iv_dialImage);
            //13-Sep-2018
            //iv_smsImage=(ImageView) view.findViewById(R.id.iv_smsImage);

            ib_favorite = (ImageButton) view.findViewById(R.id.btn_favorite);

            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

            ib_rating =(ImageButton) view.findViewById(R.id.btn_rating);

            ib_location =(ImageButton) view.findViewById(R.id.btn_location);

            tv_yourRating =(TextView) view.findViewById(R.id.tv_yourRating);

            tv_vehicleNo= (TextView) view.findViewById(R.id.tv_vehicleNo);

            view.setTag(iv_driverImage);

            iv_driverImage.setOnClickListener(this);

            iv_dialImage.setOnClickListener(this);
            //13-Sep-2018
            //iv_smsImage.setOnClickListener(this);

            ib_favorite.setOnClickListener(this);

            ib_rating.setOnClickListener(this);

            ib_location.setOnClickListener(this);

            tv_destination.setTextColor(Color.GRAY);
//            tv_mobileNo.setTextColor(Color.rgb(34, 139, 34)); //FOREST GREEN

            //tv_vacantStatus.setTextColor(Color.rgb(34, 139, 34)); //FOREST GREEN
            tv_vacantStatus.setTextColor(Color.GRAY);

            tv_yourRating.setTextColor(Color.GRAY);

            tv_vehicleNo.setTextColor(Color.GRAY);

            //------end of favroite button



        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                //mClickListener.onClick(view, getAdapterPosition());

                Log.i(TAG, "get ID "+ view.getId());


                if(view.getId()==iv_driverImage.getId()){


                    mClickListener.onClickDriverImage(view, getAdapterPosition());


                }

                if(view.getId()==iv_dialImage.getId()){


                    mClickListener.onClickDialImage(view, getAdapterPosition());


                }

                //13-Sep-2018
//                if(view.getId()==iv_smsImage.getId()){
//
//                    mClickListener.onClickSMSImage(view, getAdapterPosition());
//                }


                if(view.getId()==ib_favorite.getId()){


                    view.setEnabled(false);
                    mClickListener.onClickFavorite(view, getAdapterPosition());
                    view.setEnabled(true);


                }
                if(view.getId()==ib_rating.getId()){


                    view.setEnabled(false);
                    mClickListener.onClickRating(view, getAdapterPosition());
                    view.setEnabled(true);


                }
                if(view.getId()==ib_location.getId()){


                    view.setEnabled(false);
                    mClickListener.onClickLocation(view, getAdapterPosition());
                    view.setEnabled(true);


                }





            };

        }


    }



    public DriverAdapter(List<Driver> driversList) {
        this.driversList = driversList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drivers_list_row, parent, false);


        return new ViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();

        Driver driver = driversList.get(position);
        holder.tv_driverName.setText(driver.getDriverName());
//        holder.tv_mobileNo.setText(driver.getDriverMobileNo());
        holder.tv_destination.setText(driver.getDriverLocation()); //13-Sep-2018

        holder.tv_distance.setText((String.valueOf(driver.getDistance())) + " " + res.getString(R.string.km)); //13-Sep-2018
        //holder.tv_vacantStatus.setText(driver.getVacantStatus()); //13-Sep-2018
        holder.tv_vacantStatus.setText("");

//        //favorite button
//        ImageButton favButton = view.findViewById(R.id.btn_favorite);
//        favButton.setBackgroundResource(R.drawable.fav_yellow);

        if(driver.getFavorite().equals("N")) {
            holder.ib_favorite.setImageResource(R.drawable.fav_outline);
        }
        else
        {
            holder.ib_favorite.setImageResource(R.drawable.fav_yellow);
        }

        holder.ratingBar.setRating((float)driver.getAvgRating());

        holder.tv_yourRating.setText(res.getString(R.string.you_rated) + " " + String.valueOf(driver.getYourRating()));

        holder.tv_vehicleNo.setText(driver.getDriverVehicleNo());

        Log.i(TAG, "Rating Adapter "+ driver.getAvgRating());


        holder.iv_vehicleImage.setAdjustViewBounds(true);

        if (driver.getDriverVehicleType().equals(GlobalConstants.CAB_CODE)) {

            holder.iv_vehicleImage.setImageResource(R.drawable.taxi_blue48px);
        } else {
            holder.iv_vehicleImage.setImageResource(R.drawable.auto_blue48px);
        }



        holder.iv_driverImage.setAdjustViewBounds(true);

        //Log.d(TAG, "driverImage Found "+driverTabs.getJSONObject(i).getString("imageFound"));


        getDriverImage(holder.iv_driverImage, driver);

//        if (driver.getImageFound() == true) {
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
        if(driver.getYourRating()>0.0)
        {
            holder.ib_rating.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.ib_rating.setVisibility(View.VISIBLE);

        }

        if(driver.getYourRating()<=0.0)
        {
            holder.tv_yourRating.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.tv_yourRating.setVisibility(View.VISIBLE);

        }



    }


    @Override
    public int getItemCount() {
        return driversList.size();
    }



    //to identify the driver position;
    public int getItemPosition(Driver driver)
    {
        Driver driver1=null;
        int position=-1;

        for(int i=0;i<=driversList.size()-1;i++)
        {
            driver1=driversList.get(i);
            if(driver1.getDriverID()==driver.getDriverID())
            {
                position=i;
                break;
            }
        }
        return position;
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void getDriverImage(final ImageView imageView, Driver driver)
    {

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance(GlobalConstants.FIREBASE_URL);

            StorageReference storageRef = firebaseStorage.getReference();
            final StorageReference avatarRef = storageRef.child(GlobalConstants.FB_IMAGE_FOLDER + driver.getDriverID() + "_avatar.jpg");

            avatarRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'

                    Log.d(TAG, "onSuccess  " + uri);

                    Picasso.get().load(uri).into(imageView); //http://square.github.io/picasso/
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    //Toast.makeText(RiderMapActivity.this, "Image download failed", Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(R.drawable.driver);


                }
            });

    }



    //  private String[] mDataset;

//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView mTextView;
//        public ViewHolder(TextView v) {
//            super(v);
//            mTextView = v;
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public DriverAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public DriverAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_main, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.mTextView.setText(mDataset[position]);
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mDataset.length;
//    }

}
