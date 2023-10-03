package com.alps.shisu.Shopping.shopnow;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.alps.shisu.adapterclass.MySpinnerAdapter;
import com.alps.shisu.modelclass.PaymentModeDetails;
import com.alps.shisu.db.local.entity.ProductsDetails;
import com.alps.shisu.modelclass.StateDetails;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class MakePaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private static final String STATE_DETAILS = "state_details";
    private static final String CITY_NAME = "city_name";
    private static final String MOBILE_NO = "mobile_no";
    private static final String ADDRESS = "address";
    private static final String PRODUCT_DETETAILS = "product_details";
    private static final String TOTAL_QUANTITY = "quantity";
    private static final String NEW_MRP = "new_mrp";
    private static final String NEW_WEIGHT = "new_weight";

    private String mParam1;
    private String mobileNoStr;
    private String addressStr;
    private List<ProductsDetails> productsDetailsList;
    private Spinner _paymentModeSpinner;
    private TextView  _paymentDate;
    private EditText _bankName, _refrenceNo, _remark;
    private String bankName, refrenceNo, paymentDate, remark, paymentMode;
    private final ArrayList<String> paymentModeArray = new ArrayList<>();
    private final List<PaymentModeDetails> paymentModeList = new ArrayList<>();
    private StateDetails finalStateDetails = new StateDetails();
    private LinearLayout _makePaymentRoot, _refrenceNoLL, _bankNameLL;
    private Config ut;
    private String urls="";
    private SharedPreferences sharedPreferences;
    private float newWeight, newMrp;
    private int totalQuantity;


    public MakePaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param finalStateDetails Parameter 1.
     * @param cityName Parameter 2.
     * @return A new instance of fragment MakePaymentFragment.
     */
    public static MakePaymentFragment newInstance(StateDetails finalStateDetails, String cityName, String mobileNo, String address, List<ProductsDetails> productsDetailsList, float newWeight, float newMRP, int quantity) {
        MakePaymentFragment fragment = new MakePaymentFragment();
        Bundle args = new Bundle();
        args.putSerializable(STATE_DETAILS, finalStateDetails);
        args.putString(CITY_NAME, cityName);
        args.putString(MOBILE_NO, mobileNo);
        args.putFloat(NEW_WEIGHT, newWeight);
        args.putInt(TOTAL_QUANTITY, quantity);
        args.putFloat(NEW_MRP, newMRP);
        args.putString(ADDRESS, address);
        args.putSerializable(PRODUCT_DETETAILS, (Serializable) productsDetailsList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            finalStateDetails = (StateDetails) getArguments().getSerializable(STATE_DETAILS);
            String cityName = getArguments().getString(CITY_NAME);
            mobileNoStr = getArguments().getString(MOBILE_NO);
            addressStr = getArguments().getString(ADDRESS);
            newWeight = getArguments().getFloat(NEW_WEIGHT);
            totalQuantity = getArguments().getInt(TOTAL_QUANTITY);
            newMrp = getArguments().getFloat(NEW_MRP);
            productsDetailsList = (List<ProductsDetails>) getArguments().getSerializable(PRODUCT_DETETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_payment, container, false);

        sharedPreferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);

        _paymentModeSpinner = view.findViewById(R.id.payment_mode_spinner);
        _bankName = view.findViewById(R.id.bankName);
        _refrenceNo = view.findViewById(R.id.refrenceNo);
        _paymentDate = view.findViewById(R.id.paymentDate);
        _remark = view.findViewById(R.id.remark);
        TextView _makePayment = view.findViewById(R.id.makePayment);
        TextView _cancel = view.findViewById(R.id.cancel);
        _makePaymentRoot = view.findViewById(R.id.makePaymentRoot);
        _refrenceNoLL = view.findViewById(R.id.refrenceNoLL);
        _bankNameLL = view.findViewById(R.id.bankNameLL);
        _refrenceNoLL.setVisibility(View.GONE);
        _bankNameLL.setVisibility(View.GONE);

        _paymentModeSpinner.setOnItemSelectedListener(this);

        SessionManagement sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String,String> user= sessionManagement.getUserDetails();
        urls= Config.url +"getPaymentMethod/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);
        Log.e(TAG, "onCreateView: "+ urls );

        PaymentModeDetails paymentModeDetails = new PaymentModeDetails();
        paymentModeDetails.setPaymentId("");
        paymentModeDetails.setPaymentText("Select Payment Mode");

        paymentModeList.add(paymentModeDetails);

        paymentModeArray.add("Select Payment Mode");
//        paymentModeArray.add("Bank Transfer");
//        paymentModeArray.add("Online Payment");
////        paymentModeArray.add("Bank Deposit");
//        paymentModeArray.add("Wallet");
////        paymentModeArray.add("Cash");

        setPaymentDate();

        getPaymentModes();

        _paymentDate.setOnClickListener(v -> setSelectedPaymentDate());

        _makePayment.setOnClickListener(v -> {
            if(_paymentModeSpinner.getSelectedItemPosition() >0) {
                getTextFromEditText();
                if (checkValidation()) {
                    if (_paymentModeSpinner.getSelectedItem() != null && _paymentModeSpinner.getSelectedItem().toString().equalsIgnoreCase("Ewallet")){
//                            checkForAvaialbleFund(sharedPreferences.getString("AcBalance", ""), "1", ""+newWeight, ""+newMrp, "false");
                        checkForAvaialbleFund(sharedPreferences.getString("AcBalance", ""), "1", ""+newWeight, ""+newMrp, "false");
                    } else {
                        ((ShoppingActivity) getActivity()).saveOrder(finalStateDetails, mobileNoStr, addressStr, productsDetailsList, paymentMode, bankName, refrenceNo, paymentDate, remark, ""+newWeight, ""+newMrp, ""+totalQuantity);
                    }
                }
            } else {
                showError("Please select a payment mode.");
            }
        });

        _cancel.setOnClickListener(v -> {
            try {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            } catch (Exception e){
                e.printStackTrace();
            }
        });


        return view;
    }

    private boolean checkValidation() {
        if (_paymentModeSpinner.getSelectedItem() != null && !_paymentModeSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Payment Mode")){
            if(_paymentModeSpinner.getSelectedItemPosition() == 0){
                return true;
            } else  if (_paymentModeSpinner.getSelectedItem() != null && _paymentModeSpinner.getSelectedItem().toString().equalsIgnoreCase("Bank Deposit")){
                if(_refrenceNo.getText().toString().isEmpty()){
                    showError("Please enter reference no.");
                    return false;
                }
                if(_bankName.getText().toString().isEmpty()){
                    showError("Please enter bank name.");
                    return false;
                }
            }
//            else if(_paymentModeSpinner.getSelectedItemPosition() == 2) {
//                return true;
//            }
        }else{
            showError("Please check selected payment mode.");
            return false;
        }

        return true;
    }


    private void checkForAvaialbleFund(String goldBalance, String extraMargin, String weight, String Amount, String otherPayment) {

        float weight1 = Float.parseFloat(weight);
        Log.e(TAG, "checkForAvaialbleFund: " + weight1);
        float _1Per = (weight1 / 100);
        Log.e(TAG, "checkForAvaialbleFund: 1 % = " + _1Per);
        float extraMarg = _1Per * Float.parseFloat(extraMargin);
        Log.e(TAG, "checkForAvaialbleFund: extraMargin : " + extraMarg);
        float goldValue = Float.parseFloat(goldBalance);
        Log.e(TAG, "checkForAvaialbleFund: gold value : " + goldValue);

        if (otherPayment.equalsIgnoreCase("false")) {
            if (goldValue < (weight1 - extraMarg)) {
                Toast.makeText(getContext(), "Don't have enough Balance to purchase any item.", Toast.LENGTH_SHORT).show();
            } else {
                ((ShoppingActivity) getActivity()).saveOrder(finalStateDetails, mobileNoStr, addressStr, productsDetailsList, "", "", "", "", "", ""+newWeight, ""+newMrp, ""+totalQuantity);
            }
        }
//        else {
//            ((ShoppingActivity) getActivity()).showMakePaymentPage("Payment", finalStateDetails, cityName, mobileNoStr, addressStr, productsDetailsList);
//        }

    }

    private void getPaymentModes() {
        //dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                response -> {
                    try {

                        JSONArray object = new JSONArray(response);
//                            paymentModeArray.clear();
//                            paymentModeList.clear();

                        for (int i = 0; i < object.length(); i++) {
                            JSONObject jsonObject = object.getJSONObject(i);
                            //   Boolean b=object.getBoolean("error");

                            String payText = jsonObject.getString("payText");
                            String payid = jsonObject.getString("payid");

                            PaymentModeDetails paymentModeDetails = new PaymentModeDetails();
                            paymentModeDetails.setPaymentId(payid);
                            paymentModeDetails.setPaymentText(payText);

                            paymentModeList.add(paymentModeDetails);
                            paymentModeArray.add(payText);

                        }

                        MySpinnerAdapter adapter = new MySpinnerAdapter(
                                getContext(),
                                R.layout.view_spinner_item,
                                paymentModeArray);
                        adapter.setDropDownViewResource(R.layout.drop_down_item);
                        _paymentModeSpinner.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }   /*if (dialog.isShowing())
                        dialog.dismiss();*/

                }, error -> {
    //                Snackbar snackbar=Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
    //                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
    //                View snackbarView = snackbar.getView();
    //                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
    //                snackbar.show();
                    //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                   /* if (dialog.isShowing())
                        dialog.dismiss();*/
                }) {
            protected Map<String, String> getParams() {
                //                params.put("username",usname);
//                params.put("pass",userspass);
                return new HashMap<>();
            }
        };
        requestQueue.add(stringRequest);
    }


    private void getTextFromEditText() {
        bankName = _bankName.getText().toString();
        refrenceNo = _refrenceNo.getText().toString();
        paymentDate = _paymentDate.getText().toString();
        remark = _remark.getText().toString();
//        paymentMode = _paymentModeSpinner.getSelectedItem().toString();
    }

    private void showError(String msg){
        Snackbar snackbar=Snackbar.make(_makePaymentRoot,msg,Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
        snackbar.show();
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(parent.getId() == R.id.payment_mode_spinner) {
                if (paymentModeArray != null && paymentModeArray.size() > 0) {
                    if (position > 0) {
                        PaymentModeDetails paymentModeDetails = paymentModeList.get(position);
                        paymentMode = paymentModeDetails.getPaymentId();
                        _paymentModeSpinner.setSelection(position);
                        if(position == 2){
                            _refrenceNoLL.setVisibility(View.VISIBLE);
                            _bankNameLL.setVisibility(View.VISIBLE);
                        } else {
                            _refrenceNoLL.setVisibility(View.GONE);
                            _bankNameLL.setVisibility(View.GONE);
                        }
                    }
                } else {
                    _refrenceNoLL.setVisibility(View.GONE);
                    _bankNameLL.setVisibility(View.GONE);
                }
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    public void payment(){

    }

}