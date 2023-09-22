package com.alps.shisu.Adapters;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alps.shisu.CurrencyWallet.CurrencyWalletActivity;
import com.alps.shisu.DashBoard;
import com.alps.shisu.FinanacialReport.FinancialReportActivity;
import com.alps.shisu.GetterSetter.DataModel;
import com.alps.shisu.KycManager.KycManager;
import com.alps.shisu.LoginActivity;
import com.alps.shisu.NetworkManager.NetworkManagerActivity;
import com.alps.shisu.ProfileManager.ProfileMangerActivity;
import com.alps.shisu.R;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.bumptech.glide.Glide;

import java.util.List;


public class DashboardGridAdapter extends RecyclerView.Adapter<DashboardGridAdapter.ViewHolder> {

    final DashBoard context;
    final List<DataModel> list;

    public DashboardGridAdapter(DashBoard context, List<DataModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(context)
                .inflate(R.layout.dashboard_grid_layout,viewGroup,false);



        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(list.get(i).getName());
        final int img=list.get(i).getImage();
        Glide.with(context).load(img).into(viewHolder.imageView);
        viewHolder.linearLayout.setOnClickListener(v -> {
            String value= list.get(i).getName();
            String textvalue=list.get(i).getName();

               /*
                   carItemList.add(new DataModel("Genrate Pin", R.drawable.key));
        carItemList.add(new DataModel("List Pin", R.drawable.listpin));
        carItemList.add(new DataModel("View Profile", R.drawable.viewprofile));
        carItemList.add(new DataModel("Welcome Letter",R.drawable.viewprofile));
        carItemList.add(new DataModel("Matching Tree",R.drawable.mt));
        carItemList.add(new DataModel("Direct Sponsor", R.drawable.network));
        carItemList.add(new DataModel("Team List", R.drawable.listpin));
        carItemList.add(new DataModel("Payout Report", R.drawable.pr));
        carItemList.add(new DataModel("Payout Statement", R.drawable.report));
        carItemList.add(new DataModel("Payout Deduction",R.drawable.report));
        carItemList.add(new DataModel("TDS Report",R.drawable.report));
        carItemList.add(new DataModel("Statement", R.drawable.statement));
        carItemList.add(new DataModel("Purchase",R.drawable.shopping_black));
        carItemList.add(new DataModel("Share",R.drawable.share));pf
        carItemList.add(new DataModel("Logout", R.drawable.shutdown));
                */
            if (value.equals("Generate Pin"))
            {
                Intent intent=new Intent(context, KycManager.class);
                intent.putExtra("km",0);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
            if (value.equals("List Pin"))
            {
                Intent intent=new Intent(context, KycManager.class);
                intent.putExtra("km",1);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
            if (value.equals("Your Profile"))
            {
                Intent intent=new Intent(context, ProfileMangerActivity.class);
                intent.putExtra("pf",0);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
            if (value.equals("Welcome Letter"))
            {
                Intent intent=new Intent(context, ProfileMangerActivity.class);
                intent.putExtra("pf",1);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
//                    if (value.equals("Matching Tree"))
                if (value.equals("Level Details"))
                {
                    Intent intent=new Intent(context, NetworkManagerActivity.class);
                    intent.putExtra("nm",0);
                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                }
            if (value.equals("Direct Referrals"))
            {
                Intent intent=new Intent(context, NetworkManagerActivity.class);
                intent.putExtra("nm",1);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
            if (value.equals("Level details"))
            {
                Intent intent=new Intent(context, NetworkManagerActivity.class);
                intent.putExtra("nm",2);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }

            if (value.equals("Payout Report"))
            {
                Intent intent=new Intent(context, FinancialReportActivity.class);
                intent.putExtra("fr",0);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
            if (value.equals("Payout Statement"))
            {
                Intent intent=new Intent(context, FinancialReportActivity.class);
                intent.putExtra("fr",1);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
            if (value.equals("Payout Deduction"))
            {
                Intent intent=new Intent(context, FinancialReportActivity.class);
                intent.putExtra("fr",2);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
            if (value.equals("TDS Report"))
            {
                Intent intent=new Intent(context, FinancialReportActivity.class);
                intent.putExtra("fr",3);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }

            if (value.equals("Statement"))
            {


                Intent intent=new Intent(context, CurrencyWalletActivity.class);
                intent.putExtra("cw",0);
                // AlertDialog.Builder builder=new AlertDialog.Builder(context);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                //context.snack();
            }
            if (value.equals("Shop Now"))
            {


                Intent intent=new Intent(context, ShoppingActivity.class);
                intent.putExtra("s",0);
                // AlertDialog.Builder builder=new AlertDialog.Builder(context);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                //context.snack();
            }
            if (value.equals("Purchase List"))
            {
                Intent intent=new Intent(context, ShoppingActivity.class);
                intent.putExtra("s",1);
                // AlertDialog.Builder builder=new AlertDialog.Builder(context);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                //context.snack();
            }
            /* if (context.ShareLeftLink.isEmpty()&&context.ShareRightLink.isEmpty()){
                        context.Snackbar("Something Went Wrong");
                    }else{*/
            /* AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Share Joinig Link");
                builder.setPositiveButton("Left", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = context.ShareLeftLink;
                        String shareSub = "Your subject here";
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }
                });
                builder.setNegativeButton("Right", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = context.ShareRightLink;
                        String shareSub = "Your subject here";
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();*/
            //}
            // context.dialog();
            if (value.equals("Logout"))
            {


                Intent intent=new Intent(context, LoginActivity.class);
                // AlertDialog.Builder builder=new AlertDialog.Builder(context);
                // context..logoutUser();
                context.sessionManagement.logoutUser();
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                //context.snack();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final ImageView imageView;
        final TextView textView;
        final LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageshow);
            textView=(TextView)itemView.findViewById(R.id.txt_title);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.line);

        }
    }
}
