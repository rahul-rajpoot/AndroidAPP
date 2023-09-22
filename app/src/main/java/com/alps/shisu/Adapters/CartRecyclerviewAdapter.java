package com.alps.shisu.Adapters;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alps.shisu.CartActivity;
import com.alps.shisu.Database.DatabaseHelper;
import com.alps.shisu.GetterSetter.CartGetter;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CartRecyclerviewAdapter  extends RecyclerView.Adapter<CartRecyclerviewAdapter.ViewHolder>{
    Context context;
    final List<CartGetter> list;
    CartGetter dataModel;
    private final CartActivity cartActivity;
    final SessionManagement sessionManagement;
    final HashMap<String,String> user;
    public  double total=0;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        //void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public CartRecyclerviewAdapter(List<CartGetter> listes,CartActivity cartActivity) {
        this.cartActivity=cartActivity;
        // this.context = context;
        this.list = listes;
        sessionManagement=new SessionManagement(cartActivity);
        user=sessionManagement.getUserDetails();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cartActivity);
        View view = inflater.inflate(R.layout.cartlayout_recyclerview_design,parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            dataModel =list.get(position);
            holder.pname.setText(dataModel.getProductesName());
            holder.pcp.setText(user.get(SessionManagement.KEY_CURRENCY)+dataModel.getProductesCp().toString());
            Locale locale2=new Locale("en","IN");
            NumberFormat fmt2=NumberFormat.getCurrencyInstance(locale2);
            Double mrp=Double.parseDouble(dataModel.getProductesMrpc());
            holder.pmrp.setText(user.get(SessionManagement.KEY_CURRENCY)+list.get(position).getProductesMrpc());
            //holder.pcp.setText(dataModel.getProductesCp());
            final String img=dataModel.getProductesImage();
            Glide.with(cartActivity).load(img).into(holder.showproductimage);
            //if (holder.qtybtn.setNumber(list.get(position).getProductesQty()).){}
            holder.qtybtn.setNumber(list.get(position).getProductesQty());
            holder.qtybtn.setOnValueChangeListener((view, oldValue, newValue) -> {
                CartGetter cartGetter=list.get(position);
                cartGetter.setProductesQty(String.valueOf(newValue));
                new DatabaseHelper(cartActivity).updateCart(cartGetter);
                //update total

                List<CartGetter> orders=new DatabaseHelper(cartActivity).getCart(cartActivity.Userid);
                for (CartGetter item:orders)
                    total = (Double.parseDouble(cartGetter.getProductesCp().toString())) * (Double.parseDouble(item.getProductesQty()));
                Locale locale=new Locale("en","IN");
                NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);

                String data=new DatabaseHelper(cartActivity).TotalCal(cartGetter.getUserLoginid());
                double sum=Double.parseDouble(data);
                cartActivity.txtTotalPrice.setText(user.get(SessionManagement.KEY_CURRENCY)+ sum);

            });
            // new DatabaseHelper(cartActivity)
            Locale locale=new Locale("en","IN");
            NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
            //  double price=(Double.parseDouble(list.get(position).getProductesCp().toString()))*(Double.parseDouble(list.get(position).getProductesQty()));
            holder.pcp.setText(user.get(SessionManagement.KEY_CURRENCY)+list.get(position).getProductesCp().toString());
            // double bvtotal = (Double.parseDouble(list.get(position).getProductesBv()))*(Double.parseDouble(list.get(position).getProductesQty()));
            holder.pbv.setText("BV "+list.get(position).getProductesBv());
          //  holder.pv.setText("PV "+list.get(position).getProductesPv());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView pname;
        final TextView pbv;
        final TextView pmrp;
        final TextView pcp;
        final ImageView showproductimage;
        final ImageView remove;
        final ElegantNumberButton qtybtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pname = (TextView) itemView.findViewById(R.id.cart_item_name);
            pbv = (TextView) itemView.findViewById(R.id.cart_item_bv);
            pmrp = (TextView) itemView.findViewById(R.id.cart_item_cutaprice);
            pcp = (TextView) itemView.findViewById(R.id.cart_item_price);
            remove=(ImageView)itemView.findViewById(R.id.itemdelete);

            showproductimage = (ImageView) itemView.findViewById(R.id.cart_image);
            qtybtn = (ElegantNumberButton) itemView.findViewById(R.id.number_cart_button);

            pmrp.setPaintFlags(pmrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            remove.setOnClickListener(v -> {
                if (mListener != null){
                    int position=getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        mListener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}
