package com.alps.shisu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Available_Ledger_Balance extends BaseActivity{

    String url="";
    Config ut;
    TextView avbal,ledgbal;
    SessionManagement management;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available__ledger__balance);
        if (!isConnection(Available_Ledger_Balance.this))buildDialog(Available_Ledger_Balance.this).show();


        // Session class instance
        management = new SessionManagement(getApplicationContext());
        preferences=getSharedPreferences("ALPSPref",MODE_PRIVATE);
        HashMap<String,String> user=management.getUserDetails();
        avbal=findViewById(R.id.in_amt);
        ledgbal=findViewById(R.id.in_legamt);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);
        url= Config.url +"getWallet/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);

        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray array=new JSONArray(response);
                JSONObject object=array.getJSONObject(0);
                avbal.setText(object.getString("Available_balance"));
                ledgbal.setText(object.getString("Ledger_balance"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }, error -> progressDialog.dismiss());requestQueue.add(request);


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

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
