package com.nmp90.hearmythoughts.ui.fragments.student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.providers.speech.ISpeechRecognitionListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nmp on 15-3-11.
 */
public class StreamStudentFragment extends Fragment implements ISpeechRecognitionListener {
    public static final String TAG = StreamStudentFragment.class.getSimpleName();

    private StringBuilder dictationBuilder = new StringBuilder();

    @InjectView(R.id.et_dictation)
    EditText etStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stream_student, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onDictationStart() {

    }

    @Override
    public void onResults(ArrayList<String> dictationResults) {
        Log.d(TAG, dictationResults.get(0));
        dictationBuilder.append(dictationResults.get(0) + " ");
        etStream.setText(dictationBuilder.toString());
    }

    @Override
    public void onDictationFinish() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
