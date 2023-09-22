package com.alps.shisu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.R;
import com.alps.shisu.modelclass.IncomeLevelDetailsItem;

import java.util.List;

public class LevelDetailsAdapter extends RecyclerView.Adapter<LevelDetailsAdapter.MyViewHolder> {

    final Context context;
    final List<IncomeLevelDetailsItem> list;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(LevelDetailsAdapter.OnItemClickListener listener){
    }

    public LevelDetailsAdapter(Context context, List<IncomeLevelDetailsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LevelDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(context)
                .inflate(R.layout.level_details_layout,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelDetailsAdapter.MyViewHolder myViewHolder, int i) {

        IncomeLevelDetailsItem incomeLevelDetailsItem = list.get(i);
        myViewHolder.level.setText(incomeLevelDetailsItem.getLevel());
        myViewHolder.business.setText(incomeLevelDetailsItem.getBusiness());
//        myViewHolder.partners.setText(incomeLevelDetailsItem.getNoOfMembers());
        myViewHolder.rate.setText(incomeLevelDetailsItem.getRate());
        myViewHolder.totalIncentives.setText(incomeLevelDetailsItem.getIncome());

        if(i == 0) {
            myViewHolder.level_det_row.setBackground(context.getResources().getDrawable(R.drawable.level_details_item_bg));
        } else {
            myViewHolder.level_det_row.setBackground(context.getResources().getDrawable(R.drawable.level_det_top_less));
        }

        //  Picasso.with(context).load(list.get(i).getImgurl()).into(myViewHolder.loadimage);
//        imageLoader.displayImage(list.get(i).getImgurl(),myViewHolder.loadimage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        final TextView level;
        final TextView business;
        TextView partners;
        final TextView rate;
        final TextView totalIncentives;
        final LinearLayout level_det_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            level_det_row= itemView.findViewById(R.id.level_det_row);
            level= itemView.findViewById(R.id.level);
            business= itemView.findViewById(R.id.business);
//            partners=  itemView.findViewById(R.id.partners);
            rate=  itemView.findViewById(R.id.rate);
            totalIncentives=  itemView.findViewById(R.id.totalIncentive);
            itemView.setOnClickListener(v -> {
//                    if (mListener != null){
//                        int position=getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            mListener.onItemClick(position);
//                        }
//                    }
            });
        }
    }
}

