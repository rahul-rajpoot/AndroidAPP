package com.alps.shisu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.alps.shisu.FinanacialReport.FinancialReportActivity;
import com.alps.shisu.NetworkManager.NetworkManagerActivity;
import com.alps.shisu.ProfileManager.ProfileMangerActivity;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.Shopping.ShoppingActivity;

import java.util.HashMap;

public class OrderSummaryInvoiceActivity extends BaseActivity {
    String OrderNumber,invoice;
    Toolbar toolbar;
    LinearLayout linearoutproflfooter1,linearoutproflfooter2,linearoutproflfooter4,linearoutproflfooter5;
    int page;
    String ShareLeftLink,ShareRightLink;
    TextView ordershowids;
    SessionManagement sessionManagement;
    HashMap<String,String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary_invoice);

        sessionManagement=new SessionManagement(this.getApplicationContext());
        user=sessionManagement.getUserDetails();

        linearoutproflfooter1=findViewById(R.id.linearoutproflfooter1);
        linearoutproflfooter2=findViewById(R.id.linearoutproflfooter2);
        linearoutproflfooter4=findViewById(R.id.linearoutproflfooter4);
        linearoutproflfooter5=findViewById(R.id.linearoutproflfooter5);

        ordershowids=findViewById(R.id.ids);
        toolbar=findViewById(R.id.toolbar_order_summary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Order Successful");
        toolbar.setTitleTextColor(Color.WHITE);
        Intent intent=getIntent();
        OrderNumber=intent.getStringExtra("orderno");
        invoice=intent.getStringExtra("invurl");
       // ordershow.setText(OrderNumber);
        ordershowids.setText("Your Order No. Is: "+OrderNumber);

       /* setinvoicelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showview=new Intent(getApplicationContext(),InvoiceWebView.class);
                showview.putExtra("in",invoice);
                startActivity(showview);
                //ConvertWebPageToPDF();
            }
        });*/

        linearoutproflfooter1.setOnClickListener(v -> {
            Intent intent12 =new Intent(getApplicationContext(), ProfileMangerActivity.class);
            startActivity(intent12);
        });
        linearoutproflfooter2.setOnClickListener(v -> sharinglink());
        linearoutproflfooter4.setOnClickListener(v -> {
//                Intent intent=new Intent(getApplicationContext(), NetworkManagerActivity.class);
//                startActivity(intent);
            Intent ds = new Intent(getApplicationContext(), NetworkManagerActivity.class);
            ds.putExtra("nm", 1);
            startActivity(ds);
        });
        linearoutproflfooter5.setOnClickListener(v -> {
            Intent intent1 =new Intent(getApplicationContext(), FinancialReportActivity.class);
            startActivity(intent1);
        });
    }

//    private void sharinglink() {
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        String shareBody = user.get(sessionManagement.KEY_SHARE_LEFT);;
//        String shareSub = "Your subject here";
//        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//        startActivity(Intent.createChooser(sharingIntent, "Share using"));
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){

            Intent intent=new Intent(getApplicationContext(), ShoppingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            // overridePendingTransition(R.anim.side_in_left,R.anim.slide_out_right);

            //end the activity
//            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(OrderSummaryInvoiceActivity.this, DashBoard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
