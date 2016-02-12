package com.nmp90.hearmythoughts.ui.fragments.teacher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.api.sockets.StreamConnectionManager;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.providers.SessionProvider;
import com.nmp90.hearmythoughts.providers.speech.ISpeechRecognitionListener;
import com.nmp90.hearmythoughts.providers.speech.SpeechRecognitionProvider;
import com.nmp90.hearmythoughts.ui.views.ProgressImageView;
import com.nmp90.hearmythoughts.utils.AudioUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nmp on 15-3-11.
 */
public class StreamTeacherFragment extends Fragment implements ISpeechRecognitionListener {
    public static final String TAG = StreamTeacherFragment.class.getSimpleName();

    private boolean shoouldStopRecognition = false;
    private StringBuilder dictationBuilder = new StringBuilder();

    @InjectView(R.id.et_dictation)
    EditText etStream;

    @InjectView(R.id.btn_dictate)
    ProgressImageView btnDictate;

    @InjectView(R.id.tv_streaming)
    TextView tvStreaming;

    private FragmentActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stream_teacher, container, false);
        ButterKnife.inject(this, view);

        StreamConnectionManager.getInstance().addUserToStream(AuthProvider.getInstance(getActivity()).getUser(),
                SessionProvider.getInstance(getActivity()).getSession().getShortId());
        return view;
    }

    @OnClick(R.id.btn_dictate)
    public void setupDictation() {
        String shortId = SessionProvider.getInstance(getActivity()).getSession().getShortId();
        if(doesSupportSpeechRecognition() == false) {
            StreamConnectionManager.getInstance().sendStream(etStream.getText().toString(), shortId);
            StreamConnectionManager.getInstance().sendStreamStatus(true, shortId);
            return;
        }

        if(shoouldStopRecognition == true) {
            AudioUtils.unmute();
            shoouldStopRecognition = false;
            tvStreaming.setText(getResources().getString(R.string.start_streaming));
            btnDictate.setLoading(shoouldStopRecognition);
            SpeechRecognitionProvider.getSpeechRecognition().stopRecognition();
            StreamConnectionManager.getInstance().sendStreamStatus(false, shortId);
        } else {
            shoouldStopRecognition = true;
            tvStreaming.setText(getResources().getString(R.string.streaming));
            btnDictate.setLoading(shoouldStopRecognition);
            SpeechRecognitionProvider.getSpeechRecognition().startRecognition(activity, this);
            AudioUtils.mute(getActivity());
            StreamConnectionManager.getInstance().sendStreamStatus(true, shortId);
        }
    }

    private boolean doesSupportSpeechRecognition() {
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            Toast.makeText(getActivity(), "Your phone does not support speech recognition", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onDictationStart() {

    }

    @Override
    public void onResults(ArrayList<String> dictationResults) {
        Log.d(TAG, dictationResults.get(0));

        dictationBuilder.append(dictationResults.get(0) + " ");
        StreamConnectionManager.getInstance().sendStream(dictationBuilder.toString(), SessionProvider.getInstance(getActivity()).getSession().getShortId());
        etStream.setText(dictationBuilder.toString());
    }

    @Override
    public void onDictationFinish() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onDetach() {
        if(SessionProvider.getInstance(activity).getSession() != null) {
            StreamConnectionManager.getInstance().sendStreamStatus(false, SessionProvider.getInstance(activity).getSession().getShortId());
        }
        if(shoouldStopRecognition) {
            shoouldStopRecognition = false;
            SpeechRecognitionProvider.getSpeechRecognition().stopRecognition();
            btnDictate.setLoading(false);
            AudioUtils.unmute();
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        super.onDetach();
    }
}
