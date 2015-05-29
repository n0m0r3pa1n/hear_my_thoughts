package com.nmp90.hearmythoughts.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.api.sockets.ChatConnectionManager;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.providers.SessionProvider;
import com.nmp90.hearmythoughts.ui.fragments.ChatFragment;
import com.nmp90.hearmythoughts.ui.fragments.drawers.ChatDrawerFragment;
import com.nmp90.hearmythoughts.ui.fragments.drawers.NavigationDrawerCallbacks;
import com.nmp90.hearmythoughts.ui.fragments.drawers.NavigationDrawerFragment;
import com.nmp90.hearmythoughts.ui.fragments.notifications.AboutFragment;
import com.nmp90.hearmythoughts.ui.fragments.student.MaterialsStudentFragment;
import com.nmp90.hearmythoughts.ui.fragments.student.StreamStudentFragment;
import com.nmp90.hearmythoughts.ui.fragments.teacher.MaterialsTeacherFragment;
import com.nmp90.hearmythoughts.ui.fragments.teacher.StreamTeacherFragment;
import com.nmp90.hearmythoughts.ui.models.Role;
import com.nmp90.hearmythoughts.utils.WindowUtils;

public class SessionActivity extends ActionBarActivity implements NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ChatDrawerFragment mChatDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setupLollipopScreen(this);
        setContentView(R.layout.activity_session);
        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String sessionTitle = SessionProvider.getInstance(this).getSession().getName();
        if(!TextUtils.isEmpty(sessionTitle)) {
            setTitle(sessionTitle);
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), actionBar);

        mChatDrawerFragment = (ChatDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_chat_drawer);
        mChatDrawerFragment.setup(R.id.fragment_chat_drawer, (DrawerLayout) findViewById(R.id.drawer), actionBar);

        ChatConnectionManager.getInstance().addUserToChat(AuthProvider.getInstance(this).getUser(),
                SessionProvider.getInstance(this).getSession().getShortId());
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
        if(AuthProvider.getInstance(this).getUser().getRole() == Role.TEACHER) {
            switch (position) {
                case 0:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChatFragment(), Constants.TAG_CHAT).commit();
                    break;
                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new StreamTeacherFragment(), Constants.TAG_STREAM).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new MaterialsTeacherFragment(), Constants.TAG_MATERIALS).commit();
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChatFragment(), Constants.TAG_CHAT).commit();
                    break;
                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new StreamStudentFragment(), Constants.TAG_STREAM).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new MaterialsStudentFragment(), Constants.TAG_MATERIALS).commit();
                    break;
            }
        }

        if(position == 3) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new AboutFragment(), Constants.TAG_ABOUT).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        SessionProvider.getInstance(this).clearSession();
        super.onDestroy();
    }
}
