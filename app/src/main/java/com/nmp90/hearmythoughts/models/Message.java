package com.nmp90.hearmythoughts.models;

/**
 * Created by nmp on 15-3-11.
 */
public class Message {

    private String message;
    private boolean isMine;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }
}
