package com.example.coffeecare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        // Retrieve the agrochemist's location data from the Intent
        double agrochemistLatitude = getIntent().getDoubleExtra("agrochemist_latitude", 0);
        double agrochemistLongitude = getIntent().getDoubleExtra("agrochemist_longitude", 0);

        // Create a LatLng object with the agrochemist's location
        LatLng agrochemistLocation = new LatLng(agrochemistLatitude, agrochemistLongitude);

        // Add a marker on the map for the agrochemist's location
        googleMap.addMarker(new MarkerOptions().position(agrochemistLocation).title("Agrochemist Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(agrochemistLocation, 15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
