package com.alps.shisu.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.ActivitionOrderGetterSetter;
import com.alps.shisu.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ActivitionOrderAdapter extends RecyclerView.Adapter<ActivitionOrderAdapter.MyViewHolder> {

    final Context context;
    final List<ActivitionOrderGetterSetter>list;
    final ImageLoader imageLoader;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public ActivitionOrderAdapter(Context context, List<ActivitionOrderGetterSetter> list, ImageLoader imageLoader) {
        this.context = context;
        this.list = list;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(context)
                .inflate(R.layout.activion_order_layout,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.txt_title.setText(list.get(i).getCatName());

      //  Picasso.with(context).load(list.get(i).getImgurl()).into(myViewHolder.loadimage);
        imageLoader.displayImage(list.get(i).getImgurl(),myViewHolder.loadimage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        final TextView txt_title;
        final ImageView loadimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_title=(TextView)itemView.findViewById(R.id.txt_title);
            loadimage=(ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(v -> {
                if (mListener != null){
                    int position=getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
