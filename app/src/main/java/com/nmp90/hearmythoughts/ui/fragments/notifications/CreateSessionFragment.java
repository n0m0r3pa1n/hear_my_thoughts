package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmp90.hearmythoughts.R;

/**
 * Created by nmp on 15-3-8.
 */
public class CreateSessionFragment extends BaseNotificationFragment {
    public static final String TAG = CreateSessionFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getInflatedView(inflater, container, R.layout.fragment_create_session);

        return view;
    }
}
