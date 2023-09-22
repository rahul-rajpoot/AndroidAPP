package com.alps.shisu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alps.shisu.Session.SessionManagement;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class BaseActivity extends AppCompatActivity{

//    private DefaultLoaderFragment defaultLoaderFragment;

    public static boolean internetConnected = false;
    SessionManagement sessionManagement;
    HashMap<String,String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        sessionManagement=new SessionManagement(this.getApplicationContext());
        user=sessionManagement.getUserDetails();

    }

    public void hideDefaultLoaderFragment() {
//        try {
////            if(!SaveAppSharedPrefrence.isFirtstTimeInstall(getBaseContext(), Securetkey)) {
//                if (defaultLoaderFragment != null) {
//                    defaultLoaderFragment.dismiss();
//                    defaultLoaderFragment = null;
//                }
////            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void showDefaultLoaderFragment() {
//        if (defaultLoaderFragment == null) {
//            defaultLoaderFragment = new DefaultLoaderFragment();
//            defaultLoaderFragment.show(getSupportFragmentManager().beginTransaction(), "DefaultLoaderFragment");
//        }else {
//            defaultLoaderFragment.show(getSupportFragmentManager().beginTransaction(), "DefaultLoaderFragment");
//        }
    }

    public void showErrorSnackBar(View view, String msg){
        Snackbar snackbar=Snackbar.make(view,msg,Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
        View snackbarView = snackbar.getView();
//        snackbarView.setBackgroundColor(getResources().getColor(R.color.tabselectedcolor));
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
        snackbar.show();
    }

    public void sharinglink() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = user.get(SessionManagement.KEY_SHARE_LEFT);
        String shareSub = "Your subject here";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }


//    public void sharinglink(String ShareLeftLink) {
//
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody = ShareLeftLink;
//                String shareSub = "Your subject here";
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share using"));
//
////        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
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
////                String shareBody = ShareRightLink;
////                String shareSub = "Your subject here";
////                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
////                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
////                startActivity(Intent.createChooser(sharingIntent, "Share using"));
////            }
////        });
////        AlertDialog alertDialog = builder.create();
////        alertDialog.show();
//
//    }

    //Internet Connection check
    public boolean isConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;

        } else
            return false;

    }

    public AlertDialog.Builder buildDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or Wifi to access this. Press ok to Exit");

        builder.setPositiveButton("OK", (dialogInterface, i) -> finish());
        return builder;
    }

}