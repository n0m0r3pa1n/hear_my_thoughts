package com.nmp90.hearmythoughts.ui.models;

import java.util.List;

/**
 * Created by nmp on 15-5-29.
 */
public class MessagesList {
    private List<ChatMessage> messages;

    public MessagesList(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
