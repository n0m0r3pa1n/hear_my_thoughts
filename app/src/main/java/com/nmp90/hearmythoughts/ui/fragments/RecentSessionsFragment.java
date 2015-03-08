package com.nmp90.hearmythoughts.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.providers.FakeDataProvider;
import com.nmp90.hearmythoughts.ui.adapters.RecentSessionsAdapter;

/**
 * Created by nmp on 15-3-7.
 */
public class RecentSessionsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_sessions, container, false);
        View headerView = inflater.inflate(R.layout.view_header_recent_sessions, container, false);

        ListView lvRecentSession = (ListView) view.findViewById(R.id.lv_recent_sessions);
        lvRecentSession.setAdapter(new RecentSessionsAdapter(getActivity(), FakeDataProvider.getRecentSessions()));
        lvRecentSession.addHeaderView(headerView);
        return view;
    }
}
