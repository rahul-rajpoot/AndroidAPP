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
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.PayoutReportDataAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.PayoutReportGetterSetter;
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
public class PayoutReportFragment extends Fragment {

    RecyclerView recyclerViewPR;
    List<PayoutReportGetterSetter> PRlist;
    Config ut;
    String urlPR="";
    String balurl="";
    ProgressBar progressBar;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    TextView avlbal,ledbal;
    TextView symbaltotalincome;
    // change row size according to your need, how many row you needed per page
    int rowSize = 5;
    AlertDialog dialog;
    HashMap<String,String> user;
    public PayoutReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payout_report, container, false);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        avlbal=view.findViewById(R.id.in_amt);
        ledbal=view.findViewById(R.id.in_legamt);
        // Session class instance
        SessionManagement sessionManagement = new SessionManagement(getContext());
        preferences=this.getActivity().getSharedPreferences("ALPSPref",MODE_PRIVATE);
        user= sessionManagement.getUserDetails();
        //Commented by anshu
//        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/digital-7.ttf");
//        avlbal.setTypeface(typeface);
//        ledbal.setTypeface(typeface);
        symbaltotalincome=view.findViewById(R.id.totalincomepr);

        symbaltotalincome.setText("Total Income ("+user.get(SessionManagement.KEY_CURRENCY)+")");
       // progressBar=view.findViewById(R.id.payoutreportprogress);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        PRlist=new ArrayList<>();
//        urlPR= Config.url +"payoutReport/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);
        urlPR= Config.url +"payoutReport/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/0";
        balurl= Config.url +"getWallet/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);

        Log.e(TAG, "onCreateView payoutReport : "+ urlPR );
        Log.e(TAG, "onCreateView: getWallet : "+ balurl );
        recyclerViewPR=view.findViewById(R.id.payoutreport_recyclerview);
        recyclerViewPR.setHasFixedSize(true);
        GridLayoutManager gm = new GridLayoutManager(getContext(), 1);
        recyclerViewPR.setLayoutManager(gm);

//        refreshLayout=view.findViewById(R.id.payoutreport);
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_blue_dark);
//
//
//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//                loadRecyclerViewData();
//            }
//        });
        loadRecyclerViewData();

        return view;

    }
    /**
     * This method is called when swipe refresh is pulled down
     //     */
//    @Override
//    public void onRefresh(){
//        loadRecyclerViewData();
//    }
    private void loadRecyclerViewData(){
        dialog.show();
      //  progressBar.setVisibility(View.VISIBLE);
        //json
        //   progressDialog.setMessage("Please Wait...");
        // progressDialog.show();
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, urlPR, response -> {
            try {
                Log.e(TAG, "loadRecyclerViewData: "+ response );
                JSONArray array=new JSONArray(response);
                for (int t=0;t<array.length();t++){
                    JSONObject jsonObject=array.getJSONObject(t);


//                    PRlist.add(new PayoutReportGetterSetter(
//                            jsonObject.getString("payid"),
//                            jsonObject.getString("CFrpv"),
//                            jsonObject.getString("CFlpv"),
//                            jsonObject.getString("currentRPV"),
//                            jsonObject.getString("currentLPV"),
//                            jsonObject.getString("totalRPV"),
//                            jsonObject.getString("totalLPV"),
//                            jsonObject.getString("flushPV"),
//                            user.get(SessionManagement.KEY_CURRENCY)+""+ jsonObject.getString("directIncome"),
////                                        "₹"+ jsonObject.getString("directIncome"),
//                            jsonObject.getString("matchingPV"),
//                            jsonObject.getString("totalIncome"),
////                                        "₹"+jsonObject.getString("royaltyIncome"),
//                            user.get(SessionManagement.KEY_CURRENCY) +jsonObject.getString("royaltyIncome"),
////                                        user.get(management.KEY_CURRENCY)+""+jsonObject.getString("PVIncome")
//                            user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("PVIncome")
////                                        "₹"+jsonObject.getString("PVIncome")


                            PRlist.add(new PayoutReportGetterSetter(
                                    jsonObject.getString("payid"),
                                    jsonObject.getString("CFrpv"),
                                    jsonObject.getString("CFlpv"),
                                    jsonObject.getString("currentRPV"),
                                    jsonObject.getString("currentLPV"),
                                    jsonObject.getString("totalRPV"),
                                    jsonObject.getString("totalLPV"),
                                    jsonObject.getString("flushPV"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+ jsonObject.getString("directIncome"),
//                                        "₹"+ jsonObject.getString("directIncome"),
                                    jsonObject.getString("matchingPV"),
                                    jsonObject.getString("totalIncome"),
//                                        "₹"+jsonObject.getString("royaltyIncome"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("silverClubBonus"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("goldClubBonus"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("platinumClubBonus"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("diamondClubBonus"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("diamondOverridingBonus"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("CarFund"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("houseFund"),
                                    user.get(SessionManagement.KEY_CURRENCY)+""+jsonObject.getString("PerformanceBonus")
                    ));
                    PayoutReportDataAdapter adapter=new PayoutReportDataAdapter(getContext(),PRlist);
                    recyclerViewPR.setAdapter(adapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Stopping swipe refresh
            if (dialog.isShowing())
                dialog.dismiss();
            //    progressBar.setVisibility(View.GONE);
            // refreshLayout.setRefreshing(false);
            //                      progressDialog.dismiss();
        }, error -> {
            // Stopping swipe refresh

            if (dialog.isShowing())
                dialog.dismiss();
            //   progressBar.setVisibility(View.GONE);
            //        refreshLayout.setRefreshing(false);
//                progressDialog.dismiss();
        });queue.add(stringRequest);



// Showing refresh animation before making http call
//        refreshLayout.setRefreshing(true);
        //json bal
      //  progressBar.setVisibility(View.VISIBLE);
        dialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, balurl, response -> {
            try {
                JSONArray array=new JSONArray(response);
                JSONObject object=array.getJSONObject(0);
//                            avlbal.setText("₹ "+object.getString("Available_balance"));
                avlbal.setText(user.get(SessionManagement.KEY_CURRENCY)+object.getString("Available_balance"));
//                            ledbal.setText("₹ "+object.getString("Ledger_balance"));
                ledbal.setText(user.get(SessionManagement.KEY_CURRENCY)+object.getString("Ledger_balance"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
           // progressBar.setVisibility(View.GONE);
            //                      refreshLayout.setRefreshing(false);
            //                  progressDialog.dismiss();
        }, error -> {
            //            refreshLayout.setRefreshing(false);
            //            progressDialog.dismiss();
          //  progressBar.setVisibility(View.GONE);
            if (dialog.isShowing())
                dialog.dismiss();
        });requestQueue.add(request);

    }
}
