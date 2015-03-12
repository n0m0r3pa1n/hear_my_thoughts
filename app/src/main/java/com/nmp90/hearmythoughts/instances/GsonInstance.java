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

    private static Gson sGson;

    public static void init() {
        sGson = new Gson();
    }

    public static Gson getInstance() {
        if(sGson == null) {
            init();
        }

        return sGson;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return sGson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return sGson.fromJson(json, typeOfT);
    }

    public static <T> T fromJsonWithToast(Context context, String json, Class<T> classOfT) {
        try {
            return sGson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            DialogUtils.showServerErrorToast(context);
            return null;
        }
    }

    public static String toJson(Serializable dto) {
        return sGson.toJson(dto);
    }

    public static String toJson(Object object) {
        return sGson.toJson(object);
    }
}
