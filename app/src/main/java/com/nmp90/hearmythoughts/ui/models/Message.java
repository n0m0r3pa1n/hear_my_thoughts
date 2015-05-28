package com.nmp90.hearmythoughts.ui.models;

/**
 * Created by nmp on 15-3-11.
 */
public class Message {
    private User user;
    private String message;
    private boolean isMine;

    public Message() {}

    public Message(String message) {
        this.message = message;
    }

    public Message(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMine() {
        return isMine;
//        if(TextUtils.isEmpty(user.getName()))
//            return false;
//
//        return "Georgi Mirchev".equals(user.getName());
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }
}
