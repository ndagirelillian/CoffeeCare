package com.example.coffeecare;


// RegisterAgro.java
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAgro extends AppCompatActivity {
    private EditText etAgrochemistName, etAgrochemistEmail, etAgrochemistPassword;
    private Button btnAgrochemistRegister, locationbtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference agrochemistRef;

    private static final int REQUEST_LOCATION_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_agro);

        etAgrochemistName = findViewById(R.id.etAgrochemistName);
        etAgrochemistEmail = findViewById(R.id.etAgrochemistEmail);
        etAgrochemistPassword = findViewById(R.id.etAgrochemistPassword);
        btnAgrochemistRegister = findViewById(R.id.btnAgrochemistRegister);
        locationbtn = findViewById(R.id.location);
        agrochemistRef = FirebaseDatabase.getInstance().getReference().child("agrochemists");

        locationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationPermission();
            }
        });

        btnAgrochemistRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the entered values
                String name = etAgrochemistName.getText().toString().trim();
                String email = etAgrochemistEmail.getText().toString().trim();
                String password = etAgrochemistPassword.getText().toString().trim();

                // Perform validation and registration process
                if (TextUtils.isEmpty(name)) {
                    etAgrochemistName.setError("Please enter your name");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    etAgrochemistEmail.setError("Please enter your email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etAgrochemistPassword.setError("Please enter your password");
                    return;
                }

                // Use Firebase Authentication to register the agrochemist
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterAgro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        String agrochemistId = user.getUid();
                                        saveAgrochemistToDatabase(agrochemistId, name, email);
                                    }
                                } else {
                                    Toast.makeText(RegisterAgro.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void saveAgrochemistToDatabase(String agrochemistId, String name, String email) {
        // Get the user's location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Save agrochemist information to the database
                    Agrochemist agrochemist = new Agrochemist(agrochemistId, name, email, latitude, longitude);
                    agrochemistRef.child(agrochemistId).setValue(agrochemist)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Agrochemist data saved to the database
                                        Toast.makeText(RegisterAgro.this, "Agrochemist information saved.", Toast.LENGTH_SHORT).show();
                                        // Redirect to the login screen
                                        Intent intent = new Intent(RegisterAgro.this, LoginAgro.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Failed to save agrochemist data to the database
                                        Toast.makeText(RegisterAgro.this, "Failed to save agrochemist information.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // If the location is null, it means the GPS is not enabled or the last known location is not available.
                    // You may want to show a message or prompt the user to enable location services.
                    Toast.makeText(RegisterAgro.this, "Location not available. Please enable GPS.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Location permission not granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            // Permission already granted, proceed with your location-related functionality.
            // For example, you can directly access the user's location here.
            // Here's an example of how to get the user's location using LocationManager:
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    // Do something with the latitude and longitude values.
                    // You can display them in a TextView or use them in any other way.
                } else {
                    // If the location is null, it means the GPS is not enabled or the last known location is not available.
                    // You may want to show a message or prompt the user to enable location services.
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
