package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.ui.SessionActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nmp on 15-3-8.
 */
public class JoinSessionFragment extends BaseNotificationFragment {
    public static final String TAG = JoinSessionFragment.class.getSimpleName();

    @InjectView(R.id.et_session_code)
    EditText etSessionCode;
    @InjectView(R.id.btn_join)
    Button btnJoin;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getInflatedView(inflater, container, R.layout.fragment_join_session);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.btn_join)
    public void openSessionActivity() {
        if(isSessionCodeValid()) {
            getActivity().onBackPressed();
            Intent intent = new Intent(getActivity(), SessionActivity.class);
            startActivity(intent);
        }
    }

    private boolean isSessionCodeValid() {
        String sessionCode = etSessionCode.getText().toString();
        if(TextUtils.isEmpty(sessionCode)) {
            etSessionCode.setError("Empty string is not allowed!");
            return false;
        }

        return true;
    }
}
