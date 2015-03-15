package com.nmp90.hearmythoughts.models;

/**
 * Created by nmp on 15-3-14.
 */
public class ChatItem {
    private String mText;
    private String drawableUrl;
    public ChatItem(String text, String drawable) {
        mText = text;
        drawableUrl = drawable;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getDrawableUrl() {
        return drawableUrl;
    }

    public void setDrawableUrl(String drawable) {
        drawableUrl = drawable;
    }
}
