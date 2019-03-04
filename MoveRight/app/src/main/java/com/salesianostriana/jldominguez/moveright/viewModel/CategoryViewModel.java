package com.salesianostriana.jldominguez.moveright.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.salesianostriana.jldominguez.moveright.model.Category;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.model.ResponseContainer;
import com.salesianostriana.jldominguez.moveright.retrofit.generator.ServiceGenerator;
import com.salesianostriana.jldominguez.moveright.retrofit.services.CategoryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryService service;
    private MutableLiveData<List<Category>> categories = new MutableLiveData<List<Category>>();

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        getAllCategoriesFromCall();
    }

    public void getAllCategoriesFromCall() {
        service = ServiceGenerator.createService(CategoryService.class);
        Call<ResponseContainer<Category>> call = service.getAllCategories();

        call.enqueue(new Callback<ResponseContainer<Category>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Category>> call, Response<ResponseContainer<Category>> response) {
                ResponseContainer<Category> temp = response.body();
                categories.setValue(temp.getRows());
            }

            @Override
            public void onFailure(Call<ResponseContainer<Category>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<Category>> getCategories() { return categories; }
}
