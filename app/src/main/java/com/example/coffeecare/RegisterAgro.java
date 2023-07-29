package com.example.coffeecare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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
    TextView loginnow;

    private static final int REQUEST_LOCATION_PERMISSION = 1001;

    private boolean isRequestingLocationUpdates = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_agro);

        etAgrochemistName = findViewById(R.id.etAgrochemistName);
        etAgrochemistEmail = findViewById(R.id.etAgrochemistEmail);
        etAgrochemistPassword = findViewById(R.id.etAgrochemistPassword);
        btnAgrochemistRegister = findViewById(R.id.btnAgrochemistRegister);
        loginnow = findViewById(R.id.loginnow);
        locationbtn = findViewById(R.id.location);
        agrochemistRef = FirebaseDatabase.getInstance().getReference().child("agrochemists");

        firebaseAuth = FirebaseAuth.getInstance();

        loginnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterAgro.this, LoginAgro.class);
                startActivity(intent);
                finish();
            }
        });

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
        // Get the User's location
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
                                        // Retrieve the user ID of the currently logged-in agrochemist
                                        String userId = firebaseAuth.getCurrentUser().getUid();
                                        // Proceed to the agrochemist's activity with the user ID
                                        Intent intent = new Intent(getApplicationContext(), LoginAgro.class);
                                        intent.putExtra("USER_ID", userId); // Pass the user ID to the next activity
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Failed to save agrochemist data to the database
                                        Toast.makeText(RegisterAgro.this, "Failed to save agrochemist information.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterAgro.this, "Location not available. Please enable GPS.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Location permission not granted, request it
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
            requestLocationUpdates();
        }
    }

    private void requestLocationUpdates() {
        if (!isRequestingLocationUpdates) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // Save the User's current location to Firebase
                        saveLocationToFirebase(latitude, longitude);

                        // Stop listening for further location updates
                        locationManager.removeUpdates(this);
                        isRequestingLocationUpdates = false;
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        // Empty implementation
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        // Empty implementation
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        // Empty implementation
                    }
                });
                isRequestingLocationUpdates = true;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }



            private void saveLocationToFirebase(double latitude, double longitude) {
        // Get the current User ID
        String agrochemistId = firebaseAuth.getCurrentUser().getUid();

        // Save the location to Firebase using the agrochemist ID
        agrochemistRef.child(agrochemistId).child("latitude").setValue(latitude);
        agrochemistRef.child(agrochemistId).child("longitude").setValue(longitude)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Location saved successfully
                            Toast.makeText(RegisterAgro.this, "Location saved to Firebase.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to save location
                            Toast.makeText(RegisterAgro.this, "Failed to save location to Firebase.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}



