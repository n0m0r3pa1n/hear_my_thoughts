package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.events.UserLoginEvent;
import com.nmp90.hearmythoughts.events.UserLogoutEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;
import com.nmp90.hearmythoughts.models.Role;
import com.nmp90.hearmythoughts.models.User;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.ui.MainActivity;
import com.nmp90.hearmythoughts.ui.utils.NavUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nmp on 15-3-2.
 */
public class LoginFragment extends BaseNotificationFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, MainActivity.MainActivityResultListener {
    public static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 0;
    public static final String KEY_LOGOUT = "logout";

    private boolean isSignInClicked;
    private boolean isLogout;
    private boolean isIntentInProgress;

    private MainActivity activity;

    private ConnectionResult connectionResult;
    private GoogleApiClient googleApiClient;

    @InjectView(R.id.login_button)
    SignInButton loginButton;

    @InjectView(R.id.logout_button)
    Button logoutButton;

    @InjectView(R.id.tv_login_required)
    TextView tvWarning;

    public static LoginFragment newInstance(boolean isLogout) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_LOGOUT, isLogout);
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogout = this.getArguments().getBoolean(KEY_LOGOUT);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getInflatedView(inflater, container, R.layout.fragment_login);
        ButterKnife.inject(this, view);
        if(isLogout) {
            tvWarning.setText("Are you sure you want to logout?");
            loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @OnClick(R.id.login_button)
    void login() {
        if(!googleApiClient.isConnecting()) {
            isSignInClicked = true;
            resolveSignInError();
        }
    }

    @OnClick(R.id.logout_button)
    void logout() {
        Plus.AccountApi.clearDefaultAccount(googleApiClient);
        AuthProvider.getInstance(getActivity()).logout();
        NavUtils.removeNotificationsFragment(getActivity().getSupportFragmentManager(), this);
        EventBusInstance.getInstance().post(new UserLogoutEvent());
    }

    private void resolveSignInError() {
        if (connectionResult.hasResolution()) {
            try {
                isIntentInProgress = true;
                getActivity().startIntentSenderForResult(connectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated connectionResult.
                isIntentInProgress = false;
                googleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        connectionResult = result;
        if (!isIntentInProgress && result.hasResolution()) {
            try {
                isIntentInProgress = true;
                getActivity().startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated connectionResult.
                isIntentInProgress = false;
                googleApiClient.connect();
            }
        }
    }


    public void onMainActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != Activity.RESULT_OK) {
                isSignInClicked = false;
            }

            isIntentInProgress = false;

            if (!googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
        ((MainActivity) activity).addOnActivityResultListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                googleApiClient.disconnect();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInClicked = false;
        if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null) {
            final Person currentPerson = Plus.PeopleApi.getCurrentPerson(googleApiClient);
            String email = Plus.AccountApi.getAccountName(googleApiClient);

            //EventBusInstance.post(new UserLoginEvent(new User(currentPerson.getDisplayName(), currentPerson.getImage().getUrl(), Role.TEACHER)));
            if(!isLogout) {
                if(isAdded()) {
                    NavUtils.removeNotificationsFragment(getActivity().getSupportFragmentManager(), this);
                    new Handler().postDelayed(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            EventBusInstance.getInstance().post(new UserLoginEvent(new User(currentPerson.getDisplayName(), currentPerson.getImage().getUrl(), Role.TEACHER)));
                        }
                    }), 500);
                }
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }
}
