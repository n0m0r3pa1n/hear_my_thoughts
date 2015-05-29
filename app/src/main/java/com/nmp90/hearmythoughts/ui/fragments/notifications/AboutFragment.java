package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.providers.SessionProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nmp on 15-5-29.
 */
public class AboutFragment extends BaseNotificationFragment {

    @InjectView(R.id.tv_session_code)
    TextView tvSessionCode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getInflatedView(inflater, container, R.layout.fragment_about);
        ButterKnife.inject(this, view);

        tvSessionCode.setText("Session Code: " + SessionProvider.getInstance(getActivity()).getSession().getShortId());
        return view;
    }
}
