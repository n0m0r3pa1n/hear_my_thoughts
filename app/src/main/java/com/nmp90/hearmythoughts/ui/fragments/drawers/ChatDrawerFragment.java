package com.nmp90.hearmythoughts.ui.fragments.drawers;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.api.models.User;
import com.nmp90.hearmythoughts.api.models.UsersList;
import com.nmp90.hearmythoughts.api.sockets.ChatConnectionManager;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.ui.adapters.ChatDrawerAdapter;
import com.nmp90.hearmythoughts.ui.models.ChatItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmp on 15-3-14.
 */
public class ChatDrawerFragment extends Fragment implements NavigationDrawerCallbacks, ChatConnectionManager.OnUserChatActionsListener {
    public static final String TAG = ChatDrawerFragment.class.getSimpleName();

    private RecyclerView mDrawerList;
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ChatDrawerAdapter adapter;

    private int mCurrentSelectedPosition;
    private boolean isFirstLaunch = true;

    @Override
    public void onResume() {
        super.onResume();
        ChatConnectionManager.getInstance().addChatListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ChatConnectionManager.getInstance().removeChatListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_drawer, container, false);
        mDrawerList = (RecyclerView) view.findViewById(R.id.chatDrawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);


        selectItem(mCurrentSelectedPosition);
        return view;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.bg_primary_dark_green));
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }

        //((ChatDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

    @Override
    public void onUserJoined(final User user) {
        if(!isAdded())
            return;

        if(user.getId().equals(AuthProvider.getInstance(getActivity()).getUser().getId()))
            return;
        getActivity().runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO replace with user.getIconUrl()
                adapter.addUser(new ChatItem(user.getId(), user.getName(), "https://lh3.googleusercontent.com/-ImUaoqoJX1c/U56YqbZBN-I/AAAAAAAAARE/ewfWFE8GrwA/"));
            }
        }));
    }

    @Override
    public void onUserLeft(final User user) {
        if(!isAdded())
            return;
        getActivity().runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                adapter.removeUser(new ChatItem(user.getId(), user.getName(), user.getIconUrl()));
            }
        }));

    }

    @Override
    public void onInitialUsersListReceived(final UsersList usersList) {
        if(!isAdded())
            return;
        getActivity().runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                final List<ChatItem> navigationItems = new ArrayList<ChatItem>();
                int size = usersList.getUsers().size();
                for (int i = 0; i < size; i++) {
                    User user = usersList.getUsers().get(i);
                    navigationItems.add(new ChatItem(user.getId(), user.getName(), ""));
                }

                adapter = new ChatDrawerAdapter(getActivity(), navigationItems);
                adapter.setNavigationDrawerCallbacks(ChatDrawerFragment.this);
                mDrawerList.setAdapter(adapter);
            }
        }));
    }
}
