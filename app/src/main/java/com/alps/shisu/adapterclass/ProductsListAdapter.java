package com.alps.shisu.adapterclass;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alps.shisu.R;
import com.alps.shisu.Shopping.ShoppingActivity;
import com.alps.shisu.modelclass.ProductListDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ProductsListAdapter extends BaseAdapter
{
    private final Context mContext;
    private final List<ProductListDetails> productListDetailsList;
    private final String currencySymbol;

    public ProductsListAdapter(Context c, List<ProductListDetails> productListDetailsList, String currencySymbol )
    {
        mContext = c;
        this.currencySymbol = currencySymbol;
        this.productListDetailsList = productListDetailsList;
    }

    @Override
    public int getCount()
    {
        return productListDetailsList.size();
    }
    @Override
    public Object getItem(int position)
    {
        return position;
    }
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup
            parent)
    {
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = new View(mContext);

//        if (convertView == null)
//        {
            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.products_list_layout, null);
            // set value into textview

            ProductListDetails productListDetails = productListDetailsList.get(position);
            TextView nameView =
                    gridView.findViewById(R.id.name);
            nameView.setText(productListDetails.getpName());
            Log.e(TAG, "getView: "+ productListDetails.getpName() );

            TextView priceView =
                    gridView.findViewById(R.id.price);
//            priceView.setText("₹ "+productListDetails.getmRP());
            priceView.setText(currencySymbol+" "+productListDetails.getmRP());
            priceView.setPaintFlags(priceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



        TextView discountPriceView =
                    gridView.findViewById(R.id.discountPrice);
//            priceView.setText("₹ "+productListDetails.getmRP());
            discountPriceView.setText(currencySymbol+" "+productListDetails.getdP());


        ImageView eyeImage =
                    gridView.findViewById(R.id.eye_image);
            eyeImage.setOnClickListener(v -> ((ShoppingActivity)mContext).showProductDetails("Products Detail", productListDetails.getpId() ));

            // set image based on selected text
            ImageView imageView =
                    gridView.findViewById(R.id.product_image);
//            imageView.setImageResource(productListDetails.getImgurl());
            if (productListDetails.getImgurl() != null && !productListDetails.getImgurl().isEmpty()) {
                Glide.with(mContext).load(productListDetails.getImgurl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.e("GlideException", e.getMessage());
                                imageView.setImageResource(R.drawable.no_image);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.no_image);
            }
//        }
//        else
//        {
//            gridView = (View) convertView;
//            Log.e(TAG, "getView: "+ gridView.getId() );
//        }
        return gridView;
    }
}
