package com.nmp90.hearmythoughts.api.models;

import java.util.List;

/**
 * Created by nmp on 15-5-29.
 */
public class UsersList {
    private List<User> users;

    public UsersList(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
