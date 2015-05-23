package com.nmp90.hearmythoughts.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.ui.models.RecentSession;
import com.nmp90.hearmythoughts.ui.views.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by nmp on 15-3-8.
 */
public class RecentSessionsAdapter extends BaseAdapter {
    private Context context;
    private List<RecentSession> sessions;
    private LayoutInflater inflater;

    public RecentSessionsAdapter(Context context, List<RecentSession> sessions) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sessions = sessions;
    }

    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public RecentSession getItem(int position) {
        return sessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        RecentSession session = sessions.get(position);
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_recent_session, null);
            viewHolder.tvLecturer = (TextView) convertView.findViewById(R.id.tv_lecturer);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tvParticipants = (TextView) convertView.findViewById(R.id.tv_participants);
            viewHolder.ivLecturerPicture = (CircleImageView) convertView.findViewById(R.id.iv_lecturer_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvLecturer.setText(session.getLecturer());
        viewHolder.tvTitle.setText(session.getTitle());
        viewHolder.tvDate.setText(session.getDate());
        viewHolder.tvParticipants.setText(session.getParticipants() + "");
        Picasso.with(context)
                .load(session.getPictureUrl())
                .into(viewHolder.ivLecturerPicture);

        return convertView;
    }

    public static class ViewHolder {
        TextView tvTitle, tvLecturer, tvParticipants, tvDate;
        CircleImageView ivLecturerPicture;
    }
}
