package com.nmp90.hearmythoughts.providers;

import android.content.Context;

import com.nmp90.hearmythoughts.api.models.Session;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.instances.GsonInstance;
import com.nmp90.hearmythoughts.utils.SharedPrefsUtils;

/**
 * Created by nmp on 15-5-28.
 */
public class SessionProvider {
    private static SessionProvider sessionProvider;
    private Context context;

    public SessionProvider(Context context) {
        this.context = context;
    }

    public static SessionProvider getInstance(Context context) {
        if(sessionProvider == null) {
            sessionProvider = new SessionProvider(context);
        }
        return sessionProvider;
    }

    public void setSession(Session session) {
        SharedPrefsUtils.getInstance(context).setPreference(Constants.KEY_SESSION, GsonInstance.getInstance().toJson(session));
    }

    public Session getSession() {
        return GsonInstance.getInstance().fromJson(SharedPrefsUtils.getInstance(context).getPreference(Constants.KEY_SESSION, ""), Session.class);
    }

    public void clearSession() {
        SharedPrefsUtils.getInstance(context).setPreference(Constants.KEY_SESSION, "");
    }
}
