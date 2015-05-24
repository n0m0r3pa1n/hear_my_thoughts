package com.nmp90.hearmythoughts.api.models;

/**
 * Created by nmp on 15-5-24.
 */
public class Session {
    private String shortId;
    private String name;

    public Session(String shortId, String name) {
        this.shortId = shortId;
        this.name = name;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
