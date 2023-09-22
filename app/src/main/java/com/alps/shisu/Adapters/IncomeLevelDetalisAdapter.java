package com.alps.shisu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.R;
import com.alps.shisu.modelclass.IncomeLevelDetailsItem;

import java.util.List;

public class IncomeLevelDetalisAdapter extends RecyclerView.Adapter<IncomeLevelDetalisAdapter.MyViewHolder> {

    final Context context;
    final List<IncomeLevelDetailsItem> list;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(IncomeLevelDetalisAdapter.OnItemClickListener listener){
    }

    public IncomeLevelDetalisAdapter(Context context, List<IncomeLevelDetailsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IncomeLevelDetalisAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(context)
                .inflate(R.layout.business_details_adapter,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeLevelDetalisAdapter.MyViewHolder myViewHolder, int i) {

        IncomeLevelDetailsItem incomeLevelDetailsItem = list.get(i);
        myViewHolder.level.setText(incomeLevelDetailsItem.getLevel());
        myViewHolder.business.setText(incomeLevelDetailsItem.getBusiness());
        myViewHolder.income.setText(incomeLevelDetailsItem.getIncome());

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
        final TextView income;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            level= itemView.findViewById(R.id.level);
            business=  itemView.findViewById(R.id.business);
            income=  itemView.findViewById(R.id.income);
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
