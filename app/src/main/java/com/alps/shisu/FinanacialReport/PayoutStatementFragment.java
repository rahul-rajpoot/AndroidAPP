package com.alps.shisu.FinanacialReport;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import java.util.ArrayList;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayoutStatementFragment extends Fragment {

    Spinner spinner,yearspinner;
    Button Searchbtn;
    SessionManagement sessionManagement;
    HashMap<String,String> user;
    String URLPAYID="";
    Config ut;
    ProgressBar progressBar;
    String payidname,payid,yearname,yearid="0";
    final ArrayList list=new ArrayList<>();
    HashMap<Integer,String> listnewmop = new HashMap<>();

    CardView hidecardview;
    Animation animBounce;
    ImageView show,hide;
    AlertDialog dialog;
    public PayoutStatementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payout_statement, container, false);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        user=sessionManagement.getUserDetails();
        spinner=(Spinner)view.findViewById(R.id.payoutstatementspinner);

//        URLPAYID=ut.url+"getPayid/"+ut.merchantid+"/"+ut.securtykey+"/0/0";
        URLPAYID= Config.url +"getPayid/"+ Config.merchantid +"/"+ Config.securtykey +"/0/0";
        Log.e(TAG, "onCreateView getPayid : "+ URLPAYID );
      //  URLPAYID=ut.url+"getPayid/"+ut.merchantid+"/"+ut.securtykey+"0/0";

        Searchbtn=(Button)view.findViewById(R.id.searchbtnps);
        progressBar=view.findViewById(R.id.framepayoutreportprogress);

        hidecardview=view.findViewById(R.id.cardviewpayoutstatement);
        show=view.findViewById(R.id.opencardps);
        hide=view.findViewById(R.id.closecardps);

        animBounce = AnimationUtils.loadAnimation(getContext(),
                R.anim.bounce);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        hide.setOnClickListener(v -> {
            hidecardview.setVisibility(View.GONE);
            show.setVisibility(View.VISIBLE);
           // hidecardview.startAnimation(animBounce);
            hide.setVisibility(View.GONE);

        });
        show.setOnClickListener(v -> {
          //  hidecardview.startAnimation(animBounce);
            hidecardview.setVisibility(View.VISIBLE);
            hide.setVisibility(View.VISIBLE);
            show.setVisibility(View.GONE);
        });
        Searchbtn.setOnClickListener(v -> setFragment(new PayoutStatementFragmentFrame()));
        loadspiinervalue();
        return view;
    }

    private void loadspiinervalue() {
        dialog.show();
        //progressBar.setVisibility(View.VISIBLE);
       // progressBar.setVisibility(View.VISIBLE);
        // spinner load
        //    swipeRefreshLayout.setRefreshing(true);
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, URLPAYID, response -> {
            try {
                JSONArray array=new JSONArray(response);
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    String data=object.getString("payid");
                    list.add(data);
                    ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,list);
                    spinner.setAdapter(arrayAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            payid=adapterView.getItemAtPosition(i).toString();
                      }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
         //   progressBar.setVisibility(View.GONE);
            //              swipeRefreshLayout.setRefreshing(false);
        }, error -> {
//swipeRefreshLayout.setRefreshing(false);
            if (dialog.isShowing())
                dialog.dismiss();
            // progressBar.setVisibility(View.GONE);
        });queue.add(request);


    }
    public void setFragment(Fragment f){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();

        Bundle bundle=new Bundle();
        bundle.putString("id",payid);
        f.setArguments(bundle);
        // ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        ft.replace(R.id.framepayoutstatement,f);
        ft.commit();
    }
}
