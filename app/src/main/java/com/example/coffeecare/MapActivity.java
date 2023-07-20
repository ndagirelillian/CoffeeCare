package com.example.coffeecare;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity {
    private double agrochemistLatitude;
    private double agrochemistLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Retrieve the latitude and longitude from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            agrochemistLatitude = extras.getDouble("agrochemist_latitude", 0.0);
            agrochemistLongitude = extras.getDouble("agrochemist_longitude", 0.0);
        }

        // Initialize and display the map with the agrochemist's location
        // You can use Google Maps API or any other map library of your choice here.
        // For this example, let's assume you are using Google Maps API.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Add a marker for the agrochemist's location
                LatLng agrochemistLocation = new LatLng(agrochemistLatitude, agrochemistLongitude);
                googleMap.addMarker(new MarkerOptions().position(agrochemistLocation).title("Agrochemist Location"));

                // Move the camera to the agrochemist's location and set an appropriate zoom level
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(agrochemistLocation, 15));
            }
        });
    }
}


//public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private double agrochemistLatitude;
//    private double agrochemistLongitude;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        // You can customize the map here (e.g., add markers, set camera position, etc.)
//    }
//}


//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//    private GoogleMap googleMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.mapFragment);
//        mapFragment.getMapAsync((OnMapReadyCallback) this);
//
//        // Retrieve the agrochemist's location data from the Intent
//        double agrochemistLatitude = getIntent().getDoubleExtra("agrochemist_latitude", 0);
//        double agrochemistLongitude = getIntent().getDoubleExtra("agrochemist_longitude", 0);
//
//        // Create a LatLng object with the agrochemist's location
//        LatLng agrochemistLocation = new LatLng(agrochemistLatitude, agrochemistLongitude);
//
//        // Add a marker on the map for the agrochemist's location
//        googleMap.addMarker(new MarkerOptions().position(agrochemistLocation).title("Agrochemist Location"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(agrochemistLocation, 15));
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        this.googleMap = googleMap;
//    }
//}
