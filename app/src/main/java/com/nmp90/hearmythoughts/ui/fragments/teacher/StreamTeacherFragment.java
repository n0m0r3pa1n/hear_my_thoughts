package com.nmp90.hearmythoughts.ui.fragments.teacher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.constants.Constants;
import com.nmp90.hearmythoughts.ui.fragments.DictationFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nmp on 15-3-11.
 */
public class StreamTeacherFragment extends Fragment implements RecognitionListener {
    StringBuilder builder = new StringBuilder();

    public interface ContinuousDictationFragmentResultsCallback {
        public void onDictationStart();
        public void onResults(DictationFragment delegate, ArrayList<String> dictationResults);
        public void onDictationFinish();
    }

    // ----- TYPES ----- //
    // Timer task used to reproduce the timeout input error that seems not be called on android 4.1.2
    public class SilenceTimer extends TimerTask {
        @Override
        public void run() {
            onError(SpeechRecognizer.ERROR_SPEECH_TIMEOUT);
        }
    }

    // ---- MEMBERS ---- //
    // Callback activity called following dictation process
    private ContinuousDictationFragmentResultsCallback mCallback;
    // Logger tag
    private static final String TAG = "" + DictationFragment.class;
    // Speech recognizer instance
    private SpeechRecognizer speech = null;
    // Speech recognition control button
    private Button controlBtn = null;
    // Timer used as timeout for the speech recognition
    private Timer speechTimeout = null;

    // ---- METHODS ---- //
    // Method used to update button image as visual feedback of recognition service
    private void buttonChangeState(int state){
        switch (state) {
            case 0:
                //controlBtn.setImageDrawable(getResources().getDrawable(R.drawable.white));
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
    // Lazy instantiation method for getting the speech recognizer
    private SpeechRecognizer getSpeechRecognizer(){
        if (speech == null) {
            speech = SpeechRecognizer.createSpeechRecognizer(getActivity());
            speech.setRecognitionListener(this);
        }

        return speech;
    }

    /**
     *  onAttach(Activity) called once the fragment is associated with its activity.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ContinuousDictationFragmentResultsCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DictationFragmentResultsCallback");
        }
    }

    /**
     * onCreateView(LayoutInflater, ViewGroup, Bundle) creates and returns the view hierarchy associated with the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dictation, container, false);
        // Associate the button from the interface
        controlBtn = (Button) view.findViewById(R.id.btn_dictate);
        buttonChangeState(0);
        // Handling method for the button
        controlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (speech == null) {
                    buttonChangeState(1);
                    startVoiceRecognitionCycle();
                }
                else {
                    buttonChangeState(1);
                    stopVoiceRecognition();
                    Log.d("TEXT", builder.toString());
                    builder = new StringBuilder();

                }
            }
        });

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {

            //speakButton.setOnClickListener(this);
        } else {
            Toast.makeText(getActivity(), "Your phone does not support speech recognition", Toast.LENGTH_LONG).show();
//            speakButton.setEnabled(false);
//            speakButton.setText("Recognizer not present");
        }

        return view;
    }

    public void startVoiceRecognitionCycle()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Constants.SPEECH_DEFAULT_LANGUAGE);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, Constants.SPEECH_MAX_RESULTS);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Constants.SPEECH_COMPLETE_SILENCE);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, Constants.SPEECH_COMPLETE_POSSIBLY_COMPLETE_SILEMNCE);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, Constants.SPEECH_MINIMUM_LENGTH);
        getSpeechRecognizer().startListening(intent);
        buttonChangeState(1);
    }

    public void stopVoiceRecognition()
    {
        speechTimeout.cancel();
        if (speech != null) {
            speech.destroy();

            speech = null;
        }

        buttonChangeState(0);
    }

	/* RecognitionListener interface implementation */

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d(TAG,"onReadyForSpeech");
        // create and schedule the input speech timeout
        speechTimeout = new Timer();
        speechTimeout.schedule(new SilenceTimer(), 3000);
        buttonChangeState(3);
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG,"onBeginningOfSpeech");
        // Cancel the timeout because voice is arriving
        speechTimeout.cancel();
        buttonChangeState(2);
        mCallback.onDictationStart();
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d(TAG,"onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG,"onEndOfSpeech");
        buttonChangeState(0);
        mCallback.onDictationFinish();
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
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getSpeechRecognizer().cancel();
                    startVoiceRecognitionCycle();
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
        startVoiceRecognitionCycle();
        StringBuilder scores = new StringBuilder();
        for (int i = 0; i < results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES).length; i++) {
            scores.append(results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)[i] + " ");
        }
        Log.d(TAG,"onResults: " + results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) + " scores: " + scores.toString());
        if (results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) != null) {
            //mCallback.onResults(this, results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
        }

        builder.append(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0));
    }

    @Override
    public void onRmsChanged(float rmsdB) {
//		Log.d(TAG,"onRmsChanged");
    }

}
