package com.alps.shisu.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.PayoutDeductionGetterSetter;
import com.alps.shisu.R;

import java.util.List;

public class PayoutDeductionListDataAdapter extends RecyclerView.Adapter<PayoutDeductionListDataAdapter.ViewHolder>  {
    final Context context;
    final List<PayoutDeductionGetterSetter> list;
    Dialog dialog;
    private final String currencySymbol;

    public PayoutDeductionListDataAdapter(Context context, List<PayoutDeductionGetterSetter> list, String currencySymbol) {
        this.context = context;
        this.list = list;
        this.currencySymbol = currencySymbol;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.payoutdeduction_recyclerview_design, null);
        final ViewHolder viewHolder= new ViewHolder(view);

        dialog=new Dialog(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY );
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);
        } else {
            dialog.getWindow().setType( WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);
        }
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.payoutdeduction_dialogbox_popup);
        //  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        dialog.setTitle("Payout Deduction");

        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        viewHolder.page.setOnClickListener(view1 -> {

            TextView PayID = dialog.findViewById(R.id.payoutdeductionpayid);
            TextView PayTotalIncome = dialog.findViewById(R.id.payoutdeductiontotalincome);
            TextView PayTDSRate = dialog.findViewById(R.id.payoutdeductiontdsrate);
            TextView PayTDSAmt = dialog.findViewById(R.id.payoutdeductiontdsamt);
            TextView PayProcessing = dialog.findViewById(R.id.payoutdeductionprocessing);
            TextView PayProcessingRate = dialog.findViewById(R.id.payoutdeductionprocessingrate);
            TextView PayLockAmount = dialog.findViewById(R.id.payoutdeductionlockamt);
            TextView PayTotalDeduction = dialog.findViewById(R.id.payoutdeductiontotaldeduction);
            TextView PayNetPayable = dialog.findViewById(R.id.payoutdeductionnetpayable);

            PayID.setText(list.get(viewHolder.getAdapterPosition()).getPd_payid());
//                PayTotalIncome.setText("₹" + list.get(viewHolder.getAdapterPosition()).getPd_totalincomes());
            PayTotalIncome.setText(currencySymbol + list.get(viewHolder.getAdapterPosition()).getPd_totalincomes());
            PayTDSAmt.setText(list.get(viewHolder.getAdapterPosition()).getPd_tdsamount());
            PayTDSRate.setText("TDS @"+list.get(viewHolder.getAdapterPosition()).getPd_tdsrate());
            PayProcessing.setText(list.get(viewHolder.getAdapterPosition()).getPd_proamount());
            PayProcessingRate.setText("Processing @"+list.get(viewHolder.getAdapterPosition()).getPd_prorate());
            PayLockAmount.setText(list.get(viewHolder.getAdapterPosition()).getPd_lockamt());
            PayTotalDeduction.setText(list.get(viewHolder.getAdapterPosition()).getPd_totaldeduction());
            PayNetPayable.setText(list.get(viewHolder.getAdapterPosition()).getPd_netpayable());

            dialog.show();

        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PayoutDeductionGetterSetter payoutDeductionListData=list.get(position);

        if (payoutDeductionListData.getStatus().equals("True")){
            // holder.textIDDS.setText(directSponsorListData.getIdds());
            //   holder.textpid.setText(payoutReportListData.getPayid());
            holder.textpayid.setText(payoutDeductionListData.getPd_payid());
            holder.texttotalincome.setText(payoutDeductionListData.getPd_totalincomes());
//            holder.texttdsamt.setText(payoutDeductionListData.getPd_tdsamount());
//            holder.textprosamt.setText(payoutDeductionListData.getPd_proamt());
//            holder.textlockamt.setText(payoutDeductionListData.getPd_lockamt());
//            holder.texttotaldeduction.setText(payoutDeductionListData.getPd_totaldeduction());
//            holder.textnetpayable.setText(payoutDeductionListData.getPd_netpayable());
            holder.link.setText("View Details");

        }
        else {
            holder.textpayid.setText("N/A");
            holder.texttotalincome.setText("N/A");
//            Toast.makeText(context,"No Record Found",Toast.LENGTH_LONG).show();



        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textpayid;
        public final TextView texttotalincome;
        public TextView textsno;
        public TextView textprosamt;
        public TextView textlockamt;
        public TextView texttotaldeduction;
        public TextView textnetpayable;
        private final TextView link;
        private final CardView page;
        public ViewHolder(View itemView) {
            super(itemView);

            //  textIDDS = itemView.findViewById(R.id.dsid);
            textpayid=itemView.findViewById(R.id.pdeduction_payid);
            texttotalincome=itemView.findViewById(R.id.pdeduction_totalincome);
//            texttdsamt=itemView.findViewById(R.id.pd_tdsamt);
//            textprosamt=itemView.findViewById(R.id.pd_processingamt);
//            textlockamt=itemView.findViewById(R.id.pd_lockamount);
//            texttotaldeduction=itemView.findViewById(R.id.pd_totaldeduction);
//            textnetpayable=itemView.findViewById(R.id.pd_netpayable);
            // textsno=itemView.findViewById(R.id.sno);
            link=itemView.findViewById(R.id.uniques);
            page=itemView.findViewById(R.id.payoutdeductionpage);




        }
    }

}