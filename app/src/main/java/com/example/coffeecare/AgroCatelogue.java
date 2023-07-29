package com.example.coffeecare;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AgroCatelogue extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    Button locateshopbtn;




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivities(new Intent[]{new Intent(AgroCatelogue.this, Help.class)});
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agro_catalogue);


        locateshopbtn = findViewById(R.id.btnLocateShops);


        locateshopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateAgrochemists();

            }
        });

        recyclerView = findViewById(R.id.rec_view);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                        Toast.makeText(AgroCatelogue.this, "Agrochemist's location data is missing.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AgroCatelogue.this, "Failed to retrieve agrochemists' locations.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}





