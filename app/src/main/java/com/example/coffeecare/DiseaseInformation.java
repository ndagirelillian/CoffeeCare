package com.example.coffeecare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiseaseInformation extends AppCompatActivity {
    Button locateshopbtn, Viewtreatment;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivities(new Intent[]{new Intent(DiseaseInformation.this, Help.class)});
        finish();
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_information);

//        locateshopbtn = findViewById(R.id.btnLocateShops);
        Viewtreatment = findViewById(R.id.viewChemicals);


        Viewtreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiseaseInformation.this, AgroCatelogue.class);
                startActivity(intent);
                finish();
            }
        });


//        locateshopbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                locateAgrochemists();
//
//            }
//        });


        int[] imageIds = {
                R.drawable.rust1,
                R.drawable.rust2,
                R.drawable.rust3,
                R.drawable.rust4
        };

        ViewPager viewPager = findViewById(R.id.imageViewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageIds);
        viewPager.setAdapter(adapter);
    }

//    private void locateAgrochemists() {
//        DatabaseReference agrochemistsRef = FirebaseDatabase.getInstance().getReference().child("agrochemists");
//
//        agrochemistsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot agrochemistSnapshot : snapshot.getChildren()) {
//                    double agrochemistLatitude = agrochemistSnapshot.child("latitude").getValue(Double.class);
//                    double agrochemistLongitude = agrochemistSnapshot.child("longitude").getValue(Double.class);
//
//                    // Display the agrochemist's location on the map using the latitude and longitude
//                    // You can add markers or customize the map as needed.
//                    // Here, we're assuming you have a MapActivity to display the map and markers.
//                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
//                    intent.putExtra("agrochemist_latitude", agrochemistLatitude);
//                    intent.putExtra("agrochemist_longitude", agrochemistLongitude);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(DiseaseInformation.this, "Failed to retrieve agrochemists' locations.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}

//    private void locateAgrochemist() {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            String agrochemistId = currentUser.getUid();
//            DatabaseReference agrochemistRef = FirebaseDatabase.getInstance().getReference().child("agrochemists");
//            DatabaseReference agrochemistLocationRef = agrochemistRef.child(agrochemistId);
//
//            agrochemistLocationRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        double agrochemistLatitude = snapshot.child("latitude").getValue(Double.class);
//                        double agrochemistLongitude = snapshot.child("longitude").getValue(Double.class);
//
//                        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
//                        intent.putExtra("agrochemist_latitude", agrochemistLatitude);
//                        intent.putExtra("agrochemist_longitude", agrochemistLongitude);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(DiseaseInformation.this, "Agrochemist location not found.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(DiseaseInformation.this, "Failed to retrieve agrochemist location.", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }



//    Make sure you've set up the Google Maps API in your project and have the
//    necessary permissions and API keys configured in your AndroidManifest.xml file.


