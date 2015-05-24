package com.nmp90.hearmythoughts.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
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
import com.nmp90.hearmythoughts.ui.models.Message;
import com.nmp90.hearmythoughts.ui.views.CircleImageView;
import com.nmp90.hearmythoughts.utils.AudioUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by nmp on 15-3-11.
 */
public class MessagesAdapter extends BaseAdapter implements Animation.AnimationListener {

    public static final String TAG = MessagesAdapter.class.getSimpleName();

    private Context context;
    private List<Message> messages = new ArrayList<Message>();

    private Animation slideUp;

    private boolean isLastItemAnimated = true;

    private ListView parent;

    static class ViewHolder {
        @InjectView(R.id.message)
        TextView messageTv;

        @Optional
        @InjectView(R.id.tv_sender)
        TextView sender;

        @InjectView(R.id.avatar)
        CircleImageView avatar;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public MessagesAdapter(Context context) {
        this.context = context;

        slideUp = AnimationUtils.loadAnimation(this.context, R.anim.slide_up);
        if (slideUp != null) {
            slideUp.setAnimationListener(this);
            slideUp.setDuration(500);
        }
    }

    public MessagesAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;

        slideUp = AnimationUtils.loadAnimation(this.context, R.anim.slide_up);
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
            LayoutInflater inflater = LayoutInflater.from(context);

            if (message.isMine()) {
                view = inflater.inflate(R.layout.view_msg_left, viewGroup, false);
            } else {
                view = inflater.inflate(R.layout.view_msg_right, viewGroup, false);
            }

            viewHolder = new ViewHolder(view);

            if (view != null) {
                view.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(!TextUtils.isEmpty(message.getUser().getIconUrl())) {
            Picasso.with(context).load(message.getUser().getIconUrl()).into(viewHolder.avatar);
        }

        if(viewHolder.sender != null) {
            viewHolder.sender.setText(message.getUser().getName().split(" ")[0]);
        }
        viewHolder.messageTv.setText(message.getMessage());
        Linkify.addLinks(viewHolder.messageTv, Linkify.ALL);

        if (position == getCount() - 1 && !isLastItemAnimated && view != null)
            view.startAnimation(slideUp);

        return view;
    }

    public void addMessage(Message msg) {
        messages.add(msg);

        isLastItemAnimated = false;
        notifyDataSetChanged();

        AudioUtils.playPopSound(context);
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
