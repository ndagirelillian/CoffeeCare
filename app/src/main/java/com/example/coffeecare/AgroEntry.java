package com.example.coffeecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    EditText Chemicalname, description, Industry;
    DatabaseReference databaseUsers;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivities(new Intent[]{new Intent(AgroEntry.this, LoginAgro.class)});
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agro_entry);

        btninsertpdt = findViewById(R.id.btninsertpdt);
        btnViewpdt = findViewById(R.id.btnviewpdt);
        Chemicalname = findViewById(R.id.Chemname);
        description = findViewById(R.id.description);
        Industry = findViewById(R.id.industry);
        databaseUsers = FirebaseDatabase.getInstance().getReference();

        btninsertpdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();

            }

            private void InsertData() {
                String userChemicalname = Chemicalname.getText().toString();
                String userdescription = description.getText().toString();
                String userIndustry = Industry.getText().toString();
                String id = databaseUsers.push().getKey();

                User user = new User(userChemicalname,userdescription,userIndustry);
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
        });

        btnViewpdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgroEntry.this, AgroCatelogue.class);
                startActivity(intent);
                finish();

            }
        });






    }}