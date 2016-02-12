package com.nmp90.hearmythoughts.api.requests.user;

public class UserLoginModel {
    private final String profilePicture;
    private String email;
    private String name;

    public UserLoginModel(String email, String name, String profilePicture) {
        this.email = email;
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
