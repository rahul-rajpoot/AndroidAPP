package com.alps.shisu.CurrencyWallet;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletSummaryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "WalletSummaryFragment";

    private String mParam1;
    private String mParam2;

    String[] imageUrls;
    String[] catNames;
    String[] catIds;
    private String urls = "";
    private Config ut;
    private GridView grid;
    private TextView _avaialbleBalance, _ledgerBalance, _credit_1, _credit_2, _credit_3, _credit_4, _credit_5,
            _credit_6, _credit_7, _credit_8, _credit_9, _credit_10, _credit_11, _credit_12, _credit_13, _debit_2, _debit_3, _debit_4, _debit_1,
            _credit_1_value, _credit_2_value, _credit_3_value, _credit_4_value,_credit_5_value, _credit_6_value, _credit_7_value, _credit_8_value,
            _credit_9_value, _credit_10_value, _credit_11_value, _credit_12_value, _credit_13_value, _debit_2_value,_debit_3_value, _debit_4_value, _debit_1_value;
    private LinearLayout _walletSummaryRoot;
    private HashMap<String,String> user;

    public WalletSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletSummaryFragment.
     */
    public static WalletSummaryFragment newInstance(String param1, String param2) {
        WalletSummaryFragment fragment = new WalletSummaryFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_summary, container, false);

        // Session class instance
        SessionManagement sessionManagement = new SessionManagement(getContext());

        user= sessionManagement.getUserDetails();

        _walletSummaryRoot = view.findViewById(R.id.walletSummaryRoot);
        _avaialbleBalance = view.findViewById(R.id.avaialbleBalance);
        _ledgerBalance = view.findViewById(R.id.ledgerBalance);
        _credit_1 = view.findViewById(R.id.credit_1);
        _credit_1_value = view.findViewById(R.id.credit_1_value);
        _credit_2 = view.findViewById(R.id.credit_2);
        _credit_2_value = view.findViewById(R.id.credit_2_value);
        _credit_3 = view.findViewById(R.id.credit_3);
        _credit_3_value = view.findViewById(R.id.credit_3_value);
        _credit_4 = view.findViewById(R.id.credit_4);
        _credit_4_value = view.findViewById(R.id.credit_4_value);

        _credit_5 = view.findViewById(R.id.credit_5);
        _credit_5_value = view.findViewById(R.id.credit_5_value);
        _credit_6 = view.findViewById(R.id.credit_6);
        _credit_6_value = view.findViewById(R.id.credit_6_value);
        _credit_7 = view.findViewById(R.id.credit_7);
        _credit_7_value = view.findViewById(R.id.credit_7_value);
        _credit_8 = view.findViewById(R.id.credit_8);
        _credit_8_value = view.findViewById(R.id.credit_8_value);
        _credit_9 = view.findViewById(R.id.credit_9);
        _credit_9_value = view.findViewById(R.id.credit_9_value);
        _credit_10 = view.findViewById(R.id.credit_10);
        _credit_10_value = view.findViewById(R.id.credit_10_value);
//        _credit_11 = view.findViewById(R.id.credit_11);
//        _credit_11_value = view.findViewById(R.id.credit_11_value);
//        _credit_12 = view.findViewById(R.id.credit_12);
//        _credit_12_value = view.findViewById(R.id.credit_12_value);
//        _credit_13 = view.findViewById(R.id.credit_13);
//        _credit_13_value = view.findViewById(R.id.credit_13_value);


        _debit_1 = view.findViewById(R.id.debit_1);
        _debit_1_value = view.findViewById(R.id.debit_1_value);
        _debit_2 = view.findViewById(R.id.debit_2);
        _debit_2_value = view.findViewById(R.id.debit_2_value);
        _debit_3 = view.findViewById(R.id.debit_3);
        _debit_3_value = view.findViewById(R.id.debit_3_value);
        _debit_4 = view.findViewById(R.id.debit_4);
        _debit_4_value = view.findViewById(R.id.debit_4_value);

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("ALPSPref",MODE_PRIVATE);
        String userName = sharedPreferences.getString("uid", "");
        String userPass =sharedPreferences.getString("upass","");

//        urls = ut.url + "GetGoldWallet/" + ut.merchantid + ut.securtykey + "/AADNYA/dj29ng14";
//        urls = ut.url + "GetGoldWallet/" + ut.merchantid + ut.securtykey + "/"+userName+"/"+userPass;
        urls = ut.url + "getWallet/" + ut.merchantid +"/"+ ut.securtykey + "/"+userName+"/"+userPass;
        Log.e(TAG, "onCreateView: " + urls);
        // Uncomment
        getWalletSummary();


//        // Comment
//        _avaialbleBalance.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _ledgerBalance.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//
//        _credit_1.setText("Smart Performance Incentive");
//        _credit_1_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_2.setText("Patron Development Incentive");
//        _credit_2_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_3.setText("Leadership Royalty Bonus");
//        _credit_3_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_4.setText("Direct Deposit");
//        _credit_4_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//
//        _credit_5.setText("Recruitment Bonus");
//        _credit_5_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_6.setText("Performance Bonus");
//        _credit_6_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_7.setText("Silver Club Bonus");
//        _credit_7_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_8.setText("Gold Club Bonus");
//        _credit_8_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_9.setText("Platinum Club Bonus");
//        _credit_9_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_10.setText("Diamond Club Bonus");
//        _credit_10_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_11.setText("Overriding Bonus");
//        _credit_11_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_12.setText("Car Fund");
//        _credit_12_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _credit_13.setText("House Fund");
//        _credit_13_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//
//        _debit_1.setText("Payment");
//        _debit_1_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _debit_2.setText("Admin Fee");
//        _debit_2_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _debit_3.setText("TDS");
//        _debit_3_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//        _debit_4.setText("Direct Deduction");
//        _debit_4_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");

        return view;
    }

    private void getWalletSummary() {
        //dialog.show();
        RequestQueue requestQueue = new Volley().newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "onResponse: "+ response );

                            JSONArray object = new JSONArray(response);

//                            for (int i = 0; i < object.length(); i++) {
                            JSONObject jsonObject = object.getJSONObject(0);
                            //   Boolean b=object.getBoolean("error");

                            String available_balance = jsonObject.getString("Available_balance");
                            String ledger_balance = jsonObject.getString("Ledger_balance");

                            String Txtmpayout1 = jsonObject.getString("Txtmpayout1");
                            String Txtmpayout1_value = jsonObject.getString("Txtmpayout1_value");

                            String Txtmpayout2 = jsonObject.getString("Txtmpayout2");
                            String Txtmpayout2_value = jsonObject.getString("Txtmpayout2_value");

                            String Txtmpayout3 = jsonObject.getString("Txtmpayout3");
                            String Txtmpayout3_value = jsonObject.getString("Txtmpayout3_value");

                            String Txtmpayout4 = jsonObject.getString("Txtmpayout4");
                            String Txtmpayout4_value = jsonObject.getString("Txtmpayout4_value");

                            String Txtmpayout5 = jsonObject.getString("Txtmpayout5");
                            String Txtmpayout5_value = jsonObject.getString("Txtmpayout5_value");

                            String Txtmpayout6 = jsonObject.getString("Txtmpayout6");
                            String Txtmpayout6_value = jsonObject.getString("Txtmpayout6_value");

                            String Txtmpayout7 = jsonObject.getString("Txtmpayout7");
                            String Txtmpayout7_value = jsonObject.getString("Txtmpayout7_value");

                            String Txtmpayout8 = jsonObject.getString("Txtmpayout8");
                            String Txtmpayout8_value = jsonObject.getString("Txtmpayout8_value");

                            String Txtmpayout9 = jsonObject.getString("Txtmpayout9");
                            String Txtmpayout9_value = jsonObject.getString("Txtmpayout9_value");

                            String Txtmpayout10 = jsonObject.getString("Txtmpayout10");
                            String Txtmpayout10_value = jsonObject.getString("Txtmpayout10_value");

//                      ++++++++++++++++++++++


                            _avaialbleBalance.setText(user.get(SessionManagement.KEY_CURRENCY) + available_balance );
                            _ledgerBalance.setText(user.get(SessionManagement.KEY_CURRENCY) + ledger_balance );

                            _credit_1.setText(Txtmpayout1);
                            _credit_1_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout1_value);
                            _credit_2.setText(Txtmpayout2);
                            _credit_2_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout2_value);

                            _credit_3.setText(Txtmpayout3);
                            _credit_3_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout3_value);
                            _credit_4.setText(Txtmpayout4);
                            _credit_4_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout4_value);

                            _credit_5.setText(Txtmpayout5);
                            _credit_5_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout5_value);
                            _credit_6.setText(Txtmpayout6);
                            _credit_6_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout6_value);
                            _credit_7.setText(Txtmpayout7);
                            _credit_7_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout7_value);
                            _credit_8.setText(Txtmpayout10);
                            _credit_8_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout10_value);
                            _credit_9.setText(Txtmpayout8);
                            _credit_9_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout8_value);
                            _credit_10.setText(Txtmpayout9);
                            _credit_10_value.setText(user.get(SessionManagement.KEY_CURRENCY) + Txtmpayout9_value);
//                            _credit_11.setText("Overriding Bonus");
//                            _credit_11_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//                            _credit_12.setText("Car Fund");
//                            _credit_12_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");
//                            _credit_13.setText("House Fund");
//                            _credit_13_value.setText(user.get(SessionManagement.KEY_CURRENCY) +" 0.00");



                            String Txtmpayout11 = jsonObject.getString("Txtmpayout11");
                            String Txtmpayout11_value = jsonObject.getString("Txtmpayout11_value");

                            String Txtmpayout12 = jsonObject.getString("Txtmpayout12");
                            String Txtmpayout12_value = jsonObject.getString("Txtmpayout12_value");


                            String Txtmpayout13 = jsonObject.getString("Txtmpayout13");
                            String Txtmpayout13_value = jsonObject.getString("Txtmpayout13_value");

                            String Txtmpayout14 = jsonObject.getString("Txtmpayout14");
                            String Txtmpayout14_value = jsonObject.getString("Txtmpayout14_value");



                            _debit_1.setText(Txtmpayout11);
                            _debit_1_value.setText(user.get(SessionManagement.KEY_CURRENCY)+ Txtmpayout11_value);

                            _debit_2.setText(Txtmpayout12);
                            _debit_2_value.setText(user.get(SessionManagement.KEY_CURRENCY)+ Txtmpayout12_value);

                            _debit_3.setText(Txtmpayout13);
                            _debit_3_value.setText(user.get(SessionManagement.KEY_CURRENCY)+ Txtmpayout13_value);

                            _debit_4.setText(Txtmpayout14);
                            _debit_4_value.setText(user.get(SessionManagement.KEY_CURRENCY)+ Txtmpayout14_value);


//                            if(getActivity() != null) {
//                                ((MainActivity) getActivity()).hideDefaultLoaderFragment();
//                            }

                        } catch (JSONException e) {
                            if(getActivity() != null) {
//                                ((MainActivity) getActivity()).hideDefaultLoaderFragment();
                                ((CurrencyWalletActivity) getActivity()).showErrorSnackBar(_walletSummaryRoot, "Something went wrong");
                            }
                            e.printStackTrace();
                        }   /*if (dialog.isShowing())
                            dialog.dismiss();*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(getActivity() != null) {
//                    ((MainActivity) getActivity()).hideDefaultLoaderFragment();
                    ((CurrencyWalletActivity) getActivity()).showErrorSnackBar(_walletSummaryRoot, "Something went wrong");
                }
//                Snackbar snackbar=Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
//                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                View snackbarView = snackbar.getView();
//                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                snackbar.show();
                //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
               /* if (dialog.isShowing())
                    dialog.dismiss();*/
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("username",usname);
//                params.put("pass",userspass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}