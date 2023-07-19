package com.example.coffeecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFarmer extends AppCompatActivity {
    private EditText etFarmerName, etFarmerEmail, etFarmerPassword;
    FirebaseAuth firebaseAuth;
    TextView Clicklogin;
    Button btnFarmerRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_farmer);

        Clicklogin = findViewById(R.id.loginnow);
        etFarmerName = findViewById(R.id.etFarmerName);
        etFarmerEmail = findViewById(R.id.etFarmerEmail);
        etFarmerPassword = findViewById(R.id.etFarmerPassword);
        btnFarmerRegister = findViewById(R.id.btnFarmerRegister);

        Clicklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginFarmer.class);
                startActivity(intent);
                finish();
            }
        });



        btnFarmerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the entered values
                String name = etFarmerName.getText().toString().trim();
                String email = etFarmerEmail.getText().toString().trim();
                String password = etFarmerPassword.getText().toString().trim();

                // Perform validation and registration process
                if (TextUtils.isEmpty(name)) {
                    etFarmerName.setError("Please enter your name");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    etFarmerEmail.setError("Please enter your email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etFarmerPassword.setError("Please enter your password");
                    return;
                }

                // Use Firebase Authentication and Firebase Realtime Database/Firestore to register the farmer
                // Example code:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterFarmer.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Farmer registration successful
                                    // You can save additional farmer information to the database here
                                    Toast.makeText(RegisterFarmer.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    finish(); // Finish the activity or redirect to the login screen
                                    Intent intent = new Intent(getApplicationContext(), LoginFarmer.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Farmer registration failed
                                    Toast.makeText(RegisterFarmer.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
