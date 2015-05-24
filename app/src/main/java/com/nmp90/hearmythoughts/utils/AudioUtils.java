package com.nmp90.hearmythoughts.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.nmp90.hearmythoughts.R;

/**
 * Created by nmp on 15-3-12.
 */
public class AudioUtils {
    public static final String TAG = AudioUtils.class.getSimpleName();
    private static AudioManager audioManager;
    private static MediaPlayer mp;

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

    public static void playPopSound(Context context) {
        if(context == null)
            return;

        mp = MediaPlayer.create(context, R.raw.pop);
        if(mp == null)
            return;

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                try {
                    mp.reset();
                    mp.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }
        });
        mp.start();
    }
}
