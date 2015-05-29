package com.nmp90.hearmythoughts.stores;

import android.content.Context;

import com.nmp90.hearmythoughts.constants.actions.UserActions;
import com.nmp90.hearmythoughts.events.ServerEvent;
import com.nmp90.hearmythoughts.events.UiEvent;
import com.nmp90.hearmythoughts.instances.EventBusInstance;
import com.nmp90.hearmythoughts.providers.AuthProvider;
import com.nmp90.hearmythoughts.api.models.User;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by nmp on 15-5-23.
 */
public class UsersStore extends Bus {
    public static final String TAG = UsersStore.class.getSimpleName();
    public static UsersStore usersStore;
    private Context context;
    private User user;

    public UsersStore(Context context) {
        super(TAG);
        EventBusInstance.getInstance().register(this);
        this.context = context;
    }

    public static UsersStore getInstance(Context context) {
        if(usersStore == null)
            usersStore = new UsersStore(context.getApplicationContext());

        return usersStore;
    }

    @Subscribe
    public void receiveServerEvent(ServerEvent event) {
        //May be we should check if the operation is successful before get the data,
        //It can be used for operation which don't need to load data.
        switch(event.getAction()) {
            case UserActions.LOGIN_USER:
                user = event.getData(User.class);
                AuthProvider.getInstance(context).login(user);
                break;
            default:
                return;
        }
        this.post(new UserLoginEvent(user));
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
