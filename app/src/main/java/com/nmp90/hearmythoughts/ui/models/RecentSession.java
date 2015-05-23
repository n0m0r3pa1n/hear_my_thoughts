package com.nmp90.hearmythoughts.ui.models;

/**
 * Created by nmp on 15-3-8.
 */
public class RecentSession extends BaseModel {
    private String title, lecturer, date, pictureUrl;
    private int participants;

    public RecentSession(String id, String title, String lecturer, String date, String pictureUrl, int participants) {
        this.id = id;
        this.title = title;
        this.lecturer = lecturer;
        this.date = date;
        this.pictureUrl = pictureUrl;
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}
