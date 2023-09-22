package com.alps.shisu.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.AOProductGetter;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;

public class AOProductAdapter extends RecyclerView.Adapter<AOProductAdapter.MyViewHolder> {
    final Context context;
    final List<AOProductGetter> list;
    private OnItemClickListener mListener;
    final ImageLoader imageLoader;
    SessionManagement sessionManagement;
    HashMap<String,String> user;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public AOProductAdapter(Context context, List<AOProductGetter> list,ImageLoader imageLoader) {
        this.context = context;
        this.list = list;
        this.imageLoader=imageLoader;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ao_product_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        AOProductGetter aoProductGetter =list.get(i);
        sessionManagement=new SessionManagement(context.getApplicationContext());
        user=sessionManagement.getUserDetails();
        myViewHolder.textamount.setText(user.get(SessionManagement.KEY_CURRENCY)+aoProductGetter.getDps());
        myViewHolder.textamounbv.setText("BV:"+aoProductGetter.getBVs());
        myViewHolder.textname.setText(aoProductGetter.getPname());
        String img=aoProductGetter.getImageUrl();
        imageLoader.displayImage(img,myViewHolder.ImnageCategires);
      //  Picasso.with(context).load(img).into(myViewHolder.ImnageCategires);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        final TextView textname;
        final TextView textamount;
        final TextView textamounbv;
        TextView textpid;
        TextView textcutamount;
        public final ImageView ImnageCategires;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textname=(TextView)itemView.findViewById(R.id.txt_title_product);
            textamount=(TextView)itemView.findViewById(R.id.txt_price_product);
            textamounbv=(TextView)itemView.findViewById(R.id.txt_bv_product);

            ImnageCategires=(ImageView)itemView.findViewById(R.id.img_product);
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
