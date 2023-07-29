package com.example.coffeecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgroEntry extends AppCompatActivity {

    Button btninsertpdt, btnViewpdt;
    EditText chemicalname, description, industry;
    DatabaseReference databaseUsers;
    String userId; // Declare a variable to hold the user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agro_entry);

        btninsertpdt = findViewById(R.id.btninsertpdt);
        btnViewpdt = findViewById(R.id.btnviewpdt);
        chemicalname = findViewById(R.id.Chemname);
        description = findViewById(R.id.description);
        industry = findViewById(R.id.industry);
        databaseUsers = FirebaseDatabase.getInstance().getReference();

        // Get the user ID from the previous activity (login or registration)
        userId = getIntent().getStringExtra("USER_ID");

        btninsertpdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
                insertDataToViewAlone();
            }
        });

        btnViewpdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user ID is available
                if (userId != null) {
                    Intent intent = new Intent(AgroEntry.this, DisplayUserDetailsActivity.class);
                    intent.putExtra("USER_ID", userId); // Pass the user ID to the next activity
                    startActivity(intent);
                } else {
                    Toast.makeText(AgroEntry.this, "User ID not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertDataToViewAlone() {
        String agrochemistChemicalname = chemicalname.getText().toString();
        String agrochemistdescription = description.getText().toString();
        String agrochemistIndustry = industry.getText().toString();
        String id = databaseUsers.push().getKey();

        Agrochemist agrochemist = new Agrochemist(agrochemistChemicalname, agrochemistdescription, agrochemistIndustry);
        databaseUsers.child("agrochemists").child(id).setValue(agrochemist)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AgroEntry.this, "Chemical Products Inserted for me Alone", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle the case when data insertion fails
                        }
                    }
                });
    }
//
//    private void insertDataToView() {
//        String userchemicalname = chemicalname.getText().toString();
//        String userdescription = description.getText().toString();
//        String userindustry = industry.getText().toString();
//        String id = databaseUsers.push().getKey();
//
//        User user = new User(userchemicalname, userdescription, userindustry);
//        databaseUsers.child("users").child(id).setValue(user)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(AgroEntry.this, "Chemical Products Inserted", Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Handle the case when data insertion fails
//                        }
//                    }
//                });
//    }
    private void InsertData() {
        String userchemicalname = chemicalname.getText().toString();
        String userdescription = description.getText().toString();
        String userindustry = industry.getText().toString();
        String id = databaseUsers.push().getKey();

        User user = new User(userchemicalname, userdescription, userindustry);
        databaseUsers.child("users").child(id).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AgroEntry.this,"User details Inserted", Toast.LENGTH_SHORT).show();
                        }
                        else {

                        }
                    }
                });


}
}
