package com.alps.shisu.NetworkManager;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.Calendar;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamListFragment extends Fragment {

    Spinner yearspinner,sidespiner,selectcutoffspiner;
    Button Applybutton;
    SwipeRefreshLayout swipeRefreshLayout;
    Config ut;

    String cuttoffsession,sides,fyear;
    CardView hidecardview;
    ImageView show,hide;
    String url="";
    AlertDialog dialog;
    final ArrayList listdataes=new ArrayList();
    SessionManagement sessionManagement;
    SharedPreferences preferences;
    ArrayList<String> years;

    public TeamListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_team_list, container, false);
        preferences=getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
        sessionManagement=new SessionManagement(getActivity().getApplicationContext());
        HashMap<String,String> user=sessionManagement.getUserDetails();
//        url=ut.url+"getPayid/"+ut.merchantid+"/"+ut.securtykey+"/0/0";
        url= Config.url +"getPayid/"+ Config.merchantid +"/"+ Config.securtykey +"/0/0";
        Log.e(TAG, "onCreateView: "+ url );

        hidecardview=v.findViewById(R.id.showhidecardtl);
        show=v.findViewById(R.id.opencardtl);
        hide=v.findViewById(R.id.closecardtl);
        dialog=new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
        hide.setOnClickListener(v12 -> {
            hidecardview.setVisibility(View.GONE);
            show.setVisibility(View.VISIBLE);
            hide.setVisibility(View.GONE);

        });
        show.setOnClickListener(v1 -> {
            hidecardview.setVisibility(View.VISIBLE);
            hide.setVisibility(View.VISIBLE);
            show.setVisibility(View.GONE);
        });

        Applybutton=v.findViewById(R.id.searchapplyteamlist);

        selectcutoffspiner=v.findViewById(R.id.selectcof_teamlist);
        // spinner load

        getspinner();
        //year spinner
        yearspinner=v.findViewById(R.id.yearsp_teamlist);

        years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2020; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        yearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fyear=adapterView.getItemAtPosition(i).toString();
                //    Toast.makeText(adapterView.getContext(),"click"+fyear,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, years);
        yearspinner.setAdapter(adapter);


      /*  //side left and right Spiiner
       // sidespiner=v.findViewById(R.id.sideRlspiner);
        List<String> leftrightlist=new ArrayList<String>();
        leftrightlist.add("L");
        leftrightlist.add("R");*/
       /* sidespiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sides=adapterView.getItemAtPosition(i).toString();
                //   Toast.makeText(adapterView.getContext(),""+sides,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> LRarrayAdapter=new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,leftrightlist);
        sidespiner.setAdapter(LRarrayAdapter);*/

        Applybutton.setOnClickListener(view -> {

           // String sspiner=String.valueOf(sidespiner.getSelectedItem());
            String yspin=String.valueOf(yearspinner.getSelectedItem());
            String sspin=String.valueOf(selectcutoffspiner.getSelectedItem());


            if (yspin.isEmpty()&&yspin.isEmpty()&&sspin.isEmpty()) {
                Toast.makeText(getContext(),"Please Select Year and Cut Of Year",Toast.LENGTH_SHORT).show();

            }
            else {
                setFragment(new TeamListFragmentFrame());
            }
        });

        return  v;
    }


    /**
     * This method is called when swipe refresh is pulled down
     */

    private void getspinner(){
        dialog.show();
        //   progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray array=new JSONArray(response);
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    String data=object.getString("payText");
                    String payid=object.getString("payid");
                    listdataes.add(payid);
                    ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,listdataes);
                    selectcutoffspiner.setAdapter(arrayAdapter);
                    // listData=new ArrayList<>();
                    selectcutoffspiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            cuttoffsession = adapterView.getItemAtPosition(i).toString();
                            //   Toast.makeText(adapterView.getContext(), "Selected:"+cuttoffsession,Toast.LENGTH_SHORT).show();

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
            //     progressBar.setVisibility(View.GONE);
        }, error -> {
            if (dialog.isShowing())
                dialog.dismiss();
            // progressBar.setVisibility(View.GONE);
        });queue.add(request);

    }
    public void setFragment(Fragment f){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();

        Bundle bundle=new Bundle();
        bundle.putString("id",cuttoffsession);
        bundle.putString("year",fyear);
        bundle.putString("rlside",sides);
        f.setArguments(bundle);
        //gotoTeamlistdatashow.putExtra("cutoffsession", cuttoffsession);
        //  gotoTeamlistdatashow.putExtra("yearsession", fyear);
        // gotoTeamlistdatashow.putExtra("RLside", sides);
        // ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        ft.replace(R.id.frameteamlist,f);
        ft.commit();
    }

}
