package com.alps.shisu.adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alps.shisu.R;
import com.alps.shisu.modelclass.PackageDetails;

import java.util.List;

public class ActivePackageListAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<PackageDetails> packageDetailsList;

    public ActivePackageListAdapter(Context c, List<PackageDetails> packageDetailsList) {
        mContext = c;
        this.packageDetailsList = packageDetailsList;
    }

    @Override
    public int getCount() {
        return packageDetailsList.size();
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
            gridView = inflater.inflate(R.layout.activated_package_list_item, null);
            // set value into textview
            PackageDetails packageDetails = packageDetailsList.get(position);
            TextView serialNo = (TextView)
                    gridView.findViewById(R.id.serialNo);
            serialNo.setText(""+(position+1));

            TextView currentPackageText = (TextView)
                    gridView.findViewById(R.id.currentPackageText);
            currentPackageText.setText(packageDetails.getKitCode());
//            // set image based on selected text
//            ImageView imageView = (ImageView)
//                    gridView.findViewById(R.id.grid_item_image);
////            imageView.setImageResource(catImages[position]);
//            if (categoryItem.getCategoryImage() != null && !categoryItem.getCategoryImage().isEmpty()) {
//                Glide.with(mContext).load(categoryItem.getCategoryImage())
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
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }
}