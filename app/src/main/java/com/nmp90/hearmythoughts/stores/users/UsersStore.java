package com.nmp90.hearmythoughts.stores.users;

import android.util.Log;

import com.nmp90.hearmythoughts.api.actions.UserActions;
import com.nmp90.hearmythoughts.api.models.Token;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.events.UiEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;
import com.nmp90.hearmythoughts.ui.models.Role;
import com.nmp90.hearmythoughts.ui.models.User;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by nmp on 15-5-23.
 */
public class UsersStore extends Bus {
    public static final String TAG = UsersStore.class.getSimpleName();
    public static UsersStore usersStore;
    private Token token;

    public UsersStore() {
        super(TAG);
        EventBusInstance.getInstance().register(this);
    }

    public static UsersStore getInstance() {
        if(usersStore == null)
            usersStore = new UsersStore();

        return usersStore;
    }

    @Subscribe
    public void receiveServerEvent(ServerEvent event) {
        //May be we should check if the operation is successful before get the data,
        //It can be used for operation which don't need to load data.
        switch(event.getAction()) {
            case UserActions.LOGIN_USER:
                token = event.getData(Token.class);
                break;
            default:
                return;
        }
        Log.d(TAG, token.toString());
        this.post(new UserLoginEvent(new User("das", "dass", Role.TEACHER, token)));
    }

    @Subscribe
    public void receiveUiEvent(UiEvent event) {

    }

    public static class UserLogoutEvent {

    }

    public static class UserLoginEvent {
        public UserLoginEvent(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        private User user;
    }
}
