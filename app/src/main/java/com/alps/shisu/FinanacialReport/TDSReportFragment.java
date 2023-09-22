package com.alps.shisu.FinanacialReport;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alps.shisu.Adapters.TDSReportDataAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.TdsReportGetterSetter;
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
public class TDSReportFragment extends Fragment {

    //Declaring an Spinner
    private Spinner spinner;
    String urltds;
    Config ut;
    RecyclerView recyclerView;
    //Session
    SessionManagement sessionManagement;
    SharedPreferences preferences;
    SwipeRefreshLayout swipeRefreshLayout;
    HashMap<String,String> user;
    List<TdsReportGetterSetter> listData;
    private TextView textmonthcuuoff;
    private TextView texttdsrate;
    private TextView texttdsamount;
    private TextView texttotalamount;

    TextView headertotalincome,headertdsamount;
    ProgressBar progressBar;

    AlertDialog dialog;
    public TDSReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tdsreport, container, false);
        listData=new ArrayList<>();

        // spinner=view.findViewById(R.id.cutoff);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        headertdsamount=view.findViewById(R.id.tdsamountheader);
        headertotalincome=view.findViewById(R.id.totalincomeheadertds);




       // progressBar=view.findViewById(R.id.tdsprogress);


        recyclerView=view.findViewById(R.id.tdsrecyclerview);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gm = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, true);

        recyclerView.setLayoutManager(gm);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(linearLayoutManager);

        // Session class instance
        sessionManagement = new SessionManagement(getContext());
        preferences= this.getActivity().getSharedPreferences("ALPSPref",MODE_PRIVATE);
        user= sessionManagement.getUserDetails();

        urltds= Config.url +"payoutTDS/"+ Config.merchantid +"/"+ Config.securtykey +"/"+user.get(SessionManagement.KEY_USERNAME)+"/"+user.get(SessionManagement.KEY_PASSWORD);

        Log.e(TAG, "onCreateView payoutTDS : "+urltds );
        loadRecyclerViewData();

        headertotalincome.setText("Total Income ("+user.get(SessionManagement.KEY_CURRENCY)+")");
//        headertotalincome.setText("Total Income (₹)");
        headertdsamount.setText("TDS Amt ("+user.get(SessionManagement.KEY_CURRENCY)+")");
//        headertdsamount.setText("TDS Amt (₹)");

        return view;
    }
    /**
     * This method is called when swipe refresh is pulled down
     */
    private  void loadRecyclerViewData(){
        dialog.show();
//        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, urltds, response -> {
            try {
                JSONArray array=new JSONArray(response);
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    listData.add(new TdsReportGetterSetter(
                            object.getString("transdate"),
                            object.getString("total"),
                            object.getString("tdsRate"),
                            object.getString("tds"),
                            object.getString("Status")
                    ));
                    TDSReportDataAdapter tdsReportDataAdapter=new TDSReportDataAdapter(getContext(),listData);
                    recyclerView.setAdapter(tdsReportDataAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
            //       progressBar.setVisibility(View.GONE);
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
            //progressBar.setVisibility(View.GONE);
        });queue.add(request);

    }
}


