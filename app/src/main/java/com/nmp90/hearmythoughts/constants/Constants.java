package com.nmp90.hearmythoughts.constants;

/**
 * Created by nmp on 2/21/15.
 */
public interface Constants {
    int SPEECH_COMPLETE_SILENCE = 1000;
    int SPEECH_COMPLETE_POSSIBLY_COMPLETE_SILEMNCE = 1000;
    int SPEECH_MINIMUM_LENGTH = 2000;
    int SPEECH_MAX_RESULTS = 5;
    String SPEECH_DEFAULT_LANGUAGE = "en";
    String SPEECH_BULGARIAN_LANGUAGE = "bg";

    String KEY_USER_EMAIL = "user_emal";
    String KEY_USER = "user";
    String KEY_SESSION = "session";
    String KEY_SESSION_TITLE = "session_title";

    String TAG_RECENT_SESSIONS = "RecentSessionsFragment";
    String TAG_LOGIN = "LoginFragment";
    String TAG_JOIN_SESSION  = "JoinSessionFragment";
    String TAG_CREATE_SESSION = "CreateSessionFragment";
    String TAG_CHAT = "ChatFragment";
    String TAG_ABOUT = "AboutFragment";

    String TAG_MATERIALS = "MaterialsFragment";
    String TAG_STREAM = "StreamFragment";


    String BASE_URL_PORT = ":8080";
    String BASE_SERVER_URL = "http://hear-my-thoughts.herokuapp.com";
//    String BASE_SERVER_URL = "http://localhost";
//    String BASE_SERVER_URL = "http://10.0.3.2";
    String BASE_URL = BASE_SERVER_URL;
}
