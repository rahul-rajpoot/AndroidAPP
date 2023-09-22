package com.alps.shisu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;

public class LoginActivity extends BaseActivity {
    private EditText username;
    private EditText userpassword;
    private ImageView show_pass_btn,imageView, usernameImage;
    TextView newregisterid;
    // private ImageButton userloginbtn;

    SwipeRefreshLayout swipeRefreshLayout;
    private TextView forgatepassword;
    String urls="";
    String profileUrls="";
    Config ut;
    ProgressBar progressBar;
    RelativeLayout loginpage;
    LinearLayout logincardview,logocomes;
    private long backPressedTime;
    // Session Manager Class
    SessionManagement session;
    android.app.AlertDialog dialog;
    private ColorStateList blueColorStateList;
    private ColorStateList grayColorStateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //this is checking Interner Access or not
        if (!isConnection(LoginActivity.this))buildDialog(LoginActivity.this).show();
        // Session Manager
        session = new SessionManagement(getApplicationContext());
        //  session.checkLogin();

        int blueColor = getResources().getColor(R.color.blue_color);
        int grayColor = getResources().getColor(R.color.gray_color);

        grayColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[] {
                        grayColor,
                        grayColor,
                        grayColor,
                        grayColor,
                        grayColor
                }
        );

        blueColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[] {
                        blueColor,
                        blueColor,
                        blueColor,
                        blueColor,
                        blueColor
                }
        );

        dialog=new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        if(session.isLoggedIn())
        {
            Intent intent = new Intent(getApplicationContext(), DashBoard.class);
            startActivity(intent);
            finish();
            return;
        }
        logocomes= findViewById(R.id.logocome);
        logincardview= findViewById(R.id.cardlogin);
        progressBar= findViewById(R.id.loginprogress);
        username= findViewById(R.id.userloginusername);
        usernameImage= findViewById(R.id.username);
        userpassword= findViewById(R.id.userloginpassword);
        Button userloginbtn = findViewById(R.id.userloginbutton);
        loginpage= findViewById(R.id.loginpage);
        show_pass_btn= findViewById(R.id.show_pass_btn);

        newregisterid= findViewById(R.id.newregisterid);

        username.setOnTouchListener((v, event) -> {
            makeEditTextEnabled(username, usernameImage);
            makeEditTextDesabled(userpassword, show_pass_btn);
            return false;
        });

        userpassword.setOnTouchListener((v, event) -> {
            makeEditTextEnabled(userpassword, show_pass_btn);
            makeEditTextDesabled(username, usernameImage);
            return false;
        });

        newregisterid.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), NewRegistrationActivity.class);
            startActivity(intent);
        });

        show_pass_btn.setOnClickListener(this::ShowHidePass);

        userloginbtn.setOnClickListener(view -> {
                String uname=username.getText().toString();
                String pass=userpassword.getText().toString();
            if (username.getText().toString().length() == 0){
                Snackbar snackbar=Snackbar.make(loginpage,"Plaese Enter Valid Username",Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
             //   Toast.makeText(getApplicationContext(),"Plaese Enter Valid Email id and Password", Toast.LENGTH_SHORT).show();
            }
            else if (userpassword.getText().toString().length()==0){
                Snackbar snackbar=Snackbar.make(loginpage,"Plaese Enter Valid Password",Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
            }
            else {
                urls= Config.url +"UserAuthentication/"+ Config.merchantid +"/"+ Config.securtykey +"/"+uname+"/"+pass;
                Log.e(TAG, "onClick: "+ urls );
                loginuser(username.getText().toString(), userpassword.getText().toString());
            }
        });
    }

    private void makeEditTextEnabled(EditText editText, ImageView imageView){

        editText.setBackground(getResources().getDrawable(R.drawable.login_edit_text_bg));
        editText.setHintTextColor(getResources().getColor(R.color.blue_color));
        imageView.setImageTintList(blueColorStateList);

    }

    private void makeEditTextDesabled(EditText editText, ImageView imageView){

        editText.setBackground(getResources().getDrawable(R.drawable.login_edit_text_default_bg));
        editText.setHintTextColor(getResources().getColor(R.color.gray_color));
        imageView.setImageTintList(grayColorStateList);

    }


    public void ShowHidePass(View v) {
        if(v.getId()==R.id.show_pass_btn){

            if(userpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(v)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Show Password
                userpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(v)).setImageResource(R.drawable.ic_visibility_off_black_24dp);

                //Hide Password
                userpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void loginuser(final String usname, final String userspass) {

        //dialog.show();
        showDefaultLoaderFragment();
        RequestQueue requestQueue= new Volley().newRequestQueue(getApplicationContext());
        StringRequest stringRequest= new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray object = new JSONArray(response);
                            //    for (int i=0;i<object.length();i++)
                            JSONObject jsonObject = object.getJSONObject(0);

                            if (jsonObject.getBoolean("Status")){
                                profileUrls= ut.url+"getProfile/"+ut.merchantid+"/"+ut.securtykey+"/"+usname+"/"+userspass;
                                Log.e("TAG", "onClick: "+ profileUrls );
                                getProfile(usname, userspass);
                            }
                            else {
                                hideDefaultLoaderFragment();
                                showErrorSnackBar(loginpage,"Please Enter Correct Username & Password");
                            }
                        } catch (JSONException e) {
                            hideDefaultLoaderFragment();
                            showErrorSnackBar(loginpage, "Something went wrong");
                            e.printStackTrace();
                        }   /*if (dialog.isShowing())
                            dialog.dismiss();*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Snackbar snackbar = Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
//                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                View snackbarView = snackbar.getView();
//                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                snackbar.show();
                hideDefaultLoaderFragment();
                showErrorSnackBar(loginpage, "Something went wrong");
                //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
               /* if (dialog.isShowing())
                    dialog.dismiss();*/
            }
        })
        {
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username",usname);
                params.put("pass",userspass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getProfile(final String usname, final String userspass) {

        //dialog.show();
        showDefaultLoaderFragment();
        RequestQueue requestQueue= new Volley().newRequestQueue(getApplicationContext());
        StringRequest stringRequest= new StringRequest(Request.Method.GET, profileUrls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object=new JSONArray(response);
                            //    for (int i=0;i<object.length();i++)
                            JSONObject jsonObject=object.getJSONObject(0);
                            //   Boolean b=object.getBoolean("error");

                            if (jsonObject.getBoolean("Status")){
                                //   String user=object.getJSONObject("user").getString("UserID");
                                String usename=jsonObject.getString("UserID");
                                String Firstname=jsonObject.getString("FirstName");
                                String lastname=jsonObject.getString("LastName");
                                String status=jsonObject.getString("Status");
                                String regdate=jsonObject.getString("Regdate");
                                String email=jsonObject.getString("EmailID");
                                String dateofjoining=jsonObject.getString("Regdate");
                                String currency=jsonObject.getString("CurrencySymbol");
                                String userEncrpted=jsonObject.getString("UPEncy");
                                String MobileNo=jsonObject.getString("MobileNo");
                                String Address=jsonObject.getString("Address");
                                String State=jsonObject.getString("State");
                                String SID=jsonObject.getString("SID");
                                String PinCode=jsonObject.getString("PinCode");
                                String DISTID=jsonObject.getString("DISTID");
                                String CID=jsonObject.getString("CID");
                                String CTID=jsonObject.getString("CTID");
                                String City=jsonObject.getString("City");
                                String District=jsonObject.getString("District");

                                SharedPreferences sharedPreferences=getSharedPreferences("ALPSPref",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("uid",usname);
                                editor.putString("upass",userspass);
                                editor.putString("firstname",Firstname);
                                editor.putString("lasname",lastname);
                                editor.putString("Status",status);
                                editor.putString("reg_date",regdate);
                                editor.putString("email",email);
                                editor.putString("dojss",dateofjoining);
                                editor.putString("currencySysmble",currency);
                                editor.putString("useridpassword",userEncrpted);
                                editor.putString("MobileNo",MobileNo);
                                editor.putString("Address",Address);
                                editor.putString("State",State);
                                editor.putString("SID",SID);
                                editor.putString("PinCode",PinCode);
                                editor.putString("DISTID",DISTID);
                                editor.putString("CID",CID);
                                editor.putString("CTID",CTID);
                                editor.putString("City",City);
                                editor.putString("District",District);
                                editor.commit();
                                session.createLoginSession(Firstname,lastname,email,regdate,status,usname,userspass,dateofjoining,
                                        currency,userEncrpted);
                                //Start NavigationDrawer
                                Intent go=new Intent(LoginActivity.this, DashBoard.class);
                                startActivity(go);
                                finish();
//                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                            }
                            else {
//                                Snackbar snackbar=Snackbar.make(loginpage,"Please Enter Correct Userid & Password",Snackbar.LENGTH_LONG);
//                                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                                View snackbarView = snackbar.getView();
//                                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                                snackbar.show();
                                hideDefaultLoaderFragment();
                                showErrorSnackBar(loginpage,"Please Enter Correct Username & Password");
                            }
                        } catch (JSONException e) {
                            hideDefaultLoaderFragment();
                            showErrorSnackBar(loginpage, "Something went wrong");
                            e.printStackTrace();
                        }   /*if (dialog.isShowing())
                            dialog.dismiss();*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Snackbar snackbar = Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
//                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                View snackbarView = snackbar.getView();
//                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                snackbar.show();
                hideDefaultLoaderFragment();
                showErrorSnackBar(loginpage, "Something went wrong");
                //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
               /* if (dialog.isShowing())
                    dialog.dismiss();*/
            }
        })
        {
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username",usname);
                params.put("pass",userspass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


//    private void loginuser(final String usname, final String userspass) {
//      //  userloginbtn.setVisibility(View.GONE);
//      //  progressBar.setVisibility(View.VISIBLE);
//        dialog.show();
//        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, urls,
//                response -> {
//                    try {
//                        JSONArray object = new JSONArray(response);
//                        //    for (int i=0;i<object.length();i++)
//                        JSONObject jsonObject = object.getJSONObject(0);
//                        //   Boolean b=object.getBoolean("error");
//
//                        if (jsonObject.getBoolean("Status")){
//
//
////                            SharedPreferences sharedPreferences=getSharedPreferences("ALPSPref",MODE_PRIVATE);
////                            SharedPreferences.Editor editor=sharedPreferences.edit();
////                            editor.putString("uid",usname);
////                            editor.putString("upass",userspass);
////                            editor.commit();
//
//                            //   String user=object.getJSONObject("user").getString("UserID");
//                            String usename=jsonObject.getString("UserID");
//                            String Firstname=jsonObject.getString("FirstName");
//                            String lastname=jsonObject.getString("LastName");
//                            String status=jsonObject.getString("Status");
//                            String regNo=jsonObject.getString("RegNo");
//                            Log.e(TAG, "onResponse: "+ regNo );
//                            String regdate=jsonObject.getString("Regdate");
//                            String email=jsonObject.getString("EmailID");
//                            String dateofjoining=jsonObject.getString("Regdate");
//                            String currency=jsonObject.getString("CurrencySymbol");
//                            String userEncrpted=jsonObject.getString("UPEncy");
//                            String confpayNo=jsonObject.getString("ConfpayNo");
//                            String AcBalance=jsonObject.getString("AcBalance");
//                            String MobileNo=jsonObject.getString("MobileNo");
//                            String Address=jsonObject.getString("Address");
//                            String State=jsonObject.getString("State");
//                            String SID=jsonObject.getString("SID");
//                            String PinCode=jsonObject.getString("PinCode");
//                            String DISTID=jsonObject.getString("DISTID");
////                                String Countrycode=jsonObject.getString("Countrycode");
//                            String CID=jsonObject.getString("CID");
//                            String CTID=jsonObject.getString("CTID");
//                            String City=jsonObject.getString("City");
//                            String District=jsonObject.getString("District");
//
//                            SharedPreferences sharedPreferences=getSharedPreferences("ALPSPref",MODE_PRIVATE);
//                            SharedPreferences.Editor editor=sharedPreferences.edit();
//                            editor.putString("uid",usname);
//                            editor.putString("upass",userspass);
//                            editor.putString("firstname",Firstname);
//                            editor.putString("lasname",lastname);
//                            editor.putString("Status",status);
//                            editor.putString("RegNo",regNo);
//                            editor.putString("reg_date",regdate);
//                            editor.putString("email",email);
//                            editor.putString("dojss",dateofjoining);
//                            editor.putString("currencySysmble",currency);
//                            editor.putString("useridpassword",userEncrpted);
//                            editor.putString("ConfpayNo",confpayNo);
//                            editor.putString("AcBalance",AcBalance);
//                            editor.putString("MobileNo",MobileNo);
//                            editor.putString("Address",Address);
//                            editor.putString("State",State);
//                            editor.putString("SID",SID);
//                            editor.putString("PinCode",PinCode);
//                            editor.putString("DISTID",DISTID);
////                                editor.putString("Countrycode",Countrycode);
//                            editor.putString("CID",CID);
//                            editor.putString("CTID",CTID);
//                            editor.putString("City",City);
//                            editor.putString("District",District);
//                            editor.commit();
//
//                            Log.e(TAG, "onResponse: RegNo  : "+ sharedPreferences.getString("RegNo", "") );
//
//
//                            session.createLoginSession(Firstname,lastname,email,regdate,status,usname,userspass,dateofjoining,
//                                    currency,userEncrpted);
//
////                            session.createLoginSession("","","","","",usname,userspass,"",
////                                    "","");
////                            //Start NavigationDrawer
//                            Intent go = new Intent(LoginActivity.this, DashBoard.class);
//                            startActivity(go);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
//                        }
//                        else {
//                            Snackbar snackbar = Snackbar.make(loginpage,"Please Enter Correct Username & Password",Snackbar.LENGTH_LONG);
//                            snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                            View snackbarView = snackbar.getView();
//                            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                            snackbar.show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }   if (dialog.isShowing())
//                        dialog.dismiss();
//
//                }, error -> {
//                    Snackbar snackbar = Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
//                    snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                    View snackbarView = snackbar.getView();
//                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                    snackbar.show();
//                    //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
//                    if (dialog.isShowing())
//                        dialog.dismiss();
//                })
//        {
//            protected Map<String,String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("username",usname);
//                params.put("pass",userspass);
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
        finish();
        super.onBackPressed();

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            //moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        // do something when the button is clicked
        // do something when the button is clicked
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", (arg0, arg1) -> {

                    finish();
                    //close();
                    onBackPressed();
                })
                .setNegativeButton("No", (arg0, arg1) -> {
                })
                .show();

    }

    //Internet Connection check
    public  boolean isConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();

        if (netinfo !=null&& netinfo.isConnectedOrConnecting()){
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

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

    @Override
    protected void onStop() {
        super.onStop();
//        finish();
    }
}
