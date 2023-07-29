package com.example.coffeecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class LoginFarmer extends AppCompatActivity {
    private EditText etFarmerEmail, etFarmerPassword;
    private Button btnFarmerLogin;
    FirebaseAuth firebaseAuth;
    TextView Clickregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_farmer);

        Clickregister = findViewById(R.id.registernow);
        etFarmerEmail = findViewById(R.id.etFarmerEmail);
        etFarmerPassword = findViewById(R.id.etFarmerPassword);
        btnFarmerLogin = findViewById(R.id.btnFarmerLogin);

        Clickregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterFarmer.class);
                startActivity(intent);
                finish();
            }
        });

        btnFarmerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the entered values
                String email = etFarmerEmail.getText().toString().trim();
                String password = etFarmerPassword.getText().toString().trim();

                // Perform login process
                if (TextUtils.isEmpty(email)) {
                    etFarmerEmail.setError("Please enter your email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etFarmerPassword.setError("Please enter your password");
                    return;
                }

                // Use Firebase Authentication to authenticate the farmer
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginFarmer.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Farmer login successful
                                    Toast.makeText(LoginFarmer.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    // Proceed to the farmer's activity
                                    // You can use startActivity() to launch the farmer's activity
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // Farmer login failed
                                    Toast.makeText(LoginFarmer.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user is already logged in on app launch
        // If yes, redirect them to the main activity
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginFarmer.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivities(new Intent[]{new Intent(LoginFarmer.this, Home.class)});
        finish();
    }
}
