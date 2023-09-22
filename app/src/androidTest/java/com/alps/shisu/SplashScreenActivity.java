//package com.alps.aplusmart;
//
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.graphics.PixelFormat;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.AlertDialog;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.alps.aplusmart.Session.SessionManagement;
//
//import org.json.JSONObject;
//
//public class SplashScreenActivity extends BaseActivity{
//
//    private static int currentPage = 0;
//    private ViewPager viewPager;
//    private ImageAdapter myViewPagerAdapter;
//    private LinearLayout dotsLayout;
//    Button nextbtton;
//    private TextView[] dots;
//    private int[] layouts;
//
//    String currentVersion, latestVersion;
//    Dialog dialog;
//
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        Window window = getWindow();
//        window.setFormat(PixelFormat.RGBA_8888);
//    }
//
//    ImageView imageView;
//
//    // Session Manager Class
//    SessionManagement session;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//
//        getCurrentVersion();
//
//        imageView = (ImageView) findViewById(R.id.splashscreenimg);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.myanimation);
//        imageView.startAnimation(animation);
//        if (session.isFirtstTimeInstall(this)) {
//            //The following code will execute after the 5 seconds.
//            try {
//
//                session.setFirstTime(SplashScreenActivity.this);
//
//            } catch (Exception ignored) {
//                ignored.printStackTrace();
//            }
//        }  else {
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
//
//
//            // Making notification bar transparent
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//        setContentView(R.layout.activity_splash_screen);
//
//        nextbtton = findViewById(R.id.nextbtton);
//        viewPager = (ViewPager) findViewById(R.id.view_pager);
//        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
//
//        // add few more layouts if you want
//        layouts = new int[]{
//                R.layout.welcome_slide1,
//                R.layout.welcome_slide2,};
//
//        // adding bottom dots
//        addBottomDots(0);
//
//        // making notification bar transparent
//        changeStatusBarColor();
//
//        myViewPagerAdapter = new ImageAdapter();
//        viewPager.setAdapter(myViewPagerAdapter);
//        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//
//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == layouts.length) {
//                    currentPage = 0;
//                }
//                if (viewPager.getCurrentItem() < 1) {
//                    viewPager.setCurrentItem(currentPage = viewPager.getCurrentItem() , true);
//                }
//            }
//        };
//    }
//
//    private void getCurrentVersion() {
//        PackageManager pm = this.getPackageManager();
//        PackageInfo pInfo = null;
//
//        try {
//            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
//
//        } catch (PackageManager.NameNotFoundException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        currentVersion = pInfo.versionName;
//
//        new GetLatestVersion().execute();
//    }
//    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {
//
//        private ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected JSONObject doInBackground(String... params) {
//            try {
//                //It retrieves the latest version by scraping the content of current version from play store at runtime
//                // Document doc = Jsoup.connect("patanjaliiasclasses").get();
//                // latestVersion = doc.getElementsByClass("htlgb").get(5).text();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//
//            return new JSONObject();
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            if (latestVersion != null) {
//                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
//                    if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
//                        showUpdateDialog();
//                    }
//                }
//            } else
//                //background.start();
//                super.onPostExecute(jsonObject);
//        }
//    }
//
//    private void showUpdateDialog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("A New Update is Available");
//        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
//                        ("market://details?id=aadnyabullion")));
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //background.start();
//            }
//        });
//
//        builder.setCancelable(false);
//        dialog = builder.show();
//    }
//
//    private void addBottomDots ( int currentPage){
//            dots = new TextView[layouts.length];
//
//            int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
//            int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
//
//            dotsLayout.removeAllViews();
//            for (int i = 0; i < dots.length; i++) {
//                dots[i] = new TextView(this);
//                dots[i].setText(Html.fromHtml("&#8226;"));
//                dots[i].setTextSize(35);
//                dots[i].setTextColor(colorsInactive[currentPage]);
//                dotsLayout.addView(dots[i]);
//            }
//
//            if (dots.length > 0)
//                dots[currentPage].setTextColor(colorsActive[currentPage]);
//        }
//
//        private int getItem ( int i){
//            return viewPager.getCurrentItem() + i;
//        }
//
//        //  viewpager change listener
//        ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                addBottomDots(position);
//                if (position == 1) {
//                    nextbtton.setVisibility(View.VISIBLE);
//                    nextbtton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//
//                } else {
//                    nextbtton.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//
//            }
//        };
//
//        /**
//         * Making notification bar transparent
//         */
//        private void changeStatusBarColor () {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                Window window = getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(Color.TRANSPARENT);
//            }
//        }
//
//        public class ImageAdapter extends PagerAdapter {
//            private LayoutInflater layoutInflater;
//
//            public ImageAdapter() {
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                View view = layoutInflater.inflate(layouts[position], container, false);
//                container.addView(view);
//
//                return view;
//            }
//
//            @Override
//            public int getCount() {
//                return layouts.length;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object obj) {
//                return view == obj;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                View view = (View) object;
//                container.removeView(view);
//            }
//        }
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
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        finish();
//    }
//    @Override
//    public void onBackPressed() {
//        finish();
//        super.onBackPressed();
//    }
//}
