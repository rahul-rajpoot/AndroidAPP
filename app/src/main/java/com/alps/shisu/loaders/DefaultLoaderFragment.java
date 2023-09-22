//package com.alps.aplusmart.loaders;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.DialogFragment;
//
//import com.alps.aplusmart.R;
//
///**
// * It will show the a loader for any delay in response from server
// *
// * @Author Anhsu Verma
// */
//
//public class DefaultLoaderFragment extends DialogFragment {
//
//    public DefaultLoaderFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light);
//        setCancelable(false);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_default_loader, container, false);
//        return view;
//    }
//}