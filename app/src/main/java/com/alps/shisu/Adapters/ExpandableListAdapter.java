package com.alps.shisu.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alps.shisu.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context _context;
    private final List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private final HashMap<String, List<String>> _listDataChild;
    ExpandableListView listView;
    private final ColorStateList blueColorList;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData, ColorStateList blueColorList) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.blueColorList = blueColorList;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_list_group_child, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

//        if (groupPosition != null){
//            if ((_listDataHeader.get(groupPosition)!=null)&&(_listDataChild.get(_listDataHeader.get(groupPosition)).size()!=null)){
//pos=_listDataChild.get(_listDataHeader.get(groupPosition)).size();
//            }
//            else {
//                pos=0;
//            }
//
//        }


        List child = _listDataChild.get(_listDataHeader.get(groupPosition));
        int pos;

        if (child != null && !child.isEmpty()) {
            pos = _listDataChild.get(_listDataHeader.get(groupPosition)).size();
            // return child.size();
        } else {
            pos = 0;
        }

        return pos;
        //   return this._listDataChild.get(this._listDataHeader.get(groupPosition))
        //         .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_list_group, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.lblListHeader);
//        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        //  lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.arrow_down,0);

        ImageView im = convertView.findViewById(R.id.eximage);
        //  int imgid=this.groupImages.get(groupPosition);
        // im.setImageResource(NavigationDrawer.groupImages[]);

       /* if (headerTitle.equals("KYC Manager"))
        {
            im.setImageResource(R.drawable.kyc);
        }*/

//        listDataHeader.add("Dashboard");
//        listDataHeader.add("My Network");
//        listDataHeader.add("E-Pin Manager");
//        listDataHeader.add("Profile Manager");
//        listDataHeader.add("Financial Report");
//        listDataHeader.add("Currency Wallet");
//        listDataHeader.add("Logout");


        if (headerTitle.equals("Dashboard")) {
            im.setImageResource(R.drawable.home);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equals("Shop")) {
            im.setImageResource(R.drawable.shop);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equals("Activate Account")) {
            im.setImageResource(R.drawable.activate_account);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equals("Profile Manager")) {
            im.setImageResource(R.drawable.profile_manager);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equals("My Network")) {
            im.setImageResource(R.drawable.network_manager);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equals("E-Pin Manager")) {
            im.setImageResource(R.drawable.kyc_manager);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equals("Financial Report")) {
            im.setImageResource(R.drawable.financial_report);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equals("Currency Wallet")) {
            im.setImageResource(R.drawable.current_wallet);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equalsIgnoreCase("Customer Support")) {
            im.setImageResource(R.drawable.customer_support);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equalsIgnoreCase("Promotional Materials")) {
            im.setImageResource(R.drawable.current_wallet);
            im.setImageTintList(blueColorList);
        } else if (headerTitle.equals("Logout")) {
            im.setImageResource(R.drawable.logout);
            im.setImageTintList(blueColorList);
        }

//        if (isExpanded) {
//            img.setImageResource(R.drawable.group_down);
//        } else {
//            groupHolder.img.setImageResource(R.drawable.group_up);
//        }

        if (!headerTitle.equals("Dashboard") && !headerTitle.equals("Logout") && !headerTitle.equals("Activate Account") && !headerTitle.equals("Customer Support") && !headerTitle.equals("Promotional Materials")) {
            if (isExpanded) {
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_less_24, 0);
            } else {
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_more_24, 0);
            }
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}