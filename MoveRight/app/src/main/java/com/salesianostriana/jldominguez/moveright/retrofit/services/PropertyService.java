package com.salesianostriana.jldominguez.moveright.retrofit.services;

import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PropertyService {

    @GET("/properties")
    Call<ResponseContainer<Property>> getAllProperties();
}
