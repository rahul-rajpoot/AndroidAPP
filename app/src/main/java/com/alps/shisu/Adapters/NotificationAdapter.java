package com.alps.shisu.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.NotificationGetterSetter;
import com.alps.shisu.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {

    final NotificationGetterSetter[] listdata;

    public NotificationAdapter(NotificationGetterSetter[] listdata) {
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public NotificationAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_layout, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.Holder holder, int position) {
        final NotificationGetterSetter notificationGetterSetter=listdata[position];
        holder.demonotififytitle.setText(notificationGetterSetter.getNotifymessage());
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        final TextView demonotififytitle;

        public Holder(@NonNull View itemView) {
            super(itemView);

            demonotififytitle=itemView.findViewById(R.id.demonotififytitle);

        }
    }
}
