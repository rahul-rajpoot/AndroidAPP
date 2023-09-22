package com.alps.shisu.FinanacialReport;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayoutStatementFragmentFrame extends Fragment {

    SessionManagement sessionManagement;
    HashMap<String,String> user;
    String PayoutstatmentURL="";
    Config ut;
    ProgressBar progressBar;
    AlertDialog dialog;
    final String decution="TDS @( 5.00 %) of";
    String payidget;
    TextView tdsof;
    //Personal details textview
    TextView Name,username,address,MobileNo,Payid,dateto,datefrom;
    //income
    TextView directreferral,pairmatchingincome,DirectSponsorsMatchingRoyalty,Totalincome;
    //Deduction
    TextView TDS,processingcharge,totalincomededuction;

    //Group A rows values id here
    TextView bfbvA,currentbvA,totalbvA,matchedbvA,paidbvA,flushbvA,cfbvA;
    //Header Ids
    TextView grooupa,groupb,incomeh,deductionh;
    TextView netincome;
    //Group B rows values id here
    TextView  bfbvB,currentbvB,totalbvB,matchedbvB,paidbvB,flushbvB,cfbvB;

    public PayoutStatementFragmentFrame() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payout_statement_fragment_frame, container, false);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        user=sessionManagement.getUserDetails();
        Bundle bundle=getArguments();
        payidget=bundle.getString("id");

        progressBar=view.findViewById(R.id.frameprprogress);

        /*Payid=view.findViewById(R.id.SP_payid);
        // dateto=findViewById(R.id.SP_dateto);
        // datefrom=findViewById(R.id.SP_datefrom);
        tdsof=view.findViewById(R.id.td);
        //personal details id
        Name=view.findViewById(R.id.ps_name);
        username=view.findViewById(R.id.ps_username);
        address=view.findViewById(R.id.ps_address);
        MobileNo=view.findViewById(R.id.ps_mobile);*/
        //income  value id
        directreferral=view.findViewById(R.id.direcrt_referrai);
        pairmatchingincome=view.findViewById(R.id.pairmatchingincome);
        DirectSponsorsMatchingRoyalty=view.findViewById(R.id.directsponcormatchingroyality);
        Totalincome=view.findViewById(R.id.totalincomeincome);
        //Deduction value id
        TDS=view.findViewById(R.id.tds_ps);
        processingcharge=view.findViewById(R.id.pc_ps);
        totalincomededuction=view.findViewById(R.id.tdamt);

        //group a id value
        bfbvA=view.findViewById(R.id.ps_A_bfbv);
        currentbvA=view.findViewById(R.id.ps_A_curbv);
        totalbvA=view.findViewById(R.id.ps_A_total_bv);
        matchedbvA=view.findViewById(R.id.ps_A_matched_bv);
        paidbvA=view.findViewById(R.id.ps_A_paid_bv);
        flushbvA=view.findViewById(R.id.ps_A_flush_bv);
        cfbvA=view.findViewById(R.id.ps_A_cf_bv);
        //group B id Value Fetch
        bfbvB=view.findViewById(R.id.ps_B_bfbv);
        currentbvB=view.findViewById(R.id.ps_B_curbv);
        totalbvB=view.findViewById(R.id.ps_B_total_bv);
        matchedbvB=view.findViewById(R.id.ps_B_matched_bv);
        paidbvB=view.findViewById(R.id.ps_B_paid_bv);
        flushbvB=view.findViewById(R.id.ps_B_flush_bv);
        cfbvB=view.findViewById(R.id.ps_B_cf_bv);

        netincome=view.findViewById(R.id.ps_netincome);
        PayoutstatmentURL= Config.url +"getPayoutStatement/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+payidget;

        Log.e(TAG, "onCreateView getPayoutStatement : "+PayoutstatmentURL );
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        loadData();
        return view;
    }

    private void loadData(){
//swipeRefreshLayout.setRefreshing(true);
       dialog.show();
        // progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, PayoutstatmentURL, response -> {

            try {
                JSONArray array=new JSONArray(response);
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);

                    String payiddate=payidget+"-"+object.getString("payFrom")+"-"+object.getString("payTo");
                    Payid.setText(payiddate);
                    String uname=object.getString("Name");
                    Name.setText(uname);
                    String usernamees=object.getString("Loginid");
                    username.setText(usernamees);

                    //   String add=object.getString("Address")+object.getString("district")
                    //         +object.getString("ocity")+object.getString("ostate")+object.getString("pinCode");

                    String add=object.getString("Address");
                    address.setText(add);

                    String ph=object.getString("mobile");
                    MobileNo.setText(ph);


                    String bfleft=object.getString("bfLeft");
                    bfbvA.setText(bfleft);
                    String bfright=object.getString("bfRight");
                    bfbvB.setText(bfright);

                    String curentbvleft=object.getString("currentLeft");
                    currentbvA.setText(curentbvleft);
                    String curentbvright=object.getString("currentRight");
                    currentbvB.setText(curentbvright);

                    String totalbvleft=object.getString("totalLeft");
                    totalbvA.setText(totalbvleft);
                    String totalbvright=object.getString("totalRight");
                    totalbvB.setText(totalbvright);

                    String matchedbv=object.getString("matched");
                    matchedbvA.setText(matchedbv);
                    matchedbvB.setText(matchedbv);

                    String paidpv=object.getString("paidPV");
                    paidbvA.setText(paidpv);
                    paidbvB.setText(paidpv);

                    String flushbv=object.getString("flushPV");
                    flushbvA.setText(flushbv);
                    flushbvB.setText(flushbv);

                    String cfleft=object.getString("cfLeft");
                    cfbvA.setText(cfleft);

                    String cfright=object.getString("cfRight");
                    cfbvB.setText(cfright);


//Income table
                    String dr=object.getString("levelIncome");
                    directreferral.setText(dr);

                    String PMI=object.getString("pvIncome");
                    pairmatchingincome.setText(PMI);
                    String DSMR=object.getString("singleIncome");
                    DirectSponsorsMatchingRoyalty.setText(DSMR);
                    String totincome=object.getString("TotIncome");
                    Totalincome.setText(totincome);
                    //Deduction table

                    String colum=decution+" "+object.getString("TotIncome");
                    tdsof.setText(colum);

                    String tdsamount=object.getString("TDSAmount");
                    TDS.setText(tdsamount);

                    String procharge=object.getString("HCAmount");
                    processingcharge.setText(procharge);

                    String totdeduction=object.getString("TotDeduction");
                    totalincomededuction.setText(totdeduction);




                    String netincomes=object.getString("NetIncome");
                    netincome.setText(netincomes);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
            //  progressBar.setVisibility(View.GONE);
            //    swipeRefreshLayout.setRefreshing(false);
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
         //   progressBar.setVisibility(View.GONE);
            //swipeRefreshLayout.setRefreshing(false);
        });requestQueue.add(request);

    }
}
