package com.nmp90.hearmythoughts.events;

import com.nmp90.hearmythoughts.models.User;

/**
 * Created by nmp on 15-3-12.
 */
public class UserLoginEvent {
    public UserLoginEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;
}
