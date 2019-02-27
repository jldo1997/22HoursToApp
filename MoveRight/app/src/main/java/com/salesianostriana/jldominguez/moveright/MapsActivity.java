package com.salesianostriana.jldominguez.moveright;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.viewModel.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PropertyViewModel viewModel;
    private List<String> listLoc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        viewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        viewModel.getAllProperties().observe(this, new Observer<List<Property>>() {
            @Override
            public void onChanged(@Nullable List<Property> properties) {
                 for(Property p : properties)
                     listLoc.add(p.getLoc());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(String s : listLoc){
            if(s.contains(", ")) {
                String[] parts = s.split(", ");
                double part1 = Double.parseDouble(parts[0]);
                double part2 = Double.parseDouble(parts[1]);
                LatLng temp = new LatLng(part1, part2);

                mMap.addMarker(new MarkerOptions().position(temp));
            } else {
                String[] parts = s.split(",");
                double part1 = Double.parseDouble(parts[0]);
                double part2 = Double.parseDouble(parts[1]);
                LatLng temp = new LatLng(part1, part2);

                mMap.addMarker(new MarkerOptions().position(temp));
            }
        }

        LatLng test = new LatLng(37.383629,-6.002002);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(test, 13));
    }
}
