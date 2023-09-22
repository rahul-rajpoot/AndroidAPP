//package com.alps.aplusmart.AddressDetail;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import com.alps.aplusmart.BaseActivity;
//import com.alps.aplusmart.DashBoard;
//import com.alps.aplusmart.FinanacialReport.FinancialReportActivity;
//import com.alps.aplusmart.NetworkManager.NetworkManagerActivity;
//import com.alps.aplusmart.ProfileManager.ProfileMangerActivity;
//import com.alps.aplusmart.R;
//import com.alps.aplusmart.Session.SessionManagement;
//import com.google.android.material.tabs.TabLayout;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class AddressDetailActivity extends BaseActivity {
//    TabLayout tabLayoutkm;
//    ViewPager viewPagerkm;
//    Toolbar toolbarkM;
//    LinearLayout linearoutfooter1,linearoutfooter2,linearoutfooter4,linearoutfooter5;
//    int page;
//    String ShareLeftLink,ShareRightLink;
//    SessionManagement sessionManagement;
//    HashMap<String,String> user;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_address_detail);
//
//        toolbarkM=findViewById(R.id.toolbar_addresdetail);
//        if (!isConnection(AddressDetailActivity.this))buildDialog(AddressDetailActivity.this).show();
//        else {
//        }
//        setSupportActionBar(toolbarkM);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("Address Details");
//        toolbarkM.setTitleTextColor(getResources().getColor(R.color.colorWhite));
//        viewPagerkm=(ViewPager)findViewById(R.id.viewPageraddresdetail);
//        setupViewPager(viewPagerkm);
//        sessionManagement=new SessionManagement(this.getApplicationContext());
//        user=sessionManagement.getUserDetails();
//
//        linearoutfooter1=findViewById(R.id.linearoutfooter1);
//        linearoutfooter2=findViewById(R.id.linearoutfooter2);
//        linearoutfooter4=findViewById(R.id.linearoutfooter4);
//        linearoutfooter5=findViewById(R.id.linearoutfooter5);
//
//        linearoutfooter1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), ProfileMangerActivity.class);
//                startActivity(intent);
//            }
//        });
//        linearoutfooter2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sharinglink();
//            }
//        });
//        linearoutfooter4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent=new Intent(getApplicationContext(), NetworkManagerActivity.class);
////                startActivity(intent);
//                Intent ds = new Intent(AddressDetailActivity.this, NetworkManagerActivity.class);
//                ds.putExtra("nm", 1);
//                startActivity(ds);
//            }
//        });
//        linearoutfooter5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), FinancialReportActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        tabLayoutkm= findViewById(R.id.tabLayoutaddresdetail);
//        tabLayoutkm.setTabTextColors(
//                getResources().getColor(R.color.colorWhite),
//                getResources().getColor(R.color.colorWhite)
//        );
//        tabLayoutkm.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorWhite));
//        tabLayoutkm.setupWithViewPager(viewPagerkm);
//    }
//
////    private void sharinglink() {
////        AlertDialog.Builder builder=new AlertDialog.Builder(AddressDetailActivity.this);
////        builder.setMessage("Share Joinig Link");
////        builder.setPositiveButton("Left", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
////                sharingIntent.setType("text/plain");
////                String shareBody = ShareLeftLink;
////                String shareSub = "Your subject here";
////                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
////                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
////                startActivity(Intent.createChooser(sharingIntent, "Share using"));
////            }
////        });
////        builder.setNegativeButton("Right", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
////                sharingIntent.setType("text/plain");
////                String shareBody =ShareRightLink;
////                String shareSub = "Your subject here";
////                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
////                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
////                startActivity(Intent.createChooser(sharingIntent, "Share using"));
////            }
////        });
////        AlertDialog alertDialog = builder.create();
////        alertDialog.show();
////    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        AddressDetailActivity.ViewPagerAdapter adapter = new AddressDetailActivity.ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFrag(new AddressDetailFragment(), "");
//        //adapter.addFrag(new ListPinFragment(), "List Pin");
//        //adapter.addFrag(new MyStructureFragment(), "My Structure");
//
//
//
//
//        viewPager.setAdapter(adapter);
//        page=getIntent().getIntExtra("km",0);
//        viewPager.setCurrentItem(page);
//    }
//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFrag(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id=item.getItemId();
//        if (id==android.R.id.home){
//
//            Intent intent=new Intent(getApplicationContext(), DashBoard.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
//
//            //end the activity
////            this.finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent i = new Intent(AddressDetailActivity.this, DashBoard.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);
//        finish();
//
//        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
//    }
//
//    //Internet Connection check
//    public  boolean isConnection(Context context){
//        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netinfo=connectivityManager.getActiveNetworkInfo();
//
//        if (netinfo !=null&&netinfo.isConnectedOrConnecting()){
//            NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            NetworkInfo mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//            if ((mobile!=null && mobile.isConnectedOrConnecting()) || (wifi!=null && wifi.isConnectedOrConnecting() ))return true;
//            else return false;
//
//        }else
//            return false;
//
//
//    }
//    public AlertDialog.Builder buildDialog(Context context) {
//        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        builder.setTitle("No Internet Connection");
//        builder.setMessage("No Internet Connection. Press ok to Exit");
//
//        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                onStop();
//            }
//        });
//        builder.setPositiveButton("Setup", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
//                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(dialogIntent);
//            }
//        });
//        return builder;
//
//    }
//}