package com.alps.shisu.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.SoldButNotJoinListPinGetterSetter;
import com.alps.shisu.R;

import java.util.List;

public class SoldButNotJoinListpinListDataAdapter  extends RecyclerView.Adapter<SoldButNotJoinListpinListDataAdapter.ViewHolder> {
    final Context context;
    final List<SoldButNotJoinListPinGetterSetter> list;

    public SoldButNotJoinListpinListDataAdapter(Context context, List<SoldButNotJoinListPinGetterSetter> listes) {
        this.context = context;
        this.list = listes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi= inflater.inflate(R.layout.soldbutnotjoin_listpin_recyclerview_design, null);
        return new ViewHolder(vi);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SoldButNotJoinListPinGetterSetter soldButNotJoinListPinListData =list.get(position);
        if (soldButNotJoinListPinListData.getStatuss().equals("True")) {
            if(soldButNotJoinListPinListData.getEpin2() != null  && !soldButNotJoinListPinListData.getEpin2().isEmpty()) {
                holder.textepin2.setText(soldButNotJoinListPinListData.getEpin2());
            } else {
                holder.textepin2.setText("N/A");
            }
            if(soldButNotJoinListPinListData.getGendate2() != null  && !soldButNotJoinListPinListData.getGendate2().isEmpty()) {
                holder.textgendate2.setText(soldButNotJoinListPinListData.getGendate2());
            } else {
                holder.textgendate2.setText("N/A");
            }
            if(soldButNotJoinListPinListData.getUid2() != null  && !soldButNotJoinListPinListData.getUid2().isEmpty()) {
                holder.textubid2.setText(soldButNotJoinListPinListData.getUid2());
            } else {
                holder.textubid2.setText("N/A");
            }
            if(soldButNotJoinListPinListData.getUdate2() != null  && !soldButNotJoinListPinListData.getUdate2().isEmpty()) {
                holder.textudate2.setText(soldButNotJoinListPinListData.getUdate2());
            } else {
                holder.textudate2.setText("N/A");
            }
            if(soldButNotJoinListPinListData.getUbyname2() != null  && !soldButNotJoinListPinListData.getUbyname2().isEmpty()) {
                holder.textuname2.setText(soldButNotJoinListPinListData.getUbyname2());
            } else {
                holder.textuname2.setText("N/A");
            }
        }else {
            holder.textepin2.setText("N/A");
            holder.textgendate2.setText("N/A");
            holder.textubid2.setText("N/A");
            holder.textudate2.setText("N/A");
            holder.textuname2.setText("N/A");
//            Toast.makeText(context,"No Record Found",Toast.LENGTH_LONG).show();
        }
        holder.textepin2.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, "World Crown");
            share.putExtra(Intent.EXTRA_TEXT, "EPIN :"+soldButNotJoinListPinListData.getEpin2());
            context.startActivity(Intent.createChooser(share, "EPIN"));
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView textepin2;
        public final TextView textgendate2;
        public final TextView textubid2;
        public final TextView textudate2;
        public final TextView textuname2;

        public ViewHolder(View itemView) {
            super(itemView);

            textepin2=itemView.findViewById(R.id.epin2);
            textgendate2=itemView.findViewById(R.id.gendate2);
            textubid2=itemView.findViewById(R.id.uid2);
            textudate2=itemView.findViewById(R.id.udate2);
            textuname2=itemView.findViewById(R.id.ubyname2);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            ClipboardManager myClickboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData myClip = ClipData.newPlainText("text", textepin2.getText().toString());
            myClickboard.setPrimaryClip(myClip);
        }
    }

}
