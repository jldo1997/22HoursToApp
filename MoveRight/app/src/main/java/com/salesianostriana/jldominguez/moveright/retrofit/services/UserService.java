package com.salesianostriana.jldominguez.moveright.retrofit.services;

import com.salesianostriana.jldominguez.moveright.dto.UserDTO;
import com.salesianostriana.jldominguez.moveright.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/users")
    Call<LoginResponse> registerUser(@Body UserDTO dto);


}
