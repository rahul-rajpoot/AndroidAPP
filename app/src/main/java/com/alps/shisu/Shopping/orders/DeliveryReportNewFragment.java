package com.alps.shisu.Shopping.orders;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.adapterclass.OrdersListItemAdapter;
import com.alps.shisu.modelclass.OrderItemDetails;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryReportNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryReportNewFragment extends Fragment  implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String urls = "";
    private Config ut;
    private GridView ordersListGrid;
    //    private String status ="-1";
    private String fromDate ="0";
    private String toDate ="0";
    private final List<OrderItemDetails> orderItemDetailsList = new ArrayList<>();

    private TextView _fromDate;
    private TextView _toDate;
    private RadioButton _pendingBtn, _confirmedBtn, _rejectedButton;
    private RadioGroup _orderStatusRadioGroup;
    private RelativeLayout _no_record_found_lay;

    private String selectedStatus="-1";
    private String userName;
    private DatePickerDialog picker;

    public DeliveryReportNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeliveryReportFragment.
     */
    public static DeliveryReportNewFragment newInstance(String param1, String param2) {
        DeliveryReportNewFragment fragment = new DeliveryReportNewFragment();
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
        View view = inflater.inflate(R.layout.fragment_delivery_report2, container, false);

        initializeViews(view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);
//        userName = sharedPreferences.getString("name", "");
        userName = sharedPreferences.getString("uid", "");
        Log.e(TAG, "onCreateView: "+ userName );

//      userName = "akumar";


        _orderStatusRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // This will get the radiobutton that has changed in its check state
            RadioButton checkedRadioButton = group.findViewById(checkedId);
            // This puts the value (true/false) into the variable
            boolean isChecked = checkedRadioButton.isChecked();
            // If the radiobutton that has changed in check state is now checked...
            if (isChecked)
            {
                // Changes the textview's text to "Checked: example radiobutton text"
                String selectedType = checkedRadioButton.getText().toString();
                Log.e(TAG, "onCheckedChanged: "+ selectedType);
                if(selectedType.equalsIgnoreCase("All")){
                    selectedStatus = "-1";
                    callOnApplyButtonClick();
                } else if(selectedType.equalsIgnoreCase("In Transit")){
                    selectedStatus = "0";
                    callOnApplyButtonClick();
                } else if(selectedType.equalsIgnoreCase("Dispatched")){
                    selectedStatus = "1";
                    callOnApplyButtonClick();
                } else if(selectedType.equalsIgnoreCase("Delivered")){
                    selectedStatus = "2";
                    callOnApplyButtonClick();
                }
            }
        });

        callOnApplyButtonClick();

        return view;
    }

    private void initializeViews(View view){

        _no_record_found_lay = view.findViewById(R.id.no_record_found_lay);
        TextView _orderNoAlone = view.findViewById(R.id.orderNoAlone);
        ordersListGrid = view.findViewById(R.id.order_list_grid);
        _fromDate = view.findViewById(R.id.fromDate);
        _fromDate.setOnClickListener(this);
        _toDate = view.findViewById(R.id.toDate);
        _toDate.setOnClickListener(this);
        LinearLayout _fromDateLL = view.findViewById(R.id.fromDateLL);
        LinearLayout _toDateLL = view.findViewById(R.id.toDateLL);
        TextView _applyFilter = view.findViewById(R.id.apply_filter);
        _applyFilter.setOnClickListener(this);
        TextView _cancel = view.findViewById(R.id.cancel);
        _orderStatusRadioGroup = view.findViewById(R.id.orderStatusRadioGroup);

    }

    /**
     *
     */
    private void setFromDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getActivity(),
                (view, year1, monthOfYear, dayOfMonth) -> {
//                        _fromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    _fromDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1);
                    fromDate = (_fromDate.getText().toString()).replaceAll("/","-");
                }, year, month, day);
        picker.getDatePicker().setMaxDate(new Date().getTime());
        picker.show();
    }

    /**
     *
     */
    private void setToDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getActivity(),
                (view, year1, monthOfYear, dayOfMonth) -> {
//                       _toDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    _toDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1);
                    toDate = (_toDate.getText().toString()).replaceAll("/","-");
                }, year, month, day);
        picker.getDatePicker().setMaxDate(new Date().getTime());
        picker.show();
    }


    private void callOnApplyButtonClick(){

        //        urls = ut.url + "Orderlist/" + ut.merchantid + ut.securtykey + "/"+userName+"/Purchase/"+selectedStatus+"/"+fromDate+"/"+toDate;
        urls = Config.url + "Orderlist/" + Config.merchantid +"/"+ Config.securtykey + "/"+userName+"/Delivery/"+selectedStatus+"/"+fromDate+"/"+toDate;
//        https://api.goldways.in/DeveloperApi/getOrder/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/AADNYA/Delivery/0/0/0
        Log.e(TAG, "callOnApplyButtonClick: "+ urls );

        getOrdersList();
    }

    private void getOrdersList() {
        //dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                response -> {
                    try {
                        JSONArray object = new JSONArray(response);
                        orderItemDetailsList.clear();
                        for (int i = 0; i < object.length(); i++) {
                            JSONObject jsonObject = object.getJSONObject(i);
                            //   Boolean b=object.getBoolean("error");
                            _no_record_found_lay.setVisibility(View.GONE);
                            ordersListGrid.setVisibility(View.VISIBLE);

                            String orderNo = jsonObject.getString("OrderNo");
                            String orderDate = jsonObject.getString("OrderDate");
                            String invNO = jsonObject.getString("InvNO");
                            String invDate = jsonObject.getString("InvDate");
                            String amount = jsonObject.getString("Amount");
                            String qty = jsonObject.getString("Qty");
                            String bV = jsonObject.getString("BV");
                            String pV = jsonObject.getString("PV");
                            String status = jsonObject.getString("Status");
                            String orderurl = jsonObject.getString("Orderurl");
                            String invurl = jsonObject.getString("Invurl");
                            String statusColor = jsonObject.getString("StatusColor");

                            OrderItemDetails orderItemDetails = new OrderItemDetails();
                            orderItemDetails.setOrderNo(orderNo);
                            orderItemDetails.setOrderDate(orderDate);
                            orderItemDetails.setInvNO(invNO);
                            orderItemDetails.setInvDate(invDate);
                            orderItemDetails.setAmount(amount);
                            orderItemDetails.setQty(qty);
                            orderItemDetails.setbV(bV);
                            orderItemDetails.setpV(pV);
                            orderItemDetails.setStatus(status);
                            orderItemDetails.setOrderurl(orderurl);
                            orderItemDetails.setInvurl(invurl);
                            orderItemDetails.setStatusColor(statusColor);

                            orderItemDetailsList.add(orderItemDetails);

                        }
                        if (orderItemDetailsList.size() > 0) {

                            Collections.sort(orderItemDetailsList, new Comparator<OrderItemDetails>() {
                                //                                DateFormat f = new SimpleDateFormat("dd/MM/yyyy '@'hh:mm a");
                                DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                                //                                DateFormat f = new SimpleDateFormat("dd/mm/yyyy");
                                @Override
                                public int compare(OrderItemDetails lhs, OrderItemDetails rhs) {
                                    try {
                                        return f.parse(lhs.getOrderDate()).compareTo(f.parse(rhs.getOrderDate()));
                                    } catch (ParseException e) {
                                        throw new IllegalArgumentException(e);
                                    }
                                }
                            });
//                            Collections.sort(orderItemDetailsList, new Comparator<OrderItemDetails>() {
//                                @Override
//                                public int compare(final OrderItemDetails object1, final OrderItemDetails object2) {
//                                    return object1.getOrderDate().compareTo(object2.getOrderDate());
//                                }
//                            });
                        }


                        Collections.reverse(orderItemDetailsList);

//                        for (OrderItemDetails order:
//                                orderItemDetailsList) {
//                            Log.e(TAG, "getOrdersList: "+ order.getOrderDate() );
//                        }

                        new Handler().postDelayed(this::setAdapter, 1000);

//                        OrdersListItemAdapter adapter = new OrdersListItemAdapter(getActivity(), orderItemDetailsList);
//                        ordersListGrid.setAdapter(adapter);
//                        ordersListGrid.setOnItemClickListener((parent, view, position, id) -> {
////                                    ((MainActivity)getActivity()).showProductList("Orders", orderItemDetailsList.get(position).getCategoryId());
//                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        ordersListGrid.setVisibility(View.GONE);
                        _no_record_found_lay.setVisibility(View.VISIBLE);
                    }   /*if (dialog.isShowing())
                        dialog.dismiss();*/

                }, error -> {
    //                Snackbar snackbar=Snackbar.make(loginpage,""+error,Snackbar.LENGTH_LONG);
    //                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
    //                View snackbarView = snackbar.getView();
    //                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
    //                snackbar.show();
                    //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                   /* if (dialog.isShowing())
                        dialog.dismiss();*/
                }) {
            protected Map<String, String> getParams() {
                //                params.put("username",usname);
//                params.put("pass",userspass);
                return new HashMap<>();
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setAdapter(){
        OrdersListItemAdapter adapter = new OrdersListItemAdapter(getActivity(), orderItemDetailsList);
        ordersListGrid.setAdapter(adapter);
        ordersListGrid.setOnItemClickListener((parent, view, position, id) -> {
//                                    ((MainActivity)getActivity()).showProductList("Orders", orderItemDetailsList.get(position).getCategoryId());
        });
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.apply_filter:
                callOnApplyButtonClick();
                break;
            case R.id.cancel:
                break;
            case R.id.fromDate:
                setFromDate();
                break;
            case R.id.toDate:
                setToDate();
                break;
            case R.id.arrow:
                setFromDate();
                break;
            case R.id.calender:
                setToDate();
                break;
        }
    }
}