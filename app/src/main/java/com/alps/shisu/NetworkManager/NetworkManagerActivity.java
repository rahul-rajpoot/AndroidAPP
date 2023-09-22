package com.alps.shisu.NetworkManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.alps.shisu.BaseActivity;
import com.alps.shisu.DashBoard;
import com.alps.shisu.FinanacialReport.FinancialReportActivity;
import com.alps.shisu.ProfileManager.ProfileMangerActivity;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkManagerActivity extends BaseActivity {
    TabLayout tabLayoutnm;
    ViewPager viewPagernm;
    Toolbar toolbarNM;
    LinearLayout linearoutnetwkfooter1,linearoutnetwkfooter2,linearoutnetwkfooter4,linearoutnetwkfooter5;
    int page;
    String ShareLeftLink,ShareRightLink;
    SessionManagement sessionManagement;
    HashMap<String,String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_manager);
        toolbarNM=findViewById(R.id.toolbar_networkmanager);
        if (!isConnection(NetworkManagerActivity.this))buildDialog(NetworkManagerActivity.this).show();
        setSupportActionBar(toolbarNM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Network");
        toolbarNM.setTitleTextColor(getResources().getColor(R.color.white));
        viewPagernm=(ViewPager)findViewById(R.id.viewPagernetworkmanager);
        setupViewPager(viewPagernm);
        sessionManagement=new SessionManagement(this.getApplicationContext());
        user=sessionManagement.getUserDetails();

        linearoutnetwkfooter1=findViewById(R.id.linearoutnetwkfooter1);
        linearoutnetwkfooter2=findViewById(R.id.linearoutdashfooter2);
        linearoutnetwkfooter4=findViewById(R.id.linearoutnetwkfooter4);
        linearoutnetwkfooter5=findViewById(R.id.linearoutnetwkfooter5);

        linearoutnetwkfooter1.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(), ProfileMangerActivity.class);
            startActivity(intent);
        });
        linearoutnetwkfooter2.setOnClickListener(v -> sharinglink());
        linearoutnetwkfooter4.setOnClickListener(v -> {
//                Intent intent=new Intent(getApplicationContext(), NetworkManagerActivity.class);
//                startActivity(intent);
            Intent ds = new Intent(NetworkManagerActivity.this, NetworkManagerActivity.class);
            ds.putExtra("nm", 1);
            startActivity(ds);
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        });
        linearoutnetwkfooter5.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(), FinancialReportActivity.class);
            startActivity(intent);
        });

        tabLayoutnm=(TabLayout)findViewById(R.id.tabLayoutnetworkmanager);
        tabLayoutnm.setTabTextColors(
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.white)
        );
        tabLayoutnm.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        tabLayoutnm.setupWithViewPager(viewPagernm);
    }

//    private void sharinglink() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(NetworkManagerActivity.this);
//        builder.setMessage("Share Joinig Link");
//        builder.setPositiveButton("Left", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody = ShareLeftLink;
//                String shareSub = "Your subject here";
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share using"));
//            }
//        });
//        builder.setNegativeButton("Right", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody =ShareRightLink;
//                String shareSub = "Your subject here";
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share using"));
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RegisterNowFragment(), "REGISTER NEW");
//        adapter.addFrag(new MatchingTreeFragment(), "Level Details");
//        adapter.addFrag(new LevelDetailsFragment(), "Level Details");
        adapter.addFrag(new DirectSponsorFragment(), "Direct Referrals");
//        adapter.addFrag(new TeamListFragment(), "My Team");
        //adapter.addFrag(new TeamBusinessFragment(), "Team Business");
        // adapter.addFrag(new TeamStatusFragment(), "Team Status");



        viewPager.setAdapter(adapter);
        page=getIntent().getIntExtra("nm",0);
        viewPager.setCurrentItem(page);
    }
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){

            Intent intent=new Intent(getApplicationContext(),DashBoard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

            //end the activity
//            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(NetworkManagerActivity.this, DashBoard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    //Internet Connection check
    public  boolean isConnection(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo=connectivityManager.getActiveNetworkInfo();

        if (netinfo !=null&&netinfo.isConnectedOrConnecting()){
            NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile!=null && mobile.isConnectedOrConnecting()) || (wifi!=null && wifi.isConnectedOrConnecting() ))return true;
            else return false;

        }else
            return false;


    }
    public AlertDialog.Builder buildDialog(Context context) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("No Internet Connection. Press ok to Exit");

        builder.setNegativeButton("OK", (dialogInterface, i) -> onStop());
        builder.setPositiveButton("Setup", (dialog, which) -> {

            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        });
        return builder;
    }


}