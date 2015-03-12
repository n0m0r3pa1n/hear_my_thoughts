package com.nmp90.hearmythoughts.providers.speech;

/**
 * Created by nmp on 15-3-12.
 */
public class SpeechRecognitionProvider  {
    private static ISpeechRecognitionProvider provider;
    public static ISpeechRecognitionProvider getSpeechRecognition() {
        if(provider == null) {
            provider = new GoogleSpeechRecognitionProvider();
        }

        return provider;
    }
}
