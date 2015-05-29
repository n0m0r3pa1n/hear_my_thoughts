package com.nmp90.hearmythoughts.ui.fragments.drawers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.ui.models.NavigationItem;
import com.nmp90.hearmythoughts.ui.adapters.NavigationDrawerAdapter;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREFERENCES_FILE = "my_app_settings"; //TODO: change this to your file

    public static final String TAG = NavigationDrawerFragment.class.getSimpleName();

    private NavigationDrawerCallbacks mCallbacks;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);

        final List<NavigationItem> navigationItems = getMenu();
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationItems);
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
        selectItem(mCurrentSelectedPosition);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mActionBarDrawerToggle;
    }

    public void setActionBarDrawerToggle(ActionBarDrawerToggle actionBarDrawerToggle) {
        mActionBarDrawerToggle = actionBarDrawerToggle;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.bg_primary_dark_green));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(drawerView.getId() == R.id.fragment_drawer) {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if(drawerView.getId() == R.id.fragment_drawer) {
                    super.onDrawerOpened(drawerView);
                    if (!isAdded()) return;
                    if (!mUserLearnedDrawer) {
                        mUserLearnedDrawer = true;
                        saveSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "true");
                    }

                    getActivity().invalidateOptionsMenu();
                }
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState)
            mDrawerLayout.openDrawer(mFragmentContainerView);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<NavigationItem>();
        items.add(new NavigationItem("Chat", getResources().getDrawable(R.drawable.ic_message)));
        items.add(new NavigationItem("Stream", getResources().getDrawable(R.drawable.ic_stream)));
        items.add(new NavigationItem("Materials", getResources().getDrawable(R.drawable.ic_materials)));
        items.add(new NavigationItem("About", getResources().getDrawable(R.drawable.ic_about)));
        return items;
    }

    void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }

        ((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }
}