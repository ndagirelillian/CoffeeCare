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

public class LoginAgro extends AppCompatActivity {
    private EditText etAgrochemistEmail, etAgrochemistPassword;
    private Button btnAgrochemistLogin;
    private FirebaseAuth firebaseAuth;
    private TextView Clickregister;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginAgro.this, Home.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_agro);

        Clickregister = findViewById(R.id.registernow);
        etAgrochemistEmail = findViewById(R.id.etAgrochemistEmail);
        etAgrochemistPassword = findViewById(R.id.etAgrochemistPassword);
        btnAgrochemistLogin = findViewById(R.id.btnAgrochemistLogin);

        // Initialize Firebase Authentication instance
        firebaseAuth = FirebaseAuth.getInstance();

        Clickregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterAgro.class);
                startActivity(intent);
                finish();
            }
        });

        btnAgrochemistLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etAgrochemistEmail.getText().toString().trim();
                String password = etAgrochemistPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginAgro.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Use Firebase Authentication to authenticate the agrochemist
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginAgro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Agrochemist login successful
                                    Toast.makeText(LoginAgro.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    // Proceed to the agrochemist's activity
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    Intent intent = new Intent(LoginAgro.this, AgroEntry.class);
                                    intent.putExtra("USER_ID", userId);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Agrochemist login failed
                                    Toast.makeText(LoginAgro.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
