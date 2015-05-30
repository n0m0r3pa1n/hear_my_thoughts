package com.nmp90.hearmythoughts.ui.models;

import com.nmp90.hearmythoughts.api.models.User;

/**
 * Created by nmp on 15-5-30.
 */
public class ChatMessage {
    private User user;
    private String text;

    public ChatMessage(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
