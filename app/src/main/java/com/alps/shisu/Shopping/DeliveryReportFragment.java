package com.alps.shisu.Shopping;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryReportFragment extends Fragment {

    String pidurl="";
    Config ut;
    ProgressBar progressBar;
    Spinner stausvalue;
    EditText orderno;
    Button Searchbtn;
    TextView fromdate,todate;
    TextView drFhide,drThide;
    final ArrayList list=new ArrayList<>();
    final HashMap<Integer,String> listnewmop = new HashMap<>();
    private Calendar cal,cal2;
    String spinnervalue,order,fdate,tdate;
    CardView hidecardview;
    String v,vno;
    ImageView show,hide;
    public DeliveryReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delivery_report, container, false);
        stausvalue=(Spinner)view.findViewById(R.id.stausdr);
        orderno=(EditText)view.findViewById(R.id.drorderno);
        fromdate=(TextView) view.findViewById(R.id.indatefromdr);
        todate=(TextView)view.findViewById(R.id.indatetodr);
        Searchbtn=(Button)view.findViewById(R.id.filterbtndr);

        drFhide=(TextView) view.findViewById(R.id.fromdatahidedr);
        drThide=(TextView)view.findViewById(R.id.todatehidedr);

        hidecardview=view.findViewById(R.id.cardviewdeliveryreport);
        show=view.findViewById(R.id.opencarddeliveryreport);
        hide=view.findViewById(R.id.closecarddeliveryreport);
        hide.setOnClickListener(v -> {
            hidecardview.setVisibility(View.GONE);
            show.setVisibility(View.VISIBLE);
            hide.setVisibility(View.GONE);

        });
        show.setOnClickListener(v -> {
            hidecardview.setVisibility(View.VISIBLE);
            hide.setVisibility(View.VISIBLE);
            show.setVisibility(View.GONE);
        });
        cal = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = (view12, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        final DatePickerDialog.OnDateSetListener date2 = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelto();
        };
        fromdate.setOnClickListener(v -> new DatePickerDialog(getContext(),date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show());
        todate.setOnClickListener(v -> new DatePickerDialog(getContext(),date2,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show());
        //Periods


        status();
        setFragment(new DeliveryReportFragmentFrame());

        fromdate.setText("From Date");
        todate.setText("To Date");
        fdate=fromdate.getText().toString();
        tdate=todate.getText().toString();
        Searchbtn.setOnClickListener(v -> {
           /* if (fdate == null) {
                fromdate.setText("0");


            } else if (todate == null) {
                // tdate="0";
                // Toast.makeText(getContext(), ""+tdate, Toast.LENGTH_SHORT).show();
                todate.setText("0");
                // setFragment(new PurchasedListFramgmentframe());

            } else {
                order = orderno.getText().toString();
                fdate = fromdate.getText().toString();
                tdate = todate.getText().toString();
                setFragment(new DeliveryReportFragmentFrame());
                orderno.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }*/
        });

        return view;
    }
    private void updateLabel() {
       // String myFormat = "dd-MM-yyyy"; //In which you need put here
        String myFormat="MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        drFhide.setVisibility(View.GONE);
        fromdate.setText(sdf.format(cal.getTime()));
        fromdate.setTextColor(getResources().getColor(R.color.colorBlack));
    }
    private void updateLabelto() {
        //String myFormat = "dd-MM-yyyy"; //In which you need put here
        String myFormat="MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        drThide.setVisibility(View.GONE);
        todate.setText(sdf.format(cal.getTime()));
        todate.setTextColor(getResources().getColor(R.color.colorBlack));
    }
    public void setFragment(Fragment f){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();

        Bundle bundle=new Bundle();
        bundle.putString("ordernodr",order);
        bundle.putString("fdatedr",fdate);
        bundle.putString("tdatedr",tdate);
        bundle.putString("statusdr",spinnervalue);
        f.setArguments(bundle);
        // ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        ft.replace(R.id.framedeliveryreport,f);
        ft.commit();
    }




    private void status(){
//        url="https://api.myjson.com/bins/ge0uw";
        pidurl= Config.url +"getPayid/"+ Config.merchantid +"/"+ Config.securtykey +"/0/0";

        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, pidurl, response -> {
            try {
                JSONArray jsonArray=new JSONArray(response);
                for (int i=0;i<=jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    v=jsonObject.getString("payid");
                    //vno=jsonObject.getString("headno");

                    listnewmop.put(i,jsonObject.getString("payid"));
                    list.add(v);
                    ArrayAdapter adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
                    stausvalue.setAdapter(adapter);
                    stausvalue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnervalue=listnewmop.get(stausvalue.getSelectedItemPosition());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });queue.add(request);
    }
}
