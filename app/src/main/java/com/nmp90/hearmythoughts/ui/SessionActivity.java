package com.nmp90.hearmythoughts.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.ui.fragments.ChatFragment;
import com.nmp90.hearmythoughts.ui.fragments.NavigationDrawerCallbacks;
import com.nmp90.hearmythoughts.ui.fragments.NavigationDrawerFragment;
import com.nmp90.hearmythoughts.ui.fragments.teacher.MaterialsTeacherFragment;
import com.nmp90.hearmythoughts.utils.WindowUtils;

public class SessionActivity extends ActionBarActivity implements NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setupLollipopScreen(this);
        setContentView(R.layout.activity_session);
        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), actionBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_session, menu);
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
    public void onNavigationDrawerItemSelected(int position) {
        switch(position) {
            case 0:
                ChatFragment fragment = new ChatFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, Constants.TAG_CHAT).commit();
                break;
            case 1:
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MaterialsTeacherFragment(), Constants.TAG_MATERIALS).commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}
