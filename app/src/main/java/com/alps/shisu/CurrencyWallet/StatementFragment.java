package com.alps.shisu.CurrencyWallet;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.Adapters.StatementListDataAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.StatementGetterSetter;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatementFragment extends Fragment {
    RecyclerView recyclerViewTH;
    TextView AvlAmt,LedgerAmt;
    private HashMap<String,String> user;
    SharedPreferences preferences;
    Config ut;
    String urlTH="";
    String urlAvl="";
    List<StatementGetterSetter> listes;
    //ProgressDialog progressDialog;
    ProgressBar progressBar;
    AlertDialog dialog;
   public  RelativeLayout relativeLayoutstatement;
    public StatementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View de= inflater.inflate(R.layout.fragment_statement, container, false);
        AvlAmt=de.findViewById(R.id.amt);
//        LedgerAmt=de.findViewById(R.id.legamt);
        relativeLayoutstatement=de.findViewById(R.id.statement);
        listes=new ArrayList<>();
        // Session class instance
        SessionManagement sessionManagement = new SessionManagement(getContext());
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

        preferences=this.getActivity().getSharedPreferences("ALPSPref",MODE_PRIVATE);
        user= sessionManagement.getUserDetails();

//        AvlAmt.setText(user.get(sessionManagement.KEY_AVAILABLE_BALANCE));
//        LedgerAmt.setText(user.get(sessionManagement.KEY_LEDGER_BALANCE));

        urlAvl= Config.url +"getWallet/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);
        urlTH= Config.url +"getTransactionhistory/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);

        Log.e(TAG, "onCreateView getWallet : "+ urlAvl );
        Log.e(TAG, "onCreateView getTransactionhistory : "+ urlTH );

        //    Remarks=de.findViewById(R.id.Remark);
//    Amts=de.findViewById(R.id.Amount);
//        progressDialog=new ProgressDialog(getActivity());
//        progressDialog.setCancelable(true);
//        Dates=de.findViewById(R.id.date);
        recyclerViewTH=de.findViewById(R.id.tranactionhistory_recyclerview);
        recyclerViewTH.setHasFixedSize(true);
        recyclerViewTH.setLayoutManager(new LinearLayoutManager(this.getContext()));

//
//        swipeRefreshLayout=de.findViewById(R.id.statementrefresh);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_blue_dark);
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//                getdata();
//            }
//        });


        progressBar=de.findViewById(R.id.stataementprogress);

        getdata();
//getdata();
        return de;
    }

    private  void getdata(){
        dialog.show();
        //progressBar.setVisibility(View.VISIBLE);
        RequestQueue r= Volley.newRequestQueue(getContext());
        StringRequest requests=new StringRequest(Request.Method.GET, urlTH,
                response -> {
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            listes.add(new StatementGetterSetter(
                                    jsonObject.getString("remark"),
                                    jsonObject.getString("Transamount"),
                                    jsonObject.getString("Transdate"),
                                    jsonObject.getString("Status")

                            ));
                            Collections.reverse(listes);
                            StatementListDataAdapter DataAdapter=new StatementListDataAdapter(getContext(),listes);
                            recyclerViewTH.setAdapter(DataAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (dialog.isShowing())
                        dialog.dismiss();
                   // progressBar.setVisibility(View.GONE);
                    // swipeRefreshLayout.setRefreshing(false);
                    //          progressDialog.dismiss();
                }, error -> {
                    //progressDialog.dismiss();
                  //  progressBar.setVisibility(View.GONE);
                    if (dialog.isShowing())
                        dialog.dismiss();
                    //   swipeRefreshLayout.setRefreshing(false);
                });r.add(requests);



//progressBar.setVisibility(View.VISIBLE);
//swipeRefreshLayout.setRefreshing(true);
        //json
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, urlAvl, response -> {
            try {
                JSONArray array=new JSONArray(response);
                JSONObject object=array.getJSONObject(0);
                AvlAmt.setText(""+user.get(SessionManagement.KEY_CURRENCY)+object.getString("Available_balance"));
               // LedgerAmt.setText(object.getString("Ledger_balance"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  progressBar.setVisibility(View.GONE);
            //            swipeRefreshLayout.setRefreshing(false);
            //          progressDialog.dismiss();
        }, error -> {
            //progressDialog.dismiss();
            //     progressBar.setVisibility(View.GONE);
            //    swipeRefreshLayout.setRefreshing(false);
        });requestQueue.add(request);

    }
}
