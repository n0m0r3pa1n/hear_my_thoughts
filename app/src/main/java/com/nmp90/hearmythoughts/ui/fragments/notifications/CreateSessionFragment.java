package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.api.SessionsAPI;
import com.nmp90.hearmythoughts.api.models.User;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.providers.SessionProvider;
import com.nmp90.hearmythoughts.stores.SessionsStore;
import com.nmp90.hearmythoughts.ui.SessionActivity;
import com.nmp90.hearmythoughts.ui.models.Role;
import com.nmp90.hearmythoughts.utils.SharedPrefsUtils;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nmp on 15-3-8.
 */
public class CreateSessionFragment extends BaseNotificationFragment {
    public static final String TAG = CreateSessionFragment.class.getSimpleName();

    @InjectView(R.id.et_session_title)
    EditText etSessionTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getInflatedView(inflater, container, R.layout.fragment_create_session);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.btn_create)
    void openSessionActivity() {
        if(isSessionCodeValid()) {
            SessionsAPI.createSession(getActivity(), etSessionTitle.getText().toString());
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
    public void onSessionCreated(SessionsStore.SessionCreatedEvent event) {
        AuthProvider authProvider = AuthProvider.getInstance(getActivity());
        User user = authProvider.getUser();
        user.setRole(Role.TEACHER);
        authProvider.login(user);

        SessionProvider.getInstance(getActivity()).setSession(event.getSession());
        getActivity().onBackPressed();
        Intent intent = new Intent(getActivity(), SessionActivity.class);
        startActivity(intent);
    }

    private boolean isSessionCodeValid() {
        String sessionCode = etSessionTitle.getText().toString();
        if(TextUtils.isEmpty(sessionCode)) {
            etSessionTitle.setError("Empty string is not allowed!");
            return false;
        }

        SharedPrefsUtils.getInstance(getActivity()).setPreference(Constants.KEY_SESSION_TITLE, sessionCode);

        return true;
    }
}
