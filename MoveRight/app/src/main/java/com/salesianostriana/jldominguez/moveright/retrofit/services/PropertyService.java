package com.salesianostriana.jldominguez.moveright.retrofit.services;

import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PropertyService {

    @GET("/properties")
    Call<ResponseContainer<Property>> getAllProperties();

    @GET("/properties/{id}")
    Call<ResponseContainer<Property>> getPropertyDetails(@Path("id") String id);
}
