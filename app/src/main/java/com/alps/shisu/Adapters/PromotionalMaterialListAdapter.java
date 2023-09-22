package com.alps.shisu.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.R;
import com.alps.shisu.modelclass.PromotionalItem;

import java.util.List;

public class PromotionalMaterialListAdapter extends RecyclerView.Adapter<PromotionalMaterialListAdapter.ViewHolder>  {
    final Context context;
    final List<PromotionalItem> list;


    public PromotionalMaterialListAdapter(Context context, List<PromotionalItem> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.promotional_material_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PromotionalItem promotionalItem = list.get(position);
            holder.serialNo.setText(promotionalItem.getId());
            holder.fileName.setText(promotionalItem.getName());
            holder.fileDesc.setText(promotionalItem.getTitle());
            holder.date.setText(promotionalItem.getDate());

            holder.fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLinkInBrowser(context, promotionalItem.getUrl());
                }
            });
    }

    private void openLinkInBrowser(Context context, String url){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.android.chrome");
        try {
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Chrome is probably not installed
            // Try with the default browser
            i.setPackage(null);
            context.startActivity(i);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView date;
        public final TextView fileDesc;
        public final TextView fileName;
        public final TextView serialNo;

        public ViewHolder(View itemView) {
            super(itemView);

            serialNo = itemView.findViewById(R.id.serialNo);
            fileName = itemView.findViewById(R.id.fileName);
            fileDesc = itemView.findViewById(R.id.fileDesc);
            date = itemView.findViewById(R.id.date);

        }
    }

}