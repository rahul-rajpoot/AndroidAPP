package com.alps.shisu.KycManager;


import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alps.shisu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPinFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RadioGroup rg;
    CardView hscard;
    ImageView Show,Hide;
    RadioButton available_alloted2, all, available_soldbutnotjoin;
    public ListPinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_pin, container, false);
        hscard= view.findViewById(R.id.hideshow);
        Show= view.findViewById(R.id.opencardlistpin);
        Hide= view.findViewById(R.id.closecardlistpin);
        available_alloted2= view.findViewById(R.id.available_alloted2);
        all= view.findViewById(R.id.all);
        available_soldbutnotjoin= view.findViewById(R.id.available_soldbutnotjoin);

        Hide.setOnClickListener(v -> {
            Hide.setVisibility(View.GONE);
            Show.setVisibility(View.VISIBLE);
            hscard.setVisibility(View.GONE);
        });
        Show.setOnClickListener(v -> {
            Hide.setVisibility(View.VISIBLE);
            Show.setVisibility(View.GONE);
            hscard.setVisibility(View.VISIBLE);
        });
        rg=view.findViewById(R.id.rg);
        rg.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                //All
                case R.id.all:
                    setFragment(new AllListPinFragment());
                    break;
                  // Alloted/ Used
                case R.id.available_soldbutnotjoin:
                    setFragment(new AllotedListPinFragment());
                    break;
                //Available
                case  R.id.available_alloted2:
                   setFragment(new SoldButNotJoinListPinFragment());
                    break;
            }
        });

        available_alloted2.setChecked(true);

        return view;
    }
    public void setFragment(Fragment f){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();

        // ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        ft.replace(R.id.frame,f);
        ft.commit();
    }
}
