package com.alps.shisu.Shopping.shopnow;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.alps.shisu.db.local.RoomDBRepository;
import com.alps.shisu.db.local.entity.ProductsDetails;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import me.himanshusoni.quantityview.QuantityView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {

    private static final String PRODUCT_ID = "product_id";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ProductDetailsFragment";

    private String productId;

    private String urls = "";
    private Config ut;
    private GridView grid;

    private ProductsDetails productsDetails;

    private ImageView productImageDet;
    private TextView productName, priceValue, discountPriceValue, purityValue, description;
    private LinearLayout addToCart, productDetailsRoot;
    private QuantityView quantityView_default;
    private HashMap<String,String> user;
    private RoomDBRepository postRoomDBRepository; // Local db instance
    private final List<ProductsDetails> productsDetailsList = new ArrayList<>();
    AlertDialog dialog;
    
    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param productId Parameter 1.
     * @param param2    Parameter 2.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    public static ProductDetailsFragment newInstance(String productId, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(PRODUCT_ID, productId);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString(PRODUCT_ID);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        SessionManagement sessionManagement = new SessionManagement(getContext());
        user= sessionManagement.getUserDetails();

        postRoomDBRepository = new RoomDBRepository(getActivity().getApplication());

        dialog = new SpotsDialog.Builder().setContext(getActivity()).setCancelable(false).build();
        dialog.show();

        productDetailsRoot = view.findViewById(R.id.productDetailsRoot);
        productImageDet = view.findViewById(R.id.product_image_det);
        productName = view.findViewById(R.id.name);
//        weightValue = view.findViewById(R.id.weightValue);
        priceValue = view.findViewById(R.id.priceValue);
        discountPriceValue = view.findViewById(R.id.discountPriceValue);
        addToCart = view.findViewById(R.id.addToCart);
        description = view.findViewById(R.id.description);
        quantityView_default = view.findViewById(R.id.quantityView_default);


//        quantityView_default.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
//            @Override
//            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
//
//            }
//
//            @Override
//            public void onLimitReached() {
//
//            }
//        });


        urls = Config.url + "CategoryProducts/" + Config.merchantid +"/"+ Config.securtykey + "/Details/" + productId + "/0";
        Log.e(TAG, "onCreateView: " + urls);
        getProductsDetails();

//        NumberPicker numberPicker = view.findViewById(R.id.number_picker);
//        numberPicker.setMaxValue(15);
//        numberPicker.setMinValue(5);
//        numberPicker.setValue(10);

        return view;
    }

    private void getProductsDetails() {
        //dialog.show();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                    response -> {
                        try {
                            JSONArray object = new JSONArray(response);

                            JSONObject jsonObject = object.getJSONObject(0);

                            String pId = jsonObject.getString("Pid");
                            String pName = jsonObject.getString("Pname");
                            String mRP = jsonObject.getString("MRP");
                            String pCode = jsonObject.getString("Pcode");
                            String dP = jsonObject.getString("DP");
                            String bV = jsonObject.getString("BV");
                            String pV = jsonObject.getString("PV");
                            String quantity = jsonObject.getString("Quantity");
                            String desc = jsonObject.getString("Description");
                            String size = jsonObject.getString("Size");
                            String color = jsonObject.getString("Color");
                            String extraMargin = jsonObject.getString("extraMargin");
                            String otherPaymentMode = jsonObject.getString("OtherPaymentMode");
                            String defaultImg = jsonObject.getString("defaultImg");
//                                String pPurity = jsonObject.getString("pPurity");
                            String pWeight = jsonObject.getString("pWeight");
                            String categoryName = jsonObject.getString("CategoryName");


                            productsDetails = new ProductsDetails();
                            productsDetails.setUser_id(user.get(SessionManagement.KEY_USERNAME));
                            productsDetails.setProduct_id(pId);
                            productsDetails.setDp(dP);
                            productsDetails.setBv(bV);
                            productsDetails.setPv(pV);
                            productsDetails.setProduct_name(pName);
                            productsDetails.setMrp(mRP);
                            productsDetails.setProduct_code(pCode);
                            productsDetails.setQuantity("1");
                            productsDetails.setDesc(desc);
//                                productsDetails.setpPurity(pPurity);
//                            productsDetails.setWeight(pWeight);
                            productsDetails.setImg(defaultImg);
                            productsDetails.setExtra_margin(extraMargin);
                            productsDetails.setPaymet_mode(otherPaymentMode);
                            productsDetails.setCategory_name(categoryName);

//                                productsDetailsList.clear();
//                                productsDetailsList.add(productsDetails);

                            productName.setText(pName);
//                                weightValue.setText(pWeight + " g");
//                            weightValue.setText(pWeight);
//                                purityValue.setText(pPurity + " Kt");
//                                priceValue.setText("₹ " + mRP);
                            priceValue.setText(user.get(SessionManagement.KEY_CURRENCY)+" " + mRP);
                            priceValue.setPaintFlags(priceValue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            discountPriceValue.setText(user.get(SessionManagement.KEY_CURRENCY)+" " + dP);
                            description.setText(desc);

                            if (getActivity() != null) {
                                if (productsDetails.getImg() != null && !productsDetails.getImg().isEmpty()) {
                                    Glide.with(getActivity()).load(productsDetails.getImg())
                                            .listener(new RequestListener<Drawable>() {
                                                @Override
                                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                    Log.e("GlideException", e.getMessage());
                                                    productImageDet.setImageResource(R.drawable.no_image);
                                                    return false;
                                                }

                                                @Override
                                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                    return false;
                                                }
                                            })
                                            .into(productImageDet);
                                } else {
                                    productImageDet.setImageResource(R.drawable.no_image);
                                }
                            }

                            addToCart.setOnClickListener(v -> {

                                postRoomDBRepository.insertProductsDetails(productsDetails);
                                productsDetailsList.add(productsDetails);
                                if (productsDetailsList != null && productsDetailsList.size() > 0) {
                                    Log.e(TAG, "onClick: Quantity : " + quantityView_default.getQuantity());
                                    productsDetails.setQuantity("" + quantityView_default.getQuantity());
//                    ((ShoppingActivity) getActivity()).showBillingDetails("Billing Details", productsDetailsList);
                                    ((ShoppingActivity) getActivity()).showBillingDetails("Billing Details");

                                }
                            });
                            if (dialog.isShowing())
                                dialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }
                        /*if (dialog.isShowing())
                        dialog.dismiss();*/

                    }, error -> {
                        Snackbar snackbar = Snackbar.make(productDetailsRoot, "" + error, Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.parseColor("#FF378F44"));
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorButton));
                        snackbar.show();
                        if (dialog.isShowing())
                            dialog.dismiss();
                        //Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                        /* if (dialog.isShowing())
                        dialog.dismiss();*/
                    }) {
                protected Map<String, String> getParams() {
                    //                  params.put("username",usname);
//                  params.put("pass",userspass);
                    return new HashMap<>();
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}