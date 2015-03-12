package com.nmp90.hearmythoughts.models;

import com.nmp90.hearmythoughts.instances.GsonInstance;

/**
 * Created by nmp on 15-3-12.
 */
public class User extends BaseModel {
    private String name, iconUrl;
    private Role role;

    public User(String name, String iconUrl, Role role) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return GsonInstance.toJson(this);
    }
}
