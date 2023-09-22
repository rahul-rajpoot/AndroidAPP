package com.alps.shisu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.GetterSetter.TdsReportGetterSetter;
import com.alps.shisu.R;

import java.util.List;

public class TDSReportDataAdapter extends RecyclerView.Adapter<TDSReportDataAdapter.ViewHolder> {
    final Context context;
    final List<TdsReportGetterSetter> list;
    // Dialog myDialog;
    //   Dialog myDialog;

    public TDSReportDataAdapter(Context context, List<TdsReportGetterSetter> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tdsreport_recyclerview_design, null);
        return new ViewHolder(view);

//        final ViewHolder viewHolder = new ViewHolder(view);

/*
        myDialog =new Dialog(context);

        myDialog=new Dialog(context);
        myDialog.setContentView(R.layout.tdsreport_dialogbox_popup);
        myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        myDialog.getWindow().setBackgroundDrawableResource(R.drawable.side_nav_bar);

        myDialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            TextView payoutcuttoff=myDialog.findViewById(R.id.cutoffmonth);
                TextView tdstotalincome=myDialog.findViewById(R.id.totalamount);
                TextView tdsrate=myDialog.findViewById(R.id.tdsrate);
                TextView tdsamount=myDialog.findViewById(R.id.tdsamount);

                payoutcuttoff.setText(list.get(viewHolder.getAdapterPosition()).getCutoofmonth());
                tdstotalincome.setText(list.get(viewHolder.getAdapterPosition()).getTdstotalincome());
                tdsrate.setText(list.get(viewHolder.getAdapterPosition()).getTdsratetds());
                tdsamount.setText(list.get(viewHolder.getAdapterPosition()).getTdsamounttds());
myDialog.show();

            }
        });

*/
        //return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TdsReportGetterSetter tdsReportListData = list.get(position);
        if (tdsReportListData.getTdsstatus().equals("False")) {
            holder.textcutoffmonth.setText("N/A");
            holder.texttotalincome.setText("N/A");
            holder.texttdsrate.setText("N/A");
            holder.texttdsamount.setText("N/A");

//            Toast.makeText(context,"No Record Found",Toast.LENGTH_LONG).show();

        }
        else {
            // holder.textIDDS.setText(directSponsorListData.getIdds());
            holder.textcutoffmonth.setText(tdsReportListData.getCutoofmonth());
            holder.texttotalincome.setText(tdsReportListData.getTdstotalincome());
            holder.texttdsrate.setText(tdsReportListData.getTdsratetds());
            holder.texttdsamount.setText(tdsReportListData.getTdsamounttds());
//    holder.links.setText("View Details");

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textcutoffmonth;
        public final TextView texttotalincome;
        public final TextView texttdsrate;
        public final TextView texttdsamount;
        // private LinearLayout item;
        //  private TextView links;
        public ViewHolder(View itemView) {
            super(itemView);

            //  textIDDS = itemView.findViewById(R.id.dsid);
            textcutoffmonth = itemView.findViewById(R.id.payidtds);
            texttotalincome=itemView.findViewById(R.id.totalamounttds);
            texttdsrate=itemView.findViewById(R.id.tdsrateree);
            texttdsamount=itemView.findViewById(R.id.tdsamtree);

            //  links = itemView.findViewById(R.id.linktds);
            //    item = itemView.findViewById(R.id.tdspage);
//


        }
    }

}