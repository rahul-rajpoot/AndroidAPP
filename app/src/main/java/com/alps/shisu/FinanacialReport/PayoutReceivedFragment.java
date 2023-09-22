package com.alps.shisu.FinanacialReport;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alps.shisu.R;

/**
 * A simple {@link Fragment} subclass.View
 */
public class PayoutReceivedFragment extends Fragment {


    public PayoutReceivedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_payout_received, container, false);
    }

}
