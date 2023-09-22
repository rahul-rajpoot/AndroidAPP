package com.alps.shisu.KycManager;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.adapterclass.MySpinnerAdapter;
import com.alps.shisu.modelclass.PackageDetails;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneratePinFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinnervalue;
//    Button submit;
    String pinvalue;
    String urlpost="";
    EditText valueEdit;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences preferences;
    SessionManagement sessionManagement;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    RelativeLayout relativeLayoutgeneratepinll;
    private final ArrayList<String> selectPackageList = new ArrayList<>();
    private final ArrayList<String> selectPaymentMode = new ArrayList<>();
    private final ArrayList<String> selectBankName = new ArrayList<>();
    private final List<PackageDetails> packageDetailsList = new ArrayList<>();
    private final HashMap<String, PackageDetails> packageDetailsHashMap = new HashMap<>();
    private String currentPackageName, paymentMode;
    private LinearLayout generateEPINRoot, refrenceNoLL;

    HashMap<String,String> user;
//    private Spinner select_package_spinner, payment_mode_spinner, bank_name_spinner;
    private Spinner select_package_spinner, payment_mode_spinner;
    AlertDialog dialog;
    private String urls = "";
    private final String currentPackageUrl = "";
    private Config ut;
    private EditText noOfEPin, onePinPrice, totalAmount, note, refrenceNo, bankName;
    private String bankNameStr, refrenceNoStr, noOfPinStr, amountStr, refRegNo;
    private TextView _paymentDate;
    private String paymentDate, noteStr;
    private LinearLayout bankNameLL;
    private PackageDetails packageDetails;


    public GeneratePinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_generate_pin, container, false);
//        spinnervalue=view.findViewById(R.id.kyccode);
        TextView submitButton = view.findViewById(R.id.submitButton);
        generateEPINRoot=view.findViewById(R.id.generateEPINRoot);
        select_package_spinner=view.findViewById(R.id.select_package_spinner);
        payment_mode_spinner=view.findViewById(R.id.payment_mode_spinner);
//        bank_name_spinner=view.findViewById(R.id.bank_name_spinner);

        select_package_spinner.setOnItemSelectedListener(this);
        payment_mode_spinner.setOnItemSelectedListener(this);
//        bank_name_spinner.setOnItemSelectedListener(this);

        TextView cancelButton = view.findViewById(R.id.cancelButton);
        noOfEPin = view.findViewById(R.id.noOfEPin);
        onePinPrice = view.findViewById(R.id.onePinPrice);
        totalAmount = view.findViewById(R.id.totalAmount);
        _paymentDate = view.findViewById(R.id.paymentDate);
        refrenceNoLL = view.findViewById(R.id.refrenceNoLL);
        refrenceNo = view.findViewById(R.id.refrenceNo);
        note = view.findViewById(R.id.note);

        bankNameLL = view.findViewById(R.id.bankNameLL);
        bankName = view.findViewById(R.id.bankName);

        refrenceNoLL.setVisibility(View.GONE);
        bankNameLL.setVisibility(View.GONE);

        preferences=getActivity().getSharedPreferences("ALPSPref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        user=sessionManagement.getUserDetails();
        
        selectPackageList.add("Select Package");
        selectBankName.add("Select Bank Name");


        selectPaymentMode.add("Select Mode of Payment");
        selectPaymentMode.add("Bank Deposit");
        selectPaymentMode.add("Cash");


        //        https://api.aplusmart.online/DeveloperApi/getKit/M/A345P3Mart/1/AP1298mAtr4340BtMN/startid/12345
        urls = Config.url + "getKit/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
        Log.e(TAG, "onCreateView: " + urls);
        getPackageList();


        noOfEPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() > 0){
                    int noOfPin = Integer.parseInt(noOfEPin.getText().toString());
                    float price = Float.parseFloat(onePinPrice.getText().toString().substring(1, onePinPrice.length()));
                    float totalAmountValue = (noOfPin * price);
                    totalAmount.setText(user.get(SessionManagement.KEY_CURRENCY) + totalAmountValue);

                } else{
                    totalAmount.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        MySpinnerAdapter adapter2 = new MySpinnerAdapter(
                getContext(),
                R.layout.view_spinner_item,
                selectPaymentMode);
        adapter2.setDropDownViewResource(R.layout.drop_down_item);
        payment_mode_spinner.setAdapter(adapter2);

//        MySpinnerAdapter adapter3 = new MySpinnerAdapter(
//                getContext(),
//                R.layout.view_spinner_item,
//                selectBankName);
//        adapter3.setDropDownViewResource(R.layout.drop_down_item);
//        bank_name_spinner.setAdapter(adapter3);

//        relativeLayoutgeneratepinll=view.findViewById(R.id.generatepinll);
//        progressBar=view.findViewById(R.id.generatepinp);
      //  Circle circle=new Circle();
      //  progressBar.setIndeterminateDrawable(circle);
        //progressDialog=new ProgressDialog(getContext());
        //progressDialog.setCancelable(false);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

//        List<String> leftrightlist=new ArrayList<String>();
//
//        leftrightlist.add("Select No of Pin.");
//        leftrightlist.add("1");
//        leftrightlist.add("2");
//        leftrightlist.add("3");
//        leftrightlist.add("4");
//        leftrightlist.add("5");
//        leftrightlist.add("6");
//        leftrightlist.add("7");
//        leftrightlist.add("8");
//        leftrightlist.add("9");
//        leftrightlist.add("10");

        _paymentDate.setOnClickListener(v -> setSelectedPaymentDate());

        setPaymentDate();

//        spinnervalue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                pinvalue=adapterView.getItemAtPosition(i).toString();
//                //   Toast.makeText(adapterView.getContext(),""+sides,Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


//        ArrayAdapter<String> LRarrayAdapter=new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,leftrightlist);
//        spinnervalue.setAdapter(LRarrayAdapter);

        submitButton.setOnClickListener(view1 -> {
//                if (pinvalue.isEmpty()&& pinvalue==null){
//                    Toast.makeText(getContext(),"Please Select the no of pin",Toast.LENGTH_LONG).show();
//                }else {
////                    generateEPIN();
//                }
//                if(payment_mode_spinner.getSelectedItemPosition() >0) {
                getTextFromEditText();
                if (checkValidation()) {
                    if (payment_mode_spinner.getSelectedItemPosition() == 2) {
                        generateEPIN(refRegNo, packageDetails.getKitId(), noOfPinStr, amountStr, paymentMode, refrenceNoStr, bankNameStr, paymentDate, noteStr );
                    } else {
                        if (refrenceNoStr.isEmpty()) {
                            showError("Please enter reference no");
                        } else if (bankNameStr.isEmpty()) {
                            showError("Please enter Bank Name");
                        } else {
                            generateEPIN(refRegNo, packageDetails.getKitId(), noOfPinStr, amountStr, paymentMode, refrenceNoStr, bankNameStr, paymentDate, noteStr);
                        }
                    }
                }
//                } else {
//                    showError("Please select a payment mode.");
//                }
        });

        cancelButton.setOnClickListener(v -> clearAllData());

        return view;
    }

    private void clearAllData(){

        bankName.setText("");
        refrenceNo.setText("");
        _paymentDate.setText("");
        note.setText("");
        select_package_spinner.setSelection(0);
        payment_mode_spinner.setSelection(0);
        _paymentDate.setText("");
        noOfEPin.setText("");
        totalAmount.setText("");
        onePinPrice.setText("");

        bankNameStr = "";
        refrenceNoStr = "";
        paymentDate = "";
        noteStr = "";
        paymentMode = "";
        paymentDate = "";
        noOfPinStr = "";
        amountStr = "";


        packageDetails = new PackageDetails();

    }

    private void getTextFromEditText() {
        try {
            bankNameStr = bankName.getText().toString();
            refrenceNoStr = refrenceNo.getText().toString();
            paymentDate = _paymentDate.getText().toString();
            noteStr = note.getText().toString();
            paymentMode = payment_mode_spinner.getSelectedItem().toString();
            paymentDate = _paymentDate.getText().toString();
            noOfPinStr = noOfEPin.getText().toString();
            amountStr = totalAmount.getText().toString();
            amountStr = amountStr.substring(1);

            refRegNo = preferences.getString("RegNo", "");

            Log.e(TAG, "getTextFromEditText: refRegNo:  " + refRegNo);

            packageDetails = packageDetailsHashMap.get(currentPackageName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     */
    private void setPaymentDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        if(month <10 && day <10) {
            paymentDate = "0"+(month + 1) + "-0" + day + "-" + year;
        } else if(month <10 && day >10) {
            paymentDate = "0"+(month + 1) + "-" + day + "-" + year;
        } else if(month >10 && day <10) {
            paymentDate = (month + 1) + "-0" + day + "-" + year;
        } else if(month >10 && day >10) {
            paymentDate = (month + 1) + "-" + day + "-" + year;
        }
        _paymentDate.setText(paymentDate);

    }

    /**
     *
     */
    private void setSelectedPaymentDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        //                        _fromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        //                        _fromDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
        //                        fromDate = (_fromDate.getText().toString()).replaceAll("/","-");
        DatePickerDialog picker = new DatePickerDialog(getActivity(),
                (view, year1, monthOfYear, dayOfMonth) -> {
//                        _fromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    _paymentDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year1);
//                        _fromDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
//                        fromDate = (_fromDate.getText().toString()).replaceAll("/","-");
                }, year, month, day);
        picker.getDatePicker().setMaxDate(new Date().getTime());
        picker.show();
    }


    private void getPackageList() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                response -> {
                    try {

                        JSONArray object = new JSONArray(response);

                        packageDetailsList.clear();

//                            JSONObject jsonObject1 = object.getJSONObject(0);

                        for (int i = 0; i < object.length(); i++) {

                            JSONObject jsonObject = object.getJSONObject(i);

                            String kid = jsonObject.getString("kid");
                            String kitCode = jsonObject.getString("kitCode");
                            String kitDesc = jsonObject.getString("kitDesc");
                            String kitPrice = jsonObject.getString("kitPrice");

                            PackageDetails packageDetails = new PackageDetails();
                            packageDetails.setKitId(kid);
                            packageDetails.setKitCode(kitCode);
                            packageDetails.setKitDesc(kitDesc);
                            packageDetails.setKitPrice(kitPrice);

                            selectPackageList.add(kitCode);
                            packageDetailsList.add(packageDetails);
                            packageDetailsHashMap.put(kitCode, packageDetails);

                        }

                        MySpinnerAdapter adapter = new MySpinnerAdapter(
                                getContext(),
                                R.layout.view_spinner_item,
                                selectPackageList);
                        adapter.setDropDownViewResource(R.layout.drop_down_item);
                        select_package_spinner.setAdapter(adapter);

                        try {
                            if (dialog.isShowing())
                                dialog.dismiss();
                            ((KycManager) getActivity()).hideDefaultLoaderFragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                            }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (dialog.isShowing())
                            dialog.dismiss();
                        ((KycManager) getActivity()).hideDefaultLoaderFragment();
                        ((KycManager) getActivity()).showErrorSnackBar(generateEPINRoot, "Something went wrong");

                    }

                }, error -> {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    ((KycManager) getActivity()).hideDefaultLoaderFragment();
                    ((KycManager) getActivity()).showErrorSnackBar(generateEPINRoot, "Something went wrong");

                }) {
            protected Map<String, String> getParams() {
                //                params.put("username",usname);
//                params.put("pass",userspass);
                return new HashMap<>();
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showError(String msg){
        Snackbar snackbar=Snackbar.make(generateEPINRoot,msg,Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
        snackbar.show();
    }

    private boolean checkValidation() {

        if (select_package_spinner.getSelectedItem() != null && select_package_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Package")){
            showError("Please Select any Package first.");
            return false;
        }
        if(noOfEPin.getText().toString().isEmpty()){
            showError("Please Select the no of pin.");
            return false;
        }
        if (payment_mode_spinner.getSelectedItem() != null && !payment_mode_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Mode of Payment")){
            if(payment_mode_spinner.getSelectedItemPosition() == 0){
                return true;
            } else if(payment_mode_spinner.getSelectedItemPosition() == 1) {
                if(refrenceNo.getText().toString().isEmpty()){
                    showError("Please enter reference no.");
                    return false;
                }
                if(bankName.getText().toString().isEmpty()){
                    showError("Please enter bank name.");
                    return false;
                }
            } else if(payment_mode_spinner.getSelectedItemPosition() == 2) {
                return true;
            }
        }else{
            showError("Please check selected payment mode.");
            return false;
        }

        return true;
    }


    public void generateEPIN(final String refRegNo, final String kId, final String sEQty, final String netAmount, final String paymode, final String refNo, final String bankName, final String disc, final String note) {

        JSONArray jsonArray = new JSONArray();
        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("RefRegNo", refRegNo);
            jsonObject.put("kid", kId);
            jsonObject.put("SEQty", sEQty);
            jsonObject.put("netAmount", netAmount);
            jsonObject.put("paymode", paymode);
            jsonObject.put("refNo", refNo);
            jsonObject.put("bankName", bankName);
            jsonObject.put("comment", note);
            jsonObject.put("disc", "0");

            jsonArray.put(jsonObject);

            Log.e(TAG, "generate pin : " + jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String mRequestBody = jsonArray.toString();

//        https://api.aplusmart.online/DeveloperAPI/RequestEpin/M/A345P3Mart/1/AP1298mAtr4340BtMN/startid/Object

        urls = Config.url + "RequestEpin/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/Object";

        Log.e(TAG, "onCreateView: " + urls);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        Log.e(TAG, "onResponse: " + object);
                        String message = object.getString("Message");
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                            if (!paymode.isEmpty() && paymode.equalsIgnoreCase("OnlinePayment")) {
////                                String orderId = object.getString("Mpoid");
//                                Intent intent = new Intent(getActivity(), ActivateAccountPaymentActivity.class);
//                                PackageDetails packageDetails = packageDetailsHashMap.get(currentPackageName);
//                                intent.putExtra("kId", packageDetails.getKitId());
//                                startActivity(intent);
//                            } else {
//                                try {
//                                    String Status = object.getString("Status");
//                                    String message = object.getString("Message");
//                                    int intResultValue = Integer.parseInt(Status);
//                                    if (intResultValue == 1) {
//                                        try {
//                                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                                            startActivity(new Intent(getActivity(), KycManager.class));
//                                            getActivity().finish();
//                                        }catch (Exception e){
//                                            e.printStackTrace();
//                                        }
//                                    } else {
//                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }   /*if (dialog.isShowing())
                        dialog.dismiss();*/
                }, error -> {

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
                Map<String, String> params = new HashMap<>();
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

    private void postvalue(){
        dialog.show();
     //   progressBar.setVisibility(View.VISIBLE);

        //String value=spinnervalue.getSelectedItem().toString();

//        https://api.aplusmart.online/DeveloperAPI/RequestEpin/M/A345P3Mart/1/AP1298mAtr4340BtMN/startid/Object
//        urlpost=ut.url+"generateKYCEpin/"+ut.merchantid+"/"+ut.securtykey+"/"+user.get(sessionManagement.KEY_USERNAME)+"/"+pinvalue;
        urlpost= Config.url +"RequestEpin/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/Object";

        Log.e(TAG, "postvalue: "+urlpost  );

        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, urlpost, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                JSONObject object=jsonArray.getJSONObject(0);
                String msg=object.getString("MessageCode");
                Toast.makeText(getContext(),""+msg,Toast.LENGTH_LONG).show();
                Snackbar snackBar= Snackbar.make(relativeLayoutgeneratepinll,msg,Snackbar.LENGTH_LONG);
                snackBar.show();
                Intent intent=new Intent(getContext(),KycManager.class);
                intent.putExtra("km",1);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
            //progressBar.setVisibility(View.GONE);
            //     swipeRefreshLayout.setRefreshing(false);
        }, error -> {
//swipeRefreshLayout.setRefreshing(false);
            if (dialog.isShowing())
                dialog.dismiss();
           // progressBar.setVisibility(View.GONE);
        });queue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.select_package_spinner) {
            if (selectPackageList != null && selectPackageList.size() > 0) {
                if (position > 0) {
                    currentPackageName = selectPackageList.get(position);
                    PackageDetails packageDetails = packageDetailsHashMap.get(currentPackageName);
                    if (packageDetails != null) {
                        onePinPrice.setText(user.get(SessionManagement.KEY_CURRENCY) + packageDetails.getKitPrice());
                        onePinPrice.setEnabled(false);
                        onePinPrice.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));
                    }
                }
            }
        } else if (parent.getId() == R.id.payment_mode_spinner) {
            if (selectPaymentMode != null && selectPaymentMode.size() > 0) {
                if (position > 0) {
                    paymentMode = selectPaymentMode.get(position);
                    if (paymentMode != null && paymentMode.equalsIgnoreCase("Bank Deposit")) {
                        refrenceNoLL.setVisibility(View.VISIBLE);
                        bankNameLL.setVisibility(View.VISIBLE);
                    } else {
                        refrenceNoLL.setVisibility(View.GONE);
                        bankNameLL.setVisibility(View.GONE);
                    }
                }
            }
        }

//        else if (parent.getId() == R.id.bank_name_spinner) {
//            if (selectBankName != null && selectBankName.size() > 0) {
//                if (position > 0) {
////                    cityName = selectBankName.get(position);
////                    if (cityHashMap != null && cityHashMap.containsKey(cityName)) {
////                        cityId = cityHashMap.get(cityName.trim());
////                        pinCode.setText(pinCodeStr);
////                    }
//                }
//            }
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
