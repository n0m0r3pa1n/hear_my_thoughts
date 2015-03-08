package com.nmp90.hearmythoughts.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.providers.FakeDataProvider;
import com.nmp90.hearmythoughts.ui.adapters.RecentSessionsAdapter;
import com.nmp90.hearmythoughts.ui.fragments.notifications.CreateSessionFragment;
import com.nmp90.hearmythoughts.ui.fragments.notifications.JoinSessionFragment;

/**
 * Created by nmp on 15-3-7.
 */
public class RecentSessionsFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = RecentSessionsFragment.class.getSimpleName();

    private Activity activity;
    private FloatingActionButton actionJoin, actionCreate;
    private FloatingActionsMenu actionsMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_sessions, container, false);
        View headerView = inflater.inflate(R.layout.view_header_recent_sessions, container, false);

        actionJoin = (FloatingActionButton) view.findViewById(R.id.action_join);
        actionCreate = (FloatingActionButton) view.findViewById(R.id.action_create);
        actionsMenu = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);

        actionCreate.setOnClickListener(this);
        actionJoin.setOnClickListener(this);

        ListView lvRecentSession = (ListView) view.findViewById(R.id.lv_recent_sessions);
        lvRecentSession.setAdapter(new RecentSessionsAdapter(getActivity(), FakeDataProvider.getRecentSessions()));
        lvRecentSession.addHeaderView(headerView);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.action_join:
                actionsMenu.collapse();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.alpha_out)
                        .add(R.id.container, new JoinSessionFragment()).addToBackStack(Constants.TAG_JOIN_SESSION)
                        .commit();
                break;
            case R.id.action_create:
                actionsMenu.collapse();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.alpha_out)
                        .add(R.id.container, new CreateSessionFragment()).addToBackStack(Constants.TAG_CREATE_SESSION)
                        .commit();
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}
