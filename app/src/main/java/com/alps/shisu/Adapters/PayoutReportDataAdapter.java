package com.alps.shisu.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.PayoutReportGetterSetter;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;

import java.util.List;

public class PayoutReportDataAdapter extends RecyclerView.Adapter<PayoutReportDataAdapter.ViewHolder>  {
    final Context context;
    final List<PayoutReportGetterSetter> list;
    SessionManagement sessionManagement;
    SharedPreferences preferences;
    // Dialog myDialog;
    Dialog myDialog;
    public String reqBonus;
    public String silver;
    public String gold;
    public String platinum;
    public String diamond;
    public String diamondOverriding;
    public String carFund;
    public String houseFund;

    public String levelBonus;

    public PayoutReportDataAdapter(Context context, List<PayoutReportGetterSetter> list) {
        this.context = context;
        this.list = list;
    }
    @SuppressLint("ResourceType")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.payoutreport_recyclerview_design, null);
        final ViewHolder viewHolder= new ViewHolder(view);
        myDialog =new Dialog(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY );
            myDialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);
            myDialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;

            //  myDialog.getWindow().setTitle("Payout Report");
            //  myDialog.getWindow().getAttributes().setTitle("Payout Report");
        } else {
            myDialog.getWindow().setType( WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            myDialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);
            myDialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        }
        // myDialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);
        //  myDialog.setTitle("Payout Report");
        myDialog=new Dialog(context);
        myDialog.setContentView(R.layout.payoutreport_popup_dialogbox);
        myDialog.setTitle("Payout Report");

        //  myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY );
        //  myDialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);
        //font
        //Commented by anshu
//        final Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/digital-7.ttf");

        viewHolder.link.setOnClickListener(view1 -> {

            TextView payid= myDialog.findViewById(R.id.payID);
            TextView CFL= myDialog.findViewById(R.id.cfl);
            TextView CFR= myDialog.findViewById(R.id.cfr);
            TextView CUrrentR=(TextView)myDialog.findViewById(R.id.curntr);
            TextView CUrrentL=(TextView)myDialog.findViewById(R.id.curntl);
            TextView TotalL=(TextView)myDialog.findViewById(R.id.totall);
            TextView TotalR=(TextView)myDialog.findViewById(R.id.totalr);
            TextView FLUSH=(TextView)myDialog.findViewById(R.id.flush);
            TextView PaidPV=(TextView)myDialog.findViewById(R.id.paidpv);
//            TextView PVIncome=(TextView)myDialog.findViewById(R.id.pvincome);
            TextView reqBonus= myDialog.findViewById(R.id.reqBonus);
            TextView levelBonus= myDialog.findViewById(R.id.levelBonus);
            TextView silver= myDialog.findViewById(R.id.silverClubBonus);
            TextView gold= myDialog.findViewById(R.id.goldClubBonus);
            TextView platinum= myDialog.findViewById(R.id.platinumClubBonus);
            TextView diamond= myDialog.findViewById(R.id.diamondClubBonus);
            TextView diamondOverriding= myDialog.findViewById(R.id.diamondOverridindBonus);
            TextView carFund= myDialog.findViewById(R.id.carFund);
            TextView houseFund= myDialog.findViewById(R.id.houseFund);
//            TextView RoyalIncome=(TextView)myDialog.findViewById(R.id.royalincome);
            TextView tincome=(TextView)myDialog.findViewById(R.id.tincome);

            //Commented by anshu

//            payid.setTypeface(typeface);
//                CFL.setTypeface(typeface);
//                CFR.setTypeface(typeface);
//                CUrrentR.setTypeface(typeface);
//                CUrrentL.setTypeface(typeface);
//                TotalL.setTypeface(typeface);
//                TotalR.setTypeface(typeface);
//                FLUSH.setTypeface(typeface);
//                PaidPV.setTypeface(typeface);
//                PVIncome.setTypeface(typeface);
//                DINCOME.setTypeface(typeface);
//                RoyalIncome.setTypeface(typeface);
//                tincome.setTypeface(typeface);


            payid.setText(list.get(viewHolder.getAdapterPosition()).getPayid());
            CFL.setText(list.get(viewHolder.getAdapterPosition()).getCfL());
            CFR.setText(list.get(viewHolder.getAdapterPosition()).getCfR());
            CUrrentL.setText(list.get(viewHolder.getAdapterPosition()).getCurrentR());
            CUrrentR.setText(list.get(viewHolder.getAdapterPosition()).getCurrentL());
            TotalL.setText(list.get(viewHolder.getAdapterPosition()).getTotalL());
            TotalR.setText(list.get(viewHolder.getAdapterPosition()).getTotalR());
            FLUSH.setText(list.get(viewHolder.getAdapterPosition()).getFlushes());
            PaidPV.setText(list.get(viewHolder.getAdapterPosition()).getMatching());
            reqBonus.setText(list.get(viewHolder.getAdapterPosition()).getReqBonus());
            levelBonus.setText(list.get(viewHolder.getAdapterPosition()).getLevelBonus());
            silver.setText(list.get(viewHolder.getAdapterPosition()).getSilver());
            gold.setText(list.get(viewHolder.getAdapterPosition()).getGold());
            platinum.setText(list.get(viewHolder.getAdapterPosition()).getPlatinum());
            diamond.setText(list.get(viewHolder.getAdapterPosition()).getDiamond());
            diamondOverriding.setText(list.get(viewHolder.getAdapterPosition()).getDiamondOverriding());
            carFund.setText(list.get(viewHolder.getAdapterPosition()).getCarFund());
            houseFund.setText(list.get(viewHolder.getAdapterPosition()).getHouseFund());
            tincome.setText(list.get(viewHolder.getAdapterPosition()).getTotalincomes());
            //   Toast.makeText(context,"Click Position"+String.valueOf(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();


            myDialog.show();
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PayoutReportGetterSetter payoutReportListData=list.get(position);
        if (payoutReportListData!=null) {
            // holder.textIDDS.setText(directSponsorListData.getIdds());
            holder.textpid.setText(payoutReportListData.getPayid());
//        holder.textcfl.setText(payoutReportListData.getCfL());
//        holder.textcfr.setText(payoutReportListData.getCfR());
//        holder.currentl.setText(payoutReportListData.getCurrentL());
//        holder.currentr.setText(payoutReportListData.getCurrentR());
//        holder.texttotoalr.setText(payoutReportListData.getTotalR());
//        holder.texttotall.setText(payoutReportListData.getTotalL());
//        holder.textflush.setText(payoutReportListData.getFlush());
//        holder.textdirectincome.setText(payoutReportListData.getDirectincome());
//        holder.textmaching.setText(payoutReportListData.getMatching());
//        holder.textroyaltyincome.setText(payoutReportListData.getRoyaltyincome());
            holder.texttotalincome.setText(payoutReportListData.getTotalincomes());
            //    holder.textpbincome.setText(payoutReportListData.getPvincome());
            holder.link.setText("View");

        }
        //            Toast.makeText(context,"No Record Found",Toast.LENGTH_LONG).show();

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textpid;
        public final TextView texttotalincome;
        public TextView textcfr;
        public TextView currentl;
        public TextView currentr;
        public TextView texttotoalr;
        public TextView texttotall;
        //        public TextView textflush,textdirectincome,textmaching,textroyaltyincome,texttotalincome,textpbincome;
        private LinearLayout item;
        private final TextView link;
        public ViewHolder(View itemView) {
            super(itemView);

            //  textIDDS = itemView.findViewById(R.id.dsid);
            textpid=itemView.findViewById(R.id.payid);
//
//            textcfl=itemView.findViewById(R.id.payoutreport_cfl);
//            textcfr=itemView.findViewById(R.id.payoutreport_cfr);
//            currentl=itemView.findViewById(R.id.payoutreport_currentl);
//            currentr=itemView.findViewById(R.id.payoutreport_currentr);
//            texttotoalr=itemView.findViewById(R.id.payoutreport_totalr);
//            texttotall=itemView.findViewById(R.id.payoutreport_totall);
//
//            textflush=itemView.findViewById(R.id.payoutreport_flush);
//            textdirectincome=itemView.findViewById(R.id.payoutreport_directincome);
//            textmaching=itemView.findViewById(R.id.payoutreport_matching);
//            textroyaltyincome=itemView.findViewById(R.id.payoutreport_royaltyincome);
//            textpbincome=itemView.findViewById(R.id.payoutreport_pvincome);
            texttotalincome=itemView.findViewById(R.id.totalincome);
            link=(TextView) itemView.findViewById(R.id.unique);
//            item= itemView.findViewById(R.id.main);

            //Commented by anshu
//        Typeface typeface=Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/digital-7.ttf");
//            textpid.setTypeface(typeface);
//            texttotalincome.setTypeface(typeface);

        }
    }

}
