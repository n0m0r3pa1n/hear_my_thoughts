package com.nmp90.hearmythoughts.ui.fragments.student;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.api.sockets.StreamConnectionManager;
import com.nmp90.hearmythoughts.providers.AuthProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nmp on 15-3-11.
 */
public class StreamStudentFragment extends Fragment implements StreamConnectionManager.OnStreamListener {
    public static final String TAG = StreamStudentFragment.class.getSimpleName();

    private StringBuilder dictationBuilder = new StringBuilder();

    @InjectView(R.id.et_dictation)
    EditText etStream;

    @InjectView(R.id.tv_streaming)
    TextView tvStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stream_student, container, false);
        ButterKnife.inject(this, view);

        StreamConnectionManager.getInstance().addUserToStream(AuthProvider.getInstance(getActivity()).getUser(), "Test");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        StreamConnectionManager.getInstance().addOnStreamListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        StreamConnectionManager.getInstance().removeOnStreamListener(this);
    }

    @Override
    public void onStreamReceived(final String stream) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etStream.setText(stream);
            }
        });
    }

    @Override
    public void onStreamStatusChange(final boolean isRunning) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isRunning)
                    tvStream.setText("Streaming...");
                else
                    tvStream.setText("Stream is OFF. Waiting for lecturer.");
            }
        });
    }
}
