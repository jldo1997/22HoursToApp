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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.salesianostriana.jldominguez.moveright.model.Property;
import com.salesianostriana.jldominguez.moveright.viewModel.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PropertyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        viewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        viewModel.getAllProperties().observe(this, new Observer<List<Property>>() {
            @Override
            public void onChanged(@Nullable List<Property> properties) {
                for(Property p : properties) {

                    if (!p.getLoc().isEmpty()){
                        String[] parts;
                        if (p.getLoc().contains(", ")) {
                            parts = p.getLoc().split(", ");
                        } else {
                            parts = p.getLoc().split(",");
                        }
                        double part1 = Double.parseDouble(parts[0]);
                        double part2 = Double.parseDouble(parts[1]);
                        LatLng temp = new LatLng(part1, part2);

                        MarkerOptions optTemp = new MarkerOptions()
                                .position(temp)
                                .title(p.getTitle())
                                .snippet(p.getDescription())
                                .draggable(false);

                        Marker marker = mMap.addMarker(optTemp);
                        marker.showInfoWindow();
                }
                }


            }
        });

        LatLng test = new LatLng(37.383629,-6.002002);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(test, 13));

    }
}
