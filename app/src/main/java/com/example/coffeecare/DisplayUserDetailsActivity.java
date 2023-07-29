package com.example.coffeecare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayUserDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;

    Button logout;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivities(new Intent[]{new Intent(DisplayUserDetailsActivity.this, AgroEntry.class)});
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_details);


        logout = findViewById(R.id.logout);

        // Implement logout functionality
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // Sign out the user

                // Redirect the user back to the Home screen (or any other desired activity)
                Intent intent = new Intent(DisplayUserDetailsActivity.this, Home.class);
                startActivity(intent);
                finish();
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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
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
}

//
//
//
//
//
//
//
//
//
//
//        // Get the user ID from the previous activity (AgroEntry)
//        userId = getIntent().getStringExtra("USER_ID");
//
//        databaseUsers.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    Agrochemist agrochemist= snapshot.getValue(Agrochemist.class);
//                    if (agrochemist != null) {
//                        textChemicalname.setText("Chemical Name: " + agrochemist.getChemicalname());
//                        textDescription.setText("Description: " + agrochemist.getDescription());
//                        textIndustry.setText("Industry: " + agrochemist.getIndustry());
//                    }
//                } else {
//                    // Handle the case when the User data doesn't exist
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle any database read errors
//            }
//        });
//    }
//}

