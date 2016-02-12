package com.nmp90.hearmythoughts.api.models;

import com.nmp90.hearmythoughts.instances.GsonInstance;
import com.nmp90.hearmythoughts.ui.models.BaseModel;
import com.nmp90.hearmythoughts.ui.models.Role;

/**
 * Created by nmp on 15-3-12.
 */
public class User extends BaseModel {
    private String name, profilePicture;
    private Role role;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    @Override
    public String toString() {
        return GsonInstance.getInstance().toJson(this);
    }
}
