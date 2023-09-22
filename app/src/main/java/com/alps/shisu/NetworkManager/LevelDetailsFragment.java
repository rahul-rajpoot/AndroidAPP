package com.alps.shisu.NetworkManager;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alps.shisu.Adapters.DirectSponsorListDataAdapter;
import com.alps.shisu.Adapters.LevelDetailsAdapter;
import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.GetterSetter.DirectSponsorGetterSetter;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.modelclass.IncomeLevelDetailsItem;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LevelDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private android.app.AlertDialog dialog;
    Config ut;
    private String urlLevelDetails;
    public SessionManagement sessionManagement;
    HashMap<String, String> user;
    private RecyclerView rv_level_details;
    private final List<IncomeLevelDetailsItem> incomeLevelDetailsItemsList = new ArrayList<>();
    private float totalAmount =0;
    private TextView totalAmountValue;
    ProgressBar progressBar;
    String directsurl="";
    SharedPreferences preferences;
    RecyclerView recyclerViewDS;
    List<DirectSponsorGetterSetter> lis;

    public LevelDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LevelDetailsFragment.
     */
    public static LevelDetailsFragment newInstance(String param1, String param2) {
        LevelDetailsFragment fragment = new LevelDetailsFragment();
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
//        View view = inflater.inflate(R.layout.fragment_level_details, container, false);
        View view = inflater.inflate(R.layout.fragment_direct_sponsor, container, false);


        lis=new ArrayList<>();

        progressBar=view.findViewById(R.id.directsponsorprogress);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

        recyclerViewDS=view.findViewById(R.id.recyclerview_DS);
        recyclerViewDS.setHasFixedSize(true);
        recyclerViewDS.setLayoutManager(new LinearLayoutManager(this.getContext()));


//        TextView business_text = view.findViewById(R.id.business_text);
//        TextView income_text = view.findViewById(R.id.income_text);
//        totalAmountValue = view.findViewById(R.id.totalAmountValue);
//        totalAmountValue = view.findViewById(R.id.totalAmountValue);
//        rv_level_details = view.findViewById(R.id.rv_level_details);
//        business_text.setText("Business ("+user.get(SessionManagement.KEY_CURRENCY)+")");
//        income_text.setText("Income ("+user.get(SessionManagement.KEY_CURRENCY)+")");

        sessionManagement = new SessionManagement(getContext());
        sessionManagement.checkLogin();

        user = sessionManagement.getUserDetails();

//        urlLevelDetails = Config.url +"LevelDetails/" + Config.merchantid +"/"+ Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
        urlLevelDetails = Config.url +"MyStructure/" + Config.merchantid +"/"+ Config.securtykey + "/" + user.get(SessionManagement.KEY_USERNAME) + "/" + user.get(SessionManagement.KEY_PASSWORD);
//        getLevelDetails();
        getdata();

        return view;
    }

    private void getdata() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, directsurl,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            lis.add(new DirectSponsorGetterSetter(
                                    object.getString("fullName"),
                                    object.getString("Loginid"),
                                    object.getString("Package"),
                                    object.getString("regDate"),
                                    object.getString("regNo"),
                                    object.getString("Status"),
                                    object.getString("SponsorCount"),
                                    object.getString("Curr_GRBV"),
                                    object.getString("Curr_PRBV"),
                                    object.getString("Curr_TRBV"),
                                    object.getString("Pre_GRBV"),
                                    object.getString("Pre_PRBV"),
                                    object.getString("Pre_TRBV"),
                                    object.getString("tot_GRBV"),
                                    object.getString("tot_PRBV"),
                                    object.getString("tot_TRBV"),
                                    object.getString("rank"),
                                    object.getString("slab")
                            ));
                            DirectSponsorListDataAdapter data = new DirectSponsorListDataAdapter(getContext(), lis);
                            recyclerViewDS.setAdapter(data);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (dialog.isShowing())
                        dialog.dismiss();

                }, error -> {
            //  Toast.makeText(getContext(),"An error has occurred.",Toast.LENGTH_LONG).show();
            //progressDialog.dismiss();
            // progressBar.setVisibility(View.GONE);
            if (dialog.isShowing())
                dialog.dismiss();
            //   Toast.makeText(getContext(),"An error has occurred.",Toast.LENGTH_LONG).show();
            //  swipeRefreshLayout.setRefreshing(false);
        });requestQueue.add(stringRequest);
    }

//    private void getLevelDetails() {
//        dialog.show();
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        StringRequest request = new StringRequest(Request.Method.GET, urlLevelDetails, response -> {
//            try {
//                JSONArray jsonArray = new JSONArray(response);
//                for (int i = 0; i < jsonArray.length() ; i++) {
//
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String levels = jsonObject.getString("levels");
//                    String business = jsonObject.getString("Business");
//                    String levelIncome = jsonObject.getString("levelIncome");
//                    String rate = jsonObject.getString("paidPV");
//                    String noOfMembers = jsonObject.getString("mCount");
//
//                    IncomeLevelDetailsItem incomeLevelDetailsItem = new IncomeLevelDetailsItem();
//                    incomeLevelDetailsItem.setIncome(levelIncome);
//                    incomeLevelDetailsItem.setBusiness(business);
////                        incomeLevelDetailsItem.setLevel("Level "+levels);
//                    incomeLevelDetailsItem.setLevel(levels);
//                    incomeLevelDetailsItem.setRate(rate);
//                    incomeLevelDetailsItem.setNoOfMembers(noOfMembers);
//
//                    incomeLevelDetailsItemsList.add(incomeLevelDetailsItem);
//
//                    totalAmount = totalAmount+ Float.parseFloat(levelIncome);
//
//                }
//
//                LevelDetailsAdapter DataAdapter=new LevelDetailsAdapter(getContext(),incomeLevelDetailsItemsList);
//                rv_level_details.setLayoutManager(new LinearLayoutManager(getContext()));
//                rv_level_details.setAdapter(DataAdapter);
//
//                totalAmountValue.setText(user.get(SessionManagement.KEY_CURRENCY)+""+totalAmount);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            if (dialog.isShowing())
//                dialog.dismiss();
//        }, error -> {
//            if (dialog.isShowing())
//                dialog.dismiss();
//        });
//        queue.add(request);
//
//    }

}