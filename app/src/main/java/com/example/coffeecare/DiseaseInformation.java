package com.example.coffeecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class DiseaseInformation extends AppCompatActivity {
    Button locateshopbtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivities(new Intent[]{new Intent(DiseaseInformation.this, Gallery.class)});
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_information);

        locateshopbtn = findViewById(R.id.btnLocateShops);

        locateshopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateAgrochemist();
            }
        });

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
    // In the farmer's activity where the button is clicked
    private void locateAgrochemist() {
        double agrochemistLatitude = 0; // Get the agrochemist's latitude from your data source
        double agrochemistLongitude = 0;// Get the agrochemist's longitude from your data source

        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("agrochemist_latitude", agrochemistLatitude);
        intent.putExtra("agrochemist_longitude", agrochemistLongitude);
        startActivity(intent);
    }
//    Make sure you've set up the Google Maps API in your project and have the
//    necessary permissions and API keys configured in your AndroidManifest.xml file.

}
