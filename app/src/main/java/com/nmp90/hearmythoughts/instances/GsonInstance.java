package com.nmp90.hearmythoughts.instances;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nmp90.hearmythoughts.utils.DialogUtils;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by nmp on 15-3-12.
 */
public class GsonInstance {

    private static GsonInstance gsonInstance;
    private Gson sGson;
    private GsonInstance() {
        sGson = new Gson();
    }


    public static GsonInstance getInstance() {
        if(gsonInstance == null) {
            gsonInstance = new GsonInstance();
        }

        return gsonInstance;
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return sGson.fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return sGson.fromJson(json, typeOfT);
    }

    public <T> T fromJsonWithToast(Context context, String json, Class<T> classOfT) {
        try {
            return sGson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            DialogUtils.showServerErrorToast(context);
            return null;
        }
    }

    public String toJson(Serializable dto) {
        return sGson.toJson(dto);
    }

    public String toJson(Object object) {
        return sGson.toJson(object);
    }
}
