package com.nmp90.hearmythoughts.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nmp90.hearmythoughts.instances.GsonInstance;

import java.lang.reflect.Type;

/**
 * Created by nmp on 15-3-7.
 */
public class SharedPrefsUtils {
    public static final String TAG = SharedPrefsUtils.class.getSimpleName();

    private static SharedPrefsUtils instance;

    private static Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


    private SharedPrefsUtils(Context ctx) {
        context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SharedPrefsUtils initInstance(Context context) {
        if(instance == null) {
            instance = new SharedPrefsUtils(context);
        }

        return instance;
    }

    @SuppressWarnings (value="unchecked")
    public static <T extends Comparable> T getPreference(String key, T defaultValue) {
        if(defaultValue instanceof String) {
            return (T)sharedPreferences.getString(key, (String)defaultValue);
        } else if(defaultValue instanceof Integer) {
            return (T)((Integer)sharedPreferences.getInt(key, (Integer)defaultValue));
        } else if(defaultValue instanceof Boolean) {
            return (T)((Boolean)sharedPreferences.getBoolean(key, (Boolean)defaultValue));
        } else if(defaultValue instanceof Long){
            return (T)((Long)sharedPreferences.getLong(key, (Long)defaultValue));
        }

        return (T)((Boolean)true);
    }

    public static <T> T getObject(String key, Class<T> classOfT) {
        String value = getPreference(key, "");
        return ((T)GsonInstance.getInstance().fromJson(value, (Type) classOfT));
    }

    public static <T> void setPreference(String key, T value) {
        editor = sharedPreferences.edit();
        if(value instanceof String) {
            Log.d("SET STRING", "STRING");
            editor.putString(key, (String)value).commit();
        } else if(value instanceof Integer) {
            editor.putInt(key, (Integer) value).commit();
        } else if(value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value).commit();
        } else if(value instanceof Long) {
            editor.putLong(key, (Long) value).commit();
        }
    }
}
