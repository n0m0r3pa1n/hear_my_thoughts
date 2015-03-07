package com.nmp90.hearmythoughts.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.ui.fragments.LoginFragment;
import com.nmp90.hearmythoughts.ui.fragments.RecentSessionsFragment;
import com.nmp90.hearmythoughts.utils.WindowUtils;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Button btnTranslate;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setupLollipopScreen(this);
        setContentView(R.layout.activity_main);
        Toolbar actionBar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(actionBar);

        if(AuthProvider.isUserLoggedIn() == false) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment(), Constants.TAG_LOGIN).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new RecentSessionsFragment(), Constants.TAG_RECENT_SESSIONS).commit();
        }
//        btnTranslate = (Button) findViewById(R.id.btn_translate);
//        tvText = (TextView) findViewById(R.id.textView);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {

            //speakButton.setOnClickListener(this);
        } else {
            Toast.makeText(this, "Your phone does not support speech recognition", Toast.LENGTH_LONG).show();
//            speakButton.setEnabled(false);
//            speakButton.setText("Recognizer not present");
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
}
