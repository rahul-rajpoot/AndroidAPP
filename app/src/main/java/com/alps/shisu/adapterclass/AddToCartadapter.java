package com.alps.shisu.adapterclass;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alps.shisu.OnRecyclerItemClickListener;
import com.alps.shisu.R;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.alps.shisu.Shopping.shopnow.BillingDetailsFragment;
import com.alps.shisu.db.local.RoomDBRepository;
import com.alps.shisu.db.local.entity.ProductsDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AddToCartadapter extends RecyclerView.Adapter<AddToCartadapter.MyViewHolder> {

    private final Context mContext;
    //    List<ProductsDetails> productsDetailsList;
    ArrayList<String> quantityArrayList;
    private final BillingDetailsFragment billingDetailsFragment;
    private ProductsDetails productsDetails;
    private final Activity activity;
    private final RoomDBRepository postRoomDBRepository; // Local db instance
    private final List<ProductsDetails> productsDetailsList;
    private boolean firstTimeLoaded;
//    public int index;
    private RecyclerView.ViewHolder viewHolder;
    int check = 0;
    private OnRecyclerItemClickListener listener;

//    public void setOnItemClickListener(com.alps.aplusmart.Adapters.ActivitionOrderAdapter.OnItemClickListener listener){
//        mListener=listener;
//    }

    public AddToCartadapter(Context c, Activity activity, BillingDetailsFragment billingDetailsFragment, ArrayList<String> quantityArrayList, int position, List<ProductsDetails> productsDetailsList) {
        mContext = c;
        this.activity = activity;
//        this.productsDetailsList = productsDetailsList;
        this.quantityArrayList = quantityArrayList;
//        index = position;
        this.billingDetailsFragment = billingDetailsFragment;
        this.productsDetailsList = productsDetailsList;
        postRoomDBRepository = new RoomDBRepository(activity.getApplication());
        listener = new OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }

            @Override
            public void onSpinnerItemSelected(int pos, int itemSelectedPos) {
                    performSpinnerItemClick(pos, itemSelectedPos);
            }
        };
    }

    @NonNull
    @Override
    public AddToCartadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(mContext)
                .inflate(R.layout.cart_item_layout,viewGroup,false);

        return new MyViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AddToCartadapter.MyViewHolder myViewHolder, int position) {

        viewHolder = myViewHolder;
        productsDetails = productsDetailsList.get(position);
//        myViewHolder.quantitySpinner.setOnItemSelectedListener(this);

        myViewHolder.product.setText(productsDetails.getProduct_name());
        myViewHolder.category.setText(productsDetails.getCategory_name());
        myViewHolder.mrp.setText(productsDetails.getMrp());
        myViewHolder.dp.setText(productsDetails.getDp());

        Log.e(TAG, "updateCartValue adapter: DP : "+ productsDetails.getDp() );
            if (productsDetails.getWeight() != null && !productsDetails.getWeight().isEmpty()) {
//                if (Float.parseFloat(productsDetails.getpWeight().split(" ")[0]) > 0) {
////                    weight.setText(productsDetails.getpWeight() + "gm/"+productsDetails.getpPurity() +"Kt");
//                    weight.setText(productsDetails.getpWeight());
//                } else {
//                    weight.setText(productsDetails.getpWeight() + "mg/"+productsDetails.getpPurity() +"Kt");
                myViewHolder.weight.setText(productsDetails.getWeight());
//                }
            }

            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    mContext,
                    R.layout.view_spinner_item,
                    quantityArrayList);
            adapter.setDropDownViewResource(R.layout.drop_down_item);
            myViewHolder.quantitySpinner.setAdapter(adapter);

            myViewHolder.quantitySpinner.setSelection((Integer.parseInt(productsDetails.getQuantity())-1));
//            myViewHolder.quantitySpinner.setOnItemSelectedListener(this);

//            myViewHolder.quantitySpinner.setEnabled(false);

//            myViewHolder.quantitySpinner.setOnTouchListener(Spinner_OnTouch);
//            myViewHolder.quantitySpinner.setOnKeyListener(Spinner_OnKey);


//          billingDetailsFragment.updateCartValue(productsDetails, myViewHolder.quantitySpinner.getSelectedItem().toString());

            myViewHolder.cancelImage.setOnClickListener(v -> {
                if(productsDetailsList != null && productsDetailsList.size() >0){
                    if(productsDetailsList.size() == 1){
//                            ((DashBoard) activity).clearBackStack();
//                            HolderFragment homeFragment = new HolderFragment();
//                            FragmentTransaction ft = ((DashBoard) activity).getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.nav_host_fragment, homeFragment, "HolderFragment");
//                            ft.commit();
                        productsDetailsList.clear();
                        postRoomDBRepository.deleteProductDetailsByProductId(productsDetails.getProduct_id(), productsDetails.getUser_id());
                        FragmentManager fragmentManager = ((ShoppingActivity) activity).getSupportFragmentManager();
                        fragmentManager.popBackStackImmediate();
                    } else {
                        productsDetailsList.remove(position);
                        postRoomDBRepository.deleteProductDetailsByProductId(productsDetails.getProduct_id(), productsDetails.getUser_id());
                        notifyDataSetChanged();
                    }

                    billingDetailsFragment.clearMRPAndWeightList();
                    for (ProductsDetails productDetails:
                            productsDetailsList) {
                        billingDetailsFragment.updateCartValue(productDetails, productDetails.getQuantity(), position);
                    }
                    billingDetailsFragment.setTotalPrice();

                }
            });



            if (productsDetails.getImg() != null && !productsDetails.getImg().isEmpty()) {
                Glide.with(mContext).load(productsDetails.getImg())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.e("GlideException", e.getMessage());
                                myViewHolder.imageView.setImageResource(R.drawable.no_image);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(myViewHolder.imageView);
            } else {
                myViewHolder.imageView.setImageResource(R.drawable.no_image);
            }


    }


    @Override
    public int getItemCount() {
        return productsDetailsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        final TextView category;
        final TextView product;
        final TextView mrp;
        final TextView dp;
        final TextView weight ;
        final ImageView cancelImage;
        final ImageView imageView;
        final Spinner quantitySpinner;


        public MyViewHolder(@NonNull View itemView,OnRecyclerItemClickListener onRecyclerItemClickListener) {
            super(itemView);
            category =
                    itemView.findViewById(R.id.category);

            cancelImage =
                    itemView.findViewById(R.id.cancel_image);

            product =
                    itemView.findViewById(R.id.product);

            mrp =
                    itemView.findViewById(R.id.mrp);
            dp =
                    itemView.findViewById(R.id.dp);

            weight =
                    itemView.findViewById(R.id.weight);

            quantitySpinner =
                    itemView.findViewById(R.id.quantity_spinner);
            quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onRecyclerItemClickListener.onSpinnerItemSelected(getAdapterPosition(), position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // set image based on selected text
            imageView =
                    itemView.findViewById(R.id.product_image);

//            itemView.setOnClickListener(v -> {
//
////                index = getAbsoluteAdapterPosition();
//
////                    if (mListener != null){
////                        int position=getAdapterPosition();
////                        if (position != RecyclerView.NO_POSITION){
////                            mListener.onItemClick(position);
////                        }
////                    }
//            });
        }


//        @Override
//        public void onClick(View v) {
//            index = getAbsoluteAdapterPosition();
//        }
    }

    public void performSpinnerItemClick(int index, int position){
        try {
            if(++check > 1) {
                Log.e(TAG, "onItemSelected: index : "+ index +" position : "+position );
                productsDetails = productsDetailsList.get(index);
                Log.e(TAG, "onItemSelected: Product_name :"+ productsDetails.getProduct_name() );
                postRoomDBRepository.updateProductsDetailsQuantityByProductId(quantityArrayList.get(position),productsDetails.getProduct_id(), productsDetails.getUser_id() );
                Log.e(TAG, "onItemSelected: productsDetails : "+ productsDetails.getProduct_name() );
                billingDetailsFragment.updateCartValue(productsDetails, quantityArrayList.get(position), index);
                billingDetailsFragment.setTotalPrice();
                Log.e(TAG, "onItemSelected: called." );
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        try {
//            if(++check > 1) {
//
//                postRoomDBRepository.updateProductsDetailsQuantityByProductId(quantityArrayList.get(position),productsDetails.getProduct_id(), productsDetails.getUser_id() );
//                Log.e(TAG, "onItemSelected: productsDetails : "+ productsDetails.getProduct_name() );
//                billingDetailsFragment.updateCartValue(productsDetails, quantityArrayList.get(position), index);
//                billingDetailsFragment.setTotalPrice();
//                Log.e(TAG, "onItemSelected: called." );
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }




//    private View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
//        public boolean onTouch(View v, MotionEvent event) {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//            }
//            return true;
//        }
//    };
//    private static View.OnKeyListener Spinner_OnKey = new View.OnKeyListener() {
//        public boolean onKey(View v, int keyCode, KeyEvent event) {
//            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    };
}


//public class AddToCartadapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
//    private Context mContext;
////    List<ProductsDetails> productsDetailsList;
//    ArrayList<String> quantityArrayList = new ArrayList<>();
//    private int index;
//    private BillingDetailsFragment billingDetailsFragment;
//    private ProductsDetails productsDetails;
//    private Activity activity;
//
////    public AddToCartadapter(Context c, Activity activity, BillingDetailsFragment billingDetailsFragment, List<ProductsDetails> productsDetailsList, ArrayList<String> quantityArrayList, int position) {
//    public AddToCartadapter(Context c, Activity activity, BillingDetailsFragment billingDetailsFragment, ArrayList<String> quantityArrayList, int position) {
//        mContext = c;
//        this.activity = activity;
////        this.productsDetailsList = productsDetailsList;
//        this.quantityArrayList = quantityArrayList;
//        index = position;
//        this.billingDetailsFragment = billingDetailsFragment;
//    }
//
//    @Override
//    public int getCount() {
//        return productsDetailsList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup
//            parent) {
//        LayoutInflater inflater = (LayoutInflater)
//                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View gridView;
//        if (convertView == null) {
//            gridView = new View(mContext);
//            // get layout from mobile.xml
//            gridView = inflater.inflate(R.layout.cart_item_layout, null);
//            // set value into textview
//
//            productsDetails = productsDetailsList.get(position);
//
//            TextView category =
//                    gridView.findViewById(R.id.category);
//
//            ImageView cancelImage =
//                    gridView.findViewById(R.id.cancel_image);
//
//            TextView product =
//                    gridView.findViewById(R.id.product);
//
//            TextView mrp =
//                    gridView.findViewById(R.id.mrp);
//            TextView dp =
//                    gridView.findViewById(R.id.dp);
//
//            TextView weight =
//                    gridView.findViewById(R.id.weight);
//            Spinner quantitySpinner =
//                    gridView.findViewById(R.id.quantity_spinner);
//
//            quantitySpinner.setOnItemSelectedListener(this);
//
//            product.setText(productsDetails.getpName());
//            category.setText(productsDetails.getCategoryName());
//            mrp.setText(productsDetails.getmRP());
//            dp.setText(productsDetails.getdP());
//            if (productsDetails.getpWeight() != null && !productsDetails.getpWeight().isEmpty()) {
////                if (Float.parseFloat(productsDetails.getpWeight().split(" ")[0]) > 0) {
//////                    weight.setText(productsDetails.getpWeight() + "gm/"+productsDetails.getpPurity() +"Kt");
////                    weight.setText(productsDetails.getpWeight());
////                } else {
////                    weight.setText(productsDetails.getpWeight() + "mg/"+productsDetails.getpPurity() +"Kt");
//                    weight.setText(productsDetails.getpWeight());
////                }
//            }
//
//            MySpinnerAdapter adapter = new MySpinnerAdapter(
//                    mContext,
//                    R.layout.view_spinner_item,
//                    quantityArrayList);
//            adapter.setDropDownViewResource(R.layout.drop_down_item);
//            quantitySpinner.setAdapter(adapter);
//
//            quantitySpinner.setSelection(index);
//
//            billingDetailsFragment.updateCartValue(productsDetails, quantitySpinner.getSelectedItem().toString());
//
//            cancelImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(productsDetailsList != null && productsDetailsList.size() >0){
//                        if(productsDetailsList.size() == 1){
////                            ((DashBoard) activity).clearBackStack();
////                            HolderFragment homeFragment = new HolderFragment();
////                            FragmentTransaction ft = ((DashBoard) activity).getSupportFragmentManager().beginTransaction();
////                            ft.replace(R.id.nav_host_fragment, homeFragment, "HolderFragment");
////                            ft.commit();
//                            productsDetailsList.clear();
//                            FragmentManager fragmentManager = ((ShoppingActivity) activity).getSupportFragmentManager();
//                            fragmentManager.popBackStackImmediate();
//                        } else {
//                            productsDetailsList.remove(position);
//                            notifyDataSetChanged();
//                        }
//                    } else {
//
//                    }
//                }
//            });
//
//            // set image based on selected text
//            ImageView imageView =
//                    gridView.findViewById(R.id.product_image);
//
//            if (productsDetails.getDefaultImg() != null && !productsDetails.getDefaultImg().isEmpty()) {
//                Glide.with(mContext).load(productsDetails.getDefaultImg())
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                Log.e("GlideException", e.getMessage());
//                                imageView.setImageResource(R.drawable.no_image);
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                return false;
//                            }
//                        })
//                        .into(imageView);
//            } else {
//                imageView.setImageResource(R.drawable.no_image);
//            }
//        } else {
//            gridView = convertView;
//        }
//        return gridView;
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        try {
//            billingDetailsFragment.updateCartValue(productsDetails, quantityArrayList.get(position));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//}

