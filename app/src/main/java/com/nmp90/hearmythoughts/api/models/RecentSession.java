package com.nmp90.hearmythoughts.api.models;

import com.nmp90.hearmythoughts.ui.models.BaseModel;

import java.util.List;

/**
 * Created by nmp on 15-3-8.
 */
public class RecentSession extends BaseModel {
    private String name, createdAt, pictureUrl, shortId;
    private User lecturer;
    private List<User> participants;

    public RecentSession(String id, String name, User lecturer, String createdAt, String pictureUrl, String shortId, List<User> participants) {
        this.id = id;
        this.name = name;
        this.lecturer = lecturer;
        this.createdAt = createdAt;
        this.pictureUrl = pictureUrl;
        this.shortId = shortId;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public User getLecturer() {
        return lecturer;
    }

    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }
}
