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

/**
 * Created by nmp on 15-3-8.
 */
public class JoinSessionFragment extends BaseNotificationFragment implements View.OnClickListener {
    public static final String TAG = JoinSessionFragment.class.getSimpleName();

    private EditText etSessionCode;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getInflatedView(inflater, container, R.layout.fragment_join_session);
        Button btnJoin = (Button) view.findViewById(R.id.btn_join);
        etSessionCode = (EditText) view.findViewById(R.id.et_session_code);

        btnJoin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_join:
                if(isSessionCodeValid()) {
                    openSessionActivity();
                }
                break;
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

    private void openSessionActivity() {
        getActivity().onBackPressed();
        Intent intent = new Intent(getActivity(), SessionActivity.class);
        startActivity(intent);
    }
}
