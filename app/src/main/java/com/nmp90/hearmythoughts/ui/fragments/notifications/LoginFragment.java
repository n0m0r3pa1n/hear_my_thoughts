package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.utils.SharedPrefsUtils;

/**
 * Created by nmp on 15-3-2.
 */
public class LoginFragment extends BaseNotificationFragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 0;

    private boolean isSignInClicked;
    private boolean isIntentInProgress;

    private ConnectionResult connectionResult;
    private GoogleApiClient googleApiClient;

    private SignInButton loginButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        loginButton = (SignInButton)
                view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_button
                && !googleApiClient.isConnecting()) {
            isSignInClicked = true;
            resolveSignInError();
        }
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
    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
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
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInClicked = false;
        if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(googleApiClient);
            String email = Plus.AccountApi.getAccountName(googleApiClient);
            SharedPrefsUtils.setPreference(Constants.KEY_USER_EMAIL, email);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }
}
