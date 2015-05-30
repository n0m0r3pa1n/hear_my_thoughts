package com.nmp90.hearmythoughts.stores;

import com.nmp90.hearmythoughts.constants.actions.ChatActions;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;
import com.nmp90.hearmythoughts.ui.models.MessagesList;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by nmp on 15-5-29.
 */
public class ChatMessagesStore extends Bus {
    public static final String TAG = ChatMessagesStore.class.getSimpleName();

    private static ChatMessagesStore instance;
    private ChatMessagesStore() {
        super(TAG);
        EventBusInstance.getInstance().register(this);
    }

    public static ChatMessagesStore getInstance() {
        if(instance == null) {
            instance = new ChatMessagesStore();
        }

        return instance;
    }

    @Subscribe
    public void receiveServerEvent(ServerEvent event) {
        //May be we should check if the operation is successful before get the data,
        //It can be used for operation which don't need to load data.
        switch(event.getAction()) {
            case ChatActions.LOAD_MESSAGES:
                this.post(new MessagesLoadedEvent(event.getData(MessagesList.class)));
                break;
            default:
                return;
        }
    }

    public static class MessagesLoadedEvent {
        private MessagesList messagesList;

        public MessagesLoadedEvent(MessagesList messagesList) {
            this.messagesList = messagesList;
        }

        public MessagesList getMessagesList() {
            return messagesList;
        }
    }

}
