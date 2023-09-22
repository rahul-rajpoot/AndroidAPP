package com.alps.shisu.FinanacialReport;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.PayoutDeductionListDataAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.PayoutDeductionGetterSetter;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayoutDeductionFragment extends Fragment {
    private RecyclerView recyclerViewPD;
    private LinearLayoutManager linearLayoutManager;
    List<PayoutDeductionGetterSetter> listpd;
    Config ut;
    String Pdur="";
    SessionManagement sessionManagement;
    SharedPreferences preferences;
    //Progress Dialog Box
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    //Hasgmap using get data into Sharedpreferences
    HashMap<String,String> user;
    TextView header;
    AlertDialog dialog;
    public PayoutDeductionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payout_deduction, container, false);     // Session class instance
        sessionManagement = new SessionManagement(getContext());
        preferences=this.getActivity().getSharedPreferences("ALPSPref",MODE_PRIVATE);
        user=sessionManagement.getUserDetails();
        header=view.findViewById(R.id.totalincomepdheader);
//        header.setText("Total Income ("+user.get(sessionManagement.KEY_CURRENCY)+")");
        header.setText("Total Income ("+user.get(SessionManagement.KEY_CURRENCY)+")");
        listpd=new ArrayList<>();

        progressBar=view.findViewById(R.id.progressdeduction);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        Pdur= Config.url +"payoutDeduction/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);

        Log.e(TAG, "onCreateView payoutDeduction : "+ Pdur );
        recyclerViewPD=view.findViewById(R.id.payoutDeduction_recyclerview);
        recyclerViewPD.setHasFixedSize(true);
        GridLayoutManager gm = new GridLayoutManager(getContext(), 1);
        recyclerViewPD.setLayoutManager(gm);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);



        getdata();
        return view;
    }
    private void getdata(){
      dialog.show();
        //  progressBar.setVisibility(View.VISIBLE);
//progressDialog.setMessage("Please Wait...");
//progressDialog.show();
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, Pdur, response -> {
            try {
                JSONArray array=new JSONArray(response);
                for (int i=0;i<array.length();i++) {
                    JSONObject object = array.getJSONObject(i);


                    listpd.add(new PayoutDeductionGetterSetter(
                            object.getString("payid"),
                            object.getString("totalincome"),
                            user.get(SessionManagement.KEY_CURRENCY) + "" + object.getString("tdsamount"),
                            object.getString("tdsrate"),
                            user.get(SessionManagement.KEY_CURRENCY) + "" + object.getString("procesingamount"),
                            object.getString("procesingrate"),
                            user.get(SessionManagement.KEY_CURRENCY) + "" + object.getString("lockamount"),
                            user.get(SessionManagement.KEY_CURRENCY) + "" + object.getString("totaldeduction"),
                            user.get(SessionManagement.KEY_CURRENCY) + "" + object.getString("netpayable"),
//                                         "₹" + object.getString("tdsamount"),
//                                        "₹" + object.getString("procesingamount"),
//                                        "₹" + object.getString("lockamount"),
//                                        "₹" + object.getString("totaldeduction"),
//                                        "₹" + object.getString("netpayable"),
                            object.getString("Status")
                    ));
                    //Collections.sort(listpd,PayoutDeductionListData.BY_DATE_ASCENDING);
                    PayoutDeductionListDataAdapter payoutDeductionListDataAdapter = new PayoutDeductionListDataAdapter(getContext(), listpd, user.get(SessionManagement.KEY_CURRENCY));
                    recyclerViewPD.setAdapter(payoutDeductionListDataAdapter);
                    // payoutDeductionListDataAdapter.notifyDataSetChanged();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Stopping swipe refresh
            if (dialog.isShowing())
                dialog.dismiss();
            //   progressBar.setVisibility(View.GONE);
            //     progressDialog.dismiss();
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
            // progressBar.setVisibility(View.GONE);
            //  progressDialog.dismiss();
        });queue.add(request);

    }


}
