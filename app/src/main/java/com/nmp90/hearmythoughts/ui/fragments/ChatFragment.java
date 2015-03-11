package com.nmp90.hearmythoughts.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nmp90.hearmythoughts.R;
import com.nmp90.hearmythoughts.models.Message;
import com.nmp90.hearmythoughts.ui.adapters.MessagesAdapter;

/**
 * Created by nmp on 15-3-11.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "com.naughtyspirit.snaproulette.app.fragments.ChatFragment";

    private View view;
    private ListView msgList;
    private EditText msgTextBox;
    private Button send;

    private MessagesAdapter msgAdapter;

    //private ChatRoomInterfaces chatRoomCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        initUI();

        return view;
    }

    private void initUI() {
        ((ActionBarActivity) getActivity()).getSupportActionBar().show();

        msgList = (ListView) view.findViewById(R.id.message_container);
        msgAdapter = new MessagesAdapter(getActivity());
        msgList.setAdapter(msgAdapter);

        send = (Button) view.findViewById(R.id.send);
        send.setOnClickListener(this);

        msgTextBox = (EditText) view.findViewById(R.id.msg_text_box);
        msgTextBox.setImeActionLabel(getString(R.string.send), EditorInfo.IME_ACTION_SEND);
        msgTextBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                send.performClick();
                return true;
            }
        });
    }

    public void receiveMsg(String msg) {
        Message message = new Message();
        message.setMessage(msg);
        message.setMine(false);

        msgAdapter.addMessage(message);
    }

    public void setUsername(String username) {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(username);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            //chatRoomCallback = (ChatRoomInterfaces) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChatRoomInterfaces");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                if (msgTextBox.getText() != null && msgTextBox.getText().toString().length() > 0) {
                    Message message = new Message();
                    message.setMessage(msgTextBox.getText().toString());
                    message.setMine(true);

                    //chatRoomCallback.onSendMessage(message.getMessage());

                    msgAdapter.addMessage(message);

                    msgTextBox.getText().clear();
                }
                break;
        }
    }
}
