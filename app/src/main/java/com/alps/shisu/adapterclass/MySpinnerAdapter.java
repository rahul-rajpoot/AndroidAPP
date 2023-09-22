package com.alps.shisu.adapterclass;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MySpinnerAdapter extends ArrayAdapter<String> {
    // Initialise custom font, for example:
//    Typeface font = Typeface.createFromAsset(getContext().getAssets(),
//            "font/poppins_regular.ttf");

    // (In reality I used a manager which caches the Typeface objects)
    // Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);

    public MySpinnerAdapter(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //        view.setTypeface(font);
        return (TextView) super.getView(position, convertView, parent);
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //        view.setTypeface(font);
        return (TextView) super.getDropDownView(position, convertView, parent);
    }
}
