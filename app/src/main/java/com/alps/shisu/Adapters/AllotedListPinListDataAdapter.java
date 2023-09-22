package com.alps.shisu.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.AllotedListPinGetterSetter;
import com.alps.shisu.R;

import java.util.List;

public class AllotedListPinListDataAdapter extends RecyclerView.Adapter<AllotedListPinListDataAdapter.ViewHolder> {
    final Context context;
    final List<AllotedListPinGetterSetter> list;

    public AllotedListPinListDataAdapter(Context context, List<AllotedListPinGetterSetter> listes) {
        this.context = context;
        this.list = listes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi= inflater.inflate(R.layout.alloted_listpin_recyclerview_design, null);
        return new ViewHolder(vi);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllotedListPinGetterSetter allotedListPinListData =list.get(position);
        if (allotedListPinListData.getStatusl().equals("True")) {
            if(allotedListPinListData.getEpin() != null && !allotedListPinListData.getEpin().isEmpty()){
                holder.textepin.setText(allotedListPinListData.getEpin());
            } else {
                holder.textepin.setText("N/A");
            }
            if(allotedListPinListData.getGendate() != null && !allotedListPinListData.getGendate().isEmpty()) {
                holder.textgendate.setText(allotedListPinListData.getGendate());
            } else {
                holder.textgendate.setText("N/A");
            }
            if(allotedListPinListData.getUid() != null && !allotedListPinListData.getUid().isEmpty()) {
                holder.textubid.setText(allotedListPinListData.getUid());
            } else {
                holder.textubid.setText("N/A");
            }
            if(allotedListPinListData.getUdate() != null && !allotedListPinListData.getUdate().isEmpty()) {
                holder.textudate.setText(allotedListPinListData.getUdate());
            } else {
                holder.textudate.setText("N/A");
            }
            if(allotedListPinListData.getUbyname() != null && !allotedListPinListData.getUbyname().isEmpty()) {
                holder.textuname.setText(allotedListPinListData.getUbyname());
            } else {
                holder.textuname.setText("N/A");
            }
        }else {

            holder.textepin.setText("N/A");
            holder.textgendate.setText("N/A");
            holder.textubid.setText("N/A");
            holder.textudate.setText("N/A");
            holder.textuname.setText("N/A");
//            Toast.makeText(context,"No Record Found",Toast.LENGTH_LONG).show();
        }
        holder.textepin.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "World Crown");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "EPIN :"+allotedListPinListData.getEpin());
            context.startActivity(Intent.createChooser(sharingIntent, "abc"));
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textepin;
        public final TextView textgendate;
        public final TextView textubid;
        public final TextView textudate;
        public final TextView textuname;

        public ViewHolder(View itemView) {
            super(itemView);

            textepin=itemView.findViewById(R.id.epin);
            textgendate=itemView.findViewById(R.id.gendate);
            textubid=itemView.findViewById(R.id.uid);
            textudate=itemView.findViewById(R.id.udate);
            textuname=itemView.findViewById(R.id.ubyname);

        }
    }

}
