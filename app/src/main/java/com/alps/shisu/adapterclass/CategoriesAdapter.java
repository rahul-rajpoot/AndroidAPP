package com.alps.shisu.adapterclass;

import android.content.Context;
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
import com.alps.shisu.modelclass.CategoryItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class CategoriesAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<CategoryItem> categoryItemList;

    public CategoriesAdapter(Context c, List<CategoryItem> categoryItemList) {
        mContext = c;
        this.categoryItemList = categoryItemList;
    }

    @Override
    public int getCount() {
        return categoryItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup
            parent) {
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(mContext);
            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.categories_layout, null);
            // set value into textview
            CategoryItem categoryItem = categoryItemList.get(position);
            TextView textView = (TextView)
                    gridView.findViewById(R.id.grid_item_label);
            textView.setText(categoryItem.getCategoryName());
            // set image based on selected text
            ImageView imageView = (ImageView)
                    gridView.findViewById(R.id.grid_item_image);
//            imageView.setImageResource(catImages[position]);
            if (categoryItem.getCategoryImage() != null && !categoryItem.getCategoryImage().isEmpty()) {
                Glide.with(mContext).load(categoryItem.getCategoryImage())
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
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }
}