package com.alps.shisu.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alps.shisu.InteroSlider.CustomVolleyRequest;
import com.alps.shisu.InteroSlider.SliderUtil;
import com.alps.shisu.R;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private final Context context;
    //  private  int image[];
    private final List<SliderUtil> sliderImg;

    public ViewPagerAdapter(List<SliderUtil>sliderImg,Context context) {
        this.sliderImg=sliderImg;
        this.context=context;
    }

    @Override
    public int getCount() {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.custom_imagesliderview,null);

        SliderUtil utils=sliderImg.get(position);

        ImageView imageView=(ImageView)view.findViewById(R.id.sliderimagedeat);
        // imageView.setImageResource(image[position]);

        ImageLoader imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(utils.getSliderImageUrl(),ImageLoader.getImageListener(imageView,R.drawable.default_product
                ,android.R.drawable.ic_dialog_alert));

        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp=(ViewPager)container;
        View view=(View)object;
        vp.removeView(view);
    }
}

