package com.nmp90.hearmythoughts.api.requests.user;

import com.android.volley.Response;
import com.nmp90.hearmythoughts.api.models.Token;
import com.nmp90.hearmythoughts.api.requests.base.BaseGetRequest;

/**
 * Created by nmp on 15-5-23.
 */
public class UserLoginRequest extends BaseGetRequest<Token> {
    public UserLoginRequest(String email, Response.ErrorListener listener) {
        super(BASE_URL + "/users/" + email, Token.class, listener);
    }

    @Override
    public void deliverResponse(Token response) {
        
    }
}
