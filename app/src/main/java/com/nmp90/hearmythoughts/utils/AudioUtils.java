package com.nmp90.hearmythoughts.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by nmp on 15-3-12.
 */
public class AudioUtils {
    private static AudioManager audioManager;
    public static void mute(Context context) {
        if(audioManager == null) {
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }
        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    public static void unmute() {
        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
    }
}
