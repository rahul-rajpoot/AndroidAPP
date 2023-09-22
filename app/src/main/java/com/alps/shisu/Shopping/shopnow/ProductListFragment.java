package com.alps.shisu.Shopping.shopnow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.alps.shisu.adapterclass.ProductsListAdapter;
import com.alps.shisu.modelclass.ProductListDetails;
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
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment {


    private static final String ITEM_ID = "item_id";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ProductListFragment";

    private String itemId;
    private final List<ProductListDetails> productListDetailsList = new ArrayList<>();

    private String urls = "";
    private Config ut;
    private GridView grid;
    private RelativeLayout no_record_found_lay;
    //    private LinearLayout productListRoot;
    private FrameLayout productListRoot;
    private HashMap<String, String> user;


    public ProductListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemId Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductListFragment.
     */
    public static ProductListFragment newInstance(String itemId, String param2) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString(ITEM_ID);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        grid = view.findViewById(R.id.product_list);
        productListRoot = view.findViewById(R.id.productListRoot);
        no_record_found_lay = view.findViewById(R.id.no_record_found_lay);

        SessionManagement sessionManagement = new SessionManagement(getContext());
        user = sessionManagement.getUserDetails();

        urls = Config.url + "CategoryProducts/" + Config.merchantid + "/" + Config.securtykey + "/Products/" + itemId + "/1";
        Log.e(TAG, "onCreateView: " + urls);
        getProductsList();

        return view;

    }

    private void getProductsList() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                response -> {
                    try {

                        JSONArray object = new JSONArray(response);

                        productListDetailsList.clear();
                        no_record_found_lay.setVisibility(View.GONE);
                        grid.setVisibility(View.VISIBLE);

//                          JSONObject jsonObject1 = object.getJSONObject(0);

                        for (int i = 0; i < object.length(); i++) {

                            JSONObject jsonObject = object.getJSONObject(i);

                            String pId = jsonObject.getString("Pid");
                            String imgurl = jsonObject.getString("Imgurl");
                            String pName = jsonObject.getString("Pname");
                            String mRP = jsonObject.getString("MRP");
                            String dP = jsonObject.getString("DP");
                            String bV = jsonObject.getString("BV");
                            String pV = jsonObject.getString("PV");

                            ProductListDetails productListDetails = new ProductListDetails();
                            productListDetails.setpId(pId);
                            productListDetails.setImgurl(imgurl);
                            productListDetails.setpName(pName);
                            productListDetails.setmRP(mRP);
                            productListDetails.setdP(dP);
                            productListDetails.setbV(bV);
                            productListDetails.setpV(pV);

                            Log.e(TAG, "onResponse: "+ productListDetails.getpName());

                            productListDetailsList.add(productListDetails);

                            }

                            ProductsListAdapter adapter = new ProductsListAdapter(getActivity(), productListDetailsList, user.get(SessionManagement.KEY_CURRENCY));
                            grid.setAdapter(adapter);
                            grid.setOnItemClickListener((parent, view, position, id) -> ((ShoppingActivity) getActivity()).showProductDetails("Products Detail", productListDetailsList.get(position).getpId()));
//                            }
                        try {
                            ((ShoppingActivity) getActivity()).hideDefaultLoaderFragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        ((ShoppingActivity) getActivity()).hideDefaultLoaderFragment();
                        ((ShoppingActivity) getActivity()).showErrorSnackBar(productListRoot, "Something went wrong");
                        grid.setVisibility(View.GONE);
                        no_record_found_lay.setVisibility(View.VISIBLE);
                    }

                }, error -> {
                    ((ShoppingActivity) getActivity()).hideDefaultLoaderFragment();
                    ((ShoppingActivity) getActivity()).showErrorSnackBar(productListRoot, "Something went wrong");
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
}