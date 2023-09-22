package com.alps.shisu.Notification;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alps.shisu.Adapters.NotificationAdapter;
import com.alps.shisu.GetterSetter.NotificationGetterSetter;
import com.alps.shisu.R;

public class NotificationFragment extends Fragment {

    RecyclerView notification_recyclerview;
    NotificationAdapter mAdapter;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_notification, container, false);

        notification_recyclerview = root.findViewById(R.id.notification_recyclerview);

        pastattendenceView();
        return root;
    }

    private void pastattendenceView() {
        NotificationGetterSetter[] instalmentModels = new NotificationGetterSetter[]{
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),
                new NotificationGetterSetter("nbcmnfshm czhjrsghbrfhrmfhjmrggrdghdrz czhfsbvhdgfbshfbvshge"),

        };

        mAdapter = new NotificationAdapter(instalmentModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        notification_recyclerview.setLayoutManager(mLayoutManager);
        notification_recyclerview.setItemAnimator(new DefaultItemAnimator());
        notification_recyclerview.setAdapter(mAdapter);

    }

}
