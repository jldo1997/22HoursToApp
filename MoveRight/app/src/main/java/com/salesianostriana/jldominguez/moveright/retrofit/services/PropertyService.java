package com.salesianostriana.jldominguez.moveright.retrofit.services;

import com.salesianostriana.jldominguez.moveright.dto.PropertyDTO;
import com.salesianostriana.jldominguez.moveright.model.BFContainer;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.model.ResponseContainer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PropertyService {

    @GET("/properties")
    Call<ResponseContainer<Property>> getAllProperties();

    @GET("/properties/{id}")
    Call<BFContainer<Property>> getPropertyDetails(@Path("id") String id);

    @GET("/properties/mine")
    Call<ResponseContainer<Property>> getMyProperties();

    @GET("/properties/auth")
    Call<ResponseContainer<Property>> getFavProperties();

    @POST("/properties/fav/{id}")
    Call<ResponseBody> setFavProperty(@Path("id") String id);

    @POST("/properties")
    Call<ResponseBody> createProperty(@Body PropertyDTO dto);

    @DELETE("/properties/fav/{id}")
    Call<ResponseBody> deleteFavProperty(@Path("id") String id);

    @DELETE("/properties/{id}")
    Call<ResponseBody> deleteProperty(@Path("id") String id);
}
