package com.alps.shisu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;


import com.alps.shisu.GetterSetter.PurchaseListGetter;
import com.alps.shisu.InvoiceWebView;
import com.alps.shisu.R;

import java.util.ArrayList;
import java.util.List;

public class PurchasedlistRecyclerviewAdapter extends RecyclerView.Adapter<PurchasedlistRecyclerviewAdapter.ViewHolder> {
    final Context context;
    final List<PurchaseListGetter> list;
    private List<PurchaseListGetter> contactListFiltered;
    ViewHolder viewHolder;

    public PurchasedlistRecyclerviewAdapter(Context context, List<PurchaseListGetter> list, List<PurchaseListGetter> contactListFiltered) {
        this.context = context;
        this.list = list;
        this.contactListFiltered=contactListFiltered;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.purchasedlist_recyclerview_design, null);
          viewHolder= new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            PurchaseListGetter myTeamList = list.get(position);
            holder.textsno.setText(String.valueOf(position+1));
            holder.textorderno.setText(myTeamList.getOrderNo());
            holder.textorderdate.setText(myTeamList.getOrderDate());

            holder.textinvNo.setText(myTeamList.getInvNO());
            holder.textinvdate.setText(myTeamList.getInvDate());
            holder.textAmount.setText(myTeamList.getAmount());
            holder.textqty.setText(myTeamList.getQty());
            holder.textBv.setText(myTeamList.getBV());
            holder.pvplrc.setText(myTeamList.getPV());
            holder.textstatus.setText(myTeamList.getStatus());
            if (myTeamList.getStatus().equals("Confirmed")){
                holder.textstatus.setTextColor(Color.parseColor("#468847"));
            }else {
                holder.textstatus.setTextColor(Color.parseColor("#f89406"));
            }
            viewHolder.textorderno.setOnClickListener(v -> {
                Intent intent=new Intent(context, InvoiceWebView.class);
                intent.putExtra("in",list.get(viewHolder.getAdapterPosition()).getOrderNourl());
                context.startActivity(intent);
                //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            });

            try {
                list.get(viewHolder.getAdapterPosition()).getInvNO().equals("N/A");
                holder.textinvNo.setText("N/A");
                viewHolder.textinvNo.setOnClickListener(v -> {
                    Intent intent = new Intent(context, InvoiceWebView.class);
                    intent.putExtra("in", list.get(viewHolder.getAdapterPosition()).getInvNOutl());
                    context.startActivity(intent);
                    // LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


       /* if (list.get(viewHolder.getAdapterPosition()).getInvNO().equals("N/A")){
            holder.textinvNo.setText("N/A");
            //  Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
        }else {
            viewHolder.textinvNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InvoiceWebView.class);
                    intent.putExtra("in", list.get(viewHolder.getAdapterPosition()).getInvNOutl());
                    context.startActivity(intent);
                   // LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
                }
            });
        }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = list;
                } else {
                    List<PurchaseListGetter> filteredList = new ArrayList<>();
                    for (PurchaseListGetter row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getOrderNo().toLowerCase().contains(charString.toLowerCase()) || row.getInvNO().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactListFiltered = (ArrayList<PurchaseListGetter>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface ContactsAdapterListener {
        void onContactSelected(PurchaseListGetter contact);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         final TextView textsno;
        final TextView textAmount;
        final TextView textBv;
        final TextView pvplrc;
        final TextView textinvdate;
        final TextView textinvNo;
        final TextView textorderdate;
        final TextView textorderno;
        final TextView textqty;
        final TextView textstatus;
        final CardView item;
        public ViewHolder(View itemView) {
            super(itemView);

            item=itemView.findViewById(R.id.purchaselisrll);
            textsno=(TextView)itemView.findViewById(R.id.snopl);
            textAmount=(TextView)itemView.findViewById(R.id.totalamountplrc);
            textBv=(TextView)itemView.findViewById(R.id.bvplrc);
            pvplrc=(TextView)itemView.findViewById(R.id.pvplrc);
            textinvdate=(TextView)itemView.findViewById(R.id.invdateplrc);
            textinvNo=(TextView)itemView.findViewById(R.id.invnoplrc);
            textorderdate=(TextView)itemView.findViewById(R.id.orderdateplrc);
            textorderno=(TextView)itemView.findViewById(R.id.ordernoplrc);
            textqty=(TextView)itemView.findViewById(R.id.qtyplrc);
            textstatus=(TextView)itemView.findViewById(R.id.statusplrc);




        }
    }

}
