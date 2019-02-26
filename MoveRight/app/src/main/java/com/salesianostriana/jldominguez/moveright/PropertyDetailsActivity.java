package com.salesianostriana.jldominguez.moveright;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.viewModel.PropertyViewModel;

import java.util.List;

public class PropertyDetailsActivity extends AppCompatActivity {

    TextView tvDetailTitle, tvDetailPrice, tvDetailLoc, tvDetailRooms, tvDetailSize;
    ImageButton ibDetailFab;
    ImageView ivDetailPhotos;
    private String propertyId;
    private PropertyViewModel viewModel;
    private Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        propertyId = getIntent().getStringExtra("id");

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailPrice = findViewById(R.id.tvDetailPrice);
        tvDetailLoc = findViewById(R.id.tvDetailLoc);
        tvDetailRooms = findViewById(R.id.tvDetailRooms);
        tvDetailSize = findViewById(R.id.tvDetailSize);
        ibDetailFab = findViewById(R.id.ibDetailFab);
        ivDetailPhotos = findViewById(R.id.ivDetailPhotos);

        launchViewModel();

        tvDetailTitle.setText(property.getTitle());
        tvDetailPrice.setText(property.getPrice());
        tvDetailLoc.setText(property.getAddress() + ", " +
                property.getZipcode()+ ", " +
                property.getCity() + ", " +
                property.getProvince());
        tvDetailRooms.setText(property.getRooms());
        tvDetailSize.setText(property.getSize());

        Glide.with(PropertyDetailsActivity.this).load(property.getPhotos()[0]).into(ivDetailPhotos);

    }

    public void setNewPropertyDetails(Property newPropertyDetails){
        this.property = newPropertyDetails;

    }
    public void launchViewModel(){
        viewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        viewModel.getPropertyDetailsFromCall(propertyId);
        viewModel.getPropertyDetails().observe(this, new Observer<Property>() {
            @Override
            public void onChanged(@Nullable Property newProperty) {
                setNewPropertyDetails(newProperty);
            }
        });

    }
}

