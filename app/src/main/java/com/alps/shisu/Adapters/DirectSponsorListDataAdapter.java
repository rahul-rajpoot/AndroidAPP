package com.alps.shisu.Adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.DirectSponsorGetterSetter;
import com.alps.shisu.R;

import java.util.List;

public class DirectSponsorListDataAdapter extends RecyclerView.Adapter<DirectSponsorListDataAdapter.ViewHolder>  {
    final Context context;
    final List<DirectSponsorGetterSetter> list;


    public DirectSponsorListDataAdapter(Context context, List<DirectSponsorGetterSetter> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.directsponsor_recyclerview_design, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DirectSponsorGetterSetter directSponsorListData=list.get(position);
        if (directSponsorListData.getStatusds().equals("True")) {
            holder.textIDDS.setText(directSponsorListData.getIdds());
            holder.textNameDS.setText(directSponsorListData.getNameds());
            holder.textDesignarion.setText(directSponsorListData.getDesignation());
            holder.textregdate.setText(directSponsorListData.getRegdate());
            holder.regnods.setText(directSponsorListData.getSponsorCount());
            holder.prevMonth.setText("Previous Month: \nPRBV = "+directSponsorListData.getP_PBV()+"\nGRBV = "+ directSponsorListData.getP_GBV() +"\nTRBV = "+ directSponsorListData.getP_TBV());
            holder.currentMonth.setText("Current Month: \nPRBV = "+directSponsorListData.getC_PBV()+"\nGRBV = "+ directSponsorListData.getC_GBV()  +"\nTRBV = "+ directSponsorListData.getC_TBV());
            holder.totalMonth.setText("Total Month: \nPRBV = "+directSponsorListData.getC_PBV()+"\nGRBV = "+ directSponsorListData.getC_GBV()  +"\nTRBV = "+ directSponsorListData.getC_TBV());

            if (directSponsorListData.getPackagestatus().equals("Green")) {
                holder.rootView.setBackground(context.getResources().getDrawable(R.drawable.green_bg));
//                holder.textIDDS.setTextColor(Color.GREEN);
//                holder.textNameDS.setTextColor(Color.GREEN);
//                holder.textStatusPackage.setTextColor(Color.GREEN);
//                holder.textregdate.setTextColor(Color.GREEN);
//                holder.regnods.setTextColor(Color.GREEN);
                /*holder.goldenimage.setVisibility(View.VISIBLE);
                holder.blueimage.setVisibility(View.GONE);*/
            } else if (directSponsorListData.getPackagestatus().equals("Red")) {
                holder.rootView.setBackground(context.getResources().getDrawable(R.drawable.red_bg));
//                holder.textIDDS.setTextColor(Color.RED);
//                holder.textNameDS.setTextColor(Color.RED);
//                holder.textStatusPackage.setTextColor(Color.RED);
//                holder.textregdate.setTextColor(Color.RED);
//                holder.regnods.setTextColor(Color.RED);
               /* holder.goldenimage.setVisibility(View.GONE);
                holder.blueimage.setVisibility(View.VISIBLE);*/
            } else {
                holder.textIDDS.setTextColor(Color.rgb(85, 26, 139));
                holder.textNameDS.setTextColor(Color.rgb(85, 26, 139));
//                holder.textStatusPackage.setTextColor(Color.rgb(85, 26, 139));
                holder.textregdate.setTextColor(Color.rgb(85, 26, 139));
                holder.regnods.setTextColor(Color.rgb(85, 26, 139));
            }
        }
        else {
            // holder.textStatusPackage.setVisibility(View.VISIBLE);
            holder.textIDDS.setText("N/A");
            holder.textNameDS.setText("N/A");
//            holder.textStatusPackage.setText("N/A");
            holder.textregdate.setText("N/A");
            holder.regnods.setText("N/A");
//            Toast.makeText(context,"No Record Found",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textIDDS;
        public TextView textDesignarion;
        public TextView txtPackagestatusStatus;
        public final TextView textNameDS;
        public final TextView textregdate;
        public final TextView currentMonth;
        public final TextView prevMonth;
        public final TextView totalMonth;
        public final TextView regnods;
        public ImageView blueimage,goldenimage;
        public final RelativeLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.rootView);
            textIDDS = itemView.findViewById(R.id.dsid);
            textNameDS = itemView.findViewById(R.id.dsname);
            textDesignarion = itemView.findViewById(R.id.designation);
            regnods=itemView.findViewById(R.id.dsregno);
            textregdate=itemView.findViewById(R.id.desregdat);
            currentMonth=itemView.findViewById(R.id.currentMonth);
            prevMonth=itemView.findViewById(R.id.prevMonth);
            totalMonth=itemView.findViewById(R.id.totalMonth);
           /* blueimage=itemView.findViewById(R.id.blueimage);
            goldenimage=itemView.findViewById(R.id.goldenimage);*/

        }
    }

}