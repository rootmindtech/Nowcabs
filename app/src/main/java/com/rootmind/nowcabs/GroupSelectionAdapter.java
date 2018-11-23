package com.rootmind.nowcabs;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GroupSelectionAdapter extends RecyclerView.Adapter<GroupSelectionAdapter.ViewHolder>  {


    private static final String TAG = "GroupSelectionAdapter";


    public interface ItemClickListener {
        void onClickGroup(View view, int position);
//        void onClickDelete(View view, int position);


    }

    private List<GroupRider> groupList;

    private GroupSelectionAdapter.ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_groupID, tv_name, tv_status;
        public ImageView iv_avatar;  //iv_delete;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);



            tv_groupID = (TextView) view.findViewById(R.id.tv_groupID);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_status= (TextView) view.findViewById(R.id.tv_status);
            iv_avatar=(ImageView) view.findViewById(R.id.iv_avatar);
            cardView=(CardView) view.findViewById(R.id.card_view);
            //iv_delete=(ImageView) view.findViewById(R.id.iv_delete);


            cardView.setOnClickListener(this);
            //iv_delete.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {

                Log.i(TAG, "get ID "+ view.getId());


                switch (view.getId())
                {
                    case R.id.card_view: {
                        mClickListener.onClickGroup(view, getAdapterPosition());
                        break;

                    }
                    case R.id.iv_delete: {


//                        mClickListener.onClickDelete(view, getAdapterPosition());
//                        break;
                    }
                }


            };

        }


    }



    public GroupSelectionAdapter(List<GroupRider> groupList) {

        this.groupList = groupList;
    }

    @Override
    public GroupSelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottom_group_selection, parent, false);


        return new GroupSelectionAdapter.ViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(GroupSelectionAdapter.ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();

        GroupRider groupRider = groupList.get(position);


        holder.tv_groupID.setText(groupRider.getGroupID());
        //holder.tv_name.setText(groupRider.getName());
        holder.tv_status.setText(groupRider.getStatus());

        holder.tv_status.setTextColor(Color.GREEN);



//
//        setServiceImage(holder, ride);


    }


    //    public void setServiceImage(GroupSelectionAdapter.ViewHolder holder, Group ride)
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
        return groupList.size();
    }




    public void setClickListener(GroupSelectionAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
}
