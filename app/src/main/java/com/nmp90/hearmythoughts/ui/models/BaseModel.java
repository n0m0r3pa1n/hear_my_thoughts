package com.nmp90.hearmythoughts.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nmp on 15-3-8.
 */
public abstract class BaseModel {
    @SerializedName("_id")
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
