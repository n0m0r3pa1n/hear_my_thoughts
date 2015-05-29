package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.app.Activity;
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
import com.nmp90.hearmythoughts.api.SessionsAPI;
import com.nmp90.hearmythoughts.events.ErrorEvent;
import com.nmp90.hearmythoughts.providers.SessionProvider;
import com.nmp90.hearmythoughts.stores.SessionsStore;
import com.nmp90.hearmythoughts.ui.SessionActivity;
import com.squareup.otto.Subscribe;

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
            //onSessionJoin(new SessionsStore.SessionJoinedEvent(new Session("test", "test")));
            SessionsAPI.joinSession(getActivity(), etSessionCode.getText().toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SessionsStore.getInstance().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SessionsStore.getInstance().unregister(this);
    }

    @Subscribe
    public void onSessionJoin(SessionsStore.SessionJoinedEvent event) {
        getActivity().onBackPressed();
        SessionProvider.getInstance(getActivity()).setSession(event.getSession());

        Intent intent = new Intent(getActivity(), SessionActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void onError(ErrorEvent event) {
        etSessionCode.setError("Wrong session code!");
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
