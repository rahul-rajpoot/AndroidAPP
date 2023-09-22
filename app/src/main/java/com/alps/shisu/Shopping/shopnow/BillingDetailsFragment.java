package com.alps.shisu.Shopping.shopnow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.alps.shisu.adapterclass.AddToCartadapter;
import com.alps.shisu.adapterclass.MySpinnerAdapter;
import com.alps.shisu.db.local.RoomDBRepository;
import com.alps.shisu.db.local.entity.ProductsDetails;
import com.alps.shisu.modelclass.StateDetails;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BillingDetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String P_ID = "pId";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "BillingDetailsFragment";

    private TextView updateCart;
    private TextView subTotalAmount;
    private TextView discount;
    private TextView totalAmount;
//    private TextView totalWeight;
    private TextView grandTotal;

    private int quantityValue = 0;
    private float newMrp =0;
    private float newWeight;
    private HashMap<String, String> user;

    //  private ScrollView scrollView;
    private NestedScrollView scrollView;
    private EditText address, city, pinCode, mobileNo, email;
    private TextView selectStateTV;
    private LinearLayout selectState, stateLL, districtLL, cityLL;
    private Spinner stateSpinner, district_spinner, city_spinner;
    private final String urls = "";
    private String stateId;
    private String stateName;
    private String districId;
    private String districtName;
    private String cTID;
    private String cityName;
    private String pinCodeStr;
    private String addressStr;
    private String mobileNumberStr;
    private String emailStr;
    private String distic;
    private Config ut;
    private final ArrayList<String> stateItemArrayList = new ArrayList<>();
    private final ArrayList<String> districtItemArrayList = new ArrayList<>();
    private final ArrayList<String> cityItemArrayList = new ArrayList<>();
    private final HashMap<String, String> stateHashMap = new HashMap<>();
    private final HashMap<String, String> districtHashMap = new HashMap<>();
    private final HashMap<String, String> cityHashMap = new HashMap<>();
    private final HashMap<String, StateDetails> finalStateDetails = new HashMap<>();
    private LinearLayout _addAddressRoot;
    private final ArrayList<String> quantityArrayList = new ArrayList<>();
//    private final ArrayList<String> quantityValueList = new ArrayList<>();
    private LinearLayout billingDetailsRoot;
    private int position;
    private ViewFlipper simpleViewFlipper;
    private String countryId;
    private boolean firstTimeLoaded;
    private List<ProductsDetails> productsDetailsList = new ArrayList<>();
    private final HashMap<String, Float> newMrpsList = new HashMap<>();
    private final HashMap<String, Float> newQuantityList = new HashMap<>();
    private final HashMap<String, Float> newWeightList = new HashMap<>();
    private String quantitySymbol = "";
    private boolean feildsEnabled;
    private RoomDBRepository postRoomDBRepository;

    //  public BillingDetailsFragment(List<ProductsDetails> productsDetailsList) {
    public BillingDetailsFragment() {
//        Required empty public constructor
//        this.productsDetailsList = productsDetailsList;
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param pId Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BillingDetailsFragment.
//     */
//    public static BillingDetailsFragment newInstance(String pId, String param2) {
//        BillingDetailsFragment fragment = new BillingDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(P_ID, pId);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String pId = getArguments().getString(P_ID);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_billing_details, container, false);

//        ((MainActivity) getActivity()).showDefaultLoaderFragment();

        SessionManagement sessionManagement = new SessionManagement(getContext());
        user = sessionManagement.getUserDetails();
        // Local db instance
        postRoomDBRepository = new RoomDBRepository(getActivity().getApplication());

        BillingDetailsFragment billingDetailsFragment = this;
        quantityArrayList.add("1");
        quantityArrayList.add("2");
        quantityArrayList.add("3");
        quantityArrayList.add("4");
        quantityArrayList.add("5");
        quantityArrayList.add("6");
        quantityArrayList.add("7");
        quantityArrayList.add("8");
        quantityArrayList.add("9");
        quantityArrayList.add("10");
        quantityArrayList.add("11");
        quantityArrayList.add("12");

        stateLL = view.findViewById(R.id.stateLL);
        districtLL = view.findViewById(R.id.districtLL);
        cityLL = view.findViewById(R.id.cityLL);
        simpleViewFlipper = view.findViewById(R.id.simpleViewFlipper);
        billingDetailsRoot = view.findViewById(R.id.billingDetailsRoot);
        scrollView = view.findViewById(R.id.scrollView);
        //  private GridView grid;
        RecyclerView cart_list = view.findViewById(R.id.cart_list);
        TextView shopMore = view.findViewById(R.id.shopMore);
        TextView checkout = view.findViewById(R.id.checkout);
//      updateCart = view.findViewById(R.id.updateCart);
        subTotalAmount = view.findViewById(R.id.subTotalAmount);
        discount = view.findViewById(R.id.discount);
        totalAmount = view.findViewById(R.id.totalAmount);
//        totalWeight = view.findViewById(R.id.totalWeight);
        grandTotal = view.findViewById(R.id.grandTotal);

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);


        //Add new address page element

        TextView submit = view.findViewById(R.id.submitButton);
        TextView cancel = view.findViewById(R.id.cancelButton);
        _addAddressRoot = view.findViewById(R.id.addAddressRoot);

        stateSpinner = view.findViewById(R.id.state_spinner);
        stateSpinner.setOnItemSelectedListener(this);

        district_spinner = view.findViewById(R.id.district_spinner);
        district_spinner.setOnItemSelectedListener(this);

        city_spinner = view.findViewById(R.id.city_spinner);
        city_spinner.setOnItemSelectedListener(this);

        address = view.findViewById(R.id.address);
        pinCode = view.findViewById(R.id.pinCode);
        mobileNo = view.findViewById(R.id.mobileNumber);
        email = view.findViewById(R.id.email);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ALPSPref", MODE_PRIVATE);

        emailStr = sharedPreferences.getString("email", "");
        mobileNumberStr = sharedPreferences.getString("MobileNo", "");
        addressStr = sharedPreferences.getString("Address", "");
        stateName = sharedPreferences.getString("State", "");
        stateId = sharedPreferences.getString("SID", "");
        pinCodeStr = sharedPreferences.getString("PinCode", "");
        districId = sharedPreferences.getString("DISTID", "");
//        countryId = sharedPreferences.getString("Countrycode","");
        countryId = sharedPreferences.getString("CID", "");
        sharedPreferences.getString("CTID", "");
        cityName = sharedPreferences.getString("City", "");
        districtName = sharedPreferences.getString("District", "");

        email.setText(emailStr);
        mobileNo.setText(mobileNumberStr);
        address.setText(addressStr);
        pinCode.setText(pinCodeStr);
//      stateSpinner.setText(addressStr);

        desableAllViews();

        simpleViewFlipper.setOnClickListener(v -> {
            if (simpleViewFlipper.getDisplayedChild() == 0) {
                simpleViewFlipper.showNext();
                feildsEnabled = true;
                enableAllViews();
            } else {
                simpleViewFlipper.showPrevious();
                feildsEnabled = false;
                desableAllViews();
            }
        });


        shopMore.setOnClickListener(v -> {
//                Config.showComingSoonPopupMessage(getActivity(), 0, 0, "Comming Soon", getResources().getString(R.string.message), false);
            ((ShoppingActivity) getActivity()).removeAllFragments();
        });

        checkout.setOnClickListener(v -> {
            _addAddressRoot.setVisibility(View.VISIBLE);
            scrollToViewTop(scrollView, _addAddressRoot);
        });

//        updateCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Config.showComingSoonPopupMessage(getActivity(), 0, 0, "Comming Soon", getResources().getString(R.string.message), false);
//            }
//        });


        productsDetailsList = postRoomDBRepository.getAllProductsDetails(user.get(SessionManagement.KEY_USERNAME));

//        for (ProductsDetails productsDetails1 :
//                productsDetailsList) {
//            newMrpsList.add(Float.parseFloat(productsDetails1.getMrp()));
//
//        }

//        if (productsDetailsList != null && productsDetailsList.size() > 0) {
//            int lastIndex = productsDetailsList.size() - 1;
//            position = getQuanityPosition("" + productsDetailsList.get(lastIndex).getQuantity());
//        }

//      AddToCartadapter adapter = new AddToCartadapter(getContext(), getActivity(), billingDetailsFragment, productsDetailsList, quantityArrayList, position);
//      AddToCartadapter adapter = new AddToCartadapter(getContext(), getActivity(), billingDetailsFragment, quantityArrayList, position);
//      grid.setAdapter(adapter);
//
        AddToCartadapter adapter = new AddToCartadapter(getContext(), getActivity(), billingDetailsFragment, quantityArrayList, position, productsDetailsList);
        cart_list.setLayoutManager(new GridLayoutManager(getContext(), 1));
        cart_list.setAdapter(adapter);

//Commented by me
        updateCartValuesAndQuantities();


//        ((ShoppingActivity) getActivity()).saveProductItemInCart(productsDetailsList);

        getStateList();

        submit.setOnClickListener(v -> {
            addressStr = address.getText().toString();
            pinCodeStr = pinCode.getText().toString();
            mobileNumberStr = mobileNo.getText().toString();
            emailStr = email.getText().toString();
            emailStr = email.getText().toString();

            if (checkValidation()) {
                if (!addressStr.isEmpty()) {
                    ((ShoppingActivity) getActivity()).showMakePaymentPage("Payment", finalStateDetails.get(cityName), cityName, mobileNumberStr, addressStr, productsDetailsList, newWeight, newMrp, quantityValue);
//                        checkForAvaialbleFund(sharedPreferences.getString("AcBalance", ""), "1", ""+newWeight, ""+newMrp, "false");
                }
            }

        });

        cancel.setOnClickListener(v -> showPopup(getContext(), "Are You sure want to cancel this order?"));

        firstTimeLoaded = true;

        new Handler().postDelayed(this::setTotalPrice, 1000);

        return view;
    }

    public void updateCartValuesAndQuantities(){

        int indexValue = 0;
        for (ProductsDetails productsDetails1 :
                productsDetailsList) {
//          ProductsDetails productsDetails1 = productsDetailsList.get(0);
//          updateCartValue(productsDetails1, "1");
            updateCartValue(productsDetails1, "" + productsDetails1.getQuantity(), indexValue);
//          quantityValueList.add(productsDetails1.getQuantity());
            indexValue++;
        }
    }


    public void setTotalPrice(){

//      subTotalAmount.setText("₹ " + String.format("%.2f", newMrp));
        float totalMrp = 0, weight = 0;
        int quantity = 0;
        Log.e(TAG, "updateCartValue setTotalPrice: Size : "+ newMrpsList.size() );
        Log.e(TAG, "updateCartValue setTotalPrice: Weight : "+ newWeightList.size() );
        for (int i = 0; i < productsDetailsList.size(); i++) {
            String lastProductId = "0";
            ProductsDetails productsDetails = productsDetailsList.get(i);
            for (int j = 0; j < newMrpsList.size(); j++) {
                Log.e(TAG, "updateCartValue setTotalPrice: " + j + " " + newMrpsList.get(productsDetails.getProduct_id()));
                if(!lastProductId.equalsIgnoreCase(productsDetails.getProduct_id())) {
                    totalMrp = totalMrp + newMrpsList.get(productsDetails.getProduct_id());
                    Log.e(TAG, "updateCartValue setTotalPrice: " + totalMrp);
                }
                lastProductId = productsDetails.getProduct_id();
            }
        }
        newMrp = totalMrp;

        for (int i = 0; i < productsDetailsList.size(); i++) {
            String lastProductId = "0";
            ProductsDetails productsDetails = productsDetailsList.get(i);
            for (int j = 0; j < newWeightList.size(); j++) {
                if(!lastProductId.equalsIgnoreCase(productsDetails.getProduct_id())) {
                    Log.e(TAG, "updateCartValue setTotalPrice: " + j + " " + newWeightList.get(productsDetails.getProduct_id()));
                    weight = weight + newWeightList.get(productsDetails.getProduct_id());
//                    Log.e(TAG, "updateCartValue setTotalWeight: " + weight);
                }
                lastProductId = productsDetails.getProduct_id();
            }
        }
        newWeight = weight;

//        for (int i = 0; i < productsDetailsList.size(); i++) {
//            for (int j = 0; j < quantityValueList.size(); i++) {
//                quantity = quantity + Integer.parseInt(quantityValueList.get(j));
//            }
//        }
//
//        quantityValue = quantity;

        subTotalAmount.setText(user.get(SessionManagement.KEY_CURRENCY) + " " + String.format("%.2f", newMrp));
//        discount.setText("₹ 0.00");
        discount.setText(user.get(SessionManagement.KEY_CURRENCY) + " 0.00");
//        totalAmount.setText("₹ " + String.format("%.2f", newMrp));
        totalAmount.setText(user.get(SessionManagement.KEY_CURRENCY) + " " + String.format("%.2f", newMrp));
//        totalWeight.setText(String.format("%.2f", newWeight) + " " + quantitySymbol);
//        grandTotal.setText("₹ " + String.format("%.2f", newMrp));
        grandTotal.setText(user.get(SessionManagement.KEY_CURRENCY) + " " + String.format("%.2f", newMrp));

//        Log.e(TAG, "updateCartValue: quantityValue : " + quantityValue);
        Log.e(TAG, "updateCartValue: New MRP : " + totalMrp);
        Log.e(TAG, "updateCartValue: New Weight : " + weight);

    }

    private int getSelectedStatePosition(String stateName, ArrayList<String> stateItemArrayList) {

        int index = 0;
        if (!stateName.isEmpty()) {
            for (String str :
                    stateItemArrayList) {
                if (stateName.equalsIgnoreCase(str)) {
                    return index;
                }
                index++;
            }
        }
        return 0;
    }

    private void desableAllViews() {

        mobileNo.setEnabled(false);
        disbaleEditText(mobileNo);

        email.setEnabled(false);
        disbaleEditText(email);

        stateSpinner.setEnabled(false);
        disableSatateSpinner(stateSpinner);

        district_spinner.setEnabled(false);
        disableDistrictSpinner(district_spinner);

        city_spinner.setEnabled(false);
        disableCitySpinner(city_spinner);

        address.setEnabled(false);
        disbaleEditText(address);

        pinCode.setEnabled(false);
        disbaleEditText(pinCode);

    }

    private void disbaleEditText(EditText view){
        view.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));
        view.setTextColor(getResources().getColor(R.color.gray_color));
        view.setHintTextColor(getResources().getColor(R.color.gray_color));

    }

    private void disbaleEditText(Spinner view){
        view.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));
//        view.setTextColor(getResources().getColor(R.color.gray_color));
//        view.setHintTextColor(getResources().getColor(R.color.gray_color));

    }

    private void enableEditText(EditText view){
        view.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));
        view.setTextColor(getResources().getColor(R.color.colorBlack));
        view.setHintTextColor(getResources().getColor(R.color.colorBlack));

    }

    private void enableEditText(Spinner view){
        view.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));
//        view.setTextColor(getResources().getColor(R.color.colorBlack));
//        view.setHintTextColor(getResources().getColor(R.color.colorBlack));

    }

    private void enableAllViews() {

        mobileNo.setEnabled(true);
        enableEditText(mobileNo);

        email.setEnabled(true);
        enableEditText(email);

        stateSpinner.setEnabled(true);
        enableSatateSpinner(stateSpinner);

        district_spinner.setEnabled(true);
        enableDistrictSpinner(district_spinner);

        city_spinner.setEnabled(true);
        enableCitySpinner(city_spinner);

        address.setEnabled(true);
        enableEditText(address);

        pinCode.setEnabled(true);
        enableEditText(pinCode);


    }

    private void disableSatateSpinner(Spinner spinner){
        try {

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    getContext(),
                    R.layout.disabled_view_spinner,
                    stateItemArrayList);
            adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
            spinner.setAdapter(adapter);
            stateLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));


            if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                int selectedPosition = getSelectedStatePosition(stateName, stateItemArrayList);
                if (selectedPosition > 0) {
                    spinner.setSelection(selectedPosition);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableSatateSpinner(Spinner spinner){
        try {

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    getContext(),
                    R.layout.view_spinner_item,
                    stateItemArrayList);
            adapter.setDropDownViewResource(R.layout.drop_down_item);
            spinner.setAdapter(adapter);
            stateLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));


            if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                int selectedPosition = getSelectedStatePosition(stateName, stateItemArrayList);
                if (selectedPosition > 0) {
                    spinner.setSelection(selectedPosition);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      private void disableDistrictSpinner(Spinner spinner){
        try {

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    getContext(),
                    R.layout.disabled_view_spinner,
                    districtItemArrayList);
            adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
            spinner.setAdapter(adapter);
            districtLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));


            if (!stateName.isEmpty()) {
                if (districtItemArrayList != null && districtItemArrayList.size() > 0) {
                    int selectedPosition = getSelectedStatePosition(districtName, districtItemArrayList);
                    if (selectedPosition > 0) {
                        spinner.setSelection(selectedPosition);
                        districId = districtHashMap.get(districtName);
                    }
                }
//                getCityList(stateId, districId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableDistrictSpinner(Spinner spinner){
        try {

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    getContext(),
                    R.layout.view_spinner_item,
                    districtItemArrayList);
            adapter.setDropDownViewResource(R.layout.drop_down_item);
            spinner.setAdapter(adapter);
            districtLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));

            if (!stateName.isEmpty()) {
                if (districtItemArrayList != null && districtItemArrayList.size() > 0) {
                    int selectedPosition = getSelectedStatePosition(districtName, districtItemArrayList);
                    if (selectedPosition > 0) {
                        spinner.setSelection(selectedPosition);
                        districId = districtHashMap.get(districtName);
                    }
                }
//                getCityList(stateId, districId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void disableCitySpinner(Spinner spinner){
        try {

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    getContext(),
                    R.layout.disabled_view_spinner,
                    cityItemArrayList);
            adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
            spinner.setAdapter(adapter);
            cityLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));


            if (cityItemArrayList != null && cityItemArrayList.size() > 0) {
                int selectedPosition = getSelectedStatePosition(cityName, cityItemArrayList);
                if (selectedPosition > 0) {
                    spinner.setSelection(selectedPosition);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableCitySpinner(Spinner spinner){
        try {

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    getContext(),
                    R.layout.view_spinner_item,
                    cityItemArrayList);
            adapter.setDropDownViewResource(R.layout.drop_down_item);
            spinner.setAdapter(adapter);
            cityLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));


            if (cityItemArrayList != null && cityItemArrayList.size() > 0) {
                int selectedPosition = getSelectedStatePosition(cityName, cityItemArrayList);
                if (selectedPosition > 0) {
                    spinner.setSelection(selectedPosition);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private int getQuanityPosition(String selectedValue) {

        int index = 0;
        for (String value :
                quantityArrayList) {
            if (value.equalsIgnoreCase(selectedValue)) {
                return index;
            }
            index++;
        }
        return 0;
    }

    public void scrollToViewTop(NestedScrollView scrollView, View childView) {
        long delay = 400; //delay to let finish with possible modifications to ScrollView
        scrollView.postDelayed(() -> scrollView.smoothScrollTo(0, childView.getTop()), delay);
    }

//    private void checkForAvaialbleFund(String goldBalance, String extraMargin, String weight, String Amount, String otherPayment) {
//
//        float weight1 = Float.parseFloat(weight);
//        Log.e(TAG, "checkForAvaialbleFund: " + weight1);
//        float _1Per = (weight1 / 100);
//        Log.e(TAG, "checkForAvaialbleFund: 1 % = " + _1Per);
//        float extraMarg = _1Per * Float.parseFloat(extraMargin);
//        Log.e(TAG, "checkForAvaialbleFund: extraMargin : " + extraMarg);
//        float goldValue = Float.parseFloat(goldBalance);
//        Log.e(TAG, "checkForAvaialbleFund: gold value : " + goldValue);
//
//        if (otherPayment.equalsIgnoreCase("false")) {
//            if (goldValue < (weight1 - extraMarg)) {
//                Toast.makeText(getContext(), "Don't have enough Balance to purchase any item.", Toast.LENGTH_SHORT).show();
//            } else {
//                ((ShoppingActivity) getActivity()).saveOrder(finalStateDetails.get(cityName), mobileNumberStr, addressStr, productsDetailsList, "", "", "", "", "", newWeight, newMrp);
//            }
//        } else {
//            ((ShoppingActivity) getActivity()).showMakePaymentPage("Payment", finalStateDetails.get(cityName), cityName, mobileNumberStr, addressStr, productsDetailsList, newWeight, newMrp);
//        }
//
//    }


    private void getCityList(String stateId, String districtId) {

        try {

//      String url = "https://api.goldways.in/DeveloperApi/getCity/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/96/" + stateId + "/" + districtId;
            String url = ut.url+"getCity/" + Config.merchantid + "/" + Config.securtykey + "/" + countryId + "/" + stateId + "/" + districtId;
            Log.e(TAG, "getCityList: " + url);
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            JSONArray object = new JSONArray(response);

                            finalStateDetails.clear();
                            cityItemArrayList.clear();

                            cityItemArrayList.add("Select City");

                            for (int i = 0; i < object.length(); i++) {
                                JSONObject jsonObject = object.getJSONObject(i);

                                if (jsonObject.getBoolean("Status")) {
                                    String sID = jsonObject.getString("SID");
                                    String distID = jsonObject.getString("DistID");
//                                  String distName = jsonObject.getString("DistName");
                                    String cTID = jsonObject.getString("CTID");
                                    String cityName = jsonObject.getString("CityName");
                                    pinCodeStr = jsonObject.getString("Pincode");
                                    String messageCode = jsonObject.getString("MessageCode");
                                    String message = jsonObject.getString("message");

                                    StateDetails stateDetails = new StateDetails();
                                    stateDetails.setsID(sID);
                                    stateDetails.setStateName(stateName);
                                    stateDetails.setDistID(distID);
                                    stateDetails.setDistName(districtName);
                                    stateDetails.setcTID(cTID);
                                    stateDetails.setCityName(cityName);
                                    stateDetails.setPinCode(pinCodeStr);


                                    cityItemArrayList.add(cityName);
                                    cityHashMap.put(cityName, cTID);
                                    finalStateDetails.put(cityName, stateDetails);

                                }
                            }

                            if (feildsEnabled) {

                                MySpinnerAdapter adapter = new MySpinnerAdapter(
                                        getContext(),
                                        R.layout.view_spinner_item,
                                        cityItemArrayList);
                                adapter.setDropDownViewResource(R.layout.drop_down_item);
                                city_spinner.setAdapter(adapter);
                                cityLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));


                                if (cityItemArrayList != null && cityItemArrayList.size() > 0) {
                                    int selectedPosition = getSelectedStatePosition(cityName, cityItemArrayList);
                                    if (selectedPosition > 0) {
                                        city_spinner.setSelection(selectedPosition);
                                    }
                                }
                            } else {
                                MySpinnerAdapter adapter = new MySpinnerAdapter(
                                        getContext(),
                                        R.layout.disabled_view_spinner,
                                        cityItemArrayList);
                                adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
                                city_spinner.setAdapter(adapter);
                                cityLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));


                                if (cityItemArrayList != null && cityItemArrayList.size() > 0) {
                                    int selectedPosition = getSelectedStatePosition(cityName, cityItemArrayList);
                                    if (selectedPosition > 0) {
                                        city_spinner.setSelection(selectedPosition);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> {

            }) {
                protected Map<String, String> getParams() {
                    return new HashMap<>();
                }
            };
            requestQueue.add(stringRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDistrictList(String stateCode) {

//      String url = "https://api.goldways.in/DeveloperApi/getCity/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/96/" + stateCode + "/0";
        String url = ut.url+"getCity/" + Config.merchantid + "/" + Config.securtykey + "/" + countryId + "/" + stateCode + "/0";

        Log.e(TAG, "getDistrictList: " + url);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray object = new JSONArray(response);

                        districtItemArrayList.clear();
                        districtItemArrayList.add("Select District");

                        for (int i = 0; i < object.length(); i++) {
                            JSONObject jsonObject = object.getJSONObject(i);

                            if (jsonObject.getBoolean("Status")) {
                                String sID = jsonObject.getString("SID");
                                String districId = jsonObject.getString("DistID");
                                String distName = jsonObject.getString("DistName");
                                String messageCode = jsonObject.getString("MessageCode");
                                String message = jsonObject.getString("message");

                                StateDetails stateDetails = new StateDetails();
                                stateDetails.setsID(sID);
                                stateDetails.setStateName(stateName);
                                stateDetails.setDistID(districId);
                                stateDetails.setDistName(distName);

                                districtItemArrayList.add(distName);
                                districtHashMap.put(distName, districId);

                            }
                        }

                        if(feildsEnabled) {

                            MySpinnerAdapter adapter = new MySpinnerAdapter(
                                    getContext(),
                                    R.layout.view_spinner_item,
                                    districtItemArrayList);
                            adapter.setDropDownViewResource(R.layout.drop_down_item);
                            district_spinner.setAdapter(adapter);
                            districtLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));

                            if (!stateName.isEmpty()) {
                                if (districtItemArrayList != null && districtItemArrayList.size() > 0) {
                                    int selectedPosition = getSelectedStatePosition(districtName, districtItemArrayList);
                                    if (selectedPosition > 0) {
                                        district_spinner.setSelection(selectedPosition);
                                        districId = districtHashMap.get(districtName);
                                    }
                                }
                                getCityList(stateId, districId);
                            } else {
                                getCityList(stateId, districId);
                            }
                        } else {
                            MySpinnerAdapter adapter = new MySpinnerAdapter(
                                    getContext(),
                                    R.layout.disabled_view_spinner,
                                    districtItemArrayList);
                            adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
                            district_spinner.setAdapter(adapter);
                            districtLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));

                            if (!stateName.isEmpty()) {
                                if (districtItemArrayList != null && districtItemArrayList.size() > 0) {
                                    int selectedPosition = getSelectedStatePosition(districtName, districtItemArrayList);
                                    if (selectedPosition > 0) {
                                        district_spinner.setSelection(selectedPosition);
                                        districId = districtHashMap.get(districtName);
                                    }
                                }
                                getCityList(stateId, districId);
                            } else {
                                getCityList(stateId, districId);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }   /*if (dialog.isShowing())
                        dialog.dismiss();*/

                }, error -> {

                }) {
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getStateList() {

//      String url = "https://api.goldways.in/DeveloperApi/getCity/M/H1298HyHicofI/1/H1298HyiCofi4542crTy/96/0/0";
        String url = ut.url+"getCity/" + Config.merchantid + "/" + Config.securtykey + "/" + countryId + "/0/0";
        Log.e(TAG, "getStateList: " + url);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {

                        JSONArray object = new JSONArray(response);

                        stateItemArrayList.clear();
                        stateItemArrayList.add("Select State");

                        for (int i = 0; i < object.length(); i++) {
                            JSONObject jsonObject = object.getJSONObject(i);

//                                if (jsonObject.getBoolean("Status")) {
                            String sID = jsonObject.getString("SID");
                            String stateName = jsonObject.getString("StateName");
                            String messageCode = jsonObject.getString("MessageCode");
                            String message = jsonObject.getString("message");

                            StateDetails stateDetails = new StateDetails();
                            stateDetails.setsID(sID);
                            stateDetails.setStateName(stateName);

                            stateItemArrayList.add(stateName);
                            stateHashMap.put(stateName, sID);

//                                } else {
//                                Snackbar snackbar=Snackbar.make(loginpage,"Please Enter Correct Userid & Password",Snackbar.LENGTH_LONG);
//                                snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
//                                View snackbarView = snackbar.getView();
//                                snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
//                                snackbar.show();
//                                }
                        }
                        try {

                            if(feildsEnabled) {

                                MySpinnerAdapter adapter = new MySpinnerAdapter(
                                        getContext(),
                                        R.layout.view_spinner_item,
                                        stateItemArrayList);
                                adapter.setDropDownViewResource(R.layout.drop_down_item);
                                stateSpinner.setAdapter(adapter);
                                stateLL.setBackground(getResources().getDrawable(R.drawable.rectangle_yellow_box_layout));


                                if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                                    int selectedPosition = getSelectedStatePosition(stateName, stateItemArrayList);
                                    if (selectedPosition > 0) {
                                        stateSpinner.setSelection(selectedPosition);
                                    }
                                }
                            } else {

                                MySpinnerAdapter adapter = new MySpinnerAdapter(
                                        getContext(),
                                        R.layout.disabled_view_spinner,
                                        stateItemArrayList);
                                adapter.setDropDownViewResource(R.layout.disabled_drop_down_item);
                                stateSpinner.setAdapter(adapter);
                                stateLL.setBackground(getResources().getDrawable(R.drawable.disabled_edit_text));


                                if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                                    int selectedPosition = getSelectedStatePosition(stateName, stateItemArrayList);
                                    if (selectedPosition > 0) {
                                        stateSpinner.setSelection(selectedPosition);
                                    }
                                }

                            }

                            getDistrictList(stateId);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }   /*if (dialog.isShowing())
                        dialog.dismiss();*/

                }, error -> {
                }) {
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (firstTimeLoaded) {
            if (parent.getId() == R.id.state_spinner) {
                if (stateItemArrayList != null && stateItemArrayList.size() > 0) {
                    if (position > 0) {
                        stateName = stateItemArrayList.get(position);
                        if (stateHashMap != null && stateHashMap.containsKey(stateName)) {
                            stateId = stateHashMap.get(stateName.trim());
                            getDistrictList(stateId);
                        }
                    }
                }
            } else if (parent.getId() == R.id.district_spinner) {
                if (districtItemArrayList != null && districtItemArrayList.size() > 0) {
                    if (position > 0) {
                        districtName = districtItemArrayList.get(position);
                        if (districtHashMap != null && districtHashMap.containsKey(districtName)) {
                            districId = districtHashMap.get(districtName.trim());
                            getCityList(stateId, districId);
                        }
                    }
                }
            } else if (parent.getId() == R.id.city_spinner) {
                if (cityItemArrayList != null && cityItemArrayList.size() > 0) {
                    if (position > 0) {
                        cityName = cityItemArrayList.get(position);
                        if (cityHashMap != null && cityHashMap.containsKey(cityName)) {
                            String cityId = cityHashMap.get(cityName.trim());
                            pinCode.setText(pinCodeStr);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean checkValidation() {
        if (mobileNumberStr.isEmpty()) {
            showError("Please enter Mobile number.");
            return false;
        }
        if (emailStr.isEmpty()) {
            showError("Please enter Email id.");
            return false;
        }

        if (stateSpinner.getSelectedItem() != null && !stateSpinner.getSelectedItem().toString().isEmpty() && !stateSpinner.getSelectedItem().toString().equalsIgnoreCase("Select State")){

        }else{
            showError("Please select any State.");
            return false;
        }
        if (district_spinner.getSelectedItem() != null && !district_spinner.getSelectedItem().toString().isEmpty() && !district_spinner.getSelectedItem().toString().equalsIgnoreCase("Select District")){

        }else{
            showError("Please select any District.");
            return false;
        }
        if (city_spinner.getSelectedItem() != null && !city_spinner.getSelectedItem().toString().isEmpty() && !city_spinner.getSelectedItem().toString().equalsIgnoreCase("Select City")){

        }else{
            showError("Please select any City.");
            return false;
        }
        if (addressStr.isEmpty()) {
            showError("Please enter Address.");
            return false;
        }
        if (pinCodeStr.isEmpty()) {
            showError("Please enter Pincode.");
            return false;
        }
        return true;
    }


    private void showError(String msg) {
        Snackbar snackbar = Snackbar.make(billingDetailsRoot, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
        snackbar.show();
    }

    protected void showPopup(Context context, String msg) {

        // do something when the button is clicked
// do something when the button is clicked
        AlertDialog alertbox = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("Yes", (arg0, arg1) -> {
//                        ((ShoppingActivity) getActivity()).showProductList("Products list", productsDetailsList.get(0).getpId());
                    arg0.dismiss();
                })
                .setNegativeButton("No", (arg0, arg1) -> arg0.dismiss())
                .show();

    }

    public void clearMRPAndWeightList(){
        if(newMrpsList != null){
            newMrpsList.clear();
        }
        if(newWeightList != null){
            newWeightList.clear();
        }
       if(newQuantityList != null){
           newQuantityList.clear();
       }
    }

    public void updateCartValue(ProductsDetails productsDetails, String quantity, int index) {

//      quantityValue = quantityValue + Integer.parseInt(quantity);
        float quantityValue = Integer.parseInt(quantity);
        newQuantityList.put(productsDetails.getProduct_id(), quantityValue);

        float newMrp = (Float.parseFloat(productsDetails.getDp()) * quantityValue);
        Log.e(TAG, "updateCartValue: DP  : " + productsDetails.getDp());
        Log.e(TAG, "updateCartValue: MRP  : " + newMrp);

//        float otherProductMRP = 0;
//        if(newMrpsList.size() >1) {
//            for (int i = 0; i < newMrpsList.size(); i++) {
//                if(index == i){
//                    newMrpsList.remove(i);
//                    newMrpsList.add(i, newMrp);
//                }else {
//                    otherProductMRP = otherProductMRP + newMrpsList.get(i);
//                }
//            }
//        }
//
//        newMrp = newMrp+ otherProductMRP;
//        newMrpsList.add(newMrp);

        newMrpsList.put(productsDetails.getProduct_id(), newMrp);
        Log.e(TAG, "updateCartValue: Add Mrp : "+ newMrp +" : Product id : "+ productsDetails.getProduct_id() );

//        float newWeight =0;
//        String[] quantitySymbolArray = productsDetails.getWeight().split(" ");
//        if (quantitySymbolArray.length > 1) {
//            quantitySymbol = productsDetails.getWeight().split(" ")[1];
//            newWeight = Float.parseFloat(productsDetails.getWeight().split(" ")[0]) * quantityValue;
//        }
//        Log.e(TAG, "updateCartValue: Weight  : " + newWeight);

//        newWeightList.add(newWeight);
//        newWeightList.put(productsDetails.getProduct_id(), newWeight);

        // Weight part code




//        postRoomDBRepository.updateProductsDetailsMRPByProductId(""+newMrp, productsDetails.getProduct_id());
//        postRoomDBRepository.updateProductsDetailsWeightByProductId(""+newWeight, productsDetails.getProduct_id());
//        postRoomDBRepository.updateProductsDetailsQuantityByProductId(""+quantityValue, productsDetails.getProduct_id());


//        subTotalAmount.setText("₹ "+productsDetails.getmRP());
//        discount.setText("₹ 0.00");
//        totalAmount.setText("₹ "+productsDetails.getmRP());
//        totalWeight.setText(productsDetails.getpWeight()+" g");
//        grandTotal.setText("₹ "+productsDetails.getmRP());

    }


}