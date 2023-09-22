package com.alps.shisu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.R;
import com.alps.shisu.modelclass.SupportTicketItem;

import java.util.List;

public class SupportTicketListAdapter extends RecyclerView.Adapter<SupportTicketListAdapter.ViewHolder>  {
    final Context context;
    final List<SupportTicketItem> list;


    public SupportTicketListAdapter(Context context, List<SupportTicketItem> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.support_ticket_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SupportTicketItem supportTicketItem = list.get(position);
            holder.ticketNo.setText(supportTicketItem.getMsgID());
//            holder.userName.setText(supportTicketItem.getFullName());
//            holder.contactNo.setText(supportTicketItem.getContact());
//            holder.emailId.setText(supportTicketItem.getEmail());
            holder.date.setText(supportTicketItem.getMsgDate());
            holder.subject.setText(supportTicketItem.getMsgSubject());
            holder.action.setText(supportTicketItem.getActiveFlag());
            holder.status.setText(supportTicketItem.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView userName;
//        public TextView contactNo;
//        public final TextView emailId;
        public final TextView date;
        public final TextView subject;
        public final TextView action;
        public final TextView status;
        public final TextView ticketNo;

        public ViewHolder(View itemView) {
            super(itemView);

            ticketNo = itemView.findViewById(R.id.ticketNo);
//            userName = itemView.findViewById(R.id.userName);
//            contactNo = itemView.findViewById(R.id.contactNo);
//            emailId=itemView.findViewById(R.id.emailId);
            date=itemView.findViewById(R.id.date);
            subject=itemView.findViewById(R.id.subject);
            action=itemView.findViewById(R.id.action);
            status=itemView.findViewById(R.id.status);

        }
    }

}