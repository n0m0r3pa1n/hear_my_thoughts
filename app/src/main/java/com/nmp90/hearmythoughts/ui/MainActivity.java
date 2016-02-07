package com.nmp90.hearmythoughts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.api.SessionsAPI;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.stores.UsersStore;
import com.nmp90.hearmythoughts.ui.fragments.RecentSessionsFragment;
import com.nmp90.hearmythoughts.ui.utils.NavUtils;
import com.nmp90.hearmythoughts.utils.WindowUtils;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private List<MainActivityResultListener> onActivityResultListeners = new ArrayList<MainActivityResultListener>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setupLollipopScreen(this);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionBar);

        openRecentSessions();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    private void openRecentSessions() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.alpha_out)
                .add(R.id.container, new RecentSessionsFragment(), Constants.TAG_RECENT_SESSIONS)
                .commit();
    }

    private boolean isRecentSessionsVisible() {
        Fragment recentSessions = getSupportFragmentManager().findFragmentByTag(Constants.TAG_RECENT_SESSIONS);
        return (recentSessions != null) && (recentSessions.isVisible());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(onActivityResultListeners.size() > 0) {
            for(int i=0; i < onActivityResultListeners.size(); i++) {
                onActivityResultListeners.get(i).onMainActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(AuthProvider.getInstance(this).isUserLoggedIn()) {
            getMenuInflater().inflate(R.menu.menu_main_logout, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.action_login:
                NavUtils.showLoginFragment(getSupportFragmentManager(), false);
                break;
            case R.id.action_logout:
                NavUtils.showLoginFragment(getSupportFragmentManager(), true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        UsersStore.getInstance(this).register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SessionsAPI.getRecentSessions(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        UsersStore.getInstance(this).unregister(this);
    }

    @Subscribe
    public void userLoggedOut(UsersStore.UserLogoutEvent event) {
        supportInvalidateOptionsMenu();
    }

    public void addOnActivityResultListener(MainActivityResultListener listener) {
        onActivityResultListeners.add(listener);
    }

    public void removeOnActivityResultListener(MainActivityResultListener listener) {
        onActivityResultListeners.remove(listener);
    }

    public interface MainActivityResultListener {
        void onMainActivityResult(int requestCode, int resultCode, Intent data);
    }
}
