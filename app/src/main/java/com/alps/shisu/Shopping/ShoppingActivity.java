package com.alps.shisu.Shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.BaseActivity;
import com.alps.shisu.DashBoard;
import com.alps.shisu.FinanacialReport.FinancialReportActivity;
import com.alps.shisu.NetworkManager.NetworkManagerActivity;
import com.alps.shisu.PaymentConfirmActivity;
import com.alps.shisu.ProfileManager.ProfileMangerActivity;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.Shopping.orders.OrdersListFragment;
import com.alps.shisu.Shopping.shopnow.BillingDetailsFragment;
import com.alps.shisu.Shopping.orders.DeliveryReportNewFragment;
import com.alps.shisu.Shopping.shopnow.HolderFragment;
import com.alps.shisu.Shopping.shopnow.MakePaymentFragment;
import com.alps.shisu.Shopping.shopnow.ProductDetailsFragment;
import com.alps.shisu.Shopping.shopnow.ProductListFragment;
import com.alps.shisu.Shopping.shopnow.ShopNowFragment;
import com.alps.shisu.db.local.RoomDBRepository;
import com.alps.shisu.db.local.entity.ProductsDetails;
import com.alps.shisu.modelclass.StateDetails;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ShoppingActivity extends BaseActivity {

    TabLayout tabLayoutsp;
    ViewPager viewPagersp;
    Toolbar toolbarsp;
    LinearLayout linearoutsopngfooter1, linearoutsopngfooter2, linearoutsopngfooter4, linearoutsopngfooter5;
    int page;
    String ShareLeftLink, ShareRightLink;
    SessionManagement sessionManagement;
    HashMap<String, String> user;
    private String urls = "";
    private Config ut;
    private String showCart;
    private RoomDBRepository postRoomDBRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        postRoomDBRepository = new RoomDBRepository(getApplication());

        toolbarsp = findViewById(R.id.toolbar_shopping);
        if (!isConnection(ShoppingActivity.this)) buildDialog(ShoppingActivity.this).show();
        setSupportActionBar(toolbarsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Shop");
        toolbarsp.setTitleTextColor(Color.WHITE);

        toolbarsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                performBack();
            }
        });

        if(getIntent() != null){
            showCart = getIntent().getStringExtra("ShowCart");
        }

        viewPagersp = (ViewPager) findViewById(R.id.viewPagershopping);
        setupViewPager(viewPagersp);
        sessionManagement = new SessionManagement(this.getApplicationContext());
        user = sessionManagement.getUserDetails();

        linearoutsopngfooter1 = findViewById(R.id.linearoutsopngfooter1);
        linearoutsopngfooter2 = findViewById(R.id.linearoutdashfooter2);
        linearoutsopngfooter4 = findViewById(R.id.linearoutsopngfooter4);
        linearoutsopngfooter5 = findViewById(R.id.linearoutsopngfooter5);

        linearoutsopngfooter1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileMangerActivity.class);
            startActivity(intent);
        });
        linearoutsopngfooter2.setOnClickListener(v -> sharinglink());
        linearoutsopngfooter4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), NetworkManagerActivity.class);
            startActivity(intent);
        });
        linearoutsopngfooter5.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), FinancialReportActivity.class);
            startActivity(intent);
        });

        tabLayoutsp = findViewById(R.id.tabLayoutshopping);
        tabLayoutsp.setTabTextColors(
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.white)
        );
        tabLayoutsp.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        tabLayoutsp.setupWithViewPager(viewPagersp);

    }

//    private void sharinglink() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingActivity.this);
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
//                String shareBody = ShareRightLink;
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
//      adapter.addFrag(new ActivationOrderFragment(), "Order Now");
        adapter.addFrag(new HolderFragment(showCart), "Shop Now");
//      adapter.addFrag(new RepurchaseOrderFragment(), "Repurchase Order");
        adapter.addFrag(new OrdersListFragment(), "Orders");
        adapter.addFrag(new DeliveryReportNewFragment(), "Delivery Status");


        viewPager.setAdapter(adapter);
        page = getIntent().getIntExtra("s", 0);
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
        int id = item.getItemId();
        if (id == android.R.id.home) {

            Intent intent = new Intent(getApplicationContext(), DashBoard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //overridePendingTransition(R.anim.side_in_left,R.anim.slide_out_right);

            //end the activity
//            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.side_in_left,R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        performBack();

    }

    private void performBack(){

        try {
            FragmentManager manager = getSupportFragmentManager();
            int count = manager.getBackStackEntryCount();
            Log.e(TAG, "onBackPressed: count : " + count);
            if (count > 1) {
                manager.popBackStackImmediate();
            } else {
                Intent i = new Intent(ShoppingActivity.this, DashBoard.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

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

    private void showToolbarText(String title) {

//        if(getSupportActionBar() != null){
//            getSupportActionBar().setTitle(title);
//        }

//        if (toolbarTitleList != null && !title.isEmpty() && !toolbarTitleList.contains(title)) {
//            toolbarTitleList.add(title);
//        }
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        _menu_icon_ll = toolbar.findViewById(R.id.menu_icon_ll);
//        _appicon_ll = toolbar.findViewById(R.id.appicon_ll);
//        LinearLayout logoutIcon = toolbar.findViewById(R.id.logoutIcon);
//        _toolbar_title_ll = toolbar.findViewById(R.id.toolbar_title_ll);
//        _toolbar_title = toolbar.findViewById(R.id.toolbar_title);
//        if (title != null && !title.isEmpty()) {
//            _toolbar_title_ll.setVisibility(View.VISIBLE);
//            _appicon_ll.setVisibility(View.GONE);
//            _toolbar_title.setText(title);
//        } else {
//            _toolbar_title_ll.setVisibility(View.GONE);
//            _appicon_ll.setVisibility(View.VISIBLE);
////            _toolbar_title.setText(title);
//        }
//
//        _menu_icon_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawer.open();
//            }
//        });
//
//        logoutIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logout();
//            }
//        });

    }

    public void showProductCategories(String title, String itemId) {
        showToolbarText(title);
        ShopNowFragment shopNowFragment = ShopNowFragment.newInstance("", "");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, shopNowFragment, "ShopNowFragment");
        ft.addToBackStack(null);
        Log.e(TAG, "showProductCategories: frag count : " + getSupportFragmentManager().getBackStackEntryCount());
        ft.commit();
    }

    public void showProductList(String title, String itemId) {
        showToolbarText(title);
        ProductListFragment productListFragment = ProductListFragment.newInstance(itemId, "");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, productListFragment, "ProductListFragment");
        ft.addToBackStack(null);
        Log.e(TAG, "showProductList: frag count : " + getSupportFragmentManager().getBackStackEntryCount());
        ft.commit();
    }

    public void showProductDetails(String title, String pId) {
        showToolbarText(title);
        ProductDetailsFragment productDetailsFragment = ProductDetailsFragment.newInstance(pId, "");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, productDetailsFragment, "ProductDetailsFragment");
        ft.addToBackStack(null);
        Log.e(TAG, "showProductDetails: frag count : " + getSupportFragmentManager().getBackStackEntryCount());
        ft.commit();
    }

    //    public void showBillingDetails(String title, List<ProductsDetails> productsDetailsList) {
    public void showBillingDetails(String title) {
        showToolbarText(title);
//        BillingDetailsFragment billingDetailsFragment = new BillingDetailsFragment(productsDetailsList);
        BillingDetailsFragment billingDetailsFragment = new BillingDetailsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, billingDetailsFragment, "BillingDetailsFragment");
        ft.addToBackStack(null);
        Log.e(TAG, "showBillingDetails: frag count : " + getSupportFragmentManager().getBackStackEntryCount());
        ft.commit();
    }

    public void showMakePaymentPage(String title, StateDetails finalStateDetails, String cityName, String mobileNo, String address, List<ProductsDetails> productsDetailsList, float newWeight, float newMRP, int quantity) {
        showToolbarText(title);
        MakePaymentFragment makePaymentFragment = MakePaymentFragment.newInstance(finalStateDetails, cityName, mobileNo, address, productsDetailsList, newWeight, newMRP, quantity);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, makePaymentFragment, "MakePaymentFragment");
        ft.addToBackStack(null);
        ft.commit();
    }

//    public void saveOrder(final StateDetails stateDetails, final String mobileNo, final String address, final List<ProductsDetails> productsDetailsList, String paymentMode, String bankName, String refrenceNo, String paymentDate, String remark, String newWight, String newMrp, String quantity) {
//
////      https://api.goldways.in/DeveloperApi/SaveOrder/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/{username}/Object
//
//        SharedPreferences sharedPreferences = getSharedPreferences("ALPSPref", MODE_PRIVATE);
//        String userName = sharedPreferences.getString("uid", "");
//        String email = sharedPreferences.getString("email", "");
//        String fName = sharedPreferences.getString("firstname", "");
//        String lName = sharedPreferences.getString("lasname", "");
//        String name = fName + " " + lName;
//
//
//        JSONArray jsonArray = new JSONArray();
//        try {
//
//            for (int i = 0; i < productsDetailsList.size(); i++) {
//
//
//                ProductsDetails productsDetails = productsDetailsList.get(i);
//
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.put("LoginID", userName);
//                jsonObject.put("Name", name);
//                jsonObject.put("emailid", email);
//                jsonObject.put("MobileNo", mobileNo);
//                jsonObject.put("SID", stateDetails.getsID());
//                jsonObject.put("DISTID", stateDetails.getDistID());
//                jsonObject.put("CTID", stateDetails.getcTID());
//                jsonObject.put("Pincode", stateDetails.getPinCode());
//                jsonObject.put("Address", address);
//                jsonObject.put("CID", "96");
//                jsonObject.put("oState", stateDetails.getStateName());
//                jsonObject.put("oCity", stateDetails.getCityName());
////            jsonObject.put("Paymode", paymentMode);
//                jsonObject.put("Paymode", paymentMode);
//                jsonObject.put("TotalAmount", newMrp);
//                jsonObject.put("V_Amount", "0");
//                jsonObject.put("BankName", bankName);
//                jsonObject.put("RefNo", refrenceNo);
//                jsonObject.put("PID", productsDetails.getProduct_id());
//                jsonObject.put("MRP", newMrp);
//                jsonObject.put("DP", newMrp);
//                jsonObject.put("BV", productsDetails.getBv());
//                jsonObject.put("Quantity",quantity );
//                jsonObject.put("StoreID", "2");
//
//                jsonArray.put(jsonObject);
//            }
//
//            Log.e(TAG, "saveOrder: " + jsonArray);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
////        [
////        {
////            "LoginID": "startid",
////                "Name": "Anshul kumar",
////                "emailid": "kumar.11@tt.cc",
////                "MobileNo": "35354353",
////                "CID": "96",
////                "SID": "7",
////                "oState": "Delhi",
////                "DISTID": "0",
////                "CTID": "0",
////                "oCity": "CityName",
////                "Pincode": "110011",
////                "Address": "Address",
////                "Paymode": "Bank Deposit",
////                "TotalAmount": "160.0",
////                "V_Amount": "0",
////                "BankName": "BANK OF BARODA",
////                "RefNo": "vbbv",
////                "PID": "1",
////                "MRP": "80.00",
////                "DP": "80.00",
////                "BV": "5.00",
////                "Quantity": "2",
////                "StoreID":"2"
////        }
////]
//
//        final String mRequestBody = jsonArray.toString();
//
//        urls = Config.url + "SaveOrder/" + Config.merchantid + "/" + Config.securtykey + "/" + userName + "/Object";
//        Log.e(TAG, "onCreateView: " + urls);
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls,
//                response -> {
//                    try {
//                        JSONObject object = new JSONObject(response);
//                        Log.e(TAG, "onResponse: " + object);
//                        String status = object.getString("Status");
//                        String message = object.getString("Message");
//
//                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
//                        if(status.equalsIgnoreCase("1")) {
//                            if (!paymentMode.isEmpty() && paymentMode.equalsIgnoreCase("OnlinePayment")) {
//                                String orderId = object.getString("Mpoid");
//                                Intent intent = new Intent(ShoppingActivity.this, PaymentConfirmActivity.class);
//                                intent.putExtra("OrderNo", orderId);
//                                startActivity(intent);
//                            } else {
//                                postRoomDBRepository.deleteAllProductDetails();
//                                removeAllFragments();
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }   /*if (dialog.isShowing())
//                        dialog.dismiss();*/
//                }, error -> {
//
//                }) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                    uee.printStackTrace();
//                    return null;
//                }
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
//            }
////            protected Map<String,String> getParams() {
////                Map<String, String> params = new HashMap<>();
////                return params;
////            }
//        };
//        requestQueue.add(stringRequest);
//    }

     public void saveOrder(final StateDetails stateDetails, final String mobileNo, final String address, final List<ProductsDetails> productsDetailsList, String paymentMode, String bankName, String refrenceNo, String paymentDate, String remark, String newWight, String newMrp, String quantity) {

//      https://api.goldways.in/DeveloperApi/SaveOrder/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/{username}/Object

        SharedPreferences sharedPreferences = getSharedPreferences("ALPSPref", MODE_PRIVATE);
        String userName = sharedPreferences.getString("uid", "");
        String email = sharedPreferences.getString("email", "");
        String fName = sharedPreferences.getString("firstname", "");
        String lName = sharedPreferences.getString("lasname", "");
        String name = fName + " " + lName;


        JSONArray jsonArray = new JSONArray();
        try {

            for (int i = 0; i < productsDetailsList.size(); i++) {


                ProductsDetails productsDetails = productsDetailsList.get(i);

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("LoginID", userName);
                jsonObject.put("Name", name);
                jsonObject.put("emailid", email);
                jsonObject.put("MobileNo", mobileNo);
                jsonObject.put("SID", stateDetails.getsID());
                jsonObject.put("DISTID", stateDetails.getDistID());
                jsonObject.put("CTID", stateDetails.getcTID());
                jsonObject.put("Pincode", stateDetails.getPinCode());
                jsonObject.put("Address", address);
                jsonObject.put("CID", "96");
                jsonObject.put("oState", stateDetails.getStateName());
                jsonObject.put("oCity", stateDetails.getCityName());
//              jsonObject.put("Paymode", paymentMode);
                jsonObject.put("Paymode", paymentMode);
//                jsonObject.put("TotalAmount", newMrp);
                jsonObject.put("TotalAmount", newMrp);
                jsonObject.put("V_Amount", "0");
                jsonObject.put("BankName", bankName);
                jsonObject.put("RefNo", refrenceNo);
                jsonObject.put("PID", productsDetails.getProduct_id());
//                jsonObject.put("MRP", newMrp);
//                jsonObject.put("DP", newMrp);
                jsonObject.put("MRP", productsDetails.getMrp());
                jsonObject.put("DP", productsDetails.getDp());
//                jsonObject.put("MRP", Float.parseFloat(productsDetails.getQuantity())* Float.parseFloat(productsDetails.getMrp()));
//                jsonObject.put("DP", Float.parseFloat(productsDetails.getQuantity())* Float.parseFloat(productsDetails.getMrp()));
                jsonObject.put("BV", productsDetails.getBv());
                jsonObject.put("Quantity", productsDetails.getQuantity());
                jsonObject.put("StoreID", "2");

                jsonArray.put(jsonObject);
            }

            Log.e(TAG, "saveOrder: " + jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        [
//        {
//            "LoginID": "startid",
//                "Name": "Anshul kumar",
//                "emailid": "kumar.11@tt.cc",
//                "MobileNo": "35354353",
//                "CID": "96",
//                "SID": "7",
//                "oState": "Delhi",
//                "DISTID": "0",
//                "CTID": "0",
//                "oCity": "CityName",
//                "Pincode": "110011",
//                "Address": "Address",
//                "Paymode": "Bank Deposit",
//                "TotalAmount": "160.0",
//                "V_Amount": "0",
//                "BankName": "BANK OF BARODA",
//                "RefNo": "vbbv",
//                "PID": "1",
//                "MRP": "80.00",
//                "DP": "80.00",
//                "BV": "5.00",
//                "Quantity": "2",
//                "StoreID":"2"
//        }
//]

        final String mRequestBody = jsonArray.toString();

//      urls = ut.url + "SaveOrder/" + ut.merchantid + "/" + ut.securtykey + "/" + userName + "/Object";
        urls = ut.url + "SaveOrder/" + ut.merchantid  + ut.securtykey + "/" + userName + "/Object";
        Log.e(TAG, "onCreateView: " + urls);

        RequestQueue requestQueue = new Volley().newRequestQueue(getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Log.e(TAG, "onResponse: " + object);
                            String status = object.getString("Status");
                            String message = object.getString("Message");

                             Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                            if (status.equalsIgnoreCase("1")) {
                                if (!paymentMode.isEmpty() && paymentMode.equalsIgnoreCase("OnlinePayment")) {
                                    String orderId = object.getString("Mpoid");
                                    Intent intent =  new Intent(ShoppingActivity.this, PaymentConfirmActivity.class);
                                    intent.putExtra("OrderNo", orderId);
                                    startActivity(intent);
                                } else {
                                    removeAllFragments();
                                }
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }   /*if (dialog.isShowing())
                            dialog.dismiss();*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    uee.printStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
//            protected Map<String,String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                return params;
//            }
        };
        requestQueue.add(stringRequest);
    }

    public void clearBackStack() {
//        FragmentManager fm = getSupportFragmentManager();
//        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    //    public void removeAllFragments(FragmentManager fragmentManager) {
    public void removeAllFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
        }
    }

//    public void saveProductItemInCart(List<ProductsDetails> productsDetailsList) {
//
//        SharedPreferences sharedPreferences = getSharedPreferences("ALPSPref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String productItemFinal = "";
//
//        for (ProductsDetails productsDetails :
//                productsDetailsList) {
//
//            String productItem = productsDetails.getProduct_name() + "#" + productsDetails.getMrp() + "#" + productsDetails.getProduct_code()
//                    + "#" + productsDetails.getdP() + "#" + productsDetails.getmRP() + "#" + productsDetails.getpCode()
//                    + "#" + productsDetails.getpName() + "#" + productsDetails.getbV() + "#" + productsDetails.getpV()
//                    + "#" + productsDetails.getQuantity() + "#" + productsDetails.getDesc() + "#" + productsDetails.getSize()
//                    + "#" + productsDetails.getColor() + "#" + productsDetails.getExtraMargin() + "#" + productsDetails.getOtherPaymentMode()
//                    + "#" + productsDetails.getDefaultImg() + "#" + productsDetails.getpWeight();
//
//            productItemFinal = productItemFinal + "@" + productItem;
//        }
//        editor.putString("CartValue", productItemFinal);
//        editor.commit();
//
//    }

}

