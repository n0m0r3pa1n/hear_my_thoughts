package com.nmp90.hearmythoughts.ui.fragments;

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
import com.nmp90.hearmythoughts.api.sockets.ChatConnectionManager;
import com.nmp90.hearmythoughts.api.sockets.ChatConnectionManager.OnChatActionsListener;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.providers.SessionProvider;
import com.nmp90.hearmythoughts.ui.adapters.MessagesAdapter;
import com.nmp90.hearmythoughts.ui.models.Message;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nmp on 15-3-11.
 */
public class ChatFragment extends Fragment implements OnChatActionsListener {
    public static final String TAG = ChatFragment.class.getSimpleName();
    private View view;

    @InjectView(R.id.message_container)
    ListView msgList;

    @InjectView(R.id.msg_text_box)
    EditText msgTextBox;

    @InjectView(R.id.send)
    Button send;

    private MessagesAdapter msgAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.inject(this, view);
        initUI();

        return view;
    }

    private void initUI() {
        ((ActionBarActivity) getActivity()).getSupportActionBar().show();

        msgAdapter = new MessagesAdapter(getActivity(), new ArrayList<Message>());
        msgList.setAdapter(msgAdapter);

        msgTextBox.setImeActionLabel(getString(R.string.send), EditorInfo.IME_ACTION_SEND);
        msgTextBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                send.performClick();
                return true;
            }
        });
    }


    public void receiveMsg(Message message) {
        if(message.getUser().getId().equals(AuthProvider.getInstance(getActivity()).getUser().getId()))
            return;

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
    public void onDetach() {
        super.onDetach();
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void onResume() {
        super.onResume();
        ChatConnectionManager.getInstance().addChatListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        ChatConnectionManager.getInstance().removeChatListener(this);
    }

    @OnClick(R.id.send)
    public void sendMessage() {
        if (msgTextBox.getText() != null && msgTextBox.getText().toString().length() > 0) {
            Message message = new Message();
            message.setMessage(msgTextBox.getText().toString());
            message.setUser(AuthProvider.getInstance(getActivity()).getUser());
            message.setMine(true);

            msgAdapter.addMessage(message);
            ChatConnectionManager.getInstance().sendMessage(message, SessionProvider.getInstance(getActivity()).getSession().getShortId());
            msgTextBox.getText().clear();
        }
    }

    @Override
    public void onMessageReceived(final Message message) {
        Thread messageReceived = new Thread(new Runnable() {
            @Override
            public void run() {
                receiveMsg(message);
            }
        });
        getActivity().runOnUiThread(messageReceived);
    }
}
