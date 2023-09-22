package com.alps.shisu.Shopping;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alps.shisu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepurchaseOrderFragment extends Fragment {


    public RepurchaseOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repurchase_order, container, false);
    }

}
