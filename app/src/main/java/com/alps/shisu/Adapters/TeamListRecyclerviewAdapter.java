package com.alps.shisu.Adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alps.shisu.GetterSetter.TeamListGetterSetter;
import com.alps.shisu.R;

import java.util.List;

public class TeamListRecyclerviewAdapter  extends RecyclerView.Adapter<TeamListRecyclerviewAdapter.ViewHolder>  {
    final Context context;
    final List<TeamListGetterSetter> list;


    public TeamListRecyclerviewAdapter(Context context, List<TeamListGetterSetter> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.teamlist_recyclerview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamListGetterSetter downlineListData=list.get(position);
        if (downlineListData!=null) {
            holder.textID.setText(downlineListData.getIds());
            holder.textStatus.setText(downlineListData.getStatuss());
            holder.textRepurchse.setText(downlineListData.getRepurchases());
            holder.textDirect.setText(downlineListData.getDirects());
            holder.textName.setText(downlineListData.getNames());

            if (downlineListData.getStatuss().equals("Red")) {
                holder.textID.setTextColor(Color.RED);
                holder.textStatus.setTextColor(Color.RED);
                holder.textRepurchse.setTextColor(Color.RED);
                holder.textDirect.setTextColor(Color.RED);
                holder.textName.setTextColor(Color.RED);
            } else if (downlineListData.getStatuss().equals("Green")) {
                holder.textID.setTextColor(Color.GREEN);
                holder.textStatus.setTextColor(Color.GREEN);
                holder.textRepurchse.setTextColor(Color.GREEN);
                holder.textDirect.setTextColor(Color.GREEN);
                holder.textName.setTextColor(Color.GREEN);
            } else {
                holder.textID.setTextColor(Color.rgb(85, 26, 139));
                holder.textStatus.setTextColor(Color.rgb(85, 26, 139));
                holder.textRepurchse.setTextColor(Color.rgb(85, 26, 139));
                holder.textDirect.setTextColor(Color.rgb(85, 26, 139));
                holder.textName.setTextColor(Color.rgb(85, 26, 139));
            }
        } //            Toast.makeText(context,"No Record Found",Toast.LENGTH_LONG).show();


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textID;
        public final TextView textStatus;
        public final TextView textName;
        public final TextView textDirect;
        public final TextView textRepurchse;

        public ViewHolder(View itemView) {
            super(itemView);

            textID = itemView.findViewById(R.id.textView3);
            textName = itemView.findViewById(R.id.textView7);
            textDirect = itemView.findViewById(R.id.textView9);
            textRepurchse = itemView.findViewById(R.id.textView11);
            textStatus = itemView.findViewById(R.id.textView4);

        }
    }

}
