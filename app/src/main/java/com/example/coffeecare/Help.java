package com.example.coffeecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Help extends AppCompatActivity {
    CardView locateshop, diseaseinfo, chemicalpdts, tips;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivities(new Intent[]{new Intent(Help.this, MainActivity.class)});
        finish();
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        diseaseinfo = findViewById(R.id.diseaseinfo);
        chemicalpdts = findViewById(R.id.chempdts);
        locateshop = findViewById(R.id.shops);

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





    }
}