package com.salesianostriana.jldominguez.moveright;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.zip.Inflater;

public class SelectMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String toSend;
    private Button bSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = SelectMapActivity.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_create_property, null);

        setContentView(v);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(SelectMapActivity.this);

        bSend = v.findViewById(R.id.bSendLatlng);

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toSend.isEmpty()){
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", toSend);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                toSend = latLng.latitude + "," + latLng.longitude;
            }
        });

    }


}
