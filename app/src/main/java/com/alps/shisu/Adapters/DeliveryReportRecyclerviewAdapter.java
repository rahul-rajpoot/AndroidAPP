package com.alps.shisu.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.alps.shisu.GetterSetter.DeliveryReportGetter;
import com.alps.shisu.InvoiceWebView;
import com.alps.shisu.R;

import java.util.List;

public class DeliveryReportRecyclerviewAdapter extends RecyclerView.Adapter<DeliveryReportRecyclerviewAdapter.ViewHolder> {
    final Context context;
    final List<DeliveryReportGetter> list;
    ViewHolder viewHolder;

    public DeliveryReportRecyclerviewAdapter(Context context, List<DeliveryReportGetter> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.delivery_report_recyclerview_design, null);
          viewHolder= new ViewHolder(view);

        viewHolder.textorderno.setOnClickListener(v -> {
       //     Intent intent=new Intent(context,InvoiceWebView.class);
        //    intent.putExtra("in",list.get(viewHolder.getAdapterPosition()).getOrderNourl());
        //    context.startActivity(intent);
            //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            DeliveryReportGetter myTeamList = list.get(position);
            holder.textsno.setText(String.valueOf(position+1));
            // holder.textstatus.setText(myTeamList.getStatus());
            holder.textorderno.setText(myTeamList.getOrderNodr());
            holder.textorderdate.setText(myTeamList.getOrderDatedr());
            holder.textinvNo.setText(myTeamList.getInvNOdr());
            holder.textinvdate.setText(myTeamList.getInvDatedr());
            holder.textAmount.setText(myTeamList.getAmountdr());
            holder.textqty.setText(myTeamList.getQtydr());
            holder.textBv.setText(myTeamList.getBVdr());
            holder.PPvdrrc.setText(myTeamList.getPV());
            // holder.textstatus.setText(myTeamList.getStatusdr());

            list.get(viewHolder.getAdapterPosition()).getInvNOdr().equals("N/A");
            holder.textinvNo.setText("N/A");
            viewHolder.textinvNo.setOnClickListener(v -> {
                Intent intent = new Intent(context, InvoiceWebView.class);
                intent.putExtra("in", list.get(viewHolder.getAdapterPosition()).getInvNOutl());
                context.startActivity(intent);
                //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            });

           /* if (list.get(viewHolder.getAdapterPosition()).getInvNOdr().equals("N/A")){
                holder.textinvNo.setText("N/A");
                //  Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
            }else {
                viewHolder.textinvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, InvoiceWebView.class);
                        intent.putExtra("in", list.get(viewHolder.getAdapterPosition()).getInvNOutl());
                        context.startActivity(intent);
                        //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textsno;
        final TextView textAmount;
        final TextView textBv;
        final TextView PPvdrrc;
        final TextView textinvdate;
        final TextView textinvNo;
        final TextView textorderdate;
        final TextView textorderno;
        final TextView textqty;
        TextView textstatus;

        public ViewHolder(View itemView) {
            super(itemView);
            textsno=(TextView)itemView.findViewById(R.id.snodr);
           // textstatus=(TextView)itemView.findViewById(R.id.status);
            textAmount=(TextView)itemView.findViewById(R.id.totalamountdrrc);
            textBv=(TextView)itemView.findViewById(R.id.bvdrrc);
            PPvdrrc=itemView.findViewById(R.id.PPvdrrc);
            textinvdate=(TextView)itemView.findViewById(R.id.invdatedrrc);
            textinvNo=(TextView)itemView.findViewById(R.id.invnodrrc);
            textorderdate=(TextView)itemView.findViewById(R.id.orderdatedrrc);
            textorderno=(TextView)itemView.findViewById(R.id.ordernodrrc);
            textqty=(TextView)itemView.findViewById(R.id.qtydrrc);
          //  textstatus=(TextView)itemView.findViewById(R.id.statusplrc);

        }
    }

}
