package com.nmp90.hearmythoughts.ui.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmp on 15-3-11.
 */
public class MessagesAdapter extends BaseAdapter implements Animation.AnimationListener {

    public static final String TAG = MessagesAdapter.class.getSimpleName();

    private Context ctx;
    private List<Message> messages = new ArrayList<Message>();

    private MediaPlayer mp;
    private Animation slideUp;

    private boolean isLastItemAnimated = true;

    private ListView parent;

    static class ViewHolder {
        TextView messageTv;
    }

    public MessagesAdapter(Context ctx) {
        this.ctx = ctx;

        slideUp = AnimationUtils.loadAnimation(this.ctx, R.anim.slide_up);
        if (slideUp != null) {
            slideUp.setAnimationListener(this);
            slideUp.setDuration(500);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        this.parent = (ListView) viewGroup;

        View view = convertView;
        ViewHolder viewHolder;

        Message message = messages.get(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(ctx);

            if (message.isMine()) {
                view = inflater.inflate(R.layout.view_msg_left, viewGroup, false);
            } else {
                view = inflater.inflate(R.layout.view_msg_right, viewGroup, false);
            }

            viewHolder = new ViewHolder();

            if (view != null) {
                viewHolder.messageTv = (TextView) view.findViewById(R.id.message);

                view.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.messageTv.setText(messages.get(position).getMessage());
        Linkify.addLinks(viewHolder.messageTv, Linkify.ALL);

        if (position == getCount() - 1 && !isLastItemAnimated && view != null)
            view.startAnimation(slideUp);

        return view;
    }

    public void addMessage(Message msg) {
        messages.add(msg);

        isLastItemAnimated = false;
        notifyDataSetChanged();

        playPopSound();
    }

    private void playPopSound() {
        if(ctx == null)
            return;

        mp = MediaPlayer.create(ctx, R.raw.pop);
        if(mp == null)
            return;

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.reset();
                mp.release();
            }
        });
        mp.start();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).isMine()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        isLastItemAnimated = false;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isLastItemAnimated = true;
        parent.setSelection(getCount() - 1);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
