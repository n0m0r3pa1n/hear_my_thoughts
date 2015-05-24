package com.nmp90.hearmythoughts.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.ui.models.ChatItem;
import com.nmp90.hearmythoughts.ui.fragments.drawers.NavigationDrawerCallbacks;
import com.nmp90.hearmythoughts.ui.views.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ChatDrawerAdapter extends RecyclerView.Adapter<ChatDrawerAdapter.ViewHolder> {
    private Context context;

    private List<ChatItem> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public ChatDrawerAdapter(Context context, List<ChatItem> data) {
        this.context = context;
        mData = data;
    }

    public void addUser(ChatItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void removeUser(ChatItem item) {
        for (int i = 0; i < getItemCount(); i++) {
            if(item.getText().equals(mData.get(i).getText())) {
                mData.remove(i);
                break;
            }
        }

        notifyDataSetChanged();
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public ChatDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_chat, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatDrawerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(mData.get(i).getText());


        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                                                   @Override
                                                   public boolean onTouch(View v, MotionEvent event) {

                                                       switch (event.getAction()) {
                                                           case MotionEvent.ACTION_DOWN:
                                                               touchPosition(i);
                                                               return false;
                                                           case MotionEvent.ACTION_CANCEL:
                                                               touchPosition(-1);
                                                               return false;
                                                           case MotionEvent.ACTION_MOVE:
                                                               return false;
                                                           case MotionEvent.ACTION_UP:
                                                               touchPosition(-1);
                                                               return false;
                                                       }
                                                       return true;
                                                   }
                                               }
        );
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       if (mNavigationDrawerCallbacks != null)
                                                           mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(i);
                                                   }
                                               }
        );

        if(!TextUtils.isEmpty(mData.get(i).getDrawableUrl())) {
            Picasso.with(context).load(mData.get(i).getDrawableUrl()).resize(100, 100).centerInside().into(viewHolder.ivPicture);
        }

        if (mSelectedPosition == i || mTouchedPosition == i) {
            viewHolder.textView.setTextColor(Color.parseColor("#FF5722"));
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.selected_gray));
        } else {
            viewHolder.textView.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition);
        if (position >= 0)
            notifyItemChanged(position);
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.item_name)
        public TextView textView;

        @InjectView(R.id.iv_user_picture)
        public CircleImageView ivPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}