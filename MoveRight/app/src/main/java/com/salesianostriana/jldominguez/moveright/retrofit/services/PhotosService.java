package com.salesianostriana.jldominguez.moveright.retrofit.services;

import com.salesianostriana.jldominguez.moveright.model.Photo;
import com.salesianostriana.jldominguez.moveright.model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotosService {

    @GET("/photos")
    Call<ResponseContainer<Photo>> getAllPhotos();
}
