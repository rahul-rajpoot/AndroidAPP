package com.alps.shisu.Shopping.shopnow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.alps.shisu.ApiUtil.Config;
import com.alps.shisu.R;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.alps.shisu.adapterclass.CategoriesAdapter;
import com.alps.shisu.modelclass.CategoryItem;
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

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopNowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ShopNowFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final List<CategoryItem> categoryItemList = new ArrayList<>();

    private String urls = "";
    private Config ut;
    private GridView grid;
    private LinearLayout shopNowRoot;

    public ShopNowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopNowFragment.
     */
    public static ShopNowFragment newInstance(String param1, String param2) {
        ShopNowFragment fragment = new ShopNowFragment();
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
        View view = inflater.inflate(R.layout.fragment_shop_now, container, false);

        grid= view.findViewById(R.id.grid_view);
        shopNowRoot= view.findViewById(R.id.shopNowRoot);

        urls = Config.url + "CategoryProducts/" + Config.merchantid +"/"+ Config.securtykey + "/Category/0/0";
        Log.e(TAG, "onCreateView: "+ urls );
        getCategoriesList();

        return view;
    }

    private void getCategoriesList() {
        //dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                response -> {
                    try {
                        Log.e(TAG, "onResponse: "+ response );
                        JSONArray object = new JSONArray(response);
                        categoryItemList.clear();
                        for (int i = 0; i < object.length(); i++) {
                            JSONObject jsonObject = object.getJSONObject(i);
                            //   Boolean b=object.getBoolean("error");

                            String catid = jsonObject.getString("Catid");
                            String imgurl = jsonObject.getString("Imgurl");
                            String catName = jsonObject.getString("CatName");

                            CategoryItem categoryItem = new CategoryItem();
                            categoryItem.setCategoryId(catid);
                            categoryItem.setCategoryName(catName);
                            categoryItem.setCategoryImage(imgurl);

                            categoryItemList.add(categoryItem);

                        }

                        CategoriesAdapter adapter = new CategoriesAdapter(getActivity(), categoryItemList);
                        grid.setAdapter(adapter);
                        grid.setOnItemClickListener((parent, view, position, id) -> ((ShoppingActivity) getActivity()).showProductList("Products List", categoryItemList.get(position).getCategoryId()));
                        try {
                            ((ShoppingActivity) getActivity()).hideDefaultLoaderFragment();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        ((ShoppingActivity) getActivity()).hideDefaultLoaderFragment();
                        ((ShoppingActivity) getActivity()).showErrorSnackBar(shopNowRoot, "Something went wrong");

                    }   /*if (dialog.isShowing())
                        dialog.dismiss();*/

                }, error -> {
                    ((ShoppingActivity) getActivity()).hideDefaultLoaderFragment();
                    ((ShoppingActivity) getActivity()).showErrorSnackBar(shopNowRoot, "Something went wrong");

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