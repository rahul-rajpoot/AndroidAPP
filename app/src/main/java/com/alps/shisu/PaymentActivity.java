package com.alps.shisu;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.Database.DatabaseHelper;
import com.alps.shisu.GetterSetter.CartGetter;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.service.SendData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class PaymentActivity extends BaseActivity implements PaymentResultWithDataListener {

    String getdate, productid, productdp, productmrp, productQty, loginid, cartTotalvalue, bv, pv, getamount, paymode, banknames;
    Toolbar toolbar;
    private Calendar cal;
    DatabaseHelper mydb;
    SessionManagement sessionManagement;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    HashMap<String, String> user;
    List<CartGetter> list = new ArrayList<>();
    RadioButton radioButton;
    Config ut;
    RadioGroup radioGroup;
    Spinner paymodespinner,storeid;
    Button Make_payment, Cancel_button;
    LinearLayout linearout1;
    EditText refno,inputbankname;
    TextView TotalAmount, TotalBV, Dateget;
    TextView defaultcountry, defaultcity, defaultstate, demo;
    LinearLayout bank, paydate, ref, hideDelivery;
    CheckBox deleverycheckbox;
    LinearLayout LinearLayout;
    android.app.AlertDialog dialog;
    Spinner Countyes, States, Cityvalue;
    EditText Addressvalue, Pincodevalue, Emailaddress, mobileno,citydefaultshow,statedefaultshow,mobilenumber;
    double totalamount = 0, vwallete, payble;
    SimpleDateFormat sdf;
    //String

    //    EditText Addressvalue, Pincodevalue, Emailaddress, mobileno,citydefaultshow,statedefaultshow;
    String Paymentdate, userloginid, mrp, dp, P_wallet_value, Ordernumber, Invoiceurl, Discticode, myFormat;
    String PWallteUrl = "", posturl = "", CWallteUrl = "", bankname = "0", refnumber = "0", ProfileUrl = "", totalpayble = "0";
    String sessionVwallet, add, city, state, Country, disc, pincode, cityvalue, username, useremail, phoneuser;
    String quantity, spinner_name, spinner_value, statevalue, discvalue;

    final String countryurl= Config.url +"getCity/"+ Config.merchantid +"/"+ Config.securtykey +"/0/0/0";
    ArrayList<String> countryname;
    ArrayList<String> countryid;

    final String storeurl = Config.url +"getStore/"+ Config.merchantid +"/"+ Config.securtykey +"/0/0";
    ArrayList<String> storename;
    ArrayList<String> storeids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        if (!isConnection(PaymentActivity.this)) buildDialog(PaymentActivity.this).show();
        mydb = new DatabaseHelper(this);
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment Details");
        toolbar.setTitleTextColor(Color.WHITE);
        sessionManagement = new SessionManagement(getApplicationContext());
        user = sessionManagement.getUserDetails();

        loginid = user.get(SessionManagement.KEY_USERNAME);
        list = mydb.getCart(loginid);
        Log.d("listproduct", String.valueOf(list));
        ProfileUrl = Config.url + "getProfile/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
        // posturl= ut.url+"SaveOrder/1/whm67UytRloIp67/Updatedata";
        posturl = Config.url + "SaveOrder/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/Object";
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        Intent intent = getIntent();
        productQty = intent.getStringExtra("pqty");
        productmrp = intent.getStringExtra("pmrp");
        productdp = intent.getStringExtra("dpmrp");
        productid = intent.getStringExtra("pid");

        preferences=getApplicationContext().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        countryname = new ArrayList<>();
        countryid = new ArrayList<>();

        getstore();
        storename = new ArrayList<>();
        storeids = new ArrayList<>();

        init();
        setCondition();
        getcountry();
    }

    private void getstore() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, storeurl, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String StoreID = jsonObject.getString("StoreID");
                    String StoreName = jsonObject.getString("StoreName");
                    String StoreMobile = jsonObject.getString("StoreMobile");

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ALPSPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("storeID", StoreID);
                    editor.putString("storemobile",StoreMobile);
                    editor.apply();

                    storeids.add(StoreID);
                    storename.add(StoreName);
                }
                storeids.add(0, "");
                storeids.add(0, "Select Store");
                ArrayAdapter<String> category_list_dropdown = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, storename);
                category_list_dropdown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                storeid.setAdapter(category_list_dropdown);

                storeid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItemId = storeids.get(position);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ALPSPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("storeIDS", selectedItemId);
                        editor.apply();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //search_btn.setEnabled(false);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
                /*if (dialog.isShowing())
                    dialog.dismiss();*/

        }, error -> {
               /* if (dialog.isShowing())
                    dialog.dismiss();*/
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getcountry() {
        dialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, countryurl, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String SID=jsonObject.getString("SID");
                    String StateName=jsonObject.getString("StateName");

                    SharedPreferences sharedPreferences=PaymentActivity.this.getSharedPreferences("ALPSPref",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("stateSID", SID);
                    editor.apply();

                    countryid.add(SID);
                    countryname.add(StateName);
                }
                countryid.add(0, "");
                countryname.add(0, "Select Country");
                ArrayAdapter<String> category_list_dropdown = new ArrayAdapter(PaymentActivity.this, android.R.layout.simple_spinner_dropdown_item,countryname);
                category_list_dropdown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Countyes.setAdapter(category_list_dropdown);

                Countyes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItemId = countryid.get(position);
                        SharedPreferences sharedPreferences=PaymentActivity.this.getSharedPreferences("ALPSPref",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("countryId", selectedItemId);
                        editor.apply();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //search_btn.setEnabled(false);
                    }
                });
                volleydata();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();

        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
        });
        RequestQueue requestQueue=Volley.newRequestQueue(PaymentActivity.this);
        requestQueue.add(stringRequest);

    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final AppCompatActivity activity = this;

        final Checkout co = new Checkout();
        int image = R.drawable.logo; // Can be any drawable
        co.setImage(image);

        try {

            //   String order_id = "AKFF42";

            JSONObject options = new JSONObject();
            options.put("name", "aadnyabullion Group");
            options.put("description", "Payable Charges");
            options.put("currency", "INR");
           /* options.put("payment_capture", 1);
            options.put("order_id", "order_4xbVikJiVknbmr");*/


/*
            String payment = tvPriceAddtoCart.getText().toString();

            total = Double.parseDouble(payment);*/
            //total = total * 100;

            //totalPrice is original price
            options.put("amount", payble * 100);
            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(PaymentActivity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }




    private void init() {
        demo = (TextView) findViewById(R.id.pd);
        defaultcity = (TextView) findViewById(R.id.citydefaultshow);
        defaultcountry = (TextView) findViewById(R.id.countrydefaultshow);
        defaultstate = (TextView) findViewById(R.id.statedefaultshow);
        // coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorpayment);
        hideDelivery = findViewById(R.id.hidelinealayout);
        ref = (LinearLayout) findViewById(R.id.rednoll);
        paydate = (LinearLayout) findViewById(R.id.paymentdatell);
        bank = (LinearLayout) findViewById(R.id.banknamell);
        LinearLayout = (LinearLayout) findViewById(R.id.coordinatorpayment);
        sessionManagement = new SessionManagement(getApplicationContext());
        user = sessionManagement.getUserDetails();
        //Address
        Addressvalue = (EditText) findViewById(R.id.addressdelivery);
        Cityvalue = (Spinner) findViewById(R.id.citydelivery);
        Pincodevalue = (EditText) findViewById(R.id.pincode);
        citydefaultshow = (EditText) findViewById(R.id.citydefaultshow);
        statedefaultshow = (EditText) findViewById(R.id.statedefaultshow);

        mobileno = (EditText) findViewById(R.id.phn);
        Emailaddress = (EditText) findViewById(R.id.emaild);
        deleverycheckbox = (CheckBox) findViewById(R.id.check);
        Countyes = (Spinner) findViewById(R.id.countryspinner);
        States = (Spinner) findViewById(R.id.statespinner);
        //Button
        Make_payment = (Button) findViewById(R.id.makepayment_btn);
        Cancel_button = (Button) findViewById(R.id.cancel_button_payment);
        linearout1=findViewById(R.id.linearout1);
        //Payment
        TotalAmount = (TextView) findViewById(R.id.totalamountpayment);
        TotalBV = (TextView) findViewById(R.id.totalbvpayment);
        Dateget = (TextView) findViewById(R.id.date);

        inputbankname=findViewById(R.id.inputbankname);
        refno = (EditText) findViewById(R.id.refno);
        radioButton = (RadioButton) findViewById(R.id.rdbtn);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        paymodespinner = (Spinner) findViewById(R.id.spinnerpaymentmode);
        storeid=findViewById(R.id.storeid);
        mobilenumber=findViewById(R.id.mobilenumber);
        Cityvalue.setEnabled(false);
//        Countyes.setEnabled(false);
        States.setEnabled(false);
//if ythi setEnabled
        mydb = new DatabaseHelper(this);

    }

    private void setCondition() {

        loginid = user.get(SessionManagement.KEY_USERNAME);
        Paymentdate = Dateget.getText().toString().trim();
        list = mydb.getCart(loginid);
        Log.e("responsejson", String.valueOf(list));
        getamount = mydb.TotalCal(loginid);
        cartTotalvalue = mydb.TotalCal(loginid);
        bv = mydb.TotalBvAmount(loginid);
        pv = mydb.TotalPvAmount(loginid);
        deleverycheckbox.setChecked(true);
        deleverycheckbox.setOnClickListener(v -> {
            switch (v.getId()) {
                case R.id.check:
                    if (deleverycheckbox.isChecked()) {
                        Countyes.setVisibility(View.GONE);
                        States.setVisibility(View.GONE);
                        Cityvalue.setVisibility(View.GONE);
                        defaultcity.setVisibility(View.VISIBLE);
                        defaultcountry.setVisibility(View.VISIBLE);
                        defaultstate.setVisibility(View.VISIBLE);

                        Addressvalue.setEnabled(false);
                        Pincodevalue.setEnabled(false);
                        hideDelivery.setEnabled(false);
                        mobileno.setEnabled(false);
                        Emailaddress.setEnabled(false);

                    } else {
                        Countyes.setVisibility(View.VISIBLE);
                        Cityvalue.setVisibility(View.VISIBLE);
                        States.setVisibility(View.VISIBLE);
                        defaultcity.setVisibility(View.GONE);
                        defaultcountry.setVisibility(View.GONE);
                        defaultstate.setVisibility(View.GONE);

                        Countyes.setEnabled(true);
                        Cityvalue.setEnabled(true);
                        States.setEnabled(true);

                        Addressvalue.setEnabled(true);
                        Pincodevalue.setEnabled(true);
                        hideDelivery.setEnabled(true);
                        mobileno.setEnabled(true);
                        Emailaddress.setEnabled(true);
                    }
            }
        });


        //Mode Of patment
        ArrayList arrayList = new ArrayList();
        arrayList.add("--Select MOP--");
        arrayList.add("Bank Deposit");
        arrayList.add("Bank Transfer");
        arrayList.add("By Cheque");
        arrayList.add("MPesa");
        // arrayList.add("Cash");
        // arrayList.add("C-Wallet");
        final ArrayAdapter adapter = new ArrayAdapter<String>(PaymentActivity.this, android.R.layout.simple_list_item_1, arrayList);
        paymodespinner.setAdapter(adapter);
        paymodespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paymode = parent.getItemAtPosition(position).toString();
                validationcheck();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       /* //Bank Name
        ArrayList banknamelist = new ArrayList();
        banknamelist.add("--Select Bank--");
        banknamelist.add("BANK OF BARODA");
        banknamelist.add("HDFC BANK LTD");
        banknamelist.add("ICICI BANK");
        final ArrayAdapter adapterbank = new ArrayAdapter<String>(PaymentActivity.this, android.R.layout.simple_list_item_1, banknamelist);
        banknamepayment.setAdapter(adapterbank);
        banknamepayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankname = parent.getItemAtPosition(position).toString();
                validationcheck();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        payble = Double.parseDouble(mydb.TotalCal(loginid));
        TotalBV.setText(bv);
        TotalAmount.setText(user.get(SessionManagement.KEY_CURRENCY) + cartTotalvalue);
        //Date
        cal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        Dateget.setOnClickListener(v -> new DatePickerDialog(PaymentActivity.this, date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show());
        Cancel_button.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, DashBoard.class);
            startActivity(intent);
        });

        Make_payment.setOnClickListener(v -> {
            if (!validationcheck() == false) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Processing your order", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
               /* Make_payment.setVisibility(View.GONE);
                Cancel_button.setVisibility(View.GONE);
                mydb.cleanCart(loginid);*/
                //   post(userloginid, username, useremail, phoneuser, spinner_value, statevalue, discvalue, cityvalue, pincode, add, paymode,sessionVwallet, String.valueOf(payble), bankname, refnumber, Paymentdate);
                //datamultiplepost();
                mydb.cleanCart(loginid);
                post(user.get(SessionManagement.KEY_USERNAME), username, useremail, phoneuser, statevalue, discvalue, cityvalue, pincode, add, paymode, sessionVwallet, String.valueOf(payble), bankname, refnumber);

            }
        });
        myFormat = "MM-dd-yyyy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        Dateget.setText(sdf.format(cal.getTime()));

    }

    public boolean validationcheck() {
        //Cwallet=(Double.parseDouble(CWALLET));
        // Pwallete=(Double.parseDouble(P_wallet_value));
        bankname = inputbankname.getText().toString();
        refnumber = refno.getText().toString();
        String edtAddressvalue = Addressvalue.getText().toString();
        String edtcitydefaultshow = citydefaultshow.getText().toString();
        String edtstatedefaultshow = statedefaultshow.getText().toString();
        String edtPincodevalue = Pincodevalue.getText().toString();


        if (edtAddressvalue.isEmpty()) {
            Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Address !!", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView tv = (TextView) snackbarView.findViewById( R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
            // Change the Snackbar default background color
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
            snackbar.show();
            return false;

        }else if (edtcitydefaultshow.isEmpty()) {
            Snackbar snackbar = Snackbar.make(LinearLayout, "Enter City !!", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
            // Change the Snackbar default background color
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
            snackbar.show();
            return false;

        }else if (edtstatedefaultshow.isEmpty()) {
            Snackbar snackbar = Snackbar.make(LinearLayout, "Enter State !!", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
            // Change the Snackbar default background color
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
            snackbar.show();
            return false;

        }else if (edtPincodevalue.isEmpty() || edtPincodevalue.length() < 4) {
            Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Min.4 Digit Zip Code !!", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
            // Change the Snackbar default background color
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
            snackbar.show();
            return false;

        }else if (paymodespinner.getSelectedItemPosition()==0) {
            Snackbar snackbar = Snackbar.make(LinearLayout, "Select payment mode !!", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
            snackbar.show();
            return false;
        }

        if (paymode.equals("Cash")) {
            bank.setVisibility(View.GONE);
            ref.setVisibility(View.GONE);
            paydate.setVisibility(View.VISIBLE);
            if (bankname.length() == 0) {
                bankname = "0";
            }
            if (refnumber.length() == 0) {
                refnumber = "0";
            }
            if (sessionVwallet.length() == 0) {
                sessionVwallet = "0";
            }
            if (sdf.format(cal.getTime()) == null) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Please Select Bank Name!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }
        }
        /*
        else if (paymode.equals("C-Wallet")) {
            bank.setVisibility(View.GONE);
            ref.setVisibility(View.GONE);
             paydate.setVisibility(View.GONE);
            if(bankname.length()==0){

                bankname="0";
            }
            if (refnumber.length()==0) {
                refnumber = "0";
            }n bbb

            if (payble > Cwallet){
                Snackbar snackbar=Snackbar.make(coordinatorLayout,"C-wallet insufficent Balance!!",Snackbar.LENGTH_LONG);
                snackbar.show();
                return  false;
            }
        }*/

        else if (paymode.equals("Bank Transfer")) {
            bank.setVisibility(View.VISIBLE);
            ref.setVisibility(View.VISIBLE);
            paydate.setVisibility(View.VISIBLE);
            if (refnumber.isEmpty()) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Ref No.!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }
            if (bankname.isEmpty()) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Select Bank Name !!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }
            if (refnumber.isEmpty()) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Ref No.!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }
            if (sdf.format(cal.getTime()) == null) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Please Select Bank Name!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }

        } else if (paymode.equals("Bank Deposit")) {
            bank.setVisibility(View.VISIBLE);
            ref.setVisibility(View.VISIBLE);
            paydate.setVisibility(View.VISIBLE);
            if (refnumber.isEmpty()) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Ref No.!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }
            if (bankname.isEmpty()) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Select Bank Name !!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }
            if (refnumber.isEmpty()) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Ref No.!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }
            if (sdf.format(cal.getTime()) == null) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Please Select Bank Name!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }

        } else if (paymode.equals("By Cheque")) {
            bank.setVisibility(View.GONE);
            ref.setVisibility(View.VISIBLE);
            paydate.setVisibility(View.VISIBLE);
            if (sdf.format(cal.getTime()) == null) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Please Select Bank Name!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById( R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;
            }
            if (refnumber.isEmpty()) {
                Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Ref No.!!", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById( R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
                // Change the Snackbar default background color
                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                snackbar.show();
                return false;

            } else if (paymode.equals("MPesa")) {
                bank.setVisibility(View.VISIBLE);
                ref.setVisibility(View.VISIBLE);
                paydate.setVisibility(View.VISIBLE);
                if (refnumber.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Ref No.!!", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById( R.id.snackbar_text);
                    tv.setTextColor(getResources().getColor(R.color.colorWhite));
                    // Change the Snackbar default background color
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                    snackbar.show();
                    return false;
                }
                if (bankname.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(LinearLayout, "Select MPesa !!", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById( R.id.snackbar_text);
                    tv.setTextColor(getResources().getColor(R.color.colorWhite));
                    // Change the Snackbar default background color
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                    snackbar.show();
                    return false;
                }
                if (refnumber.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(LinearLayout, "Enter Ref No.!!", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById( R.id.snackbar_text);
                    tv.setTextColor(getResources().getColor(R.color.colorWhite));
                    // Change the Snackbar default background color
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                    snackbar.show();
                    return false;
                }
                if (sdf.format(cal.getTime()) == null) {
                    Snackbar snackbar = Snackbar.make(LinearLayout, "Please Select MPesa!!", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById( R.id.snackbar_text);
                    tv.setTextColor(getResources().getColor(R.color.colorWhite));
                    // Change the Snackbar default background color
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                    snackbar.show();
                    return false;
                }
            }
            if (bankname.length() == 0) {
                bankname = "0";
            }

        }
        return true;
    }

    private void updateLabel() {
        myFormat = "MM-dd-yyyy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        Dateget.setText(sdf.format(cal.getTime()));
        getdate = sdf.format(cal.getTime());
    }

    private void volleydata() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, ProfileUrl, response -> {
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    username = jsonObject.getString("FirstName") + " " + jsonObject.getString("LastName");
                    add = jsonObject.getString("Address");
                    disc = jsonObject.getString("District");
                    city = jsonObject.getString("City");
                    state = jsonObject.getString("State");
                    pincode = jsonObject.getString("PinCode");
                    Country = jsonObject.getString("Country");
                    useremail = jsonObject.getString("EmailID");
                    phoneuser = jsonObject.getString("MobileNo");

                    spinner_value = jsonObject.getString("CID");
                    statevalue = jsonObject.getString("SID");
                    cityvalue = jsonObject.getString("CTID");
                    discvalue = jsonObject.getString("DISTID");

                    Addressvalue.setText(add);
                    Pincodevalue.setText(pincode);
                    mobileno.setText(phoneuser);
                    Emailaddress.setText(useremail);

                    defaultcity.setText(city);
                    defaultcountry.setText(Country);
                    defaultstate.setText(state);


                    //  listcitystore.add(city);
                    // ArrayAdapter adapter=new ArrayAdapter<String>(PaymentActivity.this,android.R.layout.simple_list_item_1,listcountrystore);
                    //  Cityvalue.setAdapter(adapter);

                }
                int selectedCountry=0;
                for (int i=0;i<countryid.size();i++){
                    if (countryid.get(i).equals(spinner_value) && countryname.get(i).equals(Country)){
                        selectedCountry=i;
                    }
                }
                Countyes.setSelection(selectedCountry);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();

        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
        });
        requestQueue.add(request);
    }

    //Here Product Details with Amount Post on Server
    private void post(String userloginid, String username, String useremail, String phoneuser, String state, String disc,
                      String city, String pincode, String add, String paymode,
                      String vwamount, String totalamount, String banknames, String Refnumbers) {

        String edtAddressvalue = Addressvalue.getText().toString();
        String edtPincodevalue = Pincodevalue.getText().toString();
        String edtcitydefaultshow = citydefaultshow.getText().toString();
        String edtstatedefaultshow = statedefaultshow.getText().toString();

        preferences=getApplicationContext().getSharedPreferences("ALPSPref",MODE_PRIVATE);
        String country = preferences.getString("countryId", null);
        String storeIDS = preferences.getString("storeIDS", null);
        Log.d("spusername1", toString());


        JSONArray jsonArray = new JSONArray();
        for (int p = 0; p < list.size(); p++) {
            try {
//                JSONArray jsonArray=new JSONArray();
                JSONObject parems = new JSONObject();
                parems.put("LoginID", user.get(SessionManagement.KEY_USERNAME));
                parems.put("Name", username);
                parems.put("emailid", useremail);
                parems.put("MobileNo", phoneuser);
                parems.put("CID", country);
                parems.put("SID", "0");
                parems.put("DISTID", "0");
                parems.put("oState",statedefaultshow.getText().toString());
                parems.put("CTID",  "0");
                parems.put("oCity",citydefaultshow.getText().toString());
                parems.put("Pincode", Pincodevalue.getText().toString());
                parems.put("Address", Addressvalue.getText().toString());
                parems.put("Paymode", paymode);
                parems.put("TotalAmount", totalamount);
                parems.put("V_Amount", "0");
                parems.put("BankName", banknames);
                parems.put("RefNo", Refnumbers);

                parems.put("PID", list.get(p).getProductesid());
                parems.put("MRP", list.get(p).getProductesMrpc());
                parems.put("DP", list.get(p).getProductesCp());
                parems.put("BV", list.get(p).getProductesBv());
                parems.put("Quantity", list.get(p).getProductesQty());
                jsonArray.put(parems);
                Log.e("cartlist", jsonArray.toString());

                try {
                    new SendData(PaymentActivity.this, jsonArray, posturl, response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response", String.valueOf(jsonObject));
                            Ordernumber = jsonObject.getString("Mpoid");
                            Invoiceurl = jsonObject.getString("InvUrl");
                            String Message=jsonObject.getString("Message");
                            String Mpoid=jsonObject.getString("Mpoid");
                            String St = jsonObject.getString("Status");
                            if (St.equals("1")) {
                                Snackbar snackbar = Snackbar.make(LinearLayout, "" + Message+" Order No is: "+Mpoid, Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Make_payment.setVisibility(View.GONE);
                                //Cancel_button.setVisibility(View.GONE);
                                Cancel_button.setText("Order No:"+Mpoid+"  >> Go to Home >>");
                                Intent in = new Intent(getApplicationContext(), OrderSummaryInvoiceActivity.class);
                                in.putExtra("orderno", Ordernumber);
                                in.putExtra("invurl", Invoiceurl);
                                startActivity(in);
                                mydb.cleanCart(loginid);

                            } else {
                                Snackbar snackbar = Snackbar.make(LinearLayout, "" + Message, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, true).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("updateException", e.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();
            if (id == android.R.id.home) {

                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            return super.onOptionsItemSelected(item);
        }
        @Override
        public void finish () {
            super.finish();
            //  overridePendingTransition(R.anim.side_in_left,R.anim.slide_out_left);
        }
        @Override
        public void onBackPressed () {
            super.onBackPressed();
            Intent i = new Intent(PaymentActivity.this, CartActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        //Internet Connection check
        public boolean isConnection (Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();

            if (netinfo != null && netinfo.isConnectedOrConnecting()) {
                android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                    return true;
                else return false;
            } else
                return false;
        }
        public AlertDialog.Builder buildDialog (Context context){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Internet Connection");
            builder.setMessage("You need to have Mobile Data or Wifi to access this. Press ok to Exit");

            builder.setPositiveButton("OK", (dialogInterface, i) -> finish());
            return builder;
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Intent intent=new Intent(this,OrderSummaryInvoiceActivity.class);
        intent.putExtra("orderno", Ordernumber);
        intent.putExtra("invurl", Invoiceurl);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }
}



