package com.nmp90.hearmythoughts.ui.models;

/**
 * Created by nmp on 15-3-14.
 */
public class ChatItem {
    private String userId;
    private String text;
    private String drawableUrl;
    public ChatItem(String userId, String text, String drawable) {
        this.userId = userId;
        this.text = text;
        drawableUrl = drawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDrawableUrl() {
        return drawableUrl;
    }

    public void setDrawableUrl(String drawable) {
        drawableUrl = drawable;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
