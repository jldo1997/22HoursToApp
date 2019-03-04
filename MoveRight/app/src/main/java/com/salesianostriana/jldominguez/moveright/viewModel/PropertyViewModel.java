package com.salesianostriana.jldominguez.moveright.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.salesianostriana.jldominguez.moveright.dto.PropertyDTO;
import com.salesianostriana.jldominguez.moveright.model.BFContainer;
import com.salesianostriana.jldominguez.moveright.model.Photo;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.model.ResponseContainer;
import com.salesianostriana.jldominguez.moveright.retrofit.generator.ServiceGenerator;
import com.salesianostriana.jldominguez.moveright.retrofit.services.PhotosService;
import com.salesianostriana.jldominguez.moveright.retrofit.services.PropertyService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyViewModel extends AndroidViewModel {

    private PropertyService propertyService;
    private MutableLiveData<List<Property>> properties = new MutableLiveData<List<Property>>();
    private MutableLiveData<List<Property>> myProperties = new MutableLiveData<List<Property>>();
    private MutableLiveData<List<Property>> favProperties = new MutableLiveData<List<Property>>();
    private MutableLiveData<Property> propertyDetails = new MutableLiveData<Property>();

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
        Call<BFContainer<Property>> call = propertyService.getPropertyDetails(propertyId);

        call.enqueue(new Callback<BFContainer<Property>>() {
            @Override
            public void onResponse(Call<BFContainer<Property>> call, Response<BFContainer<Property>> response) {
                try {
                    propertyDetails.setValue(response.body().getRows());
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BFContainer<Property>> call, Throwable t) {
                Log.e("onFailurePropertyDetail", t.getMessage());
            }
        });
    }

    public void getMyPropertiesFromCall(String token){
        propertyService = ServiceGenerator.createService(PropertyService.class, token);
        Call<ResponseContainer<Property>> call = propertyService.getMyProperties();

        call.enqueue(new Callback<ResponseContainer<Property>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Property>> call, Response<ResponseContainer<Property>> response) {
                try {
                    ResponseContainer<Property> data = response.body();
                    myProperties.setValue(data.getRows());
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Property>> call, Throwable t) {
                Log.e("onFailureMyProperties", t.getMessage());
            }
        });
    }

    public void getFavPropertiesFromCall(String token){
        propertyService = ServiceGenerator.createService(PropertyService.class, token);
        Call<ResponseContainer<Property>> call = propertyService.getFavProperties();

        call.enqueue(new Callback<ResponseContainer<Property>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Property>> call, Response<ResponseContainer<Property>> response) {
                try {
                    ResponseContainer<Property> data = response.body();
                    List<Property> temp = new ArrayList<>();
                    for(Property p: data.getRows()){
                        if(p.isFav())
                            temp.add(p);
                    }
                    favProperties.setValue(temp);
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Property>> call, Throwable t) {
                Log.e("onFailureFavProperties", t.getMessage());
            }
        });
    }

    public void setFavProperty(String token, String propertyId){
        propertyService = ServiceGenerator.createService(PropertyService.class, token);
        Call<ResponseBody> call = propertyService.setFavProperty(propertyId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("onResponseSetFav", "everyfin is awesome");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailureSetFav", t.getMessage());
            }
        });
    }

    public void deleteFavProperty(final String token, String propertyId) {
        propertyService = ServiceGenerator.createService(PropertyService.class, token);

        Call<ResponseBody> call = propertyService.deleteFavProperty(propertyId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Log.d("onResponseDelFav", "All OK");
                    getFavPropertiesFromCall(token);
                } else
                    Log.e("onRespDelFavError", "Something go very WRONG");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailureDelFav", t.getMessage());
            }
        });
    }

    public void deleteProperty(String token){
        propertyService = ServiceGenerator.createService(PropertyService.class, token);

        Call<ResponseBody> call = propertyService.deleteProperty(getPropertyDetails().getValue().getId());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.d("onResponse", "all OK");
                } else {
                    Log.e("responseNotSuccesfull", "something went WRONG");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailureDeletePro", t.getMessage());
            }
        });
    }

    public void createProperty(String token, PropertyDTO dto){
        propertyService = ServiceGenerator.createService(PropertyService.class, token);
        Call<ResponseBody> call = propertyService.createProperty(dto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                    Log.d("onResponse", "all ok");
                else
                    Log.e("onResFailure", "something went WRONG");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }


    public MutableLiveData<List<Property>> getAllProperties() { return properties; }
    public MutableLiveData<Property> getPropertyDetails() { return propertyDetails; }
    public MutableLiveData<List<Property>> getMyProperties() { return myProperties; }
    public MutableLiveData<List<Property>> getFavProperties() { return favProperties; }
}
