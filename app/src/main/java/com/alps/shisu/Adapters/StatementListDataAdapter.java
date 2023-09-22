package com.alps.shisu.Adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alps.shisu.CurrencyWallet.StatementFragment;
import com.alps.shisu.GetterSetter.StatementGetterSetter;
import com.alps.shisu.R;

import java.util.List;

public class StatementListDataAdapter extends RecyclerView.Adapter<StatementListDataAdapter.ViewHolder> {
    final Context context;
    final List<StatementGetterSetter> list;
    final StatementFragment statementFragment;

    public StatementListDataAdapter(Context context, List<StatementGetterSetter> listes) {
        this.context = context;
        this.list = listes;
        statementFragment=new StatementFragment();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.statement_recyclerview_design, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StatementGetterSetter statementListData =list.get(position);
        if (statementListData.getStatusstatement().equals("True")) {
            holder.textremark.setText(statementListData.getRemarks());
            holder.textdate.setText(statementListData.getPaydate());
            holder.textamt.setText(statementListData.getAmount());
            holder.textamt.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
            holder.textremark.setSingleLine(false);

            if (statementListData.getAmount().startsWith("+")) {
                holder.textamt.setTextColor(Color.GREEN);
            } else {
                holder.textamt.setTextColor(Color.BLACK);
            }
        }
        //  Snackbar snackbar= (Snackbar) Snackbar.make(context,"No Record Found",Snackbar.LENGTH_LONG);
        // snackbar.show();
        //  Snackbar snackbar = (Snackbar) Snackbar.make(statementFragment.relativeLayoutstatement, "No Record Found", Snackbar.LENGTH_SHORT);
        //    snackbar.show();
        //            Toast.makeText(context,"No Record Found",Toast.LENGTH_LONG).show();

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textremark;
        public final TextView textdate;
        public final TextView textamt;
        public String p[];

        public ViewHolder(View itemView) {
            super(itemView);
            textamt=itemView.findViewById(R.id.Amount);
            textremark=itemView.findViewById(R.id.Remark);
            textdate=itemView.findViewById(R.id.date);

        }
    }

}

