package com.rootmind.nowcabs;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RideJobAdapter  extends RecyclerView.Adapter<RideJobAdapter.ViewHolder>  {


    private static final String TAG = "RideJobAdapter";


    public interface ItemClickListener {
        void onClickJob(View view, int position);
        void onClickDelete(View view, int position);


    }

    private List<Job> jobList;

    private RideJobAdapter.ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_jobName, tv_rate, tv_makerDateTime;//, tv_status, ;
        public ImageView iv_avatar,  iv_delete;
        public CardView cardView;


        public ViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);



            tv_jobName = (TextView) view.findViewById(R.id.tv_jobName);
            tv_rate = (TextView) view.findViewById(R.id.tv_rate);
            //tv_status= (TextView) view.findViewById(R.id.tv_status);
            iv_avatar=(ImageView) view.findViewById(R.id.iv_avatar);
            cardView=(CardView) view.findViewById(R.id.card_view);
            iv_delete=(ImageView) view.findViewById(R.id.iv_delete);
            tv_makerDateTime=(TextView) view.findViewById(R.id.tv_makerDateTime);


            cardView.setOnClickListener(this);
            iv_delete.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {

                Log.i(TAG, "get ID "+ view.getId());


                switch (view.getId())
                {
                    case R.id.card_view: {
                        mClickListener.onClickJob(view, getAdapterPosition());
                        break;

                    }
                    case R.id.iv_delete: {


                        mClickListener.onClickDelete(view, getAdapterPosition());
                        break;
                    }
                }


            };

        }


    }



    public RideJobAdapter(List<Job> jobList) {

        this.jobList = jobList;
    }

    @Override
    public RideJobAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ride_job_list_row, parent, false);


        return new RideJobAdapter.ViewHolder(itemView);
    }





    @Override
    public void onBindViewHolder(RideJobAdapter.ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();

        Job job = jobList.get(position);


        holder.tv_jobName.setText(job.getJobName());
        holder.tv_rate.setText((job.getCurrency()==null?"":job.getCurrency()) +" "+ new DecimalFormat("#").format(job.getRate()));
        //holder.tv_status.setText(job.getStatus());
        holder.tv_makerDateTime.setText(job.getMakerDateTime());

        //holder.tv_status.setBackgroundColor(Color.parseColor(GlobalConstants.DARKGREEN));
        //holder.tv_status.setTextColor(Color.WHITE);

        CommonService.getServiceImage(holder.iv_avatar,job.getServiceCode());

    }


    @Override
    public int getItemCount() {
        return jobList.size();
    }




    public void setClickListener(RideJobAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
}
