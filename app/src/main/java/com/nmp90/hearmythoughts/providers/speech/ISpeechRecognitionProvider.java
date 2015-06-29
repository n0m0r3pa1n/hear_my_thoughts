package com.nmp90.hearmythoughts.providers.speech;

import android.content.Context;

/**
 * Created by nmp on 15-3-12.
 */
public interface ISpeechRecognitionProvider {
    void startRecognition(Context context, ISpeechRecognitionListener listener);
    boolean isStarted();
    void stopRecognition();
}
