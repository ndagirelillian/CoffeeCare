package com.example.coffeecare;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Help extends AppCompatActivity {
    CardView locateshop, diseaseinfo, chemicalpdts, tips;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
//
//        // Check if the user is already logged in
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            // User is already logged in, proceed to MainActivity
//            startActivity(new Intent(Help.this, MainActivity.class));
//            finish();
//        }

        logout = findViewById(R.id.logout);
        diseaseinfo = findViewById(R.id.diseaseinfo);
        chemicalpdts = findViewById(R.id.chempdts);
        locateshop = findViewById(R.id.shops);

        // Implement logout functionality
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // Sign out the user

                // Redirect the user back to the Home screen (or any other desired activity)
                Intent intent = new Intent(Help.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        diseaseinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, DiseaseInformation.class);
                startActivity(intent);
                finish();
            }
        });

        chemicalpdts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Help.this, AgroCatelogue.class);
                startActivity(intent);
                finish();
            }
        });

        locateshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateAgrochemists();
            }
        });
    }

    private void locateAgrochemists() {
        DatabaseReference agrochemistsRef = FirebaseDatabase.getInstance().getReference().child("agrochemists");

        agrochemistsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot agrochemistSnapshot : snapshot.getChildren()) {
                    Double agrochemistLatitude = agrochemistSnapshot.child("latitude").getValue(Double.class);
                    Double agrochemistLongitude = agrochemistSnapshot.child("longitude").getValue(Double.class);

                    if (agrochemistLatitude != null && agrochemistLongitude != null) {
                        // Display the agrochemist's location on the map using the latitude and longitude
                        // You can add markers or customize the map as needed.
                        // Here, we're assuming you have a MapActivity to display the map and markers.
                        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                        intent.putExtra("agrochemist_latitude", agrochemistLatitude);
                        intent.putExtra("agrochemist_longitude", agrochemistLongitude);
                        startActivity(intent);
                    } else {
                        // Handle the case when latitude or longitude is null.
                        Toast.makeText(Help.this, "Agrochemist's location data is missing.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Help.this, "Failed to retrieve agrochemists' locations.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
