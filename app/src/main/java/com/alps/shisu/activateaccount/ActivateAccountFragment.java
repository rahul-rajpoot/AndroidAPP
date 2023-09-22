package com.alps.shisu.activateaccount;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alps.shisu.ActivateAccountPaymentActivity;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.KycManager.KycManager;
import com.alps.shisu.PaymentConfirmActivity;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.adapterclass.ActivePackageListAdapter;
import com.alps.shisu.adapterclass.MySpinnerAdapter;
import com.alps.shisu.modelclass.PackageDetails;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivateAccountFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Spinner spinnervalue;
    AppCompatTextView submit;
    String pinvalue;
    String urlpost = "";
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

    HashMap<String, String> user;
    private Spinner select_package_spinner;
    private Spinner bank_name_spinner;
    AlertDialog dialog;
    private String urls = "";
    private String currentPackageUrl = "";
    private Config ut;
    private final List<PackageDetails> packageDetailsList = new ArrayList<>();
    private final HashMap<String, PackageDetails> packageDetailsHashMap = new HashMap<>();
    private LinearLayout activateAccountRoot;
    private String currentPackageName, paymentMode;
    private EditText _price, _epinNumber;
    private GridView packageList;
    private final List<PackageDetails> activePackageDetailsList = new ArrayList<>();


    public ActivateAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivateAccountFragment.
     */
    public static ActivateAccountFragment newInstance(String param1, String param2) {
        ActivateAccountFragment fragment = new ActivateAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activate_account, container, false);
        CardView packageDetailsRoot = view.findViewById(R.id.packageDetailsRoot);
        select_package_spinner = view.findViewById(R.id.select_package_spinner);
        Spinner payment_mode_spinner = view.findViewById(R.id.payment_mode_spinner);
        activateAccountRoot = view.findViewById(R.id.activateAccountRoot);
        CardView currentPackageDetails = view.findViewById(R.id.currentPackageDetails);
        packageList = view.findViewById(R.id.packageList);
        TextView serialNo = view.findViewById(R.id.serialNo);
        TextView currentPackageText = view.findViewById(R.id.currentPackageText);
        submit = view.findViewById(R.id.submitButton);
        _price = view.findViewById(R.id.price);
        _epinNumber = view.findViewById(R.id.epinNumber);
        _epinNumber.setVisibility(View.GONE);

        select_package_spinner.setOnItemSelectedListener(this);
        payment_mode_spinner.setOnItemSelectedListener(this);

        sessionManagement = new SessionManagement(getContext());
        user = sessionManagement.getUserDetails();

        dialog = new SpotsDialog.Builder().setContext(getActivity()).setCancelable(false).build();
        dialog.show();

        try {
            preferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);
            int accountActivated = Integer.parseInt(preferences.getString("ConfpayNo", ""));
            if (accountActivated > 0) {

                packageDetailsRoot.setVisibility(View.GONE);
                currentPackageDetails.setVisibility(View.VISIBLE);
//              https://api.aplusmart.online/DeveloperApi/ActivatedPackage/M/A345P3Mart/1/AP1298mAtr4340BtMN/sanjeevsingh/1111            currentPackageUrl = ut.url + "getKitDetails/" + ut.merchantid + "/" + ut.securtykey + "/" + accountActivated+"/0";
                currentPackageUrl = Config.url + "ActivatedPackage/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
                Log.e(TAG, "onCreateView: currentPackageUrl : " + currentPackageUrl);
                getCurrentPackage();

            } else {

                packageDetailsRoot.setVisibility(View.VISIBLE);
                currentPackageDetails.setVisibility(View.GONE);

                selectPackageList.add("Select Package");
                selectPaymentMode.add("Select Mode of Payment");
                selectPaymentMode.add("Online Payment");
                selectPaymentMode.add("E-Pin");


                //        https://api.aplusmart.online/DeveloperApi/getKit/M/A345P3Mart/1/AP1298mAtr4340BtMN/startid/12345
                urls = Config.url + "getKit/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
                Log.e(TAG, "onCreateView: " + urls);
                getPackageList();


                MySpinnerAdapter adapter2 = new MySpinnerAdapter(
                        getContext(),
                        R.layout.view_spinner_item,
                        selectPaymentMode);
                adapter2.setDropDownViewResource(R.layout.drop_down_item);
                payment_mode_spinner.setAdapter(adapter2);

            }


            List<String> leftrightlist = new ArrayList<>();

            leftrightlist.add("Select No of Pin.");
            leftrightlist.add("1");
            leftrightlist.add("2");
            leftrightlist.add("3");
            leftrightlist.add("4");
            leftrightlist.add("5");

            submit.setOnClickListener(view1 -> {
                PackageDetails packageDetails = packageDetailsHashMap.get(currentPackageName);
                if (!paymentMode.isEmpty() && paymentMode.equalsIgnoreCase("Online Payment")) {
                    Intent intent = new Intent(getActivity(), PaymentConfirmActivity.class);
                    intent.putExtra("kId", packageDetails.getKitId());
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    if (_epinNumber.getVisibility() == View.VISIBLE && _epinNumber.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please enter E-Pin number.", Toast.LENGTH_LONG).show();
                    } else {
                        activateAccount(packageDetails.getKitId(), _epinNumber.getText().toString());
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            if(dialog != null)
            dialog.dismiss();
        }
        return view;
    }

    public void activateAccount(final String kId, final String ePinNo) {

        JSONArray jsonArray = new JSONArray();
        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("LoginID", user.get(SessionManagement.KEY_USERNAME));
            jsonObject.put("Kid", kId);
            jsonObject.put("Paymode", paymentMode);
            jsonObject.put("refNo", ePinNo);

            jsonArray.put(jsonObject);

            Log.e(TAG, "activateAccount: " + jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String mRequestBody = jsonArray.toString();

        urls = Config.url + "ActivateAccount/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/Object";

        Log.e(TAG, "onCreateView: " + urls);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls,
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        Log.e(TAG, "onResponse: " + object);
//                            Toast.makeText(getContext(), "Your Account has been activated successfully.", Toast.LENGTH_SHORT).show();
                        if (!paymentMode.isEmpty() && paymentMode.equalsIgnoreCase("OnlinePayment")) {
//                                String orderId = object.getString("Mpoid");
                            Intent intent = new Intent(getActivity(), ActivateAccountPaymentActivity.class);
                            PackageDetails packageDetails = packageDetailsHashMap.get(currentPackageName);
                            intent.putExtra("kId", packageDetails.getKitId());
                            startActivity(intent);
                        } else {
                            try {
                                String Status = object.getString("Status");
                                String message = object.getString("Message");
                                int intResultValue = Integer.parseInt(Status);
                                if (intResultValue == 1) {
                                    try {
                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(), ActivateAccountActivity.class));
                                        getActivity().finish();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }   /*if (dialog.isShowing())
                        dialog.dismiss();*/
                }, error -> {

                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return mRequestBody == null ? null : mRequestBody.getBytes(StandardCharsets.UTF_8);
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

    private void postvalue() {
        dialog.show();
        //   progressBar.setVisibility(View.VISIBLE);

        //String value=spinnervalue.getSelectedItem().toString();

//        https://api.aplusmart.online/DeveloperApi/ActivateAccount/M/A345P3Mart/1/AP1298mAtr4340BtMN/startid/Object
//        urlpost = ut.url + "generateKYCEpin/" + ut.merchantid + "/" + ut.securtykey + "/" + user.get(sessionManagement.KEY_USERNAME) + "/" + pinvalue;
        urlpost = Config.url + "ActivateAccount/" + Config.merchantid + "/" + Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/Object";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, urlpost, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject object = jsonArray.getJSONObject(0);
                String msg = object.getString("MessageCode");
                Toast.makeText(getContext(), "" + msg, Toast.LENGTH_LONG).show();
                Snackbar snackBar = Snackbar.make(relativeLayoutgeneratepinll, msg, Snackbar.LENGTH_LONG);
                snackBar.show();
                Intent intent = new Intent(getContext(), KycManager.class);
                intent.putExtra("km", 1);
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
        });
        queue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.select_package_spinner) {
            if (selectPackageList != null && selectPackageList.size() > 0) {
                if (position > 0) {
                    currentPackageName = selectPackageList.get(position);
                    PackageDetails packageDetails = packageDetailsHashMap.get(currentPackageName);
                    if (packageDetails != null) {
                        _price.setText(packageDetails.getKitPrice());
                        _price.setEnabled(false);
                    }
                }
            }
        } else if (parent.getId() == R.id.payment_mode_spinner) {
            if (selectPaymentMode != null && selectPaymentMode.size() > 0) {
                if (position > 0) {
                    paymentMode = selectPaymentMode.get(position);
                    if (paymentMode != null && paymentMode.equalsIgnoreCase("E-Pin")) {
                        _epinNumber.setVisibility(View.VISIBLE);
                    } else {
                        _epinNumber.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getCurrentPackage() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, currentPackageUrl,
                response -> {
                    try {

                        JSONArray object = new JSONArray(response);

                        activePackageDetailsList.clear();

                        for (int i = 0; i <object.length() ; i++) {

                            JSONObject jsonObject = object.getJSONObject(0);

                            String kid = jsonObject.getString("kid");
                            String kitCode = jsonObject.getString("kitCode");
                            String kitDesc = jsonObject.getString("kitDesc");
                            String kitPrice = jsonObject.getString("kitPrice");

                            PackageDetails packageDetails = new PackageDetails();
                            packageDetails.setKitId(kid);
                            packageDetails.setKitCode(kitCode);
                            packageDetails.setKitDesc(kitDesc);
                            packageDetails.setKitPrice(kitPrice);

                            activePackageDetailsList.add(packageDetails);

                        }

                        ActivePackageListAdapter adapter = new ActivePackageListAdapter(getActivity(), activePackageDetailsList);
                        packageList.setAdapter(adapter);
//                            packageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view,
//                                                        int position, long id) {
////                                    ((ShoppingActivity)getActivity()).showProductList("Products List", categoryItemList.get(position).getCategoryId());
//                                }
//                            });


                        try {
                            if (dialog.isShowing())
                                dialog.dismiss();
                            ((ActivateAccountActivity) getActivity()).hideDefaultLoaderFragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                            }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (dialog.isShowing())
                            dialog.dismiss();
                        ((ActivateAccountActivity) getActivity()).hideDefaultLoaderFragment();
                        ((ActivateAccountActivity) getActivity()).showErrorSnackBar(activateAccountRoot, "Something went wrong");

                    }

                }, error -> {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    ((ActivateAccountActivity) getActivity()).hideDefaultLoaderFragment();
                    ((ActivateAccountActivity) getActivity()).showErrorSnackBar(activateAccountRoot, "Something went wrong");
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
                            ((ActivateAccountActivity) getActivity()).hideDefaultLoaderFragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                            }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (dialog.isShowing())
                            dialog.dismiss();
                        ((ActivateAccountActivity) getActivity()).hideDefaultLoaderFragment();
                        ((ActivateAccountActivity) getActivity()).showErrorSnackBar(activateAccountRoot, "Something went wrong");

                    }

                }, error -> {
                    if (dialog.isShowing())
                        dialog.dismiss();
                    ((ActivateAccountActivity) getActivity()).hideDefaultLoaderFragment();
                    ((ActivateAccountActivity) getActivity()).showErrorSnackBar(activateAccountRoot, "Something went wrong");
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
}