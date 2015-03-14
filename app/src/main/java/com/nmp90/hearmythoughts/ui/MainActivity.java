package com.nmp90.hearmythoughts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.instances.EventBusInstance;
import com.nmp90.hearmythoughts.ui.fragments.RecentSessionsFragment;
import com.nmp90.hearmythoughts.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Button btnTranslate;
    private TextView tvText;


    private List<MainActivityResultListener> onActivityResultListeners = new ArrayList<MainActivityResultListener>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setupLollipopScreen(this);
        setContentView(R.layout.activity_main);
        Toolbar actionBar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionBar);

        openRecentSessions();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBusInstance.unregister(this);
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
