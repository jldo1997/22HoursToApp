package com.salesianostriana.jldominguez.moveright.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.model.ResponseContainer;
import com.salesianostriana.jldominguez.moveright.retrofit.generator.ServiceGenerator;
import com.salesianostriana.jldominguez.moveright.retrofit.services.PropertyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyViewModel extends AndroidViewModel {

    private PropertyService service;
    private MutableLiveData<List<Property>> properties = new MutableLiveData<List<Property>>();

    public PropertyViewModel(@NonNull Application application) {
        super(application);
        getAllPropertiesFromCall();
    }

    public void getAllPropertiesFromCall() {
        service = ServiceGenerator.createService(PropertyService.class);
        Call<ResponseContainer<Property>> call = service.getAllProperties();

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

    public MutableLiveData<List<Property>> getAllProperties() { return properties; }
}
