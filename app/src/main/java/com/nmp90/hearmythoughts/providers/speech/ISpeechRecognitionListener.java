package com.nmp90.hearmythoughts.providers.speech;

import java.util.ArrayList;

/**
 * Created by nmp on 15-3-12.
 */
public interface ISpeechRecognitionListener {
    public void onDictationStart();
    public void onResults(ArrayList<String> dictationResults);
    public void onDictationFinish();
}
