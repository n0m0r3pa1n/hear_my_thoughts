package com.nmp90.hearmythoughts.ui.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.ui.fragments.notifications.LoginFragment;

/**
 * Created by nmp on 15-3-18.
 */
public class NavUtils {
    public static void showLoginFragment(FragmentManager fm, boolean isLogout) {
                fm
                .beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.alpha_out)
                .add(R.id.container, LoginFragment.newInstance(isLogout))
                .addToBackStack(Constants.TAG_LOGIN)
                .commit();
    }

    public static void removeNotificationsFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.alpha_out, R.anim.abc_fade_in, R.anim.alpha_out)
                .remove(fragment).commit();
        fragmentManager.popBackStack();
    }
}
