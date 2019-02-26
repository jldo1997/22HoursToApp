package com.salesianostriana.jldominguez.moveright.retrofit.services;

import com.salesianostriana.jldominguez.moveright.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/auth")
    Call<LoginResponse> doLogin();
}
