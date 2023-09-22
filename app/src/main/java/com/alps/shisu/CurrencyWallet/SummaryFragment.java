package com.alps.shisu.CurrencyWallet;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {


    TextView avlamt,ledgamt,pairincomeat,directincome,royaltyincome;
    TextView outtds,outac,outdeduction,outwd;
    SessionManagement sessionManagement;
    SharedPreferences preferences;
    Config ut;
    ProgressDialog progressDialog;
    String statementurl="";

    ProgressBar progressBar;

    public SummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View iew= inflater.inflate(R.layout.fragment_summary, container, false);

        // Session class instance
        sessionManagement = new SessionManagement(getContext());

        preferences=this.getActivity().getSharedPreferences("ALPSPref",MODE_PRIVATE);
        HashMap<String,String> user=sessionManagement.getUserDetails();

        avlamt=iew.findViewById(R.id.S_amt);
        ledgamt=iew.findViewById(R.id.s_legamt);
        pairincomeat=iew.findViewById(R.id.pmincome);
        directincome=iew.findViewById(R.id.dincome);
        royaltyincome=iew.findViewById(R.id.rincome);

        outtds=iew.findViewById(R.id.tds);
        outac=iew.findViewById(R.id.admincharge);
        outdeduction=iew.findViewById(R.id.deduction);
        outwd=iew.findViewById(R.id.withdrawal);
        statementurl= Config.url +"getWallet/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/digital-7.ttf");
        avlamt.setTypeface(typeface);
        ledgamt.setTypeface(typeface);
        pairincomeat.setTypeface(typeface);
        directincome.setTypeface(typeface);
        royaltyincome.setTypeface(typeface);
        outtds.setTypeface(typeface);
        outac.setTypeface(typeface);
        outdeduction.setTypeface(typeface);
        outwd.setTypeface(typeface);

        progressBar=iew.findViewById(R.id.summaryprogress);

        loadData();


        // getdata();


        return iew;
    }

    private void loadData(){

//        progressDialog.setMessage("Please wait....");
//        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        //   swipeRefreshLayout.setRefreshing(true);
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, statementurl,
                response -> {
                    try {
                        JSONArray array=new JSONArray(response);
                        JSONObject object=array.getJSONObject(0);
                        if (object.getString("Status").equals("True")) {
                            String AvailableAMount = object.getString("Available_balance");
                            avlamt.setText(AvailableAMount);
                            String LedgerAMount = object.getString("Ledger_balance");
                            ledgamt.setText(LedgerAMount);

                            pairincomeat.setText(object.getString("Txtmpayout1_value"));
                            directincome.setText(object.getString("Txtmpayout2_value"));
                            royaltyincome.setText(object.getString("Txtmpayout3_value"));
                            outtds.setText(object.getString("Txtmpayout6_value"));
                            outdeduction.setText(object.getString("Txtmpayout8_value"));
                            outac.setText(object.getString("Txtmpayout7_value"));
                            outwd.setText(object.getString("Txtmpayout9_value"));
                        }
                        //                                Toast.makeText(getContext(),"No Record Found",Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);
                    //              swipeRefreshLayout.setRefreshing(false);
                    // progressDialog.dismiss();
                }, error -> {
                    progressBar.setVisibility(View.GONE);
                    //swipeRefreshLayout.setRefreshing(false);
                    //       progressDialog.dismiss();

                });queue.add(request);

    }

}
