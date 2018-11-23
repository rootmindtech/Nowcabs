package com.rootmind.nowcabs;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GroupRegisterAdapter extends RecyclerView.Adapter<GroupRegisterAdapter.ViewHolder>  {


    private static final String TAG = "GroupRegisterAdapter";


    public interface ItemClickListener {
        void onClickDelete(View view, int position);
        void onClickPublic(View view, int position);


    }

    private List<GroupRider> groupRiderList;

    private GroupRegisterAdapter.ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_name, tv_mobileNo, tv_status; //tv_groupID
        public ImageView iv_avatar, iv_delete;
        public CheckBox cb_public;


        public ViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);



//            tv_groupID = (TextView) view.findViewById(R.id.tv_groupID);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_mobileNo = (TextView) view.findViewById(R.id.tv_mobileNo);
            tv_status= (TextView) view.findViewById(R.id.tv_status);
            iv_avatar=(ImageView) view.findViewById(R.id.iv_avatar);
            cb_public=(CheckBox) view.findViewById(R.id.cb_public);
            iv_delete=(ImageView) view.findViewById(R.id.iv_delete);


//            tv_groupID.setVisibility(View.INVISIBLE);


            cb_public.setOnClickListener(this);
            iv_delete.setOnClickListener(this);



        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {

                Log.i(TAG, "get ID "+ view.getId());


                switch (view.getId()) {

                    case R.id.iv_delete: {


                        mClickListener.onClickDelete(view, getAdapterPosition());
                        break;
                    }
                    case R.id.cb_public: {


                        mClickListener.onClickPublic(view, getAdapterPosition());
                        break;
                    }


                }




            };

        }



    }



    public GroupRegisterAdapter(List<GroupRider> groupRiderList) {

        this.groupRiderList = groupRiderList;
    }

    @Override
    public GroupRegisterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_rider_list_row, parent, false);


        return new GroupRegisterAdapter.ViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(final GroupRegisterAdapter.ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();

        GroupRider groupRider = groupRiderList.get(position);


//        holder.tv_groupID.setText(groupRider.getGroupID());
        holder.tv_name.setText(groupRider.getGroup().getName());
        holder.tv_mobileNo.setText(groupRider.getGroup().getName());
        holder.tv_status.setText(groupRider.getStatus());

        holder.tv_status.setBackgroundColor(Color.parseColor(GlobalConstants.DARKGREEN));
        holder.tv_status.setTextColor(Color.WHITE);

        if(groupRider.getPublicView().equals(GlobalConstants.YES_CODE))
        {
            holder.cb_public.setChecked(true);
        }
        else
        {
            holder.cb_public.setChecked(false);
        }


//-----set avatar
        CommonService commonService = new CommonService();

        //Log.d(TAG, "setServiceImage getImageName1  " + rider.getRiderID());

        //Log.d(TAG, "setServiceImage getImageName  " + CommonService.getImageName(rider,GlobalConstants.IMAGE_AVATAR));

//        String imageFileName= CommonService.getImageName(groupRider.rider,GlobalConstants.IMAGE_AVATAR);
//
//        Log.d(TAG, "setServiceImage getImage ID  " + groupRider.rider.getRiderID());
//
//        Log.d(TAG, "setServiceImage getImageName  " + imageFileName);
//
//        if(imageFileName!=null && !imageFileName.trim().equals("")) {
//            commonService.getImage(holder.iv_avatar, imageFileName);
//        }
//        else
//        {
//            holder.iv_avatar.setImageResource(R.drawable.avatar_outline48);
//        }


        holder.cb_public.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                holder.cb_public.setSelected(isChecked);

                //mClickListener.onClickPublic(null, holder.getLayoutPosition());

            }
        });


//
//        setServiceImage(holder, ride);


    }


    //    public void setServiceImage(GroupRegisterAdapter.ViewHolder holder, GroupRider ride)
//    {
//
//        holder.iv_vehicleImage.setAdjustViewBounds(true);
//        holder.iv_avatar.setAdjustViewBounds(true);
//
//
//        //clear values
//        holder.tv_vehicleNo.setText(ride.getServiceCode());
//
////        //-----set avatar
////        CommonService commonService = new CommonService();
////
////        //Log.d(TAG, "setServiceImage getImageName1  " + ride.getrideID());
////
////        //Log.d(TAG, "setServiceImage getImageName  " + CommonService.getImageName(ride,GlobalConstants.IMAGE_AVATAR));
////
////        String imageFileName= CommonService.getImageName(ride,GlobalConstants.IMAGE_AVATAR);
////
////        Log.d(TAG, "setServiceImage getImage ID  " + ride.getrideID());
////
////        Log.d(TAG, "setServiceImage getImageName  " + imageFileName);
////
////        if(imageFileName!=null && !imageFileName.trim().equals("")) {
////            commonService.getImage(holder.iv_avatar, imageFileName);
////        }
////        else
////        {
////            holder.iv_avatar.setImageResource(R.drawable.avatar_outline48);
////        }
//
//        //------set profession image
//        switch (ride.getServiceCode()) {
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
//                holder.tv_vehicleNo.setText(ride.getVehicleNo());
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
    @Override
    public int getItemCount() {
        return groupRiderList.size();
    }




    public void setClickListener(GroupRegisterAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}
