package com.salesianostriana.jldominguez.moveright.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.salesianostriana.jldominguez.moveright.model.Photo;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.model.ResponseContainer;
import com.salesianostriana.jldominguez.moveright.retrofit.generator.ServiceGenerator;
import com.salesianostriana.jldominguez.moveright.retrofit.services.PhotosService;
import com.salesianostriana.jldominguez.moveright.retrofit.services.PropertyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyViewModel extends AndroidViewModel {

    private PropertyService propertyService;
    //private PhotosService photosService;
    private MutableLiveData<List<Property>> properties = new MutableLiveData<List<Property>>();
    private MutableLiveData<Property> propertyDetails = new MutableLiveData<Property>();
    //private List<Photo> photos;

    public PropertyViewModel(@NonNull Application application) {
        super(application);
        getAllPropertiesFromCall();
    }

    public void getAllPropertiesFromCall() {
        propertyService = ServiceGenerator.createService(PropertyService.class);
        Call<ResponseContainer<Property>> call = propertyService.getAllProperties();

        call.enqueue(new Callback<ResponseContainer<Property>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Property>> call, Response<ResponseContainer<Property>> response) {
                try {
                    ResponseContainer<Property> data = response.body();
                    properties.setValue(data.getRows());
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseContainer<Property>> call, Throwable t) {
                Log.e("onFailureAllProperties", t.getMessage());
            }
        });
    }

    public void getPropertyDetailsFromCall(String propertyId){
        propertyService = ServiceGenerator.createService(PropertyService.class);
        Call<ResponseContainer<Property>> call = propertyService.getPropertyDetails(propertyId);

        call.enqueue(new Callback<ResponseContainer<Property>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Property>> call, Response<ResponseContainer<Property>> response) {
                try {
                    propertyDetails.setValue(response.body().getRows().get(0));
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Property>> call, Throwable t) {
                Log.e("onFailurePropertyDetail", t.getMessage());
            }
        });
    }


    public MutableLiveData<List<Property>> getAllProperties() { return properties; }
    public MutableLiveData<Property> getPropertyDetails() { return propertyDetails; }
}
