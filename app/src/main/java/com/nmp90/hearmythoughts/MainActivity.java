package com.nmp90.hearmythoughts;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nmp90.hearmythoughts.utils.WindowUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.List;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "sJjsMcgHXdmcfhUaCQV5uBKI9";
    private static final String TWITTER_SECRET = "tvxTEyBHiaPk16xIwyFk8JZm83vDKokuqFc3u4AD5hoqgUK0CF";
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Button btnTranslate;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setupLollipopScreen(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);


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
