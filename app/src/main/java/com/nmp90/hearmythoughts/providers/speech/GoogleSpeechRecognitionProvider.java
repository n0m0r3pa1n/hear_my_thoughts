package com.nmp90.hearmythoughts.providers.speech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.nmp90.hearmythoughts.constants.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nmp on 15-3-12.
 */
public class GoogleSpeechRecognitionProvider implements RecognitionListener, ISpeechRecognitionProvider {
    public static final String TAG = GoogleSpeechRecognitionProvider.class.getSimpleName();

    private boolean isRunning = false;

    private SpeechRecognizer speech = null;
    private Timer speechTimeout = null;
    private Context context;
    private ISpeechRecognitionListener callback;

    @Override
    public void startRecognition(Context context, ISpeechRecognitionListener callback) {
        this.callback = callback;
        this.context = context;
        isRunning = true;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Constants.SPEECH_DEFAULT_LANGUAGE);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, Constants.SPEECH_MAX_RESULTS);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Constants.SPEECH_COMPLETE_SILENCE);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, Constants.SPEECH_COMPLETE_POSSIBLY_COMPLETE_SILEMNCE);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, Constants.SPEECH_MINIMUM_LENGTH);
        getSpeechRecognizer().startListening(intent);
    }

    @Override
    public boolean isStarted() {
        return isRunning;
    }

    @Override
    public void stopRecognition() {
        isRunning = false;
        if(speechTimeout != null) {
            speechTimeout.cancel();
        }
        if (speech != null) {
            speech.destroy();

            speech = null;
        }
    }

    public class SilenceTimer extends TimerTask {
        @Override
        public void run() {
            onError(SpeechRecognizer.ERROR_SPEECH_TIMEOUT);
        }
    }

    // Lazy instantiation method for getting the speech recognizer
    private SpeechRecognizer getSpeechRecognizer(){
        if (speech == null) {
            speech = SpeechRecognizer.createSpeechRecognizer(context);
            speech.setRecognitionListener(this);
        }

        return speech;
    }

    /* RecognitionListener interface implementation */
    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d(TAG, "onReadyForSpeech");
        // create and schedule the input speech timeout
        speechTimeout = new Timer();
        speechTimeout.schedule(new SilenceTimer(), 3000);
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG,"onBeginningOfSpeech");
        // Cancel the timeout because voice is arriving
        speechTimeout.cancel();
        callback.onDictationStart();
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d(TAG,"onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech");
        callback.onDictationFinish();
    }

    @Override
    public void onError(int error) {
        String message;
        boolean restart = true;
        switch (error)
        {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                restart = false;
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                restart = false;
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Not recognised";
                break;
        }
        Log.d(TAG,"onError code:" + error + " message: " + message);

        if (restart) {
            isRunning = true;
            ((Activity)context).runOnUiThread(new Runnable() {
                public void run() {
                    getSpeechRecognizer().cancel();
                    startRecognition(context, callback);
                }
            });
        }
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d(TAG,"onEvent");
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d(TAG,"onPartialResults");
    }

    @Override
    public void onResults(Bundle results) {
        startRecognition(context, callback);
        StringBuilder scores = new StringBuilder();
        for (int i = 0; i < results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES).length; i++) {
            scores.append(results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)[i] + " ");
        }
        Log.d(TAG,"onResults: " + results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) + " scores: " + scores.toString());
        if (results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) != null) {
            callback.onResults(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
        }
    }

    @Override
    public void onRmsChanged(float rmsdB) {
//		Log.d(TAG,"onRmsChanged");
    }

}
