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
                    //getPhotosForProperties();


                    ResponseContainer<Property> data = response.body();
                    /*
                    List<Property> temp = data.getRows();
                    for(Property p : temp){
                        for(Photo pho: photos){
                            if(p.getId() == pho.getPropertyId()){
                                List<Photo> tempPhotos = p.getImages();
                                tempPhotos.add(pho);
                                p.setImages(tempPhotos);
                            }
                        }
                    }*/
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

    /*
    public void getPhotosForProperties() {
        photosService = ServiceGenerator.createService(PhotosService.class);
        Call<ResponseContainer<Photo>> call = photosService.getAllPhotos();

        call.enqueue(new Callback<ResponseContainer<Photo>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Photo>> call, Response<ResponseContainer<Photo>> response) {
                photos = response.body().getRows();
            }

            @Override
            public void onFailure(Call<ResponseContainer<Photo>> call, Throwable t) {
                Log.e("onFailurePhoto4Property", t.getMessage());
            }
        });


    }*/

    public MutableLiveData<List<Property>> getAllProperties() { return properties; }
}
