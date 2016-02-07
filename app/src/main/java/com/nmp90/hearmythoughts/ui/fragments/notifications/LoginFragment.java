package com.nmp90.hearmythoughts.ui.fragments.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.api.UsersAPI;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.stores.UsersStore;
import com.nmp90.hearmythoughts.ui.utils.NavUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nmp on 15-3-2.
 */
public class LoginFragment extends BaseNotificationFragment {
    public static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 0;
    public static final String KEY_LOGOUT = "logout";

    private boolean isLogout;
    private boolean isIntentInProgress;

    private CallbackManager callbackManager;

    @InjectView(R.id.logout_button)
    Button logoutButton;

    @InjectView(R.id.tv_login_required)
    TextView tvWarning;

    @InjectView(R.id.login_button)
    LoginButton loginButton;

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
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getInflatedView(inflater, container, R.layout.fragment_login);
        ButterKnife.inject(this, view);
        loginButton.setReadPermissions("user_friends", "email");
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code

                                JSONObject json = response.getJSONObject();
                                Log.d(TAG, "onCompleted: " + json.toString());
                                try {
                                    String id = json.getString("id");
                                    String name = json.getString("name");
                                    String email = json.getString("email");
                                    Log.d(TAG, "onCompleted: " + email);

                                    if(!isLogout) {
                                        if(isAdded()) {
                                            NavUtils.removeNotificationsFragment(getActivity().getSupportFragmentManager(), LoginFragment.this);
                                            UsersAPI.loginUser(getActivity(), email, name);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG, "onError: " + exception.toString());
            }
        });
        if(isLogout) {
            tvWarning.setText("Are you sure you want to logout?");
            //loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @OnClick(R.id.login_button)
    void login() {
    }

    @OnClick(R.id.logout_button)
    void logout() {
        AuthProvider.getInstance(getActivity()).logout();
        NavUtils.removeNotificationsFragment(getActivity().getSupportFragmentManager(), this);
        UsersStore.getInstance(getActivity()).post(new UsersStore.UserLogoutEvent());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
