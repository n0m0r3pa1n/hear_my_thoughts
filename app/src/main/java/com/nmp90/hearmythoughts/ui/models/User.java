package com.nmp90.hearmythoughts.ui.models;

import com.nmp90.hearmythoughts.api.models.Token;
import com.nmp90.hearmythoughts.instances.GsonInstance;

/**
 * Created by nmp on 15-3-12.
 */
public class User extends BaseModel {
    private String name, iconUrl;
    private Role role;
    private Token token;

    public User(String name, String iconUrl, Role role, Token token) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.role = role;
        this.token = token;
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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return GsonInstance.getInstance().toJson(this);
    }
}
